package com.ibm.icu.util;

import org.apache.http.message.TokenParser;

/* loaded from: classes2.dex */
public class Measure {
    private final Number number;
    private final MeasureUnit unit;

    public Measure(Number number, MeasureUnit measureUnit) {
        if (number == null || measureUnit == null) {
            throw new NullPointerException("Number and MeasureUnit must not be null");
        }
        this.number = number;
        this.unit = measureUnit;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Measure)) {
            return false;
        }
        Measure measure = (Measure) obj;
        return this.unit.equals(measure.unit) && numbersEqual(this.number, measure.number);
    }

    private static boolean numbersEqual(Number number, Number number2) {
        return number.equals(number2) || number.doubleValue() == number2.doubleValue();
    }

    public int hashCode() {
        return (Double.valueOf(this.number.doubleValue()).hashCode() * 31) + this.unit.hashCode();
    }

    public String toString() {
        return this.number.toString() + TokenParser.SP + this.unit.toString();
    }

    public Number getNumber() {
        return this.number;
    }

    public MeasureUnit getUnit() {
        return this.unit;
    }
}
