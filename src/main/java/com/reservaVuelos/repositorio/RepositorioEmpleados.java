package com.reservaVuelos.repositorio;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.reservaVuelos.modelo.persona.Empleado;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para gestión de Empleados con RandomAccessFile
 * 
 * Tamaños de atributos (usando writeChars: 2 bytes por carácter):
 * - empleadoID: Long = 8 bytes
 * - estado: boolean = 1 byte (true = activo / false = inactivo)
 * - nombre: 10 caracteres × 2 = 20 bytes
 * - apellido: 10 caracteres × 2 = 20 bytes
 * - telefono: 10 caracteres × 2 = 20 bytes
 * - edad: int = 4 bytes
 * - empleadoEmail: 40 caracteres × 2 = 80 bytes
 * - empleadoContrasenia: 28 caracteres × 2 = 56 bytes
 */
@Repository
public class RepositorioEmpleados extends ArchivoDAO<Empleado> {
    
    private final int NOMBRE_SIZE = 10;
    private final int APELLIDO_SIZE = 10;
    private final int TELEFONO_SIZE = 10;
    private final int EMAIL_SIZE = 40;
    private final int CONTRASENIA_SIZE = 28;
    private static final Long TOTAL_RECORD_SIZE = 209L; // bytes por registro de Empleado

    public RepositorioEmpleados() {
        super("empleados", TOTAL_RECORD_SIZE);
    }

    @Override
    protected byte[] convertirABytes(Empleado empleado) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        try {
            // Guardar los datos en el orden de la documentacion
            dos.writeLong(empleado.getId());
            dos.writeBoolean(true);
            dos.writeChars(ajustarString(empleado.getNombre(), NOMBRE_SIZE));
            dos.writeChars(ajustarString(empleado.getApellido(), APELLIDO_SIZE));
            dos.writeChars(ajustarString(empleado.getTelefono(), TELEFONO_SIZE));
            dos.writeInt(empleado.getEdad());
            dos.writeChars(ajustarString(empleado.getEmpleadoEmail(), EMAIL_SIZE));
            dos.writeChars(ajustarString(empleado.getEmpleadoContrasenia(), CONTRASENIA_SIZE));

            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected Empleado convertirDeBytes(byte[] bytes) {
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

            return new Empleado(nombre, apellido, telefono, edad, ID, contrasenia, email);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
