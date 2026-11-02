package com.github.paolorotolo.appintro;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/* loaded from: classes.dex */
public abstract class AppIntro extends FragmentActivity {
    private static int FIRST_PAGE_NUM;
    private List<ImageView> dots;
    private PagerAdapter mPagerAdapter;
    private Vibrator mVibrator;
    private ViewPager pager;
    private int slidesNumber;
    private List<Fragment> fragments = new Vector();
    private boolean isVibrateOn = false;
    private int vibrateIntensity = 20;
    private boolean showSkip = true;

    public abstract void init(Bundle bundle);

    public abstract void onDonePressed();

    public abstract void onSkipPressed();

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected final void onCreate(Bundle bundle) throws Resources.NotFoundException {
        super.onCreate(bundle);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.intro_layout);
        final TextView textView = (TextView) findViewById(R.id.skip);
        final ImageView imageView = (ImageView) findViewById(R.id.next);
        final TextView textView2 = (TextView) findViewById(R.id.done);
        this.mVibrator = (Vibrator) getSystemService("vibrator");
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.github.paolorotolo.appintro.AppIntro.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (AppIntro.this.isVibrateOn) {
                    AppIntro.this.mVibrator.vibrate(AppIntro.this.vibrateIntensity);
                }
                AppIntro.this.onSkipPressed();
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.github.paolorotolo.appintro.AppIntro.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) throws Resources.NotFoundException {
                if (AppIntro.this.isVibrateOn) {
                    AppIntro.this.mVibrator.vibrate(AppIntro.this.vibrateIntensity);
                }
                AppIntro.this.pager.setCurrentItem(AppIntro.this.pager.getCurrentItem() + 1);
            }
        });
        textView2.setOnClickListener(new View.OnClickListener() { // from class: com.github.paolorotolo.appintro.AppIntro.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (AppIntro.this.isVibrateOn) {
                    AppIntro.this.mVibrator.vibrate(AppIntro.this.vibrateIntensity);
                }
                AppIntro.this.onDonePressed();
            }
        });
        this.mPagerAdapter = new PagerAdapter(super.getSupportFragmentManager(), this.fragments);
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        this.pager = viewPager;
        viewPager.setAdapter(this.mPagerAdapter);
        this.pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.github.paolorotolo.appintro.AppIntro.4
            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int i) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrolled(int i, float f, int i2) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageSelected(int i) {
                AppIntro.this.selectDot(i);
                if (i == AppIntro.this.slidesNumber - 1) {
                    textView.setVisibility(4);
                    imageView.setVisibility(8);
                    textView2.setVisibility(0);
                } else {
                    textView.setVisibility(0);
                    textView2.setVisibility(8);
                    imageView.setVisibility(0);
                }
                if (AppIntro.this.showSkip) {
                    return;
                }
                textView.setVisibility(4);
            }
        });
        init(bundle);
        loadDots();
    }

    private void loadDots() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.dotLayout);
        this.dots = new ArrayList();
        this.slidesNumber = this.fragments.size();
        for (int i = 0; i < this.slidesNumber; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.indicator_dot_grey));
            linearLayout.addView(imageView, new LinearLayout.LayoutParams(-2, -2));
            this.dots.add(imageView);
        }
        selectDot(FIRST_PAGE_NUM);
    }

    public void selectDot(int i) {
        Resources resources = getResources();
        int i2 = 0;
        while (i2 < this.fragments.size()) {
            this.dots.get(i2).setImageDrawable(resources.getDrawable(i2 == i ? R.drawable.indicator_dot_white : R.drawable.indicator_dot_grey));
            i2++;
        }
    }

    public void addSlide(Fragment fragment, Context context) {
        this.fragments.add(Fragment.instantiate(context, fragment.getClass().getName()));
        this.mPagerAdapter.notifyDataSetChanged();
    }

    public void setBarColor(int i) {
        ((LinearLayout) findViewById(R.id.bottom)).setBackgroundColor(i);
    }

    public void setSeparatorColor(int i) {
        ((TextView) findViewById(R.id.bottom_separator)).setBackgroundColor(i);
    }

    public void setSkipText(String str) {
        ((TextView) findViewById(R.id.skip)).setText(str);
    }

    public void setDoneText(String str) {
        ((TextView) findViewById(R.id.done)).setText(str);
    }

    public void showSkipButton(boolean z) {
        this.showSkip = z;
        if (z) {
            return;
        }
        ((TextView) findViewById(R.id.skip)).setVisibility(4);
    }

    public void setVibrate(boolean z) {
        this.isVibrateOn = z;
    }

    public void setVibrateIntensity(int i) {
        this.vibrateIntensity = i;
    }

    public class PagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragments;

        public PagerAdapter(FragmentManager fragmentManager, List<Fragment> list) {
            super(fragmentManager);
            this.fragments = list;
        }

        @Override // androidx.fragment.app.FragmentPagerAdapter
        public Fragment getItem(int i) {
            return this.fragments.get(i);
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public int getCount() {
            return this.fragments.size();
        }
    }
}
