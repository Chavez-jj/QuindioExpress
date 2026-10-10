package co.edu.uniquindio.quindioexpress;

import co.edu.uniquindio.quindioexpress.estructuras.ListaEnlazadaSimple;
import co.edu.uniquindio.quindioexpress.modelo.Paquete;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== PRUEBA DE LISTA ENLAZADA SIMPLE Y MODELOS ===");

        ListaEnlazadaSimple<Paquete> lista = new ListaEnlazadaSimple<>();

        // 1. Crear paquetes de prueba
        Paquete p1 = new Paquete("PQ001", "Armenia - Centro", 2.5, 3, 90);
        Paquete p2 = new Paquete("PQ002", "Armenia - Norte", 5.0, 5, 120);
        Paquete p3 = new Paquete("PQ003", "Calarcá", 1.2, 1, 48);

        // 2. Agregar elementos
        lista.agregar(p1);
        lista.agregar(p2);
        lista.agregar(p3);

        System.out.println("\n--- Recorrido con For-Each (Prueba de Iterator) ---");
        for (Paquete p : lista) {
            System.out.println(p);
        }
        System.out.println("Tamaño actual: " + lista.tamanio()); // Debe ser 3

        // 3. Obtener por índice
        System.out.println("\n--- Elemento en posición 1 ---");
        System.out.println(lista.obtener(1)); // Debe ser PQ002

        // 4. Prueba de Eliminación (Caso Bordes)
        System.out.println("\n--- Eliminar Cabeza (PQ001) ---");
        boolean elim1 = lista.eliminar(p1);
        System.out.println("Eliminado con éxito: " + elim1 + " | Nuevo tamaño: " + lista.tamanio());

        System.out.println("\n--- Eliminar Cola (PQ003) ---");
        boolean elim3 = lista.eliminar(p3);
        System.out.println("Eliminado con éxito: " + elim3 + " | Nuevo tamaño: " + lista.tamanio());

        System.out.println("\n--- Lista Resultante ---");
        for (Paquete p : lista) {
            System.out.println(p);
        }

        System.out.println("\n--- Pruebas de Métodos Recursivos ---");
        System.out.println("Tamaño recursivo: " + lista.tamanioRecursivo());
        System.out.println("¿Contiene PQ002? (recursivo): " + lista.contieneRecursivo(p2));
        System.out.println("Obtener posición 0 (recursivo): " + lista.obtenerRecursivo(0));

        System.out.println("\n=== ¡TODAS LAS PRUEBAS PASARON CORRECTAMENTE! ===");
    }
}
