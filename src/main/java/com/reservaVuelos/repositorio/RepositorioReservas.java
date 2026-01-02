package com.reservaVuelos.repositorio;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.reservaVuelos.modelo.Reserva;

/**
 * Repositorio para gestión de Reservas con RandomAccessFile
 * 
 * Tamaños de atributos:
 * - idReserva: Long = 8 bytes
 * - estado: boolean = 1 byte (true = activo / false = inactivo)
 * - idVuelo: Long = 8 bytes
 * - idAsiento: Long = 8 bytes
 * - idUsuario: Long = 8 bytes
 */
public class RepositorioReservas extends ArchivoDAO<Reserva> {
    
    private static final Long TOTAL_RECORD_SIZE = 33L; // bytes por registro de Reserva

    public RepositorioReservas() {
        super("reservas", TOTAL_RECORD_SIZE);
    }

    @Override
    protected byte[] convertirABytes(Reserva reserva) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        try {
            // Guardar los datos en el orden de la documentacion
            dos.writeLong(reserva.getId());
            dos.writeBoolean(true);
            dos.writeLong(reserva.getIdVuelo());
            dos.writeLong(reserva.getIdAsiento());
            dos.writeLong(reserva.getIdUsuario());

            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected Reserva convertirDeBytes(byte[] bytes) {
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        DataInputStream dis = new DataInputStream(bais);

        try {
            // Leer en el orden de la documentacion
            Long idReserva = dis.readLong();
            dis.readBoolean();
            Long idVuelo = dis.readLong();
            Long idAsiento = dis.readLong();
            Long idUsuario = dis.readLong();

            return new Reserva(idReserva, idVuelo, idAsiento, idUsuario);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
