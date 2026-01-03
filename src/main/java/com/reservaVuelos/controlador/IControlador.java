package com.reservaVuelos.controlador;

import com.reservaVuelos.modelo.vuelo.Ciudad;
import com.reservaVuelos.modelo.vuelo.ModeloAvion;
import com.reservaVuelos.servicio.DTOs.DTOsSalida.SalidaAerolineaDTO;
import com.reservaVuelos.servicio.DTOs.DTOsSalida.SalidaAvionDTO;
import com.reservaVuelos.servicio.DTOs.DTOsSalida.SalidaEmpleadoDTO;
import com.reservaVuelos.servicio.DTOs.DTOsSalida.SalidaReservaDTO;
import com.reservaVuelos.servicio.DTOs.DTOsSalida.SalidaUsuarioDTO;
import com.reservaVuelos.servicio.DTOs.DTOsSalida.SalidaVueloDTO;

import java.util.List;

/**
 * Fachada entre la capa de vista y los servicios de dominio.
 * Expone las operaciones necesarias para que la UI no conozca detalles de cada servicio.
 */
public interface IControlador {

	// Aerolíneas
	void crearAerolinea(String nombre, String telefono, String email) throws Exception;
	List<SalidaAerolineaDTO> listarAerolineas();
	List<SalidaAerolineaDTO> buscarAerolineas(String palabraBuscar);
	void eliminarAerolinea(Long id) throws Exception;
	void modificarAerolinea(Long id, String nombre, String telefono, String email) throws Exception;

	// Aviones
	void crearAvion(ModeloAvion modeloAvion) throws Exception;
	List<SalidaAvionDTO> listarAviones();
	List<SalidaAvionDTO> buscarAviones(String palabraBuscar);
	void eliminarAvion(Long id) throws Exception;
	void modificarAvion(Long id, ModeloAvion modeloAvion) throws Exception;
	SalidaAvionDTO obtenerAvionPorId(Long id) throws Exception;

	// Empleados
	void crearEmpleado(String Nombre, String Apellido, String telefono, int edad,
                       String email, String contrasenia) throws Exception;
	List<SalidaEmpleadoDTO> listarEmpleados();
	List<SalidaEmpleadoDTO> buscarEmpleados(String palabraBuscar);
	void eliminarEmpleado(Long id) throws Exception;
	void modificarEmpleado(Long id, String nombre, String apellido,
                           String telefono, int edad, String empleadoEmail) throws Exception;

	// Usuarios
	void crearUsuario(String Nombre, String Apellido, String telefono, int edad,
                      String email, String contrasenia, String pasaporte) throws Exception;
	List<SalidaUsuarioDTO> listarUsuarios();
	List<SalidaUsuarioDTO> buscarUsuarios(String palabraBuscar);
	void eliminarUsuario(Long id) throws Exception;
	void modificarUsuario(Long id, String nombre, String apellido,
                          String telefono, int edad, String empleadoEmail) throws Exception;

	// Vuelos
    void crearVuelo(Long aerolineaID, Ciudad origenVuelo, Ciudad destinoVuelo,
                    String fechaHoraSalida, String fechaHoraLlegada, String idAvion) throws Exception;
    List<SalidaVueloDTO> listarVuelos();
    List<SalidaVueloDTO> buscarVuelos(String palabraBuscar);
    void eliminarVuelo(Long id) throws Exception;
    void modificarVuelo(Long id, Long aerolineaID, Ciudad origenVuelo,
                    Ciudad destinoVuelo, String fechaHoraSalida, String fechaHoraLlegada, Long idAvion) throws Exception;

	// Reservas
    void crearReserva(Long idVuelo, Long idAsiento, Long idUsuario) throws Exception;
    List<SalidaReservaDTO> listarReservas();
    List<SalidaReservaDTO> buscarReservas(String palabraBuscar);
    void eliminarReserva(Long id) throws Exception;
    void modificarReserva(Long id, Long idVuelo, Long idAsiento, Long idUsuario) throws Exception;
}
