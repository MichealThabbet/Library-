package com.app.orsozoxi;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.app.orsozoxi.Adapters.ExpandableListAdapter;
import com.app.orsozoxi.Beans.AlamSettings;
import com.app.orsozoxi.Beans.WhiteBackgroundActivition;
import com.app.orsozoxi.Others.SavePrefs;
import com.navdrawer.SimpleSideDrawer;

/* loaded from: classes.dex */
public class BackgroundActivationActivity extends BaseActivity {
    private AlamSettings alamSettings;
    private WhiteBackgroundActivition news;

    @Override // com.app.orsozoxi.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.background_service);
        ((TextView) findViewById(R.id.tv_header_title)).setText(R.string.s_background);
        Button button = (Button) findViewById(R.id.btn_slide);
        final SimpleSideDrawer simpleSideDrawer = new SimpleSideDrawer(this);
        simpleSideDrawer.setLeftBehindContentView(R.layout.left_menu_ex);
        button.setVisibility(0);
        Slider.prepareListData(this);
        ((ExpandableListView) findViewById(R.id.lvExp)).setAdapter(new ExpandableListAdapter(this, this, Slider.listDataHeader, Slider.listDataChild));
        button.setOnClickListener(new View.OnClickListener() { // from class: com.app.orsozoxi.BackgroundActivationActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                simpleSideDrawer.toggleLeftDrawer();
            }
        });
        this.news = new WhiteBackgroundActivition();
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radat);
        final SavePrefs savePrefs = new SavePrefs((Activity) this, (Class<?>) WhiteBackgroundActivition.class);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() { // from class: com.app.orsozoxi.BackgroundActivationActivity.2
            @Override // android.widget.RadioGroup.OnCheckedChangeListener
            public void onCheckedChanged(RadioGroup radioGroup2, int i) {
                if (i == R.id.yes) {
                    BackgroundActivationActivity.this.news.setWhiteBackground(true);
                    savePrefs.save(BackgroundActivationActivity.this.news);
                } else {
                    BackgroundActivationActivity.this.news.setWhiteBackground(false);
                    savePrefs.save(BackgroundActivationActivity.this.news);
                }
            }
        });
        WhiteBackgroundActivition whiteBackgroundActivition = (WhiteBackgroundActivition) savePrefs.load();
        if (whiteBackgroundActivition != null) {
            if (whiteBackgroundActivition.isWhiteBackground()) {
                radioGroup.check(R.id.yes);
                return;
            } else {
                radioGroup.check(R.id.no);
                return;
            }
        }
        radioGroup.check(R.id.no);
        WhiteBackgroundActivition whiteBackgroundActivition2 = new WhiteBackgroundActivition();
        whiteBackgroundActivition2.setWhiteBackground(false);
        savePrefs.save(whiteBackgroundActivition2);
    }

    public void cancelButtonAction(View view) {
        finish();
    }
}
