package com.reservaVuelos.repositorio;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.reservaVuelos.modelo.persona.Usuario;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para gestión de Usuarios con RandomAccessFile
 * 
 * Tamaños de atributos (usando writeChars: 2 bytes por carácter):
 * - usuarioID: Long = 8 bytes
 * - estado: boolean = 1 byte (true = activo / false = inactivo)
 * - nombre: 10 caracteres × 2 = 20 bytes
 * - apellido: 10 caracteres × 2 = 20 bytes
 * - telefono: 10 caracteres × 2 = 20 bytes
 * - edad: int = 4 bytes
 * - usuarioEmail: 40 caracteres × 2 = 80 bytes
 * - usuarioContrasenia: 28 caracteres × 2 = 56 bytes
 * - usuarioPassaporteID: 9 caracteres × 2 = 18 bytes
 */
@Repository
public class RepositorioUsuarios extends ArchivoDAO<Usuario> {

    private final int NOMBRE_SIZE = 10;
    private final int APELLIDO_SIZE = 10;
    private final int TELEFONO_SIZE = 10;
    private final int EMAIL_SIZE = 40;
    private final int CONTRASENIA_SIZE = 28;
    private final int PASAPORTE_SIZE = 9;
    private static final Long TOTAL_RECORD_SIZE = 227L; // bytes por registro de Usuario

    public RepositorioUsuarios() {
        super("usuarios", TOTAL_RECORD_SIZE);
    }

    @Override
    protected byte[] convertirABytes(Usuario usuario) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        try {
            // Guardar los datos en el orden de la documentacion
            dos.writeLong(usuario.getId());
            dos.writeBoolean(true);
            dos.writeChars(ajustarString(usuario.getNombre(), NOMBRE_SIZE));
            dos.writeChars(ajustarString(usuario.getApellido(), APELLIDO_SIZE));
            dos.writeChars(ajustarString(usuario.getTelefono(), TELEFONO_SIZE));
            dos.writeInt(usuario.getEdad());
            dos.writeChars(ajustarString(usuario.getUsuarioEmail(), EMAIL_SIZE));
            dos.writeChars(ajustarString(usuario.getUsuarioContrasenia(), CONTRASENIA_SIZE));
            dos.writeChars(ajustarString(usuario.getUsuarioPassaporteID(), PASAPORTE_SIZE));

            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected Usuario convertirDeBytes(byte[] bytes) {
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        DataInputStream dis = new DataInputStream(bais);

        try {
            // Leer en el orden de la documentacion
            Long ID = dis.readLong();
            dis.readBoolean();

            String nombre = leerStringFijo(dis, NOMBRE_SIZE);
            String apellido = leerStringFijo(dis, APELLIDO_SIZE);
            String telefono = leerStringFijo(dis, TELEFONO_SIZE);
            int edad = dis.readInt();
            String email = leerStringFijo(dis, EMAIL_SIZE);
            String contrasenia = leerStringFijo(dis, CONTRASENIA_SIZE);
            String pasaporte = leerStringFijo(dis, PASAPORTE_SIZE);

            return new Usuario(nombre, apellido, telefono, edad, pasaporte, contrasenia, email, ID);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
