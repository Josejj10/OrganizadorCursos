package com.cursos.organizador.model.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Apunte")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Apunte implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private Date fechaCreado;
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private Date fechaUltimaModificacion;
    private String titulo;
    private String texto;
    @ElementCollection
    private List<String> tags;

    @ManyToOne
    @JsonIgnoreProperties("apuntes") // Cuando carga un apunte, ignora apuntes del curso porque seria recursivo
    private Curso curso;

    private static final long serialVersionUID = 1L;


    public Apunte() {
        tags = new ArrayList<>();
        fechaUltimaModificacion = fechaCreado = Date.from(Instant.now());
        titulo = "titulo";
        texto = "Lorem ipsum.";
        curso = new Curso();
    }
    public Apunte(Long idCurso) {
        tags = new ArrayList<>();
        fechaUltimaModificacion = fechaCreado = Date.from(Instant.now());
        titulo = "titulo";
        texto = "Lorem ipsum.";
        curso = new Curso(idCurso);
    }

    public Curso getCurso() {
        return curso;
    }

    public Long getIdCurso(){
        return curso.getId();
    }

    public void setCurso(Curso c) {
        curso = c;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void addTag(String t) {
        tags.add(t);
    }

    public void removeTag(String t) {
        tags.remove(t);
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFechaCreado() {
        return fechaCreado;
    }

    public void setFechaCreado(Date fechaCreado) {
        this.fechaCreado = fechaCreado;
    }

    public Date getFechaUltimaModificacion() {
        return fechaUltimaModificacion;
    }

    public void setFechaUltimaModificacion(Date fechaUltimaModificacion) {
        this.fechaUltimaModificacion = fechaUltimaModificacion;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Apunte)) {
            return false;
        }
        Apunte other = (Apunte) object;
        return (id != null || other.getId() == null) && (id == null || other.getId().equals(other.getId()));
    }

    @Override
    public String toString() {
        return "com.organizador.model.Apunte[ id=" + id + " ]";
    }
}

