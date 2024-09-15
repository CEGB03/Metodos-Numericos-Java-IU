package com.cegb03.metodos.logica.Interpolaciones;

import com.cegb03.metodos.logica.Pibot;

/**
 *
 * @author cegb03
 * 
 * Clase que realiza la interpolación polinómica utilizando una matriz de datos.
 */
public class InterpolacionPolinomialCode {
    // Matriz que contiene los valores de x e y para realizar la interpolación
    private Double[][] m;
    // Vectores para almacenar los valores de x e y separados de la matriz original
    private Double[] x;
    private Double[] y;
    // Número de filas (puntos de datos)
    private int filas;
    private String solucion = "";
    private String matriz = "";
    private String retornar = "";

    /**
     * Constructor de la clase Polinomial.
     * Inicializa la matriz de entrada y el número de filas para la interpolación.
     *
     * @param A Matriz de datos de entrada.
     * @param filas Número de filas en la matriz (puntos de datos).
     */
    public InterpolacionPolinomialCode(Double[][] A, int filas) {
        this.filas = filas;
        this.m = A;  // Asigna la matriz proporcionada
    }

    /**
     * Método que realiza la interpolación polinómica.
     * Crea el sistema de ecuaciones para ajustar los puntos con un polinomio y luego lo resuelve.
     *
     */
    public String interpolar() {
        System.out.println("\n\n***Ha seleccionado método de Polinomial***\n\n");

        // Matriz A para el sistema de ecuaciones (se llena con potencias de x)
        Double[][] matrizA = new Double[filas][filas];
        // Vector B para el sistema de ecuaciones (se llena con los valores de y)
        Double[] matrizB = new Double[filas];

        // Llenado de las matrices A y B
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < filas ; j++) {
                // Cada elemento de la matriz A se calcula como x^j
                matrizA[i][j] = Math.pow(m[i][0], j);
            }
            // La matriz B se llena con los valores de y
            matrizB[i] = m[i][1];
        }

        // Instancia de la clase Pibot para realizar la eliminación con pivoteo
        Pibot pibot = new Pibot(matrizA, matrizB, filas);

        // Imprimir las matrices A y B antes y después de la eliminación
        solucion += Pibot.imprimirMatriz(matrizA);
        solucion += Pibot.imprimirVector(matrizB);
        pibot.triangulacionConPivot();  // Realiza la eliminación con pivoteo
        solucion += Pibot.imprimirMatriz(matrizA);  // Imprime la matriz A después de la eliminación
        solucion += Pibot.imprimirVector(matrizB);  // Imprime el vector B después de la eliminación
        retornar += "\n" + solucion;
        return retornar;
    }

    /**
     * Método que separa los valores de x e y de la matriz original y los almacena en vectores.
     */
    private void separarXY() {
        // Inicializar los vectores de x e y
        x = new Double[filas];
        y = new Double[filas];

        // Llenar los vectores con los valores de la matriz
        for (int i = 0; i < filas; i++) {
            x[i] = m[i][0];
            y[i] = m[i][1];
        }

        // Imprimir la tabla de valores de x e y
        imprimirTabla();
    }

    /**
     * Método que imprime los valores de x e y almacenados en los vectores.
     */
    private void imprimirTabla() {
        for (int i = 0; i < filas; i++) {
            System.out.println(x[i] + ";" + y[i]);
        }
    }

    /**
     * Método que construye y muestra el polinomio interpolador.
     * El polinomio se genera simbólicamente a partir de los valores de x e y.
     */
    private void imprimirPolinomioCnk() {
        StringBuilder polinomio = new StringBuilder();

        // Construir el polinomio iterando sobre los términos de x e y
        for (int i = 0; i < filas; i++) {
            StringBuilder termino = new StringBuilder();
            termino.append("\n").append(y[i]);

            for (int j = 0; j < filas; j++) {
                if (j != i) {
                    // Añadir el término correspondiente al polinomio
                    termino.append(" * (x - ").append(x[j]).append(") / (").append(x[i]).append(" - ").append(x[j]).append(")");
                }
            }

            // Añadir el signo más o menos entre términos
            if (i > 0 && y[i] >= 0) {
                polinomio.append(" + ");
            } else if (y[i] < 0) {
                polinomio.append(" - ");
                termino.deleteCharAt(0);  // Eliminar el signo negativo de y[i] si es negativo
            }

            polinomio.append(termino);
        }

        // Mostrar el polinomio resultante
        System.out.println("El polinomio interpolador es: ");
        System.out.println(polinomio.toString());
    }
}