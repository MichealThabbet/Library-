package net.fortuna.ical4j.model;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import org.apache.commons.codec.language.Soundex;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Deprecated
/* loaded from: classes3.dex */
public class UtcOffset implements Serializable {
    private static final int HOUR_END_INDEX = 3;
    private static final int HOUR_START_INDEX = 1;
    private static final int MINUTE_END_INDEX = 5;
    private static final int MINUTE_START_INDEX = 3;
    private static final int SECOND_END_INDEX = 7;
    private static final int SECOND_START_INDEX = 5;
    private static final long serialVersionUID = 5883111996721531728L;
    private long offset;
    private static final NumberFormat HOUR_FORMAT = new DecimalFormat("00");
    private static final NumberFormat MINUTE_FORMAT = new DecimalFormat("00");
    private static final NumberFormat SECOND_FORMAT = new DecimalFormat("00");

    public UtcOffset(String str) {
        if (str.length() < 5) {
            throw new IllegalArgumentException("Invalid UTC offset [" + str + "] - must be of the form: (+/-)HHMM[SS]");
        }
        boolean z = str.charAt(0) == '-';
        if (!z && str.charAt(0) != '+') {
            throw new IllegalArgumentException("UTC offset value must be signed");
        }
        this.offset = 0L;
        this.offset = 0 + (Integer.parseInt(str.substring(1, 3)) * 3600000);
        if (str.contains(":")) {
            this.offset += Integer.parseInt(str.substring(4, 6)) * 60000;
        } else {
            this.offset += Integer.parseInt(str.substring(3, 5)) * 60000;
        }
        if (str.length() == 7) {
            this.offset += Integer.parseInt(str.substring(5, 7)) * 1000;
        }
        if (z) {
            this.offset = -this.offset;
        }
    }

    public UtcOffset(long j) {
        this.offset = ((long) Math.floor(j / 1000.0d)) * 1000;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        long jAbs = Math.abs(this.offset);
        if (this.offset < 0) {
            sb.append(Soundex.SILENT_MARKER);
        } else {
            sb.append('+');
        }
        sb.append(HOUR_FORMAT.format(jAbs / 3600000));
        long j = jAbs % 3600000;
        sb.append(MINUTE_FORMAT.format(j / 60000));
        long j2 = j % 60000;
        if (j2 > 0) {
            sb.append(SECOND_FORMAT.format(j2 / 1000));
        }
        return sb.toString();
    }

    public final long getOffset() {
        return this.offset;
    }

    public final boolean equals(Object obj) {
        if (obj instanceof UtcOffset) {
            return getOffset() == ((UtcOffset) obj).getOffset();
        }
        return super.equals(obj);
    }

    public final int hashCode() {
        return new HashCodeBuilder().append(getOffset()).toHashCode();
    }
}
