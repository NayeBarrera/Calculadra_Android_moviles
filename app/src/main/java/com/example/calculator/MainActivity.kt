package com.example.calculator

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.*

class MainActivity : AppCompatActivity() {

    private lateinit var inputField: EditText
    private lateinit var button1: Button
    private lateinit var button2: Button
    private lateinit var button3: Button
    private lateinit var button4: Button
    private lateinit var button5: Button
    private lateinit var button6: Button
    private lateinit var button7: Button
    private lateinit var button8: Button
    private lateinit var button9: Button
    private lateinit var button0: Button
    private lateinit var buttonPlus: Button
    private lateinit var buttonMinus: Button
    private lateinit var buttonMultiply: Button
    private lateinit var buttonDivide: Button
    private lateinit var buttonEquals: Button
    private lateinit var buttonSin: Button
    private lateinit var buttonCos: Button
    private lateinit var buttonTan: Button
    private lateinit var buttonClear: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inputField = findViewById(R.id.inputField)
        button0 = findViewById(R.id.button0)
        button1 = findViewById(R.id.button1)
        button2 = findViewById(R.id.button2)
        button3 = findViewById(R.id.button3)
        button4 = findViewById(R.id.button4)
        button5 = findViewById(R.id.button5)
        button6 = findViewById(R.id.button6)
        button7 = findViewById(R.id.button7)
        button8 = findViewById(R.id.button8)
        button9 = findViewById(R.id.button9)
        buttonPlus = findViewById(R.id.buttonPlus)
        buttonMinus = findViewById(R.id.buttonMinus)
        buttonMultiply = findViewById(R.id.buttonMultiply)
        buttonDivide = findViewById(R.id.buttonDivide)
        buttonEquals = findViewById(R.id.buttonEqual)
        buttonSin = findViewById(R.id.buttonSin)
        buttonCos = findViewById(R.id.buttonCos)
        buttonTan = findViewById(R.id.buttonTan)
        buttonClear = findViewById(R.id.buttonClear)

        val numberButtons = listOf(button0, button1, button2, button3, button4, button5, button6, button7, button8, button9)

        for (button in numberButtons) {
            button.setOnClickListener {
                inputField.append(button.text)
            }
        }

        buttonPlus.setOnClickListener { inputField.append(" + ") }
        buttonMinus.setOnClickListener { inputField.append(" - ") }
        buttonMultiply.setOnClickListener { inputField.append(" * ") }
        buttonDivide.setOnClickListener { inputField.append(" / ") }

        buttonEquals.setOnClickListener {
            val expression = inputField.text.toString()
            val result = evaluateExpression(expression)
            inputField.setText(result.toString())
        }

        buttonSin.setOnClickListener { inputField.append("sin(") }
        buttonCos.setOnClickListener { inputField.append("cos(") }
        buttonTan.setOnClickListener { inputField.append("tan(") }
        buttonClear.setOnClickListener { inputField.text.clear() }
    }

    private fun evaluateExpression(expression: String): Double {
        return try {
            val processedExpression = expression
                .replace("sin(", "sin(Math.toRadians(")
                .replace("cos(", "cos(Math.toRadians(")
                .replace("tan(", "tan(Math.toRadians(")
                .plus(")".repeat(expression.count { it == '(' }))

            evalInKotlin(processedExpression)
        } catch (e: Exception) {
            0.0
        }
    }

    private fun evalInKotlin(expression: String): Double {
        return try {
            val engine = javax.script.ScriptEngineManager().getEngineByName("rhino")
            engine.eval("function evalExpression(exp) { return eval(exp); }")
            engine.eval("evalExpression('$expression')") as Double
        } catch (e: Exception) {
            0.0
        }
    }
}
