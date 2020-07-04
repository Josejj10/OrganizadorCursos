package com.cursos.organizador.cursosservice.postjson;

import com.cursos.organizador.cursosservice.CursosServiceApplication;
import com.cursos.organizador.model.model.Apunte;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CursosServiceApplication.class)
public class ApunteAPILiveTest {
    private static String createApunteUrl;
    private static RestTemplate restTemplate;
    private static String updateApunteUrl;
    private static HttpHeaders headers;
    private static JSONObject apunteJsonObject;

    @BeforeClass
    public static void runBeforeAllTestMethods() throws JSONException{
        createApunteUrl = "http://localhost:8080/apuntes/createApunte";
        restTemplate = new RestTemplate();
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Apunte apunte = new Apunte(1l);
        apunte.setTexto("Test Lorem Ipsum");
        apunte.setTitulo("Title Lorem");
        apunte.addTag("test tag");
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
        apunteJsonObject = new JSONObject(gson.toJson(apunte));
    }

    @Test
    public void addApunte_ShouldNotBeNull(){
        HttpEntity<String> request =
                new HttpEntity<String>(apunteJsonObject.toString(),headers);
        Apunte apunte = restTemplate.postForObject(createApunteUrl,request, Apunte.class);
        assertNotNull(apunte);
        assertEquals(apunte.getTitulo(),"Title Lorem");
    }


}
