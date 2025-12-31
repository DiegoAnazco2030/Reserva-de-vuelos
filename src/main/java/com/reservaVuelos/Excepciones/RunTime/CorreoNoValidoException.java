package com.reservaVuelos.Excepciones.RunTime;

public class CorreoNoValidoException extends RuntimeException {
    public CorreoNoValidoException(String mensaje) {
        super(mensaje);
    }
}
