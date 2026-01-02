package com.reservaVuelos.repositorio;

/**
 * Repositorio para gestión de Empleados con RandomAccessFile
 * 
 * Tamaños de atributos (usando writeChars: 2 bytes por carácter):
 * - nombre: 10 caracteres × 2 = 20 bytes
 * - apellido: 10 caracteres × 2 = 20 bytes
 * - telefono: 10 caracteres × 2 = 20 bytes
 * - edad: int = 4 bytes
 * - empleadoEmail: 40 caracteres × 2 = 80 bytes
 * - empleadoContrasenia: 28 caracteres × 2 = 56 bytes
 * - empleadoID: Long = 8 bytes
 */
public class RepositorioEmpleados {
    
    private static final int TOTAL_RECORD_SIZE = 208; // bytes por registro de Empleado

}
