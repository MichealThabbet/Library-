package com.app.orsozoxi.BibleDataBase;

import android.content.Context;
import android.util.Log;
import com.app.orsozoxi.Others.Util;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import net.sqlcipher.Cursor;
import net.sqlcipher.SQLException;
import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;

/* loaded from: classes.dex */
public class UseDatabaseHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Bible";
    private static final int DATABASE_VERSION = 1;
    private static final String KEY_BOOK_CHAPTER_AYAH = "ayah_name";
    private static final String KEY_BOOK_CHAPTER_AYAH_INDEX = "ayah_index";
    private static final String KEY_BOOK_CHAPTER_AYAH_NORMALIZED = "ayah_name_normalized";
    private static final String KEY_BOOK_CHAPTER_INDEX = "chapter_index";
    private static final String KEY_BOOK_CHAPTER_NAME = "chapter_name";
    private static final String KEY_BOOK_INDEX = "book_index";
    private static final String KEY_BOOK_NAME = "book_name";
    private static final String KEY_ID = "id";
    private static final String TABLE_CONTACTS = "bible";
    private static SQLiteDatabase db;
    private static UseDatabaseHandler instance;
    private final Context ctx;

    @Override // net.sqlcipher.database.SQLiteOpenHelper
    public void onCreate(SQLiteDatabase sQLiteDatabase) {
    }

    public UseDatabaseHandler(Context context) {
        super(context, context.getApplicationInfo().dataDir + "/database_/" + DATABASE_NAME, null, 1);
        this.ctx = context;
    }

    public static synchronized UseDatabaseHandler getInstance(Context context) {
        if (instance == null) {
            instance = new UseDatabaseHandler(context);
        }
        return instance;
    }

    public void open(String str) throws SQLException {
        if (instance == null) {
            instance = new UseDatabaseHandler(this.ctx);
        }
        db = instance.getWritableDatabase(str);
    }

    @Override // net.sqlcipher.database.SQLiteOpenHelper
    public void close() {
        SQLiteDatabase sQLiteDatabase = db;
        if (sQLiteDatabase == null || !sQLiteDatabase.isOpen()) {
            return;
        }
        db.close();
    }

    @Override // net.sqlcipher.database.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) throws SQLException {
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS bible");
        onCreate(sQLiteDatabase);
    }

    private String[] getSearchPattern(String str) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(str);
        String[] strArrSplit = str.split(" ");
        if (strArrSplit.length > 2) {
            int i = 0;
            while (i < strArrSplit.length - 1) {
                StringBuilder sb = new StringBuilder();
                sb.append(strArrSplit[i]);
                sb.append(" ");
                i++;
                sb.append(strArrSplit[i]);
                arrayList.add(sb.toString());
            }
        } else if (strArrSplit.length == 2) {
            for (String str2 : strArrSplit) {
                arrayList.add(str2);
            }
        }
        int size = arrayList.size();
        String[] strArr = new String[size];
        for (int i2 = 0; i2 < size; i2++) {
            strArr[i2] = "%" + ((String) arrayList.get(i2)) + "%";
            StringBuilder sb2 = new StringBuilder();
            sb2.append("key=");
            sb2.append(strArr[i2]);
            Log.d("result", sb2.toString());
        }
        return strArr;
    }

    public List<BibleDataBase> getSearchResult(String str) {
        String[] strArrSplit = str.split(" ");
        ArrayList arrayList = new ArrayList();
        String[] searchPattern = getSearchPattern(str);
        String str2 = "SELECT  * FROM bible WHERE ayah_name_normalized LIKE ?";
        if (searchPattern.length > 1) {
            if (strArrSplit.length == 2) {
                str2 = "SELECT  * FROM bible WHERE ayah_name_normalized LIKE ? OR (ayah_name_normalized LIKE ? AND ayah_name_normalized LIKE ? )";
            } else {
                for (int i = 1; i < searchPattern.length; i++) {
                    str2 = str2 + " AND ayah_name_normalized LIKE ?";
                }
            }
        }
        Cursor cursorRawQuery = db.rawQuery(str2, searchPattern);
        if (cursorRawQuery.moveToFirst()) {
            do {
                BibleDataBase bibleDataBase = new BibleDataBase();
                bibleDataBase.setBookIndex(Integer.parseInt(cursorRawQuery.getString(1)));
                bibleDataBase.setBookName(cursorRawQuery.getString(2));
                bibleDataBase.setChapterIndex(Integer.parseInt(cursorRawQuery.getString(3)));
                bibleDataBase.setChapterName(cursorRawQuery.getString(4));
                bibleDataBase.setAyahIndex(Integer.parseInt(cursorRawQuery.getString(5)));
                bibleDataBase.setAyah(cursorRawQuery.getString(6));
                bibleDataBase.setAyahNormalized(cursorRawQuery.getString(7));
                arrayList.add(bibleDataBase);
            } while (cursorRawQuery.moveToNext());
        }
        cursorRawQuery.close();
        return arrayList;
    }

    public List<BibleDataBase> getSearchResult(String str, int i) {
        String[] strArrSplit = str.split(" ");
        ArrayList arrayList = new ArrayList();
        String[] searchPattern = getSearchPattern(str);
        String str2 = "SELECT  * FROM bible WHERE book_index = " + i + " AND ( " + KEY_BOOK_CHAPTER_AYAH_NORMALIZED + " LIKE ?";
        if (searchPattern.length > 1) {
            if (strArrSplit.length == 2) {
                str2 = str2 + " OR (ayah_name_normalized LIKE ? AND ayah_name_normalized LIKE ? )";
            } else {
                for (int i2 = 1; i2 < searchPattern.length; i2++) {
                    str2 = str2 + " AND ayah_name_normalized LIKE ?";
                }
            }
        }
        Cursor cursorRawQuery = db.rawQuery(str2 + " )", searchPattern);
        if (cursorRawQuery.moveToFirst()) {
            do {
                BibleDataBase bibleDataBase = new BibleDataBase();
                bibleDataBase.setBookIndex(Integer.parseInt(cursorRawQuery.getString(1)));
                bibleDataBase.setBookName(cursorRawQuery.getString(2));
                bibleDataBase.setChapterIndex(Integer.parseInt(cursorRawQuery.getString(3)));
                bibleDataBase.setChapterName(cursorRawQuery.getString(4));
                bibleDataBase.setAyahIndex(Integer.parseInt(cursorRawQuery.getString(5)));
                bibleDataBase.setAyah(cursorRawQuery.getString(6));
                bibleDataBase.setAyahNormalized(cursorRawQuery.getString(7));
                arrayList.add(bibleDataBase);
            } while (cursorRawQuery.moveToNext());
        }
        cursorRawQuery.close();
        return arrayList;
    }

    public List<BibleDataBase> getSearchResult(String str, boolean z) {
        String[] strArrSplit = str.split(" ");
        ArrayList arrayList = new ArrayList();
        String[] searchPattern = getSearchPattern(str);
        String str2 = z ? "SELECT  * FROM bible WHERE book_index < 46 AND ( ayah_name_normalized LIKE ?" : "SELECT  * FROM bible WHERE book_index > 45 AND ( ayah_name_normalized LIKE ?";
        if (searchPattern.length > 1) {
            if (strArrSplit.length == 2) {
                str2 = str2 + " OR (ayah_name_normalized LIKE ? AND ayah_name_normalized LIKE ? )";
            } else {
                for (int i = 1; i < searchPattern.length; i++) {
                    str2 = str2 + " AND ayah_name_normalized LIKE ? ";
                }
            }
        }
        String str3 = str2 + " )";
        Log.d(Util.TAG, str3);
        Cursor cursorRawQuery = db.rawQuery(str3, searchPattern);
        if (cursorRawQuery.moveToFirst()) {
            do {
                BibleDataBase bibleDataBase = new BibleDataBase();
                bibleDataBase.setBookIndex(Integer.parseInt(cursorRawQuery.getString(1)));
                bibleDataBase.setBookName(cursorRawQuery.getString(2));
                bibleDataBase.setChapterIndex(Integer.parseInt(cursorRawQuery.getString(3)));
                bibleDataBase.setChapterName(cursorRawQuery.getString(4));
                bibleDataBase.setAyahIndex(Integer.parseInt(cursorRawQuery.getString(5)));
                bibleDataBase.setAyah(cursorRawQuery.getString(6));
                bibleDataBase.setAyahNormalized(cursorRawQuery.getString(7));
                arrayList.add(bibleDataBase);
            } while (cursorRawQuery.moveToNext());
        }
        cursorRawQuery.close();
        return arrayList;
    }

    public List<BibleDataBase> getChapter(int i, int i2) {
        ArrayList arrayList = new ArrayList();
        Cursor cursorRawQuery = db.rawQuery("SELECT  * FROM bible WHERE book_index =? AND chapter_index =?", new String[]{i + "", i2 + ""});
        if (cursorRawQuery.moveToFirst()) {
            do {
                BibleDataBase bibleDataBase = new BibleDataBase();
                bibleDataBase.setBookIndex(Integer.parseInt(cursorRawQuery.getString(1)));
                bibleDataBase.setBookName(cursorRawQuery.getString(2));
                bibleDataBase.setChapterIndex(Integer.parseInt(cursorRawQuery.getString(3)));
                bibleDataBase.setChapterName(cursorRawQuery.getString(4));
                bibleDataBase.setAyahIndex(Integer.parseInt(cursorRawQuery.getString(5)));
                bibleDataBase.setAyah(cursorRawQuery.getString(6));
                arrayList.add(bibleDataBase);
            } while (cursorRawQuery.moveToNext());
        }
        cursorRawQuery.close();
        return arrayList;
    }

    public void CopyDataBaseFromAsset(String str) throws IOException {
        InputStream inputStreamOpen = this.ctx.getAssets().open("mybible/Bible");
        String str2 = str + DATABASE_NAME;
        File file = new File(this.ctx.getApplicationInfo().dataDir + "/database_");
        if (!file.exists()) {
            file.mkdir();
            if (!new File(file.getAbsolutePath().toString() + "/" + str2).exists()) {
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(str2);
                    byte[] bArr = new byte[1024];
                    while (true) {
                        int i = inputStreamOpen.read(bArr);
                        if (i <= 0) {
                            break;
                        } else {
                            fileOutputStream.write(bArr, 0, i);
                        }
                    }
                    Log.e("sample", "BibleCompleted");
                    fileOutputStream.flush();
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            Log.e("sample", "Bible Already exist");
        }
        inputStreamOpen.close();
    }
}
