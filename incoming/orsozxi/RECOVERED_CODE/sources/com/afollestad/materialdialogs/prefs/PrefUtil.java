package com.afollestad.materialdialogs.prefs;

import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import com.afollestad.materialdialogs.commons.R;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* loaded from: classes.dex */
class PrefUtil {
    private PrefUtil() {
    }

    static void setLayoutResource(Context context, Preference preference, AttributeSet attributeSet) {
        boolean z;
        boolean z2 = false;
        if (attributeSet != null) {
            for (int i = 0; i < attributeSet.getAttributeCount(); i++) {
                if (((XmlResourceParser) attributeSet).getAttributeNamespace(0).equals("http://schemas.android.com/apk/res/android") && attributeSet.getAttributeName(i).equals("layout")) {
                    z = true;
                    break;
                }
            }
            z = false;
        } else {
            z = false;
        }
        if (attributeSet != null) {
            TypedArray typedArrayObtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.Preference, 0, 0);
            try {
                z2 = typedArrayObtainStyledAttributes.getBoolean(R.styleable.Preference_useStockLayout, false);
            } finally {
                typedArrayObtainStyledAttributes.recycle();
            }
        }
        if (z || z2) {
            return;
        }
        preference.setLayoutResource(R.layout.md_preference_custom);
    }

    static void registerOnActivityDestroyListener(Preference preference, PreferenceManager.OnActivityDestroyListener onActivityDestroyListener) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        try {
            PreferenceManager preferenceManager = preference.getPreferenceManager();
            Method declaredMethod = preferenceManager.getClass().getDeclaredMethod("registerOnActivityDestroyListener", PreferenceManager.OnActivityDestroyListener.class);
            declaredMethod.setAccessible(true);
            declaredMethod.invoke(preferenceManager, onActivityDestroyListener);
        } catch (Exception unused) {
        }
    }

    static void unregisterOnActivityDestroyListener(Preference preference, PreferenceManager.OnActivityDestroyListener onActivityDestroyListener) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        try {
            PreferenceManager preferenceManager = preference.getPreferenceManager();
            Method declaredMethod = preferenceManager.getClass().getDeclaredMethod("unregisterOnActivityDestroyListener", PreferenceManager.OnActivityDestroyListener.class);
            declaredMethod.setAccessible(true);
            declaredMethod.invoke(preferenceManager, onActivityDestroyListener);
        } catch (Exception unused) {
        }
    }
}
