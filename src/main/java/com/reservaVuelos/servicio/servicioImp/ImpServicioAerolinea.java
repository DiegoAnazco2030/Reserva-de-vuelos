package com.reservaVuelos.servicio.servicioImp;

import com.reservaVuelos.Excepciones.Excepcion.EntidadNoEncontradaException;
import com.reservaVuelos.Excepciones.Excepcion.OperacionFallidaException;
import com.reservaVuelos.modelo.vuelo.Aerolinea;
import com.reservaVuelos.repositorio.IRepositorio;
import com.reservaVuelos.servicio.DTOs.DTOsCrear.CrearAereolineaDTO;
import com.reservaVuelos.servicio.DTOs.DTOsSalida.SalidaAerolineaDTO;
import com.reservaVuelos.servicio.IServicio;
import com.reservaVuelos.servicio.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImpServicioAerolinea implements IServicio<CrearAereolineaDTO, SalidaAerolineaDTO> {

    private final IRepositorio<Aerolinea> repo;
    private final Mapper mapper;

    public ImpServicioAerolinea(IRepositorio<Aerolinea> repo, Mapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    @Override
    public void crear(CrearAereolineaDTO objeto) throws Exception {
        if (objeto == null) {
            throw new OperacionFallidaException("El objeto de creación de aerolínea no puede ser nulo.");
        }

        // Asignamos el ID desde el servicio
        Long id = repo.ultimoID() + 1;
        Aerolinea nueva = mapper.AerolineaDTOAAerolinea(objeto, id);
        repo.guardar(nueva);
    }

    @Override
    public List<SalidaAerolineaDTO> obtenerTodos() {
        return repo.buscarTodos().stream()
                .map(mapper::AerolineaASalidaAerolineaDTO)
                .toList();
    }

    @Override
    public void eliminar(Long id) throws Exception {
        if (!repo.existe(id)) {
            throw new EntidadNoEncontradaException("La aerolínea con ID " + id + " no existe.");
        }
        repo.eliminar(id);
    }

    @Override
    public void modificar(Long id, CrearAereolineaDTO objeto) throws Exception {
        if (!repo.existe(id)) {
            throw new EntidadNoEncontradaException("La aerolínea con ID " + id + " no existe.");
        }
        Aerolinea existente = repo.buscarPorID(id);

        // Actualizamos los campos
        existente.setNombre(objeto.nombre());
        existente.setTelefono(objeto.telefono());
        existente.setEmail(objeto.email());

        repo.actualizar(existente);
    }

    @Override
    public List<SalidaAerolineaDTO> obtenerListaReducida(String palabraBuscar) {
        // Búsqueda flexible por nombre
        return repo.buscar(a -> a.getNombre().toLowerCase().contains(palabraBuscar.toLowerCase()))
                .stream()
                .map(mapper::AerolineaASalidaAerolineaDTO)
                .toList();
    }

    // Método helper para otros servicios
    public Aerolinea buscarAerolineaEntidad(Long id) throws Exception {
        Aerolinea a = repo.buscarPorID(id);
        if (a == null) throw new EntidadNoEncontradaException("Aerolínea no existe.");
        return a;
    }
}