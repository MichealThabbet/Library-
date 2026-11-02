package com.ibm.icu.text;

import com.ibm.icu.text.DateFormat;
import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.ChineseCalendar;
import com.ibm.icu.util.TimeZone;
import com.ibm.icu.util.ULocale;
import java.io.InvalidObjectException;
import java.text.FieldPosition;
import java.util.Locale;

@Deprecated
/* loaded from: classes2.dex */
public class ChineseDateFormat extends SimpleDateFormat {
    static final long serialVersionUID = -4610300753104099899L;

    @Deprecated
    public ChineseDateFormat(String str, Locale locale) {
        this(str, ULocale.forLocale(locale));
    }

    @Deprecated
    public ChineseDateFormat(String str, ULocale uLocale) {
        this(str, null, uLocale);
    }

    @Deprecated
    public ChineseDateFormat(String str, String str2, ULocale uLocale) {
        super(str, new ChineseDateFormatSymbols(uLocale), new ChineseCalendar(TimeZone.getDefault(), uLocale), uLocale, true, str2);
    }

    @Override // com.ibm.icu.text.SimpleDateFormat
    @Deprecated
    protected void subFormat(StringBuffer stringBuffer, char c, int i, int i2, int i3, DisplayContext displayContext, FieldPosition fieldPosition, char c2, Calendar calendar) {
        super.subFormat(stringBuffer, c, i, i2, i3, displayContext, fieldPosition, c2, calendar);
    }

    @Override // com.ibm.icu.text.SimpleDateFormat
    @Deprecated
    protected int subParse(String str, int i, char c, int i2, boolean z, boolean z2, boolean[] zArr, Calendar calendar) {
        return super.subParse(str, i, c, i2, z, z2, zArr, calendar);
    }

    @Override // com.ibm.icu.text.SimpleDateFormat
    @Deprecated
    protected DateFormat.Field patternCharToDateFormatField(char c) {
        return super.patternCharToDateFormatField(c);
    }

    @Deprecated
    public static class Field extends DateFormat.Field {

        @Deprecated
        public static final Field IS_LEAP_MONTH = new Field("is leap month", 22);
        private static final long serialVersionUID = -5102130532751400330L;

        @Deprecated
        protected Field(String str, int i) {
            super(str, i);
        }

        @Deprecated
        public static DateFormat.Field ofCalendarField(int i) {
            if (i == 22) {
                return IS_LEAP_MONTH;
            }
            return DateFormat.Field.ofCalendarField(i);
        }

        @Override // com.ibm.icu.text.DateFormat.Field, java.text.AttributedCharacterIterator.Attribute
        @Deprecated
        protected Object readResolve() throws InvalidObjectException {
            if (getClass() != Field.class) {
                throw new InvalidObjectException("A subclass of ChineseDateFormat.Field must implement readResolve.");
            }
            if (getName().equals(IS_LEAP_MONTH.getName())) {
                return IS_LEAP_MONTH;
            }
            throw new InvalidObjectException("Unknown attribute name.");
        }
    }
}
