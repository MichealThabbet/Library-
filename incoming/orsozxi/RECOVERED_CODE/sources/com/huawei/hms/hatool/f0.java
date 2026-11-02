package com.huawei.hms.hatool;

import com.ibm.icu.impl.locale.BaseLocale;
import com.ibm.icu.impl.locale.LanguageTag;
import java.util.Calendar;
import java.util.UUID;

/* loaded from: classes2.dex */
public class f0 {
    public long a = 1800000;
    public volatile boolean b = false;
    public a c = null;

    public class a {
        public String a = UUID.randomUUID().toString().replace(LanguageTag.SEP, "");
        public boolean b;
        public long c;

        public a(long j) {
            this.a += BaseLocale.SEP + j;
            this.c = j;
            this.b = true;
            f0.this.b = false;
        }

        public void a(long j) {
            if (f0.this.b) {
                f0.this.b = false;
                b(j);
            } else if (b(this.c, j) || a(this.c, j)) {
                b(j);
            } else {
                this.c = j;
                this.b = false;
            }
        }

        public final boolean a(long j, long j2) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(j);
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTimeInMillis(j2);
            return (calendar.get(1) == calendar2.get(1) && calendar.get(6) == calendar2.get(6)) ? false : true;
        }

        public final void b(long j) {
            y.c("hmsSdk", "getNewSession() session is flush!");
            String string = UUID.randomUUID().toString();
            this.a = string;
            this.a = string.replace(LanguageTag.SEP, "");
            this.a += BaseLocale.SEP + j;
            this.c = j;
            this.b = true;
        }

        public final boolean b(long j, long j2) {
            return j2 - j >= f0.this.a;
        }
    }

    public String a() {
        a aVar = this.c;
        if (aVar != null) {
            return aVar.a;
        }
        y.f("hmsSdk", "getSessionName(): session not prepared. onEvent() must be called first.");
        return "";
    }

    public void a(long j) {
        a aVar = this.c;
        if (aVar != null) {
            aVar.a(j);
        } else {
            y.c("hmsSdk", "Session is first flush");
            this.c = new a(j);
        }
    }

    public boolean b() {
        a aVar = this.c;
        if (aVar != null) {
            return aVar.b;
        }
        y.f("hmsSdk", "isFirstEvent(): session not prepared. onEvent() must be called first.");
        return false;
    }
}
