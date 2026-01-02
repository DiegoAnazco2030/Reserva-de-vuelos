package com.reservaVuelos.repositorio;

/**
 * Repositorio para gestión de Aviones con RandomAccessFile
 * 
 * Tamaños de atributos (usando writeChars: 2 bytes por carácter):
 * - estado: boolean = 1 byte
 * - idAvion: Long = 8 bytes
 * - modeloAvion: 11 caracteres × 2 = 22 bytes
 * - capacidad: int = 4 bytes
 */
public class RepositorioAviones {
    
    private static final int TOTAL_RECORD_SIZE = 35; // bytes por registro de Avion

}
