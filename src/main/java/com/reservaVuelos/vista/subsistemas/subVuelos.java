package com.reservaVuelos.vista.subsistemas;

import com.reservaVuelos.modelo.persona.RolUsuario;
import com.reservaVuelos.modelo.vuelo.Ciudad;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class subVuelos extends JFrame {

    private JPanel JPanelVuelo;

    //TextField ingreso de horas del vuelo y el buscar
    private JTextField textFiBuscarVuelo;
    private JTextField horaSalida;
    private JTextField horaLLegada;

    //Tablas y sus configuraciones
    private JTable tablaVuelos;
    private DefaultTableModel modeloTablaVuelos;
    private JTable tablaAerolineas;
    private DefaultTableModel modeloTablaAerolineas;
    private JTable tablaAviones;
    private DefaultTableModel modeloTablaAviones;

    //Combo box del formulario para las ciudades
    private JComboBox ciudadOrigen;
    private JComboBox ciudadDestino;

    //Bottoms de crud
    private JButton registrarModificarVuelo;
    private JButton eliminarVuelo;

    //Mensaje del sistema
    private JLabel mensajeSistema;

    public subVuelos() {

        setContentPane(JPanelVuelo);
        setTitle("Registro de vuelos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);

        ciudadOrigen.setModel(new DefaultComboBoxModel<>(Ciudad.values()));
        ciudadDestino.setModel(new DefaultComboBoxModel<>(Ciudad.values()));

        /*Esta funcion es para que se pueda seleccionar y deseleccionar de la tabla y dependiendo
        que si esta selecciona es modificar y si no lo esta es registrar*/
        tablaVuelos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                if (tablaVuelos.getSelectedRow() == -1) {
                    registrarModificarVuelo.setText("Registrar");
                    limpiarFormulario();
                } else {
                    registrarModificarVuelo.setText("Modificar");
                    cargarCamposDesdeTabla();
                }
            }
        });

        //Action listener del text field para buscar

        textFiBuscarVuelo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        //Bottom para Crud

        registrarModificarVuelo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        eliminarVuelo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    //Carga los datos seleccionados en la tabla en el formulario de forma automatica
    private void cargarCamposDesdeTabla() {
        int fila = tablaVuelos.getSelectedRow();
        if(fila != -1) {
            ciudadOrigen.setSelectedItem(Ciudad.valueOf(modeloTablaVuelos.getValueAt(fila, 2).toString()));
            ciudadDestino.setSelectedItem(Ciudad.valueOf(modeloTablaVuelos.getValueAt(fila, 3).toString()));
            horaSalida.setText(modeloTablaVuelos.getValueAt(fila, 4).toString());
            horaLLegada.setText(modeloTablaVuelos.getValueAt(fila, 5).toString());
        }
    }

    //Cuando se deseleccionar la tabla se limpia el fomrulario
    private void limpiarFormulario() {
        horaSalida.setText("");
        horaLLegada.setText("");
        mensajeSistema.setText(""); // Limpiar avisos
    }

    private void createUIComponents() {

        //Configuracion de tabla de vuelos

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
        modeloTablaVuelos.addColumn("Aerolinea");
        modeloTablaVuelos.addColumn("Origen");
        modeloTablaVuelos.addColumn("Destino");
        modeloTablaVuelos.addColumn("Hora Salida");
        modeloTablaVuelos.addColumn("Hora Llegada");
        tablaVuelos.setModel(modeloTablaVuelos);

        //Configuracion de tabla de Aerolienas

        tablaAerolineas = new JTable() {

            @Override
            public void changeSelection(int rowIndex, int columnIndex, boolean toggle, boolean extend) {
                if (isRowSelected(rowIndex) && isColumnSelected(columnIndex)) {
                    clearSelection();
                } else {
                    super.changeSelection(rowIndex, columnIndex, toggle, extend);
                }
            }
        };

        modeloTablaAerolineas = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };

        modeloTablaAerolineas.addColumn("ID");
        modeloTablaAerolineas.addColumn("Nombre");
        tablaAerolineas.setModel(modeloTablaAerolineas);

        //Configuracion de tabla de aviones

        tablaAviones = new JTable() {

            @Override
            public void changeSelection(int rowIndex, int columnIndex, boolean toggle, boolean extend) {
                if (isRowSelected(rowIndex) && isColumnSelected(columnIndex)) {
                    clearSelection();
                } else {
                    super.changeSelection(rowIndex, columnIndex, toggle, extend);
                }
            }
        };

        modeloTablaAviones = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };

        modeloTablaAviones.addColumn("ID");
        modeloTablaAviones.addColumn("Modelo");
        modeloTablaAviones.addColumn("Capacidad");
        tablaAviones.setModel(modeloTablaAviones);
    }
}
