package com.hieupt.android.standalonescrollbar;

import kotlin.Metadata;
import kotlin.jvm.internal.MutablePropertyReference0Impl;

/* compiled from: StandaloneScrollBar.kt */
@Metadata(bv = {1, 0, 3}, k = 3, mv = {1, 4, 1})
/* loaded from: classes2.dex */
final /* synthetic */ class StandaloneScrollBar$orientation$1 extends MutablePropertyReference0Impl {
    StandaloneScrollBar$orientation$1(StandaloneScrollBar standaloneScrollBar) {
        super(standaloneScrollBar, StandaloneScrollBar.class, "orientationHelper", "getOrientationHelper()Lcom/hieupt/android/standalonescrollbar/OrientationHelper;", 0);
    }

    @Override // kotlin.jvm.internal.MutablePropertyReference0Impl, kotlin.reflect.KProperty0
    public Object get() {
        return StandaloneScrollBar.access$getOrientationHelper$p((StandaloneScrollBar) this.receiver);
    }

    @Override // kotlin.jvm.internal.MutablePropertyReference0Impl, kotlin.reflect.KMutableProperty0
    public void set(Object obj) {
        ((StandaloneScrollBar) this.receiver).orientationHelper = (OrientationHelper) obj;
    }
}
