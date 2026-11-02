package com.ibm.icu.impl;

/* loaded from: classes2.dex */
public enum CalType {
    GREGORIAN("gregorian"),
    ISO8601("iso8601"),
    BUDDHIST("buddhist"),
    CHINESE("chinese"),
    COPTIC("coptic"),
    DANGI("dangi"),
    ETHIOPIC("ethiopic"),
    ETHIOPIC_AMETE_ALEM("ethiopic-amete-alem"),
    HEBREW("hebrew"),
    INDIAN("indian"),
    ISLAMIC("islamic"),
    ISLAMIC_CIVIL("islamic-civil"),
    ISLAMIC_RGSA("islamic-rgsa"),
    ISLAMIC_TBLA("islamic-tbla"),
    ISLAMIC_UMALQURA("islamic-umalqura"),
    JAPANESE("japanese"),
    PERSIAN("persian"),
    ROC("roc"),
    UNKNOWN("unknown");

    String id;

    CalType(String str) {
        this.id = str;
    }

    public String getId() {
        return this.id;
    }
}
