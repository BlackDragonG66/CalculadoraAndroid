package com.example.calculadora;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    private TextView resultTextView,operationEditText,debug,debug2;
    private String currentNumber = "";
    private String operator = "";
    private double resultado;
    private Button btn9,btn8,btn7,btn6,btn5,btn4,btn3,btn2,btn1,btn0,btnDecimal,btnPlus,btnMinus,btnMultiply,btnDivide,btnEquals,btnclean,btndel,btnizq,btnder;
    private Stack<Double> pila = new Stack<>();


    private void borrarUltimoCaracter() {
        if (!operator.isEmpty()) { // Si la expresión no está vacía
            operator = operator.substring(0, operator.length() - 1); // Eliminar el último carácter de la expresión
            currentNumber=operator;
            operationEditText.setText(operator); // Mostrar la expresión en el TextView
        }
    }
    private void appendNumber(String number) {
        currentNumber += number;
        operationEditText.setText(currentNumber);
    }

    // Método para evaluar la expresión y mostrar el resultado
    private void evaluarExpresion() {
        try {
            String expresionPostfija = convertirApostfija(operationEditText.getText().toString()); // Convertir la expresión a notación postfija
            resultado = evaluarPostfija(expresionPostfija); // Evaluar la expresión postfija
            resultTextView.setText(String.valueOf(resultado)); // Mostrar el resultado en el TextView
        } catch (Exception e) {
            resultTextView.setText("Error"); // Mostrar un mensaje de error si hay algún problema
        }
    }
    // Método para convertir una expresión de notación infija a notación postfija
    private String convertirApostfija(String expresionInfija) {
        Stack<Character> pila = new Stack<>(); // Crear una pila vacía para guardar los operadores
        String expresionPostfija = ""; // Crear una cadena vacía para guardar la expresión postfija
        for (int i = 0; i < expresionInfija.length(); i++) { // Recorrer la expresión infija de izquierda a derecha
            char c = expresionInfija.charAt(i); // Obtener el carácter actual
            if (Character.isDigit(c) || c == '.') { // Si es un número o un punto
                expresionPostfija += c; // Añadirlo al final de la cadena postfija
            } else if (c == '(') { // Si es un paréntesis izquierdo
                pila.push(c); // Apilarlo en la pila de operadores
            } else if (c == ')') { // Si es un paréntesis derecho
                while (!pila.isEmpty() && pila.peek() != '(') { // Mientras la pila no esté vacía y el operador en la cima no sea un paréntesis izquierdo
                    expresionPostfija += " " + pila.pop(); // Desapilar el operador y añadirlo al final de la cadena postfija, separado por un espacio
                }
                if (!pila.isEmpty() && pila.peek() == '(') { // Si la pila no está vacía y el operador en la cima es un paréntesis izquierdo
                    pila.pop(); // Desapilar el paréntesis izquierdo y descartarlo
                }
            } else if (esOperador(c)) { // Si es un operador
                expresionPostfija += " "; // Añadir un espacio al final de la cadena postfija
                while (!pila.isEmpty() && precedencia(c) <= precedencia(pila.peek())) { // Mientras la pila no esté vacía y el operador actual tenga menor o igual precedencia que el operador en la cima
                    expresionPostfija += pila.pop() + " "; // Desapilar el operador y añadirlo al final de la cadena postfija, separado por un espacio
                }
                pila.push(c); // Apilar el operador actual en la pila de operadores
            }
        }
        while (!pila.isEmpty()) { // Cuando se termine de recorrer la expresión infija, mientras la pila no esté vacía
            expresionPostfija += " " + pila.pop(); // Desapilar el operador y añadirlo al final de la cadena postfija, separado por un espacio
        }
        return expresionPostfija; // Devolver la cadena postfija como resultado
    }
    // Método para evaluar una expresión de notación postfija
    private double evaluarPostfija(String expresionPostfija) {
        Stack<Double> pila = new Stack<>(); // Crear una pila vacía para guardar los números
        for (int i = 0; i < expresionPostfija.length(); i++) { // Recorrer la expresión postfija de izquierda a derecha
            char c = expresionPostfija.charAt(i); // Obtener el carácter actual
            if (Character.isDigit(c)) { // Si es un número
                double num = 0; // Crear una variable para guardar el número
                while (Character.isDigit(c)) { // Mientras el carácter sea un número
                    num = num * 10 + (c - '0'); // Multiplicar el número por 10 y sumarle el valor del carácter
                    i++; // Incrementar el índice
                    if (i < expresionPostfija.length()) // Si el índice no se sale del rango
                        c = expresionPostfija.charAt(i); // Obtener el siguiente carácter
                }
                i--; // Decrementar el índice
                pila.push(num); // Apilar el número en la pila de números
            } else if (c == '.') { // Si es un punto
                i++; // Incrementar el índice
                if (i < expresionPostfija.length()) // Si el índice no se sale del rango
                    c = expresionPostfija.charAt(i); // Obtener el siguiente carácter
                if (Character.isDigit(c)) { // Si es un número
                    double num = pila.pop(); // Desapilar el último número de la pila
                    double factor = 0.1; // Crear una variable para guardar el factor decimal
                    while (Character.isDigit(c)) { // Mientras el carácter sea un número
                        num = num + (c - '0') * factor; // Sumar al número el valor del carácter multiplicado por el factor
                        factor = factor / 10; // Dividir el factor entre 10
                        i++; // Incrementar el índice
                        if (i < expresionPostfija.length()) // Si el índice no se sale del rango
                            c = expresionPostfija.charAt(i); // Obtener el siguiente carácter
                    }
                    i--; // Decrementar el índice
                    pila.push(num); // Apilar el número en la pila de números
                }
            } else if (esOperador(c)) { // Si es un operador
                double num2 = pila.pop(); // Desapilar el segundo número de la pila
                double num1 = pila.pop(); // Desapilar el primer número de la pila
                double res = operar(num1, num2, c); // Realizar la operación correspondiente
                pila.push(res); // Apilar el resultado en la pila de números
            }
        }
        return pila.pop(); // Cuando se termine de recorrer la expresión postfija, desapilar el último número de la pila y devolverlo como resultado
    }
    // Método para determinar si un carácter es un operador
    private boolean esOperador(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/'; // Devolver verdadero si el carácter es +, -, *, o /
    }

    // Método para determinar la precedencia de un operador
    private int precedencia(char c) {
        if (c == '+' || c == '-') // Si el operador es + o -
            return 1; // Devolver 1 como precedencia
        if (c == '*' || c == '/') // Si el operador es * o /
            return 2; // Devolver 2 como precedencia
        return 0; // Devolver 0 como precedencia por defecto
    }

    // Método para realizar una operación entre dos números
    private double operar(double num1, double num2, char c) {
        switch (c) { // Según el operador
            case '+': // Si es +
                return num1 + num2; // Devolver la suma de los números
            case '-': // Si es -
                return num1 - num2; // Devolver la resta de los números
            case '*': // Si es *
                return num1 * num2; // Devolver la multiplicación de los números
            case '/': // Si es /
                return num1 / num2; // Devolver la división de los números
            default: // Por defecto
                return 0; // Devolver 0 como resultado
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultTextView = findViewById(R.id.result);
        operationEditText = findViewById(R.id.Operacion);
        debug = findViewById(R.id.textView2);
        debug2 = findViewById(R.id.textView3);
        btn9 = findViewById(R.id.btn9);
        btn8 = findViewById(R.id.btn8);
        btn7 = findViewById(R.id.btn7);
        btn6 = findViewById(R.id.btn6);
        btn5 = findViewById(R.id.btn5);
        btn4 = findViewById(R.id.btn4);
        btn3 = findViewById(R.id.btn3);
        btn2 = findViewById(R.id.btn2);
        btn1 = findViewById(R.id.btn1);
        btn0 = findViewById(R.id.btn0);
        btnDecimal = findViewById(R.id.btn_decimal);
        btnPlus = findViewById(R.id.btn_plus);
        btnMinus = findViewById(R.id.btn_negativo);
        btnMultiply = findViewById(R.id.btn_por);
        btnclean = findViewById(R.id.btnLimpia);
        btndel = findViewById(R.id.btnBorrar);
        btnizq = findViewById(R.id.btnizquierdo);
        btnder = findViewById(R.id.btnderecho);
        btnDivide = findViewById(R.id.btn_div);
        btnEquals = findViewById(R.id.btn_res);


        btnclean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operationEditText.setText("");
                currentNumber="";
            }
        });
        btndel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                borrarUltimoCaracter();
            }
        });
        btnizq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendNumber("(");
            }
        });
        btnder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendNumber(")");
            }
        });
        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendNumber("9");
            }
        });


        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendNumber("8");
            }
        });


        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendNumber("7");
            }
        });


        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendNumber("6");
            }
        });


        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendNumber("5");
            }
        });


        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendNumber("4");
            }
        });


        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendNumber("3");
            }
        });


        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendNumber("2");
            }
        });


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendNumber("1");
            }
        });


        btn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendNumber("0");
            }
        });


        btnDecimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!currentNumber.contains(".")) {
                    appendNumber(".");
                }
            }
        });


        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                operator = "+";
                appendNumber(operator);
                debug.setText(operator);
            }
        });


        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                operator = "-";
                appendNumber(operator);
                debug.setText(operator);
            }
        });


        btnMultiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                operator = "*";
                appendNumber(operator);
                debug.setText(operator);
            }
        });


        btnDivide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                operator = "/";
                appendNumber(operator);
                debug.setText(operator);
            }
        });


        btnEquals.setOnClickListener(new View.OnClickListener() {
            //operationEditText
            @Override
            public void onClick(View v) {
                evaluarExpresion();
            }
        });


    }
}