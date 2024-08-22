package com.cegb03.metodos.logica;

/**
 *
 * @author cegb03
 */
public class Pibot {
    private int filas=0;
    private Double[][] A;
    private Double[]b;

    double factor = 0;
    int p = 0;

    Double swap = (double) 0;
    public Pibot(Double[][] matrizA, Double[] matrizB, int cantFilas) {
        this.A=matrizA;
        this.b=matrizB;
        this.filas=cantFilas;
    }
    public void pibotear(){
        double errorMinimo = 1e-5; //ACORDARSE DE CAMBIARLO!!!
        // Pivoteo parcial
        for (int jj = 0; jj < filas; jj++) {
            // Encontrar el elemento máximo en la columna actual
            p = jj;
            if (Math.abs(A[jj][jj])<errorMinimo){
                for (int ll = jj + 1; ll < filas; ll++) {
                    if (Math.abs(A[ll][jj]) > Math.abs(A[jj][jj]))
                        p = ll;
                }
                // Intercambiar filas
                if (p != jj) {
                    for (int mm = 1; mm < filas-1; mm++) {
                        swap = A[p][mm];
                        A[p][mm] = A[jj][mm];
                        A[jj][mm] = swap;
                    }
                    swap = b[p];
                    b[p] = b[jj];
                    b[jj] = swap;
                }
            }
        }
    }
    public void triangulacionConPivot(){
        double f;
        Double aux;
        double errorMinimo = 1e-5; //ACORDARSE DE CAMBIARLO!!!
        int n = filas;
        for(int i=0; i < n-1; i++) {
            //pivoteo
            int cambio = 0;
            if (Math.abs(A[i][i]) < errorMinimo) {
                for (int j = i + 1; j <= n - 1; j++) {
                    if (Math.abs(A[j][i]) > errorMinimo) //Error parecido a 0.
                    {
                        for (int k = i; k <= n - 1; k++) {
                            aux = A[i][k];
                            A[i][k] = A[j][k];
                            A[j][k] = aux;
                        }
                        aux = b[i];
                        b[i] = b[j];
                        b[j] = aux;
                        cambio = 1;
                        break;
                    }
                }
                if (cambio == 0) {
                    System.err.println("El Sistema es singular! no se puede resolver");
                } else
                    System.out.println("Se hizo pivoteo ! ");
            }
            for(int j=i+1; j <= n-1; j++)
            {
                f=(-A[j][i])/(A[i][i]);
                for (int k=i; k <= n-1; k++) //k arranca desde la diagonal principal.. k=i.
                    A[j][k]=A[j][k]+f*A[i][k];
                b[j]=b[j]+f*b[i];
            }
        }
    }
    /*
    public void pivoteo_GPT(){
        for (int i = 0; i < filas - 1; i++) {
            int p = i;
            // Encontrar el elemento máximo en la columna actual
            for (int j = i + 1; j < filas; j++) {
                if (Math.abs(A[j][i]) > Math.abs(A[p][i])) {
                    p = j;
                }
            }

            // Intercambiar filas
            Double[] tempRow = A[i];
            A[i] = A[p];
            A[p] = tempRow;

            double tempB = b[i];
            b[i] = b[p];
            b[p] = tempB;

            //triangulacion_GPT(i);
        }
    }
    public void triangulacion_GPT(int i){
        for (int j = i + 1; j < filas; j++) {
            double factor = A[j][i] / A[i][i];
            for (int k = i; k < filas; k++) {
                A[j][k] -= factor * A[i][k];
            }
            b[j] -= factor * b[i];
        }

    }
*/
    // Función para imprimir una matriz
    public static void imprimirMatriz(Double[][] matriz) {
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                System.out.print(matriz[i][j] + " ");
            }
            System.out.println();
        }
    }

    // Función para imprimir un vector
    public static void imprimirVector(Double[] vector) {
        for (int i = 0; i < vector.length; i++) {
            System.out.println("b[" + i + "] = " + vector[i]);
        }
    }

    public Double getSwap() {
        return swap;
    }
    public int getP() {
        return p;
    }
    public double getFactor() {
        return factor;
    }
    public Double[][] getA() {
        return A;
    }
    public Double[] getB() {
        return b;
    }
}
