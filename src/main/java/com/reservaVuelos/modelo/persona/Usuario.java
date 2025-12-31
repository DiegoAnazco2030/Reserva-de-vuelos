package com.reservaVuelos.modelo.persona;

import static com.reservaVuelos.modelo.persona.RolUsuario.USUARIO;

public class Usuario extends Persona {

    private String usuarioEmail;
    private String usuarioContrasenia; //La constraseña ya encriptada
    private final RolUsuario rolUsuario = USUARIO;
    private final Long usuarioPassaporteID;

    public Usuario(String personaNombre, String personaApellido, String personaTelefono, int personaEdad, Long usuarioPassaporteID, String usuarioContrasenia, String usuarioEmail) {
        super(personaNombre, personaApellido, personaTelefono, personaEdad);
        this.usuarioPassaporteID = usuarioPassaporteID;
        this.usuarioContrasenia = usuarioContrasenia;
        this.usuarioEmail = usuarioEmail;
    }

    @Override
    public Long getId(){
        return usuarioPassaporteID;
    }

    public String getUsuarioEmail() {
        return usuarioEmail;
    }

    public void setUsuarioEmail(String usuarioEmail) {
        this.usuarioEmail = usuarioEmail;
    }

    public String getUsuarioContrasenia() {
        return usuarioContrasenia;
    }

    public void setUsuarioContrasenia(String usuarioContrasenia) {
        this.usuarioContrasenia = usuarioContrasenia;
    }

    public RolUsuario getRolUsuario() {
        return rolUsuario;
    }

}
