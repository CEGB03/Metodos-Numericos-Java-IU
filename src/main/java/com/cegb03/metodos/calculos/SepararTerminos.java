package com.cegb03.metodos.calculos;

import java.util.ArrayList;
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
                terminos.add(parseTermino(parte));
                inicio = i;
            }
        }
        
        // Añadimos el último término
        if (inicio < funcion.length()) {
            String parte = funcion.substring(inicio);
            terminos.add(parseTermino(parte));
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
}