package org.apache.poi.ss.usermodel;

import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/* loaded from: classes3.dex */
public class ExcelStyleDateFormatter extends SimpleDateFormat {
    public static final char HH_BRACKET_SYMBOL = 57361;
    public static final char H_BRACKET_SYMBOL = 57360;
    public static final char MMMMM_START_SYMBOL = 57345;
    public static final char MMMMM_TRUNCATE_SYMBOL = 57346;
    public static final char MM_BRACKET_SYMBOL = 57363;
    public static final char M_BRACKET_SYMBOL = 57362;
    public static final char SS_BRACKET_SYMBOL = 57365;
    public static final char S_BRACKET_SYMBOL = 57364;
    private double dateToBeFormatted;
    private DecimalFormat format1digit;
    private DecimalFormat format2digits;

    public ExcelStyleDateFormatter() {
        this.format1digit = new DecimalFormat("0");
        this.format2digits = new DecimalFormat("00");
        DataFormatter.setExcelStyleRoundingMode(this.format1digit);
        DataFormatter.setExcelStyleRoundingMode(this.format2digits);
        this.dateToBeFormatted = 0.0d;
    }

    public ExcelStyleDateFormatter(String str) {
        super(processFormatPattern(str));
        this.format1digit = new DecimalFormat("0");
        this.format2digits = new DecimalFormat("00");
        DataFormatter.setExcelStyleRoundingMode(this.format1digit);
        DataFormatter.setExcelStyleRoundingMode(this.format2digits);
        this.dateToBeFormatted = 0.0d;
    }

    public ExcelStyleDateFormatter(String str, DateFormatSymbols dateFormatSymbols) {
        super(processFormatPattern(str), dateFormatSymbols);
        this.format1digit = new DecimalFormat("0");
        this.format2digits = new DecimalFormat("00");
        DataFormatter.setExcelStyleRoundingMode(this.format1digit);
        DataFormatter.setExcelStyleRoundingMode(this.format2digits);
        this.dateToBeFormatted = 0.0d;
    }

    public ExcelStyleDateFormatter(String str, Locale locale) {
        super(processFormatPattern(str), locale);
        this.format1digit = new DecimalFormat("0");
        this.format2digits = new DecimalFormat("00");
        DataFormatter.setExcelStyleRoundingMode(this.format1digit);
        DataFormatter.setExcelStyleRoundingMode(this.format2digits);
        this.dateToBeFormatted = 0.0d;
    }

    private static String processFormatPattern(String str) {
        return str.replaceAll("MMMMM", "\ue001MMM\ue002").replaceAll("\\[H\\]", String.valueOf(H_BRACKET_SYMBOL)).replaceAll("\\[HH\\]", String.valueOf(HH_BRACKET_SYMBOL)).replaceAll("\\[m\\]", String.valueOf(M_BRACKET_SYMBOL)).replaceAll("\\[mm\\]", String.valueOf(MM_BRACKET_SYMBOL)).replaceAll("\\[s\\]", String.valueOf(S_BRACKET_SYMBOL)).replaceAll("\\[ss\\]", String.valueOf(SS_BRACKET_SYMBOL));
    }

    public void setDateToBeFormatted(double d) {
        this.dateToBeFormatted = d;
    }

    @Override // java.text.SimpleDateFormat, java.text.DateFormat
    public StringBuffer format(Date date, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        String string = super.format(date, stringBuffer, fieldPosition).toString();
        if (string.indexOf(57345) != -1) {
            string = string.replaceAll("\ue001(\\w)\\w+\ue002", "$1");
        }
        if (string.indexOf(57360) != -1 || string.indexOf(57361) != -1) {
            double d = this.dateToBeFormatted * 24.0d;
            string = string.replaceAll(String.valueOf(H_BRACKET_SYMBOL), this.format1digit.format(d)).replaceAll(String.valueOf(HH_BRACKET_SYMBOL), this.format2digits.format(d));
        }
        if (string.indexOf(57362) != -1 || string.indexOf(57363) != -1) {
            double d2 = this.dateToBeFormatted * 24.0d * 60.0d;
            string = string.replaceAll(String.valueOf(M_BRACKET_SYMBOL), this.format1digit.format(d2)).replaceAll(String.valueOf(MM_BRACKET_SYMBOL), this.format2digits.format(d2));
        }
        if (string.indexOf(57364) != -1 || string.indexOf(57365) != -1) {
            double d3 = this.dateToBeFormatted * 24.0d * 60.0d * 60.0d;
            string = string.replaceAll(String.valueOf(S_BRACKET_SYMBOL), this.format1digit.format(d3)).replaceAll(String.valueOf(SS_BRACKET_SYMBOL), this.format2digits.format(d3));
        }
        return new StringBuffer(string);
    }
}
