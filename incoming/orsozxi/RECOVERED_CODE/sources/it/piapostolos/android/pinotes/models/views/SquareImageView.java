package it.piapostolos.android.pinotes.models.views;

import android.content.Context;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.widget.ImageView;
import java.lang.ref.WeakReference;

/* loaded from: classes3.dex */
public class SquareImageView extends ImageView {
    private WeakReference<AsyncTask<?, ?, ?>> mAsyncTaskReference;

    public SquareImageView(Context context) {
        super(context);
        setScaleType(ImageView.ScaleType.CENTER_CROP);
    }

    public SquareImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public SquareImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());
    }

    public void setAsyncTask(AsyncTask<?, ?, ?> asyncTask) {
        this.mAsyncTaskReference = new WeakReference<>(asyncTask);
    }

    public AsyncTask<?, ?, ?> getAsyncTask() {
        WeakReference<AsyncTask<?, ?, ?>> weakReference = this.mAsyncTaskReference;
        if (weakReference != null) {
            return weakReference.get();
        }
        return null;
    }
}
