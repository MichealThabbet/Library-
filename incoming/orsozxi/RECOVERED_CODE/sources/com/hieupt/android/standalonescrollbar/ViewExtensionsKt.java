package com.hieupt.android.standalonescrollbar;

import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.hieupt.android.standalonescrollbar.StandaloneScrollBar;
import com.hieupt.android.standalonescrollbar.view.HorizontalScrollView2;
import com.hieupt.android.standalonescrollbar.view.NestedScrollView2;
import com.hieupt.android.standalonescrollbar.view.ScrollView2;
import com.hieupt.android.standalonescrollbar.view.WebView2;
import com.hieupt.android.standalonescrollbar.viewhelper.HorizontalRecyclerViewHelper;
import com.hieupt.android.standalonescrollbar.viewhelper.HorizontalScrollViewHelper;
import com.hieupt.android.standalonescrollbar.viewhelper.HorizontalWebViewViewHelper;
import com.hieupt.android.standalonescrollbar.viewhelper.VerticalNestedScrollViewHelper;
import com.hieupt.android.standalonescrollbar.viewhelper.VerticalRecyclerViewHelper;
import com.hieupt.android.standalonescrollbar.viewhelper.VerticalScrollViewHelper;
import com.hieupt.android.standalonescrollbar.viewhelper.VerticalWebViewViewHelper;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ViewExtensions.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000B\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\u001a\u0012\u0010\u0004\u001a\u00020\u0005*\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b\u001a\u0012\u0010\u0004\u001a\u00020\u0005*\u00020\u00062\u0006\u0010\t\u001a\u00020\n\u001a\u0012\u0010\u0004\u001a\u00020\u0005*\u00020\u00062\u0006\u0010\t\u001a\u00020\u000b\u001a\u0012\u0010\u0004\u001a\u00020\u0005*\u00020\u00062\u0006\u0010\t\u001a\u00020\f\u001a\u001a\u0010\u0004\u001a\u00020\u0005*\u00020\u00062\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010\u001a2\u0010\u0011\u001a\u00020\u0005*\u00020\u00022\b\b\u0002\u0010\u0012\u001a\u00020\u00132\b\b\u0002\u0010\u0014\u001a\u00020\u00132\b\b\u0002\u0010\u0015\u001a\u00020\u00132\b\b\u0002\u0010\u0016\u001a\u00020\u0013\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0000\u0010\u0003¨\u0006\u0017"}, d2 = {"isLayoutRtl", "", "Landroid/view/View;", "(Landroid/view/View;)Z", "attachTo", "", "Lcom/hieupt/android/standalonescrollbar/StandaloneScrollBar;", "recyclerView", "Landroidx/recyclerview/widget/RecyclerView;", "scrollView", "Lcom/hieupt/android/standalonescrollbar/view/HorizontalScrollView2;", "Lcom/hieupt/android/standalonescrollbar/view/NestedScrollView2;", "Lcom/hieupt/android/standalonescrollbar/view/ScrollView2;", "webView", "Lcom/hieupt/android/standalonescrollbar/view/WebView2;", "orientation", "Lcom/hieupt/android/standalonescrollbar/StandaloneScrollBar$Orientation;", "updateLayout", "left", "", "top", "right", "bottom", "android-standalone-scroll-bar_release"}, k = 2, mv = {1, 4, 1})
/* loaded from: classes2.dex */
public final class ViewExtensionsKt {

    @Metadata(bv = {1, 0, 3}, k = 3, mv = {1, 4, 1})
    public final /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[StandaloneScrollBar.Orientation.values().length];
            $EnumSwitchMapping$0 = iArr;
            iArr[StandaloneScrollBar.Orientation.VERTICAL.ordinal()] = 1;
            $EnumSwitchMapping$0[StandaloneScrollBar.Orientation.HORIZONTAL.ordinal()] = 2;
        }
    }

    public static final boolean isLayoutRtl(View isLayoutRtl) {
        Intrinsics.checkNotNullParameter(isLayoutRtl, "$this$isLayoutRtl");
        return isLayoutRtl.getLayoutDirection() == 1;
    }

    public static /* synthetic */ void updateLayout$default(View view, int i, int i2, int i3, int i4, int i5, Object obj) {
        if ((i5 & 1) != 0) {
            i = view.getLeft();
        }
        if ((i5 & 2) != 0) {
            i2 = view.getTop();
        }
        if ((i5 & 4) != 0) {
            i3 = view.getRight();
        }
        if ((i5 & 8) != 0) {
            i4 = view.getBottom();
        }
        updateLayout(view, i, i2, i3, i4);
    }

    public static final void updateLayout(View updateLayout, int i, int i2, int i3, int i4) {
        Intrinsics.checkNotNullParameter(updateLayout, "$this$updateLayout");
        updateLayout.layout(i, i2, i3, i4);
    }

    public static final void attachTo(StandaloneScrollBar attachTo, RecyclerView recyclerView) {
        Intrinsics.checkNotNullParameter(attachTo, "$this$attachTo");
        Intrinsics.checkNotNullParameter(recyclerView, "recyclerView");
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            int orientation = ((LinearLayoutManager) layoutManager).getOrientation();
            if (orientation == 0) {
                attachTo.attachTo(new HorizontalRecyclerViewHelper(recyclerView));
                return;
            } else {
                if (orientation != 1) {
                    return;
                }
                attachTo.attachTo(new VerticalRecyclerViewHelper(recyclerView));
                return;
            }
        }
        throw new IllegalArgumentException("LayoutManager must be instance of LinearLayoutManager and have to be set before attach with StandaloneScrollBar");
    }

    public static final void attachTo(StandaloneScrollBar attachTo, NestedScrollView2 scrollView) {
        Intrinsics.checkNotNullParameter(attachTo, "$this$attachTo");
        Intrinsics.checkNotNullParameter(scrollView, "scrollView");
        attachTo.attachTo(new VerticalNestedScrollViewHelper(scrollView));
    }

    public static final void attachTo(StandaloneScrollBar attachTo, ScrollView2 scrollView) {
        Intrinsics.checkNotNullParameter(attachTo, "$this$attachTo");
        Intrinsics.checkNotNullParameter(scrollView, "scrollView");
        attachTo.attachTo(new VerticalScrollViewHelper(scrollView));
    }

    public static final void attachTo(StandaloneScrollBar attachTo, HorizontalScrollView2 scrollView) {
        Intrinsics.checkNotNullParameter(attachTo, "$this$attachTo");
        Intrinsics.checkNotNullParameter(scrollView, "scrollView");
        attachTo.attachTo(new HorizontalScrollViewHelper(scrollView));
    }

    public static final void attachTo(StandaloneScrollBar attachTo, WebView2 webView, StandaloneScrollBar.Orientation orientation) {
        Intrinsics.checkNotNullParameter(attachTo, "$this$attachTo");
        Intrinsics.checkNotNullParameter(webView, "webView");
        Intrinsics.checkNotNullParameter(orientation, "orientation");
        int i = WhenMappings.$EnumSwitchMapping$0[orientation.ordinal()];
        if (i == 1) {
            attachTo.attachTo(new VerticalWebViewViewHelper(webView));
        } else {
            if (i != 2) {
                return;
            }
            attachTo.attachTo(new HorizontalWebViewViewHelper(webView));
        }
    }
}
