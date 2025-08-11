package com.cegb03.metodos.calculos;

public class Termino {
    String terminoOriginal;
    String signoTerminoOriginal;

    public Termino(String terminoOriginal, String signoTerminoOriginal) {
        this.terminoOriginal = terminoOriginal;
        this.signoTerminoOriginal = signoTerminoOriginal;
    }

    public void mostrarTermino() {
        System.out.println("Término Original: " + signoTerminoOriginal + terminoOriginal);
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
}