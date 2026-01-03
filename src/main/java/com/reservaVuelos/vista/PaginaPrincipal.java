package com.reservaVuelos.vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class PaginaPrincipal {

    private JPanel JPanelPrincipal;

    //Tablas y sus configuraciones
    private JTable tablaVuelos;
    private DefaultTableModel modeloTablaVuelos;
    private JTable tablaAsientos;
    private DefaultTableModel modeloTablaAsientos;
    private JTable tablaUsuarios;
    private DefaultTableModel modeloTablaUsuarios;
    private JTable tablaReservas;
    private DefaultTableModel modeloTablaReservas;

    //Bottoms de los panels de los subsistemas
    private JButton aerolineasSubsistema;
    private JButton usuariosSubsistema;
    private JButton avionesSubsistema;
    private JButton vuelosSubsistema;

    //Bottoms del crud de reservas
    private JButton registrarModiReservas;
    private JButton eliminarReservas;
    private JLabel mensajeSistema;
    private JTextField buscarReserva;
}
