package com.cegb03.metodos.igu.LR.C;

import com.cegb03.metodos.logica.Graficador;
import com.cegb03.metodos.logica.LocRaices.Cerrados.RegulaFalsiCode;
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
public class RegulaFalsiFrame extends javax.swing.JFrame {
    
    private String funX;
    private Double a;
    private Double b;
    private Double tolerancia;

    /**
     * Creates new form Biseccion
     */
    public RegulaFalsiFrame() {
        initComponents();
        btnCalcular.setBackground(Color.green);
        btnVolver.setBackground(Color.green);
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
        txtVarA = new javax.swing.JTextField();
        txtVarB = new javax.swing.JTextField();
        txtVarTol = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAResultados = new javax.swing.JTextArea();
        jLabel6 = new javax.swing.JLabel();
        btnVolver = new javax.swing.JButton();
        btnCalcular = new javax.swing.JButton();
        jPGrafico = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Metodo Cerrado de Regula Falsi");

        jLabel2.setText("Ingrese el valor f(x): ");

        jLabel3.setText("Ingrese el valor a: ");

        jLabel4.setText("Ingrese el valor b: ");

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

        javax.swing.GroupLayout jPInputLayout = new javax.swing.GroupLayout(jPInput);
        jPInput.setLayout(jPInputLayout);
        jPInputLayout.setHorizontalGroup(
            jPInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPInputLayout.createSequentialGroup()
                .addGroup(jPInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPInputLayout.createSequentialGroup()
                                .addGap(99, 99, 99)
                                .addComponent(jLabel1))
                            .addGroup(jPInputLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3))
                                .addGap(90, 90, 90)
                                .addGroup(jPInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtFunX)
                                    .addComponent(txtVarA, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)))
                            .addGroup(jPInputLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5))
                                .addGap(25, 25, 25)
                                .addGroup(jPInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtVarB)
                                    .addComponent(txtVarTol)))
                            .addGroup(jPInputLayout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addComponent(btnCalcular)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 131, Short.MAX_VALUE)
                                .addComponent(btnVolver, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPInputLayout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jLabel6))))
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
                    .addComponent(txtVarA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(jPInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtVarB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtVarTol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnVolver)
                    .addComponent(btnCalcular))
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
            .addGap(0, 474, Short.MAX_VALUE)
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
        if (txtFunX.getText() == null)
            JOptionPane.showMessageDialog(this, "Ingrese el valor de la Funcion");
        if (txtVarA.getText() == null)
            JOptionPane.showMessageDialog(this, "Ingrese el valor del limite inferior a");
        if (txtVarB.getText() == null)
            JOptionPane.showMessageDialog(this, "Ingrese el valor del limite inferior b");
        if (txtVarTol.getText() == null)
            JOptionPane.showMessageDialog(this, "Ingrese el valor del error tolerable");
        btnCalcular.setEnabled(false);
        btnCalcular.setBackground(Color.red);
        btnVolver.setEnabled(false);
        btnVolver.setBackground(Color.red);
        funX = txtFunX.getText();
        System.out.println("funX "+funX);
        a = Double.parseDouble(txtVarA.getText());
        b = Double.parseDouble(txtVarB.getText());
        tolerancia = Double.parseDouble(txtVarTol.getText());
        RegulaFalsiCode rfc = new RegulaFalsiCode(a, b, tolerancia, funX);
        if(txtAResultados.getText().isBlank())
            txtAResultados.setText(rfc.calcularRaiz());
        else
            txtAResultados.setText(txtAResultados.getText()+"\n"+rfc.calcularRaiz());
        graficarFuncion();
        btnCalcular.setBackground(Color.green);
        btnVolver.setBackground(Color.green);
        btnCalcular.setEnabled(true);
        btnVolver.setEnabled(true);
    }//GEN-LAST:event_btnCalcularActionPerformed
        
    private void graficarFuncion() {
        String funcion = txtFunX.getText();
        ChartPanel chartPanel = Graficador.createChartPanelSimple(funcion);

        // Obtener el chart del chartPanel
        JFreeChart chart = chartPanel.getChart();
        XYPlot plot = (XYPlot) chart.getPlot();

        // Configurar los límites del eje X
        NumberAxis domainAxis = (NumberAxis) plot.getDomainAxis();
        domainAxis.setRange(a, b);

        // Limpiar y agregar el chartPanel al jPGrafico
        jPGrafico.removeAll();
        jPGrafico.setLayout(new BorderLayout());
        jPGrafico.add(chartPanel, BorderLayout.CENTER);
        jPGrafico.revalidate();
        jPGrafico.repaint();
    }    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCalcular;
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
    private javax.swing.JTextField txtVarA;
    private javax.swing.JTextField txtVarB;
    private javax.swing.JTextField txtVarTol;
    // End of variables declaration//GEN-END:variables
}
