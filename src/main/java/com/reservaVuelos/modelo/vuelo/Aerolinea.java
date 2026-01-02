package com.reservaVuelos.modelo.vuelo;

import com.reservaVuelos.modelo.Identificador;

public class Aerolinea implements Identificador {
    
    private final Long aerolineaID;       // tamaño de Long
    private String nombre;                // 30 caracteres
    private String telefono;              // 10 caracteres
    private String email;                 // 40 caracteres
    
    public Aerolinea(Long aerolineaID, String nombre, String telefono, String email) {
        this.aerolineaID = aerolineaID;
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
    }
    
    @Override
    public Long getId() {
        return aerolineaID;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getTelefono() {
        return telefono;
    }
    
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
}
