import javax.swing.*;
import Paneles.*; 

public class VentanaPrincipal extends JFrame {
    JTabbedPane pestañas;
    Interativo laboratorio;
    Laboratorio lab; 
    Registros reb; 

    public VentanaPrincipal() {
        configurarVentana();
        cargarComponentes(laboratorio, interativo, registro);
    }

    public void configurarVentana() {
        setTitle("Mi Ventana");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

    }

    public void cargarComponentes(JPanel laboratorio, JPanel interativo, JPanel registros) {
        pestañas = new JTabbedPane();
        pestañas.addTab("Laboratorio", laboratorio);
        pestañas.addTab("Interativo", interativo);
        pestañas.addTab("Registros", registros);
        
        add(pestañas);
    }
}
