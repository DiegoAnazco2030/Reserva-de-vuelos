package com.reservaVuelos.vista.subsistemas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class subAerolineas extends JFrame {

    private JPanel JPanelAero;

    //Tabla que muestra las aerolienas registradas y la configuracion
    private JTable tablaAerolinea;
    private DefaultTableModel modeloTablaAerolinea;

    //Mis text fields de ingreso y busqueda
    private JTextField nombreAerolinea;
    private JTextField telefonoAerolinea;
    private JTextField emailAerolinea;
    private JTextField textFiBuscarAerolinea;

    //Bottoms de Crud
    private JButton registrarModificarAerolineas;
    private JButton EliminarAerolinea;

    //JLabel apra mostrar los mensajes del sistema
    private JLabel mensajeService;

    public subAerolineas() {

        setContentPane(JPanelAero);
        setTitle("Registro de aerolinea");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);

        /*Esta funcion es para que se pueda seleccionar y deseleccionar de la tabla y dependiendo
        que si esta selecciona es modificar y si no lo esta es registrar*/
        tablaAerolinea.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                if (tablaAerolinea.getSelectedRow() == -1) {
                    registrarModificarAerolineas.setText("Registrar");
                    limpiarFormulario();
                } else {
                    registrarModificarAerolineas.setText("Modificar");
                    cargarCamposDesdeTabla();
                }
            }
        });


        //Action listeners del buscar

        textFiBuscarAerolinea.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        //Action listeners de los bottoms

        registrarModificarAerolineas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String modo = registrarModificarAerolineas.getText();

                if (modo.equals("Registrar")) {
                    // Lógica para capturar campos y enviar al service.save()
                    System.out.println("Registrando nueva aerolínea...");
                } else {
                    // Lógica para capturar el ID de la fila y enviar al service.update()
                    int fila = tablaAerolinea.getSelectedRow();
                    Object id = modeloTablaAerolinea.getValueAt(fila, 0);
                    System.out.println("Modificando aerolínea con ID: " + id);
                }
            }
        });

        EliminarAerolinea.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

    }

    //Carga los datos seleccionados en la tabla en el formulario de forma automatica
    private void cargarCamposDesdeTabla() {
        int fila = tablaAerolinea.getSelectedRow();
        if(fila != -1) {
            nombreAerolinea.setText(modeloTablaAerolinea.getValueAt(fila, 1).toString());
            telefonoAerolinea.setText(modeloTablaAerolinea.getValueAt(fila, 2).toString());
            emailAerolinea.setText(modeloTablaAerolinea.getValueAt(fila, 3).toString());
        }
    }

    //Cuando se deseleccionar la tabla se limpia el fomrulario
    private void limpiarFormulario() {
        nombreAerolinea.setText("");
        telefonoAerolinea.setText("");
        emailAerolinea.setText("");
        mensajeService.setText(""); // Limpiar avisos
    }

    private void createUIComponents() {
        tablaAerolinea = new JTable() {
            @Override
            public void changeSelection(int rowIndex, int columnIndex, boolean toggle, boolean extend) {
                if (isRowSelected(rowIndex) && isColumnSelected(columnIndex)) {
                    clearSelection();
                } else {
                    super.changeSelection(rowIndex, columnIndex, toggle, extend);
                }
            }
        };

        modeloTablaAerolinea = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };
        modeloTablaAerolinea.addColumn("ID");
        modeloTablaAerolinea.addColumn("Nombre");
        modeloTablaAerolinea.addColumn("Telefono");
        modeloTablaAerolinea.addColumn("Email");
        tablaAerolinea.setModel(modeloTablaAerolinea);
    }
}
