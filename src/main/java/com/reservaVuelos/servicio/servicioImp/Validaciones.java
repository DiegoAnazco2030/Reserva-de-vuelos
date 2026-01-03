package com.reservaVuelos.servicio.servicioImp;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class Validaciones {

    public boolean correoEsValido(String correo){
        //Sera verdadero si el correo es valido y falso en caso contrario
        if (correo.contains("@") && correo.contains(".")){
            return true;
        }
        return false;
    }

    public boolean documentoEsValidaGenerico(String documento) {
        if (documento == null) return false;
        String validadorGenerico = "^[a-zA-Z0-9]{5,9}$";

        return documento.matches(validadorGenerico);
    }

    public boolean validarRangoLongitud(String texto, int minimo, int maximo) {
        if (texto == null) return false;
        int largo = texto.length();
        return largo >= minimo && largo <= maximo;

    }

    public boolean validarTelefono(String telefono) {
        if (telefono == null || telefono.length() != 10) {
            return false;
        }
        return telefono.matches("^[0-9]+$");
    }

    public boolean validarContrasenia(String contrasenia) {
        if (contrasenia == null) {
            return false;
        }

        int largo = contrasenia.trim().length();

        return largo >= 8 && largo <= 20;
    }

    public void validarFechas(LocalDateTime salida, LocalDateTime llegada) {
        if (llegada.isBefore(salida)) {
            throw new RuntimeException("La fecha de llegada no puede ser anterior a la de salida");
        }
    }

    public boolean validarEdad(int edad) {
        return edad >= 1 && edad <= 120;
    }
}
