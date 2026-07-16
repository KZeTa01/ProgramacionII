package Paneles.SubPanelesLaboratorio;

import javax.swing.*;

public class PanelGrafico extends JPanel {
    public PanelGrafico() {
        setBorder(BorderFactory.createTitledBorder("Panel de visualización"));
        JTextArea area = new JTextArea("ddadadada");
        add(area); 
    }  

    int [] datos;

    public int[] generarDatosAleatorio(int cantidad){
        datos = new int[cantidad];
        int i=0;
        do{
            datos[i] = (int)(Math.random()*50);
            if (datos[i]!=0) {
                i++;
            }   
        }while(i< cantidad);
        return datos;
    }

    public int[] getDatos(){
        return datos;
    }

    public void setDatos(int[] datos){
        this.datos=datos;
    }
}