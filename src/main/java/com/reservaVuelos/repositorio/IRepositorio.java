package com.reservaVuelos.repositorio;

import com.reservaVuelos.modelo.Identificador;

import java.util.List;
import java.util.function.Predicate;

public interface IRepositorio<T extends Identificador> {

    // -------------- CRUD --------------

    // Crear
    T guardar(T entity);

    // Leer
    T buscarPorID(Long ID);

    List<T> buscarTodos();

    List<T> buscar(Predicate<? super T> filter);

    // Actualizar
    T actualizar(T entity);

    // Eliminar
    T eliminar(Long ID);

    // -------------- Auxiliares --------------

    // Verificar existencia
    boolean existe(Long ID);

    // Contar la cantidad de datos
    Long contarDatos();

    // Obtener el ultimo ID

    Long ultimoID();
}
