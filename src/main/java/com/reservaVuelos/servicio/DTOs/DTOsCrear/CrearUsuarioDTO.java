package com.reservaVuelos.servicio.DTOs.DTOsCrear;

public record CrearUsuarioDTO( String nombre,
       String apellido,
       String telefono,
       int edad,  String usuarioEmail,
       String usuarioContrasenia,
       Long usuarioPassaporteID) {
}
