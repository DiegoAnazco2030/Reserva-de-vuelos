package com.reservaVuelos.servicio;

import com.reservaVuelos.servicio.DTOs.DTOsCrear.CrearUsuarioDTO;

public interface IServicio {

    void crearUsuario();
    void obtenerTodosLosUsuarios();
    void eliminarUsuario();
    void modificarUsusario();

    void crearReserva();
    void obtenerTodaLasReserva();
    void eliminarReserva();
    void modificarReserva();

    void crearEmpleado();
    void obtenerTodosLosEmpleados();
    void eliminarEmpleado();
    void modificarEmpleado();

    void crearVuelo();
    void obtenerTodosLosVuelos();
    void eliminarVuelo();
    void modificarVuelo();

}
