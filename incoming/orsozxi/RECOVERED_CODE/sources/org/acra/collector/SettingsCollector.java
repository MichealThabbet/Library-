package org.acra.collector;

import android.content.ContentResolver;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import org.acra.ACRA;
import org.acra.ReportField;
import org.acra.builder.ReportBuilder;
import org.acra.config.CoreConfiguration;
import org.acra.data.CrashReportData;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public final class SettingsCollector extends BaseReportFieldCollector {
    private static final String ERROR = "Error: ";

    public SettingsCollector() {
        super(ReportField.SETTINGS_SYSTEM, ReportField.SETTINGS_SECURE, ReportField.SETTINGS_GLOBAL);
    }

    /* renamed from: org.acra.collector.SettingsCollector$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$acra$ReportField;

        static {
            int[] iArr = new int[ReportField.values().length];
            $SwitchMap$org$acra$ReportField = iArr;
            try {
                iArr[ReportField.SETTINGS_SYSTEM.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$acra$ReportField[ReportField.SETTINGS_SECURE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$acra$ReportField[ReportField.SETTINGS_GLOBAL.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    @Override // org.acra.collector.BaseReportFieldCollector
    void collect(ReportField reportField, Context context, CoreConfiguration coreConfiguration, ReportBuilder reportBuilder, CrashReportData crashReportData) throws Exception {
        int i = AnonymousClass1.$SwitchMap$org$acra$ReportField[reportField.ordinal()];
        if (i == 1) {
            crashReportData.put(ReportField.SETTINGS_SYSTEM, collectSettings(context, coreConfiguration, Settings.System.class));
        } else if (i == 2) {
            crashReportData.put(ReportField.SETTINGS_SECURE, collectSettings(context, coreConfiguration, Settings.Secure.class));
        } else {
            if (i == 3) {
                crashReportData.put(ReportField.SETTINGS_GLOBAL, Build.VERSION.SDK_INT >= 17 ? collectSettings(context, coreConfiguration, Settings.Global.class) : null);
                return;
            }
            throw new IllegalArgumentException();
        }
    }

    private JSONObject collectSettings(Context context, CoreConfiguration coreConfiguration, Class<?> cls) throws IllegalAccessException, JSONException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        JSONObject jSONObject = new JSONObject();
        Field[] fields = cls.getFields();
        Method method = cls.getMethod("getString", ContentResolver.class, String.class);
        for (Field field : fields) {
            if (!field.isAnnotationPresent(Deprecated.class) && field.getType() == String.class && isAuthorized(coreConfiguration, field)) {
                try {
                    Object objInvoke = method.invoke(null, context.getContentResolver(), field.get(null));
                    if (objInvoke != null) {
                        jSONObject.put(field.getName(), objInvoke);
                    }
                } catch (Exception e) {
                    ACRA.log.w(ACRA.LOG_TAG, ERROR, e);
                }
            }
        }
        return jSONObject;
    }

    private boolean isAuthorized(CoreConfiguration coreConfiguration, Field field) {
        if (field == null || field.getName().startsWith("WIFI_AP")) {
            return false;
        }
        Iterator<String> it2 = coreConfiguration.excludeMatchingSettingsKeys().iterator();
        while (it2.hasNext()) {
            if (field.getName().matches(it2.next())) {
                return false;
            }
        }
        return true;
    }
}
