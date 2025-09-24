package com.cegb03.metodos.LocRaices.Codes;

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

    // Constructor corregido - solo necesita funX
    public SecanteCode(double xv, double tol, String funX, String funY) {
        this.xv = xv;
        this.xvv = xv - 0.01; // Pequeña perturbación para el segundo punto inicial
        this.tolerancia = tol;
        this.funX = funX;  // Solo usar funX
        // this.funY = funY;  // NO usar funY en Secante
        this.error = Double.MAX_VALUE;
    }

    public String calcularRaiz() {
        // ELIMINAR todo el manejo de derivadas - El método Secante NO usa derivada
        // if (!(funX.isBlank())) {
        //     derivadaAux = derivacion.derivar(funX);
        // }
        // ... TODO ESE CÓDIGO DE DERIVADA DEBE SER ELIMINADO

        // 2- Inicializar xvv, xv, error, iteraciones - ya están inicializados
        // 4- Definir la tolerancia - ya está definida

        // Verificar que las funciones se pueden evaluar en los puntos iniciales
        double f_xvv = evaluarFuncion(xvv, funX);  // Solo usar funX (f(x))
        double f_xv = evaluarFuncion(xv, funX);    // Solo usar funX (f(x))

        if (Double.isNaN(f_xvv) || Double.isNaN(f_xv)) {
            JOptionPane.showMessageDialog(null, "Error: No se puede evaluar la función f(x) en los puntos iniciales.");
            return "Error: No se puede evaluar la función f(x) en los puntos iniciales.";
        }

        // 5- Realizar el bucle do while
        do {
            iteraciones++;

            // Evaluar la función solo en funX
            f_xvv = evaluarFuncion(xvv, funX); // f(xi-1)
            f_xv = evaluarFuncion(xv, funX);   // f(xi)

            // Verificar división por cero
            if (Math.abs(f_xvv - f_xv) < 1e-15) {
                return "Error: División por cero en el método de la Secante. f(xi-1) ≈ f(xi)";
            }

            // FÓRMULA CORRECTA DE LA SECANTE:
            // xi+1 = xi - f(xi) * (xi-1 - xi) / (f(xi-1) - f(xi))
            xnuevo = xv - f_xv * (xvv - xv) / (f_xvv - f_xv);

            // Calcular error
            error = Math.abs(xnuevo - xv);

            // Actualizar puntos: xvv = xv y xv = xnuevo
            xvv = xv;
            xv = xnuevo;

            // Verificar si encontramos la raíz exacta
            if (Math.abs(evaluarFuncion(xnuevo, funX)) < 1e-15) {
                break;
            }

        } while (error > tolerancia && iteraciones < 5000);

        DecimalFormat df = new DecimalFormat("0.00000000000000000000");
        String formattedError = df.format(error);
        String formattedRoot = df.format(xnuevo);

        return "Fun = " + funX +
               "\nRaiz = " + formattedRoot +  // NO mostrar funY porque no se usa
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