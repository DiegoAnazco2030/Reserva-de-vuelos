package com.reservaVuelos.servicio.servicioImp;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidacionesTest {

    private final Validaciones validaciones = new Validaciones();
    @Test
    void correoEsValido() {
        assertTrue(validaciones.correoEsValido("j3000@gmail.conn"));
    }

    @Test
    void correoNoEsValido() {
        assertFalse(validaciones.correoEsValido("j3000gmaildfsdfsdfsconn"));
    }

    @Test
    void documentoEsValidaGenerico() {
     assertTrue(validaciones.documentoEsValidaGenerico("133vssq12"));
     assertTrue(validaciones.documentoEsValidaGenerico("133GFK12"));
    }

    @Test
    void documentoNoEsValidaGenerico() {
        assertFalse(validaciones.documentoEsValidaGenerico("133vssq12g"));
        assertFalse(validaciones.documentoEsValidaGenerico("133G"));
    }

    @Test
    void validarRangoLongitud() {
        assertTrue(validaciones.validarRangoLongitud("HolaMundo",5,15));
        assertFalse(validaciones.validarRangoLongitud("Hol",5,15));
    }

    @Test
    void validarTelefono() {
        assertTrue(validaciones.validarTelefono("0998765432"));
        assertFalse(validaciones.validarTelefono("09987654A2"));
    }

    @Test
    void validarContrasenia() {
        assertTrue(validaciones.validarContrasenia("Contrasenia123"));
        assertFalse(validaciones.validarContrasenia("short"));
    }

    @Test
    void validarFechas() {

        assertThrows(RuntimeException.class, () -> {
            validaciones.validarFechas(
                    java.time.LocalDateTime.of(2024,6,1,12,0),
                    java.time.LocalDateTime.of(2024,6,1,10,0)
            );
        });

        assertDoesNotThrow(()->{
            validaciones.validarFechas(
                    java.time.LocalDateTime.of(2024,6,1,10,0),
                    java.time.LocalDateTime.of(2024,6,1,12,0)
            );
        });
    }

    @Test
    void validarEdad() {
        assertTrue(validaciones.validarEdad(25));
        assertFalse(validaciones.validarEdad(0));
        assertFalse(validaciones.validarEdad(130));
    }
}