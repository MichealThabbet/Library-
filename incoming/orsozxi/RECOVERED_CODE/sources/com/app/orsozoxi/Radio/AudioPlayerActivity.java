package com.app.orsozoxi.Radio;

import android.app.Activity;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.app.orsozoxi.R;

/* loaded from: classes.dex */
public class AudioPlayerActivity extends Activity {
    static Button btnPause;
    static Button btnPlay;
    static Context context;
    static LinearLayout linearLayoutPlayer;
    static TextView textAlbumArtist;
    static TextView textComposer;
    static TextView textNowPlaying;
    Button btnBack;
    Button btnNext;
    ProgressBar progressBar;
    TextView textBufferDuration;
    TextView textDuration;

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getActionBar().hide();
        setContentView(R.layout.audio_player);
        context = this;
        init();
    }

    private void init() {
        getViews();
        setListeners();
        this.progressBar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_IN);
        PlayerConstants.PROGRESSBAR_HANDLER = new Handler() { // from class: com.app.orsozoxi.Radio.AudioPlayerActivity.1
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                Integer[] numArr = (Integer[]) message.obj;
                AudioPlayerActivity.this.textBufferDuration.setText(UtilFunctions.getDuration(numArr[0].intValue()));
                AudioPlayerActivity.this.textDuration.setText(UtilFunctions.getDuration(numArr[1].intValue()));
                AudioPlayerActivity.this.progressBar.setProgress(numArr[2].intValue());
            }
        };
    }

    private void setListeners() {
        this.btnBack.setOnClickListener(new View.OnClickListener() { // from class: com.app.orsozoxi.Radio.AudioPlayerActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Controls.previousControl(AudioPlayerActivity.this.getApplicationContext());
            }
        });
        btnPause.setOnClickListener(new View.OnClickListener() { // from class: com.app.orsozoxi.Radio.AudioPlayerActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Controls.pauseControl(AudioPlayerActivity.this.getApplicationContext());
            }
        });
        btnPlay.setOnClickListener(new View.OnClickListener() { // from class: com.app.orsozoxi.Radio.AudioPlayerActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Controls.playControl(AudioPlayerActivity.this.getApplicationContext());
            }
        });
        this.btnNext.setOnClickListener(new View.OnClickListener() { // from class: com.app.orsozoxi.Radio.AudioPlayerActivity.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Controls.nextControl(AudioPlayerActivity.this.getApplicationContext());
            }
        });
    }

    public static void changeUI() {
        updateUI();
        changeButton();
    }

    private void getViews() {
        this.btnBack = (Button) findViewById(R.id.btnBack);
        btnPause = (Button) findViewById(R.id.btnPause);
        this.btnNext = (Button) findViewById(R.id.btnNext);
        btnPlay = (Button) findViewById(R.id.btnPlay);
        textNowPlaying = (TextView) findViewById(R.id.textNowPlaying);
        linearLayoutPlayer = (LinearLayout) findViewById(R.id.linearLayoutPlayer);
        textAlbumArtist = (TextView) findViewById(R.id.textAlbumArtist);
        textComposer = (TextView) findViewById(R.id.textComposer);
        this.progressBar = (ProgressBar) findViewById(R.id.progressBar);
        this.textBufferDuration = (TextView) findViewById(R.id.textBufferDuration);
        this.textDuration = (TextView) findViewById(R.id.textDuration);
        textNowPlaying.setSelected(true);
        textAlbumArtist.setSelected(true);
    }

    @Override // android.app.Activity
    protected void onResume() {
        boolean zIsServiceRunning;
        super.onResume();
        if (Build.VERSION.SDK_INT >= 26) {
            zIsServiceRunning = UtilFunctions.isServiceRunning(SongService.class.getName(), getApplicationContext());
        } else {
            zIsServiceRunning = UtilFunctions.isServiceRunning(SongService.class.getName(), getApplicationContext());
        }
        if (zIsServiceRunning) {
            updateUI();
        }
        changeButton();
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

    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:10:0x0073 -> B:16:0x0076). Please report as a decompilation issue!!! */
    private static void updateUI() {
        try {
            String title = PlayerConstants.SONGS_LIST.get(PlayerConstants.SONG_NUMBER).getTitle();
            String artist = PlayerConstants.SONGS_LIST.get(PlayerConstants.SONG_NUMBER).getArtist();
            String album = PlayerConstants.SONGS_LIST.get(PlayerConstants.SONG_NUMBER).getAlbum();
            String composer = PlayerConstants.SONGS_LIST.get(PlayerConstants.SONG_NUMBER).getComposer();
            textNowPlaying.setText(title);
            textAlbumArtist.setText(artist + " - " + album);
            if (composer != null && composer.length() > 0) {
                textComposer.setVisibility(0);
                textComposer.setText(composer);
            } else {
                textComposer.setVisibility(8);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            PlayerConstants.SONGS_LIST.get(PlayerConstants.SONG_NUMBER).getAlbumId();
            linearLayoutPlayer.setBackgroundDrawable(new BitmapDrawable(UtilFunctions.getDefaultAlbumArt(context)));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}
