package com.app.orsozoxi.Alarm;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.app.orsozoxi.Adapters.ExpandableListAdapter;
import com.app.orsozoxi.Alarm.Database.Database;
import com.app.orsozoxi.Alarm.Preferences.AlarmPreferencesActivity;
import com.app.orsozoxi.R;
import com.app.orsozoxi.Slider;
import com.navdrawer.SimpleSideDrawer;
import it.orsozoxi.android.orsonotes.R2;
import java.io.IOException;
import java.util.List;

/* loaded from: classes.dex */
public class AlarmActivity extends BaseActivity {
    AlarmListAdapter alarmListAdapter;
    ListView mathAlarmListView;
    ImageButton newButton;

    @Override // com.app.orsozoxi.Alarm.BaseActivity, android.app.Activity
    protected void onCreate(Bundle bundle) throws IllegalAccessException, NoSuchFieldException, IllegalArgumentException {
        super.onCreate(bundle);
        setContentView(R.layout.alarm_activity);
        ((TextView) findViewById(R.id.tv_header_title)).setText("الرسالة اليومية");
        Button button = (Button) findViewById(R.id.btn_slide);
        final SimpleSideDrawer simpleSideDrawer = new SimpleSideDrawer(this);
        simpleSideDrawer.setLeftBehindContentView(R.layout.left_menu_ex);
        button.setVisibility(0);
        Slider.prepareListData(this);
        ((ExpandableListView) findViewById(R.id.lvExp)).setAdapter(new ExpandableListAdapter(this, this, Slider.listDataHeader, Slider.listDataChild));
        button.setOnClickListener(new View.OnClickListener() { // from class: com.app.orsozoxi.Alarm.AlarmActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                simpleSideDrawer.toggleLeftDrawer();
            }
        });
        ListView listView = (ListView) findViewById(android.R.id.list);
        this.mathAlarmListView = listView;
        listView.setLongClickable(true);
        this.mathAlarmListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() { // from class: com.app.orsozoxi.Alarm.AlarmActivity.2
            @Override // android.widget.AdapterView.OnItemLongClickListener
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long j) {
                view.performHapticFeedback(0);
                final Alarm alarm = (Alarm) AlarmActivity.this.alarmListAdapter.getItem(i);
                AlertDialog.Builder builder = new AlertDialog.Builder(AlarmActivity.this);
                builder.setTitle(AlarmActivity.this.getString(R.string.button_delete));
                builder.setMessage(AlarmActivity.this.getString(R.string.delete_alarm));
                builder.setPositiveButton(AlarmActivity.this.getString(R.string.ok), new DialogInterface.OnClickListener() { // from class: com.app.orsozoxi.Alarm.AlarmActivity.2.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i2) throws ClassNotFoundException, IOException {
                        Database.init(AlarmActivity.this);
                        Database.deleteEntry(alarm);
                        AlarmActivity.this.callMathAlarmScheduleService();
                        AlarmActivity.this.updateAlarmList();
                    }
                });
                builder.setNegativeButton(AlarmActivity.this.getString(R.string.cancel), new DialogInterface.OnClickListener() { // from class: com.app.orsozoxi.Alarm.AlarmActivity.2.2
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i2) {
                        dialogInterface.dismiss();
                    }
                });
                builder.show();
                return true;
            }
        });
        callMathAlarmScheduleService();
        AlarmListAdapter alarmListAdapter = new AlarmListAdapter(this);
        this.alarmListAdapter = alarmListAdapter;
        this.mathAlarmListView.setAdapter((ListAdapter) alarmListAdapter);
        this.mathAlarmListView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.app.orsozoxi.Alarm.AlarmActivity.3
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                view.performHapticFeedback(1);
                Alarm alarm = (Alarm) AlarmActivity.this.alarmListAdapter.getItem(i);
                Intent intent = new Intent(AlarmActivity.this, (Class<?>) AlarmPreferencesActivity.class);
                intent.putExtra("alarm", alarm);
                AlarmActivity.this.startActivity(intent);
            }
        });
    }

    @Override // android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        if (i != 1234 || Build.VERSION.SDK_INT < 23) {
            return;
        }
        if (Settings.canDrawOverlays(this)) {
            startActivity(new Intent(this, (Class<?>) AlarmPreferencesActivity.class));
        } else {
            Toast.makeText(getApplicationContext(), "يجب الموافقة عن تفعيل ظهور الرسالة. من فضلك اضغط على اصف مرة أخرى ثم قم بالموافقة على الطلب", 1).show();
        }
    }

    public void addButtonAction(View view) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION");
                intent.setData(Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, R2.color.abc_tint_btn_checkable);
                return;
            }
            startActivity(new Intent(this, (Class<?>) AlarmPreferencesActivity.class));
            return;
        }
        startActivity(new Intent(this, (Class<?>) AlarmPreferencesActivity.class));
    }

    @Override // android.app.Activity
    protected void onPause() {
        Database.deactivate();
        super.onPause();
    }

    @Override // android.app.Activity
    protected void onResume() throws ClassNotFoundException, IOException {
        super.onResume();
        updateAlarmList();
    }

    public void updateAlarmList() throws ClassNotFoundException, IOException {
        Database.init(this);
        final List<Alarm> all = Database.getAll();
        this.alarmListAdapter.setMathAlarms(all);
        runOnUiThread(new Runnable() { // from class: com.app.orsozoxi.Alarm.AlarmActivity.4
            @Override // java.lang.Runnable
            public void run() {
                AlarmActivity.this.alarmListAdapter.notifyDataSetChanged();
                if (all.size() > 0) {
                    AlarmActivity.this.findViewById(android.R.id.empty).setVisibility(4);
                } else {
                    AlarmActivity.this.findViewById(android.R.id.empty).setVisibility(0);
                }
            }
        });
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) throws IOException {
        if (view.getId() == R.id.checkBox_alarm_active) {
            CheckBox checkBox = (CheckBox) view;
            Alarm alarm = (Alarm) this.alarmListAdapter.getItem(((Integer) checkBox.getTag()).intValue());
            alarm.setAlarmActive(Boolean.valueOf(checkBox.isChecked()));
            Database.update(alarm);
            callMathAlarmScheduleService();
            if (checkBox.isChecked()) {
                Toast.makeText(this, alarm.getTimeUntilNextAlarmMessage(this), 1).show();
            }
        }
    }
}
