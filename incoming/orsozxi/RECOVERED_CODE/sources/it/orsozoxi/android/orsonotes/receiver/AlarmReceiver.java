package it.orsozoxi.android.orsonotes.receiver;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Spanned;
import androidx.core.app.NotificationCompat;
import androidx.core.internal.view.SupportMenu;
import com.huawei.hms.push.constant.RemoteMessageConst;
import it.orsozoxi.android.orsonotes.R;
import it.orsozoxi.android.orsonotes.SnoozeActivity;
import it.orsozoxi.android.orsonotes.db.DbHelper;
import it.orsozoxi.android.orsonotes.helpers.LogDelegate;
import it.orsozoxi.android.orsonotes.models.Attachment;
import it.orsozoxi.android.orsonotes.models.Note;
import it.orsozoxi.android.orsonotes.services.NotificationListener;
import it.orsozoxi.android.orsonotes.utils.BitmapHelper;
import it.orsozoxi.android.orsonotes.utils.ConstantsBase;
import it.orsozoxi.android.orsonotes.utils.ParcelableUtil;
import it.orsozoxi.android.orsonotes.utils.TextHelper;
import it.orsozoxi.android.orsonotes.utils.notifications.NotificationsHelper;
import java.util.List;
import net.sqlcipher.database.SQLiteDatabase;

/* loaded from: classes3.dex */
public class AlarmReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        try {
            if (intent.hasExtra(ConstantsBase.INTENT_NOTE)) {
                Note note = (Note) ParcelableUtil.unmarshall(intent.getExtras().getByteArray(ConstantsBase.INTENT_NOTE), Note.CREATOR);
                createNotification(context, note);
                SnoozeActivity.setNextRecurrentReminder(note);
                updateNote(note);
            }
        } catch (Exception e) {
            LogDelegate.e("Error on receiving reminder", e);
        }
    }

    private void updateNote(Note note) {
        note.setArchived((Boolean) false);
        if (!NotificationListener.isRunning()) {
            note.setReminderFired((Boolean) true);
        }
        DbHelper.getInstance().updateNote(note, false);
    }

    private void createNotification(Context context, Note note) {
        PendingIntent activity;
        PendingIntent activity2;
        SharedPreferences sharedPreferences = context.getSharedPreferences("OrsoNotes", 4);
        Spanned[] titleAndContent = TextHelper.parseTitleAndContent(context, note);
        String alternativeTitle = TextHelper.getAlternativeTitle(context, note, titleAndContent[0]);
        String string = titleAndContent[1].toString();
        Intent intent = new Intent(context, (Class<?>) SnoozeActivity.class);
        intent.setAction(ConstantsBase.ACTION_SNOOZE);
        intent.putExtra(ConstantsBase.INTENT_NOTE, (Parcelable) note);
        if (Build.VERSION.SDK_INT >= 23) {
            activity = PendingIntent.getActivity(context, getUniqueRequestCode(note), intent, 201326592);
        } else {
            activity = PendingIntent.getActivity(context, getUniqueRequestCode(note), intent, 134217728);
        }
        Intent intent2 = new Intent(context, (Class<?>) SnoozeActivity.class);
        intent2.setAction(ConstantsBase.ACTION_POSTPONE);
        intent2.putExtra(ConstantsBase.INTENT_NOTE, (Parcelable) note);
        if (Build.VERSION.SDK_INT >= 23) {
            activity2 = PendingIntent.getActivity(context, getUniqueRequestCode(note), intent2, 201326592);
        } else {
            activity2 = PendingIntent.getActivity(context, getUniqueRequestCode(note), intent2, 134217728);
        }
        String string2 = context.getSharedPreferences("OrsoNotes", 4).getString("settings_notification_snooze_delay", ConstantsBase.PREF_SNOOZE_DEFAULT);
        Intent intent3 = new Intent(context, (Class<?>) SnoozeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(ConstantsBase.INTENT_NOTE, note);
        intent3.putExtras(bundle);
        intent3.setFlags(SQLiteDatabase.CREATE_IF_NECESSARY);
        intent3.setAction(ConstantsBase.ACTION_NOTIFICATION_CLICK + System.currentTimeMillis());
        PendingIntent activity3 = PendingIntent.getActivity(context, getUniqueRequestCode(note), intent3, 134217728);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(RemoteMessageConst.NOTIFICATION);
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel notificationChannel = new NotificationChannel("orso_id_01", "My Notifications", 3);
            notificationChannel.setDescription("Channel");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(SupportMenu.CATEGORY_MASK);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableVibration(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "orso_id_01");
        builder.setCategory(NotificationCompat.CATEGORY_PROMO).setContentTitle(alternativeTitle).setContentText(string).setSmallIcon(R.mipmap.ic_launcher).setAutoCancel(true).setVisibility(1).setContentIntent(activity3).setPriority(1).setDefaults(-1).build();
        Notification notificationBuild = builder.build();
        List<Attachment> attachmentsList = note.getAttachmentsList();
        if (!attachmentsList.isEmpty() && !attachmentsList.get(0).getMime_type().equals(ConstantsBase.MIME_TYPE_FILES)) {
            builder.setLargeIcon(BitmapHelper.getBitmapFromAttachment(context, note.getAttachmentsList().get(0), 128, 128));
        }
        builder.addAction(R.drawable.ic_material_reminder_time_light, TextHelper.capitalize(context.getString(R.string.snooze)) + ": " + string2, activity).addAction(R.drawable.ic_remind_later_light, TextHelper.capitalize(context.getString(R.string.add_reminder)), activity2);
        String string3 = sharedPreferences.getString("settings_notification_ringtone", null);
        if (string3 != null) {
            builder.setSound(Uri.parse(string3));
        }
        if (sharedPreferences.getBoolean("settings_notification_vibration", true)) {
            builder.setVibrate(new long[]{500, 500});
        }
        ((NotificationManager) context.getSystemService(RemoteMessageConst.NOTIFICATION)).notify(String.valueOf(note.get_id()), 0, notificationBuild);
    }

    private void setRingtone(SharedPreferences sharedPreferences, NotificationsHelper notificationsHelper) {
        String string = sharedPreferences.getString("settings_notification_ringtone", null);
        if (string != null) {
            notificationsHelper.setRingtone(string);
        }
    }

    private void setVibrate(SharedPreferences sharedPreferences, NotificationsHelper notificationsHelper) {
        if (sharedPreferences.getBoolean("settings_notification_vibration", true)) {
            notificationsHelper.setVibration();
        }
    }

    private int getUniqueRequestCode(Note note) {
        return note.get_id().intValue();
    }
}
