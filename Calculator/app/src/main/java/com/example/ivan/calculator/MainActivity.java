package com.example.ivan.calculator;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Operation operation;
    private int currentOperandIndex;
    private double operand1, operand2, resultMemory;
    private TextView operand1Label, operand2Label, operatorLabel, resultLabel;
    private Drawable default_background;

    DecimalFormat decimalFormat = new DecimalFormat("#.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        operand1Label = findViewById(R.id.operand1);
        operand2Label = findViewById(R.id.operand2);
        operatorLabel = findViewById(R.id.operatorLabel);
        resultLabel = findViewById(R.id.resultLabel);

        default_background = operand2Label.getBackground();

        initialize();
    }

    private void initialize() {
        resetOperandIndex();
        operation = Operation.None;
        resultMemory = 0;

        operand1 = 0;
        operand2 = Double.NaN;

        String s = convertToFormat(operand1);
        operand1Label.setText(s);

        if (Double.isNaN(operand2)) operand2Label.setText(null);
        else operand2Label.setText(s);

        resultLabel.setText(null);

        operatorLabel.setText(operation.value);
    }

    @Override
    public void onClick(View v) {
        Button b = (Button) v;
        String butText = b.getText().toString();

        String[] numbers = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"}; //, getString(R.string.plus), getString(R.string.minus), getString(R.string.times), getString(R.string.obelus)};
        String[] operands = {getString(R.string.plus), getString(R.string.minus), getString(R.string.times), getString(R.string.obelus)};

        int index = -1;
        for (int i = 0; i < numbers.length; i++) {
            if (butText.equalsIgnoreCase(numbers[i])) {
                index = i;
                break;
            }
        }

        if (index != -1) {
            assignNumber(butText);
            return;
        }

        for (int i = 0; i < operands.length; i++) {
            if (butText.equalsIgnoreCase(operands[i])) {
                index = i;
                break;
            }
        }

        if (index != -1) {
            //assignOperation(butText);
            return;
        }

        if (butText.equalsIgnoreCase(getString(R.string.clear_button)))
            clear();
        else if (butText.equalsIgnoreCase(getString(R.string.clear_entry_button)))
            clearEntry();
        else if (butText.equalsIgnoreCase(getString(R.string.del_button)))
            clear();
        else if (butText.equalsIgnoreCase(getString(R.string.result_button)))
            toggleOperandIndex(); // Testing this...
    }

    private String convertToFormat(double value) {
        return decimalFormat.format(value);
    }

    private void clear() {
        initialize();
    }

    private void clearEntry() {
        setCurrentOperand(0, false);
    }

    private void changeOperandIndex(int i) {
        Drawable background = getResources().getDrawable( android.R.drawable.divider_horizontal_textfield);

        currentOperandIndex = i;

        if (currentOperandIndex == 0) {
            operand1Label.setBackgroundDrawable( background );
            operand2Label.setBackgroundDrawable( default_background );

        }
        else{
            operand1Label.setBackgroundDrawable( default_background );
            operand2Label.setBackgroundDrawable( background );
        }

    }

    private void resetOperandIndex() {
        changeOperandIndex(0);
    }

    private void toggleOperandIndex() {
        if (currentOperandIndex == 0) changeOperandIndex(1);
        else changeOperandIndex(0);
    }

    private void setCurrentOperand(double n, boolean append) {
        String text = "";

        if (currentOperandIndex == 0) {
            if (append)
                text = operand1Label.getText().toString();
            text += convertToFormat(n);

            operand1Label.setText(text);
            operand1 = Double.parseDouble(operand1Label.getText().toString());
        }
        else {
            if (append)
                text = operand2Label.getText().toString();
            text += convertToFormat(n);

            operand2Label.setText(text);
            operand2 = Double.parseDouble(operand2Label.getText().toString());
        }
    }

    private double getCurrentOperand() {
        if (currentOperandIndex == 0)
            return operand1;
        else
            return operand2;
    }

    private void assignNumber(String buttonText) {
        boolean append = Double.compare(getCurrentOperand(), 0) != 0;
        setCurrentOperand(Double.parseDouble(buttonText), append);
    }
}
