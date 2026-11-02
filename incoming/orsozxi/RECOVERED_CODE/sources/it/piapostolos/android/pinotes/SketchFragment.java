package it.piapostolos.android.pinotes;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.larswerkman.holocolorpicker.ColorPicker;
import com.larswerkman.holocolorpicker.OpacityBar;
import com.larswerkman.holocolorpicker.SVBar;
import it.feio.android.checklistview.utils.AlphaManager;
import it.piapostolos.android.pinotes.R2;
import it.piapostolos.android.pinotes.helpers.LogDelegate;
import it.piapostolos.android.pinotes.models.ONStyle;
import it.piapostolos.android.pinotes.models.listeners.OnDrawChangedListener;
import it.piapostolos.android.pinotes.models.views.SketchView;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Objects;

/* loaded from: classes3.dex */
public class SketchFragment extends Fragment implements OnDrawChangedListener {

    @BindView(R2.id.sketch_erase)
    ImageView erase;

    @BindView(R2.id.sketch_eraser)
    ImageView eraser;
    private ImageView eraserImageView;
    private ColorPicker mColorPicker;

    @BindView(R2.id.drawing)
    SketchView mSketchView;
    private View popupEraserLayout;
    private View popupLayout;

    @BindView(R2.id.sketch_redo)
    ImageView redo;
    private int seekBarEraserProgress;
    private int seekBarStrokeProgress;
    private int size;

    @BindView(R2.id.sketch_stroke)
    ImageView stroke;
    private ImageView strokeImageView;

    @BindView(R2.id.sketch_undo)
    ImageView undo;

