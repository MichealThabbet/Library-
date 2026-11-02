package com.hieupt.android.standalonescrollbar;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import androidx.core.content.ContextCompat;
import androidx.core.math.MathUtils;
import androidx.core.view.ViewCompat;
import com.facebook.places.model.PlaceFields;
import com.hieupt.android.standalonescrollbar.OrientationHelper;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: StandaloneScrollBar.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0086\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0016\n\u0002\u0010\t\n\u0002\b\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\r\u0018\u0000 \u0090\u00012\u00020\u0001:\u0004\u0090\u0001\u0091\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\u0019\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\u0002\u0010\u0007B!\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\u000e\u0010u\u001a\u00020v2\u0006\u0010U\u001a\u00020VJ\b\u0010w\u001a\u00020vH\u0002J\b\u0010x\u001a\u00020vH\u0002J\r\u0010y\u001a\u00020\tH\u0000¢\u0006\u0002\bzJ\b\u0010{\u001a\u00020vH\u0002J\u001e\u0010|\u001a\u00020v2\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062\b\b\u0002\u0010\b\u001a\u00020\tH\u0002J\u0018\u0010}\u001a\u00020v2\u0006\u0010~\u001a\u00020\t2\u0006\u0010\u007f\u001a\u00020\tH\u0014J\u0012\u0010\u0080\u0001\u001a\u00020v2\u0007\u0010\u0081\u0001\u001a\u00020VH\u0002J\u0012\u0010\u0082\u0001\u001a\u00020v2\u0007\u0010\u0081\u0001\u001a\u00020VH\u0002J\u0013\u0010\u0083\u0001\u001a\u00020\u001e2\b\u0010\u0084\u0001\u001a\u00030\u0085\u0001H\u0016J\t\u0010\u0086\u0001\u001a\u00020vH\u0002J\u0010\u0010\u0087\u0001\u001a\u00020v2\u0007\u0010\u0088\u0001\u001a\u00020\tJ\u0017\u0010\u0089\u0001\u001a\u00020v2\u0006\u0010f\u001a\u00020\tH\u0000¢\u0006\u0003\b\u008a\u0001J\u0012\u0010\u008b\u0001\u001a\u00020v2\t\b\u0001\u0010\u008c\u0001\u001a\u00020\tJ\u0012\u0010\u008d\u0001\u001a\u00020v2\t\b\u0001\u0010\u008c\u0001\u001a\u00020\tJ\t\u0010\u008e\u0001\u001a\u00020vH\u0002J\t\u0010\u008f\u0001\u001a\u00020vH\u0002R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0010\u001a\u0004\u0018\u00010\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u000e¢\u0006\u0002\n\u0000R\u001b\u0010\u0017\u001a\u00020\u00188BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001b\u0010\u001c\u001a\u0004\b\u0019\u0010\u001aR\u001a\u0010\u001d\u001a\u00020\u001eX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010 \"\u0004\b!\u0010\"R(\u0010$\u001a\u0004\u0018\u00010\f2\b\u0010#\u001a\u0004\u0018\u00010\f8F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b%\u0010&\"\u0004\b'\u0010(R(\u0010)\u001a\u0004\u0018\u00010\f2\b\u0010#\u001a\u0004\u0018\u00010\f8F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b*\u0010&\"\u0004\b+\u0010(R(\u0010,\u001a\u0004\u0018\u00010\u000f2\b\u0010#\u001a\u0004\u0018\u00010\u000f8F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b-\u0010.\"\u0004\b/\u00100R(\u00101\u001a\u0004\u0018\u00010\u000f2\b\u0010#\u001a\u0004\u0018\u00010\u000f8F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b2\u0010.\"\u0004\b3\u00100R\u001a\u00104\u001a\u000205X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b6\u00107\"\u0004\b8\u00109R\u001a\u0010:\u001a\u00020\u001eX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b;\u0010 \"\u0004\b<\u0010\"R\u001a\u0010=\u001a\u00020\u001eX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b=\u0010 \"\u0004\b>\u0010\"R$\u0010?\u001a\u00020\u001e2\u0006\u0010#\u001a\u00020\u001e@@X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b@\u0010 \"\u0004\bA\u0010\"R\u0014\u0010B\u001a\u00020\u001e8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\bB\u0010 R\u0014\u0010C\u001a\u00020\u001e8@X\u0080\u0004¢\u0006\u0006\u001a\u0004\bD\u0010 R\u0014\u0010E\u001a\u00020\u001e8@X\u0080\u0004¢\u0006\u0006\u001a\u0004\bF\u0010 R\u000e\u0010G\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010H\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R$\u0010I\u001a\u00020\t2\u0006\u0010#\u001a\u00020\t8F@FX\u0086\u000e¢\u0006\f\u001a\u0004\bJ\u0010K\"\u0004\bL\u0010MR$\u0010N\u001a\u00020\u00132\u0006\u0010#\u001a\u00020\u00138F@FX\u0086\u000e¢\u0006\f\u001a\u0004\bO\u0010P\"\u0004\bQ\u0010RR\u000e\u0010S\u001a\u00020TX\u0082.¢\u0006\u0002\n\u0000R\u001a\u0010U\u001a\u00020VX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\bW\u0010X\"\u0004\bY\u0010ZR\u001a\u0010[\u001a\u00020\u001eX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\\\u0010 \"\u0004\b]\u0010\"R$\u0010^\u001a\u00020\t2\u0006\u0010#\u001a\u00020\t8F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b_\u0010K\"\u0004\b`\u0010MR$\u0010a\u001a\u00020\u00162\u0006\u0010#\u001a\u00020\u00168F@FX\u0086\u000e¢\u0006\f\u001a\u0004\bb\u0010c\"\u0004\bd\u0010eR\u000e\u0010f\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u001b\u0010g\u001a\u00020h8@X\u0080\u0084\u0002¢\u0006\f\n\u0004\bk\u0010\u001c\u001a\u0004\bi\u0010jR\u001b\u0010l\u001a\u00020h8@X\u0080\u0084\u0002¢\u0006\f\n\u0004\bn\u0010\u001c\u001a\u0004\bm\u0010jR\u001c\u0010o\u001a\u0004\u0018\u00010pX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bq\u0010r\"\u0004\bs\u0010t¨\u0006\u0092\u0001"}, d2 = {"Lcom/hieupt/android/standalonescrollbar/StandaloneScrollBar;", "Landroid/widget/FrameLayout;", PlaceFields.CONTEXT, "Landroid/content/Context;", "(Landroid/content/Context;)V", "attrs", "Landroid/util/AttributeSet;", "(Landroid/content/Context;Landroid/util/AttributeSet;)V", "defStyleAttr", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "_customThumbDrawable", "Landroid/graphics/drawable/Drawable;", "_customTrackDrawable", "_defaultThumbTint", "Landroid/content/res/ColorStateList;", "_defaultTrackTint", "_minThumbLength", "_orientation", "Lcom/hieupt/android/standalonescrollbar/StandaloneScrollBar$Orientation;", "_thumbLength", "_thumbLengthByTrackRatio", "", "autoHideScrollbarRunnable", "Ljava/lang/Runnable;", "getAutoHideScrollbarRunnable", "()Ljava/lang/Runnable;", "autoHideScrollbarRunnable$delegate", "Lkotlin/Lazy;", "autoThumbLength", "", "getAutoThumbLength", "()Z", "setAutoThumbLength", "(Z)V", "value", "customThumbDrawable", "getCustomThumbDrawable", "()Landroid/graphics/drawable/Drawable;", "setCustomThumbDrawable", "(Landroid/graphics/drawable/Drawable;)V", "customTrackDrawable", "getCustomTrackDrawable", "setCustomTrackDrawable", "defaultThumbTint", "getDefaultThumbTint", "()Landroid/content/res/ColorStateList;", "setDefaultThumbTint", "(Landroid/content/res/ColorStateList;)V", "defaultTrackTint", "getDefaultTrackTint", "setDefaultTrackTint", "delayBeforeAutoHide", "", "getDelayBeforeAutoHide", "()J", "setDelayBeforeAutoHide", "(J)V", "draggable", "getDraggable", "setDraggable", "isAlwaysShown", "setAlwaysShown", "isDragging", "isDragging$android_standalone_scroll_bar_release", "setDragging$android_standalone_scroll_bar_release", "isLayoutDirty", "isThumbViewShowing", "isThumbViewShowing$android_standalone_scroll_bar_release", "isTrackViewShowing", "isTrackViewShowing$android_standalone_scroll_bar_release", "lastScrollOffsetRange", "lastScrollRange", "minThumbLength", "getMinThumbLength", "()I", "setMinThumbLength", "(I)V", "orientation", "getOrientation", "()Lcom/hieupt/android/standalonescrollbar/StandaloneScrollBar$Orientation;", "setOrientation", "(Lcom/hieupt/android/standalonescrollbar/StandaloneScrollBar$Orientation;)V", "orientationHelper", "Lcom/hieupt/android/standalonescrollbar/OrientationHelper;", "scrollableView", "Lcom/hieupt/android/standalonescrollbar/ScrollableView;", "getScrollableView", "()Lcom/hieupt/android/standalonescrollbar/ScrollableView;", "setScrollableView", "(Lcom/hieupt/android/standalonescrollbar/ScrollableView;)V", "shouldShow", "getShouldShow$android_standalone_scroll_bar_release", "setShouldShow$android_standalone_scroll_bar_release", "thumbLength", "getThumbLength", "setThumbLength", "thumbLengthByTrackRatio", "getThumbLengthByTrackRatio", "()F", "setThumbLengthByTrackRatio", "(F)V", "thumbOffset", "thumbView", "Landroid/view/View;", "getThumbView$android_standalone_scroll_bar_release", "()Landroid/view/View;", "thumbView$delegate", "trackView", "getTrackView$android_standalone_scroll_bar_release", "trackView$delegate", "visibilityManager", "Lcom/hieupt/android/standalonescrollbar/VisibilityManager;", "getVisibilityManager", "()Lcom/hieupt/android/standalonescrollbar/VisibilityManager;", "setVisibilityManager", "(Lcom/hieupt/android/standalonescrollbar/VisibilityManager;)V", "attachTo", "", "autoHideScrollbar", "cancelAutoHideScrollbar", "getThumbOffset", "getThumbOffset$android_standalone_scroll_bar_release", "hideScrollbar", "init", "onMeasure", "widthMeasureSpec", "heightMeasureSpec", "onPreDraw", "caller", "onScrollChanged", "onTouchEvent", "event", "Landroid/view/MotionEvent;", "postAutoHideScrollbar", "scrollTo", "offset", "scrollToThumbOffset", "scrollToThumbOffset$android_standalone_scroll_bar_release", "setCustomThumbDrawableResource", "resId", "setCustomTrackDrawableResource", "showScrollbar", "updateScrollbarState", "Companion", "Orientation", "android-standalone-scroll-bar_release"}, k = 1, mv = {1, 4, 1})
/* loaded from: classes2.dex */
public final class StandaloneScrollBar extends FrameLayout {
    private static final long AUTO_HIDE_SCROLLBAR_DELAY_MILLIS = 1500;
    private Drawable _customThumbDrawable;
    private Drawable _customTrackDrawable;
    private ColorStateList _defaultThumbTint;
    private ColorStateList _defaultTrackTint;
    private int _minThumbLength;
    private Orientation _orientation;
    private int _thumbLength;
    private float _thumbLengthByTrackRatio;

