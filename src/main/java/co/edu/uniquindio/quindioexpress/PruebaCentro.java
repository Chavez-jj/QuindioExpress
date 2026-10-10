package co.edu.uniquindio.quindioexpress;

import co.edu.uniquindio.quindioexpress.modelo.Paquete;
import co.edu.uniquindio.quindioexpress.servicio.CentroDistribucion;

public class PruebaCentro {
    public static void main(String[] args) {
        System.out.println("\n=== PRUEBA CENTRO DE DISTRIBUCIÓN (I2) ===");
        CentroDistribucion centro = new CentroDistribucion("QuindíoExpress");

        centro.recibirPaquete(new Paquete("PQ731", "Armenia", 4.5, 5, 30));
        centro.recibirPaquete(new Paquete("PQ105", "Calarcá", 2.0, 5, 20));
        centro.recibirPaquete(new Paquete("PQ420", "Armenia", 9.5, 3, 45));
        System.out.println("Paquetes registrados: " + centro.cantidadPaquetes()); // 3

        try {
            centro.recibirPaquete(new Paquete("PQ731", "Salento", 1.0, 2, 15));
        } catch (IllegalArgumentException e) {
            System.out.println("Rechazado: " + e.getMessage());
        }

        System.out.println("Buscar PQ420: " + centro.buscarPaquete("PQ420"));
        System.out.println("Buscar PQ999: " + centro.buscarPaquete("PQ999")); // null

        // --- Paso 2: municipios y agrupación ---
        centro.recibirPaquete(new Paquete("PQ942", "Salento", 1.8, 4, 25));
        centro.recibirPaquete(new Paquete("PQ318", "Calarcá", 6.0, 2, 50));

        System.out.println("\nMunicipios sin repetidos: " + centro.obtenerDestinosUnicos());
        System.out.println("Municipios ordenados: " + centro.obtenerDestinosOrdenados()); // [Armenia, Calarcá, Salento]

        System.out.println("\nPaquetes de Armenia:");
        for (Paquete p : centro.obtenerPaquetesPorDestino("Armenia")) {
            System.out.println("  " + p); // PQ731 y PQ420
        }
        System.out.println("Paquetes de Pereira: " + centro.obtenerPaquetesPorDestino("Pereira").tamanio()); // 0

        System.out.println("\nAgrupados por municipio:");
        for (String municipio : centro.agruparPaquetesPorDestino().keySet()) {
            System.out.println("  " + municipio + " -> " + centro.obtenerPaquetesPorDestino(municipio).tamanio() + " paquete(s)");
        }

        // --- Paso 3: atención por llegada y por prioridad ---
        System.out.println("\nPendientes: " + centro.cantidadPendientes()); // 5
        System.out.println("Atender por llegada: " + centro.procesarSiguientePaquete().getCodigo()); // PQ731

        System.out.println("Despacho por prioridad:");
        Paquete siguiente = centro.despacharSiguientePrioritario();
        while (siguiente != null) {
            System.out.println("  " + siguiente.getCodigo() + " (prioridad " + siguiente.getPrioridad()
                    + ", " + siguiente.getTiempoEstimado() + " min)");
            siguiente = centro.despacharSiguientePrioritario();
        }
        // Esperado: PQ105, PQ942, PQ420, PQ318 (PQ731 ya se atendió)
        System.out.println("Pendientes: " + centro.cantidadPendientes()); // 0
        System.out.println("Siguiente por llegada: " + centro.procesarSiguientePaquete()); // null
    }
}
