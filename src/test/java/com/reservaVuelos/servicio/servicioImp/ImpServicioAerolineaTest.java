package com.reservaVuelos.servicio.servicioImp;

import com.reservaVuelos.Excepciones.Excepcion.EntidadNoEncontradaException;
import com.reservaVuelos.Excepciones.Excepcion.OperacionFallidaException;
import com.reservaVuelos.modelo.vuelo.Aerolinea;
import com.reservaVuelos.repositorio.RepositorioAerolineas;
import com.reservaVuelos.servicio.DTOs.DTOsCrear.CrearAereolineaDTO;
import com.reservaVuelos.servicio.DTOs.DTOsSalida.SalidaAerolineaDTO;
import com.reservaVuelos.servicio.DTOs.DTOsSalida.SalidaVueloDTO;
import com.reservaVuelos.servicio.Mapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ImpServicioAerolineaTest {

    @Mock
    private RepositorioAerolineas repo;

    @Mock
    private Mapper mapper;

    @InjectMocks
    private ImpServicioAerolinea servicio;

    @Test
    void ObjetoNuloAlCrear() throws Exception {
        CrearAereolineaDTO aerolinea=null;

        assertThrows(OperacionFallidaException.class, ()-> {
            servicio.crear(aerolinea);
        });

        verify(repo, never()).guardar(any());
    }
    @Test
    void CreacionCorrecta() throws Exception {
        CrearAereolineaDTO dto = new CrearAereolineaDTO("ALA", "0999999999", "gf@gmail.com");

        when(repo.ultimoID()).thenReturn(10L);

        Aerolinea entidadSimulada = new Aerolinea(11L, "ALA", "0999999999", "gf@gmail.com");

        when(mapper.AerolineaDTOAAerolinea(any(), anyLong())).thenReturn(entidadSimulada);

        // Configurar Guardar (DEBE devolver un objeto real, no any())
        when(repo.guardar(any(Aerolinea.class))).thenReturn(entidadSimulada);

        servicio.crear(dto);

        // 3. THEN (Verificación)
        verify(repo, times(1)).ultimoID();
        verify(repo, times(1)).guardar(any(Aerolinea.class));
    }
    void ErrorRepoAlCrear() throws Exception {
        CrearAereolineaDTO dto = new CrearAereolineaDTO("ALA", "0999999999", "gf@gmail.com");

        when(repo.ultimoID()).thenReturn(10L);

        Aerolinea entidadSimulada = new Aerolinea(repo.ultimoID(),"ALA", "0999999999", "gf@gmail.com");
        when(mapper.AerolineaDTOAAerolinea(any(), anyLong())).thenReturn(entidadSimulada);

        when(repo.guardar(entidadSimulada)).thenThrow(new IOException("Archivo no encontrado"));

        assertThrows(Exception.class, () -> {
            servicio.crear(dto);
        });
    }

    @Test
    void idNoEncontradoAleliminar() throws  Exception {

        Long id = 11L;
        when(repo.existe(id)).thenReturn(false);

        assertThrows(EntidadNoEncontradaException.class, ()-> {
            servicio.eliminar(id);
        });

        verify(repo,times(0)).eliminar(id);
    }

    @Test
    void idEncontradoAleliminar() throws  Exception {

        Long id = 11L;
        when(repo.existe(id)).thenReturn(true);

        assertDoesNotThrow(()-> {
            servicio.eliminar(id);
        });

        verify(repo,times(1)).eliminar(id);
    }

    @Test
    void idNoEncontradoParaModificar() throws Exception {


        Long id = 11L;
        when(repo.existe(id)).thenReturn(false);

        assertThrows(EntidadNoEncontradaException.class, ()-> {
            servicio.modificar(id,any());
        });
        Aerolinea aero1 = new Aerolinea(1L, "Iberia", "1234567890", "i@mail.com");

        verify(repo,times(0)).actualizar(aero1);
    }

    @Test
    void idEncontradoParaModificar() throws Exception {


        Long id = 11L;
        when(repo.existe(id)).thenReturn(true);

        Aerolinea aero1 = new Aerolinea(11L, "Iberia", "1234567890", "i@mail.com");
        CrearAereolineaDTO dto = new CrearAereolineaDTO( "Iberia", "1234567890", "i@mail.com");
        when(repo.buscarPorID(id)).thenReturn(aero1);
        assertDoesNotThrow(()-> {
            servicio.modificar(id,dto);
        });


        verify(repo,times(1)).actualizar(aero1);
        verify(repo,times(1)).buscarPorID(id);
    }


    @Test
    void obtenerListaReducida() {
        Aerolinea aero1 = new Aerolinea(1L, "Iberia", "1234567890", "i@mail.com");
        Aerolinea aero2 = new Aerolinea(2L, "Latam", "4567891234", "l@mail.com");
        List<Aerolinea> listaEntidades = List.of(aero1, aero2);

        SalidaAerolineaDTO dto1 = new SalidaAerolineaDTO(1L,
                "Iberia", "1234567890", "i@mail.com");
        SalidaAerolineaDTO dto2 = new SalidaAerolineaDTO(2L,
                "Latam", "4567891234", "l@mail.com");


        when(repo.buscarTodos()).thenReturn(listaEntidades);

        when(mapper.AerolineaASalidaAerolineaDTO(aero1)).thenReturn(dto1);
        when(mapper.AerolineaASalidaAerolineaDTO(aero2)).thenReturn(dto2);

        List<SalidaAerolineaDTO> resultado = servicio.obtenerTodos();

        assertNotNull(resultado);
        assertEquals(2,resultado.size());

        verify(repo, times(1)).buscarTodos();
        verify(mapper, times(2)).AerolineaASalidaAerolineaDTO(any());
    }
}