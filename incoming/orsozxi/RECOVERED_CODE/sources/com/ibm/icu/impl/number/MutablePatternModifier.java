package com.ibm.icu.impl.number;

import com.ibm.icu.impl.FormattedStringBuilder;
import com.ibm.icu.impl.StandardPlural;
import com.ibm.icu.impl.number.AffixUtils;
import com.ibm.icu.impl.number.Modifier;
import com.ibm.icu.number.NumberFormatter;
import com.ibm.icu.text.DecimalFormatSymbols;
import com.ibm.icu.text.NumberFormat;
import com.ibm.icu.text.PluralRules;
import com.ibm.icu.util.Currency;
import java.text.Format;

/* loaded from: classes2.dex */
public class MutablePatternModifier implements Modifier, AffixUtils.SymbolProvider, MicroPropsGenerator {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    Currency currency;
    StringBuilder currentAffix;
    NumberFormat.Field field;
    final boolean isStrong;
    MicroPropsGenerator parent;
    AffixPatternProvider patternInfo;
    boolean perMilleReplacesPercent;
    StandardPlural plural;
    PluralRules rules;
    NumberFormatter.SignDisplay signDisplay;
    Modifier.Signum signum;
    DecimalFormatSymbols symbols;
    NumberFormatter.UnitWidth unitWidth;

    @Override // com.ibm.icu.impl.number.Modifier
    public boolean containsField(Format.Field field) {
        return false;
    }

    @Override // com.ibm.icu.impl.number.Modifier
    public Modifier.Parameters getParameters() {
        return null;
    }

    @Override // com.ibm.icu.impl.number.Modifier
    public boolean semanticallyEquivalent(Modifier modifier) {
        return false;
    }

    public MutablePatternModifier(boolean z) {
        this.isStrong = z;
    }

    public void setPatternInfo(AffixPatternProvider affixPatternProvider, NumberFormat.Field field) {
        this.patternInfo = affixPatternProvider;
        this.field = field;
    }

    public void setPatternAttributes(NumberFormatter.SignDisplay signDisplay, boolean z) {
        this.signDisplay = signDisplay;
        this.perMilleReplacesPercent = z;
    }

    public void setSymbols(DecimalFormatSymbols decimalFormatSymbols, Currency currency, NumberFormatter.UnitWidth unitWidth, PluralRules pluralRules) {
        this.symbols = decimalFormatSymbols;
        this.currency = currency;
        this.unitWidth = unitWidth;
        this.rules = pluralRules;
    }

    public void setNumberProperties(Modifier.Signum signum, StandardPlural standardPlural) {
        this.signum = signum;
        this.plural = standardPlural;
    }

    public boolean needsPlurals() {
        return this.patternInfo.containsSymbolType(-7);
    }

    public ImmutablePatternModifier createImmutable() {
        FormattedStringBuilder formattedStringBuilder = new FormattedStringBuilder();
        FormattedStringBuilder formattedStringBuilder2 = new FormattedStringBuilder();
        if (needsPlurals()) {
            AdoptingModifierStore adoptingModifierStore = new AdoptingModifierStore();
            for (StandardPlural standardPlural : StandardPlural.VALUES) {
                setNumberProperties(Modifier.Signum.POS, standardPlural);
                adoptingModifierStore.setModifier(Modifier.Signum.POS, standardPlural, createConstantModifier(formattedStringBuilder, formattedStringBuilder2));
                setNumberProperties(Modifier.Signum.POS_ZERO, standardPlural);
                adoptingModifierStore.setModifier(Modifier.Signum.POS_ZERO, standardPlural, createConstantModifier(formattedStringBuilder, formattedStringBuilder2));
                setNumberProperties(Modifier.Signum.NEG_ZERO, standardPlural);
                adoptingModifierStore.setModifier(Modifier.Signum.NEG_ZERO, standardPlural, createConstantModifier(formattedStringBuilder, formattedStringBuilder2));
                setNumberProperties(Modifier.Signum.NEG, standardPlural);
                adoptingModifierStore.setModifier(Modifier.Signum.NEG, standardPlural, createConstantModifier(formattedStringBuilder, formattedStringBuilder2));
            }
            adoptingModifierStore.freeze();
            return new ImmutablePatternModifier(adoptingModifierStore, this.rules);
        }
        setNumberProperties(Modifier.Signum.POS, null);
        ConstantMultiFieldModifier constantMultiFieldModifierCreateConstantModifier = createConstantModifier(formattedStringBuilder, formattedStringBuilder2);
        setNumberProperties(Modifier.Signum.POS_ZERO, null);
        ConstantMultiFieldModifier constantMultiFieldModifierCreateConstantModifier2 = createConstantModifier(formattedStringBuilder, formattedStringBuilder2);
        setNumberProperties(Modifier.Signum.NEG_ZERO, null);
        ConstantMultiFieldModifier constantMultiFieldModifierCreateConstantModifier3 = createConstantModifier(formattedStringBuilder, formattedStringBuilder2);
        setNumberProperties(Modifier.Signum.NEG, null);
        return new ImmutablePatternModifier(new AdoptingModifierStore(constantMultiFieldModifierCreateConstantModifier, constantMultiFieldModifierCreateConstantModifier2, constantMultiFieldModifierCreateConstantModifier3, createConstantModifier(formattedStringBuilder, formattedStringBuilder2)), null);
    }

