package com.ibm.icu.number;

import com.ibm.icu.impl.number.DecimalQuantity;
import com.ibm.icu.impl.number.MultiplierProducer;
import com.ibm.icu.impl.number.RoundingUtils;
import com.ibm.icu.number.NumberFormatter;
import com.ibm.icu.text.PluralRules;
import com.ibm.icu.util.Currency;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;

/* loaded from: classes2.dex */
public abstract class Precision {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    MathContext mathContext = RoundingUtils.DEFAULT_MATH_CONTEXT_UNLIMITED;
    NumberFormatter.TrailingZeroDisplay trailingZeroDisplay;

    @Deprecated
    public static final BogusRounder BOGUS_PRECISION = new BogusRounder();
    static final InfiniteRounderImpl NONE = new InfiniteRounderImpl();
    static final FractionRounderImpl FIXED_FRAC_0 = new FractionRounderImpl(0, 0);
    static final FractionRounderImpl FIXED_FRAC_2 = new FractionRounderImpl(2, 2);
    static final FractionRounderImpl DEFAULT_MAX_FRAC_6 = new FractionRounderImpl(0, 6);
    static final SignificantRounderImpl FIXED_SIG_2 = new SignificantRounderImpl(2, 2);
    static final SignificantRounderImpl FIXED_SIG_3 = new SignificantRounderImpl(3, 3);
    static final SignificantRounderImpl RANGE_SIG_2_3 = new SignificantRounderImpl(2, 3);
    static final FracSigRounderImpl COMPACT_STRATEGY = new FracSigRounderImpl(0, 0, 1, 2, NumberFormatter.RoundingPriority.RELAXED);
    static final IncrementFiveRounderImpl NICKEL = new IncrementFiveRounderImpl(new BigDecimal("0.05"), 2, 2);
    static final CurrencyRounderImpl MONETARY_STANDARD = new CurrencyRounderImpl(Currency.CurrencyUsage.STANDARD);
    static final CurrencyRounderImpl MONETARY_CASH = new CurrencyRounderImpl(Currency.CurrencyUsage.CASH);

