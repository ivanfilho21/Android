package com.example.ivan.calculator;

import android.content.res.Resources;

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
}
