package it.orsozoxi.android.orsonotes;

import android.animation.ValueAnimator;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import de.greenrobot.event.EventBus;
import it.orsozoxi.android.orsonotes.async.CategoryMenuTask;
import it.orsozoxi.android.orsonotes.async.MainMenuTask;
import it.orsozoxi.android.orsonotes.async.bus.CategoriesUpdatedEvent;
import it.orsozoxi.android.orsonotes.async.bus.DynamicNavigationReadyEvent;
import it.orsozoxi.android.orsonotes.async.bus.NavigationUpdatedEvent;
import it.orsozoxi.android.orsonotes.async.bus.NavigationUpdatedNavDrawerClosedEvent;
import it.orsozoxi.android.orsonotes.async.bus.NotesLoadedEvent;
import it.orsozoxi.android.orsonotes.async.bus.NotesUpdatedEvent;
import it.orsozoxi.android.orsonotes.async.bus.SwitchFragmentEvent;
import it.orsozoxi.android.orsonotes.helpers.LogDelegate;
import it.orsozoxi.android.orsonotes.models.Category;
import it.orsozoxi.android.orsonotes.models.NavigationItem;
import it.orsozoxi.android.orsonotes.utils.Display;

/* loaded from: classes3.dex */
public class NavigationDrawerFragment extends Fragment {
    static final int ARROW = 1;
    static final int BURGER = 0;
    private boolean alreadyInitialized;
    private NotesMainActivity mActivity;
    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mDrawerToggle;

    public static boolean isDoublePanelActive() {
        return false;
    }

    @Override // androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setRetainInstance(true);
    }

    @Override // androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override // androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.fragment_navigation_drawer, viewGroup, false);
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        this.mActivity = (NotesMainActivity) getActivity();
        init();
    }

    private NotesMainActivity getMainActivity() {
        return (NotesMainActivity) getActivity();
    }

    public void onEventMainThread(DynamicNavigationReadyEvent dynamicNavigationReadyEvent) {
        if (this.alreadyInitialized) {
            this.alreadyInitialized = false;
        } else {
            refreshMenus();
        }
    }

    public void onEvent(CategoriesUpdatedEvent categoriesUpdatedEvent) {
        refreshMenus();
    }

    public void onEventAsync(NotesUpdatedEvent notesUpdatedEvent) {
        this.alreadyInitialized = false;
    }

    public void onEvent(NotesLoadedEvent notesLoadedEvent) {
        if (this.mDrawerLayout != null && !isDoublePanelActive()) {
            this.mDrawerLayout.setDrawerLockMode(0);
        }
        if (getMainActivity().getSupportFragmentManager().getBackStackEntryCount() == 0) {
            init();
        }
        refreshMenus();
        this.alreadyInitialized = true;
    }

    public void onEvent(SwitchFragmentEvent switchFragmentEvent) {
        if (SwitchFragmentEvent.Direction.CHILDREN.equals(switchFragmentEvent.direction)) {
            animateBurger(1);
        } else {
            animateBurger(0);
        }
    }

    public void onEvent(final NavigationUpdatedEvent navigationUpdatedEvent) {
        if (navigationUpdatedEvent.navigationItem.getClass().isAssignableFrom(NavigationItem.class)) {
            this.mActivity.getSupportActionBar().setTitle(((NavigationItem) navigationUpdatedEvent.navigationItem).getText());
        } else {
            this.mActivity.getSupportActionBar().setTitle(((Category) navigationUpdatedEvent.navigationItem).getName());
        }
        if (this.mDrawerLayout != null) {
            if (!isDoublePanelActive()) {
                this.mDrawerLayout.closeDrawer(8388611);
            }
            new Handler().postDelayed(new Runnable() { // from class: it.orsozoxi.android.orsonotes.-$$Lambda$NavigationDrawerFragment$wDg00nB1-YohiAshkJXOnh0a1FA
                @Override // java.lang.Runnable
                public final void run() {
                    EventBus.getDefault().post(new NavigationUpdatedNavDrawerClosedEvent(navigationUpdatedEvent.navigationItem));
                }
            }, 400L);
        }
    }

    public void init() {
        LogDelegate.v("Started navigation drawer initialization");
        DrawerLayout drawerLayout = (DrawerLayout) this.mActivity.findViewById(R.id.drawer_layout);
        this.mDrawerLayout = drawerLayout;
        drawerLayout.setFocusableInTouchMode(false);
        View viewFindViewById = getView().findViewById(R.id.left_drawer);
        viewFindViewById.setPadding(viewFindViewById.getPaddingLeft(), viewFindViewById.getPaddingTop(), viewFindViewById.getPaddingRight(), Display.getNavigationBarHeightKitkat(getActivity()));
        this.mDrawerToggle = new ActionBarDrawerToggle(this.mActivity, this.mDrawerLayout, getMainActivity().getToolbar(), R.string.drawer_open, R.string.drawer_close) { // from class: it.orsozoxi.android.orsonotes.NavigationDrawerFragment.1
            @Override // androidx.appcompat.app.ActionBarDrawerToggle, androidx.drawerlayout.widget.DrawerLayout.DrawerListener
            public void onDrawerClosed(View view) {
                NavigationDrawerFragment.this.mActivity.supportInvalidateOptionsMenu();
            }

            @Override // androidx.appcompat.app.ActionBarDrawerToggle, androidx.drawerlayout.widget.DrawerLayout.DrawerListener
            public void onDrawerOpened(View view) {
                NavigationDrawerFragment.this.mActivity.commitPending();
                NavigationDrawerFragment.this.mActivity.finishActionMode();
            }
        };
        if (isDoublePanelActive()) {
            this.mDrawerLayout.setDrawerLockMode(2);
        }
        this.mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, 8388611);
        this.mDrawerLayout.addDrawerListener(this.mDrawerToggle);
        this.mDrawerToggle.setDrawerIndicatorEnabled(true);
        LogDelegate.v("Finished navigation drawer initialization");
    }

    private void refreshMenus() {
        buildMainMenu();
        LogDelegate.v("Finished main menu initialization");
        buildCategoriesMenu();
        LogDelegate.v("Finished categories menu initialization");
        this.mDrawerToggle.syncState();
    }

    private void buildCategoriesMenu() {
        new CategoryMenuTask(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
    }

    private void buildMainMenu() {
        new MainMenuTask(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
    }

    void animateBurger(int i) {
        if (this.mDrawerToggle != null) {
            if (i == 0 || i == 1) {
                ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat((i + 1) % 2, i);
                valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: it.orsozoxi.android.orsonotes.-$$Lambda$NavigationDrawerFragment$RiLb4tscp6P2LgCEqdfNdMjVLp0
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        this.f$0.lambda$animateBurger$1$NavigationDrawerFragment(valueAnimator);
                    }
                });
                valueAnimatorOfFloat.setInterpolator(new DecelerateInterpolator());
                valueAnimatorOfFloat.setDuration(500L);
                valueAnimatorOfFloat.start();
            }
        }
    }

    public /* synthetic */ void lambda$animateBurger$1$NavigationDrawerFragment(ValueAnimator valueAnimator) {
        this.mDrawerToggle.onDrawerSlide(this.mDrawerLayout, ((Float) valueAnimator.getAnimatedValue()).floatValue());
    }
}
