package com.cegb03.metodos.logica.LocRaices.Abiertos;

import java.text.DecimalFormat;
import javax.swing.JOptionPane;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 *
 * @author cegb03
 */
public class NewtonRapsonCode {
    
    private final String funX;
    private String funY;
    private String derivadaAux;
    private final Double tolerancia;
    private Double a;
    private Double b;
    private Double error;
    private int cont=0;

    public NewtonRapsonCode(double a, double tol, String funX, String funY){
        this.a = a;
        this.tolerancia = tol;
        this.funX = funX;
        this.funY = funY;
        error=0.0;
    }

    public String calcularRaiz(){
        if ( !(funX.isBlank()) )
            derivadaAux = derivar(funX);
        else
            derivadaAux = funY;
        if(funY.isBlank())
            funY = derivadaAux;
        if (!( derivadaAux.equalsIgnoreCase(funY) ) ){
            JOptionPane.showMessageDialog(null, "Error al derivar la función, no son iguales funF " + funX + " y funY " + funY + "\nSe usara funY ingresada.");
            return "Error al derivar la función, no son iguales funF " + funX + " y funY " + funY + "\nSe usara funY ingresada.";
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
    
    public static String derivar(String funcion) {
        // Primero, maneja términos de la forma x^n
        String derivada = funcion;
        
        // Derivada de términos de la forma x^n (e.g., x^2 -> 2*x)
        Pattern pattern = Pattern.compile("([+-]?\\d*)\\*?x\\^([0-9]+)");
        Matcher matcher = pattern.matcher(derivada);
        StringBuffer resultado = new StringBuffer();
        
        while (matcher.find()) {
            String coeficiente = matcher.group(1);
            int exponente = Integer.parseInt(matcher.group(2));
            int nuevoExponente = exponente - 1;
            
            // Si el coeficiente está vacío, es 1 o -1 según el signo
            if (coeficiente.isEmpty() || coeficiente.equals("+")) {
                coeficiente = "1";
            } else if (coeficiente.equals("-")) {
                coeficiente = "-1";
            }
            
            // Crear el reemplazo para el término derivado
            String reemplazo;
            if (nuevoExponente > 0) {
                reemplazo = (exponente * Integer.parseInt(coeficiente)) + "*x^" + nuevoExponente;
            } else {
                reemplazo = (exponente * Integer.parseInt(coeficiente)) + "";
            }
            matcher.appendReplacement(resultado, reemplazo);
        }
//        matcher.appendTail(resultado);
        derivada = resultado.toString();
        
        // Luego, maneja términos lineales (e.g., x -> 1)
        derivada = derivada.replaceAll("(?<!\\*)\\bx\\b(?!\\^)", "1");
        
        // Elimina términos innecesarios
        derivada = derivada.replaceAll("\\+\\-", "-");  // Corrige casos de +-.
        derivada = derivada.replaceAll("--", "+");      // Corrige signos negativos dobles.
        derivada = derivada.replaceAll("(?<!\\d)0\\*x\\^0", ""); // Elimina 0*x^0
        derivada = derivada.replaceAll("(?<!\\d)0\\*x", "");     // Elimina 0*x
        derivada = derivada.replaceAll("(?<=\\D)0(?=\\D)", "");  // Elimina ceros entre operadores
        derivada = derivada.replaceAll("\\*x\\^1\\b", "*x");    // Elimina x^1 (debe ser simplemente x)
        derivada = derivada.replaceAll("^\\+|\\+$", ""); // Elimina signos de + al inicio o final
        
        // Elimina términos constantes
        derivada = derivada.replaceAll("(?<=\\D)([+-]?\\d+)(?=\\D|$)", "");

        // Si la derivada queda vacía, significa que era una constante
        if (derivada.trim().isEmpty()) {
            derivada = "0";
        }
        
        System.out.println("derivada " + derivada);
        return derivada;
    }
    
}
