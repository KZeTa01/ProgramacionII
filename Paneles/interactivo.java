package Paneles;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.List;
 
public class interactivo extends JPanel {
 
    private JPanel panelBanco;      // Caja pequeña con las figuras disponibles
    private JPanel panelDestino;    // Caja grande donde se colocan las figuras
    private JLabel estadoLabel;     // Muestra cuántas figuras se han colocado
    private static final int TOTAL_FIGURAS = 5;
 
    public interactivo() {
        configurarPanel();
        cargarFigurasIniciales();
    }
 
    public void configurarPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(235, 238, 245));
 
        // ===== Encabezado con degradado =====
        JPanel encabezado = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, new Color(76, 81, 191), getWidth(), 0, new Color(155, 89, 182));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
            }
        };
        encabezado.setPreferredSize(new Dimension(0, 70));
        encabezado.setBorder(new EmptyBorder(10, 20, 10, 20));
 
        JLabel titulo = new JLabel("Panel Interactivo de Figuras");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 22));
        titulo.setForeground(Color.WHITE);
 
        JLabel subtitulo = new JLabel("Arrastra cada figura al área de trabajo · doble clic para devolverla");
        subtitulo.setFont(new Font("SansSerif", Font.PLAIN, 12));
        subtitulo.setForeground(new Color(230, 230, 250));
 
        JPanel textos = new JPanel();
        textos.setOpaque(false);
        textos.setLayout(new BoxLayout(textos, BoxLayout.Y_AXIS));
        textos.add(titulo);
        textos.add(subtitulo);
        encabezado.add(textos, BorderLayout.WEST);
 
        JButton botonReiniciar = crearBotonReiniciar();
        JPanel wrapperBoton = new JPanel(new GridBagLayout());
        wrapperBoton.setOpaque(false);
        wrapperBoton.add(botonReiniciar);
        encabezado.add(wrapperBoton, BorderLayout.EAST);
 
        add(encabezado, BorderLayout.NORTH);
 
        // ===== Panel central: banco + destino =====
        JPanel centro = new JPanel(new BorderLayout());
        centro.setOpaque(false);
        centro.setBorder(new EmptyBorder(15, 15, 15, 15));
 
        // ===== Caja de figuras (banco) =====
        panelBanco = new JPanel();
        panelBanco.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 15));
        panelBanco.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(new Color(180, 180, 200), 1, true),
                        "🎨 Banco de Figuras", 0, 0, new Font("SansSerif", Font.BOLD, 13), new Color(80, 80, 100)),
                new EmptyBorder(5, 5, 5, 5)));
        panelBanco.setPreferredSize(new Dimension(0, 160));
        panelBanco.setBackground(new Color(248, 248, 252));
 
        centro.add(panelBanco, BorderLayout.NORTH);
 
        // ===== Caja de destino (área de trabajo) =====
        panelDestino = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // Cuadrícula de puntos decorativa
                g2.setColor(new Color(225, 227, 235));
                for (int x = 15; x < getWidth(); x += 25) {
                    for (int y = 15; y < getHeight(); y += 25) {
                        g2.fillOval(x, y, 2, 2);
                    }
                }
                // Línea base donde se acomodan las figuras
                g2.setColor(new Color(190, 195, 215));
                float[] dash = {6f, 6f};
                g2.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, dash, 0));
                int y = getHeight() - 5;
                g2.drawLine(0, y, getWidth(), y);
                g2.dispose();
            }
        };
        panelDestino.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 200), 1, true),
                "🧩 Área de Trabajo", 0, 0, new Font("SansSerif", Font.BOLD, 13), new Color(80, 80, 100)));
        panelDestino.setBackground(Color.WHITE);
        panelDestino.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                reacomodarEnLimites();
            }
        });
 
        centro.add(panelDestino, BorderLayout.CENTER);
        add(centro, BorderLayout.CENTER);
 
        // ===== Barra de estado =====
        JPanel barraEstado = new JPanel(new BorderLayout());
        barraEstado.setBackground(new Color(245, 246, 250));
        barraEstado.setBorder(new EmptyBorder(8, 20, 8, 20));
        estadoLabel = new JLabel();
        estadoLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        estadoLabel.setForeground(new Color(90, 90, 110));
        barraEstado.add(estadoLabel, BorderLayout.WEST);
        add(barraEstado, BorderLayout.SOUTH);
 
        actualizarEstado();
    }
 
    private JButton crearBotonReiniciar() {
        JButton boton = new JButton("🔄 Reiniciar");
        boton.setFocusPainted(false);
        boton.setForeground(new Color(76, 81, 191));
        boton.setBackground(Color.WHITE);
        boton.setFont(new Font("SansSerif", Font.BOLD, 12));
        boton.setBorder(new EmptyBorder(8, 16, 8, 16));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                boton.setBackground(new Color(235, 235, 250));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                boton.setBackground(Color.WHITE);
            }
        });
        boton.addActionListener(e -> reiniciarTodo());
        return boton;
    }
 
    // Crea algunas figuras de ejemplo dentro del banco, cada una con una forma distinta
    private void cargarFigurasIniciales() {
        Color[] colores = {
                new Color(231, 76, 60),
                new Color(52, 152, 219),
                new Color(46, 204, 113),
                new Color(230, 126, 34),
                new Color(155, 89, 182)
        };
        TipoForma[] formas = TipoForma.values();
        for (int i = 0; i < colores.length; i++) {
            Figura figura = new Figura(colores[i], "F" + (i + 1), formas[i % formas.length]);
            panelBanco.add(figura);
        }
        panelBanco.revalidate();
        panelBanco.repaint();
    }
 
    private void actualizarEstado() {
        int colocadas = panelDestino.getComponentCount();
        estadoLabel.setText("Figuras colocadas: " + colocadas + " / " + TOTAL_FIGURAS
                + (colocadas == TOTAL_FIGURAS ? "   ✅ ¡Completado!" : ""));
    }
 
    private void reiniciarTodo() {
        List<Component> actuales = new ArrayList<>();
        for (Component c : panelDestino.getComponents()) {
            actuales.add(c);
        }
        for (Component c : actuales) {
            panelDestino.remove(c);
            panelBanco.add(c);
        }
        panelBanco.revalidate();
        panelBanco.repaint();
        panelDestino.revalidate();
        panelDestino.repaint();
        actualizarEstado();
    }
 
    // Reacomoda las figuras dentro de los nuevos límites cuando la ventana cambia de tamaño
    private void reacomodarEnLimites() {
        for (Component c : panelDestino.getComponents()) {
            int maxX = Math.max(panelDestino.getWidth() - c.getWidth(), 0);
            int lineaBase = Math.max(panelDestino.getHeight() - c.getHeight(), 0);
            int nx = Math.max(0, Math.min(c.getX(), maxX));
            c.setLocation(nx, lineaBase);
        }
        panelDestino.repaint();
    }
 
    private enum TipoForma { CIRCULO, CUADRADO, TRIANGULO, ESTRELLA, HEXAGONO }
 
    // ==========================================================
    // Clase interna: representa una figura arrastrable y personalizada
    // ==========================================================
    private class Figura extends JComponent {
        private Point offset;
        private int ultimaX;
        private final Color colorBase;
        private final String texto;
        private final TipoForma tipoForma;
        private boolean hover = false;
        private boolean arrastrando = false;
        private float pulso = 0f; // 0 = sin pulso, 1 = pulso máximo (animación de aterrizaje)
        private Timer timerPulso;
 
        public Figura(Color color, String texto, TipoForma tipoForma) {
            this.colorBase = color;
            this.texto = texto;
            this.tipoForma = tipoForma;
            setPreferredSize(new Dimension(80, 100));
            setSize(80, 100);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
 
            MouseAdapter listenerArrastre = crearListenerArrastre();
            addMouseListener(listenerArrastre);
            addMouseMotionListener(listenerArrastre);
        }
 
        private MouseAdapter crearListenerArrastre() {
            return new MouseAdapter() {
 
                @Override
                public void mousePressed(MouseEvent e) {
                    offset = e.getPoint();
                    arrastrando = true;
                    repaint();
                }
 
                @Override
                public void mouseReleased(MouseEvent e) {
                    arrastrando = false;
                    if (getParent() == panelDestino) {
                        aterrizarEnLineaBase();
                        iniciarPulso();
                        actualizarEstado();
                    }
                    repaint();
                }
 
                @Override
                public void mouseEntered(MouseEvent e) {
                    hover = true;
                    repaint();
                }
 
                @Override
                public void mouseExited(MouseEvent e) {
                    hover = false;
                    repaint();
                }
 
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 2 && getParent() == panelDestino) {
                        panelDestino.remove(Figura.this);
                        panelBanco.add(Figura.this);
                        panelBanco.revalidate();
                        panelBanco.repaint();
                        panelDestino.revalidate();
                        panelDestino.repaint();
                        actualizarEstado();
                    }
                }
 
                @Override
                public void mouseDragged(MouseEvent e) {
                    Point puntoEnDestino = SwingUtilities.convertPoint(
                            Figura.this, e.getPoint(), panelDestino);
 
                    if (getParent() != panelDestino) {
                        reubicarEnDestino(puntoEnDestino);
                    } else {
                        // Sigue al mouse libremente (en X y en Y) mientras se arrastra;
                        // recién al soltar el mouse se "cae" hacia la línea base.
                        Point libre = limitarLibre(
                                puntoEnDestino.x - offset.x,
                                puntoEnDestino.y - offset.y);
                        setLocation(libre.x, libre.y);
                        ultimaX = libre.x;
                    }
                }
            };
        }
 
        private void iniciarPulso() {
            if (timerPulso != null && timerPulso.isRunning()) {
                timerPulso.stop();
            }
            pulso = 1f;
            timerPulso = new Timer(30, null);
            timerPulso.addActionListener(ev -> {
                pulso -= 0.12f;
                if (pulso <= 0f) {
                    pulso = 0f;
                    timerPulso.stop();
                }
                repaint();
            });
            timerPulso.start();
        }
 
        private void reubicarEnDestino(Point puntoEnDestino) {
            panelBanco.remove(Figura.this);
            panelBanco.revalidate();
            panelBanco.repaint();
 
            panelDestino.add(Figura.this);
 
            // Aparece pegada al mouse; recién se acomoda en la línea base al soltar.
            Point libre = limitarLibre(
                    puntoEnDestino.x - offset.x,
                    puntoEnDestino.y - offset.y);
            setLocation(libre.x, libre.y);
            ultimaX = libre.x;
 
            panelDestino.revalidate();
            panelDestino.repaint();
        }
 
        // Deja que la figura se mueva libremente (X e Y) dentro de los límites del área de trabajo
        private Point limitarLibre(int x, int y) {
            int maxX = Math.max(panelDestino.getWidth() - getWidth(), 0);
            int maxY = Math.max(panelDestino.getHeight() - getHeight(), 0);
 
            int nX = Math.max(0, Math.min(x, maxX));
            int nY = Math.max(0, Math.min(y, maxY));
 
            return new Point(nX, nY);
        }
 
        // Al soltar el mouse, la figura "cae" hacia la línea base del área de trabajo,
        // esquivando otras figuras si hace falta.
        private void aterrizarEnLineaBase() {
            int lineaBase = Math.max(panelDestino.getHeight() - getHeight(), 0);
            int x = getX();
 
            if (sobrePosicion(x, lineaBase)) {
                Point libre = buscarPosicionLibre(lineaBase);
                x = libre.x;
            }
            setLocation(x, lineaBase);
            ultimaX = x;
        }
 
        private boolean sobrePosicion(int x, int y) {
            Rectangle propuesto = new Rectangle(x, y, getWidth(), getHeight());
 
            for (Component comp : panelDestino.getComponents()) {
                if (comp == Figura.this) continue;
 
                Rectangle otro = comp.getBounds();
                if (propuesto.intersects(otro)) {
                    return true;
                }
            }
            return false;
        }
 
        private Point buscarPosicionLibre(int y) {
            int maxX = panelDestino.getWidth() - getWidth();
            maxX = Math.max(maxX, 0);
 
            for (int x = 0; x <= maxX; x += 5) {
                if (!sobrePosicion(x, y)) {
                    return new Point(x, y);
                }
            }
            return new Point(0, y);
        }
 
        // ===== Dibujo personalizado de la figura =====
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
 
            int w = getWidth();
            int h = getHeight();
            int formaAncho = w - 20;
            int formaAlto = h - 30;
            int offX = 10;
            int offY = 5;
 
            Shape forma = crearForma(tipoForma, offX, offY, formaAncho, formaAlto);
 
            // Sombra (más grande si se está arrastrando, simulando elevación)
            int sombraOffset = arrastrando ? 8 : 3;
            g2.setColor(new Color(0, 0, 0, arrastrando ? 60 : 35));
            AffineTransform sombraT = AffineTransform.getTranslateInstance(sombraOffset, sombraOffset);
            g2.fill(sombraT.createTransformedShape(forma));
 
            // Anillo de pulso al aterrizar
            if (pulso > 0f) {
                g2.setColor(new Color(colorBase.getRed(), colorBase.getGreen(), colorBase.getBlue(),
                        (int) (120 * pulso)));
                g2.setStroke(new BasicStroke(3f));
                double expansion = 10 * pulso;
                Shape anillo = AffineTransform.getScaleInstance(
                        1 + expansion / formaAncho, 1 + expansion / formaAlto)
                        .createTransformedShape(forma);
                g2.draw(anillo);
            }
 
            // Relleno con degradado
            Color claro = colorBase.brighter();
            Color oscuro = colorBase.darker();
            GradientPaint gp = new GradientPaint(offX, offY, claro, offX + formaAncho, offY + formaAlto, oscuro);
            g2.setPaint(gp);
 
            float alpha = arrastrando ? 0.75f : 1f;
            Composite compositeOriginal = g2.getComposite();
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            g2.fill(forma);
 
            // Borde (más grueso al pasar el mouse)
            g2.setColor(hover ? Color.WHITE : new Color(60, 60, 70));
            g2.setStroke(new BasicStroke(hover ? 3f : 1.5f));
            g2.draw(forma);
            g2.setComposite(compositeOriginal);
 
            // Texto centrado
            g2.setFont(new Font("SansSerif", Font.BOLD, 14));
            g2.setColor(Color.WHITE);
            FontMetrics fm = g2.getFontMetrics();
            int tx = w / 2 - fm.stringWidth(texto) / 2;
            int ty = offY + formaAlto / 2 + fm.getAscent() / 2 - 2;
            g2.drawString(texto, tx, ty);
 
            g2.dispose();
        }
 
        private Shape crearForma(TipoForma tipo, int x, int y, int w, int h) {
            switch (tipo) {
                case CIRCULO:
                    return new Ellipse2D.Double(x, y, w, h);
                case CUADRADO:
                    return new RoundRectangle2D.Double(x, y, w, h, 14, 14);
                case TRIANGULO: {
                    Polygon p = new Polygon();
                    p.addPoint(x + w / 2, y);
                    p.addPoint(x + w, y + h);
                    p.addPoint(x, y + h);
                    return p;
                }
                case HEXAGONO: {
                    Polygon p = new Polygon();
                    int cx = x + w / 2, cy = y + h / 2;
                    for (int i = 0; i < 6; i++) {
                        double ang = Math.PI / 3 * i - Math.PI / 2;
                        p.addPoint((int) (cx + (w / 2.0) * Math.cos(ang)),
                                   (int) (cy + (h / 2.0) * Math.sin(ang)));
                    }
                    return p;
                }
                case ESTRELLA:
                default: {
                    Polygon p = new Polygon();
                    int cx = x + w / 2, cy = y + h / 2;
                    double rExt = Math.min(w, h) / 2.0;
                    double rInt = rExt * 0.45;
                    for (int i = 0; i < 10; i++) {
                        double ang = Math.PI / 5 * i - Math.PI / 2;
                        double r = (i % 2 == 0) ? rExt : rInt;
                        p.addPoint((int) (cx + r * Math.cos(ang)), (int) (cy + r * Math.sin(ang)));
                    }
                    return p;
                }
            }
        }
    }
}
