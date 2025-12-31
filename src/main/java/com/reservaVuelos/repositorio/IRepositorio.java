package com.reservaVuelos.repositorio;

import com.reservaVuelos.modelo.Identifcador;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public interface IRepositorio<T extends Identifcador> {

    T guardarDato(T entity);

    T leerDato(T entity);

    T actualizarDato(T entity);

    T eliminarDato(T entity);

    List<T> ObtenerTodosDatos();

    T EncontrarPorID(Long ID);

    default List<T> ObtenerTodosDatos(Predicate<? super T> filter) {
        List<T> salida = new ArrayList<>();

        for (T t : ObtenerTodosDatos()) {
            if (filter.test(t)) salida.add(t);
        }
        return salida;
    }
}
