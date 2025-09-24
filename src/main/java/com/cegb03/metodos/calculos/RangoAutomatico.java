package com.cegb03.metodos.calculos;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

/**
 * Clase para calcular rangos automáticos de graficación según la función
 * @author cegb03
 */
public class RangoAutomatico {
    
    public static class RangoResultado {
        public final double min;
        public final double max;
        public final double paso;
        
        public RangoResultado(double min, double max, double paso) {
            this.min = min;
            this.max = max;
            this.paso = paso;
        }
        
        @Override
        public String toString() {
            return String.format("[%.2f, %.2f] con paso %.3f", min, max, paso);
        }
    }
    
    /**
     * Calcula el rango óptimo para graficar una función
     */
    public static RangoResultado calcularRangoOptimo(String funcion, Double puntoInteres) {
        double centro = (puntoInteres != null) ? puntoInteres : 0.0;
        double rango = Math.max(Math.abs(centro) * 2, 10.0);
        
        double min = centro - rango;
        double max = centro + rango;
        double paso = (max - min) / 200.0; // 200 puntos de graficación
        
        return new RangoResultado(min, max, paso);
    }
    
    public static RangoResultado calcularRangoMultiple(String[] funciones, Double puntoInteres) {
        double centro = (puntoInteres != null) ? puntoInteres : 0.0;
        double rango = Math.max(Math.abs(centro) * 2, 10.0);
        
        // Para múltiples funciones, usar un rango ligeramente mayor
        rango *= 1.5;
        
        double min = centro - rango;
        double max = centro + rango;
        double paso = (max - min) / 300.0; // 300 puntos para mejor resolución
        
        return new RangoResultado(min, max, paso);
    }
    
    private static double evaluarFuncion(String funcion, double x) {
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