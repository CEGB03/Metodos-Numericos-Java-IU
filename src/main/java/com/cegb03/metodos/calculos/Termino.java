package com.cegb03.metodos.calculos;

import java.util.List;
import java.util.ArrayList;

public class Termino {
    private String terminoOriginal;
    private String signoTerminoOriginal;
    private String terminoDerivado;
    private String tipoTermino;
    private List<String> factores;
    private boolean esProducto;
    
    public Termino(String terminoOriginal, String signoTerminoOriginal) {
        this.terminoOriginal = terminoOriginal;
        this.signoTerminoOriginal = signoTerminoOriginal;
        this.terminoDerivado = null;
        this.tipoTermino = null;
        this.factores = new ArrayList<>();
        this.esProducto = false;
    }

    public void mostrarTermino() {
        System.out.println("Término Original: " + signoTerminoOriginal + terminoOriginal);
        if (terminoDerivado != null) {
            System.out.println("Término Derivado: " + terminoDerivado);
        }
        if (tipoTermino != null) {
            System.out.println("Tipo: " + tipoTermino);
        }
    }

    public String getTerminoCompleto() {
        return signoTerminoOriginal + terminoOriginal;
    }

    public String getTerminoOriginal() {
        return terminoOriginal;
    }

    public void setTerminoOriginal(String terminoOriginal) {
        this.terminoOriginal = terminoOriginal;
    }

    public String getSignoTerminoOriginal() {
        return signoTerminoOriginal;
    }

    public void setSignoTerminoOriginal(String signoTerminoOriginal) {
        this.signoTerminoOriginal = signoTerminoOriginal;
    }
    
    public String getTerminoDerivado() {
        return terminoDerivado;
    }
    
    public void setTerminoDerivado(String terminoDerivado) {
        this.terminoDerivado = terminoDerivado;
    }
    
    public String getTipoTermino() {
        return tipoTermino;
    }
    
    public void setTipoTermino(String tipoTermino) {
        this.tipoTermino = tipoTermino;
    }
    
    public List<String> getFactores() {
        return factores;
    }
    
    public void setFactores(List<String> factores) {
        this.factores = factores;
        this.esProducto = factores.size() > 1;
    }
    
    public boolean esProducto() {
        return esProducto;
    }
    
    public void setEsProducto(boolean esProducto) {
        this.esProducto = esProducto;
    }
    
    // Método para obtener la derivada aplicando el signo original
    public String getDerivadaConSigno() {
        if (terminoDerivado == null) return "0";
        
        if ("-".equals(signoTerminoOriginal)) {
            if (terminoDerivado.startsWith("-")) {
                return terminoDerivado.substring(1); // -(-expr) = expr
            } else {
                return "-" + terminoDerivado; // -(expr) = -expr
            }
        }
        
        return terminoDerivado;
    }
}