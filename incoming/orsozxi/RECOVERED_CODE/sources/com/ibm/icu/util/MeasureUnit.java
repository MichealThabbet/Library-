package com.ibm.icu.util;

import androidx.mediarouter.media.MediaRouteProviderProtocol;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.ibm.icu.impl.CollectionSet;
import com.ibm.icu.impl.ICUData;
import com.ibm.icu.impl.ICUResourceBundle;
import com.ibm.icu.impl.UResource;
import com.ibm.icu.impl.locale.LanguageTag;
import com.ibm.icu.impl.units.MeasureUnitImpl;
import com.ibm.icu.impl.units.SingleUnitImpl;
import com.ibm.icu.text.UnicodeSet;
import it.piapostolos.android.pinotes.db.DbHelper;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import wseemann.media.FFmpegMediaMetadataRetriever;

/* loaded from: classes2.dex */
public class MeasureUnit implements Serializable {
    private static final long serialVersionUID = -1839973855554750484L;
    private MeasureUnitImpl measureUnitImpl;

    @Deprecated
    protected final String subType;

    @Deprecated
    protected final String type;
    private static final Map<String, Map<String, MeasureUnit>> cache = new HashMap();
    private static boolean cacheIsPopulated = false;
    static final UnicodeSet ASCII = new UnicodeSet(97, 122).freeze();
    static final UnicodeSet ASCII_HYPHEN_DIGITS = new UnicodeSet(45, 45, 48, 57, 97, 122).freeze();
    private static Factory UNIT_FACTORY = new Factory() { // from class: com.ibm.icu.util.MeasureUnit.1
        @Override // com.ibm.icu.util.MeasureUnit.Factory
        public MeasureUnit create(String str, String str2) {
            return new MeasureUnit(str, str2);
        }
    };
    static Factory CURRENCY_FACTORY = new Factory() { // from class: com.ibm.icu.util.MeasureUnit.2
        @Override // com.ibm.icu.util.MeasureUnit.Factory
        public MeasureUnit create(String str, String str2) {
            return new Currency(str2);
        }
    };
    static Factory TIMEUNIT_FACTORY = new Factory() { // from class: com.ibm.icu.util.MeasureUnit.3
        @Override // com.ibm.icu.util.MeasureUnit.Factory
        public MeasureUnit create(String str, String str2) {
            return new TimeUnit(str, str2);
        }
    };
    public static final MeasureUnit G_FORCE = internalGetInstance("acceleration", "g-force");
    public static final MeasureUnit METER_PER_SECOND_SQUARED = internalGetInstance("acceleration", "meter-per-square-second");
    public static final MeasureUnit ARC_MINUTE = internalGetInstance("angle", "arc-minute");
    public static final MeasureUnit ARC_SECOND = internalGetInstance("angle", "arc-second");
    public static final MeasureUnit DEGREE = internalGetInstance("angle", "degree");
    public static final MeasureUnit RADIAN = internalGetInstance("angle", "radian");
    public static final MeasureUnit REVOLUTION_ANGLE = internalGetInstance("angle", "revolution");
    public static final MeasureUnit ACRE = internalGetInstance("area", "acre");
    public static final MeasureUnit DUNAM = internalGetInstance("area", "dunam");
    public static final MeasureUnit HECTARE = internalGetInstance("area", "hectare");
    public static final MeasureUnit SQUARE_CENTIMETER = internalGetInstance("area", "square-centimeter");
    public static final MeasureUnit SQUARE_FOOT = internalGetInstance("area", "square-foot");
    public static final MeasureUnit SQUARE_INCH = internalGetInstance("area", "square-inch");
    public static final MeasureUnit SQUARE_KILOMETER = internalGetInstance("area", "square-kilometer");
    public static final MeasureUnit SQUARE_METER = internalGetInstance("area", "square-meter");
    public static final MeasureUnit SQUARE_MILE = internalGetInstance("area", "square-mile");
    public static final MeasureUnit SQUARE_YARD = internalGetInstance("area", "square-yard");
    public static final MeasureUnit KARAT = internalGetInstance("concentr", "karat");
    public static final MeasureUnit MILLIGRAM_OFGLUCOSE_PER_DECILITER = internalGetInstance("concentr", "milligram-ofglucose-per-deciliter");
    public static final MeasureUnit MILLIGRAM_PER_DECILITER = internalGetInstance("concentr", "milligram-per-deciliter");
    public static final MeasureUnit MILLIMOLE_PER_LITER = internalGetInstance("concentr", "millimole-per-liter");
    public static final MeasureUnit MOLE = internalGetInstance("concentr", "mole");
    public static final MeasureUnit PERCENT = internalGetInstance("concentr", "percent");
    public static final MeasureUnit PERMILLE = internalGetInstance("concentr", "permille");
    public static final MeasureUnit PART_PER_MILLION = internalGetInstance("concentr", "permillion");
    public static final MeasureUnit PERMYRIAD = internalGetInstance("concentr", "permyriad");
    public static final MeasureUnit LITER_PER_100KILOMETERS = internalGetInstance("consumption", "liter-per-100-kilometer");
    public static final MeasureUnit LITER_PER_KILOMETER = internalGetInstance("consumption", "liter-per-kilometer");
    public static final MeasureUnit MILE_PER_GALLON = internalGetInstance("consumption", "mile-per-gallon");
    public static final MeasureUnit MILE_PER_GALLON_IMPERIAL = internalGetInstance("consumption", "mile-per-gallon-imperial");
    public static final MeasureUnit BIT = internalGetInstance("digital", "bit");
    public static final MeasureUnit BYTE = internalGetInstance("digital", "byte");
    public static final MeasureUnit GIGABIT = internalGetInstance("digital", "gigabit");
    public static final MeasureUnit GIGABYTE = internalGetInstance("digital", "gigabyte");
    public static final MeasureUnit KILOBIT = internalGetInstance("digital", "kilobit");
    public static final MeasureUnit KILOBYTE = internalGetInstance("digital", "kilobyte");
    public static final MeasureUnit MEGABIT = internalGetInstance("digital", "megabit");
    public static final MeasureUnit MEGABYTE = internalGetInstance("digital", "megabyte");
    public static final MeasureUnit PETABYTE = internalGetInstance("digital", "petabyte");
    public static final MeasureUnit TERABIT = internalGetInstance("digital", "terabit");
    public static final MeasureUnit TERABYTE = internalGetInstance("digital", "terabyte");
    public static final MeasureUnit CENTURY = internalGetInstance(FFmpegMediaMetadataRetriever.METADATA_KEY_DURATION, "century");
    public static final TimeUnit DAY = (TimeUnit) internalGetInstance(FFmpegMediaMetadataRetriever.METADATA_KEY_DURATION, "day");
    public static final MeasureUnit DAY_PERSON = internalGetInstance(FFmpegMediaMetadataRetriever.METADATA_KEY_DURATION, "day-person");
    public static final MeasureUnit DECADE = internalGetInstance(FFmpegMediaMetadataRetriever.METADATA_KEY_DURATION, "decade");
    public static final TimeUnit HOUR = (TimeUnit) internalGetInstance(FFmpegMediaMetadataRetriever.METADATA_KEY_DURATION, "hour");
    public static final MeasureUnit MICROSECOND = internalGetInstance(FFmpegMediaMetadataRetriever.METADATA_KEY_DURATION, "microsecond");
    public static final MeasureUnit MILLISECOND = internalGetInstance(FFmpegMediaMetadataRetriever.METADATA_KEY_DURATION, "millisecond");
    public static final TimeUnit MINUTE = (TimeUnit) internalGetInstance(FFmpegMediaMetadataRetriever.METADATA_KEY_DURATION, "minute");
    public static final TimeUnit MONTH = (TimeUnit) internalGetInstance(FFmpegMediaMetadataRetriever.METADATA_KEY_DURATION, "month");
    public static final MeasureUnit MONTH_PERSON = internalGetInstance(FFmpegMediaMetadataRetriever.METADATA_KEY_DURATION, "month-person");
    public static final MeasureUnit NANOSECOND = internalGetInstance(FFmpegMediaMetadataRetriever.METADATA_KEY_DURATION, "nanosecond");
    public static final TimeUnit SECOND = (TimeUnit) internalGetInstance(FFmpegMediaMetadataRetriever.METADATA_KEY_DURATION, "second");
    public static final TimeUnit WEEK = (TimeUnit) internalGetInstance(FFmpegMediaMetadataRetriever.METADATA_KEY_DURATION, "week");
    public static final MeasureUnit WEEK_PERSON = internalGetInstance(FFmpegMediaMetadataRetriever.METADATA_KEY_DURATION, "week-person");
    public static final TimeUnit YEAR = (TimeUnit) internalGetInstance(FFmpegMediaMetadataRetriever.METADATA_KEY_DURATION, "year");
    public static final MeasureUnit YEAR_PERSON = internalGetInstance(FFmpegMediaMetadataRetriever.METADATA_KEY_DURATION, "year-person");
    public static final MeasureUnit AMPERE = internalGetInstance("electric", "ampere");
    public static final MeasureUnit MILLIAMPERE = internalGetInstance("electric", "milliampere");
    public static final MeasureUnit OHM = internalGetInstance("electric", "ohm");
    public static final MeasureUnit VOLT = internalGetInstance("electric", "volt");
    public static final MeasureUnit BRITISH_THERMAL_UNIT = internalGetInstance("energy", "british-thermal-unit");
    public static final MeasureUnit CALORIE = internalGetInstance("energy", "calorie");
    public static final MeasureUnit ELECTRONVOLT = internalGetInstance("energy", "electronvolt");
    public static final MeasureUnit FOODCALORIE = internalGetInstance("energy", "foodcalorie");
    public static final MeasureUnit JOULE = internalGetInstance("energy", "joule");
    public static final MeasureUnit KILOCALORIE = internalGetInstance("energy", "kilocalorie");
    public static final MeasureUnit KILOJOULE = internalGetInstance("energy", "kilojoule");
    public static final MeasureUnit KILOWATT_HOUR = internalGetInstance("energy", "kilowatt-hour");
    public static final MeasureUnit THERM_US = internalGetInstance("energy", "therm-us");
    public static final MeasureUnit NEWTON = internalGetInstance("force", "newton");
    public static final MeasureUnit POUND_FORCE = internalGetInstance("force", "pound-force");
    public static final MeasureUnit GIGAHERTZ = internalGetInstance("frequency", "gigahertz");
    public static final MeasureUnit HERTZ = internalGetInstance("frequency", "hertz");
    public static final MeasureUnit KILOHERTZ = internalGetInstance("frequency", "kilohertz");
    public static final MeasureUnit MEGAHERTZ = internalGetInstance("frequency", "megahertz");
    public static final MeasureUnit DOT = internalGetInstance("graphics", "dot");
    public static final MeasureUnit DOT_PER_CENTIMETER = internalGetInstance("graphics", "dot-per-centimeter");
    public static final MeasureUnit DOT_PER_INCH = internalGetInstance("graphics", "dot-per-inch");
    public static final MeasureUnit EM = internalGetInstance("graphics", "em");
    public static final MeasureUnit MEGAPIXEL = internalGetInstance("graphics", "megapixel");
    public static final MeasureUnit PIXEL = internalGetInstance("graphics", "pixel");
    public static final MeasureUnit PIXEL_PER_CENTIMETER = internalGetInstance("graphics", "pixel-per-centimeter");
    public static final MeasureUnit PIXEL_PER_INCH = internalGetInstance("graphics", "pixel-per-inch");
    public static final MeasureUnit ASTRONOMICAL_UNIT = internalGetInstance(DbHelper.KEY_ATTACHMENT_LENGTH, "astronomical-unit");
    public static final MeasureUnit CENTIMETER = internalGetInstance(DbHelper.KEY_ATTACHMENT_LENGTH, "centimeter");
    public static final MeasureUnit DECIMETER = internalGetInstance(DbHelper.KEY_ATTACHMENT_LENGTH, "decimeter");
    public static final MeasureUnit EARTH_RADIUS = internalGetInstance(DbHelper.KEY_ATTACHMENT_LENGTH, "earth-radius");
    public static final MeasureUnit FATHOM = internalGetInstance(DbHelper.KEY_ATTACHMENT_LENGTH, "fathom");
    public static final MeasureUnit FOOT = internalGetInstance(DbHelper.KEY_ATTACHMENT_LENGTH, "foot");
    public static final MeasureUnit FURLONG = internalGetInstance(DbHelper.KEY_ATTACHMENT_LENGTH, "furlong");
    public static final MeasureUnit INCH = internalGetInstance(DbHelper.KEY_ATTACHMENT_LENGTH, "inch");
    public static final MeasureUnit KILOMETER = internalGetInstance(DbHelper.KEY_ATTACHMENT_LENGTH, "kilometer");
    public static final MeasureUnit LIGHT_YEAR = internalGetInstance(DbHelper.KEY_ATTACHMENT_LENGTH, "light-year");
    public static final MeasureUnit METER = internalGetInstance(DbHelper.KEY_ATTACHMENT_LENGTH, "meter");
    public static final MeasureUnit MICROMETER = internalGetInstance(DbHelper.KEY_ATTACHMENT_LENGTH, "micrometer");
    public static final MeasureUnit MILE = internalGetInstance(DbHelper.KEY_ATTACHMENT_LENGTH, "mile");
    public static final MeasureUnit MILE_SCANDINAVIAN = internalGetInstance(DbHelper.KEY_ATTACHMENT_LENGTH, "mile-scandinavian");
    public static final MeasureUnit MILLIMETER = internalGetInstance(DbHelper.KEY_ATTACHMENT_LENGTH, "millimeter");
    public static final MeasureUnit NANOMETER = internalGetInstance(DbHelper.KEY_ATTACHMENT_LENGTH, "nanometer");
    public static final MeasureUnit NAUTICAL_MILE = internalGetInstance(DbHelper.KEY_ATTACHMENT_LENGTH, "nautical-mile");
    public static final MeasureUnit PARSEC = internalGetInstance(DbHelper.KEY_ATTACHMENT_LENGTH, "parsec");
    public static final MeasureUnit PICOMETER = internalGetInstance(DbHelper.KEY_ATTACHMENT_LENGTH, "picometer");
    public static final MeasureUnit POINT = internalGetInstance(DbHelper.KEY_ATTACHMENT_LENGTH, "point");
    public static final MeasureUnit SOLAR_RADIUS = internalGetInstance(DbHelper.KEY_ATTACHMENT_LENGTH, "solar-radius");
    public static final MeasureUnit YARD = internalGetInstance(DbHelper.KEY_ATTACHMENT_LENGTH, "yard");
    public static final MeasureUnit CANDELA = internalGetInstance("light", "candela");
    public static final MeasureUnit LUMEN = internalGetInstance("light", "lumen");
    public static final MeasureUnit LUX = internalGetInstance("light", "lux");
    public static final MeasureUnit SOLAR_LUMINOSITY = internalGetInstance("light", "solar-luminosity");
    public static final MeasureUnit CARAT = internalGetInstance("mass", "carat");
    public static final MeasureUnit DALTON = internalGetInstance("mass", "dalton");
    public static final MeasureUnit EARTH_MASS = internalGetInstance("mass", "earth-mass");
    public static final MeasureUnit GRAIN = internalGetInstance("mass", "grain");
    public static final MeasureUnit GRAM = internalGetInstance("mass", "gram");
    public static final MeasureUnit KILOGRAM = internalGetInstance("mass", "kilogram");
    public static final MeasureUnit METRIC_TON = internalGetInstance("mass", "metric-ton");
    public static final MeasureUnit MICROGRAM = internalGetInstance("mass", "microgram");
    public static final MeasureUnit MILLIGRAM = internalGetInstance("mass", "milligram");
    public static final MeasureUnit OUNCE = internalGetInstance("mass", "ounce");
    public static final MeasureUnit OUNCE_TROY = internalGetInstance("mass", "ounce-troy");
    public static final MeasureUnit POUND = internalGetInstance("mass", "pound");
    public static final MeasureUnit SOLAR_MASS = internalGetInstance("mass", "solar-mass");
    public static final MeasureUnit STONE = internalGetInstance("mass", "stone");
    public static final MeasureUnit TON = internalGetInstance("mass", "ton");
    public static final MeasureUnit GIGAWATT = internalGetInstance("power", "gigawatt");
    public static final MeasureUnit HORSEPOWER = internalGetInstance("power", "horsepower");
    public static final MeasureUnit KILOWATT = internalGetInstance("power", "kilowatt");
    public static final MeasureUnit MEGAWATT = internalGetInstance("power", "megawatt");
    public static final MeasureUnit MILLIWATT = internalGetInstance("power", "milliwatt");
    public static final MeasureUnit WATT = internalGetInstance("power", "watt");
    public static final MeasureUnit ATMOSPHERE = internalGetInstance("pressure", "atmosphere");
    public static final MeasureUnit BAR = internalGetInstance("pressure", "bar");
    public static final MeasureUnit HECTOPASCAL = internalGetInstance("pressure", "hectopascal");
    public static final MeasureUnit INCH_HG = internalGetInstance("pressure", "inch-ofhg");
    public static final MeasureUnit KILOPASCAL = internalGetInstance("pressure", "kilopascal");
    public static final MeasureUnit MEGAPASCAL = internalGetInstance("pressure", "megapascal");
    public static final MeasureUnit MILLIBAR = internalGetInstance("pressure", "millibar");
    public static final MeasureUnit MILLIMETER_OF_MERCURY = internalGetInstance("pressure", "millimeter-ofhg");
    public static final MeasureUnit PASCAL = internalGetInstance("pressure", "pascal");
    public static final MeasureUnit POUND_PER_SQUARE_INCH = internalGetInstance("pressure", "pound-force-per-square-inch");
    public static final MeasureUnit KILOMETER_PER_HOUR = internalGetInstance("speed", "kilometer-per-hour");
    public static final MeasureUnit KNOT = internalGetInstance("speed", "knot");
    public static final MeasureUnit METER_PER_SECOND = internalGetInstance("speed", "meter-per-second");
    public static final MeasureUnit MILE_PER_HOUR = internalGetInstance("speed", "mile-per-hour");
    public static final MeasureUnit CELSIUS = internalGetInstance("temperature", "celsius");
    public static final MeasureUnit FAHRENHEIT = internalGetInstance("temperature", "fahrenheit");
    public static final MeasureUnit GENERIC_TEMPERATURE = internalGetInstance("temperature", "generic");
    public static final MeasureUnit KELVIN = internalGetInstance("temperature", "kelvin");
    public static final MeasureUnit NEWTON_METER = internalGetInstance("torque", "newton-meter");
    public static final MeasureUnit POUND_FOOT = internalGetInstance("torque", "pound-force-foot");
    public static final MeasureUnit ACRE_FOOT = internalGetInstance(MediaRouteProviderProtocol.CLIENT_DATA_VOLUME, "acre-foot");
    public static final MeasureUnit BARREL = internalGetInstance(MediaRouteProviderProtocol.CLIENT_DATA_VOLUME, "barrel");
    public static final MeasureUnit BUSHEL = internalGetInstance(MediaRouteProviderProtocol.CLIENT_DATA_VOLUME, "bushel");
    public static final MeasureUnit CENTILITER = internalGetInstance(MediaRouteProviderProtocol.CLIENT_DATA_VOLUME, "centiliter");
    public static final MeasureUnit CUBIC_CENTIMETER = internalGetInstance(MediaRouteProviderProtocol.CLIENT_DATA_VOLUME, "cubic-centimeter");
    public static final MeasureUnit CUBIC_FOOT = internalGetInstance(MediaRouteProviderProtocol.CLIENT_DATA_VOLUME, "cubic-foot");
    public static final MeasureUnit CUBIC_INCH = internalGetInstance(MediaRouteProviderProtocol.CLIENT_DATA_VOLUME, "cubic-inch");
    public static final MeasureUnit CUBIC_KILOMETER = internalGetInstance(MediaRouteProviderProtocol.CLIENT_DATA_VOLUME, "cubic-kilometer");
    public static final MeasureUnit CUBIC_METER = internalGetInstance(MediaRouteProviderProtocol.CLIENT_DATA_VOLUME, "cubic-meter");
    public static final MeasureUnit CUBIC_MILE = internalGetInstance(MediaRouteProviderProtocol.CLIENT_DATA_VOLUME, "cubic-mile");
    public static final MeasureUnit CUBIC_YARD = internalGetInstance(MediaRouteProviderProtocol.CLIENT_DATA_VOLUME, "cubic-yard");
    public static final MeasureUnit CUP = internalGetInstance(MediaRouteProviderProtocol.CLIENT_DATA_VOLUME, "cup");
    public static final MeasureUnit CUP_METRIC = internalGetInstance(MediaRouteProviderProtocol.CLIENT_DATA_VOLUME, "cup-metric");
    public static final MeasureUnit DECILITER = internalGetInstance(MediaRouteProviderProtocol.CLIENT_DATA_VOLUME, "deciliter");
    public static final MeasureUnit DESSERT_SPOON = internalGetInstance(MediaRouteProviderProtocol.CLIENT_DATA_VOLUME, "dessert-spoon");
    public static final MeasureUnit DESSERT_SPOON_IMPERIAL = internalGetInstance(MediaRouteProviderProtocol.CLIENT_DATA_VOLUME, "dessert-spoon-imperial");
    public static final MeasureUnit DRAM = internalGetInstance(MediaRouteProviderProtocol.CLIENT_DATA_VOLUME, "dram");
    public static final MeasureUnit DROP = internalGetInstance(MediaRouteProviderProtocol.CLIENT_DATA_VOLUME, "drop");
    public static final MeasureUnit FLUID_OUNCE = internalGetInstance(MediaRouteProviderProtocol.CLIENT_DATA_VOLUME, "fluid-ounce");
    public static final MeasureUnit FLUID_OUNCE_IMPERIAL = internalGetInstance(MediaRouteProviderProtocol.CLIENT_DATA_VOLUME, "fluid-ounce-imperial");
    public static final MeasureUnit GALLON = internalGetInstance(MediaRouteProviderProtocol.CLIENT_DATA_VOLUME, "gallon");
    public static final MeasureUnit GALLON_IMPERIAL = internalGetInstance(MediaRouteProviderProtocol.CLIENT_DATA_VOLUME, "gallon-imperial");
    public static final MeasureUnit HECTOLITER = internalGetInstance(MediaRouteProviderProtocol.CLIENT_DATA_VOLUME, "hectoliter");
    public static final MeasureUnit JIGGER = internalGetInstance(MediaRouteProviderProtocol.CLIENT_DATA_VOLUME, "jigger");
    public static final MeasureUnit LITER = internalGetInstance(MediaRouteProviderProtocol.CLIENT_DATA_VOLUME, "liter");
    public static final MeasureUnit MEGALITER = internalGetInstance(MediaRouteProviderProtocol.CLIENT_DATA_VOLUME, "megaliter");
    public static final MeasureUnit MILLILITER = internalGetInstance(MediaRouteProviderProtocol.CLIENT_DATA_VOLUME, "milliliter");
    public static final MeasureUnit PINCH = internalGetInstance(MediaRouteProviderProtocol.CLIENT_DATA_VOLUME, "pinch");
    public static final MeasureUnit PINT = internalGetInstance(MediaRouteProviderProtocol.CLIENT_DATA_VOLUME, "pint");
    public static final MeasureUnit PINT_METRIC = internalGetInstance(MediaRouteProviderProtocol.CLIENT_DATA_VOLUME, "pint-metric");
    public static final MeasureUnit QUART = internalGetInstance(MediaRouteProviderProtocol.CLIENT_DATA_VOLUME, "quart");
    public static final MeasureUnit QUART_IMPERIAL = internalGetInstance(MediaRouteProviderProtocol.CLIENT_DATA_VOLUME, "quart-imperial");
    public static final MeasureUnit TABLESPOON = internalGetInstance(MediaRouteProviderProtocol.CLIENT_DATA_VOLUME, "tablespoon");
    public static final MeasureUnit TEASPOON = internalGetInstance(MediaRouteProviderProtocol.CLIENT_DATA_VOLUME, "teaspoon");

