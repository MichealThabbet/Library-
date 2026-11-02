package com.app.piapostolos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.facebook.share.internal.ShareConstants;

/* loaded from: classes.dex */
public class mangalya_options extends Activity {
    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_mangalyaa_options);
        getWindow().addFlags(128);
        findViewById(R.id.kolagy).setOnClickListener(new View.OnClickListener() { // from class: com.app.piapostolos.mangalya_options.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                mangalya_options.this.startActivity(new Intent(mangalya_options.this, (Class<?>) MainActivity_kholagy_menu.class));
            }
        });
        findViewById(R.id.agpya).setOnClickListener(new View.OnClickListener() { // from class: com.app.piapostolos.mangalya_options.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Intent intent = new Intent(mangalya_options.this, (Class<?>) AgpyaActivity.class);
                intent.putExtra(ShareConstants.MEDIA_TYPE, "agypa");
                mangalya_options.this.startActivity(intent);
            }
        });
        findViewById(R.id.sanksar).setOnClickListener(new View.OnClickListener() { // from class: com.app.piapostolos.mangalya_options.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                mangalya_options.this.startActivity(new Intent(mangalya_options.this, (Class<?>) MainActivity_sanksar.class));
            }
        });
        findViewById(R.id.katamras).setOnClickListener(new View.OnClickListener() { // from class: com.app.piapostolos.mangalya_options.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                mangalya_options.this.startActivity(new Intent(mangalya_options.this, (Class<?>) Katamaras.class));
            }
        });
        findViewById(R.id.psalmody_sanawy).setOnClickListener(new View.OnClickListener() { // from class: com.app.piapostolos.mangalya_options.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                mangalya_options.this.startActivity(new Intent(mangalya_options.this, (Class<?>) MainActivity_psalmody_sanawy_menu.class));
            }
        });
        findViewById(R.id.psalmody).setOnClickListener(new View.OnClickListener() { // from class: com.app.piapostolos.mangalya_options.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                mangalya_options.this.startActivity(new Intent(mangalya_options.this, (Class<?>) MainActivity_PsalList.class));
            }
        });
        findViewById(R.id.dallal).setOnClickListener(new View.OnClickListener() { // from class: com.app.piapostolos.mangalya_options.7
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                mangalya_options.this.startActivity(new Intent(mangalya_options.this, (Class<?>) MainActivity_dallal.class));
            }
        });
        findViewById(R.id.kandil).setOnClickListener(new View.OnClickListener() { // from class: com.app.piapostolos.mangalya_options.8
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Intent intent = new Intent(mangalya_options.this, (Class<?>) AgpyaActivity.class);
                intent.putExtra(ShareConstants.MEDIA_TYPE, "kandil");
                mangalya_options.this.startActivity(intent);
            }
        });
        findViewById(R.id.lakan).setOnClickListener(new View.OnClickListener() { // from class: com.app.piapostolos.mangalya_options.9
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Intent intent = new Intent(mangalya_options.this, (Class<?>) AgpyaActivity.class);
                intent.putExtra(ShareConstants.MEDIA_TYPE, "lakan");
                mangalya_options.this.startActivity(intent);
            }
        });
        findViewById(R.id.sagda).setOnClickListener(new View.OnClickListener() { // from class: com.app.piapostolos.mangalya_options.10
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Intent intent = new Intent(mangalya_options.this, (Class<?>) AgpyaActivity.class);
                intent.putExtra(ShareConstants.MEDIA_TYPE, "sagda");
                mangalya_options.this.startActivity(intent);
            }
        });
        findViewById(R.id.kism).setOnClickListener(new View.OnClickListener() { // from class: com.app.piapostolos.mangalya_options.11
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Intent intent = new Intent(mangalya_options.this, (Class<?>) AgpyaActivity.class);
                intent.putExtra(ShareConstants.MEDIA_TYPE, "kism");
                mangalya_options.this.startActivity(intent);
            }
        });
    }
}
