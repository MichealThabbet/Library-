package com.app.orsozoxi.Views;

import android.content.Context;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.app.orsozoxi.R;
import java.util.Random;

/* loaded from: classes.dex */
public class SnowFlakesLayout extends RelativeLayout {
    int animateDuration;
    Context context;
    boolean enableAlphaFade;
    boolean enableRandomCurving;
    int generateSnowTiming;
    Random generator;
    float height;
    int imageResourceID;
    Handler mHandler;
    CountDownTimer mainCountdownSnowTimer;
    boolean shouldRandomSnowSize;
    final int snowFlakeYInitializePosition;
    int snowMaxSize;
    int snowMinSize;
    int wholeAnimateTiming;
    float width;

    public SnowFlakesLayout(Context context) {
        super(context);
        this.animateDuration = 10000;
        this.wholeAnimateTiming = 300000;
        this.generateSnowTiming = 1000;
        this.snowMaxSize = 40;
        this.snowMinSize = 1;
        this.shouldRandomSnowSize = false;
        this.enableRandomCurving = false;
        this.enableAlphaFade = false;
        this.snowFlakeYInitializePosition = -30;
        this.generator = new Random();
        this.mHandler = new Handler();
        this.context = context;
    }

    public SnowFlakesLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.animateDuration = 10000;
        this.wholeAnimateTiming = 300000;
        this.generateSnowTiming = 1000;
        this.snowMaxSize = 40;
        this.snowMinSize = 1;
        this.shouldRandomSnowSize = false;
        this.enableRandomCurving = false;
        this.enableAlphaFade = false;
        this.snowFlakeYInitializePosition = -30;
        this.generator = new Random();
        this.mHandler = new Handler();
        this.context = context;
    }

    public SnowFlakesLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.animateDuration = 10000;
        this.wholeAnimateTiming = 300000;
        this.generateSnowTiming = 1000;
        this.snowMaxSize = 40;
        this.snowMinSize = 1;
        this.shouldRandomSnowSize = false;
        this.enableRandomCurving = false;
        this.enableAlphaFade = false;
        this.snowFlakeYInitializePosition = -30;
        this.generator = new Random();
        this.mHandler = new Handler();
        this.context = context;
    }

    public SnowFlakesLayout(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.animateDuration = 10000;
        this.wholeAnimateTiming = 300000;
        this.generateSnowTiming = 1000;
        this.snowMaxSize = 40;
        this.snowMinSize = 1;
        this.shouldRandomSnowSize = false;
        this.enableRandomCurving = false;
        this.enableAlphaFade = false;
        this.snowFlakeYInitializePosition = -30;
        this.generator = new Random();
        this.mHandler = new Handler();
        this.context = context;
    }

    public void setEnableAlphaFade(boolean z) {
        this.enableAlphaFade = z;
    }

    public void setImageResourceID(int i) {
        this.imageResourceID = i;
    }

    public void setWholeAnimateTiming(int i) {
        this.wholeAnimateTiming = i;
    }

    public void setAnimateDuration(int i) {
        this.animateDuration = i;
    }

    public void setGenerateSnowTiming(int i) {
        this.generateSnowTiming = i;
    }

    public void setRandomSnowSizeRange(int i, int i2) {
        this.shouldRandomSnowSize = true;
        this.snowMaxSize = i;
        this.snowMinSize = i2;
    }

    public void setEnableRandomCurving(boolean z) {
        this.enableRandomCurving = z;
    }

    public void init() {
        ((WindowManager) this.context.getSystemService("window")).getDefaultDisplay().getMetrics(new DisplayMetrics());
        this.height = r0.heightPixels;
        this.width = r0.widthPixels;
        this.imageResourceID = R.drawable.snow_flakes_pic;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Type inference failed for: r9v0, types: [com.app.orsozoxi.Views.SnowFlakesLayout$1] */
    public void showSnow() {
        final ImageView imageView = new ImageView(this.context);
        imageView.setClickable(false);
        imageView.setImageResource(this.imageResourceID);
        int iNextInt = this.snowMaxSize;
        if (this.shouldRandomSnowSize) {
            iNextInt = this.generator.nextInt(iNextInt) + this.snowMinSize;
        }
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(iNextInt, iNextInt);
        layoutParams.setMargins((int) ((this.width - iNextInt) - (this.generator.nextInt((int) this.width) + 1)), 0, 0, 0);
        addView(imageView, layoutParams);
        AnimationSet animationSet = new AnimationSet(false);
        TranslateAnimation translateAnimation = new TranslateAnimation(1.0f, 1.0f, -30.0f, this.height);
        translateAnimation.setDuration(this.animateDuration);
        animationSet.addAnimation(translateAnimation);
        if (this.enableRandomCurving) {
            RotateAnimation rotateAnimation = new RotateAnimation(0.0f, this.generator.nextInt(180) - 90);
            rotateAnimation.setStartOffset(this.animateDuration / 10);
            rotateAnimation.setDuration(this.animateDuration);
            animationSet.addAnimation(rotateAnimation);
        }
        if (this.enableAlphaFade) {
            AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.3f);
            alphaAnimation.setDuration(this.animateDuration);
            animationSet.addAnimation(alphaAnimation);
        }
        imageView.setTag(R.id.tag_countdown_timer, new CountDownTimer(this.animateDuration, 1000L) { // from class: com.app.orsozoxi.Views.SnowFlakesLayout.1
            @Override // android.os.CountDownTimer
            public void onTick(long j) {
            }

            @Override // android.os.CountDownTimer
            public void onFinish() {
                SnowFlakesLayout.this.removeView(imageView);
            }
        }.start());
        imageView.setAnimation(animationSet);
        animationSet.startNow();
    }

    /* JADX WARN: Type inference failed for: r6v0, types: [com.app.orsozoxi.Views.SnowFlakesLayout$2] */
    public void startSnowing() {
        this.mainCountdownSnowTimer = new CountDownTimer(this.wholeAnimateTiming, this.generateSnowTiming) { // from class: com.app.orsozoxi.Views.SnowFlakesLayout.2
            @Override // android.os.CountDownTimer
            public void onTick(long j) {
                SnowFlakesLayout.this.showSnow();
            }

            @Override // android.os.CountDownTimer
            public void onFinish() {
                SnowFlakesLayout.this.stopSnowing();
            }
        }.start();
    }

    public void stopSnowing() {
        this.mainCountdownSnowTimer.cancel();
        this.mHandler.post(new Runnable() { // from class: com.app.orsozoxi.Views.SnowFlakesLayout.3
            @Override // java.lang.Runnable
            public void run() {
                int childCount = SnowFlakesLayout.this.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View childAt = SnowFlakesLayout.this.getChildAt(i);
                    if (childAt != null) {
                        childAt.clearAnimation();
                    }
                    CountDownTimer countDownTimer = (CountDownTimer) childAt.getTag(R.id.tag_countdown_timer);
                    if (countDownTimer != null) {
                        countDownTimer.cancel();
                    }
                }
                SnowFlakesLayout.this.removeAllViews();
            }
        });
    }
}
