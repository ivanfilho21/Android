package com.example.ivan.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Operation operation;
    private double digitMemory;
    private double resultMemory;

    private TextView operationLabel, resultLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        operationLabel = findViewById(R.id.operationLabel);
        resultLabel = findViewById(R.id.resultLabel);

        initialize();
    }

    private void initialize() {
        operation = Operation.None;
        digitMemory = 0;
        resultMemory = 0;
    }

    @Override
    public void onClick(View v) {
        Button b = (Button) v;
        String butText = b.getText().toString();

        String[] numbers = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        String[] operands = {getString(R.string.plus), getString(R.string.minus), getString(R.string.times), getString(R.string.obelus)};

        int index = -1;
        for (int i = 0; i < numbers.length; i++) {
            if (butText.equalsIgnoreCase(numbers[i])) {
                index = i;
                break;
            }
        }

        // found it, print text or append.
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
            assignOperation(butText);
            return;
        }

        if (butText.equalsIgnoreCase(getString(R.string.clear_button)))
            clear();
    }

    private void assignNumber(String number) {
        String s = operationLabel.getText().toString();
        if (s.isEmpty() || s.equalsIgnoreCase("0")) {
            s = "" + number;
        }
        else {
            s += "" + number;
        }
        operationLabel.setText(s);
    }

    private void assignOperation(String operation) {
        final String plus = getString(R.string.plus);
        final String minus = getString(R.string.minus);
        final String times = getString(R.string.times);
        final String obelus = getString(R.string.obelus);

        if (operation.equalsIgnoreCase(plus))
            this.operation = Operation.Addition;
        else if (operation.equalsIgnoreCase(minus))
            this.operation = Operation.Subtraction;
        else if (operation.equalsIgnoreCase(times))
            this.operation = Operation.Multiplication;
        else if (operation.equalsIgnoreCase(obelus))
            this.operation = Operation.Division;

        String s = operationLabel.getText() + this.operation.getValue();
        operationLabel.setText(s);
    }

    private void doOperation() {

        String s = operationLabel.getText().toString();
        String[] a = s.split(operation.getValue());

        for (int i = 0; i < a.length; i++) {
            // Todo: calculate all operands
        }
    }

    private void clear() {
        initialize();
        operationLabel.setText("0");
    }
}