    /* JADX INFO: Access modifiers changed from: private */
    public static int getDisplayMagnitudeFraction(int i) {
        if (i == 0) {
            return Integer.MAX_VALUE;
        }
        return -i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int getRoundingMagnitudeFraction(int i) {
        if (i == -1) {
            return Integer.MIN_VALUE;
        }
        return -i;
    }

    @Deprecated
    public abstract void apply(DecimalQuantity decimalQuantity);

    abstract Precision createCopy();

    Precision() {
    }

    public static Precision unlimited() {
        return constructInfinite();
    }

    public static FractionPrecision integer() {
        return constructFraction(0, 0);
    }

    public static FractionPrecision fixedFraction(int i) {
        if (i >= 0 && i <= 999) {
            return constructFraction(i, i);
        }
        throw new IllegalArgumentException("Fraction length must be between 0 and 999 (inclusive)");
    }

    public static FractionPrecision minFraction(int i) {
        if (i >= 0 && i <= 999) {
            return constructFraction(i, -1);
        }
        throw new IllegalArgumentException("Fraction length must be between 0 and 999 (inclusive)");
    }

    public static FractionPrecision maxFraction(int i) {
        if (i >= 0 && i <= 999) {
            return constructFraction(0, i);
        }
        throw new IllegalArgumentException("Fraction length must be between 0 and 999 (inclusive)");
    }

    public static FractionPrecision minMaxFraction(int i, int i2) {
        if (i >= 0 && i2 <= 999 && i <= i2) {
            return constructFraction(i, i2);
        }
        throw new IllegalArgumentException("Fraction length must be between 0 and 999 (inclusive)");
    }

    public static Precision fixedSignificantDigits(int i) {
        if (i >= 1 && i <= 999) {
            return constructSignificant(i, i);
        }
        throw new IllegalArgumentException("Significant digits must be between 1 and 999 (inclusive)");
    }

    public static Precision minSignificantDigits(int i) {
        if (i >= 1 && i <= 999) {
            return constructSignificant(i, -1);
        }
        throw new IllegalArgumentException("Significant digits must be between 1 and 999 (inclusive)");
    }

    public static Precision maxSignificantDigits(int i) {
        if (i >= 1 && i <= 999) {
            return constructSignificant(1, i);
        }
        throw new IllegalArgumentException("Significant digits must be between 1 and 999 (inclusive)");
    }

    public static Precision minMaxSignificantDigits(int i, int i2) {
        if (i >= 1 && i2 <= 999 && i <= i2) {
            return constructSignificant(i, i2);
        }
        throw new IllegalArgumentException("Significant digits must be between 1 and 999 (inclusive)");
    }

    public static Precision increment(BigDecimal bigDecimal) {
        if (bigDecimal != null && bigDecimal.compareTo(BigDecimal.ZERO) > 0) {
            return constructIncrement(bigDecimal);
        }
        throw new IllegalArgumentException("Rounding increment must be positive and non-null");
    }

    public static CurrencyPrecision currency(Currency.CurrencyUsage currencyUsage) {
        if (currencyUsage != null) {
            return constructCurrency(currencyUsage);
        }
        throw new IllegalArgumentException("CurrencyUsage must be non-null");
    }

    public Precision trailingZeroDisplay(NumberFormatter.TrailingZeroDisplay trailingZeroDisplay) {
        Precision precisionCreateCopy = createCopy();
        precisionCreateCopy.trailingZeroDisplay = trailingZeroDisplay;
        return precisionCreateCopy;
    }

    @Deprecated
    public Precision withMode(MathContext mathContext) {
        if (this.mathContext.equals(mathContext)) {
            return this;
        }
        Precision precisionCreateCopy = createCopy();
        precisionCreateCopy.mathContext = mathContext;
        return precisionCreateCopy;
    }

    static Precision constructInfinite() {
        return NONE;
    }

    static FractionPrecision constructFraction(int i, int i2) {
        if (i == 0 && i2 == 0) {
            return FIXED_FRAC_0;
        }
        if (i == 2 && i2 == 2) {
            return FIXED_FRAC_2;
        }
        if (i == 0 && i2 == 6) {
            return DEFAULT_MAX_FRAC_6;
        }
        return new FractionRounderImpl(i, i2);
    }

    static Precision constructSignificant(int i, int i2) {
        if (i == 2 && i2 == 2) {
            return FIXED_SIG_2;
        }
        if (i == 3 && i2 == 3) {
            return FIXED_SIG_3;
        }
        if (i == 2 && i2 == 3) {
            return RANGE_SIG_2_3;
        }
        return new SignificantRounderImpl(i, i2);
    }

    static Precision constructFractionSignificant(FractionPrecision fractionPrecision, int i, int i2, NumberFormatter.RoundingPriority roundingPriority) {
        FracSigRounderImpl fracSigRounderImpl;
        FractionRounderImpl fractionRounderImpl = (FractionRounderImpl) fractionPrecision;
        if (fractionRounderImpl.minFrac == 0 && fractionRounderImpl.maxFrac == 0 && i == 1 && i2 == 2 && roundingPriority == NumberFormatter.RoundingPriority.RELAXED) {
            fracSigRounderImpl = COMPACT_STRATEGY;
        } else {
            fracSigRounderImpl = new FracSigRounderImpl(fractionRounderImpl.minFrac, fractionRounderImpl.maxFrac, i, i2, roundingPriority);
        }
        return fracSigRounderImpl.withMode(fractionRounderImpl.mathContext);
    }

    static Precision constructIncrement(BigDecimal bigDecimal) {
        if (bigDecimal.equals(NICKEL.increment)) {
            return NICKEL;
        }
        BigDecimal bigDecimalStripTrailingZeros = bigDecimal.stripTrailingZeros();
        if (bigDecimalStripTrailingZeros.precision() == 1) {
            int iScale = bigDecimal.scale();
            int iScale2 = bigDecimalStripTrailingZeros.scale();
            BigInteger bigIntegerUnscaledValue = bigDecimalStripTrailingZeros.unscaledValue();
            if (bigIntegerUnscaledValue.intValue() == 1) {
                return new IncrementOneRounderImpl(bigDecimal, iScale, iScale2);
            }
            if (bigIntegerUnscaledValue.intValue() == 5) {
                return new IncrementFiveRounderImpl(bigDecimal, iScale, iScale2);
            }
        }
        return new IncrementRounderImpl(bigDecimal);
    }

    static CurrencyPrecision constructCurrency(Currency.CurrencyUsage currencyUsage) {
        if (currencyUsage == Currency.CurrencyUsage.STANDARD) {
            return MONETARY_STANDARD;
        }
        if (currencyUsage == Currency.CurrencyUsage.CASH) {
            return MONETARY_CASH;
        }
        throw new AssertionError();
    }

    static Precision constructFromCurrency(CurrencyPrecision currencyPrecision, Currency currency) {
        Precision precisionConstructFraction;
        CurrencyRounderImpl currencyRounderImpl = (CurrencyRounderImpl) currencyPrecision;
        double roundingIncrement = currency.getRoundingIncrement(currencyRounderImpl.usage);
        if (roundingIncrement != 0.0d) {
            precisionConstructFraction = constructIncrement(BigDecimal.valueOf(roundingIncrement));
        } else {
            int defaultFractionDigits = currency.getDefaultFractionDigits(currencyRounderImpl.usage);
            precisionConstructFraction = constructFraction(defaultFractionDigits, defaultFractionDigits);
        }
        return precisionConstructFraction.withMode(currencyRounderImpl.mathContext);
    }

    Precision withLocaleData(Currency currency) {
        return this instanceof CurrencyPrecision ? ((CurrencyPrecision) this).withCurrency(currency) : this;
    }

    int chooseMultiplierAndApply(DecimalQuantity decimalQuantity, MultiplierProducer multiplierProducer) {
        int multiplier;
        int magnitude = decimalQuantity.getMagnitude();
        int multiplier2 = multiplierProducer.getMultiplier(magnitude);
        decimalQuantity.adjustMagnitude(multiplier2);
        apply(decimalQuantity);
        if (decimalQuantity.isZeroish() || decimalQuantity.getMagnitude() == magnitude + multiplier2 || multiplier2 == (multiplier = multiplierProducer.getMultiplier(magnitude + 1))) {
            return multiplier2;
        }
        decimalQuantity.adjustMagnitude(multiplier - multiplier2);
        apply(decimalQuantity);
        return multiplier;
    }

    @Deprecated
    public static class BogusRounder extends Precision {
        @Deprecated
        public BogusRounder() {
        }

        @Override // com.ibm.icu.number.Precision
        @Deprecated
        public void apply(DecimalQuantity decimalQuantity) {
            throw new AssertionError("BogusRounder must not be applied");
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.ibm.icu.number.Precision
        public BogusRounder createCopy() {
            BogusRounder bogusRounder = new BogusRounder();
            bogusRounder.mathContext = this.mathContext;
            return bogusRounder;
        }

        @Deprecated
        public Precision into(Precision precision) {
            Precision precisionCreateCopy = precision.createCopy();
            precisionCreateCopy.mathContext = this.mathContext;
            return precisionCreateCopy;
        }
    }

    static class InfiniteRounderImpl extends Precision {
        @Override // com.ibm.icu.number.Precision
        public void apply(DecimalQuantity decimalQuantity) {
            decimalQuantity.roundToInfinity();
            setResolvedMinFraction(decimalQuantity, 0);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.ibm.icu.number.Precision
        public InfiniteRounderImpl createCopy() {
            InfiniteRounderImpl infiniteRounderImpl = new InfiniteRounderImpl();
            infiniteRounderImpl.mathContext = this.mathContext;
            return infiniteRounderImpl;
        }
    }

    static class FractionRounderImpl extends FractionPrecision {
        final int maxFrac;
        final int minFrac;

        public FractionRounderImpl(int i, int i2) {
            this.minFrac = i;
            this.maxFrac = i2;
        }

        @Override // com.ibm.icu.number.Precision
        public void apply(DecimalQuantity decimalQuantity) {
            decimalQuantity.roundToMagnitude(Precision.getRoundingMagnitudeFraction(this.maxFrac), this.mathContext);
            setResolvedMinFraction(decimalQuantity, Math.max(0, -Precision.getDisplayMagnitudeFraction(this.minFrac)));
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.ibm.icu.number.Precision
        public FractionRounderImpl createCopy() {
            FractionRounderImpl fractionRounderImpl = new FractionRounderImpl(this.minFrac, this.maxFrac);
            fractionRounderImpl.mathContext = this.mathContext;
            return fractionRounderImpl;
        }
    }

    static class SignificantRounderImpl extends Precision {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        final int maxSig;
        final int minSig;

        public SignificantRounderImpl(int i, int i2) {
            this.minSig = i;
            this.maxSig = i2;
        }

        @Override // com.ibm.icu.number.Precision
        public void apply(DecimalQuantity decimalQuantity) {
            decimalQuantity.roundToMagnitude(Precision.getRoundingMagnitudeSignificant(decimalQuantity, this.maxSig), this.mathContext);
            setResolvedMinFraction(decimalQuantity, Math.max(0, -Precision.getDisplayMagnitudeSignificant(decimalQuantity, this.minSig)));
            if (!decimalQuantity.isZeroish() || this.minSig <= 0) {
                return;
            }
            decimalQuantity.setMinInteger(1);
        }

        public void apply(DecimalQuantity decimalQuantity, int i) {
            setResolvedMinFraction(decimalQuantity, this.minSig - i);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.ibm.icu.number.Precision
        public SignificantRounderImpl createCopy() {
            SignificantRounderImpl significantRounderImpl = new SignificantRounderImpl(this.minSig, this.maxSig);
            significantRounderImpl.mathContext = this.mathContext;
            return significantRounderImpl;
        }
    }

    static class FracSigRounderImpl extends Precision {
        final int maxFrac;
        final int maxSig;
        final int minFrac;
        final int minSig;
        final NumberFormatter.RoundingPriority priority;

        public FracSigRounderImpl(int i, int i2, int i3, int i4, NumberFormatter.RoundingPriority roundingPriority) {
            this.minFrac = i;
            this.maxFrac = i2;
            this.minSig = i3;
            this.maxSig = i4;
            this.priority = roundingPriority;
        }

        @Override // com.ibm.icu.number.Precision
        public void apply(DecimalQuantity decimalQuantity) {
            int iMax;
            int roundingMagnitudeFraction = Precision.getRoundingMagnitudeFraction(this.maxFrac);
            int roundingMagnitudeSignificant = Precision.getRoundingMagnitudeSignificant(decimalQuantity, this.maxSig);
            if (this.priority == NumberFormatter.RoundingPriority.RELAXED) {
                iMax = Math.min(roundingMagnitudeFraction, roundingMagnitudeSignificant);
            } else {
                iMax = Math.max(roundingMagnitudeFraction, roundingMagnitudeSignificant);
            }
            decimalQuantity.roundToMagnitude(iMax, this.mathContext);
            setResolvedMinFraction(decimalQuantity, Math.max(0, -Math.min(Precision.getDisplayMagnitudeFraction(this.minFrac), Precision.getDisplayMagnitudeSignificant(decimalQuantity, this.minSig))));
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.ibm.icu.number.Precision
        public FracSigRounderImpl createCopy() {
            FracSigRounderImpl fracSigRounderImpl = new FracSigRounderImpl(this.minFrac, this.maxFrac, this.minSig, this.maxSig, this.priority);
            fracSigRounderImpl.mathContext = this.mathContext;
            return fracSigRounderImpl;
        }
    }

    static class IncrementRounderImpl extends Precision {
        final BigDecimal increment;

        public IncrementRounderImpl(BigDecimal bigDecimal) {
            this.increment = bigDecimal;
        }

        @Override // com.ibm.icu.number.Precision
        public void apply(DecimalQuantity decimalQuantity) {
            decimalQuantity.roundToIncrement(this.increment, this.mathContext);
            setResolvedMinFraction(decimalQuantity, this.increment.scale());
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.ibm.icu.number.Precision
        public IncrementRounderImpl createCopy() {
            IncrementRounderImpl incrementRounderImpl = new IncrementRounderImpl(this.increment);
            incrementRounderImpl.mathContext = this.mathContext;
            return incrementRounderImpl;
        }
    }

    static class IncrementOneRounderImpl extends IncrementRounderImpl {
        final int maxFrac;
        final int minFrac;

        public IncrementOneRounderImpl(BigDecimal bigDecimal, int i, int i2) {
            super(bigDecimal);
            this.minFrac = i;
            this.maxFrac = i2;
        }

        @Override // com.ibm.icu.number.Precision.IncrementRounderImpl, com.ibm.icu.number.Precision
        public void apply(DecimalQuantity decimalQuantity) {
            decimalQuantity.roundToMagnitude(-this.maxFrac, this.mathContext);
            setResolvedMinFraction(decimalQuantity, this.minFrac);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.ibm.icu.number.Precision.IncrementRounderImpl, com.ibm.icu.number.Precision
        public IncrementOneRounderImpl createCopy() {
            IncrementOneRounderImpl incrementOneRounderImpl = new IncrementOneRounderImpl(this.increment, this.minFrac, this.maxFrac);
            incrementOneRounderImpl.mathContext = this.mathContext;
            return incrementOneRounderImpl;
        }
    }

    static class IncrementFiveRounderImpl extends IncrementRounderImpl {
        final int maxFrac;
        final int minFrac;

        public IncrementFiveRounderImpl(BigDecimal bigDecimal, int i, int i2) {
            super(bigDecimal);
            this.minFrac = i;
            this.maxFrac = i2;
        }

        @Override // com.ibm.icu.number.Precision.IncrementRounderImpl, com.ibm.icu.number.Precision
        public void apply(DecimalQuantity decimalQuantity) {
            decimalQuantity.roundToNickel(-this.maxFrac, this.mathContext);
            setResolvedMinFraction(decimalQuantity, this.minFrac);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.ibm.icu.number.Precision.IncrementRounderImpl, com.ibm.icu.number.Precision
        public IncrementFiveRounderImpl createCopy() {
            IncrementFiveRounderImpl incrementFiveRounderImpl = new IncrementFiveRounderImpl(this.increment, this.minFrac, this.maxFrac);
            incrementFiveRounderImpl.mathContext = this.mathContext;
            return incrementFiveRounderImpl;
        }
    }

    static class CurrencyRounderImpl extends CurrencyPrecision {
        final Currency.CurrencyUsage usage;

        public CurrencyRounderImpl(Currency.CurrencyUsage currencyUsage) {
            this.usage = currencyUsage;
        }

        @Override // com.ibm.icu.number.Precision
        public void apply(DecimalQuantity decimalQuantity) {
            throw new AssertionError();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.ibm.icu.number.Precision
        public CurrencyRounderImpl createCopy() {
            CurrencyRounderImpl currencyRounderImpl = new CurrencyRounderImpl(this.usage);
            currencyRounderImpl.mathContext = this.mathContext;
            return currencyRounderImpl;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int getRoundingMagnitudeSignificant(DecimalQuantity decimalQuantity, int i) {
        if (i == -1) {
            return Integer.MIN_VALUE;
        }
        return ((decimalQuantity.isZeroish() ? 0 : decimalQuantity.getMagnitude()) - i) + 1;
    }

    void setResolvedMinFraction(DecimalQuantity decimalQuantity, int i) {
        NumberFormatter.TrailingZeroDisplay trailingZeroDisplay = this.trailingZeroDisplay;
        if (trailingZeroDisplay == null || trailingZeroDisplay == NumberFormatter.TrailingZeroDisplay.AUTO || decimalQuantity.getPluralOperand(PluralRules.Operand.t) != 0.0d) {
            decimalQuantity.setMinFraction(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int getDisplayMagnitudeSignificant(DecimalQuantity decimalQuantity, int i) {
        return ((decimalQuantity.isZeroish() ? 0 : decimalQuantity.getMagnitude()) - i) + 1;
    }
}
