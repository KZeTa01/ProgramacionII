import javax.swing.*;
import Paneles.Interactivo;
import Paneles.Laboratorio;
import Paneles.Registro;

public class VentanaPrincipal extends JFrame {
    JTabbedPane pestañas;
    JPanel laboratorio, interativo, registro;

    public VentanaPrincipal() {
        configurarVentana();
        cargarComponentes(laboratorio, interativo, registro);
    }

    public void configurarVentana() {
        this.setTitle("Mi Ventana");
        this.setSize(900, 600);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        laboratorio = new Laboratorio();
        interativo = new Interactivo();
        registro = new Registro();
    }

    public void cargarComponentes(JPanel laboratorio, JPanel interativo, JPanel registros) {
        pestañas = new JTabbedPane();
        pestañas.addTab("Laboratorio", laboratorio);
        pestañas.addTab("Interativo", interativo);
        pestañas.addTab("Registros", registros);
        
        add(pestañas);
    }
}
