package Paneles;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.List;
 
 
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
    }

    private JButton crearBotonReiniciar() {
        return;
    }
}
