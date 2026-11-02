package com.marshalchen.ultimaterecyclerview.expanx.Util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.Toast;
import com.marshalchen.ultimaterecyclerview.expanx.ExpandableItemData;
import com.marshalchen.ultimaterecyclerview.expanx.SmartItem;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import net.sqlcipher.database.SQLiteDatabase;

/* loaded from: classes2.dex */
public class DataUtil {
    public static final String TAG = "TreeList";

    public static List<SmartItem> getSmallList(String[] strArr) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < strArr.length - 2; i += 2) {
            arrayList.add(SmartItem.child(strArr[i], strArr[i + 1]));
        }
        return arrayList;
    }

    public static List<ExpandableItemData> getChildrenByPath(String str, int i) {
        int i2 = i + 1;
        try {
            ArrayList arrayList = new ArrayList();
            File[] fileArrListFiles = new File(str).listFiles();
            ArrayList arrayList2 = new ArrayList();
            for (File file : fileArrListFiles) {
                if (file.isDirectory()) {
                    arrayList.add(new ExpandableItemData(1026, file.getName(), file.getAbsolutePath(), UUID.randomUUID().toString(), i2, null));
                } else {
                    arrayList2.add(new ExpandableItemData(1135, file.getName(), file.getAbsolutePath(), UUID.randomUUID().toString(), i2, null));
                }
            }
            Collections.sort(arrayList);
            Collections.sort(arrayList2);
            arrayList.addAll(arrayList2);
            return arrayList;
        } catch (Exception e) {
            Log.d(TAG, "get child error:" + e.getMessage());
            return null;
        }
    }

    public static void openFileInSystem(String str, Context context) {
        try {
            MimeTypeMap singleton = MimeTypeMap.getSingleton();
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setDataAndType(Uri.fromFile(new File(str)), singleton.getMimeTypeFromExtension(fileExt(str).substring(1)));
            intent.setFlags(SQLiteDatabase.CREATE_IF_NECESSARY);
            context.startActivity(intent);
        } catch (Exception unused) {
            Toast.makeText(context, "No handler for this type of file.", 1).show();
        }
    }

    private static String fileExt(String str) {
        if (str.indexOf("?") > -1) {
            str = str.substring(0, str.indexOf("?"));
        }
        if (str.lastIndexOf(".") == -1) {
            return null;
        }
        String strSubstring = str.substring(str.lastIndexOf("."));
        if (strSubstring.indexOf("%") > -1) {
            strSubstring = strSubstring.substring(0, strSubstring.indexOf("%"));
        }
        if (strSubstring.indexOf("/") > -1) {
            strSubstring = strSubstring.substring(0, strSubstring.indexOf("/"));
        }
        return strSubstring.toLowerCase();
    }
}
