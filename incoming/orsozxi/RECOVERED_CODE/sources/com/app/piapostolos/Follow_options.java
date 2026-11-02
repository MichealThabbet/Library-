package com.app.piapostolos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import com.app.piapostolos.Others.AlertDialogManager;
import com.app.piapostolos.Others.ConnectionDetector;
import com.app.piapostolos.Others.Constants;
import com.app.piapostolos.Others.checksim;
import com.facebook.share.internal.ShareConstants;
import com.google.firebase.crashlytics.internal.settings.model.AppSettingsData;

/* loaded from: classes.dex */
public class Follow_options extends Activity {
    private ConnectionDetector cd;

    @Override // android.app.Activity
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_follow_options);
        getWindow().addFlags(128);
        new checksim();
        final AlertDialogManager alertDialogManager = new AlertDialogManager();
        findViewById(R.id.facebook).setOnClickListener(new View.OnClickListener() { // from class: com.app.piapostolos.Follow_options.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (Follow_options.this.checkinternet()) {
                    Intent intent = new Intent(Follow_options.this, (Class<?>) zweilawebsite.class);
                    intent.putExtra("url", Constants.FACEBOOK_SHARE_LINK);
                    intent.putExtra("preact", Follow_options.this.getString(R.string.t_facebook));
                    intent.putExtra(ShareConstants.MEDIA_TYPE, AppSettingsData.STATUS_NEW);
                    Follow_options.this.startActivity(intent);
                    return;
                }
                AlertDialogManager alertDialogManager2 = alertDialogManager;
                Follow_options follow_options = Follow_options.this;
                alertDialogManager2.showAlertDialog(follow_options, follow_options.getString(R.string.no_internet), Follow_options.this.getString(R.string.fail_response), false);
            }
        });
        findViewById(R.id.twitter).setOnClickListener(new View.OnClickListener() { // from class: com.app.piapostolos.Follow_options.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (Follow_options.this.checkinternet()) {
                    Intent intent = new Intent(Follow_options.this, (Class<?>) zweilawebsite.class);
                    intent.putExtra("url", "https://mobile.twitter.com/piapostolos");
                    intent.putExtra("preact", Follow_options.this.getString(R.string.t_twitter));
                    intent.putExtra(ShareConstants.MEDIA_TYPE, AppSettingsData.STATUS_NEW);
                    Follow_options.this.startActivity(intent);
                    return;
                }
                AlertDialogManager alertDialogManager2 = alertDialogManager;
                Follow_options follow_options = Follow_options.this;
                alertDialogManager2.showAlertDialog(follow_options, follow_options.getString(R.string.no_internet), Follow_options.this.getString(R.string.fail_response), false);
            }
        });
        findViewById(R.id.web).setOnClickListener(new View.OnClickListener() { // from class: com.app.piapostolos.Follow_options.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (Follow_options.this.checkinternet()) {
                    Intent intent = new Intent(Follow_options.this, (Class<?>) zweilawebsite.class);
                    intent.putExtra("url", "https://services.invalid/unconfigured");
                    intent.putExtra("preact", Follow_options.this.getString(R.string.t_website));
                    intent.putExtra(ShareConstants.MEDIA_TYPE, AppSettingsData.STATUS_NEW);
                    Follow_options.this.startActivity(intent);
                    return;
                }
                AlertDialogManager alertDialogManager2 = alertDialogManager;
                Follow_options follow_options = Follow_options.this;
                alertDialogManager2.showAlertDialog(follow_options, follow_options.getString(R.string.no_internet), Follow_options.this.getString(R.string.fail_response), false);
            }
        });
        findViewById(R.id.raslna).setOnClickListener(new View.OnClickListener() { // from class: com.app.piapostolos.Follow_options.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (Follow_options.this.checkinternet()) {
                    Intent intent = new Intent(Follow_options.this, (Class<?>) SendEmailActivityOptions.class);
                    intent.putExtra("email", "contact@example.invalid");
                    intent.putExtra("preact", Follow_options.this.getString(R.string.t_raslna));
                    Follow_options.this.startActivity(intent);
                    return;
                }
                AlertDialogManager alertDialogManager2 = alertDialogManager;
                Follow_options follow_options = Follow_options.this;
                alertDialogManager2.showAlertDialog(follow_options, follow_options.getString(R.string.no_internet), Follow_options.this.getString(R.string.fail_response), false);
            }
        });
    }

    public boolean checkinternet() {
        ConnectionDetector connectionDetector = new ConnectionDetector(getApplicationContext());
        this.cd = connectionDetector;
        return connectionDetector.isConnectingToInternet();
    }
}
