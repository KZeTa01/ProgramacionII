package Math;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Busquedas {

    public static List<PasoBusqueda> busquedaSecuencial(int[] arreglo, int objetivo) {
        List<PasoBusqueda> pasos = new ArrayList<>();
        boolean[] descartados = new boolean[arreglo.length];
        Arrays.fill(descartados, false);

        for (int i = 0; i < arreglo.length; i++) {
            // Paso: Comparando actual
            pasos.add(new PasoBusqueda(arreglo, i, descartados, TipoPasoBusqueda.COMPARANDO));

            if (arreglo[i] == objetivo) {
                // Paso: Encontrado
                pasos.add(new PasoBusqueda(arreglo, i, descartados, TipoPasoBusqueda.ENCONTRADO));
                return pasos;
            }
            
            // Si no es, se marca como descartado
            descartados[i] = true;
            pasos.add(new PasoBusqueda(arreglo, i, descartados, TipoPasoBusqueda.DESCARTADO));
        }

        // Paso: No encontrado
        pasos.add(new PasoBusqueda(arreglo, -1, descartados, TipoPasoBusqueda.NO_ENCONTRADO));
        return pasos;
    }

    public static List<PasoBusqueda> busquedaBinaria(int[] arreglo, int objetivo) {
        List<PasoBusqueda> pasos = new ArrayList<>();
        boolean[] descartados = new boolean[arreglo.length];
        Arrays.fill(descartados, false);
        
        int izq = 0;
        int der = arreglo.length - 1;

        while (izq <= der) {
            int medio = izq + (der - izq) / 2;
            
            // Actualizar descartados visuales fuera del rango [izq, der]
            for (int i = 0; i < arreglo.length; i++) {
                if (i < izq || i > der) descartados[i] = true;
            }

            // Paso: Rango actual calculado y medio seleccionado
            pasos.add(new PasoBusqueda(arreglo, medio, izq, der, descartados, TipoPasoBusqueda.COMPARANDO));

            if (arreglo[medio] == objetivo) {
                pasos.add(new PasoBusqueda(arreglo, medio, izq, der, descartados, TipoPasoBusqueda.ENCONTRADO));
                return pasos;
            }

            if (arreglo[medio] < objetivo) {
                izq = medio + 1;
            } else {
                der = medio - 1;
            }
        }

        // Marcar todo como descartado al final si no se encuentra
        Arrays.fill(descartados, true);
        pasos.add(new PasoBusqueda(arreglo, -1, izq, der, descartados, TipoPasoBusqueda.NO_ENCONTRADO));
        return pasos;
    }
}