    public enum Complexity {
        SINGLE,
        COMPOUND,
        MIXED
    }

    @Deprecated
    protected interface Factory {
        @Deprecated
        MeasureUnit create(String str, String str2);
    }

    public enum MeasurePrefix {
        YOTTA(24, "yotta", 10),
        ZETTA(21, "zetta", 10),
        EXA(18, "exa", 10),
        PETA(15, "peta", 10),
        TERA(12, "tera", 10),
        GIGA(9, "giga", 10),
        MEGA(6, "mega", 10),
        KILO(3, "kilo", 10),
        HECTO(2, "hecto", 10),
        DEKA(1, "deka", 10),
        ONE(0, "", 10),
        DECI(-1, "deci", 10),
        CENTI(-2, "centi", 10),
        MILLI(-3, "milli", 10),
        MICRO(-6, "micro", 10),
        NANO(-9, "nano", 10),
        PICO(-12, "pico", 10),
        FEMTO(-15, "femto", 10),
        ATTO(-18, "atto", 10),
        ZEPTO(-21, "zepto", 10),
        YOCTO(-24, "yocto", 10),
        KIBI(1, "kibi", 1024),
        MEBI(2, "mebi", 1024),
        GIBI(3, "gibi", 1024),
        TEBI(4, "tebi", 1024),
        PEBI(5, "pebi", 1024),
        EXBI(6, "exbi", 1024),
        ZEBI(7, "zebi", 1024),
        YOBI(8, "yobi", 1024);

