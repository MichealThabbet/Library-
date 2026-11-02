package com.getbase.floatingactionbutton;

import android.graphics.Rect;
import android.view.TouchDelegate;
import android.view.View;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class TouchDelegateGroup extends TouchDelegate {
    private static final Rect USELESS_HACKY_RECT = new Rect();
    private TouchDelegate mCurrentTouchDelegate;
    private boolean mEnabled;
    private final ArrayList<TouchDelegate> mTouchDelegates;

    public TouchDelegateGroup(View view) {
        super(USELESS_HACKY_RECT, view);
        this.mTouchDelegates = new ArrayList<>();
    }

    public void addTouchDelegate(TouchDelegate touchDelegate) {
        this.mTouchDelegates.add(touchDelegate);
    }

    public void removeTouchDelegate(TouchDelegate touchDelegate) {
        this.mTouchDelegates.remove(touchDelegate);
        if (this.mCurrentTouchDelegate == touchDelegate) {
            this.mCurrentTouchDelegate = null;
        }
    }

    public void clearTouchDelegates() {
        this.mTouchDelegates.clear();
        this.mCurrentTouchDelegate = null;
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x001a  */
    @Override // android.view.TouchDelegate
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean onTouchEvent(android.view.MotionEvent r7) {
        /*
            r6 = this;
            boolean r0 = r6.mEnabled
            r1 = 0
            if (r0 != 0) goto L6
            return r1
        L6:
            int r0 = r7.getAction()
            r2 = 0
            r3 = 1
            if (r0 == 0) goto L20
            if (r0 == r3) goto L1a
            r4 = 2
            if (r0 == r4) goto L17
            r4 = 3
            if (r0 == r4) goto L1a
            goto L3d
        L17:
            android.view.TouchDelegate r2 = r6.mCurrentTouchDelegate
            goto L3d
        L1a:
            android.view.TouchDelegate r0 = r6.mCurrentTouchDelegate
            r6.mCurrentTouchDelegate = r2
            r2 = r0
            goto L3d
        L20:
            r0 = 0
        L21:
            java.util.ArrayList<android.view.TouchDelegate> r4 = r6.mTouchDelegates
            int r4 = r4.size()
            if (r0 >= r4) goto L3d
            java.util.ArrayList<android.view.TouchDelegate> r4 = r6.mTouchDelegates
            java.lang.Object r4 = r4.get(r0)
            android.view.TouchDelegate r4 = (android.view.TouchDelegate) r4
            boolean r5 = r4.onTouchEvent(r7)
            if (r5 == 0) goto L3a
            r6.mCurrentTouchDelegate = r4
            return r3
        L3a:
            int r0 = r0 + 1
            goto L21
        L3d:
            if (r2 == 0) goto L46
            boolean r7 = r2.onTouchEvent(r7)
            if (r7 == 0) goto L46
            r1 = 1
        L46:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.getbase.floatingactionbutton.TouchDelegateGroup.onTouchEvent(android.view.MotionEvent):boolean");
    }

    public void setEnabled(boolean z) {
        this.mEnabled = z;
    }
}
