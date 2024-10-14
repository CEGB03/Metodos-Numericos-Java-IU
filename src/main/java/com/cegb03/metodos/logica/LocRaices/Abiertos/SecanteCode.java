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
public class SecanteCode {
    
    private final String funX;
    private String funY;
    private String derivadaAux;
    private final Double tolerancia;
    private Double a;
    private Double b;
    private Double c;
    private Double error;
    private int cont=0;
    private final Derivar derivacion = new Derivar();

    public SecanteCode(double a, double tol, String funX, String funY){
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

            if(Math.abs(evaluarFuncion(a,funY)) < 1e-5){
                System.out.println("\n********\nDERIVADA PEQUEÑA********\n");
                break;
            } else{
                double aprox = (evaluarFuncion(a,funX) - evaluarFuncion(b,funX)) / (a - b);
                c = b - (evaluarFuncion(b,funX) / aprox);
                error = Math.abs(c - b); // Calcular el error
                a = b; // Actualizar xActual con xViejo
                b = c; // Actualizar xViejo con xNuevo
                cont ++ ;
            }
        } while (error > tolerancia && cont < 15000);
        DecimalFormat df = new DecimalFormat("0.00000000000000000000"); // Define el formato con 20 decimales
//        DecimalFormat df = new DecimalFormat("0.00000"); // Define el formato con 20 decimales
        String formattedError = df.format(error);// Calcular la cantidad de decimales según la tolerancia
        return "Fun = "+ funX +"FunG = "+ funY +"\nRaiz = " + c + "\nerror estimado = " + formattedError + "\nCantidad de iteraciones = " + cont;
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