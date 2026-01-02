package com.reservaVuelos.vista.subsistemas;

import javax.swing.*;

public class subAerolineas extends JFrame {

    private JPanel JPanelAero;

    //Tabla que muestra las aerolienas registradas
    private JTable tablaAerolinea;

    //Mis text fields de ingreso y busqueda
    private JTextField nombreAerolinea;
    private JTextField telefonoAerolinea;
    private JTextField emailAerolinea;
    private JTextField textFiBuscarAerolinea;

    //Bottoms de Crud y buscar
    private JButton registrarModificarAerolinea;
    private JButton buscarAerolinea;
    private JButton EliminarAerolinea;

    //JLabel apra mostrar los mensajes del sistema
    private JLabel mensajeService;

    public subAerolineas() {

        setContentPane(JPanelAero);
        setTitle("Registro de aerolinea");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);


    }
}
