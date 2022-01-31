package com.yazvaa.calcforandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "CALC_FOR_ANDROID";
    TypeKey lastKey;
    TextView ResultOutPut;
    Calculator calculator = new Calculator();
    private final static String KEY_CALC = "calculator";

    @Override
    public void onSaveInstanceState(@NonNull Bundle instanceState) {
        super.onSaveInstanceState(instanceState);
        instanceState.putSerializable(KEY_CALC, calculator);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle instanceState) {
        super.onRestoreInstanceState(instanceState);
        calculator = (Calculator) instanceState.getSerializable(KEY_CALC);
        print();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ResultOutPut = (TextView) findViewById(R.id.etUserInput);
        initButtons();
    }

    private void initButtons() {
        Button[] buttons = new Button[]{(Button) findViewById(R.id.btnBackSpace),
                (Button) findViewById(R.id.btnPercent),
                (Button) findViewById(R.id.btnNumZerro),
                (Button) findViewById(R.id.btnNumTwo),
                (Button) findViewById(R.id.btnNumThree),
                (Button) findViewById(R.id.btnNumSix),
                (Button) findViewById(R.id.btnNumSeven),
                (Button) findViewById(R.id.btnNumOne),
                (Button) findViewById(R.id.btnNumNine),
                (Button) findViewById(R.id.btnNumInc),
                (Button) findViewById(R.id.btnNumFour),
                (Button) findViewById(R.id.btnNumFive),
                (Button) findViewById(R.id.btnNumEight),
                (Button) findViewById(R.id.btnNumCopy),
                (Button) findViewById(R.id.btnMulti),
                (Button) findViewById(R.id.btnLim),
                (Button) findViewById(R.id.btnEq),
                (Button) findViewById(R.id.btnDiv),
                (Button) findViewById(R.id.btnClear),
                (Button) findViewById(R.id.btnDec)
        };
        setListener(buttons);
    }

    void setListener(Button[] buttons) {
        for (Button button : buttons) {
            button.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        Button button = (Button) v;
        if (button.getTag() != null) {
            switch (button.getTag().toString()) {
                case "back": {
                    calculator.backspace();
                    lastKey = TypeKey.Managed;
                    break;
                }
                case "clear": {
                    calculator.reset();
                    lastKey = TypeKey.Managed;
                    break;
                }
                case "div": {
                    if (lastKey != TypeKey.Operand) {
                        lastKey = TypeKey.Operand;
                        calculator.setAction(Actions.DIV);
                        calculator.setCurrentString(calculator.getCurrentString() + button.getText().toString());
                    }
                    break;
                }
                case "multi": {
                    if (lastKey != TypeKey.Operand) {
                        lastKey = TypeKey.Operand;
                        calculator.setAction(Actions.MULTI);
                        calculator.setCurrentString(calculator.getCurrentString() + button.getText().toString());
                    }
                    break;
                }
                case "inc": {
                    if (lastKey != TypeKey.Operand) {
                        lastKey = TypeKey.Operand;
                        calculator.setAction(Actions.INC);
                        calculator.setCurrentString(calculator.getCurrentString() + button.getText().toString());
                    }
                    break;
                }
                case "dec": {
                    if (lastKey != TypeKey.Operand) {
                        lastKey = TypeKey.Operand;
                        calculator.setAction(Actions.DEC);
                        calculator.setCurrentString(calculator.getCurrentString() + button.getText().toString());
                    }
                    break;
                }
                case "perc": {
                    if (lastKey != TypeKey.Operand) {
                        lastKey = TypeKey.Operand;
                        calculator.setAction(Actions.PERCENT);
                        calculator.setCurrentString(calculator.getCurrentString() + button.getText().toString());
                    }
                    break;
                }
                case "lim": {
                    calculator.setLimiter();
                    break;
                }
                case "eq": {
                    calculator.resumeCalc();
                    break;
                }
                case "copy": {
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("result", calculator.getCurrentString());
                    clipboard.setPrimaryClip(clip);
                    break;
                }
                default: {
                    calculator.appendValueString(button.getTag().toString());
                    lastKey = TypeKey.Num;
                    break;
                }
            }
        }
        print();
    }

    private void print() {
        ResultOutPut.setText(calculator.getOldCalcul() + calculator.getCurrentString());
    }
}