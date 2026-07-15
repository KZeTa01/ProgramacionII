package Paneles.SubPanelesLaboratorio;

import java.awt.*;
import javax.swing.*;

public class PanelRegistro extends JPanel {
    private Font font = new Font("Arial", Font.PLAIN, 14);

    public PanelRegistro() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Registro de ejecución"));
        JPanel pHistorial = new JPanel();
        pHistorial.setBackground(Color.black);
        JLabel label = new JLabel("Historial de ejecución");
        label.setFont(font);
        label.setForeground(Color.white);
        pHistorial.add(label);

        add(pHistorial, BorderLayout.CENTER);
    }

    private enum TipoRegistro {
        INFO, PASO, SWAP
    }

    private final Color INFO_COLOR = Color.BLUE;
    private final Color PASO_COLOR = Color.ORANGE;
    private final Color SWAP_COLOR = Color.RED;

}