package com.cegb03.metodos.igu.LR.A;

import com.cegb03.metodos.logica.Graficador;
import com.cegb03.metodos.logica.LocRaices.Abiertos.SecanteCode;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JOptionPane;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.axis.NumberAxis;


/**
 *
 * @author cegb03
 */
public class SecanteFrame extends javax.swing.JFrame {
    
    private String funX;
    private String funY;
    private Double a;
    private Double tolerancia;
    private double paso = 0.01; // Ajusta el paso si es necesario

    /**
     * Creates new form Biseccion
     */
    public SecanteFrame() {
        initComponents();
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPPrincial = new javax.swing.JPanel();
        jPInput = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtFunX = new javax.swing.JTextField();
        txtFunY = new javax.swing.JTextField();
        txtVarA = new javax.swing.JTextField();
        txtVarTol = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAResultados = new javax.swing.JTextArea();
        jLabel6 = new javax.swing.JLabel();
        btnVolver = new javax.swing.JButton();
        btnCalcular = new javax.swing.JButton();
        btnGraficar = new javax.swing.JButton();
        jPGrafico = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Metodo Abierto de Newton-Rapson");

        jLabel2.setText("Ingrese el valor f(x): ");

        jLabel3.setText("Ingrese el valor g(x) derivada de F: ");

        jLabel4.setText("Ingrese el valor a(Xnuevo): ");

        jLabel5.setText("Ingrese el valor error tolerable: ");

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

        javax.swing.GroupLayout jPInputLayout = new javax.swing.GroupLayout(jPInput);
        jPInput.setLayout(jPInputLayout);
        jPInputLayout.setHorizontalGroup(
            jPInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPInputLayout.createSequentialGroup()
                .addGroup(jPInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPInputLayout.createSequentialGroup()
                            .addGap(23, 23, 23)
                            .addComponent(btnCalcular)
                            .addGap(18, 18, 18)
                            .addComponent(btnGraficar)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnVolver, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPInputLayout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(jPInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel4)
                                .addComponent(jLabel5))
                            .addGap(25, 25, 25)
                            .addGroup(jPInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtVarA)
                                .addComponent(txtVarTol)))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPInputLayout.createSequentialGroup()
                                .addGap(99, 99, 99)
                                .addComponent(jLabel1))
                            .addGroup(jPInputLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtFunY, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtFunX, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPInputLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPInputLayout.setVerticalGroup(
            jPInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPInputLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(26, 26, 26)
                .addGroup(jPInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtFunX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtFunY, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(jPInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtVarA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtVarTol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnVolver)
                    .addComponent(btnCalcular)
                    .addComponent(btnGraficar))
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPGrafico.setBackground(new java.awt.Color(102, 102, 102));

        javax.swing.GroupLayout jPGraficoLayout = new javax.swing.GroupLayout(jPGrafico);
        jPGrafico.setLayout(jPGraficoLayout);
        jPGraficoLayout.setHorizontalGroup(
            jPGraficoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 450, Short.MAX_VALUE)
        );
        jPGraficoLayout.setVerticalGroup(
            jPGraficoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPPrincialLayout = new javax.swing.GroupLayout(jPPrincial);
        jPPrincial.setLayout(jPPrincialLayout);
        jPPrincialLayout.setHorizontalGroup(
            jPPrincialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPPrincialLayout.createSequentialGroup()
                .addComponent(jPInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPGrafico, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPPrincialLayout.setVerticalGroup(
            jPPrincialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPInput, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPGrafico, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPPrincial, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPPrincial, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_btnVolverActionPerformed

    private void btnCalcularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCalcularActionPerformed
        btnCalcular.setEnabled(false);
        btnCalcular.setBackground(Color.red);
        btnVolver.setEnabled(false);
        btnVolver.setBackground(Color.red);
        if (txtFunX.getText() == null)
            JOptionPane.showMessageDialog(this, "Ingrese el valor de la Funcion F(x)");
        if (txtFunY.getText() == null)
            JOptionPane.showMessageDialog(this, "Ingrese el valor de la Funcion G(x)");
        if (txtVarA.getText() == null)
            JOptionPane.showMessageDialog(this, "Ingrese el valor de inicio a");
        if (txtVarTol.getText() == null)
            JOptionPane.showMessageDialog(this, "Ingrese el valor del error tolerable");
        funX = txtFunX.getText();
        funY = txtFunY.getText();
        System.out.println( "funX " + funX + " funY " + funY );
        a = Double.valueOf(txtVarA.getText());
        tolerancia = Double.valueOf(txtVarTol.getText());
        SecanteCode sc = new SecanteCode(a, tolerancia, funX, funY);
        if(txtAResultados.getText().isBlank())
            txtAResultados.setText(sc.calcularRaiz());
        else
            txtAResultados.setText(txtAResultados.getText()+"\n"+sc.calcularRaiz());
        if( funX.isBlank() )
        graficarFuncion(funX, funY);
        btnCalcular.setBackground(Color.green);
        btnVolver.setBackground(Color.green);
        btnCalcular.setEnabled(true);
        btnVolver.setEnabled(true);
    }//GEN-LAST:event_btnCalcularActionPerformed

    private void btnGraficarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGraficarActionPerformed
        funX = txtFunX.getText();
        funY = txtFunY.getText();
        a = Double.valueOf(txtVarA.getText());
        graficarFuncion(funX, funY);
    }//GEN-LAST:event_btnGraficarActionPerformed
        
    private void graficarFuncion(String funcion1, String funcion2) {
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

            // Añadir el chartPanel al jPGrafico
            jPGrafico.add(chartPanel, BorderLayout.CENTER);
        } else if (!funcion1.isBlank() && !funcion2.isBlank()) {
            ChartPanel chartPanel = Graficador.createChartPanelDoble(funcion1, funcion2, a * (-2), a * 2, paso);

            // Añadir el chartPanel al jPGrafico
            jPGrafico.add(chartPanel, BorderLayout.CENTER);
        }

        jPGrafico.revalidate();
        jPGrafico.repaint();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCalcular;
    private javax.swing.JButton btnGraficar;
    private javax.swing.JButton btnVolver;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPGrafico;
    private javax.swing.JPanel jPInput;
    private javax.swing.JPanel jPPrincial;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea txtAResultados;
    private javax.swing.JTextField txtFunX;
    private javax.swing.JTextField txtFunY;
    private javax.swing.JTextField txtVarA;
    private javax.swing.JTextField txtVarTol;
    // End of variables declaration//GEN-END:variables
}
