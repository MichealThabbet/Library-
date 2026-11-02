package org.acra.collector;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import net.fortuna.ical4j.model.Property;
import org.acra.ReportField;
import org.acra.builder.ReportBuilder;
import org.acra.config.CoreConfiguration;
import org.acra.data.CrashReportData;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public final class ReflectionCollector extends BaseReportFieldCollector {
    public ReflectionCollector() {
        super(ReportField.BUILD, ReportField.BUILD_CONFIG, ReportField.ENVIRONMENT);
    }

    /* renamed from: org.acra.collector.ReflectionCollector$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$acra$ReportField;

        static {
            int[] iArr = new int[ReportField.values().length];
            $SwitchMap$org$acra$ReportField = iArr;
            try {
                iArr[ReportField.BUILD.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$acra$ReportField[ReportField.BUILD_CONFIG.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$acra$ReportField[ReportField.ENVIRONMENT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    @Override // org.acra.collector.BaseReportFieldCollector
    void collect(ReportField reportField, Context context, CoreConfiguration coreConfiguration, ReportBuilder reportBuilder, CrashReportData crashReportData) throws JSONException, IllegalAccessException, SecurityException, ClassNotFoundException, IllegalArgumentException {
        JSONObject jSONObject = new JSONObject();
        int i = AnonymousClass1.$SwitchMap$org$acra$ReportField[reportField.ordinal()];
        if (i == 1) {
            collectConstants(Build.class, jSONObject);
            JSONObject jSONObject2 = new JSONObject();
            collectConstants(Build.VERSION.class, jSONObject2);
            jSONObject.put(Property.VERSION, jSONObject2);
        } else if (i == 2) {
            collectConstants(getBuildConfigClass(context, coreConfiguration), jSONObject);
        } else if (i == 3) {
            collectStaticGettersResults(Environment.class, jSONObject);
        } else {
            throw new IllegalArgumentException();
        }
        crashReportData.put(reportField, jSONObject);
    }

    private static void collectConstants(Class<?> cls, JSONObject jSONObject) throws IllegalAccessException, JSONException, SecurityException, IllegalArgumentException {
        for (Field field : cls.getFields()) {
            try {
                Object obj = field.get(null);
                if (obj != null) {
                    if (field.getType().isArray()) {
                        jSONObject.put(field.getName(), new JSONArray((Collection) Arrays.asList((Object[]) obj)));
                    } else {
                        jSONObject.put(field.getName(), obj);
                    }
                }
            } catch (IllegalAccessException | IllegalArgumentException unused) {
            }
        }
    }

    private void collectStaticGettersResults(Class<?> cls, JSONObject jSONObject) throws JSONException, SecurityException {
        for (Method method : cls.getMethods()) {
            if (method.getParameterTypes().length == 0 && ((method.getName().startsWith("get") || method.getName().startsWith("is")) && !"getClass".equals(method.getName()))) {
                try {
                    jSONObject.put(method.getName(), method.invoke(null, (Object[]) null));
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException unused) {
                }
            }
        }
    }

    private Class<?> getBuildConfigClass(Context context, CoreConfiguration coreConfiguration) throws ClassNotFoundException {
        Class<?> clsBuildConfigClass = coreConfiguration.buildConfigClass();
        if (!clsBuildConfigClass.equals(Object.class)) {
            return clsBuildConfigClass;
        }
        return Class.forName(context.getPackageName() + ".BuildConfig");
    }
}
