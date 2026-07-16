package Math;

/**
 * Representa el estado del arreglo en un instante determinado del algoritmo
 * de ordenamiento, junto con la información necesaria para que la interfaz
 * pinte las barras correctamente y muestre un mensaje en el registro.
 *
 * Cada paso es autocontenido: la interfaz solo necesita este objeto para
 * saber exactamente qué dibujar, sin depender de pasos anteriores.
 */
public class PasoOrdenamiento {
    private final int[] arreglo;      // estado del arreglo en este paso
    private final int idx1;           // primer índice resaltado (-1 si no aplica)
    private final int idx2;           // segundo índice resaltado (-1 si no aplica)
    private final boolean[] ordenados; // posiciones ya definitivas hasta este paso
    private final TipoPaso tipo;
    private final String mensaje;

    public PasoOrdenamiento(int[] arreglo, int idx1, int idx2, boolean[] ordenados,
                             TipoPaso tipo, String mensaje) {
        this.arreglo = arreglo;
        this.idx1 = idx1;
        this.idx2 = idx2;
        this.ordenados = ordenados;
        this.tipo = tipo;
        this.mensaje = mensaje;
    }

    public int[] getArreglo() {
        return arreglo;
    }

    public int getIdx1() {
        return idx1;
    }

    public int getIdx2() {
        return idx2;
    }

    public boolean[] getOrdenados() {
        return ordenados;
    }

    public TipoPaso getTipo() {
        return tipo;
    }

    public String getMensaje() {
        return mensaje;
    }
}
