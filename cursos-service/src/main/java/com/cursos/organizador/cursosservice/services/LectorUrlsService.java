package com.cursos.organizador.cursosservice.services;

import com.cursos.organizador.cursosservice.services.UrlsPUCP;
import com.cursos.organizador.cursosservice.repository.CarreraRepository;
import com.cursos.organizador.cursosservice.repository.CursoRepository;
import com.cursos.organizador.model.model.Carrera;
import com.cursos.organizador.model.model.Curso;
import org.apache.commons.lang.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


// Clase llamada por el controller al entrar a localhost:8080/actualizarCursos
// NO es para probar el algoritmo. Este se prueba en LectorUrls.java en el paquete logic

@Service
@ComponentScan({"com.cursos.organizador.cursosservice.controller"})
public class LectorUrlsService {

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private CarreraRepository carreraRepository;

    private static List<Carrera> carreras = new ArrayList<>();
    private static List<Curso> cursos = new ArrayList<>();
    private static List<String> lineasCriterioElectivos =
            new ArrayList<String>(){
                {
                    add("* Del plan de estudios ");
                    add("ME: 1 Ex");
                }
            };

    public static List<Curso> getCursos() {
        return cursos;
    }

    public static void setCursos(List<Curso> cursos) {
        LectorUrlsService.cursos = cursos;
    }

    public List<Carrera> leer(){
        leerUrls();
        Curso r;
        for(Curso c : cursos){
            System.out.println(c);
            r = cursoRepository.save(c);
            System.out.println(r);
        }

        for(Carrera c : carreras){
            carreraRepository.save(c);
        }
        return carreras;
    }



    public static void imprimirCursos(){
        for(Curso curso : cursos){
            System.out.println("===========================================");
            System.out.println(curso.getCode()+": "+curso.getNombre());
            System.out.println("Ciclo " + curso.getCiclo());
            System.out.println("Creditos: "+ curso.getCreditos());
            System.out.println("Requisitos:");
            for(Curso c : curso.getRequisitosCurso(0))
                System.out.println("\t"+c.getCode() + ": " +c.getNombre());
            System.out.println("Requisitos a la vez: ");
            for(Curso c : curso.getRequisitosCurso(1))
                System.out.println("\t"+c.getCode() + ": " +c.getNombre());
            System.out.println("Requisitos nota minima 08: ");
            for(Curso c : curso.getRequisitosCurso(2))
                System.out.println("\t"+c.getCode() + ": " +c.getNombre());
            System.out.println("Minimo de Creditos: "+curso.getMinCreditos());
        }
    }

    private static void leerUrls() {
        // web scrapping
        UrlsPUCP.URLs.forEach((nom, link) -> {
                    System.out.println("Leyendo " + nom);

                    Carrera c = new Carrera();
                    c.setNombre(nom);
                    c.setLink(link);
                    leerUrl(link,c);
                }
        );
//
//        for(Carrera c : carreras){
//            c.imprimirCursos();
//        }
    }