    /* renamed from: autoHideScrollbarRunnable$delegate, reason: from kotlin metadata */
    private final Lazy autoHideScrollbarRunnable;
    private boolean autoThumbLength;
    private long delayBeforeAutoHide;
    private boolean draggable;
    private boolean isAlwaysShown;
    private boolean isDragging;
    private int lastScrollOffsetRange;
    private int lastScrollRange;
    private OrientationHelper orientationHelper;
    public ScrollableView scrollableView;
    private boolean shouldShow;
    private int thumbOffset;

    /* renamed from: thumbView$delegate, reason: from kotlin metadata */
    private final Lazy thumbView;

    /* renamed from: trackView$delegate, reason: from kotlin metadata */
    private final Lazy trackView;
    private VisibilityManager visibilityManager;

    /* compiled from: StandaloneScrollBar.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0004\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004¨\u0006\u0005"}, d2 = {"Lcom/hieupt/android/standalonescrollbar/StandaloneScrollBar$Orientation;", "", "(Ljava/lang/String;I)V", "VERTICAL", "HORIZONTAL", "android-standalone-scroll-bar_release"}, k = 1, mv = {1, 4, 1})
    public enum Orientation {
        VERTICAL,
        HORIZONTAL
    }

    @Metadata(bv = {1, 0, 3}, k = 3, mv = {1, 4, 1})
    public final /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] iArr = new int[Orientation.values().length];
            $EnumSwitchMapping$0 = iArr;
            iArr[Orientation.VERTICAL.ordinal()] = 1;
            int[] iArr2 = new int[Orientation.values().length];
            $EnumSwitchMapping$1 = iArr2;
            iArr2[Orientation.VERTICAL.ordinal()] = 1;
        }
    }

    private final Runnable getAutoHideScrollbarRunnable() {
        return (Runnable) this.autoHideScrollbarRunnable.getValue();
    }

    public final View getThumbView$android_standalone_scroll_bar_release() {
        return (View) this.thumbView.getValue();
    }

    public final View getTrackView$android_standalone_scroll_bar_release() {
        return (View) this.trackView.getValue();
    }

    /* compiled from: StandaloneScrollBar.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\b\u0004"}, d2 = {"<anonymous>", "", "p1", "Lcom/hieupt/android/standalonescrollbar/ScrollableView;", "invoke"}, k = 3, mv = {1, 4, 1})
    /* renamed from: com.hieupt.android.standalonescrollbar.StandaloneScrollBar$attachTo$1, reason: invalid class name */
    static final /* synthetic */ class AnonymousClass1 extends FunctionReferenceImpl implements Function1<ScrollableView, Unit> {
        AnonymousClass1(StandaloneScrollBar standaloneScrollBar) {
            super(1, standaloneScrollBar, StandaloneScrollBar.class, "onScrollChanged", "onScrollChanged(Lcom/hieupt/android/standalonescrollbar/ScrollableView;)V", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(ScrollableView scrollableView) {
            invoke2(scrollableView);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(ScrollableView p1) {
            Intrinsics.checkNotNullParameter(p1, "p1");
            ((StandaloneScrollBar) this.receiver).onScrollChanged(p1);
        }
    }

    /* compiled from: StandaloneScrollBar.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\b\u0004"}, d2 = {"<anonymous>", "", "p1", "Lcom/hieupt/android/standalonescrollbar/ScrollableView;", "invoke"}, k = 3, mv = {1, 4, 1})
    /* renamed from: com.hieupt.android.standalonescrollbar.StandaloneScrollBar$attachTo$2, reason: invalid class name */
    static final /* synthetic */ class AnonymousClass2 extends FunctionReferenceImpl implements Function1<ScrollableView, Unit> {
        AnonymousClass2(StandaloneScrollBar standaloneScrollBar) {
            super(1, standaloneScrollBar, StandaloneScrollBar.class, "onPreDraw", "onPreDraw(Lcom/hieupt/android/standalonescrollbar/ScrollableView;)V", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(ScrollableView scrollableView) {
            invoke2(scrollableView);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(ScrollableView p1) {
            Intrinsics.checkNotNullParameter(p1, "p1");
            ((StandaloneScrollBar) this.receiver).onPreDraw(p1);
        }
    }

    public static final /* synthetic */ OrientationHelper access$getOrientationHelper$p(StandaloneScrollBar standaloneScrollBar) {
        OrientationHelper orientationHelper = standaloneScrollBar.orientationHelper;
        if (orientationHelper == null) {
            Intrinsics.throwUninitializedPropertyAccessException("orientationHelper");
        }
        return orientationHelper;
    }

    private final boolean isLayoutDirty() {
        int i = this.lastScrollRange;
        ScrollableView scrollableView = this.scrollableView;
        if (scrollableView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("scrollableView");
        }
        if (i == scrollableView.getScrollRange()) {
            int i2 = this.lastScrollOffsetRange;
            ScrollableView scrollableView2 = this.scrollableView;
            if (scrollableView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("scrollableView");
            }
            if (i2 == scrollableView2.getScrollOffsetRange()) {
                return false;
            }
        }
        return true;
    }

    public final ScrollableView getScrollableView() {
        ScrollableView scrollableView = this.scrollableView;
        if (scrollableView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("scrollableView");
        }
        return scrollableView;
    }

    public final void setScrollableView(ScrollableView scrollableView) {
        Intrinsics.checkNotNullParameter(scrollableView, "<set-?>");
        this.scrollableView = scrollableView;
    }

    /* renamed from: getShouldShow$android_standalone_scroll_bar_release, reason: from getter */
    public final boolean getShouldShow() {
        return this.shouldShow;
    }

    public final void setShouldShow$android_standalone_scroll_bar_release(boolean z) {
        this.shouldShow = z;
    }

    /* renamed from: isDragging$android_standalone_scroll_bar_release, reason: from getter */
    public final boolean getIsDragging() {
        return this.isDragging;
    }

    public final void setDragging$android_standalone_scroll_bar_release(boolean z) {
        if (this.isDragging != z) {
            this.isDragging = z;
            if (z) {
                getParent().requestDisallowInterceptTouchEvent(true);
            }
            getTrackView$android_standalone_scroll_bar_release().setPressed(z);
            getThumbView$android_standalone_scroll_bar_release().setPressed(z);
            if (z) {
                cancelAutoHideScrollbar();
                showScrollbar();
            } else {
                postAutoHideScrollbar();
            }
        }
    }

    public final boolean isTrackViewShowing$android_standalone_scroll_bar_release() {
        VisibilityManager visibilityManager = this.visibilityManager;
        return visibilityManager != null ? visibilityManager.isTrackViewShowing(getTrackView$android_standalone_scroll_bar_release()) : DefaultVisibilityManager.INSTANCE.isTrackViewShowing(getTrackView$android_standalone_scroll_bar_release());
    }

    public final boolean isThumbViewShowing$android_standalone_scroll_bar_release() {
        VisibilityManager visibilityManager = this.visibilityManager;
        return visibilityManager != null ? visibilityManager.isThumbViewShowing(getThumbView$android_standalone_scroll_bar_release()) : DefaultVisibilityManager.INSTANCE.isThumbViewShowing(getThumbView$android_standalone_scroll_bar_release());
    }

    /* renamed from: getOrientation, reason: from getter */
    public final Orientation get_orientation() {
        return this._orientation;
    }

    public final void setOrientation(Orientation value) {
        OrientationHelper.Vertical vertical;
        OrientationHelper.Vertical vertical2;
        Intrinsics.checkNotNullParameter(value, "value");
        if (value != this._orientation) {
            this._orientation = value;
            if (WhenMappings.$EnumSwitchMapping$0[value.ordinal()] == 1) {
                vertical2 = new OrientationHelper.Vertical(this);
            } else {
                vertical2 = new OrientationHelper.Horizontal(this);
            }
            this.orientationHelper = vertical2;
            requestLayout();
        }
        if (this.orientationHelper == null) {
            if (WhenMappings.$EnumSwitchMapping$1[value.ordinal()] == 1) {
                vertical = new OrientationHelper.Vertical(this);
            } else {
                vertical = new OrientationHelper.Horizontal(this);
            }
            this.orientationHelper = vertical;
        }
    }

    public final VisibilityManager getVisibilityManager() {
        return this.visibilityManager;
    }

    public final void setVisibilityManager(VisibilityManager visibilityManager) {
        this.visibilityManager = visibilityManager;
    }

    /* renamed from: getCustomTrackDrawable, reason: from getter */
    public final Drawable get_customTrackDrawable() {
        return this._customTrackDrawable;
    }

    public final void setCustomTrackDrawable(Drawable drawable) {
        this._customTrackDrawable = drawable;
        getTrackView$android_standalone_scroll_bar_release().setBackground(drawable);
    }

    /* renamed from: getCustomThumbDrawable, reason: from getter */
    public final Drawable get_customThumbDrawable() {
        return this._customThumbDrawable;
    }

    public final void setCustomThumbDrawable(Drawable drawable) {
        this._customThumbDrawable = drawable;
        getThumbView$android_standalone_scroll_bar_release().setBackground(drawable);
    }

    /* renamed from: getDefaultTrackTint, reason: from getter */
    public final ColorStateList get_defaultTrackTint() {
        return this._defaultTrackTint;
    }

    public final void setDefaultTrackTint(ColorStateList colorStateList) {
        this._defaultTrackTint = colorStateList;
        if (this._customTrackDrawable == null) {
            getTrackView$android_standalone_scroll_bar_release().setBackgroundTintList(colorStateList);
        }
    }

    /* renamed from: getDefaultThumbTint, reason: from getter */
    public final ColorStateList get_defaultThumbTint() {
        return this._defaultThumbTint;
    }

    public final void setDefaultThumbTint(ColorStateList colorStateList) {
        this._defaultThumbTint = colorStateList;
        if (this._customThumbDrawable == null) {
            getThumbView$android_standalone_scroll_bar_release().setBackgroundTintList(colorStateList);
        }
    }

    public final boolean getDraggable() {
        return this.draggable;
    }

    public final void setDraggable(boolean z) {
        this.draggable = z;
    }

    /* renamed from: isAlwaysShown, reason: from getter */
    public final boolean getIsAlwaysShown() {
        return this.isAlwaysShown;
    }

    public final void setAlwaysShown(boolean z) {
        this.isAlwaysShown = z;
    }

    public final long getDelayBeforeAutoHide() {
        return this.delayBeforeAutoHide;
    }

    public final void setDelayBeforeAutoHide(long j) {
        this.delayBeforeAutoHide = j;
    }

    /* renamed from: getMinThumbLength, reason: from getter */
    public final int get_minThumbLength() {
        return this._minThumbLength;
    }

    public final void setMinThumbLength(int i) {
        int iMax = Math.max(i, 0);
        if (iMax != this._minThumbLength) {
            this._minThumbLength = iMax;
            requestLayout();
        }
    }

    /* renamed from: getThumbLength, reason: from getter */
    public final int get_thumbLength() {
        return this._thumbLength;
    }

    public final void setThumbLength(int i) {
        if (i != this._thumbLength) {
            this._thumbLength = i;
            requestLayout();
        }
    }

    /* renamed from: getThumbLengthByTrackRatio, reason: from getter */
    public final float get_thumbLengthByTrackRatio() {
        return this._thumbLengthByTrackRatio;
    }

    public final void setThumbLengthByTrackRatio(float f) {
        if (f != this._thumbLengthByTrackRatio) {
            this._thumbLengthByTrackRatio = f;
            requestLayout();
        }
    }

    public final boolean getAutoThumbLength() {
        return this.autoThumbLength;
    }

    public final void setAutoThumbLength(boolean z) {
        this.autoThumbLength = z;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public StandaloneScrollBar(Context context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        this.autoHideScrollbarRunnable = LazyKt.lazy(new Function0<Runnable>() { // from class: com.hieupt.android.standalonescrollbar.StandaloneScrollBar$autoHideScrollbarRunnable$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Runnable invoke() {
                return new Runnable() { // from class: com.hieupt.android.standalonescrollbar.StandaloneScrollBar$autoHideScrollbarRunnable$2.1
                    @Override // java.lang.Runnable
                    public final void run() {
                        StandaloneScrollBar$autoHideScrollbarRunnable$2.this.this$0.autoHideScrollbar();
                    }
                };
            }
        });
        this.lastScrollRange = Integer.MIN_VALUE;
        this.lastScrollOffsetRange = Integer.MIN_VALUE;
        this._orientation = Orientation.VERTICAL;
        this._thumbLength = Integer.MIN_VALUE;
        this._thumbLengthByTrackRatio = Float.NaN;
        this.trackView = LazyKt.lazy(new Function0<FrameLayout>() { // from class: com.hieupt.android.standalonescrollbar.StandaloneScrollBar$trackView$2
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final FrameLayout invoke() {
                return new FrameLayout(this.this$0.getContext());
            }
        });
        this.thumbView = LazyKt.lazy(new Function0<FrameLayout>() { // from class: com.hieupt.android.standalonescrollbar.StandaloneScrollBar$thumbView$2
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final FrameLayout invoke() {
                return new FrameLayout(this.this$0.getContext());
            }
        });
        this.draggable = true;
        this.delayBeforeAutoHide = AUTO_HIDE_SCROLLBAR_DELAY_MILLIS;
        init$default(this, null, 0, 3, null);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public StandaloneScrollBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        Intrinsics.checkNotNullParameter(context, "context");
        this.autoHideScrollbarRunnable = LazyKt.lazy(new Function0<Runnable>() { // from class: com.hieupt.android.standalonescrollbar.StandaloneScrollBar$autoHideScrollbarRunnable$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Runnable invoke() {
                return new Runnable() { // from class: com.hieupt.android.standalonescrollbar.StandaloneScrollBar$autoHideScrollbarRunnable$2.1
                    @Override // java.lang.Runnable
                    public final void run() {
                        StandaloneScrollBar$autoHideScrollbarRunnable$2.this.this$0.autoHideScrollbar();
                    }
                };
            }
        });
        this.lastScrollRange = Integer.MIN_VALUE;
        this.lastScrollOffsetRange = Integer.MIN_VALUE;
        this._orientation = Orientation.VERTICAL;
        this._thumbLength = Integer.MIN_VALUE;
        this._thumbLengthByTrackRatio = Float.NaN;
        this.trackView = LazyKt.lazy(new Function0<FrameLayout>() { // from class: com.hieupt.android.standalonescrollbar.StandaloneScrollBar$trackView$2
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final FrameLayout invoke() {
                return new FrameLayout(this.this$0.getContext());
            }
        });
        this.thumbView = LazyKt.lazy(new Function0<FrameLayout>() { // from class: com.hieupt.android.standalonescrollbar.StandaloneScrollBar$thumbView$2
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final FrameLayout invoke() {
                return new FrameLayout(this.this$0.getContext());
            }
        });
        this.draggable = true;
        this.delayBeforeAutoHide = AUTO_HIDE_SCROLLBAR_DELAY_MILLIS;
        init$default(this, attributeSet, 0, 2, null);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public StandaloneScrollBar(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        Intrinsics.checkNotNullParameter(context, "context");
        this.autoHideScrollbarRunnable = LazyKt.lazy(new Function0<Runnable>() { // from class: com.hieupt.android.standalonescrollbar.StandaloneScrollBar$autoHideScrollbarRunnable$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Runnable invoke() {
                return new Runnable() { // from class: com.hieupt.android.standalonescrollbar.StandaloneScrollBar$autoHideScrollbarRunnable$2.1
                    @Override // java.lang.Runnable
                    public final void run() {
                        StandaloneScrollBar$autoHideScrollbarRunnable$2.this.this$0.autoHideScrollbar();
                    }
                };
            }
        });
        this.lastScrollRange = Integer.MIN_VALUE;
        this.lastScrollOffsetRange = Integer.MIN_VALUE;
        this._orientation = Orientation.VERTICAL;
        this._thumbLength = Integer.MIN_VALUE;
        this._thumbLengthByTrackRatio = Float.NaN;
        this.trackView = LazyKt.lazy(new Function0<FrameLayout>() { // from class: com.hieupt.android.standalonescrollbar.StandaloneScrollBar$trackView$2
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final FrameLayout invoke() {
                return new FrameLayout(this.this$0.getContext());
            }
        });
        this.thumbView = LazyKt.lazy(new Function0<FrameLayout>() { // from class: com.hieupt.android.standalonescrollbar.StandaloneScrollBar$thumbView$2
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final FrameLayout invoke() {
                return new FrameLayout(this.this$0.getContext());
            }
        });
        this.draggable = true;
        this.delayBeforeAutoHide = AUTO_HIDE_SCROLLBAR_DELAY_MILLIS;
        init(attributeSet, i);
    }

    static /* synthetic */ void init$default(StandaloneScrollBar standaloneScrollBar, AttributeSet attributeSet, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            attributeSet = (AttributeSet) null;
        }
        if ((i2 & 2) != 0) {
            i = 0;
        }
        standaloneScrollBar.init(attributeSet, i);
    }

    private final void init(AttributeSet attrs, int defStyleAttr) {
        Orientation orientation;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "context");
        TypedArray typedArrayObtainStyledAttributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.StandaloneScrollBar, defStyleAttr, 0);
        Intrinsics.checkNotNullExpressionValue(typedArrayObtainStyledAttributes, "context.theme.obtainStyl…,\n            0\n        )");
        this._customTrackDrawable = typedArrayObtainStyledAttributes.getDrawable(R.styleable.StandaloneScrollBar_scrollbarTrackDrawable);
        this._customThumbDrawable = typedArrayObtainStyledAttributes.getDrawable(R.styleable.StandaloneScrollBar_scrollbarThumbDrawable);
        this._defaultTrackTint = typedArrayObtainStyledAttributes.getColorStateList(R.styleable.StandaloneScrollBar_scrollbarDefaultTrackTint);
        this._defaultThumbTint = typedArrayObtainStyledAttributes.getColorStateList(R.styleable.StandaloneScrollBar_scrollbarDefaultThumbTint);
        if (typedArrayObtainStyledAttributes.getInt(R.styleable.StandaloneScrollBar_scrollbarOrientation, 0) == 0) {
            orientation = Orientation.VERTICAL;
        } else {
            orientation = Orientation.HORIZONTAL;
        }
        setOrientation(orientation);
        this.isAlwaysShown = typedArrayObtainStyledAttributes.getBoolean(R.styleable.StandaloneScrollBar_scrollbarAlwaysShow, false);
        this.delayBeforeAutoHide = typedArrayObtainStyledAttributes.getInt(R.styleable.StandaloneScrollBar_scrollbarDelayBeforeAutoHideDuration, (int) AUTO_HIDE_SCROLLBAR_DELAY_MILLIS);
        this._minThumbLength = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.StandaloneScrollBar_scrollbarMinThumbLength, 0);
        this._thumbLength = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.StandaloneScrollBar_scrollbarThumbLength, Integer.MIN_VALUE);
        this._thumbLengthByTrackRatio = typedArrayObtainStyledAttributes.getFloat(R.styleable.StandaloneScrollBar_scrollbarThumbLengthByTrackRatio, Float.NaN);
        this.autoThumbLength = typedArrayObtainStyledAttributes.getBoolean(R.styleable.StandaloneScrollBar_scrollbarAutoThumbLength, false);
        this.draggable = typedArrayObtainStyledAttributes.getBoolean(R.styleable.StandaloneScrollBar_scrollbarDraggable, true);
        this.visibilityManager = new FadeVisibilityManager(0L, 0L, 3, null);
        addView(getTrackView$android_standalone_scroll_bar_release());
        addView(getThumbView$android_standalone_scroll_bar_release());
        autoHideScrollbar();
        Unit unit = Unit.INSTANCE;
        typedArrayObtainStyledAttributes.recycle();
    }

