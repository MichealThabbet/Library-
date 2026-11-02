package com.nhaarman.listviewanimations.itemmanipulation.dragdrop;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;

/* loaded from: classes2.dex */
class BitmapUtils {
    private BitmapUtils() {
    }

    static Bitmap getBitmapFromView(View view) {
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        view.draw(new Canvas(bitmapCreateBitmap));
        return bitmapCreateBitmap;
    }
}
