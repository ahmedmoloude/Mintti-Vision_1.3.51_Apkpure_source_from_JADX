package com.kongzue.dialogx.util;

public class InputInfo {
    private int MAX_LENGTH = -1;
    private int inputType;
    private boolean multipleLines;
    private boolean selectAllText;
    private TextInfo textInfo;

    public int getMAX_LENGTH() {
        return this.MAX_LENGTH;
    }

    public InputInfo setMAX_LENGTH(int i) {
        this.MAX_LENGTH = i;
        return this;
    }

    public int getInputType() {
        return this.inputType;
    }

    public InputInfo setInputType(int i) {
        this.inputType = i;
        return this;
    }

    public TextInfo getTextInfo() {
        return this.textInfo;
    }

    public InputInfo setTextInfo(TextInfo textInfo2) {
        this.textInfo = textInfo2;
        return this;
    }

    public boolean isMultipleLines() {
        return this.multipleLines;
    }

    public InputInfo setMultipleLines(boolean z) {
        this.multipleLines = z;
        return this;
    }

    public boolean isSelectAllText() {
        return this.selectAllText;
    }

    public InputInfo setSelectAllText(boolean z) {
        this.selectAllText = z;
        return this;
    }
}
