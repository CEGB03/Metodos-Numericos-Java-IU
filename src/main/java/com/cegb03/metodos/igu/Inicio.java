package com.cegb03.metodos.igu;

import com.cegb03.metodos.igu.LR.A.*;
import com.cegb03.metodos.igu.LR.C.*;
import com.cegb03.metodos.igu.SEL.*;
import com.cegb03.metodos.igu.Inter.LagrangeFrame;
import com.cegb03.metodos.igu.Inter.PolinomialFrame;
import com.cegb03.metodos.igu.reg.RegresionLinealFrame;
import com.cegb03.metodos.igu.reg.RegresionPolinomialFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author cegb03
 */
public class Inicio extends javax.swing.JFrame {

    // Crear un JComboBox para seleccionar el Tipo de Localizacion
    JComboBox<String> comboBoxLocRaices = new JComboBox<>();
    JComboBox<String> comboBoxSisEcuaLin = new JComboBox<>();
    JComboBox<String> comboBoxInterpolaciones = new JComboBox<>();
    JComboBox<String> comboBoxRegresiones = new JComboBox<>();

    // Crear JComboBox para seleccionar el nombre del metodo
    JComboBox<String> comboBoxMetodosDisponibles = new JComboBox<>();
// String[] metodos = {          0       ,             1         ,          2        ,               3              ,          4        ,
    String[] metodos = {"Biseccion", "Regula Falsi", "Punto Fijo", "Newton-Rapson", "Tangente", 
//                                                          5                  ,     6     ,             7            ,
                                        "Eliminacion Gaussiana", "Jacobi", "Gauss-Seidel", 
//                                              8        ,         9          ,
                                        "Lagrange", "Polinomial",
//                                          10   ,          11      , };
                                        "Lineal", "Polinomial"};

