package com.app.orsozoxi.Youtube;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.app.orsozoxi.Orsozoxi;
import com.app.orsozoxi.R;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import org.apache.http.protocol.HTTP;

/* loaded from: classes.dex */
public class FullscreenDemoActivity extends YouTubeFailureRecoveryActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, YouTubePlayer.OnFullscreenListener {
    private static final int PORTRAIT_ORIENTATION;
    private LinearLayout baseLayout;
    private boolean fullscreen;
    private View otherViews;
    private YouTubePlayer player;
    private YouTubePlayerView playerView;

    static {
        PORTRAIT_ORIENTATION = Build.VERSION.SDK_INT < 9 ? 1 : 7;
    }

    @Override // com.google.android.youtube.player.YouTubeBaseActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());
        }
        setContentView(R.layout.fullscreen_demo);
        this.baseLayout = (LinearLayout) findViewById(R.id.layout);
        this.playerView = (YouTubePlayerView) findViewById(R.id.player);
        ((TextView) findViewById(R.id.tv_title)).setText(Orsozoxi.getSelectedPlaylistItem().getSnippet().getChannelTitle());
        ((TextView) findViewById(R.id.tv_desc)).setText(Orsozoxi.getSelectedPlaylistItem().getSnippet().getDescription());
        this.otherViews = findViewById(R.id.other_views);
        this.playerView.initialize(DeveloperKey.DEVELOPER_KEY, this);
        doLayout();
        findViewById(R.id.iv_share).setOnClickListener(new View.OnClickListener() { // from class: com.app.orsozoxi.Youtube.FullscreenDemoActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                FullscreenDemoActivity.this.sharePost();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sharePost() {
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType(HTTP.PLAIN_TEXT_TYPE);
        intent.putExtra("android.intent.extra.SUBJECT", Orsozoxi.getSelectedPlaylistItem().getSnippet().getChannelTitle());
        intent.putExtra("android.intent.extra.TEXT", Orsozoxi.getSelectedPlaylistItem().getSnippet().getDescription() + "\nwww.youtube.com/watch?v=" + Orsozoxi.getSelectedPlaylistItem().getSnippet().getResourceId().getVideoId() + "\nتم ارسال الرسالة من تطبيق أرثوذكسى+القطمارس\nhttps://www.orsozoxi.net/");
        try {
            startActivity(Intent.createChooser(intent, "تطبيق أرثوذكسى+القطمارس"));
        } catch (Exception unused) {
        }
    }

    @Override // com.google.android.youtube.player.YouTubePlayer.OnInitializedListener
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean z) {
        this.player = youTubePlayer;
        youTubePlayer.addFullscreenControlFlag(8);
        youTubePlayer.setOnFullscreenListener(this);
        if (z) {
            return;
        }
        youTubePlayer.cueVideo(Orsozoxi.getSelectedPlaylistItem().getSnippet().getResourceId().getVideoId());
    }

    @Override // com.app.orsozoxi.Youtube.YouTubeFailureRecoveryActivity
    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return this.playerView;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        this.player.setFullscreen(!this.fullscreen);
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        int i;
        int fullscreenControlFlags = this.player.getFullscreenControlFlags();
        if (z) {
            setRequestedOrientation(PORTRAIT_ORIENTATION);
            i = fullscreenControlFlags | 4;
        } else {
            setRequestedOrientation(4);
            i = fullscreenControlFlags & (-5);
        }
        this.player.setFullscreenControlFlags(i);
    }

    private void doLayout() {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.playerView.getLayoutParams();
        if (this.fullscreen) {
            layoutParams.width = -1;
            layoutParams.height = -1;
            this.otherViews.setVisibility(8);
            return;
        }
        this.otherViews.setVisibility(0);
        ViewGroup.LayoutParams layoutParams2 = this.otherViews.getLayoutParams();
        if (getResources().getConfiguration().orientation == 2) {
            layoutParams2.width = 0;
            layoutParams.width = 0;
            layoutParams.height = -2;
            layoutParams2.height = -1;
            layoutParams.weight = 1.0f;
            this.baseLayout.setOrientation(0);
            return;
        }
        layoutParams2.width = -1;
        layoutParams.width = -1;
        layoutParams.height = -2;
        layoutParams.weight = 0.0f;
        layoutParams2.height = 0;
        this.baseLayout.setOrientation(1);
    }

    @Override // com.google.android.youtube.player.YouTubePlayer.OnFullscreenListener
    public void onFullscreen(boolean z) {
        this.fullscreen = z;
        doLayout();
    }

    @Override // android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        doLayout();
    }
}
