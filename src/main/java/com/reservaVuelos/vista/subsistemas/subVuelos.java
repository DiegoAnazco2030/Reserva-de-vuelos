package com.reservaVuelos.vista.subsistemas;

import com.reservaVuelos.controlador.IControlador;
import com.reservaVuelos.modelo.vuelo.Ciudad;
import com.reservaVuelos.servicio.DTOs.DTOsSalida.SalidaAerolineaDTO;
import com.reservaVuelos.servicio.DTOs.DTOsSalida.SalidaAvionDTO;
import com.reservaVuelos.servicio.DTOs.DTOsSalida.SalidaVueloDTO;

import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class subVuelos extends JDialog {

    private IControlador controlador;
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

    public subVuelos(JFrame parent, IControlador controlador) {

        super(parent, "Registro de vuelos", true);
        this.controlador = controlador;
        setContentPane(JPanelVuelo);
        setTitle("Registro de vuelos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        actualizarTablaAerolineas();
        actualizarTablaVuelos("");
        actualizarTablaAviones();

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
                actualizarTablaVuelos(textFiBuscarVuelo.getText());
            }
        });

        //Bottom para Crud

        registrarModificarVuelo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int filaSeleccionada = tablaVuelos.getSelectedRow();
                    int filaSelAvion = tablaAviones.getSelectedRow();
                    int filaSelAerolinea = tablaAerolineas.getSelectedRow();

                    if(filaSelAerolinea == -1 || filaSelAvion == -1) {
                        mensajeSistema.setText("Seleccione una opcion");
                        return;
                    }

                    Long idAvionSel = Long.valueOf(tablaAviones.getValueAt(filaSelAvion, 0).toString());
                    Long idAerolineaSel = Long.parseLong(tablaAerolineas.getValueAt(filaSelAerolinea, 0).toString());


                    if (filaSeleccionada == -1) {
                        controlador.crearVuelo(
                                (Ciudad) ciudadOrigen.getSelectedItem(),
                                (Ciudad) ciudadDestino.getSelectedItem(),
                                horaSalida.getText(),
                                horaLLegada.getText(),
                                idAvionSel,
                                idAerolineaSel
                        );
                        mensajeSistema.setText("Vuelo registrado");
                    }else {
                        Long idVueloSel = Long.parseLong(tablaVuelos.getValueAt(filaSeleccionada, 0).toString());
                        controlador.modificarVuelo(
                                idVueloSel,
                                idAerolineaSel,
                                (Ciudad) ciudadOrigen.getSelectedItem(),
                                (Ciudad) ciudadDestino.getSelectedItem(),
                                horaSalida.getText(),
                                horaLLegada.getText(),
                                idAvionSel
                        );
                        mensajeSistema.setText("Vuelo modificado");
                    }
                    actualizarTablaVuelos(textFiBuscarVuelo.getText());
                }catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        eliminarVuelo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    int filaSeleccionada = tablaVuelos.getSelectedRow();

                    if(filaSeleccionada == -1) {
                        mensajeSistema.setText("Seleccione un Vuelo");
                        return;
                    }
                    Long idVueloSel = Long.parseLong(tablaVuelos.getValueAt(filaSeleccionada, 0).toString());

                    controlador.eliminarVuelo(idVueloSel);
                    actualizarTablaVuelos(textFiBuscarVuelo.getText());
                    mensajeSistema.setText("Vuelo eliminado");
                }catch (Exception ex){
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }

    public void actualizarTablaVuelos(String buscarVuelosalPalabra){
        modeloTablaVuelos.setRowCount(0);
        List<SalidaVueloDTO> listVuelos = controlador.buscarVuelos(buscarVuelosalPalabra);
        for(SalidaVueloDTO  salidaVuelo : listVuelos) {
            Object[] fila = new Object[]{
                    salidaVuelo.idVuelo(),
                    salidaVuelo.origen(),
                    salidaVuelo.destino(),
                    salidaVuelo.salidaVuelo().substring(0,16),
                    salidaVuelo.llegadaVuelo().substring(0,16),
                    salidaVuelo.estadoVuelo(),
                    salidaVuelo.modeloAvion()
            };
            modeloTablaVuelos.addRow(fila);
        }
    }

    public void actualizarTablaAerolineas(){
        modeloTablaAerolineas.setRowCount(0);
        List<SalidaAerolineaDTO> listBuscarAerolinea = controlador.buscarAerolineas("");
        for(SalidaAerolineaDTO salidaAerolinea : listBuscarAerolinea){
            Object[] fila = new Object[]{
                    salidaAerolinea.aerolineaID(),
                    salidaAerolinea.nombre(),
            };
            modeloTablaAerolineas.addRow(fila);
        }
    }

    public void actualizarTablaAviones(){
        modeloTablaAviones.setRowCount(0);
        List<SalidaAvionDTO> listAviones = controlador.buscarAviones("");
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
        int fila = tablaVuelos.getSelectedRow();
        if(fila != -1) {
            ciudadOrigen.setSelectedItem(Ciudad.valueOf(modeloTablaVuelos.getValueAt(fila, 1).toString()));
            ciudadDestino.setSelectedItem(Ciudad.valueOf(modeloTablaVuelos.getValueAt(fila, 2).toString()));
            horaSalida.setText(modeloTablaVuelos.getValueAt(fila, 3).toString());
            horaLLegada.setText(modeloTablaVuelos.getValueAt(fila, 4).toString());
        }
    }

    //Cuando se deseleccionar la tabla se limpia el fomrulario
    private void limpiarFormulario() {
        horaSalida.setText("");
        horaLLegada.setText("");
        tablaVuelos.clearSelection();
        tablaAviones.clearSelection();
        tablaAerolineas.clearSelection();
        mensajeSistema.setText(""); // Limpiar avisos
    }

    //Constructor personalizado de mis tablas
    private void createUIComponents() {

        //Configuracion de tabla de vuelos

        tablaVuelos = crearTablaConToggle();

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
        modeloTablaVuelos.addColumn("Hora Llegada");
        modeloTablaVuelos.addColumn("Estado Vuelo");
        modeloTablaVuelos.addColumn("Modelo Avion");
        tablaVuelos.setModel(modeloTablaVuelos);

        //Configuracion de tabla de Aerolienas

        tablaAerolineas = crearTablaConToggle();

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

        tablaAviones = crearTablaConToggle();

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

    private JTable crearTablaConToggle(){
        return new JTable() {
            @Override
            public void changeSelection(int rowIndex, int columnIndex, boolean toggle, boolean extend) {
                if (isRowSelected(rowIndex) && isColumnSelected(columnIndex)) {
                    clearSelection();
                } else {
                    super.changeSelection(rowIndex, columnIndex, toggle, extend);
                }
            }
        };
    }
}
