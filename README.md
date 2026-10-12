# QuindíoExpress

Parcial 1 - Estructuras de Datos, Universidad del Quindío.

Sistema en Java que gestiona los paquetes y repartidores de un centro de distribución de QuindíoExpress. Usa colecciones de Java, una lista simplemente enlazada propia, comparadores, recursividad, búsqueda binaria y divide y vencerás.

## Cómo ejecutarlo

Requisitos: JDK 17 o superior y Maven.

- **IntelliJ IDEA:** abrir la carpeta del proyecto, esperar a que cargue Maven y ejecutar `Main` (botón ▶).
- **Consola:**
  ```
  mvn compile
  java -cp target/classes co.edu.uniquindio.quindioexpress.Main
  ```

Las clases `PruebaCentro`, `ListaEnlazadaSimpleTest` y `CentroDistribucionEntregasTest` también tienen `main` y prueban por separado la lista y el centro.

## Estructura del proyecto

```
src/main/java/co/edu/uniquindio/quindioexpress/
├── Main.java                    Carga los datos de prueba y demuestra cada operación
├── PruebaCentro.java            Prueba de CentroDistribucion
├── ListaEnlazadaSimpleTest.java Casos borde de la lista enlazada propia
├── CentroDistribucionEntregasTest.java  Flujo de asignación, entrega e historial
├── modelo/                      Paquete (Comparable por código) y Repartidor
├── estructuras/                 Coleccion<T>, Nodo<T> y ListaEnlazadaSimple<T> (propia, Iterable)
├── comparadores/                Criterios de orden reutilizables para Paquete
├── servicio/                    CentroDistribucion: lógica principal del sistema
├── algoritmos/                  AnalisisPaquetes: recursivos, búsqueda binaria y divide y vencerás
└── excepciones/                 QuindioExpressException: errores propios del sistema
```

## Estructuras usadas en CentroDistribucion

| Necesidad | Estructura | Por qué |
|---|---|---|
| Buscar un paquete por código y no repetir códigos | `HashMap<String, Paquete>` | Búsqueda y validación en O(1) promedio, sin recorrer todo |
| Registro en orden de llegada | `ArrayList<Paquete>` | Conserva el orden de inserción |
| Atender por orden de llegada (FIFO) | `ArrayDeque<Paquete>` como `Queue` | Saca el primero en O(1) |
| Despachar por prioridad | `PriorityQueue<Paquete>` con `ComparadorDespacho` | Siempre entrega el más urgente en O(log n) |
| Municipios sin repetir | `HashSet<String>` | No admite duplicados |
| Municipios sin repetir y ordenados | `TreeSet<String>` | No admite duplicados y mantiene orden alfabético |
| Paquetes agrupados por municipio | `HashMap<String, ListaEnlazadaSimple<Paquete>>` | Municipio → sus paquetes |
| Buscar un repartidor por identificación y no repetirla | `HashMap<String, Repartidor>` | Búsqueda y validación en O(1) promedio |
| Saber qué repartidor tiene cada paquete | `HashMap<String, Repartidor>` | Código del paquete → repartidor asignado |
| Historial de entregas en orden | `ListaEnlazadaSimple<Paquete>` (propia) | Agrega al final en O(1) y conserva el orden real de entrega |

Los métodos de análisis de `CentroDistribucion` (`pesoTotalMunicipio`, `contarPrioridadMinima`, `buscarBinario` y `paqueteMasPesado`) solo le pasan los paquetes del centro a `AnalisisPaquetes`, donde está la lógica. `buscarBinario` usa la lista ordenada por código, porque la búsqueda binaria lo exige.

## Comparadores

| Clase | Orden |
|---|---|
| `ComparadorPrioridad` | Prioridad de mayor a menor |
| `ComparadorPeso` | Peso de mayor a menor |
| `ComparadorTiempo` | Tiempo estimado de menor a mayor |
| `ComparadorDestino` | Destino en orden alfabético |
| `ComparadorDespacho` | Mayor prioridad; si empatan, menor tiempo; si siguen empatados, código |

El orden natural de `Paquete` (`Comparable`) es por código ascendente.

## Integrantes

| Integrante | Responsabilidad |
|---|---|
| Integrante 1 Juan Jose Chavez | Modelos (`Paquete`, `Repartidor`) y lista enlazada propia |
| Integrante 2 Nicolas Gabriel Rodriguez | Comparadores, `CentroDistribucion` y README |
| Integrante 3 Michael Andres Murillo | Excepciones, algoritmos recursivos, búsqueda binaria, divide y vencerás y `Main` |

## Forma de trabajo

`main` siempre compila. Cada integrante trabaja en su propia rama y los cambios entran a `main` solo por Pull Request aprobado por otro integrante.
