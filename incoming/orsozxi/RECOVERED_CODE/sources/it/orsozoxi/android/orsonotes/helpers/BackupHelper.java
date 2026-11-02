package it.orsozoxi.android.orsonotes.helpers;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import it.orsozoxi.android.orsonotes.OrsoNotes;
import it.orsozoxi.android.orsonotes.R;
import it.orsozoxi.android.orsonotes.async.DataBackupIntentService;
import it.orsozoxi.android.orsonotes.db.DbHelper;
import it.orsozoxi.android.orsonotes.models.Attachment;
import it.orsozoxi.android.orsonotes.models.Note;
import it.orsozoxi.android.orsonotes.utils.ConstantsBase;
import it.orsozoxi.android.orsonotes.utils.StorageHelper;
import it.orsozoxi.android.orsonotes.utils.TextHelper;
import it.orsozoxi.android.orsonotes.utils.notifications.NotificationsHelper;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/* loaded from: classes3.dex */
public class BackupHelper {
    private BackupHelper() {
    }

    public static void exportNotes(File file) throws Throwable {
        Iterator<Note> it2 = DbHelper.getInstance(true).getAllNotes(false).iterator();
        while (it2.hasNext()) {
            exportNote(file, it2.next());
        }
    }

    public static void exportNote(File file, Note note) throws Throwable {
        try {
            FileUtils.write(getBackupNoteFile(file, note), note.toJSON());
        } catch (IOException unused) {
            LogDelegate.e("Error backupping note: " + note.get_id());
        }
    }

    public static File getBackupNoteFile(File file, Note note) {
        return new File(file, note.get_id() + ".json");
    }

    public static boolean exportAttachments(File file) {
        return exportAttachments(file, null);
    }

    public static boolean exportAttachments(File file, NotificationsHelper notificationsHelper) {
        exportAttachments(notificationsHelper, new File(file, StorageHelper.getAttachmentDir().getName()), DbHelper.getInstance().getAllAttachments(), null);
        return true;
    }

    public static boolean exportAttachments(NotificationsHelper notificationsHelper, final File file, final List<Attachment> list, List<Attachment> list2) {
        if (list2 == null) {
            list2 = Collections.emptyList();
        }
        boolean z = true;
        int i = 0;
        int i2 = 0;
        for (Attachment attachment : list) {
            try {
                StorageHelper.copyToBackupDir(file, FilenameUtils.getName(attachment.getUriPath()), OrsoNotes.getAppContext().getContentResolver().openInputStream(attachment.getUri()));
                i2++;
            } catch (FileNotFoundException unused) {
                LogDelegate.w("Attachment not found during backup: " + attachment.getUriPath());
                i++;
                z = false;
            }
            String str = i > 0 ? " (" + i + " " + OrsoNotes.getAppContext().getString(R.string.failed) + ")" : "";
            if (notificationsHelper != null) {
                notificationsHelper.updateMessage(TextHelper.capitalize(OrsoNotes.getAppContext().getString(R.string.attachment)) + " " + i2 + "/" + list.size() + str);
            }
        }
        Observable.from(list2).filter(new Func1() { // from class: it.orsozoxi.android.orsonotes.helpers.-$$Lambda$BackupHelper$vl9eb7whbvSWYlIkQq7IT1WcD_I
            @Override // rx.functions.Func1
            public final Object call(Object obj) {
                List list3 = list;
                Attachment attachment2 = (Attachment) obj;
                return Boolean.valueOf(!list3.contains(attachment2));
            }
        }).forEach(new Action1() { // from class: it.orsozoxi.android.orsonotes.helpers.-$$Lambda$BackupHelper$DzjNXVMwESFGUX3fdqapy4G-ddY
            @Override // rx.functions.Action1
            public final void call(Object obj) {
                StorageHelper.delete(OrsoNotes.getAppContext(), new File(file.getAbsolutePath(), ((Attachment) obj).getUri().getLastPathSegment()).getAbsolutePath());
            }
        });
        return z;
    }

    public static List<Note> importNotes(File file) {
        ArrayList arrayList = new ArrayList();
        Iterator<File> it2 = FileUtils.listFiles(file, new RegexFileFilter("\\d{13}.json"), TrueFileFilter.INSTANCE).iterator();
        while (it2.hasNext()) {
            arrayList.add(importNote(it2.next()));
        }
        return arrayList;
    }

    public static Note importNote(File file) {
        Note importNote = getImportNote(file);
        if (importNote.getCategory() != null) {
            DbHelper.getInstance().updateCategory(importNote.getCategory());
        }
        importNote.setAttachmentsListOld(DbHelper.getInstance().getNoteAttachments(importNote));
        DbHelper.getInstance().updateNote(importNote, false);
        return importNote;
    }

    public static Note getImportNote(File file) {
        try {
            Note note = new Note();
            String fileToString = FileUtils.readFileToString(file);
            if (!TextUtils.isEmpty(fileToString)) {
                note.buildFromJson(fileToString);
                note.setAttachmentsListOld(DbHelper.getInstance().getNoteAttachments(note));
            }
            return note;
        } catch (IOException unused) {
            LogDelegate.e("Error parsing note json");
            return new Note();
        }
    }

    public static boolean importAttachments(File file) {
        return importAttachments(file, (NotificationsHelper) null);
    }

