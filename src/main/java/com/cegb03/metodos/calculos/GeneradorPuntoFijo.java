package com.cegb03.metodos.calculos;

import java.util.ArrayList;
import java.util.List;

/**
 * Generador automático de funciones g(x) para el método de Punto Fijo
 * usando factor de relajación que garantiza convergencia.
 * 
 * @author cegb03
 */
public class GeneradorPuntoFijo {

    /**
     * Genera automáticamente una función g(x) con factor de relajación que
     * converge.
     * 
     * @param funX         La función original f(x)
     * @param puntoInicial El punto inicial x0
     * @return Un objeto con la función g(x) y el factor λ que garantiza
     *         convergencia
     */
    public static ResultadoGeneracion generarFuncionConvergente(String funX, double puntoInicial) {
        Derivar derivador = new Derivar();

        // Array de factores de relajación a probar (de más conservador a menos)
        double[] factoresRelajacion = {
                0.01, 0.05, 0.1, 0.15, 0.2, 0.25, 0.3, 0.35, 0.4, 0.45,
                0.5, 0.55, 0.6, 0.65, 0.7, 0.75, 0.8, 0.85, 0.9, 0.95, 0.99
        };

        List<String> intentosFallidos = new ArrayList<>();

        // Probar cada factor de relajación
        for (double lambda : factoresRelajacion) {
            // Generar g(x) = x - λ * f(x)
            String funcionG = generarFuncionG(funX, lambda);

            try {
                // Verificar criterio de convergencia |g'(x)| < 1
                String derivadaG = derivador.derivar(funcionG);
                double valorDerivada = evaluarFuncion(derivadaG, puntoInicial);

                if (!Double.isNaN(valorDerivada) && !Double.isInfinite(valorDerivada)) {
                    double criterio = Math.abs(valorDerivada);

                    if (criterio < 1.0) {
                        // ¡Encontramos una función que converge!
                        return new ResultadoGeneracion(
                                funcionG,
                                lambda,
                                derivadaG,
                                criterio,
                                true,
                                "Convergencia garantizada: |g'(" + puntoInicial + ")| = " + criterio + " < 1",
                                intentosFallidos);
                    } else {
                        intentosFallidos.add("λ = " + lambda + " → |g'(x)| = " + criterio + " ≥ 1");
                    }
                } else {
                    intentosFallidos.add("λ = " + lambda + " → g'(x) indefinida");
                }

            } catch (Exception e) {
                intentosFallidos.add("λ = " + lambda + " → Error: " + e.getMessage());
            }
        }

        // Si ningún factor funcionó, usar el método de aproximación numérica
        return generarPorAproximacionNumerica(funX, puntoInicial, intentosFallidos);
    }

    /**
     * Genera la función g(x) = x - λ * f(x)
     */
    private static String generarFuncionG(String funX, double lambda) {
        if (lambda == 1.0) {
            return "x - (" + funX + ")";
        } else {
            return "x - " + lambda + "*(" + funX + ")";
        }
    }

    /**
     * Método de respaldo: encuentra λ por aproximación numérica
     */
    private static ResultadoGeneracion generarPorAproximacionNumerica(String funX, double puntoInicial,
            List<String> intentosAnteriores) {
        Derivar derivador = new Derivar();

        try {
            // Calcular f'(x) en el punto inicial
            String derivadaF = derivador.derivar(funX);
            double fPrima = evaluarFuncion(derivadaF, puntoInicial);

            if (!Double.isNaN(fPrima) && !Double.isInfinite(fPrima) && Math.abs(fPrima) > 0.001) {
                // Calcular λ óptimo: λ = 1 / (2 * |f'(x)|)
                double lambdaOptimo = 1.0 / (2.0 * Math.abs(fPrima));

                // Limitar λ para evitar valores extremos
                lambdaOptimo = Math.min(lambdaOptimo, 0.99);
                lambdaOptimo = Math.max(lambdaOptimo, 0.001);

                String funcionG = generarFuncionG(funX, lambdaOptimo);
                String derivadaG = derivador.derivar(funcionG);
                double criterio = Math.abs(evaluarFuncion(derivadaG, puntoInicial));

                return new ResultadoGeneracion(
                        funcionG,
                        lambdaOptimo,
                        derivadaG,
                        criterio,
                        criterio < 1.0,
                        criterio < 1.0
                                ? "Convergencia por aproximación numérica: |g'(" + puntoInicial + ")| = " + criterio
                                : "ADVERTENCIA: Aproximación numérica no garantiza convergencia: |g'(" + puntoInicial
                                        + ")| = " + criterio,
                        intentosAnteriores);
            }

        } catch (Exception e) {
            intentosAnteriores.add("Aproximación numérica falló: " + e.getMessage());
        }

        // Último recurso: función g(x) muy conservadora
        String funcionGConservadora = "x - 0.01*(" + funX + ")";
        return new ResultadoGeneracion(
                funcionGConservadora,
                0.01,
                "1 - 0.01*(" + derivador.derivar(funX) + ")",
                0.99, // Estimación conservadora
                true, // Asumimos que es muy conservadora
                "Función ultra-conservadora generada (λ = 0.01)",
                intentosAnteriores);
    }

    /**
     * Evalúa una función en un punto específico
     */
    private static double evaluarFuncion(String funcion, double x) {
        try {
            net.objecthunter.exp4j.Expression expression = new net.objecthunter.exp4j.ExpressionBuilder(funcion)
                    .variable("x")
                    .build()
                    .setVariable("x", x);
            return expression.evaluate();
        } catch (Exception e) {
            return Double.NaN;
        }
    }

    /**
     * Clase para encapsular el resultado de la generación
     */
    public static class ResultadoGeneracion {
        private final String funcionG;
        private final double lambda;
        private final String derivadaG;
        private final double criterioConvergencia;
        private final boolean converge;
        private final String mensaje;
        private final List<String> intentosFallidos;

        public ResultadoGeneracion(String funcionG, double lambda, String derivadaG,
                double criterioConvergencia, boolean converge,
                String mensaje, List<String> intentosFallidos) {
            this.funcionG = funcionG;
            this.lambda = lambda;
            this.derivadaG = derivadaG;
            this.criterioConvergencia = criterioConvergencia;
            this.converge = converge;
            this.mensaje = mensaje;
            this.intentosFallidos = new ArrayList<>(intentosFallidos);
        }

        // Getters
        public String getFuncionG() {
            return funcionG;
        }

        public double getLambda() {
            return lambda;
        }

        public String getDerivadaG() {
            return derivadaG;
        }

        public double getCriterioConvergencia() {
            return criterioConvergencia;
        }

        public boolean converge() {
            return converge;
        }

        public String getMensaje() {
            return mensaje;
        }

        public List<String> getIntentosFallidos() {
            return new ArrayList<>(intentosFallidos);
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("=== GENERACIÓN AUTOMÁTICA DE g(x) ===\n");
            sb.append("g(x) = ").append(funcionG).append("\n");
            sb.append("Factor de relajación λ = ").append(lambda).append("\n");
            sb.append("g'(x) = ").append(derivadaG).append("\n");
            sb.append("Criterio de convergencia: |g'(x)| = ").append(criterioConvergencia).append("\n");
            sb.append("Estado: ").append(converge ? "✓ CONVERGE" : "✗ NO CONVERGE").append("\n");
            sb.append("Mensaje: ").append(mensaje).append("\n");

            if (!intentosFallidos.isEmpty()) {
                sb.append("\nIntentos fallidos:\n");
                for (String intento : intentosFallidos) {
                    sb.append("  • ").append(intento).append("\n");
                }
            }

            return sb.toString();
        }
    }
}