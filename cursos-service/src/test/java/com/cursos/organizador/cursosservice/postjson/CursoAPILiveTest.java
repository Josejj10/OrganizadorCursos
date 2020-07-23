package com.cursos.organizador.cursosservice.postjson;

import com.cursos.organizador.cursosservice.CursosServiceApplication;
import com.cursos.organizador.model.model.Curso;
import com.cursos.organizador.model.model.enums.ETipoRequisito;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CursosServiceApplication.class)
public class CursoAPILiveTest {

    private static String createCursoUrl;
    //private static String updateCursoUrl;

    private static RestTemplate restTemplate;

    private static HttpHeaders headers;

    //private final ObjectMapper objectMapper = new ObjectMapper();

    private static JSONObject cursoJsonObject;

    @BeforeClass
    public static void runBeforeAllTestMethods() throws JSONException{
        createCursoUrl = getRootUrl() + "/cursos";
        restTemplate = new RestTemplate();
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Curso curso = new Curso();
        curso.setCode("ABC123");
        curso.setCreditos(4);
        curso.setNombre("LP1");
        curso.setUltimoCicloDictado("ProfInsertado1");
        Gson gson = new GsonBuilder().create();
        cursoJsonObject = new JSONObject(gson.toJson(curso));
    }

    private static String getRootUrl() {
        return "http://localhost:8080";
    }

    @Test
    public void addLP1LP2Requisito_ShouldAdd(){
        add2Cursos_ShouldAddLP1LP2();
        addRequisito_ShouldAdd();
    }


    @Test
    public void add2Cursos_ShouldAddLP1LP2(){
        addCurso_ShouldNotBeNull();
        addCurso_ShouldNotBeNull();
        updateCurso_ShouldBeLP2();
    }

    @Test
    public void addCurso_ShouldNotBeNull(){
        HttpEntity<String> request = new HttpEntity<String>(cursoJsonObject.toString(), headers);
        Curso curso = restTemplate.postForObject(createCursoUrl, request, Curso.class);
        assertNotNull(curso);
        assertNotNull(curso.getNombre());
    }

    @Test
    public void updateCurso_ShouldBeLP2(){
        int id = 1;
        Curso curso = restTemplate.getForObject(getRootUrl() + "/cursos/"+id,Curso.class);
        curso.setNombre("Técnicas de Programación");
        curso.setCreditos(5);
        restTemplate.put(getRootUrl()+"/cursos/"+id,curso);
        curso = restTemplate.getForObject(getRootUrl() + "/cursos/"+id,Curso.class);
        assertEquals("LP2",curso.getNombre());
        assertEquals(5,curso.getCreditos());
    }

    @Test
    public void addRequisito_ShouldAdd(){
        int lp2 = 3;
        int lp1 = 1;
        Curso lp1Curso =restTemplate.getForObject(getRootUrl()+"/cursos/"+lp1,Curso.class);
        Curso lp2Curso =restTemplate.getForObject(getRootUrl()+"/cursos/"+lp2,Curso.class);
        lp2Curso.addRequisito(lp1Curso, ETipoRequisito.LlevarSimultaneo);
        restTemplate.put(getRootUrl()+"/cursos/"+lp2,lp2Curso);
        System.out.println("Agregado1");
        restTemplate.put(getRootUrl()+"/cursos/"+lp1,lp1Curso);
        System.out.println("Agregado2");
        ArrayList<Curso> reqPor = restTemplate.getForObject(getRootUrl()+"/cursos/"+lp1+"/requeridoPor",ArrayList.class);
        ArrayList<Curso> reqs =restTemplate.getForObject(getRootUrl()+"/cursos/"+lp2+"/requisitos",ArrayList.class);
        assert lp1Curso != null;
        assertNotNull(reqPor);
        assertNotNull(reqs);
    }


//    @Test
//    public void getCurso_ShouldGetApuntesAndCurso() throws IOException {
//        HttpEntity<String> request = new HttpEntity<String>(cursoJsonObject.toString(), headers);
//
//
//        curso.setApuntes(apuntesService.getApuntes(c.getId()));
//
//        assertNotNull(curso);
//        assertNotNull(curso.getNombre());
//    }

}