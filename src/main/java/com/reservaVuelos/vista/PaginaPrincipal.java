package com.reservaVuelos.vista;

import com.reservaVuelos.controlador.IControlador;
import com.reservaVuelos.servicio.DTOs.DTOsSalida.SalidaAsientoDTO;
import com.reservaVuelos.servicio.DTOs.DTOsSalida.SalidaReservaDTO;
import com.reservaVuelos.servicio.DTOs.DTOsSalida.SalidaUsuarioDTO;
import com.reservaVuelos.servicio.DTOs.DTOsSalida.SalidaVueloDTO;
import com.reservaVuelos.vista.subsistemas.subAerolineas;
import com.reservaVuelos.vista.subsistemas.subAviones;
import com.reservaVuelos.vista.subsistemas.subUsuarios;
import com.reservaVuelos.vista.subsistemas.subVuelos;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.net.URL;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Component
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
        iniciarMenuSuperior();
        setTitle("Registro de reservas");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);

        URL urlLogo = getClass().getResource("/logo.png");
        if (urlLogo != null) {
            setIconImage(Toolkit.getDefaultToolkit().getImage(urlLogo));
        }

        actualizarTablaAsientos(null);
        actualizarTablaUsuarios();
        actualizarTablaReservas("");
        actualizarTablaVuelos();
        mensajeSistema.setText("Bienvenido");

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

        //Action listener de poner los asientos del vuelo seleccioando

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

        //Aplico mi configuracion de estilos
        aplicarEstiloCorporativo();
    }

    public void resetTablas(){
        limpiarFormulario();
        actualizarTablaReservas("");
        actualizarTablaVuelos();
        actualizarTablaUsuarios();
    }

    public void actualizarTablaReservas(String buscarReservaPalabra){
        modeloTablaReservas.setRowCount(0);
        List<SalidaReservaDTO> listBuscadaReserva = controlador.buscarReservas(buscarReservaPalabra);
        for(SalidaReservaDTO salidaReserva : listBuscadaReserva) {
            Object[] fila = new Object[]{
                    salidaReserva.idReserva(),
                    salidaReserva.idVuelo(),
                    salidaReserva.nombrePersona(),
                    salidaReserva.apellidoPersona(),
                    salidaReserva.origen(),
                    salidaReserva.destino(),
                    salidaReserva.idAsiento()
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
            mensajeSistema.setText("Seleccione un vuelo");
            return;
        }else{
            try{
                List<SalidaAsientoDTO> listAsientos = controlador.obtenerAsientos(idVuelo);
                for(SalidaAsientoDTO salidaAsiento : listAsientos) {
                    if(!salidaAsiento.estado()){
                        Object[] fila = new Object[]{
                            salidaAsiento.idAsiento(),
                            salidaAsiento.tipoAsiento()
                        };
                        modeloTablaAsientos.addRow(fila);
                    }
                }
            }catch (Exception ex){
                JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    public void actualizarTablaVuelos(){
        modeloTablaVuelos.setRowCount(0);
        List<SalidaVueloDTO> listVuelos = controlador.buscarVuelos("");
        for(SalidaVueloDTO  salidaVuelo : listVuelos) {
            Object[] fila = new Object[]{
                    salidaVuelo.idVuelo(),
                    salidaVuelo.origen(),
                    salidaVuelo.destino(),
                    salidaVuelo.llegadaVuelo().substring(0,16)
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
        modeloTablaReservas.addColumn("ID Asiento");
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

    //Esta funcion es la encargada de configurar los estilos, hay que implementarlos en los otros subsistemas tambien
    private void aplicarEstiloCorporativo() {

        //Agrego la paleta de colores
        Color azulMarino = Color.decode("#3270BF");
        Color celesteClaro = Color.decode("#33A1DE");
        Color blanco = Color.WHITE;

        // Congiguracion del bottom eliminar
        if (eliminarReservas != null) {
            eliminarReservas.setForeground(Color.RED);
            eliminarReservas.putClientProperty("JButton.borderColor", Color.RED);
            eliminarReservas.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        // Congiguracion tablas
        JTable[] tablas = {tablaVuelos, tablaAsientos, tablaUsuarios, tablaReservas};

        for (JTable tabla : tablas) {
            if (tabla != null) {
                // Estilo de la cabecera
                tabla.getTableHeader().setBackground(azulMarino);
                tabla.getTableHeader().setForeground(blanco);
                tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
                // Altura de filas
                tabla.setRowHeight(25);
                // Color de selección
                tabla.setSelectionBackground(celesteClaro);
                tabla.setSelectionForeground(blanco);
                // Quitar líneas verticales para limpieza visual
                tabla.setShowVerticalLines(false);
            }
        }

        // Barra de busqueda
        if (buscarReserva != null) {
            // Borde celeste cuando pasas el mouse
            buscarReserva.putClientProperty("JComponent.outline", celesteClaro);
            buscarReserva.putClientProperty("JTextField.placeholderText", "🔍 Buscar reserva...");
            buscarReserva.putClientProperty("JTextField.showClearButton", true);
        }
    }

    private void iniciarMenuSuperior() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menuSubsistemas = new JMenu("Subsistemas");
        menuSubsistemas.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JMenuItem itemAerolineas = new JMenuItem("Aerolíneas");
        JMenuItem itemAviones = new JMenuItem("Aviones");
        JMenuItem itemUsuarios = new JMenuItem("Usuarios");
        JMenuItem itemVuelos = new JMenuItem("Vuelos");

        // ActionListeners de los Bottoms

        // Bottom para Aerolíneas
        itemAerolineas.addActionListener(e -> {
            subAerolineas subSistemaAerolineas = new subAerolineas(PaginaPrincipal.this, controlador);
            subSistemaAerolineas.setVisible(true);
            resetTablas(); //
        });

        // Bottom para Aviones
        itemAviones.addActionListener(e -> {
            subAviones subSistemaAviones = new subAviones(PaginaPrincipal.this, controlador);
            subSistemaAviones.setVisible(true);
            resetTablas();
        });

        // Bottom para Usuarios
        itemUsuarios.addActionListener(e -> {
            subUsuarios subSistemaUsuarios = new subUsuarios(PaginaPrincipal.this, controlador);
            subSistemaUsuarios.setVisible(true);
            resetTablas();
        });

        // Bottom para Vuelos
        itemVuelos.addActionListener(e -> {
            subVuelos subSistemaVuelos = new subVuelos(PaginaPrincipal.this, controlador);
            subSistemaVuelos.setVisible(true);
            resetTablas();
        });

        menuSubsistemas.add(itemAerolineas);
        menuSubsistemas.add(itemAviones);
        menuSubsistemas.add(itemUsuarios);
        menuSubsistemas.add(itemVuelos);
        menuBar.add(menuSubsistemas);

        //Menu Help
        JMenu menuHelp = new JMenu("Help");
        menuHelp.setFont(new Font("Segoe UI", Font.BOLD, 14));
        JMenuItem itemContacto = new JMenuItem("Contactanos");
        itemContacto.addActionListener(e -> {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Sistema de Reserva de Vuelos\nContacto: diego@ucuenca.edu.ec",
                    "Información de Contacto",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE);
        });

        menuHelp.add(itemContacto);
        menuBar.add(menuSubsistemas);
        menuBar.add(menuHelp);

        this.setJMenuBar(menuBar);
    }
}
