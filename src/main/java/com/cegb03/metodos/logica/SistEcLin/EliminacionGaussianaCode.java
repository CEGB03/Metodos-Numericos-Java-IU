package com.cegb03.metodos.logica.SistEcLin;

import com.cegb03.metodos.logica.Pibot;

/**
 *
 * @author cegb03
 */
public class EliminacionGaussianaCode {
    private Double[][] A;
    private Double[] b;
    private Double[] x;
    private int filas = 0;
    private int columnas = 0;
    private double factor = 0;
    private int p = 0;
    private Double swap = (double) 0;
    private String solucion = "";
    private String matriz = "";
    private String retornar = "";

    public EliminacionGaussianaCode(Double[][] A, Double[] b, Double[] x, int filas, int columnas) {
        this.A = A;
        this.b = b;
        this.x = x;
        this.filas = filas;
        this.columnas = columnas;
    }
    public String eliminar(){
        imprimir();
        //Piboteo inicio
        Pibot pibot = new Pibot(A, b, filas);
        pibot.pibotear();
        imprimir();
        pibot.triangulacionConPivot();
        imprimir();
        A=pibot.getA();b=pibot.getB();
        //Piboteo fin
        imprimir();
        calcularDetMatriz();
        retroSustitucion();
        recuperarMatriz();recuperarSolucion();
        retornar += "\n"+matriz+"\n"+solucion;
        return retornar;
    }
    
    private void retroSustitucion(){
        // Retro-sustitución  EMA
        for (int i = filas - 1; i >= 0; i--) {
            double suma = b[i];
            imprimirSolucion();
            for (int j = i + 1; j < filas; j++) {
                suma -= A[i][j] * x[j];
            }
            //imprimirSolucion();
            x[i] = suma / A[i][i];
        }
    }
    
    private void calcularDetMatriz(){
        int det = 1;
        for (int ii = 0; ii < filas; ii++)
            det *= A[ii][ii];
        if (det == 0) {
            System.err.println("Matriz singular, no se puede resolver.\n");
            System.exit(30);
        }
        retornar = ("det(A)= " + det);
    }

    public void imprimir() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++)
                System.out.print(A[i][j] + " ");
            System.out.println("| " + b[i]);
        }
        System.out.println();
    }

    public void imprimirSolucion() {
        // Imprimir la solución x
        System.out.println("Solucion x:");
        for (int ii = 0; ii < filas; ii++) {
            System.out.println("x[" + (ii + 1) + "] = " + x[ii]);
        }
        System.out.println();
    }
    private void recuperarMatriz(){
        matriz="";
        matriz = ("Matriz A|b:\n");
        for (int ii = 0; ii < filas; ii++) {
             for (int jj = 0; jj < columnas; jj++)
                 matriz += (A[ ii ][ jj ] + " ");
             matriz += ("| " + b[ ii ] + "\n" );
        }
        matriz += "\n";
    }
    private void recuperarSolucion() {
        // Imprimir la solución x
        solucion = "";
        solucion += ("Solucion x:\n");
        for (int ii = 0; ii < filas; ii++) {
            solucion += ("x[" + (ii + 1) + "] = " + x[ii] + "\n" );
        }
        solucion += "\n";
    }

    public Double[][] getA() {
        return A;
    }

    public Double[] getB() {
        return b;
    }

    public Double[] getX() {
        return x;
    }
    
}
