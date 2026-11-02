package com.hieupt.android.standalonescrollbar;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;
import androidx.constraintlayout.solver.widgets.analyzer.BasicMeasure;
import androidx.core.content.ContextCompat;
import androidx.core.math.MathUtils;
import com.facebook.places.model.PlaceFields;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: OrientationHelper.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b0\u0018\u00002\u00020\u0001:\u0002./B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0019\u0010\u001a\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00100\u001bH ¢\u0006\u0002\b\u001cJ\r\u0010\u001d\u001a\u00020\u0010H ¢\u0006\u0002\b\u001eJ\u0015\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\"H ¢\u0006\u0002\b#J\u0018\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020'2\u0006\u0010(\u001a\u00020'H\u0002J\u001d\u0010)\u001a\u00020%2\u0006\u0010&\u001a\u00020'2\u0006\u0010(\u001a\u00020'H ¢\u0006\u0002\b*J\r\u0010+\u001a\u00020%H ¢\u0006\u0002\b,J\b\u0010-\u001a\u00020%H\u0002R\u0014\u0010\u0005\u001a\u00020\u00068@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u0004\u0018\u00010\nX \u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\r\u001a\u0004\u0018\u00010\nX \u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\fR\u001b\u0010\u000f\u001a\u00020\u00108@X\u0080\u0084\u0002¢\u0006\f\n\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0011\u0010\u0012R\u0014\u0010\u0002\u001a\u00020\u0003X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u001b\u0010\u0017\u001a\u00020\u00108@X\u0080\u0084\u0002¢\u0006\f\n\u0004\b\u0019\u0010\u0014\u001a\u0004\b\u0018\u0010\u0012\u0082\u0001\u000201¨\u00062"}, d2 = {"Lcom/hieupt/android/standalonescrollbar/OrientationHelper;", "", "scrollBar", "Lcom/hieupt/android/standalonescrollbar/StandaloneScrollBar;", "(Lcom/hieupt/android/standalonescrollbar/StandaloneScrollBar;)V", PlaceFields.CONTEXT, "Landroid/content/Context;", "getContext$android_standalone_scroll_bar_release", "()Landroid/content/Context;", "defaultThumbDrawable", "Landroid/graphics/drawable/Drawable;", "getDefaultThumbDrawable$android_standalone_scroll_bar_release", "()Landroid/graphics/drawable/Drawable;", "defaultTrackDrawable", "getDefaultTrackDrawable$android_standalone_scroll_bar_release", "minTouchTargetSize", "", "getMinTouchTargetSize$android_standalone_scroll_bar_release", "()I", "minTouchTargetSize$delegate", "Lkotlin/Lazy;", "getScrollBar$android_standalone_scroll_bar_release", "()Lcom/hieupt/android/standalonescrollbar/StandaloneScrollBar;", "touchSlop", "getTouchSlop$android_standalone_scroll_bar_release", "touchSlop$delegate", "getThumbMeasureSpec", "Lkotlin/Pair;", "getThumbMeasureSpec$android_standalone_scroll_bar_release", "getThumbOffsetRange", "getThumbOffsetRange$android_standalone_scroll_bar_release", "onTouchEvent", "", "event", "Landroid/view/MotionEvent;", "onTouchEvent$android_standalone_scroll_bar_release", "updateBackground", "", "trackView", "Landroid/view/View;", "thumbView", "updateLayoutParams", "updateLayoutParams$android_standalone_scroll_bar_release", "updateThumbOffsetLayout", "updateThumbOffsetLayout$android_standalone_scroll_bar_release", "updateViews", "Horizontal", "Vertical", "Lcom/hieupt/android/standalonescrollbar/OrientationHelper$Vertical;", "Lcom/hieupt/android/standalonescrollbar/OrientationHelper$Horizontal;", "android-standalone-scroll-bar_release"}, k = 1, mv = {1, 4, 1})
/* loaded from: classes2.dex */
public abstract class OrientationHelper {

    /* renamed from: minTouchTargetSize$delegate, reason: from kotlin metadata */
    private final Lazy minTouchTargetSize;
    private final StandaloneScrollBar scrollBar;

