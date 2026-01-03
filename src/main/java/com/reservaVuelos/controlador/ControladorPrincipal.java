package com.reservaVuelos.controlador;

import com.reservaVuelos.modelo.vuelo.Ciudad;
import com.reservaVuelos.modelo.vuelo.ModeloAvion;
import com.reservaVuelos.servicio.DTOs.DTOsCrear.*;
import com.reservaVuelos.servicio.DTOs.DTOsSalida.*;
import com.reservaVuelos.servicio.IServicio;
import com.reservaVuelos.servicio.servicioImp.ImpServicioAvion;

import java.util.List;

public class ControladorPrincipal implements IControlador {
    private final IServicio<CrearEmpleadoDTO, SalidaEmpleadoDTO> servicioEmpleado;
    private final IServicio<CrearAereolineaDTO, SalidaAerolineaDTO> servicioAerolinea;
    private final ImpServicioAvion servicioAvion;
    private final IServicio<CrearReservaDTO, SalidaReservaDTO> servicioReserva;
    private final IServicio<CrearUsuarioDTO, SalidaUsuarioDTO> servicioUsuario;
    private final IServicio<CrearVueloDTO, SalidaVueloDTO> servicioVuelo;

    public ControladorPrincipal(IServicio<CrearEmpleadoDTO, SalidaEmpleadoDTO> servicioEmpleado,
                                IServicio<CrearAereolineaDTO, SalidaAerolineaDTO> servicioAerolinea,
                                ImpServicioAvion servicioAvion,
                                IServicio<CrearReservaDTO, SalidaReservaDTO> servicioReserva,
                                IServicio<CrearUsuarioDTO, SalidaUsuarioDTO> servicioUsuario,
                                IServicio<CrearVueloDTO, SalidaVueloDTO> servicioVuelo) {
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
        CrearAereolineaDTO aerolinea = new CrearAereolineaDTO(nombre, telefono, email);
        servicioAerolinea.modificar(id, aerolinea);
    }

    //Avion
    @Override
    public void crearAvion(ModeloAvion modeloAvion) throws Exception {
        CrearAvionDTO avion = new CrearAvionDTO(modeloAvion);
        servicioAvion.crear(avion);
    }

    @Override
    public List<SalidaAvionDTO> buscarAviones(String palabraBuscar) {
        return servicioAvion.obtenerListaReducida(palabraBuscar);
    }

    @Override
    public void eliminarAvion(Long id) throws Exception {
        servicioAvion.eliminar(id);
    }

    @Override
    public void modificarAvion(Long id, ModeloAvion modeloAvion) throws Exception {
        CrearAvionDTO avionDTO = new CrearAvionDTO(modeloAvion);
        servicioAvion.modificar(id, avionDTO);
    }

    @Override
    public SalidaAvionDTO obtenerAvionPorId(Long id) throws Exception {
        return servicioAvion.obtenerAvionPorID(id);
    }

    //Empleado
    @Override
    public void crearEmpleado(String nombre, String apellido, String telefono, int edad,
                              String email, String contrasenia) throws Exception {
        CrearEmpleadoDTO empleadoDTO= new CrearEmpleadoDTO(nombre, apellido,
                telefono,edad,email,contrasenia);
        servicioEmpleado.crear(empleadoDTO);
    }

    @Override
    public List<SalidaEmpleadoDTO> buscarEmpleados(String palabraBuscar) {
        return servicioEmpleado.obtenerListaReducida(palabraBuscar);
    }

    @Override
    public void eliminarEmpleado(Long id) throws Exception {
        servicioEmpleado.eliminar(id);

    }

    @Override
    public void modificarEmpleado(Long id, String nombre, String apellido, String telefono,
                                  int edad, String empleadoEmail) throws Exception {
        CrearEmpleadoDTO empleadoModificar = new CrearEmpleadoDTO(nombre,
                apellido, telefono, edad, empleadoEmail, " ");
    }

    //Usuario
    @Override
    public void crearUsuario(String nombre, String apellido, String telefono, int edad,
                             String email, String contrasenia, String pasaporte) throws Exception {
        CrearUsuarioDTO usuarioDTO = new CrearUsuarioDTO(nombre, apellido,
                telefono, edad, email, contrasenia, pasaporte);
        servicioUsuario.crear(usuarioDTO);
    }

    @Override
    public List<SalidaUsuarioDTO> buscarUsuarios(String palabraBuscar) {
        return servicioUsuario.obtenerListaReducida(palabraBuscar);
    }

    @Override
    public void eliminarUsuario(Long id) throws Exception {
        servicioUsuario.eliminar(id);
    }

    @Override
    public void modificarUsuario(Long id, String nombre, String apellido, String telefono,
                                 int edad, String empleadoEmail) throws Exception {
        CrearUsuarioDTO usuarioModificar= new CrearUsuarioDTO(nombre, apellido,
                telefono, edad, empleadoEmail, " ", " " );
    }

    //Vuelo
    @Override
    public void crearVuelo(Ciudad origenVuelo, Ciudad destinoVuelo, String fechaHoraSalida,
                           String fechaHoraLlegada, Long idAvion, Long idAerolinea) throws Exception {
        CrearVueloDTO vueloDTO = new CrearVueloDTO();
    }

    @Override
    public List<SalidaVueloDTO> buscarVuelos(String palabraBuscar) {
        return servicioVuelo.obtenerListaReducida(palabraBuscar);
    }

    @Override
    public void eliminarVuelo(Long id) throws Exception {
        servicioVuelo.eliminar(id);
    }

    @Override
    public void modificarVuelo(Long id, Long idAerolinea, Ciudad origenVuelo, Ciudad destinoVuelo,
                               String fechaHoraSalida, String fechaHoraLlegada, Long idAvion) throws Exception {
        CrearVueloDTO dto = new CrearVueloDTO(idAerolinea, origenVuelo, destinoVuelo, fechaHoraSalida, fechaHoraLlegada, idAvion);
    }

    @Override
    public void crearReserva(Long idVuelo, Long idAsiento, Long idUsuario) throws Exception {
        CrearReservaDTO dto = new CrearReservaDTO(idVuelo, idAsiento, idUsuario);
        servicioReserva.crear(dto);
    }

    @Override
    public List<SalidaReservaDTO> buscarReservas(String palabraBuscar) {
        return servicioReserva.obtenerListaReducida(palabraBuscar);
    }

    @Override
    public void eliminarReserva(Long id) throws Exception {
        servicioReserva.eliminar(id);
    }

    @Override
    public void modificarReserva(Long id, Long idVuelo, Long idAsiento, Long idUsuario) throws Exception {
        CrearReservaDTO dto = new CrearReservaDTO(idVuelo, idAsiento, idUsuario);
        servicioReserva.modificar(id, dto);
    }
}
