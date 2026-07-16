package Paneles;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.RenderingHints;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
 
 
public class Interativo extends JPanel{
    private JPanel panelBanco;      // Caja pequeña con las figuras disponibles
    private JPanel panelDestino;    // Caja grande donde se colocan las figuras
    private JLabel estadoLabel;     // Muestra cuántas figuras se han colocado
    
    public Interativo() {
        configurarPanel();
    }

    public void configurarPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(235, 238, 245));

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
                
            }
        });
 
        centro.add(panelDestino, BorderLayout.CENTER);
        add(centro, BorderLayout.CENTER);

        

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

    private void actualizarEstado() {
        
    }
}
