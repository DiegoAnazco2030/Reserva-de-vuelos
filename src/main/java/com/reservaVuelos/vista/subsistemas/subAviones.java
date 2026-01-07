package com.reservaVuelos.vista.subsistemas;

import com.reservaVuelos.controlador.IControlador;
import com.reservaVuelos.modelo.vuelo.ModeloAvion;
import com.reservaVuelos.servicio.DTOs.DTOsSalida.SalidaAvionDTO;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class subAviones extends JDialog {

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

    public subAviones(JFrame parent, IControlador controlador) {

        super(parent, "Registro de avion", true);
        this.controlador = controlador;
        setContentPane(JPanelAvion);
        setTitle("Registro de avion");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        actualizarTablaAviones("");

        DefaultComboBoxModel<ModeloAvion> modelModeloAvion = new DefaultComboBoxModel<>(ModeloAvion.values());
        modeloAvion.setModel(modelModeloAvion);

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
                        mensajeSistema.setText("No se puede modificar un Avion");
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

        aplicarEstiloCorporativo();
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

    private void aplicarEstiloCorporativo() {

        // Agrego la paleta de colores (Mismos que PaginaPrincipal)
        Color azulMarino = Color.decode("#3270BF");
        Color celesteClaro = Color.decode("#33A1DE");
        Color blanco = Color.WHITE;

        // Configuracion del boton principal (Registrar)
        if (registrarModificarAvion != null) {
            registrarModificarAvion.setBackground(azulMarino);
            registrarModificarAvion.setForeground(blanco);
            registrarModificarAvion.setFont(new Font("Segoe UI", Font.BOLD, 14));
            registrarModificarAvion.setCursor(new Cursor(Cursor.HAND_CURSOR));
            registrarModificarAvion.putClientProperty("JButton.buttonType", "roundRect");
        }

        // Configuracion del boton eliminar
        if (eliminarAvion != null) {
            eliminarAvion.setForeground(Color.RED);
            eliminarAvion.putClientProperty("JButton.borderColor", Color.RED);
            eliminarAvion.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        // Configuracion tabla de aviones
        if (tablaAviones != null) {
            // Estilo de la cabecera
            tablaAviones.getTableHeader().setBackground(azulMarino);
            tablaAviones.getTableHeader().setForeground(blanco);
            tablaAviones.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
            // Altura de filas
            tablaAviones.setRowHeight(25);
            // Color de selección
            tablaAviones.setSelectionBackground(celesteClaro);
            tablaAviones.setSelectionForeground(blanco);
            // Quitar líneas verticales
            tablaAviones.setShowVerticalLines(false);
        }

        // Barra de busqueda
        if (textFiBusquedaAvion != null) {
            // Borde celeste cuando pasas el mouse
            textFiBusquedaAvion.putClientProperty("JComponent.outline", celesteClaro);
            textFiBusquedaAvion.putClientProperty("JTextField.placeholderText", "🔍 Buscar avion...");
            textFiBusquedaAvion.putClientProperty("JTextField.showClearButton", true);
        }
    }
}
