package com.app.piapostolos;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.app.piapostolos.Adapters.ExpandableListAdapter;
import com.app.piapostolos.Beans.AlamSettings;
import com.app.piapostolos.Beans.NewsActivition;
import com.app.piapostolos.Others.SavePrefs;
import com.navdrawer.SimpleSideDrawer;

/* loaded from: classes.dex */
public class NewsActivationActivity extends BaseActivity {
    private AlamSettings alamSettings;
    private NewsActivition news;

    @Override // com.app.piapostolos.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.news_service);
        ((TextView) findViewById(R.id.tv_header_title)).setText(R.string.receive_news);
        Button button = (Button) findViewById(R.id.btn_slide);
        final SimpleSideDrawer simpleSideDrawer = new SimpleSideDrawer(this);
        simpleSideDrawer.setLeftBehindContentView(R.layout.left_menu_ex);
        button.setVisibility(0);
        Slider.prepareListData(this);
        ((ExpandableListView) findViewById(R.id.lvExp)).setAdapter(new ExpandableListAdapter(this, this, Slider.listDataHeader, Slider.listDataChild));
        button.setOnClickListener(new View.OnClickListener() { // from class: com.app.piapostolos.NewsActivationActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                simpleSideDrawer.toggleLeftDrawer();
            }
        });
        this.news = new NewsActivition();
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radat);
        final SavePrefs savePrefs = new SavePrefs((Activity) this, (Class<?>) NewsActivition.class);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() { // from class: com.app.piapostolos.NewsActivationActivity.2
            @Override // android.widget.RadioGroup.OnCheckedChangeListener
            public void onCheckedChanged(RadioGroup radioGroup2, int i) {
                if (i == R.id.yes) {
                    NewsActivationActivity.this.news.setNewsActivated(true);
                    savePrefs.save(NewsActivationActivity.this.news);
                } else {
                    NewsActivationActivity.this.news.setNewsActivated(false);
                    savePrefs.save(NewsActivationActivity.this.news);
                }
            }
        });
        NewsActivition newsActivition = (NewsActivition) savePrefs.load();
        if (newsActivition != null) {
            if (newsActivition.isNewsActivated()) {
                radioGroup.check(R.id.yes);
                return;
            } else {
                radioGroup.check(R.id.no);
                return;
            }
        }
        radioGroup.check(R.id.no);
        NewsActivition newsActivition2 = new NewsActivition();
        newsActivition2.setNewsActivated(false);
        savePrefs.save(newsActivition2);
    }

    public void cancelButtonAction(View view) {
        finish();
    }
}
