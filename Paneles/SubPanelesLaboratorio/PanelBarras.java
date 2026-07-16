package Paneles.SubPanelesLaboratorio;

import java.awt.*;
import javax.swing.*;

import Math.TipoPaso;

public class PanelBarras extends JPanel implements Scrollable {
    // Valor de referencia usado para escalar la altura de las barras (rango de datos: 2 a 50)
    private static final int VALOR_MAXIMO = 50;
    private static final int ANCHO_BARRA = 30;
    private static final int ESPACIO_ENTRE_BARRAS = 10;
    private static final int MARGEN_SUPERIOR = 25; // espacio para el número encima de la barra
    private static final int MARGEN_INFERIOR = 25; // espacio para el eje/base
    private static final int ALTO_PREFERIDO = 300; // alto inicial de referencia (luego se ajusta al viewport)

        // Colores según el estado de cada barra durante la animación
    private static final Color COLOR_BASE = new Color(70, 130, 180);        // azul (normal)
    private static final Color COLOR_COMPARANDO = new Color(230, 200, 80);  // amarillo
    private static final Color COLOR_INTERCAMBIO = new Color(220, 90, 90);  // rojo
    private static final Color COLOR_ORDENADO = new Color(120, 200, 130);   // verde

    private int[] valores = new int[0];

    // Estado de resaltado (se resetea con cada setDatos, se actualiza con cada aplicarPaso)
    private int idxDestacado1 = -1;
    private int idxDestacado2 = -1;
    private boolean[] ordenados = new boolean[0];
    private TipoPaso tipoActual = null;

    public PanelBarras() {
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(ESPACIO_ENTRE_BARRAS, ALTO_PREFERIDO));
    }

    public void setDatos(int[] nuevosValores) {
        this.valores = (nuevosValores != null) ? nuevosValores : new int[0];

        // Nuevos datos: se limpia cualquier resaltado de una animación anterior
        this.idxDestacado1 = -1;
        this.idxDestacado2 = -1;
        this.tipoActual = null;
        this.ordenados = new boolean[valores.length];

        int anchoNecesario = ESPACIO_ENTRE_BARRAS
                + valores.length * (ANCHO_BARRA + ESPACIO_ENTRE_BARRAS);
        setPreferredSize(new Dimension(anchoNecesario, ALTO_PREFERIDO));

        revalidate();
        repaint();
    }

    public void aplicarPaso(int[] arreglo, int idx1, int idx2, boolean[] ordenados, TipoPaso tipo) {
        this.valores = (arreglo != null) ? arreglo : new int[0];
        this.idxDestacado1 = idx1;
        this.idxDestacado2 = idx2;
        this.ordenados = (ordenados != null) ? ordenados : new boolean[this.valores.length];
        this.tipoActual = tipo;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (valores == null || valores.length == 0) {
            return;
        }

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int alturaPanel = getHeight();
        int alturaMaxDisponible = alturaPanel - MARGEN_SUPERIOR - MARGEN_INFERIOR;

        for (int idx = 0; idx < valores.length; idx++) {
            int valor = valores[idx];
            int alturaBarra = (int) (((double) valor / VALOR_MAXIMO) * alturaMaxDisponible);
            if (alturaBarra < 1) alturaBarra = 1;

            int x = ESPACIO_ENTRE_BARRAS + idx * (ANCHO_BARRA + ESPACIO_ENTRE_BARRAS);
            int y = alturaPanel - MARGEN_INFERIOR - alturaBarra;

            g2.setColor(colorParaBarra(idx));
            g2.fillRect(x, y, ANCHO_BARRA, alturaBarra);
            g2.setColor(Color.BLACK);
            g2.drawRect(x, y, ANCHO_BARRA, alturaBarra);

            String texto = String.valueOf(valor);
            FontMetrics fm = g2.getFontMetrics();
            int textoX = x + (ANCHO_BARRA - fm.stringWidth(texto)) / 2;
            g2.drawString(texto, textoX, y - 5);
        }
    }

    // Determina el color de la barra en la posición idx según el estado actual
    private Color colorParaBarra(int idx) {
        if (tipoActual == TipoPaso.FINALIZADO) {
            return COLOR_ORDENADO;
        }

        if (idx == idxDestacado1 || idx == idxDestacado2) {
            return (tipoActual == TipoPaso.INTERCAMBIO) ? COLOR_INTERCAMBIO : COLOR_COMPARANDO;
        }

        if (ordenados != null && idx < ordenados.length && ordenados[idx]) {
            return COLOR_ORDENADO;
        }

        return COLOR_BASE;
    }

    // ---- Métodos de Scrollable ----

    @Override
    public Dimension getPreferredScrollableViewportSize() {
        return getPreferredSize();
    }

    @Override
    public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
        return ANCHO_BARRA + ESPACIO_ENTRE_BARRAS;
    }

    @Override
    public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
        return visibleRect.width;
    }

    @Override
    public boolean getScrollableTracksViewportWidth() {
        // Si las barras entran en el ancho visible, que el panel se estire
        // para llenar el espacio; si no entran, permitir el scroll horizontal.
        Container padre = getParent();
        if (padre instanceof JViewport) {
            return getPreferredSize().width < padre.getWidth();
        }
        return false;
    }

    @Override
    public boolean getScrollableTracksViewportHeight() {
        // La altura siempre se ajusta al alto visible del panel
        return true;
    }
}