    @Override // androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setHasOptionsMenu(true);
        setRetainInstance(false);
    }

    @Override // androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) throws NoSuchMethodException, SecurityException {
        View viewInflate = layoutInflater.inflate(R.layout.fragment_sketch, viewGroup, false);
        ButterKnife.bind(this, viewInflate);
        return viewInflate;
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityCreated(Bundle bundle) throws Resources.NotFoundException {
        super.onActivityCreated(bundle);
        getMainActivity().getToolbar().setNavigationOnClickListener(new View.OnClickListener() { // from class: it.piapostolos.android.pinotes.-$$Lambda$SketchFragment$lWHAfJFoTtAPzlFQxIBwPloti-Q
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$onActivityCreated$0$SketchFragment(view);
            }
        });
        this.mSketchView.setOnDrawChangedListener(this);
        Uri uri = (Uri) getArguments().getParcelable("base");
        if (uri != null) {
            try {
                this.mSketchView.setBackgroundBitmap(getActivity(), BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(uri)));
            } catch (FileNotFoundException e) {
                LogDelegate.e("Error replacing sketch bitmap background", e);
            }
        }
        if (getMainActivity().getSupportActionBar() != null) {
            getMainActivity().getSupportActionBar().setDisplayShowTitleEnabled(true);
            getMainActivity().getSupportActionBar().setTitle(R.string.title_activity_sketch);
            getMainActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        this.stroke.setOnClickListener(new View.OnClickListener() { // from class: it.piapostolos.android.pinotes.-$$Lambda$SketchFragment$HotlI_b0-I2k_j44dnFJymV5gI0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$onActivityCreated$1$SketchFragment(view);
            }
        });
        AlphaManager.setAlpha(this.eraser, 0.4f);
        this.eraser.setOnClickListener(new View.OnClickListener() { // from class: it.piapostolos.android.pinotes.-$$Lambda$SketchFragment$Lcv51OyJcLEw59RsGVqMKPMK7Lw
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$onActivityCreated$2$SketchFragment(view);
            }
        });
        this.undo.setOnClickListener(new View.OnClickListener() { // from class: it.piapostolos.android.pinotes.-$$Lambda$SketchFragment$zLdSNrqk5H_JAziqQmJ3UfTiSSs
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$onActivityCreated$3$SketchFragment(view);
            }
        });
        this.redo.setOnClickListener(new View.OnClickListener() { // from class: it.piapostolos.android.pinotes.-$$Lambda$SketchFragment$jht-vmhmLz2orNVMZZt_N0k5x2Q
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$onActivityCreated$4$SketchFragment(view);
            }
        });
        this.erase.setOnClickListener(new AnonymousClass1());
        this.popupLayout = ((LayoutInflater) getActivity().getSystemService("layout_inflater")).inflate(R.layout.popup_sketch_stroke, (ViewGroup) null);
        this.popupEraserLayout = ((LayoutInflater) getActivity().getSystemService("layout_inflater")).inflate(R.layout.popup_sketch_eraser, (ViewGroup) null);
        this.strokeImageView = (ImageView) this.popupLayout.findViewById(R.id.stroke_circle);
        Drawable drawable = getResources().getDrawable(R.drawable.circle);
        this.size = drawable.getIntrinsicWidth();
        this.eraserImageView = (ImageView) this.popupEraserLayout.findViewById(R.id.stroke_circle);
        this.size = drawable.getIntrinsicWidth();
        setSeekbarProgress(7, 0);
        setSeekbarProgress(50, 1);
        ColorPicker colorPicker = (ColorPicker) this.popupLayout.findViewById(R.id.stroke_color_picker);
        this.mColorPicker = colorPicker;
        colorPicker.addSVBar((SVBar) this.popupLayout.findViewById(R.id.svbar));
        this.mColorPicker.addOpacityBar((OpacityBar) this.popupLayout.findViewById(R.id.opacitybar));
        ColorPicker colorPicker2 = this.mColorPicker;
        final SketchView sketchView = this.mSketchView;
        Objects.requireNonNull(sketchView);
        colorPicker2.setOnColorChangedListener(new ColorPicker.OnColorChangedListener() { // from class: it.piapostolos.android.pinotes.-$$Lambda$DcMX0g5D8jYR2lY9ps5UagztXsI
            @Override // com.larswerkman.holocolorpicker.ColorPicker.OnColorChangedListener
            public final void onColorChanged(int i) {
                sketchView.setStrokeColor(i);
            }
        });
        this.mColorPicker.setColor(this.mSketchView.getStrokeColor());
        this.mColorPicker.setOldCenterColor(this.mSketchView.getStrokeColor());
    }

    public /* synthetic */ void lambda$onActivityCreated$0$SketchFragment(View view) {
        getActivity().onBackPressed();
    }

    public /* synthetic */ void lambda$onActivityCreated$1$SketchFragment(View view) {
        if (this.mSketchView.getMode() == 0) {
            showPopup(view, 0);
            return;
        }
        this.mSketchView.setMode(0);
        AlphaManager.setAlpha(this.eraser, 0.4f);
        AlphaManager.setAlpha(this.stroke, 1.0f);
    }

    public /* synthetic */ void lambda$onActivityCreated$2$SketchFragment(View view) {
        if (this.mSketchView.getMode() == 1) {
            showPopup(view, 1);
            return;
        }
        this.mSketchView.setMode(1);
        AlphaManager.setAlpha(this.stroke, 0.4f);
        AlphaManager.setAlpha(this.eraser, 1.0f);
    }

    public /* synthetic */ void lambda$onActivityCreated$3$SketchFragment(View view) {
        this.mSketchView.undo();
    }

    public /* synthetic */ void lambda$onActivityCreated$4$SketchFragment(View view) {
        this.mSketchView.redo();
    }

    /* renamed from: it.piapostolos.android.pinotes.SketchFragment$1, reason: invalid class name */
    class AnonymousClass1 implements View.OnClickListener {
        AnonymousClass1() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            askForErase();
        }

        private void askForErase() {
            new MaterialDialog.Builder(SketchFragment.this.getActivity()).content(R.string.erase_sketch).positiveText(R.string.confirm).onPositive(new MaterialDialog.SingleButtonCallback() { // from class: it.piapostolos.android.pinotes.-$$Lambda$SketchFragment$1$AMepxcdili_m1_ebjEnYC4QQARQ
                @Override // com.afollestad.materialdialogs.MaterialDialog.SingleButtonCallback
                public final void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                    this.f$0.lambda$askForErase$0$SketchFragment$1(materialDialog, dialogAction);
                }
            }).build().show();
        }

        public /* synthetic */ void lambda$askForErase$0$SketchFragment$1(MaterialDialog materialDialog, DialogAction dialogAction) {
            SketchFragment.this.mSketchView.erase();
        }
    }

    @Override // androidx.fragment.app.Fragment
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            getActivity().onBackPressed();
        } else {
            LogDelegate.e("Wrong element choosen: " + menuItem.getItemId());
        }
        return super.onOptionsItemSelected(menuItem);
    }

    public void save() {
        Bitmap bitmap = this.mSketchView.getBitmap();
        if (bitmap != null) {
            try {
                Uri uri = (Uri) getArguments().getParcelable("output");
                File file = new File(uri.getPath());
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.PNG, 90, fileOutputStream);
                fileOutputStream.close();
                if (file.exists()) {
                    getMainActivity().sketchUri = uri;
                } else {
                    getMainActivity().showMessage(R.string.error, ONStyle.ALERT);
                }
            } catch (Exception e) {
                LogDelegate.e("Error writing sketch image data", e);
            }
        }
    }

    private void showPopup(View view, final int i) {
        View viewFindViewById;
        boolean z = i == 1;
        final int color = this.mColorPicker.getColor();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(new DisplayMetrics());
        PopupWindow popupWindow = new PopupWindow(getActivity());
        popupWindow.setContentView(z ? this.popupEraserLayout : this.popupLayout);
        popupWindow.setWidth(-2);
        popupWindow.setHeight(-2);
        popupWindow.setFocusable(true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() { // from class: it.piapostolos.android.pinotes.-$$Lambda$SketchFragment$8IKyP4-wovtM6hIRpTND_3SyHmY
            @Override // android.widget.PopupWindow.OnDismissListener
            public final void onDismiss() {
                this.f$0.lambda$showPopup$5$SketchFragment(color);
            }
        });
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.showAsDropDown(view);
        if (z) {
            viewFindViewById = this.popupEraserLayout.findViewById(R.id.stroke_seekbar);
        } else {
            viewFindViewById = this.popupLayout.findViewById(R.id.stroke_seekbar);
        }
        SeekBar seekBar = (SeekBar) viewFindViewById;
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: it.piapostolos.android.pinotes.SketchFragment.2
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar2) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar2) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar2, int i2, boolean z2) {
                SketchFragment.this.setSeekbarProgress(i2, i);
            }
        });
        seekBar.setProgress(z ? this.seekBarEraserProgress : this.seekBarStrokeProgress);
    }

    public /* synthetic */ void lambda$showPopup$5$SketchFragment(int i) {
        if (this.mColorPicker.getColor() != i) {
            this.mColorPicker.setOldCenterColor(i);
        }
    }

    protected void setSeekbarProgress(int i, int i2) {
        int i3 = i > 1 ? i : 1;
        int iRound = Math.round((this.size / 100.0f) * i3);
        int i4 = (this.size - iRound) / 2;
        LogDelegate.v("Stroke size " + iRound + " (" + i3 + "%)");
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(iRound, iRound);
        layoutParams.setMargins(i4, i4, i4, i4);
        if (i2 == 0) {
            this.strokeImageView.setLayoutParams(layoutParams);
            this.seekBarStrokeProgress = i;
        } else {
            this.eraserImageView.setLayoutParams(layoutParams);
            this.seekBarEraserProgress = i;
        }
        this.mSketchView.setSize(iRound, i2);
    }

    @Override // it.piapostolos.android.pinotes.models.listeners.OnDrawChangedListener
    public void onDrawChanged() {
        if (this.mSketchView.getPaths().isEmpty()) {
            AlphaManager.setAlpha(this.undo, 1.0f);
        } else {
            AlphaManager.setAlpha(this.undo, 0.4f);
        }
        if (this.mSketchView.getUndoneCount() > 0) {
            AlphaManager.setAlpha(this.redo, 1.0f);
        } else {
            AlphaManager.setAlpha(this.redo, 0.4f);
        }
    }

    private NotesMainActivity getMainActivity() {
        return (NotesMainActivity) getActivity();
    }
}
