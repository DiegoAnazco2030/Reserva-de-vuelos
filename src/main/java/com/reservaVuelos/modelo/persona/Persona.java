package com.reservaVuelos.modelo.persona;

import com.reservaVuelos.modelo.Identificador;

public abstract class Persona implements Identificador {

    private String Nombre;
    private String Apellido;
    private String Telefono;
    private int Edad;

    public Persona(String Nombre, String Apellido, String Telefono, int Edad) {
        this.Nombre = Nombre;
        this.Apellido = Apellido;
        this.Telefono = Telefono;
        this.Edad = Edad;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        this.Nombre = nombre;
    }

    public String getApellido() {
        return Apellido;
    }

    public void setApellido(String apellido) {
        this.Apellido = apellido;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String telefono) {
        this.Telefono = telefono;
    }

    public int getEdad() {
        return Edad;
    }

    public void setEdad(int edad) {
        this.Edad = edad;
    }
}
