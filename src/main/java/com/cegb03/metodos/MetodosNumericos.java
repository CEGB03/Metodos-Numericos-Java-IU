package com.cegb03.metodos;

import javax.swing.UIManager;

/**
 *
 * @author cegb03
 */
public class MetodosNumericos {

    public static void main(String[] args) {
        // Usar el Look and Feel del sistema para preservar los colores personalizados
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // Si falla, usa el Look and Feel por defecto
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (Exception ex) {
                // Ignorar - usar el por defecto
            }
        }
        
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
