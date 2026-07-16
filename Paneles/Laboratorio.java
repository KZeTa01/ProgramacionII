package Paneles;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import Paneles.SubPanelesLaboratorio.*;

public class Laboratorio extends JPanel implements ActionListener {
    JPanel pOpciones, pCentro, pInferior, pDatos, pGrafico, pCodigo, pBuscar,superior;
    JButton btnEjecutar, btnAleatorio, btnManual;
    JSlider sdrVelocidad;
    JRadioButton rbOrdenamiento,rbBusqueda; 
    ButtonGroup grupoBotones;
    JComboBox<String> combo;
    DefaultComboBoxModel<String> modeloCombo;
    JTextField txtBusqueda;
    final String [] algoritmosOrdenamiento = {"Selección", "Inserción", "Burbuja"};
    final String [] algoritmosBusqueda = {"Secuencial", "Binaria"};

    JSpinner spCantidad;
    

    public Laboratorio() {
        configurarPestaña();
        cargarComponentesLab();
    }

    public void configurarPestaña() {
        setLayout(new BorderLayout());

        pCentro = AgregarPanelCentro();
        add(pCentro, BorderLayout.CENTER);
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
        ButtonGroup grupoBotones; 
        JPanel pModos,pDatos,pInferior;
        pBuscar = new JPanel(new FlowLayout());
        pBuscar.setBorder(BorderFactory.createTitledBorder("Indique valor a buscar"));
        txtBusqueda = new JTextField(10);
        pBuscar.add(txtBusqueda);
        //PRIMER PANEL(Panel de modos)
        pModos =new JPanel(new GridLayout(3,1,10,10));
        pModos.setBorder(BorderFactory.createTitledBorder("Algoritmo")); 
            //Generando el grupo de botones
                grupoBotones = new ButtonGroup(); 
                rbOrdenamiento = new JRadioButton("Ordenamiento",true); 
                rbBusqueda = new JRadioButton("Busqueda");
                //agregando listener a los botones
                rbOrdenamiento.addActionListener(this);
                rbBusqueda.addActionListener(this);
                //agregando los botones al grupo
                grupoBotones.add(rbOrdenamiento);
                grupoBotones.add(rbBusqueda);
            //creando combo de algoritmos de busqueda
                combo  = new JComboBox<>(algoritmosOrdenamiento);
        //Agregando los componentes al grupo y al panel
            pModos.add(rbOrdenamiento);
            pModos.add(rbBusqueda);
            pModos.add(combo);

        //SEGUNDO PANEL(Panel de Datos)

        pDatos = new JPanel(new GridLayout(3,1,10,5));
        pDatos.setBorder(BorderFactory.createTitledBorder("Datos: Modo - Cantidad - Busqueda"));
        
        btnAleatorio = new JButton("Aleatorio");
        btnManual = new JButton("Manual");
        spCantidad = new JSpinner(new SpinnerNumberModel(5, 5, 100, 1));
        
        pDatos.add(btnAleatorio); pDatos.add(btnManual); pDatos.add(spCantidad); 

        //Panel superior izquierdo inferior (Velocidad y ejecutar)
        pInferior = new JPanel(new GridLayout(3,1));
        btnEjecutar = new JButton("Ejecutar");

        pInferior.add(new JLabel("Velocidad"));
        sdrVelocidad = new JSlider(1, 3, 1);
        sdrVelocidad.setMajorTickSpacing(1);
        sdrVelocidad.setMinorTickSpacing(1);
        sdrVelocidad.setPaintTicks(true);
        sdrVelocidad.setPaintLabels(true); 
        pInferior.add(sdrVelocidad);
        pInferior.add(btnEjecutar);
        

        //Agregar paneles al panel superior
        superior = new JPanel(new GridLayout(4,1));
        superior.add(pModos);
        superior.add(pDatos);
        superior.add(pInferior);

        add(superior, BorderLayout.WEST);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()== rbOrdenamiento) {
            combo.setModel(new DefaultComboBoxModel<>(algoritmosOrdenamiento));
            superior.remove(pBuscar);
            superior.revalidate();
            superior.repaint();
        } else if (e.getSource() == rbBusqueda) {
            combo.setModel(new DefaultComboBoxModel<>(algoritmosBusqueda));
            superior.add(pBuscar);
            superior.revalidate();
            superior.repaint();
        }
    }
}
