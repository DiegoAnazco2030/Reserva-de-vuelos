package com.reservaVuelos.servicio.servicioImp;

import com.reservaVuelos.Excepciones.Excepcion.EntidadNoEncontradaException;
import com.reservaVuelos.modelo.vuelo.Aerolinea;
import com.reservaVuelos.modelo.vuelo.Avion;
import com.reservaVuelos.modelo.vuelo.Vuelo;
import com.reservaVuelos.repositorio.IRepositorio;
import com.reservaVuelos.servicio.DTOs.DTOsCrear.CrearVueloDTO;
import com.reservaVuelos.servicio.DTOs.DTOsSalida.SalidaAvionDTO;
import com.reservaVuelos.servicio.DTOs.DTOsSalida.SalidaVueloDTO;
import com.reservaVuelos.servicio.IServicio;
import com.reservaVuelos.servicio.Mapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ImpServicioVuelo implements IServicio<CrearVueloDTO, SalidaVueloDTO> {

    private final IRepositorio<Vuelo> repo;
    private final Mapper mapperVuelo;
    private final ImpServicioAvion avionServicio;
    private final ImpServicioAerolinea aerolineaServicio;
    private final Validaciones validacion;

    public ImpServicioVuelo(IRepositorio<Vuelo> repo, Mapper mapperVuelo, ImpServicioAvion avionServicio, ImpServicioAerolinea aerolineaServicio, Validaciones validacion) {
        this.repo = repo;
        this.mapperVuelo = mapperVuelo;
        this.avionServicio = avionServicio;
        this.aerolineaServicio = aerolineaServicio;
        this.validacion = validacion;
    }

    @Override
    public void crear(CrearVueloDTO vueloNuevo) throws Exception {
        validacion.validarFechas(vueloNuevo.fechaHoraSalida(), vueloNuevo.fechaHoraLlegada());
        Long id = repo.ultimoID();
        Avion avionEncontrado = mapperAvion(avionServicio.obtenerAvionPorID(vueloNuevo.idAvion()), vueloNuevo.idAvion());
        Aerolinea aerolineaEncontrada = aerolineaServicio.buscarAerolineaEntidad(vueloNuevo.idAerolinea());
        Vuelo nuevoVuelo = mapperVuelo.VuealoDTOAVuelo(vueloNuevo, ++id, aerolineaEncontrada, avionEncontrado);
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
            throw new EntidadNoEncontradaException("El vuelo no existe");
        }
        repo.eliminar(id);
    }

    @Override
    public void modificar(Long id, CrearVueloDTO objeto) throws Exception {
        Vuelo vueloExistente = repo.buscarPorID(id);
        if (vueloExistente == null) {
            throw new EntidadNoEncontradaException("Vuelo no encontrado");
        }

        validacion.validarFechas(objeto.fechaHoraSalida(), objeto.fechaHoraLlegada());

        vueloExistente.setFechaHoraSalida(objeto.fechaHoraSalida());
        vueloExistente.setFechaHoraLlegada(objeto.fechaHoraLlegada());

        SalidaAvionDTO avionInfo = avionServicio.obtenerAvionPorID(objeto.idAvion());
        Avion nuevoAvion = new Avion(objeto.idAvion(), avionInfo.modeloAvion());
        vueloExistente.setAvionVuelo(nuevoAvion);

        actualizarEstadoVuelo(vueloExistente);
        repo.actualizar(vueloExistente);
    }

    @Override
    public List<SalidaVueloDTO> obtenerListaReducida(String palabraBuscar) {
        List<Vuelo> vuelos = repo.buscar(t -> t.getOrigenVuelo().name().toLowerCase().contains(palabraBuscar.toLowerCase()));
        vuelos.forEach(this::actualizarEstadoVuelo);
        return vuelos.stream()
                .map(mapperVuelo::VueloASalidaVueloDTO)
                .toList();
    }

    private void actualizarEstadoVuelo(Vuelo vuelo) {
        if (LocalDateTime.now().isAfter(vuelo.getFechaHoraLlegada())) {
            vuelo.setEstadoVuelo(true);
        }
    }

    public Long obtenerIdAvion(Long idVuelo){
        Long id = obtenerVuelo(idVuelo);
        return id;
    }

    private Long obtenerVuelo(Long id){
        Vuelo vuelo= repo.buscarPorID(id);
        return vuelo.getAvionVuelo().getId();
    }

    private Avion mapperAvion(SalidaAvionDTO dto, Long idAvion) {
        return new Avion(idAvion, dto.modeloAvion());
    }
}