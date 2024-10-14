package com.cegb03.metodos.logica;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author cegb03
 */
public class Derivar {
    
    public static String derivar(String funcion) {
        // Primero, maneja términos de la forma x^n
        String derivada = funcion;
        System.out.println("Original:                                         " + derivada);
        
        // Elimina constantes sueltas que no están precedidas por un término x con o sin exponente
        derivada = derivada.replaceAll("(?<!\\*x\\^-?\\d+)(?<!\\*x)(?<!\\^-?)\\b[\\+\\-]?\\d+\\b(?!\\*?x)", "");
        System.out.println("Después de eliminar constantes sueltas:           " + derivada);
        
        System.out.println("Derivada de eliminar constantes sueltas esperada: 2*x^-5+3*x^3-5*x^-2+6*x");

        // Derivada de términos de la forma x^n (e.g., x^2 -> 2*x)
        Pattern pattern = Pattern.compile("([+-]?\\d*)\\*?x\\^([-]?\\d+)");
        Matcher matcher = pattern.matcher(derivada);
        StringBuffer resultado = new StringBuffer();

        while (matcher.find()) {
            String coeficiente = matcher.group(1);
            int exponente = Integer.parseInt(matcher.group(2));
            int nuevoExponente = exponente > 0 ? exponente - 1 : exponente + 1;

            // Si el coeficiente está vacío, es 1 o -1 según el signo
            if (coeficiente.isEmpty() || coeficiente.equals("+")) {
                coeficiente = "1";
            } else if (coeficiente.equals("-")) {
                coeficiente = "-1";
            }
            
            // Multiplicar coeficiente por exponente
            int nuevoCoeficiente = exponente * Integer.parseInt(coeficiente);


            // Crear el reemplazo para el término derivado
            String reemplazo;
            if (nuevoExponente != 0) {
                reemplazo = (nuevoCoeficiente < 0 ? "" : "+") + nuevoCoeficiente + "*x^" + nuevoExponente;
            } else {
                reemplazo = (nuevoCoeficiente < 0 ? "" : "+") + nuevoCoeficiente;
            }

            // Asegurarse de que el signo sea correcto
            if (nuevoCoeficiente < 0) {
                reemplazo = reemplazo.replaceFirst("\\+", "");
            }

            System.out.println("Reemplazo en x^n: " + reemplazo);

            matcher.appendReplacement(resultado, reemplazo);
        }
        matcher.appendTail(resultado);
        derivada = resultado.toString();
        System.out.println("Después de derivar x^n: " + derivada); 

        // Derivada de términos lineales (e.g., 6*x -> 6)
        derivada = derivada.replaceAll("([+-]?\\d*)\\*?x(?!\\^)", "$1");
        System.out.println("Después de manejar x lineal: " + derivada);

        // Corrige errores de signos y simplifica términos
        derivada = derivada.replaceAll("\\+\\-", "-");  // Corrige casos de +-.
        System.out.println("Después de corregir +-: " + derivada);
        
        derivada = derivada.replaceAll("--", "+");      // Corrige signos negativos dobles.
        System.out.println("Después de corregir signos negativos dobles: " + derivada);
        
        derivada = derivada.replaceAll("\\*x\\^0", ""); // Elimina x^0 (debe ser 1).
        System.out.println("Después de eliminar x^0: " + derivada);
        
        derivada = derivada.replaceAll("(?<!\\d)0\\*x", ""); // Elimina 0*x.
        System.out.println("Después de eliminar 0*x: " + derivada);
        
        derivada = derivada.replaceAll("(?<=\\D)0(?=\\D)", ""); // Elimina ceros entre operadores.
        System.out.println("Después de eliminar ceros entre operadores: " + derivada);
        
        derivada = derivada.replaceAll("\\*x\\^1\\b", "*x"); // Elimina x^1 (debe ser simplemente x).
        System.out.println("Después de eliminar x^1: " + derivada);
        
        derivada = derivada.replaceAll("^\\+|\\+$", ""); // Elimina signos de + al inicio o final.
        System.out.println("Después de eliminar signos de + al inicio o final: " + derivada);

        // Elimina cualquier signo residual al final
        derivada = derivada.replaceAll("[\\+\\-]$", "");
        System.out.println("Después de eliminar signos residuales al final: " + derivada);

        return derivada;
    }

//    public static void main(String[] args) {
//        // Prueba con el ejemplo dado
//        String funcion = "2*x^-5+3*x^3-5*x^-2+6*x-5";
//        System.out.println("Derivada obtenida: " + derivar(funcion));
//        System.out.println("Derivada esperada: -10*x^-4+9*x^2+10*x^-1+6");
//    }
}
