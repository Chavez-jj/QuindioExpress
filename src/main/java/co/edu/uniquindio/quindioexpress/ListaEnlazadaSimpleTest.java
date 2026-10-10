package co.edu.uniquindio.quindioexpress;

import co.edu.uniquindio.quindioexpress.estructuras.ListaEnlazadaSimple;

public class ListaEnlazadaSimpleTest {

    public static void main(String[] args) {
        System.out.println("=== PRUEBAS DE LOS 6 CASOS BORDE EN LISTA ENLAZADA SIMPLE ===\n");

        // Caso 1: Intentar borrar en lista vacía
        ListaEnlazadaSimple<String> listaVacia = new ListaEnlazadaSimple<>();
        boolean caso1 = listaVacia.eliminar("PQ001");
        System.out.println("1. Borrar en lista vacía -> Eliminado: " + caso1 + " | Esperado: false");

        // Caso 2: Lista con un solo elemento
        ListaEnlazadaSimple<String> listaUnElemento = new ListaEnlazadaSimple<>();
        listaUnElemento.agregar("PQ001");
        System.out.println("\n2. Lista con un elemento -> Tamaño: " + listaUnElemento.tamanio() + " | Elemento: " + listaUnElemento.obtener(0));

        // Caso 3: Borrar el primer elemento (cabeza)
        ListaEnlazadaSimple<String> lista3 = crearListaPrueba();
        boolean caso3 = lista3.eliminar("PQ001");
        System.out.println("\n3. Borrar primera posición (cabeza PQ001) -> Eliminado: " + caso3
                + " | Nuevo primero: " + lista3.obtener(0) + " | Tamaño: " + lista3.tamanio());

        // Caso 4: Borrar elemento intermedio
        ListaEnlazadaSimple<String> lista4 = crearListaPrueba();
        boolean caso4 = lista4.eliminar("PQ002");
        System.out.println("\n4. Borrar posición intermedia (PQ002) -> Eliminado: " + caso4
                + " | Posición 1 ahora es: " + lista4.obtener(1) + " | Tamaño: " + lista4.tamanio());

        // Caso 5: Borrar el último elemento (cola)
        ListaEnlazadaSimple<String> lista5 = crearListaPrueba();
        boolean caso5 = lista5.eliminar("PQ003");
        System.out.println("\n5. Borrar última posición (cola PQ003) -> Eliminado: " + caso5
                + " | Tamaño resultante: " + lista5.tamanio());

        // Caso 6: Borrar un elemento inexistente
        ListaEnlazadaSimple<String> lista6 = crearListaPrueba();
        boolean caso6 = lista6.eliminar("PQ999");
        System.out.println("\n6. Borrar elemento inexistente (PQ999) -> Eliminado: " + caso6
                + " | Tamaño sin cambios: " + lista6.tamanio());
    }

    private static ListaEnlazadaSimple<String> crearListaPrueba() {
        ListaEnlazadaSimple<String> lista = new ListaEnlazadaSimple<>();
        lista.agregar("PQ001");
        lista.agregar("PQ002");
        lista.agregar("PQ003");
        return lista;
    }
}