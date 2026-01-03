package com.reservaVuelos.vista.subsistemas;

import com.reservaVuelos.controlador.IControlador;
import com.reservaVuelos.servicio.DTOs.DTOsSalida.SalidaAerolineaDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.util.List;
import java.awt.event.ActionListener;

public class subAerolineas extends JFrame {

    private IControlador controlador;
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

    public subAerolineas(IControlador controlador) {

        this.controlador = controlador;
        setContentPane(JPanelAero);
        setTitle("Registro de aerolinea");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        actualizarTablaAerolineas("");

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
                actualizarTablaAerolineas(textFiBuscarAerolinea.getText());
            }
        });

        //Action listeners de los bottoms

        registrarModificarAerolineas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    int filaSeleccionada =  tablaAerolinea.getSelectedRow();
                    if(filaSeleccionada == -1){
                        controlador.crearAerolinea(
                                nombreAerolinea.getText(),
                                telefonoAerolinea.getText(),
                                emailAerolinea.getText()
                        );
                        actualizarTablaAerolineas(textFiBuscarAerolinea.getText());
                        limpiarFormulario();
                        mensajeService.setText("Aerolinea Registrada");
                    }else{
                        controlador.modificarAerolinea(
                                Long.parseLong(modeloTablaAerolinea.getValueAt(filaSeleccionada,0).toString()),
                                nombreAerolinea.getText(),
                                telefonoAerolinea.getText(),
                                emailAerolinea.getText()
                        );
                        actualizarTablaAerolineas(textFiBuscarAerolinea.getText());
                        limpiarFormulario();
                        mensajeService.setText("Aerolinea Modificada");
                    }

                }catch(Exception ex){
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
                }

            }
        });

        EliminarAerolinea.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    int filaSeleccionada =  tablaAerolinea.getSelectedRow();

                    if(filaSeleccionada == -1){
                        mensajeService.setText("Seleccione un Aerolinea");
                        return;
                    }

                    controlador.eliminarAerolinea(Long.parseLong(modeloTablaAerolinea.getValueAt(filaSeleccionada,0).toString()));
                    actualizarTablaAerolineas(textFiBuscarAerolinea.getText());
                    limpiarFormulario();
                    mensajeService.setText("Aerolinea Eliminada");

                }catch(Exception ex){
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

    }

    public void actualizarTablaAerolineas(String buscarAerolineaPalabra){
        modeloTablaAerolinea.setRowCount(0);
        List<SalidaAerolineaDTO> listBuscarAerolinea = controlador.buscarAerolineas(buscarAerolineaPalabra);
        for(SalidaAerolineaDTO salidaAerolinea : listBuscarAerolinea){
            Object[] fila = new Object[]{
                    salidaAerolinea.aerolineaID(),
                    salidaAerolinea.nombre(),
                    salidaAerolinea.telefono(),
                    salidaAerolinea.email()
            };
            modeloTablaAerolinea.addRow(fila);
        }
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

    //Buidel de la tabla
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
