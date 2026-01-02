package com.reservaVuelos.repositorio;

/**
 * Repositorio para gestión de Reservas con RandomAccessFile
 * 
 * Tamaños de atributos:
 * - estado: boolean = 1 byte
 * - idReserva: Long = 8 bytes
 * - idVuelo: Long = 8 bytes
 * - idAsiento: Long = 8 bytes
 * - idUsuario: Long = 8 bytes
 */
public class RepositorioReservas {
    
    private static final int TOTAL_RECORD_SIZE = 33; // bytes por registro de Reserva

}
