package com.reservaVuelos.modelo.vuelo;

import com.reservaVuelos.modelo.Identifcador;

import java.util.List;


public class Avion implements Identifcador {

    private final Long idAvion;  // 7 caracteres (10^7 = 10,000,000 posibles IDs)
    private ModeloAvion modeloAvion; // 11 caracteres
    private int capacidad; // 3 caracteres
    private final List<Asiento> asientosAvion;

    public Avion(Long idAvion, ModeloAvion modeloAvion, List<Asiento> asientosAvion) {
        this.idAvion = idAvion;
        this.modeloAvion = modeloAvion;
        this.capacidad = modeloAvion.getCapacidadAsientos();



        this.asientosAvion = asientosAvion;
    }

    @Override
    public Long getId() {
        return idAvion;
    }

    public ModeloAvion getModeloAvion() {
        return modeloAvion;
    }

    public void setModeloAvion(ModeloAvion modeloAvion) {
        this.modeloAvion = modeloAvion;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public List<Asiento> getAsientosAvion() {
        return asientosAvion;
    }

    public void setAsientosAvion(List<Asiento> asientosAvion) {}
}
