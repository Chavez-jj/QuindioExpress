package co.edu.uniquindio.quindioexpress;

import co.edu.uniquindio.quindioexpress.comparadores.*;
import co.edu.uniquindio.quindioexpress.modelo.Paquete;
import co.edu.uniquindio.quindioexpress.modelo.Repartidor;
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

        // --- Paso 4: ordenar con los comparadores ---
        // El registro en orden de llegada se conserva aunque ya se hayan atendido
        System.out.println("\nPor código (Comparable):   " + codigos(centro.ordenarPorCodigo()));
        System.out.println("Por prioridad (mayor a menor): " + codigos(centro.ordenarPor(new ComparadorPrioridad())));
        System.out.println("Por peso (mayor a menor):  " + codigos(centro.ordenarPor(new ComparadorPeso())));
        System.out.println("Por tiempo (menor a mayor): " + codigos(centro.ordenarPor(new ComparadorTiempo())));
        System.out.println("Por destino (alfabético):  " + codigos(centro.ordenarPor(new ComparadorDestino())));

        // --- Paso 5: repartidores, asignación, entregas e historial ---
        // Centro nuevo para que haya paquetes pendientes
        CentroDistribucion centro2 = new CentroDistribucion("QuindíoExpress 2");
        centro2.recibirPaquete(new Paquete("PQ731", "Armenia", 4.5, 5, 30));
        centro2.recibirPaquete(new Paquete("PQ105", "Calarcá", 2.0, 5, 20));
        centro2.recibirPaquete(new Paquete("PQ420", "Armenia", 9.5, 3, 45));

        centro2.registrarRepartidor(new Repartidor("R01", "Ana", "Armenia"));
        centro2.registrarRepartidor(new Repartidor("R02", "Luis", "Calarcá"));
        centro2.registrarRepartidor(new Repartidor("R03", "Sofía", "Armenia", false));
        System.out.println("\nRepartidores registrados: " + centro2.obtenerRepartidores().size()); // 3
        System.out.println("Buscar R02: " + centro2.buscarRepartidor("R02"));

        probar("Registrar R01 repetido", () -> centro2.registrarRepartidor(new Repartidor("R01", "Pedro", "Salento")));
        probar("Asignar PQ731 a R01 (Ana, Armenia)", () -> centro2.asignarPaquete("PQ731", "R01"));       // OK
        probar("Asignar PQ420 a R01 (ocupada)", () -> centro2.asignarPaquete("PQ420", "R01"));            // no disponible
        probar("Asignar PQ420 a R03 (no disponible)", () -> centro2.asignarPaquete("PQ420", "R03"));      // no disponible
        probar("Asignar PQ420 a R02 (otra zona)", () -> centro2.asignarPaquete("PQ420", "R02"));          // zona
        probar("Asignar PQ105 a R99 (no existe)", () -> centro2.asignarPaquete("PQ105", "R99"));          // no existe
        probar("Asignar PQ105 a R02 (Luis, Calarcá)", () -> centro2.asignarPaquete("PQ105", "R02"));      // OK

        centro2.registrarEntrega("PQ105");
        centro2.registrarEntrega("PQ731");
        System.out.println("Ana disponible otra vez: " + centro2.buscarRepartidor("R01").isDisponible()); // true
        probar("Entregar PQ731 otra vez", () -> centro2.registrarEntrega("PQ731"));

        System.out.println("Historial de entregas (en orden): " + codigosLista(centro2.obtenerHistorial())); // PQ105 PQ731
        System.out.println("Pendientes: " + centro2.cantidadPendientes()); // 1 (PQ420)

        // --- Paso 6: análisis (los implementa el Integrante 3 en AnalisisPaquetes) ---
        System.out.println();
        probarAnalisis("Peso total Armenia", () -> centro.pesoTotalMunicipio("Armenia"));          // 14.0
        probarAnalisis("Prioridad >= 4", () -> centro.contarPrioridadMinima(4));                   // 3
        probarAnalisis("Búsqueda binaria PQ420", () -> centro.buscarBinario("PQ420"));             // PQ420
        probarAnalisis("Paquete más pesado", () -> centro.paqueteMasPesado());                     // PQ420
    }

    // Muestra el resultado, o "pendiente" si AnalisisPaquetes aún no tiene ese método hecho
    private static void probarAnalisis(String descripcion, java.util.function.Supplier<Object> operacion) {
        try {
            System.out.println(descripcion + ": " + operacion.get());
        } catch (UnsupportedOperationException e) {
            System.out.println(descripcion + ": pendiente (Integrante 3)");
        }
    }

    // Ejecuta una operación y muestra si se aceptó o por qué se rechazó
    private static void probar(String descripcion, Runnable operacion) {
        try {
            operacion.run();
            System.out.println(descripcion + ": OK");
        } catch (IllegalArgumentException e) {
            System.out.println(descripcion + ": rechazado -> " + e.getMessage());
        }
    }

    private static String codigosLista(Iterable<Paquete> paquetes) {
        String texto = "";
        for (Paquete p : paquetes) {
            texto += p.getCodigo() + " ";
        }
        return texto;
    }

    // Convierte una lista de paquetes en el texto de sus códigos, para imprimir corto
    private static String codigos(java.util.List<Paquete> paquetes) {
        String texto = "";
        for (Paquete p : paquetes) {
            texto += p.getCodigo() + " ";
        }
        return texto;
    }
}
