package it.orsozoxi.android.orsonotes.helpers.count;

import it.orsozoxi.android.orsonotes.OrsoNotes;
import it.orsozoxi.android.orsonotes.helpers.LanguageHelper;

/* loaded from: classes3.dex */
public class CountFactory {
    private CountFactory() {
    }

    public static WordCounter getWordCounter() {
        return getCounterInstanceByLocale(LanguageHelper.getCurrentLocaleAsString(OrsoNotes.getAppContext()));
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0034  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    static it.orsozoxi.android.orsonotes.helpers.count.WordCounter getCounterInstanceByLocale(java.lang.String r4) {
        /*
            int r0 = r4.hashCode()
            r1 = 100876622(0x603414e, float:2.468634E-35)
            r2 = 2
            r3 = 1
            if (r0 == r1) goto L2a
            r1 = 115861276(0x6e7e71c, float:8.7232127E-35)
            if (r0 == r1) goto L20
            r1 = 115861812(0x6e7e934, float:8.7235204E-35)
            if (r0 == r1) goto L16
            goto L34
        L16:
            java.lang.String r0 = "zh_TW"
            boolean r4 = r4.equals(r0)
            if (r4 == 0) goto L34
            r4 = 2
            goto L35
        L20:
            java.lang.String r0 = "zh_CN"
            boolean r4 = r4.equals(r0)
            if (r4 == 0) goto L34
            r4 = 1
            goto L35
        L2a:
            java.lang.String r0 = "ja_JP"
            boolean r4 = r4.equals(r0)
            if (r4 == 0) goto L34
            r4 = 0
            goto L35
        L34:
            r4 = -1
        L35:
            if (r4 == 0) goto L4d
            if (r4 == r3) goto L47
            if (r4 == r2) goto L41
            it.orsozoxi.android.orsonotes.helpers.count.DefaultWordCounter r4 = new it.orsozoxi.android.orsonotes.helpers.count.DefaultWordCounter
            r4.<init>()
            return r4
        L41:
            it.orsozoxi.android.orsonotes.helpers.count.IdeogramsWordCounter r4 = new it.orsozoxi.android.orsonotes.helpers.count.IdeogramsWordCounter
            r4.<init>()
            return r4
        L47:
            it.orsozoxi.android.orsonotes.helpers.count.IdeogramsWordCounter r4 = new it.orsozoxi.android.orsonotes.helpers.count.IdeogramsWordCounter
            r4.<init>()
            return r4
        L4d:
            it.orsozoxi.android.orsonotes.helpers.count.IdeogramsWordCounter r4 = new it.orsozoxi.android.orsonotes.helpers.count.IdeogramsWordCounter
            r4.<init>()
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: it.orsozoxi.android.orsonotes.helpers.count.CountFactory.getCounterInstanceByLocale(java.lang.String):it.orsozoxi.android.orsonotes.helpers.count.WordCounter");
    }
}
