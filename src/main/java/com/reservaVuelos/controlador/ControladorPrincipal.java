package com.reservaVuelos.controlador;

import com.reservaVuelos.modelo.vuelo.Ciudad;
import com.reservaVuelos.modelo.vuelo.ModeloAvion;
import com.reservaVuelos.servicio.DTOs.DTOsCrear.*;
import com.reservaVuelos.servicio.DTOs.DTOsSalida.*;
import com.reservaVuelos.servicio.IServicio;

import java.util.List;

public class ControladorPrincipal implements IControlador {
    private final IServicio<CrearEmpleadoDTO, SalidaEmpleadoDTO> servicioEmpleado;
    private final IServicio<CrearAereolineaDTO, SalidaAerolineaDTO> servicioAerolinea;
    private final IServicio<CrearAvionDTO, SalidaAvionDTO> servicioAvion;
    private final IServicio<CrearReservaDTO, SalidaReservaDTO> servicioReserva;
    private final IServicio<CrearUsuarioDTO, SalidaUsuarioDTO> servicioUsuario;
    private final IServicio<CrearVueloDTO, SalidaVueloDTO> servicioVuelo;


    public ControladorPrincipal(IServicio<CrearEmpleadoDTO, SalidaEmpleadoDTO> servicioEmpleado, IServicio<CrearAereolineaDTO, SalidaAerolineaDTO> servicioAerolinea, IServicio<CrearAvionDTO, SalidaAvionDTO> servicioAvion, IServicio<CrearReservaDTO, SalidaReservaDTO> servicioReserva, IServicio<CrearUsuarioDTO, SalidaUsuarioDTO> servicioUsuario, IServicio<CrearVueloDTO, SalidaVueloDTO> servicioVuelo) {
        this.servicioEmpleado = servicioEmpleado;
        this.servicioAerolinea = servicioAerolinea;
        this.servicioAvion = servicioAvion;
        this.servicioReserva = servicioReserva;
        this.servicioUsuario = servicioUsuario;
        this.servicioVuelo = servicioVuelo;
    }

    //Aerolinea
    @Override
    public void crearAerolinea(String nombre, String telefono, String email) throws Exception {
        CrearAereolineaDTO aerolinea= new CrearAereolineaDTO(nombre, telefono, email);
        servicioAerolinea.crear(aerolinea);
    }

    @Override
    public List<SalidaAerolineaDTO> buscarAerolineas(String palabraBuscar) {
        return servicioAerolinea.obtenerListaReducida(palabraBuscar);
    }

    @Override
    public void eliminarAerolinea(Long id) throws Exception {
        servicioAerolinea.eliminar(id);
    }

    @Override
    public void modificarAerolinea(Long id, String nombre, String telefono, String email) throws Exception {
        CrearAereolineaDTO aerolinea =
        servicioAerolinea.modificar();
    }

    @Override
    public void crearAvion(ModeloAvion modeloAvion) throws Exception {

    }

    @Override
    public List<SalidaAvionDTO> listarAviones() {
        return List.of();
    }

    @Override
    public void eliminarAvion(Long id) throws Exception {

    }

    @Override
    public void modificarAvion(Long id, ModeloAvion modeloAvion) throws Exception {

    }

    @Override
    public SalidaAvionDTO obtenerAvionPorId(Long id) throws Exception {
        return null;
    }

    @Override
    public void crearEmpleado(String Nombre, String Apellido, String telefono, int edad, String email, String contrasenia) throws Exception {

    }

    @Override
    public List<SalidaEmpleadoDTO> buscarEmpleados(String palabraBuscar) {
        return List.of();
    }

    @Override
    public void eliminarEmpleado(Long id) throws Exception {

    }

    @Override
    public void modificarEmpleado(Long id, String nombre, String apellido, String telefono, int edad, String empleadoEmail) throws Exception {

    }

    @Override
    public void crearUsuario(String Nombre, String Apellido, String telefono, int edad, String email, String contrasenia, String pasaporte) throws Exception {

    }

    @Override
    public List<SalidaUsuarioDTO> buscarUsuarios(String palabraBuscar) {
        return List.of();
    }

    @Override
    public void eliminarUsuario(Long id) throws Exception {

    }

    @Override
    public void modificarUsuario(Long id, String nombre, String apellido, String telefono, int edad, String empleadoEmail) throws Exception {

    }

    @Override
    public void crearVuelo(Long aerolineaID, Ciudad origenVuelo, Ciudad destinoVuelo, String fechaHoraSalida, String fechaHoraLlegada, String idAvion) throws Exception {

    }

    @Override
    public List<SalidaVueloDTO> buscarVuelos(String palabraBuscar) {
        return List.of();
    }

    @Override
    public void eliminarVuelo(Long id) throws Exception {

    }

    @Override
    public void modificarVuelo(Long id, Long aerolineaID, Ciudad origenVuelo, Ciudad destinoVuelo, String fechaHoraSalida, String fechaHoraLlegada, Long idAvion) throws Exception {

    }

    @Override
    public void crearReserva(Long idVuelo, Long idAsiento, Long idUsuario) throws Exception {

    }

    @Override
    public List<SalidaReservaDTO> buscarReservas(String palabraBuscar) {
        return List.of();
    }

    @Override
    public void eliminarReserva(Long id) throws Exception {

    }

    @Override
    public void modificarReserva(Long id, Long idVuelo, Long idAsiento, Long idUsuario) throws Exception {

    }
}
