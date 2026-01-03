package com.reservaVuelos.repositorio;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.reservaVuelos.modelo.vuelo.Vuelo;
import com.reservaVuelos.modelo.vuelo.Ciudad;
import com.reservaVuelos.modelo.vuelo.Aerolinea;
import com.reservaVuelos.modelo.vuelo.Avion;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para gestión de Vuelos con RandomAccessFile
 * 
 * Tamaños de atributos (usando writeChars: 2 bytes por carácter):
 * - vueloID: Long = 8 bytes
 * - estado: boolean = 1 byte (true = activo / false = inactivo)
 * - aerolineaID: Long = 8 bytes
 * - origenVuelo: 12 caracteres × 2 = 24 bytes
 * - destinoVuelo: 12 caracteres × 2 = 24 bytes
 * - fechaHoraSalida: 29 caracteres × 2 = 58 bytes
 * - fechaHoraLlegada: 29 caracteres × 2 = 58 bytes
 * - estadoVuelo: boolean = 1 byte
 * - avionVueloID: Long = 8 bytes
 */
@Repository
public class RepositorioVuelos extends ArchivoDAO<Vuelo> {
    
    private final int ORIGEN_SIZE = 12;
    private final int DESTINO_SIZE = 12;
    private final int FECHA_HORA_SIZE = 29;
    private final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSSSSS");
    private static final Long TOTAL_RECORD_SIZE = 190L; // bytes por registro de Vuelo

    private RepositorioAerolineas repositorioAerolineas;
    private RepositorioAviones repositorioAviones;

    public RepositorioVuelos(RepositorioAerolineas repositorioAerolineas, RepositorioAviones repositorioAviones) {
        super("vuelos", TOTAL_RECORD_SIZE);
        this.repositorioAerolineas = repositorioAerolineas;
        this.repositorioAviones = repositorioAviones;
    }

    @Override
    protected byte[] convertirABytes(Vuelo vuelo) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        try {
            // Guardar los datos en el orden de la documentacion
            dos.writeLong(vuelo.getId());
            dos.writeBoolean(true);
            dos.writeLong(vuelo.getAerolinea().getId());
            dos.writeChars(ajustarString(vuelo.getOrigenVuelo().name(), ORIGEN_SIZE));
            dos.writeChars(ajustarString(vuelo.getDestinoVuelo().name(), DESTINO_SIZE));
            dos.writeChars(ajustarString(vuelo.getFechaHoraSalida().format(DATE_TIME_FORMATTER), FECHA_HORA_SIZE));
            dos.writeChars(ajustarString(vuelo.getFechaHoraLlegada().format(DATE_TIME_FORMATTER), FECHA_HORA_SIZE));
            dos.writeBoolean(vuelo.isEstadoVuelo());
            dos.writeLong(vuelo.getAvionVuelo().getId());

            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected Vuelo convertirDeBytes(byte[] bytes) {
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        DataInputStream dis = new DataInputStream(bais);

        try {
            // Leer en el orden de la documentacion
            Long vueloID = dis.readLong();
            dis.readBoolean();

            Long aerolineaID = dis.readLong();
            Aerolinea aerolinea = repositorioAerolineas.buscarPorID(aerolineaID);

            String origenStr = leerStringFijo(dis, ORIGEN_SIZE);
            Ciudad origen = Ciudad.valueOf(origenStr);
            
            String destinoStr = leerStringFijo(dis, DESTINO_SIZE);
            Ciudad destino = Ciudad.valueOf(destinoStr);
            
            String fechaSalidaStr = leerStringFijo(dis, FECHA_HORA_SIZE);
            LocalDateTime fechaSalida = LocalDateTime.parse(fechaSalidaStr, DATE_TIME_FORMATTER);
            
            String fechaLlegadaStr = leerStringFijo(dis, FECHA_HORA_SIZE);
            LocalDateTime fechaLlegada = LocalDateTime.parse(fechaLlegadaStr, DATE_TIME_FORMATTER);
            
            boolean estadoVuelo = dis.readBoolean();

            Long avionID = dis.readLong();
            Avion avion = repositorioAviones.buscarPorID(avionID);

            // Nota: Aquí se necesitaría obtener los objetos Aerolinea y Avion desde sus respectivos repositorios
            // Por ahora retornamos null para los objetos complejos
            return new Vuelo(vueloID, aerolinea, origen, destino, fechaSalida, fechaLlegada, estadoVuelo, avion);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
