package com.cursos.organizador.model.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Formula")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Formula {
    @Id
    @GeneratedValue
    private int id;
    private String formula;
    private static List<String> conceptosConocidos =
            new ArrayList<String>() {{
                add("Laboratorios");
                add("Practicas Calificadas");
                add("Practicas Dirigidas");
                add("Tarea Academica");
                add("Examen 1");
                add("Examen 2");
                add("Examen 3");
                add("Examen 4");
            }};
    private static List<String> abreviacionesConocidas =
            new ArrayList<String>() {{
                add("Lab");
                add("PC");
                add("PD");
                add("TA");
                add("EX1");
                add("EX2");
                add("EX3");
                add("EX4");
            }};

    @ElementCollection
    private List<String> conceptos; // Laboratorio, Practica, etc.
    @ElementCollection
    private List<String> abreviaciones; //Lab, Pc, etc.
    @ElementCollection
    private List<Integer> pesos;
    private int divisor;

    public Formula() {
        conceptos = new ArrayList<>();
        abreviaciones = new ArrayList<>();
        pesos = new ArrayList<>();

        // Por default
        conceptos.add("Practicas Calificadas");
        conceptos.add("Examen 1");
        conceptos.add("Examen 2");

        abreviaciones.add("PC");
        abreviaciones.add("EX1");
        abreviaciones.add("EX2");

        pesos.add(3);
        pesos.add(3);
        pesos.add(4);

        formula = "3PC + 3EX1 + 4EX2";
        divisor = 10;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public static List<String> getConceptosConocidos() {
        return conceptosConocidos;
    }

    public static void addConceptoConocido(String c) {
        conceptosConocidos.add(c);
    }

    public static void removeConceptoConocido(String c) {
        conceptosConocidos.remove(c);
    }

    public static void setConceptosConocidos(List<String> conceptosConocidos) {
        Formula.conceptosConocidos = conceptosConocidos;
    }

    public static List<String> getAbreviacionesConocidas() {
        return abreviacionesConocidas;
    }

    public static void setAbreviacionesConocidas(List<String> abreviacionesConocidas) {
        Formula.abreviacionesConocidas = abreviacionesConocidas;
    }

    public static void addAbreviacionConocido(String c) {
        abreviacionesConocidas.add(c);
    }

    public static void removeAbreviacionConocido(String c) {
        abreviacionesConocidas.remove(c);
    }

    public List<String> getConceptos() {
        return conceptos;
    }

    public void setConceptos(List<String> conceptos) {
        this.conceptos = conceptos;
    }

    public void addConcepto(String c) {
        conceptos.add(c);
        if (!conceptosConocidos.contains(c))
            addConceptoConocido(c);
    }

    public void removeConcepto(String c) {
        conceptos.remove(c);
    }

    public void cambiarConcepto(String a, String nuevo) {
        if (!conceptos.contains(a))
            return;
        conceptos.set(conceptos.indexOf(a), nuevo);
        conceptosConocidos.set(
                conceptosConocidos.indexOf(a), nuevo);
    }

    public List<String> getAbreviaciones() {
        return abreviaciones;
    }

    public void setAbreviaciones(List<String> abreviaciones) {
        this.abreviaciones = abreviaciones;
    }

    public void addAbreviacion(String a) {
        abreviaciones.add(a);
        if (!abreviacionesConocidas.contains(a))
            abreviacionesConocidas.add(a);
    }

    public void cambiarAbreviacion(String a, String nuevo) {
        if (!abreviaciones.contains(a))
            return;
        abreviaciones.set(abreviaciones.indexOf(a), nuevo);
        abreviacionesConocidas.set(
                abreviacionesConocidas.indexOf(a), nuevo);
    }

    public List<Integer> getPesos() {
        return pesos;
    }

    public void setPesos(List<Integer> pesos) {
        this.pesos = pesos;
    }

    public void addPeso(int p) {
        pesos.add(p);
    }

    public void removePeso(int p) {
        pesos.remove(p);
    }

    public void cambiarPeso(int p, int nuevo) {
        if (!pesos.contains(p))
            return;
        pesos.set(pesos.indexOf(p), nuevo);
    }

    public int getDivisor() {
        return divisor;
    }

    public void setDivisor(int divisor) {
        this.divisor = divisor;
    }


}
