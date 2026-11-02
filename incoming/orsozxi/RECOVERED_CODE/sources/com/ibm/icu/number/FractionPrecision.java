package com.ibm.icu.number;

import com.ibm.icu.number.NumberFormatter;

/* loaded from: classes2.dex */
public abstract class FractionPrecision extends Precision {
    FractionPrecision() {
    }

    public Precision withSignificantDigits(int i, int i2, NumberFormatter.RoundingPriority roundingPriority) {
        if (i2 >= 1 && i2 >= i && i2 <= 999) {
            return constructFractionSignificant(this, i, i2, roundingPriority);
        }
        throw new IllegalArgumentException("Significant digits must be between 1 and 999 (inclusive)");
    }

    public Precision withMinDigits(int i) {
        if (i >= 1 && i <= 999) {
            return constructFractionSignificant(this, 1, i, NumberFormatter.RoundingPriority.RELAXED);
        }
        throw new IllegalArgumentException("Significant digits must be between 1 and 999 (inclusive)");
    }

    public Precision withMaxDigits(int i) {
        if (i >= 1 && i <= 999) {
            return constructFractionSignificant(this, 1, i, NumberFormatter.RoundingPriority.STRICT);
        }
        throw new IllegalArgumentException("Significant digits must be between 1 and 999 (inclusive)");
    }
}
