package com.reservaVuelos.servicio.servicioImp;

import com.reservaVuelos.Excepciones.Excepcion.PersonaNoEncontradaException;
import com.reservaVuelos.modelo.vuelo.Avion;
import com.reservaVuelos.modelo.vuelo.Vuelo;
import com.reservaVuelos.repositorio.IRepositorio;
import com.reservaVuelos.servicio.DTOs.DTOsCrear.CrearVueloDTO;
import com.reservaVuelos.servicio.DTOs.DTOsSalida.SalidaVueloDTO;
import com.reservaVuelos.servicio.IServicio;
import com.reservaVuelos.servicio.Mapper;

import java.time.LocalDateTime;
import java.util.List;

public class ImpServicioVuelo implements IServicio<CrearVueloDTO, SalidaVueloDTO> {

    private final IRepositorio<Vuelo> repo;
    private final Mapper mapperVuelo;

    public ImpServicioVuelo(IRepositorio<Vuelo> repo, Mapper mapperVuelo) {
        this.repo = repo;
        this.mapperVuelo = mapperVuelo;
    }

    @Override
    public void crear(CrearVueloDTO objeto) {
        validarFechas(objeto.fechaHoraSalida(), objeto.fechaHoraLlegada());
        Long id = repo.ultimoID();

        Vuelo nuevoVuelo = mapperVuelo.VuealoDTOAVuelo(objeto, id);
        repo.guardar(nuevoVuelo);
    }

    @Override
    public List<SalidaVueloDTO> obtenerTodos() {
        List<Vuelo> vuelos = repo.buscarTodos();
        vuelos.forEach(this::actualizarEstadoVuelo);
        return vuelos.stream()
                .map(mapperVuelo::VueloASalidaVueloDTO)
                .toList();
    }

    @Override
    public void eliminar(Long id) throws Exception {
        if (!repo.existe(id)) {
            throw new PersonaNoEncontradaException("El vuelo no existe");
        }
        repo.eliminar(id);
    }

    @Override
    public void modificar(Long id, CrearVueloDTO objeto) throws Exception {
        Vuelo vueloExistente = repo.buscarPorID(id);
        if (vueloExistente == null) {
            throw new PersonaNoEncontradaException("Vuelo no encontrado");
        }

        validarFechas(objeto.fechaHoraSalida(), objeto.fechaHoraLlegada());

        vueloExistente.setFechaHoraSalida(objeto.fechaHoraSalida());
        vueloExistente.setFechaHoraLlegada(objeto.fechaHoraLlegada());

        Avion nuevoAvion = new Avion(objeto.idAvion(), null);
        vueloExistente.setAvionVuelo(nuevoAvion);

        actualizarEstadoVuelo(vueloExistente);
        repo.actualizar(vueloExistente);
    }

    @Override
    public List<SalidaVueloDTO> obtenerListaReducida(String palabraBuscar) {
        List<Vuelo> vuelos = repo.buscar(t -> t.getOrigenVuelo().name().equalsIgnoreCase(palabraBuscar));
        vuelos.forEach(this::actualizarEstadoVuelo);
        return vuelos.stream()
                .map(mapperVuelo::VueloASalidaVueloDTO)
                .toList();
    }

    private void validarFechas(LocalDateTime salida, LocalDateTime llegada) {
        if (llegada.isBefore(salida)) {
            throw new RuntimeException("La fecha de llegada no puede ser anterior a la de salida");
        }
    }

    private void actualizarEstadoVuelo(Vuelo vuelo) {
        if (LocalDateTime.now().isAfter(vuelo.getFechaHoraLlegada())) {
            vuelo.setEstadoVuelo(true);
        }
    }
}