package com.reservaVuelos.modelo.vuelo;

import com.reservaVuelos.modelo.Identificador;

public class Asiento implements Identificador {

    private final Long idAsiento;           // tamaño de un Long
    private TipoDeAsiento tipoAsiento;      // 9 caracteres
    private boolean estadoAsiento = false;  // tamaño de un booblean

    public Asiento(Long idAsiento, TipoDeAsiento tipoAsiento) {
        this.idAsiento = idAsiento;
        this.tipoAsiento = tipoAsiento;
    }

    @Override
    public Long getId() {
        return idAsiento;
    }

    public TipoDeAsiento getTipoAsiento() {
        return tipoAsiento;
    }

    public void setTipoAsiento(TipoDeAsiento tipoAsiento) {
        this.tipoAsiento = tipoAsiento;
    }

    public boolean isEstadoAsiento() {
        return estadoAsiento;
    }

    public void setEstadoAsiento(boolean estadoAsiento) {
        this.estadoAsiento = estadoAsiento;
    }
}
