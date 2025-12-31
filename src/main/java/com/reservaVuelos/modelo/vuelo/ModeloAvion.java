package com.reservaVuelos.modelo.vuelo;

public enum ModeloAvion {
    // 1. Definimos las constantes con el número de asientos entre paréntesis
    AIRBUS_A320(150),
    BOEING_737(180),
    BOEING_707(140),
    BOEING_767(250),
    BOEING_787(300);

    // 2. Variable para almacenar la cantidad de asientos
    private final int capacidadAsientos;

    // 3. Constructor del Enum
    ModeloAvion(int capacidadAsientos) {
        this.capacidadAsientos = capacidadAsientos;
    }

    // 4. Método Getter para obtener la cantidad de asientos
    public int getCapacidadAsientos() {
        return capacidadAsientos;
    }
}