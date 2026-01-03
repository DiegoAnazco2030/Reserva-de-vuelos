package com.reservaVuelos.repositorio;

import com.reservaVuelos.Excepciones.Excepcion.OperacionFallidaException;
import com.reservaVuelos.modelo.Identificador;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * para guardar cualquier cosa el ID es el primer dato que se guarda
 * Existe un archivo de datos (.raf) y un archivo de indices (.idx) para el indice denso
 * El indice denso se guarda en un mapa en RAM (ID vs Posición)
 * Los datos eliminados se eliminan logicamente se marcan con false en el boolean de estado
 * Template Method para los DAO que usan archivos RandomAccessFile
 * 
 * Se tiene pensado hacer una funcion de mantenimiento para elimine fisicamente
 *  los datos que se eliminaron logicamente (Por implementar)
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
    @SuppressWarnings("unchecked")
    private void cargarIndiceDensoEnMemoria() {
        if (!archivoIndices.exists()) {
            if (archivoDatos.exists() && archivoDatos.length() > 0) {
                reconstruirIndiceDensoEnDisco();
            }
            return;
        }

        try (ObjectInputStream ios = new ObjectInputStream(new FileInputStream(archivoIndices))) {
            Long longitudArchivoRegistros = ios.readLong();
            Long longitudActualRegistros = archivoDatos.length();
            if (!longitudArchivoRegistros.equals(longitudActualRegistros)) {
                System.out.println("El tamaño del registro ha cambiado. Reconstruyendo el índice denso...");
                reconstruirIndiceDensoEnDisco();
                return;
            }

            indicesMapa = (Map<Long, Long>) ios.readObject();
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
                // El estado es siempre el segundo dato guardado
                if (raf.readBoolean()) {
                    indicesMapa.put(idRecuperado, pos);
                }
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
            oos.writeLong(archivoDatos.length());
            oos.writeObject(indicesMapa);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    // -------------- Métodos de la interfaz IRepositorio --------------

    @Override
    public T guardar(T entity) throws Exception {
        try (RandomAccessFile raf = new RandomAccessFile(archivoDatos, "rw")) {
            byte[] datosBytes = convertirABytes(entity);

            if (datosBytes == null || datosBytes.length != tamanoRegistro) {
                throw new IOException("Error al convertir la entidad a bytes o tamaño incorrecto");
            }

            if (existe(entity.getId())) {
                throw new IOException("La entidad con ID " + entity.getId() + " ya existe.");
            }

            Long posicion = raf.length();
            raf.seek(posicion);

            raf.write(datosBytes);

            indicesMapa.put(entity.getId(), posicion);
            return entity;
        } catch(IOException e) {
            throw new OperacionFallidaException("No se pudo guardar el " + entity.getClass().getSimpleName(), e);
        }
    }

    @Override
    public T buscarPorID(Long ID) {
        Long posicion = indicesMapa.get(ID);
        if (posicion == null) return null;

        try (RandomAccessFile raf = new RandomAccessFile(archivoDatos, "r")) {
            raf.seek(posicion);
            byte[] datosBytes = new byte[tamanoRegistro.intValue()];
            raf.readFully(datosBytes);

            return convertirDeBytes(datosBytes);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<T> buscarTodos() {
        List<T> resultados = new ArrayList<>();

        if (indicesMapa.isEmpty()) return resultados;

        try (RandomAccessFile raf = new RandomAccessFile(archivoDatos, "r")) {
            byte[] datosBytes = new byte[tamanoRegistro.intValue()];

            for (Long posicion : indicesMapa.values()) {
                raf.seek(posicion);
                raf.readFully(datosBytes);
                T entidad = convertirDeBytes(datosBytes);

                if (entidad != null) {
                    resultados.add(entidad);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultados;
    }

    @Override
    public List<T> buscar(Predicate<? super T> filter) {
        List<T> resultados = new ArrayList<>();

        if (indicesMapa.isEmpty()) return resultados;

        try (RandomAccessFile raf = new RandomAccessFile(archivoDatos, "r")) {
            byte[] datosBytes = new byte[tamanoRegistro.intValue()];

            for (Long posicion : indicesMapa.values()) {
                raf.seek(posicion);
                raf.readFully(datosBytes);
                T entidad = convertirDeBytes(datosBytes);

                if (entidad != null && filter.test(entidad)) {
                    resultados.add(entidad);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultados;
    }

    @Override
    public T actualizar(T entity) throws Exception {
        if (entity == null || indicesMapa.get(entity.getId()) == null) return null;
        Long posicion = indicesMapa.get(entity.getId());
        
        try (RandomAccessFile raf = new RandomAccessFile(archivoDatos, "rw")) {
            byte[] datosBytes = convertirABytes(entity);

            if (datosBytes == null || datosBytes.length != tamanoRegistro) {
                throw new IOException("Error al convertir la entidad a bytes o tamaño incorrecto");
            }

            raf.seek(posicion);
            raf.write(datosBytes);
            return entity;
        } catch (Exception e) {
            throw new OperacionFallidaException("No se pudo actualizar el " + entity.getClass().getSimpleName(), e);
        }
    }

    @Override
    public T eliminar(Long ID) throws Exception {
        if (ID == null || indicesMapa.get(ID) == null) return null;

        T entidadEliminada = buscarPorID(ID);

        try (RandomAccessFile raf = new RandomAccessFile(archivoDatos, "rw")) {
            Long posicion = indicesMapa.get(ID);
            raf.seek(posicion + 8); // Se salta a la posición del boolean de estado
            raf.writeBoolean(false);

            indicesMapa.remove(ID);
        } catch (IOException e) {
            throw new OperacionFallidaException("No se pudo eliminar el " + entidadEliminada.getClass().getSimpleName(), e);
        }

        return entidadEliminada;
    }

    @Override
    public boolean existe(Long ID) {
        return indicesMapa.containsKey(ID);
    }

    @Override
    public Long contarDatos() {
        return (long) indicesMapa.size();
    }

    @Override
    public Long ultimoID() {
        if (indicesMapa.isEmpty()) return 0L;

        // Los IDs son unicos y crecientes, por lo que el maximo es el ultimo
        return Collections.max(indicesMapa.keySet());
    }

    // Poner los metodos abstractos que se necesiten para los DAO concretos. (si es que existen)

    protected abstract byte[] convertirABytes(T entidad);

    protected abstract T convertirDeBytes(byte[] bytes);

    protected String ajustarString(String texto, int longitud) {
        if (texto == null) texto = "";
        StringBuilder sb = new StringBuilder(texto);
        
        // Rellenar
        while (sb.length() < longitud) {
            sb.append(" ");
        }
        // Recortar
        if (sb.length() > longitud) {
            sb.setLength(longitud);
        }
        return sb.toString();
    }

    protected String leerStringFijo(DataInputStream dis, int longitud) throws IOException {
        StringBuilder sb = new StringBuilder(longitud);
        for (int i = 0; i < longitud; i++) {
            sb.append(dis.readChar());
        }
        return sb.toString().trim();
    }
}
