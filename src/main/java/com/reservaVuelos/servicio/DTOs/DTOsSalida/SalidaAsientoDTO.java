package com.reservaVuelos.servicio.DTOs.DTOsSalida;

import com.reservaVuelos.modelo.vuelo.TipoDeAsiento;

public record SalidaAsientoDTO(Long idAsiento,
                               boolean estado,
                               TipoDeAsiento tipoAsiento) {
}
