package Paneles.SubPanelesLaboratorio;
import java.awt.Color;
import  java.awt.FlowLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PanelDatos extends JPanel implements FocusListener{
    private String texto = "Ej: 5, 3, 8, 1, 9, 2"; 
    private JTextField caja;

    public JTextField getCaja(){
        return caja; 
    }
    public void desactivar(int[] valores){
        caja.setEnabled(false);
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < valores.length; i++) {
            sb.append(valores[i]);
            if (i < valores.length - 1) {
                sb.append(", ");
            }
        }
        caja.setText(sb.toString());
        caja.setForeground(Color.red);

    }
    public void activar(){
        caja.setEnabled(true);
        caja.setForeground(Color.BLUE);
        caja.setText(texto);
        caja.setForeground(Color.GRAY); 
        
    }
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

    public String getTexto() {
        String valorActual = caja.getText();
        if (valorActual == null || valorActual.equals(texto)) {
            return "";
        }
        return valorActual.trim();
    }

    public void marcarError(boolean error) {
        caja.setBorder(BorderFactory.createLineBorder(error ? Color.RED : Color.GRAY, error ? 2 : 1));
    }

}