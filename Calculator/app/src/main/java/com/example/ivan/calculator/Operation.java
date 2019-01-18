package com.example.ivan.calculator;

public enum Operation {
    None(""),
    Addition("+"),
    Subtraction("−"),
    Multiplication("×"),
    Division("÷");

    String value;

    Operation(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Operation getOperation(String value) {
        if (value.equalsIgnoreCase(Addition.value)) return Addition;
        if (value.equalsIgnoreCase(Subtraction.value)) return Subtraction;
        if (value.equalsIgnoreCase(Multiplication.value)) return Multiplication;
        if (value.equalsIgnoreCase(Division.value)) return Division;
        return None;
    }
}
