package com.reservaVuelos.servicio;

import com.reservaVuelos.servicio.DTOs.UsuarioDTO;

public interface IServicio {

    void crearUsuario(UsuarioDTO usuarioNuevo);
    void crearReserva();
    void crearEmpleado();


}
