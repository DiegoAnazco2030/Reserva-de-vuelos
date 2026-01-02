package com.reservaVuelos.servicio;

import com.reservaVuelos.modelo.Identificador;
import com.reservaVuelos.servicio.DTOs.DTOsCrear.CrearUsuarioDTO;

import java.util.List;

public interface IServicio<I, O> {

    void crear(I objeto);
    List<O> obtenerTodos();
    void eliminar(Long id) throws Exception;
    void modificar(Long id, I objeto) throws Exception;
    List<O> obtenerListaReducida();

}
