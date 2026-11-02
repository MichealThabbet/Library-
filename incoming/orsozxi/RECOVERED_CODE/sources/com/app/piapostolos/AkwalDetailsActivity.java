package com.app.piapostolos;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.GestureDetector;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.core.view.InputDeviceCompat;
import androidx.core.view.ViewCompat;
import com.app.piapostolos.Adapters.ExpandableListAdapter;
import com.app.piapostolos.Beans.WhiteBackgroundActivition;
import com.app.piapostolos.Others.CommonUtilities;
import com.app.piapostolos.Others.SavePrefs;
import com.app.piapostolos.Others.Util;
import com.app.piapostolos.Others.checksim;
import com.app.piapostolos.Views.MenuDateView;
import com.github.clans.fab.FloatingActionMenu;
import com.navdrawer.SimpleSideDrawer;
import com.squareup.picasso.Picasso;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import it.piapostolos.android.pinotes.utils.ConstantsBase;
import java.io.File;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.Callable;

/* loaded from: classes.dex */
public class AkwalDetailsActivity extends BaseActivity implements View.OnClickListener {
    private View content;
    TextView feedburner;
    private GestureDetector gestureDetector;
    TextView link;
    private FloatingActionMenu mHomeFloatingActionMenu;
    private MenuDateView menuDateView;
    TextView pubDate;
    TextView title;
    String tit = "";
    String desc = "";
    String pubda = "";
    String gui = "";
    String url = "";
    String link2 = "";
    private final int CONTEXT_MENU_ID = 1;
    private boolean isWhite = false;

