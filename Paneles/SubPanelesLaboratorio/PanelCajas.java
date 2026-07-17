package Paneles.SubPanelesLaboratorio;

import Math.PasoBusqueda;
import Math.TipoPasoBusqueda;

import javax.swing.*;
import java.awt.*;

public class PanelCajas extends JPanel implements Scrollable {
    private PasoBusqueda pasoActual;
    private int[] arreglo;
    private final int LADO_CAJA = 50;
    private final int ESPACIADO = 10;
    private static final Color COLOR_FONDO = new Color(235, 245, 255);
    private static final Color COLOR_RANGO = new Color(200, 230, 255);
    private static final Color COLOR_ACTUAL = new Color(255, 235, 130);
    private static final Color COLOR_DESCARTADO = new Color(220, 220, 220);
    private static final Color COLOR_ENCONTRADO = new Color(155, 215, 145);
    private static final Color COLOR_NO_ENCONTRADO = new Color(245, 125, 125);

    public PanelCajas() {
        setBackground(Color.WHITE);
    }

    public void setDatos(int[] datos) {
        if (datos != null) {
            this.arreglo = datos.clone();
            boolean[] descartados = new boolean[datos.length];
            pasoActual = new PasoBusqueda(datos, -1, descartados, TipoPasoBusqueda.RANGO_ACTUAL);
        } else {
            this.arreglo = null;
            pasoActual = null;
        }
        repaint();
        revalidate();
    }

    public void setPasoActual(PasoBusqueda paso) {
        this.pasoActual = paso;
        if (paso != null) {
            this.arreglo = paso.getArreglo();
        }
        repaint();
        revalidate();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if ((pasoActual == null || pasoActual.getArreglo() == null) && arreglo == null) return;

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int[] arreglo = pasoActual != null ? pasoActual.getArreglo() : this.arreglo;
        boolean[] descartados = pasoActual != null ? pasoActual.getDescartados() : new boolean[arreglo.length];
        int yCaja = getHeight() / 2 - LADO_CAJA / 2;

        Integer indiceActual = pasoActual != null ? pasoActual.getIndiceActual() : null;
        TipoPasoBusqueda tipoActual = pasoActual != null ? pasoActual.getTipoPaso() : null;

        for (int i = 0; i < arreglo.length; i++) {
            int x = ESPACIADO + i * (LADO_CAJA + ESPACIADO);

                // Determinar color de fondo
            Color colorFondo = COLOR_FONDO;
            if (pasoActual != null && pasoActual.getIndiceIzq() != -1) {
                int izq = pasoActual.getIndiceIzq();
                int der = pasoActual.getIndiceDer();
                if (!descartados[i] && i >= izq && i <= der) {
                    colorFondo = COLOR_RANGO;
                }
            }
            if (descartados[i]) {
                colorFondo = COLOR_DESCARTADO;
            }
            if (indiceActual != null && i == indiceActual) {
                if (tipoActual == TipoPasoBusqueda.ENCONTRADO) {
                    colorFondo = COLOR_ENCONTRADO;
                } else if (tipoActual == TipoPasoBusqueda.NO_ENCONTRADO) {
                    colorFondo = COLOR_NO_ENCONTRADO;
                } else {
                    colorFondo = COLOR_ACTUAL; // Comparando
                }
            }

            // Dibujar Caja
            g2.setColor(colorFondo);
            g2.fillRect(x, yCaja, LADO_CAJA, LADO_CAJA);
            g2.setColor(Color.DARK_GRAY);
            g2.drawRect(x, yCaja, LADO_CAJA, LADO_CAJA);

            // Dibujar Valor (centrado)
            String valorStr = String.valueOf(arreglo[i]);
            FontMetrics fm = g2.getFontMetrics();
            int strX = x + (LADO_CAJA - fm.stringWidth(valorStr)) / 2;
            int strY = yCaja + ((LADO_CAJA - fm.getHeight()) / 2) + fm.getAscent();
            g2.drawString(valorStr, strX, strY);

            // Dibujar Índice arriba
            g2.setColor(Color.GRAY);
            String idxStr = "[" + i + "]";
            g2.drawString(idxStr, x + (LADO_CAJA - fm.stringWidth(idxStr)) / 2, yCaja - 5);

            // Dibujar Punteros abajo (L, M, R)
            g2.setColor(Color.BLUE);
            String punteros = "";
            if (pasoActual != null) {
                if (pasoActual.getIndiceIzq() != -1) { // Es binaria
                    if (i == pasoActual.getIndiceIzq()) punteros += "L ";
                    if (i == pasoActual.getIndiceActual()) punteros += "M ";
                    if (i == pasoActual.getIndiceDer()) punteros += "R ";
                } else { // Es secuencial
                    if (i == pasoActual.getIndiceActual()) punteros = "^";
                }
            }
            
            if (!punteros.isEmpty()) {
                g2.drawString(punteros.trim(), x + (LADO_CAJA - fm.stringWidth(punteros.trim())) / 2, yCaja + LADO_CAJA + 15);
            }
        }
    }

    // Métodos de Scrollable para mantener el tamaño dinámico horizontalmente
    @Override
    public Dimension getPreferredScrollableViewportSize() {
        return getPreferredSize();
    }

    @Override
    public Dimension getPreferredSize() {
        int[] arregloActual = pasoActual != null ? pasoActual.getArreglo() : this.arreglo;
        if (arregloActual == null) return super.getPreferredSize();
        int width = (arregloActual.length * (LADO_CAJA + ESPACIADO)) + ESPACIADO;
        return new Dimension(width, 200);
    }

    @Override public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) { return LADO_CAJA + ESPACIADO; }
    @Override public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) { return visibleRect.width; }
    @Override public boolean getScrollableTracksViewportWidth() { return false; }
    @Override public boolean getScrollableTracksViewportHeight() { return true; }
}