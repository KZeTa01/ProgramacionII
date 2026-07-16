package Math;

public class Busquedas {
    



    public int busquedaSecuencial(int[] arreglo, int valorBuscado) {
    for (int i = 0; i < arreglo.length; i++) {
        if (arreglo[i] == valorBuscado) {
            return i; // Retorna la posición donde se encontró
        }
    }
    return -1; // No se encontró el valor
    }
    public int busquedaBinaria(int[] arreglo, int valorBuscado) {
        int izquierda = 0;
        int derecha = arreglo.length - 1;

        while (izquierda <= derecha) {
            int centro = (izquierda + derecha) / 2;

            if (arreglo[centro] == valorBuscado) {
            return centro; // Se encontró
            } else if (valorBuscado < arreglo[centro]) {
            derecha = centro - 1;
            } else {
            izquierda = centro + 1;
            }
        }

        return -1; // No se encontró
    }
}
