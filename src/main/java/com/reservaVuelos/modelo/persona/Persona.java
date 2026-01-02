package com.reservaVuelos.modelo.persona;

import com.reservaVuelos.modelo.Identificador;

public abstract class Persona implements Identificador {

    private String nombre; // 10 caracteres
    private String apellido; // 10 caracteres
    private String telefono; // 10 caracteres
    private int edad; // tamaño de int

    public Persona(String Nombre, String Apellido, String Telefono, int Edad) {
        this.nombre = Nombre;
        this.apellido = Apellido;
        this.telefono = Telefono;
        this.edad = Edad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }
}
