package com.reservaVuelos.repositorio;

/**
 * Repositorio para gestión de Aerolineas con RandomAccessFile
 * 
 * Tamaños de atributos (usando writeChars: 2 bytes por carácter):
 * - aerolineaID: Long = 8 bytes
 * - nombre: 30 caracteres × 2 = 60 bytes
 * - telefono: 10 caracteres × 2 = 20 bytes
 * - email: 40 caracteres × 2 = 80 bytes
 */
public class RepositorioAerolineas {
    
    private static final int TOTAL_RECORD_SIZE = 168; // bytes por registro de Aerolinea

}
