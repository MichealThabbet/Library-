package com.ibm.icu.number;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.ibm.icu.impl.FormattedStringBuilder;
import com.ibm.icu.impl.StandardPlural;
import com.ibm.icu.impl.number.DecimalQuantity;
import com.ibm.icu.impl.number.DecimalQuantity_DualStorageBCD;
import com.ibm.icu.impl.number.MacroProps;
import com.ibm.icu.impl.number.MicroProps;
import com.ibm.icu.impl.number.MicroPropsGenerator;
import com.ibm.icu.number.NumberFormatter;
import com.ibm.icu.text.NumberFormat;
import com.ibm.icu.util.Currency;
import com.ibm.icu.util.MeasureUnit;

/* loaded from: classes2.dex */
class NumberFormatterImpl {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final Currency DEFAULT_CURRENCY = Currency.getInstance("XXX");
    final MicroPropsGenerator microPropsGenerator;
    final MicroProps micros;

    private static boolean unitIsBaseUnit(MeasureUnit measureUnit) {
        return measureUnit == null;
    }

    public NumberFormatterImpl(MacroProps macroProps) {
        MicroProps microProps = new MicroProps(true);
        this.micros = microProps;
        this.microPropsGenerator = macrosToMicroGenerator(macroProps, microProps, true);
    }

    public static MicroProps formatStatic(MacroProps macroProps, DecimalQuantity decimalQuantity, FormattedStringBuilder formattedStringBuilder) {
        MicroProps microPropsPreProcessUnsafe = preProcessUnsafe(macroProps, decimalQuantity);
        writeAffixes(microPropsPreProcessUnsafe, formattedStringBuilder, 0, writeNumber(microPropsPreProcessUnsafe, decimalQuantity, formattedStringBuilder, 0));
        return microPropsPreProcessUnsafe;
    }

    public static int getPrefixSuffixStatic(MacroProps macroProps, byte b, StandardPlural standardPlural, FormattedStringBuilder formattedStringBuilder) {
        return getPrefixSuffixImpl(macrosToMicroGenerator(macroProps, new MicroProps(false), false), b, formattedStringBuilder);
    }

    public MicroProps format(DecimalQuantity decimalQuantity, FormattedStringBuilder formattedStringBuilder) {
        MicroProps microPropsPreProcess = preProcess(decimalQuantity);
        writeAffixes(microPropsPreProcess, formattedStringBuilder, 0, writeNumber(microPropsPreProcess, decimalQuantity, formattedStringBuilder, 0));
        return microPropsPreProcess;
    }

    public MicroProps preProcess(DecimalQuantity decimalQuantity) {
        MicroProps microPropsProcessQuantity = this.microPropsGenerator.processQuantity(decimalQuantity);
        if (microPropsProcessQuantity.integerWidth.maxInt == -1) {
            decimalQuantity.setMinInteger(microPropsProcessQuantity.integerWidth.minInt);
        } else {
            decimalQuantity.setMinInteger(microPropsProcessQuantity.integerWidth.minInt);
            decimalQuantity.applyMaxInteger(microPropsProcessQuantity.integerWidth.maxInt);
        }
        return microPropsProcessQuantity;
    }

    private static MicroProps preProcessUnsafe(MacroProps macroProps, DecimalQuantity decimalQuantity) {
        MicroProps microPropsProcessQuantity = macrosToMicroGenerator(macroProps, new MicroProps(false), false).processQuantity(decimalQuantity);
        if (microPropsProcessQuantity.integerWidth.maxInt == -1) {
            decimalQuantity.setMinInteger(microPropsProcessQuantity.integerWidth.minInt);
        } else {
            decimalQuantity.setMinInteger(microPropsProcessQuantity.integerWidth.minInt);
            decimalQuantity.applyMaxInteger(microPropsProcessQuantity.integerWidth.maxInt);
        }
        return microPropsProcessQuantity;
    }

    public int getPrefixSuffix(byte b, StandardPlural standardPlural, FormattedStringBuilder formattedStringBuilder) {
        return getPrefixSuffixImpl(this.microPropsGenerator, b, formattedStringBuilder);
    }

    private static int getPrefixSuffixImpl(MicroPropsGenerator microPropsGenerator, byte b, FormattedStringBuilder formattedStringBuilder) {
        DecimalQuantity_DualStorageBCD decimalQuantity_DualStorageBCD = new DecimalQuantity_DualStorageBCD(0);
        if (b < 0) {
            decimalQuantity_DualStorageBCD.negate();
        }
        MicroProps microPropsProcessQuantity = microPropsGenerator.processQuantity(decimalQuantity_DualStorageBCD);
        microPropsProcessQuantity.modMiddle.apply(formattedStringBuilder, 0, 0);
        return microPropsProcessQuantity.modMiddle.getPrefixLength();
    }

    public MicroProps getRawMicroProps() {
        return this.micros;
    }

    private static boolean unitIsCurrency(MeasureUnit measureUnit) {
        return measureUnit != null && FirebaseAnalytics.Param.CURRENCY.equals(measureUnit.getType());
    }

    private static boolean unitIsPercent(MeasureUnit measureUnit) {
        return measureUnit != null && "percent".equals(measureUnit.getSubtype());
    }

