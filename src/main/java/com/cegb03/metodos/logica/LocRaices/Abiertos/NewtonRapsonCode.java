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
public class NewtonRapsonCode {
    
    private final String funX;
    private String funY;
    private String derivadaAux;
    private String vuelta;
    private final Double tolerancia;
    private Double a;
    private Double b;
    private Double error;
    private int cont=0;
    private final Derivar derivacion = new Derivar();

    public NewtonRapsonCode(double a, double tol, String funX, String funY){
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
            JOptionPane.showMessageDialog(null, "Error al derivar la función, no son iguales funY " + funY + " y la derivada " + derivadaAux + "\nSe usara funY ingresada.");
            vuelta = "Error al derivar la función, no son iguales funY " + funY + " y la derivada " + derivadaAux + "\nSe usara funY ingresada.\n";
        }
        
        do{
            cont ++ ;

            if( Math.abs(evaluarFuncion(a, funY) ) < 1e-5){
                System.out.println("\n********\nDERIVADA PEQUEÑA********\n");
                break;
            } else{
                b = a - (evaluarFuncion(a, funX)/evaluarFuncion(a, funY));
                error = Math.abs(b - a);
                a = b;
            }
        } while (error > tolerancia && cont < 15000);
        DecimalFormat df = new DecimalFormat("0.00000000000000000000"); // Define el formato con 20 decimales
//        DecimalFormat df = new DecimalFormat("0.00000"); // Define el formato con 20 decimales
        String formattedError = df.format(error);// Calcular la cantidad de decimales según la tolerancia
        return vuelta + "Fun = "+ funX +"FunG = "+ funY +"\nRaiz = " + b + "\nerror estimado = " + formattedError + "\nCantidad de iteraciones = " + cont;
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
