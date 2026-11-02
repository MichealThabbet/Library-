package com.ibm.icu.impl.duration;

import com.ibm.icu.impl.duration.impl.PeriodFormatterData;
import com.ibm.icu.impl.duration.impl.PeriodFormatterDataService;
import java.util.TimeZone;

/* loaded from: classes2.dex */
class BasicPeriodBuilderFactory implements PeriodBuilderFactory {
    private static final short allBits = 255;
    private PeriodFormatterDataService ds;
    private Settings settings = new Settings();

    @Override // com.ibm.icu.impl.duration.PeriodBuilderFactory
    public PeriodBuilderFactory setTimeZone(TimeZone timeZone) {
        return this;
    }

    BasicPeriodBuilderFactory(PeriodFormatterDataService periodFormatterDataService) {
        this.ds = periodFormatterDataService;
    }

    static long approximateDurationOf(TimeUnit timeUnit) {
        return TimeUnit.approxDurations[timeUnit.ordinal];
    }

    class Settings {
        boolean inUse;
        int maxLimit;
        int minLimit;
        boolean weeksAloneOnly;
        short uset = 255;
        TimeUnit maxUnit = TimeUnit.YEAR;
        TimeUnit minUnit = TimeUnit.MILLISECOND;
        boolean allowZero = true;
        boolean allowMillis = true;

        Settings() {
        }

        Settings setUnits(int i) {
            if (this.uset == i) {
                return this;
            }
            Settings settingsCopy = this.inUse ? copy() : this;
            settingsCopy.uset = (short) i;
            if ((i & 255) == 255) {
                settingsCopy.uset = (short) 255;
                settingsCopy.maxUnit = TimeUnit.YEAR;
                settingsCopy.minUnit = TimeUnit.MILLISECOND;
            } else {
                int i2 = -1;
                for (int i3 = 0; i3 < TimeUnit.units.length; i3++) {
                    if (((1 << i3) & i) != 0) {
                        if (i2 == -1) {
                            settingsCopy.maxUnit = TimeUnit.units[i3];
                        }
                        i2 = i3;
                    }
                }
                if (i2 == -1) {
                    settingsCopy.maxUnit = null;
                    settingsCopy.minUnit = null;
                } else {
                    settingsCopy.minUnit = TimeUnit.units[i2];
                }
            }
            return settingsCopy;
        }

        short effectiveSet() {
            if (this.allowMillis) {
                return this.uset;
            }
            return (short) (this.uset & (~(1 << TimeUnit.MILLISECOND.ordinal)));
        }

        TimeUnit effectiveMinUnit() {
            if (this.allowMillis || this.minUnit != TimeUnit.MILLISECOND) {
                return this.minUnit;
            }
            int length = TimeUnit.units.length - 1;
            do {
                length--;
                if (length < 0) {
                    return TimeUnit.SECOND;
                }
            } while ((this.uset & (1 << length)) == 0);
            return TimeUnit.units[length];
        }

        Settings setMaxLimit(float f) {
            int i = f <= 0.0f ? 0 : (int) (1000.0f * f);
            if (f == i) {
                return this;
            }
            Settings settingsCopy = this.inUse ? copy() : this;
            settingsCopy.maxLimit = i;
            return settingsCopy;
        }

        Settings setMinLimit(float f) {
            int i = f <= 0.0f ? 0 : (int) (1000.0f * f);
            if (f == i) {
                return this;
            }
            Settings settingsCopy = this.inUse ? copy() : this;
            settingsCopy.minLimit = i;
            return settingsCopy;
        }

        Settings setAllowZero(boolean z) {
            if (this.allowZero == z) {
                return this;
            }
            Settings settingsCopy = this.inUse ? copy() : this;
            settingsCopy.allowZero = z;
            return settingsCopy;
        }

        Settings setWeeksAloneOnly(boolean z) {
            if (this.weeksAloneOnly == z) {
                return this;
            }
            Settings settingsCopy = this.inUse ? copy() : this;
            settingsCopy.weeksAloneOnly = z;
            return settingsCopy;
        }

        Settings setAllowMilliseconds(boolean z) {
            if (this.allowMillis == z) {
                return this;
            }
            Settings settingsCopy = this.inUse ? copy() : this;
            settingsCopy.allowMillis = z;
            return settingsCopy;
        }

        Settings setLocale(String str) {
            PeriodFormatterData periodFormatterData = BasicPeriodBuilderFactory.this.ds.get(str);
            return setAllowZero(periodFormatterData.allowZero()).setWeeksAloneOnly(periodFormatterData.weeksAloneOnly()).setAllowMilliseconds(periodFormatterData.useMilliseconds() != 1);
        }

        Settings setInUse() {
            this.inUse = true;
            return this;
        }

