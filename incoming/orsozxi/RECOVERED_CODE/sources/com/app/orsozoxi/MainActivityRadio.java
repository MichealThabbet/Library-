package com.app.orsozoxi;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.app.orsozoxi.Radio.Controls;
import com.app.orsozoxi.Radio.CustomAdapter;
import com.app.orsozoxi.Radio.MediaItem;
import com.app.orsozoxi.Radio.PlayerConstants;
import com.app.orsozoxi.Radio.SongService;
import com.app.orsozoxi.Radio.UtilFunctions;
import com.ibm.icu.impl.locale.LanguageTag;

/* loaded from: classes.dex */
public class MainActivityRadio extends BaseActivity {
    static Button btnNext;
    static Button btnPause;
    static Button btnPlay;
    static Button btnPrevious;
    static Context context;
    static ImageView imageViewAlbumArt;
    static LinearLayout linearLayoutPlayingSong;
    static TextView playingSong;
    Button btnPlayer;
    Button btnStop;
    RelativeLayout mediaLayout;
    ProgressBar progressBar;
    TextView textBufferDuration;
    TextView textDuration;
    String LOG_CLASS = "MainActivity";
    CustomAdapter customAdapter = null;
    private int quality = 24;

    @Override // com.app.orsozoxi.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_new_radio);
        context = this;
        WebView webView = (WebView) findViewById(R.id.wv_radio);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.loadUrl("https://orsozoxi.net/radio/radio_fn.html");
        init();
    }

    private void init() {
        getViews();
        setListeners();
        playingSong.setSelected(true);
        this.progressBar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_IN);
        new AlertDialog.Builder(this).setTitle(getString(R.string.radio_quality)).setMessage(getString(R.string.choose_radio_quality)).setCancelable(false).setPositiveButton(getString(R.string.quality_24), new DialogInterface.OnClickListener() { // from class: com.app.orsozoxi.MainActivityRadio.3
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                MainActivityRadio.this.quality = 24;
                PlayerConstants.SONGS_LIST = UtilFunctions.listOfSongs(MainActivityRadio.this, 24);
                MainActivityRadio.this.setListItems();
            }
        }).setNeutralButton(getString(R.string.quality_48), new DialogInterface.OnClickListener() { // from class: com.app.orsozoxi.MainActivityRadio.2
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                MainActivityRadio.this.quality = 48;
                PlayerConstants.SONGS_LIST = UtilFunctions.listOfSongs(MainActivityRadio.this, 48);
                MainActivityRadio.this.setListItems();
            }
        }).setNegativeButton(getString(R.string.quality_64), new DialogInterface.OnClickListener() { // from class: com.app.orsozoxi.MainActivityRadio.1
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                MainActivityRadio.this.quality = 64;
                PlayerConstants.SONGS_LIST = UtilFunctions.listOfSongs(MainActivityRadio.this, 64);
                MainActivityRadio.this.setListItems();
            }
        }).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setListItems() {
        this.customAdapter = new CustomAdapter(this, R.layout.custom_list, PlayerConstants.SONGS_LIST);
    }

    private void getViews() {
        playingSong = (TextView) findViewById(R.id.textNowPlaying);
        this.btnPlayer = (Button) findViewById(R.id.btnMusicPlayer);
        this.mediaLayout = (RelativeLayout) findViewById(R.id.linearLayoutMusicList);
        btnPause = (Button) findViewById(R.id.btnPause);
        btnPlay = (Button) findViewById(R.id.btnPlay);
        linearLayoutPlayingSong = (LinearLayout) findViewById(R.id.linearLayoutPlayingSong);
        this.progressBar = (ProgressBar) findViewById(R.id.progressBar);
        this.btnStop = (Button) findViewById(R.id.btnStop);
        this.textBufferDuration = (TextView) findViewById(R.id.textBufferDuration);
        this.textDuration = (TextView) findViewById(R.id.textDuration);
        imageViewAlbumArt = (ImageView) findViewById(R.id.imageViewAlbumArt);
        btnNext = (Button) findViewById(R.id.btnNext);
        btnPrevious = (Button) findViewById(R.id.btnPrevious);
    }

    private void setListeners() {
        Log.d("TAG", "TAG Tapped INOUT(OUT)");
        this.btnPlayer.setOnClickListener(new View.OnClickListener() { // from class: com.app.orsozoxi.MainActivityRadio.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
            }
        });
        btnPlay.setOnClickListener(new View.OnClickListener() { // from class: com.app.orsozoxi.MainActivityRadio.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Log.d("TAG", "TAG Tapped INOUT(IN)");
                MainActivityRadio.this.findViewById(R.id.tv_duration).setVisibility(0);
                PlayerConstants.SONG_PAUSED = false;
                PlayerConstants.SONG_NUMBER = 0;
                if (Build.VERSION.SDK_INT >= 26) {
                    if (!UtilFunctions.isServiceRunning(SongService.class.getName(), MainActivityRadio.this.getApplicationContext())) {
                        Intent intent = new Intent(MainActivityRadio.this.getApplicationContext(), (Class<?>) SongService.class);
                        intent.putExtra("quality", MainActivityRadio.this.quality);
                        ContextCompat.startForegroundService(MainActivityRadio.this.getApplicationContext(), intent);
                    } else {
                        PlayerConstants.SONG_CHANGE_HANDLER.sendMessage(PlayerConstants.SONG_CHANGE_HANDLER.obtainMessage());
                    }
                } else if (!UtilFunctions.isServiceRunning(SongService.class.getName(), MainActivityRadio.this.getApplicationContext())) {
                    Intent intent2 = new Intent(MainActivityRadio.this.getApplicationContext(), (Class<?>) SongService.class);
                    intent2.putExtra("quality", MainActivityRadio.this.quality);
                    MainActivityRadio.this.startService(intent2);
                } else {
                    PlayerConstants.SONG_CHANGE_HANDLER.sendMessage(PlayerConstants.SONG_CHANGE_HANDLER.obtainMessage());
                }
                MainActivityRadio.updateUI();
                MainActivityRadio.changeButton();
                Controls.playControl(MainActivityRadio.this.getApplicationContext());
            }
        });
        btnPause.setOnClickListener(new View.OnClickListener() { // from class: com.app.orsozoxi.MainActivityRadio.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Controls.pauseControl(MainActivityRadio.this.getApplicationContext());
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() { // from class: com.app.orsozoxi.MainActivityRadio.7
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Controls.nextControl(MainActivityRadio.this.getApplicationContext());
            }
        });
        btnPrevious.setOnClickListener(new View.OnClickListener() { // from class: com.app.orsozoxi.MainActivityRadio.8
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Controls.previousControl(MainActivityRadio.this.getApplicationContext());
            }
        });
        this.btnStop.setOnClickListener(new View.OnClickListener() { // from class: com.app.orsozoxi.MainActivityRadio.9
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MainActivityRadio.this.findViewById(R.id.tv_duration).setVisibility(8);
                if (Build.VERSION.SDK_INT >= 26) {
                    MainActivityRadio.this.stopService(new Intent(MainActivityRadio.this.getApplicationContext(), (Class<?>) SongService.class));
                } else {
                    MainActivityRadio.this.stopService(new Intent(MainActivityRadio.this.getApplicationContext(), (Class<?>) SongService.class));
                }
            }
        });
        imageViewAlbumArt.setOnClickListener(new View.OnClickListener() { // from class: com.app.orsozoxi.MainActivityRadio.10
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
            }
        });
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        boolean zIsServiceRunning;
        super.onResume();
        try {
            if (Build.VERSION.SDK_INT >= 26) {
                zIsServiceRunning = UtilFunctions.isServiceRunning(SongService.class.getName(), getApplicationContext());
            } else {
                zIsServiceRunning = UtilFunctions.isServiceRunning(SongService.class.getName(), getApplicationContext());
            }
            if (zIsServiceRunning) {
                updateUI();
            }
            changeButton();
            PlayerConstants.PROGRESSBAR_HANDLER = new Handler() { // from class: com.app.orsozoxi.MainActivityRadio.11
                @Override // android.os.Handler
                public void handleMessage(Message message) {
                    Integer[] numArr = (Integer[]) message.obj;
                    MainActivityRadio.this.textBufferDuration.setText(UtilFunctions.getDuration(numArr[0].intValue()));
                    MainActivityRadio.this.textDuration.setText(UtilFunctions.getDuration(numArr[1].intValue()));
                    MainActivityRadio.this.progressBar.setProgress(numArr[2].intValue());
                }
            };
        } catch (Exception unused) {
        }
    }

    public static void updateUI() {
        try {
            MediaItem mediaItem = PlayerConstants.SONGS_LIST.get(PlayerConstants.SONG_NUMBER);
            playingSong.setText(mediaItem.getTitle() + " " + mediaItem.getArtist() + LanguageTag.SEP + mediaItem.getAlbum());
            imageViewAlbumArt.setBackgroundDrawable(new BitmapDrawable(UtilFunctions.getDefaultAlbumArt(context)));
            linearLayoutPlayingSong.setVisibility(0);
        } catch (Exception unused) {
        }
    }

    public static void changeButton() {
        if (PlayerConstants.SONG_PAUSED) {
            btnPause.setVisibility(8);
            btnPlay.setVisibility(0);
        } else {
            btnPause.setVisibility(0);
            btnPlay.setVisibility(8);
        }
    }

    public static void changeUI() {
        updateUI();
        changeButton();
    }
}
