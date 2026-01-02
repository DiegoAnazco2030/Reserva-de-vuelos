package com.reservaVuelos.Excepciones.Excepcion;

public class AvionNoEncontradoDTO extends RuntimeException {
    public AvionNoEncontradoDTO(String message) {
        super(message);
    }
}
