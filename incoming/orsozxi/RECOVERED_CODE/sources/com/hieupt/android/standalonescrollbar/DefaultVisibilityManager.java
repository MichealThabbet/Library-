package com.hieupt.android.standalonescrollbar;

import android.view.View;
import com.hieupt.android.standalonescrollbar.VisibilityManager;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: DefaultVisibilityManager.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÀ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lcom/hieupt/android/standalonescrollbar/DefaultVisibilityManager;", "Lcom/hieupt/android/standalonescrollbar/VisibilityManager;", "()V", "android-standalone-scroll-bar_release"}, k = 1, mv = {1, 4, 1})
/* loaded from: classes2.dex */
public final class DefaultVisibilityManager implements VisibilityManager {
    public static final DefaultVisibilityManager INSTANCE = new DefaultVisibilityManager();

    private DefaultVisibilityManager() {
    }

    @Override // com.hieupt.android.standalonescrollbar.VisibilityManager
    public void hideScrollbar(View trackView, View thumbView, boolean z) {
        Intrinsics.checkNotNullParameter(trackView, "trackView");
        Intrinsics.checkNotNullParameter(thumbView, "thumbView");
        VisibilityManager.DefaultImpls.hideScrollbar(this, trackView, thumbView, z);
    }

    @Override // com.hieupt.android.standalonescrollbar.VisibilityManager
    public boolean isThumbViewShowing(View thumbView) {
        Intrinsics.checkNotNullParameter(thumbView, "thumbView");
        return VisibilityManager.DefaultImpls.isThumbViewShowing(this, thumbView);
    }

    @Override // com.hieupt.android.standalonescrollbar.VisibilityManager
    public boolean isTrackViewShowing(View trackView) {
        Intrinsics.checkNotNullParameter(trackView, "trackView");
        return VisibilityManager.DefaultImpls.isTrackViewShowing(this, trackView);
    }

    @Override // com.hieupt.android.standalonescrollbar.VisibilityManager
    public void showScrollbar(View trackView, View thumbView, boolean z) {
        Intrinsics.checkNotNullParameter(trackView, "trackView");
        Intrinsics.checkNotNullParameter(thumbView, "thumbView");
        VisibilityManager.DefaultImpls.showScrollbar(this, trackView, thumbView, z);
    }
}
