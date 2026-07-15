    package Paneles.SubPanelesLaboratorio;
    import java.awt.*;
    import javax.swing.*;

    public class PanelDatos extends JPanel {
        public PanelDatos() {
            configurarPanel();
        }

        public void configurarPanel() {
            setLayout(new FlowLayout());
            setBorder(BorderFactory.createTitledBorder("Datos Generados"));
        }
    }