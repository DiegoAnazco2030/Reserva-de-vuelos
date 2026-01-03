package com.reservaVuelos.repositorio;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.reservaVuelos.modelo.vuelo.Aerolinea;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para gestión de Aerolineas con RandomAccessFile
 * 
 * Tamaños de atributos (usando writeChars: 2 bytes por carácter):
 * - aerolineaID: Long = 8 bytes
 * - estado: boolean = 1 byte (true = activo / false = inactivo)
 * - nombre: 30 caracteres × 2 = 60 bytes
 * - telefono: 10 caracteres × 2 = 20 bytes
 * - email: 40 caracteres × 2 = 80 bytes
 */
@Repository
public class RepositorioAerolineas extends ArchivoDAO<Aerolinea> {

    private final int NOMBRE_SIZE = 30;
    private final int TELEFONO_SIZE = 10;
    private final int EMAIL_SIZE = 40;
    private static final Long TOTAL_RECORD_SIZE = 169L; // bytes por registro de Aerolinea

    public RepositorioAerolineas() {
        super("aerolineas", TOTAL_RECORD_SIZE);
    }

    @Override
    protected byte[] convertirABytes(Aerolinea aerolinea) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        try {
            // Guardar los datos en el orden de la documentacion
            dos.writeLong(aerolinea.getId());
            dos.writeBoolean(true);
            dos.writeChars(ajustarString(aerolinea.getNombre(), NOMBRE_SIZE));
            dos.writeChars(ajustarString(aerolinea.getTelefono(), TELEFONO_SIZE));
            dos.writeChars(ajustarString(aerolinea.getEmail(), EMAIL_SIZE));

            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected Aerolinea convertirDeBytes(byte[] bytes) {
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        DataInputStream dis = new DataInputStream(bais);

        try {
            // Leer en el orden de la documentacion
            Long ID = dis.readLong();
            dis.readBoolean();

            String nombre = leerStringFijo(dis, NOMBRE_SIZE);

            String telefono = leerStringFijo(dis, TELEFONO_SIZE);

            String email = leerStringFijo(dis, EMAIL_SIZE);

            return new Aerolinea(ID, nombre, telefono, email);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