        private final int base;
        private final String identifier;
        private final int power;

        MeasurePrefix(int i, String str, int i2) {
            this.base = i2;
            this.power = i;
            this.identifier = str;
        }

        @Deprecated
        public String getIdentifier() {
            return this.identifier;
        }

        public int getBase() {
            return this.base;
        }

        public int getPower() {
            return this.power;
        }
    }

    @Deprecated
    protected MeasureUnit(String str, String str2) {
        this.type = str;
        this.subType = str2;
    }

    public static MeasureUnit forIdentifier(String str) {
        if (str == null || str.isEmpty()) {
            return NoUnit.BASE;
        }
        return MeasureUnitImpl.forIdentifier(str).build();
    }

    @Deprecated
    public static MeasureUnit fromMeasureUnitImpl(MeasureUnitImpl measureUnitImpl) {
        measureUnitImpl.serialize();
        MeasureUnit measureUnitFindBySubType = findBySubType(measureUnitImpl.getIdentifier());
        return measureUnitFindBySubType != null ? measureUnitFindBySubType : new MeasureUnit(measureUnitImpl);
    }

    private MeasureUnit(MeasureUnitImpl measureUnitImpl) {
        this.type = null;
        this.subType = null;
        this.measureUnitImpl = measureUnitImpl.copy();
    }

