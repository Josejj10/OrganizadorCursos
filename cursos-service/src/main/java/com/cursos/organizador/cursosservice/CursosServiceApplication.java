package com.cursos.organizador.cursosservice;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@ComponentScan({"com.cursos.organizador.cursosservice.services","com.cursos.organizador.cursosservice.controller",
        "com.cursos.organizador.cursosservice.dto", "com.cursos.organizador.cursosservice.assembler"})
@EntityScan(basePackages = "com.cursos.organizador.model.model")
@EnableJpaRepositories("com.cursos.organizador.cursosservice.repository")
//@EnableEurekaClient
public class CursosServiceApplication {

    @Bean
    //@LoadBalanced
    public RestTemplate getRestTemplate(){
//        HttpComponentsClientHttpRequestFactory httpRequestFactory
//                = new HttpComponentsClientHttpRequestFactory();
//        httpRequestFactory.setConnectTimeout(3000);
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(CursosServiceApplication.class, args);
    }

}
