package org.ocpsoft.prettytime.i18n;

import java.util.ListResourceBundle;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.ocpsoft.prettytime.Duration;
import org.ocpsoft.prettytime.TimeFormat;
import org.ocpsoft.prettytime.TimeUnit;
import org.ocpsoft.prettytime.impl.TimeFormatProvider;

/* loaded from: classes3.dex */
public class Resources_ja extends ListResourceBundle implements TimeFormatProvider {
    private static final Object[][] OBJECTS = {new Object[]{"CenturyPattern", "%n%u"}, new Object[]{"CenturyFuturePrefix", "今から"}, new Object[]{"CenturyFutureSuffix", "世紀にもわたっ"}, new Object[]{"CenturyPastPrefix", ""}, new Object[]{"CenturyPastSuffix", "世紀前"}, new Object[]{"CenturySingularName", ""}, new Object[]{"CenturyPluralName", ""}, new Object[]{"DayPattern", "%n%u"}, new Object[]{"DayFuturePrefix", "今から"}, new Object[]{"DayFutureSuffix", "間"}, new Object[]{"DayPastPrefix", ""}, new Object[]{"DayPastSuffix", "前"}, new Object[]{"DaySingularName", "日"}, new Object[]{"DayPluralName", "日"}, new Object[]{"DecadePattern", "%n%u"}, new Object[]{"DecadeFuturePrefix", "今から"}, new Object[]{"DecadeFutureSuffix", "年間"}, new Object[]{"DecadePastPrefix", ""}, new Object[]{"DecadePastSuffix", "年前"}, new Object[]{"DecadeSingularName", ""}, new Object[]{"DecadePluralName", ""}, new Object[]{"HourPattern", "%n%u"}, new Object[]{"HourFuturePrefix", "今から"}, new Object[]{"HourFutureSuffix", ""}, new Object[]{"HourPastPrefix", ""}, new Object[]{"HourPastSuffix", "前"}, new Object[]{"HourSingularName", "時間"}, new Object[]{"HourPluralName", "時間"}, new Object[]{"JustNowPattern", "%u"}, new Object[]{"JustNowFuturePrefix", ""}, new Object[]{"JustNowFutureSuffix", "今からすぐ"}, new Object[]{"JustNowPastPrefix", ""}, new Object[]{"JustNowPastSuffix", "さっき"}, new Object[]{"JustNowSingularName", ""}, new Object[]{"JustNowPluralName", ""}, new Object[]{"MillenniumPattern", "%n %u"}, new Object[]{"MillenniumFuturePrefix", ""}, new Object[]{"MillenniumFutureSuffix", "今から"}, new Object[]{"MillenniumPastPrefix", ""}, new Object[]{"MillenniumPastSuffix", "前"}, new Object[]{"MillenniumSingularName", "千年"}, new Object[]{"MillenniumPluralName", "千年"}, new Object[]{"MillisecondPattern", "%n%u"}, new Object[]{"MillisecondFuturePrefix", ""}, new Object[]{"MillisecondFutureSuffix", "今から"}, new Object[]{"MillisecondPastPrefix", ""}, new Object[]{"MillisecondPastSuffix", "前"}, new Object[]{"MillisecondSingularName", "ミリ秒"}, new Object[]{"MillisecondPluralName", "ミリ秒"}, new Object[]{"MinutePattern", "%n%u"}, new Object[]{"MinuteFuturePrefix", "今から"}, new Object[]{"MinuteFutureSuffix", ""}, new Object[]{"MinutePastPrefix", ""}, new Object[]{"MinutePastSuffix", "前"}, new Object[]{"MinuteSingularName", "分"}, new Object[]{"MinutePluralName", "分"}, new Object[]{"MonthPattern", "%n%u"}, new Object[]{"MonthFuturePrefix", "今から"}, new Object[]{"MonthFutureSuffix", ""}, new Object[]{"MonthPastPrefix", ""}, new Object[]{"MonthPastSuffix", "前"}, new Object[]{"MonthSingularName", "ヶ月"}, new Object[]{"MonthPluralName", "ヶ月"}, new Object[]{"SecondPattern", "%n%u"}, new Object[]{"SecondFuturePrefix", ""}, new Object[]{"SecondFutureSuffix", "今から"}, new Object[]{"SecondPastPrefix", ""}, new Object[]{"SecondPastSuffix", "前"}, new Object[]{"SecondSingularName", "秒"}, new Object[]{"SecondPluralName", "秒"}, new Object[]{"WeekPattern", "%n%u"}, new Object[]{"WeekFuturePrefix", "今から"}, new Object[]{"WeekFutureSuffix", "週間"}, new Object[]{"WeekPastPrefix", ""}, new Object[]{"WeekPastSuffix", "週間前"}, new Object[]{"WeekSingularName", ""}, new Object[]{"WeekPluralName", ""}, new Object[]{"YearPattern", "%n%u"}, new Object[]{"YearFuturePrefix", ""}, new Object[]{"YearFutureSuffix", "年後の"}, new Object[]{"YearPastPrefix", ""}, new Object[]{"YearPastSuffix", "年前"}, new Object[]{"YearSingularName", ""}, new Object[]{"YearPluralName", ""}, new Object[]{"AbstractTimeUnitPattern", ""}, new Object[]{"AbstractTimeUnitFuturePrefix", ""}, new Object[]{"AbstractTimeUnitFutureSuffix", ""}, new Object[]{"AbstractTimeUnitPastPrefix", ""}, new Object[]{"AbstractTimeUnitPastSuffix", ""}, new Object[]{"AbstractTimeUnitSingularName", ""}, new Object[]{"AbstractTimeUnitPluralName", ""}};
    private volatile ConcurrentMap<TimeUnit, TimeFormat> formatMap = new ConcurrentHashMap();

