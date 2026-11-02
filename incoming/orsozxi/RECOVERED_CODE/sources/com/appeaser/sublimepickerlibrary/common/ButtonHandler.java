package com.appeaser.sublimepickerlibrary.common;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import com.appeaser.sublimepickerlibrary.R;
import com.appeaser.sublimepickerlibrary.SublimePicker;
import com.appeaser.sublimepickerlibrary.helpers.SublimeOptions;
import com.appeaser.sublimepickerlibrary.utilities.SUtils;

/* loaded from: classes.dex */
public class ButtonHandler implements View.OnClickListener {
    int mButtonBarBgColor;
    Callback mCallback;
    int mDisabledAlpha;
    int mIconOverlayColor;
    private boolean mIsInLandscapeMode;
    View mNegativeButtonDP;
    View mNegativeButtonTP;
    private ButtonLayout mPortraitButtonHandler;
    View mPositiveButtonDP;
    View mPositiveButtonTP;
    Button mSwitcherButtonDP;
    Button mSwitcherButtonTP;

    public interface Callback {
        void onCancel();

        void onOkay();

        void onSwitch();
    }

    public ButtonHandler(SublimePicker sublimePicker) throws Throwable {
        boolean z = sublimePicker.getContext().getResources().getConfiguration().orientation == 2;
        this.mIsInLandscapeMode = z;
        if (z) {
            initializeForLandscape(sublimePicker);
        } else {
            this.mPortraitButtonHandler = (ButtonLayout) sublimePicker.findViewById(R.id.button_layout);
        }
    }

