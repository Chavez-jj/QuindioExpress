package co.edu.uniquindio.quindioexpress.comparadores;

import co.edu.uniquindio.quindioexpress.modelo.Paquete;
import java.util.Comparator;

/**
 * Ordena los paquetes por prioridad de MAYOR a MENOR.
 * Ejemplo: prioridad 5 va antes que prioridad 2.
 */
public class ComparadorPrioridad implements Comparator<Paquete> {
    @Override
    public int compare(Paquete p1, Paquete p2) {
        // Se invierte el orden (p2 primero) para que quede de mayor a menor
        return Integer.compare(p2.getPrioridad(), p1.getPrioridad());
    }
}
