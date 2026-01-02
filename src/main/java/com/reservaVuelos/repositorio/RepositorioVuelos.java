package com.reservaVuelos.repositorio;

/**
 * Repositorio para gestión de Vuelos con RandomAccessFile
 * 
 * Tamaños de atributos (usando writeChars: 2 bytes por carácter):
 * - estado: boolean = 1 byte
 * - vueloID: Long = 8 bytes
 * - aerolineaID: Long = 8 bytes
 * - origenVuelo: 12 caracteres × 2 = 24 bytes
 * - destinoVuelo: 12 caracteres × 2 = 24 bytes
 * - fechaHoraSalida: 29 caracteres × 2 = 58 bytes
 * - fechaHoraLlegada: 29 caracteres × 2 = 58 bytes
 * - estadoVuelo: boolean = 1 byte
 * - avionVueloID: Long = 8 bytes
 */
public class RepositorioVuelos {
    
    private static final int TOTAL_RECORD_SIZE = 190; // bytes por registro de Vuelo

}
