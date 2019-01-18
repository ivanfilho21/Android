package com.example.ivan.calculator;

import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
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
    private double[] operands;
    private TextView[] operandLabels;
    private TextView operatorLabel, resultLabel;
    private Drawable background, default_background;
    private boolean canAppend;

    private final int MAX_LENGTH = 15;

    private DecimalFormat decimalFormat = new DecimalFormat("#.###############");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        operandLabels = new TextView[2];
        operandLabels[0] = findViewById(R.id.operand1);
        operandLabels[1] = findViewById(R.id.operand2);

        operatorLabel = findViewById(R.id.operatorLabel);
        resultLabel = findViewById(R.id.result);

        operandLabels[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOperandIndex(0);
            }
        });
        operandLabels[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOperandIndex(1);
            }
        });

        background = operandLabels[0].getBackground();
        default_background = operandLabels[1].getBackground();

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
                showAlertDialog(getString(R.string.about_message), getString(R.string.about));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initialize() {
        resetOperandIndex();

        operands = new double[2];
        operands[0] = 0;
        operands[1] = Double.NaN;

        String s = convertToFormat(operands[0]);
        operandLabels[0].setText(s);

        if (Double.isNaN(operands[1])) operandLabels[1].setText(null);
        else operandLabels[1].setText(s);

        resultLabel.setText(null);

        assignOperation(Operation.None.value);
        canAppend = true;
    }

    @Override
    public void onClick(View v) {
        Button b = (Button) v;
        String buttonText = b.getText().toString();

        String[] numbers = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        String[] operands = {getString(R.string.plus), getString(R.string.minus), getString(R.string.times), getString(R.string.obelus)};

        int index = -1;

        for (int i = 0; i < numbers.length; i++) {
            if (buttonText.equalsIgnoreCase(numbers[i])) {
                index = i;
                break;
            }
        }

        if (index != -1) {
            setCurrentOperand(Double.parseDouble(buttonText));
            return;
        }

        for (int i = 0; i < operands.length; i++) {
            if (buttonText.equalsIgnoreCase(operands[i])) {
                index = i;
                break;
            }
        }

        if (index != -1) {
            assignOperation(buttonText);
            return;
        }

        if (buttonText.equalsIgnoreCase(getString(R.string.clear_button)))
            clear();
        else if (buttonText.equalsIgnoreCase(getString(R.string.clear_entry_button)))
            clearEntry();
        else if (buttonText.equalsIgnoreCase(getString(R.string.del_button)))
            delete();
        else if (buttonText.equalsIgnoreCase(getString(R.string.result_button)))
            doOperation();
    }

    private String convertToFormat(double value) {
        return decimalFormat.format(value);
    }

    private void setOperandIndex(int i) {
        currentOperandIndex = i;

        if (currentOperandIndex == 0) {
            operandLabels[0].setBackgroundDrawable( background );
            operandLabels[1].setBackgroundDrawable( default_background );
        }
        else{
            operandLabels[0].setBackgroundDrawable( default_background );
            operandLabels[1].setBackgroundDrawable( background );
        }

        canAppend = false;
    }

    private void resetOperandIndex() {
        setOperandIndex(0);
    }

    private void setCurrentOperand(double n) {
        TextView operandLabel = operandLabels[currentOperandIndex];
        String text = "";

        if (operandLabel.length() >= MAX_LENGTH) return;

        if (!operandLabel.getText().toString().isEmpty() && Double.parseDouble(operandLabel.getText().toString()) == 0)
            operandLabel.setText("");

        if (canAppend)
            text = operandLabel.getText().toString();
        text += convertToFormat(n);

        operandLabel.setText(text);

        operands[currentOperandIndex] = Double.parseDouble(operandLabel.getText().toString());

        canAppend = true;
    }

    private void assignOperation(String operationText) {
        operation = Operation.getOperation(operationText);
        operatorLabel.setText(operationText);
        if (operation != Operation.None) {
            setOperandIndex(1);
        }
    }

    private void doOperation() {
        double o1 = operands[0];
        double o2 = operands[1];

        if (Double.isNaN(operands[1])) {
            if (operation == Operation.Multiplication || operation == Operation.Division)
                o2 = 1;
            else
                o2 = 0;
        }

        double r = 0;

        switch (operation) {
            case Addition: r = o1 + o2; break;
            case Subtraction: r = o1 - o2; break;
            case Multiplication: r = o1 * o2; break;
            case Division:
                if (o2 == 0) {
                    resultLabel.setText(getString(R.string.cannot_divide_by_zero));
                    return;
                }
                r = o1 / o2; break;
            case None: resultLabel.setText(null); return;
        }
        resultLabel.setText(convertToFormat(r));
        canAppend = false;
    }

    private void clear() {
        initialize();
    }

    private void clearEntry() {
        canAppend = false;
        setCurrentOperand(0);
    }

    private void delete() {
        TextView operandLabel = operandLabels[currentOperandIndex];
        String t = operandLabel.getText().toString();

        if (t.length() > 1)
            t = t.substring(0, t.length() -1);
        else {
            t = convertToFormat(0);
        }
        operandLabel.setText(t);
        canAppend = false;
        setCurrentOperand(Double.parseDouble(t));
    }

    private void showAlertDialog(String message, String title) {
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
}
