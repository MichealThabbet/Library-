package com.app.piapostolos;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
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
public class KatamarasTafserActivity extends BaseActivity {
    private Context ctx;
    private GestureDetector gestureDetector;
    TextView tafserTextView;
    private String text = "";
    private int tafserDay = -1;
    private String title = "";

    @Override // com.app.piapostolos.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_tafser);
        TextView textView = (TextView) findViewById(R.id.tv_header_title);
        Intent intent = getIntent();
        this.tafserTextView = (TextView) findViewById(R.id.tv_tafser);
        this.title = intent.getStringExtra("title");
        checksim checksimVar = new checksim();
        this.tafserDay = intent.getIntExtra("tafserDay", -1);
        this.tafserTextView.setTextSize(Float.valueOf(checksimVar.checkfontsize(this)).floatValue());
        textView.setText(intent.getStringExtra("title"));
        getWindow().addFlags(128);
        readFile();
        this.tafserTextView.setOnClickListener(new View.OnClickListener() { // from class: com.app.piapostolos.KatamarasTafserActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                app.copyText(KatamarasTafserActivity.this, KatamarasTafserActivity.this.title + " " + KatamarasTafserActivity.this.text);
            }
        });
        WhiteBackgroundActivition whiteBackgroundActivition = (WhiteBackgroundActivition) new SavePrefs((Activity) this, (Class<?>) WhiteBackgroundActivition.class).load();
        if (whiteBackgroundActivition == null || !whiteBackgroundActivition.isWhiteBackground()) {
            getWindow().setBackgroundDrawableResource(R.drawable.background);
        } else {
            this.tafserTextView.setTextColor(ViewCompat.MEASURED_STATE_MASK);
        }
        this.ctx = this;
        Button button = (Button) findViewById(R.id.btn_slide);
        final SimpleSideDrawer simpleSideDrawer = new SimpleSideDrawer(this);
        simpleSideDrawer.setLeftBehindContentView(R.layout.left_menu_ex);
        button.setVisibility(0);
        Slider.prepareListData(this);
        ((ExpandableListView) findViewById(R.id.lvExp)).setAdapter(new ExpandableListAdapter(this, this, Slider.listDataHeader, Slider.listDataChild));
        button.setOnClickListener(new View.OnClickListener() { // from class: com.app.piapostolos.KatamarasTafserActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                simpleSideDrawer.toggleLeftDrawer();
            }
        });
    }

    private void readFile() {
        try {
            String str = new String(app.decodeFile2(this, "piapostolos", "katamars/KatamrasSoomKeberTafser/" + this.tafserDay + ".txt"));
            this.text = str;
            String[] strArrSplit = str.split("\n");
            String str2 = "";
            for (int i = 0; i < strArrSplit.length; i++) {
                if (strArrSplit[i] == null) {
                    str2 = str2 + "<br>";
                } else if (strArrSplit[i].contains("*") && !strArrSplit[i].contains("**")) {
                    str2 = str2 + "<font color=\"#FF0000\">" + strArrSplit[i] + "</font><br>";
                } else if (strArrSplit[i].contains("**")) {
                    str2 = str2 + "<font color=\"#EE7429\">" + strArrSplit[i] + "</font><br>";
                } else {
                    str2 = str2 + strArrSplit[i] + "<br>";
                }
            }
            this.tafserTextView.setText(Html.fromHtml(str2));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
