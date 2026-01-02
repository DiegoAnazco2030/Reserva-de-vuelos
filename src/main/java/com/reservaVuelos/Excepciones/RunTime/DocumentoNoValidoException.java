package com.reservaVuelos.Excepciones.RunTime;

public class DocumentoNoValidoException extends RuntimeException {
    public DocumentoNoValidoException(String mensaje) {
        super(mensaje);
    }
}
