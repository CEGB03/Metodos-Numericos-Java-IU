package com.cegb03.metodos.LocRaices.Abiertos;

import com.cegb03.metodos.calculos.Derivar;
import java.text.DecimalFormat;
import javax.swing.JOptionPane;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

/**
 *
 * @author cegb03
 */
public class SecanteCode {

    private final String funX;
    private String funY;
    private String derivadaAux;
    private String vuelta = ""; // Para acumular mensajes de error/advertencia
    private final Double tolerancia;
    private Double xvv; // xi-1 (punto anterior)
    private Double xv; // xi (punto actual)
    private Double xnuevo; // xi+1 (nuevo punto calculado)
    private Double error;
    private int iteraciones = 0;
    private final Derivar derivacion = new Derivar();

    // Constructor según material teórico: solo necesita UN punto inicial
    public SecanteCode(double xv, double tol, String funX, String funY) {
        this.xv = xv;
        // Inicializar xvv con una pequeña perturbación para comenzar el algoritmo
        this.xvv = xv - 0.01; // o xv * 0.99 si xv != 0
        this.tolerancia = tol;
        this.funX = funX;
        this.funY = funY;
        this.error = Double.MAX_VALUE;
    }

    public String calcularRaiz() {
        // 1- Definir f(x) - ya está en funX

        // Manejo de derivadas como en Newton-Raphson
        if (!(funX.isBlank())) {
            derivadaAux = derivacion.derivar(funX);
        } else {
            derivadaAux = funY;
        }

        if (funY.isBlank()) {
            funY = derivadaAux;
        }

        if (!(derivadaAux.equalsIgnoreCase(funY))) {
            JOptionPane.showMessageDialog(null, "Error al derivar la función, no son iguales funY " + funY
                    + " y la derivada " + derivadaAux + "\nSe usara funY ingresada.");
            vuelta = "Error al derivar la función, no son iguales funY " + funY + " y la derivada " + derivadaAux
                    + "\nSe usara funY ingresada.\n";
        }

        // 2- Inicializar xvv, xv, error, iteraciones - ya están inicializados
        // 4- Definir la tolerancia - ya está definida

        // Verificar que las funciones se pueden evaluar en los puntos iniciales
        double f_xvv = evaluarFuncion(xvv, funX);
        double f_xv = evaluarFuncion(xv, funX);

        if (Double.isNaN(f_xvv) || Double.isNaN(f_xv)) {
            JOptionPane.showMessageDialog(null, "Error: No se puede evaluar la función f(x) en los puntos iniciales.");
            return vuelta + "Error: No se puede evaluar la función f(x) en los puntos iniciales.";
        }

        // 5- Realizar el bucle do while
        do {
            // Aumentar la iteración
            iteraciones++;

            // Evaluar la función en los puntos actuales
            f_xvv = evaluarFuncion(xvv, funX); // f(xi-1)
            f_xv = evaluarFuncion(xv, funX); // f(xi)

            // Verificar división por cero (similar al check de derivada pequeña en
            // Newton-Raphson)
            if (Math.abs(f_xvv - f_xv) < 1e-15) {
                System.out.println("\n********\nDIFERENCIA DE FUNCIONES PEQUEÑA - POSIBLE DIVISIÓN POR CERO********\n");
                JOptionPane.showMessageDialog(null,
                        "Error: División por cero en el método de la Secante. f(xi-1) ≈ f(xi)");
                return vuelta + "Error: División por cero en el método de la Secante. f(xi-1) ≈ f(xi)";
            }

            // FÓRMULA EXACTA SEGÚN TU MATERIAL TEÓRICO:
            // xi+1 = xi - f(xi) * (xi-1 - xi) / (f(xi-1) - f(xi))
            xnuevo = xv - f_xv * (xvv - xv) / (f_xvv - f_xv);

            // Calcular el error
            error = Math.abs(xnuevo - xv);

            // Realizar los pasajes: xvv = xv y xv = xnuevo
            xvv = xv;
            xv = xnuevo;

            // Verificar si encontramos la raíz exacta
            if (Math.abs(evaluarFuncion(xnuevo, funX)) < 1e-15) {
                break;
            }

        } while (error > tolerancia && iteraciones < 5000);

        // Mostrar mensaje si se alcanzó el límite de iteraciones
        if (iteraciones >= 5000) {
            System.out.println("\n********\nSE ALCANZÓ EL LÍMITE MÁXIMO DE ITERACIONES (5000)********\n");
            JOptionPane.showMessageDialog(null,
                    "Advertencia: Se alcanzó el límite máximo de iteraciones (5000). El resultado puede no ser preciso.");
            vuelta += "Advertencia: Se alcanzó el límite máximo de iteraciones (5000). El resultado puede no ser preciso.\n";
        }

        DecimalFormat df = new DecimalFormat("0.00000000000000000000");
        String formattedError = df.format(error);
        String formattedRoot = df.format(xnuevo);

        return vuelta + "Fun = " + funX +
                "\nFunG = " + funY +
                "\nRaiz = " + formattedRoot +
                "\nerror estimado = " + formattedError +
                "\nCantidad de iteraciones = " + iteraciones;
    }

    private double evaluarFuncion(double x, String fun) {
        try {
            Expression expression = new ExpressionBuilder(fun)
                    .variable("x")
                    .build()
                    .setVariable("x", x);

            return expression.evaluate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al evaluar la función: " + fun + " en el punto " + x
                    + " por el siguiente error: " + e.getMessage());
            return Double.NaN;
        }
    }
}