    private void initializeForLandscape(SublimePicker sublimePicker) throws Throwable {
        ContextThemeWrapper contextThemeWrapperCreateThemeWrapper = SUtils.createThemeWrapper(sublimePicker.getContext(), R.attr.sublimePickerStyle, R.style.SublimePickerStyleLight, R.attr.spButtonLayoutStyle, R.style.ButtonLayoutStyle);
        Resources resources = contextThemeWrapperCreateThemeWrapper.getResources();
        TypedArray typedArrayObtainStyledAttributes = contextThemeWrapperCreateThemeWrapper.obtainStyledAttributes(R.styleable.ButtonLayout);
        this.mSwitcherButtonDP = (Button) sublimePicker.findViewById(R.id.buttonSwitcherDP);
        this.mSwitcherButtonTP = (Button) sublimePicker.findViewById(R.id.buttonSwitcherTP);
        Button button = (Button) sublimePicker.findViewById(R.id.buttonPositiveDP);
        Button button2 = (Button) sublimePicker.findViewById(R.id.buttonPositiveTP);
        Button button3 = (Button) sublimePicker.findViewById(R.id.buttonNegativeDP);
        Button button4 = (Button) sublimePicker.findViewById(R.id.buttonNegativeTP);
        ImageView imageView = (ImageView) sublimePicker.findViewById(R.id.imageViewPositiveDP);
        ImageView imageView2 = (ImageView) sublimePicker.findViewById(R.id.imageViewPositiveTP);
        ImageView imageView3 = (ImageView) sublimePicker.findViewById(R.id.imageViewNegativeDP);
        ImageView imageView4 = (ImageView) sublimePicker.findViewById(R.id.imageViewNegativeTP);
        try {
            TypedValue typedValue = new TypedValue();
            contextThemeWrapperCreateThemeWrapper.getTheme().resolveAttribute(android.R.attr.disabledAlpha, typedValue, true);
            this.mDisabledAlpha = typedValue.type == 4 ? (int) (typedValue.getFloat() * 255.0f) : 122;
            int i = typedArrayObtainStyledAttributes.getInt(R.styleable.ButtonLayout_spPresentation, 0);
            int color = typedArrayObtainStyledAttributes.getColor(R.styleable.ButtonLayout_spButtonBgColor, SUtils.COLOR_BUTTON_NORMAL);
            int color2 = typedArrayObtainStyledAttributes.getColor(R.styleable.ButtonLayout_spButtonPressedBgColor, SUtils.COLOR_CONTROL_HIGHLIGHT);
            this.mButtonBarBgColor = typedArrayObtainStyledAttributes.getColor(R.styleable.ButtonLayout_spButtonBarBgColor, 0);
            int color3 = typedArrayObtainStyledAttributes.getColor(R.styleable.ButtonLayout_spButtonInvertedBgColor, SUtils.COLOR_ACCENT);
            int color4 = typedArrayObtainStyledAttributes.getColor(R.styleable.ButtonLayout_spButtonPressedInvertedBgColor, ContextCompat.getColor(contextThemeWrapperCreateThemeWrapper, R.color.sp_ripple_material_dark));
            try {
                SUtils.setViewBackground(this.mSwitcherButtonDP, SUtils.createButtonBg(contextThemeWrapperCreateThemeWrapper, color3, color4));
                SUtils.setViewBackground(this.mSwitcherButtonTP, SUtils.createButtonBg(contextThemeWrapperCreateThemeWrapper, color3, color4));
                if (i == 0) {
                    button.setVisibility(0);
                    button2.setVisibility(0);
                    button3.setVisibility(0);
                    button4.setVisibility(0);
                    button.setText(resources.getString(R.string.ok));
                    button2.setText(resources.getString(R.string.ok));
                    button3.setText(resources.getString(R.string.cancel));
                    button4.setText(resources.getString(R.string.cancel));
                    SUtils.setViewBackground(button, SUtils.createButtonBg(contextThemeWrapperCreateThemeWrapper, color, color2));
                    SUtils.setViewBackground(button2, SUtils.createButtonBg(contextThemeWrapperCreateThemeWrapper, color, color2));
                    SUtils.setViewBackground(button3, SUtils.createButtonBg(contextThemeWrapperCreateThemeWrapper, color, color2));
                    SUtils.setViewBackground(button4, SUtils.createButtonBg(contextThemeWrapperCreateThemeWrapper, color, color2));
                    this.mPositiveButtonDP = button;
                    this.mPositiveButtonTP = button2;
                    this.mNegativeButtonDP = button3;
                    this.mNegativeButtonTP = button4;
                    typedArrayObtainStyledAttributes = typedArrayObtainStyledAttributes;
                } else {
                    imageView.setVisibility(0);
                    imageView2.setVisibility(0);
                    imageView3.setVisibility(0);
                    imageView4.setVisibility(0);
                    typedArrayObtainStyledAttributes = typedArrayObtainStyledAttributes;
                    int color5 = typedArrayObtainStyledAttributes.getColor(R.styleable.ButtonLayout_spIconColor, SUtils.COLOR_ACCENT);
                    this.mIconOverlayColor = color5;
                    imageView.setColorFilter(color5, PorterDuff.Mode.MULTIPLY);
                    imageView2.setColorFilter(this.mIconOverlayColor, PorterDuff.Mode.MULTIPLY);
                    imageView3.setColorFilter(this.mIconOverlayColor, PorterDuff.Mode.MULTIPLY);
                    imageView4.setColorFilter(this.mIconOverlayColor, PorterDuff.Mode.MULTIPLY);
                    SUtils.setViewBackground(imageView, SUtils.createImageViewBg(color, color2));
                    SUtils.setViewBackground(imageView2, SUtils.createImageViewBg(color, color2));
                    SUtils.setViewBackground(imageView3, SUtils.createImageViewBg(color, color2));
                    SUtils.setViewBackground(imageView4, SUtils.createImageViewBg(color, color2));
                    this.mPositiveButtonDP = imageView;
                    this.mPositiveButtonTP = imageView2;
                    this.mNegativeButtonDP = imageView3;
                    this.mNegativeButtonTP = imageView4;
                }
                typedArrayObtainStyledAttributes.recycle();
                this.mPositiveButtonDP.setOnClickListener(this);
                this.mPositiveButtonTP.setOnClickListener(this);
                this.mNegativeButtonDP.setOnClickListener(this);
                this.mNegativeButtonTP.setOnClickListener(this);
                this.mSwitcherButtonDP.setOnClickListener(this);
                this.mSwitcherButtonTP.setOnClickListener(this);
            } catch (Throwable th) {
                th = th;
                typedArrayObtainStyledAttributes = typedArrayObtainStyledAttributes;
                typedArrayObtainStyledAttributes.recycle();
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public void applyOptions(boolean z, Callback callback) {
        this.mCallback = callback;
        if (this.mIsInLandscapeMode) {
            this.mSwitcherButtonDP.setVisibility(z ? 0 : 8);
            this.mSwitcherButtonTP.setVisibility(z ? 0 : 8);
        } else {
            this.mPortraitButtonHandler.applyOptions(z, callback);
        }
    }

    public boolean isSwitcherButtonEnabled() {
        if (this.mIsInLandscapeMode) {
            return this.mSwitcherButtonDP.getVisibility() == 0 || this.mSwitcherButtonTP.getVisibility() == 0;
        }
        return this.mPortraitButtonHandler.isSwitcherButtonEnabled();
    }

    public void updateSwitcherText(SublimeOptions.Picker picker, CharSequence charSequence) {
        if (this.mIsInLandscapeMode) {
            if (picker == SublimeOptions.Picker.DATE_PICKER) {
                this.mSwitcherButtonDP.setText(charSequence);
                return;
            } else {
                if (picker == SublimeOptions.Picker.TIME_PICKER) {
                    this.mSwitcherButtonTP.setText(charSequence);
                    return;
                }
                return;
            }
        }
        this.mPortraitButtonHandler.updateSwitcherText(charSequence);
    }

    public void updateValidity(boolean z) {
        if (this.mIsInLandscapeMode) {
            this.mPositiveButtonDP.setEnabled(z);
            this.mPositiveButtonTP.setEnabled(z);
            if (this.mPositiveButtonDP instanceof ImageView) {
                int i = this.mIconOverlayColor;
                if (!z) {
                    i = (i & ViewCompat.MEASURED_SIZE_MASK) | (this.mDisabledAlpha << 24);
                }
                ((ImageView) this.mPositiveButtonDP).setColorFilter(i, PorterDuff.Mode.MULTIPLY);
                ((ImageView) this.mPositiveButtonTP).setColorFilter(i, PorterDuff.Mode.MULTIPLY);
                return;
            }
            return;
        }
        this.mPortraitButtonHandler.updateValidity(z);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (view == this.mPositiveButtonDP || view == this.mPositiveButtonTP) {
            this.mCallback.onOkay();
            return;
        }
        if (view == this.mNegativeButtonDP || view == this.mNegativeButtonTP) {
            this.mCallback.onCancel();
        } else if (view == this.mSwitcherButtonDP || view == this.mSwitcherButtonTP) {
            this.mCallback.onSwitch();
        }
    }
}
