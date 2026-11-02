package org.ocpsoft.prettytime.i18n;

import java.util.ListResourceBundle;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.ocpsoft.prettytime.Duration;
import org.ocpsoft.prettytime.TimeFormat;
import org.ocpsoft.prettytime.TimeUnit;
import org.ocpsoft.prettytime.format.SimpleTimeFormat;
import org.ocpsoft.prettytime.impl.TimeFormatProvider;
import org.ocpsoft.prettytime.units.Day;

/* loaded from: classes3.dex */
public class Resources_fi extends ListResourceBundle implements TimeFormatProvider {
    private static Object[][] CONTENTS = {new Object[]{"JustNowPattern", "%u"}, new Object[]{"JustNowPastSingularName", "hetki"}, new Object[]{"JustNowFutureSingularName", "hetken"}, new Object[]{"JustNowPastSuffix", "sitten"}, new Object[]{"JustNowFutureSuffix", "päästä"}, new Object[]{"MillisecondPattern", "%u"}, new Object[]{"MillisecondPluralPattern", "%n %u"}, new Object[]{"MillisecondPastSingularName", "millisekunti"}, new Object[]{"MillisecondPastPluralName", "millisekuntia"}, new Object[]{"MillisecondFutureSingularName", "millisekunnin"}, new Object[]{"MillisecondPastSuffix", "sitten"}, new Object[]{"MillisecondFutureSuffix", "päästä"}, new Object[]{"SecondPattern", "%u"}, new Object[]{"SecondPluralPattern", "%n %u"}, new Object[]{"SecondPastSingularName", "sekunti"}, new Object[]{"SecondPastPluralName", "sekuntia"}, new Object[]{"SecondFutureSingularName", "sekunnin"}, new Object[]{"SecondPastSuffix", "sitten"}, new Object[]{"SecondFutureSuffix", "päästä"}, new Object[]{"MinutePattern", "%u"}, new Object[]{"MinutePluralPattern", "%n %u"}, new Object[]{"MinutePastSingularName", "minuutti"}, new Object[]{"MinutePastPluralName", "minuuttia"}, new Object[]{"MinuteFutureSingularName", "minuutin"}, new Object[]{"MinutePastSuffix", "sitten"}, new Object[]{"MinuteFutureSuffix", "päästä"}, new Object[]{"HourPattern", "%u"}, new Object[]{"HourPluralPattern", "%n %u"}, new Object[]{"HourPastSingularName", "tunti"}, new Object[]{"HourPastPluralName", "tuntia"}, new Object[]{"HourFutureSingularName", "tunnin"}, new Object[]{"HourPastSuffix", "sitten"}, new Object[]{"HourFutureSuffix", "päästä"}, new Object[]{"DayPattern", "%u"}, new Object[]{"DayPluralPattern", "%n %u"}, new Object[]{"DayPastSingularName", "eilen"}, new Object[]{"DayPastPluralName", "päivää"}, new Object[]{"DayFutureSingularName", "huomenna"}, new Object[]{"DayFuturePluralName", "päivän"}, new Object[]{"DayPastSuffix", "sitten"}, new Object[]{"DayFutureSuffix", "päästä"}, new Object[]{"WeekPattern", "%u"}, new Object[]{"WeekPluralPattern", "%n %u"}, new Object[]{"WeekPastSingularName", "viikko"}, new Object[]{"WeekPastPluralName", "viikkoa"}, new Object[]{"WeekFutureSingularName", "viikon"}, new Object[]{"WeekFuturePluralName", "viikon"}, new Object[]{"WeekPastSuffix", "sitten"}, new Object[]{"WeekFutureSuffix", "päästä"}, new Object[]{"MonthPattern", "%u"}, new Object[]{"MonthPluralPattern", "%n %u"}, new Object[]{"MonthPastSingularName", "kuukausi"}, new Object[]{"MonthPastPluralName", "kuukautta"}, new Object[]{"MonthFutureSingularName", "kuukauden"}, new Object[]{"MonthPastSuffix", "sitten"}, new Object[]{"MonthFutureSuffix", "päästä"}, new Object[]{"YearPattern", "%u"}, new Object[]{"YearPluralPattern", "%n %u"}, new Object[]{"YearPastSingularName", "vuosi"}, new Object[]{"YearPastPluralName", "vuotta"}, new Object[]{"YearFutureSingularName", "vuoden"}, new Object[]{"YearPastSuffix", "sitten"}, new Object[]{"YearFutureSuffix", "päästä"}, new Object[]{"DecadePattern", "%u"}, new Object[]{"DecadePluralPattern", "%n %u"}, new Object[]{"DecadePastSingularName", "vuosikymmen"}, new Object[]{"DecadePastPluralName", "vuosikymmentä"}, new Object[]{"DecadeFutureSingularName", "vuosikymmenen"}, new Object[]{"DecadePastSuffix", "sitten"}, new Object[]{"DecadeFutureSuffix", "päästä"}, new Object[]{"CenturyPattern", "%u"}, new Object[]{"CenturyPluralPattern", "%n %u"}, new Object[]{"CenturyPastSingularName", "vuosisata"}, new Object[]{"CenturyPastPluralName", "vuosisataa"}, new Object[]{"CenturyFutureSingularName", "vuosisadan"}, new Object[]{"CenturyPastSuffix", "sitten"}, new Object[]{"CenturyFutureSuffix", "päästä"}, new Object[]{"MillenniumPattern", "%u"}, new Object[]{"MillenniumPluralPattern", "%n %u"}, new Object[]{"MillenniumPastSingularName", "vuosituhat"}, new Object[]{"MillenniumPastPluralName", "vuosituhatta"}, new Object[]{"MillenniumFutureSingularName", "vuosituhannen"}, new Object[]{"MillenniumPastSuffix", "sitten"}, new Object[]{"MillenniumFutureSuffix", "päästä"}};
    private static final int tolerance = 50;
    private volatile ConcurrentMap<TimeUnit, TimeFormat> formatMap = new ConcurrentHashMap();

