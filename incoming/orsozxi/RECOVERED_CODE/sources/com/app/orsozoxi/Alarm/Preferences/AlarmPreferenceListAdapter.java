package com.app.orsozoxi.Alarm.Preferences;

import android.content.Context;
import android.database.Cursor;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;
import com.app.orsozoxi.Alarm.Alarm;
import com.app.orsozoxi.Alarm.Preferences.AlarmPreference;
import com.app.orsozoxi.R;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class AlarmPreferenceListAdapter extends BaseAdapter {
    private Alarm alarm;
    private String[] alarmTonePaths;
    private String[] alarmTones;
    private Context context;
    private List<AlarmPreference> preferences = new ArrayList();
    private final String[] repeatDays = new String[7];
    private final String[] alarmDifficulties = new String[3];

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return i;
    }

    public AlarmPreferenceListAdapter(Context context, Alarm alarm) {
        setContext(context);
        this.repeatDays[0] = context.getString(R.string.sun);
        this.repeatDays[1] = context.getString(R.string.mon);
        this.repeatDays[2] = context.getString(R.string.tues);
        this.repeatDays[3] = context.getString(R.string.wed);
        this.repeatDays[4] = context.getString(R.string.thurs);
        this.repeatDays[5] = context.getString(R.string.fri);
        this.repeatDays[6] = context.getString(R.string.satur);
        this.alarmDifficulties[0] = context.getString(R.string.easy);
        this.alarmDifficulties[1] = context.getString(R.string.normal);
        this.alarmDifficulties[2] = context.getString(R.string.hard);
        RingtoneManager ringtoneManager = new RingtoneManager(getContext());
        ringtoneManager.setType(4);
        Cursor cursor = ringtoneManager.getCursor();
        String[] strArr = new String[cursor.getCount() + 1];
        this.alarmTones = strArr;
        strArr[0] = context.getString(R.string.silent);
        String[] strArr2 = new String[cursor.getCount() + 1];
        this.alarmTonePaths = strArr2;
        strArr2[0] = "";
        if (cursor.moveToFirst()) {
            do {
                this.alarmTones[cursor.getPosition() + 1] = ringtoneManager.getRingtone(cursor.getPosition()).getTitle(getContext());
                this.alarmTonePaths[cursor.getPosition() + 1] = ringtoneManager.getRingtoneUri(cursor.getPosition()).toString();
            } while (cursor.moveToNext());
        }
        cursor.close();
        setMathAlarm(alarm, context);
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.preferences.size();
    }

    @Override // android.widget.Adapter
    public Object getItem(int i) {
        return this.preferences.get(i);
    }

    @Override // android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        AlarmPreference alarmPreference = (AlarmPreference) getItem(i);
        LayoutInflater layoutInflaterFrom = LayoutInflater.from(getContext());
        if (AnonymousClass1.$SwitchMap$com$app$orsozoxi$Alarm$Preferences$AlarmPreference$Type[alarmPreference.getType().ordinal()] == 1) {
            if (view == null) {
                view = layoutInflaterFrom.inflate(android.R.layout.simple_list_item_checked, (ViewGroup) null);
            }
            CheckedTextView checkedTextView = (CheckedTextView) view.findViewById(android.R.id.text1);
            checkedTextView.setText(alarmPreference.getTitle());
            checkedTextView.setChecked(((Boolean) alarmPreference.getValue()).booleanValue());
        } else {
            if (view == null) {
                view = layoutInflaterFrom.inflate(android.R.layout.simple_list_item_2, (ViewGroup) null);
            }
            TextView textView = (TextView) view.findViewById(android.R.id.text1);
            textView.setTextSize(18.0f);
            textView.setText(alarmPreference.getTitle());
            ((TextView) view.findViewById(android.R.id.text2)).setText(alarmPreference.getSummary());
        }
        return view;
    }

    /* renamed from: com.app.orsozoxi.Alarm.Preferences.AlarmPreferenceListAdapter$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$app$orsozoxi$Alarm$Preferences$AlarmPreference$Key;
        static final /* synthetic */ int[] $SwitchMap$com$app$orsozoxi$Alarm$Preferences$AlarmPreference$Type;

        static {
            int[] iArr = new int[AlarmPreference.Key.values().length];
            $SwitchMap$com$app$orsozoxi$Alarm$Preferences$AlarmPreference$Key = iArr;
            try {
                iArr[AlarmPreference.Key.ALARM_ACTIVE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$app$orsozoxi$Alarm$Preferences$AlarmPreference$Key[AlarmPreference.Key.ALARM_NAME.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$app$orsozoxi$Alarm$Preferences$AlarmPreference$Key[AlarmPreference.Key.ALARM_TIME.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$app$orsozoxi$Alarm$Preferences$AlarmPreference$Key[AlarmPreference.Key.ALARM_DIFFICULTY.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$app$orsozoxi$Alarm$Preferences$AlarmPreference$Key[AlarmPreference.Key.ALARM_TONE.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$app$orsozoxi$Alarm$Preferences$AlarmPreference$Key[AlarmPreference.Key.ALARM_VIBRATE.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$app$orsozoxi$Alarm$Preferences$AlarmPreference$Key[AlarmPreference.Key.ALARM_REPEAT.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            int[] iArr2 = new int[AlarmPreference.Type.values().length];
            $SwitchMap$com$app$orsozoxi$Alarm$Preferences$AlarmPreference$Type = iArr2;
            try {
                iArr2[AlarmPreference.Type.BOOLEAN.ordinal()] = 1;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$app$orsozoxi$Alarm$Preferences$AlarmPreference$Type[AlarmPreference.Type.INTEGER.ordinal()] = 2;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$app$orsozoxi$Alarm$Preferences$AlarmPreference$Type[AlarmPreference.Type.STRING.ordinal()] = 3;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$app$orsozoxi$Alarm$Preferences$AlarmPreference$Type[AlarmPreference.Type.LIST.ordinal()] = 4;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$app$orsozoxi$Alarm$Preferences$AlarmPreference$Type[AlarmPreference.Type.MULTIPLE_LIST.ordinal()] = 5;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$com$app$orsozoxi$Alarm$Preferences$AlarmPreference$Type[AlarmPreference.Type.TIME.ordinal()] = 6;
            } catch (NoSuchFieldError unused13) {
            }
        }
    }

    public Alarm getMathAlarm() {
        for (AlarmPreference alarmPreference : this.preferences) {
            switch (AnonymousClass1.$SwitchMap$com$app$orsozoxi$Alarm$Preferences$AlarmPreference$Key[alarmPreference.getKey().ordinal()]) {
                case 1:
                    this.alarm.setAlarmActive((Boolean) alarmPreference.getValue());
                    break;
                case 2:
                    this.alarm.setAlarmName((String) alarmPreference.getValue());
                    break;
                case 3:
                    this.alarm.setAlarmTime((String) alarmPreference.getValue());
                    break;
                case 4:
                    this.alarm.setDifficulty(Alarm.Difficulty.valueOf((String) alarmPreference.getValue()));
                    break;
                case 5:
                    this.alarm.setAlarmTonePath((String) alarmPreference.getValue());
                    break;
                case 6:
                    this.alarm.setVibrate((Boolean) alarmPreference.getValue());
                    break;
                case 7:
                    this.alarm.setDays((Alarm.Day[]) alarmPreference.getValue());
                    break;
            }
        }
        return this.alarm;
    }

    public void setMathAlarm(Alarm alarm, Context context) {
        this.alarm = alarm;
        this.preferences.clear();
        this.preferences.add(new AlarmPreference(AlarmPreference.Key.ALARM_ACTIVE, context.getString(R.string.active), null, null, alarm.getAlarmActive(), AlarmPreference.Type.BOOLEAN));
        if (alarm.getAlarmName().equals("Alarm Clock")) {
            this.preferences.add(new AlarmPreference(AlarmPreference.Key.ALARM_NAME, context.getString(R.string.label), context.getString(R.string.label), null, alarm.getAlarmName(), AlarmPreference.Type.STRING));
        } else {
            this.preferences.add(new AlarmPreference(AlarmPreference.Key.ALARM_NAME, context.getString(R.string.label), alarm.getAlarmName(), null, alarm.getAlarmName(), AlarmPreference.Type.STRING));
        }
        this.preferences.add(new AlarmPreference(AlarmPreference.Key.ALARM_TIME, context.getString(R.string.set_time), alarm.getAlarmTimeString(), null, alarm.getAlarmTime(), AlarmPreference.Type.TIME));
        this.preferences.add(new AlarmPreference(AlarmPreference.Key.ALARM_REPEAT, context.getString(R.string.repeat), alarm.getRepeatDaysString(context), this.repeatDays, alarm.getDays(), AlarmPreference.Type.MULTIPLE_LIST));
        Ringtone ringtone = RingtoneManager.getRingtone(getContext(), Uri.parse(alarm.getAlarmTonePath()));
        if ((ringtone instanceof Ringtone) && !alarm.getAlarmTonePath().equalsIgnoreCase("")) {
            this.preferences.add(new AlarmPreference(AlarmPreference.Key.ALARM_TONE, context.getString(R.string.ringtones), ringtone.getTitle(getContext()), this.alarmTones, alarm.getAlarmTonePath(), AlarmPreference.Type.LIST));
        } else {
            this.preferences.add(new AlarmPreference(AlarmPreference.Key.ALARM_TONE, context.getString(R.string.ringtones), getAlarmTones()[0], this.alarmTones, null, AlarmPreference.Type.LIST));
        }
        this.preferences.add(new AlarmPreference(AlarmPreference.Key.ALARM_VIBRATE, context.getString(R.string.vibrate), null, null, alarm.getVibrate(), AlarmPreference.Type.BOOLEAN));
    }

    public Context getContext() {
        return this.context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String[] getRepeatDays() {
        return this.repeatDays;
    }

    public String[] getAlarmDifficulties() {
        return this.alarmDifficulties;
    }

    public String[] getAlarmTones() {
        return this.alarmTones;
    }

    public String[] getAlarmTonePaths() {
        return this.alarmTonePaths;
    }
}
