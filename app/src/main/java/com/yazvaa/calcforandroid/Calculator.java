package com.yazvaa.calcforandroid;

import java.io.Serializable;
import java.util.ArrayList;

public class Calculator implements Serializable {
    private String currentString = "0";
    private int lastOperandPos = 0;
    private ArrayList<Double> values = new ArrayList<Double>();
    private ArrayList<Actions> operands = new ArrayList<Actions>();

    public String getOldCalculate() {
        return oldCalculate;
    }

    public void setOldCalculate(String oldCalculate) {
        this.oldCalculate = oldCalculate;
    }

    private String oldCalculate = "";


    public double percentValue(double currentValue) {
        return this.values.get(this.values.size() - 1) * currentValue / 100;
    }

    public void resumeCalc() {
        setAction(Actions.EQUALLY);
        double prevValue = 0;
        double nextValue = 0;
        double resume = 0;
        int step = 0;
        prevValue = this.values.get(step);
        while (step + 1 < this.values.size()) {
            nextValue = this.values.get(step + 1);
            switch (this.operands.get(step)) {
                case INC: {
                    resume = prevValue + nextValue;
                    break;
                }
                case MULTI: {
                    resume = prevValue * nextValue;
                    break;
                }
                case DIV: {
                    resume = prevValue / nextValue;
                    break;
                }
                case DEC: {
                    resume = prevValue - nextValue;
                    break;
                }
                case EQUALLY:
                    break;
            }
            prevValue = resume;
            step++;
        }
        double d = prevValue % 1;
        if (d > 0) {
            this.currentString += "=" + prevValue;
        } else {
            this.currentString += "=" + (int) prevValue;
        }
        this.oldCalculate += this.currentString + "\n";
        this.currentString = "0";
        this.values.clear();
        this.operands.clear();
    }

    public void setAction(Actions action) {
        double newValue = 0;
        String stringValue = "";
        try {
            if (this.operands.size() != 0) {
                stringValue = getCurrentStringValue(this.lastOperandPos, this.currentString.length());
            } else {
                stringValue = this.currentString;
            }
            this.lastOperandPos = this.currentString.length() + 1;
            newValue = Float.valueOf(stringValue);
            if (action != Actions.PERCENT) {
                this.values.add(newValue);
                this.operands.add(action);
            } else {
                if (this.operands.size() == 0) {
                    this.values.add(newValue / 100);
                } else {
                    this.values.add(percentValue(newValue));
                }
            }
        } catch (Exception e) {
            return;
        }
    }

    public void setLimiter() {
        double newValue = 0;
        String stringValue = "";
        if (this.operands.size() != 0) {
            stringValue = getCurrentStringValue(this.lastOperandPos, this.currentString.length());
        } else
            stringValue = this.currentString;
        if (stringValue.length() == 0) {
            this.currentString += "0.";
            return;
        }
        if (stringValue.indexOf('.') == -1) {
            this.currentString += '.';
        }
    }

    public void setCurrentString(String currentString) {
        this.currentString = currentString;
    }

    @Override
    public String toString() {
        return this.currentString;
    }

    public String getCurrentString() {
        return this.currentString;
    }

    public void backspace() {
        if (this.currentString.length() > 1)
            this.currentString = getCurrentStringValue(0, this.currentString.length() - 1);
        else
            this.currentString = "0";
    }

    private String getCurrentStringValue(int start, int end){
        return this.currentString.substring(start, end);
    }
    public void appendValueString(String value) {
        if (this.currentString != "0")
            this.currentString += value;
        else
            this.currentString = value;

    }

    public void reset() {
        this.currentString = "0";
        this.operands.clear();
        this.values.clear();
        this.oldCalculate = "";
    }

}
