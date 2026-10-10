package co.edu.uniquindio.quindioexpress;

import co.edu.uniquindio.quindioexpress.modelo.Paquete;
import co.edu.uniquindio.quindioexpress.servicio.CentroDistribucion;

public class PruebaCentro {
    public static void main(String[] args) {
        System.out.println("\n=== PRUEBA CENTRO DE DISTRIBUCIÓN (I2) ===");
        CentroDistribucion centro = new CentroDistribucion("QuindíoExpress");

        centro.recibirPaquete(new Paquete("PQ731", "Armenia", 4.5, 5, 30));
        centro.recibirPaquete(new Paquete("PQ105", "Calarcá", 2.0, 5, 20));
        centro.recibirPaquete(new Paquete("PQ420", "Armenia", 9.5, 3, 45));
        System.out.println("Paquetes registrados: " + centro.cantidadPaquetes()); // 3

        try {
            centro.recibirPaquete(new Paquete("PQ731", "Salento", 1.0, 2, 15));
        } catch (IllegalArgumentException e) {
            System.out.println("Rechazado: " + e.getMessage());
        }

        System.out.println("Buscar PQ420: " + centro.buscarPaquete("PQ420"));
        System.out.println("Buscar PQ999: " + centro.buscarPaquete("PQ999")); // null
    }
}
