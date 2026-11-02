package it.feio.android.simplegallery.async;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.ImageView;
import it.feio.android.simplegallery.util.BitmapUtils;
import java.io.File;

/* loaded from: classes2.dex */
public class ImageLoadTask extends AsyncTask<String, Integer, Bitmap> {
    int height;
    Context mContext;
    ImageView mImageView;
    int width;

    public ImageLoadTask(Context context, ImageView imageView, int i, int i2) {
        this.mContext = context;
        this.mImageView = imageView;
        this.width = i;
        this.height = i2;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public Bitmap doInBackground(String... strArr) {
        return BitmapUtils.getFullImage(this.mContext, Uri.fromFile(new File(strArr[0])), this.width, this.height);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public void onPostExecute(Bitmap bitmap) {
        if (bitmap == null) {
            this.mImageView.setScaleType(ImageView.ScaleType.CENTER);
        } else {
            this.mImageView.setScaleType(ImageView.ScaleType.MATRIX);
            this.mImageView.setImageBitmap(bitmap);
        }
    }
}
