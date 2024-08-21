package com.cegb03.metodos.logica.LocRaices.Abiertos;

import com.cegb03.metodos.logica.Derivar;
import java.text.DecimalFormat;
import javax.swing.JOptionPane;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
/**
 *
 * @author cegb03
 */
public class PuntoFijoCode {
    
    private final String funX;
    private String funY;
    private String derivadaAux;
    private final Double tolerancia;
    private Double a;
    private Double b;
    private Double error;
    private int cont=0;
    private final double delta = 0.01;
    private final Derivar derivacion = new Derivar();

    public PuntoFijoCode(double a, double tol, String funX, String funY){
        this.a = a;
        this.tolerancia = tol;
        this.funX = funX;
        this.funY = funY;
        error=0.0;
    }

    public String calcularRaiz(){
        if ( !(funX.isBlank()) )
            derivadaAux = derivacion.derivar(funX);
        else
            derivadaAux = funY;
        if(funY.isBlank())
            funY = derivadaAux;
        if (!( derivadaAux.equalsIgnoreCase(funY) ) ){
            JOptionPane.showMessageDialog(null, "Error al derivar la función, no son iguales funF " + funX + " y funY " + funY + "\nSe usara funY ingresada.");
            return "Error al derivar la función, no son iguales funF " + funX + " y funY " + funY + "\nSe usara funY ingresada.";
        }
        
        do{
            cont++;
           if ((Math.abs((evaluarFuncion(a + delta, funY) - evaluarFuncion(a, funY)) / delta)) > 1) {
                return "\nNo cumple con el criterio de convergencia. El método diverge (|g'(x)| ≥ 1)";
            }else{
                b = evaluarFuncion(a, funY);
                error = Math.abs(b - a);
                a = b;
            }

        } while (error > tolerancia && cont < 15000);
        DecimalFormat df = new DecimalFormat("0.00000000000000000000"); // Define el formato con 20 decimales
//        DecimalFormat df = new DecimalFormat("0.00000"); // Define el formato con 20 decimales
        String formattedError = df.format(error);// Calcular la cantidad de decimales según la tolerancia
        return "Fun = "+ funX +"FunG = "+ funY +"\nRaiz = " + b + "\nerror estimado = " + formattedError + "\nCantidad de iteraciones = " + cont;
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
