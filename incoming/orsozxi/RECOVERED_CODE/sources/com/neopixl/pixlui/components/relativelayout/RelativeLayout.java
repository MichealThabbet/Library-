package com.neopixl.pixlui.components.relativelayout;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;

/* loaded from: classes2.dex */
public class RelativeLayout extends android.widget.RelativeLayout {
    private static final String RELATIVE_LAYOUT_OS_ATTRIBUTE_TEXT_ALPHA = "alpha";
    private float mAlpha;
    private boolean mOldDeviceTextAlpha;

    public RelativeLayout(Context context) {
        super(context);
        this.mAlpha = 1.0f;
        editTextVersion();
    }

    public RelativeLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mAlpha = 1.0f;
        editTextVersion();
        if (isOldDeviceTextAlpha()) {
            setAlpha(context, attributeSet);
        }
    }

    public RelativeLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mAlpha = 1.0f;
        editTextVersion();
        if (isOldDeviceTextAlpha()) {
            setAlpha(context, attributeSet);
        }
    }

    private void editTextVersion() {
        if (Build.VERSION.SDK_INT < 11) {
            setOldDeviceTextAlpha(true);
        } else {
            setOldDeviceTextAlpha(false);
        }
    }

    private void setAlpha(Context context, AttributeSet attributeSet) {
        float attributeFloatValue;
        if (isInEditMode()) {
            return;
        }
        int attributeCount = attributeSet.getAttributeCount();
        int i = 0;
        while (true) {
            if (i >= attributeCount) {
                attributeFloatValue = 1.0f;
                break;
            } else {
                if (attributeSet.getAttributeName(i).equals(RELATIVE_LAYOUT_OS_ATTRIBUTE_TEXT_ALPHA)) {
                    attributeFloatValue = attributeSet.getAttributeFloatValue(i, 1.0f);
                    break;
                }
                i++;
            }
        }
        if (attributeFloatValue != 1.0f) {
            setAlpha(attributeFloatValue);
        }
    }

    @Override // android.view.View
    public void setAlpha(float f) {
        if (isOldDeviceTextAlpha()) {
            set_Alpha(f);
        } else {
            super.setAlpha(f);
        }
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        float f = this.mAlpha;
        if (f != 1.0f) {
            canvas.saveLayerAlpha(0.0f, 0.0f, canvas.getWidth(), canvas.getHeight(), (int) (f * 255.0f), 4);
        }
        super.onDraw(canvas);
    }

    public boolean isOldDeviceTextAlpha() {
        return this.mOldDeviceTextAlpha;
    }

    public void setOldDeviceTextAlpha(boolean z) {
        this.mOldDeviceTextAlpha = z;
    }

    public float get_Alpha() {
        return this.mAlpha;
    }

    public void set_Alpha(float f) {
        this.mAlpha = f;
    }
}
