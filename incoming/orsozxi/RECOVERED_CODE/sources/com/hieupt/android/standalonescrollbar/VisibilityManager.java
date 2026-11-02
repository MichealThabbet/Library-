package com.hieupt.android.standalonescrollbar;

import android.view.View;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: VisibilityManager.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001J \u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u0010\u0010\t\u001a\u00020\b2\u0006\u0010\u0006\u001a\u00020\u0005H\u0016J\u0010\u0010\n\u001a\u00020\b2\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J \u0010\u000b\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\bH\u0016¨\u0006\f"}, d2 = {"Lcom/hieupt/android/standalonescrollbar/VisibilityManager;", "", "hideScrollbar", "", "trackView", "Landroid/view/View;", "thumbView", "isLayoutRtl", "", "isThumbViewShowing", "isTrackViewShowing", "showScrollbar", "android-standalone-scroll-bar_release"}, k = 1, mv = {1, 4, 1})
/* loaded from: classes2.dex */
public interface VisibilityManager {
    void hideScrollbar(View trackView, View thumbView, boolean isLayoutRtl);

    boolean isThumbViewShowing(View thumbView);

    boolean isTrackViewShowing(View trackView);

    void showScrollbar(View trackView, View thumbView, boolean isLayoutRtl);

    /* compiled from: VisibilityManager.kt */
    @Metadata(bv = {1, 0, 3}, k = 3, mv = {1, 4, 1})
    public static final class DefaultImpls {
        public static boolean isTrackViewShowing(VisibilityManager visibilityManager, View trackView) {
            Intrinsics.checkNotNullParameter(trackView, "trackView");
            return trackView.getVisibility() == 0;
        }

        public static boolean isThumbViewShowing(VisibilityManager visibilityManager, View thumbView) {
            Intrinsics.checkNotNullParameter(thumbView, "thumbView");
            return thumbView.getVisibility() == 0;
        }

        public static void showScrollbar(VisibilityManager visibilityManager, View trackView, View thumbView, boolean z) {
            Intrinsics.checkNotNullParameter(trackView, "trackView");
            Intrinsics.checkNotNullParameter(thumbView, "thumbView");
            trackView.setVisibility(0);
            thumbView.setVisibility(0);
        }

        public static void hideScrollbar(VisibilityManager visibilityManager, View trackView, View thumbView, boolean z) {
            Intrinsics.checkNotNullParameter(trackView, "trackView");
            Intrinsics.checkNotNullParameter(thumbView, "thumbView");
            trackView.setVisibility(8);
            thumbView.setVisibility(8);
        }
    }
}
