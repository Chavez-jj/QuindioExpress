package co.edu.uniquindio.quindioexpress.comparadores;

import co.edu.uniquindio.quindioexpress.modelo.Paquete;
import java.util.Comparator;

/**
 * Ordena los paquetes por peso de MAYOR a MENOR.
 * Ejemplo: 18.7 kg va antes que 4.5 kg.
 */
public class ComparadorPeso implements Comparator<Paquete> {
    @Override
    public int compare(Paquete p1, Paquete p2) {
        // Double.compare evita errores de restar decimales; p2 primero para mayor a menor
        return Double.compare(p2.getPeso(), p1.getPeso());
    }
}
