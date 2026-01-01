package com.reservaVuelos.modelo.vuelo;

import com.reservaVuelos.modelo.Identificador;

import java.time.LocalDateTime;

public class Vuelo implements Identificador {
    private final Long vueloID; // 7 caracteres (10^7 = 10,000,000 posibles IDs)
    private Aerolinea aerolinea; // 19 caracteres
    private Ciudad origenVuelo; // 12 caracteres
    private Ciudad destinoVuelo; // 12 caracteres
    private LocalDateTime fechaHoraSalida; // 29 caracteres
    private LocalDateTime fechaHoraLlegada; // 29 caracteres
    private boolean estadoVuelo; //"0 = Programado", "1 = Aterrizado" / 1 caracter
    private Avion avionVuelo; // 7 caracteres (ID del avión)

    public Vuelo(Long vueloID, Aerolinea aerolinea, Ciudad origenVuelo, Ciudad destinoVuelo, LocalDateTime fechaHoraSalida, LocalDateTime fechaHoraLlegada, boolean estadoVuelo, Avion avionVuelo) {
        this.vueloID = vueloID;
        this.aerolinea = aerolinea;
        this.origenVuelo = origenVuelo;
        this.destinoVuelo = destinoVuelo;
        this.fechaHoraSalida = fechaHoraSalida;
        this.fechaHoraLlegada = fechaHoraLlegada;
        this.estadoVuelo = false;
        this.avionVuelo = avionVuelo;
    }

    public Aerolinea getAerolinea() {
        return aerolinea;
    }

    public void setAerolinea(Aerolinea aerolinea) {
        this.aerolinea = aerolinea;
    }

    public Ciudad getOrigenVuelo() {
        return origenVuelo;
    }

    public void setOrigenVuelo(Ciudad origenVuelo) {
        this.origenVuelo = origenVuelo;
    }

    public Ciudad getDestinoVuelo() {
        return destinoVuelo;
    }

    public void setDestinoVuelo(Ciudad destinoVuelo) {
        this.destinoVuelo = destinoVuelo;
    }

    public LocalDateTime getFechaHoraSalida() {
        return fechaHoraSalida;
    }

    public void setFechaHoraSalida(LocalDateTime fechaHoraSalida) {
        this.fechaHoraSalida = fechaHoraSalida;
    }

    public LocalDateTime getFechaHoraLlegada() {
        return fechaHoraLlegada;
    }

    public void setFechaHoraLlegada(LocalDateTime fechaHoraLlegada) {
        this.fechaHoraLlegada = fechaHoraLlegada;
    }

    public boolean isEstadoVuelo() {
        return estadoVuelo;
    }

    public void setEstadoVuelo(boolean estadoVuelo) {
        this.estadoVuelo = estadoVuelo;
    }

    public Avion getAvionVuelo() {
        return avionVuelo;
    }

    public void setAvionVuelo(Avion avionVuelo) {
        this.avionVuelo = avionVuelo;
    }

    @Override
    public Long getId() {
        return vueloID;
    }
}
