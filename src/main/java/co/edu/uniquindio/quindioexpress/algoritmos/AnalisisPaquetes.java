package co.edu.uniquindio.quindioexpress.algoritmos;

import co.edu.uniquindio.quindioexpress.modelo.Paquete;

import java.util.List;

/**
 * Algoritmos de análisis para el sistema QuindíoExpress (Integrante 3).
 */
public class AnalisisPaquetes {

    /**
     * Suma recursiva del peso de los paquetes cuyo destino es el municipio dado.
     * RECURSIVIDAD PURA (Sin ciclos for, while ni do-while).
     */
    public static double pesoTotalMunicipio(List<Paquete> paquetes, String municipio) {
        if (paquetes == null || municipio == null) {
            return 0.0;
        }
        return pesoTotalMunicipioAuxiliar(paquetes, municipio, 0);
    }

    private static double pesoTotalMunicipioAuxiliar(List<Paquete> paquetes, String municipio, int indice) {
        // Caso Base: se llegó al final de la lista
        if (indice >= paquetes.size()) {
            return 0.0;
        }

        Paquete actual = paquetes.get(indice);
        double pesoActual = 0.0;

        if (actual != null && actual.getDestino() != null && actual.getDestino().equalsIgnoreCase(municipio)) {
            pesoActual = actual.getPeso();
        }

        // Caso Recursivo
        return pesoActual + pesoTotalMunicipioAuxiliar(paquetes, municipio, indice + 1);
    }

    /**
     * Cuenta de forma recursiva cuántos paquetes tienen prioridad >= prioridadMinima.
     * RECURSIVIDAD PURA (Sin ciclos for, while ni do-while).
     */
    public static int contarPrioridadMinima(List<Paquete> paquetes, int prioridadMinima) {
        if (paquetes == null) {
            return 0;
        }
        return contarPrioridadMinimaAuxiliar(paquetes, prioridadMinima, 0);
    }

    private static int contarPrioridadMinimaAuxiliar(List<Paquete> paquetes, int prioridadMinima, int indice) {
        // Caso Base
        if (indice >= paquetes.size()) {
            return 0;
        }

        Paquete actual = paquetes.get(indice);
        int coincidencia = (actual != null && actual.getPrioridad() >= prioridadMinima) ? 1 : 0;

        // Caso Recursivo
        return coincidencia + contarPrioridadMinimaAuxiliar(paquetes, prioridadMinima, indice + 1);
    }

    /**
     * Búsqueda binaria por código. Precondición: la lista está ordenada por código ascendente.
     * Retorna null si no lo encuentra.
     */
    public static Paquete buscarBinario(List<Paquete> paquetesOrdenadosPorCodigo, String codigo) {
        if (paquetesOrdenadosPorCodigo == null || codigo == null) {
            return null;
        }

        int inicio = 0;
        int fin = paquetesOrdenadosPorCodigo.size() - 1;

        while (inicio <= fin) {
            int medio = inicio + (fin - inicio) / 2;
            Paquete paqueteMedio = paquetesOrdenadosPorCodigo.get(medio);

            if (paqueteMedio == null) {
                return null;
            }

            int comparacion = paqueteMedio.getCodigo().compareTo(codigo);

            if (comparacion == 0) {
                return paqueteMedio; // Encontrado
            } else if (comparacion < 0) {
                inicio = medio + 1; // Buscar en la mitad derecha
            } else {
                fin = medio - 1; // Buscar en la mitad izquierda
            }
        }

        return null; // No encontrado
    }

    /**
     * Encuentra el paquete de mayor peso con la estrategia DIVIDE Y VENCERÁS.
     * Retorna null si la lista está vacía.
     */
    public static Paquete paqueteMasPesado(List<Paquete> paquetes) {
        if (paquetes == null || paquetes.isEmpty()) {
            return null;
        }
        return paqueteMasPesadoAuxiliar(paquetes, 0, paquetes.size() - 1);
    }

    private static Paquete paqueteMasPesadoAuxiliar(List<Paquete> paquetes, int inicio, int fin) {
        // Caso Base: un solo paquete en la sublista
        if (inicio == fin) {
            return paquetes.get(inicio);
        }

        // 1. División del problema
        int medio = inicio + (fin - inicio) / 2;

        // 2. Subproblemas (Izquierdo y Derecho)
        Paquete masPesadoIzq = paqueteMasPesadoAuxiliar(paquetes, inicio, medio);
        Paquete masPesadoDer = paqueteMasPesadoAuxiliar(paquetes, medio + 1, fin);

        // 3. Combinación de resultados
        if (masPesadoIzq == null) return masPesadoDer;
        if (masPesadoDer == null) return masPesadoIzq;

        return (masPesadoIzq.getPeso() >= masPesadoDer.getPeso()) ? masPesadoIzq : masPesadoDer;
    }
}