package it.orsozoxi.android.orsonotes.db;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import com.ibm.icu.impl.locale.BaseLocale;
import defpackage.C$r8$backportedMethods$utility$Objects$1$nonNull;
import it.orsozoxi.android.orsonotes.OrsoNotes;
import it.orsozoxi.android.orsonotes.async.upgrade.UpgradeProcessor;
import it.orsozoxi.android.orsonotes.exceptions.DatabaseException;
import it.orsozoxi.android.orsonotes.helpers.LogDelegate;
import it.orsozoxi.android.orsonotes.helpers.NotesHelper;
import it.orsozoxi.android.orsonotes.models.Attachment;
import it.orsozoxi.android.orsonotes.models.Category;
import it.orsozoxi.android.orsonotes.models.Note;
import it.orsozoxi.android.orsonotes.models.Stats;
import it.orsozoxi.android.orsonotes.models.Tag;
import it.orsozoxi.android.orsonotes.utils.AssetUtils;
import it.orsozoxi.android.orsonotes.utils.ConstantsBase;
import it.orsozoxi.android.orsonotes.utils.Navigation;
import it.orsozoxi.android.orsonotes.utils.Security;
import it.orsozoxi.android.orsonotes.utils.TagsHelper;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;
import rx.Observable;
import rx.functions.Func1;

/* loaded from: classes3.dex */
public class DbHelper extends SQLiteOpenHelper {
    private static final String CREATE_QUERY = "create.sql";
    private static final int DATABASE_VERSION = 561;
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_ARCHIVED = "archived";
    public static final String KEY_ATTACHMENT_ID = "attachment_id";
    public static final String KEY_ATTACHMENT_LENGTH = "length";
    public static final String KEY_ATTACHMENT_MIME_TYPE = "mime_type";
    public static final String KEY_ATTACHMENT_NAME = "name";
    public static final String KEY_ATTACHMENT_NOTE_ID = "note_id";
    public static final String KEY_ATTACHMENT_SIZE = "size";
    public static final String KEY_ATTACHMENT_URI = "uri";
    public static final String KEY_CATEGORY = "category_id";
    public static final String KEY_CATEGORY_COLOR = "color";
    public static final String KEY_CATEGORY_DESCRIPTION = "description";
    public static final String KEY_CATEGORY_ID = "category_id";
    public static final String KEY_CATEGORY_NAME = "name";
    public static final String KEY_CHECKLIST = "checklist";
    public static final String KEY_CONTENT = "content";
    public static final String KEY_CREATION = "creation";
    public static final String KEY_ID = "creation";
    public static final String KEY_LAST_MODIFICATION = "last_modification";
    public static final String KEY_LATITUDE = "latitude";
    public static final String KEY_LOCKED = "locked";
    public static final String KEY_LONGITUDE = "longitude";
    public static final String KEY_RECURRENCE_RULE = "recurrence_rule";
    public static final String KEY_REMINDER = "alarm";
    public static final String KEY_REMINDER_FIRED = "reminder_fired";
    public static final String KEY_TITLE = "title";
    public static final String KEY_TRASHED = "trashed";
    private static final String SQL_DIR = "sql";
    public static final String TABLE_ATTACHMENTS = "attachments";
    public static final String TABLE_CATEGORY = "categories";
    public static final String TABLE_NOTES = "notes";
    private static final String UPGRADE_QUERY_PREFIX = "upgrade-";
    private static final String UPGRADE_QUERY_SUFFIX = ".sql";
    private static DbHelper instance;
    private SQLiteDatabase db;
    private final Context mContext;
    private final SharedPreferences prefs;

    @Override // android.database.sqlite.SQLiteOpenHelper
    public String getDatabaseName() {
        return ConstantsBase.DATABASE_NAME;
    }

    public static synchronized DbHelper getInstance() {
        return getInstance(OrsoNotes.getAppContext());
    }

