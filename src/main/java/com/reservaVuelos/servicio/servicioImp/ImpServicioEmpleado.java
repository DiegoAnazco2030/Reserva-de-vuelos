package com.reservaVuelos.servicio.servicioImp;

import com.reservaVuelos.Excepciones.RunTime.CorreoNoValidoException;
import com.reservaVuelos.modelo.persona.Empleado;
import com.reservaVuelos.repositorio.IRepositorio;
import com.reservaVuelos.servicio.DTOs.DTOsCrear.CrearEmpleadoDTO;
import com.reservaVuelos.servicio.DTOs.DTOsSalida.SalidaEmpleadoDTO;
import com.reservaVuelos.servicio.IServicio;
import com.reservaVuelos.servicio.Mapper;

import java.util.ArrayList;
import java.util.List;

public class ImpServicioEmpleado implements IServicio<CrearEmpleadoDTO,SalidaEmpleadoDTO> {
    private final Validaciones validacion;
    private final IRepositorio<Empleado> repo;
    private final Mapper mapperEmpleado;

    public ImpServicioEmpleado(Validaciones validacion, IRepositorio repo, Mapper mapperEmpleado) {
        this.validacion = validacion;
        this.repo = repo;
        this.mapperEmpleado = mapperEmpleado;
    }

    @Override
    public void crear(CrearEmpleadoDTO empleadoNuevoDTO) {
        if(!validacion.correoEsValido(empleadoNuevoDTO.empleadoEmail())){
            throw new CorreoNoValidoException("Se debe usar un correo valido");
        }
        Long id=0L;
        Empleado empleadoNuevo = mapperEmpleado.EmpleadoDTOAEmpleado(empleadoNuevoDTO,id++);
        repo.guardarDato(empleadoNuevo);
    }

    @Override
    public List<SalidaEmpleadoDTO> obtenerTodos() {
        List<Empleado> listaEmpleados= repo.obtenerTodosDatos();
        List<SalidaEmpleadoDTO> todosEmpleados=new ArrayList<>();
        for(Empleado e : listaEmpleados){
            SalidaEmpleadoDTO empleadoDTO = mapperEmpleado.EmpleadoASalidaEmpleadoDTO(e);
            todosEmpleados.add(empleadoDTO);
        }
        return todosEmpleados;
    }

    @Override
    public void eliminar(Long id) {
        repo.eliminarDato();
    }

    @Override
    public void modificar(Long id) {

    }

}
