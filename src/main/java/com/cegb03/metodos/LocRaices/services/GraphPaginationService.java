package com.cegb03.metodos.LocRaices.services;

import com.cegb03.metodos.LocRaices.models.CalculationInput;
import com.cegb03.metodos.calculos.Graficador;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;

import javax.swing.*;
import java.awt.*;

public class GraphPaginationService {
    
    private final GraphingService graphingService;
    
    public GraphPaginationService() {
        this.graphingService = new GraphingService();
    }
    
    /**
     * Crea pestañas para alternar entre gráficos de métodos cerrados y abiertos
     */
    public void createTabbedGraphs(JPanel container, CalculationInput input) {
        container.removeAll();
        container.setLayout(new BorderLayout());
        
        boolean hasClosedMethods = input.hasClosedMethodsSelected();
        boolean hasOpenMethods = input.hasOpenMethodsSelected();
        
        if (hasClosedMethods && hasOpenMethods) {
            // Crear pestañas cuando ambos tipos están seleccionados
            JTabbedPane tabbedPane = new JTabbedPane();
            
            // Pestaña para métodos cerrados
            JPanel closedMethodsPanel = new JPanel(new BorderLayout());
            graphingService.graficarMetodosCerrados(closedMethodsPanel, 
                input.getFunX(), input.getA(), input.getB());
            tabbedPane.addTab("Métodos Cerrados", closedMethodsPanel);
            
            // Pestaña para métodos abiertos
            JPanel openMethodsPanel = new JPanel(new BorderLayout());
            graphingService.graficarFuncionesInteligente(openMethodsPanel, input);
            tabbedPane.addTab("Métodos Abiertos", openMethodsPanel);
            
            container.add(tabbedPane, BorderLayout.CENTER);
            
        } else if (hasClosedMethods) {
            // Solo métodos cerrados
            graphingService.graficarMetodosCerrados(container, 
                input.getFunX(), input.getA(), input.getB());
                
        } else if (hasOpenMethods) {
            // Solo métodos abiertos
            graphingService.graficarFuncionesInteligente(container, input);
        }
        
        container.revalidate();
        container.repaint();
    }
    
    /**
     * Verifica si se necesitan pestañas (ambos tipos de métodos seleccionados)
     */
    public boolean needsTabs(CalculationInput input) {
        return input.hasClosedMethodsSelected() && input.hasOpenMethodsSelected();
    }
}