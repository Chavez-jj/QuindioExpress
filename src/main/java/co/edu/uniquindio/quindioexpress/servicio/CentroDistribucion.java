package co.edu.uniquindio.quindioexpress.servicio;

import co.edu.uniquindio.quindioexpress.modelo.Paquete;
import co.edu.uniquindio.quindioexpress.modelo.Repartidor;
import co.edu.uniquindio.quindioexpress.estructuras.ListaEnlazadaSimple;

import java.util.*;

/**
 * Esqueleto de servicio del Centro de Distribución.
 * Define los contratos para que I2 e I3 puedan trabajar en paralelo.
 */
public class CentroDistribucion {

    private String nombre;

    public CentroDistribucion(String nombre) {
        this.nombre = nombre;
    }

    public void recibirPaquete(Paquete paquete) {
        throw new UnsupportedOperationException("Esqueleto I2: Pendiente por implementar.");
    }

    public Paquete procesarSiguientePaquete() {
        throw new UnsupportedOperationException("Esqueleto I2: Pendiente por implementar.");
    }

    public void registrarRepartidor(Repartidor repartidor) {
        throw new UnsupportedOperationException("Esqueleto I2: Pendiente por implementar.");
    }

    public Map<String, ListaEnlazadaSimple<Paquete>> agruparPaquetesPorDestino() {
        throw new UnsupportedOperationException("Esqueleto I2: Pendiente por implementar.");
    }

    public Set<String> obtenerDestinosUnicos() {
        throw new UnsupportedOperationException("Esqueleto I2: Pendiente por implementar.");
    }
}