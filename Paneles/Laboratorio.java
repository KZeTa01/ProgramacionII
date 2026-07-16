package Paneles;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import Paneles.SubPanelesLaboratorio.PanelDatos;
import Paneles.SubPanelesLaboratorio.PanelGrafico;
import Paneles.SubPanelesLaboratorio.PanelRegistro;

public class Laboratorio extends JPanel implements ActionListener {
    JPanel pOpciones, pCentro, pInferior, pDatos, pGrafico, pCodigo, pBuscar,superior;
    PanelGrafico panelGrafico;
    PanelDatos panelDatos;
    PanelRegistro panelRegistro; 
    JButton btnEjecutar, btnAleatorio, btnManual;
    JSlider sdrVelocidad;
    JRadioButton rbOrdenamiento,rbBusqueda; 
    ButtonGroup grupoBotones;
    JComboBox<String> combo;
    int cantidadBarras;
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
        
        panelDatos = new PanelDatos();
        panelGrafico = new PanelGrafico();
        panelRegistro = new PanelRegistro();
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
        btnAleatorio.addActionListener(this);
        btnManual = new JButton("Manual");
        btnManual.addActionListener(this);
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

        pBuscar = new JPanel(new FlowLayout(FlowLayout.CENTER,0,20));
        pBuscar.setBorder(BorderFactory.createTitledBorder("Indique valor a buscar"));
        txtBusqueda = new JTextField();
        txtBusqueda.setEnabled(false);
        txtBusqueda.setPreferredSize(new Dimension(150, 25));
        pBuscar.add(txtBusqueda);
        //Agregar paneles al panel superior
        superior = new JPanel();
        superior.setLayout(new BoxLayout(superior, BoxLayout.Y_AXIS));
        superior.add(pModos);
        superior.add(pDatos);
        superior.add(pBuscar);
        superior.add(pInferior);

        JPanel izq = new JPanel(new BorderLayout()); izq.add(superior, BorderLayout.NORTH);
        add(izq, BorderLayout.WEST);
    }  

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()== rbOrdenamiento) {
            combo.setModel(new DefaultComboBoxModel<>(algoritmosOrdenamiento));
            txtBusqueda.setEnabled(false);
        } else if (e.getSource() == rbBusqueda) {
            combo.setModel(new DefaultComboBoxModel<>(algoritmosBusqueda));
            txtBusqueda.setEnabled(true);
        } else if (e.getSource() == btnAleatorio){
            int [] a; 
            
            cantidadBarras = (int) spCantidad.getValue();
            a = panelGrafico.generarDatosAleatorio(cantidadBarras);
            panelDatos.desactivar(a);
        } else if (e.getSource() == btnManual){
                panelDatos.activar();
        } else if (e.getSource() == btnEjecutar){
            
        }
    }
}
