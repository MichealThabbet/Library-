package com.ibm.icu.impl.number;

import com.ibm.icu.impl.ICUData;
import com.ibm.icu.impl.ICUResourceBundle;
import com.ibm.icu.impl.number.PatternStringParser;
import com.ibm.icu.number.NumberFormatter;
import com.ibm.icu.util.ULocale;
import com.ibm.icu.util.UResourceBundle;

/* loaded from: classes2.dex */
public class Grouper {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final short grouping1;
    private final short grouping2;
    private final short minGrouping;
    private static final Grouper GROUPER_NEVER = new Grouper(-1, -1, -2);
    private static final Grouper GROUPER_MIN2 = new Grouper(-2, -2, -3);
    private static final Grouper GROUPER_AUTO = new Grouper(-2, -2, -2);
    private static final Grouper GROUPER_ON_ALIGNED = new Grouper(-4, -4, 1);
    private static final Grouper GROUPER_WESTERN = new Grouper(3, 3, 1);
    private static final Grouper GROUPER_INDIC = new Grouper(3, 2, 1);
    private static final Grouper GROUPER_WESTERN_MIN2 = new Grouper(3, 3, 2);
    private static final Grouper GROUPER_INDIC_MIN2 = new Grouper(3, 2, 2);

    /* renamed from: com.ibm.icu.impl.number.Grouper$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$ibm$icu$number$NumberFormatter$GroupingStrategy;

        static {
            int[] iArr = new int[NumberFormatter.GroupingStrategy.values().length];
            $SwitchMap$com$ibm$icu$number$NumberFormatter$GroupingStrategy = iArr;
            try {
                iArr[NumberFormatter.GroupingStrategy.OFF.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$ibm$icu$number$NumberFormatter$GroupingStrategy[NumberFormatter.GroupingStrategy.MIN2.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$ibm$icu$number$NumberFormatter$GroupingStrategy[NumberFormatter.GroupingStrategy.AUTO.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$ibm$icu$number$NumberFormatter$GroupingStrategy[NumberFormatter.GroupingStrategy.ON_ALIGNED.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$ibm$icu$number$NumberFormatter$GroupingStrategy[NumberFormatter.GroupingStrategy.THOUSANDS.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    public static Grouper forStrategy(NumberFormatter.GroupingStrategy groupingStrategy) {
        int i = AnonymousClass1.$SwitchMap$com$ibm$icu$number$NumberFormatter$GroupingStrategy[groupingStrategy.ordinal()];
        if (i == 1) {
            return GROUPER_NEVER;
        }
        if (i == 2) {
            return GROUPER_MIN2;
        }
        if (i == 3) {
            return GROUPER_AUTO;
        }
        if (i == 4) {
            return GROUPER_ON_ALIGNED;
        }
        if (i == 5) {
            return GROUPER_WESTERN;
        }
        throw new AssertionError();
    }

    public static Grouper forProperties(DecimalFormatProperties decimalFormatProperties) {
        if (!decimalFormatProperties.getGroupingUsed()) {
            return GROUPER_NEVER;
        }
        short groupingSize = (short) decimalFormatProperties.getGroupingSize();
        short secondaryGroupingSize = (short) decimalFormatProperties.getSecondaryGroupingSize();
        short minimumGroupingDigits = (short) decimalFormatProperties.getMinimumGroupingDigits();
        if (groupingSize <= 0 && secondaryGroupingSize > 0) {
            groupingSize = secondaryGroupingSize;
        }
        if (secondaryGroupingSize <= 0) {
            secondaryGroupingSize = groupingSize;
        }
        return getInstance(groupingSize, secondaryGroupingSize, minimumGroupingDigits);
    }

    public static Grouper getInstance(short s, short s2, short s3) {
        if (s == -1) {
            return GROUPER_NEVER;
        }
        if (s == 3 && s2 == 3 && s3 == 1) {
            return GROUPER_WESTERN;
        }
        if (s == 3 && s2 == 2 && s3 == 1) {
            return GROUPER_INDIC;
        }
        if (s == 3 && s2 == 3 && s3 == 2) {
            return GROUPER_WESTERN_MIN2;
        }
        if (s == 3 && s2 == 2 && s3 == 2) {
            return GROUPER_INDIC_MIN2;
        }
        return new Grouper(s, s2, s3);
    }

    private static short getMinGroupingForLocale(ULocale uLocale) {
        return Short.valueOf(((ICUResourceBundle) UResourceBundle.getBundleInstance(ICUData.ICU_BASE_NAME, uLocale)).getStringWithFallback("NumberElements/minimumGroupingDigits")).shortValue();
    }

    private Grouper(short s, short s2, short s3) {
        this.grouping1 = s;
        this.grouping2 = s2;
        this.minGrouping = s3;
    }

    public Grouper withLocaleData(ULocale uLocale, PatternStringParser.ParsedPatternInfo parsedPatternInfo) {
        short s;
        short sMax = this.minGrouping;
        if (sMax == -2) {
            sMax = getMinGroupingForLocale(uLocale);
        } else if (sMax == -3) {
            sMax = (short) Math.max(2, (int) getMinGroupingForLocale(uLocale));
        }
        short s2 = this.grouping1;
        if (s2 != -2 && (s = this.grouping2) != -4) {
            return sMax == this.minGrouping ? this : getInstance(s2, s, sMax);
        }
        short s3 = (short) (parsedPatternInfo.positive.groupingSizes & 65535);
        short s4 = (short) ((parsedPatternInfo.positive.groupingSizes >>> 16) & 65535);
        short s5 = (short) ((parsedPatternInfo.positive.groupingSizes >>> 32) & 65535);
        if (s4 == -1) {
            s3 = this.grouping1 == -4 ? (short) 3 : (short) -1;
        }
        if (s5 == -1) {
            s4 = s3;
        }
        return getInstance(s3, s4, sMax);
    }

    public boolean groupAtPosition(int i, DecimalQuantity decimalQuantity) {
        int i2;
        short s = this.grouping1;
        return s != -1 && s != 0 && (i2 = i - s) >= 0 && i2 % this.grouping2 == 0 && (decimalQuantity.getUpperDisplayMagnitude() - this.grouping1) + 1 >= this.minGrouping;
    }

    public short getPrimary() {
        return this.grouping1;
    }

    public short getSecondary() {
        return this.grouping2;
    }
}
