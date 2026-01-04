package com.reservaVuelos.servicio.servicioImp;

import com.reservaVuelos.Excepciones.Excepcion.EntidadNoEncontradaException;
import com.reservaVuelos.modelo.persona.Empleado;
import com.reservaVuelos.repositorio.IRepositorio;
import com.reservaVuelos.servicio.DTOs.DTOsCrear.CrearEmpleadoDTO;
import com.reservaVuelos.servicio.DTOs.DTOsSalida.SalidaEmpleadoDTO;
import com.reservaVuelos.servicio.Mapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ImpServicioEmpleadoTest {

    @Mock
    private IRepositorio<Empleado> repo;

    @Mock
    private Mapper mapper;

    @Mock
    private Validaciones validacion;

    @InjectMocks
    private ImpServicioEmpleado servicio;

    @Test
    void CreacionCorrecta() throws Exception {
        CrearEmpleadoDTO dto = new CrearEmpleadoDTO("Juan", "Perez", "1234567890", 30, "juan@mail.com", "pass123");

        when(validacion.validarRangoLongitud(anyString(), anyInt(), anyInt())).thenReturn(true);
        when(validacion.validarTelefono(anyString())).thenReturn(true);
        when(validacion.correoEsValido(anyString())).thenReturn(true);
        when(repo.ultimoID()).thenReturn(10L);

        Empleado entidadSimulada = new Empleado("Juan", "Perez", "1234567890", 30, 11L, "pass123", "juan@mail.com");
        when(mapper.EmpleadoDTOAEmpleado(any(), anyLong())).thenReturn(entidadSimulada);

        servicio.crear(dto);

        verify(repo, times(1)).ultimoID();
        verify(repo, times(1)).guardar(any(Empleado.class));
    }

    @Test
    void obtenerTodos() {
        Empleado emp1 = new Empleado("Juan", "Perez", "123", 30, 1L, "p1", "j@m.com");
        List<Empleado> lista = List.of(emp1);
        SalidaEmpleadoDTO dto = new SalidaEmpleadoDTO("j@m.com", "Juan", "Perez", 1L);

        when(repo.buscarTodos()).thenReturn(lista);
        when(mapper.EmpleadoASalidaEmpleadoDTO(emp1)).thenReturn(dto);

        List<SalidaEmpleadoDTO> resultado = servicio.obtenerTodos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(repo, times(1)).buscarTodos();
    }

    @Test
    void idNoEncontradoAleliminar() throws Exception {
        Long id = 1L;
        when(repo.buscarPorID(id)).thenReturn(null);

        assertThrows(EntidadNoEncontradaException.class, () -> {
            servicio.eliminar(id);
        });

        verify(repo, never()).eliminar(id);
    }

    @Test
    void idEncontradoAleliminar() throws Exception {
        Long id = 1L;
        Empleado emp = new Empleado("Juan", "Perez", "123", 30, id, "p1", "j@m.com");
        when(repo.buscarPorID(id)).thenReturn(emp);

        assertDoesNotThrow(() -> {
            servicio.eliminar(id);
        });

        verify(repo, times(1)).eliminar(id);
    }

    @Test
    void idNoEncontradoParaModificar() throws Exception {
        Long id = 1L;
        when(repo.buscarPorID(id)).thenReturn(null);

        assertThrows(EntidadNoEncontradaException.class, () -> {
            servicio.modificar(id, any());
        });

        verify(repo, never()).guardar(any());
    }

    @Test
    void idEncontradoParaModificar() throws Exception {
        Long id = 1L;
        Empleado existente = new Empleado("Juan", "Perez", "123", 30, id, "p1", "j@m.com");
        CrearEmpleadoDTO dto = new CrearEmpleadoDTO("Juan", "Perez", "123", 35, "nuevo@m.com", "p1");

        when(repo.buscarPorID(id)).thenReturn(existente);
        when(validacion.correoEsValido(anyString())).thenReturn(true);
        when(validacion.validarEdad(anyInt())).thenReturn(true);

        assertDoesNotThrow(() -> {
            servicio.modificar(id, dto);
        });

        verify(repo, times(1)).guardar(existente);
    }

    @Test
    void obtenerListaReducida() {
        String palabra = "Juan";
        Empleado emp = new Empleado("Juan", "Perez", "123", 30, 1L, "p1", "j@m.com");
        SalidaEmpleadoDTO dto = new SalidaEmpleadoDTO("j@m.com", "Juan", "Perez", 1L);

        when(repo.buscar(any())).thenReturn(List.of(emp));
        when(mapper.EmpleadoASalidaEmpleadoDTO(emp)).thenReturn(dto);

        List<SalidaEmpleadoDTO> resultado = servicio.obtenerListaReducida(palabra);

        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        verify(repo, times(1)).buscar(any());
    }
}