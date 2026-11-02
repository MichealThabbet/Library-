package com.ibm.icu.number;

import com.ibm.icu.impl.FormattedStringBuilder;
import com.ibm.icu.impl.ICUData;
import com.ibm.icu.impl.ICUResourceBundle;
import com.ibm.icu.impl.SimpleFormatterImpl;
import com.ibm.icu.impl.UResource;
import com.ibm.icu.impl.number.DecimalQuantity;
import com.ibm.icu.impl.number.MicroProps;
import com.ibm.icu.impl.number.Modifier;
import com.ibm.icu.impl.number.SimpleModifier;
import com.ibm.icu.impl.number.range.RangeMacroProps;
import com.ibm.icu.impl.number.range.StandardPluralRanges;
import com.ibm.icu.number.NumberRangeFormatter;
import com.ibm.icu.util.ULocale;
import com.ibm.icu.util.UResourceBundle;
import java.util.MissingResourceException;

/* loaded from: classes2.dex */
class NumberRangeFormatterImpl {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    SimpleModifier fApproximatelyModifier;
    final NumberRangeFormatter.RangeCollapse fCollapse;
    final NumberRangeFormatter.RangeIdentityFallback fIdentityFallback;
    final StandardPluralRanges fPluralRanges;
    String fRangePattern;
    final boolean fSameFormatters;
    final NumberFormatterImpl formatterImpl1;
    final NumberFormatterImpl formatterImpl2;

    int identity2d(NumberRangeFormatter.RangeIdentityFallback rangeIdentityFallback, NumberRangeFormatter.RangeIdentityResult rangeIdentityResult) {
        return rangeIdentityFallback.ordinal() | (rangeIdentityResult.ordinal() << 4);
    }

    private static final class NumberRangeDataSink extends UResource.Sink {
        String approximatelyPattern;
        String rangePattern;
        StringBuilder sb;

        NumberRangeDataSink(StringBuilder sb) {
            this.sb = sb;
        }

        @Override // com.ibm.icu.impl.UResource.Sink
        public void put(UResource.Key key, UResource.Value value, boolean z) {
            UResource.Table table = value.getTable();
            for (int i = 0; table.getKeyAndValue(i, key, value); i++) {
                if (key.contentEquals("range") && !hasRangeData()) {
                    this.rangePattern = SimpleFormatterImpl.compileToStringMinMaxArguments(value.getString(), this.sb, 2, 2);
                }
                if (key.contentEquals("approximately") && !hasApproxData()) {
                    this.approximatelyPattern = SimpleFormatterImpl.compileToStringMinMaxArguments(value.getString(), this.sb, 1, 1);
                }
            }
        }

        private boolean hasRangeData() {
            return this.rangePattern != null;
        }

        private boolean hasApproxData() {
            return this.approximatelyPattern != null;
        }

        public boolean isComplete() {
            return hasRangeData() && hasApproxData();
        }

        public void fillInDefaults() {
            if (!hasRangeData()) {
                this.rangePattern = SimpleFormatterImpl.compileToStringMinMaxArguments("{0}–{1}", this.sb, 2, 2);
            }
            if (hasApproxData()) {
                return;
            }
            this.approximatelyPattern = SimpleFormatterImpl.compileToStringMinMaxArguments("~{0}", this.sb, 1, 1);
        }
    }

    private static void getNumberRangeData(ULocale uLocale, String str, NumberRangeFormatterImpl numberRangeFormatterImpl) throws MissingResourceException {
        StringBuilder sb = new StringBuilder();
        NumberRangeDataSink numberRangeDataSink = new NumberRangeDataSink(sb);
        ICUResourceBundle iCUResourceBundle = (ICUResourceBundle) UResourceBundle.getBundleInstance(ICUData.ICU_BASE_NAME, uLocale);
        sb.append("NumberElements/");
        sb.append(str);
        sb.append("/miscPatterns");
        try {
            iCUResourceBundle.getAllItemsWithFallback(sb.toString(), numberRangeDataSink);
        } catch (MissingResourceException unused) {
        }
        if (!numberRangeDataSink.isComplete()) {
            iCUResourceBundle.getAllItemsWithFallback("NumberElements/latn/miscPatterns", numberRangeDataSink);
        }
        numberRangeDataSink.fillInDefaults();
        numberRangeFormatterImpl.fRangePattern = numberRangeDataSink.rangePattern;
        numberRangeFormatterImpl.fApproximatelyModifier = new SimpleModifier(numberRangeDataSink.approximatelyPattern, null, false);
    }

