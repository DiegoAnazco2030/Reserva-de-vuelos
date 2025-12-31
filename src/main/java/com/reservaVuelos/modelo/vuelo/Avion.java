package com.reservaVuelos.modelo.vuelo;

import com.reservaVuelos.modelo.Identificador;

import java.util.ArrayList;
import java.util.List;

import static com.reservaVuelos.modelo.vuelo.TipoDeAsiento.*;


public class Avion implements Identificador {

    private final Long idAvion;
    private ModeloAvion modeloAvion;
    private int capacidad;
    private final List<Asiento> asientosAvion = new ArrayList<>();

    //Contadores de los asientos
    private int cantAsientoPrimera;
    private int cantAsientoEjecutiva;
    private int cantAsientoTurista;

    public Avion(Long idAvion, ModeloAvion modeloAvion) {
        this.idAvion = idAvion;
        this.modeloAvion = modeloAvion;
        this.capacidad = modeloAvion.getCapacidadAsientos();

        //Cantidad de asientos
        cantAsientoPrimera = (int) (capacidad * 0.1);
        cantAsientoEjecutiva = (int) (capacidad * 0.3);
        cantAsientoTurista =  capacidad -  cantAsientoPrimera - cantAsientoEjecutiva;
        //Relleno mi avion
        for(int i =1 ; i<= capacidad; i++){
            if(i < cantAsientoPrimera){
                asientosAvion.add(new Asiento(idAvion + i, PRIMERA));
            }else  if(i < cantAsientoEjecutiva){
                asientosAvion.add(new Asiento(idAvion + i, EJECUTIVA));
            }else asientosAvion.add(new Asiento(idAvion + i, TURISTA));
        }
    }

    @Override
    public Long getId() {
        return idAvion;
    }

    public ModeloAvion getModeloAvion() {
        return modeloAvion;
    }

    public void setModeloAvion(ModeloAvion modeloAvion) {
        this.modeloAvion = modeloAvion;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public List<Asiento> getAsientosAvion() {
        return asientosAvion;
    }
}
