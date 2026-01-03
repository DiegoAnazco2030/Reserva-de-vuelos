package com.reservaVuelos.vista.subsistemas;

import com.reservaVuelos.modelo.vuelo.ModeloAvion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class subAviones extends JFrame {

    private JPanel JPanelAvion;

    //Los text field para la busqueda
    private JTextField textFiBusquedaAvion;

    //Los bottom para el crud
    private JButton registrarModificarAvion;
    private JButton eliminarAvion;

    //Tabla de aviones
    private JTable tablaAviones;
    private DefaultTableModel modeloTablaAviones;

    //Combo Box del modelo de avion
    private JComboBox modeloAvion;

    //JLabel apra mostrar los mensajes del sistema
    private JLabel mensajeSistema;

    public subAviones() {

        setContentPane(JPanelAvion);
        setTitle("Registro de avion");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);

        DefaultComboBoxModel<ModeloAvion> modelModeloAvion = new DefaultComboBoxModel<>(ModeloAvion.values());
        modeloAvion.setModel(modelModeloAvion);

        /*Esta funcion es para que se pueda seleccionar y deseleccionar de la tabla y dependiendo
        que si esta selecciona es modificar y si no lo esta es registrar*/
        tablaAviones.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                if (tablaAviones.getSelectedRow() == -1) {
                    registrarModificarAvion.setText("Registrar");
                } else {
                    registrarModificarAvion.setText("Modificar");
                    cargarCamposDesdeTabla();
                }
            }
        });

        //Listener del text field de buscar

        textFiBusquedaAvion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        //Listener de los bottoms

        registrarModificarAvion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        eliminarAvion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    //Carga los datos seleccionados en la tabla en el formulario de forma automatica
    private void cargarCamposDesdeTabla() {
        int fila = tablaAviones.getSelectedRow();
        if(fila != -1) {
            modeloAvion.setSelectedItem(ModeloAvion.valueOf(modeloTablaAviones.getValueAt(fila, 1).toString()));
        }
    }

    private void createUIComponents() {
        tablaAviones = new  JTable(){

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
