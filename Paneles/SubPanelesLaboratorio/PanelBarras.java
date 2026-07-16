package Paneles.SubPanelesLaboratorio;

import java.awt.*;
import javax.swing.*;

public class PanelBarras extends JPanel implements Scrollable {
    // Valor de referencia usado para escalar la altura de las barras (rango de datos: 2 a 50)
    private static final int VALOR_MAXIMO = 50;
    private static final int ANCHO_BARRA = 30;
    private static final int ESPACIO_ENTRE_BARRAS = 10;
    private static final int MARGEN_SUPERIOR = 25; // espacio para el número encima de la barra
    private static final int MARGEN_INFERIOR = 25; // espacio para el eje/base
    private static final int ALTO_PREFERIDO = 300; // alto inicial de referencia (luego se ajusta al viewport)

    private int[] valores = new int[0];

    public PanelBarras() {
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(ESPACIO_ENTRE_BARRAS, ALTO_PREFERIDO));
    }

    public void setDatos(int[] nuevosValores) {
        this.valores = (nuevosValores != null) ? nuevosValores : new int[0];

        int anchoNecesario = ESPACIO_ENTRE_BARRAS
                + valores.length * (ANCHO_BARRA + ESPACIO_ENTRE_BARRAS);
        setPreferredSize(new Dimension(anchoNecesario, ALTO_PREFERIDO));

        revalidate();
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

            g2.setColor(new Color(70, 130, 180));
            g2.fillRect(x, y, ANCHO_BARRA, alturaBarra);
            g2.setColor(Color.BLACK);
            g2.drawRect(x, y, ANCHO_BARRA, alturaBarra);

            String texto = String.valueOf(valor);
            FontMetrics fm = g2.getFontMetrics();
            int textoX = x + (ANCHO_BARRA - fm.stringWidth(texto)) / 2;
            g2.drawString(texto, textoX, y - 5);
        }
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
