package com.ibm.icu.text;

import com.ibm.icu.impl.PatternProps;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Objects;
import kotlin.jvm.internal.LongCompanionObject;

/* loaded from: classes2.dex */
final class NFRuleSet {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static final int DEFAULT_RULE_INDEX = 3;
    static final int IMPROPER_FRACTION_RULE_INDEX = 1;
    static final int INFINITY_RULE_INDEX = 4;
    static final int NAN_RULE_INDEX = 5;
    static final int NEGATIVE_RULE_INDEX = 0;
    static final int PROPER_FRACTION_RULE_INDEX = 2;
    private static final int RECURSION_LIMIT = 64;
    LinkedList<NFRule> fractionRules;
    private final boolean isParseable;
    private final String name;
    final RuleBasedNumberFormat owner;
    private NFRule[] rules;
    final NFRule[] nonNumericalRules = new NFRule[6];
    private boolean isFractionRuleSet = false;

    public int hashCode() {
        return 42;
    }

    public NFRuleSet(RuleBasedNumberFormat ruleBasedNumberFormat, String[] strArr, int i) throws IllegalArgumentException {
        this.owner = ruleBasedNumberFormat;
        String strSubstring = strArr[i];
        if (strSubstring.length() == 0) {
            throw new IllegalArgumentException("Empty rule set description");
        }
        if (strSubstring.charAt(0) == '%') {
            int iIndexOf = strSubstring.indexOf(58);
            if (iIndexOf == -1) {
                throw new IllegalArgumentException("Rule set name doesn't end in colon");
            }
            String strSubstring2 = strSubstring.substring(0, iIndexOf);
            boolean zEndsWith = true ^ strSubstring2.endsWith("@noparse");
            this.isParseable = zEndsWith;
            this.name = zEndsWith ? strSubstring2 : strSubstring2.substring(0, strSubstring2.length() - 8);
            while (iIndexOf < strSubstring.length()) {
                iIndexOf++;
                if (!PatternProps.isWhiteSpace(strSubstring.charAt(iIndexOf))) {
                    break;
                }
            }
            strSubstring = strSubstring.substring(iIndexOf);
            strArr[i] = strSubstring;
        } else {
            this.name = "%default";
            this.isParseable = true;
        }
        if (strSubstring.length() == 0) {
            throw new IllegalArgumentException("Empty rule set description");
        }
    }

    public void parseRules(String str) {
        ArrayList<NFRule> arrayList = new ArrayList();
        int length = str.length();
        NFRule nFRule = null;
        int i = 0;
        do {
            int iIndexOf = str.indexOf(59, i);
            if (iIndexOf < 0) {
                iIndexOf = length;
            }
            NFRule.makeRules(str.substring(i, iIndexOf), this, nFRule, this.owner, arrayList);
            if (!arrayList.isEmpty()) {
                nFRule = (NFRule) arrayList.get(arrayList.size() - 1);
            }
            i = iIndexOf + 1;
        } while (i < length);
        long j = 0;
        for (NFRule nFRule2 : arrayList) {
            long baseValue = nFRule2.getBaseValue();
            if (baseValue == 0) {
                nFRule2.setBaseValue(j);
            } else {
                if (baseValue < j) {
                    throw new IllegalArgumentException("Rules are not in order, base: " + baseValue + " < " + j);
                }
                j = baseValue;
            }
            if (!this.isFractionRuleSet) {
                j++;
            }
        }
        NFRule[] nFRuleArr = new NFRule[arrayList.size()];
        this.rules = nFRuleArr;
        arrayList.toArray(nFRuleArr);
    }

    void setNonNumericalRule(NFRule nFRule) {
        long baseValue = nFRule.getBaseValue();
        if (baseValue == -1) {
            this.nonNumericalRules[0] = nFRule;
            return;
        }
        if (baseValue == -2) {
            setBestFractionRule(1, nFRule, true);
            return;
        }
        if (baseValue == -3) {
            setBestFractionRule(2, nFRule, true);
            return;
        }
        if (baseValue == -4) {
            setBestFractionRule(3, nFRule, true);
        } else if (baseValue == -5) {
            this.nonNumericalRules[4] = nFRule;
        } else if (baseValue == -6) {
            this.nonNumericalRules[5] = nFRule;
        }
    }

    private void setBestFractionRule(int i, NFRule nFRule, boolean z) {
        if (z) {
            if (this.fractionRules == null) {
                this.fractionRules = new LinkedList<>();
            }
            this.fractionRules.add(nFRule);
        }
        NFRule[] nFRuleArr = this.nonNumericalRules;
        if (nFRuleArr[i] == null) {
            nFRuleArr[i] = nFRule;
        } else if (this.owner.getDecimalFormatSymbols().getDecimalSeparator() == nFRule.getDecimalPoint()) {
            this.nonNumericalRules[i] = nFRule;
        }
    }

