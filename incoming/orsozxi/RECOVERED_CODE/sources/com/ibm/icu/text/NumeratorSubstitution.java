package com.ibm.icu.text;

import java.text.ParsePosition;
import kotlin.text.Typography;
import org.apache.http.message.TokenParser;

/* compiled from: NFSubstitution.java */
/* loaded from: classes2.dex */
class NumeratorSubstitution extends NFSubstitution {
    private final double denominator;
    private final boolean withZeros;

    @Override // com.ibm.icu.text.NFSubstitution
    public double composeRuleValue(double d, double d2) {
        return d / d2;
    }

    @Override // com.ibm.icu.text.NFSubstitution
    char tokenChar() {
        return Typography.less;
    }

    NumeratorSubstitution(int i, double d, NFRuleSet nFRuleSet, String str) {
        super(i, nFRuleSet, fixdesc(str));
        this.denominator = d;
        this.withZeros = str.endsWith("<<");
    }

    static String fixdesc(String str) {
        return str.endsWith("<<") ? str.substring(0, str.length() - 1) : str;
    }

    @Override // com.ibm.icu.text.NFSubstitution
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        NumeratorSubstitution numeratorSubstitution = (NumeratorSubstitution) obj;
        return this.denominator == numeratorSubstitution.denominator && this.withZeros == numeratorSubstitution.withZeros;
    }

    @Override // com.ibm.icu.text.NFSubstitution
    public void doSubstitution(double d, StringBuilder sb, int i, int i2) {
        int length;
        double dTransformNumber = transformNumber(d);
        if (!this.withZeros || this.ruleSet == null) {
            length = i;
        } else {
            long j = (long) dTransformNumber;
            int length2 = sb.length();
            while (true) {
                long j2 = j * 10;
                if (j2 >= this.denominator) {
                    break;
                }
                sb.insert(i + this.pos, TokenParser.SP);
                this.ruleSet.format(0L, sb, i + this.pos, i2);
                j = j2;
            }
            length = i + (sb.length() - length2);
        }
        if (dTransformNumber == Math.floor(dTransformNumber) && this.ruleSet != null) {
            this.ruleSet.format((long) dTransformNumber, sb, this.pos + length, i2);
        } else if (this.ruleSet != null) {
            this.ruleSet.format(dTransformNumber, sb, length + this.pos, i2);
        } else {
            sb.insert(length + this.pos, this.numberFormat.format(dTransformNumber));
        }
    }

    @Override // com.ibm.icu.text.NFSubstitution
    public long transformNumber(long j) {
        return Math.round(j * this.denominator);
    }

    @Override // com.ibm.icu.text.NFSubstitution
    public double transformNumber(double d) {
        return Math.round(d * this.denominator);
    }

    @Override // com.ibm.icu.text.NFSubstitution
    public Number doParse(String str, ParsePosition parsePosition, double d, double d2, boolean z, int i) {
        String str2;
        int i2;
        if (this.withZeros) {
            ParsePosition parsePosition2 = new ParsePosition(1);
            String strSubstring = str;
            i2 = 0;
            while (strSubstring.length() > 0 && parsePosition2.getIndex() != 0) {
                parsePosition2.setIndex(0);
                this.ruleSet.parse(strSubstring, parsePosition2, 1.0d, i).intValue();
                if (parsePosition2.getIndex() == 0) {
                    break;
                }
                i2++;
                parsePosition.setIndex(parsePosition.getIndex() + parsePosition2.getIndex());
                strSubstring = strSubstring.substring(parsePosition2.getIndex());
                while (strSubstring.length() > 0 && strSubstring.charAt(0) == ' ') {
                    strSubstring = strSubstring.substring(1);
                    parsePosition.setIndex(parsePosition.getIndex() + 1);
                }
            }
            String strSubstring2 = str.substring(parsePosition.getIndex());
            parsePosition.setIndex(0);
            str2 = strSubstring2;
        } else {
            str2 = str;
            i2 = 0;
        }
        Number numberDoParse = super.doParse(str2, parsePosition, this.withZeros ? 1.0d : d, d2, false, i);
        if (!this.withZeros) {
            return numberDoParse;
        }
        long jLongValue = numberDoParse.longValue();
        long j = 1;
        while (j <= jLongValue) {
            j *= 10;
        }
        while (i2 > 0) {
            j *= 10;
            i2--;
        }
        return new Double(jLongValue / j);
    }

    @Override // com.ibm.icu.text.NFSubstitution
    public double calcUpperBound(double d) {
        return this.denominator;
    }
}
