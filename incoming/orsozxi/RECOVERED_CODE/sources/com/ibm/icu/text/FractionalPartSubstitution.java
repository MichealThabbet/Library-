package com.ibm.icu.text;

import com.ibm.icu.impl.number.DecimalQuantity_DualStorageBCD;
import java.text.ParsePosition;
import kotlin.text.Typography;
import org.apache.http.message.TokenParser;

/* compiled from: NFSubstitution.java */
/* loaded from: classes2.dex */
class FractionalPartSubstitution extends NFSubstitution {
    private final boolean byDigits;
    private final boolean useSpaces;

    @Override // com.ibm.icu.text.NFSubstitution
    public double calcUpperBound(double d) {
        return 0.0d;
    }

    @Override // com.ibm.icu.text.NFSubstitution
    public double composeRuleValue(double d, double d2) {
        return d + d2;
    }

    @Override // com.ibm.icu.text.NFSubstitution
    char tokenChar() {
        return Typography.greater;
    }

    @Override // com.ibm.icu.text.NFSubstitution
    public long transformNumber(long j) {
        return 0L;
    }

    FractionalPartSubstitution(int i, NFRuleSet nFRuleSet, String str) {
        super(i, nFRuleSet, str);
        if (str.equals(">>") || str.equals(">>>") || nFRuleSet == this.ruleSet) {
            this.byDigits = true;
            this.useSpaces = !str.equals(">>>");
        } else {
            this.byDigits = false;
            this.useSpaces = true;
            this.ruleSet.makeIntoFractionRuleSet();
        }
    }

    @Override // com.ibm.icu.text.NFSubstitution
    public void doSubstitution(double d, StringBuilder sb, int i, int i2) {
        if (!this.byDigits) {
            super.doSubstitution(d, sb, i, i2);
            return;
        }
        DecimalQuantity_DualStorageBCD decimalQuantity_DualStorageBCD = new DecimalQuantity_DualStorageBCD(d);
        decimalQuantity_DualStorageBCD.roundToInfinity();
        boolean z = false;
        for (int lowerDisplayMagnitude = decimalQuantity_DualStorageBCD.getLowerDisplayMagnitude(); lowerDisplayMagnitude < 0; lowerDisplayMagnitude++) {
            if (z && this.useSpaces) {
                sb.insert(this.pos + i, TokenParser.SP);
            } else {
                z = true;
            }
            this.ruleSet.format(decimalQuantity_DualStorageBCD.getDigit(lowerDisplayMagnitude), sb, i + this.pos, i2);
        }
    }

    @Override // com.ibm.icu.text.NFSubstitution
    public double transformNumber(double d) {
        return d - Math.floor(d);
    }

    @Override // com.ibm.icu.text.NFSubstitution
    public Number doParse(String str, ParsePosition parsePosition, double d, double d2, boolean z, int i) {
        Number number;
        if (!this.byDigits) {
            return super.doParse(str, parsePosition, d, 0.0d, z, i);
        }
        ParsePosition parsePosition2 = new ParsePosition(1);
        DecimalQuantity_DualStorageBCD decimalQuantity_DualStorageBCD = new DecimalQuantity_DualStorageBCD();
        String strSubstring = str;
        int i2 = 0;
        while (strSubstring.length() > 0 && parsePosition2.getIndex() != 0) {
            parsePosition2.setIndex(0);
            int iIntValue = this.ruleSet.parse(strSubstring, parsePosition2, 10.0d, i).intValue();
            if (z && parsePosition2.getIndex() == 0 && (number = this.ruleSet.owner.getDecimalFormat().parse(strSubstring, parsePosition2)) != null) {
                iIntValue = number.intValue();
            }
            if (parsePosition2.getIndex() != 0) {
                decimalQuantity_DualStorageBCD.appendDigit((byte) iIntValue, 0, true);
                i2++;
                parsePosition.setIndex(parsePosition.getIndex() + parsePosition2.getIndex());
                strSubstring = strSubstring.substring(parsePosition2.getIndex());
                while (strSubstring.length() > 0 && strSubstring.charAt(0) == ' ') {
                    strSubstring = strSubstring.substring(1);
                    parsePosition.setIndex(parsePosition.getIndex() + 1);
                }
            }
        }
        decimalQuantity_DualStorageBCD.adjustMagnitude(-i2);
        return new Double(composeRuleValue(decimalQuantity_DualStorageBCD.toDouble(), d));
    }
}
