package com.cegb03.metodos.logica.LocRaices.Cerrados;

import java.text.DecimalFormat;
import javax.swing.JOptionPane;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

/**
 *
 * @author cegb03
 */
public class BiseccionCode {
    private String funX;
    private Double a;
    private Double b;
    private Double c;
    private Double tolerancia;
    private Double error;
    private int cont=0;
    
    public BiseccionCode(double a, double b, double tol, String funX){
        this.a = a;
        this.b = b;
        this.tolerancia = tol;
        this.funX = funX;
        error=0.0;
    }
    
    public String calcularRaiz(){
        if(fun(a)*fun(b)>0){
            return "No cumple condicion para operar"+fun(a)*fun(b)+"<0";
        }
        
        cont=0;
        
        do {            
            c=(a+b)/2 ;
            cont++;
            if(fun(a)*fun(c)>0)
                a = c;
            else if(fun(a)*fun(c)<0)
                b = c;
            else{
                break;
            }
            error = (b-a)/2;
            System.out.println("Iteracion " + cont);
        } while (error>tolerancia && cont < 15000);
        DecimalFormat df = new DecimalFormat("0.00000000000000000000"); // Define el formato con 20 decimales
//        DecimalFormat df = new DecimalFormat("0.00000"); // Define el formato con 20 decimales
        String formattedError = df.format(error);// Calcular la cantidad de decimales según la tolerancia
        return "Fun = "+ funX +"\nRaiz = " + c + "\nerror estimado = " + formattedError + "\nCantidad de iteraciones = " + cont;
    }
    
    private double fun(double x) {
        try {
            Expression expression = new ExpressionBuilder(funX)
                    .variable("x")
                    .build()
                    .setVariable("x", x);

            return expression.evaluate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al evaluar la función: " + e.getMessage());
            return Double.NaN;
        }
    }
    
    public Double getA() {
        return a;
    }

    public void setA(Double a) {
        this.a = a;
    }

    public Double getB() {
        return b;
    }

    public void setB(Double b) {
        this.b = b;
    }

    public Double getC() {
        return c;
    }

    public void setC(Double c) {
        this.c = c;
    }

    public Double getTolerancia() {
        return tolerancia;
    }

    public void setTolerancia(Double tolerancia) {
        this.tolerancia = tolerancia;
    }

    public Double getError() {
        return error;
    }

    public void setError(Double error) {
        this.error = error;
    }
}
