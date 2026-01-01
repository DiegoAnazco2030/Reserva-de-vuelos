package com.reservaVuelos.servicio.DTOs.DTOsCrear;

import com.reservaVuelos.modelo.vuelo.Aerolinea;
import com.reservaVuelos.modelo.vuelo.Ciudad;

import java.time.LocalDateTime;

public record CrearVueloDTO(Aerolinea aerolinea,
    Ciudad origenVuelo,
     Ciudad destinoVuelo,
     LocalDateTime fechaHoraSalida,
     LocalDateTime fechaHoraLlegada, Long idAvion) {

}
