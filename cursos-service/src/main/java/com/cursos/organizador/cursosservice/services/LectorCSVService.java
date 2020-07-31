package com.cursos.organizador.cursosservice.services;

import com.cursos.organizador.cursosservice.repository.CarreraRepository;
import com.cursos.organizador.cursosservice.repository.CursoPlanDeEstudiosRepository;
import com.cursos.organizador.cursosservice.repository.CursoRepository;
import com.cursos.organizador.cursosservice.repository.PlanDeEstudiosRepository;
import com.cursos.organizador.model.model.Carrera;
import com.cursos.organizador.model.model.Curso;
import com.cursos.organizador.model.model.CursoPlanDeEstudios;
import com.cursos.organizador.model.model.PlanDeEstudios;
import com.cursos.organizador.model.model.enums.ETipoCurso;
import com.cursos.organizador.model.model.enums.ETipoPlanEstudios;
import com.cursos.organizador.model.model.enums.ETipoRequisito;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.Buffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@ComponentScan({"com.cursos.organizador.cursosservice.repository"})
public class LectorCSVService {

    public void main(String[] args) {
        leerCsvsLocales();
    }

    private static List<Curso> cursos;
    private static List<PlanDeEstudios> planes;

    @Autowired
    public LectorCSVService(PlanDeEstudiosRepository pde, CarreraRepository cr){
        pdeRepo = pde;
        carreraRepository = cr;
    }

    private final PlanDeEstudiosRepository pdeRepo;
    private final CarreraRepository carreraRepository;

    public void leerCsvsLocales(){
        // Llenar lista de cursos
        cursos = new ArrayList<>(); // TODO que sea el repository
        planes = new ArrayList<>();
        // Extraer lista de los archivos del directorio
        String path = "./PlanesEstudio/";
        File dirPath = new File(path);
        BufferedReader reader;
        for(File f : Objects.requireNonNull(dirPath.listFiles())){
            System.out.println("Leyendo:" + f.getName());
            try {
                reader = Files.newBufferedReader(Paths.get(f.getPath()));
                planes.add(leerCsv(reader));
            }
            catch(Exception e){
                System.out.println(e);
            }
        }
        // TODO Pensar en como Persistir cursos o si se persistiran desde el PdE
//        for(Curso c : cursos)
//            cursoRepository.save(c);
    }

    // Debe recibir un file de leerCsvsLocales
    public PlanDeEstudios leerCsv(BufferedReader archivo) throws IOException {
        String[] headers = {"Ciclo","Codigo","Curso","CTeoricas","PTipoA","PaPeriodo","Pb",
                "PbPeriodo","Requisitos","Creditos","ModalidadEv"};

        // Crear el objeto PlanDeEstudios y leer el encabezado
        PlanDeEstudios planDeEstudios = new PlanDeEstudios();

        // Leer datos del inicio y devolver el Reader cuando inicia el primer curso obligatorio
        leerEncabezado(planDeEstudios, archivo);

        // Iterar por los datos de los cursos
        CSVParser lineas = CSVFormat.DEFAULT.withHeader(headers).parse(archivo);

        // Leer los cursos obligatorios
        int cicloActual = 0;
        for(CSVRecord linea: lineas){
            // Salir si llega a cursos electivos
            if(linea.get(0).equals("Electivos")) break;

            // Extraer ciclo
            cicloActual = leerCiclo(cicloActual, linea.get("Ciclo"));

            // Evaluar opciones si el código está vacío
            if(linea.get("Codigo").isEmpty()) {
                evaluarCodigoVacio(linea.get("Curso"), cicloActual, planDeEstudios);
                continue;
            }

            // Si no, entonces es un curso comun y silvestre
            CursoPlanDeEstudios c = leerCurso(cicloActual, linea,planDeEstudios);
            c.setTipoCurso(ETipoCurso.Obligatorio);
            c.setPlanDeEstudios(planDeEstudios);
            planDeEstudios.getCursos().add(c);

        }
        int i = 0;
        // Leer los cursos electivos
        for(CSVRecord linea: lineas){
            // Saltar encabezado
            if(i==0){i++;continue;}

            // Todos los cursos son comunes y silvestres, se coloca cicloActual en 20 para los electivos
            CursoPlanDeEstudios c = leerCurso(20, linea,planDeEstudios);
            c.setTipoCurso(ETipoCurso.Electivo);
            c.setPlanDeEstudios(planDeEstudios);
            planDeEstudios.getCursos().add(c);
        }
        archivo.close();

        // Imprimir Plan de Estudios
        //System.out.println(planDeEstudios);

        // TODO Persistir Plan de Estudios
        System.out.println("Guardando");
        carreraRepository.save(planDeEstudios.getCarrera());
        pdeRepo.save(planDeEstudios);
//        for(CursoPlanDeEstudios cp : planDeEstudios.getCursos()){
//            System.out.println(cp.getCurso().getCode());
//            cursoPlanDeEstudiosRepository.save(cp);
//        }
        System.out.println("Guardado");
        return planDeEstudios;
    }

