package com.app.orsozoxi;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ExpandableListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.app.orsozoxi.Adapters.ExpandableListAdapter;
import com.app.orsozoxi.Beans.AlarmReceiver_afr;
import com.app.orsozoxi.Others.checksim;
import com.navdrawer.SimpleSideDrawer;
import java.util.Calendar;

/* loaded from: classes.dex */
public class Atraf extends BaseActivity {
    AlarmManager alarmManager;
    Intent alarmintent;
    RadioButton b00;
    RadioButton b2;
    DatePicker da;
    private GestureDetector gestureDetector;
    private PendingIntent operation1;
    private RadioGroup rad;
    Button savee;
    TimePicker ti;
    String type = "";
    Button update;

    @Override // com.app.orsozoxi.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_atraf);
        ((TextView) findViewById(R.id.tv_header_title)).setText(R.string.s_etraf);
        this.b2 = (RadioButton) findViewById(R.id.radio2);
        this.b00 = (RadioButton) findViewById(R.id.radio3);
        getWindow().addFlags(128);
        new checksim();
        Button button = (Button) findViewById(R.id.btn_slide);
        final SimpleSideDrawer simpleSideDrawer = new SimpleSideDrawer(this);
        simpleSideDrawer.setLeftBehindContentView(R.layout.left_menu_ex);
        button.setVisibility(0);
        Slider.prepareListData(this);
        ((ExpandableListView) findViewById(R.id.lvExp)).setAdapter(new ExpandableListAdapter(this, this, Slider.listDataHeader, Slider.listDataChild));
        button.setOnClickListener(new View.OnClickListener() { // from class: com.app.orsozoxi.Atraf.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                simpleSideDrawer.toggleLeftDrawer();
            }
        });
        this.da = (DatePicker) findViewById(R.id.daat);
        this.ti = (TimePicker) findViewById(R.id.tiat);
        this.savee = (Button) findViewById(R.id.save3);
        this.update = (Button) findViewById(R.id.update22);
        this.rad = (RadioGroup) findViewById(R.id.radat);
        this.alarmintent = new Intent(getApplicationContext(), (Class<?>) AlarmReceiver_afr.class);
        this.alarmManager = (AlarmManager) getBaseContext().getSystemService("alarm");
        this.operation1 = PendingIntent.getBroadcast(getApplicationContext(), 9, this.alarmintent, 201326594);
        Calendar calendar = Calendar.getInstance();
        this.da.init(calendar.get(1), calendar.get(2), calendar.get(5), null);
        this.ti.setCurrentHour(Integer.valueOf(calendar.get(11)));
        this.ti.setCurrentMinute(Integer.valueOf(calendar.get(12)));
        this.savee.setOnClickListener(new View.OnClickListener() { // from class: com.app.orsozoxi.Atraf.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Calendar calendar2 = Calendar.getInstance();
                Calendar calendar3 = Calendar.getInstance();
                calendar3.set(Atraf.this.da.getYear(), Atraf.this.da.getMonth(), Atraf.this.da.getDayOfMonth(), Atraf.this.ti.getCurrentHour().intValue(), Atraf.this.ti.getCurrentMinute().intValue(), 0);
                if (calendar3.compareTo(calendar2) <= 0) {
                    Toast.makeText(Atraf.this.getApplicationContext(), "Invalid Date/Time", 1).show();
                    return;
                }
                Atraf.this.setAlarm(calendar3);
                Toast.makeText(Atraf.this.getApplicationContext(), R.string.alarm_activated, 1).show();
                Atraf.this.finish();
            }
        });
        this.update.setOnClickListener(new View.OnClickListener() { // from class: com.app.orsozoxi.Atraf.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Toast.makeText(Atraf.this.getApplicationContext(), R.string.alarm_deactivated, 1).show();
                Atraf.this.cancelalarm();
                Atraf.this.finish();
            }
        });
    }

    public void setAlarm(Calendar calendar) {
        this.alarmManager.set(0, calendar.getTimeInMillis(), this.operation1);
    }

    public void cancelalarm() {
        this.alarmManager.cancel(this.operation1);
    }
}
