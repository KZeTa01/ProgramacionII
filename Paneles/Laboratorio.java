package Paneles;

import javax.swing.*;

import Excepciones.*;
import Math.Ordenamientos;
import Math.PasoOrdenamiento;
import Math.TipoPaso;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import Paneles.SubPanelesLaboratorio.*;
import Paneles.SubPanelesLaboratorio.PanelRegistro.TipoRegistro;

public class Laboratorio extends JPanel implements ActionListener {
    JPanel pOpciones, pCentro, pInferior, pDatos, pGrafico, pCodigo, pBuscar,superior;
    PanelGrafico panelGrafico; PanelDatos panelDatos; PanelRegistro panelRegistro; 
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
    javax.swing.Timer timerAnimacion;

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
        spCantidad = new JSpinner(new SpinnerNumberModel(5, 5, 40, 1));
        
        pDatos.add(btnAleatorio); pDatos.add(btnManual); pDatos.add(spCantidad); 

        //Panel superior izquierdo inferior (Velocidad y ejecutar)
        pInferior = new JPanel(new GridLayout(3,1));
        btnEjecutar = new JButton("Ejecutar");
        btnEjecutar.addActionListener(this);

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
            panelRegistro.agregarRegistro("Modo cambiado a Ordenamiento", TipoRegistro.INFO);

        } else if (e.getSource() == rbBusqueda) {
            combo.setModel(new DefaultComboBoxModel<>(algoritmosBusqueda));
            txtBusqueda.setEnabled(true);
            panelRegistro.agregarRegistro("Modo cambiado a Búsqueda", TipoRegistro.INFO);

        } else if (e.getSource() == btnAleatorio){
            int [] a;
            cantidadBarras = (int) spCantidad.getValue();
            panelDatos.marcarError(false);
            a= panelGrafico.generarDatosAleatorio(cantidadBarras);
            panelDatos.desactivar(a); //rr
            panelRegistro.agregarRegistro(
                    "Datos aleatorios generados (" + cantidadBarras + " valores): " + Arrays.toString(a),
                    TipoRegistro.INFO);

        } else if (e.getSource() == btnManual){
            panelDatos.activar();
            String texto = panelDatos.getTexto();

            if (texto.isEmpty()) {
                panelDatos.marcarError(true);
                panelRegistro.agregarRegistro(
                        "Intento de carga manual sin datos ingresados", TipoRegistro.INFO);
                JOptionPane.showMessageDialog(this,
                        "Ingrese al menos un valor separado por ';' (Ej: 3;33;5;7;32).",
                        "Datos vacíos", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                int cantidadEsperada = (int) spCantidad.getValue();
                int[] datosManual = parsearDatosManuales(texto);

                if (datosManual.length != cantidadEsperada) {
                    panelDatos.marcarError(true);
                    JOptionPane.showMessageDialog(this,
                            "Debe ingresar exactamente " + cantidadEsperada + " valor(es) para coincidir con la cantidad de barras.",
                            "Cantidad de datos incorrecta", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                cantidadBarras = datosManual.length;
                panelDatos.marcarError(false);
                panelGrafico.setDatos(datosManual);

                panelRegistro.agregarRegistro(
                        "Datos manuales cargados (" + cantidadBarras + " valores): " + Arrays.toString(datosManual),
                        TipoRegistro.INFO);

            } catch (NumberFormatException ex) {
                panelDatos.marcarError(true);
                panelRegistro.agregarRegistro(
                        "Error al cargar datos manuales: " + ex.getMessage(), TipoRegistro.INFO);
                JOptionPane.showMessageDialog(this,
                        "Formato inválido. Ingrese solo números enteros separados por ';' (Ej: 3;33;5;7;32).\n"
                                + "Detalle: " + ex.getMessage(),
                        "Error en los datos", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == btnEjecutar){

            ejecutarAnimacion();

            int[] datosActuales = panelGrafico.getDatos();
            if (datosActuales == null || datosActuales.length == 0) {
                panelRegistro.agregarRegistro(
                        "Intento de ejecución sin datos cargados", TipoRegistro.INFO);
                JOptionPane.showMessageDialog(this,
                        "Primero genere o ingrese datos antes de ejecutar.",
                        "Sin datos", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String algoritmo = (String) combo.getSelectedItem();
            String modo = rbOrdenamiento.isSelected() ? "Ordenamiento" : "Búsqueda";

            panelRegistro.agregarRegistro(
                    "Ejecutando " + modo + " - Algoritmo: " + algoritmo
                            + " - Velocidad: " + sdrVelocidad.getValue(),
                    TipoRegistro.PASO);

            // TODO: aquí se debe invocar la lógica real del algoritmo seleccionado
        }
    }


        private void ejecutarAnimacion() {
        if (timerAnimacion != null && timerAnimacion.isRunning()) {
            return; // ya hay una animación en curso
        }

        int[] datosActuales = panelGrafico.getDatos();
        if (datosActuales == null || datosActuales.length == 0) {
            JOptionPane.showMessageDialog(this,
                    "Primero genere datos con 'Aleatorio' o 'Manual'.",
                    "Sin datos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!rbOrdenamiento.isSelected()) {
            JOptionPane.showMessageDialog(this,
                    "La animación de búsqueda aún no está implementada.",
                    "Próximamente", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String algoritmo = (String) combo.getSelectedItem();
        List<PasoOrdenamiento> pasos;
        switch (algoritmo) {
            case "Selección":
                pasos = Ordenamientos.seleccion(datosActuales);
                break;
            case "Inserción":
                pasos = Ordenamientos.insercion(datosActuales);
                break;
            case "Burbuja":
                pasos = Ordenamientos.burbuja(datosActuales);
                break;
            default:
                return;
        }

        panelRegistro.limpiarRegistro();
        panelRegistro.agregarSeparadorEjecucion();
        panelRegistro.agregarRegistro("Iniciando ordenamiento por " + algoritmo, PanelRegistro.TipoRegistro.INFO);
        habilitarControles(false);

        int velocidad = sdrVelocidad.getValue();
        int retraso;
        if (velocidad == 1) {
            retraso = 700;
        } else if (velocidad == 2) {
            retraso = 350;
        } else {
            retraso = 130;
        }

        Iterator<PasoOrdenamiento> iterador = pasos.iterator();
        timerAnimacion = new Timer(retraso, null);
        timerAnimacion.addActionListener(ev -> {
            if (!iterador.hasNext()) {
                timerAnimacion.stop();
                habilitarControles(true);
                return;
            }
            PasoOrdenamiento paso = iterador.next();
            panelGrafico.aplicarPaso(paso);
            panelRegistro.agregarRegistro(paso.getMensaje(), mapearTipoRegistro(paso.getTipo()));
        });
        timerAnimacion.start();
    }

    private PanelRegistro.TipoRegistro mapearTipoRegistro(TipoPaso tipo) {
        switch (tipo) {
            case INTERCAMBIO:
                return PanelRegistro.TipoRegistro.SWAP;
            case COMPARANDO:
                return PanelRegistro.TipoRegistro.PASO;
            default:
                return PanelRegistro.TipoRegistro.INFO;
        }
    }

        private void habilitarControles(boolean habilitar) {
        btnAleatorio.setEnabled(habilitar);
        btnManual.setEnabled(habilitar);
        btnEjecutar.setEnabled(habilitar);
        combo.setEnabled(habilitar);
        rbOrdenamiento.setEnabled(habilitar);
        rbBusqueda.setEnabled(habilitar);
        spCantidad.setEnabled(habilitar);
    }

    private int[] parsearDatosManuales(String texto) {
        String[] partes = texto.split(",");
        int[] resultado = new int[partes.length];

        for (int i = 0; i < partes.length; i++) {
            String valor = partes[i].trim();
            if (valor.isEmpty()) {
                throw new NumberFormatException("valor vacío en la posición " + (i + 1));
            }
            try {
                int numero = Integer.parseInt(valor);
                if (numero > 50) {
                    resultado[i] = 50;
                    throw new ValorExcedidoException("El valor " + numero + " (posición " + (i + 1) + ") excede el máximo permitido de 50, se reemplazó por el valor máximo.");
                } else {
                    resultado[i] = numero;
                }
            } catch (NumberFormatException ex) {
                throw new NumberFormatException("\"" + valor + "\" no es un número entero (posición " + (i + 1) + ")");
            } catch (ValorExcedidoException ex){
                panelRegistro.agregarRegistro(ex.getMessage(), TipoRegistro.INFO);
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Valor Excedido", JOptionPane.WARNING_MESSAGE);
            }
        }
        return resultado;
    }
}