package com.reservaVuelos.repositorio;

import com.reservaVuelos.modelo.Identificador;

import java.io.RandomAccessFile;
import java.util.List;
import java.util.Map;

public abstract class ArchivoDAO<T extends Identificador> implements IRepositorio<T> {
    protected RandomAccessFile file; // El archivo físico
    protected Map<Integer, Long> indexMap; // El Índice Denso en RAM (ID vs Posición)

    public ArchivoDAO(RandomAccessFile file) {
        this.file = file;
    }

    // -------------- Métodos de la interfaz IRepositorio --------------

    @Override
    public T guardarDato(T entity) {
        // poner codigo
        return null;
    }

    @Override
    public T encontrarPorID(Long ID) {
        // poner codigo
        return null;
    }

    @Override
    public List<T> obtenerTodosDatos() {
        // poner codigo
        return null;
    }

    @Override
    public T actualizarDato(T entity) {
        // poner codigo
        return null;
    }

    @Override
    public T eliminarDato(T entity) {
        // poner codigo
        return null;
    }

    @Override
    public boolean existePorID(Long ID) {
        // poner codigo
        return false;
    }

    @Override
    public Long contarDatos() {
        // poner codigo
        return null;
    }

    // Poner los metodos abstractos que se necesiten para los DAO concretos.
}
