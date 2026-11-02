package com.huawei.hms.analytics.framework.e;

import com.huawei.hms.analytics.core.log.HiLog;
import com.huawei.hms.analytics.framework.b.c;
import com.ibm.icu.impl.locale.BaseLocale;
import com.ibm.icu.impl.locale.LanguageTag;
import java.util.Calendar;
import java.util.UUID;

/* loaded from: classes2.dex */
public final class b {
    public long a = 1800000;
    public long b = 30000;
    public volatile boolean c = false;
    public volatile long d = 0;
    private a e = null;

    class a {
        String a = UUID.randomUUID().toString().replace(LanguageTag.SEP, "");
        boolean b;
        private long d;

        a(long j) {
            this.a += BaseLocale.SEP + j;
            this.d = j;
            this.b = true;
            b.this.c = false;
        }

        private void a(long j) {
            HiLog.i("SessionKeeper", "getNewSession() session is flush!");
            String string = UUID.randomUUID().toString();
            this.a = string;
            this.a = string.replace(LanguageTag.SEP, "");
            this.a += BaseLocale.SEP + j;
            this.d = j;
            this.b = true;
        }

        private static boolean a(long j, long j2) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(j);
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTimeInMillis(j2);
            return (calendar.get(1) == calendar2.get(1) && calendar.get(6) == calendar2.get(6)) ? false : true;
        }

        private boolean b(long j, long j2) {
            return j2 - j >= b.this.a;
        }

        final void a(String str, long j) {
            c cVarB = com.huawei.hms.analytics.framework.b.b.a().b(str);
            if (cVarB != null && cVarB.a) {
                cVarB.a = false;
                a(j);
                return;
            }
            if (b.this.c && j - b.this.d > b.this.b) {
                b.this.c = false;
                b.this.d = 0L;
                a(j);
            } else if (b(this.d, j) || a(this.d, j)) {
                a(j);
            } else {
                this.d = j;
                this.b = false;
            }
        }
    }

    public final String a() {
        a aVar = this.e;
        if (aVar != null) {
            return aVar.a;
        }
        HiLog.w("SessionKeeper", "getSessionName(): session not prepared. onEvent() must be called first.");
        return "";
    }

    final void a(String str, long j) {
        a aVar = this.e;
        if (aVar != null) {
            aVar.a(str, j);
        } else {
            HiLog.i("SessionKeeper", "Session is first flush");
            this.e = new a(j);
        }
    }

    public final boolean b() {
        a aVar = this.e;
        if (aVar != null) {
            return aVar.b;
        }
        HiLog.w("SessionKeeper", "isFirstEvent(): session not prepared. onEvent() must be called first.");
        return false;
    }
}