    /* renamed from: touchSlop$delegate, reason: from kotlin metadata */
    private final Lazy touchSlop;

    public abstract Drawable getDefaultThumbDrawable$android_standalone_scroll_bar_release();

    public abstract Drawable getDefaultTrackDrawable$android_standalone_scroll_bar_release();

    public final int getMinTouchTargetSize$android_standalone_scroll_bar_release() {
        return ((Number) this.minTouchTargetSize.getValue()).intValue();
    }

    public abstract Pair<Integer, Integer> getThumbMeasureSpec$android_standalone_scroll_bar_release();

    public abstract int getThumbOffsetRange$android_standalone_scroll_bar_release();

    public final int getTouchSlop$android_standalone_scroll_bar_release() {
        return ((Number) this.touchSlop.getValue()).intValue();
    }

    public abstract boolean onTouchEvent$android_standalone_scroll_bar_release(MotionEvent event);

    public abstract void updateLayoutParams$android_standalone_scroll_bar_release(View trackView, View thumbView);

    public abstract void updateThumbOffsetLayout$android_standalone_scroll_bar_release();

    private OrientationHelper(StandaloneScrollBar standaloneScrollBar) {
        this.scrollBar = standaloneScrollBar;
        this.minTouchTargetSize = LazyKt.lazy(new Function0<Integer>() { // from class: com.hieupt.android.standalonescrollbar.OrientationHelper$minTouchTargetSize$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Integer invoke() {
                return Integer.valueOf(invoke2());
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final int invoke2() {
                return this.this$0.getContext$android_standalone_scroll_bar_release().getResources().getDimensionPixelSize(R.dimen.min_touch_target_size);
            }
        });
        this.touchSlop = LazyKt.lazy(new Function0<Integer>() { // from class: com.hieupt.android.standalonescrollbar.OrientationHelper$touchSlop$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Integer invoke() {
                return Integer.valueOf(invoke2());
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final int invoke2() {
                ViewConfiguration viewConfiguration = ViewConfiguration.get(this.this$0.getContext$android_standalone_scroll_bar_release());
                Intrinsics.checkNotNullExpressionValue(viewConfiguration, "ViewConfiguration.get(context)");
                return viewConfiguration.getScaledTouchSlop();
            }
        });
        updateViews();
    }

    public /* synthetic */ OrientationHelper(StandaloneScrollBar standaloneScrollBar, DefaultConstructorMarker defaultConstructorMarker) {
        this(standaloneScrollBar);
    }

    /* renamed from: getScrollBar$android_standalone_scroll_bar_release, reason: from getter */
    public final StandaloneScrollBar getScrollBar() {
        return this.scrollBar;
    }

