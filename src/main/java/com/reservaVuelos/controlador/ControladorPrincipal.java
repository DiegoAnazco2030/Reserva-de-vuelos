package com.reservaVuelos.controlador;

import com.reservaVuelos.servicio.DTOs.DTOsCrear.*;
import com.reservaVuelos.servicio.DTOs.DTOsSalida.*;
import com.reservaVuelos.servicio.IServicio;

public class ControladorPrincipal {
    private final IServicio<CrearEmpleadoDTO, SalidaEmpleadoDTO> servicioEmpleado;
    private final IServicio<CrearAereolineaDTO, SalidaAerolineaDTO> servicioAerolinea;
    private final IServicio<CrearAvionDTO, SalidaAvionDTO> servicioAvion;
    private final IServicio<CrearReservaDTO, SalidaReservaDTO> servicioReserva;
    private final IServicio<CrearUsuarioDTO, SalidaUsuarioDTO> servicioUsuario;
    private final IServicio<CrearVueloDTO, SalidaVueloDTO> servicioVuelo;


    public ControladorPrincipal(IServicio<CrearEmpleadoDTO, SalidaEmpleadoDTO> servicioEmpleado, IServicio<CrearAereolineaDTO, SalidaAerolineaDTO> servicioAerolinea, IServicio<CrearAvionDTO, SalidaAvionDTO> servicioAvion, IServicio<CrearReservaDTO, SalidaReservaDTO> servicioReserva, IServicio<CrearUsuarioDTO, SalidaUsuarioDTO> servicioUsuario, IServicio<CrearVueloDTO, SalidaVueloDTO> servicioVuelo) {
        this.servicioEmpleado = servicioEmpleado;
        this.servicioAerolinea = servicioAerolinea;
        this.servicioAvion = servicioAvion;
        this.servicioReserva = servicioReserva;
        this.servicioUsuario = servicioUsuario;
        this.servicioVuelo = servicioVuelo;
    }


}
