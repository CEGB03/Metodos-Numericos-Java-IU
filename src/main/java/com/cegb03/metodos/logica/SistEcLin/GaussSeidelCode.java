package com.cegb03.metodos.logica.SistEcLin;

import java.text.DecimalFormat;

/**
 *
 * @author cegb03
 */
public class GaussSeidelCode {
    
    private Double[][] a;
    private final Double[] b;
    private Double xViejo[];
    private Double xNuevo[];
    private int filas = 0;
    private final Double tolerancia;
    private Double error = 0.0;
    private int iteraciones = 0;
    private Double factorRelajacion = 0.0;
    private String calculos = "";

    public GaussSeidelCode(Double[][] A, Double[] b, int filas, Double tolerancia) {
        this.a = A;
        this.b = b;
        this.filas = filas;
        this.tolerancia = tolerancia;
    }
    public String sinRelajacion(){
        boolean check = diagonalmenteDominante(a,filas, calculos);
        if(!check)
            return calculos + "\nMatriz no diagonalmente dominante.";

        Double suma = 0.0;
        xNuevo = new Double[filas];
        xViejo = new Double[filas];

        for (int i = 0; i < filas; i++) {
            xViejo[i] = 0.0;
            xNuevo[i] = xViejo[i];
        }

        int iteraciones = 0;

        do {
            iteraciones++;
            for (int i = 0; i < filas; i++) {
                suma = 0.0;
                if(i == 0){
                    for(int j = 1 ; j < filas ; j++){
                        suma+= a[i][j] * xNuevo[j];
                    }
                    xNuevo[i] = (b[i] - suma)/a[i][i];
                }else{
                    for(int j = 0 ; j < i  ; j++){
                        suma += a[i][j] * xNuevo[j];
                    }

                    for(int j = i+1 ; j < filas ; j++){
                        suma+= a[i][j] * xViejo[j];
                    }

                    xNuevo[i] = (b[i] - suma)/a[i][i];
                }
            }

            // Manejo del error
            suma = 0.0;
            for (int i = 0; i < filas; i++) {
                suma += (xNuevo[i] - xViejo[i]) * (xNuevo[i] - xViejo[i]);
            }
            error = Math.sqrt(suma);

            // Reasignación del vector viejo para la próxima pasada.
            System.arraycopy(xNuevo, 0, xViejo, 0, filas);
        } while (error > tolerancia && iteraciones < 10000);

        calculos += ("\n El resultado es: \nxnuevo = [\t");
        for (int i = 0; i < filas; i++) {
            calculos += (xNuevo[i]+",    ");
        }

        DecimalFormat df = new DecimalFormat("0.00000000000000000000"); // Define el formato con 20 decimales
//        DecimalFormat df = new DecimalFormat("0.00000"); // Define el formato con 20 decimales
        String formattedError = df.format(error);// Calcular la cantidad de decimales según la tolerancia
        calculos += ("]\n La cantidad de iteraciones fueron:"+iteraciones+"\n El error es de "+formattedError+".");
        
        return calculos;
    }
    public String conRelajacion(Double relajacion){
        this.factorRelajacion = relajacion;
        
        System.out.println("tolerancia " + tolerancia);
        boolean check = diagonalmenteDominante(a,filas, calculos);
        if(!check)
            return calculos + "\nMatriz no diagonalmente dominante.";

        Double suma = 0.0;
        xNuevo = new Double[filas];
        xViejo = new Double[filas];

        for (int i = 0; i < filas; i++) {
            xViejo[i] = 0.0;
            xNuevo[i] = xViejo[i];
        }

        int iteraciones = 0;

        do {
            iteraciones++;
            for (int i = 0; i < filas; i++) {
                suma = 0.0;
                if(i == 0){
                    for(int j = 1 ; j < filas ; j++){
                        suma+= a[i][j] * xNuevo[j];
                    }
                    xNuevo[i] = (b[i] - suma)/a[i][i];
                    xNuevo[i] = factorRelajacion * xNuevo[i] + (1-factorRelajacion) * xViejo[i];
                }else{
                    for(int j = 0 ; j < i  ; j++){
                        suma += a[i][j] * xNuevo[j];
                    }

                    for(int j = i+1 ; j < filas ; j++){
                        suma+= a[i][j] * xViejo[j];
                    }

                    xNuevo[i] = (b[i] - suma)/a[i][i];
                    xNuevo[i] = factorRelajacion * xNuevo[i] + (1-factorRelajacion) * xViejo[i];
                }
            }

            // Manejo del error
            suma = 0.0;
            for (int i = 0; i < filas; i++) {
                suma += (xNuevo[i] - xViejo[i]) * (xNuevo[i] - xViejo[i]);
            }
            error = Math.sqrt(suma);

            // Reasignación del vector viejo para la próxima pasada.
            System.arraycopy(xNuevo, 0, xViejo, 0, filas);
        } while (error > tolerancia && iteraciones < 10000);

        calculos += ("\n El resultado es: \nxnuevo = [  ");
        for (int i = 0; i < filas; i++) {
            calculos += (xNuevo[i]+",    ");
        }

        DecimalFormat df = new DecimalFormat("0.00000000000000000000"); // Define el formato con 20 decimales
//        DecimalFormat df = new DecimalFormat("0.00000"); // Define el formato con 20 decimales
        String formattedError = df.format(error);// Calcular la cantidad de decimales según la tolerancia
        calculos += ("]\n La cantidad de iteraciones fueron:"+iteraciones+"\n El error es de "+formattedError+".");
        
        return calculos;
    }
    boolean diagonalmenteDominante(Double[][] a, int filas, String calculos){
        double suma = 0;
        int counter = 0;
        for(int i = 0; i < filas ; i++){
            suma = 0;
            counter++;
            for(int j = 0 ; j < filas ; j++){
                if(j!=i){
                    suma+= Math.abs(a[i][j]);
                }
            }

            if(Math.abs(a[i][i]) < suma)
                calculos += ("\nLa matriz no es diagonalmente dominante. Fila: "+counter+".");

            if(a[i][i] == 0){
                calculos += ("\nCeros en la diagonal");
                return false;
            }
        }
        return true;
    }
}
