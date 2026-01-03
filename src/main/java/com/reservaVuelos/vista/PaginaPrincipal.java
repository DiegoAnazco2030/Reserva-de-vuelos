package com.reservaVuelos.vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PaginaPrincipal extends JFrame {

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

        setContentPane(JPanelPrincipal);
        setTitle("Registro de reservas");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);

        /*Esta funcion es para que se pueda seleccionar y deseleccionar de la tabla y dependiendo
        que si esta selecciona es modificar y si no lo esta es registrar*/
        tablaReservas.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                if (tablaReservas.getSelectedRow() == -1) {
                    registrarModiReservas.setText("Registrar");
                } else {
                    registrarModiReservas.setText("Modificar");
                }
            }
        });

        //Action listeners del buscar

        buscarReserva.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        //Action listener de los bottom de los subsistemas

        aerolineasSubsistema.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        avionesSubsistema.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        usuariosSubsistema.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        vuelosSubsistema.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        //Action listeners de los bottom de registrar y eliminar

        registrarModiReservas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        eliminarReservas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
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

        tablaVuelos = new JTable() {

            @Override
            public void changeSelection(int rowIndex, int columnIndex, boolean toggle, boolean extend) {
                if (isRowSelected(rowIndex) && isColumnSelected(columnIndex)) {
                    clearSelection();
                } else {
                    super.changeSelection(rowIndex, columnIndex, toggle, extend);
                }
            }
        };

        modeloTablaVuelos = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };

        modeloTablaVuelos.addColumn("ID");
        modeloTablaVuelos.addColumn("Origen");
        modeloTablaVuelos.addColumn("Destino");
        modeloTablaVuelos.addColumn("Hora Salida");
        tablaVuelos.setModel(modeloTablaVuelos);

        //Configurar tabla de asientos

        tablaAsientos = new JTable() {

            @Override
            public void changeSelection(int rowIndex, int columnIndex, boolean toggle, boolean extend) {
                if (isRowSelected(rowIndex) && isColumnSelected(columnIndex)) {
                    clearSelection();
                } else {
                    super.changeSelection(rowIndex, columnIndex, toggle, extend);
                }
            }
        };

        modeloTablaAsientos = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };

        modeloTablaAsientos.addColumn("ID");
        modeloTablaAsientos.addColumn("Tipo");
        tablaAsientos.setModel(modeloTablaAsientos);

        //Configurar tabla de usuarios

        tablaUsuarios = new JTable() {

            @Override
            public void changeSelection(int rowIndex, int columnIndex, boolean toggle, boolean extend) {
                if (isRowSelected(rowIndex) && isColumnSelected(columnIndex)) {
                    clearSelection();
                } else {
                    super.changeSelection(rowIndex, columnIndex, toggle, extend);
                }
            }
        };

        modeloTablaUsuarios = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };

        modeloTablaUsuarios.addColumn("ID");
        modeloTablaUsuarios.addColumn("Nombre");
        modeloTablaUsuarios.addColumn("Apellido");
        modeloTablaUsuarios.addColumn("Telefono");

    }
}
