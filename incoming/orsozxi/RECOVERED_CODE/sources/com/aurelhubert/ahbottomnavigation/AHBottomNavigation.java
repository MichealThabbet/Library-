package com.aurelhubert.ahbottomnavigation;

import android.animation.Animator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class AHBottomNavigation extends FrameLayout {
    public static final int CURRENT_ITEM_NONE = -1;
    private static final int MAX_ITEMS = 5;
    private static final int MIN_ITEMS = 3;
    private static String TAG = "AHBottomNavigation";
    public static final int UPDATE_ALL_NOTIFICATIONS = -1;
    private View backgroundColorView;
    private boolean behaviorTranslationEnabled;
    private AHBottomNavigationBehavior<AHBottomNavigation> bottomNavigationBehavior;
    private int bottomNavigationHeight;
    private Animator circleRevealAnim;
    private boolean colored;
    private int coloredTitleColorActive;
    private int coloredTitleColorInactive;
    private Context context;
    private int currentColor;
    private int currentItem;
    private int defaultBackgroundColor;
    private boolean forceTint;
    private boolean forceTitlesDisplay;
    private boolean hideBottomNavigationWithAnimation;
    private boolean isBehaviorTranslationSet;
    private int itemActiveColor;
    private int itemInactiveColor;
    private ArrayList<AHBottomNavigationItem> items;
    private OnNavigationPositionListener navigationPositionListener;
    private boolean needHideBottomNavigation;
    private float notSelectedItemWidth;
    private int notificationActiveMarginLeft;
    private int notificationActiveMarginTop;
    private int notificationBackgroundColor;
    private Drawable notificationBackgroundDrawable;
    private int notificationInactiveMarginLeft;
    private int notificationInactiveMarginTop;
    private int notificationTextColor;
    private Typeface notificationTypeface;
    private String[] notifications;
    private Resources resources;
    private float selectedItemWidth;
    private OnTabSelectedListener tabSelectedListener;
    private float titleActiveTextSize;
    private int titleColorActive;
    private int titleColorInactive;
    private float titleInactiveTextSize;
    private Typeface titleTypeface;
    private ArrayList<View> views;

    public interface OnNavigationPositionListener {
        void onPositionChange(int i);
    }

    public interface OnTabSelectedListener {
        boolean onTabSelected(int i, boolean z);
    }

    public AHBottomNavigation(Context context) {
        super(context);
        this.items = new ArrayList<>();
        this.views = new ArrayList<>();
        this.colored = false;
        this.notifications = new String[]{"", "", "", "", ""};
        this.isBehaviorTranslationSet = false;
        this.currentItem = 0;
        this.currentColor = 0;
        this.behaviorTranslationEnabled = true;
        this.needHideBottomNavigation = false;
        this.hideBottomNavigationWithAnimation = false;
        this.defaultBackgroundColor = -1;
        this.forceTint = false;
        this.forceTitlesDisplay = false;
        init(context);
    }

    public AHBottomNavigation(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.items = new ArrayList<>();
        this.views = new ArrayList<>();
        this.colored = false;
        this.notifications = new String[]{"", "", "", "", ""};
        this.isBehaviorTranslationSet = false;
        this.currentItem = 0;
        this.currentColor = 0;
        this.behaviorTranslationEnabled = true;
        this.needHideBottomNavigation = false;
        this.hideBottomNavigationWithAnimation = false;
        this.defaultBackgroundColor = -1;
        this.forceTint = false;
        this.forceTitlesDisplay = false;
        init(context);
    }

    public AHBottomNavigation(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.items = new ArrayList<>();
        this.views = new ArrayList<>();
        this.colored = false;
        this.notifications = new String[]{"", "", "", "", ""};
        this.isBehaviorTranslationSet = false;
        this.currentItem = 0;
        this.currentColor = 0;
        this.behaviorTranslationEnabled = true;
        this.needHideBottomNavigation = false;
        this.hideBottomNavigationWithAnimation = false;
        this.defaultBackgroundColor = -1;
        this.forceTint = false;
        this.forceTitlesDisplay = false;
        init(context);
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) throws Resources.NotFoundException {
        super.onSizeChanged(i, i2, i3, i4);
        createItems();
    }

    @Override // android.widget.FrameLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        if (this.isBehaviorTranslationSet) {
            return;
        }
        setBehaviorTranslationEnabled(this.behaviorTranslationEnabled);
        this.isBehaviorTranslationSet = true;
    }

    @Override // android.view.View
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("superState", super.onSaveInstanceState());
        bundle.putInt("current_item", this.currentItem);
        bundle.putStringArray("notifications", this.notifications);
        return bundle;
    }

    @Override // android.view.View
    protected void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof Bundle) {
            Bundle bundle = (Bundle) parcelable;
            this.currentItem = bundle.getInt("current_item");
            this.notifications = bundle.getStringArray("notifications");
            parcelable = bundle.getParcelable("superState");
        }
        super.onRestoreInstanceState(parcelable);
    }

    private void init(Context context) {
        this.context = context;
        this.resources = context.getResources();
        this.notificationTextColor = ContextCompat.getColor(context, android.R.color.white);
        this.bottomNavigationHeight = (int) this.resources.getDimension(R.dimen.bottom_navigation_height);
        this.titleColorActive = ContextCompat.getColor(context, R.color.colorBottomNavigationAccent);
        this.titleColorInactive = ContextCompat.getColor(context, R.color.colorBottomNavigationInactive);
        this.coloredTitleColorActive = ContextCompat.getColor(context, R.color.colorBottomNavigationActiveColored);
        this.coloredTitleColorInactive = ContextCompat.getColor(context, R.color.colorBottomNavigationInactiveColored);
        this.itemActiveColor = this.titleColorActive;
        this.itemInactiveColor = this.titleColorInactive;
        this.notificationActiveMarginLeft = (int) this.resources.getDimension(R.dimen.bottom_navigation_notification_margin_left_active);
        this.notificationInactiveMarginLeft = (int) this.resources.getDimension(R.dimen.bottom_navigation_notification_margin_left);
        this.notificationActiveMarginTop = (int) this.resources.getDimension(R.dimen.bottom_navigation_notification_margin_top_active);
        this.notificationInactiveMarginTop = (int) this.resources.getDimension(R.dimen.bottom_navigation_notification_margin_top);
        ViewCompat.setElevation(this, this.resources.getDimension(R.dimen.bottom_navigation_elevation));
        setClipToPadding(false);
        setLayoutParams(new ViewGroup.LayoutParams(-1, this.bottomNavigationHeight));
    }

    private void createItems() throws Resources.NotFoundException {
        if (this.items.size() < 3) {
            Log.w(TAG, "The items list should have at least 3 items");
        } else if (this.items.size() > 5) {
            Log.w(TAG, "The items list should not have more than 5 items");
        }
        int dimension = (int) this.resources.getDimension(R.dimen.bottom_navigation_height);
        removeAllViews();
        this.views.clear();
        this.backgroundColorView = new View(this.context);
        if (Build.VERSION.SDK_INT >= 21) {
            addView(this.backgroundColorView, new FrameLayout.LayoutParams(-1, dimension));
        }
        LinearLayout linearLayout = new LinearLayout(this.context);
        linearLayout.setOrientation(0);
        linearLayout.setGravity(17);
        addView(linearLayout, new FrameLayout.LayoutParams(-1, dimension));
        if (this.items.size() == 3 || this.forceTitlesDisplay) {
            createClassicItems(linearLayout);
        } else {
            createSmallItems(linearLayout);
        }
        post(new Runnable() { // from class: com.aurelhubert.ahbottomnavigation.AHBottomNavigation.1
            @Override // java.lang.Runnable
            public void run() {
                AHBottomNavigation.this.requestLayout();
            }
        });
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x008e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void createClassicItems(android.widget.LinearLayout r18) throws android.content.res.Resources.NotFoundException {
        /*
            Method dump skipped, instructions count: 478
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.aurelhubert.ahbottomnavigation.AHBottomNavigation.createClassicItems(android.widget.LinearLayout):void");
    }

    private void createSmallItems(LinearLayout linearLayout) throws Resources.NotFoundException {
        LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService("layout_inflater");
        float dimension = this.resources.getDimension(R.dimen.bottom_navigation_height);
        float dimension2 = this.resources.getDimension(R.dimen.bottom_navigation_small_inactive_min_width);
        float dimension3 = this.resources.getDimension(R.dimen.bottom_navigation_small_inactive_max_width);
        int width = getWidth();
        if (width == 0 || this.items.size() == 0) {
            return;
        }
        float size = width / this.items.size();
        if (size >= dimension2) {
            dimension2 = size > dimension3 ? dimension3 : size;
        }
        int dimension4 = (int) this.resources.getDimension(R.dimen.bottom_navigation_small_margin_top_active);
        float dimension5 = this.resources.getDimension(R.dimen.bottom_navigation_small_selected_width_difference);
        this.selectedItemWidth = (this.items.size() * dimension5) + dimension2;
        float f = dimension2 - dimension5;
        this.notSelectedItemWidth = f;
        final int i = 0;
        while (i < this.items.size()) {
            AHBottomNavigationItem aHBottomNavigationItem = this.items.get(i);
            View viewInflate = layoutInflater.inflate(R.layout.bottom_navigation_small_item, (ViewGroup) this, false);
            ImageView imageView = (ImageView) viewInflate.findViewById(R.id.bottom_navigation_small_item_icon);
            TextView textView = (TextView) viewInflate.findViewById(R.id.bottom_navigation_small_item_title);
            TextView textView2 = (TextView) viewInflate.findViewById(R.id.bottom_navigation_notification);
            imageView.setImageDrawable(aHBottomNavigationItem.getDrawable(this.context));
            textView.setText(aHBottomNavigationItem.getTitle(this.context));
            float f2 = this.titleActiveTextSize;
            if (f2 != 0.0f) {
                textView.setTextSize(0, f2);
            }
            Typeface typeface = this.titleTypeface;
            if (typeface != null) {
                textView.setTypeface(typeface);
            }
            if (i == this.currentItem) {
                imageView.setSelected(true);
                if (viewInflate.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
                    ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) imageView.getLayoutParams();
                    marginLayoutParams.setMargins(marginLayoutParams.leftMargin, dimension4, marginLayoutParams.rightMargin, marginLayoutParams.bottomMargin);
                    ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) textView2.getLayoutParams();
                    marginLayoutParams2.setMargins(this.notificationActiveMarginLeft, this.notificationActiveMarginTop, marginLayoutParams2.rightMargin, marginLayoutParams2.bottomMargin);
                    viewInflate.requestLayout();
                }
            } else {
                imageView.setSelected(false);
                ViewGroup.MarginLayoutParams marginLayoutParams3 = (ViewGroup.MarginLayoutParams) textView2.getLayoutParams();
                marginLayoutParams3.setMargins(this.notificationInactiveMarginLeft, this.notificationInactiveMarginTop, marginLayoutParams3.rightMargin, marginLayoutParams3.bottomMargin);
            }
            if (this.colored) {
                if (i == this.currentItem) {
                    setBackgroundColor(aHBottomNavigationItem.getColor(this.context));
                    this.currentColor = aHBottomNavigationItem.getColor(this.context);
                }
            } else {
                setBackgroundColor(this.defaultBackgroundColor);
            }
            imageView.setImageDrawable(AHHelper.getTintDrawable(this.items.get(i).getDrawable(this.context), this.currentItem == i ? this.itemActiveColor : this.itemInactiveColor, this.forceTint));
            textView.setTextColor(this.currentItem == i ? this.itemActiveColor : this.itemInactiveColor);
            textView.setAlpha(this.currentItem == i ? 1.0f : 0.0f);
            viewInflate.setOnClickListener(new View.OnClickListener() { // from class: com.aurelhubert.ahbottomnavigation.AHBottomNavigation.3
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    AHBottomNavigation.this.updateSmallItems(i, true);
                }
            });
            linearLayout.addView(viewInflate, new FrameLayout.LayoutParams(i == this.currentItem ? (int) this.selectedItemWidth : (int) f, (int) dimension));
            this.views.add(viewInflate);
            i++;
        }
        updateNotifications(true, -1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0053  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void updateItems(final int r19, boolean r20) throws android.content.res.Resources.NotFoundException {
        /*
            Method dump skipped, instructions count: 554
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.aurelhubert.ahbottomnavigation.AHBottomNavigation.updateItems(int, boolean):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateSmallItems(final int i, boolean z) {
        if (this.currentItem == i) {
            OnTabSelectedListener onTabSelectedListener = this.tabSelectedListener;
            if (onTabSelectedListener == null || !z) {
                return;
            }
            onTabSelectedListener.onTabSelected(i, true);
            return;
        }
        OnTabSelectedListener onTabSelectedListener2 = this.tabSelectedListener;
        if (onTabSelectedListener2 == null || !z || onTabSelectedListener2.onTabSelected(i, false)) {
            int dimension = (int) this.resources.getDimension(R.dimen.bottom_navigation_small_margin_top_active);
            int dimension2 = (int) this.resources.getDimension(R.dimen.bottom_navigation_small_margin_top);
            for (int i2 = 0; i2 < this.views.size(); i2++) {
                if (i2 == i) {
                    FrameLayout frameLayout = (FrameLayout) this.views.get(i).findViewById(R.id.bottom_navigation_small_container);
                    TextView textView = (TextView) this.views.get(i).findViewById(R.id.bottom_navigation_small_item_title);
                    ImageView imageView = (ImageView) this.views.get(i).findViewById(R.id.bottom_navigation_small_item_icon);
                    TextView textView2 = (TextView) this.views.get(i).findViewById(R.id.bottom_navigation_notification);
                    imageView.setSelected(true);
                    AHHelper.updateTopMargin(imageView, dimension2, dimension);
                    AHHelper.updateLeftMargin(textView2, this.notificationInactiveMarginLeft, this.notificationActiveMarginLeft);
                    AHHelper.updateTopMargin(textView2, this.notificationInactiveMarginTop, this.notificationActiveMarginTop);
                    AHHelper.updateTextColor(textView, this.itemInactiveColor, this.itemActiveColor);
                    AHHelper.updateAlpha(textView, 0.0f, 1.0f);
                    AHHelper.updateWidth(frameLayout, this.notSelectedItemWidth, this.selectedItemWidth);
                    AHHelper.updateDrawableColor(this.context, this.items.get(i).getDrawable(this.context), imageView, this.itemInactiveColor, this.itemActiveColor, this.forceTint);
                    if (Build.VERSION.SDK_INT >= 21 && this.colored) {
                        int iMax = Math.max(getWidth(), getHeight());
                        int x = ((int) this.views.get(i).getX()) + (this.views.get(i).getWidth() / 2);
                        int height = this.views.get(i).getHeight() / 2;
                        Animator animator = this.circleRevealAnim;
                        if (animator != null && animator.isRunning()) {
                            this.circleRevealAnim.cancel();
                            setBackgroundColor(this.items.get(i).getColor(this.context));
                            this.backgroundColorView.setBackgroundColor(0);
                        }
                        Animator animatorCreateCircularReveal = ViewAnimationUtils.createCircularReveal(this.backgroundColorView, x, height, 0.0f, iMax);
                        this.circleRevealAnim = animatorCreateCircularReveal;
                        animatorCreateCircularReveal.setStartDelay(5L);
                        this.circleRevealAnim.addListener(new Animator.AnimatorListener() { // from class: com.aurelhubert.ahbottomnavigation.AHBottomNavigation.5
                            @Override // android.animation.Animator.AnimatorListener
                            public void onAnimationCancel(Animator animator2) {
                            }

                            @Override // android.animation.Animator.AnimatorListener
                            public void onAnimationRepeat(Animator animator2) {
                            }

                            @Override // android.animation.Animator.AnimatorListener
                            public void onAnimationStart(Animator animator2) {
                                AHBottomNavigation.this.backgroundColorView.setBackgroundColor(((AHBottomNavigationItem) AHBottomNavigation.this.items.get(i)).getColor(AHBottomNavigation.this.context));
                            }

                            @Override // android.animation.Animator.AnimatorListener
                            public void onAnimationEnd(Animator animator2) {
                                AHBottomNavigation aHBottomNavigation = AHBottomNavigation.this;
                                aHBottomNavigation.setBackgroundColor(((AHBottomNavigationItem) aHBottomNavigation.items.get(i)).getColor(AHBottomNavigation.this.context));
                                AHBottomNavigation.this.backgroundColorView.setBackgroundColor(0);
                            }
                        });
                        this.circleRevealAnim.start();
                    } else if (this.colored) {
                        AHHelper.updateViewBackgroundColor(this, this.currentColor, this.items.get(i).getColor(this.context));
                    } else {
                        setBackgroundColor(this.defaultBackgroundColor);
                        this.backgroundColorView.setBackgroundColor(0);
                    }
                } else {
                    int i3 = this.currentItem;
                    if (i2 == i3) {
                        View viewFindViewById = this.views.get(i3).findViewById(R.id.bottom_navigation_small_container);
                        TextView textView3 = (TextView) this.views.get(this.currentItem).findViewById(R.id.bottom_navigation_small_item_title);
                        ImageView imageView2 = (ImageView) this.views.get(this.currentItem).findViewById(R.id.bottom_navigation_small_item_icon);
                        TextView textView4 = (TextView) this.views.get(this.currentItem).findViewById(R.id.bottom_navigation_notification);
                        imageView2.setSelected(false);
                        AHHelper.updateTopMargin(imageView2, dimension, dimension2);
                        AHHelper.updateLeftMargin(textView4, this.notificationActiveMarginLeft, this.notificationInactiveMarginLeft);
                        AHHelper.updateTopMargin(textView4, this.notificationActiveMarginTop, this.notificationInactiveMarginTop);
                        AHHelper.updateTextColor(textView3, this.itemActiveColor, this.itemInactiveColor);
                        AHHelper.updateAlpha(textView3, 1.0f, 0.0f);
                        AHHelper.updateWidth(viewFindViewById, this.selectedItemWidth, this.notSelectedItemWidth);
                        AHHelper.updateDrawableColor(this.context, this.items.get(this.currentItem).getDrawable(this.context), imageView2, this.itemActiveColor, this.itemInactiveColor, this.forceTint);
                    }
                }
            }
            this.currentItem = i;
            if (i > 0 && i < this.items.size()) {
                this.currentColor = this.items.get(this.currentItem).getColor(this.context);
            } else if (this.currentItem == -1) {
                setBackgroundColor(this.defaultBackgroundColor);
                this.backgroundColorView.setBackgroundColor(0);
            }
        }
    }

    private void updateNotifications(boolean z, int i) {
        for (int i2 = 0; i2 < this.views.size(); i2++) {
            if (i == -1 || i == i2) {
                TextView textView = (TextView) this.views.get(i2).findViewById(R.id.bottom_navigation_notification);
                boolean z2 = !textView.getText().toString().equals(String.valueOf(this.notifications[i2]));
                if (z) {
                    textView.setTextColor(this.notificationTextColor);
                    Typeface typeface = this.notificationTypeface;
                    if (typeface != null) {
                        textView.setTypeface(typeface);
                    } else {
                        textView.setTypeface(null, 1);
                    }
                    if (this.notificationBackgroundDrawable != null) {
                        if (Build.VERSION.SDK_INT >= 16) {
                            textView.setBackground(this.notificationBackgroundDrawable.getConstantState().newDrawable());
                        } else {
                            textView.setBackgroundDrawable(this.notificationBackgroundDrawable);
                        }
                    } else if (this.notificationBackgroundColor != 0) {
                        Drawable drawable = ContextCompat.getDrawable(this.context, R.drawable.notification_background);
                        if (Build.VERSION.SDK_INT >= 16) {
                            textView.setBackground(AHHelper.getTintDrawable(drawable, this.notificationBackgroundColor, this.forceTint));
                        } else {
                            textView.setBackgroundDrawable(AHHelper.getTintDrawable(drawable, this.notificationBackgroundColor, this.forceTint));
                        }
                    }
                }
                if (this.notifications[i2].length() == 0 && textView.getText().length() > 0) {
                    textView.setText("");
                    if (z2) {
                        textView.animate().scaleX(0.0f).scaleY(0.0f).alpha(0.0f).setInterpolator(new AccelerateInterpolator()).setDuration(150L).start();
                    }
                } else if (this.notifications[i2].length() > 0) {
                    textView.setText(String.valueOf(this.notifications[i2]));
                    if (z2) {
                        textView.setScaleX(0.0f);
                        textView.setScaleY(0.0f);
                        textView.animate().scaleX(1.0f).scaleY(1.0f).alpha(1.0f).setInterpolator(new OvershootInterpolator()).setDuration(150L).start();
                    }
                }
            }
        }
    }

    public void addItem(AHBottomNavigationItem aHBottomNavigationItem) throws Resources.NotFoundException {
        if (this.items.size() > 5) {
            Log.w(TAG, "The items list should not have more than 5 items");
        }
        this.items.add(aHBottomNavigationItem);
        createItems();
    }

    public void addItems(List<AHBottomNavigationItem> list) throws Resources.NotFoundException {
        if (list.size() > 5 || this.items.size() + list.size() > 5) {
            Log.w(TAG, "The items list should not have more than 5 items");
        }
        this.items.addAll(list);
        createItems();
    }

    public void removeItemAtIndex(int i) throws Resources.NotFoundException {
        if (i < this.items.size()) {
            this.items.remove(i);
            createItems();
        }
    }

    public void removeAllItems() throws Resources.NotFoundException {
        this.items.clear();
        createItems();
    }

    public void refresh() throws Resources.NotFoundException {
        createItems();
    }

    public int getItemsCount() {
        return this.items.size();
    }

    public boolean isColored() {
        return this.colored;
    }

    public void setColored(boolean z) throws Resources.NotFoundException {
        this.colored = z;
        this.itemActiveColor = z ? this.coloredTitleColorActive : this.titleColorActive;
        this.itemInactiveColor = z ? this.coloredTitleColorInactive : this.titleColorInactive;
        createItems();
    }

    public int getDefaultBackgroundColor() {
        return this.defaultBackgroundColor;
    }

    public void setDefaultBackgroundColor(int i) throws Resources.NotFoundException {
        this.defaultBackgroundColor = i;
        createItems();
    }

    public int getAccentColor() {
        return this.itemActiveColor;
    }

    public void setAccentColor(int i) throws Resources.NotFoundException {
        this.titleColorActive = i;
        this.itemActiveColor = i;
        createItems();
    }

    public int getInactiveColor() {
        return this.itemInactiveColor;
    }

    public void setInactiveColor(int i) throws Resources.NotFoundException {
        this.titleColorInactive = i;
        this.itemInactiveColor = i;
        createItems();
    }

    public void setColoredModeColors(int i, int i2) throws Resources.NotFoundException {
        this.coloredTitleColorActive = i;
        this.coloredTitleColorInactive = i2;
        createItems();
    }

    public void setTitleTypeface(Typeface typeface) throws Resources.NotFoundException {
        this.titleTypeface = typeface;
        createItems();
    }

    public void setTitleTextSize(float f, float f2) throws Resources.NotFoundException {
        this.titleActiveTextSize = f;
        this.titleInactiveTextSize = f2;
        createItems();
    }

    public AHBottomNavigationItem getItem(int i) {
        if (i < 0 || i > this.items.size() - 1) {
            Log.w(TAG, "The position is out of bounds of the items (" + this.items.size() + " elements)");
        }
        return this.items.get(i);
    }

    public int getCurrentItem() {
        return this.currentItem;
    }

    public void setCurrentItem(int i) throws Resources.NotFoundException {
        setCurrentItem(i, true);
    }

    public void setCurrentItem(int i, boolean z) throws Resources.NotFoundException {
        if (i >= this.items.size()) {
            Log.w(TAG, "The position is out of bounds of the items (" + this.items.size() + " elements)");
            return;
        }
        if (this.items.size() == 3 || this.forceTitlesDisplay) {
            updateItems(i, z);
        } else {
            updateSmallItems(i, z);
        }
    }

    public boolean isBehaviorTranslationEnabled() {
        return this.behaviorTranslationEnabled;
    }

    public void setBehaviorTranslationEnabled(boolean z) {
        this.behaviorTranslationEnabled = z;
        if (getParent() instanceof CoordinatorLayout) {
            ViewGroup.LayoutParams layoutParams = getLayoutParams();
            AHBottomNavigationBehavior<AHBottomNavigation> aHBottomNavigationBehavior = this.bottomNavigationBehavior;
            if (aHBottomNavigationBehavior == null) {
                this.bottomNavigationBehavior = new AHBottomNavigationBehavior<>(z);
            } else {
                aHBottomNavigationBehavior.setBehaviorTranslationEnabled(z);
            }
            OnNavigationPositionListener onNavigationPositionListener = this.navigationPositionListener;
            if (onNavigationPositionListener != null) {
                this.bottomNavigationBehavior.setOnNavigationPositionListener(onNavigationPositionListener);
            }
            ((CoordinatorLayout.LayoutParams) layoutParams).setBehavior(this.bottomNavigationBehavior);
            if (this.needHideBottomNavigation) {
                this.needHideBottomNavigation = false;
                this.bottomNavigationBehavior.hideView(this, this.bottomNavigationHeight, this.hideBottomNavigationWithAnimation);
            }
        }
    }

    public void hideBottomNavigation() {
        hideBottomNavigation(true);
    }

    public void hideBottomNavigation(boolean z) {
        AHBottomNavigationBehavior<AHBottomNavigation> aHBottomNavigationBehavior = this.bottomNavigationBehavior;
        if (aHBottomNavigationBehavior != null) {
            aHBottomNavigationBehavior.hideView(this, this.bottomNavigationHeight, z);
        } else if (getParent() instanceof CoordinatorLayout) {
            this.needHideBottomNavigation = true;
            this.hideBottomNavigationWithAnimation = z;
        } else {
            ViewCompat.animate(this).translationY(this.bottomNavigationHeight).setInterpolator(new LinearOutSlowInInterpolator()).setDuration(z ? 300L : 0L).start();
        }
    }

    public void restoreBottomNavigation() {
        restoreBottomNavigation(true);
    }

    public void restoreBottomNavigation(boolean z) {
        AHBottomNavigationBehavior<AHBottomNavigation> aHBottomNavigationBehavior = this.bottomNavigationBehavior;
        if (aHBottomNavigationBehavior != null) {
            aHBottomNavigationBehavior.resetOffset(this, z);
        } else {
            ViewCompat.animate(this).translationY(0.0f).setInterpolator(new LinearOutSlowInInterpolator()).setDuration(z ? 300L : 0L).start();
        }
    }

    public boolean isForceTint() {
        return this.forceTint;
    }

    public void setForceTint(boolean z) throws Resources.NotFoundException {
        this.forceTint = z;
        createItems();
    }

    public boolean isForceTitlesDisplay() {
        return this.forceTitlesDisplay;
    }

    public void setForceTitlesDisplay(boolean z) throws Resources.NotFoundException {
        this.forceTitlesDisplay = z;
        createItems();
    }

    public void setOnTabSelectedListener(OnTabSelectedListener onTabSelectedListener) {
        this.tabSelectedListener = onTabSelectedListener;
    }

    public void removeOnTabSelectedListener() {
        this.tabSelectedListener = null;
    }

    public void setOnNavigationPositionListener(OnNavigationPositionListener onNavigationPositionListener) {
        this.navigationPositionListener = onNavigationPositionListener;
        AHBottomNavigationBehavior<AHBottomNavigation> aHBottomNavigationBehavior = this.bottomNavigationBehavior;
        if (aHBottomNavigationBehavior != null) {
            aHBottomNavigationBehavior.setOnNavigationPositionListener(onNavigationPositionListener);
        }
    }

    public void removeOnNavigationPositionListener() {
        this.navigationPositionListener = null;
        AHBottomNavigationBehavior<AHBottomNavigation> aHBottomNavigationBehavior = this.bottomNavigationBehavior;
        if (aHBottomNavigationBehavior != null) {
            aHBottomNavigationBehavior.removeOnNavigationPositionListener();
        }
    }

    @Deprecated
    public void setNotification(int i, int i2) {
        if (i2 < 0 || i2 > this.items.size() - 1) {
            Log.w(TAG, "The position is out of bounds of the items (" + this.items.size() + " elements)");
            return;
        }
        this.notifications[i2] = i == 0 ? "" : String.valueOf(i);
        updateNotifications(false, i2);
    }

    public void setNotification(String str, int i) {
        this.notifications[i] = str;
        updateNotifications(false, i);
    }

    public void setNotificationTextColor(int i) {
        this.notificationTextColor = i;
        updateNotifications(true, -1);
    }

    public void setNotificationTextColorResource(int i) {
        this.notificationTextColor = ContextCompat.getColor(this.context, i);
        updateNotifications(true, -1);
    }

    public void setNotificationBackground(Drawable drawable) {
        this.notificationBackgroundDrawable = drawable;
        updateNotifications(true, -1);
    }

    public void setNotificationBackgroundColor(int i) {
        this.notificationBackgroundColor = i;
        updateNotifications(true, -1);
    }

    public void setNotificationBackgroundColorResource(int i) {
        this.notificationBackgroundColor = ContextCompat.getColor(this.context, i);
        updateNotifications(true, -1);
    }

    public void setNotificationTypeface(Typeface typeface) {
        this.notificationTypeface = typeface;
        updateNotifications(true, -1);
    }

    public void setNotificationMarginLeft(int i, int i2) throws Resources.NotFoundException {
        this.notificationActiveMarginLeft = i;
        this.notificationInactiveMarginLeft = i2;
        createItems();
    }

    public void setUseElevation(boolean z) {
        ViewCompat.setElevation(this, z ? this.resources.getDimension(R.dimen.bottom_navigation_elevation) : 0.0f);
        setClipToPadding(false);
    }

    public void setUseElevation(boolean z, float f) {
        if (!z) {
            f = 0.0f;
        }
        ViewCompat.setElevation(this, f);
        setClipToPadding(false);
    }
}
