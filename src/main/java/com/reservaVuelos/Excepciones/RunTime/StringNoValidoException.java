package com.reservaVuelos.Excepciones.RunTime;

public class StringNoValidoException extends RuntimeException {
    public StringNoValidoException(String message) {
        super(message);
    }
}
