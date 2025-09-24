package com.cegb03.metodos.LocRaices.validators;

import com.cegb03.metodos.LocRaices.models.CalculationInput;
import com.cegb03.metodos.LocRaices.models.ValidationResult;

public class InputValidator {
    
    public ValidationResult validateInput(CalculationInput input) {
        ValidationResult result = new ValidationResult();
        
        if (!input.hasMethodSelected()) {
            result.setErrorMessage("Debe seleccionar al menos un método para calcular.");
            return result;
        }
        
        if (input.hasClosedMethodsSelected()) {
            ValidationResult closedValidation = validateClosedMethods(input);
            if (!closedValidation.isValid()) return closedValidation;
        }
        
        if (input.hasOpenMethodsSelected()) {
            ValidationResult openValidation = validateOpenMethods(input);
            if (!openValidation.isValid()) return openValidation;
        }
        
        result.setValid(true);
        return result;
    }
    
    private ValidationResult validateClosedMethods(CalculationInput input) {
        ValidationResult result = new ValidationResult();
        
        if (input.getFunX() == null || input.getFunX().trim().isEmpty()) {
            result.setErrorMessage("La función F(x) es requerida para métodos cerrados.");
            return result;
        }
        
        // Validar que 'a' sea un número válido
        if (input.getA() == null) {
            result.setErrorMessage("El valor del límite inferior 'a' es requerido.");
            return result;
        }
        
        // Validar que 'b' sea un número válido
        if (input.getB() == null) {
            result.setErrorMessage("El valor del límite superior 'b' es requerido.");
            return result;
        }
        
        // Validar que la tolerancia sea un número válido y positivo
        if (input.getTolerancia() == null || input.getTolerancia() <= 0) {
            result.setErrorMessage("La tolerancia debe ser un valor numérico positivo.");
            return result;
        }
        
        if (input.getA() >= input.getB()) {
            result.setErrorMessage("El límite inferior 'a' debe ser menor que 'b'.");
            return result;
        }
        
        result.setValid(true);
        return result;
    }
    
    private ValidationResult validateOpenMethods(CalculationInput input) {
        ValidationResult result = new ValidationResult();
        
        if (input.getFunX() == null || input.getFunX().trim().isEmpty()) {
            result.setErrorMessage("La función F(x) es requerida para métodos abiertos.");
            return result;
        }
        
        // Validar que Xn sea un número válido
        if (input.getXn() == null) {
            result.setErrorMessage("El valor inicial X₀ es requerido para métodos abiertos.");
            return result;
        }
        
        // Validar que la tolerancia sea un número válido y positivo
        if (input.getTolerancia() == null || input.getTolerancia() <= 0) {
            result.setErrorMessage("La tolerancia debe ser un valor numérico positivo.");
            return result;
        }
        
        result.setValid(true);
        return result;
    }
    
    /**
     * Valida que una cadena represente un número válido (entero o decimal)
     * @param value Cadena a validar
     * @return true si es un número válido, false en caso contrario
     */
    private boolean isValidNumber(String value) {
        if (value == null || value.trim().isEmpty()) {
            return false;
        }
        
        try {
            Double.parseDouble(value.trim());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    /**
     * Valida campos numéricos antes de la conversión
     * @param aText Texto del campo 'a'
     * @param bText Texto del campo 'b' 
     * @param xnText Texto del campo 'Xn'
     * @param toleranciaText Texto del campo 'tolerancia'
     * @param hasClosedMethods Si hay métodos cerrados seleccionados
     * @param hasOpenMethods Si hay métodos abiertos seleccionados
     * @return ValidationResult con el resultado de la validación
     */
    public ValidationResult validateNumericFields(String aText, String bText, String xnText, 
                                                String toleranciaText, boolean hasClosedMethods, 
                                                boolean hasOpenMethods) {
        ValidationResult result = new ValidationResult();
        
        // Validar campos para métodos cerrados
        if (hasClosedMethods) {
            if (aText == null || aText.trim().isEmpty()) {
                result.setErrorMessage("El campo 'a' no puede estar vacío para métodos cerrados.");
                return result;
            }
            
            if (!isValidNumber(aText)) {
                result.setErrorMessage("El campo 'a' debe contener solo números. Ejemplo: -2, 0, 1.5");
                return result;
            }
            
            if (bText == null || bText.trim().isEmpty()) {
                result.setErrorMessage("El campo 'b' no puede estar vacío para métodos cerrados.");
                return result;
            }
            
            if (!isValidNumber(bText)) {
                result.setErrorMessage("El campo 'b' debe contener solo números. Ejemplo: -1, 2, 3.14");
                return result;
            }
        }
        
        // Validar campos para métodos abiertos
        if (hasOpenMethods) {
            if (xnText == null || xnText.trim().isEmpty()) {
                result.setErrorMessage("El campo 'X₀' no puede estar vacío para métodos abiertos.");
                return result;
            }
            
            if (!isValidNumber(xnText)) {
                result.setErrorMessage("El campo 'X₀' debe contener solo números. Ejemplo: 0, 1.5, -2.8");
                return result;
            }
        }
        
        // Validar tolerancia (común para ambos tipos de métodos)
        if (hasClosedMethods || hasOpenMethods) {
            if (toleranciaText == null || toleranciaText.trim().isEmpty()) {
                result.setErrorMessage("El campo 'Tolerancia' no puede estar vacío.");
                return result;
            }
            
            if (!isValidNumber(toleranciaText)) {
                result.setErrorMessage("El campo 'Tolerancia' debe contener solo números. Ejemplo: 0.001, 0.0001");
                return result;
            }
            
            try {
                double tolerancia = Double.parseDouble(toleranciaText.trim());
                if (tolerancia <= 0) {
                    result.setErrorMessage("La tolerancia debe ser un número positivo mayor que 0.");
                    return result;
                }
            } catch (NumberFormatException e) {
                result.setErrorMessage("La tolerancia debe ser un número válido.");
                return result;
            }
        }
        
        result.setValid(true);
        return result;
    }
}