package com.reservaVuelos.modelo.persona;

import static com.reservaVuelos.modelo.persona.RolUsuario.USUARIO;

public class Usuario extends Persona {

    private String usuarioEmail; // 40 caracteres
    private String usuarioContrasenia; /*La constraseña ya encriptada
    contraseña con un maximo de 20 caracteres
    28 caracteres usando SHA-1
    */
    private final RolUsuario rolUsuario = USUARIO; // 13 caracteres
    private final Long usuarioPassaporteID; // 10 caracteres por el passaporte

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
