package com.reservaVuelos.servicio;

import com.reservaVuelos.modelo.Identificador;
import com.reservaVuelos.servicio.DTOs.DTOsCrear.CrearUsuarioDTO;

import java.util.List;

public interface IServicio<T> {

    void crear(T objeto);
    List<T> obtenerTodos();
    void eliminar(Long id);
    void modificar(Long id);


}
