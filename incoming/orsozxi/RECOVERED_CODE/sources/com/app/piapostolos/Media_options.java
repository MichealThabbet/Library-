package com.app.piapostolos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.app.piapostolos.Others.AlertDialogManager;
import com.app.piapostolos.Others.ConnectionDetector;
import com.app.piapostolos.Youtube.MainActivityYoutube;
import com.facebook.share.internal.ShareConstants;
import com.google.firebase.crashlytics.internal.settings.model.AppSettingsData;

/* loaded from: classes.dex */
public class Media_options extends Activity {
    private ConnectionDetector cd;

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_media_options);
        getWindow().addFlags(128);
        final AlertDialogManager alertDialogManager = new AlertDialogManager();
        findViewById(R.id.radio).setOnClickListener(new View.OnClickListener() { // from class: com.app.piapostolos.Media_options.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (Media_options.this.checkinternet()) {
                    Media_options.this.startActivity(new Intent(Media_options.this, (Class<?>) NewRadioActivity.class));
                } else {
                    AlertDialogManager alertDialogManager2 = alertDialogManager;
                    Media_options media_options = Media_options.this;
                    alertDialogManager2.showAlertDialog(media_options, "No Internet Connection", media_options.getString(R.string.connect_internet), false);
                }
            }
        });
        findViewById(R.id.images).setOnClickListener(new View.OnClickListener() { // from class: com.app.piapostolos.Media_options.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Intent intent = new Intent(Media_options.this, (Class<?>) MainActivityCards.class);
                intent.putExtra("url", "https://www.orsozoxi.net/archives/category/cards/feed");
                Media_options.this.startActivity(intent);
            }
        });
        findViewById(R.id.channal).setOnClickListener(new View.OnClickListener() { // from class: com.app.piapostolos.Media_options.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (Media_options.this.checkinternet()) {
                    if (PiApostolos.isPlay_Store()) {
                        Media_options.this.startActivity(new Intent(Media_options.this, (Class<?>) MainActivityYoutube.class));
                        return;
                    }
                    Intent intent = new Intent(Media_options.this, (Class<?>) zweilawebsite.class);
                    intent.putExtra("url", "https://www.youtube.com/channel/UCU6j31zEB-Bo-1vDWlQOF2g");
                    intent.putExtra("preact", "قناة اليوتيوب");
                    intent.putExtra(ShareConstants.MEDIA_TYPE, AppSettingsData.STATUS_NEW);
                    Media_options.this.startActivity(intent);
                    return;
                }
                AlertDialogManager alertDialogManager2 = alertDialogManager;
                Media_options media_options = Media_options.this;
                alertDialogManager2.showAlertDialog(media_options, "No Internet Connection", media_options.getString(R.string.connect_internet), false);
            }
        });
        findViewById(R.id.games).setOnClickListener(new View.OnClickListener() { // from class: com.app.piapostolos.Media_options.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Media_options.this.startActivity(new Intent(Media_options.this, (Class<?>) Games.class));
            }
        });
    }

    public boolean checkinternet() {
        ConnectionDetector connectionDetector = new ConnectionDetector(getApplicationContext());
        this.cd = connectionDetector;
        return connectionDetector.isConnectingToInternet();
    }
}
