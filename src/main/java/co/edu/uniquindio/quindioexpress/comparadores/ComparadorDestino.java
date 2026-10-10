package co.edu.uniquindio.quindioexpress.comparadores;

import co.edu.uniquindio.quindioexpress.modelo.Paquete;
import java.util.Comparator;

/**
 * Ordena los paquetes alfabéticamente por destino (municipio).
 * Ejemplo: Armenia, Calarcá, Salento.
 */
public class ComparadorDestino implements Comparator<Paquete> {
    @Override
    public int compare(Paquete p1, Paquete p2) {
        return p1.getDestino().compareTo(p2.getDestino());
    }
}
