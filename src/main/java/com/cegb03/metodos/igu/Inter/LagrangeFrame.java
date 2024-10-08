package com.cegb03.metodos.igu.Inter;

import com.cegb03.metodos.logica.Interpolaciones.LagrangeCode;
import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author cegb03
 */
public class LagrangeFrame extends javax.swing.JFrame {
    
    private Double[][] matrix;
    private Double[][] matrixA;
    private Double[] matrixB;
    
    private int filas = 0;
    private int columnas = 0;
    
    double coeficienteInterpolador;//Coeficiente a interpolar
    private String funX;

    /**
     * Creates new form EliminacionGaussianaFrame
     */
    public LagrangeFrame() {
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

        jPanel1 = new javax.swing.JPanel();
        btnCargarArchivo = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        btnVolver = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAResultados = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        txtFunX = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtCoeInter = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnCargarArchivo.setText("Cargar Archivo");
        btnCargarArchivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCargarArchivoActionPerformed(evt);
            }
        });

        jLabel1.setText("Metodo de Interpolacion con Lagrange");

        btnVolver.setText("Volver");
        btnVolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolverActionPerformed(evt);
            }
        });

        txtAResultados.setEditable(false);
        txtAResultados.setColumns(20);
        txtAResultados.setRows(5);
        jScrollPane1.setViewportView(txtAResultados);

        jLabel2.setText("Ingrese la Funcion de ser posible");

        txtFunX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFunXActionPerformed(evt);
            }
        });

        jLabel3.setText("Ingrese el coeficiente interpolador");

        txtCoeInter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCoeInterActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtFunX))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnVolver))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCoeInter, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                        .addComponent(btnCargarArchivo)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtFunX, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtCoeInter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnCargarArchivo)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnVolver)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCargarArchivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCargarArchivoActionPerformed
        if(!txtFunX.getText().isBlank())
            funX = txtFunX.getText();
        else{
            JOptionPane.showMessageDialog(this, "No hay funcion definida, se realizara lo posible menos el calculo del error.");
            funX = "";
        }
        coeficienteInterpolador = Double.parseDouble(txtCoeInter.getText());
        System.out.println("coeficienteInterpolador = " + coeficienteInterpolador);
        loadMatrixFromFile();
        //System.out.println("loadMatrixFromFile() fin");
        separarMatricesAB();
        printMatrix();
//         descomentar las lineas de abajo y terminar de pasar la eliminacion gaussiana.
        LagrangeCode lc = new LagrangeCode(matrixA, matrixB, filas, columnas, coeficienteInterpolador, funX);
        if(txtAResultados.getText().isBlank()){
            //System.out.println("matriz recuperda:\n"+printMatrix()+"\nFin Matriz recuperada.");
            txtAResultados.setText(imprimir()+"\nTolerancia usada: "+"\n"+lc.interpolar()); // Puedes modificar esta línea para otro procesamiento
        }
        else
            txtAResultados.setText(txtAResultados.getText()+"\n"+imprimir()+"\nTolerancia usada: "+"\n"+lc.interpolar()); // Puedes modificar esta línea para otro procesamiento
        
        System.out.println("Fin Elimnacion");
    }//GEN-LAST:event_btnCargarArchivoActionPerformed
    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_btnVolverActionPerformed

    private void txtFunXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFunXActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFunXActionPerformed

    private void txtCoeInterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCoeInterActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCoeInterActionPerformed

    private void separarMatricesAB(){
        int columns=--columnas;
        //System.out.println("columnas " + columnas + " filas " + filas + " columns " + columns);
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columns; j++)
                matrixA[i][j] = matrix[i][j];
            matrixB[i] = matrix[i][columns];
        }
        //imprimir();
    }
    
    private void loadMatrixFromFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                readMatrixFromFile(selectedFile);
                if(txtAResultados.getText().isBlank()){
                    System.out.println("matriz recuperda:\n"+printMatrix()+"\nFin Matriz recuperada.");
                    txtAResultados.setText(printMatrix()); // Puedes modificar esta línea para otro procesamiento
                }
                else
                    txtAResultados.setText(txtAResultados.getText()+"\n"+printMatrix()); // Puedes modificar esta línea para otro procesamiento
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error al leer el archivo", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    private void readMatrixFromFile(File file) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        int rows = 0;
        int cols = 0;
        Double[][] matrizCarga = null;

        // Primer pase para determinar el tamaño de la matriz
        while ((line = br.readLine()) != null) {
            String[] values = line.trim().split("\\s+|,");
            if (matrizCarga == null) {
                cols = values.length;
                matrizCarga = new Double[countLines(file)][cols];
            }
            for (int i = 0; i < cols; i++) {
                matrizCarga[rows][i] = Double.valueOf(values[i]);
            }
            rows++;
        }
        br.close();
        filas = rows;
        columnas = cols;
        this.matrix = new Double[filas][columnas];
        this.matrixA = new Double[filas][columnas];
        this.matrixB = new Double[filas];
        
        for (int i = 0; i < filas; i++)
            System.arraycopy(matrizCarga[i], 0, this.matrix[i], 0, columnas);
    }

    private int countLines(File file) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        int lines = 0;
        while (br.readLine() != null) {
            lines++;
        }
        br.close();
        return lines;
    }

    private String printMatrix() {
        String matriz;
        matriz = "";
        for (Double[] row : matrix) {
            for (Double value : row) {
                matriz += (value + " ");
            }
            matriz += "\n";
        }
        return matriz;
    }
    
    public String imprimir() {
        String matrizImpresa = "";
        matrizImpresa += ("Impresion de matrices A y b separadas\n");
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++)
                matrizImpresa += (matrixA[i][j] + " ");
            matrizImpresa += ("| " + matrixB[i]+"\n");
        }
        matrizImpresa += "\n";
        return matrizImpresa;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCargarArchivo;
    private javax.swing.JButton btnVolver;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea txtAResultados;
    private javax.swing.JTextField txtCoeInter;
    private javax.swing.JTextField txtFunX;
    // End of variables declaration//GEN-END:variables
}
