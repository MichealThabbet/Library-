package com.app.piapostolos;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.core.view.ViewCompat;
import com.app.piapostolos.Adapters.ExpandableListAdapter;
import com.app.piapostolos.Beans.WhiteBackgroundActivition;
import com.app.piapostolos.Others.CommonUtilities;
import com.app.piapostolos.Others.SavePrefs;
import com.app.piapostolos.Others.Util;
import com.app.piapostolos.Others.checksim;
import com.navdrawer.SimpleSideDrawer;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import it.piapostolos.android.pinotes.utils.ConstantsBase;
import java.io.File;
import java.io.FileOutputStream;
import java.util.concurrent.Callable;

/* loaded from: classes.dex */
public class CardsDetailsActivity extends BaseActivity {
    private GestureDetector gestureDetector;
    DisplayImageOptions options;
    TextView title;
    String tit = "";
    String gui = "";
    int def = 0;
    private final int CONTEXT_MENU_ID = 1;

    @Override // com.app.piapostolos.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_card_details);
        this.options = new DisplayImageOptions.Builder().displayer(new RoundedBitmapDisplayer(5)).cacheInMemory().imageScaleType(ImageScaleType.EXACTLY).cacheOnDisc().showImageForEmptyUri(R.drawable.thumb).showImageOnFail(R.drawable.thumb).showStubImage(R.drawable.thumb).bitmapConfig(Bitmap.Config.RGB_565).build();
        TextView textView = (TextView) findViewById(R.id.tv_header_title);
        Button button = (Button) findViewById(R.id.btn_sharrre);
        button.setVisibility(0);
        button.setOnClickListener(new View.OnClickListener() { // from class: com.app.piapostolos.CardsDetailsActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                CardsDetailsActivity.this.isStoragePermissionGranted();
            }
        });
        textView.setText(R.string.eids_cards);
        try {
            this.title = (TextView) findViewById(R.id.titlerow2);
            Intent intent = getIntent();
            this.tit = intent.getStringExtra("title");
            this.gui = intent.getStringExtra("guid");
            this.title.setText(this.tit);
            getWindow().addFlags(128);
            new checksim();
            WhiteBackgroundActivition whiteBackgroundActivition = (WhiteBackgroundActivition) new SavePrefs((Activity) this, (Class<?>) WhiteBackgroundActivition.class).load();
            if (whiteBackgroundActivition == null || !whiteBackgroundActivition.isWhiteBackground()) {
                getWindow().setBackgroundDrawableResource(R.drawable.background);
                this.title.setTextColor(Color.parseColor("#FF6600"));
            } else {
                this.title.setTextColor(ViewCompat.MEASURED_STATE_MASK);
            }
            Button button2 = (Button) findViewById(R.id.btn_slide);
            final SimpleSideDrawer simpleSideDrawer = new SimpleSideDrawer(this);
            simpleSideDrawer.setLeftBehindContentView(R.layout.left_menu_ex);
            button2.setVisibility(0);
            Slider.prepareListData(this);
            ((ExpandableListView) findViewById(R.id.lvExp)).setAdapter(new ExpandableListAdapter(this, this, Slider.listDataHeader, Slider.listDataChild));
            button2.setOnClickListener(new View.OnClickListener() { // from class: com.app.piapostolos.CardsDetailsActivity.2
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    simpleSideDrawer.toggleLeftDrawer();
                }
            });
            ImageView imageView = (ImageView) findViewById(R.id.imageView11);
            ImageLoader.getInstance().displayImage(this.gui, imageView, this.options);
            imageView.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.app.piapostolos.CardsDetailsActivity.3
                @Override // android.view.View.OnLongClickListener
                public boolean onLongClick(View view) {
                    CardsDetailsActivity.this.showDialog(1);
                    return false;
                }
            });
        } catch (Exception unused) {
            Log.i("BEFORE", "<<------------- Before SetAdapter2-------------->>");
            Log.e("Error", "Loading exception");
        }
    }

    public void share(int i) {
        Observable.fromCallable(new Callable() { // from class: com.app.piapostolos.-$$Lambda$CardsDetailsActivity$bj1Q_mgXg2qF-cSWSSA6KAnf2yc
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return this.f$0.lambda$share$0$CardsDetailsActivity();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.app.piapostolos.-$$Lambda$CardsDetailsActivity$S9zY4GuZstONbRR7AsWJbARKou4
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.lambda$share$1$CardsDetailsActivity((File) obj);
            }
        });
    }

    public /* synthetic */ File lambda$share$0$CardsDetailsActivity() throws Exception {
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(findViewById(R.id.imageView11).getWidth(), findViewById(R.id.imageView11).getHeight(), Bitmap.Config.ARGB_8888);
        findViewById(R.id.imageView11).draw(new Canvas(bitmapCreateBitmap));
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

    public /* synthetic */ void lambda$share$1$CardsDetailsActivity(File file) throws Exception {
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

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 30) {
            share(0);
            return true;
        }
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") == 0) {
                Log.v(Util.TAG, "Permission is granted");
                share(0);
                return true;
            }
            Log.v(Util.TAG, "Permission is revoked");
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE"}, 1);
            return false;
        }
        Log.v(Util.TAG, "Permission is granted");
        share(0);
        return true;
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (iArr[0] == 0) {
            Log.v(Util.TAG, "Permission: " + strArr[0] + "was " + iArr[0]);
            share(0);
        }
    }
}
