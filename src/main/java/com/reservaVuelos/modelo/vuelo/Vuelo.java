package com.reservaVuelos.modelo.vuelo;

import com.reservaVuelos.modelo.Identifcador;

import java.time.LocalDateTime;

public class Vuelo implements Identifcador {
    private final Long vueloID;
    private Aerolinea aerolinea;
    private Paises origenVuelo;
    private Paises destinoVuelo;
    private LocalDateTime fechaHoraSalida;
    private LocalDateTime fechaHoraLlegada;
    private boolean estadoVuelo; //"1 = Programado", "0 = Aterrizado"
    private Avion avionVuelo;


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
