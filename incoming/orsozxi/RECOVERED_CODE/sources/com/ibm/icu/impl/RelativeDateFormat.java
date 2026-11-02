package com.ibm.icu.impl;

import com.ibm.icu.impl.UResource;
import com.ibm.icu.lang.UCharacter;
import com.ibm.icu.text.BreakIterator;
import com.ibm.icu.text.DateFormat;
import com.ibm.icu.text.DisplayContext;
import com.ibm.icu.text.MessageFormat;
import com.ibm.icu.text.SimpleDateFormat;
import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.TimeZone;
import com.ibm.icu.util.ULocale;
import com.ibm.icu.util.UResourceBundle;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.MissingResourceException;

/* loaded from: classes2.dex */
public class RelativeDateFormat extends DateFormat {
    private static final long serialVersionUID = 1131984966440549435L;
    private MessageFormat fCombinedFormat;
    private DateFormat fDateFormat;
    private String fDatePattern;
    int fDateStyle;
    private SimpleDateFormat fDateTimeFormat;
    ULocale fLocale;
    private DateFormat fTimeFormat;
    private String fTimePattern;
    int fTimeStyle;
    private transient List<URelativeString> fDates = null;
    private boolean combinedFormatHasDateAtStart = false;
    private boolean capitalizationInfoIsSet = false;
    private boolean capitalizationOfRelativeUnitsForListOrMenu = false;
    private boolean capitalizationOfRelativeUnitsForStandAlone = false;
    private transient BreakIterator capitalizationBrkIter = null;

    public static class URelativeString {
        public int offset;
        public String string;

        URelativeString(int i, String str) {
            this.offset = i;
            this.string = str;
        }

        URelativeString(String str, String str2) {
            this.offset = Integer.parseInt(str);
            this.string = str2;
        }
    }

    public RelativeDateFormat(int i, int i2, ULocale uLocale, Calendar calendar) {
        this.fDateTimeFormat = null;
        this.fDatePattern = null;
        this.fTimePattern = null;
        this.calendar = calendar;
        this.fLocale = uLocale;
        this.fTimeStyle = i;
        this.fDateStyle = i2;
        if (i2 != -1) {
            DateFormat dateInstance = DateFormat.getDateInstance(i2 & (-129), uLocale);
            if (dateInstance instanceof SimpleDateFormat) {
                SimpleDateFormat simpleDateFormat = (SimpleDateFormat) dateInstance;
                this.fDateTimeFormat = simpleDateFormat;
                this.fDatePattern = simpleDateFormat.toPattern();
                int i3 = this.fTimeStyle;
                if (i3 != -1) {
                    DateFormat timeInstance = DateFormat.getTimeInstance(i3 & (-129), uLocale);
                    if (timeInstance instanceof SimpleDateFormat) {
                        this.fTimePattern = ((SimpleDateFormat) timeInstance).toPattern();
                    }
                }
            } else {
                throw new IllegalArgumentException("Can't create SimpleDateFormat for date style");
            }
        } else {
            DateFormat timeInstance2 = DateFormat.getTimeInstance(i & (-129), uLocale);
            if (timeInstance2 instanceof SimpleDateFormat) {
                SimpleDateFormat simpleDateFormat2 = (SimpleDateFormat) timeInstance2;
                this.fDateTimeFormat = simpleDateFormat2;
                this.fTimePattern = simpleDateFormat2.toPattern();
            } else {
                throw new IllegalArgumentException("Can't create SimpleDateFormat for time style");
            }
        }
        initializeCalendar(null, this.fLocale);
        loadDates();
        initializeCombinedFormat(this.calendar, this.fLocale);
    }

