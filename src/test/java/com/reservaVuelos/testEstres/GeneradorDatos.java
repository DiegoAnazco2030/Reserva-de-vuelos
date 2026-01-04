package com.reservaVuelos.testEstres;

import com.reservaVuelos.modelo.persona.Usuario;
import java.util.Random;
import java.util.UUID;

public class GeneradorDatos {

    private static final Random random = new Random();
    private static final String[] NOMBRES = {"Juan", "Ana", "Luis", "Maria", "Pedro", "Lucia", "Carlos", "Sofia"};
    private static final String[] APELLIDOS = {"Perez", "Gomez", "Lopez", "Diaz", "Mora", "Ruiz", "Jimenez", "Torres"};

    public static Usuario generarUsuarioAleatorio() {
        // Generao datos aleatorios
        String nombre = NOMBRES[random.nextInt(NOMBRES.length)];
        String apellido = APELLIDOS[random.nextInt(APELLIDOS.length)];

        // Telefono aleatorio
        String telefono = "09" + String.format("%08d", (long)(random.nextDouble() * 100000000L));

        int edad = 18 + random.nextInt(70);

        // UUID para hacer unico el email y pasaporte
        String uuid = UUID.randomUUID().toString().substring(0, 8);
        String email = nombre.toLowerCase() + "." + uuid + "@test.com";
        String pasaporte = uuid.toUpperCase();
        String password = "Pass" + uuid;

        // El ID se asignará en el servicio/repo, aquí lo dejamos null o temporal
        return new Usuario(nombre, apellido, telefono, edad, pasaporte, password, email, null);
    }
}
