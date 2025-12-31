package com.reservaVuelos.repositorio;

import com.reservaVuelos.modelo.Identifcador;

import java.io.RandomAccessFile;
import java.util.List;
import java.util.Map;

public abstract class ArchivoDAO<T extends Identifcador> implements IRepositorio<T> {
    protected RandomAccessFile file; // El archivo físico
    protected Map<Integer, Long> indexMap; // El Índice Denso en RAM (ID vs Posición)

    @Override
    T guardarDato(T entity) {
        // poner codigo
    }

    @Override
    T leerDato(T entity) {
        // poner codigo
    }

    @Override
    T actualizarDato(T entity) {
        // poner codigo
    }

    @Override
    T eliminarDato(T entity) {
        // poner codigo
    }

    @Override
    List<T> ObtenerTodosDatos() {
        // poner codigo
    }

    @Override
    T EncontrarPorID(Long ID) {
        // poner codigo
    }

    // Poner los metodos abstractos que se necesiten para los DAO concretos.
}
