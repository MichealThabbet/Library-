package com.app.piapostolos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import com.app.piapostolos.Others.AlertDialogManager;
import com.app.piapostolos.Others.ConnectionDetector;
import com.app.piapostolos.Others.checksim;
import com.google.android.gms.common.GoogleApiAvailability;

/* loaded from: classes.dex */
public class MainActivityGCM extends BaseActivity {
    public static String email;
    public static String name;
    AlertDialogManager alert = new AlertDialogManager();
    ConnectionDetector cd;
    WebView lblMessage;

    @Override // com.app.piapostolos.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_gcm);
        this.lblMessage = (WebView) findViewById(R.id.lblMessage);
        getWindow().addFlags(128);
        checksim checksimVar = new checksim();
        if (!PiApostolos.isPlay_Store() && isGooglePlayServicesAvailable(this)) {
            PiApostolos.activateFireBase(this);
        }
        ((Button) findViewById(R.id.next)).setOnClickListener(new View.OnClickListener() { // from class: com.app.piapostolos.MainActivityGCM.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MainActivityGCM.this.startActivity(new Intent(MainActivityGCM.this, (Class<?>) MainActivity.class));
                MainActivityGCM.this.finish();
            }
        });
        Intent intent = getIntent();
        ImageView imageView = (ImageView) findViewById(R.id.img_logo);
        name = intent.getStringExtra("name");
        email = intent.getStringExtra("email");
        String stringExtra = intent.getStringExtra("image");
        if (email.equals("22")) {
            this.lblMessage.getSettings().setJavaScriptEnabled(true);
            this.lblMessage.setBackgroundColor(0);
            this.lblMessage.getSettings().setDefaultFontSize(Integer.valueOf(checksimVar.checkfontsize(this)).intValue());
            this.lblMessage.loadDataWithBaseURL("", "<html><head><style type=\"text/css\">body{color: #fff; }</style></head><body>" + name + "</body></html>", "text/html", "UTF-8", "");
            if (stringExtra.equals("https://www.orsozoxi.net/bprsms/images/bpr.jpg")) {
                getWindow().setBackgroundDrawableResource(R.drawable.saydna);
                imageView.setImageResource(R.drawable.saydnalogo);
            }
            if (stringExtra.equals("https://www.orsozoxi.net/gcmsms/images/orsozoxisms.jpg")) {
                getWindow().setBackgroundDrawableResource(R.drawable.orso);
                imageView.setImageResource(R.drawable.orsologo);
            }
            if (stringExtra.equals("https://www.orsozoxi.net/gcmnews/images/news.jpg")) {
                getWindow().setBackgroundDrawableResource(R.drawable.newsss);
                imageView.setImageResource(R.drawable.newsslogo);
                return;
            }
            return;
        }
        getWindow().setBackgroundDrawableResource(R.drawable.background);
    }

    public boolean isGooglePlayServicesAvailable(Activity activity) {
        return GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(activity) == 0;
    }
}
