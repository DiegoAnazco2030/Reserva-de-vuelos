package com.reservaVuelos.servicio.DTOs.DTOsSalida;

import com.reservaVuelos.modelo.vuelo.Ciudad;
import com.reservaVuelos.modelo.vuelo.ModeloAvion;

public record SalidaVueloDTO(Long idVuelo,
                         Ciudad origen,
                        Ciudad destino,
                        String salidaVuelo,
                        String llegadaVuelo,
                        String estadoVuelo,
                        ModeloAvion modeloAvion) {
}
