package Math;

public class PasoBusqueda {
    private int[] arreglo;
    private int indiceActual; // El índice que se está comparando (Medio en binaria)
    private int indiceIzq;    // Puntero L
    private int indiceDer;    // Puntero R
    private boolean[] descartados; // Arreglo paralelo para saber qué casillas pintar de gris
    private TipoPasoBusqueda tipoPaso;

    // Constructor para Búsqueda Binaria
    public PasoBusqueda(int[] arreglo, int indiceActual, int indiceIzq, int indiceDer, boolean[] descartados, TipoPasoBusqueda tipoPaso) {
        this.arreglo = arreglo.clone();
        this.indiceActual = indiceActual;
        this.indiceIzq = indiceIzq;
        this.indiceDer = indiceDer;
        this.descartados = descartados.clone();
        this.tipoPaso = tipoPaso;
    }

    // Constructor simplificado para Búsqueda Secuencial
    public PasoBusqueda(int[] arreglo, int indiceActual, boolean[] descartados, TipoPasoBusqueda tipoPaso) {
        this(arreglo, indiceActual, -1, -1, descartados, tipoPaso);
    }

    public int[] getArreglo() { return arreglo; }
    public int getIndiceActual() { return indiceActual; }
    public int getIndiceIzq() { return indiceIzq; }
    public int getIndiceDer() { return indiceDer; }
    public boolean[] getDescartados() { return descartados; }
    public TipoPasoBusqueda getTipoPaso() { return tipoPaso; }
}