package co.edu.uniquindio.quindioexpress.estructuras;

/**
 * Interfaz genérica base para las estructuras de datos propias del proyecto.
 * @param <T> Tipo de elemento almacenado en la colección.
 */
public interface Coleccion<T> extends Iterable<T> {

    void agregar(T elemento);

    boolean eliminar(T elemento);

    T obtener(int posicion);

    int tamanio();

    boolean estaVacia();
}
