package com.app.piapostolos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.app.piapostolos.Alarm.Database.Database;
import com.app.piapostolos.ApsalmodyBeans.Apsalmody;
import com.app.piapostolos.ApsalmodyBeans.FavouritListEntity;
import com.app.piapostolos.Others.SavePrefs;
import com.app.piapostolos.Others.checksim;
import com.app.piapostolos.Views.DropDown.DragNDropListView;
import com.app.piapostolos.Views.DropDown.DragNDropSimpleAdapter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public class ApsalmodyArrangeFavouriteActivity extends BaseActivity {
    DragNDropSimpleAdapter dragDrop;
    FavouritListEntity favourite;
    Intent intent = null;
    ArrayList<Map<String, Object>> items;
    private DragNDropListView list;

    @Override // com.app.piapostolos.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) throws IOException {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main_activity__arrange_favourite);
        getWindow().addFlags(128);
        ((TextView) findViewById(R.id.textView1)).setTextSize(Float.valueOf(new checksim().checkfontsize(this)).floatValue());
        FavouritListEntity favouritListEntity = (FavouritListEntity) new SavePrefs((Activity) this, (Class<?>) FavouritListEntity.class).load();
        this.favourite = favouritListEntity;
        if (favouritListEntity != null) {
            this.intent = getIntent();
            Apsalmody.readsheetAll(this);
            this.items = new ArrayList<>();
            for (int i = 0; i < this.favourite.getFavouriteList().get(this.intent.getIntExtra("id", 0)).length; i++) {
                HashMap map = new HashMap();
                map.put("name", Apsalmody.allNames[this.favourite.getFavouriteList().get(this.intent.getIntExtra("id", 0))[i] - 1]);
                map.put(Database.COLUMN_ALARM_ID, Integer.valueOf(this.favourite.getFavouriteList().get(this.intent.getIntExtra("id", 0))[i]));
                this.items.add(map);
            }
            this.list = (DragNDropListView) findViewById(R.id.mainListView);
            DragNDropSimpleAdapter dragNDropSimpleAdapter = new DragNDropSimpleAdapter(this, this.items, R.layout.arrange_item, new String[]{"name"}, new int[]{R.id.text}, R.id.handler);
            this.dragDrop = dragNDropSimpleAdapter;
            this.list.setDragNDropAdapter(dragNDropSimpleAdapter);
        }
        findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() { // from class: com.app.piapostolos.ApsalmodyArrangeFavouriteActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                int length = ApsalmodyArrangeFavouriteActivity.this.favourite.getFavouriteList().get(ApsalmodyArrangeFavouriteActivity.this.intent.getIntExtra("id", 0)).length;
                int[] iArr = new int[length];
                for (int i2 = 0; i2 < length; i2++) {
                    iArr[i2] = ((Integer) ApsalmodyArrangeFavouriteActivity.this.items.get(ApsalmodyArrangeFavouriteActivity.this.dragDrop.getmPosition()[i2]).get(Database.COLUMN_ALARM_ID)).intValue();
                    Log.d("txt", "id=" + iArr[i2]);
                    Log.d("txt", "name=" + ApsalmodyArrangeFavouriteActivity.this.items.get(ApsalmodyArrangeFavouriteActivity.this.dragDrop.getmPosition()[i2]).get("name"));
                }
                FavouritListEntity favouritListEntity2 = (FavouritListEntity) new SavePrefs((Activity) ApsalmodyArrangeFavouriteActivity.this, (Class<?>) FavouritListEntity.class).load();
                if (favouritListEntity2 != null) {
                    Log.d("txt", "index=" + ApsalmodyArrangeFavouriteActivity.this.intent.getIntExtra("id", 0));
                    favouritListEntity2.getFavouriteList().set(ApsalmodyArrangeFavouriteActivity.this.intent.getIntExtra("id", 0), iArr);
                    new SavePrefs((Activity) ApsalmodyArrangeFavouriteActivity.this, (Class<?>) FavouritListEntity.class).save(favouritListEntity2);
                }
                ApsalmodyArrangeFavouriteActivity.this.finish();
            }
        });
    }
}
