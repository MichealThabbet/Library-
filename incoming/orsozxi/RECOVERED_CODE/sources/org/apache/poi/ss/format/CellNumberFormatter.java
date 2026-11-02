package org.apache.poi.ss.format;

import com.ibm.icu.text.DateFormat;
import it.orsozoxi.android.orsonotes.utils.ConstantsBase;
import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.util.Collections;
import java.util.Formatter;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import org.apache.http.message.TokenParser;
import org.apache.poi.ss.format.CellFormatPart;

/* loaded from: classes3.dex */
public class CellNumberFormatter extends CellFormatter {
    private Special afterFractional;
    private Special afterInteger;
    private DecimalFormat decimalFmt;
    private Special decimalPoint;
    private String denominatorFmt;
    private List<Special> denominatorSpecials;
    private final String desc;
    private Special exponent;
    private List<Special> exponentDigitSpecials;
    private List<Special> exponentSpecials;
    private List<Special> fractionalSpecials;
    private boolean improperFraction;
    private boolean integerCommas;
    private List<Special> integerSpecials;
    private int maxDenominator;
    private Special numerator;
    private String numeratorFmt;
    private List<Special> numeratorSpecials;
    private String printfFmt;
    private double scale;
    private Special slash;
    private final List<Special> specials;
    static final CellFormatter SIMPLE_NUMBER = new CellFormatter("General") { // from class: org.apache.poi.ss.format.CellNumberFormatter.1
        @Override // org.apache.poi.ss.format.CellFormatter
        public void formatValue(StringBuffer stringBuffer, Object obj) {
            if (obj == null) {
                return;
            }
            if (!(obj instanceof Number)) {
                CellTextFormatter.SIMPLE_TEXT.formatValue(stringBuffer, obj);
            } else if (((Number) obj).doubleValue() % 1.0d == 0.0d) {
                CellNumberFormatter.SIMPLE_INT.formatValue(stringBuffer, obj);
            } else {
                CellNumberFormatter.SIMPLE_FLOAT.formatValue(stringBuffer, obj);
            }
        }

        @Override // org.apache.poi.ss.format.CellFormatter
        public void simpleValue(StringBuffer stringBuffer, Object obj) {
            formatValue(stringBuffer, obj);
        }
    };
    private static final CellFormatter SIMPLE_INT = new CellNumberFormatter("#");
    private static final CellFormatter SIMPLE_FLOAT = new CellNumberFormatter("#.#");

    static /* synthetic */ double access$1034(CellNumberFormatter cellNumberFormatter, double d) {
        double d2 = cellNumberFormatter.scale * d;
        cellNumberFormatter.scale = d2;
        return d2;
    }

    static class Special {
        final char ch;
        int pos;

        Special(char c, int i) {
            this.ch = c;
            this.pos = i;
        }

        public String toString() {
            return "'" + this.ch + "' @ " + this.pos;
        }
    }

    static class StringMod implements Comparable<StringMod> {
        public static final int AFTER = 2;
        public static final int BEFORE = 1;
        public static final int REPLACE = 3;
        Special end;
        boolean endInclusive;
        final int op;
        final Special special;
        boolean startInclusive;
        CharSequence toAdd;

        private StringMod(Special special, CharSequence charSequence, int i) {
            this.special = special;
            this.toAdd = charSequence;
            this.op = i;
        }

        public StringMod(Special special, boolean z, Special special2, boolean z2, char c) {
            this(special, z, special2, z2);
            this.toAdd = c + "";
        }

        public StringMod(Special special, boolean z, Special special2, boolean z2) {
            this.special = special;
            this.startInclusive = z;
            this.end = special2;
            this.endInclusive = z2;
            this.op = 3;
            this.toAdd = "";
        }

        @Override // java.lang.Comparable
        public int compareTo(StringMod stringMod) {
            int i = this.special.pos - stringMod.special.pos;
            return i != 0 ? i : this.op - stringMod.op;
        }

