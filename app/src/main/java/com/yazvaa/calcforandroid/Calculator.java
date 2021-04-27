package com.yazvaa.calcforandroid;

import java.io.Serializable;
import java.util.ArrayList;

public class Calculator implements Serializable {
    private String currentString = "0";
    private int lastOperandPos = 0;
    private ArrayList<Float> values = new ArrayList<Float>();
    private ArrayList<Actions> operands = new ArrayList<Actions>();
    private String oldCalcul = "";

    public String getOldCalcul() {
        return oldCalcul;
    }

    public void setOldCalcul(String oldCalcul) {
        this.oldCalcul = oldCalcul;
    }

    public void resumeCalc() {
        setAction(Actions.NONE);
        float prevValue = 0;
        float nextValue = 0;
        float resume = 0;
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
                case PERCENT: {
                    // не успел
                    break;
                }
                case NONE:
                    break;
            }
            prevValue = resume;
            step++;
        }
        float d = prevValue % 1;
        if (d > 0)
            this.currentString += "=" + prevValue;
        else
            this.currentString += "=" + String.valueOf(prevValue).substring(0, String.valueOf(prevValue).indexOf('.'));
        this.oldCalcul += this.currentString + "\n";
        this.currentString = "0";
        this.values.clear();
        this.operands.clear();
    }

    public void setAction(Actions action) {
        float newValue = 0;
        String stringValue = "";
        try {
            if (this.operands.size() != 0) {
                stringValue = this.currentString.substring(this.lastOperandPos, this.currentString.length());
            } else
                stringValue = this.currentString;
            this.lastOperandPos = this.currentString.length() + 1;
            newValue = Float.valueOf(stringValue);
            if (action != Actions.PERCENT) {
                this.values.add(newValue);
                this.operands.add(action);
            } else
                this.values.add(newValue / 100);
        } catch (Exception e) {
            return;
        }
    }

    public void setLimiter() {
        float newValue = 0;
        String stringValue = "";
        try {
            if (this.operands.size() != 0) {
                stringValue = this.currentString.substring(this.lastOperandPos, this.currentString.length());
            } else
                stringValue = this.currentString;
            if (stringValue.length() == 0) {
                this.currentString += "0.";
                return;
            }
            if (stringValue.indexOf('.') == -1) {
                this.currentString += '.';
            }
        } catch (Exception e) {
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
            this.currentString = this.currentString.substring(0, this.currentString.length() - 1);
        else
            this.currentString = "0";
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
        this.oldCalcul = "";
    }

}