    public static boolean importAttachments(File file, NotificationsHelper notificationsHelper) throws Throwable {
        File attachmentDir = StorageHelper.getAttachmentDir();
        File file2 = new File(file, attachmentDir.getName());
        if (!file2.exists()) {
            return true;
        }
        Collection<File> collectionListFiles = FileUtils.listFiles(file2, FileFilterUtils.trueFileFilter(), TrueFileFilter.INSTANCE);
        File file3 = null;
        boolean z = true;
        int i = 0;
        for (File file4 : collectionListFiles) {
            try {
                try {
                    FileUtils.copyFileToDirectory(file4, attachmentDir, true);
                    if (notificationsHelper != null) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(TextHelper.capitalize(OrsoNotes.getAppContext().getString(R.string.attachment)));
                        sb.append(" ");
                        int i2 = i + 1;
                        try {
                            sb.append(i);
                            sb.append("/");
                            sb.append(collectionListFiles.size());
                            notificationsHelper.updateMessage(sb.toString());
                            i = i2;
                        } catch (IOException unused) {
                            file3 = file4;
                            i = i2;
                            LogDelegate.e("Error importing the attachment " + file3.getName());
                            z = false;
                        }
                    }
                    file3 = file4;
                } catch (IOException unused2) {
                    file3 = file4;
                }
            } catch (IOException unused3) {
            }
        }
        return z;
    }

    public static void importAttachments(Note note, File file) throws Throwable {
        File file2 = new File(file, StorageHelper.getAttachmentDir().getName());
        Iterator<Attachment> it2 = note.getAttachmentsList().iterator();
        while (it2.hasNext()) {
            String name = FilenameUtils.getName(it2.next().getUriPath());
            File file3 = new File(file2, name);
            if (file3.exists()) {
                FileUtils.copyFileToDirectory(file3, StorageHelper.getAttachmentDir(), true);
            } else {
                LogDelegate.e("Attachment file not found: " + name);
            }
        }
    }

    public static void startBackupService(String str) {
        Intent intent = new Intent(OrsoNotes.getAppContext(), (Class<?>) DataBackupIntentService.class);
        intent.setAction(DataBackupIntentService.ACTION_DATA_EXPORT);
        intent.putExtra(DataBackupIntentService.INTENT_BACKUP_NAME, str);
        OrsoNotes.getAppContext().startService(intent);
    }

    public static boolean exportSettings(File file) {
        File sharedPreferencesFile = StorageHelper.getSharedPreferencesFile(OrsoNotes.getAppContext());
        return StorageHelper.copyFile(sharedPreferencesFile, new File(file, sharedPreferencesFile.getName()));
    }

    public static boolean importSettings(File file) {
        File sharedPreferencesFile = StorageHelper.getSharedPreferencesFile(OrsoNotes.getAppContext());
        return StorageHelper.copyFile(new File(file, sharedPreferencesFile.getName()), sharedPreferencesFile);
    }

    public static boolean deleteNoteBackup(File file, Note note) {
        boolean zDelete = getBackupNoteFile(file, note).delete();
        File file2 = new File(file, StorageHelper.getAttachmentDir().getName());
        Iterator<Attachment> it2 = note.getAttachmentsList().iterator();
        while (it2.hasNext()) {
            zDelete = zDelete && new File(file2, FilenameUtils.getName(it2.next().getUri().getPath())).delete();
        }
        return zDelete;
    }

    public static void deleteNote(File file) {
        try {
            Note note = new Note();
            note.buildFromJson(FileUtils.readFileToString(file));
            DbHelper.getInstance().deleteNote(note);
        } catch (IOException unused) {
            LogDelegate.e("Error parsing note json");
        }
    }

    @Deprecated
    public static boolean importDB(Context context, File file) {
        File databasePath = context.getDatabasePath(ConstantsBase.DATABASE_NAME);
        if (databasePath.exists()) {
            databasePath.delete();
        }
        return StorageHelper.copyFile(new File(file, ConstantsBase.DATABASE_NAME), databasePath);
    }

    public static List<LinkedList<DiffMatchPatch.Diff>> integrityCheck(File file) {
        ArrayList arrayList = new ArrayList();
        for (Note note : DbHelper.getInstance(true).getAllNotes(false)) {
            File backupNoteFile = getBackupNoteFile(file, note);
            try {
                String json = note.toJSON();
                String fileToString = FileUtils.readFileToString(backupNoteFile);
                if (json.equals(fileToString)) {
                    File file2 = new File(file, StorageHelper.getAttachmentDir().getName());
                    for (Attachment attachment : note.getAttachmentsList()) {
                        if (!new File(file2, FilenameUtils.getName(attachment.getUriPath())).exists()) {
                            addIntegrityCheckError(arrayList, new FileNotFoundException("Attachment " + attachment.getUriPath() + " missing"));
                        }
                    }
                } else {
                    arrayList.add(new DiffMatchPatch().diffMain(json, fileToString));
                }
            } catch (IOException e) {
                LogDelegate.e(e.getMessage(), e);
                addIntegrityCheckError(arrayList, e);
            }
        }
        return arrayList;
    }

    private static void addIntegrityCheckError(List<LinkedList<DiffMatchPatch.Diff>> list, IOException iOException) {
        LinkedList<DiffMatchPatch.Diff> linkedList = new LinkedList<>();
        linkedList.add(new DiffMatchPatch.Diff(DiffMatchPatch.Operation.DELETE, iOException.getMessage()));
        list.add(linkedList);
    }
}
