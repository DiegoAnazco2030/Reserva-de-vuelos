package com.reservaVuelos.vista.subsistemas;

import com.reservaVuelos.controlador.IControlador;
import com.reservaVuelos.modelo.persona.RolUsuario;
import com.reservaVuelos.servicio.DTOs.DTOsSalida.SalidaEmpleadoDTO;
import com.reservaVuelos.servicio.DTOs.DTOsSalida.SalidaUsuarioDTO;

import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class subUsuarios extends JFrame{

    private final IControlador controlador;
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
        actualizarTablaUsuarios("");

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
                actualizarTablaUsuarios(textFiBuscarUsuario.getText());
            }
        });

        //Listeners de los bottoms

        registroUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    int filaSeleccionada = tablaUsuarios.getSelectedRow();
                    RolUsuario seleccion =  (RolUsuario) rolUsuario.getSelectedItem();

                    if (seleccion == null) {
                        mensajeSistema.setText("Seleccione el rol de usuario");
                        return;
                    }

                    String nombre = nombrePersona.getText();
                    String apellido = apellidoPersona.getText();
                    String tel = telefonoPersona.getText();
                    int edad = Integer.parseInt(edadPersona.getText()); // Idealmente envolver en try-catch
                    String email = emailUsuario.getText();
                    String pass = contrasenaUsuario.getText();

                    if(filaSeleccionada == -1){
                        if(seleccion == RolUsuario.USUARIO){
                            controlador.crearUsuario(nombre, apellido, tel, edad, email, pass, pasaporteUsuario.getText());
                        }else{
                            controlador.crearEmpleado(nombre, apellido, tel, edad, email, pass);
                        }
                        actualizarTablaUsuarios(textFiBuscarUsuario.getText());
                        mensajeSistema.setText("Usuario registrado");
                        return;
                    }

                    RolUsuario seleccionTabla = (RolUsuario) tablaUsuarios.getValueAt(filaSeleccionada, 4);
                    Long idSeleccion = Long.parseLong(tablaUsuarios.getValueAt(filaSeleccionada,0).toString());

                    if(seleccionTabla == RolUsuario.USUARIO){
                        controlador.modificarUsuario(idSeleccion, nombre, apellido, tel, edad, email);
                    }else{
                        controlador.modificarEmpleado(idSeleccion, nombre, apellido, tel, edad, email);
                    }

                    actualizarTablaUsuarios(textFiBuscarUsuario.getText());
                    limpiarFormulario();
                    mensajeSistema.setText("Usuario modificado");
                }catch(Exception ex){
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        eliminarUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    int filaSeleccionada = tablaUsuarios.getSelectedRow();

                    if(filaSeleccionada == -1){
                        mensajeSistema.setText("Seleccione una opcion");
                        return;
                    }

                    RolUsuario seleccion =  (RolUsuario) tablaUsuarios.getValueAt(filaSeleccionada, 4);
                    Long idSelcion = Long.parseLong(tablaUsuarios.getValueAt(filaSeleccionada,0).toString());

                    if(seleccion == RolUsuario.USUARIO){
                        controlador.eliminarUsuario(idSelcion);
                    }else {
                        controlador.eliminarEmpleado(idSelcion);
                    }
                    actualizarTablaUsuarios(textFiBuscarUsuario.getText());
                    limpiarFormulario();
                    mensajeSistema.setText("Usuario eliminado");

                }catch(Exception ex){
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

    }

    public void actualizarTablaUsuarios(String buscarUsuarioPalabra){
        modelotablaUsuario.setRowCount(0);
        List<SalidaUsuarioDTO> listUsuarios = controlador.buscarUsuarios(buscarUsuarioPalabra);
        List<SalidaEmpleadoDTO> listEmpleados = controlador.buscarEmpleados(buscarUsuarioPalabra);
        for (SalidaUsuarioDTO salidaUsuario : listUsuarios) {
            Object[] fila = new Object[]{
                    salidaUsuario.idUsuario(),
                    salidaUsuario.nombre(),
                    salidaUsuario.Apellido(),
                    salidaUsuario.correo(),
                    RolUsuario.USUARIO
            };
            modelotablaUsuario.addRow(fila);
        }
        for (SalidaEmpleadoDTO salidaEmpleado : listEmpleados) {
            Object[] fila = new Object[]{
                    salidaEmpleado.idEmpleado(),
                    salidaEmpleado.nombre(),
                    salidaEmpleado.apellido(),
                    salidaEmpleado.correoEmpleado(),
                    RolUsuario.ADMINISTRADOR
            };
            modelotablaUsuario.addRow(fila);
        }
    }

    //Carga los datos seleccionados en la tabla en el formulario de forma automatica
    private void cargarCamposDesdeTabla() {
        int fila = tablaUsuarios.getSelectedRow();
        if(fila != -1) {
            nombrePersona.setText(modelotablaUsuario.getValueAt(fila, 1).toString());
            apellidoPersona.setText(modelotablaUsuario.getValueAt(fila, 2).toString());
            emailUsuario.setText(modelotablaUsuario.getValueAt(fila, 3).toString());

            telefonoPersona.setText("");
            edadPersona.setText("");
            pasaporteUsuario.setText("");
            contrasenaUsuario.setText("");
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

    //Constructor personalizado de la tabla
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
        modelotablaUsuario.addColumn("Email");
        modelotablaUsuario.addColumn("Rol");
        tablaUsuarios.setModel(modelotablaUsuario);
    }
}
