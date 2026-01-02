package com.reservaVuelos.repositorio;

/**
 * Repositorio para gestión de Usuarios con RandomAccessFile
 * 
 * Tamaños de atributos (usando writeChars: 2 bytes por carácter):
 * - nombre: 10 caracteres × 2 = 20 bytes
 * - apellido: 10 caracteres × 2 = 20 bytes
 * - telefono: 10 caracteres × 2 = 20 bytes
 * - edad: int = 4 bytes
 * - usuarioEmail: 40 caracteres × 2 = 80 bytes
 * - usuarioContrasenia: 28 caracteres × 2 = 56 bytes
 * - usuarioPassaporteID: 9 caracteres × 2 = 18 bytes
 * - usuarioID: Long = 8 bytes
 */
public class RepositorioUsuarios {
    
    private static final int TOTAL_RECORD_SIZE = 226; // bytes por registro de Usuario

}
