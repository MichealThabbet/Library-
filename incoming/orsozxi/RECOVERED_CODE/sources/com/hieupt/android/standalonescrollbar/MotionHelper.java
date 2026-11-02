package com.hieupt.android.standalonescrollbar;

import android.view.View;
import com.google.android.gms.analytics.ecommerce.Promotion;
import com.ibm.icu.impl.locale.LanguageTag;
import com.ibm.icu.text.DateFormat;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MotionHelper.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J8\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\bH\u0002J.\u0010\r\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u00062\u0006\u0010\u0012\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\b¨\u0006\u0013"}, d2 = {"Lcom/hieupt/android/standalonescrollbar/MotionHelper;", "", "()V", "isInTouchTarget", "", "position", "", "viewStart", "", "viewEnd", "parentStart", "parentEnd", "minTouchTargetSize", "isInViewTouchTarget", "parentView", "Landroid/view/View;", Promotion.ACTION_VIEW, LanguageTag.PRIVATEUSE, DateFormat.YEAR, "android-standalone-scroll-bar_release"}, k = 1, mv = {1, 4, 1})
/* loaded from: classes2.dex */
public final class MotionHelper {
    public static final MotionHelper INSTANCE = new MotionHelper();

    private MotionHelper() {
    }

    public final boolean isInViewTouchTarget(View parentView, View view, float x, float y, int minTouchTargetSize) {
        Intrinsics.checkNotNullParameter(parentView, "parentView");
        Intrinsics.checkNotNullParameter(view, "view");
        int scrollX = parentView.getScrollX();
        int scrollY = parentView.getScrollY();
        return isInTouchTarget(x, view.getLeft() - scrollX, view.getRight() - scrollX, 0, parentView.getWidth(), minTouchTargetSize) && isInTouchTarget(y, view.getTop() - scrollY, view.getBottom() - scrollY, 0, parentView.getHeight(), minTouchTargetSize);
    }

    private final boolean isInTouchTarget(float position, int viewStart, int viewEnd, int parentStart, int parentEnd, int minTouchTargetSize) {
        int i = viewEnd - viewStart;
        if (i >= minTouchTargetSize) {
            return position >= ((float) viewStart) && position < ((float) viewEnd);
        }
        int i2 = viewStart - ((minTouchTargetSize - i) / 2);
        if (i2 < parentStart) {
            i2 = parentStart;
        }
        int i3 = i2 + minTouchTargetSize;
        if (i3 > parentEnd) {
            int i4 = parentEnd - minTouchTargetSize;
            if (i4 >= parentStart) {
                parentStart = i4;
            }
        } else {
            parentStart = i2;
            parentEnd = i3;
        }
        return position >= ((float) parentStart) && position < ((float) parentEnd);
    }
}
