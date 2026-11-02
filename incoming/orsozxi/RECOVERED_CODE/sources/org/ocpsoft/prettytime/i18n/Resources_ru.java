package org.ocpsoft.prettytime.i18n;

import java.lang.reflect.Array;
import java.util.ListResourceBundle;
import org.ocpsoft.prettytime.Duration;
import org.ocpsoft.prettytime.TimeFormat;
import org.ocpsoft.prettytime.TimeUnit;
import org.ocpsoft.prettytime.impl.TimeFormatProvider;
import org.ocpsoft.prettytime.units.Century;
import org.ocpsoft.prettytime.units.Day;
import org.ocpsoft.prettytime.units.Decade;
import org.ocpsoft.prettytime.units.Hour;
import org.ocpsoft.prettytime.units.JustNow;
import org.ocpsoft.prettytime.units.Millennium;
import org.ocpsoft.prettytime.units.Millisecond;
import org.ocpsoft.prettytime.units.Minute;
import org.ocpsoft.prettytime.units.Month;
import org.ocpsoft.prettytime.units.Second;
import org.ocpsoft.prettytime.units.Week;
import org.ocpsoft.prettytime.units.Year;

/* loaded from: classes3.dex */
public class Resources_ru extends ListResourceBundle implements TimeFormatProvider {
    private static final Object[][] OBJECTS = (Object[][]) Array.newInstance((Class<?>) Object.class, 0, 0);
    private static final int russianPluralForms = 3;
    private static final int tolerance = 50;

    private static class TimeFormatAided implements TimeFormat {
        private final String[] pluarls;

        public TimeFormatAided(String... strArr) {
            if (strArr.length != 3) {
                throw new IllegalArgumentException("Wrong plural forms number for russian language!");
            }
            this.pluarls = strArr;
        }

        @Override // org.ocpsoft.prettytime.TimeFormat
        public String format(Duration duration) {
            long quantityRounded = duration.getQuantityRounded(50);
            StringBuilder sb = new StringBuilder();
            sb.append(quantityRounded);
            return sb.toString();
        }

        @Override // org.ocpsoft.prettytime.TimeFormat
        public String formatUnrounded(Duration duration) {
            long quantity = duration.getQuantity();
            StringBuilder sb = new StringBuilder();
            sb.append(quantity);
            return sb.toString();
        }

        @Override // org.ocpsoft.prettytime.TimeFormat
        public String decorate(Duration duration, String str) {
            return performDecoration(duration.isInPast(), duration.isInFuture(), duration.getQuantityRounded(50), str);
        }

        @Override // org.ocpsoft.prettytime.TimeFormat
        public String decorateUnrounded(Duration duration, String str) {
            return performDecoration(duration.isInPast(), duration.isInFuture(), duration.getQuantity(), str);
        }

        /* JADX WARN: Removed duplicated region for block: B:16:0x0030  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        private java.lang.String performDecoration(boolean r12, boolean r13, long r14, java.lang.String r16) {
            /*
                r11 = this;
                r0 = 10
                long r2 = r14 % r0
                r4 = 100
                r6 = 1
                int r8 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1))
                if (r8 != 0) goto L16
                long r6 = r14 % r4
                r8 = 11
                int r10 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
                if (r10 == 0) goto L16
                r0 = 0
                goto L31
            L16:
                r6 = 2
                int r8 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1))
                if (r8 < 0) goto L30
                r6 = 4
                int r8 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1))
                if (r8 > 0) goto L30
                long r2 = r14 % r4
                int r4 = (r2 > r0 ? 1 : (r2 == r0 ? 0 : -1))
                if (r4 < 0) goto L2e
                r0 = 20
                int r4 = (r2 > r0 ? 1 : (r2 == r0 ? 0 : -1))
                if (r4 < 0) goto L30
            L2e:
                r0 = 1
                goto L31
            L30:
                r0 = 2
            L31:
                r1 = 3
                if (r0 > r1) goto L5e
                java.lang.StringBuilder r1 = new java.lang.StringBuilder
                r1.<init>()
                if (r13 == 0) goto L40
                java.lang.String r2 = "через "
                r1.append(r2)
            L40:
                r2 = r16
                r1.append(r2)
                r2 = 32
                r1.append(r2)
                r2 = r11
                java.lang.String[] r3 = r2.pluarls
                r0 = r3[r0]
                r1.append(r0)
                if (r12 == 0) goto L59
                java.lang.String r0 = " назад"
                r1.append(r0)
            L59:
                java.lang.String r0 = r1.toString()
                return r0
            L5e:
                r2 = r11
                java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
                java.lang.String r1 = "Wrong plural index was calculated somehow for russian language"
                r0.<init>(r1)
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: org.ocpsoft.prettytime.i18n.Resources_ru.TimeFormatAided.performDecoration(boolean, boolean, long, java.lang.String):java.lang.String");
        }
    }

    @Override // java.util.ListResourceBundle
    public Object[][] getContents() {
        return OBJECTS;
    }

    @Override // org.ocpsoft.prettytime.impl.TimeFormatProvider
    public TimeFormat getFormatFor(TimeUnit timeUnit) {
        if (timeUnit instanceof JustNow) {
            return new TimeFormat() { // from class: org.ocpsoft.prettytime.i18n.Resources_ru.1
                @Override // org.ocpsoft.prettytime.TimeFormat
                public String decorate(Duration duration, String str) {
                    return str;
                }

                @Override // org.ocpsoft.prettytime.TimeFormat
                public String decorateUnrounded(Duration duration, String str) {
                    return str;
                }

                @Override // org.ocpsoft.prettytime.TimeFormat
                public String format(Duration duration) {
                    return performFormat(duration);
                }

                @Override // org.ocpsoft.prettytime.TimeFormat
                public String formatUnrounded(Duration duration) {
                    return performFormat(duration);
                }

                private String performFormat(Duration duration) {
                    if (duration.isInFuture()) {
                        return "сейчас";
                    }
                    if (duration.isInPast()) {
                        return "только что";
                    }
                    return null;
                }
            };
        }
        if (timeUnit instanceof Century) {
            return new TimeFormatAided("век", "века", "веков");
        }
        if (timeUnit instanceof Day) {
            return new TimeFormatAided("день", "дня", "дней");
        }
        if (timeUnit instanceof Decade) {
            return new TimeFormatAided("десятилетие", "десятилетия", "десятилетий");
        }
        if (timeUnit instanceof Hour) {
            return new TimeFormatAided("час", "часа", "часов");
        }
        if (timeUnit instanceof Millennium) {
            return new TimeFormatAided("тысячелетие", "тысячелетия", "тысячелетий");
        }
        if (timeUnit instanceof Millisecond) {
            return new TimeFormatAided("миллисекунду", "миллисекунды", "миллисекунд");
        }
        if (timeUnit instanceof Minute) {
            return new TimeFormatAided("минуту", "минуты", "минут");
        }
        if (timeUnit instanceof Month) {
            return new TimeFormatAided("месяц", "месяца", "месяцев");
        }
        if (timeUnit instanceof Second) {
            return new TimeFormatAided("секунду", "секунды", "секунд");
        }
        if (timeUnit instanceof Week) {
            return new TimeFormatAided("неделю", "недели", "недель");
        }
        if (timeUnit instanceof Year) {
            return new TimeFormatAided("год", "года", "лет");
        }
        return null;
    }
}
