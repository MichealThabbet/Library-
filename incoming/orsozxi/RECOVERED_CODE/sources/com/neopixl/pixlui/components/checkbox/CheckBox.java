package com.neopixl.pixlui.components.checkbox;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import com.android.export.AllCapsTransformationMethod;
import com.neopixl.pixlui.components.textview.FontFactory;
import com.neopixl.pixlui.intern.PixlUIContants;

/* loaded from: classes2.dex */
public class CheckBox extends android.widget.CheckBox {
    private static String BUTTON_ATTRIBUTE_FONT_NAME = "typeface";
    private static final String BUTTON_OS_ATTRIBUTE_TEXT_ALL_CAPS = "textAllCaps";
    private boolean mOldDeviceTextAllCaps;

    public CheckBox(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        checkBoxVersion();
        setCustomFont(context, attributeSet);
        if (isOldDeviceTextAllCaps()) {
            setAllCaps(context, attributeSet);
        }
    }

    public CheckBox(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        checkBoxVersion();
        setCustomFont(context, attributeSet);
        if (isOldDeviceTextAllCaps()) {
            setAllCaps(context, attributeSet);
        }
    }

    public CheckBox(Context context) {
        super(context);
        checkBoxVersion();
    }

    private void checkBoxVersion() {
        if (Build.VERSION.SDK_INT < 14) {
            setOldDeviceTextAllCaps(true);
        } else {
            setOldDeviceTextAllCaps(false);
        }
    }

    private void setCustomFont(Context context, AttributeSet attributeSet) {
        String attributeValue = attributeSet.getAttributeValue(PixlUIContants.SCHEMA_URL, BUTTON_ATTRIBUTE_FONT_NAME);
        if (attributeValue != null) {
            setPaintFlags(getPaintFlags() | 128 | 64);
            setCustomFont(context, attributeValue);
        }
    }

    private void setAllCaps(Context context, AttributeSet attributeSet) {
        if (isInEditMode()) {
            return;
        }
        int attributeCount = attributeSet.getAttributeCount();
        boolean attributeBooleanValue = false;
        int i = 0;
        while (true) {
            if (i >= attributeCount) {
                break;
            }
            if (attributeSet.getAttributeName(i).equals(BUTTON_OS_ATTRIBUTE_TEXT_ALL_CAPS)) {
                attributeBooleanValue = attributeSet.getAttributeBooleanValue(i, false);
                break;
            }
            i++;
        }
        if (attributeBooleanValue) {
            setAllCaps(attributeBooleanValue);
        }
    }

    @Override // android.widget.TextView
    public void setAllCaps(boolean z) {
        if (!isOldDeviceTextAllCaps()) {
            super.setAllCaps(z);
        } else if (z) {
            setTransformationMethod(new AllCapsTransformationMethod(getContext()));
        } else {
            setTransformationMethod(null);
        }
    }

    public boolean setCustomFont(Context context, String str) {
        Typeface font = FontFactory.getInstance(context).getFont(str);
        if (font == null) {
            return false;
        }
        setTypeface(font);
        return true;
    }

    public boolean isOldDeviceTextAllCaps() {
        return this.mOldDeviceTextAllCaps;
    }

    public void setOldDeviceTextAllCaps(boolean z) {
        this.mOldDeviceTextAllCaps = z;
    }
}
