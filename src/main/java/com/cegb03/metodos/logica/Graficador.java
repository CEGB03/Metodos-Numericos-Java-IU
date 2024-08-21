package com.cegb03.metodos.logica;

import javax.swing.JOptionPane;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author cegb03
 */
public class Graficador {
    
    public static ChartPanel createChartPanelSimple(String funcion) {
        // Crear una serie de datos para la función
        XYSeries series = new XYSeries("f(x)");

        // Rango de valores para x
        for (double x = -10; x <= 10; x += 0.1) {
            double y = evaluarFuncion(funcion, x);
            series.add(x, y);
        }

        XYSeriesCollection dataset = new XYSeriesCollection(series);
        JFreeChart chart = ChartFactory.createXYLineChart(
            "Gráfico de f(x)", 
            "X", 
            "f(x)", 
            dataset, 
            PlotOrientation.VERTICAL, 
            true, 
            true, 
            false
        );

        return new ChartPanel(chart);
    }
    
    private static double evaluarFuncion(String funcion, double x)  {
        try {
            Expression expression = new ExpressionBuilder(funcion)
                    .variable("x")
                    .build()
                    .setVariable("x", x);

            return expression.evaluate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al evaluar la función: " + e.getMessage());
            return Double.NaN;
        }
    }
     public static ChartPanel createChartPanelDoble(String funcion1, String funcion2, double a, double b, double paso) {
        // Crear una serie para cada función
        XYSeries serie1 = new XYSeries("Función 1");
        XYSeries serie2 = new XYSeries("Función 2");

        // Rellenar las series con datos
        for (double x = a; x <= b; x += paso) {
            double y1 = eval(funcion1, x);
            double y2 = eval(funcion2, x);
            serie1.add(x, y1);
            serie2.add(x, y2);
        }

        // Crear un conjunto de datos y agregar las series
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(serie1);
        dataset.addSeries(serie2);

        // Crear el gráfico
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Funciones",     // Título del gráfico
                "X",             // Etiqueta del eje X
                "Y",             // Etiqueta del eje Y
                dataset,         // Datos
                PlotOrientation.VERTICAL, // Orientación
                true,            // Legenda
                true,            // Tooltips
                false            // URLs
        );

        // Crear un panel para el gráfico
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(600, 400));
        return chartPanel;
    }

    private static double eval(String funcion, double x) {
        try {
            Expression expression = new ExpressionBuilder(funcion)
                    .variable("x")
                    .build()
                    .setVariable("x", x);

            return expression.evaluate();
        } catch (Exception e) {
            e.printStackTrace();
            return Double.NaN;
        }
    }
}
