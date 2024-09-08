package com.cegb03.metodos.logica.Interpolaciones;

import javax.swing.JOptionPane;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

/**
 *
 * @author cegb03
 */
public class LagrangeCode {
    private Double[][] A;
    private Double[] b;
    private Double[] x;
    private Double[] y;
    double coeficienteInterpolador;//Coeficiente a interpolar
    double e;
    private int filas = 0;
    private int columnas = 0;
    private double factor = 0;
    private int p = 0;
    private Double swap = (double) 0;
    private String solucion = "";
    private String matriz = "";
    private String retornar = "";
    private final String funX;

    public LagrangeCode(Double[][] A, Double[] b, int filas, int columnas, double interppolador, String funX) {
        this.A = A;
        this.b = b;
        this.filas = filas;
        this.columnas = columnas;
        this.coeficienteInterpolador = interppolador;
        this.funX = funX;
    }
    public void imprimriMatrizLlegada(){
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++)
                System.out.print(A[i][j] + " ");
            System.out.println("| " + b[i]);
        }
        System.out.println();
        separarXY();
    }
    public String interpolar(){
        separarXY();
        Double sum = 0.0;
        for (int i = 0; i < filas; i++) {
            Double producto = 1.0;
            for (int j = 0; j < filas; j++) {
                if (j != i) {
                    producto *= ((coeficienteInterpolador - x[j]) / (x[j] - x[i]));
                }
            }
            sum += y[i] * producto;
        }
        e = Math.abs(evaluarFuncion(coeficienteInterpolador, funX) - sum);

        System.out.println("El valor interpolado para "+coeficienteInterpolador+" es: "+sum+", con un error de "+e+".");
        retornar += ("El valor interpolado para "+coeficienteInterpolador+" es: "+sum+", con un error de "+e+".");
        imprimirPolinomioCnk();
        retornar += "\n" + solucion;
        return retornar;
    }
    private void separarXY(){
            x = new Double[filas];
            y = new Double[filas];
            for (int i = 0; i < filas; i++) {
                x[i] = A[i][0];
                y[i] = b[i];
            }
            for (int i = 0; i < filas; i++) {
                System.out.println("( " + x[i] + " ; " + y[i] + ")");
            }
        }
    private void imprimirPolinomioCnk() {
        StringBuilder polinomio = new StringBuilder();

        for (int i = 0; i < filas; i++) {
            StringBuilder termino = new StringBuilder();
            termino.append("\n").append(y[i]);

            for (int j = 0; j < filas; j++) {
                if (j != i) {
                    termino.append(" * (x - ").append(x[j]).append(") / (").append(x[i]).append(" - ").append(x[j]).append(")");
                }
            }

            // Añadir el signo más entre términos (excepto antes del primer término)
            if (i > 0 && y[i] >= 0) {
                polinomio.append(" + ");
            } else if (y[i] < 0) {
                polinomio.append(" - ");
                termino.deleteCharAt(0);  // Eliminar el signo negativo de y[i] si es negativo
            }

            polinomio.append(termino);
        }

        System.out.println("El polinomio interpolador de Lagrange es: ");
        System.out.println(polinomio.toString());
        solucion +=("El polinomio interpolador de Lagrange es: ");
        solucion +=(polinomio.toString());
    }
    private double evaluarFuncion(double x, String fun) {
        try {
            Expression expression = new ExpressionBuilder(fun)
                    .variable("x")
                    .build()
                    .setVariable("x", x);

            return expression.evaluate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al evaluar la función: "+ fun + " en el punto " + x + " por el siguiente error: " + e.getMessage());
            return Double.NaN;
        }
    }
}
