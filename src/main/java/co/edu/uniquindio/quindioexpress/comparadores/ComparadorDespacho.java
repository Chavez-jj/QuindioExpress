package co.edu.uniquindio.quindioexpress.comparadores;

import co.edu.uniquindio.quindioexpress.modelo.Paquete;
import java.util.Comparator;

/**
 * Define qué paquete se despacha primero en la PriorityQueue:
 * 1. Mayor prioridad.
 * 2. Si empatan, menor tiempo estimado.
 * 3. Si siguen empatados, el código que vaya primero alfabéticamente.
 *
 * Reutiliza los comparadores de prioridad y tiempo, y el orden natural (código).
 */
public class ComparadorDespacho implements Comparator<Paquete> {

    private final ComparadorPrioridad comparadorPrioridad = new ComparadorPrioridad();
    private final ComparadorTiempo comparadorTiempo = new ComparadorTiempo();

    @Override
    public int compare(Paquete p1, Paquete p2) {
        int resultado = comparadorPrioridad.compare(p1, p2);
        if (resultado != 0) {
            return resultado;
        }
        resultado = comparadorTiempo.compare(p1, p2);
        if (resultado != 0) {
            return resultado;
        }
        return p1.compareTo(p2); // orden natural: código ascendente
    }
}
