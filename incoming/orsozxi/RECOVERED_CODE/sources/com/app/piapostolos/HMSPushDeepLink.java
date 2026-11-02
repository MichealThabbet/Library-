package com.app.piapostolos;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import com.app.piapostolos.Adapters.DBAdapter;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class HMSPushDeepLink extends Activity {
    @Override // android.app.Activity
    public void onCreate(Bundle bundle) throws SQLException {
        super.onCreate(bundle);
        Intent intent = getIntent();
        if (intent != null) {
            String stringExtra = intent.getStringExtra("message");
            String stringExtra2 = intent.getStringExtra("link");
            if (!isRepeated(stringExtra)) {
                insertnewmessage(stringExtra, stringExtra2);
                Intent intent2 = new Intent(this, (Class<?>) MainActivityGCM.class);
                intent2.setFlags(268468224);
                intent2.putExtra("name", stringExtra);
                intent2.putExtra("email", "22");
                intent2.putExtra("image", stringExtra2);
                startActivity(intent2);
                finish();
                return;
            }
            finish();
        }
    }

    public void insertnewmessage(String str, String str2) throws SQLException {
        DBAdapter dBAdapter = new DBAdapter(getApplicationContext());
        dBAdapter.open();
        dBAdapter.insert(str, str2);
        dBAdapter.close();
    }

    public boolean isRepeated(String str) throws SQLException {
        DBAdapter dBAdapter = new DBAdapter(getApplicationContext());
        dBAdapter.open();
        Cursor cursorFetchAll = dBAdapter.fetchAll();
        if (cursorFetchAll.getCount() <= 0) {
            return false;
        }
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        if (cursorFetchAll.moveToFirst()) {
            do {
                arrayList.add(cursorFetchAll.getString(1));
                arrayList2.add(cursorFetchAll.getString(2));
            } while (cursorFetchAll.moveToNext());
        }
        dBAdapter.close();
        for (int size = arrayList.size() - 1; size > 0; size--) {
            if (((String) arrayList.get(size)).toString().equals(str)) {
                return true;
            }
        }
        return false;
    }
}