    public void makeIntoFractionRuleSet() {
        this.isFractionRuleSet = true;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof NFRuleSet)) {
            return false;
        }
        NFRuleSet nFRuleSet = (NFRuleSet) obj;
        if (!this.name.equals(nFRuleSet.name) || this.rules.length != nFRuleSet.rules.length || this.isFractionRuleSet != nFRuleSet.isFractionRuleSet) {
            return false;
        }
        int i = 0;
        while (true) {
            NFRule[] nFRuleArr = this.nonNumericalRules;
            if (i >= nFRuleArr.length) {
                int i2 = 0;
                while (true) {
                    NFRule[] nFRuleArr2 = this.rules;
                    if (i2 >= nFRuleArr2.length) {
                        return true;
                    }
                    if (!nFRuleArr2[i2].equals(nFRuleSet.rules[i2])) {
                        return false;
                    }
                    i2++;
                }
            } else {
                if (!Objects.equals(nFRuleArr[i], nFRuleSet.nonNumericalRules[i])) {
                    return false;
                }
                i++;
            }
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.name);
        sb.append(":\n");
        for (NFRule nFRule : this.rules) {
            sb.append(nFRule.toString());
            sb.append("\n");
        }
        for (NFRule nFRule2 : this.nonNumericalRules) {
            if (nFRule2 != null) {
                if (nFRule2.getBaseValue() == -2 || nFRule2.getBaseValue() == -3 || nFRule2.getBaseValue() == -4) {
                    Iterator<NFRule> it2 = this.fractionRules.iterator();
                    while (it2.hasNext()) {
                        NFRule next = it2.next();
                        if (next.getBaseValue() == nFRule2.getBaseValue()) {
                            sb.append(next.toString());
                            sb.append("\n");
                        }
                    }
                } else {
                    sb.append(nFRule2.toString());
                    sb.append("\n");
                }
            }
        }
        return sb.toString();
    }

    public boolean isFractionSet() {
        return this.isFractionRuleSet;
    }

    public String getName() {
        return this.name;
    }

    public boolean isPublic() {
        return !this.name.startsWith("%%");
    }

    public boolean isParseable() {
        return this.isParseable;
    }

    public void format(long j, StringBuilder sb, int i, int i2) {
        if (i2 >= 64) {
            throw new IllegalStateException("Recursion limit exceeded when applying ruleSet " + this.name);
        }
        findNormalRule(j).doFormat(j, sb, i, i2 + 1);
    }

    public void format(double d, StringBuilder sb, int i, int i2) {
        if (i2 >= 64) {
            throw new IllegalStateException("Recursion limit exceeded when applying ruleSet " + this.name);
        }
        findRule(d).doFormat(d, sb, i, i2 + 1);
    }

    NFRule findRule(double d) {
        if (this.isFractionRuleSet) {
            return findFractionRuleSetRule(d);
        }
        if (Double.isNaN(d)) {
            NFRule nFRule = this.nonNumericalRules[5];
            return nFRule == null ? this.owner.getDefaultNaNRule() : nFRule;
        }
        if (d < 0.0d) {
            NFRule[] nFRuleArr = this.nonNumericalRules;
            if (nFRuleArr[0] != null) {
                return nFRuleArr[0];
            }
            d = -d;
        }
        if (Double.isInfinite(d)) {
            NFRule nFRule2 = this.nonNumericalRules[4];
            return nFRule2 == null ? this.owner.getDefaultInfinityRule() : nFRule2;
        }
        if (d != Math.floor(d)) {
            if (d < 1.0d) {
                NFRule[] nFRuleArr2 = this.nonNumericalRules;
                if (nFRuleArr2[2] != null) {
                    return nFRuleArr2[2];
                }
            }
            NFRule[] nFRuleArr3 = this.nonNumericalRules;
            if (nFRuleArr3[1] != null) {
                return nFRuleArr3[1];
            }
        }
        NFRule[] nFRuleArr4 = this.nonNumericalRules;
        if (nFRuleArr4[3] != null) {
            return nFRuleArr4[3];
        }
        return findNormalRule(Math.round(d));
    }

    private NFRule findNormalRule(long j) {
        if (this.isFractionRuleSet) {
            return findFractionRuleSetRule(j);
        }
        int i = 0;
        if (j < 0) {
            NFRule[] nFRuleArr = this.nonNumericalRules;
            if (nFRuleArr[0] != null) {
                return nFRuleArr[0];
            }
            j = -j;
        }
        int length = this.rules.length;
        if (length <= 0) {
            return this.nonNumericalRules[3];
        }
        while (i < length) {
            int i2 = (i + length) >>> 1;
            long baseValue = this.rules[i2].getBaseValue();
            if (baseValue == j) {
                return this.rules[i2];
            }
            if (baseValue > j) {
                length = i2;
            } else {
                i = i2 + 1;
            }
        }
        if (length == 0) {
            throw new IllegalStateException("The rule set " + this.name + " cannot format the value " + j);
        }
        NFRule nFRule = this.rules[length - 1];
        if (!nFRule.shouldRollBack(j)) {
            return nFRule;
        }
        if (length == 1) {
            throw new IllegalStateException("The rule set " + this.name + " cannot roll back from the rule '" + nFRule + "'");
        }
        return this.rules[length - 2];
    }

    private NFRule findFractionRuleSetRule(double d) {
        int i = 0;
        long baseValue = this.rules[0].getBaseValue();
        int i2 = 1;
        while (true) {
            NFRule[] nFRuleArr = this.rules;
            if (i2 >= nFRuleArr.length) {
                break;
            }
            baseValue = lcm(baseValue, nFRuleArr[i2].getBaseValue());
            i2++;
        }
        long jRound = Math.round(baseValue * d);
        long j = LongCompanionObject.MAX_VALUE;
        int i3 = 0;
        while (true) {
            NFRule[] nFRuleArr2 = this.rules;
            if (i >= nFRuleArr2.length) {
                i = i3;
                break;
            }
            long baseValue2 = (nFRuleArr2[i].getBaseValue() * jRound) % baseValue;
            long j2 = baseValue - baseValue2;
            if (j2 < baseValue2) {
                baseValue2 = j2;
            }
            if (baseValue2 < j) {
                if (baseValue2 == 0) {
                    break;
                }
                i3 = i;
                j = baseValue2;
            }
            i++;
        }
        int i4 = i + 1;
        NFRule[] nFRuleArr3 = this.rules;
        if (i4 < nFRuleArr3.length && nFRuleArr3[i4].getBaseValue() == this.rules[i].getBaseValue() && (Math.round(this.rules[i].getBaseValue() * d) < 1 || Math.round(d * this.rules[i].getBaseValue()) >= 2)) {
            i = i4;
        }
        return this.rules[i];
    }

    private static long lcm(long j, long j2) {
        long j3;
        long j4;
        long j5;
        int i = 0;
        long j6 = j;
        long j7 = j2;
        while (true) {
            j3 = j6 & 1;
            if (j3 != 0 || (j7 & 1) != 0) {
                break;
            }
            i++;
            j6 >>= 1;
            j7 >>= 1;
        }
        if (j3 == 1) {
            long j8 = j6;
            j6 = -j7;
            j4 = j7;
            j5 = j8;
        } else {
            j4 = j7;
            j5 = j6;
        }
        while (j6 != 0) {
            while ((j6 & 1) == 0) {
                j6 >>= 1;
            }
            if (j6 > 0) {
                j5 = j6;
            } else {
                j4 = -j6;
            }
            j6 = j5 - j4;
        }
        return (j / (j5 << i)) * j2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v10, types: [java.lang.Number] */
    /* JADX WARN: Type inference failed for: r1v16, types: [java.lang.Number] */
    public Number parse(String str, ParsePosition parsePosition, double d, int i) {
        ParsePosition parsePosition2 = new ParsePosition(0);
        Long l = NFRule.ZERO;
        if (str.length() == 0) {
            return l;
        }
        int i2 = i;
        Long l2 = l;
        int i3 = 0;
        while (true) {
            NFRule[] nFRuleArr = this.nonNumericalRules;
            if (i3 >= nFRuleArr.length) {
                break;
            }
            NFRule nFRule = nFRuleArr[i3];
            if (nFRule != null && ((i2 >> i3) & 1) == 0) {
                i2 |= 1 << i3;
                ?? DoParse = nFRule.doParse(str, parsePosition, false, d, i2);
                if (parsePosition.getIndex() > parsePosition2.getIndex()) {
                    parsePosition2.setIndex(parsePosition.getIndex());
                    l2 = DoParse;
                }
                parsePosition.setIndex(0);
            }
            i3++;
        }
        Long l3 = l2;
        for (int length = this.rules.length - 1; length >= 0 && parsePosition2.getIndex() < str.length(); length--) {
            if (this.isFractionRuleSet || this.rules[length].getBaseValue() < d) {
                ?? DoParse2 = this.rules[length].doParse(str, parsePosition, this.isFractionRuleSet, d, i2);
                if (parsePosition.getIndex() > parsePosition2.getIndex()) {
                    parsePosition2.setIndex(parsePosition.getIndex());
                    l3 = DoParse2;
                }
                parsePosition.setIndex(0);
            }
        }
        parsePosition.setIndex(parsePosition2.getIndex());
        return l3;
    }

    public void setDecimalFormatSymbols(DecimalFormatSymbols decimalFormatSymbols) {
        for (NFRule nFRule : this.rules) {
            nFRule.setDecimalFormatSymbols(decimalFormatSymbols);
        }
        if (this.fractionRules != null) {
            for (int i = 1; i <= 3; i++) {
                if (this.nonNumericalRules[i] != null) {
                    Iterator<NFRule> it2 = this.fractionRules.iterator();
                    while (it2.hasNext()) {
                        NFRule next = it2.next();
                        if (this.nonNumericalRules[i].getBaseValue() == next.getBaseValue()) {
                            setBestFractionRule(i, next, false);
                        }
                    }
                }
            }
        }
        for (NFRule nFRule2 : this.nonNumericalRules) {
            if (nFRule2 != null) {
                nFRule2.setDecimalFormatSymbols(decimalFormatSymbols);
            }
        }
    }
}
