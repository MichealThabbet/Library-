package com.app.orsozoxi;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.app.ActivityCompat;
import com.app.orsozoxi.Adapters.ExpandableListAdapter;
import com.app.orsozoxi.Others.CommonUtilities;
import com.app.orsozoxi.Others.ConnectionDetector;
import com.app.orsozoxi.Others.Util;
import com.facebook.share.internal.ShareConstants;
import com.huawei.hms.push.constant.RemoteMessageConst;
import com.navdrawer.SimpleSideDrawer;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.concurrent.Callable;
import org.apache.http.protocol.HTTP;

/* loaded from: classes.dex */
public class MainActivity_kholagy_menu extends BaseActivity {
    private ConnectionDetector cd;
    File fileWithinMyDirHTML;
    private ProgressDialog mProgressDialog;
    String signFilePath;

    @Override // com.app.orsozoxi.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().addFlags(128);
        setContentView(R.layout.activity_kholagy_index);
        ((TextView) findViewById(R.id.tv_header_title)).setText(R.string.t_kholagy);
        Button button = (Button) findViewById(R.id.btn_slide);
        final SimpleSideDrawer simpleSideDrawer = new SimpleSideDrawer(this);
        simpleSideDrawer.setLeftBehindContentView(R.layout.left_menu_ex);
        button.setVisibility(0);
        Slider.prepareListData(this);
        ((ExpandableListView) findViewById(R.id.lvExp)).setAdapter(new ExpandableListAdapter(this, this, Slider.listDataHeader, Slider.listDataChild));
        button.setOnClickListener(new View.OnClickListener() { // from class: com.app.orsozoxi.MainActivity_kholagy_menu.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                simpleSideDrawer.toggleLeftDrawer();
            }
        });
        ShowAfter5Message();
        findViewById(R.id.btn_basely).setOnClickListener(new View.OnClickListener() { // from class: com.app.orsozoxi.MainActivity_kholagy_menu.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity_kholagy_menu.this, (Class<?>) MainActivity_kholagy.class);
                intent.putExtra(ShareConstants.MEDIA_TYPE, "basely");
                MainActivity_kholagy_menu.this.startActivity(intent);
            }
        });
        findViewById(R.id.btn_hamal).setOnClickListener(new View.OnClickListener() { // from class: com.app.orsozoxi.MainActivity_kholagy_menu.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity_kholagy_menu.this, (Class<?>) MainActivity_kholagy.class);
                intent.putExtra(ShareConstants.MEDIA_TYPE, "hamal");
                MainActivity_kholagy_menu.this.startActivity(intent);
            }
        });
        findViewById(R.id.btn_gergory).setOnClickListener(new View.OnClickListener() { // from class: com.app.orsozoxi.MainActivity_kholagy_menu.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity_kholagy_menu.this, (Class<?>) MainActivity_kholagy.class);
                intent.putExtra(ShareConstants.MEDIA_TYPE, "gergory");
                MainActivity_kholagy_menu.this.startActivity(intent);
            }
        });
        findViewById(R.id.btn_sign_langauge).setOnClickListener(new View.OnClickListener() { // from class: com.app.orsozoxi.MainActivity_kholagy_menu.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MainActivity_kholagy_menu.this.signLanguageReader();
            }
        });
        findViewById(R.id.btn_all).setOnClickListener(new View.OnClickListener() { // from class: com.app.orsozoxi.MainActivity_kholagy_menu.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MainActivity_kholagy_menu.this.startActivity(new Intent(MainActivity_kholagy_menu.this, (Class<?>) KholagyShamasMenuActivity.class));
            }
        });
    }

    private void ShowAfter5Message() {
        katamarsReaders katamarsreaders = new katamarsReaders(this, false);
        if (Calendar.getInstance().get(11) < 17 || katamarsreaders.eidName.equals("kyama") || katamarsreaders.eidName.equals("gomaa_azema") || katamarsreaders.eidName.contains("sabt_farah") || katamarsreaders.eidName.equals("barmoon_gotas_lastday") || katamarsreaders.eidName.equals("barmoon_gotas_lastday_sun") || katamarsreaders.eidName.equals("eid_gotas") || katamarsreaders.eidName.equals("barmoon_melad_lastday") || katamarsreaders.eidName.equals("eid_melad")) {
            return;
        }
        new AlertDialog.Builder(this).setIcon(R.drawable.logo).setTitle(getString(R.string.alert)).setMessage(getString(R.string.alert_5_kholagy)).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() { // from class: com.app.orsozoxi.MainActivity_kholagy_menu.7
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        }).show();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStart() {
        super.onStart();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void signLanguageReader() {
        this.signFilePath = CommonUtilities.getInternalPath(this) + "kholagy.pdf";
        if (new File(this.signFilePath).exists()) {
            Intent intent = new Intent(this, (Class<?>) PdfViewerActivity.class);
            intent.putExtra("filepath", this.signFilePath);
            startActivity(intent);
            return;
        }
        manageData();
    }

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 30) {
            manageData();
            return true;
        }
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") == 0) {
                Log.v(Util.TAG, "Permission is granted");
                manageData();
                return true;
            }
            Log.v(Util.TAG, "Permission is revoked");
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE"}, 1);
            return false;
        }
        Log.v(Util.TAG, "Permission is granted");
        manageData();
        return true;
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (iArr[0] == 0 && iArr[1] == 0) {
            Log.v(Util.TAG, "Permission: " + strArr[0] + "was " + iArr[0]);
            manageData();
        }
    }

    public boolean checkinternet() {
        ConnectionDetector connectionDetector = new ConnectionDetector(getApplicationContext());
        this.cd = connectionDetector;
        return connectionDetector.isConnectingToInternet();
    }

    private void manageData() {
        Log.d(RemoteMessageConst.Notification.TAG, "id=");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.t_kholagy));
        builder.setMessage(getString(R.string.download_sign_question));
        builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() { // from class: com.app.orsozoxi.MainActivity_kholagy_menu.8
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                if (MainActivity_kholagy_menu.this.checkinternet()) {
                    MainActivity_kholagy_menu.this.mProgressDialog = new ProgressDialog(MainActivity_kholagy_menu.this);
                    MainActivity_kholagy_menu.this.mProgressDialog.setMessage(MainActivity_kholagy_menu.this.getString(R.string.download_tafser));
                    MainActivity_kholagy_menu.this.mProgressDialog.setCancelable(false);
                    MainActivity_kholagy_menu.this.mProgressDialog.show();
                    MainActivity_kholagy_menu.this.downloadHTMLFileAsync();
                    return;
                }
                Toast.makeText(MainActivity_kholagy_menu.this.getApplicationContext(), R.string.no_internet, 1).show();
            }
        });
        builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() { // from class: com.app.orsozoxi.MainActivity_kholagy_menu.9
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void downloadHTMLFileAsync() {
        Observable.fromCallable(new Callable() { // from class: com.app.orsozoxi.-$$Lambda$MainActivity_kholagy_menu$rlOrRmCM6f_INie8xcxdwWJKGng
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return this.f$0.lambda$downloadHTMLFileAsync$0$MainActivity_kholagy_menu();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.app.orsozoxi.-$$Lambda$MainActivity_kholagy_menu$Ky1HpmJAQJbVicHYnKXzviwSfx0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.lambda$downloadHTMLFileAsync$1$MainActivity_kholagy_menu((Boolean) obj);
            }
        });
    }

    public /* synthetic */ Boolean lambda$downloadHTMLFileAsync$0$MainActivity_kholagy_menu() throws Exception {
        try {
            Log.d("test", "download");
            URLConnection uRLConnectionOpenConnection = new URL(CommonUtilities.SERVER_URL_KHOLAGY_SIGN).openConnection();
            uRLConnectionOpenConnection.setRequestProperty("Content-Type", "text/plain; charset=utf-8");
            uRLConnectionOpenConnection.setRequestProperty("Expect", HTTP.EXPECT_CONTINUE);
            uRLConnectionOpenConnection.setDoOutput(true);
            uRLConnectionOpenConnection.connect();
            File file = new File(this.signFilePath);
            this.fileWithinMyDirHTML = file;
            if (file.exists()) {
                this.fileWithinMyDirHTML.delete();
                this.fileWithinMyDirHTML = new File(this.signFilePath);
            }
            FileOutputStream fileOutputStream = new FileOutputStream(this.fileWithinMyDirHTML);
            InputStream inputStream = uRLConnectionOpenConnection.getInputStream();
            byte[] bArr = new byte[1024];
            while (true) {
                int i = inputStream.read(bArr);
                if (i <= 0) {
                    break;
                }
                fileOutputStream.write(bArr, 0, i);
            }
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public /* synthetic */ void lambda$downloadHTMLFileAsync$1$MainActivity_kholagy_menu(Boolean bool) throws Exception {
        this.mProgressDialog.dismiss();
        Intent intent = new Intent(this, (Class<?>) PdfViewerActivity.class);
        intent.putExtra("filepath", this.signFilePath);
        startActivity(intent);
    }
}
