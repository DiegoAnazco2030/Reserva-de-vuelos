package com.reservaVuelos.repositorio;

public class RepositorioVuelos {
        // Tamaños fijos en bytes
    private static final int VUELO_ID_SIZE = 7;
    private static final int AEROLINEA_SIZE = 19;
    private static final int ORIGEN_VUELO_SIZE = 9;
    private static final int DESTINO_VUELO_SIZE = 9;
    private static final int FECHA_HORA_SALIDA_SIZE = 29;
    private static final int FECHA_HORA_LLEGADA_SIZE = 29;
    private static final int ESTADO_VUELO_SIZE = 1;
    private static final int AVION_VUELO_SIZE = 7;
    
    private static final int TOTAL_RECORD_SIZE = 111; // bytes por registro
}
