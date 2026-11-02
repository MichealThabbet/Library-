package org.apache.poi.ss.format;

import it.piapostolos.android.pinotes.R2;
import java.text.AttributedCharacterIterator;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.regex.Matcher;
import org.apache.poi.ss.format.CellFormatPart;

/* loaded from: classes3.dex */
public class CellDateFormatter extends CellFormatter {
    private static final Date EXCEL_EPOCH_DATE;
    private static final long EXCEL_EPOCH_TIME;
    private static final CellFormatter SIMPLE_DATE = new CellDateFormatter("mm/d/y");
    private boolean amPmUpper;
    private final DateFormat dateFmt;
    private String sFmt;
    private boolean showAmPm;
    private boolean showM;

    static {
        Calendar calendar = Calendar.getInstance();
        calendar.set(R2.dimen.mtrl_extended_fab_end_padding_icon, 0, 1, 0, 0, 0);
        EXCEL_EPOCH_DATE = calendar.getTime();
        EXCEL_EPOCH_TIME = calendar.getTimeInMillis();
    }

    private class DatePartHandler implements CellFormatPart.PartHandler {
        private int hLen;
        private int hStart;
        private int mLen;
        private int mStart;

        private DatePartHandler() {
            this.mStart = -1;
            this.hStart = -1;
        }

        @Override // org.apache.poi.ss.format.CellFormatPart.PartHandler
        public String handlePart(Matcher matcher, String str, CellFormatType cellFormatType, StringBuffer stringBuffer) {
            int length = stringBuffer.length();
            switch (str.charAt(0)) {
                case '0':
                    this.mStart = -1;
                    int length2 = str.length();
                    CellDateFormatter.this.sFmt = "%0" + (length2 + 2) + "." + length2 + "f";
                    return str.replace('0', 'S');
                case 'A':
                case 'P':
                case 'a':
                case 'p':
                    if (str.length() <= 1) {
                        return null;
                    }
                    this.mStart = -1;
                    CellDateFormatter.this.showAmPm = true;
                    CellDateFormatter.this.showM = Character.toLowerCase(str.charAt(1)) == 'm';
                    CellDateFormatter cellDateFormatter = CellDateFormatter.this;
                    cellDateFormatter.amPmUpper = cellDateFormatter.showM || Character.isUpperCase(str.charAt(0));
                    return "a";
                case 'D':
                case 'd':
                    this.mStart = -1;
                    if (str.length() <= 2) {
                        return str.toLowerCase();
                    }
                    return str.toLowerCase().replace('d', 'E');
                case 'H':
                case 'h':
                    this.mStart = -1;
                    this.hStart = length;
                    this.hLen = str.length();
                    return str.toLowerCase();
                case 'M':
                case 'm':
                    this.mStart = length;
                    this.mLen = str.length();
                    return str.toUpperCase();
                case 'S':
                case 's':
                    if (this.mStart >= 0) {
                        for (int i = 0; i < this.mLen; i++) {
                            stringBuffer.setCharAt(this.mStart + i, 'm');
                        }
                        this.mStart = -1;
                    }
                    return str.toLowerCase();
                case 'Y':
                case 'y':
                    this.mStart = -1;
                    if (str.length() == 3) {
                        str = "yyyy";
                    }
                    return str.toLowerCase();
                default:
                    return null;
            }
        }

        public void finish(StringBuffer stringBuffer) {
            if (this.hStart < 0 || CellDateFormatter.this.showAmPm) {
                return;
            }
            for (int i = 0; i < this.hLen; i++) {
                stringBuffer.setCharAt(this.hStart + i, 'H');
            }
        }
    }

    public CellDateFormatter(String str) {
        super(str);
        DatePartHandler datePartHandler = new DatePartHandler();
        StringBuffer format = CellFormatPart.parseFormat(str, CellFormatType.DATE, datePartHandler);
        datePartHandler.finish(format);
        this.dateFmt = new SimpleDateFormat(format.toString());
    }

    @Override // org.apache.poi.ss.format.CellFormatter
    public void formatValue(StringBuffer stringBuffer, Object obj) {
        if (obj == null) {
            obj = Double.valueOf(0.0d);
        }
        if (obj instanceof Number) {
            double dDoubleValue = ((Number) obj).doubleValue();
            if (dDoubleValue == 0.0d) {
                obj = EXCEL_EPOCH_DATE;
            } else {
                obj = new Date((long) (EXCEL_EPOCH_TIME + dDoubleValue));
            }
        }
        AttributedCharacterIterator toCharacterIterator = this.dateFmt.formatToCharacterIterator(obj);
        toCharacterIterator.first();
        boolean z = false;
        boolean z2 = false;
        for (char cFirst = toCharacterIterator.first(); cFirst != 65535; cFirst = toCharacterIterator.next()) {
            if (toCharacterIterator.getAttribute(DateFormat.Field.MILLISECOND) != null) {
                if (!z) {
                    int length = stringBuffer.length();
                    new Formatter(stringBuffer).format(LOCALE, this.sFmt, Double.valueOf((((Date) obj).getTime() % 1000) / 1000.0d));
                    stringBuffer.delete(length, length + 2);
                    z = true;
                }
            } else if (toCharacterIterator.getAttribute(DateFormat.Field.AM_PM) == null) {
                stringBuffer.append(cFirst);
            } else if (!z2) {
                if (this.showAmPm) {
                    if (this.amPmUpper) {
                        stringBuffer.append(Character.toUpperCase(cFirst));
                        if (this.showM) {
                            stringBuffer.append('M');
                        }
                    } else {
                        stringBuffer.append(Character.toLowerCase(cFirst));
                        if (this.showM) {
                            stringBuffer.append('m');
                        }
                    }
                }
                z2 = true;
            }
        }
    }

    @Override // org.apache.poi.ss.format.CellFormatter
    public void simpleValue(StringBuffer stringBuffer, Object obj) {
        SIMPLE_DATE.formatValue(stringBuffer, obj);
    }
}
