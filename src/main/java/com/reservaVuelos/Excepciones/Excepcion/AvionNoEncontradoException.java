package com.reservaVuelos.Excepciones.Excepcion;

public class AvionNoEncontradoException extends RuntimeException {
    public AvionNoEncontradoException(String message) {
        super(message);
    }
}
