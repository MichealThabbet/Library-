package com.app.orsozoxi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import com.app.orsozoxi.Others.checksim;

/* loaded from: classes.dex */
public class Bible_options extends Activity {
    @Override // android.app.Activity
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_bible_options);
        getWindow().addFlags(128);
        new checksim();
        findViewById(R.id.bible_new).setOnClickListener(new View.OnClickListener() { // from class: com.app.orsozoxi.Bible_options.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Intent intent = new Intent(Bible_options.this, (Class<?>) MainActivity_Bible.class);
                intent.putExtra("isOld", false);
                Bible_options.this.startActivity(intent);
            }
        });
        findViewById(R.id.bible_old).setOnClickListener(new View.OnClickListener() { // from class: com.app.orsozoxi.Bible_options.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Intent intent = new Intent(Bible_options.this, (Class<?>) MainActivity_Bible.class);
                intent.putExtra("isOld", true);
                Bible_options.this.startActivity(intent);
            }
        });
        findViewById(R.id.bible_read).setOnClickListener(new View.OnClickListener() { // from class: com.app.orsozoxi.Bible_options.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Bible_options.this.startActivity(new Intent(Bible_options.this, (Class<?>) Daily_bible.class));
            }
        });
        findViewById(R.id.bible_read_two).setOnClickListener(new View.OnClickListener() { // from class: com.app.orsozoxi.Bible_options.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Bible_options.this.startActivity(new Intent(Bible_options.this, (Class<?>) Daily_bible_twoyears.class));
            }
        });
    }
}
