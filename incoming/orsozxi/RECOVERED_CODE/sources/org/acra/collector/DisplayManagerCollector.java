package org.acra.collector;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.display.DisplayManager;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.view.Display;
import android.view.WindowManager;
import com.ibm.icu.impl.locale.LanguageTag;
import it.piapostolos.android.pinotes.db.DbHelper;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import org.acra.ACRA;
import org.acra.ReportField;
import org.acra.builder.ReportBuilder;
import org.acra.config.CoreConfiguration;
import org.acra.data.CrashReportData;
import org.apache.poi.ss.util.CellUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public final class DisplayManagerCollector extends BaseReportFieldCollector {
    public DisplayManagerCollector() {
        super(ReportField.DISPLAY, new ReportField[0]);
    }

    @Override // org.acra.collector.BaseReportFieldCollector
    void collect(ReportField reportField, Context context, CoreConfiguration coreConfiguration, ReportBuilder reportBuilder, CrashReportData crashReportData) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        for (Display display : getDisplays(context)) {
            try {
                jSONObject.put(String.valueOf(display.getDisplayId()), collectDisplayData(display));
            } catch (JSONException e) {
                ACRA.log.w(ACRA.LOG_TAG, "Failed to collect data for display " + display.getDisplayId(), e);
            }
        }
        crashReportData.put(ReportField.DISPLAY, jSONObject);
    }

    private Display[] getDisplays(Context context) {
        return Build.VERSION.SDK_INT >= 17 ? ((DisplayManager) context.getSystemService("display")).getDisplays() : new Display[]{((WindowManager) context.getSystemService("window")).getDefaultDisplay()};
    }

    private JSONObject collectDisplayData(Display display) throws JSONException, SecurityException {
        display.getMetrics(new DisplayMetrics());
        JSONObject jSONObject = new JSONObject();
        collectCurrentSizeRange(display, jSONObject);
        collectFlags(display, jSONObject);
        collectMetrics(display, jSONObject);
        collectRealMetrics(display, jSONObject);
        collectName(display, jSONObject);
        collectRealSize(display, jSONObject);
        collectRectSize(display, jSONObject);
        collectSize(display, jSONObject);
        collectRotation(display, jSONObject);
        collectIsValid(display, jSONObject);
        jSONObject.put("orientation", display.getRotation()).put("refreshRate", display.getRefreshRate());
        jSONObject.put("height", display.getHeight()).put("width", display.getWidth()).put("pixelFormat", display.getPixelFormat());
        return jSONObject;
    }

    private void collectIsValid(Display display, JSONObject jSONObject) throws JSONException {
        if (Build.VERSION.SDK_INT >= 17) {
            jSONObject.put("isValid", display.isValid());
        }
    }

    private void collectRotation(Display display, JSONObject jSONObject) throws JSONException {
        jSONObject.put(CellUtil.ROTATION, rotationToString(display.getRotation()));
    }

    private String rotationToString(int i) {
        return i != 0 ? i != 1 ? i != 2 ? i != 3 ? String.valueOf(i) : "ROTATION_270" : "ROTATION_180" : "ROTATION_90" : "ROTATION_0";
    }

    private void collectRectSize(Display display, JSONObject jSONObject) throws JSONException {
        Rect rect = new Rect();
        display.getRectSize(rect);
        jSONObject.put("rectSize", new JSONArray((Collection) Arrays.asList(Integer.valueOf(rect.top), Integer.valueOf(rect.left), Integer.valueOf(rect.width()), Integer.valueOf(rect.height()))));
    }

    private void collectSize(Display display, JSONObject jSONObject) throws JSONException {
        Point point = new Point();
        display.getSize(point);
        jSONObject.put(DbHelper.KEY_ATTACHMENT_SIZE, new JSONArray((Collection) Arrays.asList(Integer.valueOf(point.x), Integer.valueOf(point.y))));
    }

    private void collectRealSize(Display display, JSONObject jSONObject) throws JSONException {
        if (Build.VERSION.SDK_INT >= 17) {
            Point point = new Point();
            display.getRealSize(point);
            jSONObject.put("realSize", new JSONArray((Collection) Arrays.asList(Integer.valueOf(point.x), Integer.valueOf(point.y))));
        }
    }

    private void collectCurrentSizeRange(Display display, JSONObject jSONObject) throws JSONException {
        if (Build.VERSION.SDK_INT >= 16) {
            Point point = new Point();
            Point point2 = new Point();
            display.getCurrentSizeRange(point, point2);
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("smallest", new JSONArray((Collection) Arrays.asList(Integer.valueOf(point.x), Integer.valueOf(point.y))));
            jSONObject2.put("largest", new JSONArray((Collection) Arrays.asList(Integer.valueOf(point2.x), Integer.valueOf(point2.y))));
            jSONObject.put("currentSizeRange", jSONObject2);
        }
    }

    private void collectFlags(Display display, JSONObject jSONObject) throws JSONException, SecurityException {
        if (Build.VERSION.SDK_INT >= 17) {
            SparseArray<String> sparseArray = new SparseArray<>();
            int flags = display.getFlags();
            for (Field field : display.getClass().getFields()) {
                if (field.getName().startsWith("FLAG_")) {
                    try {
                        sparseArray.put(field.getInt(null), field.getName());
                    } catch (IllegalAccessException unused) {
                    }
                }
            }
            jSONObject.put("flags", activeFlags(sparseArray, flags));
        }
    }

    private void collectName(Display display, JSONObject jSONObject) throws JSONException {
        if (Build.VERSION.SDK_INT >= 17) {
            jSONObject.put("name", display.getName());
        }
    }

    private void collectMetrics(Display display, JSONObject jSONObject) throws JSONException {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);
        JSONObject jSONObject2 = new JSONObject();
        collectMetrics(displayMetrics, jSONObject2);
        jSONObject.put("metrics", jSONObject2);
    }

    private void collectRealMetrics(Display display, JSONObject jSONObject) throws JSONException {
        if (Build.VERSION.SDK_INT >= 17) {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            display.getRealMetrics(displayMetrics);
            JSONObject jSONObject2 = new JSONObject();
            collectMetrics(displayMetrics, jSONObject2);
            jSONObject.put("realMetrics", jSONObject2);
        }
    }

    private void collectMetrics(DisplayMetrics displayMetrics, JSONObject jSONObject) throws JSONException {
        jSONObject.put("density", displayMetrics.density).put("densityDpi", displayMetrics.densityDpi).put("scaledDensity", LanguageTag.PRIVATEUSE + displayMetrics.scaledDensity).put("widthPixels", displayMetrics.widthPixels).put("heightPixels", displayMetrics.heightPixels).put("xdpi", displayMetrics.xdpi).put("ydpi", displayMetrics.ydpi);
    }

    private String activeFlags(SparseArray<String> sparseArray, int i) {
        StringBuilder sb = new StringBuilder();
        for (int i2 = 0; i2 < sparseArray.size(); i2++) {
            int iKeyAt = sparseArray.keyAt(i2) & i;
            if (iKeyAt > 0) {
                if (sb.length() > 0) {
                    sb.append('+');
                }
                sb.append(sparseArray.get(iKeyAt));
            }
        }
        return sb.toString();
    }
}
