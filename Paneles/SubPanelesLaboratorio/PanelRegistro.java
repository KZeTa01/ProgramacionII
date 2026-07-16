package Paneles.SubPanelesLaboratorio;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
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

    // --- Manejo de archivo ---
    private BufferedWriter escritorArchivo;
    private File archivoActual;
    private static final DateTimeFormatter FORMATO_HORA = DateTimeFormatter.ofPattern("HH:mm:ss");

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

        // Crea (o reutiliza) el archivo de log al iniciar el panel
        iniciarArchivo("registro.txt");
    }

    public enum TipoRegistro {
        INFO, PASO, SWAP
    }

    private final Color INFO_COLOR = new Color(230, 200, 80);   // amarillo
    private final Color PASO_COLOR = new Color(120, 220, 130);  // verde
    private final Color SWAP_COLOR = new Color(90, 200, 230);   // celeste

    /**
     * Abre (o crea) el archivo de texto donde se irá volcando el registro.
     * Si ya había uno abierto, lo cierra antes de abrir el nuevo.
     */
    public void iniciarArchivo(String rutaArchivo) {
        cerrarArchivo(); // por si ya había uno abierto antes

        try {
            archivoActual = new File(rutaArchivo);
            // true = append, así no se pisa el contenido si el archivo ya existía
            escritorArchivo = new BufferedWriter(new FileWriter(archivoActual, true));
        } catch (IOException e) {
            escritorArchivo = null;
            JOptionPane.showMessageDialog(this,
                    "No se pudo abrir el archivo de registro: " + e.getMessage(),
                    "Error de archivo", JOptionPane.ERROR_MESSAGE);
        }
    }

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

        // Escribe la misma línea en el archivo .txt (sin colores, en texto plano)
        escribirEnArchivo(etiqueta + mensaje);
    }

    /**
     * Escribe una línea de texto plano en el archivo, con marca de hora.
     * Si el archivo no se pudo abrir, simplemente no hace nada (ya se avisó una vez).
     */
    private void escribirEnArchivo(String lineaPlano) {
        if (escritorArchivo == null) {
            return;
        }
        try {
            String hora = LocalDateTime.now().format(FORMATO_HORA);
            escritorArchivo.write("(" + hora + ") " + lineaPlano);
            escritorArchivo.newLine();
            escritorArchivo.flush(); // aseguramos que quede escrito en disco al instante
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "No se pudo escribir en el archivo de registro: " + e.getMessage(),
                    "Error de archivo", JOptionPane.ERROR_MESSAGE);
            cerrarArchivo();
        }
    }

    /**
     * Guarda una copia completa del contenido actual del área de registro
     * en la ruta indicada (útil para un botón "Exportar" o "Guardar como").
     */
    public void guardarComo(String rutaArchivo) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivo))) {
            bw.write(areaRegistro.getText());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "No se pudo guardar el archivo: " + e.getMessage(),
                    "Error de archivo", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Cierra el escritor del archivo. Conviene llamarlo al cerrar la aplicación
     * (por ejemplo, desde un WindowListener en VentanaPrincipal).
     */
    public void cerrarArchivo() {
        if (escritorArchivo != null) {
            try {
                escritorArchivo.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                escritorArchivo = null;
            }
        }
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

    // Limpia todo el historial (solo en pantalla; el archivo .txt no se borra)
    public void limpiarRegistro() {
        areaRegistro.setText("");
    }
}