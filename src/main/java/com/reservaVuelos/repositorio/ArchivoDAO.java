package com.reservaVuelos.repositorio;

import com.reservaVuelos.modelo.Identificador;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * para guardar cualquier cosa el ID es el primer dato que se guarda
 * Existe un archivo de datos (.raf) y un archivo de indices (.idx) para el indice denso
 * El indice denso se guarda en un mapa en RAM (ID vs Posición)
 */

public abstract class ArchivoDAO<T extends Identificador> implements IRepositorio<T> {
    private File archivoIndices;            // Archivo de los indices densos
    private File archivoDatos;              // Archivo de los datos
    private Long tamanoRegistro;            // Tamano de Bytes del registro
    private Map<Long, Long> indicesMapa;    // El Índice Denso en RAM (ID vs Posición)

    public ArchivoDAO(String nombreBase, Long tamanoRegistro) {
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
        if (!archivoIndices.exists()) {
            if (archivoDatos.exists() && archivoDatos.length() > 0) {
                reconstruirIndiceDensoEnDisco();
            }
            return;
        }

        try (ObjectInputStream ios = new ObjectInputStream(new FileInputStream(archivoIndices))) {
            indicesMapa = (Map<Long, Long>) ios.readObject();

            Long registroEstimado = archivoDatos.length() / tamanoRegistro;
            if (indicesMapa.size() != registroEstimado) {
                reconstruirIndiceDensoEnDisco();
            }
        } catch(Exception e) {
            e.printStackTrace();
            reconstruirIndiceDensoEnDisco();
        }
    }

    // Reconstruccion de indice denso en disco en caso de incuerrencias
    private void reconstruirIndiceDensoEnDisco() {
        System.out.println("Reconstruyendo indice denso en disco, esto puede tardar un momento...");

        indicesMapa.clear();

        try (RandomAccessFile raf = new RandomAccessFile(archivoDatos, "r")) {
            Long totalBytes = raf.length();
            Long idRecuperado;

            for (Long pos = 0L; pos < totalBytes; pos += tamanoRegistro) {
                raf.seek(pos);
                // El ID es siempre el primer dato guardado
                idRecuperado = raf.readLong();
                indicesMapa.put(idRecuperado, pos);
            }

            guardarIndiceDensoEnDisco();
            System.out.println("Reconstrucción del índice denso completada.");
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    // Guardar indice denso en disco
    private void guardarIndiceDensoEnDisco() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivoIndices))) {
            oos.writeObject(indicesMapa);
        } catch(IOException e) {
            e.printStackTrace();
        }
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
