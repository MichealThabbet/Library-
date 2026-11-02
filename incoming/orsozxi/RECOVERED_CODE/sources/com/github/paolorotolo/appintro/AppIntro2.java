package com.github.paolorotolo.appintro;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/* loaded from: classes.dex */
public abstract class AppIntro2 extends FragmentActivity {
    private static int FIRST_PAGE_NUM;
    private List<ImageView> dots;
    private PagerAdapter mPagerAdapter;
    private Vibrator mVibrator;
    private ViewPager pager;
    private int slidesNumber;
    private List<Fragment> fragments = new Vector();
    private boolean isVibrateOn = false;
    private int vibrateIntensity = 20;

    public abstract void init(Bundle bundle);

    public abstract void onDonePressed();

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected final void onCreate(Bundle bundle) throws Resources.NotFoundException {
        super.onCreate(bundle);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.intro_layout2);
        final ImageView imageView = (ImageView) findViewById(R.id.next);
        final ImageView imageView2 = (ImageView) findViewById(R.id.done);
        this.mVibrator = (Vibrator) getSystemService("vibrator");
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.github.paolorotolo.appintro.AppIntro2.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) throws Resources.NotFoundException {
                if (AppIntro2.this.isVibrateOn) {
                    AppIntro2.this.mVibrator.vibrate(AppIntro2.this.vibrateIntensity);
                }
                AppIntro2.this.pager.setCurrentItem(AppIntro2.this.pager.getCurrentItem() + 1);
            }
        });
        imageView2.setOnClickListener(new View.OnClickListener() { // from class: com.github.paolorotolo.appintro.AppIntro2.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (AppIntro2.this.isVibrateOn) {
                    AppIntro2.this.mVibrator.vibrate(AppIntro2.this.vibrateIntensity);
                }
                AppIntro2.this.onDonePressed();
            }
        });
        this.mPagerAdapter = new PagerAdapter(super.getSupportFragmentManager(), this.fragments);
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        this.pager = viewPager;
        viewPager.setAdapter(this.mPagerAdapter);
        this.pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.github.paolorotolo.appintro.AppIntro2.3
            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int i) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrolled(int i, float f, int i2) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageSelected(int i) {
                AppIntro2.this.selectDot(i);
                if (i == AppIntro2.this.slidesNumber - 1) {
                    imageView.setVisibility(8);
                    imageView2.setVisibility(0);
                } else {
                    imageView2.setVisibility(8);
                    imageView.setVisibility(0);
                }
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
