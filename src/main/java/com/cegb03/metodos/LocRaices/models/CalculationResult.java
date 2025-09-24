package com.cegb03.metodos.LocRaices.models;

public class CalculationResult {
    private boolean success;
    private String results;
    private String errorMessage;
    private String derivative;
    private String generatedFunction;
    private String generationInfo; // NUEVO CAMPO

    public CalculationResult() {
        this.success = false;
    }

    // Getters y setters existentes
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getResults() {
        return results;
    }

    public void setResults(String results) {
        this.results = results;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getDerivative() {
        return derivative;
    }

    public void setDerivative(String derivative) {
        this.derivative = derivative;
    }

    public String getGeneratedFunction() {
        return generatedFunction;
    }

    public void setGeneratedFunction(String generatedFunction) {
        this.generatedFunction = generatedFunction;
    }

    // NUEVO GETTER Y SETTER
    public String getGenerationInfo() {
        return generationInfo;
    }

    public void setGenerationInfo(String generationInfo) {
        this.generationInfo = generationInfo;
    }
}