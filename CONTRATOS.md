# Contratos de Métodos y Estructuras - QuindíoExpress

## 1. Modelo Paquete
- `codigo`: String inmutable, usado en `equals` y `hashCode`.
- `peso`: double, mayor a 0, no admite NaN ni infinitos.
- `prioridad`: int (rango 1 a 5, por defecto 3).
- `tiempoEstimado`: int en **minutos**.

## 2. Estructura ListaEnlazadaSimple<T>
- `agregar(T)`: O(1)
- `eliminar(T)`: O(n)
- Métodos recursivos: `tamanioRecursivo()`, `contieneRecursivo(T)`, `obtenerRecursivo(int)`.

## 3. Firmas Servicio (CentroDistribucion)
- `recibirPaquete(Paquete)`
- `procesarSiguientePaquete(): Paquete`
- `registrarRepartidor(Repartidor)`
- `agruparPaquetesPorDestino(): Map<String, ListaEnlazadaSimple<Paquete>>`
- `obtenerDestinosUnicos(): Set<String>`