package com.cegb03.metodos.LocRaices.Codes;

import com.cegb03.metodos.calculos.Derivar;
import com.cegb03.metodos.calculos.GeneradorPuntoFijo;
import java.text.DecimalFormat;
import javax.swing.JOptionPane;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

/**
 * Implementación del método de Punto Fijo según la teoría matemática.
 * 
 * TEORÍA:
 * - Para resolver f(x) = 0, se transforma a x = g(x)
 * - Se itera: x_{n+1} = g(x_n)
 * - CRITERIO DE CONVERGENCIA: |g'(x)| < 1 en el intervalo de interés
 * - El usuario debe proporcionar g(x) manualmente, NO se calcula automáticamente
 * 
 * @author cegb03
 */
public class PuntoFijoCode {
    
    private final String funX;      // f(x) - función original 
    private final String funY;      // g(x) - función de iteración (DEBE ser ingresada por el usuario)
    private final Double tolerancia;
    private Double a;               // punto de iteración actual
    private Double b;               // g(a) - resultado de evaluar g en el punto actual
    private Double error;
    private int cont = 0;
    private final Derivar derivacion = new Derivar();

    public PuntoFijoCode(double a, double tol, String funX, String funY) {
        this.a = a;
        this.tolerancia = tol;
        this.funX = funX;
        this.funY = funY;
        this.error = 0.0;
    }

    public String calcularRaiz() {
        // VALIDACIÓN 1: g(x) debe ser proporcionada por el usuario
        if (funY == null || funY.isBlank()) {
            return "ERROR CRÍTICO: Método de Punto Fijo requiere g(x)\n" +
                   "=================================================\n" +
                   "Para resolver f(x) = 0 con Punto Fijo, debe transformar a x = g(x)\n" +
                   "y proporcionar g(x) manualmente.\n\n" +
                   "EJEMPLOS DE TRANSFORMACIÓN:\n" +
                   "• Si f(x) = x² - 2x - 3, puede usar g(x) = (x² - 3)/2\n" +
                   "• Si f(x) = x - cos(x), puede usar g(x) = cos(x)\n" +
                   "• Si f(x) = 10x² - 2x - 2, puede usar g(x) = sqrt((2x + 2)/10)\n\n" +
                   "Ingrese g(x) en el campo correspondiente.";
        }

        // VALIDACIÓN 2: Verificar que g(x) se puede evaluar en el punto inicial
        double g_inicial = evaluarFuncion(a, funY);
        if (Double.isNaN(g_inicial) || Double.isInfinite(g_inicial)) {
            return "ERROR: No se puede evaluar g(x) = " + funY + " en x = " + a + "\n" +
                   "Verifique que g(x) esté correctamente definida.";
        }

        // VALIDACIÓN 3: Verificar criterio de convergencia |g'(x)| < 1
        String derivadaG = derivacion.derivar(funY);
        double gPrima = evaluarFuncion(a, derivadaG);
        
        if (Double.isNaN(gPrima)) {
            return "ERROR: No se puede evaluar g'(x) = " + derivadaG + " en x = " + a + "\n" +
                   "Verifique la expresión de g(x).";
        }

        if (Math.abs(gPrima) >= 1) {
            return "ADVERTENCIA: El método NO CONVERGE\n" +
                   "====================================\n" +
                   "Criterio de convergencia no se cumple:\n" +
                   "g'(x) = " + derivadaG + "\n" +
                   "|g'(" + a + ")| = " + Math.abs(gPrima) + " ≥ 1\n\n" +
                   "PARA QUE CONVERJA se requiere |g'(x)| < 1\n\n" +
                   "SUGERENCIAS:\n" +
                   "• Reformule g(x) despejando x de manera diferente\n" +
                   "• Si f(x) = 10x² - 2x - 2, pruebe:\n" +
                   "  - g(x) = sqrt((2*x + 2)/10)\n" +
                   "  - g(x) = (10*x² - 2)/2\n" +
                   "  - g(x) = x - 0.1*(10*x² - 2*x - 2)";
        }
        
        // ALGORITMO DE PUNTO FIJO
        System.out.println("=== INICIO MÉTODO PUNTO FIJO ===");
        System.out.println("f(x) = " + funX);
        System.out.println("g(x) = " + funY);
        System.out.println("x0 = " + a);
        System.out.println("Criterio: |g'(x)| = " + Math.abs(gPrima) + " < 1 ✓");
        
        do {
            cont++;
            
            // PASO PRINCIPAL: x_{n+1} = g(x_n)
            b = evaluarFuncion(a, funY);
            
            // Verificar valores problemáticos
            if (Double.isInfinite(b) || Double.isNaN(b)) {
                return "ERROR DE CONVERGENCIA\n" +
                       "Iteración " + cont + ": g(" + a + ") = " + b + "\n" +
                       "La función g(x) produce valores indefinidos.\n" +
                       "Reformule g(x) o cambie el punto inicial.";
            }
            
            // Calcular error: |x_{n+1} - x_n|
            error = Math.abs(b - a);
            
            System.out.printf("Iteración %d: x = %.10f, g(x) = %.10f, error = %.2e%n", 
                             cont, a, b, error);
            
            // Verificar divergencia temprana
            if (cont > 10 && error > 100) {
                return "DIVERGENCIA DETECTADA\n" +
                       "El método diverge rápidamente.\n" +
                       "Iteración " + cont + ": error = " + error + "\n" +
                       "Reformule g(x) o cambie el punto inicial.";
            }
            
            // Actualizar para la siguiente iteración
            a = b;
            
        } while (error > tolerancia && cont < 15000);
        
        // VERIFICACIÓN FINAL: Comprobar que es realmente una raíz de f(x) = 0
        double verificacion_f = evaluarFuncion(b, funX);
        
        DecimalFormat df = new DecimalFormat("0.00000000000000000000");
        String formattedError = df.format(error);
        String formattedRoot = df.format(b);
        String formattedVerif = df.format(Math.abs(verificacion_f));
        
        System.out.println("=== RESULTADO FINAL ===");
        System.out.println("Raíz encontrada: x = " + formattedRoot);
        System.out.println("Verificación f(x) = " + formattedVerif);
        System.out.println("Error estimado: " + formattedError);
        System.out.println("Iteraciones: " + cont);
        
        String resultado = "Fun = " + funX + "\n" +
                          "FunG = " + funY + "\n" +
                          "Raiz = " + formattedRoot + "\n" +
                          "error estimado = " + formattedError + "\n" +
                          "Cantidad de iteraciones = " + cont;
        
        // Advertencia si la verificación no es buena
        if (Math.abs(verificacion_f) > tolerancia * 10) {
            resultado += "\n⚠️  ADVERTENCIA: f(raiz) = " + formattedVerif + 
                        " (debería ser ≈ 0)\nPosible error numérico o g(x) incorrecta.";
        }
        
        return resultado;
    }
    
