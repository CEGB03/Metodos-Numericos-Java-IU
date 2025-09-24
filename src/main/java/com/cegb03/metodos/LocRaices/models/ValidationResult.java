package com.cegb03.metodos.LocRaices.models;

public class ValidationResult {
    private boolean valid;
    private String errorMessage;
    
    public ValidationResult() { this.valid = false; }
    
    public boolean isValid() { return valid; }
    public void setValid(boolean valid) { this.valid = valid; }
    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
}