package com.reservaVuelos.servicio.servicioImp;

import com.reservaVuelos.Excepciones.Excepcion.AvionNoEncontradoDTO;
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

    public ImpServicioAvion(IRepositorio<Avion> repo, Mapper  mapperAvion) {
        this.repo = repo;
        this.mapperAvion =  mapperAvion;
    }

    @Override
    public void crear(CrearAvionDTO avionNuevo) {
        Long id=repo.ultimoID();
        repo.guardar(mapperAvion.AvionDTOAAvion(avionNuevo,id++));
    }

    @Override
    public List<SalidaAvionDTO> obtenerTodos() {
        return repo.buscarTodos().stream().map(mapperAvion::AvionASalidaAvionDTO).toList();
    }

    @Override
    public void eliminar(Long id) throws Exception {
        if(!repo.existe(id)){
            throw new AvionNoEncontradoDTO("El avion a eliminar no existe");
        }
    }

    @Override
    public void modificar(Long id, CrearAvionDTO avionModificar) throws Exception {
        Avion avionEncontrado=repo.buscarPorID(id);
        avionEncontrado.setModeloAvion(avionModificar.modeloAvion());
        repo.actualizar(avionEncontrado);
    }

    @Override
    public List<SalidaAvionDTO> obtenerListaReducida(String palabraBuscar) {

        return repo.buscar(t -> t.getModeloAvion().name().equals(palabraBuscar)).stream().map(mapperAvion::AvionASalidaAvionDTO).toList();
    }
}
