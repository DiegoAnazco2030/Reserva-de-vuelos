package com.reservaVuelos.modelo.vuelo;

public enum ModeloAvion {

    AIRBUS_A320(150),
    BOEING_737(180),
    BOEING_707(140),
    BOEING_767(250),
    BOEING_787(300);

    private final int capacidadAsientos;

    ModeloAvion(int capacidadAsientos) {
        this.capacidadAsientos = capacidadAsientos;
    }

    public int getCapacidadAsientos() {
        return capacidadAsientos;
    }
}