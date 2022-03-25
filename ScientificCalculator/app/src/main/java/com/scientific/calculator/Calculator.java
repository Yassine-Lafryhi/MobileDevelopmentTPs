package com.scientific.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.mariuszgromada.math.mxparser.Expression;

public class Calculator extends AppCompatActivity {
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculator);
        editText = findViewById(R.id.editText);

    }

    public void handleClick(View view) {
        Button clicked = (Button) view;
        if (clicked.getText().toString().equals("AC")) {
            editText.getText().clear();
        } else if (clicked.getText().toString().equals("=")) {
            if (editText.getText().toString().trim().isEmpty()) {
                Toast.makeText(Calculator.this, "Please enter an expression !", Toast.LENGTH_LONG).show();
            } else evaluate(editText.getText().toString());
        } else {
            if (clicked.getId() == R.id.plus)
                editText.setText(editText.getText() + "+");
            else if (clicked.getId() == R.id.minus)
                editText.setText(editText.getText() + "-");
            else if (clicked.getId() == R.id.multiply)
                editText.setText(editText.getText() + "*");
            else if (clicked.getId() == R.id.divide)
                editText.setText(editText.getText() + "/");
            else editText.setText(editText.getText() + clicked.getText().toString());
        }
    }

    public void evaluate(String expression) {
        Expression exp = new Expression(expression);
        double result = exp.calculate();
        if (Double.isNaN(result) && (editText.getText().toString().contains("/0"))) {
            Toast.makeText(Calculator.this, "The division by 0 is impossible !", Toast.LENGTH_LONG).show();
            editText.getText().clear();
        } else if (Double.isNaN(result)) {
            Toast.makeText(Calculator.this, "Syntax error !", Toast.LENGTH_LONG).show();
            editText.getText().clear();
        } else {
            editText.setText(String.valueOf(result));
        }
    }
}