package com.reservaVuelos.servicio.servicioImp;

import com.reservaVuelos.Excepciones.Excepcion.EntidadNoEncontradaException;
import com.reservaVuelos.modelo.Reserva;
import com.reservaVuelos.modelo.persona.Usuario;
import com.reservaVuelos.modelo.vuelo.Vuelo;
import com.reservaVuelos.repositorio.IRepositorio;
import com.reservaVuelos.servicio.DTOs.DTOsCrear.CrearReservaDTO;
import com.reservaVuelos.servicio.DTOs.DTOsSalida.SalidaReservaDTO;
import com.reservaVuelos.servicio.Mapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ImpServicioReservaTest {

    @Mock
    private IRepositorio<Reserva> repoReserva;

    @Mock
    private IRepositorio<Vuelo> repoVuelo;

    @Mock
    private IRepositorio<Usuario> repoUsuario;

    @Mock
    private Mapper mapper;

    @InjectMocks
    private ImpServicioReserva servicio;

    @Test
    void CreacionCorrecta() throws Exception {
        CrearReservaDTO dto = new CrearReservaDTO(1L, 10L, 2L);

        // Simular que el vuelo y el usuario existen
        when(repoVuelo.buscarPorID(1L)).thenReturn(mock(Vuelo.class));
        when(repoUsuario.buscarPorID(2L)).thenReturn(mock(Usuario.class));
        when(repoReserva.ultimoID()).thenReturn(100L);

        Reserva reservaSimulada = new Reserva(101L, 1L, 10L, 2L);
        when(mapper.ReservaDTOAReserva(anyLong(), any())).thenReturn(reservaSimulada);

        servicio.crear(dto);

        verify(repoReserva, times(1)).guardar(any(Reserva.class));
    }

    @Test
    void obtenerTodos() {
        Reserva reserva = new Reserva(1L, 10L, 5L, 20L);
        when(repoReserva.buscarTodos()).thenReturn(List.of(reserva));

        // Mocks necesarios para el método privado mapearASalida
        when(repoVuelo.buscarPorID(10L)).thenReturn(mock(Vuelo.class));
        when(repoUsuario.buscarPorID(20L)).thenReturn(mock(Usuario.class));
        when(mapper.ReservaAReservaDTO(any(), any(), any())).thenReturn(mock(SalidaReservaDTO.class));

        List<SalidaReservaDTO> resultado = servicio.obtenerTodos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(repoReserva, times(1)).buscarTodos();
    }

    @Test
    void idNoEncontradoAleliminar() throws Exception {
        Long id = 1L;
        when(repoReserva.existe(id)).thenReturn(false);

        assertThrows(EntidadNoEncontradaException.class, () -> {
            servicio.eliminar(id);
        });

        verify(repoReserva, never()).eliminar(id);
    }

    @Test
    void idEncontradoAleliminar() throws Exception {
        Long id = 1L;
        when(repoReserva.existe(id)).thenReturn(true);

        assertDoesNotThrow(() -> {
            servicio.eliminar(id);
        });

        verify(repoReserva, times(1)).eliminar(id);
    }

    @Test
    void idNoEncontradoParaModificar() throws Exception {
        Long id = 1L;
        when(repoReserva.existe(id)).thenReturn(false);

        assertThrows(EntidadNoEncontradaException.class, () -> {
            servicio.modificar(id, mock(CrearReservaDTO.class));
        });

        verify(repoReserva, never()).actualizar(any());
    }

    @Test
    void idEncontradoParaModificar() throws Exception {
        Long id = 1L;
        CrearReservaDTO dto = new CrearReservaDTO(10L, 5L, 20L);

        when(repoReserva.existe(id)).thenReturn(true);
        when(repoVuelo.buscarPorID(10L)).thenReturn(mock(Vuelo.class));
        when(repoUsuario.buscarPorID(20L)).thenReturn(mock(Usuario.class));

        assertDoesNotThrow(() -> {
            servicio.modificar(id, dto);
        });

        verify(repoReserva, times(1)).actualizar(any());
    }

    @Test
    void obtenerListaReducida() {
        String palabra = "20"; // idUsuario
        Reserva reserva = new Reserva(1L, 10L, 5L, 20L);

        when(repoReserva.buscar(any())).thenReturn(List.of(reserva));
        when(repoVuelo.buscarPorID(any())).thenReturn(mock(Vuelo.class));
        when(repoUsuario.buscarPorID(any())).thenReturn(mock(Usuario.class));

        List<SalidaReservaDTO> resultado = servicio.obtenerListaReducida(palabra);

        assertNotNull(resultado);
        verify(repoReserva, times(1)).buscar(any());
    }
}