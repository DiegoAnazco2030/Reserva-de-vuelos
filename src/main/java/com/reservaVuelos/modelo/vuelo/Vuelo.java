package com.reservaVuelos.modelo.vuelo;

import com.reservaVuelos.modelo.Identifcador;

import java.time.LocalDateTime;

public class Vuelo implements Identifcador {
    private final Long vueloID;
    private final Aerolinea aerolinea;
    private final String origenVuelo;
    private final String destinoVuelo;
    private final LocalDateTime fechaHoraSalida;
    private final LocalDateTime fechaHoraLlegada;
    private final int capacidad;
    private final ModeloAvion modeloAvion;
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

    @Override
    public Long getId() {
        return vueloID;
    }
}
