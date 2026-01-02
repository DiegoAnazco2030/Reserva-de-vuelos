package com.reservaVuelos.repositorio;

import com.reservaVuelos.modelo.Identificador;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public interface IRepositorio<T extends Identificador> {

    // -------------- CRUD --------------

    // Crear
    T guardar(T entity);

    // Leer
    T buscarPorID(Long ID);

    List<T> buscarTodos();

    default List<T> buscar(Predicate<? super T> filter) {
        List<T> salida = new ArrayList<>();

        for (T t : buscarTodos()) {
            if (filter.test(t)) salida.add(t);
        }
        return salida;
    }

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
