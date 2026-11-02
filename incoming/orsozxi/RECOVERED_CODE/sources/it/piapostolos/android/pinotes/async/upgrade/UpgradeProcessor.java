package it.piapostolos.android.pinotes.async.upgrade;

import android.content.ContentValues;
import android.net.Uri;
import android.text.TextUtils;
import com.facebook.internal.AnalyticsEvents;
import it.piapostolos.android.pinotes.PiNotes;
import it.piapostolos.android.pinotes.db.DbHelper;
import it.piapostolos.android.pinotes.helpers.LogDelegate;
import it.piapostolos.android.pinotes.models.Attachment;
import it.piapostolos.android.pinotes.models.Note;
import it.piapostolos.android.pinotes.utils.ConstantsBase;
import it.piapostolos.android.pinotes.utils.ReminderHelper;
import it.piapostolos.android.pinotes.utils.StorageHelper;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.io.FilenameUtils;

/* loaded from: classes3.dex */
public class UpgradeProcessor {
    private static final String METHODS_PREFIX = "onUpgradeTo";
    private static UpgradeProcessor instance;

    private UpgradeProcessor() {
    }

    private static UpgradeProcessor getInstance() {
        if (instance == null) {
            instance = new UpgradeProcessor();
        }
        return instance;
    }

    public static void process(int i, int i2) throws IllegalAccessException, InvocationTargetException {
        try {
            for (Method method : getInstance().getMethodsToLaunch(i, i2)) {
                LogDelegate.d("Running upgrade processing method: " + method.getName());
                method.invoke(getInstance(), new Object[0]);
            }
        } catch (IllegalAccessException | SecurityException | InvocationTargetException e) {
            LogDelegate.e("Explosion processing upgrade!", e);
            throw e;
        }
    }

    private List<Method> getMethodsToLaunch(int i, int i2) throws SecurityException {
        int i3;
        ArrayList arrayList = new ArrayList();
        for (Method method : getInstance().getClass().getDeclaredMethods()) {
            if (method.getName().contains(METHODS_PREFIX) && i <= (i3 = Integer.parseInt(method.getName().replace(METHODS_PREFIX, ""))) && i3 <= i2) {
                arrayList.add(method);
            }
        }
        return arrayList;
    }

    private void onUpgradeTo476() {
        DbHelper dbHelper = DbHelper.getInstance();
        Iterator<Attachment> it2 = dbHelper.getAllAttachments().iterator();
        while (it2.hasNext()) {
            Attachment next = it2.next();
            if (next.getMime_type() == null) {
                String mimeType = StorageHelper.getMimeType(next.getUri().toString());
                if (!TextUtils.isEmpty(mimeType)) {
                    String strReplaceFirst = mimeType.replaceFirst("/.*", "");
                    char c = 65535;
                    int iHashCode = strReplaceFirst.hashCode();
                    if (iHashCode != 93166550) {
                        if (iHashCode != 100313435) {
                            if (iHashCode == 112202875 && strReplaceFirst.equals(AnalyticsEvents.PARAMETER_SHARE_DIALOG_CONTENT_VIDEO)) {
                                c = 1;
                            }
                        } else if (strReplaceFirst.equals("image")) {
                            c = 0;
                        }
                    } else if (strReplaceFirst.equals("audio")) {
                        c = 2;
                    }
                    if (c == 0) {
                        next.setMime_type(ConstantsBase.MIME_TYPE_IMAGE);
                    } else if (c == 1) {
                        next.setMime_type(ConstantsBase.MIME_TYPE_VIDEO);
                    } else if (c == 2) {
                        next.setMime_type(ConstantsBase.MIME_TYPE_AUDIO);
                    } else {
                        next.setMime_type(ConstantsBase.MIME_TYPE_FILES);
                    }
                    dbHelper.updateAttachment(next);
                } else {
                    next.setMime_type(ConstantsBase.MIME_TYPE_FILES);
                }
            }
        }
    }

    private void onUpgradeTo480() {
        DbHelper dbHelper = DbHelper.getInstance();
        Iterator<Attachment> it2 = dbHelper.getAllAttachments().iterator();
        while (it2.hasNext()) {
            Attachment next = it2.next();
            if ("audio/3gp".equals(next.getMime_type()) || "audio/3gpp".equals(next.getMime_type())) {
                File file = new File(next.getUriPath());
                FilenameUtils.getExtension(file.getName());
                File file2 = new File(file.getParent(), file.getName().replace(FilenameUtils.getExtension(file.getName()), ConstantsBase.MIME_TYPE_AUDIO_EXT));
                file.renameTo(file2);
                next.setUri(Uri.fromFile(file2));
                next.setMime_type(ConstantsBase.MIME_TYPE_AUDIO);
                dbHelper.updateAttachment(next);
            }
        }
    }

    private void onUpgradeTo482() {
        Iterator<Note> it2 = DbHelper.getInstance().getNotesWithReminderNotFired().iterator();
        while (it2.hasNext()) {
            ReminderHelper.addReminder(PiNotes.getAppContext(), it2.next());
        }
    }

    private void onUpgradeTo501() {
        ArrayList arrayList = new ArrayList();
        for (Note note : DbHelper.getInstance().getAllNotes(false)) {
            if (arrayList.contains(note.getCreation())) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("creation", Long.valueOf(note.getCreation().longValue() + ((long) (Math.random() * 999.0d))));
                DbHelper.getInstance().getDatabase().update("notes", contentValues, "title = ? AND creation = ? AND content = ?", new String[]{note.getTitle(), String.valueOf(note.getCreation()), note.getContent()});
            }
            arrayList.add(note.getCreation());
        }
    }
}
