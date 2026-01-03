package com.reservaVuelos.vista;

import com.reservaVuelos.controlador.IControlador;
import com.reservaVuelos.servicio.DTOs.DTOsSalida.SalidaReservaDTO;
import com.reservaVuelos.servicio.DTOs.DTOsSalida.SalidaUsuarioDTO;
import com.reservaVuelos.servicio.DTOs.DTOsSalida.SalidaVueloDTO;
import com.reservaVuelos.vista.subsistemas.subAerolineas;
import com.reservaVuelos.vista.subsistemas.subAviones;
import com.reservaVuelos.vista.subsistemas.subUsuarios;
import com.reservaVuelos.vista.subsistemas.subVuelos;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PaginaPrincipal extends JFrame {

    private JPanel JPanelPrincipal;
    private IControlador controlador;

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

    public PaginaPrincipal(IControlador controlador) {

        this.controlador = controlador;
        setContentPane(JPanelPrincipal);
        setTitle("Registro de reservas");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);

        actualizarTablaAsientos(null);
        actualizarTablaUsuarios();
        actualizarTablaReservas("");
        actualizarTablaVuelos();

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
                actualizarTablaReservas(buscarReserva.getText());
            }
        });

        //Action listener de los bottom de los subsistemas

        aerolineasSubsistema.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                subAerolineas subSistemaAerolineas = new subAerolineas(controlador);
                subSistemaAerolineas.setVisible(true);
            }
        });

        avionesSubsistema.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                subAviones subSistemaAaviones = new subAviones(controlador);
                subSistemaAaviones.setVisible(true);
            }
        });

        usuariosSubsistema.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                subUsuarios subSistemaUsuarios = new subUsuarios(controlador);
                subSistemaUsuarios.setVisible(true);
            }
        });

        vuelosSubsistema.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                subVuelos subSistemaVuelos = new subVuelos(controlador);
                subSistemaVuelos.setVisible(true);
            }
        });

        //Action listeners de los bottom de registrar y eliminar

        registrarModiReservas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    int filaSeleccionada = tablaReservas.getSelectedRow();
                    int filaVuelo = tablaVuelos.getSelectedRow();
                    int filaAsiento = tablaAsientos.getSelectedRow();
                    int filaUsuario = tablaUsuarios.getSelectedRow();

                    if(filaVuelo == -1 || filaAsiento == -1 || filaUsuario == -1){
                        mensajeSistema.setText("Seleccione vuelo, asiento y usuario");
                        return;
                    }

                    if(filaSeleccionada == -1){
                        controlador.crearReserva(
                                Long.parseLong(modeloTablaVuelos.getValueAt(filaVuelo, 0).toString()),
                                Long.parseLong(modeloTablaAsientos.getValueAt(filaAsiento, 0).toString()),
                                Long.parseLong(modeloTablaUsuarios.getValueAt(filaUsuario, 0).toString())
                        );
                        actualizarTablaReservas(buscarReserva.getText());
                        limpiarFormulario();
                        mensajeSistema.setText("Reserva Creada");
                    }else{

                        controlador.modificarReserva(
                                Long.parseLong(modeloTablaReservas.getValueAt(filaSeleccionada,0).toString()),
                                Long.parseLong(modeloTablaVuelos.getValueAt(filaVuelo, 0).toString()),
                                Long.parseLong(modeloTablaAsientos.getValueAt(filaAsiento, 0).toString()),
                                Long.parseLong(modeloTablaUsuarios.getValueAt(filaUsuario, 0).toString())
                        );
                        actualizarTablaReservas(buscarReserva.getText());
                        limpiarFormulario();
                        mensajeSistema.setText("Reserva Modificada");
                    }
                }catch(Exception ex){
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        eliminarReservas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int filaSeleccionada = tablaReservas.getSelectedRow();
                    if (filaSeleccionada == -1) {
                        mensajeSistema.setText("Seleccione una reserva");
                    } else {
                        controlador.eliminarReserva(Long.parseLong(modeloTablaReservas.getValueAt(filaSeleccionada, 0).toString()));
                        actualizarTablaReservas(buscarReserva.getText());
                        limpiarFormulario();
                        mensajeSistema.setText("Reserva Eliminada");
                    }
                }catch(Exception ex){
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        tablaVuelos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int filaSeleccionada = tablaVuelos.getSelectedRow();

                if (filaSeleccionada != -1) {
                    Long idVuelo = Long.parseLong(modeloTablaVuelos.getValueAt(filaSeleccionada, 0).toString());
                    actualizarTablaAsientos(idVuelo);
                } else {
                    modeloTablaAsientos.setRowCount(0);
                }
            }
        });
    }

    public void actualizarTablaReservas(String buscarReservaPalbra){
        modeloTablaReservas.setRowCount(0);
        List<SalidaReservaDTO> listBuscadaReserva = controlador.buscarReservas(buscarReservaPalbra);
        for(SalidaReservaDTO salidaReserva : listBuscadaReserva) {
            Object[] fila = new Object[]{
                    salidaReserva.idReserva(),
                    salidaReserva.idVuelo(),
                    salidaReserva.nombrePersona(),
                    salidaReserva.apellidoPersona(),
                    salidaReserva.origen(),
                    salidaReserva.destino()
            };
            modeloTablaReservas.addRow(fila);
        }
    }

    public void actualizarTablaUsuarios(){
        modeloTablaUsuarios.setRowCount(0);
        List<SalidaUsuarioDTO> listUsuarios = controlador.buscarUsuarios("");
        for(SalidaUsuarioDTO salidaUsuario : listUsuarios) {
            Object[] fila = new Object[]{
                    salidaUsuario.idUsuario(),
                    salidaUsuario.nombre(),
                    salidaUsuario.Apellido(),
                    salidaUsuario.correo()
            };
            modeloTablaUsuarios.addRow(fila);
        }
    }

    public void actualizarTablaAsientos(Long idVuelo){
        modeloTablaAsientos.setRowCount(0);
        if(idVuelo==null){
            return;
        }
        /*
        *
        * Esto debo completar con lo que se haga con asientos
        *
         */
    }

    public void actualizarTablaVuelos(){
        modeloTablaVuelos.setRowCount(0);
        List<SalidaVueloDTO> listVuelos = controlador.buscarVuelos("");
        for(SalidaVueloDTO  salidaVuelo : listVuelos) {
            Object[] fila = new Object[]{
                    salidaVuelo.idVuelo(),
                    salidaVuelo.origen(),
                    salidaVuelo.destino(),
                    salidaVuelo.llegadaVuelo()
            };
            modeloTablaVuelos.addRow(fila);
        }
    }

    private void limpiarFormulario() {
        tablaReservas.clearSelection();
        tablaVuelos.clearSelection();
        tablaAsientos.clearSelection();
        tablaUsuarios.clearSelection();
        modeloTablaAsientos.setRowCount(0); // Limpiar asientos hasta que elijan otro vuelo
        registrarModiReservas.setText("Registrar");
    }

    private void createUIComponents() {

        //Configuracion tabla reservas

        tablaReservas = crearTablaConToggle();

        modeloTablaReservas = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };

        modeloTablaReservas.addColumn("ID Reserva");
        modeloTablaReservas.addColumn("ID Vuelo");
        modeloTablaReservas.addColumn("Nombre");
        modeloTablaReservas.addColumn("Apellido");
        modeloTablaReservas.addColumn("Origen");
        modeloTablaReservas.addColumn("Destino");
        tablaReservas.setModel(modeloTablaReservas);

        //Configurar tabla de vuelos

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
        tablaVuelos.setModel(modeloTablaVuelos);

        //Configurar tabla de asientos

        tablaAsientos = crearTablaConToggle();

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

        tablaUsuarios = crearTablaConToggle();

        modeloTablaUsuarios = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };

        modeloTablaUsuarios.addColumn("ID");
        modeloTablaUsuarios.addColumn("Nombre");
        modeloTablaUsuarios.addColumn("Apellido");
        modeloTablaUsuarios.addColumn("Correo");
        tablaUsuarios.setModel(modeloTablaUsuarios);

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
