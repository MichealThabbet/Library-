package com.app.orsozoxi.Alarm;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import com.app.orsozoxi.Alarm.Database.Database;
import com.app.orsozoxi.R;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class AlarmListAdapter extends BaseAdapter {
    public static final String[] ALARM_FIELDS = {Database.COLUMN_ALARM_ACTIVE, Database.COLUMN_ALARM_TIME, Database.COLUMN_ALARM_DAYS};
    private AlarmActivity alarmActivity;
    private List<Alarm> alarms = new ArrayList();

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return i;
    }

    public AlarmListAdapter(AlarmActivity alarmActivity) {
        this.alarmActivity = alarmActivity;
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.alarms.size();
    }

    @Override // android.widget.Adapter
    public Object getItem(int i) {
        return this.alarms.get(i);
    }

    @Override // android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(this.alarmActivity).inflate(R.layout.alarm_list_element, (ViewGroup) null);
        }
        Alarm alarm = (Alarm) getItem(i);
        CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkBox_alarm_active);
        checkBox.setChecked(alarm.getAlarmActive().booleanValue());
        checkBox.setTag(Integer.valueOf(i));
        checkBox.setOnClickListener(this.alarmActivity);
        ((TextView) view.findViewById(R.id.textView_alarm_time)).setText(alarm.getAlarmTimeString());
        ((TextView) view.findViewById(R.id.textView_alarm_days)).setText(alarm.getRepeatDaysString(this.alarmActivity.getApplicationContext()));
        return view;
    }

    public List<Alarm> getMathAlarms() {
        return this.alarms;
    }

    public void setMathAlarms(List<Alarm> list) {
        this.alarms = list;
    }
}
