package co.edu.uniquindio.quindioexpress.modelo;

import java.util.Objects;

/**
 * Modelo de dominio que representa un paquete en el sistema QuindíoExpress.
 */
public class Paquete implements Comparable<Paquete> {

    // Constantes para validación de prioridad (fácilmente modificables si el profesor confirma de 0 a 10)
    public static final int PRIORIDAD_MIN = 1;
    public static final int PRIORIDAD_MAX = 5;
    public static final int PRIORIDAD_DEFECTO = 3;

    private String codigo;
    private String destino;
    private double peso;
    private int prioridad;
    private double tiempoEstimado;

    public Paquete(String codigo, String destino, double peso, int prioridad, double tiempoEstimado) {
        setCodigo(codigo);
        setDestino(destino);
        setPeso(peso);
        setPrioridad(prioridad);
        setTiempoEstimado(tiempoEstimado);
    }

    public Paquete(String codigo, String destino, double peso, double tiempoEstimado) {
        this(codigo, destino, peso, PRIORIDAD_DEFECTO, tiempoEstimado);
    }

    // --- GETTERS Y SETTERS CON VALIDACIONES ---

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("El código del paquete no puede estar vacío.");
        }
        this.codigo = codigo.trim();
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        if (destino == null || destino.trim().isEmpty()) {
            throw new IllegalArgumentException("El destino del paquete no puede estar vacío.");
        }
        this.destino = destino.trim();
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        if (peso <= 0) {
            throw new IllegalArgumentException("El peso debe ser mayor a 0 kg.");
        }
        this.peso = peso;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(int prioridad) {
        if (prioridad < PRIORIDAD_MIN || prioridad > PRIORIDAD_MAX) {
            throw new IllegalArgumentException("La prioridad debe estar entre " + PRIORIDAD_MIN + " y " + PRIORIDAD_MAX + ".");
        }
        this.prioridad = prioridad;
    }

    public double getTiempoEstimado() {
        return tiempoEstimado;
    }

    public void setTiempoEstimado(double tiempoEstimado) {
        if (tiempoEstimado <= 0) {
            throw new IllegalArgumentException("El tiempo estimado debe ser mayor a 0.");
        }
        this.tiempoEstimado = tiempoEstimado;
    }

    // --- ORDEN NATURAL (Por código de paquete ascendente) ---

    @Override
    public int compareTo(Paquete otro) {
        return this.codigo.compareTo(otro.codigo);
    }

    // --- IDENTIDAD (Dos paquetes son iguales si tienen el mismo código) ---

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Paquete paquete = (Paquete) o;
        return Objects.equals(codigo, paquete.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return "Paquete{" +
                "código='" + codigo + '\'' +
                ", destino='" + destino + '\'' +
                ", peso=" + peso +
                "kg, prioridad=" + prioridad +
                ", tiempo=" + tiempoEstimado + "h}";
    }
}
