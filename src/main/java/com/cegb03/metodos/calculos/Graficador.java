package com.cegb03.metodos.calculos;

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
    
    // MÉTODOS CON RANGO AUTOMÁTICO (NUEVOS)
    
    public static ChartPanel createChartPanelSimple(String funcion) {
        return createChartPanelSimpleConRango(funcion, null);
    }

    public static ChartPanel createChartPanelSimpleConRango(String funcion, Double puntoInteres) {
        // Calcular rango automático
        RangoAutomatico.RangoResultado rango = RangoAutomatico.calcularRangoOptimo(funcion, puntoInteres);
        
        System.out.println("Graficando función simple: " + funcion);
        System.out.println("Rango automático: " + rango);
        
        // Crear una serie de datos para la función
        XYSeries series = new XYSeries("f(x)");

        // Usar el rango calculado automáticamente
        for (double x = rango.min; x <= rango.max; x += rango.paso) {
            double y = evaluarFuncion(funcion, x);
            if (!Double.isNaN(y) && !Double.isInfinite(y)) {
                series.add(x, y);
            }
        }

        XYSeriesCollection dataset = new XYSeriesCollection(series);
        JFreeChart chart = ChartFactory.createXYLineChart(
            "Gráfico de " + funcion, 
            "X", 
            "f(x)", 
            dataset, 
            PlotOrientation.VERTICAL, 
            true, 
            true, 
            false
        );

        // Configurar ejes y líneas base para mostrar los ejes 0
        org.jfree.chart.plot.XYPlot plot = chart.getXYPlot();
        
        // Mostrar las líneas base en 0
        plot.setDomainZeroBaselineVisible(true);  // Línea vertical en X=0
        plot.setRangeZeroBaselineVisible(true);   // Línea horizontal en Y=0
        
        // Configurar colores y grosor de las líneas de base
        plot.setDomainZeroBaselinePaint(java.awt.Color.BLACK);
        plot.setRangeZeroBaselinePaint(java.awt.Color.BLACK);
        plot.setDomainZeroBaselineStroke(new java.awt.BasicStroke(1.5f));
        plot.setRangeZeroBaselineStroke(new java.awt.BasicStroke(1.5f));
        
        // Mostrar líneas de cuadrícula
        plot.setDomainGridlinesVisible(true);
        plot.setRangeGridlinesVisible(true);

        return new ChartPanel(chart);
    }
    
    public static ChartPanel createChartPanelDoble(String funcion1, String funcion2, Double puntoInteres) {
        // Calcular rango automático para múltiples funciones
        String[] funciones = {funcion1, funcion2};
        RangoAutomatico.RangoResultado rango = RangoAutomatico.calcularRangoMultiple(funciones, puntoInteres);
        
        System.out.println("Graficando funciones dobles: " + funcion1 + ", " + funcion2);
        System.out.println("Rango automático: " + rango);
        
        return createChartPanelDoble(funcion1, funcion2, rango.min, rango.max, rango.paso);
    }

    public static ChartPanel createChartPanelTriple(String funcion1, String funcion2, String funcion3, Double puntoInteres) {
        // Calcular rango automático para múltiples funciones
        String[] funciones = {funcion1, funcion2, funcion3};
        RangoAutomatico.RangoResultado rango = RangoAutomatico.calcularRangoMultiple(funciones, puntoInteres);
        
        System.out.println("Graficando funciones triples: " + funcion1 + ", " + funcion2 + ", " + funcion3);
        System.out.println("Rango automático: " + rango);
        
        return createChartPanelTriple(funcion1, funcion2, funcion3, rango.min, rango.max, rango.paso);
    }
    
    // MÉTODOS LEGACY CON RANGO MANUAL (MANTENER COMPATIBILIDAD)
    
    public static ChartPanel createChartPanelDoble(String funcion1, String funcion2, double min, double max, double paso) {
        XYSeries series1 = new XYSeries("f(x) = " + funcion1);
        XYSeries series2 = new XYSeries("g(x) = " + funcion2);

        for (double x = min; x <= max; x += paso) {
            double y1 = evaluarFuncion(funcion1, x);
            double y2 = evaluarFuncion(funcion2, x);
            
            if (!Double.isNaN(y1) && !Double.isInfinite(y1)) {
                series1.add(x, y1);
            }
            if (!Double.isNaN(y2) && !Double.isInfinite(y2)) {
                series2.add(x, y2);
            }
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series1);
        dataset.addSeries(series2);
        
        JFreeChart chart = ChartFactory.createXYLineChart(
            "Gráfico de funciones", 
            "X", 
            "Y", 
            dataset, 
            PlotOrientation.VERTICAL, 
            true, 
            true, 
            false
        );

        return new ChartPanel(chart);
    }

    public static ChartPanel createChartPanelTriple(String funcion1, String funcion2, String funcion3, double min, double max, double paso) {
        XYSeries series1 = new XYSeries("f(x) = " + funcion1);
        XYSeries series2 = new XYSeries("g(x) = " + funcion2);
        XYSeries series3 = new XYSeries("h(x) = " + funcion3);

        for (double x = min; x <= max; x += paso) {
            double y1 = evaluarFuncion(funcion1, x);
            double y2 = evaluarFuncion(funcion2, x);
            double y3 = evaluarFuncion(funcion3, x);
            
            if (!Double.isNaN(y1) && !Double.isInfinite(y1)) {
                series1.add(x, y1);
            }
            if (!Double.isNaN(y2) && !Double.isInfinite(y2)) {
                series2.add(x, y2);
            }
            if (!Double.isNaN(y3) && !Double.isInfinite(y3)) {
                series3.add(x, y3);
            }
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series1);
        dataset.addSeries(series2);
        dataset.addSeries(series3);
        
        JFreeChart chart = ChartFactory.createXYLineChart(
            "Gráfico de funciones", 
            "X", 
            "Y", 
            dataset, 
            PlotOrientation.VERTICAL, 
            true, 
            true, 
            false
        );

        return new ChartPanel(chart);
    }
    
    // NUEVO MÉTODO PARA REFRESCAR EL CHARTPANEL SIN RECREARLO
    public static void refreshChartPanel(ChartPanel panel,
                                     String[] funciones,
                                     Double puntoInteres) {
    
    XYSeriesCollection newData = new XYSeriesCollection();

    // Calcular rango automático para múltiples funciones
    RangoAutomatico.RangoResultado rango =
            RangoAutomatico.calcularRangoMultiple(funciones, puntoInteres);

    System.out.println("=== REFRESH CHART PANEL ===");
    System.out.println("Funciones a graficar: " + java.util.Arrays.toString(funciones));
    System.out.println("Punto de interés: " + puntoInteres);
    System.out.println("Rango calculado: " + rango);

    // Crear series para cada función con nombres distintivos
    for (int i = 0; i < funciones.length; i++) {
        String nombreSerie;
        switch (i) {
            case 0:
                nombreSerie = "f(x) = " + funciones[i];
                break;
            case 1:
                nombreSerie = "f'(x) = " + funciones[i];
                break;
            case 2:
                nombreSerie = "g(x) = " + funciones[i];
                break;
            default:
                nombreSerie = "h" + i + "(x) = " + funciones[i];
                break;
        }
        
        XYSeries serie = new XYSeries(nombreSerie);
        
        // Evaluar función en el rango calculado
        for (double x = rango.min; x <= rango.max; x += rango.paso) {
            double y = evaluarFuncion(funciones[i], x);
            if (!Double.isNaN(y) && !Double.isInfinite(y)) {
                serie.add(x, y);
            }
        }
        newData.addSeries(serie);
    }

    // Obtener el chart existente y actualizar solo el dataset
    JFreeChart chart = panel.getChart();
    if (chart != null && chart.getXYPlot() != null) {
        // Actualizar el dataset
        chart.getXYPlot().setDataset(newData);
        
        // Restaurar configuraciones visuales de los ejes
        org.jfree.chart.plot.XYPlot plot = chart.getXYPlot();
        
        // Mostrar las líneas de la cuadrícula (grid lines) incluyendo los ejes 0
        plot.setDomainGridlinesVisible(true);
        plot.setRangeGridlinesVisible(true);
        plot.setDomainZeroBaselineVisible(true);  // Línea vertical en X=0
        plot.setRangeZeroBaselineVisible(true);   // Línea horizontal en Y=0
        
        // Configurar colores de las líneas de base (ejes 0)
        plot.setDomainZeroBaselinePaint(java.awt.Color.BLACK);
        plot.setRangeZeroBaselinePaint(java.awt.Color.BLACK);
        plot.setDomainZeroBaselineStroke(new java.awt.BasicStroke(1.5f));
        plot.setRangeZeroBaselineStroke(new java.awt.BasicStroke(1.5f));
        
        // Actualizar título del gráfico
        String titulo = "Gráfico de ";
        if (funciones.length == 1) {
            titulo += "f(x)";
        } else if (funciones.length == 2) {
            titulo += "f(x) y f'(x)";
        } else if (funciones.length == 3) {
            titulo += "f(x), f'(x) y g(x)";
        } else {
            titulo += funciones.length + " funciones";
        }
        chart.setTitle(titulo);
        
        System.out.println("Chart actualizado exitosamente con " + funciones.length + " funciones");
        System.out.println("Configuraciones de ejes restauradas");
    } else {
        System.err.println("Error: Chart o XYPlot es null en refreshChartPanel");
    }
    }
    
    // MÉTODO AUXILIAR PRIVADO
    
    public static double evaluarFuncion(String funcion, double x) {
        try {
            Expression expression = new ExpressionBuilder(funcion)
                    .variable("x")
                    .build()
                    .setVariable("x", x);

            return expression.evaluate();
        } catch (Exception e) {
            return Double.NaN;
        }
    }
}