        Period createLimited(long j, boolean z) {
            if (this.maxLimit > 0) {
                long jApproximateDurationOf = BasicPeriodBuilderFactory.approximateDurationOf(this.maxUnit);
                long j2 = j * 1000;
                int i = this.maxLimit;
                if (j2 > i * jApproximateDurationOf) {
                    return Period.moreThan(i / 1000.0f, this.maxUnit).inPast(z);
                }
            }
            if (this.minLimit <= 0) {
                return null;
            }
            TimeUnit timeUnitEffectiveMinUnit = effectiveMinUnit();
            long jApproximateDurationOf2 = BasicPeriodBuilderFactory.approximateDurationOf(timeUnitEffectiveMinUnit);
            TimeUnit timeUnit = this.minUnit;
            long jMax = timeUnitEffectiveMinUnit == timeUnit ? this.minLimit : Math.max(1000L, (BasicPeriodBuilderFactory.approximateDurationOf(timeUnit) * this.minLimit) / jApproximateDurationOf2);
            if (j * 1000 < jApproximateDurationOf2 * jMax) {
                return Period.lessThan(jMax / 1000.0f, timeUnitEffectiveMinUnit).inPast(z);
            }
            return null;
        }

        public Settings copy() {
            Settings settings = BasicPeriodBuilderFactory.this.new Settings();
            settings.inUse = this.inUse;
            settings.uset = this.uset;
            settings.maxUnit = this.maxUnit;
            settings.minUnit = this.minUnit;
            settings.maxLimit = this.maxLimit;
            settings.minLimit = this.minLimit;
            settings.allowZero = this.allowZero;
            settings.weeksAloneOnly = this.weeksAloneOnly;
            settings.allowMillis = this.allowMillis;
            return settings;
        }
    }

    @Override // com.ibm.icu.impl.duration.PeriodBuilderFactory
    public PeriodBuilderFactory setAvailableUnitRange(TimeUnit timeUnit, TimeUnit timeUnit2) {
        int i = 0;
        for (int i2 = timeUnit2.ordinal; i2 <= timeUnit.ordinal; i2++) {
            i |= 1 << i2;
        }
        if (i == 0) {
            throw new IllegalArgumentException("range " + timeUnit + " to " + timeUnit2 + " is empty");
        }
        this.settings = this.settings.setUnits(i);
        return this;
    }

    @Override // com.ibm.icu.impl.duration.PeriodBuilderFactory
    public PeriodBuilderFactory setUnitIsAvailable(TimeUnit timeUnit, boolean z) {
        int i;
        short s = this.settings.uset;
        if (z) {
            i = (1 << timeUnit.ordinal) | s;
        } else {
            i = (~(1 << timeUnit.ordinal)) & s;
        }
        this.settings = this.settings.setUnits(i);
        return this;
    }

    @Override // com.ibm.icu.impl.duration.PeriodBuilderFactory
    public PeriodBuilderFactory setMaxLimit(float f) {
        this.settings = this.settings.setMaxLimit(f);
        return this;
    }

    @Override // com.ibm.icu.impl.duration.PeriodBuilderFactory
    public PeriodBuilderFactory setMinLimit(float f) {
        this.settings = this.settings.setMinLimit(f);
        return this;
    }

    @Override // com.ibm.icu.impl.duration.PeriodBuilderFactory
    public PeriodBuilderFactory setAllowZero(boolean z) {
        this.settings = this.settings.setAllowZero(z);
        return this;
    }

    @Override // com.ibm.icu.impl.duration.PeriodBuilderFactory
    public PeriodBuilderFactory setWeeksAloneOnly(boolean z) {
        this.settings = this.settings.setWeeksAloneOnly(z);
        return this;
    }

    @Override // com.ibm.icu.impl.duration.PeriodBuilderFactory
    public PeriodBuilderFactory setAllowMilliseconds(boolean z) {
        this.settings = this.settings.setAllowMilliseconds(z);
        return this;
    }

    @Override // com.ibm.icu.impl.duration.PeriodBuilderFactory
    public PeriodBuilderFactory setLocale(String str) {
        this.settings = this.settings.setLocale(str);
        return this;
    }

    private Settings getSettings() {
        if (this.settings.effectiveSet() == 0) {
            return null;
        }
        return this.settings.setInUse();
    }

    @Override // com.ibm.icu.impl.duration.PeriodBuilderFactory
    public PeriodBuilder getFixedUnitBuilder(TimeUnit timeUnit) {
        return FixedUnitBuilder.get(timeUnit, getSettings());
    }

    @Override // com.ibm.icu.impl.duration.PeriodBuilderFactory
    public PeriodBuilder getSingleUnitBuilder() {
        return SingleUnitBuilder.get(getSettings());
    }

    @Override // com.ibm.icu.impl.duration.PeriodBuilderFactory
    public PeriodBuilder getOneOrTwoUnitBuilder() {
        return OneOrTwoUnitBuilder.get(getSettings());
    }

    @Override // com.ibm.icu.impl.duration.PeriodBuilderFactory
    public PeriodBuilder getMultiUnitBuilder(int i) {
        return MultiUnitBuilder.get(i, getSettings());
    }
}