    public String getType() {
        return this.type;
    }

    public String getSubtype() {
        return this.subType;
    }

    public String getIdentifier() {
        MeasureUnitImpl measureUnitImpl = this.measureUnitImpl;
        String subtype = measureUnitImpl == null ? getSubtype() : measureUnitImpl.getIdentifier();
        return subtype == null ? "" : subtype;
    }

    public Complexity getComplexity() {
        MeasureUnitImpl measureUnitImpl = this.measureUnitImpl;
        if (measureUnitImpl == null) {
            return MeasureUnitImpl.forIdentifier(getIdentifier()).getComplexity();
        }
        return measureUnitImpl.getComplexity();
    }

    public MeasureUnit withPrefix(MeasurePrefix measurePrefix) {
        SingleUnitImpl singleUnitImpl = getSingleUnitImpl();
        singleUnitImpl.setPrefix(measurePrefix);
        return singleUnitImpl.build();
    }

    public MeasurePrefix getPrefix() {
        return getSingleUnitImpl().getPrefix();
    }

    public int getDimensionality() {
        return getSingleUnitImpl().getDimensionality();
    }

    public MeasureUnit withDimensionality(int i) {
        SingleUnitImpl singleUnitImpl = getSingleUnitImpl();
        singleUnitImpl.setDimensionality(i);
        return singleUnitImpl.build();
    }

