package com.reservaVuelos.servicio.servicioImp;

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
        String validadorGenerico = "^[a-zA-Z0-9]{5,10}$";

        return documento.matches(validadorGenerico);
    }
}