    private ConstantMultiFieldModifier createConstantModifier(FormattedStringBuilder formattedStringBuilder, FormattedStringBuilder formattedStringBuilder2) {
        insertPrefix(formattedStringBuilder.clear(), 0);
        insertSuffix(formattedStringBuilder2.clear(), 0);
        if (this.patternInfo.hasCurrencySign()) {
            return new CurrencySpacingEnabledModifier(formattedStringBuilder, formattedStringBuilder2, !this.patternInfo.hasBody(), this.isStrong, this.symbols);
        }
        return new ConstantMultiFieldModifier(formattedStringBuilder, formattedStringBuilder2, !this.patternInfo.hasBody(), this.isStrong);
    }

    public static class ImmutablePatternModifier implements MicroPropsGenerator {
        MicroPropsGenerator parent = null;
        final AdoptingModifierStore pm;
        final PluralRules rules;

        ImmutablePatternModifier(AdoptingModifierStore adoptingModifierStore, PluralRules pluralRules) {
            this.pm = adoptingModifierStore;
            this.rules = pluralRules;
        }

        public ImmutablePatternModifier addToChain(MicroPropsGenerator microPropsGenerator) {
            this.parent = microPropsGenerator;
            return this;
        }

        @Override // com.ibm.icu.impl.number.MicroPropsGenerator
        public MicroProps processQuantity(DecimalQuantity decimalQuantity) {
            MicroProps microPropsProcessQuantity = this.parent.processQuantity(decimalQuantity);
            if (microPropsProcessQuantity.rounder != null) {
                microPropsProcessQuantity.rounder.apply(decimalQuantity);
            }
            if (microPropsProcessQuantity.modMiddle != null) {
                return microPropsProcessQuantity;
            }
            applyToMicros(microPropsProcessQuantity, decimalQuantity);
            return microPropsProcessQuantity;
        }

        public void applyToMicros(MicroProps microProps, DecimalQuantity decimalQuantity) {
            if (this.rules == null) {
                microProps.modMiddle = this.pm.getModifierWithoutPlural(decimalQuantity.signum());
            } else {
                microProps.modMiddle = this.pm.getModifier(decimalQuantity.signum(), RoundingUtils.getPluralSafe(microProps.rounder, this.rules, decimalQuantity));
            }
        }
    }

    public MicroPropsGenerator addToChain(MicroPropsGenerator microPropsGenerator) {
        this.parent = microPropsGenerator;
        return this;
    }

    @Override // com.ibm.icu.impl.number.MicroPropsGenerator
    public MicroProps processQuantity(DecimalQuantity decimalQuantity) {
        MicroProps microPropsProcessQuantity = this.parent.processQuantity(decimalQuantity);
        if (microPropsProcessQuantity.rounder != null) {
            microPropsProcessQuantity.rounder.apply(decimalQuantity);
        }
        if (microPropsProcessQuantity.modMiddle != null) {
            return microPropsProcessQuantity;
        }
        if (needsPlurals()) {
            setNumberProperties(decimalQuantity.signum(), RoundingUtils.getPluralSafe(microPropsProcessQuantity.rounder, this.rules, decimalQuantity));
        } else {
            setNumberProperties(decimalQuantity.signum(), null);
        }
        microPropsProcessQuantity.modMiddle = this;
        return microPropsProcessQuantity;
    }

