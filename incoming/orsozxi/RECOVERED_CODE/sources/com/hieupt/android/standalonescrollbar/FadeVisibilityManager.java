package com.hieupt.android.standalonescrollbar;

import android.view.View;
import android.view.animation.Interpolator;
import androidx.interpolator.view.animation.FastOutLinearInInterpolator;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: FadeVisibilityManager.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u0000 \u00112\u00020\u0001:\u0001\u0011B\u0019\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J \u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u0007H\u0016J\u0010\u0010\u000e\u001a\u00020\u00072\u0006\u0010\f\u001a\u00020\u000bH\u0016J\u0010\u0010\u000f\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\u000bH\u0016J \u0010\u0010\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u0007H\u0016R\u000e\u0010\u0004\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lcom/hieupt/android/standalonescrollbar/FadeVisibilityManager;", "Lcom/hieupt/android/standalonescrollbar/VisibilityManager;", "showDuration", "", "hideDuration", "(JJ)V", "isShowingScrollbar", "", "hideScrollbar", "", "trackView", "Landroid/view/View;", "thumbView", "isLayoutRtl", "isThumbViewShowing", "isTrackViewShowing", "showScrollbar", "Companion", "android-standalone-scroll-bar_release"}, k = 1, mv = {1, 4, 1})
/* loaded from: classes2.dex */
public final class FadeVisibilityManager implements VisibilityManager {
    private static final long HIDE_DURATION_MILLIS = 200;
    private static final long SHOW_DURATION_MILLIS = 150;
    private final long hideDuration;
    private boolean isShowingScrollbar;
    private final long showDuration;
    private static final Interpolator SHOW_SCROLLBAR_INTERPOLATOR = new LinearOutSlowInInterpolator();
    private static final Interpolator HIDE_SCROLLBAR_INTERPOLATOR = new FastOutLinearInInterpolator();

    public FadeVisibilityManager() {
        this(0L, 0L, 3, null);
    }

    public FadeVisibilityManager(long j, long j2) {
        this.showDuration = j;
        this.hideDuration = j2;
        this.isShowingScrollbar = true;
    }

    public /* synthetic */ FadeVisibilityManager(long j, long j2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? 150L : j, (i & 2) != 0 ? HIDE_DURATION_MILLIS : j2);
    }

    @Override // com.hieupt.android.standalonescrollbar.VisibilityManager
    public boolean isTrackViewShowing(View trackView) {
        Intrinsics.checkNotNullParameter(trackView, "trackView");
        return trackView.getAlpha() > ((float) 0);
    }

    @Override // com.hieupt.android.standalonescrollbar.VisibilityManager
    public boolean isThumbViewShowing(View thumbView) {
        Intrinsics.checkNotNullParameter(thumbView, "thumbView");
        return thumbView.getAlpha() > ((float) 0);
    }

    @Override // com.hieupt.android.standalonescrollbar.VisibilityManager
    public void showScrollbar(View trackView, View thumbView, boolean isLayoutRtl) {
        Intrinsics.checkNotNullParameter(trackView, "trackView");
        Intrinsics.checkNotNullParameter(thumbView, "thumbView");
        if (this.isShowingScrollbar) {
            return;
        }
        this.isShowingScrollbar = true;
        trackView.animate().alpha(1.0f).setDuration(this.showDuration).setInterpolator(SHOW_SCROLLBAR_INTERPOLATOR).start();
        thumbView.animate().alpha(1.0f).setDuration(this.showDuration).setInterpolator(SHOW_SCROLLBAR_INTERPOLATOR).start();
    }

    @Override // com.hieupt.android.standalonescrollbar.VisibilityManager
    public void hideScrollbar(View trackView, View thumbView, boolean isLayoutRtl) {
        Intrinsics.checkNotNullParameter(trackView, "trackView");
        Intrinsics.checkNotNullParameter(thumbView, "thumbView");
        if (this.isShowingScrollbar) {
            this.isShowingScrollbar = false;
            trackView.animate().alpha(0.0f).setDuration(this.hideDuration).setInterpolator(HIDE_SCROLLBAR_INTERPOLATOR).start();
            thumbView.animate().alpha(0.0f).setDuration(this.hideDuration).setInterpolator(HIDE_SCROLLBAR_INTERPOLATOR).start();
        }
    }
}