    public NumberRangeFormatterImpl(RangeMacroProps rangeMacroProps) throws MissingResourceException {
        this.formatterImpl1 = new NumberFormatterImpl(rangeMacroProps.formatter1 != null ? rangeMacroProps.formatter1.resolve() : NumberFormatter.withLocale(rangeMacroProps.loc).resolve());
        this.formatterImpl2 = new NumberFormatterImpl(rangeMacroProps.formatter2 != null ? rangeMacroProps.formatter2.resolve() : NumberFormatter.withLocale(rangeMacroProps.loc).resolve());
        this.fSameFormatters = rangeMacroProps.sameFormatters != 0;
        this.fCollapse = rangeMacroProps.collapse != null ? rangeMacroProps.collapse : NumberRangeFormatter.RangeCollapse.AUTO;
        this.fIdentityFallback = rangeMacroProps.identityFallback != null ? rangeMacroProps.identityFallback : NumberRangeFormatter.RangeIdentityFallback.APPROXIMATELY;
        String str = this.formatterImpl1.getRawMicroProps().nsName;
        if (str == null || !str.equals(this.formatterImpl2.getRawMicroProps().nsName)) {
            throw new IllegalArgumentException("Both formatters must have same numbering system");
        }
        getNumberRangeData(rangeMacroProps.loc, str, this);
        this.fPluralRanges = StandardPluralRanges.forLocale(rangeMacroProps.loc);
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x0064 A[FALL_THROUGH] */
    /* JADX WARN: Removed duplicated region for block: B:31:0x006c  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0074  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public com.ibm.icu.number.FormattedNumberRange format(com.ibm.icu.impl.number.DecimalQuantity r8, com.ibm.icu.impl.number.DecimalQuantity r9, boolean r10) {
        /*
            r7 = this;
            com.ibm.icu.impl.FormattedStringBuilder r6 = new com.ibm.icu.impl.FormattedStringBuilder
            r6.<init>()
            com.ibm.icu.number.NumberFormatterImpl r0 = r7.formatterImpl1
            com.ibm.icu.impl.number.MicroProps r4 = r0.preProcess(r8)
            boolean r0 = r7.fSameFormatters
            if (r0 == 0) goto L16
            com.ibm.icu.number.NumberFormatterImpl r0 = r7.formatterImpl1
            com.ibm.icu.impl.number.MicroProps r0 = r0.preProcess(r9)
            goto L1c
        L16:
            com.ibm.icu.number.NumberFormatterImpl r0 = r7.formatterImpl2
            com.ibm.icu.impl.number.MicroProps r0 = r0.preProcess(r9)
        L1c:
            r5 = r0
            com.ibm.icu.impl.number.Modifier r0 = r4.modInner
            com.ibm.icu.impl.number.Modifier r1 = r5.modInner
            boolean r0 = r0.semanticallyEquivalent(r1)
            if (r0 == 0) goto L81
            com.ibm.icu.impl.number.Modifier r0 = r4.modMiddle
            com.ibm.icu.impl.number.Modifier r1 = r5.modMiddle
            boolean r0 = r0.semanticallyEquivalent(r1)
            if (r0 == 0) goto L81
            com.ibm.icu.impl.number.Modifier r0 = r4.modOuter
            com.ibm.icu.impl.number.Modifier r1 = r5.modOuter
            boolean r0 = r0.semanticallyEquivalent(r1)
            if (r0 != 0) goto L3c
            goto L81
        L3c:
            if (r10 == 0) goto L41
            com.ibm.icu.number.NumberRangeFormatter$RangeIdentityResult r10 = com.ibm.icu.number.NumberRangeFormatter.RangeIdentityResult.EQUAL_BEFORE_ROUNDING
            goto L4c
        L41:
            boolean r10 = r8.equals(r9)
            if (r10 == 0) goto L4a
            com.ibm.icu.number.NumberRangeFormatter$RangeIdentityResult r10 = com.ibm.icu.number.NumberRangeFormatter.RangeIdentityResult.EQUAL_AFTER_ROUNDING
            goto L4c
        L4a:
            com.ibm.icu.number.NumberRangeFormatter$RangeIdentityResult r10 = com.ibm.icu.number.NumberRangeFormatter.RangeIdentityResult.NOT_EQUAL
        L4c:
            com.ibm.icu.number.NumberRangeFormatter$RangeIdentityFallback r0 = r7.fIdentityFallback
            int r0 = r7.identity2d(r0, r10)
            if (r0 == 0) goto L74
            r1 = 1
            if (r0 == r1) goto L74
            r1 = 2
            if (r0 == r1) goto L6c
            r1 = 3
            if (r0 == r1) goto L64
            switch(r0) {
                case 16: goto L74;
                case 17: goto L6c;
                case 18: goto L6c;
                case 19: goto L64;
                default: goto L60;
            }
        L60:
            switch(r0) {
                case 32: goto L64;
                case 33: goto L64;
                case 34: goto L64;
                case 35: goto L64;
                default: goto L63;
            }
        L63:
            goto L7b
        L64:
            r0 = r7
            r1 = r8
            r2 = r9
            r3 = r6
            r0.formatRange(r1, r2, r3, r4, r5)
            goto L7b
        L6c:
            r0 = r7
            r1 = r8
            r2 = r9
            r3 = r6
            r0.formatApproximately(r1, r2, r3, r4, r5)
            goto L7b
        L74:
            r0 = r7
            r1 = r8
            r2 = r9
            r3 = r6
            r0.formatSingleValue(r1, r2, r3, r4, r5)
        L7b:
            com.ibm.icu.number.FormattedNumberRange r0 = new com.ibm.icu.number.FormattedNumberRange
            r0.<init>(r6, r8, r9, r10)
            return r0
        L81:
            r0 = r7
            r1 = r8
            r2 = r9
            r3 = r6
            r0.formatRange(r1, r2, r3, r4, r5)
            com.ibm.icu.number.FormattedNumberRange r10 = new com.ibm.icu.number.FormattedNumberRange
            com.ibm.icu.number.NumberRangeFormatter$RangeIdentityResult r0 = com.ibm.icu.number.NumberRangeFormatter.RangeIdentityResult.NOT_EQUAL
            r10.<init>(r6, r8, r9, r0)
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.number.NumberRangeFormatterImpl.format(com.ibm.icu.impl.number.DecimalQuantity, com.ibm.icu.impl.number.DecimalQuantity, boolean):com.ibm.icu.number.FormattedNumberRange");
    }

    private void formatSingleValue(DecimalQuantity decimalQuantity, DecimalQuantity decimalQuantity2, FormattedStringBuilder formattedStringBuilder, MicroProps microProps, MicroProps microProps2) {
        if (this.fSameFormatters) {
            NumberFormatterImpl.writeAffixes(microProps, formattedStringBuilder, 0, NumberFormatterImpl.writeNumber(microProps, decimalQuantity, formattedStringBuilder, 0));
        } else {
            formatRange(decimalQuantity, decimalQuantity2, formattedStringBuilder, microProps, microProps2);
        }
    }

    private void formatApproximately(DecimalQuantity decimalQuantity, DecimalQuantity decimalQuantity2, FormattedStringBuilder formattedStringBuilder, MicroProps microProps, MicroProps microProps2) {
        if (this.fSameFormatters) {
            int iWriteNumber = NumberFormatterImpl.writeNumber(microProps, decimalQuantity, formattedStringBuilder, 0);
            int iApply = iWriteNumber + microProps.modInner.apply(formattedStringBuilder, 0, iWriteNumber);
            int iApply2 = iApply + microProps.modMiddle.apply(formattedStringBuilder, 0, iApply);
            microProps.modOuter.apply(formattedStringBuilder, 0, iApply2 + this.fApproximatelyModifier.apply(formattedStringBuilder, 0, iApply2));
            return;
        }
        formatRange(decimalQuantity, decimalQuantity2, formattedStringBuilder, microProps, microProps2);
    }

    /* renamed from: com.ibm.icu.number.NumberRangeFormatterImpl$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$ibm$icu$number$NumberRangeFormatter$RangeCollapse;

        static {
            int[] iArr = new int[NumberRangeFormatter.RangeCollapse.values().length];
            $SwitchMap$com$ibm$icu$number$NumberRangeFormatter$RangeCollapse = iArr;
            try {
                iArr[NumberRangeFormatter.RangeCollapse.ALL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$ibm$icu$number$NumberRangeFormatter$RangeCollapse[NumberRangeFormatter.RangeCollapse.AUTO.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$ibm$icu$number$NumberRangeFormatter$RangeCollapse[NumberRangeFormatter.RangeCollapse.UNIT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:36:0x0072  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x007c  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x007f  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0089  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x008c  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0096  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0099 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:53:0x009d  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x00ad  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x00c9  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x00f2  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x011e  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x0146  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x0172  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x019a  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x01c6  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void formatRange(com.ibm.icu.impl.number.DecimalQuantity r11, com.ibm.icu.impl.number.DecimalQuantity r12, com.ibm.icu.impl.FormattedStringBuilder r13, com.ibm.icu.impl.number.MicroProps r14, com.ibm.icu.impl.number.MicroProps r15) {
        /*
            Method dump skipped, instructions count: 527
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.number.NumberRangeFormatterImpl.formatRange(com.ibm.icu.impl.number.DecimalQuantity, com.ibm.icu.impl.number.DecimalQuantity, com.ibm.icu.impl.FormattedStringBuilder, com.ibm.icu.impl.number.MicroProps, com.ibm.icu.impl.number.MicroProps):void");
    }

    Modifier resolveModifierPlurals(Modifier modifier, Modifier modifier2) {
        Modifier.Parameters parameters;
        Modifier.Parameters parameters2 = modifier.getParameters();
        return (parameters2 == null || (parameters = modifier2.getParameters()) == null) ? modifier : parameters2.obj.getModifier(parameters2.signum, this.fPluralRanges.resolve(parameters2.plural, parameters.plural));
    }
}