    public final Context getContext$android_standalone_scroll_bar_release() {
        Context context = this.scrollBar.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "scrollBar.context");
        return context;
    }

    private final void updateViews() {
        updateLayoutParams$android_standalone_scroll_bar_release(this.scrollBar.getTrackView$android_standalone_scroll_bar_release(), this.scrollBar.getThumbView$android_standalone_scroll_bar_release());
        updateBackground(this.scrollBar.getTrackView$android_standalone_scroll_bar_release(), this.scrollBar.getThumbView$android_standalone_scroll_bar_release());
    }

    private final void updateBackground(View trackView, View thumbView) {
        ColorStateList colorStateList;
        ColorStateList colorStateList2;
        Drawable defaultTrackDrawable$android_standalone_scroll_bar_release = this.scrollBar.get_customTrackDrawable();
        if (defaultTrackDrawable$android_standalone_scroll_bar_release == null) {
            defaultTrackDrawable$android_standalone_scroll_bar_release = getDefaultTrackDrawable$android_standalone_scroll_bar_release();
        }
        trackView.setBackground(defaultTrackDrawable$android_standalone_scroll_bar_release);
        if (this.scrollBar.get_customTrackDrawable() == null && (colorStateList2 = this.scrollBar.get_defaultTrackTint()) != null) {
            trackView.setBackgroundTintList(colorStateList2);
        }
        Drawable defaultThumbDrawable$android_standalone_scroll_bar_release = this.scrollBar.get_customThumbDrawable();
        if (defaultThumbDrawable$android_standalone_scroll_bar_release == null) {
            defaultThumbDrawable$android_standalone_scroll_bar_release = getDefaultThumbDrawable$android_standalone_scroll_bar_release();
        }
        thumbView.setBackground(defaultThumbDrawable$android_standalone_scroll_bar_release);
        if (this.scrollBar.get_customThumbDrawable() != null || (colorStateList = this.scrollBar.get_defaultThumbTint()) == null) {
            return;
        }
        thumbView.setBackgroundTintList(colorStateList);
    }

    /* compiled from: OrientationHelper.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0019\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u000f0\u0013H\u0010¢\u0006\u0002\b\u0014J\r\u0010\u0015\u001a\u00020\u000fH\u0010¢\u0006\u0002\b\u0016J\u0015\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001aH\u0010¢\u0006\u0002\b\u001bJ\u001d\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u001fH\u0010¢\u0006\u0002\b!J\r\u0010\"\u001a\u00020\u001dH\u0010¢\u0006\u0002\b#R\u0016\u0010\u0005\u001a\u0004\u0018\u00010\u00068PX\u0090\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0016\u0010\t\u001a\u0004\u0018\u00010\u00068PX\u0090\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\bR\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006$"}, d2 = {"Lcom/hieupt/android/standalonescrollbar/OrientationHelper$Vertical;", "Lcom/hieupt/android/standalonescrollbar/OrientationHelper;", "scrollBarView", "Lcom/hieupt/android/standalonescrollbar/StandaloneScrollBar;", "(Lcom/hieupt/android/standalonescrollbar/StandaloneScrollBar;)V", "defaultThumbDrawable", "Landroid/graphics/drawable/Drawable;", "getDefaultThumbDrawable$android_standalone_scroll_bar_release", "()Landroid/graphics/drawable/Drawable;", "defaultTrackDrawable", "getDefaultTrackDrawable$android_standalone_scroll_bar_release", "downX", "", "downY", "dragStartThumbOffset", "", "dragStartY", "lastY", "getThumbMeasureSpec", "Lkotlin/Pair;", "getThumbMeasureSpec$android_standalone_scroll_bar_release", "getThumbOffsetRange", "getThumbOffsetRange$android_standalone_scroll_bar_release", "onTouchEvent", "", "event", "Landroid/view/MotionEvent;", "onTouchEvent$android_standalone_scroll_bar_release", "updateLayoutParams", "", "trackView", "Landroid/view/View;", "thumbView", "updateLayoutParams$android_standalone_scroll_bar_release", "updateThumbOffsetLayout", "updateThumbOffsetLayout$android_standalone_scroll_bar_release", "android-standalone-scroll-bar_release"}, k = 1, mv = {1, 4, 1})
    public static final class Vertical extends OrientationHelper {
        private float downX;
        private float downY;
        private int dragStartThumbOffset;
        private float dragStartY;
        private float lastY;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Vertical(StandaloneScrollBar scrollBarView) {
            super(scrollBarView, null);
            Intrinsics.checkNotNullParameter(scrollBarView, "scrollBarView");
        }

        @Override // com.hieupt.android.standalonescrollbar.OrientationHelper
        public Drawable getDefaultTrackDrawable$android_standalone_scroll_bar_release() {
            return ContextCompat.getDrawable(getContext$android_standalone_scroll_bar_release(), R.drawable.default_vertical_track);
        }

        @Override // com.hieupt.android.standalonescrollbar.OrientationHelper
        public Drawable getDefaultThumbDrawable$android_standalone_scroll_bar_release() {
            return ContextCompat.getDrawable(getContext$android_standalone_scroll_bar_release(), R.drawable.default_vertical_thumb);
        }

        @Override // com.hieupt.android.standalonescrollbar.OrientationHelper
        public void updateLayoutParams$android_standalone_scroll_bar_release(View trackView, View thumbView) {
            Intrinsics.checkNotNullParameter(trackView, "trackView");
            Intrinsics.checkNotNullParameter(thumbView, "thumbView");
            trackView.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-2, -2);
            layoutParams.gravity = 1;
            Unit unit = Unit.INSTANCE;
            thumbView.setLayoutParams(layoutParams);
        }

        @Override // com.hieupt.android.standalonescrollbar.OrientationHelper
        public int getThumbOffsetRange$android_standalone_scroll_bar_release() {
            int intrinsicHeight;
            if (getScrollBar().getThumbView$android_standalone_scroll_bar_release().getHeight() != 0) {
                intrinsicHeight = getScrollBar().getThumbView$android_standalone_scroll_bar_release().getHeight();
            } else {
                Drawable background = getScrollBar().getThumbView$android_standalone_scroll_bar_release().getBackground();
                Intrinsics.checkNotNullExpressionValue(background, "scrollBar.thumbView.background");
                intrinsicHeight = background.getIntrinsicHeight();
            }
            return ((getScrollBar().getHeight() - getScrollBar().getPaddingTop()) - getScrollBar().getPaddingBottom()) - intrinsicHeight;
        }

        @Override // com.hieupt.android.standalonescrollbar.OrientationHelper
        public void updateThumbOffsetLayout$android_standalone_scroll_bar_release() {
            int thumbOffset = getScrollBar().getThumbOffset();
            ViewExtensionsKt.updateLayout$default(getScrollBar().getThumbView$android_standalone_scroll_bar_release(), 0, thumbOffset, 0, thumbOffset + getScrollBar().getThumbView$android_standalone_scroll_bar_release().getHeight(), 5, null);
        }

        @Override // com.hieupt.android.standalonescrollbar.OrientationHelper
        public Pair<Integer, Integer> getThumbMeasureSpec$android_standalone_scroll_bar_release() {
            int iMakeMeasureSpec;
            int iMakeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(getScrollBar().getThumbView$android_standalone_scroll_bar_release().getMeasuredWidth(), BasicMeasure.EXACTLY);
            if (getScrollBar().get_thumbLength() >= 0) {
                iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(MathUtils.clamp(getScrollBar().get_thumbLength(), getScrollBar().get_minThumbLength(), getScrollBar().getTrackView$android_standalone_scroll_bar_release().getMeasuredHeight()), BasicMeasure.EXACTLY);
            } else if (getScrollBar().get_thumbLengthByTrackRatio() >= 0) {
                iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(MathUtils.clamp((int) (getScrollBar().getTrackView$android_standalone_scroll_bar_release().getMeasuredHeight() * getScrollBar().get_thumbLengthByTrackRatio()), getScrollBar().get_minThumbLength(), getScrollBar().getTrackView$android_standalone_scroll_bar_release().getMeasuredHeight()), BasicMeasure.EXACTLY);
            } else if (getScrollBar().getAutoThumbLength()) {
                iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(MathUtils.clamp((int) ((getScrollBar().getScrollableView().getViewHeight() / getScrollBar().getScrollableView().getScrollRange()) * getScrollBar().getTrackView$android_standalone_scroll_bar_release().getMeasuredHeight()), getScrollBar().get_minThumbLength(), getScrollBar().getTrackView$android_standalone_scroll_bar_release().getMeasuredHeight()), BasicMeasure.EXACTLY);
            } else {
                iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(MathUtils.clamp(getScrollBar().getThumbView$android_standalone_scroll_bar_release().getMeasuredHeight(), getScrollBar().get_minThumbLength(), getScrollBar().getTrackView$android_standalone_scroll_bar_release().getMeasuredHeight()), BasicMeasure.EXACTLY);
            }
            return new Pair<>(Integer.valueOf(iMakeMeasureSpec2), Integer.valueOf(iMakeMeasureSpec));
        }

        /* JADX WARN: Removed duplicated region for block: B:26:0x00d9  */
        @Override // com.hieupt.android.standalonescrollbar.OrientationHelper
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public boolean onTouchEvent$android_standalone_scroll_bar_release(android.view.MotionEvent r11) {
            /*
                Method dump skipped, instructions count: 392
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: com.hieupt.android.standalonescrollbar.OrientationHelper.Vertical.onTouchEvent$android_standalone_scroll_bar_release(android.view.MotionEvent):boolean");
        }
    }

    /* compiled from: OrientationHelper.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0019\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u000f0\u0013H\u0010¢\u0006\u0002\b\u0014J\r\u0010\u0015\u001a\u00020\u000fH\u0010¢\u0006\u0002\b\u0016J\u0015\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001aH\u0010¢\u0006\u0002\b\u001bJ\u001d\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u001fH\u0010¢\u0006\u0002\b!J\r\u0010\"\u001a\u00020\u001dH\u0010¢\u0006\u0002\b#R\u0016\u0010\u0005\u001a\u0004\u0018\u00010\u00068PX\u0090\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0016\u0010\t\u001a\u0004\u0018\u00010\u00068PX\u0090\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\bR\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006$"}, d2 = {"Lcom/hieupt/android/standalonescrollbar/OrientationHelper$Horizontal;", "Lcom/hieupt/android/standalonescrollbar/OrientationHelper;", "scrollBarView", "Lcom/hieupt/android/standalonescrollbar/StandaloneScrollBar;", "(Lcom/hieupt/android/standalonescrollbar/StandaloneScrollBar;)V", "defaultThumbDrawable", "Landroid/graphics/drawable/Drawable;", "getDefaultThumbDrawable$android_standalone_scroll_bar_release", "()Landroid/graphics/drawable/Drawable;", "defaultTrackDrawable", "getDefaultTrackDrawable$android_standalone_scroll_bar_release", "downX", "", "downY", "dragStartThumbOffset", "", "dragStartX", "lastX", "getThumbMeasureSpec", "Lkotlin/Pair;", "getThumbMeasureSpec$android_standalone_scroll_bar_release", "getThumbOffsetRange", "getThumbOffsetRange$android_standalone_scroll_bar_release", "onTouchEvent", "", "event", "Landroid/view/MotionEvent;", "onTouchEvent$android_standalone_scroll_bar_release", "updateLayoutParams", "", "trackView", "Landroid/view/View;", "thumbView", "updateLayoutParams$android_standalone_scroll_bar_release", "updateThumbOffsetLayout", "updateThumbOffsetLayout$android_standalone_scroll_bar_release", "android-standalone-scroll-bar_release"}, k = 1, mv = {1, 4, 1})
    public static final class Horizontal extends OrientationHelper {
        private float downX;
        private float downY;
        private int dragStartThumbOffset;
        private float dragStartX;
        private float lastX;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Horizontal(StandaloneScrollBar scrollBarView) {
            super(scrollBarView, null);
            Intrinsics.checkNotNullParameter(scrollBarView, "scrollBarView");
        }

        @Override // com.hieupt.android.standalonescrollbar.OrientationHelper
        public Drawable getDefaultTrackDrawable$android_standalone_scroll_bar_release() {
            return ContextCompat.getDrawable(getContext$android_standalone_scroll_bar_release(), R.drawable.default_horizontal_track);
        }

        @Override // com.hieupt.android.standalonescrollbar.OrientationHelper
        public Drawable getDefaultThumbDrawable$android_standalone_scroll_bar_release() {
            return ContextCompat.getDrawable(getContext$android_standalone_scroll_bar_release(), R.drawable.default_horizontal_thumb);
        }

        @Override // com.hieupt.android.standalonescrollbar.OrientationHelper
        public void updateLayoutParams$android_standalone_scroll_bar_release(View trackView, View thumbView) {
            Intrinsics.checkNotNullParameter(trackView, "trackView");
            Intrinsics.checkNotNullParameter(thumbView, "thumbView");
            trackView.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-2, -2);
            layoutParams.gravity = 16;
            Unit unit = Unit.INSTANCE;
            thumbView.setLayoutParams(layoutParams);
        }

        @Override // com.hieupt.android.standalonescrollbar.OrientationHelper
        public int getThumbOffsetRange$android_standalone_scroll_bar_release() {
            int intrinsicWidth;
            if (getScrollBar().getThumbView$android_standalone_scroll_bar_release().getWidth() != 0) {
                intrinsicWidth = getScrollBar().getThumbView$android_standalone_scroll_bar_release().getWidth();
            } else {
                Drawable background = getScrollBar().getThumbView$android_standalone_scroll_bar_release().getBackground();
                Intrinsics.checkNotNullExpressionValue(background, "scrollBar.thumbView.background");
                intrinsicWidth = background.getIntrinsicWidth();
            }
            return ((getScrollBar().getWidth() - getScrollBar().getPaddingStart()) - getScrollBar().getPaddingEnd()) - intrinsicWidth;
        }

        @Override // com.hieupt.android.standalonescrollbar.OrientationHelper
        public void updateThumbOffsetLayout$android_standalone_scroll_bar_release() {
            int thumbOffset = getScrollBar().getThumbOffset();
            int width = getScrollBar().getThumbView$android_standalone_scroll_bar_release().getWidth();
            if (ViewExtensionsKt.isLayoutRtl(getScrollBar())) {
                ViewExtensionsKt.updateLayout$default(getScrollBar().getThumbView$android_standalone_scroll_bar_release(), (((getScrollBar().getWidth() - getScrollBar().getPaddingStart()) - getScrollBar().getPaddingEnd()) - thumbOffset) - width, 0, ((getScrollBar().getWidth() - getScrollBar().getPaddingStart()) - getScrollBar().getPaddingEnd()) - thumbOffset, 0, 10, null);
            } else {
                ViewExtensionsKt.updateLayout$default(getScrollBar().getThumbView$android_standalone_scroll_bar_release(), thumbOffset, 0, thumbOffset + width, 0, 10, null);
            }
        }

        @Override // com.hieupt.android.standalonescrollbar.OrientationHelper
        public Pair<Integer, Integer> getThumbMeasureSpec$android_standalone_scroll_bar_release() {
            int iMakeMeasureSpec;
            if (getScrollBar().get_thumbLength() >= 0) {
                iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(MathUtils.clamp(getScrollBar().get_thumbLength(), getScrollBar().get_minThumbLength(), getScrollBar().getTrackView$android_standalone_scroll_bar_release().getMeasuredWidth()), BasicMeasure.EXACTLY);
            } else if (getScrollBar().get_thumbLengthByTrackRatio() >= 0) {
                iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(MathUtils.clamp((int) (getScrollBar().getTrackView$android_standalone_scroll_bar_release().getMeasuredWidth() * getScrollBar().get_thumbLengthByTrackRatio()), getScrollBar().get_minThumbLength(), getScrollBar().getTrackView$android_standalone_scroll_bar_release().getMeasuredWidth()), BasicMeasure.EXACTLY);
            } else if (getScrollBar().getAutoThumbLength()) {
                iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(MathUtils.clamp((int) ((getScrollBar().getScrollableView().getViewWidth() / getScrollBar().getScrollableView().getScrollRange()) * getScrollBar().getTrackView$android_standalone_scroll_bar_release().getMeasuredWidth()), getScrollBar().get_minThumbLength(), getScrollBar().getTrackView$android_standalone_scroll_bar_release().getMeasuredWidth()), BasicMeasure.EXACTLY);
            } else {
                iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(MathUtils.clamp(getScrollBar().getThumbView$android_standalone_scroll_bar_release().getMeasuredWidth(), getScrollBar().get_minThumbLength(), getScrollBar().getTrackView$android_standalone_scroll_bar_release().getMeasuredWidth()), BasicMeasure.EXACTLY);
            }
            return new Pair<>(Integer.valueOf(iMakeMeasureSpec), Integer.valueOf(View.MeasureSpec.makeMeasureSpec(getScrollBar().getThumbView$android_standalone_scroll_bar_release().getMeasuredHeight(), BasicMeasure.EXACTLY)));
        }

        /* JADX WARN: Removed duplicated region for block: B:34:0x0118  */
        @Override // com.hieupt.android.standalonescrollbar.OrientationHelper
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public boolean onTouchEvent$android_standalone_scroll_bar_release(android.view.MotionEvent r11) {
            /*
                Method dump skipped, instructions count: 500
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: com.hieupt.android.standalonescrollbar.OrientationHelper.Horizontal.onTouchEvent$android_standalone_scroll_bar_release(android.view.MotionEvent):boolean");
        }
    }
}
