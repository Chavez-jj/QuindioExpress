package co.edu.uniquindio.quindioexpress.comparadores;

import co.edu.uniquindio.quindioexpress.modelo.Paquete;
import java.util.Comparator;

/**
 * Ordena los paquetes por tiempo estimado de MENOR a MAYOR.
 * Ejemplo: 20 minutos va antes que 45 minutos.
 */
public class ComparadorTiempo implements Comparator<Paquete> {
    @Override
    public int compare(Paquete p1, Paquete p2) {
        return Integer.compare(p1.getTiempoEstimado(), p2.getTiempoEstimado());
    }
}