    private void actualizarSeleccion(JComboBox comboBoxMetodosDisponibles, String[] metodos, JComboBox comboBoxMetodos){
            switch ((String) comboBoxMetodos.getSelectedItem()) {
                case "Metodos Cerrados" -> {
                    comboBoxMetodosDisponibles.removeAllItems();
                    for (int i = 0; i < 2; i++) 
                        comboBoxMetodosDisponibles.addItem(metodos[i]);
                }
                case "Metodos Abiertos" -> {
                    comboBoxMetodosDisponibles.removeAllItems();
                    for (int i = 2; i < 5; i++) 
                        comboBoxMetodosDisponibles.addItem(metodos[i]);
                }
                case "Metodos Sistema de Ecuaciones Algebraicas Lineales" -> {
                    comboBoxMetodosDisponibles.removeAllItems();
                    for (int i = 5; i < 6; i++) 
                        comboBoxMetodosDisponibles.addItem(metodos[i]);
                }
                case "Metodos Iterativos para Sistemas de Ecuaciones Lineales" -> {
                    comboBoxMetodosDisponibles.removeAllItems();
                    for (int i = 6; i < 8; i++) 
                        comboBoxMetodosDisponibles.addItem(metodos[i]);
                }  
                case "Metodos de Interpolacion" -> {
                    comboBoxMetodosDisponibles.removeAllItems();
                    for (int i = 8; i < 10; i++)
                        comboBoxMetodosDisponibles.addItem(metodos[i]);
                }  
/*                case "Metodos de Interpolacion Segmentaria" -> {
                    comboBoxMetodosDisponibles.removeAllItems();
                    for (int i = 100; i < 110; i++)
                        comboBoxMetodosDisponibles.addItem(metodos[i]);
                }*/
                case "Metodos de Regresioncion" -> {
                    comboBoxMetodosDisponibles.removeAllItems();
                    for (int i = 10; i < 12; i++)
                        comboBoxMetodosDisponibles.addItem(metodos[i]);
                }
                default -> {
                }
            }
    }
    /**
     * Creates new form Inicio
     */
    public Inicio() {
        initComponents();
        comboBoxLocRaices.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                actualizarSeleccion(comboBoxMetodosDisponibles, metodos, comboBoxLocRaices);
            }
        });
        comboBoxSisEcuaLin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                actualizarSeleccion(comboBoxMetodosDisponibles, metodos, comboBoxSisEcuaLin);
            }
        });
        comboBoxInterpolaciones.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                actualizarSeleccion(comboBoxMetodosDisponibles, metodos, comboBoxInterpolaciones);
            }
        });
        comboBoxRegresiones.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                actualizarSeleccion(comboBoxMetodosDisponibles, metodos, comboBoxRegresiones);
            }
        });
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
        btnLocRaices = new javax.swing.JButton();
        btnSisEcuLin = new javax.swing.JButton();
        btnInterpolacion = new javax.swing.JButton();
        btnRegresion = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnLocRaices.setText("Loc Raices");
        btnLocRaices.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLocRaicesActionPerformed(evt);
            }
        });

        btnSisEcuLin.setText("Sis Ecu Lin");
        btnSisEcuLin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSisEcuLinActionPerformed(evt);
            }
        });

        btnInterpolacion.setText("Interpolacion");
        btnInterpolacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInterpolacionActionPerformed(evt);
            }
        });

        btnRegresion.setText("Regresion");
        btnRegresion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegresionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnInterpolacion)
                    .addComponent(btnLocRaices))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSisEcuLin)
                    .addComponent(btnRegresion))
                .addContainerGap(174, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLocRaices)
                    .addComponent(btnSisEcuLin))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnInterpolacion)
                    .addComponent(btnRegresion))
                .addContainerGap(223, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void btnLocRaicesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLocRaicesActionPerformed
        comboBoxLocRaices.removeAllItems();
        comboBoxLocRaices.addItem("Metodos Cerrados");
        comboBoxLocRaices.addItem("Metodos Abiertos");

        // Crear un panel para contener los componentes
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Seleccione el tipo:"));
        panel.add(comboBoxLocRaices);
        panel.add(new JLabel("Seleccione el metodo:"));
        panel.add(comboBoxMetodosDisponibles);
        
        // Mostrar el cuadro de diálogo y capturar la respuesta del usuario
        int result = JOptionPane.showConfirmDialog(null, panel, "Ingrese el metodo a usar", JOptionPane.OK_CANCEL_OPTION);
        
        // Verificar si el usuario presionó "Aceptar" (OK)
        if (result == JOptionPane.OK_OPTION) {
            // Obtener el nombre del metodo seleccionado del JComboBox
            String nombreTipoMetodo = (String) comboBoxLocRaices.getSelectedItem();
            // Obtener el metodo seleccionado
            String metodoSelect = (String) comboBoxMetodosDisponibles.getSelectedItem();
            if ( nombreTipoMetodo.equals(comboBoxLocRaices.getItemAt(0)) ){
                if ( metodoSelect.equals(metodos[0]) ){
                    BiseccionFrame biseccion = new BiseccionFrame();
                    biseccion.setVisible(true);
                    biseccion.setLocationRelativeTo(null);
                }
                else if( metodoSelect.equals(metodos[1]) ){
                    RegulaFalsiFrame regulaFalsi = new RegulaFalsiFrame();
                    regulaFalsi.setVisible(true);
                    regulaFalsi.setLocationRelativeTo(null);
                }else
                    JOptionPane.showMessageDialog(this, "No hay metodo coincidente");
            }else if( nombreTipoMetodo.equals(comboBoxLocRaices.getItemAt(1)) ){
                if( metodoSelect.equals(metodos[2]) ){
                    PuntoFijoFrame puntoFijo = new PuntoFijoFrame();
                    puntoFijo.setVisible(true);
                    puntoFijo.setLocationRelativeTo(null);
                }else if( metodoSelect.equals(metodos[3]) ){
                    NewtonRapsonFrame newtonRapson = new NewtonRapsonFrame();
                    newtonRapson.setVisible(true);
                    newtonRapson.setLocationRelativeTo(null);
                }else if( metodoSelect.equals(metodos[4]) ){
                    SecanteFrame secante = new SecanteFrame();
                    secante.setVisible(true);
                    secante.setLocationRelativeTo(null);
                }
            }else
                JOptionPane.showMessageDialog(this, "Ninguna conincidencia entre el tipo de metodo " + nombreTipoMetodo + " y el metodo " + metodoSelect + " seleccionado");
        }
    }//GEN-LAST:event_btnLocRaicesActionPerformed

    private void btnSisEcuLinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSisEcuLinActionPerformed
        comboBoxSisEcuaLin.removeAllItems();
        comboBoxSisEcuaLin.addItem("Metodos Sistema de Ecuaciones Algebraicas Lineales");
        comboBoxSisEcuaLin.addItem("Metodos Iterativos para Sistemas de Ecuaciones Lineales");
        
        // Crear un panel para contener los componentes
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Seleccione el tipo:"));
        panel.add(comboBoxSisEcuaLin);
        panel.add(new JLabel("Seleccione el metodo:"));
        panel.add(comboBoxMetodosDisponibles);
        
        // Mostrar el cuadro de diálogo y capturar la respuesta del usuario
        int result = JOptionPane.showConfirmDialog(null, panel, "Ingrese el metodo a usar", JOptionPane.OK_CANCEL_OPTION);
        
        // Verificar si el usuario presionó "Aceptar" (OK)
        if (result == JOptionPane.OK_OPTION) {
            // Obtener el nombre del metodo seleccionado del JComboBox
            String nombreTipoMetodo = (String) comboBoxSisEcuaLin.getSelectedItem();
            // Obtener el metodo seleccionado
            String metodoSelect = (String) comboBoxMetodosDisponibles.getSelectedItem();
            if ( nombreTipoMetodo.equals(comboBoxSisEcuaLin.getItemAt(0)) ){
                if ( metodoSelect.equals(metodos[5]) ){
                    EliminacionGaussianaFrame eliminacionGaussianaFrame = new EliminacionGaussianaFrame();
                    eliminacionGaussianaFrame.setVisible(true);
                    eliminacionGaussianaFrame.setLocationRelativeTo(null);
                }
                else
                    JOptionPane.showMessageDialog(this, "No hay metodo coincidente");
            }else if ( nombreTipoMetodo.equals(comboBoxSisEcuaLin.getItemAt(1)) ){
                if ( metodoSelect.equals(metodos[6]) ){
                    // Jacobi
                    JacobiFrame JacobiFrame = new JacobiFrame();
                    JacobiFrame.setVisible(true);
                    JacobiFrame.setLocationRelativeTo(null);
                }
                else if( metodoSelect.equals(metodos[7]) ){
                    // Gauss-Seidel
                    GaussSeidelFrame gaussSeidelFrame = new GaussSeidelFrame();
                    gaussSeidelFrame.setVisible(true);
                    gaussSeidelFrame.setLocationRelativeTo(null);
                }else
                    JOptionPane.showMessageDialog(this, "No hay metodo coincidente");
            }else
                JOptionPane.showMessageDialog(this, "Ninguna conincidencia entre el tipo de metodo " + nombreTipoMetodo + " y el metodo " + metodoSelect + " seleccionado");
        }
    }//GEN-LAST:event_btnSisEcuLinActionPerformed

    private void btnInterpolacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInterpolacionActionPerformed
        comboBoxInterpolaciones.removeAllItems();
        comboBoxInterpolaciones.addItem("Metodos de Interpolacion");
        comboBoxInterpolaciones.addItem("Metodos de Interpolacion Segmentaria");
        
        // Crear un panel para contener los componentes
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Seleccione el tipo:"));
        panel.add(comboBoxInterpolaciones);
        panel.add(new JLabel("Seleccione el metodo:"));
        panel.add(comboBoxMetodosDisponibles);
        
        // Mostrar el cuadro de diálogo y capturar la respuesta del usuario
        int result = JOptionPane.showConfirmDialog(null, panel, "Ingrese el metodo a usar", JOptionPane.OK_CANCEL_OPTION);
        
        // Verificar si el usuario presionó "Aceptar" (OK)
        if (result == JOptionPane.OK_OPTION) {
            // Obtener el nombre del metodo seleccionado del JComboBox
            String nombreTipoMetodo = (String) comboBoxInterpolaciones.getSelectedItem();
            // Obtener el metodo seleccionado
            String metodoSelect = (String) comboBoxMetodosDisponibles.getSelectedItem();
            if ( nombreTipoMetodo.equals(comboBoxInterpolaciones.getItemAt(0)) ){
                //Interpolacion comun
                if ( metodoSelect.equals(metodos[8]) ){
                    //Lagrange
                    LagrangeFrame lagrangeFrame = new LagrangeFrame();
                    lagrangeFrame.setVisible(true);
                    lagrangeFrame.setLocationRelativeTo(null);
                }
                else if ( metodoSelect.equals(metodos[9]) ){
                    // Polinomial
                    PolinomialFrame polinomialFrame = new PolinomialFrame();
                    polinomialFrame.setVisible(true);
                    polinomialFrame.setLocationRelativeTo(null);
                }
                else
                    JOptionPane.showMessageDialog(this, "No hay metodo coincidente");
            }else if ( nombreTipoMetodo.equals(comboBoxInterpolaciones.getItemAt(1)) ){
                 if( metodoSelect.equals(metodos[10]) ){
                    // Spline --> Segmentaria
                    JOptionPane.showMessageDialog(this, "No hay desarrollado todavia");
                }else
                    JOptionPane.showMessageDialog(this, "No hay metodo coincidente");
            }else
                JOptionPane.showMessageDialog(this, "Ninguna conincidencia entre el tipo de metodo " + nombreTipoMetodo + " y el metodo " + metodoSelect + " seleccionado");
        }
    }//GEN-LAST:event_btnInterpolacionActionPerformed

    private void btnRegresionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresionActionPerformed
        comboBoxRegresiones.removeAllItems();
        comboBoxRegresiones.addItem("Metodos de Regresioncion");
        
        // Crear un panel para contener los componentes
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Seleccione el tipo:"));
        panel.add(comboBoxRegresiones);
        panel.add(new JLabel("Seleccione el metodo:"));
        panel.add(comboBoxMetodosDisponibles);
        
        // Mostrar el cuadro de diálogo y capturar la respuesta del usuario
        int result = JOptionPane.showConfirmDialog(null, panel, "Ingrese el metodo a usar", JOptionPane.OK_CANCEL_OPTION);
        
        // Verificar si el usuario presionó "Aceptar" (OK)
        if (result == JOptionPane.OK_OPTION) {
            // Obtener el nombre del metodo seleccionado del JComboBox
            String nombreTipoMetodo = (String) comboBoxRegresiones.getSelectedItem();
            // Obtener el metodo seleccionado
            String metodoSelect = (String) comboBoxMetodosDisponibles.getSelectedItem();
            if ( nombreTipoMetodo.equals(comboBoxRegresiones.getItemAt(0)) ){
                //Interpolacion comun
                if ( metodoSelect.equals(metodos[10]) ){
                    //Lineal
                    RegresionLinealFrame regresionLinealFrame = new RegresionLinealFrame();
                    regresionLinealFrame.setVisible(true);
                    regresionLinealFrame.setLocationRelativeTo(null);
                }
                else if ( metodoSelect.equals(metodos[11]) ){
                    // Polinomial
                    RegresionPolinomialFrame regresionPolinomialFrame = new RegresionPolinomialFrame();
                    regresionPolinomialFrame.setVisible(true);
                    regresionPolinomialFrame.setLocationRelativeTo(null);
                }
                else
                    JOptionPane.showMessageDialog(this, "No hay metodo coincidente");
            }else
                JOptionPane.showMessageDialog(this, "Ninguna conincidencia entre el tipo de metodo " + nombreTipoMetodo + " y el metodo " + metodoSelect + " seleccionado");
        }
    }//GEN-LAST:event_btnRegresionActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnInterpolacion;
    private javax.swing.JButton btnLocRaices;
    private javax.swing.JButton btnRegresion;
    private javax.swing.JButton btnSisEcuLin;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
