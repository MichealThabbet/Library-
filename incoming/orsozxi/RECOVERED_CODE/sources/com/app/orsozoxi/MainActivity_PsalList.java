package com.app.orsozoxi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.app.orsozoxi.Adapters.ExpandableListAdapter;
import com.app.orsozoxi.ApsalmodyBeans.FavouritListEntity;
import com.app.orsozoxi.ApsalmodyBeans.FavouriteNames;
import com.app.orsozoxi.ApsalmodyBeans.PsalmodyListAdapter;
import com.app.orsozoxi.Others.SavePrefs;
import com.app.orsozoxi.Views.MenuDateView;
import com.navdrawer.SimpleSideDrawer;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class MainActivity_PsalList extends BaseActivity {
    private View content;
    private GestureDetector gestureDetector;
    private ListView listViewFootballLegend;
    private MenuDateView menuDateView;
    private PsalmodyListAdapter psalomdyListAdapter;

    @Override // com.app.orsozoxi.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main_activity__psal_list);
        getWindow().addFlags(128);
        ((TextView) findViewById(R.id.tv_header_title)).setText(R.string.t_apsalomdy_kiahk);
        Button button = (Button) findViewById(R.id.btn_slide);
        final SimpleSideDrawer simpleSideDrawer = new SimpleSideDrawer(this);
        simpleSideDrawer.setLeftBehindContentView(R.layout.left_menu_ex);
        button.setVisibility(0);
        Slider.prepareListData(this);
        ((ExpandableListView) findViewById(R.id.lvExp)).setAdapter(new ExpandableListAdapter(this, this, Slider.listDataHeader, Slider.listDataChild));
        button.setOnClickListener(new View.OnClickListener() { // from class: com.app.orsozoxi.MainActivity_PsalList.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                simpleSideDrawer.toggleLeftDrawer();
            }
        });
        ListView listView = (ListView) findViewById(R.id.lv_days);
        this.listViewFootballLegend = listView;
        listView.setBackgroundColor(0);
        this.listViewFootballLegend.setCacheColorHint(0);
        this.listViewFootballLegend.setClickable(false);
        this.listViewFootballLegend.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.app.orsozoxi.MainActivity_PsalList.2
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                Intent intent = new Intent(MainActivity_PsalList.this, (Class<?>) MainActivity_absalmodyaKiahk.class);
                intent.putExtra("day", i);
                MainActivity_PsalList.this.startActivity(intent);
            }
        });
        findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() { // from class: com.app.orsozoxi.MainActivity_PsalList.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity_PsalList.this, (Class<?>) ApsalmodyMultiSelectListFavouriteActivity.class);
                intent.putExtra("isAdd", true);
                MainActivity_PsalList.this.startActivity(intent);
            }
        });
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(getString(R.string.psal_sun));
        arrayList.add(getString(R.string.psal_mon));
        arrayList.add(getString(R.string.psal_tue));
        arrayList.add(getString(R.string.psal_wed));
        arrayList.add(getString(R.string.psal_thur));
        arrayList.add(getString(R.string.psal_fri));
        arrayList.add(getString(R.string.psal_sat));
        arrayList.add(getString(R.string.t_apsalomdy_kiahk));
        FavouritListEntity favouritListEntity = (FavouritListEntity) new SavePrefs((Activity) this, (Class<?>) FavouritListEntity.class).load();
        FavouriteNames favouriteNames = (FavouriteNames) new SavePrefs((Activity) this, (Class<?>) FavouriteNames.class).load();
        if (favouritListEntity != null && favouritListEntity.getFavouriteList().size() > 0) {
            for (int i = 0; i < favouritListEntity.getFavouriteList().size(); i++) {
                if (favouriteNames != null) {
                    arrayList.add(favouriteNames.getFavouriteListsNames().get(i));
                } else {
                    arrayList.add(getString(R.string.psalmody) + " " + (i + 1));
                }
            }
        }
        if (favouritListEntity == null) {
            PsalmodyListAdapter psalmodyListAdapter = new PsalmodyListAdapter(this, arrayList, null);
            this.psalomdyListAdapter = psalmodyListAdapter;
            this.listViewFootballLegend.setAdapter((ListAdapter) psalmodyListAdapter);
        } else {
            PsalmodyListAdapter psalmodyListAdapter2 = new PsalmodyListAdapter(this, arrayList, favouritListEntity.getFavouriteList());
            this.psalomdyListAdapter = psalmodyListAdapter2;
            this.listViewFootballLegend.setAdapter((ListAdapter) psalmodyListAdapter2);
        }
        super.onResume();
    }
}
