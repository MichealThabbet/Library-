package com.app.orsozoxi;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Process;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.app.orsozoxi.Fragments.RadioHomeFragment;
import com.app.orsozoxi.Fragments.ScheduleFragment;
import com.app.orsozoxi.Fragments.SuggestFragment;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;

/* loaded from: classes.dex */
public class NewRadioActivity extends BaseActivity {
    private static Activity activity;
    private static String fragmentHome;
    Fragment fragment = null;
    private String fragmentTitle = null;
    private RelativeLayout mLoadingView;
    private TextView titleTextView;

    public static void startProgress() {
    }

    @Override // com.app.orsozoxi.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) throws Resources.NotFoundException {
        super.onCreate(bundle);
        setContentView(R.layout.activity_home);
        app.updateLanguage(this, "en");
        getWindow().addFlags(128);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        initView();
        initFooter();
        this.mLoadingView = (RelativeLayout) findViewById(R.id.v_progress);
    }

    private void initView() {
        this.titleTextView = (TextView) findViewById(R.id.tv_header_title);
        displayView(2);
        fragmentHome = getString(R.string.radio_orso);
        activity = this;
    }

    public void displayView(int i) {
        if (i == 0) {
            this.fragment = new SuggestFragment();
            setFragmentTitle(getString(R.string.tab_suggest));
        } else if (i == 1) {
            this.fragment = new ScheduleFragment();
            setFragmentTitle(getString(R.string.tab_schedule));
        } else if (i == 2) {
            this.fragment = new RadioHomeFragment();
            setFragmentTitle(getString(R.string.radio_orso));
        }
        if (this.fragment != null) {
            FragmentTransaction fragmentTransactionBeginTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransactionBeginTransaction.replace(R.id.contentContainer, this.fragment, getFragmentTitle());
            fragmentTransactionBeginTransaction.commit();
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle((CharSequence) null);
            this.titleTextView.setText(getFragmentTitle());
        }
    }

    public void setFragmentTitle(String str) {
        this.fragmentTitle = str;
    }

    public String getFragmentTitle() {
        return this.fragmentTitle;
    }

    private void initFooter() throws Resources.NotFoundException {
        AHBottomNavigation aHBottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);
        AHBottomNavigationItem aHBottomNavigationItem = new AHBottomNavigationItem(R.string.radio_orso, R.drawable.ic_action_home, R.color.white);
        AHBottomNavigationItem aHBottomNavigationItem2 = new AHBottomNavigationItem(R.string.tab_schedule, R.drawable.ic_action_schedule, R.color.white);
        aHBottomNavigation.addItem(new AHBottomNavigationItem(R.string.tab_suggest, R.drawable.ic_action_suggest, R.color.white));
        aHBottomNavigation.addItem(aHBottomNavigationItem2);
        aHBottomNavigation.addItem(aHBottomNavigationItem);
        aHBottomNavigation.setDefaultBackgroundColor(Color.parseColor("#33479f"));
        aHBottomNavigation.setBehaviorTranslationEnabled(false);
        aHBottomNavigation.setAccentColor(Color.parseColor("#EE7429"));
        aHBottomNavigation.setInactiveColor(Color.parseColor("#FFFFFF"));
        aHBottomNavigation.setForceTint(true);
        aHBottomNavigation.setForceTitlesDisplay(true);
        aHBottomNavigation.setColored(false);
        aHBottomNavigation.setCurrentItem(2);
        aHBottomNavigation.setNotificationBackgroundColor(Color.parseColor("#F58220"));
        aHBottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() { // from class: com.app.orsozoxi.NewRadioActivity.1
            @Override // com.aurelhubert.ahbottomnavigation.AHBottomNavigation.OnTabSelectedListener
            public boolean onTabSelected(int i, boolean z) {
                NewRadioActivity.this.displayView(i);
                return true;
            }
        });
        aHBottomNavigation.setOnNavigationPositionListener(new AHBottomNavigation.OnNavigationPositionListener() { // from class: com.app.orsozoxi.NewRadioActivity.2
            @Override // com.aurelhubert.ahbottomnavigation.AHBottomNavigation.OnNavigationPositionListener
            public void onPositionChange(int i) {
                NewRadioActivity.this.displayView(i);
            }
        });
    }

    public void changeUI() {
        ((RadioHomeFragment) getSupportFragmentManager().findFragmentByTag(getString(R.string.radio_orso))).changeUI();
    }

    public void changeButton() {
        ((RadioHomeFragment) getSupportFragmentManager().findFragmentByTag(getString(R.string.radio_orso))).changeUI();
    }

    public void dismissProgress2() {
        findViewById(R.id.v_progress).setVisibility(8);
    }

    public static void dismissProgress() {
        Activity activity2 = activity;
        if (activity2 != null) {
            activity2.findViewById(R.id.v_progress).setVisibility(8);
        }
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        super.onBackPressed();
        if (findViewById(R.id.v_progress).getVisibility() == 0) {
            RadioHomeFragment radioHomeFragment = (RadioHomeFragment) getSupportFragmentManager().findFragmentByTag(getString(R.string.radio_orso));
            if (radioHomeFragment != null) {
                radioHomeFragment.stopPlayer();
            }
            Process.killProcess(Process.myPid());
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        super.onPause();
        if (findViewById(R.id.v_progress).getVisibility() == 0) {
            RadioHomeFragment radioHomeFragment = (RadioHomeFragment) getSupportFragmentManager().findFragmentByTag(getString(R.string.radio_orso));
            if (radioHomeFragment != null) {
                radioHomeFragment.stopPlayer();
            }
            Process.killProcess(Process.myPid());
        }
    }
}