    private final void postAutoHideScrollbar() {
        cancelAutoHideScrollbar();
        if (this.isAlwaysShown) {
            return;
        }
        postDelayed(getAutoHideScrollbarRunnable(), this.delayBeforeAutoHide);
    }

    private final void cancelAutoHideScrollbar() {
        removeCallbacks(getAutoHideScrollbarRunnable());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void autoHideScrollbar() {
        if (this.isDragging) {
            return;
        }
        hideScrollbar();
    }

    private final void showScrollbar() {
        VisibilityManager visibilityManager = this.visibilityManager;
        if (visibilityManager != null) {
            visibilityManager.showScrollbar(getTrackView$android_standalone_scroll_bar_release(), getThumbView$android_standalone_scroll_bar_release(), ViewExtensionsKt.isLayoutRtl(this));
        } else {
            DefaultVisibilityManager.INSTANCE.showScrollbar(getTrackView$android_standalone_scroll_bar_release(), getThumbView$android_standalone_scroll_bar_release(), ViewExtensionsKt.isLayoutRtl(this));
        }
    }

    private final void hideScrollbar() {
        VisibilityManager visibilityManager = this.visibilityManager;
        if (visibilityManager != null) {
            visibilityManager.hideScrollbar(getTrackView$android_standalone_scroll_bar_release(), getThumbView$android_standalone_scroll_bar_release(), ViewExtensionsKt.isLayoutRtl(this));
        } else {
            DefaultVisibilityManager.INSTANCE.hideScrollbar(getTrackView$android_standalone_scroll_bar_release(), getThumbView$android_standalone_scroll_bar_release(), ViewExtensionsKt.isLayoutRtl(this));
        }
    }

    public final void attachTo(ScrollableView scrollableView) {
        Intrinsics.checkNotNullParameter(scrollableView, "scrollableView");
        this.scrollableView = scrollableView;
        if (scrollableView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("scrollableView");
        }
        StandaloneScrollBar standaloneScrollBar = this;
        scrollableView.addOnScrollChangedListener(new AnonymousClass1(standaloneScrollBar));
        ScrollableView scrollableView2 = this.scrollableView;
        if (scrollableView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("scrollableView");
        }
        scrollableView2.addOnDraw(new AnonymousClass2(standaloneScrollBar));
        if (this.isAlwaysShown) {
            return;
        }
        autoHideScrollbar();
    }

    public final void scrollTo(int offset) {
        ScrollableView scrollableView = this.scrollableView;
        if (scrollableView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("scrollableView");
        }
        scrollableView.scrollTo(offset);
    }

    public final void setCustomTrackDrawableResource(int resId) {
        setCustomTrackDrawable(ContextCompat.getDrawable(getContext(), resId));
    }

    public final void setCustomThumbDrawableResource(int resId) {
        setCustomThumbDrawable(ContextCompat.getDrawable(getContext(), resId));
    }

    /* renamed from: getThumbOffset$android_standalone_scroll_bar_release, reason: from getter */
    public final int getThumbOffset() {
        return this.thumbOffset;
    }

    public final void scrollToThumbOffset$android_standalone_scroll_bar_release(int thumbOffset) {
        OrientationHelper orientationHelper = this.orientationHelper;
        if (orientationHelper == null) {
            Intrinsics.throwUninitializedPropertyAccessException("orientationHelper");
        }
        int thumbOffsetRange$android_standalone_scroll_bar_release = orientationHelper.getThumbOffsetRange$android_standalone_scroll_bar_release();
        int iClamp = MathUtils.clamp(thumbOffset, 0, thumbOffsetRange$android_standalone_scroll_bar_release);
        ScrollableView scrollableView = this.scrollableView;
        if (scrollableView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("scrollableView");
        }
        scrollTo((scrollableView.getScrollOffsetRange() * iClamp) / thumbOffsetRange$android_standalone_scroll_bar_release);
    }

    private final void updateScrollbarState() {
        ScrollableView scrollableView = this.scrollableView;
        if (scrollableView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("scrollableView");
        }
        int scrollOffsetRange = scrollableView.getScrollOffsetRange();
        int scrollOffset = 0;
        boolean z = scrollOffsetRange > 0;
        this.shouldShow = z;
        if (z) {
            OrientationHelper orientationHelper = this.orientationHelper;
            if (orientationHelper == null) {
                Intrinsics.throwUninitializedPropertyAccessException("orientationHelper");
            }
            int thumbOffsetRange$android_standalone_scroll_bar_release = orientationHelper.getThumbOffsetRange$android_standalone_scroll_bar_release();
            ScrollableView scrollableView2 = this.scrollableView;
            if (scrollableView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("scrollableView");
            }
            scrollOffset = (thumbOffsetRange$android_standalone_scroll_bar_release * scrollableView2.getScrollOffset()) / scrollOffsetRange;
        }
        this.thumbOffset = scrollOffset;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onScrollChanged(ScrollableView caller) {
        ScrollableView scrollableView = this.scrollableView;
        if (scrollableView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("scrollableView");
        }
        if (caller == scrollableView) {
            updateScrollbarState();
            if (this.shouldShow) {
                showScrollbar();
                postAutoHideScrollbar();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onPreDraw(ScrollableView caller) {
        ScrollableView scrollableView = this.scrollableView;
        if (scrollableView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("scrollableView");
        }
        if (caller == scrollableView) {
            if (this.autoThumbLength && isLayoutDirty()) {
                requestLayout();
            }
            ScrollableView scrollableView2 = this.scrollableView;
            if (scrollableView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("scrollableView");
            }
            this.lastScrollRange = scrollableView2.getScrollRange();
            ScrollableView scrollableView3 = this.scrollableView;
            if (scrollableView3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("scrollableView");
            }
            this.lastScrollOffsetRange = scrollableView3.getScrollOffsetRange();
            updateScrollbarState();
            if (this.shouldShow) {
                if (ViewCompat.isLaidOut(this) && !isLayoutRequested()) {
                    access$getOrientationHelper$p(this).updateThumbOffsetLayout$android_standalone_scroll_bar_release();
                } else {
                    addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.hieupt.android.standalonescrollbar.StandaloneScrollBar$onPreDraw$$inlined$doOnLayout$1
                        @Override // android.view.View.OnLayoutChangeListener
                        public void onLayoutChange(View view, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                            Intrinsics.checkParameterIsNotNull(view, "view");
                            view.removeOnLayoutChangeListener(this);
                            StandaloneScrollBar.access$getOrientationHelper$p(this.this$0).updateThumbOffsetLayout$android_standalone_scroll_bar_release();
                        }
                    });
                }
            }
        }
    }

    @Override // android.widget.FrameLayout, android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (get_thumbLength() >= 0 || get_thumbLengthByTrackRatio() >= 0 || this.autoThumbLength || get_minThumbLength() > 0) {
            OrientationHelper orientationHelper = this.orientationHelper;
            if (orientationHelper == null) {
                Intrinsics.throwUninitializedPropertyAccessException("orientationHelper");
            }
            Pair<Integer, Integer> thumbMeasureSpec$android_standalone_scroll_bar_release = orientationHelper.getThumbMeasureSpec$android_standalone_scroll_bar_release();
            getThumbView$android_standalone_scroll_bar_release().measure(thumbMeasureSpec$android_standalone_scroll_bar_release.component1().intValue(), thumbMeasureSpec$android_standalone_scroll_bar_release.component2().intValue());
        }
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (!this.draggable) {
            return false;
        }
        OrientationHelper orientationHelper = this.orientationHelper;
        if (orientationHelper == null) {
            Intrinsics.throwUninitializedPropertyAccessException("orientationHelper");
        }
        return orientationHelper.onTouchEvent$android_standalone_scroll_bar_release(event);
    }
}
