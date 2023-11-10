package com.itextpdf.xmp.options;

import com.itextpdf.xmp.XMPException;
import java.util.HashMap;
import java.util.Map;

public abstract class Options {
    private Map optionNames = null;
    private int options = 0;

    /* access modifiers changed from: protected */
    public void assertConsistency(int i) throws XMPException {
    }

    /* access modifiers changed from: protected */
    public abstract String defineOptionName(int i);

    /* access modifiers changed from: protected */
    public abstract int getValidOptions();

    public Options() {
    }

    public Options(int i) throws XMPException {
        assertOptionsValid(i);
        setOptions(i);
    }

    public void clear() {
        this.options = 0;
    }

    public boolean isExactly(int i) {
        return getOptions() == i;
    }

    public boolean containsAllOptions(int i) {
        return (getOptions() & i) == i;
    }

    public boolean containsOneOf(int i) {
        return (i & getOptions()) != 0;
    }

    /* access modifiers changed from: protected */
    public boolean getOption(int i) {
        return (i & this.options) != 0;
    }

    public void setOption(int i, boolean z) {
        int i2;
        if (z) {
            i2 = i | this.options;
        } else {
            i2 = (~i) & this.options;
        }
        this.options = i2;
    }

    public int getOptions() {
        return this.options;
    }

    public void setOptions(int i) throws XMPException {
        assertOptionsValid(i);
        this.options = i;
    }

    public boolean equals(Object obj) {
        return getOptions() == ((Options) obj).getOptions();
    }

    public int hashCode() {
        return getOptions();
    }

    public String getOptionsString() {
        if (this.options == 0) {
            return "<none>";
        }
        StringBuffer stringBuffer = new StringBuffer();
        int i = this.options;
        while (i != 0) {
            int i2 = (i - 1) & i;
            stringBuffer.append(getOptionName(i ^ i2));
            if (i2 != 0) {
                stringBuffer.append(" | ");
            }
            i = i2;
        }
        return stringBuffer.toString();
    }

    public String toString() {
        return "0x" + Integer.toHexString(this.options);
    }

    private void assertOptionsValid(int i) throws XMPException {
        int i2 = (~getValidOptions()) & i;
        if (i2 == 0) {
            assertConsistency(i);
            return;
        }
        throw new XMPException("The option bit(s) 0x" + Integer.toHexString(i2) + " are invalid!", 103);
    }

    private String getOptionName(int i) {
        Map procureOptionNames = procureOptionNames();
        Integer num = new Integer(i);
        String str = (String) procureOptionNames.get(num);
        if (str != null) {
            return str;
        }
        String defineOptionName = defineOptionName(i);
        if (defineOptionName == null) {
            return "<option name not defined>";
        }
        procureOptionNames.put(num, defineOptionName);
        return defineOptionName;
    }

    private Map procureOptionNames() {
        if (this.optionNames == null) {
            this.optionNames = new HashMap();
        }
        return this.optionNames;
    }
}
