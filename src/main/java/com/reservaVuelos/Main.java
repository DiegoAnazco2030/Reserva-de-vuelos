package com.reservaVuelos;

import com.reservaVuelos.vista.PaginaPrincipal;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import javax.swing.SwingUtilities;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {

        // Creamos el constructor del spring boot
        SpringApplicationBuilder builder = new SpringApplicationBuilder(Main.class);
        // Desactivamos la configuracion de version web porque usamos swing
        builder.headless(false);
        // Construyo mi aplicacion haciendo la inyeccion de depdencias automaticamente
        ConfigurableApplicationContext context = builder.run(args);
        // Inicio mi interfaz grafica y la corro en un hilo propio del spring boot
        SwingUtilities.invokeLater(() -> {
            // Inicio mi pagina principal con ya echa la inversion de dependencias
            PaginaPrincipal pagina = context.getBean(PaginaPrincipal.class);
            // Muestro la ventana
            pagina.setVisible(true);
        });
    }
}