    public static synchronized DbHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DbHelper(context);
        }
        return instance;
    }

    public static synchronized DbHelper getInstance(boolean z) {
        if (instance == null || z) {
            instance = new DbHelper((instance == null || instance.mContext == null) ? OrsoNotes.getAppContext() : instance.mContext);
        }
        return instance;
    }

    private DbHelper(Context context) {
        super(context, ConstantsBase.DATABASE_NAME, (SQLiteDatabase.CursorFactory) null, 561);
        this.mContext = context;
        this.prefs = context.getSharedPreferences("OrsoNotes", 4);
    }

    public SQLiteDatabase getDatabase() {
        return getDatabase(false);
    }

    public SQLiteDatabase getDatabase(boolean z) {
        try {
            return z ? getWritableDatabase() : getReadableDatabase();
        } catch (IllegalStateException unused) {
            return this.db;
        }
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onOpen(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.disableWriteAheadLogging();
        super.onOpen(sQLiteDatabase);
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(SQLiteDatabase sQLiteDatabase) throws SQLException {
        try {
            LogDelegate.i("Database creation");
            execSqlFile(CREATE_QUERY, sQLiteDatabase);
        } catch (IOException e) {
            throw new DatabaseException("Database creation failed: " + e.getMessage(), e);
        }
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        int i3;
        this.db = sQLiteDatabase;
        LogDelegate.i("Upgrading database version from " + i + " to " + i2);
        try {
            UpgradeProcessor.process(i, i2);
            for (String str : AssetUtils.list(SQL_DIR, this.mContext.getAssets())) {
                if (str.startsWith(UPGRADE_QUERY_PREFIX) && (i3 = Integer.parseInt(str.substring(8, str.length() - 4))) > i && i3 <= i2) {
                    execSqlFile(str, sQLiteDatabase);
                }
            }
            LogDelegate.i("Database upgrade successful");
        } catch (IOException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Database upgrade failed", e);
        }
    }

    public Note updateNote(Note note, boolean z) {
        String content;
        long timeInMillis;
        this.db = getDatabase(true);
        if (Boolean.TRUE.equals(note.isLocked())) {
            content = Security.encrypt(note.getContent(), this.prefs.getString(ConstantsBase.PREF_PASSWORD, ""));
        } else {
            content = note.getContent();
        }
        this.db.beginTransaction();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", note.getTitle());
        contentValues.put("content", content);
        contentValues.put("creation", Long.valueOf(note.getCreation() != null ? note.getCreation().longValue() : Calendar.getInstance().getTimeInMillis()));
        if (note.getLastModification() != null && !z) {
            timeInMillis = note.getLastModification().longValue();
        } else {
            timeInMillis = Calendar.getInstance().getTimeInMillis();
        }
        contentValues.put(KEY_LAST_MODIFICATION, Long.valueOf(timeInMillis));
        contentValues.put(KEY_ARCHIVED, note.isArchived());
        contentValues.put(KEY_TRASHED, note.isTrashed());
        contentValues.put("alarm", note.getAlarm());
        contentValues.put(KEY_REMINDER_FIRED, note.isReminderFired());
        contentValues.put(KEY_RECURRENCE_RULE, note.getRecurrenceRule());
        contentValues.put(KEY_LATITUDE, note.getLatitude());
        contentValues.put(KEY_LONGITUDE, note.getLongitude());
        contentValues.put("address", note.getAddress());
        contentValues.put("category_id", note.getCategory() != null ? note.getCategory().getId() : null);
        contentValues.put("locked", Boolean.valueOf(note.isLocked() != null && note.isLocked().booleanValue()));
        contentValues.put(KEY_CHECKLIST, Boolean.valueOf(note.isChecklist() != null && note.isChecklist().booleanValue()));
        this.db.insertWithOnConflict("notes", "creation", contentValues, 5);
        LogDelegate.d("Updated note titled '" + note.getTitle() + "'");
        List<Attachment> attachmentsListOld = note.getAttachmentsListOld();
        for (Attachment attachment : note.getAttachmentsList()) {
            updateAttachment((note.get_id() != null ? note.get_id() : contentValues.getAsLong("creation")).longValue(), attachment, this.db);
            attachmentsListOld.remove(attachment);
        }
        Iterator<Attachment> it2 = attachmentsListOld.iterator();
        while (it2.hasNext()) {
            this.db.delete("attachments", "attachment_id = ?", new String[]{String.valueOf(it2.next().getId())});
        }
        this.db.setTransactionSuccessful();
        this.db.endTransaction();
        note.setCreation(note.getCreation() != null ? note.getCreation() : contentValues.getAsLong("creation"));
        note.setLastModification(contentValues.getAsLong(KEY_LAST_MODIFICATION));
        return note;
    }

    private void execSqlFile(String str, SQLiteDatabase sQLiteDatabase) throws IOException, SQLException {
        LogDelegate.i("  exec sql file: {}" + str);
        for (String str2 : SqlParser.parseSqlFile("sql/" + str, this.mContext.getAssets())) {
            LogDelegate.v("    sql: {}" + str2);
            try {
                sQLiteDatabase.execSQL(str2);
            } catch (Exception e) {
                LogDelegate.e("Error executing command: " + str2, e);
            }
        }
    }

    public Attachment updateAttachment(Attachment attachment) {
        return updateAttachment(-1L, attachment, getDatabase(true));
    }

    public Attachment updateAttachment(long j, Attachment attachment, SQLiteDatabase sQLiteDatabase) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_ATTACHMENT_ID, Long.valueOf(attachment.getId() != null ? attachment.getId().longValue() : Calendar.getInstance().getTimeInMillis()));
        contentValues.put("note_id", Long.valueOf(j));
        contentValues.put("uri", attachment.getUri().toString());
        contentValues.put(KEY_ATTACHMENT_MIME_TYPE, attachment.getMime_type());
        contentValues.put("name", attachment.getName());
        contentValues.put(KEY_ATTACHMENT_SIZE, Long.valueOf(attachment.getSize()));
        contentValues.put(KEY_ATTACHMENT_LENGTH, Long.valueOf(attachment.getLength()));
        sQLiteDatabase.insertWithOnConflict("attachments", KEY_ATTACHMENT_ID, contentValues, 5);
        return attachment;
    }

    public Note getNote(long j) {
        List<Note> notes = getNotes(" WHERE creation = " + j, true);
        if (notes.isEmpty()) {
            return null;
        }
        return notes.get(0);
    }

    public List<Note> getAllNotes(Boolean bool) throws Resources.NotFoundException {
        if (Boolean.TRUE.equals(bool)) {
            int navigation = Navigation.getNavigation();
            if (navigation == 0) {
                return getNotesActive();
            }
            if (navigation == 1) {
                return getNotesArchived();
            }
            if (navigation == 2) {
                return getNotesWithReminder(this.prefs.getBoolean(ConstantsBase.PREF_FILTER_PAST_REMINDERS, false));
            }
            if (navigation == 3) {
                return getNotesTrashed();
            }
            if (navigation == 4) {
                return getNotesUncategorized();
            }
            if (navigation == 5) {
                return getNotesByCategory(Navigation.getCategory());
            }
            return getNotes("", true);
        }
        return getNotes("", true);
    }

    public List<Note> getNotesActive() {
        return getNotes(" WHERE archived IS NOT 1 AND trashed IS NOT 1 ", true);
    }

    public List<Note> getNotesArchived() {
        return getNotes(" WHERE archived = 1 AND trashed IS NOT 1 ", true);
    }

    public List<Note> getNotesTrashed() {
        return getNotes(" WHERE trashed = 1 ", true);
    }

    public List<Note> getNotesUncategorized() {
        return getNotes(" WHERE (category_id IS NULL OR category_id == 0) AND trashed IS NOT 1", true);
    }

    public List<Note> getNotesWithLocation() {
        return getNotes(" WHERE longitude IS NOT NULL AND longitude != 0 ", true);
    }

    public List<Note> getNotes(String str, boolean z) {
        String str2;
        String str3;
        ArrayList arrayList = new ArrayList();
        String string = Navigation.checkNavigation(2) ? "alarm" : this.prefs.getString(ConstantsBase.PREF_SORTING_COLUMN, "title");
        if (z) {
            str2 = ("title".equals(string) || "alarm".equals(string)) ? " ASC " : " DESC ";
        } else {
            str2 = "";
        }
        if ("title".equals(string)) {
            string = "title||content";
        }
        if ("alarm".equals(string)) {
            string = "IFNULL(alarm, 0)";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT creation,last_modification,title,content,archived,trashed,alarm,reminder_fired,recurrence_rule,latitude,longitude,address,locked,checklist,category_id,name,description,color FROM notes LEFT JOIN categories USING( category_id) ");
        sb.append(str);
        if (z) {
            str3 = " ORDER BY " + string + " COLLATE NOCASE " + str2;
        } else {
            str3 = "";
        }
        sb.append(str3);
        String string2 = sb.toString();
        LogDelegate.v("Query: " + string2);
        Cursor cursorRawQuery = getDatabase().rawQuery(string2, null);
        try {
            if (cursorRawQuery.moveToFirst()) {
                do {
                    Note note = new Note();
                    note.setCreation(Long.valueOf(cursorRawQuery.getLong(0)));
                    note.setLastModification(Long.valueOf(cursorRawQuery.getLong(1)));
                    note.setTitle(cursorRawQuery.getString(2));
                    note.setContent(cursorRawQuery.getString(3));
                    note.setArchived(Boolean.valueOf("1".equals(cursorRawQuery.getString(4))));
                    note.setTrashed(Boolean.valueOf("1".equals(cursorRawQuery.getString(5))));
                    note.setAlarm(cursorRawQuery.getString(6));
                    note.setReminderFired(cursorRawQuery.getInt(7));
                    note.setRecurrenceRule(cursorRawQuery.getString(8));
                    note.setLatitude(cursorRawQuery.getString(9));
                    note.setLongitude(cursorRawQuery.getString(10));
                    note.setAddress(cursorRawQuery.getString(11));
                    note.setLocked(Boolean.valueOf("1".equals(cursorRawQuery.getString(12))));
                    note.setChecklist(Boolean.valueOf("1".equals(cursorRawQuery.getString(13))));
                    if (Boolean.TRUE.equals(note.isLocked())) {
                        note.setContent(Security.decrypt(note.getContent(), this.prefs.getString(ConstantsBase.PREF_PASSWORD, "")));
                    }
                    long j = cursorRawQuery.getLong(14);
                    if (j != 0) {
                        note.setCategory(new Category(Long.valueOf(j), cursorRawQuery.getString(15), cursorRawQuery.getString(16), cursorRawQuery.getString(17)));
                    }
                    note.setAttachmentsList(getNoteAttachments(note));
                    arrayList.add(note);
                } while (cursorRawQuery.moveToNext());
            }
            if (cursorRawQuery != null) {
                cursorRawQuery.close();
            }
            LogDelegate.v("Query: Retrieval finished!");
            return arrayList;
        } catch (Throwable th) {
            if (cursorRawQuery != null) {
                try {
                    cursorRawQuery.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public void archiveNote(Note note, boolean z) {
        note.setArchived(Boolean.valueOf(z));
        updateNote(note, false);
    }

    public void trashNote(Note note, boolean z) {
        note.setTrashed(Boolean.valueOf(z));
        updateNote(note, false);
    }

    public boolean deleteNote(Note note) {
        return deleteNote(note, false);
    }

    public boolean deleteNote(Note note, boolean z) {
        return deleteNote(note.get_id().longValue(), z);
    }

    public boolean deleteNote(long j, boolean z) {
        SQLiteDatabase database = getDatabase(true);
        database.delete("notes", "creation = ?", new String[]{String.valueOf(j)});
        if (!z) {
            database.delete("attachments", "note_id = ?", new String[]{String.valueOf(j)});
        }
        return true;
    }

    public void emptyTrash() {
        Iterator<Note> it2 = getNotesTrashed().iterator();
        while (it2.hasNext()) {
            deleteNote(it2.next());
        }
    }

    public List<Note> getNotesByPattern(String str) throws Resources.NotFoundException {
        String str2;
        String strEscapeSql = escapeSql(str);
        int navigation = Navigation.getNavigation();
        StringBuilder sb = new StringBuilder();
        sb.append(" WHERE trashed");
        sb.append(navigation == 3 ? " IS 1" : " IS NOT 1");
        sb.append(navigation == 1 ? " AND archived IS 1" : "");
        if (navigation == 5) {
            str2 = " AND category_id = " + Navigation.getCategory();
        } else {
            str2 = "";
        }
        sb.append(str2);
        sb.append(navigation == 4 ? " AND (category_id IS NULL OR category_id == 0) " : "");
        sb.append(Navigation.checkNavigation(2) ? " AND alarm IS NOT NULL" : "");
        sb.append(" AND ( ( ");
        sb.append("locked");
        sb.append(" IS NOT 1 AND (");
        sb.append("title");
        sb.append(" LIKE '%");
        sb.append(strEscapeSql);
        sb.append("%' ESCAPE '\\'  OR ");
        sb.append("content");
        sb.append(" LIKE '%");
        sb.append(strEscapeSql);
        sb.append("%' ESCAPE '\\' )) OR ( ");
        sb.append("locked");
        sb.append(" = 1 AND ");
        sb.append("title");
        sb.append(" LIKE '%");
        sb.append(strEscapeSql);
        sb.append("%' ESCAPE '\\' ))");
        return getNotes(sb.toString(), true);
    }

    static String escapeSql(String str) {
        return StringUtils.replace(str, "'", "''").replace("%", "\\%").replace(BaseLocale.SEP, "\\_");
    }

    public List<Note> getNotesWithReminder(boolean z) {
        String str;
        StringBuilder sb = new StringBuilder();
        sb.append(" WHERE alarm");
        if (z) {
            str = " >= " + Calendar.getInstance().getTimeInMillis();
        } else {
            str = " IS NOT NULL";
        }
        sb.append(str);
        sb.append(" AND ");
        sb.append(KEY_ARCHIVED);
        sb.append(" IS NOT 1 AND ");
        sb.append(KEY_TRASHED);
        sb.append(" IS NOT 1");
        return getNotes(sb.toString(), true);
    }

    public List<Note> getNotesWithReminderNotFired() {
        return getNotes(" WHERE alarm IS NOT NULL AND reminder_fired IS NOT 1 AND archived IS NOT 1 AND trashed IS NOT 1", true);
    }

    public List<Note> getNotesWithLock(boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append(" WHERE locked");
        sb.append(z ? " = 1 " : " IS NOT 1 ");
        return getNotes(sb.toString(), true);
    }

    public List<Note> getTodayReminders() {
        return getNotes(" WHERE DATE(alarm/1000, 'unixepoch') = DATE('now') AND trashed IS NOT 1", false);
    }

    public ArrayList<Attachment> getNoteAttachments(Note note) {
        return getAttachments(" WHERE note_id = " + note.get_id());
    }

    public List<Note> getChecklists() {
        return getNotes(" WHERE checklist = 1", false);
    }

    public List<Note> getMasked() {
        return getNotes(" WHERE locked = 1", false);
    }

    public List<Note> getNotesByCategory(Long l) {
        boolean z = this.prefs.getBoolean(ConstantsBase.PREF_FILTER_ARCHIVED_IN_CATEGORIES + l, false);
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(" WHERE category_id = ");
            sb.append(l);
            sb.append(" AND ");
            sb.append(KEY_TRASHED);
            sb.append(" IS NOT 1");
            sb.append(z ? " AND archived IS NOT 1" : "");
            return getNotes(sb.toString(), true);
        } catch (NumberFormatException unused) {
            return getAllNotes(true);
        }
    }

    public List<Tag> getTags() {
        return getTags(null);
    }

    public List<Tag> getTags(Note note) {
        ArrayList arrayList = new ArrayList();
        HashMap map = new HashMap();
        StringBuilder sb = new StringBuilder();
        sb.append(" WHERE ");
        sb.append(note != null ? "creation = " + note.get_id() + " AND " : "");
        sb.append("(");
        sb.append("content");
        sb.append(" LIKE '%#%' OR ");
        sb.append("title");
        sb.append(" LIKE '%#%' ) AND ");
        sb.append(KEY_TRASHED);
        sb.append(" IS ");
        sb.append(Navigation.checkNavigation(3) ? "" : " NOT ");
        sb.append(" 1");
        Iterator<Note> it2 = getNotes(sb.toString(), true).iterator();
        while (it2.hasNext()) {
            for (String str : TagsHelper.retrieveTags(it2.next()).keySet()) {
                map.put(str, Integer.valueOf((map.get(str) == null ? 0 : ((Integer) map.get(str)).intValue()) + 1));
            }
        }
        for (Map.Entry entry : map.entrySet()) {
            arrayList.add(new Tag((String) entry.getKey(), (Integer) entry.getValue()));
        }
        Collections.sort(arrayList, new Comparator() { // from class: it.orsozoxi.android.orsonotes.db.-$$Lambda$DbHelper$NTUve8k59-4zrH2YbsNIYSdwg8I
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return ((Tag) obj).getText().compareToIgnoreCase(((Tag) obj2).getText());
            }
        });
        return arrayList;
    }

    public List<Note> getNotesByTag(String str) {
        if (str.contains(ConstantsBase.PROPERTIES_PARAMS_SEPARATOR)) {
            return getNotesByTag(str.split(ConstantsBase.PROPERTIES_PARAMS_SEPARATOR));
        }
        return getNotesByTag(new String[]{str});
    }

    public List<Note> getNotesByTag(final String[] strArr) {
        StringBuilder sb = new StringBuilder();
        sb.append(" WHERE ");
        for (int i = 0; i < strArr.length; i++) {
            if (i != 0) {
                sb.append(" AND ");
            }
            sb.append("(content LIKE '%");
            sb.append(strArr[i]);
            sb.append("%' OR ");
            sb.append("title");
            sb.append(" LIKE '%");
            sb.append(strArr[i]);
            sb.append("%')");
        }
        sb.append(" AND trashed IS ");
        sb.append(Navigation.checkNavigation(3) ? "" : " NOT ");
        sb.append(" 1");
        return (List) Observable.from(getNotes(sb.toString(), true)).map(new Func1() { // from class: it.orsozoxi.android.orsonotes.db.-$$Lambda$DbHelper$ydhMpDznxs1xBsC3PgSK2rr7s8w
            @Override // rx.functions.Func1
            public final Object call(Object obj) {
                return DbHelper.lambda$getNotesByTag$2(strArr, (Note) obj);
            }
        }).filter(new Func1() { // from class: it.orsozoxi.android.orsonotes.db.-$$Lambda$xxLyx8YzXpJfQhDyBKugO05p7Xs
            @Override // rx.functions.Func1
            public final Object call(Object obj) {
                return Boolean.valueOf(C$r8$backportedMethods$utility$Objects$1$nonNull.nonNull((Note) obj));
            }
        }).toList().toBlocking().single();
    }

    static /* synthetic */ Note lambda$getNotesByTag$2(String[] strArr, final Note note) {
        if (Observable.from(strArr).all(new Func1() { // from class: it.orsozoxi.android.orsonotes.db.-$$Lambda$DbHelper$VkxpgGrlNahsBZps7safuTuNJBo
            @Override // rx.functions.Func1
            public final Object call(Object obj) {
                Note note2 = note;
                return Boolean.valueOf(Pattern.compile(".*(\\s|^)" + ((String) obj) + "(\\s|$).*", 8).matcher(note2.getTitle() + " " + note2.getContent()).find());
            }
        }).toBlocking().single().booleanValue()) {
            return note;
        }
        return null;
    }

    public List<Note> getNotesByUncompleteChecklist() {
        return getNotes(" WHERE checklist = 1 AND content LIKE '%[ ] %'", true);
    }

    public ArrayList<Attachment> getAllAttachments() {
        return getAttachments("");
    }

    public ArrayList<Attachment> getAttachments(String str) {
        ArrayList<Attachment> arrayList = new ArrayList<>();
        Cursor cursorRawQuery = null;
        try {
            cursorRawQuery = getDatabase().rawQuery("SELECT attachment_id,uri,name,size,length,mime_type FROM attachments" + str, null);
            if (cursorRawQuery.moveToFirst()) {
                do {
                    arrayList.add(new Attachment(cursorRawQuery.getLong(0), Uri.parse(cursorRawQuery.getString(1)), cursorRawQuery.getString(2), cursorRawQuery.getInt(3), cursorRawQuery.getInt(4), cursorRawQuery.getString(5)));
                } while (cursorRawQuery.moveToNext());
            }
            return arrayList;
        } finally {
            if (cursorRawQuery != null) {
                cursorRawQuery.close();
            }
        }
    }

    public ArrayList<Category> getCategories() {
        ArrayList<Category> arrayList = new ArrayList<>();
        Cursor cursorRawQuery = null;
        try {
            cursorRawQuery = getDatabase().rawQuery("SELECT category_id,name,description,color, COUNT(creation) count FROM categories LEFT JOIN ( SELECT creation, category_id FROM notes WHERE trashed IS NOT 1) USING( category_id)  GROUP BY category_id,name,description,color ORDER BY IFNULL(NULLIF(name, ''),'zzzzzzzz') ", null);
            if (cursorRawQuery.moveToFirst()) {
                do {
                    arrayList.add(new Category(Long.valueOf(cursorRawQuery.getLong(0)), cursorRawQuery.getString(1), cursorRawQuery.getString(2), cursorRawQuery.getString(3), cursorRawQuery.getInt(4)));
                } while (cursorRawQuery.moveToNext());
            }
            return arrayList;
        } finally {
            if (cursorRawQuery != null) {
                cursorRawQuery.close();
            }
        }
    }

    public Category updateCategory(Category category) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("category_id", Long.valueOf(category.getId() != null ? category.getId().longValue() : Calendar.getInstance().getTimeInMillis()));
        contentValues.put("name", category.getName());
        contentValues.put("description", category.getDescription());
        contentValues.put("color", category.getColor());
        getDatabase(true).insertWithOnConflict("categories", "category_id", contentValues, 5);
        return category;
    }

    public long deleteCategory(Category category) {
        SQLiteDatabase database = getDatabase(true);
        ContentValues contentValues = new ContentValues();
        contentValues.put("category_id", "");
        database.update("notes", contentValues, "category_id = ?", new String[]{String.valueOf(category.getId())});
        return database.delete("categories", "category_id = ?", new String[]{String.valueOf(category.getId())});
    }

    public Category getCategory(Long l) {
        Cursor cursorRawQuery = getDatabase().rawQuery("SELECT category_id,name,description,color FROM categories WHERE category_id = " + l, null);
        try {
            Category category = cursorRawQuery.moveToFirst() ? new Category(Long.valueOf(cursorRawQuery.getLong(0)), cursorRawQuery.getString(1), cursorRawQuery.getString(2), cursorRawQuery.getString(3)) : null;
            if (cursorRawQuery != null) {
                cursorRawQuery.close();
            }
            return category;
        } catch (Throwable th) {
            if (cursorRawQuery != null) {
                try {
                    cursorRawQuery.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public int getCategorizedCount(Category category) {
        Cursor cursorRawQuery = getDatabase().rawQuery("SELECT COUNT(*) FROM notes WHERE category_id = " + category.getId(), null);
        try {
            int i = cursorRawQuery.moveToFirst() ? cursorRawQuery.getInt(0) : 0;
            if (cursorRawQuery != null) {
                cursorRawQuery.close();
            }
            return i;
        } catch (Throwable th) {
            if (cursorRawQuery != null) {
                try {
                    cursorRawQuery.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public Stats getStats() throws Resources.NotFoundException {
        Stats stats = new Stats();
        stats.setCategories(getCategories().size());
        int i = 0;
        List<Note> allNotes = getAllNotes(false);
        Iterator<Note> it2 = allNotes.iterator();
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        int i7 = 0;
        int i8 = 0;
        int i9 = 0;
        int size = 0;
        int i10 = 0;
        int i11 = 0;
        int i12 = 0;
        while (it2.hasNext()) {
            Note next = it2.next();
            if (next.isTrashed().booleanValue()) {
                i5++;
            } else if (next.isArchived().booleanValue()) {
                i4++;
            } else {
                i3++;
            }
            if (next.getAlarm() != null && Long.parseLong(next.getAlarm()) > 0) {
                if (Long.parseLong(next.getAlarm()) > Calendar.getInstance().getTimeInMillis()) {
                    i7++;
                } else {
                    i6++;
                }
            }
            if (next.isChecklist().booleanValue()) {
                i8++;
            }
            if (next.isLocked().booleanValue()) {
                i9++;
            }
            size += TagsHelper.retrieveTags(next).size();
            if (next.getLongitude() != null && next.getLongitude().doubleValue() != 0.0d) {
                i10++;
            }
            Iterator<Note> it3 = it2;
            int words = NotesHelper.getWords(next);
            int i13 = i3;
            int chars = NotesHelper.getChars(next);
            if (words > i) {
                i = words;
            }
            if (chars > i2) {
                i2 = chars;
            }
            i11 += words;
            i12 += chars;
            it2 = it3;
            i3 = i13;
        }
        stats.setNotesActive(i3);
        stats.setNotesArchived(i4);
        stats.setNotesTrashed(i5);
        stats.setReminders(i6);
        stats.setRemindersFutures(i7);
        stats.setNotesChecklist(i8);
        stats.setNotesMasked(i9);
        stats.setTags(size);
        stats.setLocation(i10);
        int size2 = i11 / (!allNotes.isEmpty() ? allNotes.size() : 1);
        int size3 = allNotes.isEmpty() ? 1 : allNotes.size();
        stats.setWords(i11);
        stats.setWordsMax(i);
        stats.setWordsAvg(size2);
        stats.setChars(i12);
        stats.setCharsMax(i2);
        stats.setCharsAvg(i12 / size3);
        int i14 = 0;
        int i15 = 0;
        int i16 = 0;
        int i17 = 0;
        int i18 = 0;
        for (Attachment attachment : getAllAttachments()) {
            if (ConstantsBase.MIME_TYPE_IMAGE.equals(attachment.getMime_type())) {
                i14++;
            } else if (ConstantsBase.MIME_TYPE_VIDEO.equals(attachment.getMime_type())) {
                i15++;
            } else if (ConstantsBase.MIME_TYPE_AUDIO.equals(attachment.getMime_type())) {
                i16++;
            } else if (ConstantsBase.MIME_TYPE_SKETCH.equals(attachment.getMime_type())) {
                i17++;
            } else if (ConstantsBase.MIME_TYPE_FILES.equals(attachment.getMime_type())) {
                i18++;
            }
        }
        stats.setAttachments(0);
        stats.setImages(i14);
        stats.setVideos(i15);
        stats.setAudioRecordings(i16);
        stats.setSketches(i17);
        stats.setFiles(i18);
        return stats;
    }

    public void setReminderFired(long j, boolean z) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_REMINDER_FIRED, Boolean.valueOf(z));
        getDatabase(true).update("notes", contentValues, "creation = ?", new String[]{String.valueOf(j)});
    }
}
