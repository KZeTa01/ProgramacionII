package Paneles;

import javax.swing.*;
import java.awt.*;
import Paneles.SubPanelesLaboratorio.*;

public class Laboratorio extends JPanel{
    JPanel pOpciones, pCentro, pInferior, pDatos, pGrafico, pCodigo;
    
    JRadioButton rbOrdenamiento, rbBusqueda;
    ButtonGroup bgOpciones;

    JComboBox<String> cbAlgoritmos;
    JSpinner spCantidad;
    

    public Laboratorio() {
        configurarPestaña();
        
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
}
