package com.reservaVuelos.repositorio;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.reservaVuelos.modelo.vuelo.Asiento;
import com.reservaVuelos.modelo.vuelo.TipoDeAsiento;

/**
 * Repositorio para gestión de Asientos con RandomAccessFile
 * 
 * Tamaños de atributos (usando writeChars: 2 bytes por carácter):
 * - idAsiento: Long = 8 bytes
 * - estado: boolean = 1 byte (true = activo / false = inactivo)
 * - tipoAsiento: 9 caracteres × 2 = 18 bytes
 * - estadoAsiento: boolean = 1 byte
 */
public class RepositorioAsientos extends ArchivoDAO<Asiento> {

    private final int TIPO_ASIENTO_SIZE = 9;
    private static final Long TOTAL_RECORD_SIZE = 28L; // bytes por registro de Asiento

    public RepositorioAsientos() {
        super("asientos", TOTAL_RECORD_SIZE);
    }

    @Override
    protected byte[] convertirABytes(Asiento asiento) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        try {
            // Guardar los datos en el orden de la documentacion
            dos.writeLong(asiento.getId());
            dos.writeBoolean(true);
            dos.writeChars(ajustarString(asiento.getTipoAsiento().name(), TIPO_ASIENTO_SIZE));
            dos.writeBoolean(asiento.isEstadoAsiento());

            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected Asiento convertirDeBytes(byte[] bytes) {
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        DataInputStream dis = new DataInputStream(bais);

        try {
            // Leer en el orden de la documentacion
            Long ID = dis.readLong();
            dis.readBoolean();

            String tipoAsientoStr = leerStringFijo(dis, TIPO_ASIENTO_SIZE);
            TipoDeAsiento tipoAsiento = TipoDeAsiento.valueOf(tipoAsientoStr);

            boolean estadoAsiento = dis.readBoolean();

            Asiento asiento = new Asiento(ID, tipoAsiento);
            asiento.setEstadoAsiento(estadoAsiento);

            return asiento;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
