package com.huawei.hms.analytics;

import android.database.Cursor;
import android.database.SQLException;
import android.text.TextUtils;
import com.huawei.hms.analytics.core.log.HiLog;
import it.piapostolos.android.pinotes.utils.ConstantsBase;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.internal.DaoConfig;

/* loaded from: classes2.dex */
public final class bg {
    @SafeVarargs
    public static void klm(Database database, Class<? extends AbstractDao<?, ?>>... clsArr) throws SQLException {
        for (int i = 0; i <= 0; i++) {
            String str = new DaoConfig(database, clsArr[0]).tablename;
            database.execSQL("ALTER TABLE " + str + " RENAME TO " + (str + "_TEMP"));
        }
    }

    private static List<String> lmn(Database database, String str) {
        Cursor cursorRawQuery = null;
        try {
            try {
                cursorRawQuery = database.rawQuery("SELECT * FROM " + str + " limit 1", null);
            } catch (Exception unused) {
                HiLog.w("MigrationDB", "rawQuery Exception.");
                if (cursorRawQuery != null) {
                }
            }
            if (cursorRawQuery == null) {
                if (cursorRawQuery != null) {
                    cursorRawQuery.close();
                }
                return new ArrayList();
            }
            ArrayList arrayList = new ArrayList(Arrays.asList(cursorRawQuery.getColumnNames()));
            if (cursorRawQuery != null) {
                cursorRawQuery.close();
            }
            return arrayList;
        } catch (Throwable th) {
            if (cursorRawQuery != null) {
                cursorRawQuery.close();
            }
            throw th;
        }
    }

    @SafeVarargs
    public static void lmn(Database database, Class<? extends AbstractDao<?, ?>>... clsArr) throws Exception {
        String str;
        for (int i = 0; i <= 0; i++) {
            DaoConfig daoConfig = new DaoConfig(database, clsArr[i]);
            String str2 = daoConfig.tablename;
            String str3 = str2 + "_TEMP";
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            int length = daoConfig.properties.length;
            for (int i2 = 0; i2 < length; i2++) {
                String str4 = daoConfig.properties[i2].columnName;
                if (lmn(database, str3).contains(str4)) {
                    arrayList.add(str4);
                    arrayList2.add(str4);
                } else {
                    try {
                        Class<?> cls = daoConfig.properties[i2].type;
                        if (cls.equals(String.class)) {
                            str = "TEXT";
                        } else if (cls.equals(Long.class) || cls.equals(Integer.class) || cls.equals(Long.TYPE) || cls.equals(Integer.TYPE)) {
                            str = "INTEGER";
                        } else {
                            if (!cls.equals(Boolean.class) && !cls.equals(Boolean.TYPE)) {
                                throw new Exception("Migration Helper types does not match current parameter");
                            }
                            str = "BOOLEAN";
                        }
                        if (str.equals("INTEGER")) {
                            arrayList2.add("0 as ".concat(String.valueOf(str4)));
                            arrayList.add(str4);
                        }
                    } catch (Exception e) {
                        HiLog.w("MigrationDB", e.getMessage());
                    }
                }
            }
            database.execSQL("INSERT INTO " + str2 + " (" + TextUtils.join(ConstantsBase.PROPERTIES_PARAMS_SEPARATOR, arrayList) + ") SELECT " + TextUtils.join(ConstantsBase.PROPERTIES_PARAMS_SEPARATOR, arrayList2) + " FROM " + str3 + ";");
            database.execSQL("DROP TABLE ".concat(String.valueOf(str3)));
        }
    }
}
