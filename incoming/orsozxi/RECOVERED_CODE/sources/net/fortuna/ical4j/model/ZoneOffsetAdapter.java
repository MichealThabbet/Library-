package net.fortuna.ical4j.model;

import it.orsozoxi.android.orsonotes.R2;
import java.io.Serializable;
import java.time.ZoneOffset;

/* loaded from: classes3.dex */
public class ZoneOffsetAdapter implements Serializable {
    private final ZoneOffset offset;

    public ZoneOffsetAdapter(ZoneOffset zoneOffset) {
        this.offset = zoneOffset;
    }

    public ZoneOffset getOffset() {
        return this.offset;
    }

    public String toString() {
        ZoneOffset zoneOffset = this.offset;
        if (zoneOffset == null) {
            return "";
        }
        int iAbs = Math.abs(zoneOffset.getTotalSeconds()) / R2.string.fab_transformation_scrim_behavior;
        if (this.offset.getTotalSeconds() < 0) {
            iAbs = -iAbs;
        }
        int iAbs2 = (Math.abs(this.offset.getTotalSeconds()) % R2.string.fab_transformation_scrim_behavior) / 60;
        int iAbs3 = (Math.abs(this.offset.getTotalSeconds()) % R2.string.fab_transformation_scrim_behavior) % 60;
        return iAbs3 > 0 ? String.format("%+03d%02d%02d", Integer.valueOf(iAbs), Integer.valueOf(iAbs2), Integer.valueOf(iAbs3)) : String.format("%+03d%02d", Integer.valueOf(iAbs), Integer.valueOf(iAbs2));
    }

    public static ZoneOffset from(UtcOffset utcOffset) {
        return ZoneOffset.of(utcOffset.toString());
    }
}