    @Override // com.app.piapostolos.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_details_akwal);
        try {
            Log.i("BEFORE", "<<------------- Before SetAdapter-------------->>");
            Log.i("BEFORE", "<<------------- Before SetAdapter11-------------->>");
            this.title = (TextView) findViewById(R.id.titlerow2);
            this.pubDate = (TextView) findViewById(R.id.daterow2);
            Log.i("BEFORE", "<<------------- Before SetAdapter11-------------->>");
            this.feedburner = (TextView) findViewById(R.id.contentrow2);
            this.link = (TextView) findViewById(R.id.TextView01);
            getWindow().addFlags(128);
            checksim checksimVar = new checksim();
            WhiteBackgroundActivition whiteBackgroundActivition = (WhiteBackgroundActivition) new SavePrefs((Activity) this, (Class<?>) WhiteBackgroundActivition.class).load();
            if (whiteBackgroundActivition == null || !whiteBackgroundActivition.isWhiteBackground()) {
                getWindow().setBackgroundDrawableResource(R.drawable.background);
                this.feedburner.setTextColor(-1);
                this.title.setTextColor(Color.parseColor("#FF6600"));
                this.pubDate.setTextColor(InputDeviceCompat.SOURCE_ANY);
                this.link.setTextColor(InputDeviceCompat.SOURCE_ANY);
            } else {
                this.isWhite = true;
                this.feedburner.setTextColor(ViewCompat.MEASURED_STATE_MASK);
                this.title.setTextColor(ViewCompat.MEASURED_STATE_MASK);
                this.pubDate.setTextColor(-16776961);
                this.link.setTextColor(-16776961);
            }
            initFloating();
            Button button = (Button) findViewById(R.id.btn_slide);
            final SimpleSideDrawer simpleSideDrawer = new SimpleSideDrawer(this);
            simpleSideDrawer.setLeftBehindContentView(R.layout.left_menu_ex);
            button.setVisibility(0);
            Slider.prepareListData(this);
            ((ExpandableListView) findViewById(R.id.lvExp)).setAdapter(new ExpandableListAdapter(this, this, Slider.listDataHeader, Slider.listDataChild));
            button.setOnClickListener(new View.OnClickListener() { // from class: com.app.piapostolos.AkwalDetailsActivity.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    simpleSideDrawer.toggleLeftDrawer();
                }
            });
            this.feedburner.setTextSize(Float.valueOf(checksimVar.checkfontsize(getApplicationContext())).floatValue());
            this.title.setTextSize(Float.valueOf(checksimVar.checkfontsize(getApplicationContext())).floatValue() + 5.0f);
            this.link.setTextSize(Float.valueOf(checksimVar.checkfontsize(getApplicationContext())).floatValue());
            this.pubDate.setTextSize(Float.valueOf(checksimVar.checkfontsize(getApplicationContext())).floatValue());
            Log.i("BEFORE", "<<------------- Before SetAdapter11-------------->>");
            Intent intent = getIntent();
            Log.i("BEFORE", "<<------------- Before SetAdapter111-------------->>");
            this.tit = intent.getStringExtra("title");
            this.pubda = intent.getStringExtra("pubDate");
            this.desc = intent.getStringExtra("feedburner");
            this.gui = intent.getStringExtra("guid");
            this.url = intent.getStringExtra("url");
            this.link2 = intent.getStringExtra("linkk");
            TextView textView = (TextView) findViewById(R.id.tv_header_title);
            if (this.url.equals("https://services.invalid/unconfigured")) {
                setTitle(getString(R.string.taks_church));
                textView.setText(getString(R.string.taks_church));
            } else if (this.url.equals("https://services.invalid/unconfigured")) {
                textView.setText(getString(R.string.eids_cards));
            } else {
                setTitle(getString(R.string.akwal_title));
                textView.setText(getString(R.string.akwal_title));
            }
            Log.i("BEFORE", "<<------------- Before SetAdapter1111-------------->>");
            this.title.setText(this.tit);
            this.link.setText(this.link2);
            this.link.setOnClickListener(new View.OnClickListener() { // from class: com.app.piapostolos.AkwalDetailsActivity.2
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (TextUtils.isEmpty(AkwalDetailsActivity.this.link2)) {
                        return;
                    }
                    AkwalDetailsActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(AkwalDetailsActivity.this.link2)));
                }
            });
            String strSubstring = this.pubda.substring(0, 16);
            try {
                Date date = new SimpleDateFormat("EEE, dd MMM yyyy").parse(strSubstring);
                this.pubDate.setText(new SimpleDateFormat("EEE, dd MMM yyyy", new Locale("ar")).format(date));
            } catch (ParseException unused) {
                this.pubDate.setText(strSubstring);
            }
            this.feedburner.setText(this.desc);
            this.feedburner.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.app.piapostolos.AkwalDetailsActivity.3
                @Override // android.view.View.OnLongClickListener
                public boolean onLongClick(View view) {
                    AkwalDetailsActivity.this.showDialog(1);
                    return false;
                }
            });
            Log.i("BEFORE", "<<------------- Before SetAdapter1-------------->>");
            Picasso.with(this).load(this.gui).fit().placeholder(R.drawable.thumb_m).into((ImageView) findViewById(R.id.imageView11));
        } catch (Exception unused2) {
            Log.i("BEFORE", "<<------------- Before SetAdapter2-------------->>");
            Log.e("Error", "Loading exception");
        }
        getResources();
    }

    public void shareImage() {
        if (!this.isWhite) {
            findViewById(R.id.scrollView1).setBackgroundResource(R.drawable.background);
        }
        Observable.fromCallable(new Callable() { // from class: com.app.piapostolos.-$$Lambda$AkwalDetailsActivity$nN13upAfDbDaVSfUD__F_HQJHMs
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return this.f$0.lambda$shareImage$0$AkwalDetailsActivity();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.app.piapostolos.-$$Lambda$AkwalDetailsActivity$FUhZZaGZCD2qgNjwqtsHQLuwtXY
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.lambda$shareImage$1$AkwalDetailsActivity((File) obj);
            }
        });
    }

    public /* synthetic */ File lambda$shareImage$0$AkwalDetailsActivity() throws Exception {
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(findViewById(R.id.scrollView1).getWidth(), findViewById(R.id.scrollView1).getHeight(), Bitmap.Config.ARGB_8888);
        findViewById(R.id.scrollView1).draw(new Canvas(bitmapCreateBitmap));
        try {
            File file = new File(CommonUtilities.getStoragePath(this) + "/PiApostolos");
            if (!file.exists()) {
                file.mkdirs();
            }
            File file2 = new File(file, "" + System.currentTimeMillis() + ConstantsBase.MIME_TYPE_SKETCH_EXT);
            FileOutputStream fileOutputStream = new FileOutputStream(file2);
            bitmapCreateBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            return file2;
        } catch (Exception unused) {
            return null;
        }
    }

    public /* synthetic */ void lambda$shareImage$1$AkwalDetailsActivity(File file) throws Exception {
        if (file == null) {
            return;
        }
        Uri uriForFile = FileProvider.getUriForFile(this, getPackageName(), file);
        Intent intent = new Intent("android.intent.action.SEND");
        intent.putExtra("android.intent.extra.TEXT", this.tit);
        intent.setType(ConstantsBase.MIME_TYPE_SKETCH);
        intent.setFlags(1);
        intent.putExtra("android.intent.extra.STREAM", uriForFile);
        startActivity(Intent.createChooser(intent, getString(R.string.app_name)));
    }

    private void initFloating() {
        FloatingActionMenu floatingActionMenu = (FloatingActionMenu) findViewById(R.id.menu_options);
        this.mHomeFloatingActionMenu = floatingActionMenu;
        floatingActionMenu.setIconAnimated(false);
        this.mHomeFloatingActionMenu.setClosedOnTouchOutside(true);
        this.mHomeFloatingActionMenu.setOnMenuButtonClickListener(new View.OnClickListener() { // from class: com.app.piapostolos.AkwalDetailsActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                AkwalDetailsActivity.this.mHomeFloatingActionMenu.toggle(true);
            }
        });
        findViewById(R.id.fb_copy).setOnClickListener(this);
        findViewById(R.id.fb_share_img).setOnClickListener(this);
        findViewById(R.id.fb_share).setOnClickListener(this);
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        if (this.mHomeFloatingActionMenu.isOpened()) {
            this.mHomeFloatingActionMenu.toggle(true);
        } else {
            super.onBackPressed();
        }
    }

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 30) {
            shareImage();
            return true;
        }
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") == 0) {
                Log.v(Util.TAG, "Permission is granted");
                shareImage();
                return true;
            }
            Log.v(Util.TAG, "Permission is revoked");
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE"}, 1);
            return false;
        }
        Log.v(Util.TAG, "Permission is granted");
        shareImage();
        return true;
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (iArr[0] == 0) {
            Log.v(Util.TAG, "Permission: " + strArr[0] + "was " + iArr[0]);
            shareImage();
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fb_copy /* 2131296647 */:
                this.mHomeFloatingActionMenu.toggle(false);
                app.copyText(this, ((Object) this.title.getText()) + "\n" + ((Object) this.feedburner.getText()));
                break;
            case R.id.fb_share /* 2131296656 */:
                this.mHomeFloatingActionMenu.toggle(false);
                app.shareText(this, ((Object) this.title.getText()) + "\n" + ((Object) this.feedburner.getText()));
                break;
            case R.id.fb_share_img /* 2131296657 */:
                this.mHomeFloatingActionMenu.toggle(false);
                isStoragePermissionGranted();
                break;
        }
    }
}
