package com.reservaVuelos.servicio.servicioImp;

import com.reservaVuelos.Excepciones.Excepcion.EntidadNoEncontradaException;
import com.reservaVuelos.Excepciones.Excepcion.OperacionFallidaException;
import com.reservaVuelos.modelo.vuelo.Asiento;
import com.reservaVuelos.modelo.vuelo.Avion;
import com.reservaVuelos.repositorio.IRepositorio;
import com.reservaVuelos.servicio.DTOs.DTOsCrear.CrearAvionDTO;
import com.reservaVuelos.servicio.DTOs.DTOsSalida.SalidaAsientoDTO;
import com.reservaVuelos.servicio.DTOs.DTOsSalida.SalidaAvionDTO;
import com.reservaVuelos.servicio.DTOs.DTOsSalida.SalidaVueloDTO;
import com.reservaVuelos.servicio.IServicio;
import com.reservaVuelos.servicio.Mapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ImpServicioAvion implements IServicio<CrearAvionDTO, SalidaAvionDTO> {

    private final IRepositorio<Avion> repo;
    private final IRepositorio<Asiento> repoAsiento;
    private final ImpServicioVuelo servicioVuelo;
    private final Mapper mapperAvion;

    public ImpServicioAvion(IRepositorio<Avion> repo, IRepositorio<Asiento> repoAsiento, ImpServicioVuelo servicioVuelo, Mapper mapperAvion) {
        this.repo = repo;
        this.repoAsiento = repoAsiento;
        this.servicioVuelo = servicioVuelo;
        this.mapperAvion =  mapperAvion;
    }

    @Override
    public void crear(CrearAvionDTO avionNuevo) throws Exception {
        if (avionNuevo == null) {
            throw new OperacionFallidaException("El DTO de avión no puede ser nulo");
        }

        Long id = repo.ultimoID() +1;

        Avion avionParaGuardar = mapperAvion.AvionDTOAAvion(avionNuevo, id);

        if (repo.guardar(avionParaGuardar) == null) {
            throw new OperacionFallidaException("No se pudo crear el avion");
        }

        List<Asiento> asientosAVincular = avionParaGuardar.getAsientosAvion();
        for (Asiento asiento : asientosAVincular) {
            if (repoAsiento.guardar(asiento) == null) {
                throw new OperacionFallidaException("No se pudieron crear los asientos del avión");
            }
        }
    }

    @Override
    public List<SalidaAvionDTO> obtenerTodos() {
        return repo.buscarTodos().stream().map(mapperAvion::AvionASalidaAvionDTO).toList();
    }

    @Override
    public void eliminar(Long id) throws Exception {
        boolean idAvion = servicioVuelo.obtenerTodos().stream()
                .anyMatch(v -> v.idAvion().equals(id));
        if (idAvion) {
            throw new OperacionFallidaException("No se puede eliminar el avión porque está asociado a un vuelo");
        }
        if(!repo.existe(id)){
            throw new EntidadNoEncontradaException("El avion a eliminar no existe");
        }
        if (repo.eliminar(id) == null) {
            throw new Exception("No se pudo eliminar el avion");
        }
    }

    @Override
    public void modificar(Long id, CrearAvionDTO avionModificar) throws Exception {
        if (!repo.existe(id)) {
            throw new EntidadNoEncontradaException("El avion no existe");
        }
        Avion avionEncontrado = repo.buscarPorID(id);
        avionEncontrado.setModeloAvion(avionModificar.modeloAvion());
        if (repo.actualizar(avionEncontrado) == null) {
            throw new Exception("No se pudo modificar el avion");
        }
    }

    @Override
    public List<SalidaAvionDTO> obtenerListaReducida(String palabraBuscar) {
        return repo.buscar(t -> t.getModeloAvion().name().toLowerCase().contains(palabraBuscar.toLowerCase()))
                .stream()
                .map(mapperAvion::AvionASalidaAvionDTO)
                .toList();
    }

    public SalidaAvionDTO obtenerAvionPorID(Long id) throws Exception {
        Avion avionEncontrado= repo.buscarPorID(id);
        if(avionEncontrado == null){
            throw new EntidadNoEncontradaException("El avion no existe");
        }
        return mapperAvion.AvionASalidaAvionDTO(avionEncontrado);

    }

    public List<SalidaAsientoDTO> obtenerAsientos(Long idAvion) {
        if (idAvion == null) return new ArrayList<SalidaAsientoDTO>();

        return repoAsiento.buscar(a -> verificarIDAvion(a.getId(), idAvion))
                .stream()
                .map(mapperAvion::AsientoASalidaAsientoDTO)
                .toList();
    }

    private boolean verificarIDAvion(Long idAsiento, Long idAvion) {
        Long idAvionAVerificar = (Long) idAsiento/1000;
        return idAvionAVerificar.equals(idAvion);
    }

    public void modificarAsiento(Long idAsiento, boolean estado) throws Exception {
        Asiento asiento = repoAsiento.buscarPorID(idAsiento);
        asiento.setEstadoAsiento(estado);
        repoAsiento.actualizar(asiento);
    }
}