    @Override // com.ibm.icu.impl.number.Modifier
    public int apply(FormattedStringBuilder formattedStringBuilder, int i, int i2) {
        int iInsertPrefix = insertPrefix(formattedStringBuilder, i);
        int i3 = i2 + iInsertPrefix;
        int iInsertSuffix = insertSuffix(formattedStringBuilder, i3);
        int iSplice = !this.patternInfo.hasBody() ? formattedStringBuilder.splice(i + iInsertPrefix, i3, "", 0, 0, null) : 0;
        CurrencySpacingEnabledModifier.applyCurrencySpacing(formattedStringBuilder, i, iInsertPrefix, i3 + iSplice, iInsertSuffix, this.symbols);
        return iInsertPrefix + iSplice + iInsertSuffix;
    }

    @Override // com.ibm.icu.impl.number.Modifier
    public int getPrefixLength() {
        prepareAffix(true);
        return AffixUtils.unescapedCount(this.currentAffix, true, this);
    }

    @Override // com.ibm.icu.impl.number.Modifier
    public int getCodePointCount() {
        prepareAffix(true);
        int iUnescapedCount = AffixUtils.unescapedCount(this.currentAffix, false, this);
        prepareAffix(false);
        return iUnescapedCount + AffixUtils.unescapedCount(this.currentAffix, false, this);
    }

    @Override // com.ibm.icu.impl.number.Modifier
    public boolean isStrong() {
        return this.isStrong;
    }

    private int insertPrefix(FormattedStringBuilder formattedStringBuilder, int i) {
        prepareAffix(true);
        return AffixUtils.unescape(this.currentAffix, formattedStringBuilder, i, this, this.field);
    }

    private int insertSuffix(FormattedStringBuilder formattedStringBuilder, int i) {
        prepareAffix(false);
        return AffixUtils.unescape(this.currentAffix, formattedStringBuilder, i, this, this.field);
    }

    private void prepareAffix(boolean z) {
        if (this.currentAffix == null) {
            this.currentAffix = new StringBuilder();
        }
        PatternStringUtils.patternInfoToStringBuilder(this.patternInfo, z, PatternStringUtils.resolveSignDisplay(this.signDisplay, this.signum), this.plural, this.perMilleReplacesPercent, this.currentAffix);
    }

    /* renamed from: com.ibm.icu.impl.number.MutablePatternModifier$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$ibm$icu$number$NumberFormatter$UnitWidth;

        static {
            int[] iArr = new int[NumberFormatter.UnitWidth.values().length];
            $SwitchMap$com$ibm$icu$number$NumberFormatter$UnitWidth = iArr;
            try {
                iArr[NumberFormatter.UnitWidth.SHORT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$ibm$icu$number$NumberFormatter$UnitWidth[NumberFormatter.UnitWidth.NARROW.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$ibm$icu$number$NumberFormatter$UnitWidth[NumberFormatter.UnitWidth.FORMAL.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$ibm$icu$number$NumberFormatter$UnitWidth[NumberFormatter.UnitWidth.VARIANT.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    @Override // com.ibm.icu.impl.number.AffixUtils.SymbolProvider
    public CharSequence getSymbol(int i) {
        int i2 = 3;
        switch (i) {
            case -9:
                return this.currency.getName(this.symbols.getULocale(), 3, (boolean[]) null);
            case -8:
                return "�";
            case -7:
                return this.currency.getName(this.symbols.getULocale(), 2, this.plural.getKeyword(), (boolean[]) null);
            case -6:
                return this.currency.getCurrencyCode();
            case -5:
                if (this.unitWidth == NumberFormatter.UnitWidth.ISO_CODE) {
                    return this.currency.getCurrencyCode();
                }
                if (this.unitWidth == NumberFormatter.UnitWidth.HIDDEN) {
                    return "";
                }
                int i3 = AnonymousClass1.$SwitchMap$com$ibm$icu$number$NumberFormatter$UnitWidth[this.unitWidth.ordinal()];
                if (i3 == 1) {
                    i2 = 0;
                } else if (i3 != 2) {
                    if (i3 == 3) {
                        i2 = 4;
                    } else {
                        if (i3 != 4) {
                            throw new AssertionError();
                        }
                        i2 = 5;
                    }
                }
                return this.currency.getName(this.symbols.getULocale(), i2, (boolean[]) null);
            case -4:
                return this.symbols.getPerMillString();
            case -3:
                return this.symbols.getPercentString();
            case -2:
                return this.symbols.getPlusSignString();
            case -1:
                return this.symbols.getMinusSignString();
            default:
                throw new AssertionError();
        }
    }
}
