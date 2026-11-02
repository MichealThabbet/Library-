package com.app.orsozoxi.Alarm.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.app.orsozoxi.Alarm.Alarm;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class Database extends SQLiteOpenHelper {
    public static final String ALARM_TABLE = "alarm";
    public static final String COLUMN_ALARM_ACTIVE = "alarm_active";
    public static final String COLUMN_ALARM_DAYS = "alarm_days";
    public static final String COLUMN_ALARM_DIFFICULTY = "alarm_difficulty";
    public static final String COLUMN_ALARM_ID = "_id";
    public static final String COLUMN_ALARM_NAME = "alarm_name";
    public static final String COLUMN_ALARM_TIME = "alarm_time";
    public static final String COLUMN_ALARM_TONE = "alarm_tone";
    public static final String COLUMN_ALARM_VIBRATE = "alarm_vibrate";
    static final String DATABASE_NAME = "DB";
    static final int DATABASE_VERSION = 1;
    private static Context con;
    static SQLiteDatabase database;
    static Database instance;

    public static void init(Context context) {
        if (instance == null) {
            instance = new Database(context);
        }
        con = context;
    }

    public static SQLiteDatabase getDatabase() {
        if (database == null) {
            if (instance == null) {
                instance = new Database(con);
            }
            database = instance.getWritableDatabase();
        }
        return database;
    }

    public static void deactivate() {
        SQLiteDatabase sQLiteDatabase = database;
        if (sQLiteDatabase != null && sQLiteDatabase.isOpen()) {
            database.close();
        }
        database = null;
        instance = null;
    }

    public static long create(Alarm alarm) throws IOException {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ALARM_ACTIVE, alarm.getAlarmActive());
        contentValues.put(COLUMN_ALARM_TIME, alarm.getAlarmTimeString());
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            new ObjectOutputStream(byteArrayOutputStream).writeObject(alarm.getDays());
            contentValues.put(COLUMN_ALARM_DAYS, byteArrayOutputStream.toByteArray());
        } catch (Exception unused) {
        }
        contentValues.put(COLUMN_ALARM_DIFFICULTY, Integer.valueOf(alarm.getDifficulty().ordinal()));
        contentValues.put(COLUMN_ALARM_TONE, alarm.getAlarmTonePath());
        contentValues.put(COLUMN_ALARM_VIBRATE, alarm.getVibrate());
        contentValues.put(COLUMN_ALARM_NAME, alarm.getAlarmName());
        return getDatabase().insert("alarm", null, contentValues);
    }

    public static int update(Alarm alarm) throws IOException {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ALARM_ACTIVE, alarm.getAlarmActive());
        contentValues.put(COLUMN_ALARM_TIME, alarm.getAlarmTimeString());
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            new ObjectOutputStream(byteArrayOutputStream).writeObject(alarm.getDays());
            contentValues.put(COLUMN_ALARM_DAYS, byteArrayOutputStream.toByteArray());
        } catch (Exception unused) {
        }
        contentValues.put(COLUMN_ALARM_DIFFICULTY, Integer.valueOf(alarm.getDifficulty().ordinal()));
        contentValues.put(COLUMN_ALARM_TONE, alarm.getAlarmTonePath());
        contentValues.put(COLUMN_ALARM_VIBRATE, alarm.getVibrate());
        contentValues.put(COLUMN_ALARM_NAME, alarm.getAlarmName());
        return getDatabase().update("alarm", contentValues, "_id=" + alarm.getId(), null);
    }

    public static int deleteEntry(Alarm alarm) {
        return deleteEntry(alarm.getId());
    }

    public static int deleteEntry(int i) {
        return getDatabase().delete("alarm", "_id=" + i, null);
    }

    public static int deleteAll() {
        return getDatabase().delete("alarm", "1", null);
    }

    public static Alarm getAlarm(int i) throws ClassNotFoundException, IOException {
        Alarm alarm;
        String[] strArr = {COLUMN_ALARM_ID, COLUMN_ALARM_ACTIVE, COLUMN_ALARM_TIME, COLUMN_ALARM_DAYS, COLUMN_ALARM_DIFFICULTY, COLUMN_ALARM_TONE, COLUMN_ALARM_VIBRATE, COLUMN_ALARM_NAME};
        Cursor cursorQuery = getDatabase().query("alarm", strArr, "_id=" + i, null, null, null, null);
        if (cursorQuery.moveToFirst()) {
            alarm = new Alarm();
            alarm.setId(cursorQuery.getInt(1));
            alarm.setAlarmActive(Boolean.valueOf(cursorQuery.getInt(2) == 1));
            alarm.setAlarmTime(cursorQuery.getString(3));
            try {
                Object object = new ObjectInputStream(new ByteArrayInputStream(cursorQuery.getBlob(4))).readObject();
                if (object instanceof Alarm.Day[]) {
                    alarm.setDays((Alarm.Day[]) object);
                }
            } catch (StreamCorruptedException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            } catch (ClassNotFoundException e3) {
                e3.printStackTrace();
            }
            alarm.setDifficulty(Alarm.Difficulty.values()[cursorQuery.getInt(5)]);
            alarm.setAlarmTonePath(cursorQuery.getString(6));
            alarm.setVibrate(Boolean.valueOf(cursorQuery.getInt(7) == 1));
            alarm.setAlarmName(cursorQuery.getString(8));
        } else {
            alarm = null;
        }
        cursorQuery.close();
        return alarm;
    }

    public static Cursor getCursor() {
        return getDatabase().query("alarm", new String[]{COLUMN_ALARM_ID, COLUMN_ALARM_ACTIVE, COLUMN_ALARM_TIME, COLUMN_ALARM_DAYS, COLUMN_ALARM_DIFFICULTY, COLUMN_ALARM_TONE, COLUMN_ALARM_VIBRATE, COLUMN_ALARM_NAME}, null, null, null, null, null);
    }

    Database(Context context) {
        super(context, DATABASE_NAME, (SQLiteDatabase.CursorFactory) null, 1);
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(SQLiteDatabase sQLiteDatabase) throws SQLException {
        sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS alarm ( _id INTEGER primary key autoincrement, alarm_active INTEGER NOT NULL, alarm_time TEXT NOT NULL, alarm_days BLOB NOT NULL, alarm_difficulty INTEGER NOT NULL, alarm_tone TEXT NOT NULL, alarm_vibrate INTEGER NOT NULL, alarm_name TEXT NOT NULL)");
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) throws SQLException {
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS alarm");
        onCreate(sQLiteDatabase);
    }

    public static List<Alarm> getAll() throws ClassNotFoundException, IOException {
        ArrayList arrayList = new ArrayList();
        Cursor cursor = getCursor();
        if (cursor.moveToFirst()) {
            do {
                Alarm alarm = new Alarm();
                alarm.setId(cursor.getInt(0));
                alarm.setAlarmActive(Boolean.valueOf(cursor.getInt(1) == 1));
                alarm.setAlarmTime(cursor.getString(2));
                try {
                    Object object = new ObjectInputStream(new ByteArrayInputStream(cursor.getBlob(3))).readObject();
                    if (object instanceof Alarm.Day[]) {
                        alarm.setDays((Alarm.Day[]) object);
                    }
                } catch (StreamCorruptedException e) {
                    e.printStackTrace();
                } catch (IOException e2) {
                    e2.printStackTrace();
                } catch (ClassNotFoundException e3) {
                    e3.printStackTrace();
                }
                alarm.setDifficulty(Alarm.Difficulty.values()[cursor.getInt(4)]);
                alarm.setAlarmTonePath(cursor.getString(5));
                alarm.setVibrate(Boolean.valueOf(cursor.getInt(6) == 1));
                alarm.setAlarmName(cursor.getString(7));
                arrayList.add(alarm);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return arrayList;
    }
}
