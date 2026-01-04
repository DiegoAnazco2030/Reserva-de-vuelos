package com.reservaVuelos.servicio.servicioImp;

import com.reservaVuelos.Excepciones.Excepcion.EntidadNoEncontradaException;
import com.reservaVuelos.Excepciones.Excepcion.OperacionFallidaException;
import com.reservaVuelos.modelo.Reserva;
import com.reservaVuelos.modelo.persona.Usuario;
import com.reservaVuelos.modelo.vuelo.Asiento;
import com.reservaVuelos.modelo.vuelo.Vuelo;
import com.reservaVuelos.repositorio.IRepositorio;
import com.reservaVuelos.servicio.DTOs.DTOsCrear.CrearReservaDTO;
import com.reservaVuelos.servicio.DTOs.DTOsSalida.SalidaReservaDTO;
import com.reservaVuelos.servicio.IServicio;
import com.reservaVuelos.servicio.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImpServicioReserva implements IServicio<CrearReservaDTO, SalidaReservaDTO> {
    private final IRepositorio<Reserva> repoReserva;
    private final IRepositorio<Vuelo> repoVuelo;
    private final IRepositorio<Usuario> repoUsuario;
    private final IRepositorio<Asiento> repoAsiento;
    private final Mapper mapper;

    public ImpServicioReserva(IRepositorio<Reserva> repoReserva, IRepositorio<Vuelo> repoVuelo, IRepositorio<Usuario> repoUsuario, IRepositorio<Asiento> repoAsiento, Mapper mapper) {
        this.repoReserva = repoReserva;
        this.repoVuelo = repoVuelo;
        this.repoUsuario = repoUsuario;
        this.repoAsiento = repoAsiento;
        this.mapper = mapper;
    }

    @Override
    public void crear(CrearReservaDTO crearReserva) throws Exception {
        validarExistenciaComponentes(crearReserva.idVuelo(), crearReserva.idUsuario());
        if (repoAsiento.buscarPorID(crearReserva.idAsiento()) == null) {
            throw new EntidadNoEncontradaException("Asiento no encontrado");
        }
        if (repoAsiento.buscarPorID(crearReserva.idAsiento()).isEstadoAsiento()) {
            throw new OperacionFallidaException("El asiento ya esta reservado");
        }

        Long id = repoReserva.ultimoID() + 1;
        Reserva nuevaReserva = mapper.ReservaDTOAReserva(id,crearReserva);
        Asiento asientoReservado = repoAsiento.buscarPorID(crearReserva.idAsiento());
        asientoReservado.setEstadoAsiento(true);
        repoReserva.guardar(nuevaReserva);
        repoAsiento.actualizar(asientoReservado);
    }

    @Override
    public List<SalidaReservaDTO> obtenerTodos() {
        return repoReserva.buscarTodos().stream()
                .map(this::mapearASalida)
                .toList();
    }

    @Override
    public List<SalidaReservaDTO> obtenerListaReducida(String palabraBuscar) {
        return repoReserva.buscar(r -> String.valueOf(r.getIdAsiento()).contains(palabraBuscar))
                .stream()
                .map(this::mapearASalida)
                .toList();
    }

    @Override
    public void eliminar(Long id) throws Exception {
        if (!repoReserva.existe(id)) {
            throw new EntidadNoEncontradaException("La reserva no existe");
        }
        Reserva reservaAEliminar = repoReserva.buscarPorID(id);
        Asiento asientoLiberado = repoAsiento.buscarPorID(reservaAEliminar.getIdAsiento());
        asientoLiberado.setEstadoAsiento(false);
        repoReserva.eliminar(id);
        repoAsiento.actualizar(asientoLiberado);
    }

    @Override
    public void modificar(Long id, CrearReservaDTO objeto) throws Exception {
        if (!repoReserva.existe(id)) {
            throw new EntidadNoEncontradaException("Reserva no encontrada");
        }
        validarExistenciaComponentes(objeto.idVuelo(), objeto.idUsuario());

        Reserva actualizada = mapper.ReservaDTOAReserva(id,objeto);
        Asiento asientoAnterior = repoAsiento.buscarPorID(repoReserva.buscarPorID(id).getIdAsiento());
        Asiento asientoNuevo = repoAsiento.buscarPorID(objeto.idAsiento());
        
        asientoNuevo.setEstadoAsiento(true);
        asientoAnterior.setEstadoAsiento(false);

        repoReserva.actualizar(actualizada);
        repoAsiento.actualizar(asientoAnterior);
        repoAsiento.actualizar(asientoNuevo);
    }

    private void validarExistenciaComponentes(Long idVuelo, Long idUsuario) throws Exception {
        if (repoVuelo.buscarPorID(idVuelo) == null) {
            throw new EntidadNoEncontradaException("Vuelo no encontrado");
        }
        if (repoUsuario.buscarPorID(idUsuario) == null) {
            throw new EntidadNoEncontradaException("Usuario no encontrado");
        }
    }

    private SalidaReservaDTO mapearASalida(Reserva reserva) {
        Vuelo vuelo = repoVuelo.buscarPorID(reserva.getIdVuelo());
        Usuario usuario = repoUsuario.buscarPorID(reserva.getIdUsuario());
        return mapper.ReservaAReservaDTO(reserva.getId(), reserva, vuelo, usuario);
    }
}
