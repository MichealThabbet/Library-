package it.orsozoxi.android.orsonotes.async;

import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import it.orsozoxi.android.orsonotes.OrsoNotes;
import it.orsozoxi.android.orsonotes.R;
import it.orsozoxi.android.orsonotes.db.DbHelper;
import it.orsozoxi.android.orsonotes.helpers.BackupHelper;
import it.orsozoxi.android.orsonotes.helpers.LogDelegate;
import it.orsozoxi.android.orsonotes.helpers.SpringImportHelper;
import it.orsozoxi.android.orsonotes.models.Attachment;
import it.orsozoxi.android.orsonotes.models.Note;
import it.orsozoxi.android.orsonotes.models.listeners.OnAttachingFileListener;
import it.orsozoxi.android.orsonotes.utils.ConstantsBase;
import it.orsozoxi.android.orsonotes.utils.ReminderHelper;
import it.orsozoxi.android.orsonotes.utils.StorageHelper;
import it.orsozoxi.android.orsonotes.utils.notifications.NotificationChannels;
import it.orsozoxi.android.orsonotes.utils.notifications.NotificationsHelper;
import java.io.File;
import java.util.Iterator;
import net.sqlcipher.database.SQLiteDatabase;

/* loaded from: classes3.dex */
public class DataBackupIntentService extends IntentService implements OnAttachingFileListener {
    public static final String ACTION_DATA_DELETE = "action_data_delete";
    public static final String ACTION_DATA_EXPORT = "action_data_export";
    public static final String ACTION_DATA_IMPORT = "action_data_import";
    public static final String ACTION_DATA_IMPORT_LEGACY = "action_data_import_legacy";
    public static final String INTENT_BACKUP_INCLUDE_SETTINGS = "backup_include_settings";
    public static final String INTENT_BACKUP_NAME = "backup_name";
    private NotificationsHelper mNotificationsHelper;
    private SharedPreferences prefs;

    @Override // it.orsozoxi.android.orsonotes.models.listeners.OnAttachingFileListener
    public void onAttachingFileErrorOccurred(Attachment attachment) {
    }

    @Override // it.orsozoxi.android.orsonotes.models.listeners.OnAttachingFileListener
    public void onAttachingFileFinished(Attachment attachment) {
    }

    public DataBackupIntentService() {
        super("DataBackupIntentService");
    }

    @Override // android.app.IntentService
    protected void onHandleIntent(Intent intent) {
        this.prefs = getSharedPreferences("OrsoNotes", 4);
        this.mNotificationsHelper = new NotificationsHelper(this).start(NotificationChannels.NotificationChannelNames.Backups, R.drawable.ic_content_save_white_24dp, getString(R.string.working));
        if (ACTION_DATA_EXPORT.equals(intent.getAction())) {
            exportData(intent);
            return;
        }
        if (ACTION_DATA_IMPORT.equals(intent.getAction()) || ACTION_DATA_IMPORT_LEGACY.equals(intent.getAction())) {
            importData(intent);
        } else if (SpringImportHelper.ACTION_DATA_IMPORT_SPRINGPAD.equals(intent.getAction())) {
            importDataFromSpringpad(intent, this.mNotificationsHelper);
        } else if (ACTION_DATA_DELETE.equals(intent.getAction())) {
            deleteData(intent);
        }
    }

    private void importDataFromSpringpad(Intent intent, NotificationsHelper notificationsHelper) {
        new SpringImportHelper(OrsoNotes.getAppContext()).importDataFromSpringpad(intent, notificationsHelper);
        createNotification(intent, this, getString(R.string.data_import_completed), getString(R.string.click_to_refresh_application), null);
    }

    private synchronized void exportData(Intent intent) {
        String stringExtra = intent.getStringExtra(INTENT_BACKUP_NAME);
        StorageHelper.delete(this, StorageHelper.getBackupDir(this, stringExtra).getAbsolutePath());
        File backupDir = StorageHelper.getBackupDir(this, stringExtra);
        BackupHelper.exportNotes(backupDir);
        boolean zExportAttachments = BackupHelper.exportAttachments(backupDir, this.mNotificationsHelper);
        if (intent.getBooleanExtra(INTENT_BACKUP_INCLUDE_SETTINGS, true)) {
            BackupHelper.exportSettings(backupDir);
        }
        this.mNotificationsHelper.finish(intent, getString(zExportAttachments ? R.string.data_export_completed : R.string.data_export_failed));
    }

    private synchronized void importData(Intent intent) {
        boolean zEquals = ACTION_DATA_IMPORT_LEGACY.equals(intent.getAction());
        String stringExtra = intent.getStringExtra(INTENT_BACKUP_NAME);
        File file = zEquals ? new File(stringExtra) : StorageHelper.getBackupDir(this, stringExtra);
        BackupHelper.importSettings(file);
        if (zEquals) {
            BackupHelper.importDB(this, file);
        } else {
            BackupHelper.importNotes(file);
        }
        BackupHelper.importAttachments(file, this.mNotificationsHelper);
        resetReminders();
        this.mNotificationsHelper.cancel();
        createNotification(intent, this, getString(R.string.data_import_completed), getString(R.string.click_to_refresh_application), file);
    }

    private synchronized void deleteData(Intent intent) {
        String stringExtra = intent.getStringExtra(INTENT_BACKUP_NAME);
        File backupDir = StorageHelper.getBackupDir(this, stringExtra);
        StorageHelper.delete(this, backupDir.getAbsolutePath());
        createNotification(intent, this, getString(R.string.data_deletion_completed), stringExtra + " " + getString(R.string.deleted), backupDir);
    }

    private void createNotification(Intent intent, Context context, String str, String str2, File file) {
        Intent intent2;
        if (ACTION_DATA_IMPORT.equals(intent.getAction()) || SpringImportHelper.ACTION_DATA_IMPORT_SPRINGPAD.equals(intent.getAction())) {
            try {
                intent2 = new Intent(context, Class.forName("com.app.orsozoxi.SplashScreen"));
                intent2.setAction(ConstantsBase.ACTION_RESTART_APP);
            } catch (ClassNotFoundException e) {
                Intent intent3 = new Intent();
                e.printStackTrace();
                intent2 = intent3;
            }
        } else {
            intent2 = new Intent();
        }
        intent2.setFlags(67108864);
        intent2.addFlags(SQLiteDatabase.CREATE_IF_NECESSARY);
        PendingIntent activity = PendingIntent.getActivity(context, 0, intent2, 134217728);
        NotificationsHelper notificationsHelper = new NotificationsHelper(context);
        notificationsHelper.createNotification(NotificationChannels.NotificationChannelNames.Backups, R.drawable.ic_content_save_white_24dp, str, activity).setMessage(str2).setRingtone(this.prefs.getString("settings_notification_ringtone", null)).setLedActive();
        if (this.prefs.getBoolean("settings_notification_vibration", true)) {
            notificationsHelper.setVibration();
        }
        notificationsHelper.show();
    }

    private void resetReminders() {
        LogDelegate.d("Resettings reminders");
        Iterator<Note> it2 = DbHelper.getInstance().getNotesWithReminderNotFired().iterator();
        while (it2.hasNext()) {
            ReminderHelper.addReminder(OrsoNotes.getAppContext(), it2.next());
        }
    }
}
