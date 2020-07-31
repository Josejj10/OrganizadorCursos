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