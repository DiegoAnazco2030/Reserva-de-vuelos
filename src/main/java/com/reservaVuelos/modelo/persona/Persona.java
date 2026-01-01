package com.reservaVuelos.modelo.persona;

import com.reservaVuelos.modelo.Identifcador;

public abstract class Persona implements Identifcador {

    private String personaNombre; // 10 caracteres
    private String personaApellido; // 10 caracteres
    private String personaTelefono; // 10 caracteres
    private int personaEdad; // 3 caracteres

    public Persona(String personaNombre, String personaApellido, String personaTelefono, int personaEdad) {
        this.personaNombre = personaNombre;
        this.personaApellido = personaApellido;
        this.personaTelefono = personaTelefono;
        this.personaEdad = personaEdad;
    }

    public String getPersonaNombre() {
        return personaNombre;
    }

    public void setPersonaNombre(String personaNombre) {
        this.personaNombre = personaNombre;
    }

    public String getPersonaApellido() {
        return personaApellido;
    }

    public void setPersonaApellido(String personaApellido) {
        this.personaApellido = personaApellido;
    }

    public String getPersonaTelefono() {
        return personaTelefono;
    }

    public void setPersonaTelefono(String personaTelefono) {
        this.personaTelefono = personaTelefono;
    }

    public int getPersonaEdad() {
        return personaEdad;
    }

    public void setPersonaEdad(int personaEdad) {
        this.personaEdad = personaEdad;
    }
}
