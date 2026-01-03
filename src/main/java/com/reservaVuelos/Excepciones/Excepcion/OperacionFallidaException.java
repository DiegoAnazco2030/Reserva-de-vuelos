package com.reservaVuelos.Excepciones.Excepcion;

public class OperacionFallidaException extends Exception {
    public OperacionFallidaException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }

    public OperacionFallidaException(String mensaje) {
        super(mensaje);
    }

}
