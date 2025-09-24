package com.cegb03.metodos;

import javax.swing.UIManager;

/**
 *
 * @author cegb03
 */
public class MetodosNumericos {

    public static void main(String[] args) {
        // FORZAR TEMA CLARO - No usar el Look and Feel del sistema para evitar tema oscuro
        try {
            // Opción 1: Metal Look and Feel (clásico claro)
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
            System.out.println("Aplicando tema Metal (claro)");
        } catch (Exception e) {
            try {
                // Opción 2: Nimbus Look and Feel (moderno claro)
                UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
                System.out.println("Aplicando tema Nimbus (claro)");
            } catch (Exception ex) {
                try {
                    // Opción 3: CrossPlatform Look and Feel (por defecto de Java - claro)
                    UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
                    System.out.println("Aplicando tema CrossPlatform (claro)");
                } catch (Exception exc) {
                    System.out.println("Usando tema por defecto");
                }
            }
        }
        
        // Configuraciones adicionales para asegurar tema claro
        UIManager.put("control", new java.awt.Color(240, 240, 240));
        UIManager.put("info", new java.awt.Color(255, 255, 225));
        UIManager.put("nimbusBase", new java.awt.Color(240, 240, 240));
        UIManager.put("nimbusAlertYellow", new java.awt.Color(255, 255, 0));
        UIManager.put("nimbusDisabledText", new java.awt.Color(128, 128, 128));
        UIManager.put("nimbusFocus", new java.awt.Color(115, 164, 209));
        UIManager.put("nimbusGreen", new java.awt.Color(176, 179, 50));
        UIManager.put("nimbusInfoBlue", new java.awt.Color(66, 139, 221));
        UIManager.put("nimbusLightBackground", new java.awt.Color(255, 255, 255));
        UIManager.put("nimbusOrange", new java.awt.Color(191, 98, 4));
        UIManager.put("nimbusRed", new java.awt.Color(169, 46, 34));
        UIManager.put("nimbusSelectedText", new java.awt.Color(255, 255, 255));
        UIManager.put("nimbusSelectionBackground", new java.awt.Color(104, 93, 156));
        UIManager.put("text", new java.awt.Color(0, 0, 0));
        
        // Crear e inicializar la ventana principal
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Inicio inicio = new Inicio();
                inicio.setVisible(true);
                inicio.setLocationRelativeTo(null);
            }
        });
    }
}
