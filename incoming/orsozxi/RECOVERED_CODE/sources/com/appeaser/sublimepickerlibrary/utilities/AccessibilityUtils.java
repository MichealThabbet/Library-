package com.appeaser.sublimepickerlibrary.utilities;

import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import androidx.core.view.accessibility.AccessibilityEventCompat;

/* loaded from: classes.dex */
public class AccessibilityUtils {
    public static void makeAnnouncement(View view, CharSequence charSequence) {
        if (view == null) {
            return;
        }
        if (SUtils.isApi_16_OrHigher()) {
            view.announceForAccessibility(charSequence);
            return;
        }
        AccessibilityManager accessibilityManager = (AccessibilityManager) view.getContext().getSystemService("accessibility");
        if (accessibilityManager.isEnabled()) {
            AccessibilityEvent accessibilityEventObtain = AccessibilityEvent.obtain(64);
            AccessibilityEventCompat.asRecord(accessibilityEventObtain).setSource(view);
            accessibilityEventObtain.setClassName(view.getClass().getName());
            accessibilityEventObtain.setPackageName(view.getContext().getPackageName());
            accessibilityEventObtain.setEnabled(view.isEnabled());
            accessibilityEventObtain.getText().add(charSequence);
            accessibilityManager.sendAccessibilityEvent(accessibilityEventObtain);
        }
    }
}
