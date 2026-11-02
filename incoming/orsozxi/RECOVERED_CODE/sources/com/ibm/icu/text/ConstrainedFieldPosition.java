package com.ibm.icu.text;

import java.text.Format;
import java.util.Objects;
import org.apache.commons.codec.language.Soundex;
import org.apache.http.message.TokenParser;

/* loaded from: classes2.dex */
public class ConstrainedFieldPosition {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private Class<?> fClassConstraint;
    private ConstraintType fConstraint;
    private long fContext;
    private Format.Field fField;
    private int fLimit;
    private int fStart;
    private Object fValue;

    private enum ConstraintType {
        NONE,
        CLASS,
        FIELD,
        VALUE
    }

    public ConstrainedFieldPosition() {
        reset();
    }

    public void reset() {
        this.fConstraint = ConstraintType.NONE;
        this.fClassConstraint = Object.class;
        this.fField = null;
        this.fValue = null;
        this.fStart = 0;
        this.fLimit = 0;
        this.fContext = 0L;
    }

    public void constrainField(Format.Field field) {
        if (field == null) {
            throw new IllegalArgumentException("Cannot constrain on null field");
        }
        this.fConstraint = ConstraintType.FIELD;
        this.fClassConstraint = Object.class;
        this.fField = field;
        this.fValue = null;
    }

    public void constrainClass(Class<?> cls) {
        if (cls == null) {
            throw new IllegalArgumentException("Cannot constrain on null field class");
        }
        this.fConstraint = ConstraintType.CLASS;
        this.fClassConstraint = cls;
        this.fField = null;
        this.fValue = null;
    }

    @Deprecated
    public void constrainFieldAndValue(Format.Field field, Object obj) {
        this.fConstraint = ConstraintType.VALUE;
        this.fClassConstraint = Object.class;
        this.fField = field;
        this.fValue = obj;
    }

    public Format.Field getField() {
        return this.fField;
    }

    public int getStart() {
        return this.fStart;
    }

    public int getLimit() {
        return this.fLimit;
    }

    public Object getFieldValue() {
        return this.fValue;
    }

    public long getInt64IterationContext() {
        return this.fContext;
    }

    public void setInt64IterationContext(long j) {
        this.fContext = j;
    }

    public void setState(Format.Field field, Object obj, int i, int i2) {
        this.fField = field;
        this.fValue = obj;
        this.fStart = i;
        this.fLimit = i2;
    }

    /* renamed from: com.ibm.icu.text.ConstrainedFieldPosition$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$ibm$icu$text$ConstrainedFieldPosition$ConstraintType;

        static {
            int[] iArr = new int[ConstraintType.values().length];
            $SwitchMap$com$ibm$icu$text$ConstrainedFieldPosition$ConstraintType = iArr;
            try {
                iArr[ConstraintType.NONE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$ibm$icu$text$ConstrainedFieldPosition$ConstraintType[ConstraintType.CLASS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$ibm$icu$text$ConstrainedFieldPosition$ConstraintType[ConstraintType.FIELD.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$ibm$icu$text$ConstrainedFieldPosition$ConstraintType[ConstraintType.VALUE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public boolean matchesField(Format.Field field, Object obj) {
        if (field == null) {
            throw new IllegalArgumentException("field must not be null");
        }
        int i = AnonymousClass1.$SwitchMap$com$ibm$icu$text$ConstrainedFieldPosition$ConstraintType[this.fConstraint.ordinal()];
        if (i == 1) {
            return true;
        }
        if (i == 2) {
            return this.fClassConstraint.isAssignableFrom(field.getClass());
        }
        if (i == 3) {
            return this.fField == field;
        }
        if (i == 4) {
            return this.fField == field && Objects.equals(this.fValue, obj);
        }
        throw new AssertionError();
    }

    public String toString() {
        return "CFPos[" + this.fStart + Soundex.SILENT_MARKER + this.fLimit + TokenParser.SP + this.fField + ']';
    }
}
