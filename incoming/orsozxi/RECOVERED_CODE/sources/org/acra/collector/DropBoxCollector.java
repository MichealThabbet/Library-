package org.acra.collector;

import android.content.Context;
import android.os.Build;
import android.os.DropBoxManager;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;
import org.acra.ACRA;
import org.acra.ReportField;
import org.acra.builder.ReportBuilder;
import org.acra.collections.ImmutableList;
import org.acra.collector.Collector;
import org.acra.config.CoreConfiguration;
import org.acra.data.CrashReportData;
import org.acra.prefs.SharedPreferencesFactory;
import org.acra.util.PackageManagerWrapper;
import org.acra.util.SystemServices;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public final class DropBoxCollector extends BaseReportFieldCollector {
    private static final String[] SYSTEM_TAGS = {"system_app_anr", "system_app_wtf", "system_app_crash", "system_server_anr", "system_server_wtf", "system_server_crash", "BATTERY_DISCHARGE_INFO", "SYSTEM_RECOVERY_LOG", "SYSTEM_BOOT", "SYSTEM_LAST_KMSG", "APANIC_CONSOLE", "APANIC_THREADS", "SYSTEM_RESTART", "SYSTEM_TOMBSTONE", "data_app_strictmode"};
    private final SimpleDateFormat dateFormat;

    public DropBoxCollector() {
        super(ReportField.DROPBOX, new ReportField[0]);
        this.dateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss", Locale.getDefault());
    }

    @Override // org.acra.collector.BaseReportFieldCollector, org.acra.collector.Collector
    public Collector.Order getOrder() {
        return Collector.Order.FIRST;
    }

    @Override // org.acra.collector.BaseReportFieldCollector
    void collect(ReportField reportField, Context context, CoreConfiguration coreConfiguration, ReportBuilder reportBuilder, CrashReportData crashReportData) throws Exception {
        DropBoxManager dropBoxManager = SystemServices.getDropBoxManager(context);
        Calendar calendar = Calendar.getInstance();
        calendar.roll(12, -coreConfiguration.dropboxCollectionMinutes());
        long timeInMillis = calendar.getTimeInMillis();
        this.dateFormat.format(calendar.getTime());
        ArrayList<String> arrayList = new ArrayList();
        if (coreConfiguration.includeDropBoxSystemTags()) {
            arrayList.addAll(Arrays.asList(SYSTEM_TAGS));
        }
        ImmutableList<String> immutableListAdditionalDropBoxTags = coreConfiguration.additionalDropBoxTags();
        if (!immutableListAdditionalDropBoxTags.isEmpty()) {
            arrayList.addAll(immutableListAdditionalDropBoxTags);
        }
        if (arrayList.isEmpty()) {
            return;
        }
        JSONObject jSONObject = new JSONObject();
        for (String str : arrayList) {
            StringBuilder sb = new StringBuilder();
            DropBoxManager.Entry nextEntry = dropBoxManager.getNextEntry(str, timeInMillis);
            if (nextEntry == null) {
                sb.append("Nothing.");
                sb.append('\n');
            } else {
                while (nextEntry != null) {
                    long timeMillis = nextEntry.getTimeMillis();
                    calendar.setTimeInMillis(timeMillis);
                    sb.append('@');
                    sb.append(this.dateFormat.format(calendar.getTime()));
                    sb.append('\n');
                    String text = nextEntry.getText(500);
                    if (text != null) {
                        sb.append("Text: ");
                        sb.append(text);
                        sb.append('\n');
                    } else {
                        sb.append("Not Text!");
                        sb.append('\n');
                    }
                    nextEntry.close();
                    nextEntry = dropBoxManager.getNextEntry(str, timeMillis);
                }
                try {
                    jSONObject.put(str, sb.toString());
                } catch (JSONException e) {
                    ACRA.log.w(ACRA.LOG_TAG, "Failed to collect data for tag " + str, e);
                }
            }
        }
        crashReportData.put(ReportField.DROPBOX, jSONObject);
    }

    @Override // org.acra.collector.BaseReportFieldCollector
    boolean shouldCollect(Context context, CoreConfiguration coreConfiguration, ReportField reportField, ReportBuilder reportBuilder) {
        return super.shouldCollect(context, coreConfiguration, reportField, reportBuilder) && (Build.VERSION.SDK_INT >= 16 || new PackageManagerWrapper(context).hasPermission("android.permission.READ_LOGS")) && new SharedPreferencesFactory(context, coreConfiguration).create().getBoolean(ACRA.PREF_ENABLE_SYSTEM_LOGS, true);
    }
}