    @Override // com.ibm.icu.text.DateFormat
    public StringBuffer format(Calendar calendar, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        DisplayContext context = getContext(DisplayContext.Type.CAPITALIZATION);
        String stringForDay = this.fDateStyle != -1 ? getStringForDay(dayDifference(calendar)) : null;
        if (this.fDateTimeFormat != null) {
            if (stringForDay != null && this.fDatePattern != null && (this.fTimePattern == null || this.fCombinedFormat == null || this.combinedFormatHasDateAtStart)) {
                if (stringForDay.length() > 0 && UCharacter.isLowerCase(stringForDay.codePointAt(0)) && (context == DisplayContext.CAPITALIZATION_FOR_BEGINNING_OF_SENTENCE || ((context == DisplayContext.CAPITALIZATION_FOR_UI_LIST_OR_MENU && this.capitalizationOfRelativeUnitsForListOrMenu) || (context == DisplayContext.CAPITALIZATION_FOR_STANDALONE && this.capitalizationOfRelativeUnitsForStandAlone)))) {
                    if (this.capitalizationBrkIter == null) {
                        this.capitalizationBrkIter = BreakIterator.getSentenceInstance(this.fLocale);
                    }
                    stringForDay = UCharacter.toTitleCase(this.fLocale, stringForDay, this.capitalizationBrkIter, 768);
                }
                this.fDateTimeFormat.setContext(DisplayContext.CAPITALIZATION_NONE);
            } else {
                this.fDateTimeFormat.setContext(context);
            }
        }
        if (this.fDateTimeFormat != null && (this.fDatePattern != null || this.fTimePattern != null)) {
            String str = this.fDatePattern;
            if (str == null) {
                this.fDateTimeFormat.applyPattern(this.fTimePattern);
                this.fDateTimeFormat.format(calendar, stringBuffer, fieldPosition);
            } else if (this.fTimePattern != null) {
                if (stringForDay != null) {
                    str = "'" + stringForDay.replace("'", "''") + "'";
                }
                StringBuffer stringBuffer2 = new StringBuffer("");
                this.fCombinedFormat.format(new Object[]{this.fTimePattern, str}, stringBuffer2, new FieldPosition(0));
                this.fDateTimeFormat.applyPattern(stringBuffer2.toString());
                this.fDateTimeFormat.format(calendar, stringBuffer, fieldPosition);
            } else if (stringForDay != null) {
                stringBuffer.append(stringForDay);
            } else {
                this.fDateTimeFormat.applyPattern(str);
                this.fDateTimeFormat.format(calendar, stringBuffer, fieldPosition);
            }
        } else {
            DateFormat dateFormat = this.fDateFormat;
            if (dateFormat != null) {
                if (stringForDay != null) {
                    stringBuffer.append(stringForDay);
                } else {
                    dateFormat.format(calendar, stringBuffer, fieldPosition);
                }
            }
        }
        return stringBuffer;
    }

    @Override // com.ibm.icu.text.DateFormat
    public void parse(String str, Calendar calendar, ParsePosition parsePosition) {
        throw new UnsupportedOperationException("Relative Date parse is not implemented yet");
    }

