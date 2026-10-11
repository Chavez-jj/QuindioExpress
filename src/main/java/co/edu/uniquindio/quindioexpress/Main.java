package co.edu.uniquindio.quindioexpress;

import co.edu.uniquindio.quindioexpress.comparadores.ComparadorPeso;
import co.edu.uniquindio.quindioexpress.comparadores.ComparadorPrioridad;
import co.edu.uniquindio.quindioexpress.comparadores.ComparadorTiempo;
import co.edu.uniquindio.quindioexpress.estructuras.ListaEnlazadaSimple;
import co.edu.uniquindio.quindioexpress.excepciones.QuindioExpressException;
import co.edu.uniquindio.quindioexpress.modelo.Paquete;
import co.edu.uniquindio.quindioexpress.modelo.Repartidor;
import co.edu.uniquindio.quindioexpress.servicio.CentroDistribucion;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println("=========================================================");
        System.out.println("   CENTRO DE DISTRIBUCIÓN QUINDÍOEXPRESS - VALIDACIÓN    ");
        System.out.println("=========================================================\n");

        CentroDistribucion centro = new CentroDistribucion("Centro Principal Armenia");

        // ---------------------------------------------------------------------
        // 1. REGISTRO DE REPARTIDORES (6 repartidores)
        // REP106 inicia NO disponible para probar esa regla.
        // ---------------------------------------------------------------------
        System.out.println("--- 1. Carga de Repartidores ---");
        centro.registrarRepartidor(new Repartidor("REP101", "Carlos Pérez", "Armenia", true));
        centro.registrarRepartidor(new Repartidor("REP102", "María Rodríguez", "Salento", true));
        centro.registrarRepartidor(new Repartidor("REP103", "Juan Gómez", "Calarcá", true));
        centro.registrarRepartidor(new Repartidor("REP104", "Ana Martínez", "Circasia", true));
        centro.registrarRepartidor(new Repartidor("REP105", "Luis Fernández", "Montenegro", true));
        centro.registrarRepartidor(new Repartidor("REP106", "Diana López", "Armenia", false));

        System.out.println("-> Total de repartidores registrados: " + centro.obtenerRepartidores().size());
        System.out.println("-> Consulta REP103: " + centro.buscarRepartidor("REP103") + "\n");

        // ---------------------------------------------------------------------
        // 2. REGISTRO DE PAQUETES (30 paquetes, códigos NO consecutivos)
        // Paquete(codigo, destino, peso, prioridad, tiempoEstimadoMinutos)
        // ---------------------------------------------------------------------
        System.out.println("--- 2. Carga de 30 Paquetes ---");
        Paquete[] paquetes = {
                new Paquete("PQ731", "Armenia", 4.5, 5, 45),
                new Paquete("PQ105", "Salento", 3.0, 5, 25),
                new Paquete("PQ942", "Armenia", 7.2, 4, 20),
                new Paquete("PQ318", "Calarcá", 2.1, 2, 30),
                new Paquete("PQ567", "Salento", 1.5, 1, 15),
                new Paquete("PQ829", "Armenia", 2.3, 3, 50),
                new Paquete("PQ420", "Circasia", 8.0, 3, 30),
                new Paquete("PQ112", "Montenegro", 12.5, 4, 40),
                new Paquete("PQ604", "Calarcá", 5.4, 2, 35),
                new Paquete("PQ203", "Salento", 3.8, 5, 22),

                new Paquete("PQ881", "Armenia", 6.1, 1, 60),
                new Paquete("PQ309", "Circasia", 4.2, 3, 28),
                new Paquete("PQ755", "Montenegro", 15.0, 5, 55),
                new Paquete("PQ190", "Calarcá", 1.2, 2, 12),
                new Paquete("PQ443", "Armenia", 9.3, 4, 42),
                new Paquete("PQ221", "Salento", 2.7, 3, 18),
                new Paquete("PQ908", "Circasia", 3.3, 1, 25),
                new Paquete("PQ512", "Montenegro", 7.8, 5, 38),
                new Paquete("PQ633", "Calarcá", 4.0, 4, 30),
                new Paquete("PQ801", "Armenia", 11.2, 2, 50),

                new Paquete("PQ350", "Salento", 0.9, 1, 10),
                new Paquete("PQ177", "Circasia", 5.0, 3, 32),
                new Paquete("PQ699", "Montenegro", 2.4, 4, 20),
                new Paquete("PQ842", "Calarcá", 8.6, 5, 48),
                new Paquete("PQ255", "Armenia", 3.6, 2, 27),
                new Paquete("PQ930", "Salento", 6.7, 4, 35),
                new Paquete("PQ404", "Circasia", 1.8, 1, 15),
                new Paquete("PQ588", "Montenegro", 10.1, 3, 45),
                new Paquete("PQ123", "Calarcá", 3.1, 5, 22),
                new Paquete("PQ777", "Armenia", 14.3, 5, 65)
        };

        for (Paquete p : paquetes) {
            centro.recibirPaquete(p);
        }
        System.out.println("-> Total de paquetes registrados: " + centro.cantidadPaquetes() + "\n");

        // ---------------------------------------------------------------------
        // 3. VALIDACIONES
        // ---------------------------------------------------------------------
        titulo("3. VALIDACIONES");
        try {
            centro.recibirPaquete(new Paquete("PQ731", "Armenia", 1.0, 3, 10));
        } catch (QuindioExpressException | IllegalArgumentException e) {
            System.out.println("Código repetido rechazado: " + e.getMessage());
        }
        try {
            new Paquete("PQ999", "Armenia", -5, 3, 10);
        } catch (QuindioExpressException | IllegalArgumentException e) {
            System.out.println("Peso inválido rechazado: " + e.getMessage());
        }
        try {
            new Paquete("PQ998", "Armenia", 2.0, 9, 10);
        } catch (QuindioExpressException | IllegalArgumentException e) {
            System.out.println("Prioridad inválida rechazada: " + e.getMessage());
        }
        try {
            new Paquete("PQ997", "  ", 2.0, 3, 10);
        } catch (QuindioExpressException | IllegalArgumentException e) {
            System.out.println("Destino vacío rechazado: " + e.getMessage());
        }
        try {
            centro.registrarRepartidor(new Repartidor("REP101", "Repetido", "Armenia"));
        } catch (QuindioExpressException | IllegalArgumentException e) {
            System.out.println("Repartidor repetido rechazado: " + e.getMessage());
        }
        System.out.println("Prioridad por defecto: " + new Paquete("PQ996", "Armenia", 1.0, 10).getPrioridad());

        // ---------------------------------------------------------------------
        // 4. CONSULTA POR CÓDIGO (HashMap)
        // ---------------------------------------------------------------------
        titulo("4. CONSULTA POR CÓDIGO (HashMap, O(1))");
        System.out.println("PQ731 -> " + centro.buscarPaquete("PQ731"));
        System.out.println("PQ000 -> " + centro.buscarPaquete("PQ000"));

        // ---------------------------------------------------------------------
        // 5. MUNICIPIOS Y AGRUPACIÓN
        // ---------------------------------------------------------------------
        titulo("5. MUNICIPIOS Y AGRUPACIÓN");
        System.out.println("Sin repetidos (HashSet): " + centro.obtenerDestinosUnicos());
        System.out.println("Alfabético (TreeSet): " + centro.obtenerDestinosOrdenados());
        System.out.println("Paquetes por municipio:");
        for (String municipio : centro.obtenerDestinosOrdenados()) {
            StringBuilder sb = new StringBuilder("  " + municipio + " -> ");
            for (Paquete p : centro.obtenerPaquetesPorDestino(municipio)) {
                sb.append(p.getCodigo()).append(" ");
            }
            System.out.println(sb.toString().trim());
        }

        // ---------------------------------------------------------------------
        // 6. ORDENAMIENTOS (Comparable y Comparator)
        // ---------------------------------------------------------------------
        titulo("6. ORDENAMIENTOS");
        System.out.println("Orden natural (código ascendente), primeros 8:");
        imprimirResumen(centro.ordenarPorCodigo(), 8, "codigo");
        System.out.println("Por peso (mayor a menor), primeros 5:");
        imprimirResumen(centro.ordenarPor(new ComparadorPeso()), 5, "peso");
        System.out.println("Por prioridad (mayor a menor), primeros 5:");
        imprimirResumen(centro.ordenarPor(new ComparadorPrioridad()), 5, "prioridad");
        System.out.println("Por tiempo (menor a mayor), primeros 5:");
        imprimirResumen(centro.ordenarPor(new ComparadorTiempo()), 5, "tiempo");

        // ---------------------------------------------------------------------
        // 7. ALGORITMOS DE ANÁLISIS (INTEGRANTE 3)
        // ---------------------------------------------------------------------
        System.out.println("\n=========================================================");
        System.out.println("   7. EJECUCIÓN DE ALGORITMOS DE ANÁLISIS (I3)          ");
        System.out.println("=========================================================");

        String municipioConsulta = "Armenia";
        System.out.printf("1. [RECURSIVIDAD] Peso total de paquetes para %s: %.1f kg%n",
                municipioConsulta, centro.pesoTotalMunicipio(municipioConsulta));
        System.out.printf("   [RECURSIVIDAD] Peso total de paquetes para Salento: %.1f kg%n",
                centro.pesoTotalMunicipio("Salento"));

        int prioridadMinima = 4;
        System.out.println("2. [RECURSIVIDAD] Cantidad de paquetes con prioridad >= " + prioridadMinima + ": "
                + centro.contarPrioridadMinima(prioridadMinima));

        String codigoBuscado = "PQ731";
        Paquete hallado = centro.buscarBinario(codigoBuscado);
        System.out.println("3. [BÚSQUEDA BINARIA] Buscar '" + codigoBuscado + "': "
                + (hallado != null ? hallado : "No encontrado"));
        System.out.println("   [BÚSQUEDA BINARIA] Buscar 'PQ000': "
                + (centro.buscarBinario("PQ000") != null ? "Encontrado" : "No encontrado"));

        Paquete masPesado = centro.paqueteMasPesado();
        System.out.println("4. [DIVIDE Y VENCERÁS] Paquete más pesado: "
                + (masPesado != null ? String.format("%s (%.1f kg)", masPesado.getCodigo(), masPesado.getPeso())
                : "Ninguno"));

        // ---------------------------------------------------------------------
        // 8. ATENCIÓN POR ORDEN DE LLEGADA (FIFO)
        // ---------------------------------------------------------------------
        titulo("8. ATENCIÓN POR ORDEN DE LLEGADA (FIFO)");
        System.out.println("Pendientes antes: " + centro.cantidadPendientes());
        for (int i = 0; i < 3; i++) {
            System.out.println("Atiende: " + centro.procesarSiguientePaquete().getCodigo());
        }

        // ---------------------------------------------------------------------
        // 9. DESPACHO PRIORITARIO (prioridad, tiempo, código)
        // ---------------------------------------------------------------------
        titulo("9. DESPACHO PRIORITARIO");
        for (int i = 0; i < 3; i++) {
            Paquete p = centro.despacharSiguientePrioritario();
            System.out.println("Despacha: " + p.getCodigo() + " (prioridad " + p.getPrioridad()
                    + ", " + p.getTiempoEstimado() + " min)");
        }
        System.out.println("Pendientes después: " + centro.cantidadPendientes());

        // ---------------------------------------------------------------------
        // 10. ASIGNACIÓN A REPARTIDORES
        // ---------------------------------------------------------------------
        titulo("10. ASIGNACIÓN A REPARTIDORES");
        intentarAsignar(centro, "PQ881", "REP101"); // válida (Armenia -> Armenia)
        intentarAsignar(centro, "PQ881", "REP101"); // ya asignado
        intentarAsignar(centro, "PQ255", "REP101"); // REP101 quedó ocupado
        intentarAsignar(centro, "PQ255", "REP106"); // REP106 no disponible
        intentarAsignar(centro, "PQ567", "REP103"); // zona distinta (Salento vs Calarcá)
        intentarAsignar(centro, "PQ000", "REP102"); // paquete inexistente
        intentarAsignar(centro, "PQ350", "REP999"); // repartidor inexistente

        // ---------------------------------------------------------------------
        // 11. ENTREGAS E HISTORIAL (lista simplemente enlazada propia)
        // ---------------------------------------------------------------------
        titulo("11. ENTREGAS E HISTORIAL");
        try {
            centro.registrarEntrega("PQ105");
            centro.registrarEntrega("PQ942");
            centro.registrarEntrega("PQ731");
            centro.registrarEntrega("PQ881"); // libera a REP101
        } catch (QuindioExpressException | IllegalArgumentException e) {
            System.out.println("Error durante la operación: " + e.getMessage());
        }

        StringBuilder historial = new StringBuilder();
        ListaEnlazadaSimple<Paquete> entregados = centro.obtenerHistorial();
        for (Paquete p : entregados) {
            if (historial.length() > 0) {
                historial.append(" → ");
            }
            historial.append(p.getCodigo());
        }
        System.out.println("Historial: " + historial);
        System.out.println("REP101 disponible de nuevo: " + centro.buscarRepartidor("REP101").isDisponible());

        try {
            centro.registrarEntrega("PQ105");
        } catch (QuindioExpressException | IllegalArgumentException e) {
            System.out.println("Doble entrega rechazada: " + e.getMessage());
        }

        System.out.println("\n=========================================================");
        System.out.println("   VALIDACIÓN COMPLETADA SATISFACTORIAMENTE              ");
        System.out.println("=========================================================");
    }

    // ------------------------------------------------------------- ayudas

    private static void intentarAsignar(CentroDistribucion centro, String codigo, String idRepartidor) {
        try {
            centro.asignarPaquete(codigo, idRepartidor);
            System.out.println("OK: " + codigo + " asignado a " + centro.obtenerRepartidorAsignado(codigo).getNombre());
        } catch (QuindioExpressException | IllegalArgumentException e) {
            System.out.println("RECHAZADO (" + codigo + " -> " + idRepartidor + "): " + e.getMessage());
        }
    }

    private static void imprimirResumen(List<Paquete> lista, int limite, String criterio) {
        for (int i = 0; i < Math.min(limite, lista.size()); i++) {
            Paquete p = lista.get(i);
            String valor;
            switch (criterio) {
                case "peso":
                    valor = String.format("%.1f kg", p.getPeso());
                    break;
                case "prioridad":
                    valor = "prioridad " + p.getPrioridad();
                    break;
                case "tiempo":
                    valor = p.getTiempoEstimado() + " min";
                    break;
                default:
                    valor = p.getDestino();
            }
            System.out.println("  " + p.getCodigo() + " - " + valor);
        }
    }

    private static void titulo(String texto) {
        System.out.println("\n--- " + texto + " ---");
    }
}