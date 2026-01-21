package com.reservaVuelos.repositorio;

import java.io.*;
import java.util.zip.*;

public class prueba {
    // Rutas según tu estructura
    private static final String CARPETA_BASE = "./base_datos_archivos/";
    private static final String RUTA_DATOS = CARPETA_BASE + "datos/";
    private static final String RUTA_INDICES = CARPETA_BASE + "indices/";
    private static final String RUTA_ZIPS = CARPETA_BASE + "comprimidos/";

    private final File archivoRaf;
    private final File archivoIdx;
    private final File archivoZip;

    public prueba(String nombreBase) {
        // Inicializamos los archivos en el constructor
        this.archivoRaf = new File(RUTA_DATOS + nombreBase + ".raf");
        this.archivoIdx = new File(RUTA_INDICES + nombreBase + ".idx");
        this.archivoZip = new File(RUTA_ZIPS + nombreBase + ".zip");

        asegurarDirectorios();
    }

    /**
     * Comprime .raf e .idx y LUEGO los borra.
     */
    public void comprimirYLimpiar() {
        // Usamos try-with-resources para asegurar que los flujos se cierren antes de borrar
        try (FileOutputStream fos = new FileOutputStream(this.archivoZip);
             ZipOutputStream zos = new ZipOutputStream(fos)) {

            agregarEntrada(this.archivoRaf, zos);
            agregarEntrada(this.archivoIdx, zos);

            // Forzamos el cierre de los flujos del ZIP antes de borrar los originales
            zos.finish();

            System.out.println("ZIP creado con éxito. Procediendo a borrar archivos originales...");

            // Borrar originales solo si el proceso terminó bien
            if (archivoRaf.exists()) archivoRaf.delete();
            if (archivoIdx.exists()) archivoIdx.delete();

        } catch (IOException e) {
            System.err.println("Error en la compresión: " + e.getMessage());
        }
    }

    /**
     * Descomprime el ZIP, reparte los archivos y LUEGO borra el ZIP.
     */
    public void descomprimirYLimpiar() {
        if (!archivoZip.exists()) {
            System.err.println("No se encontró el archivo ZIP: " + archivoZip.getName());
            return;
        }

        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(this.archivoZip))) {
            ZipEntry entrada;
            while ((entrada = zis.getNextEntry()) != null) {
                // Determinar destino según extensión
                File destino = entrada.getName().endsWith(".raf") ? archivoRaf : archivoIdx;

                escribirFisicamente(zis, destino);
                zis.closeEntry();
            }

            // Cerramos el flujo de entrada explícitamente para poder borrar el archivo en Windows
            zis.close();

            System.out.println("Archivos restaurados. Borrando el archivo ZIP...");
            archivoZip.delete();

        } catch (IOException e) {
            System.err.println("Error en la descompresión: " + e.getMessage());
        }
    }

    // --- Métodos Privados Auxiliares ---

    private void agregarEntrada(File archivo, ZipOutputStream zos) throws IOException {
        if (!archivo.exists()) return;
        try (FileInputStream fis = new FileInputStream(archivo)) {
            zos.putNextEntry(new ZipEntry(archivo.getName()));
            byte[] buffer = new byte[4096];
            int leido;
            while ((leido = fis.read(buffer)) != -1) {
                zos.write(buffer, 0, leido);
            }
            zos.closeEntry();
        }
    }

    private void escribirFisicamente(ZipInputStream zis, File destino) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(destino)) {
            byte[] buffer = new byte[4096];
            int leido;
            while ((leido = zis.read(buffer)) != -1) {
                fos.write(buffer, 0, leido);
            }
        }
    }

    private void asegurarDirectorios() {
        archivoRaf.getParentFile().mkdirs();
        archivoIdx.getParentFile().mkdirs();
        archivoZip.getParentFile().mkdirs();
    }
}