    private static void leerUrl(String u, Carrera carrera) {
        try{
            URL url = new URL(u);
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(url.openStream()));
            BufferedInputStream bis = new BufferedInputStream(url.openStream());
            PDDocument doc = PDDocument.load(bis);
            PDFTextStripper pdf = new PDFTextStripper();
            pdf.setEndPage(1);
            String obligatorios = pdf.getText(doc);
            pdf.setEndPage(2);
            pdf.setStartPage(2);
            String electivos = pdf.getText(doc);
            doc.close();
            bis.close();
            leerCursos(obligatorios,carrera, true);
            leerCursos(electivos,carrera, false);
            carreras.add(carrera);
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    private static void leerCursos(String txtPdf, Carrera carrera, boolean leyendoObligatorios) {
        List<String> lineas;
        AtomicInteger ciclo = new AtomicInteger(0);
        lineas = arreglarLineas(new LinkedList<>(Arrays.asList(txtPdf.split("\\r?\n"))),
                carrera, ciclo, leyendoObligatorios);
        if (leyendoObligatorios) {
            for (String string : lineas)
                carrera.addCursoObligatorio(leerCurso(string, ciclo));
        } else {
            for (String string : lineas)
                carrera.addCursoElectivo(leerCursoElectivo(string));
        }
    }

    private static List<String> quitarCabecera(List<String> lineas, int n){
        for(int i = 0;i<n;i++)
            lineas.remove(0);
        return lineas;
    }

    private static List<String> arreglarLineasProblema(List<String> lineas, List<String> lineasProblema){
        Pattern p = Pattern.compile("\\d+\\.\\d+\\s\\d$");
        Matcher m;
        List<String> lineasArregladas = new ArrayList<>();
        String lineaArreglando = "";
        for(String linea : lineasProblema){
            lineaArreglando+=" "+linea;
            m = p.matcher(lineaArreglando);
            if(m.find()){
                lineasArregladas.add(lineaArreglando);
                lineaArreglando="";
            }
        }
        for(String linea :lineasArregladas){
            // Quitar doble espacio
            linea = linea.replaceAll("\\s+", " ");
            linea = linea.replaceFirst("^\\s*", "");
            lineas.add(linea);
        }
        return lineas;
    }


    private static int criteriosCursosObligatorios(String linea,Carrera carrera, AtomicInteger ciclo){
        Pattern p;
        Matcher m;
        if(linea.contains("Idioma")) {
            carrera.setCicloIngles2(ciclo.intValue()+1);
            return 1;
        }
        if(linea.contains("Supervisada")) {
            carrera.setCicloPSP(ciclo.intValue()+1);
            return 0;
        }
        if(linea.contains("electivo") || linea.contains("libre disponibilidad")){
            // TODO cambiar a MAPA de ciclos electivo <ciclo, nCiclos>
            p = Pattern.compile("\\(\\d+\\)");
            m = p.matcher(linea);
            if(m.find())
                carrera.setNumElectivos(carrera.getNumElectivos()+Integer.parseInt(linea.substring(m.start()+1,m.end()-1)));
            return 1;
        }
        if(linea.contains("para Bachillerato")){
            carrera.setTieneTrabajoBachiller(true);
            return 1;
        }
        if (linea.contains("Ciclo ME")) {
            return 2;
        }
        return -1;
    }

    private static int criteriosCursosElectivos(String linea){
        for(String l : lineasCriterioElectivos)
            if(linea.contains(l))
                return 1;
        return -1;
    }

    private static List<String> conseguirLineasProblema(List<String> lineas, Carrera carrera, AtomicInteger ciclo, boolean obligatorio){
        boolean borrando = false;
        List<String> lineasProblema = new ArrayList<>();
        Iterator<String> itr = lineas.iterator();
        Pattern p;
        Matcher m;

        while(itr.hasNext()){
            String linea = itr.next();
            if(borrando){
                itr.remove();
                continue;
            }
            linea = leerCiclo(linea); // Ignorar el primer numero del ciclo

            if(obligatorio) {
                switch (criteriosCursosObligatorios(linea, carrera, ciclo)) {
                    case 1:
                        itr.remove();
                        continue;
                    case 2:
                        itr.remove();
                        borrando = true;
                        continue;
                    case 0:
                        continue;
                    default:
                        break;
                }
            }else {
                if (criteriosCursosElectivos(linea) == 1) {
                    borrando = true;
                    itr.remove();
                    continue;
                }
            }

            // Verificar si linea comienza con un codigo
            p = Pattern.compile("^[A-Za-z0-9]{6}\\s");
            m = p.matcher(linea);
            if(m.find()){
                // Verificar que la linea termina con un "4.0 4", (creditos y tipo de evaluacion)
                p = Pattern.compile("\\d+\\.\\d+\\s?\\d?$");
                m = p.matcher(linea);
                if(!m.find()){
                    lineasProblema.add(linea);
                    itr.remove();
                }
            }else{
                lineasProblema.add(linea);
                itr.remove();
            }
        }

        return lineasProblema;
    }

    private static List<String> arreglarLineas(List<String> lineas, Carrera carrera,AtomicInteger ciclo, boolean obligatorio){

        lineas = quitarCabecera(lineas,3);

        Pattern p;
        Matcher m;
        boolean borrando = false;
        List<String> lineasProblema = conseguirLineasProblema(lineas,carrera,ciclo, obligatorio);

        return arreglarLineasProblema(lineas,lineasProblema);
    }

    private static String leerCursoElectivo(String linea) {
        String lineaInicio = linea; // Para errores
        Curso curso;
        // Verificar si hay un numero de ciclo al comienzo
        linea = leerCiclo(linea);
        // Leer codigo -> Primeros 6 caracteres
        curso = buscarCurso(linea.substring(0, 6));
        curso.setCiclo(20); // Cursos con ciclo 20 son electivos
        return leerCursoR(lineaInicio, linea, curso);
    }

    private static String leerCursoR(String lineaInicio, String linea, Curso curso){
        try {
            // Nombre del curso
            linea = extraerNombre(linea.substring(7), curso);

            // Minimo de creditos
            linea = extraerMinCreditos(linea, curso);

            // Conseguir requisitos
            String[] conseguirR = conseguirRequisitos(linea);
            String reqs = conseguirR[0];
            linea = conseguirR[1];

            // Creditos del curso
            curso.setCreditos(extraerCreditos(linea));
            // Agregar requisitos si hay, y devolver el codigo
            return agregarRequisitos(reqs, curso);
        }catch (Exception e){
            System.out.println("!==========================================");
            System.out.println("Error leyendo: "+ lineaInicio + ":\n\t " +linea+"\nExcepcion: "+e);
            System.out.println("!==========================================");
            return null;
        }
    }

    private static String leerCurso(String linea, AtomicInteger c) {
        String lineaInicio = linea; // Para errores
        Curso curso;

        // Verificar si hay un numero de ciclo al comienzo
        linea = leerCiclo(linea, c);
        // Leer codigo -> Primeros 6 caracteres
        curso = buscarCurso(linea.substring(0, 6));
        curso.setCiclo(c.intValue());
        return leerCursoR(lineaInicio, linea, curso);
    }

    private static String extraerNombre(String linea, Curso curso){
        String aux;
        String regex = "(?=(?=[^\\.])[^\\d])[^\\s]";
        int intAux;

        // Nombre del curso
        // Leer la linea hasta el primer numero
        aux = linea.split("[0-9]")[0];
        linea = linea.substring(aux.length());

        // Verificar que el nombre no deberia tener numero, por ejemplo, no es LP1 o LP2
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(linea); // input is your String
        m.find();
        if ((intAux = verificarNombre(linea.substring(0, m.start()))) != -1)
            aux += intAux;
        curso.setNombre(aux);
        return linea;
    }

    private static String extraerMinCreditos(String linea, Curso curso){
        // Si contiene creditos aprobados, leer el numero de creditos aprobados minimos y quitar esa parte
        Pattern p = Pattern.compile("\\d+\\s?créditos aprobados\\s?\\*\\s?");
        Matcher m = p.matcher(linea);
        if (m.find()) {
            String s = linea.substring(m.start(), m.end());
            linea = linea.substring(0, m.start()) + linea.substring(m.end());
            p = Pattern.compile("\\d+");
            m = p.matcher(s);
            if(m.find()) {
                curso.setMinCreditos((int) Float.parseFloat(s.substring(m.start(), m.end())));
            }
        }
        return linea;
    }

    private static String[] conseguirRequisitos(String aux){
        // Conseguir los requisitos
        String reqs = "";
        Pattern p = Pattern.compile("\\[?\\(?[A-Za-z0-9]{6}");
        Matcher m = p.matcher(aux);
        if(m.find()) {
            int mstart = m.start();
            // Buscarlo de nuevo esta vez consumiendo hasta hallar el ultimo
            p = Pattern.compile(".*(\\[?[A-Za-z0-9]{6}\\,?\\)?\\]?)");
            m = p.matcher(aux);
            if (m.find()) {
                reqs = aux.substring(mstart, m.end());
                aux = aux.substring(m.end());
            }
        }
        return new String[] {reqs, aux};
    }

    private static String agregarRequisitos(String reqs, Curso curso){
        // Agregar requisitos si hay
        if(!StringUtils.isEmpty(reqs)) {
            for (String code : extraerRequisitos(reqs))
                curso.addRequisito(buscarAgregarCurso(code), 0);
            for (String code : extraerRequisitosDurante(reqs))
                curso.addRequisito(buscarAgregarCurso(code), 1);
            for (String code : extraerRequisitosNotaMinima(reqs))
                curso.addRequisito(buscarAgregarCurso(code),2);
        }
        if(!cursos.contains(curso))
            cursos.add(curso);
        return curso.getCode();
    }

    private static float extraerCreditos(String aux){
        // Creditos del curso
        Pattern p = Pattern.compile("\\s?\\d+\\.\\d+");
        Matcher m = p.matcher(aux);
        if(!m.find())
            return -1;
        return Float.parseFloat(aux.substring(m.start(), m.end()));
    }

    private static Curso buscarAgregarCurso(String codigo) {

        for(Curso curso : cursos){
//            System.out.println("Buscando "+codigo +": "+ curso.getCode());
            if(curso.getCode().contains(codigo)) {
//                System.out.println("Hallado "+curso.getCode());
                return curso;
            }
        }
//        System.out.println("No hallado "+ codigo +", creando uno nuevo");
        Curso n = new Curso();
        n.setCode(codigo);
        cursos.add(n);
        return n;
    }

    private static Curso buscarCurso(String codigo) {

        for(Curso curso : cursos){
//            System.out.println("Buscando "+codigo +": "+ curso.getCode());
            if(curso.getCode().contains(codigo)) {
//                System.out.println("Hallado "+curso.getCode());
                return curso;
            }
        }
//        System.out.println("No hallado "+ codigo +", creando uno nuevo");
        Curso n = new Curso();
        n.setCode(codigo);
        return n;
    }

    private static String leerCiclo(String linea, AtomicInteger c) {
        Pattern p = Pattern.compile("^\\d+\\s");
        Matcher m = p.matcher(linea);
        if(m.find()){
            c.set(Integer.parseInt(linea.substring(m.start(),m.end()-1)));
            return linea.substring(m.end());
        }

        return linea;
    }

    private static String leerCiclo(String linea) {
        Pattern p = Pattern.compile("^\\d+\\s");
        Matcher m = p.matcher(linea);
        if(m.find()){
            return linea.substring(m.end());
        }
        return linea;
    }

    private static int verificarNombre (String linea) {
        // Separar linea
        // leyendo tres numeros: si falla, entonces no tiene numero al final del nombre
        // Si si se da, entonces tiene al final del nombre
        // De una u otra manera, ignorar los otros numeros (
        try {
            String[] split = linea.split("\\s+");
            float x = Float.parseFloat(split[0]);
            float y = Float.parseFloat(split[1]);
            float z = Float.parseFloat(split[2]);
            return (int)x;
        } catch (Exception e) {
//            System.out.println(e);
            return -1;
        }
    }

    private static Iterable<String> extraerRequisitos(String reqs){
        reqs =reqs.replaceAll("\\s","");
        Iterable<String> requisitos = Arrays.asList(reqs.split("\\,"));
        List<String> devolver = new ArrayList<>();
        for (String requisito : requisitos) {
            if(requisito.charAt(0) != '[')
                devolver.add(requisito);
        }
        return devolver;
    }

    private static Iterable<String> extraerRequisitosDurante(String reqs){
        reqs =reqs.replaceAll("\\s","");
        Iterable<String> requisitos = Arrays.asList(reqs.split("\\,"));
        List<String> devolver = new ArrayList<>();
        for (String requisito : requisitos) {
            if(requisito.charAt(0) == '[')
                devolver.add(requisito.substring(1,requisito.length()-1));
        }
        return devolver;
    }

    private static Iterable<String> extraerRequisitosNotaMinima(String reqs){
        reqs =reqs.replaceAll("\\s","");
        Iterable<String> requisitos = Arrays.asList(reqs.split("\\,"));
        List<String> devolver = new ArrayList<>();
        for (String requisito : requisitos) {
            if(requisito.charAt(0) == '(')
                devolver.add(requisito.substring(1,requisito.length()-1));
        }
        return devolver;
    }

    public static List<Carrera> getCarreras() {
        return carreras;
    }

    public static void setCarreras(List<Carrera> carreras) {
        LectorUrlsService.carreras = carreras;
    }
}
