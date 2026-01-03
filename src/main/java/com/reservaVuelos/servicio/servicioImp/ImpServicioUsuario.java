package com.reservaVuelos.servicio.servicioImp;

import com.reservaVuelos.Excepciones.Excepcion.EntidadNoEncontradaException;
import com.reservaVuelos.Excepciones.RunTime.CorreoNoValidoException;
import com.reservaVuelos.Excepciones.RunTime.DocumentoNoValidoException;
import com.reservaVuelos.Excepciones.RunTime.StringNoValidoException;
import com.reservaVuelos.Excepciones.RunTime.TelefonoNoValidoException;
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
    public void crear(CrearUsuarioDTO usuarioNuevoDTO) throws Exception {
        validarDatosUsuario(usuarioNuevoDTO);
        Long id=repo.ultimoID();
        Usuario usuarioNuevo = mapperUsuario.UsuarioDTOAUsuario(usuarioNuevoDTO,++id);
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
            throw new EntidadNoEncontradaException("Error, la persona no existe");
        }
        repo.eliminar(id);

    }

    @Override
    public void modificar(Long id, CrearUsuarioDTO usuarioModificar) throws Exception {

        Usuario usuarioExistente = repo.buscarPorID(id);
        if (usuarioExistente == null) {
            throw new EntidadNoEncontradaException("Error, la persona no existe");
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

    @Override
    public List<SalidaUsuarioDTO> obtenerListaReducida(String palabraBuscar) {
        return repo.buscarTodos().stream().map(mapperUsuario::UsuarioASalidaUsuarioDTO).toList();
    }

    private void validarDatosUsuario(CrearUsuarioDTO dto) {
        // Validación de Nombre
        if (!validacion.validarRangoLongitud(dto.nombre(), 1, 10)) {
            throw new StringNoValidoException("Nombre no valido");
        }

        if (!validacion.validarRangoLongitud(dto.apellido(), 1, 10)) {
            throw new StringNoValidoException("Apellido no valido");
        }

        if (!validacion.validarTelefono(dto.telefono())) {
            throw new TelefonoNoValidoException("Ingrese un telefono valido");
        }

        // Validación de longitud de Email
        if (!validacion.validarRangoLongitud(dto.usuarioEmail(), 10, 40)) {
            throw new StringNoValidoException("La longitud de caracteres del correo no es valida");
        }

        // Validación de formato de Email
        if (!validacion.correoEsValido(dto.usuarioEmail())) {
            throw new CorreoNoValidoException("Se debe usar un correo valido");
        }
        if(!validacion.documentoEsValidaGenerico(dto.usuarioPassaporteID())){
            throw new DocumentoNoValidoException("El pasaporte no es correcto");
        }
    }
}
