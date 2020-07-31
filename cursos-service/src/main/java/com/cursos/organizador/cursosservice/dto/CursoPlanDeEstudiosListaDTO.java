package com.cursos.organizador.cursosservice.dto;

import com.cursos.organizador.model.model.CursoPlanDeEstudios;

public class CursoPlanDeEstudiosListaDTO {

    private String carrera;
    private String cicloPde;

    public CursoPlanDeEstudiosListaDTO() {
    }

    public CursoPlanDeEstudiosListaDTO(CursoPlanDeEstudios cpe) {
        carrera = cpe.getPlanDeEstudios().getCarrera().getNombre();
        cicloPde = cpe.getPlanDeEstudios().getCicloInicioPlan();
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public String getCicloPde() {
        return cicloPde;
    }

    public void setCicloPde(String cicloPde) {
        this.cicloPde = cicloPde;
    }
}