    @Override // java.util.ListResourceBundle
    public Object[][] getContents() {
        return OBJECTS;
    }

    @Override // org.ocpsoft.prettytime.impl.TimeFormatProvider
    public TimeFormat getFormatFor(TimeUnit timeUnit) {
        if (!this.formatMap.containsKey(timeUnit)) {
            this.formatMap.putIfAbsent(timeUnit, new JaTimeFormat(this, timeUnit));
        }
        return this.formatMap.get(timeUnit);
    }

    private static class JaTimeFormat implements TimeFormat {
        private static final String NEGATIVE = "-";
        public static final String QUANTITY = "%n";
        public static final String SIGN = "%s";
        public static final String UNIT = "%u";
        private final ResourceBundle bundle;
        private String singularName = "";
        private String pluralName = "";
        private String futureSingularName = "";
        private String futurePluralName = "";
        private String pastSingularName = "";
        private String pastPluralName = "";
        private String pattern = "";
        private String futurePrefix = "";
        private String futureSuffix = "";
        private String pastPrefix = "";
        private String pastSuffix = "";
        private int roundingTolerance = 50;

        public JaTimeFormat(ResourceBundle resourceBundle, TimeUnit timeUnit) {
            this.bundle = resourceBundle;
            setPattern(resourceBundle.getString(getUnitName(timeUnit) + "Pattern"));
            setFuturePrefix(resourceBundle.getString(getUnitName(timeUnit) + "FuturePrefix"));
            setFutureSuffix(resourceBundle.getString(getUnitName(timeUnit) + "FutureSuffix"));
            setPastPrefix(resourceBundle.getString(getUnitName(timeUnit) + "PastPrefix"));
            setPastSuffix(resourceBundle.getString(getUnitName(timeUnit) + "PastSuffix"));
            setSingularName(resourceBundle.getString(getUnitName(timeUnit) + "SingularName"));
            setPluralName(resourceBundle.getString(getUnitName(timeUnit) + "PluralName"));
            try {
                setFuturePluralName(resourceBundle.getString(getUnitName(timeUnit) + "FuturePluralName"));
            } catch (Exception unused) {
            }
            try {
                setFutureSingularName(resourceBundle.getString(getUnitName(timeUnit) + "FutureSingularName"));
            } catch (Exception unused2) {
            }
            try {
                setPastPluralName(resourceBundle.getString(getUnitName(timeUnit) + "PastPluralName"));
            } catch (Exception unused3) {
            }
            try {
                setPastSingularName(resourceBundle.getString(getUnitName(timeUnit) + "PastSingularName"));
            } catch (Exception unused4) {
            }
        }

        private String getUnitName(TimeUnit timeUnit) {
            return timeUnit.getClass().getSimpleName();
        }

        @Override // org.ocpsoft.prettytime.TimeFormat
        public String format(Duration duration) {
            return format(duration, true);
        }

