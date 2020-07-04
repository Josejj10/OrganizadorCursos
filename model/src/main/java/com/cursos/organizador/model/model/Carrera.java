package com.cursos.organizador.model.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Carrera")
public class Carrera {
    @Id
    @GeneratedValue
    private int id;
    private String nombre;
    private String link;
    private int cicloIngles1 = 4;
    private int cicloIngles2 = 8;
    private int cicloPSP = 9;
    private int numElectivos = 0;
    private boolean tieneTrabajoBachiller = false;
    @ElementCollection
    private List<String> obligatorios;
    @ElementCollection
    private List<String> electivos;

    public Carrera(){
        obligatorios= new ArrayList<>();
        electivos=new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isTieneTrabajoBachiller() {
        return tieneTrabajoBachiller;
    }

    public void setTieneTrabajoBachiller(boolean tieneTrabajoBachiller) {
        this.tieneTrabajoBachiller = tieneTrabajoBachiller;
    }

    public int getCicloIngles1() {
        return cicloIngles1;
    }

    public void setCicloIngles1(int cicloIngles1) {
        this.cicloIngles1 = cicloIngles1;
    }

    public int getCicloIngles2() {
        return cicloIngles2;
    }

    public void setCicloIngles2(int cicloIngles2) {
        this.cicloIngles2 = cicloIngles2;
    }

    public int getCicloPSP() {
        return cicloPSP;
    }

    public void setCicloPSP(int cicloPSP) {
        this.cicloPSP = cicloPSP;
    }

    public int getNumElectivos() {
        return numElectivos;
    }

    public void setNumElectivos(int numElectivos) {
        this.numElectivos = numElectivos;
    }

    public List<String> getObligatorios() {
        return obligatorios;
    }

    public void setObligatorios(List<String> obligatorios) {
        this.obligatorios = obligatorios;
    }

    public void setElectivos(List<String> electivos) {
        this.electivos = electivos;
    }

    public List<String> getElectivos() {
        return electivos;
    }

    public void addCursoObligatorio(String codigo){
        obligatorios.add(codigo);
    }

    public void addCursoElectivo(String codigo){
        electivos.add(codigo);
    }

    public void removeCurso(String cod){
        if(!electivos.remove(cod))
            obligatorios.remove(cod);
    }

    public void imprimirCursos(){
        System.out.println("Cursos de "+getNombre());
        System.out.println("=============");
        System.out.println("Obligatorios");
        System.out.println("-------------");
        for(String linea : obligatorios){
            System.out.println(linea);
        }
        System.out.println("=============");
        System.out.println("Electivos");
        System.out.println("-------------");
        for(String linea : electivos){
            System.out.println(linea);
        }
        System.out.println("=============");
    }

    public boolean tieneCurso(String cod){
        return tieneCursoElectivo(cod) || tieneCursoObligatorio(cod);
    }

    public boolean tieneCursoObligatorio(String cod){
        return obligatorios.contains(cod);
    }
    public boolean tieneCursoElectivo(String cod){
        return electivos.contains(cod);
    }



}