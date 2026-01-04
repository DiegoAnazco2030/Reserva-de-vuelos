package com.reservaVuelos.servicio.DTOs.DTOsSalida;

import com.reservaVuelos.modelo.vuelo.Ciudad;
import com.reservaVuelos.modelo.vuelo.ModeloAvion;

public record SalidaReservaDTO(Long idReserva,
   Long idVuelo,
   ModeloAvion avion,
   String nombrePersona,
   String apellidoPersona,
   String correoPersona,
   Ciudad origen,
   Ciudad destino,
   Long idAsiento) {
}
