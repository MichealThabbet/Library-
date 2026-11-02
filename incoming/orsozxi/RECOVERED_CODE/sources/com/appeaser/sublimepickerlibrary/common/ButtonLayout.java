package com.appeaser.sublimepickerlibrary.common;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.core.view.ViewCompat;
import com.appeaser.sublimepickerlibrary.R;
import com.appeaser.sublimepickerlibrary.common.ButtonHandler;
import com.appeaser.sublimepickerlibrary.utilities.SUtils;

/* loaded from: classes.dex */
public class ButtonLayout extends LinearLayout implements View.OnClickListener {
    int mButtonBarBgColor;
    ButtonHandler.Callback mCallback;
    int mDisabledAlpha;
    int mIconOverlayColor;
    View mNegativeButton;
    View mPositiveButton;
    Button mSwitcherButton;

    public ButtonLayout(Context context) {
        this(context, null);
    }

    public ButtonLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.spButtonLayoutStyle);
    }

    public ButtonLayout(Context context, AttributeSet attributeSet, int i) {
        super(SUtils.createThemeWrapper(context, R.attr.sublimePickerStyle, R.style.SublimePickerStyleLight, R.attr.spButtonLayoutStyle, R.style.ButtonLayoutStyle), attributeSet, i);
        initialize();
    }

    public ButtonLayout(Context context, AttributeSet attributeSet, int i, int i2) {
        super(SUtils.createThemeWrapper(context, R.attr.sublimePickerStyle, R.style.SublimePickerStyleLight, R.attr.spButtonLayoutStyle, R.style.ButtonLayoutStyle), attributeSet, i, i2);
        initialize();
    }

    void initialize() {
        Context context = getContext();
        Resources resources = getResources();
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(R.styleable.ButtonLayout);
        if (SUtils.isApi_17_OrHigher()) {
            setLayoutDirection(3);
        }
        setOrientation(0);
        setGravity(80);
        setPadding(resources.getDimensionPixelSize(R.dimen.sp_button_bar_padding_start), resources.getDimensionPixelSize(R.dimen.sp_button_bar_padding_top), resources.getDimensionPixelSize(R.dimen.sp_button_bar_padding_end), resources.getDimensionPixelSize(R.dimen.sp_button_bar_padding_bottom));
        ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.sublime_button_panel_layout, (ViewGroup) this, true);
        this.mSwitcherButton = (Button) findViewById(R.id.buttonSwitcher);
        Button button = (Button) findViewById(R.id.buttonPositive);
        Button button2 = (Button) findViewById(R.id.buttonNegative);
        ImageView imageView = (ImageView) findViewById(R.id.imageViewPositive);
        ImageView imageView2 = (ImageView) findViewById(R.id.imageViewNegative);
        try {
            TypedValue typedValue = new TypedValue();
            getContext().getTheme().resolveAttribute(android.R.attr.disabledAlpha, typedValue, true);
            this.mDisabledAlpha = typedValue.type == 4 ? (int) (typedValue.getFloat() * 255.0f) : 122;
            int i = typedArrayObtainStyledAttributes.getInt(R.styleable.ButtonLayout_spPresentation, 0);
            int color = typedArrayObtainStyledAttributes.getColor(R.styleable.ButtonLayout_spButtonBgColor, SUtils.COLOR_BUTTON_NORMAL);
            int color2 = typedArrayObtainStyledAttributes.getColor(R.styleable.ButtonLayout_spButtonPressedBgColor, SUtils.COLOR_CONTROL_HIGHLIGHT);
            this.mButtonBarBgColor = typedArrayObtainStyledAttributes.getColor(R.styleable.ButtonLayout_spButtonBarBgColor, 0);
            SUtils.setViewBackground(this.mSwitcherButton, SUtils.createButtonBg(context, color, color2));
            setBackgroundColor(this.mButtonBarBgColor);
            if (i == 0) {
                button.setVisibility(0);
                button2.setVisibility(0);
                button.setText(resources.getString(R.string.ok));
                button2.setText(resources.getString(R.string.cancel));
                SUtils.setViewBackground(button, SUtils.createButtonBg(context, color, color2));
                SUtils.setViewBackground(button2, SUtils.createButtonBg(context, color, color2));
                this.mPositiveButton = button;
                this.mNegativeButton = button2;
            } else {
                imageView.setVisibility(0);
                imageView2.setVisibility(0);
                int color3 = typedArrayObtainStyledAttributes.getColor(R.styleable.ButtonLayout_spIconColor, SUtils.COLOR_ACCENT);
                this.mIconOverlayColor = color3;
                imageView.setColorFilter(color3, PorterDuff.Mode.MULTIPLY);
                imageView2.setColorFilter(this.mIconOverlayColor, PorterDuff.Mode.MULTIPLY);
                SUtils.setViewBackground(imageView, SUtils.createImageViewBg(color, color2));
                SUtils.setViewBackground(imageView2, SUtils.createImageViewBg(color, color2));
                this.mPositiveButton = imageView;
                this.mNegativeButton = imageView2;
            }
            typedArrayObtainStyledAttributes.recycle();
            this.mPositiveButton.setOnClickListener(this);
            this.mNegativeButton.setOnClickListener(this);
            this.mSwitcherButton.setOnClickListener(this);
        } catch (Throwable th) {
            typedArrayObtainStyledAttributes.recycle();
            throw th;
        }
    }

    public void applyOptions(boolean z, ButtonHandler.Callback callback) {
        this.mSwitcherButton.setVisibility(z ? 0 : 8);
        this.mCallback = callback;
    }

    public boolean isSwitcherButtonEnabled() {
        return this.mSwitcherButton.getVisibility() == 0;
    }

    public void updateSwitcherText(CharSequence charSequence) {
        this.mSwitcherButton.setText(charSequence);
    }

    public void updateValidity(boolean z) {
        this.mPositiveButton.setEnabled(z);
        if (this.mPositiveButton instanceof ImageView) {
            int i = this.mIconOverlayColor;
            if (!z) {
                i = (i & ViewCompat.MEASURED_SIZE_MASK) | (this.mDisabledAlpha << 24);
            }
            ((ImageView) this.mPositiveButton).setColorFilter(i, PorterDuff.Mode.MULTIPLY);
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (view == this.mPositiveButton) {
            this.mCallback.onOkay();
        } else if (view == this.mNegativeButton) {
            this.mCallback.onCancel();
        } else if (view == this.mSwitcherButton) {
            this.mCallback.onSwitch();
        }
    }
}
