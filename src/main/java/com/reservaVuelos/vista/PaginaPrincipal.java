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

    //Text field para buscar
    private JTextField buscarReserva;

    //Mensaje del sistema
    private JLabel mensajeSistema;

    public PaginaPrincipal() {

    }

    private void createUIComponents() {

        //Configuracion tabla reservas

        tablaReservas = new JTable() {

            @Override
            public void changeSelection(int rowIndex, int columnIndex, boolean toggle, boolean extend) {
                if (isRowSelected(rowIndex) && isColumnSelected(columnIndex)) {
                    clearSelection();
                } else {
                    super.changeSelection(rowIndex, columnIndex, toggle, extend);
                }
            }
        };

        modeloTablaReservas = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };

        modeloTablaReservas.addColumn("ID Reserva");
        modeloTablaReservas.addColumn("ID Usuario");
        modeloTablaReservas.addColumn("ID Vuelo");
        modeloTablaReservas.addColumn("ID Asientos");
        modeloTablaReservas.addColumn("Aerolinea");
        tablaReservas.setModel(modeloTablaReservas);

        //Configurar tabla de vuelos


    }
}