    // region encabezado

    private void leerEncabezado(PlanDeEstudios planDeEstudios, BufferedReader archivo) throws IOException {
        // Tiene la forma de
        // Plan de Estudios de Facultad
        // Vigente 2020-1
        // Ingeniería Informática

        // Plan de Estudios de Facultad
        String aux = archivo.readLine();
        leerTipoPlanEstudios(planDeEstudios,aux);

        // Vigente 2020-1
        aux = archivo.readLine();
        // TODO leer y decidir que clase de ciclo se usara

        // Ingeniería Informática
        // TODO Buscar de la lista de carreras el nombre y asignarselo al plan de estudios
        // Si no esta, entonces tomar la nueva carrera
        planDeEstudios.getCarrera().setNombre(archivo.readLine().replaceAll(",",""));
        planDeEstudios.getCarrera().getPlanesDeEstudios().add(planDeEstudios);

        // Leer los comentarios como el significado de los asteriscos para este plan de estudios
        leerComentariosEncabezado(planDeEstudios,archivo);
    }

    private void leerTipoPlanEstudios(PlanDeEstudios planDeEstudios, String aux){
        // Plan de Estudios de Facultad
        if(aux.contains("Facultad"))
            planDeEstudios.setTipoPlanEstudios(ETipoPlanEstudios.Facultad);
        else if(aux.contains("Generales"))
            planDeEstudios.setTipoPlanEstudios(ETipoPlanEstudios.Generales);
        else if(aux.contains("Completo"))
            planDeEstudios.setTipoPlanEstudios(ETipoPlanEstudios.Completo);
        else
            planDeEstudios.setTipoPlanEstudios(ETipoPlanEstudios.Otro);
    }

    private void leerComentariosEncabezado(PlanDeEstudios planDeEstudios, BufferedReader archivo) throws IOException {
        // Los comentarios pueden ser de la siguiente manera
        String aux;
        while(true) {
            aux = archivo.readLine();

            // Salir una vez se halle el encabezado de la tabla
            if(aux.startsWith("CI")) break;
            evaluarExpresionEncabezado(planDeEstudios,aux);
        }
    }

    private void evaluarExpresionEncabezado(PlanDeEstudios planDeEstudios, String aux) {
        // TODO Si no, evaluar las expresiones brindadas
        // * Pueden ser cursos de otras facultades con autorizacion del decano (Matematica)
        // ** Pueden considerarse 2 cursos de libre disponibilidad

        // * Del plan de estudios de esta especialidad
        // ** Pueden considerarse 3 cursos de libre disponibilidad hasta por un máximo de 8 créditos y
        // cursos de otras facultades, con autorización del Decano.

        // TODO quiza agregar esto a la clase plan de estudios?
        //Cursos de libre disponibilidad	3
        //Max. Creditos Cursos de Libre Disponibilidad	8

        // * Del plan de estudios de esta especialidad (Mecatronica)
        //** Pueden ser cursos de otras facultades, con autorización del Decano (Mecatronica)

    }

    // endregion

    // region Leer Cursos
    private int leerCiclo(int cicloActual, String strCiclo) {
        // Se recibe el ciclo actual porque el string puede ser un numero o vacio
        // Si es un numero entonces significa que se cambia de ciclo
        // Tratar de pasar a numero el string recibido
        try{cicloActual = Integer.parseInt(strCiclo);}
        catch(Exception e){return cicloActual;}
        return cicloActual;
    }

    private CursoPlanDeEstudios leerCurso(int cicloActual, CSVRecord linea, PlanDeEstudios pde){
        // Crear CursoPlanDeEstudios y colocar el ciclo
        CursoPlanDeEstudios curso = new CursoPlanDeEstudios();
        curso.setCiclo(cicloActual);

        // Buscar el Curso (Crea uno nuevo si no existe)
        Curso c = buscarCurso(linea.get("Codigo"), pde,false).getCurso();
        // Si el curso no existe, ingresar sus datos
        if(c.getCreditos()==0){
            // Codigo ya ingresado en buscarCurso
            // Nombre
            c.setNombre(linea.get("Curso"));
            // Creditos
            if(!linea.get("Creditos").isEmpty())
                c.setCreditos(Double.parseDouble(linea.get("Creditos")));
            else
                c.setCreditos(0);
            // Unidad Academica
            // c.setUnidadAcademica();
        }
        curso.setCurso(c);
        // Requisitos y/o Minimo de Creditos
        leerRequisitos(curso,linea.get("Requisitos"), pde);
        return curso;
    }

