package com.reservaVuelos.servicio.DTOs.DTOsCrear;

public record CrearReservaDTO( Long idReserva,
    Long idVuelo,
    Long idAsiento,
    Long idUsuario) {
}
