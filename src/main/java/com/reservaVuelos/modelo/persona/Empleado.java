package com.reservaVuelos.modelo.persona;

import static com.reservaVuelos.modelo.persona.RolUsuario.ADMINISTRADOR;

public class Empleado extends Persona {

    private String empleadoEmail;
    private String empleadoContrasenia;
    private final RolUsuario rolUsuario = ADMINISTRADOR;
    private final Long empleadoID;

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
