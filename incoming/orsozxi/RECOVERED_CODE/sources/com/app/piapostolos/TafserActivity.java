package com.app.piapostolos;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;
import androidx.core.view.ViewCompat;
import com.app.piapostolos.Adapters.ExpandableListAdapter;
import com.app.piapostolos.Beans.WhiteBackgroundActivition;
import com.app.piapostolos.Others.SavePrefs;
import com.app.piapostolos.Others.checksim;
import com.navdrawer.SimpleSideDrawer;

/* loaded from: classes.dex */
public class TafserActivity extends BaseActivity {
    private Context ctx;
    private GestureDetector gestureDetector;
    private String text = "";
    private String title = "";

    @Override // com.app.piapostolos.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_tafser);
        TextView textView = (TextView) findViewById(R.id.tv_header_title);
        Intent intent = getIntent();
        TextView textView2 = (TextView) findViewById(R.id.tv_tafser);
        this.title = intent.getStringExtra("title");
        checksim checksimVar = new checksim();
        this.text = intent.getStringExtra("tafser");
        textView2.setTextSize(Float.valueOf(checksimVar.checkfontsize(this)).floatValue());
        textView2.setText(intent.getStringExtra("tafser"));
        textView.setText(intent.getStringExtra("title"));
        getWindow().addFlags(128);
        textView2.setOnClickListener(new View.OnClickListener() { // from class: com.app.piapostolos.TafserActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                app.copyText(TafserActivity.this, TafserActivity.this.title + " " + TafserActivity.this.text);
            }
        });
        WhiteBackgroundActivition whiteBackgroundActivition = (WhiteBackgroundActivition) new SavePrefs((Activity) this, (Class<?>) WhiteBackgroundActivition.class).load();
        if (whiteBackgroundActivition == null || !whiteBackgroundActivition.isWhiteBackground()) {
            getWindow().setBackgroundDrawableResource(R.drawable.background);
        } else {
            textView2.setTextColor(ViewCompat.MEASURED_STATE_MASK);
        }
        this.ctx = this;
        Button button = (Button) findViewById(R.id.btn_slide);
        final SimpleSideDrawer simpleSideDrawer = new SimpleSideDrawer(this);
        simpleSideDrawer.setLeftBehindContentView(R.layout.left_menu_ex);
        button.setVisibility(0);
        Slider.prepareListData(this);
        ((ExpandableListView) findViewById(R.id.lvExp)).setAdapter(new ExpandableListAdapter(this, this, Slider.listDataHeader, Slider.listDataChild));
        button.setOnClickListener(new View.OnClickListener() { // from class: com.app.piapostolos.TafserActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                simpleSideDrawer.toggleLeftDrawer();
            }
        });
    }
}
