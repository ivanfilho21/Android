package com.example.ivan.calculator;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Operation operation;
    private int currentOperandIndex;
    private double operand1, operand2, resultMemory;
    private TextView operand1Label, operand2Label, operatorLabel, resultLabel;
    private Drawable background, default_background;

    DecimalFormat decimalFormat = new DecimalFormat("#.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getTitle());
        }
        //toolbar.setSubtitle("");
        toolbar.inflateMenu(R.menu.menu);*/

        operand1Label = findViewById(R.id.operand1);
        operand2Label = findViewById(R.id.operand2);
        operatorLabel = findViewById(R.id.operatorLabel);
        resultLabel = findViewById(R.id.result);

        operand1Label.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOperandIndex(0);
            }
        });
        operand2Label.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOperandIndex(1);
            }
        });

        background = operand1Label.getBackground();
        default_background = operand2Label.getBackground();

        initialize();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.show_hide_labels:
                if (item.getTitle() == getString(R.string.hide_labels)) {
                    findViewById(R.id.operand1Label).setVisibility(View.GONE);
                    findViewById(R.id.operand2Label).setVisibility(View.GONE);
                    findViewById(R.id.resultLabel).setVisibility(View.GONE);

                    item.setTitle(getString(R.string.show_labels));
                } else {
                    findViewById(R.id.operand1Label).setVisibility(View.VISIBLE);
                    findViewById(R.id.operand2Label).setVisibility(View.VISIBLE);
                    findViewById(R.id.resultLabel).setVisibility(View.VISIBLE);

                    item.setTitle(getString(R.string.hide_labels));
                }
                return true;
            case R.id.about:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initialize() {
        resetOperandIndex();
        resultMemory = 0;

        operand1 = 0;
        operand2 = Double.NaN;

        String s = convertToFormat(operand1);
        operand1Label.setText(s);

        if (Double.isNaN(operand2)) operand2Label.setText(null);
        else operand2Label.setText(s);

        resultLabel.setText(null);

        assignOperation(Operation.None.value);
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
            assignOperation(butText);
            return;
        }

        if (butText.equalsIgnoreCase(getString(R.string.clear_button)))
            clear();
        else if (butText.equalsIgnoreCase(getString(R.string.clear_entry_button)))
            clearEntry();
        else if (butText.equalsIgnoreCase(getString(R.string.del_button)))
            clear();
        else if (butText.equalsIgnoreCase(getString(R.string.result_button)))
            doOperation();
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

    private void setOperandIndex(int i) {
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
        setOperandIndex(0);
    }

    private void toggleOperandIndex() {
        if (currentOperandIndex == 0) setOperandIndex(1);
        else setOperandIndex(0);
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
        return currentOperandIndex == 0 ? operand1 : operand2;
    }

    private void assignNumber(String buttonText) {
        boolean append = Double.compare(getCurrentOperand(), 0) != 0;
        setCurrentOperand(Double.parseDouble(buttonText), append);
    }

    private void assignOperation(String operationText) {
        operation = Operation.getOperation(operationText);
        operatorLabel.setText(operationText);
        if (operation != Operation.None)
            setOperandIndex(1);
    }

    private void doOperation() {
        if (Double.isNaN(operand2)) {
            if (operation == Operation.Multiplication || operation == Operation.Division) {
                operand2 = 1;
            }
            else {
                operand2 = 0;
            }
        }

        double r = 0;
        switch (operation) {
            case Addition: r = operand1 + operand2; break;
            case Subtraction: r = operand1 - operand2; break;
            case Multiplication: r = operand1 * operand2; break;
            case Division: r = operand1 / operand2; break;
            case None: resultLabel.setText(null); return;
        }
        resultLabel.setText(convertToFormat(r));
    }
}
