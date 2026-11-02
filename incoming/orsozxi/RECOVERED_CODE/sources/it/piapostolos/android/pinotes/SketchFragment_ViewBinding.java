package it.piapostolos.android.pinotes;

import android.view.View;
import android.widget.ImageView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import it.piapostolos.android.pinotes.models.views.SketchView;

/* loaded from: classes3.dex */
public class SketchFragment_ViewBinding implements Unbinder {
    private SketchFragment target;

    public SketchFragment_ViewBinding(SketchFragment sketchFragment, View view) {
        this.target = sketchFragment;
        sketchFragment.stroke = (ImageView) Utils.findRequiredViewAsType(view, R.id.sketch_stroke, "field 'stroke'", ImageView.class);
        sketchFragment.eraser = (ImageView) Utils.findRequiredViewAsType(view, R.id.sketch_eraser, "field 'eraser'", ImageView.class);
        sketchFragment.mSketchView = (SketchView) Utils.findRequiredViewAsType(view, R.id.drawing, "field 'mSketchView'", SketchView.class);
        sketchFragment.undo = (ImageView) Utils.findRequiredViewAsType(view, R.id.sketch_undo, "field 'undo'", ImageView.class);
        sketchFragment.redo = (ImageView) Utils.findRequiredViewAsType(view, R.id.sketch_redo, "field 'redo'", ImageView.class);
        sketchFragment.erase = (ImageView) Utils.findRequiredViewAsType(view, R.id.sketch_erase, "field 'erase'", ImageView.class);
    }

    @Override // butterknife.Unbinder
    public void unbind() {
        SketchFragment sketchFragment = this.target;
        if (sketchFragment == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        sketchFragment.stroke = null;
        sketchFragment.eraser = null;
        sketchFragment.mSketchView = null;
        sketchFragment.undo = null;
        sketchFragment.redo = null;
        sketchFragment.erase = null;
    }
}
