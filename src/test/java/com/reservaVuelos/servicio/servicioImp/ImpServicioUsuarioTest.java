package com.reservaVuelos.servicio.servicioImp;

import com.reservaVuelos.Excepciones.Excepcion.EntidadNoEncontradaException;
import com.reservaVuelos.modelo.persona.Usuario;
import com.reservaVuelos.repositorio.IRepositorio;
import com.reservaVuelos.servicio.DTOs.DTOsCrear.CrearUsuarioDTO;
import com.reservaVuelos.servicio.DTOs.DTOsSalida.SalidaUsuarioDTO;
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
class ImpServicioUsuarioTest {

    @Mock
    private IRepositorio<Usuario> repo;

    @Mock
    private Mapper mapper;

    @Mock
    private Validaciones validacion;

    @InjectMocks
    private ImpServicioUsuario servicio;

    @Test
    void CreacionCorrecta() throws Exception {
        CrearUsuarioDTO dto = new CrearUsuarioDTO("Carlos", "Ruiz", "0987654321", 25, "carlos@mail.com", "pass123", "A12345678");

        when(validacion.validarRangoLongitud(anyString(), anyInt(), anyInt())).thenReturn(true);
        when(validacion.validarTelefono(anyString())).thenReturn(true);
        when(validacion.correoEsValido(anyString())).thenReturn(true);
        when(validacion.documentoEsValidaGenerico(anyString())).thenReturn(true);
        when(repo.ultimoID()).thenReturn(5L);

        Usuario entidadSimulada = new Usuario("Carlos", "Ruiz", "0987654321", 25, "A12345678", "pass123", "carlos@mail.com", 6L);
        when(mapper.UsuarioDTOAUsuario(any(), anyLong())).thenReturn(entidadSimulada);

        servicio.crear(dto);

        verify(repo, times(1)).ultimoID();
        verify(repo, times(1)).guardar(any(Usuario.class));
    }

    @Test
    void obtenerTodos() {
        Usuario user1 = new Usuario("Carlos", "Ruiz", "0987", 25, "A123", "p1", "c@m.com", 1L);
        List<Usuario> lista = List.of(user1);
        SalidaUsuarioDTO dto = new SalidaUsuarioDTO(1L, "Carlos", "Ruiz", "c@m.com");

        when(repo.buscarTodos()).thenReturn(lista);
        when(mapper.UsuarioASalidaUsuarioDTO(user1)).thenReturn(dto);

        List<SalidaUsuarioDTO> resultado = servicio.obtenerTodos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(repo, times(1)).buscarTodos();
    }

    @Test
    void idNoEncontradoAleliminar() throws Exception {
        Long id = 10L;
        when(repo.buscarPorID(id)).thenReturn(null);

        assertThrows(EntidadNoEncontradaException.class, () -> {
            servicio.eliminar(id);
        });

        verify(repo, never()).eliminar(id);
    }

    @Test
    void idEncontradoAleliminar() throws Exception {
        Long id = 10L;
        Usuario user = new Usuario("Carlos", "Ruiz", "0987", 25, "A123", "p1", "c@m.com", id);
        when(repo.buscarPorID(id)).thenReturn(user);

        assertDoesNotThrow(() -> {
            servicio.eliminar(id);
        });

        verify(repo, times(1)).eliminar(id);
    }

    @Test
    void idNoEncontradoParaModificar() throws Exception {
        Long id = 10L;
        when(repo.buscarPorID(id)).thenReturn(null);

        assertThrows(EntidadNoEncontradaException.class, () -> {
            servicio.modificar(id, any());
        });

        verify(repo, never()).guardar(any());
    }

    @Test
    void idEncontradoParaModificar() throws Exception {
        Long id = 10L;
        Usuario existente = new Usuario("Carlos", "Ruiz", "0987", 25, "A123", "p1", "c@m.com", id);
        CrearUsuarioDTO dto = new CrearUsuarioDTO("Carlos", "Ruiz", "1111111111", 25, "nuevo@m.com", "newPass", "A123");

        when(repo.buscarPorID(id)).thenReturn(existente);
        when(validacion.correoEsValido(anyString())).thenReturn(true);

        assertDoesNotThrow(() -> {
            servicio.modificar(id, dto);
        });

        verify(repo, times(1)).guardar(existente);
    }

    @Test
    void obtenerListaReducida() {
        String palabra = "Carlos";
        Usuario user = new Usuario("Carlos", "Ruiz", "0987", 25, "A123", "p1", "c@m.com", 1L);
        SalidaUsuarioDTO dto = new SalidaUsuarioDTO(1L, "Carlos", "Ruiz", "c@m.com");

        when(repo.buscar(any())).thenReturn(List.of(user));
        when(mapper.UsuarioASalidaUsuarioDTO(user)).thenReturn(dto);

        List<SalidaUsuarioDTO> resultado = servicio.obtenerListaReducida(palabra);

        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        verify(repo, times(1)).buscar(any());
    }
}