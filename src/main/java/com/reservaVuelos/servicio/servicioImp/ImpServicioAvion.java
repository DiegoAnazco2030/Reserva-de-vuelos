package com.reservaVuelos.servicio.servicioImp;

import com.reservaVuelos.Excepciones.Excepcion.AvionNoEncontradoException;
import com.reservaVuelos.modelo.vuelo.Avion;
import com.reservaVuelos.repositorio.IRepositorio;
import com.reservaVuelos.servicio.DTOs.DTOsCrear.CrearAvionDTO;
import com.reservaVuelos.servicio.DTOs.DTOsSalida.SalidaAvionDTO;
import com.reservaVuelos.servicio.IServicio;
import com.reservaVuelos.servicio.Mapper;

import java.util.List;

public class ImpServicioAvion implements IServicio<CrearAvionDTO, SalidaAvionDTO> {

    private final IRepositorio<Avion> repo;
    private final Mapper mapperAvion;

    public ImpServicioAvion(IRepositorio<Avion> repo, Mapper mapperAvion) {
        this.repo = repo;
        this.mapperAvion =  mapperAvion;
    }

    @Override
    public void crear(CrearAvionDTO avionNuevo) throws Exception {
        Long id = repo.ultimoID();
        if (repo.guardar(mapperAvion.AvionDTOAAvion(avionNuevo,++id)) == null) {
            throw new Exception("No se pudo crear el avion");
        }
    }

    @Override
    public List<SalidaAvionDTO> obtenerTodos() {
        return repo.buscarTodos().stream().map(mapperAvion::AvionASalidaAvionDTO).toList();
    }

    @Override
    public void eliminar(Long id) throws Exception {
        if(!repo.existe(id)){
            throw new AvionNoEncontradoException("El avion a eliminar no existe");
        }
        if (repo.eliminar(id) == null) {
            throw new Exception("No se pudo eliminar el avion");
        }
    }

    @Override
    public void modificar(Long id, CrearAvionDTO avionModificar) throws Exception {
        if (!repo.existe(id)) {
            throw new AvionNoEncontradoException("El avion no existe");
        }
        Avion avionEncontrado = repo.buscarPorID(id);
        avionEncontrado.setModeloAvion(avionModificar.modeloAvion());
        if (repo.actualizar(avionEncontrado) == null) {
            throw new Exception("No se pudo modificar el avion");
        }
    }

    @Override
    public List<SalidaAvionDTO> obtenerListaReducida(String palabraBuscar) {
        return repo.buscar(t -> t.getModeloAvion().name().toLowerCase().contains(palabraBuscar.toLowerCase())).stream().
        map(mapperAvion::AvionASalidaAvionDTO).
        toList();
    }

    public Avion obtenerAvionPorID(Long id) throws AvionNoEncontradoException {
        Avion avionEncontrado= repo.buscarPorID(id);
        if(avionEncontrado == null){
            throw new AvionNoEncontradoException("El avion no existe");
        }
        return avionEncontrado;
    }
}
