package com.ibm.icu.text;

import com.ibm.icu.impl.ICUResourceBundle;
import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.ChineseCalendar;
import com.ibm.icu.util.ULocale;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.NoSuchElementException;

@Deprecated
/* loaded from: classes2.dex */
public class ChineseDateFormatSymbols extends DateFormatSymbols {
    static final long serialVersionUID = 6827816119783952890L;
    String[] isLeapMonth;

    @Deprecated
    public ChineseDateFormatSymbols() {
        this(ULocale.getDefault(ULocale.Category.FORMAT));
    }

    @Deprecated
    public ChineseDateFormatSymbols(Locale locale) {
        super((Class<? extends Calendar>) ChineseCalendar.class, ULocale.forLocale(locale));
    }

    @Deprecated
    public ChineseDateFormatSymbols(ULocale uLocale) {
        super((Class<? extends Calendar>) ChineseCalendar.class, uLocale);
    }

    @Deprecated
    public ChineseDateFormatSymbols(Calendar calendar, Locale locale) {
        super((Class<? extends Calendar>) calendar.getClass(), locale);
    }

    @Deprecated
    public ChineseDateFormatSymbols(Calendar calendar, ULocale uLocale) {
        super((Class<? extends Calendar>) calendar.getClass(), uLocale);
    }

    @Deprecated
    public String getLeapMonth(int i) {
        return this.isLeapMonth[i];
    }

    @Override // com.ibm.icu.text.DateFormatSymbols
    @Deprecated
    protected void initializeData(ULocale uLocale, ICUResourceBundle iCUResourceBundle, String str) throws MissingResourceException, NoSuchElementException {
        super.initializeData(uLocale, iCUResourceBundle, str);
        initializeIsLeapMonth();
    }

    @Override // com.ibm.icu.text.DateFormatSymbols
    void initializeData(DateFormatSymbols dateFormatSymbols) {
        super.initializeData(dateFormatSymbols);
        if (dateFormatSymbols instanceof ChineseDateFormatSymbols) {
            this.isLeapMonth = ((ChineseDateFormatSymbols) dateFormatSymbols).isLeapMonth;
        } else {
            initializeIsLeapMonth();
        }
    }

    private void initializeIsLeapMonth() {
        String[] strArr = new String[2];
        this.isLeapMonth = strArr;
        strArr[0] = "";
        strArr[1] = this.leapMonthPatterns != null ? this.leapMonthPatterns[0].replace("{0}", "") : "";
    }
}