    public MeasureUnit reciprocal() {
        MeasureUnitImpl copyOfMeasureUnitImpl = getCopyOfMeasureUnitImpl();
        copyOfMeasureUnitImpl.takeReciprocal();
        return copyOfMeasureUnitImpl.build();
    }

    public MeasureUnit product(MeasureUnit measureUnit) {
        MeasureUnitImpl copyOfMeasureUnitImpl = getCopyOfMeasureUnitImpl();
        if (measureUnit == null) {
            return copyOfMeasureUnitImpl.build();
        }
        MeasureUnitImpl maybeReferenceOfMeasureUnitImpl = measureUnit.getMaybeReferenceOfMeasureUnitImpl();
        if (copyOfMeasureUnitImpl.getComplexity() == Complexity.MIXED || maybeReferenceOfMeasureUnitImpl.getComplexity() == Complexity.MIXED) {
            throw new UnsupportedOperationException();
        }
        Iterator<SingleUnitImpl> it2 = maybeReferenceOfMeasureUnitImpl.getSingleUnits().iterator();
        while (it2.hasNext()) {
            copyOfMeasureUnitImpl.appendSingleUnit(it2.next());
        }
        return copyOfMeasureUnitImpl.build();
    }

    public List<MeasureUnit> splitToSingleUnits() {
        ArrayList<SingleUnitImpl> singleUnits = getMaybeReferenceOfMeasureUnitImpl().getSingleUnits();
        ArrayList arrayList = new ArrayList(singleUnits.size());
        Iterator<SingleUnitImpl> it2 = singleUnits.iterator();
        while (it2.hasNext()) {
            arrayList.add(it2.next().build());
        }
        return arrayList;
    }

