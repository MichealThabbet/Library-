package com.app.orsozoxi.Alarm.Services;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import com.app.orsozoxi.R;

/* loaded from: classes.dex */
public class MessageActivity extends Activity {
    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_message);
        setFinishOnTouchOutside(false);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        getWindow().addFlags(128);
        int intExtra = getIntent().getIntExtra("id", -1);
        ((TextView) findViewById(R.id.tv_plugin)).setText("ID+" + intExtra);
    }
}
