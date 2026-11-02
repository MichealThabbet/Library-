package com.app.piapostolos;

import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;
import com.app.piapostolos.Adapters.ExpandableListAdapter;
import com.app.piapostolos.Others.AlertDialogManager;
import com.app.piapostolos.Others.ConnectionDetector;
import com.app.piapostolos.Others.checksim;
import com.facebook.share.internal.ShareConstants;
import com.navdrawer.SimpleSideDrawer;

/* loaded from: classes.dex */
public class Radiooptions extends BaseActivity {
    ConnectionDetector cd;
    private GestureDetector gestureDetector;
    Boolean isInternetPresent = false;

    @Override // com.app.piapostolos.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_followus);
        getWindow().addFlags(128);
        new checksim();
        getWindow().setBackgroundDrawableResource(R.drawable.background);
        ((TextView) findViewById(R.id.tv_header_title)).setText(R.string.radio_orso);
        Button button = (Button) findViewById(R.id.btn_slide);
        final SimpleSideDrawer simpleSideDrawer = new SimpleSideDrawer(this);
        simpleSideDrawer.setLeftBehindContentView(R.layout.left_menu_ex);
        button.setVisibility(0);
        Slider.prepareListData(this);
        ((ExpandableListView) findViewById(R.id.lvExp)).setAdapter(new ExpandableListAdapter(this, this, Slider.listDataHeader, Slider.listDataChild));
        button.setOnClickListener(new View.OnClickListener() { // from class: com.app.piapostolos.Radiooptions.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                simpleSideDrawer.toggleLeftDrawer();
            }
        });
        findViewById(R.id.btn_orders).setOnClickListener(new View.OnClickListener() { // from class: com.app.piapostolos.Radiooptions.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (Radiooptions.this.checkinternet()) {
                    Intent intent = new Intent(Radiooptions.this, (Class<?>) SendEmailActivity.class);
                    intent.putExtra("email", "radio@orsozoxi.net");
                    intent.putExtra("preact", "المقترحات");
                    Radiooptions.this.startActivity(intent);
                    return;
                }
                if (Radiooptions.this.checkinternet()) {
                    return;
                }
                AlertDialogManager alertDialogManager = new AlertDialogManager();
                Radiooptions radiooptions = Radiooptions.this;
                alertDialogManager.showAlertDialog(radiooptions, "No Internet Connection", radiooptions.getString(R.string.connect_internet), false);
            }
        });
        findViewById(R.id.btn_list).setOnClickListener(new View.OnClickListener() { // from class: com.app.piapostolos.Radiooptions.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (Radiooptions.this.checkinternet()) {
                    Radiooptions.this.startActivity(new Intent(Radiooptions.this, (Class<?>) MainActivityRadio.class));
                } else {
                    if (Radiooptions.this.checkinternet()) {
                        return;
                    }
                    AlertDialogManager alertDialogManager = new AlertDialogManager();
                    Radiooptions radiooptions = Radiooptions.this;
                    alertDialogManager.showAlertDialog(radiooptions, "No Internet Connection", radiooptions.getString(R.string.connect_internet), false);
                }
            }
        });
        findViewById(R.id.btn_times).setOnClickListener(new View.OnClickListener() { // from class: com.app.piapostolos.Radiooptions.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (Radiooptions.this.checkinternet()) {
                    Intent intent = new Intent(Radiooptions.this, (Class<?>) zweilawebsite.class);
                    intent.putExtra("url", "https://orsozoxi.net/radio/radio_sch.html");
                    intent.putExtra(ShareConstants.MEDIA_TYPE, "old");
                    intent.putExtra("preact", Radiooptions.this.getString(R.string.radio_program));
                    Radiooptions.this.startActivity(intent);
                    return;
                }
                if (Radiooptions.this.checkinternet()) {
                    return;
                }
                AlertDialogManager alertDialogManager = new AlertDialogManager();
                Radiooptions radiooptions = Radiooptions.this;
                alertDialogManager.showAlertDialog(radiooptions, "No Internet Connection", radiooptions.getString(R.string.connect_internet), false);
            }
        });
    }

    public boolean checkinternet() {
        ConnectionDetector connectionDetector = new ConnectionDetector(getApplicationContext());
        this.cd = connectionDetector;
        Boolean boolValueOf = Boolean.valueOf(connectionDetector.isConnectingToInternet());
        this.isInternetPresent = boolValueOf;
        return boolValueOf.booleanValue();
    }
}
