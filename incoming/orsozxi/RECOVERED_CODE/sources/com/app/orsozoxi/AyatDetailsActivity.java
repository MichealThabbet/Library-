package com.app.orsozoxi;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;
import androidx.core.view.InputDeviceCompat;
import androidx.core.view.ViewCompat;
import com.app.orsozoxi.Adapters.ExpandableListAdapter;
import com.app.orsozoxi.Beans.WhiteBackgroundActivition;
import com.app.orsozoxi.Others.SavePrefs;
import com.app.orsozoxi.Others.checksim;
import com.app.orsozoxi.Views.MenuDateView;
import com.github.clans.fab.FloatingActionMenu;
import com.navdrawer.SimpleSideDrawer;

/* loaded from: classes.dex */
public class AyatDetailsActivity extends BaseActivity implements View.OnClickListener {
    private View content;
    TextView feedburner;
    private GestureDetector gestureDetector;
    TextView link;
    private FloatingActionMenu mHomeFloatingActionMenu;
    private MenuDateView menuDateView;
    TextView pubDate;
    TextView title;
    String tit = "";
    String desc = "";
    String pubda = "";
    String gui = "";
    String url = "";
    String link2 = "";
    private final int CONTEXT_MENU_ID = 1;

    @Override // com.app.orsozoxi.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_details_ayat);
        ((TextView) findViewById(R.id.tv_header_title)).setText(R.string.t_true_ayah);
        initFloating();
        try {
            Log.i("BEFORE", "<<------------- Before SetAdapter-------------->>");
            Log.i("BEFORE", "<<------------- Before SetAdapter11-------------->>");
            this.title = (TextView) findViewById(R.id.titlerow2);
            Log.i("BEFORE", "<<------------- Before SetAdapter11-------------->>");
            this.pubDate = (TextView) findViewById(R.id.daterow2);
            Log.i("BEFORE", "<<------------- Before SetAdapter11-------------->>");
            this.feedburner = (TextView) findViewById(R.id.contentrow2);
            this.link = (TextView) findViewById(R.id.TextView01);
            Log.i("BEFORE", "<<------------- Before SetAdapter11-------------->>");
            Intent intent = getIntent();
            Log.i("BEFORE", "<<------------- Before SetAdapter111-------------->>");
            this.tit = intent.getStringExtra("title");
            this.pubda = intent.getStringExtra("pubDate");
            this.desc = intent.getStringExtra("feedburner");
            this.url = intent.getStringExtra("url");
            this.link2 = intent.getStringExtra("linkk");
            Log.i("BEFORE", "<<------------- Before SetAdapter1111-------------->>");
            this.title.setText(this.tit);
            this.link.setText(this.link2);
            getWindow().addFlags(128);
            new checksim();
            WhiteBackgroundActivition whiteBackgroundActivition = (WhiteBackgroundActivition) new SavePrefs((Activity) this, (Class<?>) WhiteBackgroundActivition.class).load();
            if (whiteBackgroundActivition == null || !whiteBackgroundActivition.isWhiteBackground()) {
                getWindow().setBackgroundDrawableResource(R.drawable.background);
                this.feedburner.setTextColor(-1);
                this.title.setTextColor(Color.parseColor("#FF6600"));
                this.pubDate.setTextColor(InputDeviceCompat.SOURCE_ANY);
                this.link.setTextColor(InputDeviceCompat.SOURCE_ANY);
            } else {
                this.feedburner.setTextColor(ViewCompat.MEASURED_STATE_MASK);
                this.title.setTextColor(ViewCompat.MEASURED_STATE_MASK);
                this.pubDate.setTextColor(-16776961);
                this.link.setTextColor(-16776961);
            }
            Button button = (Button) findViewById(R.id.btn_slide);
            final SimpleSideDrawer simpleSideDrawer = new SimpleSideDrawer(this);
            simpleSideDrawer.setLeftBehindContentView(R.layout.left_menu_ex);
            button.setVisibility(0);
            Slider.prepareListData(this);
            ((ExpandableListView) findViewById(R.id.lvExp)).setAdapter(new ExpandableListAdapter(this, this, Slider.listDataHeader, Slider.listDataChild));
            button.setOnClickListener(new View.OnClickListener() { // from class: com.app.orsozoxi.AyatDetailsActivity.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    simpleSideDrawer.toggleLeftDrawer();
                }
            });
            this.link.setOnClickListener(new View.OnClickListener() { // from class: com.app.orsozoxi.AyatDetailsActivity.2
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    AyatDetailsActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(AyatDetailsActivity.this.link2)));
                }
            });
            this.pubDate.setText(this.pubda.substring(0, 16));
            this.feedburner.setText(this.desc);
            this.feedburner.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.app.orsozoxi.AyatDetailsActivity.3
                @Override // android.view.View.OnLongClickListener
                public boolean onLongClick(View view) {
                    AyatDetailsActivity.this.showDialog(1);
                    return false;
                }
            });
            Log.i("BEFORE", "<<------------- Before SetAdapter1-------------->>");
        } catch (Exception unused) {
            Log.i("BEFORE", "<<------------- Before SetAdapter2-------------->>");
            Log.e("Error", "Loading exception");
        }
    }

    private void initFloating() {
        FloatingActionMenu floatingActionMenu = (FloatingActionMenu) findViewById(R.id.menu_options);
        this.mHomeFloatingActionMenu = floatingActionMenu;
        floatingActionMenu.setIconAnimated(false);
        this.mHomeFloatingActionMenu.setClosedOnTouchOutside(true);
        this.mHomeFloatingActionMenu.setOnMenuButtonClickListener(new View.OnClickListener() { // from class: com.app.orsozoxi.AyatDetailsActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                AyatDetailsActivity.this.mHomeFloatingActionMenu.toggle(true);
            }
        });
        findViewById(R.id.fb_copy).setOnClickListener(this);
        findViewById(R.id.fb_share).setOnClickListener(this);
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        if (this.mHomeFloatingActionMenu.isOpened()) {
            this.mHomeFloatingActionMenu.toggle(true);
        } else {
            super.onBackPressed();
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.fb_copy) {
            this.mHomeFloatingActionMenu.toggle(false);
            app.copyText(this, ((Object) this.title.getText()) + "\n" + ((Object) this.feedburner.getText()));
            return;
        }
        if (id != R.id.fb_share) {
            return;
        }
        this.mHomeFloatingActionMenu.toggle(false);
        app.shareText(this, ((Object) this.title.getText()) + "\n" + ((Object) this.feedburner.getText()));
    }
}
