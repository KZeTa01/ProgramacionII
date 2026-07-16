package Paneles.SubPanelesLaboratorio;
import java.awt.*;
import  java.awt.event.*; 
import javax.swing.*;

public class PanelDatos extends JPanel implements FocusListener{
    private String texto = "Ej: 5, 3, 8, 1, 9, 2"; 
        

    public PanelDatos() {
        configurarPanel();
        cargarComp(); 
    }

    public void configurarPanel() {
        setLayout(new FlowLayout(FlowLayout.CENTER,10,30));
        setBorder(BorderFactory.createTitledBorder("Generacion o ingreso de datos"));
            
    }

    public void cargarComp(){
        JPanel panel = new JPanel(); 
        JTextField caja; 
            
        panel.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createTitledBorder("Valores separados por comas"),
        BorderFactory.createEmptyBorder(10, 15, 10, 15)));
            
        caja  = new JTextField(40); 
        caja.setForeground(Color.GRAY);
        caja.setText(texto);
        caja.addFocusListener(this);
        panel.add(caja);

        add(panel);
            
    }

    @Override
    public void focusGained(FocusEvent e) {

        JTextField campo = (JTextField) e.getSource();

        if (campo.getText().equals(texto)) {
            campo.setText("");
            campo.setForeground(Color.BLACK);
        }
    }

    public void focusLost(FocusEvent e) {
        JTextField campo = (JTextField) e.getSource();
        if (campo.getText().isEmpty()) {
            campo.setForeground(Color.GRAY);
            campo.setText(texto);
        }
    }
   

}
        
    