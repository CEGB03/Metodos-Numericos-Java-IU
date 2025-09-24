package com.cegb03.metodos.LocRaices;

import com.cegb03.metodos.LocRaices.controllers.LocRaicesController;
import com.cegb03.metodos.LocRaices.services.GraphingService;
import com.cegb03.metodos.LocRaices.validators.InputValidator;
import com.cegb03.metodos.LocRaices.models.*;
import com.cegb03.metodos.calculos.Derivar;
import com.cegb03.metodos.calculos.Graficador;

import com.cegb03.metodos.LocRaices.models.CalculationInput;
import com.cegb03.metodos.LocRaices.models.CalculationResult;
import com.cegb03.metodos.LocRaices.models.ValidationResult;
import com.cegb03.metodos.LocRaices.services.GraphPaginationService;

import java.awt.Color;
import java.awt.Component;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;

/**
 *
 * @author cegb03
 */
public class LocRaicesFrame extends javax.swing.JFrame {

    // Servicios y controladores
    private final LocRaicesController controller = new LocRaicesController();
    private final InputValidator validator = new InputValidator();
    private final GraphingService graphingService = new GraphingService();
    private final GraphPaginationService paginationService = new GraphPaginationService();
    private final Derivar derivacion = new Derivar();

    // Variables para el gráfico
    private ChartPanel chartPanelHolder;
    private Dimension currentSize;
    private Point currentLocation;

    // Variables para compatibilidad con código existente
    private String funcionGGenerada;
    private boolean metodosAbiertos = false;

    // Variables de compatibilidad con el código original
    private String funX;
    private Double a;
    private Double b;
    private Double tolerancia;
    private String funY;
    private String derivadaAux;
    private Double xn;
    private double paso = 0.01;

    /**
     * Creates new form LocRaicesFrame
     */
    public LocRaicesFrame() {
        initComponents();
        initializeFrame();
    }

    private void initializeFrame() {
        /* 1 – only ONE pack in the life-cycle */
        pack(); // <- first and last pack
        setMinimumSize(getSize()); // forbid smaller
        setLocationRelativeTo(null);

        /* 2 – give the chart container a fixed pixel size */
        jPGrafico.setPreferredSize(new Dimension(500, 580));
        jPGrafico.setMinimumSize(new Dimension(500, 580));
        jPGrafico.setMaximumSize(new Dimension(500, 580));

        /* 3 – create an *empty* ChartPanel once and stick to it */
        chartPanelHolder = Graficador.createChartPanelSimple("0"); // dummy
        jPGrafico.setLayout(new BorderLayout());
        jPGrafico.add(chartPanelHolder, BorderLayout.CENTER);

        // Configurar propiedades para evitar redimensionado automático
        setResizable(true); // Permitir redimensionar manualmente

        setupTextFields();

        // NUEVO: Añadir menú de estilos de gráfico
        crearMenuEstilos();
    }

    /**
     * Crea un menú para cambiar el estilo de los gráficos en tiempo real
     */
    private void crearMenuEstilos() {
        javax.swing.JMenu menuEstilos = new javax.swing.JMenu("Estilos Gráfico");

        javax.swing.JMenuItem estiloOscuro = new javax.swing.JMenuItem("Fondo Oscuro");
        estiloOscuro.addActionListener(e -> {
            GraphingService.aplicarEstiloOscuro();
            refrescarGraficos();
        });

        javax.swing.JMenuItem estiloClaro = new javax.swing.JMenuItem("Fondo Claro");
        estiloClaro.addActionListener(e -> {
            GraphingService.aplicarEstiloClaro();
            refrescarGraficos();
        });

        javax.swing.JMenuItem estiloAzul = new javax.swing.JMenuItem("Estilo Azul");
        estiloAzul.addActionListener(e -> {
            GraphingService.aplicarEstiloAzul();
            refrescarGraficos();
        });

        javax.swing.JMenuItem estiloPersonalizado = new javax.swing.JMenuItem("Personalizado...");
        estiloPersonalizado.addActionListener(e -> abrirDialogoEstiloPersonalizado());

        menuEstilos.add(estiloOscuro);
        menuEstilos.add(estiloClaro);
        menuEstilos.add(estiloAzul);
        menuEstilos.addSeparator();
        menuEstilos.add(estiloPersonalizado);

        // Añadir al menú existente
        jMenuBar1.add(menuEstilos);
    }

    /**
     * Abre un diálogo para personalizar colores
     */
    private void abrirDialogoEstiloPersonalizado() {
        javax.swing.JColorChooser selectorFondo = new javax.swing.JColorChooser();
        Color fondo = javax.swing.JColorChooser.showDialog(this, "Seleccionar Color de Fondo", Color.DARK_GRAY);

        if (fondo != null) {
            javax.swing.JColorChooser selectorEjes = new javax.swing.JColorChooser();
            Color ejes = javax.swing.JColorChooser.showDialog(this, "Seleccionar Color de Ejes", Color.WHITE);

            if (ejes != null) {
                GraphingService.cambiarEstiloGrafico(fondo, ejes);
                refrescarGraficos();
            }
        }
    }

    /**
     * Refresca los gráficos actuales con el nuevo estilo
     */
    private void refrescarGraficos() {
        try {
            CalculationInput input = createInputFromUI();

            // Regenerar gráficos con el nuevo estilo
            SwingUtilities.invokeLater(() -> {
                paginationService.createTabbedGraphs(jPGrafico, input);
            });

        } catch (Exception e) {
            System.err.println("Error al refrescar gráficos: " + e.getMessage());
        }
    }

