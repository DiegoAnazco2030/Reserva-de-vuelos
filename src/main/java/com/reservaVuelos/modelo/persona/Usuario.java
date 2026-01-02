package com.reservaVuelos.modelo.persona;

import static com.reservaVuelos.modelo.persona.RolUsuario.USUARIO;

public class Usuario extends Persona {

    private String usuarioEmail; // 40 caracteres
    private String usuarioContrasenia; /*La constraseña ya encriptada
    contraseña con un maximo de 20 caracteres
    28 caracteres usando SHA-1
    */
    private final RolUsuario rolUsuario = USUARIO; // Esto no va ya que el repoUsuario y repoEmpleado estan separados
    private final String usuarioPassaporteID; // 9 caracteres por pasaporte
    private final Long usuarioID; // tamaño de Long

    public Usuario(String personaNombre, String personaApellido, String personaTelefono, int personaEdad,
        String usuarioPassaporteID, String usuarioContrasenia, String usuarioEmail, Long usuarioID) {
        super(personaNombre, personaApellido, personaTelefono, personaEdad);
        this.usuarioPassaporteID = usuarioPassaporteID;
        this.usuarioContrasenia = usuarioContrasenia;
        this.usuarioEmail = usuarioEmail;
        this.usuarioID = usuarioID;
    }

    @Override
    public Long getId(){
        return usuarioID;
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

    public String getUsuarioPassaporteID() {
        return usuarioPassaporteID;
    }

    public RolUsuario getRolUsuario() {
        return rolUsuario;
    }

}