    @Override // com.ibm.icu.text.DateFormat
    public void setContext(DisplayContext displayContext) {
        super.setContext(displayContext);
        if (!this.capitalizationInfoIsSet && (displayContext == DisplayContext.CAPITALIZATION_FOR_UI_LIST_OR_MENU || displayContext == DisplayContext.CAPITALIZATION_FOR_STANDALONE)) {
            initCapitalizationContextInfo(this.fLocale);
            this.capitalizationInfoIsSet = true;
        }
        if (this.capitalizationBrkIter == null) {
            if (displayContext == DisplayContext.CAPITALIZATION_FOR_BEGINNING_OF_SENTENCE || ((displayContext == DisplayContext.CAPITALIZATION_FOR_UI_LIST_OR_MENU && this.capitalizationOfRelativeUnitsForListOrMenu) || (displayContext == DisplayContext.CAPITALIZATION_FOR_STANDALONE && this.capitalizationOfRelativeUnitsForStandAlone))) {
                this.capitalizationBrkIter = BreakIterator.getSentenceInstance(this.fLocale);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getStringForDay(int i) {
        if (this.fDates == null) {
            loadDates();
        }
        for (URelativeString uRelativeString : this.fDates) {
            if (uRelativeString.offset == i) {
                return uRelativeString.string;
            }
        }
        return null;
    }

    private final class RelDateFmtDataSink extends UResource.Sink {
        private RelDateFmtDataSink() {
        }

        @Override // com.ibm.icu.impl.UResource.Sink
        public void put(UResource.Key key, UResource.Value value, boolean z) throws NumberFormatException {
            if (value.getType() == 3) {
                return;
            }
            UResource.Table table = value.getTable();
            for (int i = 0; table.getKeyAndValue(i, key, value); i++) {
                try {
                    int i2 = Integer.parseInt(key.toString());
                    if (RelativeDateFormat.this.getStringForDay(i2) == null) {
                        RelativeDateFormat.this.fDates.add(new URelativeString(i2, value.getString()));
                    }
                } catch (NumberFormatException unused) {
                    return;
                }
            }
        }
    }

    private synchronized void loadDates() {
        ICUResourceBundle iCUResourceBundle = (ICUResourceBundle) UResourceBundle.getBundleInstance(ICUData.ICU_BASE_NAME, this.fLocale);
        this.fDates = new ArrayList();
        iCUResourceBundle.getAllItemsWithFallback("fields/day/relative", new RelDateFmtDataSink());
    }

    private void initCapitalizationContextInfo(ULocale uLocale) {
        try {
            int[] intVector = ((ICUResourceBundle) UResourceBundle.getBundleInstance(ICUData.ICU_BASE_NAME, uLocale)).getWithFallback("contextTransforms/relative").getIntVector();
            if (intVector.length >= 2) {
                this.capitalizationOfRelativeUnitsForListOrMenu = intVector[0] != 0;
                this.capitalizationOfRelativeUnitsForStandAlone = intVector[1] != 0;
            }
        } catch (MissingResourceException unused) {
        }
    }

    private static int dayDifference(Calendar calendar) {
        Calendar calendar2 = (Calendar) calendar.clone();
        Date date = new Date(System.currentTimeMillis());
        calendar2.clear();
        calendar2.setTime(date);
        return calendar.get(20) - calendar2.get(20);
    }

    private Calendar initializeCalendar(TimeZone timeZone, ULocale uLocale) {
        if (this.calendar == null) {
            if (timeZone == null) {
                this.calendar = Calendar.getInstance(uLocale);
            } else {
                this.calendar = Calendar.getInstance(timeZone, uLocale);
            }
        }
        return this.calendar;
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x0074  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x007e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private com.ibm.icu.text.MessageFormat initializeCombinedFormat(com.ibm.icu.util.Calendar r5, com.ibm.icu.util.ULocale r6) {
        /*
            r4 = this;
            java.lang.String r0 = "com/ibm/icu/impl/data/icudt69b"
            com.ibm.icu.util.UResourceBundle r0 = com.ibm.icu.util.UResourceBundle.getBundleInstance(r0, r6)
            com.ibm.icu.impl.ICUResourceBundle r0 = (com.ibm.icu.impl.ICUResourceBundle) r0
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "calendar/"
            r1.append(r2)
            java.lang.String r2 = r5.getType()
            r1.append(r2)
            java.lang.String r2 = "/DateTimePatterns"
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            com.ibm.icu.impl.ICUResourceBundle r1 = r0.findWithFallback(r1)
            if (r1 != 0) goto L3a
            java.lang.String r5 = r5.getType()
            java.lang.String r2 = "gregorian"
            boolean r5 = r5.equals(r2)
            if (r5 != 0) goto L3a
            java.lang.String r5 = "calendar/gregorian/DateTimePatterns"
            com.ibm.icu.impl.ICUResourceBundle r1 = r0.findWithFallback(r5)
        L3a:
            if (r1 == 0) goto L83
            int r5 = r1.getSize()
            r0 = 9
            if (r5 >= r0) goto L45
            goto L83
        L45:
            int r5 = r1.getSize()
            r0 = 13
            r2 = 8
            if (r5 < r0) goto L68
            int r5 = r4.fDateStyle
            if (r5 < 0) goto L5a
            r0 = 3
            if (r5 > r0) goto L5a
            int r5 = r5 + 1
        L58:
            int r5 = r5 + r2
            goto L6a
        L5a:
            int r5 = r4.fDateStyle
            r0 = 128(0x80, float:1.8E-43)
            if (r5 < r0) goto L68
            r3 = 131(0x83, float:1.84E-43)
            if (r5 > r3) goto L68
            int r5 = r5 + 1
            int r5 = r5 - r0
            goto L58
        L68:
            r5 = 8
        L6a:
            com.ibm.icu.util.UResourceBundle r0 = r1.get(r5)
            int r0 = r0.getType()
            if (r0 != r2) goto L7e
            com.ibm.icu.util.UResourceBundle r5 = r1.get(r5)
            r0 = 0
            java.lang.String r5 = r5.getString(r0)
            goto L86
        L7e:
            java.lang.String r5 = r1.getString(r5)
            goto L86
        L83:
            java.lang.String r5 = "{1} {0}"
        L86:
            java.lang.String r0 = "{1}"
            boolean r0 = r5.startsWith(r0)
            r4.combinedFormatHasDateAtStart = r0
            com.ibm.icu.text.MessageFormat r0 = new com.ibm.icu.text.MessageFormat
            r0.<init>(r5, r6)
            r4.fCombinedFormat = r0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.impl.RelativeDateFormat.initializeCombinedFormat(com.ibm.icu.util.Calendar, com.ibm.icu.util.ULocale):com.ibm.icu.text.MessageFormat");
    }
}
