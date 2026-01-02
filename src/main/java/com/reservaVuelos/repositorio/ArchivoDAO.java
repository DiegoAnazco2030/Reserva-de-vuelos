package com.reservaVuelos.repositorio;

import com.reservaVuelos.modelo.Identificador;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * para guardar cualquier cosa el ID es el primer dato que se guarda
 */

public abstract class ArchivoDAO<T extends Identificador> implements IRepositorio<T> {
    private File archivoIndices;            // Archivo de los indices densos
    private File archivoDatos;              // Archivo de los datos
    private int tamanoRegistro;             // Tamano de Bytes del registro
    private Map<Long, Long> indicesMapa;    // El Índice Denso en RAM (ID vs Posición)

    public ArchivoDAO(String nombreBase, int tamanoRegistro) {
        this.archivoIndices = new File(nombreBase + ".idx");
        this.archivoDatos = new File(nombreBase + ".raf");
        this.tamanoRegistro = tamanoRegistro;
        this.indicesMapa = new HashMap<>();

        // Cargar el indice denso en memoria al inicializar el DAO
        cargarIndiceDensoEnMemoria();

        // Uso de SHUTDOWN HOOK al cerrar la aplicacion para guardar el indice denso en disco
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            guardarIndiceDensoEnDisco();
            System.out.println("Indices densos guardados antes de cerrar todo");
        }));
    }

    // -------------- Indice Denso --------------

    // Cargar el indice denso a memoria
    private void cargarIndiceDensoEnMemoria() {
        // poner codigo
    }

    // Reconstruccion de indice denso en disco en caso de incuerrencias
    private void reconstruirIndiceDensoEnDisco() {
        // poner codigo
    }

    // Guardar indice denso en disco
    private void guardarIndiceDensoEnDisco() {
        // poner codigo
    }

    // -------------- Métodos de la interfaz IRepositorio --------------

    @Override
    public T guardar(T entity) {
        // poner codigo
        return null;
    }

    @Override
    public T buscarPorID(Long ID) {
        // poner codigo
        return null;
    }

    @Override
    public List<T> buscarTodos() {
        // poner codigo
        return null;
    }

    @Override
    public T actualizar(T entity) {
        // poner codigo
        return null;
    }

    @Override
    public T eliminar(Long ID) {
        // poner codigo
        return null;
    }

    @Override
    public boolean existe(Long ID) {
        // poner codigo
        return false;
    }

    @Override
    public Long contarDatos() {
        // poner codigo
        return null;
    }

    @Override
    public Long ultimoID() {
        // poner codigo
        return null;
    }

    // Poner los metodos abstractos que se necesiten para los DAO concretos. (si es que existen)

    //Una función que devuelva el último ID de cada objeto
}
