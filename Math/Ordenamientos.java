package Math;

import java.util.ArrayList;
import java.util.List;

public class Ordenamientos {

    /**
     * Genera la secuencia de pasos del algoritmo de ordenamiento por Selección
     * a partir del arreglo original (no lo modifica).
     */
    public static List<PasoOrdenamiento> seleccion(int[] datosOriginales) {
        List<PasoOrdenamiento> pasos = new ArrayList<>();
        int[] arr = datosOriginales.clone();
        int n = arr.length;
        boolean[] ordenados = new boolean[n];

        for (int i = 0; i < n - 1; i++) {
            int indiceMinimo = i;

            for (int j = i + 1; j < n; j++) {
                pasos.add(new PasoOrdenamiento(arr.clone(), indiceMinimo, j, ordenados.clone(),
                        TipoPasoOrdenamiento.COMPARANDO,
                        "Comparando posición " + j + " (" + arr[j] + ") con el menor actual, posición "
                                + indiceMinimo + " (" + arr[indiceMinimo] + ")"));

                if (arr[j] < arr[indiceMinimo]) {
                    indiceMinimo = j;
                }
            }

            if (indiceMinimo != i) {
                int tmp = arr[i];
                arr[i] = arr[indiceMinimo];
                arr[indiceMinimo] = tmp;
                pasos.add(new PasoOrdenamiento(arr.clone(), i, indiceMinimo, ordenados.clone(),
                        TipoPasoOrdenamiento.INTERCAMBIO,
                        "Intercambiando posiciones " + i + " y " + indiceMinimo));
            }

            ordenados[i] = true;
            pasos.add(new PasoOrdenamiento(arr.clone(), i, -1, ordenados.clone(),
                    TipoPasoOrdenamiento.ORDENADO, "Posición " + i + " quedó ordenada"));
        }

        ordenados[n - 1] = true;
        pasos.add(new PasoOrdenamiento(arr.clone(), -1, -1, ordenados.clone(),
                TipoPasoOrdenamiento.FINALIZADO, "Ordenamiento por Selección finalizado"));
        return pasos;
    }

    /**
     * Genera la secuencia de pasos del algoritmo de ordenamiento por Inserción
     * a partir del arreglo original (no lo modifica).
     */
    public static List<PasoOrdenamiento> insercion(int[] datosOriginales) {
        List<PasoOrdenamiento> pasos = new ArrayList<>();
        int[] arr = datosOriginales.clone();
        int n = arr.length;
        boolean[] ordenados = new boolean[n];
        if (n > 0) {
            ordenados[0] = true;
        }

        for (int i = 1; i < n; i++) {
            int actual = arr[i];
            int j = i - 1;

            pasos.add(new PasoOrdenamiento(arr.clone(), i, -1, ordenados.clone(),
                    TipoPasoOrdenamiento.COMPARANDO,
                    "Tomando el elemento en posición " + i + " (" + actual + ") para insertarlo"));

            while (j >= 0 && arr[j] > actual) {
                pasos.add(new PasoOrdenamiento(arr.clone(), j, j + 1, ordenados.clone(),
                        TipoPasoOrdenamiento.COMPARANDO,
                        "Comparando " + arr[j] + " (posición " + j + ") con " + actual));

                arr[j + 1] = arr[j];
                pasos.add(new PasoOrdenamiento(arr.clone(), j, j + 1, ordenados.clone(),
                        TipoPasoOrdenamiento.INTERCAMBIO,
                        "Desplazando " + arr[j] + " una posición a la derecha"));
                j--;
            }

            arr[j + 1] = actual;
            ordenados[i] = true;
            pasos.add(new PasoOrdenamiento(arr.clone(), j + 1, -1, ordenados.clone(),
                    TipoPasoOrdenamiento.ORDENADO, "Insertando " + actual + " en la posición " + (j + 1)));
        }

        pasos.add(new PasoOrdenamiento(arr.clone(), -1, -1, ordenados.clone(),
                TipoPasoOrdenamiento.FINALIZADO, "Ordenamiento por Inserción finalizado"));
        return pasos;
    }

    /**
     * Genera la secuencia de pasos del algoritmo de ordenamiento Burbuja
     * a partir del arreglo original (no lo modifica).
     */
    public static List<PasoOrdenamiento> burbuja(int[] datosOriginales) {
        List<PasoOrdenamiento> pasos = new ArrayList<>();
        int[] arr = datosOriginales.clone();
        int n = arr.length;
        boolean[] ordenados = new boolean[n];

        for (int i = 0; i < n - 1; i++) {
            boolean huboIntercambio = false;

            for (int j = 0; j < n - i - 1; j++) {
                pasos.add(new PasoOrdenamiento(arr.clone(), j, j + 1, ordenados.clone(),
                        TipoPasoOrdenamiento.COMPARANDO,
                        "Comparando posiciones " + j + " (" + arr[j] + ") y " + (j + 1) + " (" + arr[j + 1] + ")"));

                if (arr[j] > arr[j + 1]) {
                    int tmp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = tmp;
                    huboIntercambio = true;
                    pasos.add(new PasoOrdenamiento(arr.clone(), j, j + 1, ordenados.clone(),
                            TipoPasoOrdenamiento.INTERCAMBIO,
                            "Intercambiando posiciones " + j + " y " + (j + 1)));
                }
            }

            ordenados[n - i - 1] = true;
            pasos.add(new PasoOrdenamiento(arr.clone(), n - i - 1, -1, ordenados.clone(),
                    TipoPasoOrdenamiento.ORDENADO, "Posición " + (n - i - 1) + " quedó ordenada"));

            if (!huboIntercambio) {
                break;
            }
        }

        for (int i = 0; i < n; i++) {
            ordenados[i] = true;
        }
        pasos.add(new PasoOrdenamiento(arr.clone(), -1, -1, ordenados.clone(),
                TipoPasoOrdenamiento.FINALIZADO, "Ordenamiento Burbuja finalizado"));
        return pasos;
    }
}
