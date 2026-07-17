package Math;
public enum TipoPasoBusqueda {
    COMPARANDO,      // Resaltado en amarillo (revisando si es el valor)
    DESCARTADO,      // Gris tenue (ya se revisó o quedó fuera del rango)
    ENCONTRADO,      // Verde (éxito)
    NO_ENCONTRADO,   // Rojo o estado final de error
    RANGO_ACTUAL     // Para binaria: casillas activas dentro de [izq, der]
}
