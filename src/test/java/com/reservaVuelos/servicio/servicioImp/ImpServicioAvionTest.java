package com.reservaVuelos.servicio.servicioImp;

import com.reservaVuelos.Excepciones.Excepcion.EntidadNoEncontradaException;
import com.reservaVuelos.modelo.vuelo.Asiento;
import com.reservaVuelos.modelo.vuelo.Avion;
import com.reservaVuelos.modelo.vuelo.ModeloAvion;
import com.reservaVuelos.modelo.vuelo.TipoDeAsiento;
import com.reservaVuelos.repositorio.RepositorioAsientos;
import com.reservaVuelos.repositorio.RepositorioAviones;
import com.reservaVuelos.servicio.DTOs.DTOsCrear.CrearAvionDTO;
import com.reservaVuelos.servicio.DTOs.DTOsSalida.SalidaAvionDTO;
import com.reservaVuelos.servicio.Mapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ImpServicioAvionTest {

    @Mock
    private RepositorioAviones repo;

    @Mock
    private RepositorioAsientos repoAsiento;

    @Mock
    private Mapper mapperAvion;

    @InjectMocks
    private ImpServicioAvion servicio;

    @Test
    void objetoNuloAlCrear() throws Exception {
        CrearAvionDTO dto = null;

        assertThrows(Exception.class, () -> {
            servicio.crear(dto);
        });

        verify(repo, never()).guardar(any());
    }

    @Test
    void creacionCorrecta() throws Exception {
        // GIVEN
        CrearAvionDTO dto = new CrearAvionDTO(ModeloAvion.AIRBUS_A320);

        Avion entidadSimulada = new Avion(1L, ModeloAvion.AIRBUS_A320);

        when(repo.ultimoID()).thenReturn(0L);

        when(mapperAvion.AvionDTOAAvion(dto, 1L)).thenReturn(entidadSimulada);

        // El repo debe devolver la entidad para que (resultado == null) sea falso
        when(repo.guardar(entidadSimulada)).thenReturn(entidadSimulada);

        // WHEN & THEN
        assertDoesNotThrow(() -> {
            servicio.crear(dto);
        });

        verify(repo).ultimoID();
        verify(repo).guardar(entidadSimulada);
    }


    @Test
    void errorRepoAlCrear() throws Exception {
        CrearAvionDTO dto = new CrearAvionDTO(ModeloAvion.BOEING_737);
        when(repo.ultimoID()).thenReturn(10L);

        Avion entidadSimulada = new Avion(11L, ModeloAvion.BOEING_737);
        when(mapperAvion.AvionDTOAAvion(any(), anyLong())).thenReturn(entidadSimulada);

        when(repo.guardar(entidadSimulada)).thenThrow(new IOException("Archivo no encontrado"));

        assertThrows(Exception.class, () -> {
            servicio.crear(dto);
        });
    }

    @Test
    void idNoEncontradoAlEliminar() throws Exception {
        Long id = 1L;
        when(repo.existe(id)).thenReturn(false);

        assertThrows(EntidadNoEncontradaException.class, () -> {
            servicio.eliminar(id);
        });

        verify(repo, times(0)).eliminar(id);
    }

    @Test
    void idEncontradoAlEliminar() throws Exception {
        Long id = 1L;
        Avion avionEliminado = new Avion(id, ModeloAvion.BOEING_707);

        when(repo.existe(id)).thenReturn(true);
        when(repo.eliminar(id)).thenReturn(avionEliminado);

        assertDoesNotThrow(() -> {
            servicio.eliminar(id);
        });

        verify(repo, times(1)).eliminar(id);
    }

    @Test
    void idNoEncontradoParaModificar() throws Exception { //
        Long id = 1L;
        CrearAvionDTO dto = new CrearAvionDTO(ModeloAvion.BOEING_787);

        when(repo.existe(id)).thenReturn(false);

        assertThrows(EntidadNoEncontradaException.class, () -> {
            servicio.modificar(id, dto);
        });

        verify(repo, times(0)).buscarPorID(id);
        verify(repo, times(0)).actualizar(any());
    }

    @Test
    void idEncontradoParaModificar() throws Exception {
        Long id = 1L;
        CrearAvionDTO dto = new CrearAvionDTO(ModeloAvion.BOEING_787);
        Avion avionEncontrado = new Avion(id, ModeloAvion.AIRBUS_A320);

        when(repo.existe(id)).thenReturn(true);
        when(repo.buscarPorID(id)).thenReturn(avionEncontrado);
        when(repo.actualizar(any(Avion.class))).thenReturn(avionEncontrado);

        assertDoesNotThrow(() -> {
            servicio.modificar(id, dto);
        });

        verify(repo, times(1)).buscarPorID(id);
        verify(repo, times(1)).actualizar(avionEncontrado);
    }

    @Test
    void obtenerListaReducidaDeAviones() { //
        // GIVEN
        String palabra = "BOEING";
        Avion avion = new Avion(1L, ModeloAvion.BOEING_787);
        SalidaAvionDTO dto = new SalidaAvionDTO(ModeloAvion.BOEING_787, 1L);

        when(repo.buscar(any())).thenReturn(List.of(avion));
        when(mapperAvion.AvionASalidaAvionDTO(avion)).thenReturn(dto);

        // WHEN
        List<SalidaAvionDTO> resultado = servicio.obtenerListaReducida(palabra);

        // THEN
        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        verify(repo, times(1)).buscar(any());
    }

    @Test
    void obtenerAvionPorIDExistente() throws Exception {
        // GIVEN
        Long id = 1L;
        Avion avionEncontrado = new Avion(id, ModeloAvion.AIRBUS_A320);
        SalidaAvionDTO dto = new SalidaAvionDTO(ModeloAvion.AIRBUS_A320, id);

        when(repo.buscarPorID(id)).thenReturn(avionEncontrado);
        when(mapperAvion.AvionASalidaAvionDTO(avionEncontrado)).thenReturn(dto);

        // WHEN
        SalidaAvionDTO resultado = servicio.obtenerAvionPorID(id);

        // THEN
        assertNotNull(resultado);
        assertEquals(id, resultado.idAvion());
        verify(repo, times(1)).buscarPorID(id);
    }

    @Test
    void obtenerAvionPorIDNoExistente() throws Exception {
        // GIVEN
        Long id = 1L;

        when(repo.buscarPorID(id)).thenReturn(null);

        assertThrows(EntidadNoEncontradaException.class, () -> {
            servicio.obtenerAvionPorID(id);
        });

        verify(repo).buscarPorID(id);
    }

    @Test
    void modificarEstadoAsiento() throws Exception {
        // GIVEN
        Long idAsiento = 1001L;
        boolean nuevoEstado = true;
        // Creamos un asiento real para que el setter funcione en la prueba
        Asiento asientoSimulado = new Asiento(idAsiento, TipoDeAsiento.TURISTA);

        when(repoAsiento.buscarPorID(idAsiento)).thenReturn(asientoSimulado);

        // WHEN
        assertDoesNotThrow(() -> {
            servicio.modificarAsiento(idAsiento, nuevoEstado);
        });

        // THEN
        assertTrue(asientoSimulado.isEstadoAsiento());
        verify(repoAsiento, times(1)).buscarPorID(idAsiento);
        verify(repoAsiento, times(1)).actualizar(asientoSimulado);
    }
}