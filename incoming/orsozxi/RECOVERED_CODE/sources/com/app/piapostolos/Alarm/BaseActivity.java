package com.app.piapostolos.Alarm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewConfiguration;
import com.app.piapostolos.Alarm.Services.AlarmServiceBroadcastReciever;
import java.lang.reflect.Field;

/* loaded from: classes.dex */
public abstract class BaseActivity extends Activity implements View.OnClickListener {
    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) throws IllegalAccessException, NoSuchFieldException, IllegalArgumentException {
        super.onCreate(bundle);
        try {
            ViewConfiguration viewConfiguration = ViewConfiguration.get(this);
            Field declaredField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if (declaredField != null) {
                declaredField.setAccessible(true);
                declaredField.setBoolean(viewConfiguration, false);
            }
        } catch (Exception unused) {
        }
    }

    protected void callMathAlarmScheduleService() {
        sendBroadcast(new Intent(this, (Class<?>) AlarmServiceBroadcastReciever.class), null);
    }
}
