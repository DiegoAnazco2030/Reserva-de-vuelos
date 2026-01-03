package com.reservaVuelos.vista.subsistemas;

import com.reservaVuelos.controlador.IControlador;
import com.reservaVuelos.modelo.persona.RolUsuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class subUsuarios extends JFrame{

    private IControlador controlador;
    private JPanel JPanelUsuario;

    //Tabla y configuracion
    private JTable tablaUsuarios;
    private DefaultTableModel modelotablaUsuario;

    //Text Field de ingreso y busqueda
    private JTextField textFiBuscarUsuario;
    private JTextField nombrePersona;
    private JTextField apellidoPersona;
    private JTextField telefonoPersona;
    private JTextField edadPersona;
    private JTextField emailUsuario;
    private JTextField contrasenaUsuario;
    private JTextField pasaporteUsuario;

    //Bottoms de buscar
    private JButton registroUsuario;
    private JButton eliminarUsuario;

    //Combo box de seleccionar el rol de Usuario o Empleado
    private JComboBox rolUsuario;

    //JLabl del mensaje del sistema
    private JLabel mensajeSistema;

    public subUsuarios(IControlador controlador) {

        this.controlador = controlador;
        setContentPane(JPanelUsuario);
        setTitle("Registro de usuarios");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);

        DefaultComboBoxModel<RolUsuario> modelo = new DefaultComboBoxModel<>(RolUsuario.values());
        rolUsuario.setModel(modelo);

        /*Esta funcion es para que se pueda seleccionar y deseleccionar de la tabla y dependiendo
        que si esta selecciona es modificar y si no lo esta es registrar*/
        tablaUsuarios.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                if (tablaUsuarios.getSelectedRow() == -1) {
                    registroUsuario.setText("Registrar");
                    limpiarFormulario();
                } else {
                    registroUsuario.setText("Modificar");
                    cargarCamposDesdeTabla();
                }
            }
        });

        //Listener del text Field para buscar

        textFiBuscarUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        //Listeners de los bottoms

        registroUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        eliminarUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

    }

    //Carga los datos seleccionados en la tabla en el formulario de forma automatica
    private void cargarCamposDesdeTabla() {
        int fila = tablaUsuarios.getSelectedRow();
        if(fila != -1) {
            nombrePersona.setText(modelotablaUsuario.getValueAt(fila, 1).toString());
            apellidoPersona.setText(modelotablaUsuario.getValueAt(fila, 2).toString());
            telefonoPersona.setText(modelotablaUsuario.getValueAt(fila, 3).toString());
            edadPersona.setText(modelotablaUsuario.getValueAt(fila, 4).toString());
            emailUsuario.setText(modelotablaUsuario.getValueAt(fila, 5).toString());
            rolUsuario.setSelectedItem(RolUsuario.valueOf(modelotablaUsuario.getValueAt(fila, 6).toString()));
        }
    }

    //Cuando se deseleccionar la tabla se limpia el fomrulario
    private void limpiarFormulario() {
        nombrePersona.setText("");
        apellidoPersona.setText("");
        telefonoPersona.setText("");
        edadPersona.setText("");
        emailUsuario.setText("");
        contrasenaUsuario.setText("");
        pasaporteUsuario.setText("");
        mensajeSistema.setText(""); // Limpiar avisos
    }

    private void createUIComponents() {

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

        modelotablaUsuario = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };

        modelotablaUsuario.addColumn("ID");
        modelotablaUsuario.addColumn("Nombre");
        modelotablaUsuario.addColumn("Apellido");
        modelotablaUsuario.addColumn("Telefono");
        modelotablaUsuario.addColumn("Edad");
        modelotablaUsuario.addColumn("Email");
        modelotablaUsuario.addColumn("Rol");
        tablaUsuarios.setModel(modelotablaUsuario);
    }
}
