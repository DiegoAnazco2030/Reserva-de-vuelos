package com.reservaVuelos.servicio.servicioImp;

import com.reservaVuelos.Excepciones.Excepcion.PersonaNoEncontradaException;
import com.reservaVuelos.Excepciones.RunTime.CorreoNoValidoException;
import com.reservaVuelos.modelo.persona.Usuario;
import com.reservaVuelos.repositorio.IRepositorio;
import com.reservaVuelos.servicio.DTOs.DTOsCrear.CrearUsuarioDTO;
import com.reservaVuelos.servicio.DTOs.DTOsSalida.SalidaUsuarioDTO;
import com.reservaVuelos.servicio.IServicio;
import com.reservaVuelos.servicio.Mapper;

import java.util.ArrayList;
import java.util.List;

public class ImpServicioUsuario implements IServicio<CrearUsuarioDTO, SalidaUsuarioDTO> {

    private final Validaciones validacion;
    private final IRepositorio<Usuario> repo;
    private final Mapper mapperUsuario;

    public ImpServicioUsuario(Validaciones validacion, IRepositorio<Usuario> repo, Mapper mapperUsuario) {
        this.validacion = validacion;
        this.repo = repo;
        this.mapperUsuario = mapperUsuario;
    }

    @Override
    public void crear(CrearUsuarioDTO usuarioNuevoDTO) {
        if(!validacion.correoEsValido(usuarioNuevoDTO.usuarioEmail())){
            throw new CorreoNoValidoException("Se debe usar un correo valido");
        }
        Long id=repo.ultimoID();
        Usuario usuarioNuevo = mapperUsuario.UsuarioDTOAUsuario(usuarioNuevoDTO,id++);
        repo.guardar(usuarioNuevo);
    }

    @Override
    public List<SalidaUsuarioDTO> obtenerTodos() {
        List<Usuario> listaUsuarios= repo.buscarTodos();
        List<SalidaUsuarioDTO> todosUsuarios=new ArrayList<>();
        for(Usuario u : listaUsuarios){
            SalidaUsuarioDTO usuarioDTO = mapperUsuario.UsuarioASalidaUsuarioDTO(u);
            todosUsuarios.add(usuarioDTO);
        }
        return todosUsuarios;
    }

    @Override
    public void eliminar(Long id) throws Exception {
        if(repo.buscarPorID(id)==null){
            throw new PersonaNoEncontradaException("Error, la persona no existe");
        }
        repo.eliminar(id);

    }

    @Override
    public void modificar(Long id, CrearUsuarioDTO usuarioModificar) throws Exception {

        Usuario usuarioExistente = repo.buscarPorID(id);
        if (usuarioExistente == null) {
            throw new PersonaNoEncontradaException("Error, la persona no existe");
        }

        if (!validacion.correoEsValido(usuarioModificar.usuarioEmail())) {
            throw new CorreoNoValidoException("El nuevo correo no es valido");
        }

        usuarioExistente.setUsuarioEmail(usuarioModificar.usuarioEmail());

        if (usuarioModificar.usuarioContrasenia() != null && !usuarioModificar.usuarioContrasenia().isBlank()) {
            usuarioExistente.setUsuarioContrasenia(usuarioModificar.usuarioContrasenia().trim());
        }

        if (usuarioModificar.telefono() != null && !usuarioModificar.telefono().isBlank()) {
            usuarioExistente.setTelefono(usuarioModificar.telefono().trim());
        }

        repo.guardar(usuarioExistente);
    }
}
