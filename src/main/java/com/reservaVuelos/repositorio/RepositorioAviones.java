package com.reservaVuelos.repositorio;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.reservaVuelos.modelo.vuelo.Avion;
import com.reservaVuelos.modelo.vuelo.ModeloAvion;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para gestión de Aviones con RandomAccessFile
 * 
 * Tamaños de atributos (usando writeChars: 2 bytes por carácter):
 * - idAvion: Long = 8 bytes
 * - estado: boolean = 1 byte (true = activo / false = inactivo)
 * - modeloAvion: 11 caracteres × 2 = 22 bytes
 * - capacidad: int = 4 bytes
 */
@Repository
public class RepositorioAviones extends ArchivoDAO<Avion> {
    
    private final int MODELO_AVION_SIZE = 11;
    private static final Long TOTAL_RECORD_SIZE = 35L; // bytes por registro de Avion

    public RepositorioAviones() {
        super("aviones", TOTAL_RECORD_SIZE);
    }

    @Override
    protected byte[] convertirABytes(Avion avion) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        try {
            // Guardar los datos en el orden de la documentacion
            dos.writeLong(avion.getId());
            dos.writeBoolean(true);
            dos.writeChars(ajustarString(avion.getModeloAvion().name(), MODELO_AVION_SIZE));
            dos.writeInt(avion.getCapacidad());

            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected Avion convertirDeBytes(byte[] bytes) {
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        DataInputStream dis = new DataInputStream(bais);

        try {
            // Leer en el orden de la documentacion
            Long ID = dis.readLong();
            dis.readBoolean();

            String modeloAvionStr = leerStringFijo(dis, MODELO_AVION_SIZE);
            ModeloAvion modeloAvion = ModeloAvion.valueOf(modeloAvionStr);

            dis.readInt();

            return new Avion(ID, modeloAvion);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
