package com.ibm.icu.number;

import com.ibm.icu.impl.FormattedStringBuilder;
import com.ibm.icu.impl.number.ConstantAffixModifier;
import com.ibm.icu.impl.number.DecimalQuantity;
import com.ibm.icu.impl.number.MicroProps;
import com.ibm.icu.impl.number.MicroPropsGenerator;
import com.ibm.icu.impl.number.Modifier;
import com.ibm.icu.impl.number.MultiplierProducer;
import com.ibm.icu.number.NumberFormatter;
import com.ibm.icu.number.Precision;
import com.ibm.icu.text.DecimalFormatSymbols;
import com.ibm.icu.text.NumberFormat;
import java.text.Format;

/* loaded from: classes2.dex */
public class ScientificNotation extends Notation {
    int engineeringInterval;
    NumberFormatter.SignDisplay exponentSignDisplay;
    int minExponentDigits;
    boolean requireMinInt;

    ScientificNotation(int i, boolean z, int i2, NumberFormatter.SignDisplay signDisplay) {
        this.engineeringInterval = i;
        this.requireMinInt = z;
        this.minExponentDigits = i2;
        this.exponentSignDisplay = signDisplay;
    }

    public ScientificNotation withMinExponentDigits(int i) {
        if (i >= 1 && i <= 999) {
            ScientificNotation scientificNotationCreateCopy = createCopy();
            scientificNotationCreateCopy.minExponentDigits = i;
            return scientificNotationCreateCopy;
        }
        throw new IllegalArgumentException("Integer digits must be between 1 and 999 (inclusive)");
    }

    public ScientificNotation withExponentSignDisplay(NumberFormatter.SignDisplay signDisplay) {
        ScientificNotation scientificNotationCreateCopy = createCopy();
        scientificNotationCreateCopy.exponentSignDisplay = signDisplay;
        return scientificNotationCreateCopy;
    }

    ScientificNotation createCopy() {
        return new ScientificNotation(this.engineeringInterval, this.requireMinInt, this.minExponentDigits, this.exponentSignDisplay);
    }

    MicroPropsGenerator withLocaleData(DecimalFormatSymbols decimalFormatSymbols, boolean z, MicroPropsGenerator microPropsGenerator) {
        return new ScientificHandler(decimalFormatSymbols, z, microPropsGenerator);
    }

    private static class ScientificHandler implements MicroPropsGenerator, MultiplierProducer, Modifier {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        int exponent;
        final ScientificNotation notation;
        final MicroPropsGenerator parent;
        final ScientificModifier[] precomputedMods;
        final DecimalFormatSymbols symbols;

        @Override // com.ibm.icu.impl.number.Modifier
        public boolean containsField(Format.Field field) {
            return false;
        }

        @Override // com.ibm.icu.impl.number.Modifier
        public int getCodePointCount() {
            return 999;
        }

        @Override // com.ibm.icu.impl.number.Modifier
        public Modifier.Parameters getParameters() {
            return null;
        }

        @Override // com.ibm.icu.impl.number.Modifier
        public int getPrefixLength() {
            return 0;
        }

        @Override // com.ibm.icu.impl.number.Modifier
        public boolean isStrong() {
            return true;
        }

        @Override // com.ibm.icu.impl.number.Modifier
        public boolean semanticallyEquivalent(Modifier modifier) {
            return false;
        }

        private ScientificHandler(ScientificNotation scientificNotation, DecimalFormatSymbols decimalFormatSymbols, boolean z, MicroPropsGenerator microPropsGenerator) {
            this.notation = scientificNotation;
            this.symbols = decimalFormatSymbols;
            this.parent = microPropsGenerator;
            if (z) {
                this.precomputedMods = new ScientificModifier[25];
                for (int i = -12; i <= 12; i++) {
                    this.precomputedMods[i + 12] = new ScientificModifier(i, this);
                }
                return;
            }
            this.precomputedMods = null;
        }

