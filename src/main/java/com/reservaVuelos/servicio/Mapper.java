package com.reservaVuelos.servicio;

import com.reservaVuelos.modelo.Reserva;
import com.reservaVuelos.modelo.persona.Empleado;
import com.reservaVuelos.modelo.persona.Usuario;
import com.reservaVuelos.modelo.vuelo.Aerolinea;
import com.reservaVuelos.modelo.vuelo.Asiento;
import com.reservaVuelos.modelo.vuelo.Avion;
import com.reservaVuelos.modelo.vuelo.Vuelo;
import com.reservaVuelos.servicio.DTOs.DTOsCrear.*;
import com.reservaVuelos.servicio.DTOs.DTOsSalida.*;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class Mapper {

    // Formateador para convertir de String a LocalDateTime y viceversa
    private static final DateTimeFormatter formateador = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSSSSS");

    // --- USUARIO ---

    // Crear Usuario (Entrada)
    public Usuario UsuarioDTOAUsuario(CrearUsuarioDTO dto, Long usuarioID) {
        return new Usuario(
                dto.nombre(),
                dto.apellido(),
                dto.telefono(),
                dto.edad(),
                dto.usuarioPassaporteID(),
                dto.usuarioContrasenia(),
                dto.usuarioEmail(),
                usuarioID
        );
    }

    // Salida Usuario (Salida)
    public SalidaUsuarioDTO UsuarioASalidaUsuarioDTO(Usuario usuario) {
        return new SalidaUsuarioDTO(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getUsuarioEmail()
        );
    }

    // --- EMPLEADO ---

    // Crear Empleado (Entrada)
    public Empleado EmpleadoDTOAEmpleado(CrearEmpleadoDTO dto, Long empleadoID) {
        return new Empleado(
                dto.nombre(),
                dto.apellido(),
                dto.telefono(),
                dto.edad(),
                empleadoID,
                dto.empleadoContrasenia(),
                dto.empleadoEmail()
        );
    }

    // Salida Empleado (Salida)
    public SalidaEmpleadoDTO EmpleadoASalidaEmpleadoDTO(Empleado empleado) {
        return new SalidaEmpleadoDTO(
                empleado.getEmpleadoEmail(),
                empleado.getNombre(),
                empleado.getApellido(),
                empleado.getId()
        );
    }

    // --- AVION ---

    // Crear Avion (Entrada)
    public Avion AvionDTOAAvion(CrearAvionDTO dto, Long idAvion) {
        return new Avion(idAvion, dto.modeloAvion());
    }

    // Salida Avion (Salida)
    public SalidaAvionDTO AvionASalidaAvionDTO(Avion avion) {
        return new SalidaAvionDTO(
                avion.getModeloAvion(),
                avion.getId()
        );
    }
    // --- ASIENTO ---

    // Modificar Asiento (Entrada/Actualización)
    public void actualizarEstadoAsiento(Asiento asiento, ModificarAsientoDTO dto) {
        asiento.setEstadoAsiento(dto.estadoAsiento());
    }

    // Salida Asiento (Salida)
    public SalidaAsientoDTO AsientoASalidaAsientoDTO(Asiento asiento) {
        return new SalidaAsientoDTO(
                asiento.getId(),
                asiento.isEstadoAsiento(),
                asiento.getTipoAsiento()
        );
    }

    // --- VUELO ---

    // Crear Vuelo (Entrada)
    public Vuelo VuealoDTOAVuelo(CrearVueloDTO dto, Long vueloID, Aerolinea aerolinea, Avion avion) {
        return new Vuelo(
                vueloID,
                aerolinea,
                dto.origenVuelo(),
                dto.destinoVuelo(),
                dto.fechaHoraSalida(),
                dto.fechaHoraLlegada(),
                false,
                avion
        );
    }

    // Salida Vuelo (Salida)
    public SalidaVueloDTO VueloASalidaVueloDTO(Vuelo vuelo) {
        return new SalidaVueloDTO(
                vuelo.getId(),
                vuelo.getOrigenVuelo(),
                vuelo.getDestinoVuelo(),
                vuelo.getFechaHoraSalida().format(formateador), // LocalDateTime a String
                vuelo.getFechaHoraLlegada().format(formateador), // LocalDateTime a String
                String.valueOf(vuelo.isEstadoVuelo()),
                vuelo.getAvionVuelo().getModeloAvion(),
                vuelo.getAvionVuelo().getId()
        );
    }

    // --- RESERVA ---

    // Procesar entrada de Reserva (ID Vuelo)
    public Long obtenerVueloIdDeReserva(CrearReservaDTO dto) {
        return dto.idVuelo();
    }

    // Procesar entrada de Reserva (ID Usuario)
    public Long obtenerUsuarioIdDeReserva(CrearReservaDTO dto) {
        return dto.idUsuario();
    }

    // Entrada Reserva (Entrada)
    public Reserva ReservaDTOAReserva(Long id,CrearReservaDTO dto) {
        return new Reserva(
            id,
            dto.idVuelo(),
            dto.idAsiento(),
            dto.idUsuario());
    }

    // Salida Reserva (Salida)
    public SalidaReservaDTO ReservaAReservaDTO(Long idReserva, Vuelo vuelo, Usuario usuario) {
        return new SalidaReservaDTO(
                idReserva,
                vuelo.getId(),
                vuelo.getAvionVuelo().getModeloAvion(),
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getUsuarioEmail(),
                vuelo.getOrigenVuelo(),
                vuelo.getDestinoVuelo()
        );
    }

    // --- AEROLINEA ---

    public Aerolinea AerolineaDTOAAerolinea(CrearAereolineaDTO dto, Long id) {
        return new Aerolinea(
                id,
                dto.nombre(),
                dto.telefono(),
                dto.email()
        );
    }

    public SalidaAerolineaDTO AerolineaASalidaAerolineaDTO(Aerolinea aerolinea) {
        return new SalidaAerolineaDTO(
                aerolinea.getId(),
                aerolinea.getNombre(),
                aerolinea.getTelefono(),
                aerolinea.getEmail()
        );
    }
}