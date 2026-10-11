package co.edu.uniquindio.quindioexpress;

import co.edu.uniquindio.quindioexpress.excepciones.QuindioExpressException;
import co.edu.uniquindio.quindioexpress.estructuras.ListaEnlazadaSimple;
import co.edu.uniquindio.quindioexpress.modelo.Paquete;
import co.edu.uniquindio.quindioexpress.modelo.Repartidor;
import co.edu.uniquindio.quindioexpress.servicio.CentroDistribucion;

public class CentroDistribucionEntregasTest {

    public static void main(String[] args) {
        System.out.println("=== PRUEBAS UNITARIAS DE ENTREGAS E HISTORIAL ===");

        CentroDistribucion centro = new CentroDistribucion("QuindíoExpress");

        // 1. Configuración de datos iniciales
        Paquete p1 = new Paquete("PQ105", "Armenia - Centro", 2.0, 3, 60);
        Paquete p2 = new Paquete("PQ731", "Calarcá", 4.5, 5, 90);
        Repartidor repartidor = new Repartidor("REP01", "Ana", "Armenia - Centro");

        centro.recibirPaquete(p1);
        centro.recibirPaquete(p2);
        centro.registrarRepartidor(repartidor);

        // Asignar paquete a repartidor (Ana pasa a disponible: false)
        centro.asignarPaquete("PQ105", "REP01");
        System.out.println("1. Repartidor Ana disponible antes de entregar: " + repartidor.isDisponible() + " | Esperado: false");

        // 2. Registrar entrega válida
        centro.registrarEntrega("PQ105");
        System.out.println("2. Repartidor Ana liberada tras entrega: " + repartidor.isDisponible() + " | Esperado: true");

        // 3. Intentar entregar el mismo paquete (Duplicado)
        try {
            centro.registrarEntrega("PQ105");
            System.out.println("3. Error: Permitión entrega duplicada.");
        } catch (QuindioExpressException e) {
            System.out.println("3. Rechazo de entrega duplicada -> Excepción capturada: " + e.getMessage());
        }

        // 4. Verificar orden y tamaño del historial
        centro.registrarEntrega("PQ731");
        ListaEnlazadaSimple<Paquete> historial = centro.obtenerHistorial();
        System.out.println("4. Cantidad en historial: " + historial.tamanio() + " | Esperado: 2");
        System.out.println("   Primer entregado: " + historial.obtener(0).getCodigo() + " | Esperado: PQ105");
        System.out.println("   Segundo entregado: " + historial.obtener(1).getCodigo() + " | Esperado: PQ731");

        // 5. Verificar protección de Encapsulamiento (Copia Defensiva)
        historial.eliminar(p1); // Intentar alterar la lista devuelta externamente
        System.out.println("5. Verificación de copia defensiva:");
        System.out.println("   Tamaño lista devuelta tras eliminar: " + historial.tamanio());
        System.out.println("   Tamaño real en el centro: " + centro.obtenerHistorial().tamanio() + " | Esperado: 2");
    }
}