package com.app.piapostolos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.app.piapostolos.Others.ConnectionDetector;
import com.app.piapostolos.Youtube.MainActivityYoutube;
import com.facebook.share.internal.ShareConstants;
import com.google.firebase.crashlytics.internal.settings.model.AppSettingsData;

/* loaded from: classes.dex */
public class gawhara_options extends Activity {
    private ConnectionDetector cd;

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_gwhara_options);
        getWindow().addFlags(128);
        findViewById(R.id.iv_history).setOnClickListener(new View.OnClickListener() { // from class: com.app.piapostolos.gawhara_options.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (PiApostolos.isPlay_Store()) {
                    Intent intent = new Intent(gawhara_options.this, (Class<?>) MainActivityYoutube.class);
                    intent.putExtra("isHistory", true);
                    gawhara_options.this.startActivity(intent);
                } else {
                    Intent intent2 = new Intent(gawhara_options.this, (Class<?>) zweilawebsite.class);
                    intent2.putExtra("url", "https://www.youtube.com/channel/UCAga3fiKOaUy0xVAl2f856Q");
                    intent2.putExtra("preact", "برنامج H120");
                    intent2.putExtra(ShareConstants.MEDIA_TYPE, AppSettingsData.STATUS_NEW);
                    gawhara_options.this.startActivity(intent2);
                }
            }
        });
        findViewById(R.id.taks).setOnClickListener(new View.OnClickListener() { // from class: com.app.piapostolos.gawhara_options.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Intent intent = new Intent(gawhara_options.this, (Class<?>) AgpyaActivity.class);
                intent.putExtra(ShareConstants.MEDIA_TYPE, "taks");
                gawhara_options.this.startActivity(intent);
            }
        });
        findViewById(R.id.akwal).setOnClickListener(new View.OnClickListener() { // from class: com.app.piapostolos.gawhara_options.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Intent intent = new Intent(gawhara_options.this, (Class<?>) AkwalActivity.class);
                intent.putExtra("url", "https://www.orsozoxi.net/archives/category/konuz/akwaal/feed");
                gawhara_options.this.startActivity(intent);
            }
        });
        findViewById(R.id.iv_tip_in_word).setOnClickListener(new View.OnClickListener() { // from class: com.app.piapostolos.gawhara_options.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                gawhara_options.this.startActivity(new Intent(gawhara_options.this, (Class<?>) TipInWordActivity.class));
            }
        });
        findViewById(R.id.trueayah).setOnClickListener(new View.OnClickListener() { // from class: com.app.piapostolos.gawhara_options.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Intent intent = new Intent(gawhara_options.this, (Class<?>) MainActivitysite_aya3t.class);
                intent.putExtra("url", "https://orsozoxi.net/archives/category/trueaya/feed");
                gawhara_options.this.startActivity(intent);
            }
        });
        findViewById(R.id.mdaya7).setOnClickListener(new View.OnClickListener() { // from class: com.app.piapostolos.gawhara_options.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                gawhara_options.this.startActivity(new Intent(gawhara_options.this, (Class<?>) MainActivity_mdaya7.class));
            }
        });
    }
}