        public boolean equals(Object obj) {
            try {
                return compareTo((StringMod) obj) == 0;
            } catch (RuntimeException unused) {
                return false;
            }
        }

        public int hashCode() {
            return this.special.hashCode() + this.op;
        }
    }

    private class NumPartHandler implements CellFormatPart.PartHandler {
        private char insertSignForExponent;

        private NumPartHandler() {
        }

        /* JADX WARN: Removed duplicated region for block: B:35:0x00d4  */
        @Override // org.apache.poi.ss.format.CellFormatPart.PartHandler
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public java.lang.String handlePart(java.util.regex.Matcher r5, java.lang.String r6, org.apache.poi.ss.format.CellFormatType r7, java.lang.StringBuffer r8) {
            /*
                Method dump skipped, instructions count: 282
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: org.apache.poi.ss.format.CellNumberFormatter.NumPartHandler.handlePart(java.util.regex.Matcher, java.lang.String, org.apache.poi.ss.format.CellFormatType, java.lang.StringBuffer):java.lang.String");
        }
    }

    public CellNumberFormatter(String str) {
        int iInterpretPrecision;
        int i;
        super(str);
        this.scale = 1.0d;
        this.specials = new LinkedList();
        StringBuffer format = CellFormatPart.parseFormat(str, CellFormatType.NUMBER, new NumPartHandler());
        if ((this.decimalPoint != null || this.exponent != null) && this.slash != null) {
            this.slash = null;
            this.numerator = null;
        }
        interpretCommas(format);
        if (this.decimalPoint == null) {
            iInterpretPrecision = 0;
            i = 0;
        } else {
            iInterpretPrecision = interpretPrecision();
            i = iInterpretPrecision + 1;
            if (iInterpretPrecision == 0) {
                this.specials.remove(this.decimalPoint);
                this.decimalPoint = null;
            }
        }
        boolean z = true;
        if (iInterpretPrecision == 0) {
            this.fractionalSpecials = Collections.emptyList();
        } else {
            List<Special> list = this.specials;
            this.fractionalSpecials = list.subList(list.indexOf(this.decimalPoint) + 1, fractionalEnd());
        }
        Special special = this.exponent;
        if (special == null) {
            this.exponentSpecials = Collections.emptyList();
        } else {
            int iIndexOf = this.specials.indexOf(special);
            this.exponentSpecials = specialsFor(iIndexOf, 2);
            this.exponentDigitSpecials = specialsFor(iIndexOf + 2);
        }
        if (this.slash == null) {
            this.numeratorSpecials = Collections.emptyList();
            this.denominatorSpecials = Collections.emptyList();
        } else {
            Special special2 = this.numerator;
            if (special2 == null) {
                this.numeratorSpecials = Collections.emptyList();
            } else {
                this.numeratorSpecials = specialsFor(this.specials.indexOf(special2));
            }
            List<Special> listSpecialsFor = specialsFor(this.specials.indexOf(this.slash) + 1);
            this.denominatorSpecials = listSpecialsFor;
            if (listSpecialsFor.isEmpty()) {
                this.numeratorSpecials = Collections.emptyList();
            } else {
                this.maxDenominator = maxValue(this.denominatorSpecials);
                this.numeratorFmt = singleNumberFormat(this.numeratorSpecials);
                this.denominatorFmt = singleNumberFormat(this.denominatorSpecials);
            }
        }
        this.integerSpecials = this.specials.subList(0, integerEnd());
        if (this.exponent == null) {
            StringBuffer stringBuffer = new StringBuffer("%");
            int iCalculateIntegerPartWidth = calculateIntegerPartWidth() + i;
            stringBuffer.append('0');
            stringBuffer.append(iCalculateIntegerPartWidth);
            stringBuffer.append('.');
            stringBuffer.append(iInterpretPrecision);
            stringBuffer.append("f");
            this.printfFmt = stringBuffer.toString();
        } else {
            StringBuffer stringBuffer2 = new StringBuffer();
            List<Special> list2 = this.integerSpecials;
            if (list2.size() == 1) {
                stringBuffer2.append("0");
                z = false;
            } else {
                Iterator<Special> it2 = list2.iterator();
                while (it2.hasNext()) {
                    if (isDigitFmt(it2.next())) {
                        stringBuffer2.append(z ? '#' : '0');
                        z = false;
                    }
                }
            }
            if (this.fractionalSpecials.size() > 0) {
                stringBuffer2.append('.');
                Iterator<Special> it3 = this.fractionalSpecials.iterator();
                while (it3.hasNext()) {
                    if (isDigitFmt(it3.next())) {
                        if (!z) {
                            stringBuffer2.append('0');
                        }
                        z = false;
                    }
                }
            }
            stringBuffer2.append('E');
            List<Special> list3 = this.exponentSpecials;
            placeZeros(stringBuffer2, list3.subList(2, list3.size()));
            this.decimalFmt = new DecimalFormat(stringBuffer2.toString());
        }
        if (this.exponent != null) {
            this.scale = 1.0d;
        }
        this.desc = format.toString();
    }

    private static void placeZeros(StringBuffer stringBuffer, List<Special> list) {
        Iterator<Special> it2 = list.iterator();
        while (it2.hasNext()) {
            if (isDigitFmt(it2.next())) {
                stringBuffer.append('0');
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Special firstDigit(List<Special> list) {
        for (Special special : list) {
            if (isDigitFmt(special)) {
                return special;
            }
        }
        return null;
    }

    static StringMod insertMod(Special special, CharSequence charSequence, int i) {
        return new StringMod(special, charSequence, i);
    }

    static StringMod deleteMod(Special special, boolean z, Special special2, boolean z2) {
        return new StringMod(special, z, special2, z2);
    }

    static StringMod replaceMod(Special special, boolean z, Special special2, boolean z2, char c) {
        return new StringMod(special, z, special2, z2, c);
    }

    private static String singleNumberFormat(List<Special> list) {
        return "%0" + list.size() + DateFormat.DAY;
    }

    private static int maxValue(List<Special> list) {
        return (int) Math.round(Math.pow(10.0d, list.size()) - 1.0d);
    }

    private List<Special> specialsFor(int i, int i2) {
        if (i >= this.specials.size()) {
            return Collections.emptyList();
        }
        int i3 = i2 + i;
        ListIterator<Special> listIterator = this.specials.listIterator(i3);
        Special next = listIterator.next();
        while (listIterator.hasNext()) {
            Special next2 = listIterator.next();
            if (!isDigitFmt(next2) || next2.pos - next.pos > 1) {
                break;
            }
            i3++;
            next = next2;
        }
        return this.specials.subList(i, i3 + 1);
    }

    private List<Special> specialsFor(int i) {
        return specialsFor(i, 0);
    }

    private static boolean isDigitFmt(Special special) {
        return special.ch == '0' || special.ch == '?' || special.ch == '#';
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Special previousNumber() {
        List<Special> list = this.specials;
        ListIterator<Special> listIterator = list.listIterator(list.size());
        while (listIterator.hasPrevious()) {
            Special specialPrevious = listIterator.previous();
            if (isDigitFmt(specialPrevious)) {
                while (listIterator.hasPrevious()) {
                    Special specialPrevious2 = listIterator.previous();
                    if (specialPrevious.pos - specialPrevious2.pos > 1 || !isDigitFmt(specialPrevious2)) {
                        break;
                    }
                    specialPrevious = specialPrevious2;
                }
                return specialPrevious;
            }
        }
        return null;
    }

    private int calculateIntegerPartWidth() {
        Special next;
        ListIterator<Special> listIterator = this.specials.listIterator();
        int i = 0;
        while (listIterator.hasNext() && (next = listIterator.next()) != this.afterInteger) {
            if (isDigitFmt(next)) {
                i++;
            }
        }
        return i;
    }

    private int interpretPrecision() {
        Special special = this.decimalPoint;
        if (special == null) {
            return -1;
        }
        int i = 0;
        List<Special> list = this.specials;
        ListIterator<Special> listIterator = list.listIterator(list.indexOf(special));
        if (listIterator.hasNext()) {
            listIterator.next();
        }
        while (listIterator.hasNext() && isDigitFmt(listIterator.next())) {
            i++;
        }
        return i;
    }

    private void interpretCommas(StringBuffer stringBuffer) {
        ListIterator<Special> listIterator = this.specials.listIterator(integerEnd());
        int i = 0;
        this.integerCommas = false;
        boolean z = true;
        while (listIterator.hasPrevious()) {
            if (listIterator.previous().ch != ',') {
                z = false;
            } else if (z) {
                this.scale /= 1000.0d;
            } else {
                this.integerCommas = true;
            }
        }
        if (this.decimalPoint != null) {
            ListIterator<Special> listIterator2 = this.specials.listIterator(fractionalEnd());
            while (listIterator2.hasPrevious() && listIterator2.previous().ch == ',') {
                this.scale /= 1000.0d;
            }
        }
        ListIterator<Special> listIterator3 = this.specials.listIterator();
        while (listIterator3.hasNext()) {
            Special next = listIterator3.next();
            next.pos -= i;
            if (next.ch == ',') {
                i++;
                listIterator3.remove();
                stringBuffer.deleteCharAt(next.pos);
            }
        }
    }

    private int integerEnd() {
        Special special = this.decimalPoint;
        if (special != null) {
            this.afterInteger = special;
        } else {
            Special special2 = this.exponent;
            if (special2 != null) {
                this.afterInteger = special2;
            } else {
                Special special3 = this.numerator;
                if (special3 != null) {
                    this.afterInteger = special3;
                } else {
                    this.afterInteger = null;
                }
            }
        }
        Special special4 = this.afterInteger;
        return special4 == null ? this.specials.size() : this.specials.indexOf(special4);
    }

    private int fractionalEnd() {
        Special special = this.exponent;
        if (special != null) {
            this.afterFractional = special;
        } else {
            Special special2 = this.numerator;
            if (special2 != null) {
                this.afterInteger = special2;
            } else {
                this.afterFractional = null;
            }
        }
        Special special3 = this.afterFractional;
        return special3 == null ? this.specials.size() : this.specials.indexOf(special3);
    }

    /* JADX WARN: Removed duplicated region for block: B:77:0x016c  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x0174  */
    @Override // org.apache.poi.ss.format.CellFormatter
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void formatValue(java.lang.StringBuffer r19, java.lang.Object r20) {
        /*
            Method dump skipped, instructions count: 396
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.poi.ss.format.CellNumberFormatter.formatValue(java.lang.StringBuffer, java.lang.Object):void");
    }

    private void writeScientific(double d, StringBuffer stringBuffer, Set<StringMod> set) {
        StringBuffer stringBuffer2 = new StringBuffer();
        FieldPosition fieldPosition = new FieldPosition(1);
        this.decimalFmt.format(d, stringBuffer2, fieldPosition);
        writeInteger(stringBuffer2, stringBuffer, this.integerSpecials, set, this.integerCommas);
        writeFractional(stringBuffer2, stringBuffer);
        int endIndex = fieldPosition.getEndIndex() + 1;
        char cCharAt = stringBuffer2.charAt(endIndex);
        if (cCharAt != '-') {
            stringBuffer2.insert(endIndex, '+');
            cCharAt = '+';
        }
        Special next = this.exponentSpecials.listIterator(1).next();
        char c = next.ch;
        if (cCharAt == '-' || c == '+') {
            set.add(replaceMod(next, true, next, true, cCharAt));
        } else {
            set.add(deleteMod(next, true, next, true));
        }
        writeInteger(new StringBuffer(stringBuffer2.substring(endIndex + 1)), stringBuffer, this.exponentDigitSpecials, set, false);
    }

    private void writeFraction(double d, StringBuffer stringBuffer, double d2, StringBuffer stringBuffer2, Set<StringMod> set) {
        double d3;
        int numerator;
        int denominator = 1;
        if (this.improperFraction) {
            d3 = 0.0d;
        } else {
            if (d2 == 0.0d && !hasChar('0', this.numeratorSpecials)) {
                writeInteger(stringBuffer, stringBuffer2, this.integerSpecials, set, false);
                List<Special> list = this.integerSpecials;
                Special special = list.get(list.size() - 1);
                List<Special> list2 = this.denominatorSpecials;
                Special special2 = list2.get(list2.size() - 1);
                if (hasChar('?', this.integerSpecials, this.numeratorSpecials, this.denominatorSpecials)) {
                    set.add(replaceMod(special, false, special2, true, TokenParser.SP));
                    return;
                } else {
                    set.add(deleteMod(special, false, special2, true));
                    return;
                }
            }
            boolean z = d == 0.0d && d2 == 0.0d;
            boolean z2 = d2 != 0.0d || hasChar('0', this.numeratorSpecials);
            boolean z3 = z && (hasOnly('#', this.integerSpecials) || !hasChar('0', this.numeratorSpecials));
            boolean z4 = !z && d == 0.0d && z2 && !hasChar('0', this.integerSpecials);
            if (z3 || z4) {
                List<Special> list3 = this.integerSpecials;
                Special special3 = list3.get(list3.size() - 1);
                if (hasChar('?', this.integerSpecials, this.numeratorSpecials)) {
                    set.add(replaceMod(special3, true, this.numerator, false, TokenParser.SP));
                } else {
                    set.add(deleteMod(special3, true, this.numerator, false));
                }
            } else {
                writeInteger(stringBuffer, stringBuffer2, this.integerSpecials, set, false);
            }
            d3 = 0.0d;
        }
        if (d2 != d3) {
            try {
                if (this.improperFraction && d2 % 1.0d == d3) {
                    numerator = (int) Math.round(d2);
                } else {
                    Fraction fraction = new Fraction(d2, this.maxDenominator);
                    numerator = fraction.getNumerator();
                    denominator = fraction.getDenominator();
                }
            } catch (RuntimeException e) {
                e.printStackTrace();
                return;
            }
        } else {
            numerator = (int) Math.round(d2);
        }
        if (this.improperFraction) {
            numerator = (int) (numerator + Math.round(denominator * d));
        }
        writeSingleInteger(this.numeratorFmt, numerator, stringBuffer2, this.numeratorSpecials, set);
        writeSingleInteger(this.denominatorFmt, denominator, stringBuffer2, this.denominatorSpecials, set);
    }

    private static boolean hasChar(char c, List<Special>... listArr) {
        for (List<Special> list : listArr) {
            Iterator<Special> it2 = list.iterator();
            while (it2.hasNext()) {
                if (it2.next().ch == c) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean hasOnly(char c, List<Special>... listArr) {
        for (List<Special> list : listArr) {
            Iterator<Special> it2 = list.iterator();
            while (it2.hasNext()) {
                if (it2.next().ch != c) {
                    return false;
                }
            }
        }
        return true;
    }

    private void writeSingleInteger(String str, int i, StringBuffer stringBuffer, List<Special> list, Set<StringMod> set) {
        StringBuffer stringBuffer2 = new StringBuffer();
        new Formatter(stringBuffer2).format(LOCALE, str, Integer.valueOf(i));
        writeInteger(stringBuffer2, stringBuffer, list, set, false);
    }

    private void writeInteger(StringBuffer stringBuffer, StringBuffer stringBuffer2, List<Special> list, Set<StringMod> set, boolean z) {
        boolean z2;
        char cCharAt;
        int length;
        int iIndexOf = stringBuffer.indexOf(".") - 1;
        if (iIndexOf < 0) {
            if (this.exponent != null && list == this.integerSpecials) {
                length = stringBuffer.indexOf(DateFormat.ABBR_WEEKDAY);
            } else {
                length = stringBuffer.length();
            }
            iIndexOf = length - 1;
        }
        int i = 0;
        while (i < iIndexOf && ((cCharAt = stringBuffer.charAt(i)) == '0' || cCharAt == ',')) {
            i++;
        }
        ListIterator<Special> listIterator = list.listIterator(list.size());
        Special special = null;
        int i2 = 0;
        while (listIterator.hasPrevious()) {
            char cCharAt2 = iIndexOf >= 0 ? stringBuffer.charAt(iIndexOf) : '0';
            Special specialPrevious = listIterator.previous();
            boolean z3 = z && i2 > 0 && i2 % 3 == 0;
            if (cCharAt2 != '0' || specialPrevious.ch == '0' || specialPrevious.ch == '?' || iIndexOf >= i) {
                z2 = specialPrevious.ch == '?' && iIndexOf < i;
                int i3 = specialPrevious.pos;
                if (z2) {
                    cCharAt2 = TokenParser.SP;
                }
                stringBuffer2.setCharAt(i3, cCharAt2);
                special = specialPrevious;
            } else {
                z2 = false;
            }
            if (z3) {
                set.add(insertMod(specialPrevious, z2 ? " " : ConstantsBase.PROPERTIES_PARAMS_SEPARATOR, 2));
            }
            i2++;
            iIndexOf--;
        }
        new StringBuffer();
        if (iIndexOf >= 0) {
            int i4 = iIndexOf + 1;
            StringBuffer stringBuffer3 = new StringBuffer(stringBuffer.substring(0, i4));
            if (z) {
                while (i4 > 0) {
                    if (i2 > 0 && i2 % 3 == 0) {
                        stringBuffer3.insert(i4, ',');
                    }
                    i2++;
                    i4--;
                }
            }
            set.add(insertMod(special, stringBuffer3, 1));
        }
    }

    private void writeFractional(StringBuffer stringBuffer, StringBuffer stringBuffer2) {
        int length;
        if (this.fractionalSpecials.size() > 0) {
            int iIndexOf = stringBuffer.indexOf(".") + 1;
            if (this.exponent != null) {
                length = stringBuffer.indexOf("e");
            } else {
                length = stringBuffer.length();
            }
            do {
                length--;
                if (length <= iIndexOf) {
                    break;
                }
            } while (stringBuffer.charAt(length) == '0');
            ListIterator<Special> listIterator = this.fractionalSpecials.listIterator();
            while (listIterator.hasNext()) {
                Special next = listIterator.next();
                char cCharAt = stringBuffer.charAt(iIndexOf);
                if (cCharAt != '0' || next.ch == '0' || iIndexOf < length) {
                    stringBuffer2.setCharAt(next.pos, cCharAt);
                } else if (next.ch == '?') {
                    stringBuffer2.setCharAt(next.pos, TokenParser.SP);
                }
                iIndexOf++;
            }
        }
    }

    @Override // org.apache.poi.ss.format.CellFormatter
    public void simpleValue(StringBuffer stringBuffer, Object obj) {
        SIMPLE_NUMBER.formatValue(stringBuffer, obj);
    }

    private static class Fraction {
        private final int denominator;
        private final int numerator;

        /* JADX WARN: Code restructure failed: missing block: B:32:0x0102, code lost:
        
            throw new java.lang.RuntimeException("Overflow trying to convert " + r40 + " to fraction (" + r5 + r8 + r11 + r7);
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        private Fraction(double r40, double r42, int r44, int r45) {
            /*
                Method dump skipped, instructions count: 302
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: org.apache.poi.ss.format.CellNumberFormatter.Fraction.<init>(double, double, int, int):void");
        }

        public Fraction(double d, int i) {
            this(d, 0.0d, i, 100);
        }

        public int getDenominator() {
            return this.denominator;
        }

        public int getNumerator() {
            return this.numerator;
        }
    }
}
