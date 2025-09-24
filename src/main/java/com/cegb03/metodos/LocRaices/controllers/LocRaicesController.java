package com.cegb03.metodos.LocRaices.controllers;

import com.cegb03.metodos.LocRaices.models.*;
import com.cegb03.metodos.LocRaices.Abiertos.*;
import com.cegb03.metodos.LocRaices.Cerrados.*;
import com.cegb03.metodos.calculos.GeneradorPuntoFijo;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocRaicesController {

    private final GeneradorPuntoFijo generadorPF = new GeneradorPuntoFijo();

    public CalculationResult executeCalculations(CalculationInput input) {
        CalculationResult result = new CalculationResult();
        StringBuilder resultados = new StringBuilder();

        try {
            // VALIDAR Y CORREGIR FUNCIÓN G(X) PARA PUNTO FIJO
            if (input.isPuntoFijoSelected()) {
                validateAndCorrectPointoFijo(input, result);
            }

            // Agregar timestamp
            resultados.append("Hora: ").append(LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append("\n");

            // Ejecutar métodos cerrados
            if (input.hasClosedMethodsSelected()) {
                executeClosedMethods(input, resultados);
            }

            // Ejecutar métodos abiertos
            if (input.hasOpenMethodsSelected()) {
                executeOpenMethods(input, resultados);
            }

            result.setResults(resultados.toString());
            result.setSuccess(true);

        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMessage("Error en los cálculos: " + e.getMessage());
        }

        return result;
    }

    /**
     * Valida y corrige automáticamente la función g(x) para Punto Fijo
     */
    private void validateAndCorrectPointoFijo(CalculationInput input, CalculationResult result) {
        String funF = input.getFunX();
        String funGPF = input.getFunGPF();
        Double xn = input.getXn();
        Double tolerancia = input.getTolerancia();

        // Si no hay función g(x), generar automáticamente
        if (funGPF == null || funGPF.trim().isEmpty()) {
            String gGenerada = generadorPF.generarFuncionG(funF);
            input.setFunGPF(gGenerada);
            result.setGeneratedFunction(gGenerada);
            result.setGenerationInfo("Función g(x) generada automáticamente desde F(x) = 0");
            return;
        }

        // Verificar si la función g(x) es correcta
        boolean esCorrecta = verificarFuncionPuntoFijo(funF, funGPF, xn, tolerancia);

        if (!esCorrecta) {
            // La función g(x) no es correcta, generar una nueva
            String gCorregida = generadorPF.generarFuncionG(funF);

            // Verificar que la función corregida sea efectivamente mejor
            boolean corregidaEsMejor = verificarFuncionPuntoFijo(funF, gCorregida, xn, tolerancia);

            if (corregidaEsMejor) {
                input.setFunGPF(gCorregida);
                result.setGeneratedFunction(gCorregida);
                result.setGenerationInfo(String.format(
                        "CORRECCIÓN AUTOMÁTICA APLICADA\n" +
                                "════════════════════════════════\n" +
                                "La función g(x) ingresada no garantiza convergencia.\n" +
                                "Función original: %s\n" +
                                "Función corregida: %s\n" +
                                "La nueva función garantiza convergencia para x₀ = %.4f",
                        funGPF, gCorregida, xn));
            } else {
                // Si ninguna función es buena, usar la original pero advertir
                result.setGenerationInfo(String.format(
                        "ADVERTENCIA: La función g(x) = %s podría no converger.\n" +
                                "Considere usar un valor inicial diferente o revisar la función.",
                        funGPF));
            }
        }
    }

    /**
     * Verifica si la función g(x) es adecuada para Punto Fijo
     */
    private boolean verificarFuncionPuntoFijo(String funF, String funG, Double x0, Double tolerancia) {
        try {
            // Crear instancia temporal de Punto Fijo para verificar
            PuntoFijo verificador = new PuntoFijo();

            // Ejecutar algunas iteraciones para ver si converge
            String resultado = verificador.calcular(funF, funG, x0, tolerancia);

            // Si el resultado contiene "convergencia" o no contiene "error", es buena
            return !resultado.toLowerCase().contains("error") &&
                    !resultado.toLowerCase().contains("diverge") &&
                    !resultado.toLowerCase().contains("no converge");

        } catch (Exception e) {
            return false;
        }
    }

    private void executeClosedMethods(CalculationInput input, StringBuilder resultados) {
        if (input.isBiseccionSelected()) {
            try {
                Biseccion biseccion = new Biseccion();
                String resultado = biseccion.calcular(input.getFunX(), input.getA(), input.getB(),
                        input.getTolerancia());
                resultados.append("Bisección:\n").append(resultado).append("\n");
            } catch (Exception e) {
                resultados.append("Error en Bisección: ").append(e.getMessage()).append("\n");
            }
        }

        if (input.isRegulaFalsiSelected()) {
            try {
                RegulaFalsi regulaFalsi = new RegulaFalsi();
                String resultado = regulaFalsi.calcular(input.getFunX(), input.getA(), input.getB(),
                        input.getTolerancia());
                resultados.append("Regula Falsi:\n").append(resultado).append("\n");
            } catch (Exception e) {
                resultados.append("Error en Regula Falsi: ").append(e.getMessage()).append("\n");
            }
        }
    }

    private void executeOpenMethods(CalculationInput input, StringBuilder resultados) {
        if (input.isNewtonRapsonSelected()) {
            try {
                NewtonRapson newton = new NewtonRapson();
                String resultado = newton.calcular(input.getFunX(), input.getFunY(), input.getXn(),
                        input.getTolerancia());
                resultados.append("Newton Rapson:\n").append(resultado).append("\n");
            } catch (Exception e) {
                resultados.append("Error en Newton-Raphson: ").append(e.getMessage()).append("\n");
            }
        }

        if (input.isPuntoFijoSelected()) {
            try {
                PuntoFijo puntoFijo = new PuntoFijo();
                String resultado = puntoFijo.calcular(input.getFunX(), input.getFunGPF(), input.getXn(),
                        input.getTolerancia());
                resultados.append("Punto Fijo:\n").append(resultado).append("\n");
            } catch (Exception e) {
                resultados.append("Error en Punto Fijo: ").append(e.getMessage()).append("\n");
            }
        }

        if (input.isSecanteSelected()) {
            try {
                Secante secante = new Secante();
                String resultado = secante.calcular(input.getFunX(), input.getXn(), input.getTolerancia());
                resultados.append("Secante:\n").append(resultado).append("\n");
            } catch (Exception e) {
                resultados.append("Error en Secante: ").append(e.getMessage()).append("\n");
            }
        }
    }
}