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

    // =========================================================================
    // MÉTODOS RECURSIVOS (Requisito de la asignatura / Integrante 3)
    // Patrón: Método público wrapper -> Método privado auxiliar con Nodo<T>
    // =========================================================================

    /**
     * Calcula el tamaño de la lista de forma recursiva (O(n) en tiempo y pila).
     */
    public int tamanioRecursivo() {
        return tamanioRecursivoAux(primerNodo);
    }

    private int tamanioRecursivoAux(Nodo<T> nodo) {
        // Caso base: lista vacía o fin de la lista
        if (nodo == null) {
            return 0;
        }
        // Paso recursivo: 1 + el tamaño del resto de la lista
        return 1 + tamanioRecursivoAux(nodo.getSiguiente());
    }

    /**
     * Busca un elemento en la lista de forma recursiva.
     */
    public boolean contieneRecursivo(T elemento) {
        if (elemento == null) return false;
        return contieneRecursivoAux(primerNodo, elemento);
    }

    private boolean contieneRecursivoAux(Nodo<T> nodo, T elemento) {
        // Caso base 1: no se encontró el elemento
        if (nodo == null) {
            return false;
        }
        // Caso base 2: elemento encontrado
        if (nodo.getDato().equals(elemento)) {
            return true;
        }
        // Paso recursivo: buscar en el siguiente nodo
        return contieneRecursivoAux(nodo.getSiguiente(), elemento);
    }

    /**
     * Obtiene un elemento por su índice de forma recursiva.
     */
    public T obtenerRecursivo(int posicion) {
        if (posicion < 0 || posicion >= tamanio) {
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + posicion);
        }
        return obtenerRecursivoAux(primerNodo, posicion, 0);
    }

    private T obtenerRecursivoAux(Nodo<T> nodo, int posicionDeseada, int indiceActual) {
        if (indiceActual == posicionDeseada) {
            return nodo.getDato();
        }
        return obtenerRecursivoAux(nodo.getSiguiente(), posicionDeseada, indiceActual + 1);
    }
}