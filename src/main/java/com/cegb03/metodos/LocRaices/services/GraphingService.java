package com.cegb03.metodos.LocRaices.services;

import com.cegb03.metodos.LocRaices.models.CalculationInput;
import com.cegb03.metodos.calculos.Graficador;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;

public class GraphingService {

    // ===============================
    // CONFIGURACIÓN DE ESTILOS ESTÁTICOS
    // ===============================

    public static class EstiloGrafico {
        public static final Color FONDO_GRAFICO = Color.DARK_GRAY;
        public static final Color COLOR_EJES = Color.WHITE;
        public static final Color COLOR_CUADRICULA = Color.LIGHT_GRAY;
        public static final Color COLOR_BASELINE = Color.YELLOW;

        // Permitir cambio dinámico
        private static Color fondoActual = FONDO_GRAFICO;
        private static Color ejesActual = COLOR_EJES;

        public static void setFondoGrafico(Color fondo) {
            fondoActual = fondo;
        }

        public static void setColorEjes(Color color) {
            ejesActual = color;
        }

        public static Color getFondoActual() {
            return fondoActual;
        }

        public static Color getEjesActual() {
            return ejesActual;
        }
    }

    // ===============================
    // MÉTODOS ESTÁTICOS PARA CAMBIO DE ESTILO
    // ===============================

    public static void cambiarEstiloGrafico(Color fondo, Color ejes) {
        EstiloGrafico.setFondoGrafico(fondo);
        EstiloGrafico.setColorEjes(ejes);
    }

    public static void aplicarEstiloOscuro() {
        cambiarEstiloGrafico(Color.DARK_GRAY, Color.WHITE);
    }

    public static void aplicarEstiloClaro() {
        cambiarEstiloGrafico(Color.WHITE, Color.BLACK);
    }

    public static void aplicarEstiloAzul() {
        cambiarEstiloGrafico(new Color(25, 25, 50), Color.CYAN);
    }

    // ===============================
    // MÉTODOS DE GRAFICACIÓN
    // ===============================

    public void graficarFuncionesInteligente(JPanel panel, CalculationInput input) {
        panel.removeAll();
        panel.setLayout(new BorderLayout());

        boolean puntoFijo = input.isPuntoFijoSelected();
        boolean newtonRapson = input.isNewtonRapsonSelected();
        boolean secante = input.isSecanteSelected();

        String funF = input.getFunX();
        String funY = input.getFunY();
        String funGPF = input.getFunGPF();
        Double puntoInteres = input.getXn();

        System.out.println("=== GRAFICACIÓN INTELIGENTE MÉTODOS ABIERTOS ===");
        System.out.println("Funciones: F=" + funF + ", Y=" + funY + ", GPF=" + funGPF);

        if (puntoFijo && (newtonRapson || secante)) {
            // Graficar 3 funciones
            if (isValid(funF) && isValid(funY) && isValid(funGPF)) {
                ChartPanel chartPanel = Graficador.createChartPanelTriple(funF, funY, funGPF, puntoInteres);
                configurarEstiloUnificado(chartPanel, "Gráfico - Métodos Abiertos");
                panel.add(chartPanel, BorderLayout.CENTER);
                System.out.println("✓ 3 funciones graficadas");
            }
        } else if (puntoFijo) {
            // Solo Punto Fijo
            if (isValid(funF) && isValid(funGPF)) {
                ChartPanel chartPanel = Graficador.createChartPanelDoble(funF, funGPF, puntoInteres);
                configurarEstiloUnificado(chartPanel, "Gráfico - Métodos Abiertos");
                panel.add(chartPanel, BorderLayout.CENTER);
                System.out.println("✓ F(x) y g(x) graficadas");
            }
        } else if (newtonRapson || secante) {
            // Solo Newton-Raphson/Secante
            if (isValid(funF) && isValid(funY)) {
                ChartPanel chartPanel = Graficador.createChartPanelDoble(funF, funY, puntoInteres);
                configurarEstiloUnificado(chartPanel, "Gráfico - Métodos Abiertos");
                panel.add(chartPanel, BorderLayout.CENTER);
                System.out.println("✓ F(x) y f'(x) graficadas");
            }
        }

        panel.revalidate();
        panel.repaint();
    }

