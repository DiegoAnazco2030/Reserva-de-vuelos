package com.reservaVuelos.servicio.servicioImp;

import com.reservaVuelos.Excepciones.Excepcion.PersonaNoEncontradaException;
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

    public ImpServicioEmpleado(Validaciones validacion, IRepositorio<Empleado> repo, Mapper mapperEmpleado) {
        this.validacion = validacion;
        this.repo = repo;
        this.mapperEmpleado = mapperEmpleado;
    }

    @Override
    public void crear(CrearEmpleadoDTO empleadoNuevoDTO) {
        if(!validacion.correoEsValido(empleadoNuevoDTO.empleadoEmail())){
            throw new CorreoNoValidoException("Se debe usar un correo valido");
        }
        Long id=repo.ultimoID();
        Empleado empleadoNuevo = mapperEmpleado.EmpleadoDTOAEmpleado(empleadoNuevoDTO,id++);
        repo.guardar(empleadoNuevo);
    }

    @Override
    public List<SalidaEmpleadoDTO> obtenerTodos() {
        List<Empleado> listaEmpleados= repo.buscarTodos();
        List<SalidaEmpleadoDTO> todosEmpleados=new ArrayList<>();
        for(Empleado e : listaEmpleados){
            SalidaEmpleadoDTO empleadoDTO = mapperEmpleado.EmpleadoASalidaEmpleadoDTO(e);
            todosEmpleados.add(empleadoDTO);
        }
        return todosEmpleados;
    }

    @Override
    public void eliminar(Long id) throws PersonaNoEncontradaException {
        if(repo.buscarPorID(id)==null){
            throw new PersonaNoEncontradaException("Error, la persona no existe");
        }
        repo.eliminar(id);

    }


    @Override
    public void modificar(Long id, CrearEmpleadoDTO empleadoModificar) throws Exception {

        Empleado empleadoExistente = repo.buscarPorID(id);
        if (empleadoExistente == null) {
            throw new PersonaNoEncontradaException("Error, la persona no existe");
        }

        if (!validacion.correoEsValido(empleadoModificar.empleadoEmail())) {
            throw new CorreoNoValidoException("El nuevo correo no es valido");
        }

        empleadoExistente.setEmpleadoEmail(empleadoModificar.empleadoEmail());

        if (empleadoModificar.empleadoContrasenia() != null && !empleadoModificar.empleadoContrasenia().isBlank()) {
            empleadoExistente.setEmpleadoContrasenia(empleadoModificar.empleadoContrasenia().trim());
        }

        if (empleadoModificar.telefono() != null && !empleadoModificar.telefono().isBlank()) {
            empleadoExistente.setTelefono(empleadoModificar.telefono().trim());
        }

        repo.guardar(empleadoExistente);
    }
}
