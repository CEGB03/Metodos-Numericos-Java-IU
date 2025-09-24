package com.cegb03.metodos.LocRaices.models;

public class CalculationInput {
    private String funX;
    private String funY;
    private String funGPF;
    private Double a;
    private Double b;
    private Double xn;
    private Double tolerancia;
    private boolean biseccionSelected;
    private boolean regulaFalsiSelected;
    private boolean newtonRapsonSelected;
    private boolean puntoFijoSelected;
    private boolean secanteSelected;
    
    // Constructor
    public CalculationInput() {}
    
    // Getters y Setters
    public String getFunX() { return funX; }
    public void setFunX(String funX) { this.funX = funX; }
    
    public String getFunY() { return funY; }
    public void setFunY(String funY) { this.funY = funY; }
    
    public String getFunGPF() { return funGPF; }
    public void setFunGPF(String funGPF) { this.funGPF = funGPF; }
    
    public Double getA() { return a; }
    public void setA(Double a) { this.a = a; }
    
    public Double getB() { return b; }
    public void setB(Double b) { this.b = b; }
    
    public Double getXn() { return xn; }
    public void setXn(Double xn) { this.xn = xn; }
    
    public Double getTolerancia() { return tolerancia; }
    public void setTolerancia(Double tolerancia) { this.tolerancia = tolerancia; }
    
    public boolean isBiseccionSelected() { return biseccionSelected; }
    public void setBiseccionSelected(boolean biseccionSelected) { this.biseccionSelected = biseccionSelected; }
    
    public boolean isRegulaFalsiSelected() { return regulaFalsiSelected; }
    public void setRegulaFalsiSelected(boolean regulaFalsiSelected) { this.regulaFalsiSelected = regulaFalsiSelected; }
    
    public boolean isNewtonRapsonSelected() { return newtonRapsonSelected; }
    public void setNewtonRapsonSelected(boolean newtonRapsonSelected) { this.newtonRapsonSelected = newtonRapsonSelected; }
    
    public boolean isPuntoFijoSelected() { return puntoFijoSelected; }
    public void setPuntoFijoSelected(boolean puntoFijoSelected) { this.puntoFijoSelected = puntoFijoSelected; }
    
    public boolean isSecanteSelected() { return secanteSelected; }
    public void setSecanteSelected(boolean secanteSelected) { this.secanteSelected = secanteSelected; }
    
    // Métodos de utilidad
    public boolean hasMethodSelected() {
        return biseccionSelected || regulaFalsiSelected || newtonRapsonSelected || 
               puntoFijoSelected || secanteSelected;
    }
    
    public boolean hasClosedMethodsSelected() {
        return biseccionSelected || regulaFalsiSelected;
    }
    
    public boolean hasOpenMethodsSelected() {
        return newtonRapsonSelected || puntoFijoSelected || secanteSelected;
    }
}