    public void graficarMetodosCerrados(JPanel panel, String funcion, Double a, Double b) {
        panel.removeAll();
        panel.setLayout(new BorderLayout());

        System.out.println("=== GRAFICACIÓN MÉTODOS CERRADOS ===");
        System.out.println("Función: " + funcion + ", Intervalo: [" + a + ", " + b + "]");

        Double puntoMedio = (a != null && b != null) ? (a + b) / 2 : null;
        ChartPanel chartPanel = Graficador.createChartPanelSimpleConRango(funcion, puntoMedio);

        JFreeChart chart = chartPanel.getChart();
        XYPlot plot = (XYPlot) chart.getPlot();
        NumberAxis domainAxis = (NumberAxis) plot.getDomainAxis();

        // Para métodos cerrados, forzar el rango al intervalo [a, b]
        if (a != null && b != null) {
            domainAxis.setRange(a, b);
        }

        // Usar el mismo estilo unificado
        configurarEstiloUnificado(chartPanel, "Gráfico - Métodos Cerrados");

        panel.add(chartPanel, BorderLayout.CENTER);
        panel.revalidate();
        panel.repaint();

        System.out.println("✓ Gráfico de métodos cerrados creado");
    }

    /**
     * Configuración de estilo unificado para todos los gráficos
     */
    private void configurarEstiloUnificado(ChartPanel chartPanel, String titulo) {
        JFreeChart chart = chartPanel.getChart();
        XYPlot plot = (XYPlot) chart.getPlot();

        // FONDO UNIFICADO
        plot.setBackgroundPaint(EstiloGrafico.getFondoActual());

        // EJES Y LÍNEAS BASE
        plot.setDomainZeroBaselineVisible(true);
        plot.setRangeZeroBaselineVisible(true);
        plot.setDomainZeroBaselinePaint(EstiloGrafico.COLOR_BASELINE);
        plot.setRangeZeroBaselinePaint(EstiloGrafico.COLOR_BASELINE);

        // CONFIGURACIÓN DE EJES
        NumberAxis domainAxis = (NumberAxis) plot.getDomainAxis();
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();

        // Colores de ejes unificados
        domainAxis.setAxisLinePaint(EstiloGrafico.getEjesActual());
        domainAxis.setTickMarkPaint(EstiloGrafico.getEjesActual());
        domainAxis.setTickLabelPaint(EstiloGrafico.getEjesActual());

        rangeAxis.setAxisLinePaint(EstiloGrafico.getEjesActual());
        rangeAxis.setTickMarkPaint(EstiloGrafico.getEjesActual());
        rangeAxis.setTickLabelPaint(EstiloGrafico.getEjesActual());

        // CUADRÍCULA UNIFICADA
        plot.setDomainGridlinesVisible(true);
        plot.setRangeGridlinesVisible(true);
        plot.setDomainGridlinePaint(EstiloGrafico.COLOR_CUADRICULA);
        plot.setRangeGridlinePaint(EstiloGrafico.COLOR_CUADRICULA);

        // TÍTULO
        chart.setTitle(titulo);
        chart.getTitle().setPaint(EstiloGrafico.getEjesActual());
    }

    private void configurarEstiloGraficoAbierto(ChartPanel chartPanel) {
        JFreeChart chart = chartPanel.getChart();
        XYPlot plot = (XYPlot) chart.getPlot();

        plot.setDomainZeroBaselineVisible(true);
        plot.setRangeZeroBaselineVisible(true);
        plot.setBackgroundPaint(EstiloGrafico.getFondoActual());

        NumberAxis domainAxis = (NumberAxis) plot.getDomainAxis();
        domainAxis.setAxisLinePaint(EstiloGrafico.getEjesActual());
        domainAxis.setTickMarkPaint(EstiloGrafico.getEjesActual());
        domainAxis.setTickLabelPaint(EstiloGrafico.getEjesActual());
        plot.setRangeGridlinePaint(EstiloGrafico.COLOR_CUADRICULA);

        // Título distintivo para métodos abiertos
        chart.setTitle("Gráfico - Métodos Abiertos");
    }

    private void configurarEstiloGraficoCerrado(ChartPanel chartPanel) {
        JFreeChart chart = chartPanel.getChart();
        XYPlot plot = (XYPlot) chart.getPlot();

        plot.setDomainZeroBaselineVisible(true);
        plot.setRangeZeroBaselineVisible(true);
        plot.setBackgroundPaint(EstiloGrafico.getFondoActual());

        NumberAxis domainAxis = (NumberAxis) plot.getDomainAxis();
        domainAxis.setAxisLinePaint(EstiloGrafico.getEjesActual());
        domainAxis.setTickMarkPaint(EstiloGrafico.getEjesActual());
        domainAxis.setTickLabelPaint(EstiloGrafico.getEjesActual());
        plot.setRangeGridlinePaint(EstiloGrafico.COLOR_CUADRICULA);

        // Título distintivo para métodos cerrados
        chart.setTitle("Gráfico - Métodos Cerrados");
    }

    private boolean isValid(String function) {
        return function != null && !function.trim().isEmpty();
    }
}