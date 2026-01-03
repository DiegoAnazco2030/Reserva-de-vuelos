package com.reservaVuelos.vista.subsistemas;

import com.reservaVuelos.controlador.IControlador;
import com.reservaVuelos.modelo.vuelo.ModeloAvion;
import com.reservaVuelos.servicio.DTOs.DTOsSalida.SalidaAvionDTO;

import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class subAviones extends JFrame {

    private IControlador controlador;
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

    public subAviones(IControlador controlador) {

        this.controlador = controlador;
        setContentPane(JPanelAvion);
        setTitle("Registro de avion");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        actualizarTablaAviones("");

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
                actualizarTablaAviones(textFiBusquedaAvion.getText());
            }
        });

        //Listener de los bottoms

        registrarModificarAvion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    int filaSeleccionado = tablaAviones.getSelectedRow();
                    if(filaSeleccionado == -1){
                        controlador.crearAvion(
                                (ModeloAvion)  modeloAvion.getSelectedItem()
                        );
                        mensajeSistema.setText("Avion registrado");
                    }else{
                        controlador.modificarAvion(
                                Long.parseLong(modeloTablaAviones.getValueAt(filaSeleccionado,0).toString()),
                                (ModeloAvion)  modeloAvion.getSelectedItem()
                        );

                        mensajeSistema.setText("Avion modificado");
                    }
                    actualizarTablaAviones(textFiBusquedaAvion.getText());
                }catch(Exception ex){
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        eliminarAvion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    int filaSeleccionada = tablaAviones.getSelectedRow();

                    if(filaSeleccionada == -1){
                        mensajeSistema.setText("Seleccione un avion");
                        return;
                    }

                    controlador.eliminarAvion(
                            Long.parseLong(modeloTablaAviones.getValueAt(filaSeleccionada,0).toString())
                    );
                    actualizarTablaAviones(textFiBusquedaAvion.getText());
                    mensajeSistema.setText("Avion eliminado");

                }catch(Exception ex){
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }

    public void actualizarTablaAviones(String buscarAvionesPalabra){
        modeloTablaAviones.setRowCount(0);
        List<SalidaAvionDTO> listAviones = controlador.buscarAviones(buscarAvionesPalabra);
        for (SalidaAvionDTO salidaAvion : listAviones) {
            Object[] fila = new Object[]{
                    salidaAvion.idAvion(),
                    salidaAvion.modeloAvion(),
                    salidaAvion.modeloAvion().getCapacidadAsientos()
            };
            modeloTablaAviones.addRow(fila);
        }
    }

    //Carga los datos seleccionados en la tabla en el formulario de forma automatica
    private void cargarCamposDesdeTabla() {
        int fila = tablaAviones.getSelectedRow();
        if(fila != -1) {
            modeloAvion.setSelectedItem(ModeloAvion.valueOf(modeloTablaAviones.getValueAt(fila, 1).toString()));
        }
    }

    //Constructor personalizado de la tabla
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
