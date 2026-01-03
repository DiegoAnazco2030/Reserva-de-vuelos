package com.reservaVuelos.servicio.servicioImp;

import com.reservaVuelos.Excepciones.Excepcion.EntidadNoEncontradaException;
import com.reservaVuelos.Excepciones.RunTime.CorreoNoValidoException;
import com.reservaVuelos.Excepciones.RunTime.StringNoValidoException;
import com.reservaVuelos.Excepciones.RunTime.TelefonoNoValidoException;
import com.reservaVuelos.modelo.persona.Empleado;
import com.reservaVuelos.repositorio.IRepositorio;
import com.reservaVuelos.servicio.DTOs.DTOsCrear.CrearEmpleadoDTO;
import com.reservaVuelos.servicio.DTOs.DTOsSalida.SalidaEmpleadoDTO;
import com.reservaVuelos.servicio.IServicio;
import com.reservaVuelos.servicio.Mapper;

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
    public void crear(CrearEmpleadoDTO empleadoNuevoDTO) throws Exception {
        validarDatosEmpleado(empleadoNuevoDTO);
        Long id = repo.ultimoID() + 1;
        Empleado empleadoNuevo = mapperEmpleado.EmpleadoDTOAEmpleado(empleadoNuevoDTO,id);
        repo.guardar(empleadoNuevo);
    }

    @Override
    public List<SalidaEmpleadoDTO> obtenerTodos() {
        return repo.buscarTodos().stream().map(mapperEmpleado::EmpleadoASalidaEmpleadoDTO).toList();
    }

    @Override
    public void eliminar(Long id) throws Exception {
        if(repo.buscarPorID(id)==null){
            throw new EntidadNoEncontradaException("Error, la persona no existe");
        }
        repo.eliminar(id);

    }


    @Override
    public void modificar(Long id, CrearEmpleadoDTO empleadoModificar) throws Exception {

        Empleado empleadoExistente = repo.buscarPorID(id);
        if (empleadoExistente == null) {
            throw new EntidadNoEncontradaException("Error, la persona no existe");
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

    @Override
    public List<SalidaEmpleadoDTO> obtenerListaReducida(String palabraBuscar) {
        return  repo.buscar(t -> t.getNombre().equals(palabraBuscar)).stream().map(mapperEmpleado::EmpleadoASalidaEmpleadoDTO).toList();
    }

    private void validarDatosEmpleado(CrearEmpleadoDTO dto) {
        // Validar Nombre
        if (!validacion.validarRangoLongitud(dto.nombre(), 1, 10)) {
            throw new StringNoValidoException("Nombre no valido");
        }

        // Validar Apellido (CORREGIDO: ahora usa el campo correcto)
        if (!validacion.validarRangoLongitud(dto.apellido(), 1, 10)) {
            throw new StringNoValidoException("Apellido no valido");
        }

        // Validar Teléfono (SOLO UNA VEZ)
        if (!validacion.validarTelefono(dto.telefono())) {
            throw new TelefonoNoValidoException("Ingrese un telefono valido");
        }

        // Validar Longitud Email
        if (!validacion.validarRangoLongitud(dto.empleadoEmail(), 10, 40)) {
            throw new StringNoValidoException("La longitud de caracteres del correo no es valida");
        }

        // Validar Formato Email
        if (!validacion.correoEsValido(dto.empleadoEmail())) {
            throw new CorreoNoValidoException("Se debe usar un correo valido");
        }
    }
}