        @Override // org.ocpsoft.prettytime.TimeFormat
        public String formatUnrounded(Duration duration) {
            return format(duration, false);
        }

        private String format(Duration duration, boolean z) {
            return applyPattern(getSign(duration), getGramaticallyCorrectName(duration, z), getQuantity(duration, z));
        }

        private String applyPattern(String str, String str2, long j) {
            return getPattern(j).replaceAll("%s", str).replaceAll("%n", String.valueOf(j)).replaceAll("%u", str2);
        }

        protected String getPattern(long j) {
            return this.pattern;
        }

        public String getPattern() {
            return this.pattern;
        }

        protected long getQuantity(Duration duration, boolean z) {
            return Math.abs(z ? duration.getQuantityRounded(this.roundingTolerance) : duration.getQuantity());
        }

        protected String getGramaticallyCorrectName(Duration duration, boolean z) {
            return (Math.abs(getQuantity(duration, z)) == 0 || Math.abs(getQuantity(duration, z)) > 1) ? getPluralName(duration) : getSingularName(duration);
        }

        private String getSign(Duration duration) {
            return duration.getQuantity() < 0 ? "-" : "";
        }

        private String getSingularName(Duration duration) {
            String str;
            String str2;
            if (duration.isInFuture() && (str2 = this.futureSingularName) != null && str2.length() > 0) {
                return this.futureSingularName;
            }
            if (duration.isInPast() && (str = this.pastSingularName) != null && str.length() > 0) {
                return this.pastSingularName;
            }
            return this.singularName;
        }

        private String getPluralName(Duration duration) {
            if (duration.isInFuture() && this.futurePluralName != null && this.futureSingularName.length() > 0) {
                return this.futurePluralName;
            }
            if (duration.isInPast() && this.pastPluralName != null && this.pastSingularName.length() > 0) {
                return this.pastPluralName;
            }
            return this.pluralName;
        }

        @Override // org.ocpsoft.prettytime.TimeFormat
        public String decorate(Duration duration, String str) {
            StringBuilder sb = new StringBuilder();
            if (duration.isInPast()) {
                sb.append(this.pastPrefix);
                sb.append(str);
                sb.append(this.pastSuffix);
            } else {
                sb.append(this.futurePrefix);
                sb.append(str);
                sb.append(this.futureSuffix);
            }
            return sb.toString().replaceAll("\\s+", " ").trim();
        }

        @Override // org.ocpsoft.prettytime.TimeFormat
        public String decorateUnrounded(Duration duration, String str) {
            return decorate(duration, str);
        }

        public JaTimeFormat setPattern(String str) {
            this.pattern = str;
            return this;
        }

        public JaTimeFormat setFuturePrefix(String str) {
            this.futurePrefix = str.trim();
            return this;
        }

        public JaTimeFormat setFutureSuffix(String str) {
            this.futureSuffix = str.trim();
            return this;
        }

        public JaTimeFormat setPastPrefix(String str) {
            this.pastPrefix = str.trim();
            return this;
        }

        public JaTimeFormat setPastSuffix(String str) {
            this.pastSuffix = str.trim();
            return this;
        }

        public JaTimeFormat setRoundingTolerance(int i) {
            this.roundingTolerance = i;
            return this;
        }

        public JaTimeFormat setSingularName(String str) {
            this.singularName = str;
            return this;
        }

        public JaTimeFormat setPluralName(String str) {
            this.pluralName = str;
            return this;
        }

        public JaTimeFormat setFutureSingularName(String str) {
            this.futureSingularName = str;
            return this;
        }

        public JaTimeFormat setFuturePluralName(String str) {
            this.futurePluralName = str;
            return this;
        }

        public JaTimeFormat setPastSingularName(String str) {
            this.pastSingularName = str;
            return this;
        }

        public JaTimeFormat setPastPluralName(String str) {
            this.pastPluralName = str;
            return this;
        }

        public String toString() {
            return "JaTimeFormat [pattern=" + this.pattern + ", futurePrefix=" + this.futurePrefix + ", futureSuffix=" + this.futureSuffix + ", pastPrefix=" + this.pastPrefix + ", pastSuffix=" + this.pastSuffix + ", roundingTolerance=" + this.roundingTolerance + "]";
        }
    }
}