    public int hashCode() {
        return (this.type.hashCode() * 31) + this.subType.hashCode();
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof MeasureUnit) {
            return getIdentifier().equals(((MeasureUnit) obj).getIdentifier());
        }
        return false;
    }

    public String toString() {
        String identifier;
        MeasureUnitImpl measureUnitImpl = this.measureUnitImpl;
        if (measureUnitImpl == null) {
            identifier = this.type + LanguageTag.SEP + this.subType;
        } else {
            identifier = measureUnitImpl.getIdentifier();
        }
        return identifier == null ? "" : identifier;
    }

    public static Set<String> getAvailableTypes() {
        populateCache();
        return Collections.unmodifiableSet(cache.keySet());
    }

    public static Set<MeasureUnit> getAvailable(String str) {
        populateCache();
        Map<String, MeasureUnit> map = cache.get(str);
        if (map == null) {
            return Collections.emptySet();
        }
        return Collections.unmodifiableSet(new CollectionSet(map.values()));
    }

    public static synchronized Set<MeasureUnit> getAvailable() {
        HashSet hashSet;
        hashSet = new HashSet();
        Iterator it2 = new HashSet(getAvailableTypes()).iterator();
        while (it2.hasNext()) {
            Iterator<MeasureUnit> it3 = getAvailable((String) it2.next()).iterator();
            while (it3.hasNext()) {
                hashSet.add(it3.next());
            }
        }
        return Collections.unmodifiableSet(hashSet);
    }

    @Deprecated
    public static MeasureUnit internalGetInstance(String str, String str2) {
        Factory factory;
        if (str == null || str2 == null) {
            throw new NullPointerException("Type and subType must be non-null");
        }
        if (!FirebaseAnalytics.Param.CURRENCY.equals(str) && (!ASCII.containsAll(str) || !ASCII_HYPHEN_DIGITS.containsAll(str2))) {
            throw new IllegalArgumentException("The type or subType are invalid.");
        }
        if (FirebaseAnalytics.Param.CURRENCY.equals(str)) {
            factory = CURRENCY_FACTORY;
        } else if (FFmpegMediaMetadataRetriever.METADATA_KEY_DURATION.equals(str)) {
            factory = TIMEUNIT_FACTORY;
        } else {
            factory = UNIT_FACTORY;
        }
        return addUnit(str, str2, factory);
    }

    @Deprecated
    public static MeasureUnit findBySubType(String str) {
        populateCache();
        for (Map<String, MeasureUnit> map : cache.values()) {
            if (map.containsKey(str)) {
                return map.get(str);
            }
        }
        return null;
    }

    private static final class MeasureUnitSink extends UResource.Sink {
        private MeasureUnitSink() {
        }

        @Override // com.ibm.icu.impl.UResource.Sink
        public void put(UResource.Key key, UResource.Value value, boolean z) {
            UResource.Table table = value.getTable();
            for (int i = 0; table.getKeyAndValue(i, key, value); i++) {
                if (!key.contentEquals("compound") && !key.contentEquals("coordinate")) {
                    String string = key.toString();
                    UResource.Table table2 = value.getTable();
                    for (int i2 = 0; table2.getKeyAndValue(i2, key, value); i2++) {
                        MeasureUnit.internalGetInstance(string, key.toString());
                    }
                }
            }
        }
    }

    private static final class CurrencyNumericCodeSink extends UResource.Sink {
        private CurrencyNumericCodeSink() {
        }

        @Override // com.ibm.icu.impl.UResource.Sink
        public void put(UResource.Key key, UResource.Value value, boolean z) {
            UResource.Table table = value.getTable();
            for (int i = 0; table.getKeyAndValue(i, key, value); i++) {
                MeasureUnit.internalGetInstance(FirebaseAnalytics.Param.CURRENCY, key.toString());
            }
        }
    }

    private static synchronized void populateCache() {
        if (cacheIsPopulated) {
            return;
        }
        cacheIsPopulated = true;
        ((ICUResourceBundle) UResourceBundle.getBundleInstance(ICUData.ICU_UNIT_BASE_NAME, "en")).getAllItemsWithFallback("units", new MeasureUnitSink());
        ((ICUResourceBundle) UResourceBundle.getBundleInstance(ICUData.ICU_BASE_NAME, "currencyNumericCodes", ICUResourceBundle.ICU_DATA_CLASS_LOADER)).getAllItemsWithFallback("codeMap", new CurrencyNumericCodeSink());
    }

    @Deprecated
    protected static synchronized MeasureUnit addUnit(String str, String str2, Factory factory) {
        MeasureUnit measureUnitCreate;
        Map<String, MeasureUnit> map = cache.get(str);
        if (map == null) {
            Map<String, Map<String, MeasureUnit>> map2 = cache;
            HashMap map3 = new HashMap();
            map2.put(str, map3);
            map = map3;
        } else {
            str = map.entrySet().iterator().next().getValue().type;
        }
        measureUnitCreate = map.get(str2);
        if (measureUnitCreate == null) {
            measureUnitCreate = factory.create(str, str2);
            map.put(str2, measureUnitCreate);
        }
        return measureUnitCreate;
    }

    private Object writeReplace() throws ObjectStreamException {
        return new MeasureUnitProxy(this.type, this.subType);
    }

    private SingleUnitImpl getSingleUnitImpl() {
        MeasureUnitImpl measureUnitImpl = this.measureUnitImpl;
        if (measureUnitImpl == null) {
            return MeasureUnitImpl.forIdentifier(getIdentifier()).getSingleUnitImpl();
        }
        return measureUnitImpl.getSingleUnitImpl();
    }

    @Deprecated
    public MeasureUnitImpl getCopyOfMeasureUnitImpl() {
        MeasureUnitImpl measureUnitImpl = this.measureUnitImpl;
        if (measureUnitImpl == null) {
            return MeasureUnitImpl.forIdentifier(getIdentifier());
        }
        return measureUnitImpl.copy();
    }

    private MeasureUnitImpl getMaybeReferenceOfMeasureUnitImpl() {
        MeasureUnitImpl measureUnitImpl = this.measureUnitImpl;
        return measureUnitImpl == null ? MeasureUnitImpl.forIdentifier(getIdentifier()) : measureUnitImpl;
    }

    static final class MeasureUnitProxy implements Externalizable {
        private static final long serialVersionUID = -3910681415330989598L;
        private String subType;
        private String type;

        public MeasureUnitProxy(String str, String str2) {
            this.type = str;
            this.subType = str2;
        }

        public MeasureUnitProxy() {
        }

        @Override // java.io.Externalizable
        public void writeExternal(ObjectOutput objectOutput) throws IOException {
            objectOutput.writeByte(0);
            objectOutput.writeUTF(this.type);
            objectOutput.writeUTF(this.subType);
            objectOutput.writeShort(0);
        }

        @Override // java.io.Externalizable
        public void readExternal(ObjectInput objectInput) throws IOException, ClassNotFoundException {
            objectInput.readByte();
            this.type = objectInput.readUTF();
            this.subType = objectInput.readUTF();
            int i = objectInput.readShort();
            if (i > 0) {
                objectInput.read(new byte[i], 0, i);
            }
        }

        private Object readResolve() throws ObjectStreamException {
            return MeasureUnit.internalGetInstance(this.type, this.subType);
        }
    }
}
