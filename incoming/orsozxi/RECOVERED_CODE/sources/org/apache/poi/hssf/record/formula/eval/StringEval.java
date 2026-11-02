package org.apache.poi.hssf.record.formula.eval;

import org.apache.poi.hssf.record.formula.Ptg;
import org.apache.poi.hssf.record.formula.StringPtg;

/* loaded from: classes3.dex */
public final class StringEval implements StringValueEval {
    public static final StringEval EMPTY_INSTANCE = new StringEval("");
    private final String _value;

    public StringEval(Ptg ptg) {
        this(((StringPtg) ptg).getValue());
    }

    public StringEval(String str) {
        if (str == null) {
            throw new IllegalArgumentException("value must not be null");
        }
        this._value = str;
    }

    @Override // org.apache.poi.hssf.record.formula.eval.StringValueEval
    public String getStringValue() {
        return this._value;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(64);
        sb.append(getClass().getName());
        sb.append(" [");
        sb.append(this._value);
        sb.append("]");
        return sb.toString();
    }
}
