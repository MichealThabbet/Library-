package com.huawei.hms.analytics;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import com.huawei.hms.analytics.core.log.HiLog;
import com.huawei.hms.analytics.i;

/* loaded from: classes2.dex */
public final class ai extends ad {
    protected ai(Context context, i.lmn lmnVar) {
        super(context, lmnVar, new v(context, lmnVar));
    }

    private String ijk() {
        int columnIndex;
        String string = "";
        Cursor cursorQuery = null;
        try {
            try {
                cursorQuery = this.klm.getContentResolver().query(Uri.parse("content://com.vivo.vms.IdProvider/IdentifierId/OAID"), null, null, null, null);
                if (cursorQuery != null && cursorQuery.moveToFirst() && (columnIndex = cursorQuery.getColumnIndex("value")) >= 0) {
                    string = cursorQuery.getString(columnIndex);
                }
            } catch (Exception unused) {
                HiLog.w("vvOaid", "contentResolver query exception");
                if (cursorQuery != null) {
                }
            }
            return string;
        } finally {
            if (cursorQuery != null) {
                cursorQuery.close();
            }
        }
    }

    @Override // com.huawei.hms.analytics.ad
    public final boolean klm() {
        return Build.VERSION.SDK_INT >= 28;
    }

    @Override // com.huawei.hms.analytics.ad
    public final void lmn() {
        String strIjk = ijk();
        if (TextUtils.isEmpty(strIjk)) {
            lmn("oaid is empty, begin get gaid");
        } else {
            this.lmn.lmn(strIjk, "", "oaid");
        }
    }
}
