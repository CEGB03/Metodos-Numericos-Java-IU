package com.cegb03.metodos.calculos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SepararTerminos {

    public static List<Termino> separar(String funcion) {
        List<Termino> terminos = new ArrayList<>();
        int paren = 0;
        int inicio = 0;
        
        for (int i = 0; i < funcion.length(); i++) {
            char c = funcion.charAt(i);
            if (c == '(') {
                paren++;
            } else if (c == ')') {
                paren--;
            } else if ((c == '+' || c == '-') && paren == 0 && i != inicio) {
                // Verificar si este signo es parte de un exponente
                if (c == '-' && i > 0 && funcion.charAt(i-1) == '^') {
                    // Este signo menos es parte de un exponente negativo, no separar aquí
                    continue;
                }
                
                String parte = funcion.substring(inicio, i);
                Termino termino = parseTermino(parte);
                analizarTermino(termino); // NUEVO: Analizar el término
                terminos.add(termino);
                inicio = i;
            }
        }
        
        // Añadimos el último término
        if (inicio < funcion.length()) {
            String parte = funcion.substring(inicio);
            Termino termino = parseTermino(parte);
            analizarTermino(termino); // NUEVO: Analizar el término
            terminos.add(termino);
        }
        
        return terminos;
    }

    private static Termino parseTermino(String parte) {
        parte = parte.trim();
        String signo = "+";
        if (parte.startsWith("+") || parte.startsWith("-")) {
            signo = parte.substring(0, 1);
            parte = parte.substring(1);
        }
        return new Termino(parte, signo);
    }
    
    // NUEVO: Método para analizar un término y determinar su tipo y factores
    private static void analizarTermino(Termino termino) {
        String expr = termino.getTerminoOriginal().trim();
        
        // Determinar tipo de término
        String tipo = determinarTipoTermino(expr);
        termino.setTipoTermino(tipo);
        
        // Si es un producto, separar factores
        if ("producto".equals(tipo)) {
            List<String> factores = separarFactores(expr);
            termino.setFactores(factores);
        }
    }
    
    // Método para determinar el tipo de término (copiado y simplificado de Derivar)
    private static String determinarTipoTermino(String termino) {
        termino = termino.trim();
        
        // Verificar constantes numéricas
        if (termino.matches("^[+-]?\\d+(\\.\\d+)?$")) {
            return "constante";
        }
        
        // CORRECCIÓN CRÍTICA: Si contiene + o - fuera de paréntesis, ES UNA SUMA
        if (contieneSumaRestafueraParentesis(termino)) {
            return "suma";
        }
        
        // Verificar términos polinómicos simples PRIMERO
        if (termino.matches("^[+-]?\\d*\\*?x\\^[+-]?\\d+$") || 
            termino.matches("^[+-]?\\d*\\*?x$") || 
            termino.equals("x")) {
            return "polinomica";
        }
        
        // CORRECCIÓN: También incluir exponentes en paréntesis con números simples
        if (termino.matches("^[+-]?\\d*\\*?x\\^\\([+-]?\\d+\\)$")) {
            return "polinomica";
        }
        
        // CORRECCIÓN CRÍTICA: Solo detectar productos si NO contiene suma/resta
        // y tiene múltiples factores multiplicativos reales
        List<String> factores = separarFactores(termino);
        if (factores.size() > 1 && !contieneSumaRestafueraParentesis(termino)) {
            return "producto";
        }
        
        // Verificar potencias especiales (solo si no es producto)
        if (esPolinomicaEspecial(termino)) {
            return "polinomica_especial";
        }
        
        // Verificar funciones trigonométricas
        if (termino.matches("^(sin|cos|tan)\\(.+\\)$")) {
            return "trigonometrica";
        } 
        
        // Verificar funciones exponenciales y logarítmicas
        if (termino.matches("^(exp|log)\\(.+\\)$")) {
            return "exponencial";
        } 
        
        // Verificar potencias generales (u^v)
        if (buscarOperadorFueraParentesis(termino, '^') != -1) {
            return "potencia";
        }
        
        return "otro";
    }
    
    // Método para separar factores de un producto
    public static List<String> separarFactores(String termino) {
        List<String> factores = new ArrayList<>();
        
        // CORRECCIÓN CRÍTICA: Si contiene suma/resta fuera de paréntesis, NO es un producto
        if (contieneSumaRestafueraParentesis(termino)) {
            factores.add(termino); // Devolver como un solo "factor" (que en realidad es una suma)
            return factores;
        }
        
        // Casos simples conocidos
        if (termino.equals("x") || termino.matches("^\\d+$")) {
            factores.add(termino);
            return factores;
        }
        
        // CORRECCIÓN: Primero detectar multiplicaciones implícitas y añadir * explícitos
        termino = detectarMultiplicacionesImplicitas(termino);
        
        int paren = 0;
        int inicio = 0;
        
        for (int i = 0; i < termino.length(); i++) {
            char c = termino.charAt(i);
            
            if (c == '(') {
                paren++;
            } else if (c == ')') {
                paren--;
            } else if (c == '*' && paren == 0) {
                String factor = termino.substring(inicio, i);
                if (!factor.trim().isEmpty()) {
                    factores.add(factor.trim());
                }
                inicio = i + 1;
            }
        }
        
        // Añadir el último factor
        if (inicio < termino.length()) {
            String factor = termino.substring(inicio);
            if (!factor.trim().isEmpty()) {
                factores.add(factor.trim());
            }
        }
        
        // Si no encontramos *, es un factor único
        if (factores.isEmpty()) {
            factores.add(termino);
        }
        
        return factores;
    }
    
    // NUEVO: Método para detectar y corregir multiplicaciones implícitas
    private static String detectarMultiplicacionesImplicitas(String termino) {
        // SOLUCIÓN DIRECTA: Usar un enfoque paso a paso más confiable
        
        // Paso 1: Detectar patrones función)función específicos
        termino = corregirFuncionesAdyacentes(termino);
        
        // Paso 2: Patrones básicos
        // función)número -> función)*número
        termino = termino.replaceAll("\\)([0-9])", ")*$1");
        
        // función)x -> función)*x
        termino = termino.replaceAll("\\)x", ")*x");
        
        // número)función -> número)*función 
        termino = termino.replaceAll("([0-9])\\(", "$1*(");
        
        // número seguido de variable -> número*variable
        termino = termino.replaceAll("([0-9])([a-zA-Z])", "$1*$2");
        
        // variable seguida de paréntesis (que NO sean funciones) -> variable*(
        termino = termino.replaceAll("([x])\\((?!(sin|cos|tan|exp|log))", "$1*(");
        
        return termino;
    }
    
    // NUEVO: Método específico para corregir funciones adyacentes
    private static String corregirFuncionesAdyacentes(String termino) {
        // Lista de funciones matemáticas conocidas
        String[] funciones = {"sin", "cos", "tan", "exp", "log"};
        
        for (String func1 : funciones) {
            for (String func2 : funciones) {
                // Patrón: func1(...)func2(...) -> func1(...)*func2(...)
                String patron = "(" + func1 + "\\([^)]*\\))(" + func2 + "\\()";
                String reemplazo = "$1*$2";
                termino = termino.replaceAll(patron, reemplazo);
            }
        }
        
        // Casos específicos problemáticos identificados
        // tan(x^2)log(sin(x)+cos(x)) -> tan(x^2)*log(sin(x)+cos(x))
        termino = manejarFuncionesComplejas(termino);
        
        return termino;
    }
    
    // NUEVO: Método para manejar funciones con argumentos complejos
    private static String manejarFuncionesComplejas(String termino) {
        StringBuilder resultado = new StringBuilder();
        int i = 0;
        
        while (i < termino.length()) {
            // Buscar inicio de función
            int inicioFunc = encontrarInicioFuncion(termino, i);
            
            if (inicioFunc != -1) {
                // Añadir texto antes de la función
                resultado.append(termino.substring(i, inicioFunc));
                
                // Encontrar final de la función
                int finFunc = encontrarFinFuncion(termino, inicioFunc);
                
                if (finFunc != -1) {
                    // Añadir la función completa
                    resultado.append(termino.substring(inicioFunc, finFunc + 1));
                    
                    // Verificar si hay otra función inmediatamente después
                    int siguienteFunc = encontrarInicioFuncion(termino, finFunc + 1);
                    if (siguienteFunc == finFunc + 1) {
                        resultado.append("*");
                    }
                    
                    i = finFunc + 1;
                } else {
                    // Si no se puede balancear, añadir carácter y continuar
                    resultado.append(termino.charAt(i));
                    i++;
                }
            } else {
                // No hay más funciones, añadir el resto
                resultado.append(termino.substring(i));
                break;
            }
        }
        
        return resultado.toString();
    }
    
    // NUEVO: Encontrar inicio de función matemática
    private static int encontrarInicioFuncion(String termino, int desde) {
        String[] funciones = {"sin(", "cos(", "tan(", "exp(", "log("};
        
        int posicionMasCercana = Integer.MAX_VALUE;
        
        for (String func : funciones) {
            int pos = termino.indexOf(func, desde);
            if (pos != -1 && pos < posicionMasCercana) {
                posicionMasCercana = pos;
            }
        }
        
        return posicionMasCercana == Integer.MAX_VALUE ? -1 : posicionMasCercana;
    }
    
    // NUEVO: Encontrar final de función balanceando paréntesis
    private static int encontrarFinFuncion(String termino, int inicioFunc) {
        int inicioParentesis = termino.indexOf('(', inicioFunc);
        if (inicioParentesis == -1) return -1;
        
        int balance = 1;
        int i = inicioParentesis + 1;
        
        while (i < termino.length() && balance > 0) {
            if (termino.charAt(i) == '(') {
                balance++;
            } else if (termino.charAt(i) == ')') {
                balance--;
            }
            i++;
        }
        
        return balance == 0 ? i - 1 : -1;
    }
    
    // Métodos auxiliares (simplificados de Derivar)
    private static boolean contieneSumaRestafueraParentesis(String termino) {
        int paren = 0;
        for (int i = 0; i < termino.length(); i++) {
            char c = termino.charAt(i);
            if (c == '(') paren++;
            else if (c == ')') paren--;
            else if (paren == 0 && i > 0 && (c == '+' || c == '-')) {
                // CORRECCIÓN CRÍTICA: Verificar que NO sea parte de un exponente
                if (c == '-' && i > 0 && termino.charAt(i-1) == '^') continue;
                // CORRECCIÓN: Verificar que NO sea signo inicial de un número
                if (i == 0) continue;
                // Si llegamos aquí, es una suma/resta real
                return true;
            }
        }
        return false;
    }
    
    private static boolean esPolinomicaEspecial(String termino) {
        return termino.contains("x^(") && 
               (termino.contains("sin") || termino.contains("cos") || termino.contains("tan") ||
                termino.contains("exp") || termino.contains("log"));
    }
    
    private static int buscarOperadorFueraParentesis(String expr, char operador) {
        int paren = 0;
        for (int i = 0; i < expr.length(); i++) {
            char c = expr.charAt(i);
            if (c == '(') paren++;
            else if (c == ')') paren--;
            else if (paren == 0 && c == operador) {
                return i;
            }
        }
        return -1;
    }
}