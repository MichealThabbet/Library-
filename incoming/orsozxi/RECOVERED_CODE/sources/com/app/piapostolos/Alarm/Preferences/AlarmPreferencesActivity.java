package com.app.piapostolos.Alarm.Preferences;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.app.piapostolos.Adapters.ExpandableListAdapter;
import com.app.piapostolos.Alarm.Alarm;
import com.app.piapostolos.Alarm.Database.Database;
import com.app.piapostolos.Alarm.Preferences.AlarmPreference;
import com.app.piapostolos.Alarm.Services.AlarmServiceBroadcastReciever;
import com.app.piapostolos.R;
import com.app.piapostolos.Slider;
import com.navdrawer.SimpleSideDrawer;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.undo.TimedUndoAdapter;
import java.io.IOException;
import java.util.Calendar;

/* loaded from: classes.dex */
public class AlarmPreferencesActivity extends Activity {
    private Alarm alarm;
    private CountDownTimer alarmToneTimer;
    TextView cancelButton;
    ImageButton deleteButton;
    private ListAdapter listAdapter;
    private ListView listView;
    private MediaPlayer mediaPlayer;
    TextView okButton;

    public void onClick(View view) {
    }

    @Override // android.app.Activity
    protected void onSaveInstanceState(Bundle bundle) {
    }

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.alarm_preferences);
        ((TextView) findViewById(R.id.tv_header_title)).setText("الرسالة اليومية");
        Button button = (Button) findViewById(R.id.btn_slide);
        final SimpleSideDrawer simpleSideDrawer = new SimpleSideDrawer(this);
        simpleSideDrawer.setLeftBehindContentView(R.layout.left_menu_ex);
        button.setVisibility(0);
        Slider.prepareListData(this);
        ((ExpandableListView) findViewById(R.id.lvExp)).setAdapter(new ExpandableListAdapter(this, this, Slider.listDataHeader, Slider.listDataChild));
        button.setOnClickListener(new View.OnClickListener() { // from class: com.app.piapostolos.Alarm.Preferences.AlarmPreferencesActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                simpleSideDrawer.toggleLeftDrawer();
            }
        });
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey("alarm")) {
            setMathAlarm((Alarm) extras.getSerializable("alarm"));
        } else {
            setMathAlarm(new Alarm());
        }
        if (extras != null && extras.containsKey("adapter")) {
            setListAdapter((AlarmPreferenceListAdapter) extras.getSerializable("adapter"));
        } else {
            setListAdapter(new AlarmPreferenceListAdapter(this, getMathAlarm()));
        }
        getListView().setOnItemClickListener(new AnonymousClass2());
    }

    /* renamed from: com.app.piapostolos.Alarm.Preferences.AlarmPreferencesActivity$2, reason: invalid class name */
    class AnonymousClass2 implements AdapterView.OnItemClickListener {
        AnonymousClass2() {
        }

        @Override // android.widget.AdapterView.OnItemClickListener
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
            final AlarmPreferenceListAdapter alarmPreferenceListAdapter = (AlarmPreferenceListAdapter) AlarmPreferencesActivity.this.getListAdapter();
            final AlarmPreference alarmPreference = (AlarmPreference) alarmPreferenceListAdapter.getItem(i);
            view.performHapticFeedback(1);
            int i2 = AnonymousClass5.$SwitchMap$com$app$piapostolos$Alarm$Preferences$AlarmPreference$Type[alarmPreference.getType().ordinal()];
            if (i2 == 1) {
                CheckedTextView checkedTextView = (CheckedTextView) view;
                boolean z = !checkedTextView.isChecked();
                checkedTextView.setChecked(z);
                int i3 = AnonymousClass5.$SwitchMap$com$app$piapostolos$Alarm$Preferences$AlarmPreference$Key[alarmPreference.getKey().ordinal()];
                if (i3 == 1) {
                    AlarmPreferencesActivity.this.alarm.setAlarmActive(Boolean.valueOf(z));
                } else if (i3 == 2) {
                    AlarmPreferencesActivity.this.alarm.setVibrate(Boolean.valueOf(z));
                    if (z) {
                        ((Vibrator) AlarmPreferencesActivity.this.getSystemService("vibrator")).vibrate(1000L);
                    }
                }
                alarmPreference.setValue(Boolean.valueOf(z));
                return;
            }
            if (i2 == 2) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AlarmPreferencesActivity.this);
                builder.setTitle(alarmPreference.getTitle());
                final EditText editText = new EditText(AlarmPreferencesActivity.this);
                editText.setText(alarmPreference.getValue().toString());
                builder.setView(editText);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() { // from class: com.app.piapostolos.Alarm.Preferences.AlarmPreferencesActivity.2.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i4) {
                        alarmPreference.setValue(editText.getText().toString());
                        if (alarmPreference.getKey() == AlarmPreference.Key.ALARM_NAME) {
                            AlarmPreferencesActivity.this.alarm.setAlarmName(alarmPreference.getValue().toString());
                        }
                        alarmPreferenceListAdapter.setMathAlarm(AlarmPreferencesActivity.this.getMathAlarm(), AlarmPreferencesActivity.this);
                        alarmPreferenceListAdapter.notifyDataSetChanged();
                    }
                });
                builder.show();
                return;
            }
            int i4 = 0;
            if (i2 == 3) {
                AlertDialog.Builder builder2 = new AlertDialog.Builder(AlarmPreferencesActivity.this);
                builder2.setTitle(alarmPreference.getTitle());
                int length = alarmPreference.getOptions().length;
                CharSequence[] charSequenceArr = new CharSequence[length];
                while (i4 < length) {
                    charSequenceArr[i4] = alarmPreference.getOptions()[i4];
                    i4++;
                }
                builder2.setItems(charSequenceArr, new DialogInterface.OnClickListener() { // from class: com.app.piapostolos.Alarm.Preferences.AlarmPreferencesActivity.2.2
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i5) throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
                        int i6 = AnonymousClass5.$SwitchMap$com$app$piapostolos$Alarm$Preferences$AlarmPreference$Key[alarmPreference.getKey().ordinal()];
                        if (i6 == 3) {
                            AlarmPreferencesActivity.this.alarm.setDifficulty(Alarm.Difficulty.values()[i5]);
                        } else if (i6 == 4) {
                            AlarmPreferencesActivity.this.alarm.setAlarmTonePath(alarmPreferenceListAdapter.getAlarmTonePaths()[i5]);
                            if (AlarmPreferencesActivity.this.alarm.getAlarmTonePath() != null) {
                                if (AlarmPreferencesActivity.this.mediaPlayer != null) {
                                    if (AlarmPreferencesActivity.this.mediaPlayer.isPlaying()) {
                                        AlarmPreferencesActivity.this.mediaPlayer.stop();
                                    }
                                    AlarmPreferencesActivity.this.mediaPlayer.reset();
                                } else {
                                    AlarmPreferencesActivity.this.mediaPlayer = new MediaPlayer();
                                }
                                try {
                                    try {
                                        AlarmPreferencesActivity.this.mediaPlayer.setVolume(0.2f, 0.2f);
                                        AlarmPreferencesActivity.this.mediaPlayer.setDataSource(AlarmPreferencesActivity.this, Uri.parse(AlarmPreferencesActivity.this.alarm.getAlarmTonePath()));
                                        AlarmPreferencesActivity.this.mediaPlayer.setAudioStreamType(4);
                                        AlarmPreferencesActivity.this.mediaPlayer.setLooping(false);
                                        AlarmPreferencesActivity.this.mediaPlayer.prepare();
                                        AlarmPreferencesActivity.this.mediaPlayer.start();
                                        if (AlarmPreferencesActivity.this.alarmToneTimer != null) {
                                            AlarmPreferencesActivity.this.alarmToneTimer.cancel();
                                        }
                                        AlarmPreferencesActivity.this.alarmToneTimer = new CountDownTimer(TimedUndoAdapter.DEFAULT_TIMEOUT_MS, TimedUndoAdapter.DEFAULT_TIMEOUT_MS) { // from class: com.app.piapostolos.Alarm.Preferences.AlarmPreferencesActivity.2.2.1
                                            @Override // android.os.CountDownTimer
                                            public void onTick(long j2) {
                                            }

                                            @Override // android.os.CountDownTimer
                                            public void onFinish() throws IllegalStateException {
                                                try {
                                                    if (AlarmPreferencesActivity.this.mediaPlayer.isPlaying()) {
                                                        AlarmPreferencesActivity.this.mediaPlayer.stop();
                                                    }
                                                } catch (Exception unused) {
                                                }
                                            }
                                        };
                                        AlarmPreferencesActivity.this.alarmToneTimer.start();
                                    } catch (Exception unused) {
                                        if (AlarmPreferencesActivity.this.mediaPlayer.isPlaying()) {
                                            AlarmPreferencesActivity.this.mediaPlayer.stop();
                                        }
                                    }
                                } catch (Exception unused2) {
                                }
                            }
                        }
                        alarmPreferenceListAdapter.setMathAlarm(AlarmPreferencesActivity.this.getMathAlarm(), AlarmPreferencesActivity.this);
                        alarmPreferenceListAdapter.notifyDataSetChanged();
                    }
                });
                builder2.show();
                return;
            }
            if (i2 != 4) {
                if (i2 != 5) {
                    return;
                }
                TimePickerDialog timePickerDialog = new TimePickerDialog(AlarmPreferencesActivity.this, new TimePickerDialog.OnTimeSetListener() { // from class: com.app.piapostolos.Alarm.Preferences.AlarmPreferencesActivity.2.5
                    @Override // android.app.TimePickerDialog.OnTimeSetListener
                    public void onTimeSet(TimePicker timePicker, int i5, int i6) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(11, i5);
                        calendar.set(12, i6);
                        calendar.set(13, 0);
                        AlarmPreferencesActivity.this.alarm.setAlarmTime(calendar);
                        alarmPreferenceListAdapter.setMathAlarm(AlarmPreferencesActivity.this.getMathAlarm(), AlarmPreferencesActivity.this);
                        alarmPreferenceListAdapter.notifyDataSetChanged();
                    }
                }, AlarmPreferencesActivity.this.alarm.getAlarmTime().get(11), AlarmPreferencesActivity.this.alarm.getAlarmTime().get(12), true);
                timePickerDialog.setTitle(alarmPreference.getTitle());
                timePickerDialog.show();
                return;
            }
            AlertDialog.Builder builder3 = new AlertDialog.Builder(AlarmPreferencesActivity.this);
            builder3.setTitle(alarmPreference.getTitle());
            int length2 = alarmPreference.getOptions().length;
            CharSequence[] charSequenceArr2 = new CharSequence[length2];
            for (int i5 = 0; i5 < length2; i5++) {
                charSequenceArr2[i5] = alarmPreference.getOptions()[i5];
            }
            boolean[] zArr = new boolean[length2];
            Alarm.Day[] days = AlarmPreferencesActivity.this.getMathAlarm().getDays();
            int length3 = days.length;
            while (i4 < length3) {
                zArr[days[i4].ordinal()] = true;
                i4++;
            }
            builder3.setMultiChoiceItems(charSequenceArr2, zArr, new DialogInterface.OnMultiChoiceClickListener() { // from class: com.app.piapostolos.Alarm.Preferences.AlarmPreferencesActivity.2.3
                @Override // android.content.DialogInterface.OnMultiChoiceClickListener
                public void onClick(DialogInterface dialogInterface, int i6, boolean z2) {
                    Alarm.Day day = Alarm.Day.values()[i6];
                    if (z2) {
                        AlarmPreferencesActivity.this.alarm.addDay(day);
                    } else if (AlarmPreferencesActivity.this.alarm.getDays().length > 1) {
                        AlarmPreferencesActivity.this.alarm.removeDay(day);
                    } else {
                        ((AlertDialog) dialogInterface).getListView().setItemChecked(i6, true);
                    }
                }
            });
            builder3.setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: com.app.piapostolos.Alarm.Preferences.AlarmPreferencesActivity.2.4
                @Override // android.content.DialogInterface.OnCancelListener
                public void onCancel(DialogInterface dialogInterface) {
                    alarmPreferenceListAdapter.setMathAlarm(AlarmPreferencesActivity.this.getMathAlarm(), AlarmPreferencesActivity.this);
                    alarmPreferenceListAdapter.notifyDataSetChanged();
                }
            });
            builder3.show();
        }
    }

    /* renamed from: com.app.piapostolos.Alarm.Preferences.AlarmPreferencesActivity$5, reason: invalid class name */
    static /* synthetic */ class AnonymousClass5 {
        static final /* synthetic */ int[] $SwitchMap$com$app$piapostolos$Alarm$Preferences$AlarmPreference$Key;
        static final /* synthetic */ int[] $SwitchMap$com$app$piapostolos$Alarm$Preferences$AlarmPreference$Type;

        static {
            int[] iArr = new int[AlarmPreference.Type.values().length];
            $SwitchMap$com$app$piapostolos$Alarm$Preferences$AlarmPreference$Type = iArr;
            try {
                iArr[AlarmPreference.Type.BOOLEAN.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$app$piapostolos$Alarm$Preferences$AlarmPreference$Type[AlarmPreference.Type.STRING.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$app$piapostolos$Alarm$Preferences$AlarmPreference$Type[AlarmPreference.Type.LIST.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$app$piapostolos$Alarm$Preferences$AlarmPreference$Type[AlarmPreference.Type.MULTIPLE_LIST.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$app$piapostolos$Alarm$Preferences$AlarmPreference$Type[AlarmPreference.Type.TIME.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            int[] iArr2 = new int[AlarmPreference.Key.values().length];
            $SwitchMap$com$app$piapostolos$Alarm$Preferences$AlarmPreference$Key = iArr2;
            try {
                iArr2[AlarmPreference.Key.ALARM_ACTIVE.ordinal()] = 1;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$app$piapostolos$Alarm$Preferences$AlarmPreference$Key[AlarmPreference.Key.ALARM_VIBRATE.ordinal()] = 2;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$app$piapostolos$Alarm$Preferences$AlarmPreference$Key[AlarmPreference.Key.ALARM_DIFFICULTY.ordinal()] = 3;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$app$piapostolos$Alarm$Preferences$AlarmPreference$Key[AlarmPreference.Key.ALARM_TONE.ordinal()] = 4;
            } catch (NoSuchFieldError unused9) {
            }
        }
    }

    public void saveButtonAction(View view) throws IOException {
        Database.init(getApplicationContext());
        if (getMathAlarm().getId() < 1) {
            Database.create(getMathAlarm());
        } else {
            Database.update(getMathAlarm());
        }
        callMathAlarmScheduleService();
        Toast.makeText(this, getMathAlarm().getTimeUntilNextAlarmMessage(this), 1).show();
        finish();
    }

    public void deleteButtonAction(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.button_delete));
        builder.setMessage(getString(R.string.delete_alarm));
        builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() { // from class: com.app.piapostolos.Alarm.Preferences.AlarmPreferencesActivity.3
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                Database.init(AlarmPreferencesActivity.this.getApplicationContext());
                if (AlarmPreferencesActivity.this.getMathAlarm().getId() >= 1) {
                    Database.deleteEntry(AlarmPreferencesActivity.this.alarm);
                    AlarmPreferencesActivity.this.callMathAlarmScheduleService();
                }
                AlarmPreferencesActivity.this.finish();
            }
        });
        builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() { // from class: com.app.piapostolos.Alarm.Preferences.AlarmPreferencesActivity.4
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    protected void callMathAlarmScheduleService() {
        sendBroadcast(new Intent(this, (Class<?>) AlarmServiceBroadcastReciever.class), null);
    }

    @Override // android.app.Activity
    protected void onPause() {
        super.onPause();
        try {
            if (this.mediaPlayer != null) {
                this.mediaPlayer.release();
            }
        } catch (Exception unused) {
        }
    }

    @Override // android.app.Activity
    protected void onResume() {
        super.onResume();
    }

    public Alarm getMathAlarm() {
        return this.alarm;
    }

    public void setMathAlarm(Alarm alarm) {
        this.alarm = alarm;
    }

    public ListAdapter getListAdapter() {
        return this.listAdapter;
    }

    public void setListAdapter(ListAdapter listAdapter) {
        this.listAdapter = listAdapter;
        getListView().setAdapter(listAdapter);
    }

    public ListView getListView() {
        if (this.listView == null) {
            this.listView = (ListView) findViewById(android.R.id.list);
        }
        return this.listView;
    }

    public void setListView(ListView listView) {
        this.listView = listView;
    }
}