    private CursoPlanDeEstudios buscarCurso(String code, PlanDeEstudios pde, boolean agregando) {

        // Si el curso esta en el Plan de Estudios actual
        for(CursoPlanDeEstudios cpd : pde.getCursos()){
            if(cpd.getCurso().getCode().equals(code)) {
                return cpd;
            }
        }
        // Agregarlo al plan de estudios
        CursoPlanDeEstudios c = new CursoPlanDeEstudios();
        c.setPlanDeEstudios(pde);
        // Si el curso esta en el repositorio de todos los cursos
        boolean cursoEncontrado = false;
        for(Curso cur : cursos){
            if(cur.getCode().equals(code)){
                c.setCurso(cur);
                cursoEncontrado = true;
                break;
            }
        }

        // Si no se hallo el curso, crearlo y agregarlo a la lista de cursos
        if(!cursoEncontrado){
            c.getCurso().setCode(code);
            cursos.add(c.getCurso());
        }

        // Agregar el curso al plan de estudios si esta activado
        if(agregando)
            pde.getCursos().add(c);
        return c;
    }

    // region Requisitos
    private void leerRequisitos(CursoPlanDeEstudios curso, String reqs, PlanDeEstudios pde) {
        // Volver si no hay requisitos
        if (reqs.isEmpty()) return;

        // Otros requisitos como
        // Acreditar capacidad de lectura
        // reqs = leerOtros(reqs, curso);
        // if (reqs.isEmpty()) return;

        // Minimo de Creditos
        reqs = extraerMinCreditos(reqs,curso);

        if (reqs.isEmpty()) return;

        // Si hay requisitos entonces buscarlos segun su tipo con regex
        for (String code : extraerRequisitos(reqs))
            curso.addRequisito(buscarCurso(code, pde,true), ETipoRequisito.HaberAprobado);
        for (String code : extraerRequisitosDurante(reqs))
            curso.addRequisito(buscarCurso(code, pde,true), ETipoRequisito.LlevarSimultaneo);
        for (String code : extraerRequisitosNotaMinima(reqs))
            curso.addRequisito(buscarCurso(code, pde,true), ETipoRequisito.NotaMinima08);
    }

    private String extraerMinCreditos(String linea, CursoPlanDeEstudios curso){
        // Si contiene creditos aprobados, leer el numero de creditos aprobados minimos usando regex
        Pattern p = Pattern.compile("\\d+\\s?cré?e?ditos aprobados\\s?.*\\*?\\s?");
        Matcher m = p.matcher(linea);
        if (m.find()) {
            String s = linea.substring(m.start(), m.end());
            // Devolver la linea sin esa parte
            linea = linea.substring(0, m.start()) + linea.substring(m.end());
            // Extraer solo el numero
            p = Pattern.compile("\\d+");
            m = p.matcher(s);
            if(m.find()) {
                curso.setMinCreditos(Double.parseDouble(s.substring(m.start(), m.end())));
            }
        }
        return linea;
    }

    private Iterable<String> extraerRequisitos(String reqs){
        reqs =reqs.replaceAll("\\s","");
        // Separar uno por uno los requisitos, pues estan separados por comas
        String[] requisitos = reqs.split(",");
        List<String> devolver = new ArrayList<>();
        for (String requisito : requisitos) {
            // Si no hay un '[' o un '(' agregarlo
            if(requisito.indexOf('[') <0 && requisito.indexOf('(')<0)
                devolver.add(requisito);
        }
        return devolver;
    }

    private Iterable<String> extraerRequisitosDurante(String reqs){
        reqs =reqs.replaceAll("\\s","");
        String[] requisitos = reqs.split(",");
        List<String> devolver = new ArrayList<>();
        for (String requisito : requisitos) {
            if(requisito.charAt(0) == '[')
                devolver.add(requisito.substring(1,requisito.length()-1));
        }
        return devolver;
    }

    private Iterable<String> extraerRequisitosNotaMinima(String reqs){
        reqs =reqs.replaceAll("\\s","");
        String[] requisitos = reqs.split(",");
        List<String> devolver = new ArrayList<>();
        for (String requisito : requisitos) {
            if(requisito.charAt(0) == '(')
                devolver.add(requisito.substring(1,requisito.length()-1));
        }
        return devolver;
    }

    // endregion

    private void evaluarCodigoVacio(String linea, int ciclo, PlanDeEstudios planDeEstudios) {
        // TODO
        // Entonces es posible este leyendo una línea de cursos electivos
        // Cuatro (4) cursos electivos**
        // Un (01) curso electivo (**)
        // Dos (2) cursos electivos *
        // Un (01) electivo de libre disponibilidad (*)
        // Un (01) electivo de Física
        // Cuatro (4) cursos electivos* (Biomédica)

        // Cambiar el numero de electivos del planDeEstudios


    }

    //endregion


}
