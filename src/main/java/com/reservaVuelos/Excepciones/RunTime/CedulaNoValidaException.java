package com.reservaVuelos.Excepciones.RunTime;

public class CedulaNoValidaException extends RuntimeException {
    public CedulaNoValidaException(String mensaje) {
        super(mensaje);
    }
}
