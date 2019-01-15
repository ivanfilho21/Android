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
            String s = operationLabel.getText().toString();
            if (s.isEmpty() || s.equalsIgnoreCase("0"))
                operationLabel.setText(butText);
            else {
                s += butText;
                operationLabel.setText(s);
            }
            return;
        }

        if (butText.equalsIgnoreCase(getString(R.string.clear_button)))
            operationLabel.setText("0");

    }
}
