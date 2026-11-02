package com.afollestad.materialdialogs.color;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.afollestad.materialdialogs.commons.R;
import com.afollestad.materialdialogs.internal.MDTintHelper;
import com.afollestad.materialdialogs.util.DialogUtils;
import com.google.android.material.timepicker.TimeModel;
import java.io.Serializable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Locale;

/* loaded from: classes.dex */
public class ColorChooserDialog extends DialogFragment implements View.OnClickListener, View.OnLongClickListener {
    public static final String TAG_ACCENT = "[MD_COLOR_CHOOSER]";
    public static final String TAG_CUSTOM = "[MD_COLOR_CHOOSER]";
    public static final String TAG_PRIMARY = "[MD_COLOR_CHOOSER]";
    private ColorCallback callback;
    private int circleSize;
    private View colorChooserCustomFrame;
    private int[][] colorsSub;
    private int[] colorsTop;
    private EditText customColorHex;
    private View customColorIndicator;
    private SeekBar.OnSeekBarChangeListener customColorRgbListener;
    private TextWatcher customColorTextWatcher;
    private SeekBar customSeekA;
    private TextView customSeekAValue;
    private SeekBar customSeekB;
    private TextView customSeekBValue;
    private SeekBar customSeekG;
    private TextView customSeekGValue;
    private SeekBar customSeekR;
    private TextView customSeekRValue;
    private GridView grid;
    private int selectedCustomColor;

    public interface ColorCallback {
        void onColorChooserDismissed(ColorChooserDialog colorChooserDialog);

        void onColorSelection(ColorChooserDialog colorChooserDialog, int i);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ColorChooserTag {
    }

    public static ColorChooserDialog findVisible(FragmentManager fragmentManager, String str) {
        Fragment fragmentFindFragmentByTag = fragmentManager.findFragmentByTag(str);
        if (fragmentFindFragmentByTag == null || !(fragmentFindFragmentByTag instanceof ColorChooserDialog)) {
            return null;
        }
        return (ColorChooserDialog) fragmentFindFragmentByTag;
    }

