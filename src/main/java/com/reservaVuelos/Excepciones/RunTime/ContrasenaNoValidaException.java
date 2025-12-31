package com.reservaVuelos.Excepciones.RunTime;

public class ContrasenaNoValidaException extends RuntimeException {
    public ContrasenaNoValidaException(String mensaje) {
        super(mensaje);
    }
}