    private static boolean unitIsPermille(MeasureUnit measureUnit) {
        return measureUnit != null && "permille".equals(measureUnit.getSubtype());
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:53:0x00c7  */
    /* JADX WARN: Type inference failed for: r14v12 */
    /* JADX WARN: Type inference failed for: r14v13 */
    /* JADX WARN: Type inference failed for: r14v20 */
    /* JADX WARN: Type inference failed for: r4v21, types: [com.ibm.icu.impl.number.AffixPatternProvider] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static com.ibm.icu.impl.number.MicroPropsGenerator macrosToMicroGenerator(com.ibm.icu.impl.number.MacroProps r19, com.ibm.icu.impl.number.MicroProps r20, boolean r21) {
        /*
            Method dump skipped, instructions count: 752
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.number.NumberFormatterImpl.macrosToMicroGenerator(com.ibm.icu.impl.number.MacroProps, com.ibm.icu.impl.number.MicroProps, boolean):com.ibm.icu.impl.number.MicroPropsGenerator");
    }

    public static int writeAffixes(MicroProps microProps, FormattedStringBuilder formattedStringBuilder, int i, int i2) {
        int iApply = microProps.modInner.apply(formattedStringBuilder, i, i2);
        if (microProps.padding.isValid()) {
            microProps.padding.padAndApply(microProps.modMiddle, microProps.modOuter, formattedStringBuilder, i, i2 + iApply);
            return iApply;
        }
        int iApply2 = iApply + microProps.modMiddle.apply(formattedStringBuilder, i, i2 + iApply);
        return iApply2 + microProps.modOuter.apply(formattedStringBuilder, i, i2 + iApply2);
    }

    public static int writeNumber(MicroProps microProps, DecimalQuantity decimalQuantity, FormattedStringBuilder formattedStringBuilder, int i) {
        String decimalSeparatorString;
        int iInsert;
        int iInsert2;
        if (decimalQuantity.isInfinite()) {
            iInsert2 = formattedStringBuilder.insert(i + 0, microProps.symbols.getInfinity(), NumberFormat.Field.INTEGER);
        } else if (decimalQuantity.isNaN()) {
            iInsert2 = formattedStringBuilder.insert(i + 0, microProps.symbols.getNaN(), NumberFormat.Field.INTEGER);
        } else {
            int iWriteIntegerDigits = writeIntegerDigits(microProps, decimalQuantity, formattedStringBuilder, i + 0) + 0;
            if (decimalQuantity.getLowerDisplayMagnitude() < 0 || microProps.decimal == NumberFormatter.DecimalSeparatorDisplay.ALWAYS) {
                int i2 = iWriteIntegerDigits + i;
                if (microProps.useCurrency) {
                    decimalSeparatorString = microProps.symbols.getMonetaryDecimalSeparatorString();
                } else {
                    decimalSeparatorString = microProps.symbols.getDecimalSeparatorString();
                }
                iWriteIntegerDigits += formattedStringBuilder.insert(i2, decimalSeparatorString, NumberFormat.Field.DECIMAL_SEPARATOR);
            }
            int iWriteFractionDigits = writeFractionDigits(microProps, decimalQuantity, formattedStringBuilder, iWriteIntegerDigits + i) + iWriteIntegerDigits;
            if (iWriteFractionDigits != 0) {
                return iWriteFractionDigits;
            }
            if (microProps.symbols.getCodePointZero() != -1) {
                iInsert = formattedStringBuilder.insertCodePoint(i, microProps.symbols.getCodePointZero(), NumberFormat.Field.INTEGER);
            } else {
                iInsert = formattedStringBuilder.insert(i, microProps.symbols.getDigitStringsLocal()[0], NumberFormat.Field.INTEGER);
            }
            return iInsert + iWriteFractionDigits;
        }
        return iInsert2 + 0;
    }

    private static int writeIntegerDigits(MicroProps microProps, DecimalQuantity decimalQuantity, FormattedStringBuilder formattedStringBuilder, int i) {
        int iInsert;
        String groupingSeparatorString;
        int upperDisplayMagnitude = decimalQuantity.getUpperDisplayMagnitude() + 1;
        int iInsert2 = 0;
        for (int i2 = 0; i2 < upperDisplayMagnitude; i2++) {
            if (microProps.grouping.groupAtPosition(i2, decimalQuantity)) {
                if (microProps.useCurrency) {
                    groupingSeparatorString = microProps.symbols.getMonetaryGroupingSeparatorString();
                } else {
                    groupingSeparatorString = microProps.symbols.getGroupingSeparatorString();
                }
                iInsert2 += formattedStringBuilder.insert(i, groupingSeparatorString, NumberFormat.Field.GROUPING_SEPARATOR);
            }
            byte digit = decimalQuantity.getDigit(i2);
            if (microProps.symbols.getCodePointZero() != -1) {
                iInsert = formattedStringBuilder.insertCodePoint(i, microProps.symbols.getCodePointZero() + digit, NumberFormat.Field.INTEGER);
            } else {
                iInsert = formattedStringBuilder.insert(i, microProps.symbols.getDigitStringsLocal()[digit], NumberFormat.Field.INTEGER);
            }
            iInsert2 += iInsert;
        }
        return iInsert2;
    }

    private static int writeFractionDigits(MicroProps microProps, DecimalQuantity decimalQuantity, FormattedStringBuilder formattedStringBuilder, int i) {
        int iInsert;
        int i2 = -decimalQuantity.getLowerDisplayMagnitude();
        int i3 = 0;
        for (int i4 = 0; i4 < i2; i4++) {
            byte digit = decimalQuantity.getDigit((-i4) - 1);
            if (microProps.symbols.getCodePointZero() != -1) {
                iInsert = formattedStringBuilder.insertCodePoint(i3 + i, microProps.symbols.getCodePointZero() + digit, NumberFormat.Field.FRACTION);
            } else {
                iInsert = formattedStringBuilder.insert(i3 + i, microProps.symbols.getDigitStringsLocal()[digit], NumberFormat.Field.FRACTION);
            }
            i3 += iInsert;
        }
        return i3;
    }
}
