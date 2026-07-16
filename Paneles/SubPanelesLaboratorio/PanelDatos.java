    package Paneles.SubPanelesLaboratorio;
    import java.awt.*;
    import javax.swing.*;

    public class PanelDatos extends JPanel {
        public PanelDatos() {
            configurarPanel();
            cargarComp(); 
        }

        public void configurarPanel() {
            setLayout(new FlowLayout());
            setBorder(BorderFactory.createTitledBorder("Generacion o ingreso de datos"));
            setPreferredSize(new Dimension(300, 0));
        }
        public void cargarComp(){
            JTextField caja; 
            caja  = new JTextField(20); 
            add(caja);

        }
    }