    private void generateColors() {
        Builder builder = getBuilder();
        if (builder.colorsTop != null) {
            this.colorsTop = builder.colorsTop;
            this.colorsSub = builder.colorsSub;
        } else if (builder.accentMode) {
            this.colorsTop = ColorPalette.ACCENT_COLORS;
            this.colorsSub = ColorPalette.ACCENT_COLORS_SUB;
        } else {
            this.colorsTop = ColorPalette.PRIMARY_COLORS;
            this.colorsSub = ColorPalette.PRIMARY_COLORS_SUB;
        }
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt("top_index", topIndex());
        bundle.putBoolean("in_sub", isInSub());
        bundle.putInt("sub_index", subIndex());
        View view = this.colorChooserCustomFrame;
        bundle.putBoolean("in_custom", view != null && view.getVisibility() == 0);
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() instanceof ColorCallback) {
            this.callback = (ColorCallback) getActivity();
        } else {
            if (getParentFragment() instanceof ColorCallback) {
                this.callback = (ColorCallback) getParentFragment();
                return;
            }
            throw new IllegalStateException("ColorChooserDialog needs to be shown from an Activity/Fragment implementing ColorCallback.");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isInSub() {
        return getArguments().getBoolean("in_sub", false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void isInSub(boolean z) {
        getArguments().putBoolean("in_sub", z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int topIndex() {
        return getArguments().getInt("top_index", -1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void topIndex(int i) {
        if (i > -1) {
            findSubIndexForColor(i, this.colorsTop[i]);
        }
        getArguments().putInt("top_index", i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int subIndex() {
        if (this.colorsSub == null) {
            return -1;
        }
        return getArguments().getInt("sub_index", -1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void subIndex(int i) {
        if (this.colorsSub == null) {
            return;
        }
        getArguments().putInt("sub_index", i);
    }

    public int getTitle() {
        int i;
        Builder builder = getBuilder();
        if (isInSub()) {
            i = builder.titleSub;
        } else {
            i = builder.title;
        }
        return i == 0 ? builder.title : i;
    }

    public String tag() {
        Builder builder = getBuilder();
        if (builder.tag != null) {
            return builder.tag;
        }
        return super.getTag();
    }

    public boolean isAccentMode() {
        return getBuilder().accentMode;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) throws NumberFormatException {
        if (view.getTag() != null) {
            int i = Integer.parseInt(((String) view.getTag()).split(":")[0]);
            MaterialDialog materialDialog = (MaterialDialog) getDialog();
            Builder builder = getBuilder();
            if (isInSub()) {
                subIndex(i);
            } else {
                topIndex(i);
                int[][] iArr = this.colorsSub;
                if (iArr != null && i < iArr.length) {
                    materialDialog.setActionButton(DialogAction.NEGATIVE, builder.backBtn);
                    isInSub(true);
                }
            }
            if (builder.allowUserCustom) {
                this.selectedCustomColor = getSelectedColor();
            }
            invalidateDynamicButtonColors();
            invalidate();
        }
    }

    @Override // android.view.View.OnLongClickListener
    public boolean onLongClick(View view) throws NumberFormatException {
        if (view.getTag() == null) {
            return false;
        }
        ((CircleView) view).showHint(Integer.parseInt(((String) view.getTag()).split(":")[1]));
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void invalidateDynamicButtonColors() {
        MaterialDialog materialDialog = (MaterialDialog) getDialog();
        if (materialDialog != null && getBuilder().dynamicButtonColor) {
            int selectedColor = getSelectedColor();
            if (Color.alpha(selectedColor) < 64 || (Color.red(selectedColor) > 247 && Color.green(selectedColor) > 247 && Color.blue(selectedColor) > 247)) {
                selectedColor = Color.parseColor("#DEDEDE");
            }
            if (getBuilder().dynamicButtonColor) {
                materialDialog.getActionButton(DialogAction.POSITIVE).setTextColor(selectedColor);
                materialDialog.getActionButton(DialogAction.NEGATIVE).setTextColor(selectedColor);
                materialDialog.getActionButton(DialogAction.NEUTRAL).setTextColor(selectedColor);
            }
            if (this.customSeekR != null) {
                if (this.customSeekA.getVisibility() == 0) {
                    MDTintHelper.setTint(this.customSeekA, selectedColor);
                }
                MDTintHelper.setTint(this.customSeekR, selectedColor);
                MDTintHelper.setTint(this.customSeekG, selectedColor);
                MDTintHelper.setTint(this.customSeekB, selectedColor);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getSelectedColor() {
        int i;
        View view = this.colorChooserCustomFrame;
        if (view != null && view.getVisibility() == 0) {
            return this.selectedCustomColor;
        }
        if (subIndex() > -1) {
            i = this.colorsSub[topIndex()][subIndex()];
        } else {
            i = topIndex() > -1 ? this.colorsTop[topIndex()] : 0;
        }
        if (i == 0) {
            return DialogUtils.resolveColor(getActivity(), R.attr.colorAccent, Build.VERSION.SDK_INT >= 21 ? DialogUtils.resolveColor(getActivity(), android.R.attr.colorAccent) : 0);
        }
        return i;
    }

    private void findSubIndexForColor(int i, int i2) {
        int[][] iArr = this.colorsSub;
        if (iArr == null || iArr.length - 1 < i) {
            return;
        }
        int[] iArr2 = iArr[i];
        for (int i3 = 0; i3 < iArr2.length; i3++) {
            if (iArr2[i3] == i2) {
                subIndex(i3);
                return;
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:37:0x0080, code lost:
    
        r8 = r3;
     */
    @Override // androidx.fragment.app.DialogFragment
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public android.app.Dialog onCreateDialog(android.os.Bundle r8) {
        /*
            Method dump skipped, instructions count: 469
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.afollestad.materialdialogs.color.ColorChooserDialog.onCreateDialog(android.os.Bundle):android.app.Dialog");
    }

    @Override // androidx.fragment.app.DialogFragment, android.content.DialogInterface.OnDismissListener
    public void onDismiss(DialogInterface dialogInterface) {
        super.onDismiss(dialogInterface);
        ColorCallback colorCallback = this.callback;
        if (colorCallback != null) {
            colorCallback.onColorChooserDismissed(this);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void toggleCustom(MaterialDialog materialDialog) {
        if (materialDialog == null) {
            materialDialog = (MaterialDialog) getDialog();
        }
        if (this.grid.getVisibility() == 0) {
            materialDialog.setTitle(getBuilder().customBtn);
            materialDialog.setActionButton(DialogAction.NEUTRAL, getBuilder().presetsBtn);
            materialDialog.setActionButton(DialogAction.NEGATIVE, getBuilder().cancelBtn);
            this.grid.setVisibility(4);
            this.colorChooserCustomFrame.setVisibility(0);
            TextWatcher textWatcher = new TextWatcher() { // from class: com.afollestad.materialdialogs.color.ColorChooserDialog.5
                @Override // android.text.TextWatcher
                public void afterTextChanged(Editable editable) {
                }

                @Override // android.text.TextWatcher
                public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                }

                @Override // android.text.TextWatcher
                public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                    try {
                        ColorChooserDialog.this.selectedCustomColor = Color.parseColor("#" + charSequence.toString());
                    } catch (IllegalArgumentException unused) {
                        ColorChooserDialog.this.selectedCustomColor = ViewCompat.MEASURED_STATE_MASK;
                    }
                    ColorChooserDialog.this.customColorIndicator.setBackgroundColor(ColorChooserDialog.this.selectedCustomColor);
                    if (ColorChooserDialog.this.customSeekA.getVisibility() == 0) {
                        int iAlpha = Color.alpha(ColorChooserDialog.this.selectedCustomColor);
                        ColorChooserDialog.this.customSeekA.setProgress(iAlpha);
                        ColorChooserDialog.this.customSeekAValue.setText(String.format(Locale.US, TimeModel.NUMBER_FORMAT, Integer.valueOf(iAlpha)));
                    }
                    ColorChooserDialog.this.customSeekR.setProgress(Color.red(ColorChooserDialog.this.selectedCustomColor));
                    ColorChooserDialog.this.customSeekG.setProgress(Color.green(ColorChooserDialog.this.selectedCustomColor));
                    ColorChooserDialog.this.customSeekB.setProgress(Color.blue(ColorChooserDialog.this.selectedCustomColor));
                    ColorChooserDialog.this.isInSub(false);
                    ColorChooserDialog.this.topIndex(-1);
                    ColorChooserDialog.this.subIndex(-1);
                    ColorChooserDialog.this.invalidateDynamicButtonColors();
                }
            };
            this.customColorTextWatcher = textWatcher;
            this.customColorHex.addTextChangedListener(textWatcher);
            SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() { // from class: com.afollestad.materialdialogs.color.ColorChooserDialog.6
                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onStopTrackingTouch(SeekBar seekBar) {
                }

                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                    if (z) {
                        if (ColorChooserDialog.this.getBuilder().allowUserCustomAlpha) {
                            ColorChooserDialog.this.customColorHex.setText(String.format("%08X", Integer.valueOf(Color.argb(ColorChooserDialog.this.customSeekA.getProgress(), ColorChooserDialog.this.customSeekR.getProgress(), ColorChooserDialog.this.customSeekG.getProgress(), ColorChooserDialog.this.customSeekB.getProgress()))));
                        } else {
                            ColorChooserDialog.this.customColorHex.setText(String.format("%06X", Integer.valueOf(Color.rgb(ColorChooserDialog.this.customSeekR.getProgress(), ColorChooserDialog.this.customSeekG.getProgress(), ColorChooserDialog.this.customSeekB.getProgress()) & ViewCompat.MEASURED_SIZE_MASK)));
                        }
                    }
                    ColorChooserDialog.this.customSeekAValue.setText(String.format(TimeModel.NUMBER_FORMAT, Integer.valueOf(ColorChooserDialog.this.customSeekA.getProgress())));
                    ColorChooserDialog.this.customSeekRValue.setText(String.format(TimeModel.NUMBER_FORMAT, Integer.valueOf(ColorChooserDialog.this.customSeekR.getProgress())));
                    ColorChooserDialog.this.customSeekGValue.setText(String.format(TimeModel.NUMBER_FORMAT, Integer.valueOf(ColorChooserDialog.this.customSeekG.getProgress())));
                    ColorChooserDialog.this.customSeekBValue.setText(String.format(TimeModel.NUMBER_FORMAT, Integer.valueOf(ColorChooserDialog.this.customSeekB.getProgress())));
                }
            };
            this.customColorRgbListener = onSeekBarChangeListener;
            this.customSeekR.setOnSeekBarChangeListener(onSeekBarChangeListener);
            this.customSeekG.setOnSeekBarChangeListener(this.customColorRgbListener);
            this.customSeekB.setOnSeekBarChangeListener(this.customColorRgbListener);
            if (this.customSeekA.getVisibility() == 0) {
                this.customSeekA.setOnSeekBarChangeListener(this.customColorRgbListener);
                this.customColorHex.setText(String.format("%08X", Integer.valueOf(this.selectedCustomColor)));
                return;
            } else {
                this.customColorHex.setText(String.format("%06X", Integer.valueOf(16777215 & this.selectedCustomColor)));
                return;
            }
        }
        materialDialog.setTitle(getBuilder().title);
        materialDialog.setActionButton(DialogAction.NEUTRAL, getBuilder().customBtn);
        if (isInSub()) {
            materialDialog.setActionButton(DialogAction.NEGATIVE, getBuilder().backBtn);
        } else {
            materialDialog.setActionButton(DialogAction.NEGATIVE, getBuilder().cancelBtn);
        }
        this.grid.setVisibility(0);
        this.colorChooserCustomFrame.setVisibility(8);
        this.customColorHex.removeTextChangedListener(this.customColorTextWatcher);
        this.customColorTextWatcher = null;
        this.customSeekR.setOnSeekBarChangeListener(null);
        this.customSeekG.setOnSeekBarChangeListener(null);
        this.customSeekB.setOnSeekBarChangeListener(null);
        this.customColorRgbListener = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void invalidate() {
        if (this.grid.getAdapter() == null) {
            this.grid.setAdapter((ListAdapter) new ColorGridAdapter());
            this.grid.setSelector(ResourcesCompat.getDrawable(getResources(), R.drawable.md_transparent, null));
        } else {
            ((BaseAdapter) this.grid.getAdapter()).notifyDataSetChanged();
        }
        if (getDialog() != null) {
            getDialog().setTitle(getTitle());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Builder getBuilder() {
        if (getArguments() == null || !getArguments().containsKey("builder")) {
            return null;
        }
        return (Builder) getArguments().getSerializable("builder");
    }

    private void dismissIfNecessary(FragmentManager fragmentManager, String str) {
        Fragment fragmentFindFragmentByTag = fragmentManager.findFragmentByTag(str);
        if (fragmentFindFragmentByTag != null) {
            ((DialogFragment) fragmentFindFragmentByTag).dismiss();
            fragmentManager.beginTransaction().remove(fragmentFindFragmentByTag).commit();
        }
    }

    public ColorChooserDialog show(FragmentManager fragmentManager) {
        Builder builder = getBuilder();
        if (builder.colorsTop == null) {
            boolean z = builder.accentMode;
        }
        dismissIfNecessary(fragmentManager, "[MD_COLOR_CHOOSER]");
        show(fragmentManager, "[MD_COLOR_CHOOSER]");
        return this;
    }

    public ColorChooserDialog show(FragmentActivity fragmentActivity) {
        return show(fragmentActivity.getSupportFragmentManager());
    }

    public static class Builder implements Serializable {
        int[][] colorsSub;
        int[] colorsTop;
        final transient Context context;
        String mediumFont;
        int preselectColor;
        String regularFont;
        String tag;
        Theme theme;
        final int title;
        int titleSub;
        int doneBtn = R.string.md_done_label;
        int backBtn = R.string.md_back_label;
        int cancelBtn = R.string.md_cancel_label;
        int customBtn = R.string.md_custom_label;
        int presetsBtn = R.string.md_presets_label;
        boolean accentMode = false;
        boolean dynamicButtonColor = true;
        boolean allowUserCustom = true;
        boolean allowUserCustomAlpha = true;
        boolean setPreselectionColor = false;

        public Builder(Context context, int i) {
            this.context = context;
            this.title = i;
        }

        public Builder typeface(String str, String str2) {
            this.mediumFont = str;
            this.regularFont = str2;
            return this;
        }

        public Builder titleSub(int i) {
            this.titleSub = i;
            return this;
        }

        public Builder tag(String str) {
            this.tag = str;
            return this;
        }

        public Builder theme(Theme theme) {
            this.theme = theme;
            return this;
        }

        public Builder preselect(int i) {
            this.preselectColor = i;
            this.setPreselectionColor = true;
            return this;
        }

        public Builder accentMode(boolean z) {
            this.accentMode = z;
            return this;
        }

        public Builder doneButton(int i) {
            this.doneBtn = i;
            return this;
        }

        public Builder backButton(int i) {
            this.backBtn = i;
            return this;
        }

        public Builder cancelButton(int i) {
            this.cancelBtn = i;
            return this;
        }

        public Builder customButton(int i) {
            this.customBtn = i;
            return this;
        }

        public Builder presetsButton(int i) {
            this.presetsBtn = i;
            return this;
        }

        public Builder dynamicButtonColor(boolean z) {
            this.dynamicButtonColor = z;
            return this;
        }

        public Builder customColors(int[] iArr, int[][] iArr2) {
            this.colorsTop = iArr;
            this.colorsSub = iArr2;
            return this;
        }

        public Builder customColors(int i, int[][] iArr) {
            this.colorsTop = DialogUtils.getColorArray(this.context, i);
            this.colorsSub = iArr;
            return this;
        }

        public Builder allowUserColorInput(boolean z) {
            this.allowUserCustom = z;
            return this;
        }

        public Builder allowUserColorInputAlpha(boolean z) {
            this.allowUserCustomAlpha = z;
            return this;
        }

        public ColorChooserDialog build() {
            ColorChooserDialog colorChooserDialog = new ColorChooserDialog();
            Bundle bundle = new Bundle();
            bundle.putSerializable("builder", this);
            colorChooserDialog.setArguments(bundle);
            return colorChooserDialog;
        }

        public ColorChooserDialog show(FragmentManager fragmentManager) {
            ColorChooserDialog colorChooserDialogBuild = build();
            colorChooserDialogBuild.show(fragmentManager);
            return colorChooserDialogBuild;
        }

        public ColorChooserDialog show(FragmentActivity fragmentActivity) {
            return show(fragmentActivity.getSupportFragmentManager());
        }
    }

    private class ColorGridAdapter extends BaseAdapter {
        @Override // android.widget.Adapter
        public long getItemId(int i) {
            return i;
        }

        ColorGridAdapter() {
        }

        @Override // android.widget.Adapter
        public int getCount() {
            return ColorChooserDialog.this.isInSub() ? ColorChooserDialog.this.colorsSub[ColorChooserDialog.this.topIndex()].length : ColorChooserDialog.this.colorsTop.length;
        }

        @Override // android.widget.Adapter
        public Object getItem(int i) {
            return ColorChooserDialog.this.isInSub() ? Integer.valueOf(ColorChooserDialog.this.colorsSub[ColorChooserDialog.this.topIndex()][i]) : Integer.valueOf(ColorChooserDialog.this.colorsTop[i]);
        }

        @Override // android.widget.Adapter
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = new CircleView(ColorChooserDialog.this.getContext());
                view.setLayoutParams(new AbsListView.LayoutParams(ColorChooserDialog.this.circleSize, ColorChooserDialog.this.circleSize));
            }
            CircleView circleView = (CircleView) view;
            int i2 = ColorChooserDialog.this.isInSub() ? ColorChooserDialog.this.colorsSub[ColorChooserDialog.this.topIndex()][i] : ColorChooserDialog.this.colorsTop[i];
            circleView.setBackgroundColor(i2);
            if (ColorChooserDialog.this.isInSub()) {
                circleView.setSelected(ColorChooserDialog.this.subIndex() == i);
            } else {
                circleView.setSelected(ColorChooserDialog.this.topIndex() == i);
            }
            circleView.setTag(String.format("%d:%d", Integer.valueOf(i), Integer.valueOf(i2)));
            circleView.setOnClickListener(ColorChooserDialog.this);
            circleView.setOnLongClickListener(ColorChooserDialog.this);
            return view;
        }
    }
}
