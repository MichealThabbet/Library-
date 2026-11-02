package com.huawei.hms.analytics;

import android.content.Context;
import android.os.Bundle;
import com.huawei.hms.analytics.core.log.HiLog;
import com.huawei.hms.analytics.internal.filter.EventFilter;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public final class bj {
    private static Context klm;
    private static bj lmn;
    private static bk ikl = new EventFilter();
    private static bm ijk = new bm();

    static class lmn implements bl {
        private lmn() {
        }

        /* synthetic */ lmn(byte b) {
            this();
        }

        private static boolean lmn() {
            return dl.klm(bj.klm, "global_v2", "is_analytics_enabled", true);
        }

        @Override // com.huawei.hms.analytics.bl
        public final void klm(final String str, final String str2, final List<JSONObject> list) {
            if (lmn()) {
                cx.lmn().lmn(new Runnable() { // from class: com.huawei.hms.analytics.bj.lmn.2
                    @Override // java.lang.Runnable
                    public final void run() {
                        bh bhVarLmn;
                        if (!ba.lmn() || (bhVarLmn = fgh.lmn().lmn(str)) == null) {
                            return;
                        }
                        bhVarLmn.lmn(list);
                    }
                });
            } else {
                HiLog.w("ThirdRingback", HiLog.ErrorCode.IE006, "The Analytics Kit is disabled");
            }
        }

        @Override // com.huawei.hms.analytics.bl
        public final String lmn(String str) {
            if (!dc.lmn(str)) {
                return null;
            }
            Map<String, Object> mapKlm = ar.lmn().klm().klm();
            return mapKlm.containsKey(str) ? String.valueOf(mapKlm.get(str)) : "";
        }

        @Override // com.huawei.hms.analytics.bl
        public final void lmn(final String str, final String str2, final List<JSONObject> list) {
            if (lmn()) {
                cx.lmn().lmn(new Runnable() { // from class: com.huawei.hms.analytics.bj.lmn.1
                    @Override // java.lang.Runnable
                    public final void run() {
                        bh bhVarLmn;
                        if (!ba.lmn() || (bhVarLmn = fgh.lmn().lmn(str)) == null) {
                            return;
                        }
                        bhVarLmn.lmn(str2, list);
                    }
                });
            } else {
                HiLog.w("ThirdRingback", HiLog.ErrorCode.IE006, "The Analytics Kit is disabled");
            }
        }
    }

    private bj() {
    }

    public static bm klm() {
        return ijk;
    }

    private static synchronized void klm(Context context) {
        String str;
        String str2;
        if (lmn == null) {
            lmn = new bj();
            if (context == null) {
                return;
            }
            klm = context;
            ikl.lmn(context);
            ikl.lmn(new lmn((byte) 0));
            bm bmVar = ijk;
            for (String str3 : bm.lmn) {
                try {
                    Class<?> cls = Class.forName(str3);
                    Object objNewInstance = cls.newInstance();
                    bmVar.ikl.put(str3, objNewInstance);
                    cls.getDeclaredMethod("initialize", Context.class).invoke(objNewInstance, context);
                    bmVar.klm.put(str3, cls.getDeclaredMethod("onEvent", String.class, Bundle.class, Bundle.class));
                } catch (ClassNotFoundException unused) {
                    str = "IncidentAdapter";
                    str2 = "Class not found warning";
                    HiLog.w(str, str2);
                } catch (IllegalAccessException unused2) {
                    str = "IncidentAdapter";
                    str2 = "Illegal access warning";
                    HiLog.w(str, str2);
                } catch (InstantiationException unused3) {
                    str = "IncidentAdapter";
                    str2 = "Instantiation warning";
                    HiLog.w(str, str2);
                } catch (NoSuchMethodException unused4) {
                    str = "IncidentAdapter";
                    str2 = "No such method warning";
                    HiLog.w(str, str2);
                } catch (InvocationTargetException unused5) {
                    str = "IncidentAdapter";
                    str2 = "Invocation target warning";
                    HiLog.w(str, str2);
                }
            }
        }
    }

    public static bj lmn(Context context) {
        if (lmn == null) {
            klm(context);
        }
        return lmn;
    }

    public static bk lmn() {
        return ikl;
    }
}
