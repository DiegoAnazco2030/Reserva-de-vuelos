package com.reservaVuelos.modelo;

public class Reserva implements Identificador {

    private final Long idReserva; // tamaño de Long
    private final Long idVuelo; // tamaño de Long
    private final Long idAsiento; // tamaño de Long
    private final Long idUsuario; // tamaño de Long

    public Reserva(Long idReserva, Long idVuelo, Long idAsiento, Long idUsuario) {
        this.idReserva = idReserva;
        this.idVuelo = idVuelo;
        this.idAsiento = idAsiento;
        this.idUsuario = idUsuario;
    }

    @Override
    public Long getId() {
        return idReserva;
    }

    public Long getIdVuelo() {
        return idVuelo;
    }

    public Long getIdAsiento() {
        return idAsiento;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }
}
