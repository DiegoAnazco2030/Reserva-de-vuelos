package com.reservaVuelos.modelo.vuelo;

import com.reservaVuelos.modelo.Identifcador;

import java.time.LocalDateTime;

public class Vuelo implements Identifcador {
    private final Long vueloID; // 7 caracteres (10^7 = 10,000,000 posibles IDs)
    private Aerolinea aerolinea; // 19 caracteres
    private Paises origenVuelo; // 9 caracteres
    private Paises destinoVuelo; // 9 caracteres
    private LocalDateTime fechaHoraSalida; // 29 caracteres
    private LocalDateTime fechaHoraLlegada; // 29 caracteres
    private boolean estadoVuelo; //"0 = Programado", "1 = Aterrizado" / 1 caracter
    private Avion avionVuelo; // 7 caracteres (ID del avión)

    public Vuelo(Long vueloID, Aerolinea aerolinea, Paises origenVuelo, Paises destinoVuelo, LocalDateTime fechaHoraSalida, LocalDateTime fechaHoraLlegada, boolean estadoVuelo, Avion avionVuelo) {
        this.vueloID = vueloID;
        this.aerolinea = aerolinea;
        this.origenVuelo = origenVuelo;
        this.destinoVuelo = destinoVuelo;
        this.fechaHoraSalida = fechaHoraSalida;
        this.fechaHoraLlegada = fechaHoraLlegada;
        this.estadoVuelo = estadoVuelo;
        this.avionVuelo = avionVuelo;
    }

    public Aerolinea getAerolinea() {
        return aerolinea;
    }

    public void setAerolinea(Aerolinea aerolinea) {
        this.aerolinea = aerolinea;
    }

    public Paises getOrigenVuelo() {
        return origenVuelo;
    }

    public void setOrigenVuelo(Paises origenVuelo) {
        this.origenVuelo = origenVuelo;
    }

    public Paises getDestinoVuelo() {
        return destinoVuelo;
    }

    public void setDestinoVuelo(Paises destinoVuelo) {
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
