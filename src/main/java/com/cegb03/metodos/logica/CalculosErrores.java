package com.cegb03.metodos.logica;

import java.text.DecimalFormat;

/**
 *
 * @author cegb03
 */
public class CalculosErrores {
    private DecimalFormat df = new DecimalFormat("0.00000");// Define el formato con 5 decimales

    //Distintos errores---------------------------
    //System.out.println("----------Errores Estimado----------\n");
    
    public void errorComunEstimado(Double error, Double c){
        System.out.println("~~~Error comun~~~");
        String formattedError = df.format(error);// Calcular la cantidad de decimales según la tolerancia
        System.out.println("La raiz es aproximadamente:("+c+"±"+formattedError+")\n");//Este error se calcula en el loop
    }
    public void errorPorcentualEstimado(Double a, Double b, Double c ){
        System.out.println("~~~Error Porcentual~~~");
        double errorPorcentual = ((b-a)/2)*(1/c)*100;
        System.out.println("El error porcentual es de:"+errorPorcentual+"%\n");
    }
    //Exactos~~~~~
    //System.out.println("----------Errores exactos----------\n");
    public void errorComunExacto(Double c, Double raizReal){
        System.out.println("~~~Error comun~~~");
        double errorAbsolutoExacto = Math.abs((c-raizReal));
        String formattedErrorAbsolutoExacto = df.format(errorAbsolutoExacto);
        System.out.println("La raiz es aproximadamente:("+c+"±"+formattedErrorAbsolutoExacto+")\n");
    }
    public void errorPorcentualExacto(Double c, Double raizReal){
        System.out.println("~~~Error Porcentual~~~");
        double errorPorcentualAbsolutoExacto = Math.abs((raizReal-c)/raizReal)*100;
        System.out.println("El error porcentual es de:"+errorPorcentualAbsolutoExacto+"%\n");
    }
    //--------------------------------------------

}
