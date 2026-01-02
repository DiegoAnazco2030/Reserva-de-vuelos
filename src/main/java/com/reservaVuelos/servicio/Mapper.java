package com.reservaVuelos.servicio;

import com.reservaVuelos.modelo.persona.Empleado;
import com.reservaVuelos.modelo.persona.Usuario;
import com.reservaVuelos.modelo.vuelo.Asiento;
import com.reservaVuelos.modelo.vuelo.Avion;
import com.reservaVuelos.modelo.vuelo.Vuelo;
import com.reservaVuelos.servicio.DTOs.DTOsCrear.*;
import com.reservaVuelos.servicio.DTOs.DTOsSalida.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Mapper {

    // Formateador para convertir de String a LocalDateTime y viceversa
    private static final DateTimeFormatter formateador = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    // --- USUARIO ---

    // 1. Crear Usuario (Entrada)
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

    // 2. Salida Usuario (Salida)
    public  SalidaUsuarioDTO UsuarioASalidaUsuarioDTO(Usuario usuario) {
        return new SalidaUsuarioDTO(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getApellido(), // Se mapea al campo "Apelliido" del Record
                usuario.getUsuarioEmail()
        );
    }

    // --- EMPLEADO ---

    // 3. Crear Empleado (Entrada)
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

    // 4. Salida Empleado (Salida)
    public  SalidaEmpleadoDTO EmpleadoASalidaEmpleadoDTO(Empleado empleado) {
        return new SalidaEmpleadoDTO(
                empleado.getEmpleadoEmail(),
                empleado.getNombre(),
                empleado.getApellido(),
                empleado.getId()
        );
    }

    // --- AVION ---

    // 5. Crear Avion (Entrada)
    public  Avion AvionDTOAAvion(CrearAvionDTO dto, Long idAvion) {
        return new Avion(idAvion, dto.modeloAvion());
    }

    // --- ASIENTO ---

    // 6. Salida Asiento (Salida)
    public  SalidaAsientoDTO AsientoASalidaAsientoDTO(Asiento asiento) {
        return new SalidaAsientoDTO(
                asiento.getId(),
                asiento.isEstadoAsiento(),
                asiento.getTipoAsiento()
        );
    }

    // 7. Modificar Asiento (Entrada/Actualización)
    public  void actualizarEstadoAsiento(Asiento asiento, ModificarAsientoDTO dto) {
        asiento.setEstadoAsiento(dto.estadoAsiento());
    }

    // --- VUELO ---

    // 8. Crear Vuelo (Entrada: LocalDateTime viene del DTO)
    public  Vuelo VuealoDTOAVuelo(CrearVueloDTO dto, Long vueloID, Avion avion) {
        return new Vuelo(
                vueloID,
                dto.aerolinea(),
                dto.origenVuelo(),
                dto.destinoVuelo(),
                dto.fechaHoraSalida(),
                dto.fechaHoraLlegada(),
                false,
                avion
        );
    }

    // 9. Salida Vuelo (Salida: Convierte LocalDateTime a String)
    public  SalidaVueloDTO VueloASalidaVueloDTO(Vuelo vuelo) {
        return new SalidaVueloDTO(
                vuelo.getOrigenVuelo(),
                vuelo.getDestinoVuelo(),
                vuelo.getFechaHoraSalida().format(formateador), // LocalDateTime a String
                vuelo.getFechaHoraLlegada().format(formateador), // LocalDateTime a String
                String.valueOf(vuelo.isEstadoVuelo()),
                vuelo.getAvionVuelo().getModeloAvion()
        );
    }

    // --- RESERVA ---

    // 10. Procesar entrada de Reserva (ID Vuelo)
    public  Long obtenerVueloIdDeReserva(CrearReservaDTO dto) {
        return dto.idVuelo();
    }

    // 11. Procesar entrada de Reserva (ID Usuario)
    public  Long obtenerUsuarioIdDeReserva(CrearReservaDTO dto) {
        return dto.idUsuario();
    }

    // 12. Salida Reserva (Transformación final)
    public SalidaReservaDTO mapearASalidaReservaDTO(Long idReserva, Vuelo vuelo, Usuario usuario) {
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
}