        @Override // com.ibm.icu.impl.number.MicroPropsGenerator
        public MicroProps processQuantity(DecimalQuantity decimalQuantity) {
            MicroProps microPropsProcessQuantity = this.parent.processQuantity(decimalQuantity);
            if (decimalQuantity.isInfinite() || decimalQuantity.isNaN()) {
                microPropsProcessQuantity.modInner = ConstantAffixModifier.EMPTY;
                return microPropsProcessQuantity;
            }
            int i = 0;
            if (decimalQuantity.isZeroish()) {
                if (this.notation.requireMinInt && (microPropsProcessQuantity.rounder instanceof Precision.SignificantRounderImpl)) {
                    ((Precision.SignificantRounderImpl) microPropsProcessQuantity.rounder).apply(decimalQuantity, this.notation.engineeringInterval);
                } else {
                    microPropsProcessQuantity.rounder.apply(decimalQuantity);
                }
            } else {
                i = -microPropsProcessQuantity.rounder.chooseMultiplierAndApply(decimalQuantity, this);
            }
            ScientificModifier[] scientificModifierArr = this.precomputedMods;
            if (scientificModifierArr != null && i >= -12 && i <= 12) {
                microPropsProcessQuantity.modInner = scientificModifierArr[i + 12];
            } else if (this.precomputedMods != null) {
                microPropsProcessQuantity.modInner = new ScientificModifier(i, this);
            } else {
                this.exponent = i;
                microPropsProcessQuantity.modInner = this;
            }
            decimalQuantity.adjustExponent(i);
            microPropsProcessQuantity.rounder = null;
            return microPropsProcessQuantity;
        }

        @Override // com.ibm.icu.impl.number.MultiplierProducer
        public int getMultiplier(int i) {
            int i2 = this.notation.engineeringInterval;
            if (!this.notation.requireMinInt) {
                i2 = i2 <= 1 ? 1 : (((i % i2) + i2) % i2) + 1;
            }
            return (i2 - i) - 1;
        }

        @Override // com.ibm.icu.impl.number.Modifier
        public int apply(FormattedStringBuilder formattedStringBuilder, int i, int i2) {
            return doApply(this.exponent, formattedStringBuilder, i2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int doApply(int i, FormattedStringBuilder formattedStringBuilder, int i2) {
            int iInsert;
            int iAbs;
            int i3;
            int iInsert2 = formattedStringBuilder.insert(i2, this.symbols.getExponentSeparator(), NumberFormat.Field.EXPONENT_SYMBOL) + i2;
            if (i < 0 && this.notation.exponentSignDisplay != NumberFormatter.SignDisplay.NEVER) {
                iInsert = formattedStringBuilder.insert(iInsert2, this.symbols.getMinusSignString(), NumberFormat.Field.EXPONENT_SIGN);
            } else {
                if (i >= 0 && this.notation.exponentSignDisplay == NumberFormatter.SignDisplay.ALWAYS) {
                    iInsert = formattedStringBuilder.insert(iInsert2, this.symbols.getPlusSignString(), NumberFormat.Field.EXPONENT_SIGN);
                }
                iAbs = Math.abs(i);
                i3 = 0;
                while (true) {
                    if (i3 < this.notation.minExponentDigits && iAbs <= 0) {
                        return iInsert2 - i2;
                    }
                    iInsert2 += formattedStringBuilder.insert(iInsert2 - i3, this.symbols.getDigitStringsLocal()[iAbs % 10], NumberFormat.Field.EXPONENT);
                    i3++;
                    iAbs /= 10;
                }
            }
            iInsert2 += iInsert;
            iAbs = Math.abs(i);
            i3 = 0;
            while (true) {
                if (i3 < this.notation.minExponentDigits) {
                }
                iInsert2 += formattedStringBuilder.insert(iInsert2 - i3, this.symbols.getDigitStringsLocal()[iAbs % 10], NumberFormat.Field.EXPONENT);
                i3++;
                iAbs /= 10;
            }
        }
    }

    private static class ScientificModifier implements Modifier {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        final int exponent;
        final ScientificHandler handler;

        @Override // com.ibm.icu.impl.number.Modifier
        public boolean containsField(Format.Field field) {
            return false;
        }

        @Override // com.ibm.icu.impl.number.Modifier
        public int getCodePointCount() {
            return 999;
        }

        @Override // com.ibm.icu.impl.number.Modifier
        public Modifier.Parameters getParameters() {
            return null;
        }

        @Override // com.ibm.icu.impl.number.Modifier
        public int getPrefixLength() {
            return 0;
        }

        @Override // com.ibm.icu.impl.number.Modifier
        public boolean isStrong() {
            return true;
        }

        ScientificModifier(int i, ScientificHandler scientificHandler) {
            this.exponent = i;
            this.handler = scientificHandler;
        }

        @Override // com.ibm.icu.impl.number.Modifier
        public int apply(FormattedStringBuilder formattedStringBuilder, int i, int i2) {
            return this.handler.doApply(this.exponent, formattedStringBuilder, i2);
        }

        @Override // com.ibm.icu.impl.number.Modifier
        public boolean semanticallyEquivalent(Modifier modifier) {
            return (modifier instanceof ScientificModifier) && this.exponent == ((ScientificModifier) modifier).exponent;
        }
    }
}
