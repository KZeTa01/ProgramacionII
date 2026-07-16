package Paneles;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import Paneles.SubPanelesLaboratorio.PanelDatos;
import Paneles.SubPanelesLaboratorio.PanelGrafico;
import Paneles.SubPanelesLaboratorio.PanelRegistro;

public class Laboratorio extends JPanel{
    JPanel pOpciones, pCentro, pInferior, pDatos, pGrafico, pCodigo;
    
    JRadioButton rbOrdenamiento, rbBusqueda;
    ButtonGroup bgOpciones;

    JComboBox<String> cbAlgoritmos;
    JSpinner spCantidad;
    

    public Laboratorio() {
        configurarPestaña();
        cargarComponentesLab();
    }

    public void configurarPestaña() {
        setLayout(new BorderLayout());
        pOpciones = AgregarPanelOpciones();
        add(pOpciones, BorderLayout.WEST);

        pCentro = AgregarPanelCentro();
        add(pCentro, BorderLayout.CENTER);
    }
    //Panel Opciones
    public JPanel AgregarPanelOpciones() {
        pOpciones = new JPanel();
        pOpciones.setBorder(BorderFactory.createTitledBorder("Opciones"));
        pOpciones.setLayout(new FlowLayout());
        
        rbOrdenamiento = new JRadioButton("Ordenamiento");
        rbBusqueda = new JRadioButton("Búsqueda");
        bgOpciones = new ButtonGroup();
        bgOpciones.add(rbOrdenamiento);
        bgOpciones.add(rbBusqueda);
        
        cbAlgoritmos = new JComboBox<>();
        cbAlgoritmos.addItem("Burbuja");
        cbAlgoritmos.addItem("Selección");
        cbAlgoritmos.addItem("Inserción");
        
        spCantidad = new JSpinner(new SpinnerNumberModel(10, 1, 100, 1));
        
        pOpciones.add(rbOrdenamiento);
        pOpciones.add(rbBusqueda);
        pOpciones.add(cbAlgoritmos);
        pOpciones.add(spCantidad);
        
        return pOpciones;
    }


    //Panel Centro
    public JPanel AgregarPanelCentro() {
        pCentro = new JPanel();
        pCentro.setLayout(new GridLayout(3, 1));
        
        PanelDatos panelDatos = new PanelDatos();
        PanelGrafico panelGrafico = new PanelGrafico();
        PanelRegistro panelRegistro = new PanelRegistro();
        pCentro.add(panelDatos);
        pCentro.add(panelGrafico);
        pCentro.add(panelRegistro);
        
        return pCentro;
    }

    public void cargarPaneLab(){
        setLayout(new BorderLayout());

    }
    public void cargarComponentesLab(){
        JSpinner spin; 
        JSlider velocidad;
        JButton aleatorio,ejecutar, manual;  
        JComboBox combo; 
        JRadioButton ordenamiento,busqueda; 
        ButtonGroup grupo; 
        JPanel superior,S1,S2,S4,medio1,medio2,inferior,izquierda,IzModo,IzAlgoritmo,Izdatos;
        String[] busq; 
        
        //PRIMER PANEL(Panel de modos)
        S1 =new JPanel(new GridLayout(3,1,10,10));
        S1.setBorder(BorderFactory.createTitledBorder("Modo")); 
            //Generando el grupo de botones
                grupo = new ButtonGroup(); 
                ordenamiento = new JRadioButton("Ordenamiento"); 
                busqueda = new JRadioButton("Busqueda");
                //agregando los botones al grupo
                grupo.add(ordenamiento);
                grupo.add(busqueda);
            //creando combo de algoritmos de busqueda
                
                String[] ord = {"Selección", "Inserción", "Burbuja", "QuickSort"};
                combo  = new JComboBox<>(ord);
        //Agregando los componentes al grupo y al panel
            S1.add(ordenamiento);
            S1.add(busqueda);
            S1.add(combo);

        //SEGUNDO PANEL(Panel de Datos)

        S2 = new JPanel(new GridLayout(3,1,10,5));
        S2.setBorder(BorderFactory.createTitledBorder("DATOS Y CANTIDAD"));
        
        aleatorio = new JButton("Aleatorio");
        manual = new JButton("Manual");
        spin = new JSpinner(new SpinnerNumberModel(5, 5, 100, 1));
        
        S2.add(aleatorio); S2.add(manual); S2.add(spin); 

        //Panel superior izquierdo inferior
        S4 = new JPanel(new GridLayout(3,1));

        ejecutar = new JButton("Ejecutar");

        S4.add(new JLabel("Velocidad"));
        velocidad = new JSlider(1, 3, 1);
        S4.add(velocidad);
        S4.add(ejecutar);
        

        //Agregar paneles al panel superior
        superior = new JPanel(new GridLayout(3,1));
        superior.add(S1);
        superior.add(S2);
        superior.add(S4);

        add(superior, BorderLayout.WEST);
    }
}