    // Método adicional para usar generación automática
    public String calcularRaizAutomatico() {
        // Si no se proporcionó g(x), generar automáticamente
        if (funY == null || funY.isBlank()) {
            System.out.println("=== GENERANDO g(x) AUTOMÁTICAMENTE ===");
            
            GeneradorPuntoFijo.ResultadoGeneracion resultado = 
                GeneradorPuntoFijo.generarFuncionConvergente(funX, a);
            
            System.out.println(resultado.toString());
            
            if (!resultado.converge()) {
                return "ERROR: No se pudo generar una función g(x) convergente automáticamente.\n" +
                       resultado.toString() + "\n" +
                       "Intente proporcionar g(x) manualmente o cambie el punto inicial.";
            }
            
            // Usar la función generada automáticamente
            return calcularConFuncionGenerada(resultado);
        }
        
        // Si se proporcionó g(x), usar el método original
        return calcularRaiz();
    }

    private String calcularConFuncionGenerada(GeneradorPuntoFijo.ResultadoGeneracion resultado) {
        String funcionGenerada = resultado.getFuncionG();
        
        System.out.println("=== EJECUTANDO PUNTO FIJO CON g(x) GENERADA ===");
        System.out.println("f(x) = " + funX);
        System.out.println("g(x) = " + funcionGenerada);
        System.out.println("λ = " + resultado.getLambda());
        System.out.println("x0 = " + a);
        
        do {
            cont++;
            
            // PASO PRINCIPAL: x_{n+1} = g(x_n)
            b = evaluarFuncion(a, funcionGenerada);
            
            if (Double.isInfinite(b) || Double.isNaN(b)) {
                return "ERROR DE CONVERGENCIA\n" +
                       "Iteración " + cont + ": g(" + a + ") = " + b + "\n" +
                       "La función generada produce valores indefinidos.";
            }
            
            error = Math.abs(b - a);
            
            System.out.printf("Iteración %d: x = %.10f, g(x) = %.10f, error = %.2e%n", 
                             cont, a, b, error);
            
            if (cont > 10 && error > 100) {
                return "DIVERGENCIA DETECTADA\n" +
                       "El método diverge rápidamente a pesar de la generación automática.\n" +
                       "Iteración " + cont + ": error = " + error;
            }
            
            a = b;
            
        } while (error > tolerancia && cont < 15000);
        
        // Verificación final
        double verificacion_f = evaluarFuncion(b, funX);
        
        DecimalFormat df = new DecimalFormat("0.00000000000000000000");
        String formattedError = df.format(error);
        String formattedRoot = df.format(b);
        String formattedVerif = df.format(Math.abs(verificacion_f));
        
        String resultadoFinal = "=== PUNTO FIJO CON GENERACIÓN AUTOMÁTICA ===\n" +
                               resultado.getMensaje() + "\n\n" +
                               "Fun = " + funX + "\n" +
                               "FunG = " + funcionGenerada + "\n" +
                               "Factor λ = " + resultado.getLambda() + "\n" +
                               "Raiz = " + formattedRoot + "\n" +
                               "error estimado = " + formattedError + "\n" +
                               "Cantidad de iteraciones = " + cont + "\n" +
                               "Verificación f(raiz) = " + formattedVerif;
        
        if (Math.abs(verificacion_f) > tolerancia * 10) {
            resultadoFinal += "\n⚠️  ADVERTENCIA: f(raiz) = " + formattedVerif + 
                             " (debería ser ≈ 0)\nPosible error numérico.";
        }
        
        return resultadoFinal;
    }
    
    private double evaluarFuncion(double x, String fun) {
        try {
            Expression expression = new ExpressionBuilder(fun)
                    .variable("x")
                    .build()
                    .setVariable("x", x);

            return expression.evaluate();
        } catch (Exception e) {
            System.err.println("Error evaluando " + fun + " en x=" + x + ": " + e.getMessage());
            return Double.NaN;
        }
    }
}
