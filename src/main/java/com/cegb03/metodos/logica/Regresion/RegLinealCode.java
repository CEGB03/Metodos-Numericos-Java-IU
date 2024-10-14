package com.cegb03.metodos.logica.Regresion;

import com.cegb03.metodos.logica.SistEcLin.EliminacionGaussianaCode;

/**
 *
 * @author cegb03
 * 
 * La clase RegresionLineal realiza la regresión lineal sobre un conjunto de datos.
 * Calcula la línea de mejor ajuste y proporciona métricas de ajuste como el error y el coeficiente de correlación.
 * 
 */
public class RegLinealCode {
    Double[][] matrizA; // Matriz que contiene los datos de entrada (x, y)
    Double[] x; // Soluciones de la regresión
    int filas = 0, columnas = 0; // Número de filas y columnas en la matriz
    private String solucion = "";
    private String matriz = "";
    private String retornar = "";

    /**
     * Constructor que inicializa la regresión lineal.
     *
     * @param A Matriz de entrada con datos (x, y)
     * @param filas Número de filas en la matriz
     * @param columnas Número de columnas en la matriz
     */
    public RegLinealCode(Double[][] A, int filas, int columnas) {
        this.filas = filas;
        this.columnas = columnas + 1; // La matriz se amplía en 1 columna para los resultados
        recortarMatriz(A); // Ajusta la matriz para el cálculo
        imprimir(); // Imprime la matriz ajustada
    }

    /**
     * Recorta la matriz de entrada para ajustarla al tamaño requerido.
     *
     * @param A Matriz original de entrada
     */
    private void recortarMatriz(Double[][] A){
        matrizA = new Double[filas][columnas];
        for (int i = 0; i < filas; i++) {
            System.arraycopy(A[i], 0, matrizA[i], 0, columnas);
        }
    }

    /**
     * Imprime la matriz ajustada.
     */
    private void imprimir() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++)
                System.out.print(matrizA[i][j] + " | ");
            System.out.println();
        }
        System.out.println();
    }

    /**
     * Realiza la regresión lineal calculando los parámetros de la recta de mejor ajuste.
     */
    public String regresion(){
        Double[][] a = new Double[columnas][columnas]; // Matriz para los coeficientes de la regresión
        Double[] b = new Double[columnas]; // Vector para los términos independientes
        for (int i = 0; i < columnas; i++) {
            for (int j = 0; j < columnas; j++) {
                a[i][j] = 0.0; // Inicializar la matriz a
            }
            b[i] = 0.0; // Inicializar el vector b
        }
        double yProm = 0, r = 0, Sr = 0, St = 0, sum = 0; // Variables para cálculos de error y ajuste
        a[1][1] = (double) filas; // Inicializa a[1][1] con el número de observaciones
        for(int i = 0 ; i < columnas ; i++){

            // Calcula las sumas requeridas para construir la matriz de coeficientes
            a[0][0] += Math.pow(matrizA[i][0], 2); // Suma de los cuadrados de las x.
            a[0][1] += matrizA[i][0];              // Suma de las x.
            a[1][0] += matrizA[i][0];              // Suma de las x.
            b[0] += matrizA[i][0] * matrizA[i][1]; // Suma de x[i] * y[i].
            b[1] += matrizA[i][1];                 // Suma de las y.

        }

        // Imprime la matriz de coeficientes y el vector de términos independientes
        System.out.println("Matriz:");
        matriz += "Matriz:\n";
        for(int i = 0 ; i < 2 ; i++){
            for(int j = 0 ; j < 2 ; j++){
                System.out.print("a["+i+"]["+j+"] = " + a[i][j] + " ");
                matriz +=("a["+i+"]["+j+"] = " + a[i][j] + " ");
            }
            System.out.print("| b["+i+"] = " + "| " + b[i] + "\n");
                matriz +=("| b["+i+"] = " + "| " + b[i] + "\n");
        }

        // Crea una instancia de EliminacionGaussiana para resolver el sistema
        x = new Double[2];
        EliminacionGaussianaCode gaussiana = new EliminacionGaussianaCode(a, b, x, a.length, a.length);
        retornar += gaussiana.eliminar(); // Ejecuta la eliminación gaussiana

        // Imprime las soluciones de la regresión
        System.out.println("Conjunto solución:");
        solucion += ("Conjunto solución:\n");
        for (int i = 0; i < 2; ++i) {
            System.out.print("X" + i + " = "+x[i]+"\n");
            solucion +=("X" + i + " = "+x[i]+"\n");
        }

        // Calcula y muestra el error de la regresión
        for(int i = 0; i < filas ; i++){
            yProm+= matrizA[i][1];
        }
        yProm = yProm/filas; // Promedio de y
        for(int i = 0; i < filas ; i++){
            St+= Math.pow((matrizA[i][1] - yProm) , 2); // Suma de cuadrados de las diferencias con el promedio

        }

        for(int i = 0; i<filas ; i++){
            sum+= Math.pow((matrizA[i][1] - (x[0]*matrizA[i][0]+x[1])) , 2); // Suma de cuadrados de las diferencias con la recta de ajuste
        }

        Sr = Math.sqrt(sum/filas); // Error estándar de la regresión
        System.out.println("\nSr = " + Sr);

        r = (St - sum)/St; // Coeficiente de determinación (r cuadrado)
        System.out.println("\nr = " + r);

        // Calcula y muestra el error cuadrático medio y otros métricas de ajuste
        error(x, a, 1, a.length);
        
        return "\n"+matriz+"\n"+retornar+"\n"+solucion;
    }

    /**
     * Calcula el error cuadrático medio y otras métricas de ajuste.
     *
     * @param x Soluciones de la regresión
     * @param m Matriz de datos
     * @param gradoP Grado del polinomio (en este caso, lineal)
     * @param filas Número de filas en la matriz
     */
    private void error(Double[] x, Double[][] m, int gradoP, int filas){
        double yb = 0;
        double e = 0;
        double ecm;
        double st = 0;
        double r;
        for (int i = 0; i < filas; i++) {
            double sum = 0;
            for (int j = 0; j <= gradoP; j++) {
                double aux2 = Math.pow(m[i][0], j);
                double aux = x[j] * aux2;
                sum = sum + aux; // Calcula la estimación para la i-ésima observación
            }
            e = e + Math.pow(m[i][1] - sum, 2); // Error cuadrático
            yb = yb + m[i][1]; // Suma de los valores observados
        }
        yb = yb / (filas + 1); // Promedio de los valores observados
        for (int i = 0; i < filas; i++) {
            st = st + Math.pow(m[i][1] - yb, 2); // Suma de cuadrados totales
        }
        r = Math.sqrt(Math.abs(e - st) / st); // Coeficiente de determinación
        ecm = Math.sqrt(e / filas); // Error cuadrático medio
        solucion += ("\nEl error es de(e): "+e);
        System.out.println("\nEl error es de(e): "+e);
        solucion += ("\nEl error cuadrático medio es de(ecm): "+ecm);
        System.out.println("El error cuadrático medio es de(ecm): "+ecm);
        solucion += ("\nst: %"+ st);
        System.out.println("st: %"+ st);
        solucion +=("\nEl coeficiente de correlación es(r): "+r);
        System.out.println("El coeficiente de correlación es(r): "+r);
    }
}
