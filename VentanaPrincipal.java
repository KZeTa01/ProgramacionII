import javax.swing.*;
import Paneles.*; 

public class VentanaPrincipal extends JFrame {
    JPanel laboratorio, interativo, registro;
    JTabbedPane pestañas;

    public VentanaPrincipal() {
        configurarVentana();
        cargarComponentes(laboratorio, interativo, registro);
    }

    public void configurarVentana() {
        setTitle("Laboratorio-Visual-Algoritmos");
        setSize(900, 600);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        laboratorio = new Laboratorio();
        interativo = new Interativo();
        registro = new Registro();
    }

    public void cargarComponentes(JPanel laboratorio, JPanel interativo, JPanel registros) {
        pestañas = new JTabbedPane();
        pestañas.addTab("Principal", laboratorio);
        pestañas.addTab("Interativo", interativo);
        pestañas.addTab("Registros", registros);
        
        add(pestañas);
    }
}
