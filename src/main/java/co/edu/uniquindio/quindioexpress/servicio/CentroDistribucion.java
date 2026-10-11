package co.edu.uniquindio.quindioexpress.servicio;

import co.edu.uniquindio.quindioexpress.algoritmos.AnalisisPaquetes;
import co.edu.uniquindio.quindioexpress.comparadores.ComparadorDespacho;
import co.edu.uniquindio.quindioexpress.modelo.Paquete;
import co.edu.uniquindio.quindioexpress.modelo.Repartidor;
import co.edu.uniquindio.quindioexpress.estructuras.ListaEnlazadaSimple;
import co.edu.uniquindio.quindioexpress.excepciones.QuindioExpressException;

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
    // Repartidores por identificación: búsqueda O(1) y control de repetidos
    private final Map<String, Repartidor> repartidores;
    // Código del paquete -> repartidor al que se asignó
    private final Map<String, Repartidor> asignaciones;
    // Historial de entregas en el orden real en que se entregaron (lista propia)
    private final ListaEnlazadaSimple<Paquete> historialEntregas;

    public CentroDistribucion(String nombre) {
        this.nombre = nombre;
        this.paquetesPorCodigo = new HashMap<>();
        this.paquetesEnOrden = new ArrayList<>();
        this.colaLlegada = new ArrayDeque<>();
        this.colaPrioridad = new PriorityQueue<>(new ComparadorDespacho());
        this.municipios = new HashSet<>();
        this.municipiosOrdenados = new TreeSet<>();
        this.paquetesPorMunicipio = new HashMap<>();
        this.repartidores = new HashMap<>();
        this.asignaciones = new HashMap<>();
        this.historialEntregas = new ListaEnlazadaSimple<>();
    }

    /**
     * Registra un paquete en todas las colecciones.
     * Rechaza el paquete si ya existe otro con el mismo código.
     */
    public void recibirPaquete(Paquete paquete) {
        if (paquete == null) {
            throw new QuindioExpressException("El paquete no puede ser nulo.");
        }
        if (paquetesPorCodigo.containsKey(paquete.getCodigo())) {
            throw new QuindioExpressException("Ya existe un paquete con el código " + paquete.getCodigo());
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

    /**
     * Registra un repartidor. Rechaza identificaciones repetidas.
     */
    public void registrarRepartidor(Repartidor repartidor) {
        if (repartidor == null) {
            throw new QuindioExpressException("El repartidor no puede ser nulo.");
        }
        if (repartidores.containsKey(repartidor.getIdentificacion())) {
            throw new QuindioExpressException("Ya existe un repartidor con la identificación " + repartidor.getIdentificacion());
        }
        repartidores.put(repartidor.getIdentificacion(), repartidor);
    }

    /**
     * Busca un repartidor por identificación (O(1) promedio). Retorna null si no existe.
     */
    public Repartidor buscarRepartidor(String identificacion) {
        return repartidores.get(identificacion);
    }

    /**
     * Retorna una copia de los repartidores registrados.
     */
    public List<Repartidor> obtenerRepartidores() {
        return new ArrayList<>(repartidores.values());
    }

    /**
     * Asigna un paquete a un repartidor solo si:
     * el paquete existe, no está entregado ni asignado, el repartidor existe,
     * está disponible y su zona es el destino del paquete.
     * Al asignarlo, el repartidor queda no disponible hasta que entregue.
     */
    public void asignarPaquete(String codigo, String identificacion) {
        Paquete paquete = paquetesPorCodigo.get(codigo);
        if (paquete == null) {
            throw new QuindioExpressException("No existe el paquete " + codigo);
        }
        if (historialEntregas.contieneRecursivo(paquete)) {
            throw new QuindioExpressException("El paquete " + codigo + " ya fue entregado.");
        }
        if (asignaciones.containsKey(codigo)) {
            throw new QuindioExpressException("El paquete " + codigo + " ya está asignado.");
        }
        Repartidor repartidor = repartidores.get(identificacion);
        if (repartidor == null) {
            throw new QuindioExpressException("No existe el repartidor " + identificacion);
        }
        if (!repartidor.isDisponible()) {
            throw new QuindioExpressException("El repartidor " + repartidor.getNombre() + " no está disponible.");
        }
        if (!repartidor.getZona().equalsIgnoreCase(paquete.getDestino())) {
            throw new QuindioExpressException("La zona de " + repartidor.getNombre() + " (" + repartidor.getZona()
                    + ") no coincide con el destino del paquete (" + paquete.getDestino() + ").");
        }
        asignaciones.put(codigo, repartidor);
        repartidor.setDisponible(false);
    }

    /**
     * Retorna el repartidor asignado a un paquete, o null si no tiene.
     */
    public Repartidor obtenerRepartidorAsignado(String codigo) {
        return asignaciones.get(codigo);
    }

    /**
     * Registra la entrega de un paquete: lo saca de las colas de pendientes (si sigue ahí)
     * y lo agrega al final del historial. Si tenía repartidor, este queda disponible otra vez.
     */
    public void registrarEntrega(String codigo) {
        Paquete paquete = paquetesPorCodigo.get(codigo);
        if (paquete == null) {
            throw new QuindioExpressException("No existe el paquete " + codigo);
        }
        if (historialEntregas.contieneRecursivo(paquete)) {
            throw new QuindioExpressException("El paquete " + codigo + " ya fue entregado.");
        }
        colaLlegada.remove(paquete);
        colaPrioridad.remove(paquete);
        historialEntregas.agregar(paquete);

        Repartidor repartidor = asignaciones.remove(codigo);
        if (repartidor != null) {
            repartidor.setDisponible(true);
        }
    }

    /**
     * Retorna el historial de entregas en el orden en que ocurrieron.
     */
    /**
     * Retorna el historial de paquetes entregados.
     * Aplica una copia defensiva para proteger el encapsulamiento de la estructura interna.
     */
    public ListaEnlazadaSimple<Paquete> obtenerHistorial() {
        ListaEnlazadaSimple<Paquete> copia = new ListaEnlazadaSimple<>();
        for (Paquete p : historialEntregas) {
            copia.agregar(p);
        }
        return copia;
    }

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

    // =========================================================================
    // ANÁLISIS: estos métodos solo pasan los datos del centro a AnalisisPaquetes
    // (la lógica recursiva, binaria y de divide y vencerás está en esa clase).
    // =========================================================================

    public double pesoTotalMunicipio(String municipio) {
        return AnalisisPaquetes.pesoTotalMunicipio(paquetesEnOrden, municipio);
    }

    public int contarPrioridadMinima(int prioridadMinima) {
        return AnalisisPaquetes.contarPrioridadMinima(paquetesEnOrden, prioridadMinima);
    }

    /**
     * La búsqueda binaria necesita la lista ordenada por código, por eso se usa ordenarPorCodigo().
     */
    public Paquete buscarBinario(String codigo) {
        return AnalisisPaquetes.buscarBinario(ordenarPorCodigo(), codigo);
    }

    public Paquete paqueteMasPesado() {
        return AnalisisPaquetes.paqueteMasPesado(paquetesEnOrden);
    }

    public int cantidadPaquetes() {
        return paquetesEnOrden.size();
    }
}