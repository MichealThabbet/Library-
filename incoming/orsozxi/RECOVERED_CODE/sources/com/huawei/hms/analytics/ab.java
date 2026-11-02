package com.huawei.hms.analytics;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import com.huawei.hms.analytics.core.log.HiLog;
import com.huawei.hms.analytics.i;

/* loaded from: classes2.dex */
public final class ab extends ad {
    protected ab(Context context, i.lmn lmnVar) {
        super(context, lmnVar, new v(context, lmnVar));
    }

    private String ijk() {
        int columnIndex;
        Cursor cursorQuery = null;
        try {
            try {
                cursorQuery = this.klm.getContentResolver().query(Uri.parse("content://com.meizu.flyme.openidsdk/"), null, null, new String[]{"oaid"}, null);
            } catch (Exception unused) {
                HiLog.w("MeizHelper", "contentResolver query exception");
                if (cursorQuery == null) {
                    return "";
                }
            }
            if (cursorQuery == null || !cursorQuery.moveToFirst() || (columnIndex = cursorQuery.getColumnIndex("value")) < 0) {
                if (cursorQuery == null) {
                    return "";
                }
                cursorQuery.close();
                return "";
            }
            String string = cursorQuery.getString(columnIndex);
            if (cursorQuery != null) {
                cursorQuery.close();
            }
            return string;
        } catch (Throwable th) {
            if (cursorQuery != null) {
                cursorQuery.close();
            }
            throw th;
        }
    }

    @Override // com.huawei.hms.analytics.ad
    public final boolean klm() {
        return Build.VERSION.SDK_INT >= 29;
    }

    @Override // com.huawei.hms.analytics.ad
    public final void lmn() {
        String strIjk = ijk();
        if (TextUtils.isEmpty(strIjk)) {
            lmn("meiz oid is empty, begin get gaid");
        } else {
            this.lmn.lmn(strIjk, "", "oaid");
        }
    }
}
