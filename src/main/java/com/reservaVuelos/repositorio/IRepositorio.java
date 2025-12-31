package com.reservaVuelos.repositorio;

import com.reservaVuelos.modelo.Identifcador;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public interface IRepositorio<T extends Identifcador> {

    // -------------- CRUD --------------

    // Crear
    T guardarDato(T entity);

    // Leer
    T encontrarPorID(Long ID);

    List<T> obtenerTodosDatos();

    default List<T> obtenerTodosDatos(Predicate<? super T> filter) {
        List<T> salida = new ArrayList<>();

        for (T t : obtenerTodosDatos()) {
            if (filter.test(t)) salida.add(t);
        }
        return salida;
    }

    // Actualizar
    T actualizarDato(T entity);

    // Eliminar
    T eliminarDato(T entity);

    // -------------- Auxiliares --------------

    // Verificar existencia
    boolean existePorID(Long ID);

    // Contar la cantidad de datos
    Long contarDatos();
}
