package org.ocpsoft.prettytime.format;

import org.ocpsoft.prettytime.Duration;
import org.ocpsoft.prettytime.TimeFormat;

/* loaded from: classes3.dex */
public class SimpleTimeFormat implements TimeFormat {
    private static final String NEGATIVE = "-";
    public static final String QUANTITY = "%n";
    public static final String SIGN = "%s";
    public static final String UNIT = "%u";
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

    @Override // org.ocpsoft.prettytime.TimeFormat
    public String format(Duration duration) {
        return format(duration, true);
    }

    @Override // org.ocpsoft.prettytime.TimeFormat
    public String formatUnrounded(Duration duration) {
        return format(duration, false);
    }

    @Override // org.ocpsoft.prettytime.TimeFormat
    public String decorate(Duration duration, String str) {
        StringBuilder sb = new StringBuilder();
        if (duration.isInPast()) {
            sb.append(this.pastPrefix);
            sb.append(" ");
            sb.append(str);
            sb.append(" ");
            sb.append(this.pastSuffix);
        } else {
            sb.append(this.futurePrefix);
            sb.append(" ");
            sb.append(str);
            sb.append(" ");
            sb.append(this.futureSuffix);
        }
        return sb.toString().replaceAll("\\s+", " ").trim();
    }

    @Override // org.ocpsoft.prettytime.TimeFormat
    public String decorateUnrounded(Duration duration, String str) {
        return decorate(duration, str);
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

    public SimpleTimeFormat setPattern(String str) {
        this.pattern = str;
        return this;
    }

    public SimpleTimeFormat setFuturePrefix(String str) {
        this.futurePrefix = str.trim();
        return this;
    }

    public SimpleTimeFormat setFutureSuffix(String str) {
        this.futureSuffix = str.trim();
        return this;
    }

    public SimpleTimeFormat setPastPrefix(String str) {
        this.pastPrefix = str.trim();
        return this;
    }

    public SimpleTimeFormat setPastSuffix(String str) {
        this.pastSuffix = str.trim();
        return this;
    }

    public SimpleTimeFormat setRoundingTolerance(int i) {
        this.roundingTolerance = i;
        return this;
    }

    public SimpleTimeFormat setSingularName(String str) {
        this.singularName = str;
        return this;
    }

    public SimpleTimeFormat setPluralName(String str) {
        this.pluralName = str;
        return this;
    }

    public SimpleTimeFormat setFutureSingularName(String str) {
        this.futureSingularName = str;
        return this;
    }

    public SimpleTimeFormat setFuturePluralName(String str) {
        this.futurePluralName = str;
        return this;
    }

    public SimpleTimeFormat setPastSingularName(String str) {
        this.pastSingularName = str;
        return this;
    }

    public SimpleTimeFormat setPastPluralName(String str) {
        this.pastPluralName = str;
        return this;
    }

    public String toString() {
        return "SimpleTimeFormat [pattern=" + this.pattern + ", futurePrefix=" + this.futurePrefix + ", futureSuffix=" + this.futureSuffix + ", pastPrefix=" + this.pastPrefix + ", pastSuffix=" + this.pastSuffix + ", roundingTolerance=" + this.roundingTolerance + "]";
    }
}
