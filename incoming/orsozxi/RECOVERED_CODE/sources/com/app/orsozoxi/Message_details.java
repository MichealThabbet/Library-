package com.app.orsozoxi;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import com.app.orsozoxi.Adapters.ExpandableListAdapter;
import com.app.orsozoxi.Others.CommonUtilities;
import com.app.orsozoxi.Others.Util;
import com.app.orsozoxi.Others.checksim;
import com.facebook.share.internal.ShareConstants;
import com.github.clans.fab.FloatingActionMenu;
import com.navdrawer.SimpleSideDrawer;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import it.orsozoxi.android.orsonotes.utils.ConstantsBase;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Map;
import java.util.concurrent.Callable;
import org.jsoup.Jsoup;

/* loaded from: classes.dex */
public class Message_details extends BaseActivity implements View.OnClickListener {
    Map<String, String> actionsMap;
    private GestureDetector gestureDetector;
    String link;
    String linkDescription;
    String linkName;
    private FloatingActionMenu mHomeFloatingActionMenu;
    MediaPlayer mp;
    WebView msg;
    String picture;
    private final int MENU_ITEM_8_ACTION = 8;
    String message = "";

    @Override // com.app.orsozoxi.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_message_details);
        ((TextView) findViewById(R.id.tv_header_title)).setText(R.string.s_daily_msg);
        initFloating();
        Button button = (Button) findViewById(R.id.btn_slide);
        final SimpleSideDrawer simpleSideDrawer = new SimpleSideDrawer(this);
        simpleSideDrawer.setLeftBehindContentView(R.layout.left_menu_ex);
        button.setVisibility(0);
        Slider.prepareListData(this);
        ((ExpandableListView) findViewById(R.id.lvExp)).setAdapter(new ExpandableListAdapter(this, this, Slider.listDataHeader, Slider.listDataChild));
        button.setOnClickListener(new View.OnClickListener() { // from class: com.app.orsozoxi.Message_details.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                simpleSideDrawer.toggleLeftDrawer();
            }
        });
        Intent intent = getIntent();
        this.msg = (WebView) findViewById(R.id.tv_messages);
        getWindow().addFlags(128);
        checksim checksimVar = new checksim();
        ScrollView scrollView = (ScrollView) findViewById(R.id.scrollView1);
        int intExtra = intent.getIntExtra(ShareConstants.MEDIA_TYPE, 0);
        if (intExtra == 0) {
            scrollView.setBackgroundResource(R.drawable.saydna);
        } else if (intExtra == 1) {
            scrollView.setBackgroundResource(R.drawable.orso);
        } else if (intExtra == 2) {
            scrollView.setBackgroundResource(R.drawable.newsss);
        }
        this.message = intent.getStringExtra("msg");
        this.msg.getSettings().setJavaScriptEnabled(true);
        this.msg.setBackgroundColor(0);
        this.msg.getSettings().setDefaultFontSize(Integer.valueOf(checksimVar.checkfontsize(this)).intValue());
        this.msg.loadDataWithBaseURL("", "<html><head><style type=\"text/css\">body{color: #fff; }</style></head><body>" + this.message + "</body></html>", "text/html", "UTF-8", "");
    }

    public void share() {
        Observable.fromCallable(new Callable() { // from class: com.app.orsozoxi.-$$Lambda$Message_details$zpMbwVjcVoSFhatTqOGReeC4MEg
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return this.f$0.lambda$share$0$Message_details();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.app.orsozoxi.-$$Lambda$Message_details$1HKiZ0ySspvLX7X8YiYshZh4bqo
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.lambda$share$1$Message_details((File) obj);
            }
        });
    }

    public /* synthetic */ File lambda$share$0$Message_details() throws Exception {
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(findViewById(R.id.scrollView1).getWidth(), findViewById(R.id.scrollView1).getHeight(), Bitmap.Config.ARGB_8888);
        findViewById(R.id.scrollView1).draw(new Canvas(bitmapCreateBitmap));
        try {
            File file = new File(CommonUtilities.getStoragePath(this) + "/Orsozoxi");
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

    public /* synthetic */ void lambda$share$1$Message_details(File file) throws Exception {
        if (file == null) {
            return;
        }
        Uri uriForFile = FileProvider.getUriForFile(this, getPackageName(), file);
        Intent intent = new Intent("android.intent.action.SEND");
        intent.putExtra("android.intent.extra.TEXT", getString(R.string.from_daily_msg));
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
        this.mHomeFloatingActionMenu.setOnMenuButtonClickListener(new View.OnClickListener() { // from class: com.app.orsozoxi.Message_details.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Message_details.this.mHomeFloatingActionMenu.toggle(true);
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

    public static String html2text(String str) {
        return Jsoup.parse(str).text();
    }

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 30) {
            share();
            return true;
        }
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") == 0) {
                Log.v(Util.TAG, "Permission is granted");
                share();
                return true;
            }
            Log.v(Util.TAG, "Permission is revoked");
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 1);
            return false;
        }
        Log.v(Util.TAG, "Permission is granted");
        share();
        return true;
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (iArr[0] == 0) {
            Log.v(Util.TAG, "Permission: " + strArr[0] + "was " + iArr[0]);
            share();
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fb_copy /* 2131296647 */:
                this.mHomeFloatingActionMenu.toggle(false);
                app.copyText(this, html2text(this.message).toString());
                break;
            case R.id.fb_share /* 2131296656 */:
                this.mHomeFloatingActionMenu.toggle(false);
                app.shareText(this, html2text(this.message).toString());
                break;
            case R.id.fb_share_img /* 2131296657 */:
                this.mHomeFloatingActionMenu.toggle(false);
                isStoragePermissionGranted();
                break;
        }
    }
}
