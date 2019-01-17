package com.example.ivan.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Operation operation;
    private double operand1, operand2, digitMemory;
    private double resultMemory;

    private TextView operationLabel, resultLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
    }

    private void initialize() {
        operation = Operation.None;
        digitMemory = 0;
        resultMemory = 0;

        operand1 = Double.NaN;
        operand2 = Double.NaN;
    }

    @Override
    public void onClick(View v) {
        Button b = (Button) v;
        String butText = b.getText().toString();

        String[] numbers = {"0", "1", "2", "3", "4", "5", "6", "7", "8"}; //, getString(R.string.plus), getString(R.string.minus), getString(R.string.times), getString(R.string.obelus)};
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
            //assignNumber(butText);
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
        else if (butText.equalsIgnoreCase(getString(R.string.result_button)))
            clear();
    }

    private void clear() {
        initialize();
        operationLabel.setText("0");
    }
}
