package com.reservaVuelos.servicio;

import java.util.List;

public interface IServicio<I, O> {

    // ----------------------- CRUD ----------------------

    // Crear
    void crear(I objeto);

    // Leer todo los datos
    List<O> obtenerTodos();
    // Leer por busqueda especifica
    List<O> obtenerListaReducida(String palabraBuscar);

    // Eliminar por id
    void eliminar(Long id) throws Exception;

    // Actualizar por id
    void modificar(Long id, I objeto) throws Exception;

}
