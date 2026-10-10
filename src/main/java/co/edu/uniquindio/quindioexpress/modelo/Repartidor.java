package co.edu.uniquindio.quindioexpress.modelo;

import java.util.Objects;

/**
 * Modelo de dominio que representa a un repartidor en el sistema QuindíoExpress.
 */
public class Repartidor {

    private String identificacion;
    private String nombre;
    private String zona;
    private boolean disponible;

    public Repartidor(String identificacion, String nombre, String zona) {
        setIdentificacion(identificacion);
        setNombre(nombre);
        setZona(zona);
        this.disponible = true; // Por defecto está disponible al registrarse
    }

    public Repartidor(String identificacion, String nombre, String zona, boolean disponible) {
        setIdentificacion(identificacion);
        setNombre(nombre);
        setZona(zona);
        this.disponible = disponible;
    }

    // --- GETTERS Y SETTERS CON VALIDACIONES ---

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        if (identificacion == null || identificacion.trim().isEmpty()) {
            throw new IllegalArgumentException("La identificación del repartidor no puede estar vacía.");
        }
        this.identificacion = identificacion.trim();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del repartidor no puede estar vacío.");
        }
        this.nombre = nombre.trim();
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        if (zona == null || zona.trim().isEmpty()) {
            throw new IllegalArgumentException("La zona del repartidor no puede estar vacía.");
        }
        this.zona = zona.trim();
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    // --- IDENTIDAD (Dos repartidores son iguales si tienen la misma identificación) ---

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Repartidor queRepartidor = (Repartidor) o;
        return Objects.equals(identificacion, queRepartidor.identificacion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identificacion);
    }

    @Override
    public String toString() {
        return "Repartidor{" +
                "id='" + identificacion + '\'' +
                ", nombre='" + nombre + '\'' +
                ", zona='" + zona + '\'' +
                ", disponible=" + (disponible ? "Sí" : "No") + '}';
    }
}