    @Override // org.ocpsoft.prettytime.impl.TimeFormatProvider
    public TimeFormat getFormatFor(TimeUnit timeUnit) {
        if (!this.formatMap.containsKey(timeUnit)) {
            this.formatMap.putIfAbsent(timeUnit, new FiTimeFormat(this, timeUnit));
        }
        return this.formatMap.get(timeUnit);
    }

    @Override // java.util.ListResourceBundle
    protected Object[][] getContents() {
        return CONTENTS;
    }

    private static class FiTimeFormat extends SimpleTimeFormat {
        private final ResourceBundle bundle;
        private String pastName = "";
        private String futureName = "";
        private String pastPluralName = "";
        private String futurePluralName = "";
        private String pluralPattern = "";

        public FiTimeFormat(ResourceBundle resourceBundle, TimeUnit timeUnit) {
            this.bundle = resourceBundle;
            if (resourceBundle.containsKey(getUnitName(timeUnit) + "PastSingularName")) {
                setPastName(this.bundle.getString(getUnitName(timeUnit) + "PastSingularName")).setFutureName(this.bundle.getString(getUnitName(timeUnit) + "FutureSingularName")).setPastPluralName(this.bundle.getString(getUnitName(timeUnit) + "PastSingularName")).setFuturePluralName(this.bundle.getString(getUnitName(timeUnit) + "FutureSingularName")).setPluralPattern(this.bundle.getString(getUnitName(timeUnit) + "Pattern"));
                if (this.bundle.containsKey(getUnitName(timeUnit) + "PastPluralName")) {
                    setPastPluralName(this.bundle.getString(getUnitName(timeUnit) + "PastPluralName"));
                }
                if (this.bundle.containsKey(getUnitName(timeUnit) + "FuturePluralName")) {
                    setFuturePluralName(this.bundle.getString(getUnitName(timeUnit) + "FuturePluralName"));
                }
                if (this.bundle.containsKey(getUnitName(timeUnit) + "PluralPattern")) {
                    setPluralPattern(this.bundle.getString(getUnitName(timeUnit) + "PluralPattern"));
                }
                setPattern(this.bundle.getString(getUnitName(timeUnit) + "Pattern")).setPastSuffix(this.bundle.getString(getUnitName(timeUnit) + "PastSuffix")).setFutureSuffix(this.bundle.getString(getUnitName(timeUnit) + "FutureSuffix")).setFuturePrefix("").setPastPrefix("").setSingularName("").setPluralName("");
            }
        }

        public String getPastName() {
            return this.pastName;
        }

        public String getFutureName() {
            return this.futureName;
        }

        public String getPastPluralName() {
            return this.pastPluralName;
        }

        public String getFuturePluralName() {
            return this.futurePluralName;
        }

        public String getPluralPattern() {
            return this.pluralPattern;
        }

        public FiTimeFormat setPastName(String str) {
            this.pastName = str;
            return this;
        }

        public FiTimeFormat setFutureName(String str) {
            this.futureName = str;
            return this;
        }

        @Override // org.ocpsoft.prettytime.format.SimpleTimeFormat
        public FiTimeFormat setPastPluralName(String str) {
            this.pastPluralName = str;
            return this;
        }

        @Override // org.ocpsoft.prettytime.format.SimpleTimeFormat
        public FiTimeFormat setFuturePluralName(String str) {
            this.futurePluralName = str;
            return this;
        }

        public FiTimeFormat setPluralPattern(String str) {
            this.pluralPattern = str;
            return this;
        }

        @Override // org.ocpsoft.prettytime.format.SimpleTimeFormat
        protected String getGramaticallyCorrectName(Duration duration, boolean z) {
            String pastName = duration.isInPast() ? getPastName() : getFutureName();
            if (Math.abs(getQuantity(duration, z)) == 0 || Math.abs(getQuantity(duration, z)) > 1) {
                return duration.isInPast() ? getPastPluralName() : getFuturePluralName();
            }
            return pastName;
        }

        @Override // org.ocpsoft.prettytime.format.SimpleTimeFormat
        protected String getPattern(long j) {
            if (Math.abs(j) == 1) {
                return getPattern();
            }
            return getPluralPattern();
        }

        @Override // org.ocpsoft.prettytime.format.SimpleTimeFormat, org.ocpsoft.prettytime.TimeFormat
        public String decorate(Duration duration, String str) {
            return ((duration.getUnit() instanceof Day) && Math.abs(duration.getQuantityRounded(50)) == 1) ? str : super.decorate(duration, str);
        }

        private String getUnitName(TimeUnit timeUnit) {
            return timeUnit.getClass().getSimpleName();
        }
    }
}
