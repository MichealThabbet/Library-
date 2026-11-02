package it.orsozoxi.android.orsonotes.extensions;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.google.android.apps.dashclock.api.DashClockExtension;
import com.google.android.apps.dashclock.api.ExtensionData;
import it.orsozoxi.android.orsonotes.NotesMainActivity;
import it.orsozoxi.android.orsonotes.R;
import it.orsozoxi.android.orsonotes.db.DbHelper;
import it.orsozoxi.android.orsonotes.models.Note;
import it.orsozoxi.android.orsonotes.utils.ConstantsBase;
import it.orsozoxi.android.orsonotes.utils.TextHelper;
import it.orsozoxi.android.orsonotes.utils.date.DateUtils;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
public class ONDashClockExtension extends DashClockExtension {
    private DashClockUpdateReceiver mDashClockReceiver;

    private enum Counters {
        ACTIVE,
        REMINDERS,
        TODAY,
        TOMORROW
    }

    @Override // com.google.android.apps.dashclock.api.DashClockExtension
    protected void onInitialize(boolean z) {
        super.onInitialize(z);
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
        DashClockUpdateReceiver dashClockUpdateReceiver = this.mDashClockReceiver;
        if (dashClockUpdateReceiver != null) {
            localBroadcastManager.unregisterReceiver(dashClockUpdateReceiver);
        }
        DashClockUpdateReceiver dashClockUpdateReceiver2 = new DashClockUpdateReceiver();
        this.mDashClockReceiver = dashClockUpdateReceiver2;
        localBroadcastManager.registerReceiver(dashClockUpdateReceiver2, new IntentFilter(ConstantsBase.INTENT_UPDATE_DASHCLOCK));
    }

    @Override // com.google.android.apps.dashclock.api.DashClockExtension
    protected void onUpdateData(int i) {
        Map<Counters, List<Note>> notesCounters = getNotesCounters();
        int size = notesCounters.get(Counters.REMINDERS).size();
        StringBuilder sb = new StringBuilder();
        sb.append(notesCounters.get(Counters.ACTIVE).size());
        sb.append(" ");
        sb.append(getString(R.string.notes).toLowerCase());
        if (size > 0) {
            sb.append(", ");
            sb.append(size);
            sb.append(" ");
            sb.append(getString(R.string.reminders));
        }
        StringBuilder sb2 = new StringBuilder();
        if (!notesCounters.get(Counters.TODAY).isEmpty()) {
            sb2.append(notesCounters.get(Counters.TODAY).size());
            sb2.append(" ");
            sb2.append(getString(R.string.today));
            sb2.append(":");
            for (Note note : notesCounters.get(Counters.TODAY)) {
                sb2.append(System.getProperty("line.separator"));
                sb2.append("☆ ");
                sb2.append(getNoteTitle(this, note));
            }
            sb2.append("\n");
        }
        if (!notesCounters.get(Counters.TOMORROW).isEmpty()) {
            sb2.append(notesCounters.get(Counters.TOMORROW).size());
            sb2.append(" ");
            sb2.append(getString(R.string.tomorrow));
            sb2.append(":");
            for (Note note2 : notesCounters.get(Counters.TOMORROW)) {
                sb2.append(System.getProperty("line.separator"));
                sb2.append("☆ ");
                sb2.append(getNoteTitle(this, note2));
            }
        }
        Intent intent = new Intent(this, (Class<?>) NotesMainActivity.class);
        intent.setAction("android.intent.action.MAIN");
        publishUpdate(new ExtensionData().visible(true).icon(R.drawable.ic_stat_literal_icon).status(String.valueOf(notesCounters.get(Counters.ACTIVE).size())).expandedTitle(sb.toString()).expandedBody(sb2.toString()).clickIntent(intent));
    }

    private String getNoteTitle(Context context, Note note) {
        return TextHelper.getAlternativeTitle(context, note, TextHelper.parseTitleAndContent(context, note)[0]);
    }

    private Map<Counters, List<Note>> getNotesCounters() {
        HashMap map = new HashMap();
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        ArrayList arrayList4 = new ArrayList();
        for (Note note : DbHelper.getInstance().getNotesActive()) {
            arrayList.add(note);
            if (note.getAlarm() != null && !note.isReminderFired().booleanValue()) {
                arrayList2.add(note);
                if (DateUtils.isSameDay(Long.valueOf(note.getAlarm()).longValue(), Calendar.getInstance().getTimeInMillis())) {
                    arrayList3.add(note);
                } else if ((Long.valueOf(note.getAlarm()).longValue() - Calendar.getInstance().getTimeInMillis()) / 3600000 < 24) {
                    arrayList4.add(note);
                }
            }
        }
        map.put(Counters.ACTIVE, arrayList);
        map.put(Counters.REMINDERS, arrayList2);
        map.put(Counters.TODAY, arrayList3);
        map.put(Counters.TOMORROW, arrayList4);
        return map;
    }

    public class DashClockUpdateReceiver extends BroadcastReceiver {
        public DashClockUpdateReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            ONDashClockExtension.this.onUpdateData(6);
        }
    }
}
