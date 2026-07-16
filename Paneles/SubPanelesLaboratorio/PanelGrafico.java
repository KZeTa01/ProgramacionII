package Paneles.SubPanelesLaboratorio;

import javax.swing.*;
import java.awt.*;

import Math.PasoOrdenamiento;

public class PanelGrafico extends JPanel {

    private int[] datos;
    private final PanelBarras panelBarras;
    private final JScrollPane scrollPane;

    public PanelGrafico() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Panel de visualización"));

        panelBarras = new PanelBarras();

        scrollPane = new JScrollPane(panelBarras);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Genera un arreglo de valores aleatorios entre 2 y 50 (inclusive),
     * lo guarda como el arreglo actual y actualiza el gráfico.
     */
    public int[] generarDatosAleatorio(int cantidad) {
        datos = new int[cantidad];
        for (int i = 0; i < cantidad; i++) {
            datos[i] = (int) (Math.random() * 49) + 2; // rango [2, 50]
        }
        actualizarGrafico();
        return datos;
    }

    public int[] getDatos() {
        return datos;
    }

    /**
     * Permite establecer el arreglo de datos desde afuera (por ejemplo,
     * cuando el usuario ingresa los valores manualmente) y refresca el gráfico.
     */
    public void setDatos(int[] datos) {
        this.datos = datos;
        actualizarGrafico();
    }

    // Notifica al panel de dibujo que los datos cambiaron para que se vuelva a pintar
    private void actualizarGrafico() {
        panelBarras.setDatos(datos);
    }

    public void aplicarPaso(PasoOrdenamiento paso) {
        this.datos = paso.getArreglo();
        panelBarras.aplicarPaso(paso.getArreglo(), paso.getIdx1(), paso.getIdx2(),
                paso.getOrdenados(), paso.getTipo());
    }

    /**
     * Panel interno encargado únicamente de dibujar las barras.
     * Implementa Scrollable para que, cuando la cantidad de barras no quepa
     * en el ancho visible, aparezca una barra de desplazamiento horizontal
     * en vez de comprimir (y deformar) las barras.
     */
}