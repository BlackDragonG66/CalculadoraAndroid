package com.example.calculadora;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView resultTextView;
    private EditText operationEditText;

    private String currentNumber = "";
    private String previousNumber = "";
    private String operator = "";
    private void performOperation () {
        try {
            double number = Double.parseDouble(currentNumber);

            switch (operator) {
                case "+":
                    previousNumber = previousNumber + number;
                    break;
                case "-":
                    previousNumber = previousNumber - number;
                    break;
                case "*":
                    previousNumber = previousNumber * number;
                    break;
                case "/":
                    previousNumber = previousNumber / number;
                    break;
            }

            currentNumber = "";
            operationEditText.setText(previousNumber + " " + operator);
            resultTextView.setText(previousNumber);
        } catch (NumberFormatException e) {
            // No se puede realizar la operación porque el número actual no es un número válido.
        }
    }
    private void appendNumber(String number) {
        currentNumber += number;
        operationEditText.setText(currentNumber);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultTextView = findViewById(R.id.result);
        operationEditText = findViewById(R.id.Operacion);

        Button btn9 = findViewById(R.id.btn9);
        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendNumber("9");
            }
        });

        Button btn8 = findViewById(R.id.btn8);
        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendNumber("8");
            }
        });

        Button btn7 = findViewById(R.id.btn7);
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendNumber("7");
            }
        });

        Button btn6 = findViewById(R.id.btn6);
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendNumber("6");
            }
        });

        Button btn5 = findViewById(R.id.btn5);
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendNumber("5");
            }
        });

        Button btn4 = findViewById(R.id.btn4);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendNumber("4");
            }
        });

        Button btn3 = findViewById(R.id.btn3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendNumber("3");
            }
        });

        Button btn2 = findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendNumber("2");
            }
        });

        Button btn1 = findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendNumber("1");
            }
        });

        Button btn0 = findViewById(R.id.btn0);
        btn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendNumber("0");
            }
        });

        Button btnDecimal = findViewById(R.id.btn_decimal);
        btnDecimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!currentNumber.contains(".")) {
                    appendNumber(".");
                }
            }
        });

        Button btnPlus = findViewById(R.id.btn_plus);
        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performOperation();
                operator = "+";
            }
        });

        Button btnMinus = findViewById(R.id.btn_negativo);
        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performOperation();
                operator = "-";
            }
        });

        Button btnMultiply = findViewById(R.id.btn_por);
        btnMultiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performOperation();
                operator = "*";
            }
        });

        Button btnDivide = findViewById(R.id.btn_div);
        btnDivide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performOperation();
                operator = "/";
            }
        });

        Button btnEquals = findViewById(R.id.btn_res);
        btnEquals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performOperation();
                operator = "";
                previousNumber = "";
                currentNumber = "";
            }
        });

    }
}