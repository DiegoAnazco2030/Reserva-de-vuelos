package com.reservaVuelos.repositorio;

import com.reservaVuelos.modelo.Identifcador;

import java.io.RandomAccessFile;
import java.util.List;
import java.util.Map;

public abstract class ArchivoDAO<T extends Identifcador> implements IRepositorio<T> {
    protected RandomAccessFile file; // El archivo físico
    protected Map<Integer, Long> indexMap; // El Índice Denso en RAM (ID vs Posición)

    public ArchivoDAO(RandomAccessFile file) {
        this.file = file;
    }

    // -------------- Métodos de la interfaz IRepositorio --------------

    // Poner los metodos abstractos que se necesiten para los DAO concretos.
}
