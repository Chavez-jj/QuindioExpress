package co.edu.uniquindio.quindioexpress.estructuras;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Lista simplemente enlazada propia (sin colecciones del JCF internamente).
 * Mantiene referencia al primer y último nodo para garantizar inserción al final en O(1).
 *
 * @param <T> Tipo de elemento almacenado.
 */
public class ListaEnlazadaSimple<T> implements Coleccion<T> {

    private Nodo<T> primerNodo;
    private Nodo<T> ultimoNodo;
    private int tamanio;

    public ListaEnlazadaSimple() {
        this.primerNodo = null;
        this.ultimoNodo = null;
        this.tamanio = 0;
    }

    // --- MÉTODOS DE LA INTERFAZ COLECCION ---

    @Override
    public void agregar(T elemento) {
        if (elemento == null) {
            throw new IllegalArgumentException("No se pueden agregar elementos nulos.");
        }

        Nodo<T> nuevoNodo = new Nodo<>(elemento);

        if (estaVacia()) {
            primerNodo = nuevoNodo;
            ultimoNodo = nuevoNodo;
        } else {
            ultimoNodo.setSiguiente(nuevoNodo);
            ultimoNodo = nuevoNodo;
        }
        tamanio++;
    }

    @Override
    public boolean eliminar(T elemento) {
        if (estaVacia() || elemento == null) {
            return false;
        }

        // Caso 1: El elemento a eliminar está en la cabeza
        if (primerNodo.getDato().equals(elemento)) {
            primerNodo = primerNodo.getSiguiente();
            tamanio--;
            if (primerNodo == null) { // La lista quedó vacía
                ultimoNodo = null;
            }
            return true;
        }

        // Caso 2: El elemento está en el centro o al final
        Nodo<T> anterior = primerNodo;
        Nodo<T> actual = primerNodo.getSiguiente();

        while (actual != null) {
            if (actual.getDato().equals(elemento)) {
                anterior.setSiguiente(actual.getSiguiente());

                // Si eliminamos el último nodo, actualizamos ultimoNodo
                if (actual == ultimoNodo) {
                    ultimoNodo = anterior;
                }

                tamanio--;
                return true;
            }
            anterior = actual;
            actual = actual.getSiguiente();
        }

        return false; // No se encontró el elemento
    }

    @Override
    public T obtener(int posicion) {
        if (posicion < 0 || posicion >= tamanio) {
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + posicion);
        }

        Nodo<T> actual = primerNodo;
        for (int i = 0; i < posicion; i++) {
            actual = actual.getSiguiente();
        }
        return actual.getDato();
    }

    @Override
    public int tamanio() {
        return tamanio;
    }

    @Override
    public boolean estaVacia() {
        return tamanio == 0;
    }

    // --- SOPORTE PARA ITERABLE E ITERATOR ---

    @Override
    public Iterator<T> iterator() {
        return new IteradorLista();
    }

    /**
     * Clase interna privada que implementa Iterator<T> para permitir el recorrido con for-each.
     */
    private class IteradorLista implements Iterator<T> {

        private Nodo<T> actual = primerNodo;

        @Override
        public boolean hasNext() {
            return actual != null;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No hay más elementos en la lista.");
            }
            T dato = actual.getDato();
            actual = actual.getSiguiente();
            return dato;
        }
    }
}