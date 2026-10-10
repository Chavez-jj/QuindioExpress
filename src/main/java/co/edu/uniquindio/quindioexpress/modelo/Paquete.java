package co.edu.uniquindio.quindioexpress.modelo;

import java.util.Objects;

public class Paquete implements Comparable<Paquete> {

    public static final int PRIORIDAD_MIN = 1;
    public static final int PRIORIDAD_MAX = 5;
    public static final int PRIORIDAD_DEFECTO = 5;

    private final String codigo;
    private String destino;
    private double peso;
    private int prioridad;
    private int tiempoEstimado; // En minutos

    public Paquete(String codigo, String destino, double peso, int prioridad, int tiempoEstimado) {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("El código del paquete no puede estar vacío.");
        }
        this.codigo = codigo.trim();
        setDestino(destino);
        setPeso(peso);
        setPrioridad(prioridad);
        setTiempoEstimado(tiempoEstimado);
    }

    public Paquete(String codigo, String destino, double peso, int tiempoEstimado) {
        this(codigo, destino, peso, PRIORIDAD_DEFECTO, tiempoEstimado);
    }

    public String getCodigo() { return codigo; }
    public String getDestino() { return destino; }

    public final void setDestino(String destino) {
        if (destino == null || destino.trim().isEmpty()) {
            throw new IllegalArgumentException("El destino del paquete no puede estar vacío.");
        }
        this.destino = destino.trim();
    }

    public double getPeso() { return peso; }

    public final void setPeso(double peso) {
        if (Double.isNaN(peso) || Double.isInfinite(peso) || peso <= 0) {
            throw new IllegalArgumentException("El peso debe ser un número finito mayor a 0 kg.");
        }
        this.peso = peso;
    }

    public int getPrioridad() { return prioridad; }

    public final void setPrioridad(int prioridad) {
        if (prioridad < PRIORIDAD_MIN || prioridad > PRIORIDAD_MAX) {
            throw new IllegalArgumentException("La prioridad debe estar entre " + PRIORIDAD_MIN + " y " + PRIORIDAD_MAX + ".");
        }
        this.prioridad = prioridad;
    }

    public int getTiempoEstimado() { return tiempoEstimado; }

    public final void setTiempoEstimado(int tiempoEstimado) {
        if (tiempoEstimado <= 0) {
            throw new IllegalArgumentException("El tiempo estimado debe ser mayor a 0 minutos.");
        }
        this.tiempoEstimado = tiempoEstimado;
    }

    @Override
    public int compareTo(Paquete otro) {
        return this.codigo.compareTo(otro.codigo);
    }

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
                ", tiempo=" + tiempoEstimado + " min}";
    }
}