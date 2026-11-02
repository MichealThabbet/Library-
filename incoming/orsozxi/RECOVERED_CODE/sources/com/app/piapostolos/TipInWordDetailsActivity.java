package com.app.piapostolos;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.GestureDetector;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.core.app.NotificationCompat;
import androidx.core.app.ShareCompat;
import androidx.core.view.InputDeviceCompat;
import androidx.core.view.ViewCompat;
import com.app.piapostolos.Adapters.ExpandableListAdapter;
import com.app.piapostolos.Beans.WhiteBackgroundActivition;
import com.app.piapostolos.Others.CommonUtilities;
import com.app.piapostolos.Others.PlayPauseView;
import com.app.piapostolos.Others.SavePrefs;
import com.app.piapostolos.Others.SliderAudio;
import com.app.piapostolos.Others.checksim;
import com.app.piapostolos.Views.MenuDateView;
import com.github.clans.fab.FloatingActionMenu;
import com.ibm.icu.impl.locale.LanguageTag;
import com.navdrawer.SimpleSideDrawer;
import com.squareup.picasso.Picasso;
import dm.audiostreamer.AudioStreamingManager;
import dm.audiostreamer.CurrentSessionCallback;
import dm.audiostreamer.MediaMetaData;
import it.piapostolos.android.pinotes.utils.ConstantsBase;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import wseemann.media.FFmpegMediaMetadataRetriever;

/* loaded from: classes.dex */
public class TipInWordDetailsActivity extends BaseActivity implements View.OnClickListener, CurrentSessionCallback, SliderAudio.OnValueChangedListener {
    private SliderAudio audioPg;
    private PlayPauseView btn_play;
    private View content;
    private MediaMetaData currentSong;
    TextView feedburner;
    private GestureDetector gestureDetector;
    TextView link;
    private FloatingActionMenu mHomeFloatingActionMenu;
    private MenuDateView menuDateView;
    private RelativeLayout pgPlayPauseLayout;
    TextView pubDate;
    AudioStreamingManager streamingManager;
    private TextView time_progress_slide;
    private TextView time_total_slide;
    TextView title;
    String tit = "";
    String desc = "";
    String pubda = "";
    String gui = "";
    String url = "";
    String link2 = "";
    private final int CONTEXT_MENU_ID = 1;
    private String category = "";
    private boolean isWhite = false;
    boolean backPressed = false;

    @Override // dm.audiostreamer.CurrentSessionCallback
    public void playCurrent(int i, MediaMetaData mediaMetaData) {
    }

    @Override // dm.audiostreamer.CurrentSessionCallback
    public void playNext(int i, MediaMetaData mediaMetaData) {
    }

    @Override // dm.audiostreamer.CurrentSessionCallback
    public void playPrevious(int i, MediaMetaData mediaMetaData) {
    }

