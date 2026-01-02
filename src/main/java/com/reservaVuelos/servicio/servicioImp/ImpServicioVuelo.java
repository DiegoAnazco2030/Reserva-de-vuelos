package com.reservaVuelos.servicio.servicioImp;

import com.reservaVuelos.servicio.DTOs.DTOsCrear.CrearVueloDTO;
import com.reservaVuelos.servicio.DTOs.DTOsSalida.SalidaVueloDTO;
import com.reservaVuelos.servicio.IServicio;

import java.util.List;

public class ImpServicioVuelo implements IServicio<CrearVueloDTO, SalidaVueloDTO> {
    @Override
    public void crear(CrearVueloDTO objeto) {

    }

    @Override
    public List<SalidaVueloDTO> obtenerTodos() {
        return List.of();
    }

    @Override
    public void eliminar(Long id) throws Exception {

    }

    @Override
    public void modificar(Long id, CrearVueloDTO objeto) throws Exception {

    }

    @Override
    public List<SalidaVueloDTO> obtenerListaReducida(String palabraBuscar) {
        return List.of();
    }
}
