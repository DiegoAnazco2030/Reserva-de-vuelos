package com.reservaVuelos.modelo.persona;

import static com.reservaVuelos.modelo.persona.RolUsuario.ADMINISTRADOR;

public class Empleado extends Persona {

    private String empleadoEmail; // 40 caracteres
    private String empleadoContrasenia;/*La constraseña ya encriptada
    contraseña con un maximo de 20 caracteres
    28 caracteres usando SHA-1
    */
    private final RolUsuario rolUsuario = ADMINISTRADOR; // Esto no va ya que el repoEmpleado y repoUsuario estan separados
    private final Long empleadoID; // tamaño de Long

    public Empleado(String personaNombre, String personaApellido, String personaTelefono, int personaEdad, Long empleadoID, String empleadoContrasenia, String empleadoEmail) {
        super(personaNombre, personaApellido, personaTelefono, personaEdad);
        this.empleadoID = empleadoID;
        this.empleadoContrasenia = empleadoContrasenia;
        this.empleadoEmail = empleadoEmail;
    }

    @Override
    public Long getId(){
        return this.empleadoID;
    }

    public String getEmpleadoEmail() {
        return empleadoEmail;
    }

    public void setEmpleadoEmail(String empleadoEmail) {
        this.empleadoEmail = empleadoEmail;
    }

    public String getEmpleadoContrasenia() {
        return empleadoContrasenia;
    }

    public void setEmpleadoContrasenia(String empleadoContrasenia) {
        this.empleadoContrasenia = empleadoContrasenia;
    }

    public RolUsuario getRolUsuario() {
        return rolUsuario;
    }

}
