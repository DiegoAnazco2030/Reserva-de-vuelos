package com.reservaVuelos.controlador;

import com.reservaVuelos.servicio.DTOs.DTOsCrear.CrearAereolineaDTO;
import com.reservaVuelos.servicio.DTOs.DTOsCrear.CrearAvionDTO;
import com.reservaVuelos.servicio.DTOs.DTOsCrear.CrearEmpleadoDTO;
import com.reservaVuelos.servicio.DTOs.DTOsCrear.CrearReservaDTO;
import com.reservaVuelos.servicio.DTOs.DTOsCrear.CrearUsuarioDTO;
import com.reservaVuelos.servicio.DTOs.DTOsCrear.CrearVueloDTO;
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
	void crearAerolinea(CrearAereolineaDTO dto) throws Exception;
	List<SalidaAerolineaDTO> listarAerolineas();
	List<SalidaAerolineaDTO> buscarAerolineas(String palabraBuscar);
	void eliminarAerolinea(Long id) throws Exception;
	void modificarAerolinea(Long id, CrearAereolineaDTO dto) throws Exception;

	// Aviones
	void crearAvion(CrearAvionDTO dto) throws Exception;
	List<SalidaAvionDTO> listarAviones();
	List<SalidaAvionDTO> buscarAviones(String palabraBuscar);
	void eliminarAvion(Long id) throws Exception;
	void modificarAvion(Long id, CrearAvionDTO dto) throws Exception;
	SalidaAvionDTO obtenerAvionPorId(Long id) throws Exception;

	// Empleados
	void crearEmpleado(CrearEmpleadoDTO dto) throws Exception;
	List<SalidaEmpleadoDTO> listarEmpleados();
	List<SalidaEmpleadoDTO> buscarEmpleados(String palabraBuscar);
	void eliminarEmpleado(Long id) throws Exception;
	void modificarEmpleado(Long id, CrearEmpleadoDTO dto) throws Exception;

	// Usuarios
	void crearUsuario(CrearUsuarioDTO dto) throws Exception;
	List<SalidaUsuarioDTO> listarUsuarios();
	List<SalidaUsuarioDTO> buscarUsuarios(String palabraBuscar);
	void eliminarUsuario(Long id) throws Exception;
	void modificarUsuario(Long id, CrearUsuarioDTO dto) throws Exception;

	// Vuelos
	void crearVuelo(CrearVueloDTO dto) throws Exception;
	List<SalidaVueloDTO> listarVuelos();
	List<SalidaVueloDTO> buscarVuelos(String palabraBuscar);
	void eliminarVuelo(Long id) throws Exception;
	void modificarVuelo(Long id, CrearVueloDTO dto) throws Exception;

	// Reservas
	void crearReserva(CrearReservaDTO dto) throws Exception;
	List<SalidaReservaDTO> listarReservas();
	List<SalidaReservaDTO> buscarReservas(String palabraBuscar);
	void eliminarReserva(Long id) throws Exception;
	void modificarReserva(Long id, CrearReservaDTO dto) throws Exception;
}