    private void setupTextFields() {
        // Deshabilitar campos inicialmente
        txtFunF.setEnabled(false);
        txtFunY.setEnabled(false);
        txtFunGPF.setEnabled(false);
        txtVarXNuevo.setEnabled(false);
        txtVarA.setEnabled(false);
        txtVarB.setEnabled(false);
        txtVarTol.setEnabled(false);

        // COLORES DISTINTIVOS PARA CADA FUNCIÓN
        txtFunF.setBackground(Color.LIGHT_GRAY); // F(x) - Gris claro
        txtFunF.setOpaque(true);
        txtFunF.repaint();

        txtFunY.setBackground(Color.CYAN); // f'(x) derivada - Cyan
        txtFunY.setOpaque(true);
        txtFunY.repaint();

        txtFunGPF.setBackground(Color.ORANGE); // g(x) Punto Fijo - Naranja
        txtFunGPF.setOpaque(true);
        txtFunGPF.repaint();

        txtVarXNuevo.setBackground(Color.MAGENTA);
        txtVarXNuevo.setOpaque(true);
        txtVarXNuevo.repaint();
        txtVarA.setBackground(new Color(173, 216, 230)); // Light Blue
        txtVarA.setOpaque(true);
        txtVarA.repaint();
        txtVarB.setBackground(new Color(173, 216, 230)); // Light Blue
        txtVarB.setOpaque(true);
        txtVarB.repaint();
        txtVarTol.setBackground(Color.GRAY);
        txtVarTol.setOpaque(true);
        txtVarTol.repaint();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPPrincial = new javax.swing.JPanel();
        jPInput = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        lblInFunF = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtFunF = new javax.swing.JTextField();
        txtVarA = new javax.swing.JTextField();
        txtVarB = new javax.swing.JTextField();
        txtVarTol = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAResultados = new javax.swing.JTextArea();
        jLabel6 = new javax.swing.JLabel();
        btnVolver = new javax.swing.JButton();
        btnCalcular = new javax.swing.JButton();
        btnGraficar = new javax.swing.JButton();
        btnCheckBiseccion = new javax.swing.JCheckBox();
        btnChecRegulaFalsi = new javax.swing.JCheckBox();
        btnChecNewtonRapson = new javax.swing.JCheckBox();
        btnChecPuntoFijo = new javax.swing.JCheckBox();
        btnChecSecante = new javax.swing.JCheckBox();
        lblInFunG = new javax.swing.JLabel();
        txtFunY = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtVarXNuevo = new javax.swing.JTextField();
        txtFunGPF = new javax.swing.JTextField();
        lblInFunG1 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPGrafico = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Metodo Localizacion de Raices General");

        lblInFunF.setText("Ingrese el valor f(x): ");

        jLabel3.setText("Ingrese el valor a: ");

        jLabel4.setText("Ingrese el valor b: ");

        jLabel5.setText("Ingrese el valor error tolerable: ");

        txtFunF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFunFActionPerformed(evt);
            }
        });

        txtVarA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtVarAActionPerformed(evt);
            }
        });

        txtVarTol.setText("0.0001");

        txtAResultados.setEditable(false);
        txtAResultados.setColumns(20);
        txtAResultados.setRows(5);
        jScrollPane1.setViewportView(txtAResultados);

        jLabel6.setText("Resultados:");

        btnVolver.setText("Volver");
        btnVolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolverActionPerformed(evt);
            }
        });

        btnCalcular.setText("Calcular Racies");
        btnCalcular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCalcularActionPerformed(evt);
            }
        });

        btnGraficar.setText("Graficar");
        btnGraficar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGraficarActionPerformed(evt);
            }
        });

        btnCheckBiseccion.setText("Biseccion");
        btnCheckBiseccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCheckBiseccionActionPerformed(evt);
            }
        });

        btnChecRegulaFalsi.setText("RegulaFalsi");
        btnChecRegulaFalsi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChecRegulaFalsiActionPerformed(evt);
            }
        });

        btnChecNewtonRapson.setText("Newton Rapson");
        btnChecNewtonRapson.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChecNewtonRapsonActionPerformed(evt);
            }
        });

        btnChecPuntoFijo.setText("Punto Fijo");
        btnChecPuntoFijo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChecPuntoFijoActionPerformed(evt);
            }
        });

        btnChecSecante.setText("Secante");
        btnChecSecante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChecSecanteActionPerformed(evt);
            }
        });

        lblInFunG.setText("Ingrese el valor g(x) para Punto Fijo: ");

        txtFunY.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFunYActionPerformed(evt);
            }
        });

        jLabel8.setText("Ingrese el valor a(Xnuevo): ");

        txtFunGPF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFunGPFActionPerformed(evt);
            }
        });

        lblInFunG1.setText("Ingrese el valor g(x) derivada de F: ");

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel1.setText("Metodos Cerrados");

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel2.setText("Metodos Abiertos");

        jPGrafico.setBackground(new java.awt.Color(102, 102, 102));

        javax.swing.GroupLayout jPGraficoLayout = new javax.swing.GroupLayout(jPGrafico);
        jPGrafico.setLayout(jPGraficoLayout);
        jPGraficoLayout.setHorizontalGroup(
            jPGraficoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 552, Short.MAX_VALUE)
        );
        jPGraficoLayout.setVerticalGroup(
            jPGraficoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 579, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPInputLayout = new javax.swing.GroupLayout(jPInput);
        jPInput.setLayout(jPInputLayout);
        jPInputLayout.setHorizontalGroup(
            jPInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPInputLayout.createSequentialGroup()
                .addGroup(jPInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPInputLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPInputLayout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addComponent(btnCalcular)
                                .addGap(64, 64, 64)
                                .addComponent(btnGraficar))
                            .addComponent(jLabel6)
                            .addGroup(jPInputLayout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(27, 27, 27))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPInputLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnVolver, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPInputLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPInputLayout.createSequentialGroup()
                                .addComponent(lblInFunF)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtFunF))
                            .addGroup(jPInputLayout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(25, 25, 25)
                                .addComponent(txtVarTol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(jPInputLayout.createSequentialGroup()
                        .addGap(100, 100, 100)
                        .addComponent(lblTitulo)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPInputLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPInputLayout.createSequentialGroup()
                                .addGroup(jPInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPInputLayout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnCheckBiseccion)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnChecRegulaFalsi))
                                    .addGroup(jPInputLayout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtVarA, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtVarB, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPInputLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel2)
                                .addGap(95, 95, 95))
                            .addGroup(jPInputLayout.createSequentialGroup()
                                .addComponent(lblInFunG)
                                .addGap(26, 26, 26)
                                .addComponent(txtFunGPF))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPInputLayout.createSequentialGroup()
                                .addGroup(jPInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblInFunG1)
                                    .addComponent(jLabel8))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtVarXNuevo)
                                    .addComponent(txtFunY, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPInputLayout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addComponent(btnChecPuntoFijo)
                                .addGap(43, 43, 43)
                                .addComponent(btnChecNewtonRapson)
                                .addGap(18, 18, 18)
                                .addComponent(btnChecSecante)
                                .addGap(25, 25, 25)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPGrafico, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPInputLayout.setVerticalGroup(
            jPInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPInputLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitulo)
                .addGap(18, 18, 18)
                .addGroup(jPInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblInFunF)
                    .addComponent(txtFunF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtVarTol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCheckBiseccion)
                    .addComponent(btnChecRegulaFalsi))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtVarA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(txtVarB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnChecPuntoFijo)
                    .addComponent(btnChecSecante)
                    .addComponent(btnChecNewtonRapson))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtVarXNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblInFunG1)
                    .addComponent(txtFunY, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblInFunG)
                    .addComponent(txtFunGPF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCalcular)
                    .addComponent(btnGraficar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addGap(5, 5, 5)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnVolver)
                .addGap(59, 59, 59))
            .addGroup(jPInputLayout.createSequentialGroup()
                .addComponent(jPGrafico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPPrincialLayout = new javax.swing.GroupLayout(jPPrincial);
        jPPrincial.setLayout(jPPrincialLayout);
        jPPrincialLayout.setHorizontalGroup(
            jPPrincialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPPrincialLayout.createSequentialGroup()
                .addComponent(jPInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPPrincialLayout.setVerticalGroup(
            jPPrincialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPPrincialLayout.createSequentialGroup()
                .addComponent(jPInput, javax.swing.GroupLayout.PREFERRED_SIZE, 602, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        jMenu1.setText("Volver");

        jMenuItem1.setText("Volver");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Reset Botones");
        jMenu2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu2ActionPerformed(evt);
            }
        });
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPPrincial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPPrincial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenu2ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenu2ActionPerformed
        resetButtonStates();
    }// GEN-LAST:event_jMenu2ActionPerformed

    private void txtFunGPFActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtFunGPFActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_txtFunGPFActionPerformed

    private void txtVarAActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void txtFunFActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void txtFunYActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtVarAActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_txtVarAActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem1ActionPerformed
        this.setVisible(false);
    }// GEN-LAST:event_jMenuItem1ActionPerformed

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnVolverActionPerformed
        this.setVisible(false);
    }// GEN-LAST:event_btnVolverActionPerformed

    // MÉTODO REFACTORIZADO - REEMPLAZAR TODO EL MÉTODO btnCalcularActionPerformed
    private void btnCalcularActionPerformed(java.awt.event.ActionEvent evt) {
        btnCalcular.setEnabled(false);
        btnCalcular.setBackground(Color.RED);
        btnVolver.setEnabled(false);
        btnVolver.setBackground(Color.RED);

        try {
            // Crear input del modelo
            CalculationInput input = createInputFromUI();

            // Validar entrada
            ValidationResult validation = validator.validateInput(input);
            if (!validation.isValid()) {
                JOptionPane.showMessageDialog(this, validation.getErrorMessage(),
                        "Error de Validación", JOptionPane.ERROR_MESSAGE);
                resetButtonStates();
                return;
            }

            // Manejar derivada automática para métodos abiertos
            if (input.hasOpenMethodsSelected()) {
                handleDerivativeForOpenMethods(input);
            }

            // Ejecutar cálculos
            CalculationResult result = controller.executeCalculations(input);

            if (result.isSuccess()) {
                updateResultsDisplay(result);
                updateUIWithResults(result);

                // Aplicar cambios visuales
                aplicarCambiosVisualesSinDesplazamiento();

                // USAR EL SERVICIO DE PAGINACIÓN
                SwingUtilities.invokeLater(() -> {
                    paginationService.createTabbedGraphs(jPGrafico, input);

                    // Restaurar tamaño y posición si cambiaron
                    if (currentSize != null && !this.getSize().equals(currentSize)) {
                        this.setSize(currentSize);
                    }
                    if (currentLocation != null && !this.getLocation().equals(currentLocation)) {
                        this.setLocation(currentLocation);
                    }
                });

            } else {
                JOptionPane.showMessageDialog(this, result.getErrorMessage(),
                        "Error de Cálculo", JOptionPane.ERROR_MESSAGE);
            }

        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),
                    "Error de Validación de Datos", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error inesperado: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            SwingUtilities.invokeLater(() -> resetButtonStates());
        }
    }

    private CalculationInput createInputFromUI() {
        CalculationInput input = new CalculationInput();

        input.setFunX(txtFunF.getText().trim());
        input.setFunY(txtFunY.getText().trim());
        input.setFunGPF(txtFunGPF.getText().trim());

        // Obtener los textos sin convertir
        String aText = txtVarA.getText().trim();
        String bText = txtVarB.getText().trim();
        String xnText = txtVarXNuevo.getText().trim();
        String toleranciaText = txtVarTol.getText().trim();

        // Determinar qué tipos de métodos están seleccionados
        boolean hasClosedMethods = btnCheckBiseccion.isSelected() || btnChecRegulaFalsi.isSelected();
        boolean hasOpenMethods = btnChecNewtonRapson.isSelected() || btnChecPuntoFijo.isSelected()
                || btnChecSecante.isSelected();

        // Validar campos numéricos ANTES de convertir
        ValidationResult numericValidation = validator.validateNumericFields(
                aText, bText, xnText, toleranciaText, hasClosedMethods, hasOpenMethods);

        if (!numericValidation.isValid()) {
            throw new IllegalArgumentException(numericValidation.getErrorMessage());
        }

        // Si la validación pasa, entonces convertir los números
        try {
            if (!aText.isEmpty()) {
                input.setA(Double.valueOf(aText));
            }
            if (!bText.isEmpty()) {
                input.setB(Double.valueOf(bText));
            }
            if (!xnText.isEmpty()) {
                input.setXn(Double.valueOf(xnText));
            }
            if (!toleranciaText.isEmpty()) {
                input.setTolerancia(Double.valueOf(toleranciaText));
            }
        } catch (NumberFormatException e) {
            // Este catch no debería ejecutarse nunca debido a la validación previa
            throw new IllegalArgumentException("Error inesperado al procesar números: " + e.getMessage());
        }

        // Estados de los checkboxes
        input.setBiseccionSelected(btnCheckBiseccion.isSelected());
        input.setRegulaFalsiSelected(btnChecRegulaFalsi.isSelected());
        input.setNewtonRapsonSelected(btnChecNewtonRapson.isSelected());
        input.setPuntoFijoSelected(btnChecPuntoFijo.isSelected());
        input.setSecanteSelected(btnChecSecante.isSelected());

        return input;
    }

    private void handleDerivativeForOpenMethods(CalculationInput input) {
        if (input.getFunY() == null || input.getFunY().trim().isEmpty()) {
            int respuesta = JOptionPane.showConfirmDialog(this,
                    "El campo de la derivada está vacío. ¿Desea derivar automáticamente la función F(x)?",
                    "Derivación automática", JOptionPane.YES_NO_OPTION);

            if (respuesta == JOptionPane.YES_OPTION) {
                String derivada = derivacion.derivar(input.getFunX());
                int confirmar = JOptionPane.showConfirmDialog(this,
                        "La derivada calculada es: " + derivada + "\n¿Desea usarla?",
                        "Confirmar derivada", JOptionPane.YES_NO_OPTION);

                if (confirmar == JOptionPane.YES_OPTION) {
                    input.setFunY(derivada);
                    txtFunY.setText(derivada);
                }
            }
        }
    }

    private void updateResultsDisplay(CalculationResult result) {
        if (txtAResultados.getText().isBlank()) {
            txtAResultados.setText(result.getResults());
        } else {
            txtAResultados.setText(txtAResultados.getText() + "\n\n" + result.getResults());
        }
    }

    private void updateUIWithResults(CalculationResult result) {
        // Actualizar derivada si fue calculada
        if (result.getDerivative() != null) {
            txtFunY.setText(result.getDerivative());
            txtFunY.setBackground(Color.PINK);
            derivadaAux = result.getDerivative();
        }

        // Actualizar función generada si fue creada
        if (result.getGeneratedFunction() != null) {
            txtFunGPF.setText(result.getGeneratedFunction());
            txtFunGPF.setBackground(Color.YELLOW);
            funcionGGenerada = result.getGeneratedFunction();

            if (result.getGenerationInfo() != null) {
                JOptionPane.showMessageDialog(this,
                        "CORRECCIÓN AUTOMÁTICA - Punto Fijo\n" +
                                "=====================================\n" +
                                "g(x) = " + result.getGeneratedFunction() + "\n" +
                                result.getGenerationInfo() + "\n\n" +
                                "Esta función garantiza convergencia a la raíz correcta.",
                        "Corrección automática aplicada",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private void btnGraficarActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnGraficarActionPerformed
        try {
            CalculationInput input = createInputFromUI();

            // Usar paginación si ambos tipos están seleccionados
            paginationService.createTabbedGraphs(jPGrafico, input);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al graficar: " + e.getMessage(),
                    "Error de Graficación", JOptionPane.ERROR_MESSAGE);
        }
    }// GEN-LAST:event_btnGraficarActionPerformed

    private void btnChecPuntoFijoActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnChecPuntoFijoActionPerformed
        boolean metodosAbiertos = btnChecPuntoFijo.isSelected() || btnChecNewtonRapson.isSelected()
                || btnChecSecante.isSelected();
        boolean metodosCerrados = btnChecRegulaFalsi.isSelected() || btnCheckBiseccion.isSelected();

        txtFunF.setEnabled(metodosAbiertos || metodosCerrados);
        txtVarTol.setEnabled(metodosAbiertos || metodosCerrados);
        txtFunY.setEnabled(metodosAbiertos);
        txtFunGPF.setEnabled(metodosAbiertos); // Mismo tratamiento que txtFunY
        txtVarXNuevo.setEnabled(metodosAbiertos);
        txtVarA.setEnabled(metodosCerrados);
        txtVarB.setEnabled(metodosCerrados);

    }// GEN-LAST:event_btnChecPuntoFijoActionPerformed

    private void btnCheckBiseccionActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnCheckBiseccionActionPerformed
        boolean metodosAbiertos = btnChecPuntoFijo.isSelected() || btnChecNewtonRapson.isSelected()
                || btnChecSecante.isSelected();
        boolean metodosCerrados = btnChecRegulaFalsi.isSelected() || btnCheckBiseccion.isSelected();

        txtFunF.setEnabled(metodosAbiertos || metodosCerrados);
        txtVarTol.setEnabled(metodosAbiertos || metodosCerrados);
        txtVarA.setEnabled(metodosCerrados);
        txtVarB.setEnabled(metodosCerrados);
        txtFunY.setEnabled(metodosAbiertos);
        txtFunGPF.setEnabled(metodosAbiertos); // Mismo tratamiento que txtFunY
        txtVarXNuevo.setEnabled(metodosAbiertos);
    }// GEN-LAST:event_btnCheckBiseccionActionPerformed

    private void btnChecRegulaFalsiActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnChecRegulaFalsiActionPerformed
        boolean metodosAbiertos = btnChecPuntoFijo.isSelected() || btnChecNewtonRapson.isSelected()
                || btnChecSecante.isSelected();
        boolean metodosCerrados = btnChecRegulaFalsi.isSelected() || btnCheckBiseccion.isSelected();

        txtFunF.setEnabled(metodosAbiertos || metodosCerrados);
        txtVarTol.setEnabled(metodosAbiertos || metodosCerrados);
        txtVarA.setEnabled(metodosCerrados);
        txtVarB.setEnabled(metodosCerrados);
        txtFunY.setEnabled(metodosAbiertos);
        txtFunGPF.setEnabled(metodosAbiertos); // Mismo tratamiento que txtFunY
        txtVarXNuevo.setEnabled(metodosAbiertos);
    }// GEN-LAST:event_btnChecRegulaFalsiActionPerformed

    private void btnChecNewtonRapsonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnChecNewtonRapsonActionPerformed
        boolean metodosAbiertos = btnChecPuntoFijo.isSelected() || btnChecNewtonRapson.isSelected()
                || btnChecSecante.isSelected();
        boolean metodosCerrados = btnChecRegulaFalsi.isSelected() || btnCheckBiseccion.isSelected();

        txtFunF.setEnabled(metodosAbiertos || metodosCerrados);
        txtVarTol.setEnabled(metodosAbiertos || metodosCerrados);
        txtFunY.setEnabled(metodosAbiertos);
        txtFunGPF.setEnabled(metodosAbiertos); // Mismo tratamiento que txtFunY
        txtVarXNuevo.setEnabled(metodosAbiertos);
        txtVarA.setEnabled(metodosCerrados);
        txtVarB.setEnabled(metodosCerrados);

    }// GEN-LAST:event_btnChecNewtonRapsonActionPerformed

    private void btnChecSecanteActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnChecSecanteActionPerformed
        boolean metodosAbiertos = btnChecPuntoFijo.isSelected() || btnChecNewtonRapson.isSelected()
                || btnChecSecante.isSelected();
        boolean metodosCerrados = btnChecRegulaFalsi.isSelected() || btnCheckBiseccion.isSelected();

        txtFunF.setEnabled(metodosAbiertos || metodosCerrados);
        txtVarTol.setEnabled(metodosAbiertos || metodosCerrados);
        txtFunY.setEnabled(metodosAbiertos);
        txtFunGPF.setEnabled(metodosAbiertos); // Mismo tratamiento que txtFunY
        txtVarXNuevo.setEnabled(metodosAbiertos);
        txtVarA.setEnabled(metodosCerrados);
        txtVarB.setEnabled(metodosCerrados);
    }// GEN-LAST:event_btnChecSecanteActionPerformed

    private void graficarFuncionMetodosCerradosSimple(JPanel panelDestino) {
        String funcion = txtFunF.getText();
        ChartPanel chartPanel = Graficador.createChartPanelSimple(funcion);
        JFreeChart chart = chartPanel.getChart();
        XYPlot plot = (XYPlot) chart.getPlot();
        NumberAxis domainAxis = (NumberAxis) plot.getDomainAxis();
        domainAxis.setRange(a, b);
        plot.setDomainZeroBaselineVisible(true);
        plot.setRangeZeroBaselineVisible(true);
        panelDestino.setLayout(new BorderLayout());
        panelDestino.removeAll();
        panelDestino.add(chartPanel, BorderLayout.CENTER);
        panelDestino.revalidate();
        panelDestino.repaint();
    }

    private void graficarFuncionMetodosAbiertosSimple(String funcion1, String funcion2, JPanel panelDestino) {
        panelDestino.removeAll();
        panelDestino.setLayout(new BorderLayout());
        if (funcion1.isBlank() && !funcion2.isBlank()) {
            ChartPanel chartPanel = Graficador.createChartPanelSimple(funcion2);
            JFreeChart chart = chartPanel.getChart();
            XYPlot plot = (XYPlot) chart.getPlot();
            NumberAxis domainAxis = (NumberAxis) plot.getDomainAxis();
            domainAxis.setRange(a * (-2), a * 2);
            plot.setDomainZeroBaselineVisible(true);
            plot.setRangeZeroBaselineVisible(true);
            panelDestino.add(chartPanel, BorderLayout.CENTER);
        } else if (!funcion1.isBlank() && funcion2.isBlank()) {
            ChartPanel chartPanel = Graficador.createChartPanelSimple(funcion1);
            JFreeChart chart = chartPanel.getChart();
            XYPlot plot = (XYPlot) chart.getPlot();
            NumberAxis domainAxis = (NumberAxis) plot.getDomainAxis();
            domainAxis.setRange(a * (-2), a * 2);
            plot.setDomainZeroBaselineVisible(true);
            plot.setRangeZeroBaselineVisible(true);
            panelDestino.add(chartPanel, BorderLayout.CENTER);
        } else if (!funcion1.isBlank() && !funcion2.isBlank()) {
            ChartPanel chartPanel = Graficador.createChartPanelDoble(funcion1, funcion2, a * (-2), a * 2,
                    paso);
            JFreeChart chart = chartPanel.getChart();
            XYPlot plot = (XYPlot) chart.getPlot();
            plot.setDomainZeroBaselineVisible(true);
            plot.setRangeZeroBaselineVisible(true);
            panelDestino.add(chartPanel, BorderLayout.CENTER);
        }
        panelDestino.revalidate();
        panelDestino.repaint();
    }

    /**
     * Grafica inteligentemente las funciones según los métodos seleccionados:
     * - Punto Fijo + (Newton-Raphson o Secante): F, Y y GPF (3 funciones)
     * - Solo Punto Fijo: F y GPF (2 funciones)
     * - Solo Newton-Raphson/Secante: F y Y (2 funciones)
     */
    private void graficarFuncionesInteligente() {
        boolean puntoFijoSeleccionado = btnChecPuntoFijo.isSelected();
        boolean newtonRapsonSeleccionado = btnChecNewtonRapson.isSelected();
        boolean secanteSeleccionado = btnChecSecante.isSelected();

        String funF = txtFunF.getText().trim();
        String funY = txtFunY.getText().trim(); // Derivada
        String funGPF = txtFunGPF.getText().trim(); // Punto Fijo

        System.out.println("=== GRAFICACIÓN INTELIGENTE CON RANGO AUTOMÁTICO ===");
        System.out.println("Punto Fijo: " + puntoFijoSeleccionado);
        System.out.println("Newton-Raphson: " + newtonRapsonSeleccionado);
        System.out.println("Secante: " + secanteSeleccionado);
        System.out.println("F(x): " + funF);
        System.out.println("f'(x): " + funY);
        System.out.println("g(x): " + funGPF);

        jPGrafico.removeAll();
        jPGrafico.setLayout(new BorderLayout());

        // NO cambiar el layout si ya está establecido
        if (!(jPGrafico.getLayout() instanceof BorderLayout)) {
            jPGrafico.setLayout(new BorderLayout());
        }

        // Remover componentes de manera más estable
        Component[] components = jPGrafico.getComponents();
        for (Component comp : components) {
            jPGrafico.remove(comp);
        }

        // Usar el punto inicial como referencia para el cálculo automático
        Double puntoInteres = (xn != null) ? xn : null;

        if (puntoFijoSeleccionado && (newtonRapsonSeleccionado || secanteSeleccionado)) {
            // CASO 1: Graficar las 3 funciones (F, Y, GPF)
            System.out.println("Graficando 3 funciones con rango automático");

            if (!funF.isBlank() && !funY.isBlank() && !funGPF.isBlank()) {
                ChartPanel chartPanel = Graficador.createChartPanelTriple(funF, funY, funGPF,
                        puntoInteres);
                configurarEstiloGrafico(chartPanel);
                jPGrafico.add(chartPanel, BorderLayout.CENTER);
                System.out.println("✓ Graficadas 3 funciones con rango automático");
            } else if (!funF.isBlank() && !funGPF.isBlank()) {
                ChartPanel chartPanel = Graficador.createChartPanelDoble(funF, funGPF, puntoInteres);
                configurarEstiloGrafico(chartPanel);
                jPGrafico.add(chartPanel, BorderLayout.CENTER);
                System.out.println("✓ Fallback: F(x) y g(x) con rango automático");
            } else if (!funF.isBlank() && !funY.isBlank()) {
                ChartPanel chartPanel = Graficador.createChartPanelDoble(funF, funY, puntoInteres);
                configurarEstiloGrafico(chartPanel);
                jPGrafico.add(chartPanel, BorderLayout.CENTER);
                System.out.println("✓ Fallback: F(x) y f'(x) con rango automático");
            }

        } else if (puntoFijoSeleccionado) {
            // CASO 2: Solo Punto Fijo - Graficar F y GPF
            System.out.println("Graficando Punto Fijo con rango automático");

            if (!funF.isBlank() && !funGPF.isBlank()) {
                ChartPanel chartPanel = Graficador.createChartPanelDoble(funF, funGPF, puntoInteres);
                configurarEstiloGrafico(chartPanel);
                jPGrafico.add(chartPanel, BorderLayout.CENTER);
                System.out.println("✓ Graficadas F(x) y g(x) con rango automático");
            } else if (!funF.isBlank()) {
                ChartPanel chartPanel = Graficador.createChartPanelSimpleConRango(funF, puntoInteres);
                configurarEstiloGrafico(chartPanel);
                jPGrafico.add(chartPanel, BorderLayout.CENTER);
                System.out.println("✓ Solo F(x) con rango automático");
            }

        } else if (newtonRapsonSeleccionado || secanteSeleccionado) {
            // CASO 3: Solo Newton-Raphson/Secante - Graficar F y Y
            System.out.println("Graficando Newton-Raphson/Secante con rango automático");

            if (!funF.isBlank() && !funY.isBlank()) {
                ChartPanel chartPanel = Graficador.createChartPanelDoble(funF, funY, puntoInteres);
                configurarEstiloGrafico(chartPanel);
                jPGrafico.add(chartPanel, BorderLayout.CENTER);
                System.out.println("✓ Graficadas F(x) y f'(x) con rango automático");
            } else if (!funF.isBlank()) {
                ChartPanel chartPanel = Graficador.createChartPanelSimpleConRango(funF, puntoInteres);
                configurarEstiloGrafico(chartPanel);
                jPGrafico.add(chartPanel, BorderLayout.CENTER);
                System.out.println("✓ Solo F(x) con rango automático");
            }
        }

        // Revalidar de manera controlada
        SwingUtilities.invokeLater(() -> {
            jPGrafico.revalidate();
            jPGrafico.repaint();
        });
    }

    /**
     * Configura el estilo visual de los gráficos
     */
    private void configurarEstiloGrafico(ChartPanel chartPanel) {
        JFreeChart chart = chartPanel.getChart();
        XYPlot plot = (XYPlot) chart.getPlot();

        // NO configurar rango manualmente - usar el automático
        // domainAxis.setRange(rangoMin, rangoMax); // ← ELIMINAR ESTA LÍNEA

        // Solo configurar el estilo visual
        plot.setDomainZeroBaselineVisible(true);
        plot.setRangeZeroBaselineVisible(true);

        plot.setBackgroundPaint(Color.LIGHT_GRAY);
        NumberAxis domainAxis = (NumberAxis) plot.getDomainAxis();
        domainAxis.setAxisLinePaint(Color.BLACK);
        domainAxis.setTickMarkPaint(Color.BLACK);
        domainAxis.setTickLabelPaint(Color.BLACK);
        plot.setRangeGridlinePaint(Color.GRAY);
    }

    private void graficarFuncionMetodosCerrados() {
        String funcion = txtFunF.getText();

        // Para métodos cerrados, usar el intervalo [a, b] como referencia
        Double puntoMedio = (a != null && b != null) ? (a + b) / 2 : null;
        ChartPanel chartPanel = Graficador.createChartPanelSimpleConRango(funcion, puntoMedio);

        JFreeChart chart = chartPanel.getChart();
        XYPlot plot = (XYPlot) chart.getPlot();

        // Para métodos cerrados, SÍ forzar el rango a [a, b]
        NumberAxis domainAxis = (NumberAxis) plot.getDomainAxis();
        domainAxis.setRange(a, b);

        plot.setDomainZeroBaselineVisible(true);
        plot.setRangeZeroBaselineVisible(true);
        plot.setBackgroundPaint(Color.GRAY);
        domainAxis.setAxisLinePaint(Color.WHITE);
    }

    private void graficarFuncionMetodosAbiertos(String funcion1, String funcion2) {
        jPGrafico.removeAll();
        jPGrafico.setLayout(new BorderLayout());

        if (funcion1.isBlank() && !funcion2.isBlank()) {
            ChartPanel chartPanel = Graficador.createChartPanelSimple(funcion2);

            // Obtener el chart del chartPanel
            JFreeChart chart = chartPanel.getChart();
            XYPlot plot = (XYPlot) chart.getPlot();

            // Configurar los límites del eje X
            NumberAxis domainAxis = (NumberAxis) plot.getDomainAxis();
            domainAxis.setRange(a * (-2), a * 2);

            // Resaltar los ejes x e y en 0
            plot.setDomainZeroBaselineVisible(true);
            plot.setRangeZeroBaselineVisible(true);

            // Cambiar el fondo a oscuro y configurar los colores de los ejes
            plot.setBackgroundPaint(Color.GRAY); // Fondo oscuro
            domainAxis.setAxisLinePaint(Color.GRAY); // Línea del eje X en blanco
            domainAxis.setTickMarkPaint(Color.WHITE); // Marcas de graduación en blanco
            domainAxis.setTickLabelPaint(Color.WHITE); // Etiquetas en blanco
            plot.setRangeGridlinePaint(Color.WHITE); // Líneas de grid en blanco

            // Añadir el chartPanel al jPGrafico
            jPGrafico.add(chartPanel, BorderLayout.CENTER);
        } else if (!funcion1.isBlank() && funcion2.isBlank()) {
            ChartPanel chartPanel = Graficador.createChartPanelSimple(funcion1);

            // Obtener el chart del chartPanel
            JFreeChart chart = chartPanel.getChart();
            XYPlot plot = (XYPlot) chart.getPlot();

            // Configurar los límites del eje X
            NumberAxis domainAxis = (NumberAxis) plot.getDomainAxis();
            domainAxis.setRange(a * (-2), a * 2);

            // Resaltar los ejes x e y en 0
            plot.setDomainZeroBaselineVisible(true);
            plot.setRangeZeroBaselineVisible(true);

            // Cambiar el fondo a oscuro y configurar los colores de los ejes
            plot.setBackgroundPaint(Color.GRAY); // Fondo oscuro
            domainAxis.setAxisLinePaint(Color.WHITE); // Línea del eje X en blanco
            domainAxis.setTickMarkPaint(Color.WHITE); // Marcas de graduación en blanco
            domainAxis.setTickLabelPaint(Color.WHITE); // Etiquetas en blanco
            plot.setRangeGridlinePaint(Color.WHITE); // Líneas de grid en blanco

            // Añadir el chartPanel al jPGrafico
            jPGrafico.add(chartPanel, BorderLayout.CENTER);
        } else if (!funcion1.isBlank() && !funcion2.isBlank()) {
            ChartPanel chartPanel = Graficador.createChartPanelDoble(funcion1, funcion2, a * (-2), a * 2,
                    paso);

            // Obtener el chart del chartPanel
            JFreeChart chart = chartPanel.getChart();
            XYPlot plot = (XYPlot) chart.getPlot();

            // Resaltar los ejes x e y en 0
            plot.setDomainZeroBaselineVisible(true);
            plot.setRangeZeroBaselineVisible(true);

            // Cambiar el fondo a oscuro y configurar los colores de los ejes
            plot.setBackgroundPaint(Color.GRAY); // Fondo oscuro
            NumberAxis domainAxis = (NumberAxis) plot.getDomainAxis();
            domainAxis.setAxisLinePaint(Color.WHITE); // Línea del eje X en blanco
            domainAxis.setTickMarkPaint(Color.WHITE); // Marcas de graduación en blanco
            domainAxis.setTickLabelPaint(Color.WHITE); // Etiquetas en blanco
            plot.setRangeGridlinePaint(Color.WHITE); // Líneas de grid en blanco

            // Añadir el chartPanel al jPGrafico
            jPGrafico.add(chartPanel, BorderLayout.CENTER);
        }

        jPGrafico.revalidate();
        jPGrafico.repaint();
    }

    private void graficarAmbosSiEsNecesario() {
        // Usar la graficación inteligente que considera los colores distintivos
        // y las funciones específicas de cada método
        graficarFuncionesInteligente();

        // Aplicar colores de campos según el tipo de método seleccionado
        boolean metodosAbiertos = btnChecPuntoFijo.isSelected() || btnChecNewtonRapson.isSelected()
                || btnChecSecante.isSelected();
        boolean metodosCerrados = btnChecRegulaFalsi.isSelected() || btnCheckBiseccion.isSelected();

        if (metodosCerrados) {
            txtVarA.setBackground(Color.BLUE);
            txtVarA.setOpaque(true);
            txtVarA.repaint();
            txtVarB.setBackground(Color.BLUE);
            txtVarB.setOpaque(true);
            txtVarB.repaint();
        }

        if (metodosAbiertos) {
            txtVarXNuevo.setBackground(Color.MAGENTA);
            txtVarXNuevo.setOpaque(true);
            txtVarXNuevo.repaint();
        }

        // Siempre aplicar colores distintivos a las funciones
        txtVarTol.setBackground(Color.GRAY);
        txtVarTol.setOpaque(true);
        txtVarTol.repaint();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCalcular;
    private javax.swing.JCheckBox btnChecNewtonRapson;
    private javax.swing.JCheckBox btnChecPuntoFijo;
    private javax.swing.JCheckBox btnChecRegulaFalsi;
    private javax.swing.JCheckBox btnChecSecante;
    private javax.swing.JCheckBox btnCheckBiseccion;
    private javax.swing.JButton btnGraficar;
    private javax.swing.JButton btnVolver;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPGrafico;
    private javax.swing.JPanel jPInput;
    private javax.swing.JPanel jPPrincial;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblInFunF;
    private javax.swing.JLabel lblInFunG;
    private javax.swing.JLabel lblInFunG1;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JTextArea txtAResultados;
    private javax.swing.JTextField txtFunF;
    private javax.swing.JTextField txtFunGPF;
    private javax.swing.JTextField txtFunY;
    private javax.swing.JTextField txtVarA;
    private javax.swing.JTextField txtVarB;
    private javax.swing.JTextField txtVarTol;
    private javax.swing.JTextField txtVarXNuevo;
    // End of variables declaration//GEN-END:variables

    private void resetButtonStates() {
        btnCalcular.setEnabled(true);
        btnCalcular.setBackground(null);
        btnVolver.setEnabled(true);
        btnVolver.setBackground(null);
    }

    private void aplicarCambiosVisualesSinDesplazamiento() {
        // Actualizar el estado de métodos abiertos
        metodosAbiertos = btnChecPuntoFijo.isSelected() || btnChecNewtonRapson.isSelected()
                || btnChecSecante.isSelected();

        // Deshabilitar repaint automático temporalmente
        setIgnoreRepaint(true);

        try {
            // Aplicar todos los cambios visuales
            if (derivadaAux != null && !derivadaAux.isBlank() && metodosAbiertos) {
                txtFunY.setText(derivadaAux);
                txtFunY.setBackground(Color.PINK);

                lblInFunF.setForeground(Color.RED);
                lblInFunG.setForeground(Color.BLUE);
            }

            // Cambios en campos de Punto Fijo
            if (btnChecPuntoFijo.isSelected() && funcionGGenerada != null) {
                txtFunGPF.setText(funcionGGenerada);
                txtFunGPF.setBackground(Color.YELLOW);
            }

        } finally {
            // Restaurar repaint automático
            setIgnoreRepaint(false);

            // Hacer un solo repaint coordinado
            SwingUtilities.invokeLater(() -> {
                this.revalidate();
                this.repaint();
            });
        }
    }
}