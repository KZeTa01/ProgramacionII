package Math;

/**
 * Representa la acción realizada por el algoritmo en un paso específico,
 * usada por la interfaz para decidir cómo colorear las barras.
 */
public enum TipoPasoOrdenamiento {
    COMPARANDO,   // se están comparando dos elementos (color amarillo)
    INTERCAMBIO,  // se intercambiaron/movieron dos elementos (color rojo)
    ORDENADO,     // una posición quedó definitivamente en su lugar (color verde)
    FINALIZADO    // el algoritmo terminó, todo el arreglo queda ordenado
}
