package co.edu.uniquindio.quindioexpress.servicio;

import co.edu.uniquindio.quindioexpress.comparadores.ComparadorDespacho;
import co.edu.uniquindio.quindioexpress.modelo.Paquete;
import co.edu.uniquindio.quindioexpress.modelo.Repartidor;
import co.edu.uniquindio.quindioexpress.estructuras.ListaEnlazadaSimple;

import java.util.*;

/**
 * Centro de Distribución de QuindíoExpress.
 * Cada colección resuelve una necesidad distinta del enunciado.
 */
public class CentroDistribucion {

    private String nombre;

    // Búsqueda por código en O(1) y control de códigos repetidos
    private final Map<String, Paquete> paquetesPorCodigo;
    // Registro de todos los paquetes en orden de llegada
    private final List<Paquete> paquetesEnOrden;
    // Atención por orden de llegada (FIFO)
    private final Queue<Paquete> colaLlegada;
    // Despacho por prioridad, luego tiempo, luego código
    private final PriorityQueue<Paquete> colaPrioridad;
    // Municipios sin repetidos y municipios ordenados alfabéticamente
    private final Set<String> municipios;
    private final Set<String> municipiosOrdenados;
    // Municipio -> paquetes de ese municipio
    private final Map<String, ListaEnlazadaSimple<Paquete>> paquetesPorMunicipio;

    public CentroDistribucion(String nombre) {
        this.nombre = nombre;
        this.paquetesPorCodigo = new HashMap<>();
        this.paquetesEnOrden = new ArrayList<>();
        this.colaLlegada = new ArrayDeque<>();
        this.colaPrioridad = new PriorityQueue<>(new ComparadorDespacho());
        this.municipios = new HashSet<>();
        this.municipiosOrdenados = new TreeSet<>();
        this.paquetesPorMunicipio = new HashMap<>();
    }

    /**
     * Registra un paquete en todas las colecciones.
     * Rechaza el paquete si ya existe otro con el mismo código.
     */
    public void recibirPaquete(Paquete paquete) {
        if (paquete == null) {
            throw new IllegalArgumentException("El paquete no puede ser nulo.");
        }
        if (paquetesPorCodigo.containsKey(paquete.getCodigo())) {
            throw new IllegalArgumentException("Ya existe un paquete con el código " + paquete.getCodigo());
        }

        paquetesPorCodigo.put(paquete.getCodigo(), paquete);
        paquetesEnOrden.add(paquete);
        colaLlegada.add(paquete);
        colaPrioridad.add(paquete);

        String municipio = paquete.getDestino();
        municipios.add(municipio);
        municipiosOrdenados.add(municipio);

        // Si el municipio aún no tiene lista, se crea; luego se agrega el paquete
        if (!paquetesPorMunicipio.containsKey(municipio)) {
            paquetesPorMunicipio.put(municipio, new ListaEnlazadaSimple<>());
        }
        paquetesPorMunicipio.get(municipio).agregar(paquete);
    }

    /**
     * Busca un paquete por su código sin recorrer todos los paquetes (O(1) promedio).
     * Retorna null si no existe.
     */
    public Paquete buscarPaquete(String codigo) {
        return paquetesPorCodigo.get(codigo);
    }

    /**
     * Atiende el siguiente paquete por orden de llegada (FIFO).
     * poll() saca el primero de la cola; retorna null si no hay pendientes.
     * También se quita de la cola de prioridad para que no se despache dos veces.
     */
    public Paquete procesarSiguientePaquete() {
        Paquete paquete = colaLlegada.poll();
        if (paquete != null) {
            colaPrioridad.remove(paquete);
        }
        return paquete;
    }

    /**
     * Despacha el siguiente paquete según ComparadorDespacho:
     * mayor prioridad, luego menor tiempo, luego código.
     * Retorna null si no hay pendientes.
     * También se quita de la cola de llegada para que no se atienda dos veces.
     */
    public Paquete despacharSiguientePrioritario() {
        Paquete paquete = colaPrioridad.poll();
        if (paquete != null) {
            colaLlegada.remove(paquete);
        }
        return paquete;
    }

    public int cantidadPendientes() {
        return colaLlegada.size();
    }

    public void registrarRepartidor(Repartidor repartidor) {
        throw new UnsupportedOperationException("Esqueleto I2: Pendiente por implementar.");
    }

    /**
     * Retorna el mapa municipio -> paquetes de ese municipio.
     * Se llena en recibirPaquete, así que aquí solo se entrega.
     */
    public Map<String, ListaEnlazadaSimple<Paquete>> agruparPaquetesPorDestino() {
        return paquetesPorMunicipio;
    }

    /**
     * Retorna los paquetes de un municipio, o una lista vacía si no hay ninguno.
     */
    public ListaEnlazadaSimple<Paquete> obtenerPaquetesPorDestino(String municipio) {
        ListaEnlazadaSimple<Paquete> lista = paquetesPorMunicipio.get(municipio);
        if (lista == null) {
            return new ListaEnlazadaSimple<>();
        }
        return lista;
    }

    /**
     * Municipios con paquetes, sin repetidos (HashSet: sin orden).
     * Se retorna una copia para que desde afuera no se pueda modificar el original.
     */
    public Set<String> obtenerDestinosUnicos() {
        return new HashSet<>(municipios);
    }

    /**
     * Municipios con paquetes, sin repetidos y en orden alfabético (TreeSet).
     */
    public Set<String> obtenerDestinosOrdenados() {
        return new TreeSet<>(municipiosOrdenados);
    }

    /**
     * Retorna una copia de todos los paquetes registrados, ordenada con el comparador que se pase
     * (ComparadorPeso, ComparadorTiempo, ComparadorPrioridad, ComparadorDestino...).
     * Se ordena una copia para no dañar el orden de llegada original.
     */
    public List<Paquete> ordenarPor(Comparator<Paquete> comparador) {
        List<Paquete> copia = new ArrayList<>(paquetesEnOrden);
        copia.sort(comparador);
        return copia;
    }

    /**
     * Retorna una copia de los paquetes en su orden natural (Comparable: código ascendente).
     * Esta lista sirve para la búsqueda binaria por código.
     */
    public List<Paquete> ordenarPorCodigo() {
        List<Paquete> copia = new ArrayList<>(paquetesEnOrden);
        Collections.sort(copia);
        return copia;
    }

    public int cantidadPaquetes() {
        return paquetesEnOrden.size();
    }
}
