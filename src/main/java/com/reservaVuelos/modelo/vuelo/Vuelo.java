package com.reservaVuelos.modelo.vuelo;

import com.reservaVuelos.modelo.Identifcador;

import java.time.LocalDateTime;

public class Vuelo implements Identifcador {
    private Long vueloID;
    private Aerolinea aerolinea;
    private String origenVuelo;
    private String destinoVuelo;
    private LocalDateTime fechaHoraSalida;
    private LocalDateTime fechaHoraLlegada;
    private int capacidad;
    private ModeloAvion modeloAvion;
    private boolean estadoVuelo; //"1 = Programado", "0 = Aterrizado"

    public Vuelo(Long vueloID, Aerolinea aerolinea, String origenVuelo,
                 String destinoVuelo, LocalDateTime fechaHoraSalida, LocalDateTime fechaHoraLlegada,
                 int capacida, ModeloAvion modeloAvion, boolean estadoVuelo) {
        this.vueloID = vueloID;
        this.aerolinea = aerolinea;
        this.origenVuelo = origenVuelo;
        this.destinoVuelo = destinoVuelo;
        this.fechaHoraSalida = fechaHoraSalida;
        this.fechaHoraLlegada = fechaHoraLlegada;
        this.capacidad = capacida;
        this.modeloAvion = modeloAvion;
        this.estadoVuelo = estadoVuelo;
    }

    public void setEstadoVuelo(boolean estadoVuelo) {
        this.estadoVuelo = estadoVuelo;
    }

    public Long getVueloID() {
        return vueloID;
    }

    public Aerolinea getAerolinea() {
        return aerolinea;
    }

    public String getOrigenVuelo() {
        return origenVuelo;
    }

    public String getDestinoVuelo() {
        return destinoVuelo;
    }

    public LocalDateTime getFechaHoraSalida() {
        return fechaHoraSalida;
    }

    public LocalDateTime getFechaHoraLlegada() {
        return fechaHoraLlegada;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public ModeloAvion getModeloAvion() {
        return modeloAvion;
    }

    public boolean isEstadoVuelo() {
        return estadoVuelo;
    }

    @Override
    public Long getId() {
        return vueloID;
    }
}