    @Override // com.app.piapostolos.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_details_tip);
        try {
            Log.i("BEFORE", "<<------------- Before SetAdapter-------------->>");
            Log.i("BEFORE", "<<------------- Before SetAdapter11-------------->>");
            this.title = (TextView) findViewById(R.id.titlerow2);
            this.pubDate = (TextView) findViewById(R.id.daterow2);
            Log.i("BEFORE", "<<------------- Before SetAdapter11-------------->>");
            this.feedburner = (TextView) findViewById(R.id.contentrow2);
            this.link = (TextView) findViewById(R.id.TextView01);
            this.pgPlayPauseLayout = (RelativeLayout) findViewById(R.id.pgPlayPauseLayout);
            this.btn_play = (PlayPauseView) findViewById(R.id.btn_play);
            this.time_progress_slide = (TextView) findViewById(R.id.slidepanel_time_progress);
            this.audioPg = (SliderAudio) findViewById(R.id.audio_progress_control);
            this.time_total_slide = (TextView) findViewById(R.id.slidepanel_time_total);
            getWindow().addFlags(128);
            checksim checksimVar = new checksim();
            WhiteBackgroundActivition whiteBackgroundActivition = (WhiteBackgroundActivition) new SavePrefs((Activity) this, (Class<?>) WhiteBackgroundActivition.class).load();
            if (whiteBackgroundActivition == null || !whiteBackgroundActivition.isWhiteBackground()) {
                getWindow().setBackgroundDrawableResource(R.drawable.background);
                this.feedburner.setTextColor(-1);
                this.title.setTextColor(Color.parseColor("#ffffff"));
                this.pubDate.setTextColor(Color.parseColor("#FF6600"));
                this.link.setTextColor(InputDeviceCompat.SOURCE_ANY);
                this.time_progress_slide.setTextColor(-1);
                this.time_total_slide.setTextColor(-1);
            } else {
                this.isWhite = true;
                this.feedburner.setTextColor(ViewCompat.MEASURED_STATE_MASK);
                this.title.setTextColor(ViewCompat.MEASURED_STATE_MASK);
                this.pubDate.setTextColor(ViewCompat.MEASURED_STATE_MASK);
                this.link.setTextColor(ViewCompat.MEASURED_STATE_MASK);
                this.time_progress_slide.setTextColor(ViewCompat.MEASURED_STATE_MASK);
                this.time_total_slide.setTextColor(ViewCompat.MEASURED_STATE_MASK);
            }
            initFloating();
            Button button = (Button) findViewById(R.id.btn_slide);
            final SimpleSideDrawer simpleSideDrawer = new SimpleSideDrawer(this);
            simpleSideDrawer.setLeftBehindContentView(R.layout.left_menu_ex);
            button.setVisibility(0);
            Slider.prepareListData(this);
            ((ExpandableListView) findViewById(R.id.lvExp)).setAdapter(new ExpandableListAdapter(this, this, Slider.listDataHeader, Slider.listDataChild));
            button.setOnClickListener(new View.OnClickListener() { // from class: com.app.piapostolos.TipInWordDetailsActivity.1
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
            this.category = intent.getStringExtra("category");
            TextView textView = (TextView) findViewById(R.id.tv_header_title);
            setTitle(getString(R.string.tip_in_word));
            textView.setText(getString(R.string.tip_in_word));
            Log.i("BEFORE", "<<------------- Before SetAdapter1111-------------->>");
            this.title.setText(this.tit);
            this.link.setText(this.link2);
            this.link.setOnClickListener(new View.OnClickListener() { // from class: com.app.piapostolos.TipInWordDetailsActivity.2
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (TextUtils.isEmpty(TipInWordDetailsActivity.this.link2)) {
                        return;
                    }
                    TipInWordDetailsActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(TipInWordDetailsActivity.this.link2)));
                }
            });
            String strSubstring = this.pubda.substring(0, 16);
            try {
                Date date = new SimpleDateFormat("EEE, dd MMM yyyy").parse(strSubstring);
                this.pubDate.setText(new SimpleDateFormat("EEE, dd MMM yyyy", new Locale("ar")).format(date));
            } catch (ParseException unused) {
                this.pubDate.setText(strSubstring);
            }
            String strReplaceAll = this.category.replaceAll("null", "");
            this.category = strReplaceAll;
            if (strReplaceAll.contains(LanguageTag.SEP)) {
                this.category = this.category.split(LanguageTag.SEP)[0];
            }
            this.feedburner.setText(this.category);
            Log.i("BEFORE", "<<------------- Before SetAdapter1-------------->>");
            Picasso.with(this).load(this.gui).fit().placeholder(R.drawable.thumb_m).into((ImageView) findViewById(R.id.imageView11));
        } catch (Exception unused2) {
            Log.i("BEFORE", "<<------------- Before SetAdapter2-------------->>");
            Log.e("Error", "Loading exception");
        }
        initPlayer();
    }

    public void shareImage() throws IOException {
        if (!this.isWhite) {
            findViewById(R.id.scrollView1).setBackgroundResource(R.drawable.background);
        }
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(findViewById(R.id.scrollView1).getWidth(), findViewById(R.id.scrollView1).getHeight(), Bitmap.Config.ARGB_8888);
        findViewById(R.id.scrollView1).draw(new Canvas(bitmapCreateBitmap));
        try {
            File file = new File(CommonUtilities.getStoragePath(this) + "/PiApostolos");
            if (!file.exists()) {
                file.mkdirs();
            }
            File file2 = new File(file, "" + System.currentTimeMillis() + ConstantsBase.MIME_TYPE_SKETCH_EXT);
            FileOutputStream fileOutputStream = new FileOutputStream(file2);
            bitmapCreateBitmap.compress(Bitmap.CompressFormat.PNG, 85, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            startActivity(ShareCompat.IntentBuilder.from(this).setText(getString(R.string.from_akwal)).setSubject(getString(R.string.share_from_piapostolos)).setStream(Uri.fromFile(file2)).setType(ConstantsBase.MIME_TYPE_IMAGE).getIntent().setPackage("com.facebook.katana"));
        } catch (Exception unused) {
        }
    }

    private void initFloating() {
        FloatingActionMenu floatingActionMenu = (FloatingActionMenu) findViewById(R.id.menu_options);
        this.mHomeFloatingActionMenu = floatingActionMenu;
        floatingActionMenu.setIconAnimated(false);
        this.mHomeFloatingActionMenu.setClosedOnTouchOutside(true);
        this.mHomeFloatingActionMenu.setOnMenuButtonClickListener(new View.OnClickListener() { // from class: com.app.piapostolos.TipInWordDetailsActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                TipInWordDetailsActivity.this.mHomeFloatingActionMenu.toggle(true);
            }
        });
        findViewById(R.id.fb_copy).setOnClickListener(this);
        findViewById(R.id.fb_share).setOnClickListener(this);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.fb_copy) {
            this.mHomeFloatingActionMenu.toggle(false);
            app.copyText(this, (((Object) this.title.getText()) + "\n" + ((Object) this.feedburner.getText())) + "\n" + this.desc);
            return;
        }
        if (id != R.id.fb_share) {
            return;
        }
        this.mHomeFloatingActionMenu.toggle(false);
        app.shareText(this, (((Object) this.title.getText()) + "\n" + ((Object) this.feedburner.getText())) + "\n" + this.desc);
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        if (this.mHomeFloatingActionMenu.isOpened()) {
            this.mHomeFloatingActionMenu.toggle(true);
        } else {
            this.backPressed = true;
            super.onBackPressed();
        }
    }

    private void initPlayer() {
        findViewById(R.id.btn_play).setOnClickListener(new View.OnClickListener() { // from class: com.app.piapostolos.-$$Lambda$TipInWordDetailsActivity$I_csHMvXv4DpK6shhjGa83eEBcg
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
                this.f$0.lambda$initPlayer$0$TipInWordDetailsActivity(view);
            }
        });
        AudioStreamingManager audioStreamingManager = AudioStreamingManager.getInstance(this);
        this.streamingManager = audioStreamingManager;
        audioStreamingManager.setPlayMultiple(false);
        this.streamingManager.setShowPlayerNotification(true);
        this.streamingManager.setPendingIntentAct(getNotificationPendingIntent());
        MediaMetaData mediaMetaData = new MediaMetaData();
        this.currentSong = mediaMetaData;
        mediaMetaData.setMediaId("0");
        this.currentSong.setMediaUrl(this.desc.replace("http://", "https://"));
        this.currentSong.setMediaTitle(this.tit);
        long durationInMilliseconds = getDurationInMilliseconds() / 1000;
        this.currentSong.setMediaDuration(Long.toString(durationInMilliseconds));
        this.currentSong.setMediaComposer(this.category);
        this.currentSong.setMediaArtist(this.category);
        this.currentSong.setMediaArt(this.gui);
        if (this.streamingManager.getCurrentAudio() != null && this.streamingManager.getCurrentAudio().getMediaUrl() != null && this.streamingManager.getCurrentAudio().getMediaUrl().equals(this.desc.replace("http://", "https://"))) {
            if (this.streamingManager.isPlaying()) {
                this.btn_play.Play();
            } else {
                this.btn_play.Pause();
            }
        } else {
            this.streamingManager.onPause();
            this.btn_play.Pause();
        }
        this.audioPg.setValue(0);
        this.audioPg.setMin(0);
        Log.d("progress audioPg max", durationInMilliseconds + "");
        this.audioPg.setMax(((int) durationInMilliseconds) * 1000);
        setPGTime(0);
        setMaxTime(durationInMilliseconds);
        this.audioPg.setOnValueChangedListener(this);
    }

    public /* synthetic */ void lambda$initPlayer$0$TipInWordDetailsActivity(View view) throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
        if (this.currentSong != null) {
            playPauseEvent(view);
        }
    }

    private PendingIntent getNotificationPendingIntent() {
        Intent intent = new Intent(this, (Class<?>) TipInWordDetailsActivity.class);
        intent.setAction("openplayer");
        intent.putExtra("title", this.tit);
        intent.putExtra("pubDate", this.pubda);
        intent.putExtra("feedburner", this.desc);
        intent.putExtra("guid", this.gui);
        intent.putExtra("linkk", this.link2);
        intent.putExtra("category", this.category);
        intent.putExtra("url", this.url);
        intent.setFlags(268468224);
        if (Build.VERSION.SDK_INT >= 23) {
            return PendingIntent.getActivity(this, 0, intent, 335544320);
        }
        return PendingIntent.getActivity(this, 0, intent, 0);
    }

    public int getDurationInMilliseconds() throws NumberFormatException {
        try {
            FFmpegMediaMetadataRetriever fFmpegMediaMetadataRetriever = new FFmpegMediaMetadataRetriever();
            fFmpegMediaMetadataRetriever.setDataSource(this.desc.replace("https://", "http://"));
            int i = Integer.parseInt(fFmpegMediaMetadataRetriever.extractMetadata(FFmpegMediaMetadataRetriever.METADATA_KEY_DURATION));
            fFmpegMediaMetadataRetriever.release();
            return i;
        } catch (IllegalArgumentException unused) {
            return 0;
        }
    }

    private void setMaxTime(long j) {
        try {
            this.time_total_slide.setText(DateUtils.formatElapsedTime(Long.parseLong(Long.toString(j))));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private void playPauseEvent(View view) throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
        if (this.streamingManager.getCurrentAudio() == null || this.streamingManager.getCurrentAudio().getMediaUrl() == null) {
            if (this.streamingManager.isPlaying()) {
                this.streamingManager.onPause();
                ((PlayPauseView) view).Pause();
                return;
            } else {
                this.streamingManager.onPlay(this.currentSong);
                ((PlayPauseView) view).Play();
                return;
            }
        }
        if (this.streamingManager.getCurrentAudio() != null && this.streamingManager.getCurrentAudio().getMediaUrl() != null && this.streamingManager.getCurrentAudio().getMediaUrl().equals(this.desc.replace("http://", "https://"))) {
            if (this.streamingManager.isPlaying()) {
                this.streamingManager.onPause();
                ((PlayPauseView) view).Pause();
                return;
            } else {
                this.streamingManager.onPlay(this.currentSong);
                ((PlayPauseView) view).Play();
                return;
            }
        }
        playSong();
    }

    private void playSong() throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
        AudioStreamingManager audioStreamingManager = this.streamingManager;
        if (audioStreamingManager != null) {
            audioStreamingManager.onStop();
            this.streamingManager.clearList();
            initPlayer();
            this.streamingManager.onPlay(this.currentSong);
            this.btn_play.Play();
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStart() {
        super.onStart();
        AudioStreamingManager audioStreamingManager = this.streamingManager;
        if (audioStreamingManager != null) {
            audioStreamingManager.subscribesCallBack(this);
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStop() {
        super.onStop();
        AudioStreamingManager audioStreamingManager = this.streamingManager;
        if (audioStreamingManager != null) {
            audioStreamingManager.unSubscribeCallBack();
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        try {
            if (this.streamingManager != null) {
                this.streamingManager.unSubscribeCallBack();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    @Override // dm.audiostreamer.CurrentSessionCallback
    public void updatePlaybackState(int i) {
        if (i == 0) {
            this.currentSong.setPlayState(0);
            return;
        }
        if (i == 1) {
            this.pgPlayPauseLayout.setVisibility(4);
            this.btn_play.Pause();
            this.audioPg.setValue(0);
            MediaMetaData mediaMetaData = this.currentSong;
            if (mediaMetaData != null) {
                mediaMetaData.setPlayState(0);
                return;
            }
            return;
        }
        if (i == 2) {
            this.pgPlayPauseLayout.setVisibility(4);
            this.btn_play.Pause();
            MediaMetaData mediaMetaData2 = this.currentSong;
            if (mediaMetaData2 != null) {
                mediaMetaData2.setPlayState(2);
                return;
            }
            return;
        }
        if (i == 3) {
            this.pgPlayPauseLayout.setVisibility(4);
            this.btn_play.Play();
            MediaMetaData mediaMetaData3 = this.currentSong;
            if (mediaMetaData3 != null) {
                mediaMetaData3.setPlayState(3);
                return;
            }
            return;
        }
        if (i != 6) {
            return;
        }
        this.pgPlayPauseLayout.setVisibility(0);
        MediaMetaData mediaMetaData4 = this.currentSong;
        if (mediaMetaData4 != null) {
            mediaMetaData4.setPlayState(0);
        }
    }

    @Override // dm.audiostreamer.CurrentSessionCallback
    public void playSongComplete() {
        this.time_progress_slide.setText("00.00");
        this.time_total_slide.setText("00.00");
        this.audioPg.setValue(0);
    }

    @Override // dm.audiostreamer.CurrentSessionCallback
    public void currentSeekBarPosition(int i) {
        Log.d(NotificationCompat.CATEGORY_PROGRESS, i + "");
        this.audioPg.setValue(i);
        setPGTime(i);
    }

    private void setPGTime(int i) {
        try {
            String elapsedTime = "00.00";
            if (this.currentSong != null && i != Long.parseLong(this.currentSong.getMediaDuration())) {
                elapsedTime = DateUtils.formatElapsedTime(i / 1000);
            }
            this.time_progress_slide.setText(elapsedTime);
            Log.d("progress linePG", i + "");
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    @Override // com.app.piapostolos.Others.SliderAudio.OnValueChangedListener
    public void onValueChanged(int i) throws IllegalStateException {
        this.streamingManager.onSeekTo(i);
        this.streamingManager.scheduleSeekBarUpdate();
        Log.d("progress onValueChanged", i + "");
    }
}
