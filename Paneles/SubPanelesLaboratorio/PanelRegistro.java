package Paneles.SubPanelesLaboratorio;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class PanelRegistro extends JPanel {
    private Font fontMonoEtiqueta = new Font("Consolas", Font.BOLD, 13);
    private Font fontMonoTexto = new Font("Consolas", Font.PLAIN, 13);

    private JTextPane areaRegistro;
    private StyledDocument documento;

    // Fondo tipo "slate" oscuro, similar a la imagen de referencia
    private final Color FONDO = new Color(30, 37, 48);
    private final Color TEXTO_COLOR = new Color(220, 220, 220);

    public PanelRegistro() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Registro de ejecución"));

        areaRegistro = new JTextPane();
        areaRegistro.setEditable(false);
        areaRegistro.setBackground(FONDO);
        documento = areaRegistro.getStyledDocument();

        JScrollPane scroll = new JScrollPane(areaRegistro);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setBorder(BorderFactory.createEmptyBorder());

        add(scroll, BorderLayout.CENTER);
    }

    public enum TipoRegistro {
        INFO, PASO, SWAP
    }

    private final Color INFO_COLOR = new Color(230, 200, 80);   // amarillo
    private final Color PASO_COLOR = new Color(120, 220, 130);  // verde
    private final Color SWAP_COLOR = new Color(90, 200, 230);   // celeste

    // Agrega una línea con formato "[TIPO] mensaje", etiqueta coloreada + texto en gris claro
    public void agregarRegistro(String mensaje, TipoRegistro tipo) {
        Color colorEtiqueta = obtenerColor(tipo);
        String etiqueta = "[" + tipo.name() + "] ";

        Style estiloEtiqueta = areaRegistro.addStyle("etiqueta_" + System.nanoTime(), null);
        StyleConstants.setForeground(estiloEtiqueta, colorEtiqueta);
        StyleConstants.setBold(estiloEtiqueta, true);
        StyleConstants.setFontFamily(estiloEtiqueta, fontMonoEtiqueta.getFamily());
        StyleConstants.setFontSize(estiloEtiqueta, fontMonoEtiqueta.getSize());

        Style estiloTexto = areaRegistro.addStyle("texto_" + System.nanoTime(), null);
        StyleConstants.setForeground(estiloTexto, TEXTO_COLOR);
        StyleConstants.setFontFamily(estiloTexto, fontMonoTexto.getFamily());
        StyleConstants.setFontSize(estiloTexto, fontMonoTexto.getSize());

        try {
            documento.insertString(documento.getLength(), etiqueta, estiloEtiqueta);
            documento.insertString(documento.getLength(), mensaje + "\n", estiloTexto);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

        // Auto-scroll hacia abajo
        areaRegistro.setCaretPosition(documento.getLength());
    }

    private Color obtenerColor(TipoRegistro tipo) {
        switch (tipo) {
            case INFO:
                return INFO_COLOR;
            case PASO:
                return PASO_COLOR;
            case SWAP:
                return SWAP_COLOR;
            default:
                return TEXTO_COLOR;
        }
    }

    // Limpia todo el historial
    public void limpiarRegistro() {
        areaRegistro.setText("");
    }
}