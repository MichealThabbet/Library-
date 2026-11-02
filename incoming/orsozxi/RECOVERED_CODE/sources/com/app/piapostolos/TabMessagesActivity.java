package com.app.piapostolos;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TabHost;
import android.widget.TextView;
import com.app.piapostolos.Adapters.ExpandableListAdapter;
import com.facebook.share.internal.ShareConstants;
import com.navdrawer.SimpleSideDrawer;

/* loaded from: classes.dex */
public class TabMessagesActivity extends TabActivity {
    private TabHost tabHost;

    @Override // android.app.ActivityGroup, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_tab_messages);
        getWindow().addFlags(128);
        ((TextView) findViewById(R.id.tv_header_title)).setText(R.string.s_daily_msg);
        this.tabHost = getTabHost();
        Button button = (Button) findViewById(R.id.btn_slide);
        final SimpleSideDrawer simpleSideDrawer = new SimpleSideDrawer(this);
        simpleSideDrawer.setLeftBehindContentView(R.layout.left_menu_ex);
        button.setVisibility(0);
        Slider.prepareListData(this);
        ((ExpandableListView) findViewById(R.id.lvExp)).setAdapter(new ExpandableListAdapter(this, this, Slider.listDataHeader, Slider.listDataChild));
        button.setOnClickListener(new View.OnClickListener() { // from class: com.app.piapostolos.TabMessagesActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                simpleSideDrawer.toggleLeftDrawer();
            }
        });
        TabHost.TabSpec tabSpecNewTabSpec = this.tabHost.newTabSpec(getString(R.string.bishop_rafail));
        tabSpecNewTabSpec.setIndicator(getString(R.string.bishop_rafail));
        Intent intent = new Intent(this, (Class<?>) Messages.class);
        intent.putExtra(ShareConstants.MEDIA_TYPE, 0);
        tabSpecNewTabSpec.setContent(intent);
        TabHost.TabSpec tabSpecNewTabSpec2 = this.tabHost.newTabSpec(getString(R.string.piapostolos_family));
        tabSpecNewTabSpec2.setIndicator(getString(R.string.piapostolos_family));
        Intent intent2 = new Intent(this, (Class<?>) Messages.class);
        intent2.putExtra(ShareConstants.MEDIA_TYPE, 1);
        tabSpecNewTabSpec2.setContent(intent2);
        TabHost.TabSpec tabSpecNewTabSpec3 = this.tabHost.newTabSpec(getString(R.string.church_news));
        tabSpecNewTabSpec3.setIndicator(getString(R.string.church_news));
        Intent intent3 = new Intent(this, (Class<?>) Messages.class);
        intent3.putExtra(ShareConstants.MEDIA_TYPE, 2);
        tabSpecNewTabSpec3.setContent(intent3);
        this.tabHost.addTab(tabSpecNewTabSpec3);
        this.tabHost.addTab(tabSpecNewTabSpec2);
        this.tabHost.addTab(tabSpecNewTabSpec);
        this.tabHost.setCurrentTab(2);
        for (int i = 0; i < this.tabHost.getTabWidget().getChildCount(); i++) {
            this.tabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.button_black);
            ((TextView) this.tabHost.getTabWidget().getChildTabViewAt(i).findViewById(android.R.id.title)).setTextColor(getResources().getColor(R.color.white));
        }
        this.tabHost.getTabWidget().setCurrentTab(2);
        this.tabHost.getTabWidget().getChildAt(this.tabHost.getCurrentTab()).setBackgroundResource(R.drawable.button_black_selected);
        this.tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() { // from class: com.app.piapostolos.TabMessagesActivity.2
            @Override // android.widget.TabHost.OnTabChangeListener
            public void onTabChanged(String str) {
                for (int i2 = 0; i2 < TabMessagesActivity.this.tabHost.getTabWidget().getChildCount(); i2++) {
                    TabMessagesActivity.this.tabHost.getTabWidget().getChildAt(i2).setBackgroundResource(R.drawable.button_black);
                    ((TextView) TabMessagesActivity.this.tabHost.getTabWidget().getChildTabViewAt(i2).findViewById(android.R.id.title)).setTextColor(TabMessagesActivity.this.getResources().getColor(R.color.white));
                }
                TabMessagesActivity.this.tabHost.getTabWidget().getChildAt(TabMessagesActivity.this.tabHost.getCurrentTab()).setBackgroundResource(R.drawable.button_black_selected);
            }
        });
    }

    private static View createTabView(Context context, String str) {
        View viewInflate = LayoutInflater.from(context).inflate(R.layout.saydna_tab, (ViewGroup) null);
        ((TextView) viewInflate.findViewById(R.id.site_title)).setText(str);
        return viewInflate;
    }
}
