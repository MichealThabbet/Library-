package com.app.orsozoxi;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.app.orsozoxi.Adapters.DBAdapter;
import com.app.orsozoxi.Adapters.gcmItemsMainAdapter;
import com.app.orsozoxi.Beans.gcm_msgs;
import com.app.orsozoxi.Others.checksim;
import com.facebook.share.internal.ShareConstants;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class Messages extends BaseActivity {
    private gcmItemsMainAdapter CommemtsAdapter;

    @Override // com.app.orsozoxi.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) throws SQLException {
        super.onCreate(bundle);
        setContentView(R.layout.activity_messages);
        getWindow().addFlags(128);
        new checksim();
        getWindow().setBackgroundDrawableResource(R.drawable.background);
        DBAdapter dBAdapter = new DBAdapter(getApplicationContext());
        dBAdapter.open();
        ArrayList arrayList = new ArrayList();
        Cursor cursorFetchAll = dBAdapter.fetchAll();
        ArrayList arrayList2 = new ArrayList();
        final int intExtra = getIntent().getIntExtra(ShareConstants.MEDIA_TYPE, 0);
        if (intExtra == 0) {
            getWindow().setBackgroundDrawableResource(R.drawable.saydna);
        } else if (intExtra == 1) {
            getWindow().setBackgroundDrawableResource(R.drawable.orso);
        } else if (intExtra == 2) {
            getWindow().setBackgroundDrawableResource(R.drawable.newsss);
        }
        if (cursorFetchAll.moveToFirst()) {
            do {
                boolean z = false;
                for (int i = 0; i < arrayList2.size(); i++) {
                    if (((String) arrayList2.get(i)).equals(cursorFetchAll.getString(1))) {
                        z = true;
                    }
                }
                gcm_msgs gcm_msgsVar = new gcm_msgs();
                gcm_msgsVar.setMsg(cursorFetchAll.getString(1));
                gcm_msgsVar.setImagelink(cursorFetchAll.getString(2));
                if (!z) {
                    arrayList2.add(cursorFetchAll.getString(1));
                    arrayList.add(gcm_msgsVar);
                }
                System.out.println("dis" + cursorFetchAll.getString(1));
                System.out.println("display" + cursorFetchAll.getString(2));
            } while (cursorFetchAll.moveToNext());
        }
        cursorFetchAll.close();
        dBAdapter.close();
        ListView listView = (ListView) findViewById(R.id.dailylist);
        listView.setCacheColorHint(0);
        listView.setDivider(null);
        listView.setTextFilterEnabled(true);
        final ArrayList arrayList3 = new ArrayList();
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            if (intExtra == 0) {
                if (((gcm_msgs) arrayList.get(size)).getImagelink().equals("https://www.orsozoxi.net/bprsms/images/bpr.jpg")) {
                    arrayList3.add((gcm_msgs) arrayList.get(size));
                }
            } else if (intExtra == 1) {
                if (((gcm_msgs) arrayList.get(size)).getImagelink().equals("https://www.orsozoxi.net/gcmsms/images/orsozoxisms.jpg")) {
                    arrayList3.add((gcm_msgs) arrayList.get(size));
                }
            } else if (intExtra == 2 && ((gcm_msgs) arrayList.get(size)).getImagelink().equals("https://www.orsozoxi.net/gcmnews/images/news.jpg")) {
                arrayList3.add((gcm_msgs) arrayList.get(size));
            }
        }
        if (arrayList3.size() == 0) {
            findViewById(R.id.tv_messages).setVisibility(0);
        } else {
            findViewById(R.id.tv_messages).setVisibility(8);
        }
        gcmItemsMainAdapter gcmitemsmainadapter = new gcmItemsMainAdapter(this, arrayList3);
        this.CommemtsAdapter = gcmitemsmainadapter;
        listView.setAdapter((ListAdapter) gcmitemsmainadapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.app.orsozoxi.Messages.1
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view, int i2, long j) {
                Intent intent = new Intent();
                intent.setClass(Messages.this, Message_details.class);
                intent.putExtra("msg", ((gcm_msgs) arrayList3.get(i2)).getMsg());
                intent.putExtra("url", ((gcm_msgs) arrayList3.get(i2)).getImagelink());
                intent.putExtra(ShareConstants.MEDIA_TYPE, intExtra);
                Messages.this.startActivity(intent);
            }
        });
        this.CommemtsAdapter.notifyDataSetChanged();
    }
}
