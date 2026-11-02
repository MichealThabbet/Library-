package com.ibm.icu.impl.number;

import com.ibm.icu.impl.FormattedStringBuilder;
import com.ibm.icu.impl.StandardPlural;
import java.text.Format;

/* loaded from: classes2.dex */
public interface Modifier {

    public static class Parameters {
        public ModifierStore obj;
        public StandardPlural plural;
        public Signum signum;
    }

    public enum Signum {
        NEG,
        NEG_ZERO,
        POS_ZERO,
        POS;

        static final int COUNT = values().length;
    }

    int apply(FormattedStringBuilder formattedStringBuilder, int i, int i2);

    boolean containsField(Format.Field field);

    int getCodePointCount();

    Parameters getParameters();

    int getPrefixLength();

    boolean isStrong();

    boolean semanticallyEquivalent(Modifier modifier);
}
