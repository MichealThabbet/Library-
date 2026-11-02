package it.feio.android.simplegallery.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.media.ExifInterface;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import it.feio.android.simplegallery.R;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes2.dex */
public class BitmapUtils {
    public static Bitmap getThumbnail(Context context, Uri uri, int i, int i2) throws Throwable {
        int width;
        int height;
        int width2;
        Bitmap bitmapDecodeSampledFromUri = decodeSampledFromUri(context, uri, i, i2);
        if (bitmapDecodeSampledFromUri.getWidth() < i && bitmapDecodeSampledFromUri.getHeight() < i2) {
            return ThumbnailUtils.extractThumbnail(bitmapDecodeSampledFromUri, i, i2);
        }
        int width3 = bitmapDecodeSampledFromUri.getWidth();
        int height2 = bitmapDecodeSampledFromUri.getHeight();
        float height3 = (i / i2) * (bitmapDecodeSampledFromUri.getHeight() / bitmapDecodeSampledFromUri.getWidth());
        int height4 = 0;
        if (height3 < 1.0f) {
            width = (int) (bitmapDecodeSampledFromUri.getWidth() * height3);
            height = height2;
            width2 = ((int) (bitmapDecodeSampledFromUri.getWidth() - (bitmapDecodeSampledFromUri.getWidth() * height3))) / 2;
        } else {
            width = width3;
            height = (int) (bitmapDecodeSampledFromUri.getHeight() / height3);
            height4 = ((int) (bitmapDecodeSampledFromUri.getHeight() - (bitmapDecodeSampledFromUri.getHeight() / height3))) / 2;
            width2 = 0;
        }
        int iNeededRotation = neededRotation(new File(uri.getPath()));
        if (iNeededRotation != 0) {
            Matrix matrix = new Matrix();
            matrix.postRotate(iNeededRotation);
            return Bitmap.createBitmap(bitmapDecodeSampledFromUri, width2, height4, width, height, matrix, true);
        }
        return Bitmap.createBitmap(bitmapDecodeSampledFromUri, width2, height4, width, height);
    }

    public static Bitmap getFullImage(Context context, Uri uri, int i, int i2) throws Throwable {
        String fileExtensionFromUrl = MimeTypeMap.getFileExtensionFromUrl(uri.getPath());
        boolean z = fileExtensionFromUrl != null && MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtensionFromUrl).contains("video/");
        if (z) {
            if (!z) {
                return null;
            }
            Bitmap bitmapCreateVideoThumbnail = ThumbnailUtils.createVideoThumbnail(uri.getPath(), 1);
            if (bitmapCreateVideoThumbnail == null) {
                bitmapCreateVideoThumbnail = BitmapFactory.decodeResource(context.getResources(), R.drawable.image_broken);
            }
            return createVideoThumbnail(context, bitmapCreateVideoThumbnail, i, i2);
        }
        Bitmap bitmapDecodeSampledFromUri = decodeSampledFromUri(context, uri, i, i2);
        int iNeededRotation = neededRotation(new File(uri.getPath()));
        if (iNeededRotation == 0) {
            return bitmapDecodeSampledFromUri;
        }
        Matrix matrix = new Matrix();
        matrix.postRotate(iNeededRotation);
        return Bitmap.createBitmap(bitmapDecodeSampledFromUri, 0, 0, bitmapDecodeSampledFromUri.getWidth(), bitmapDecodeSampledFromUri.getHeight(), matrix, true);
    }

    public static int neededRotation(File file) {
        try {
            int attributeInt = new ExifInterface(file.getAbsolutePath()).getAttributeInt("Orientation", 1);
            if (attributeInt == 8) {
                return 270;
            }
            if (attributeInt == 3) {
                return 180;
            }
            return attributeInt == 6 ? 90 : 0;
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static Bitmap decodeSampledFromUri(Context context, Uri uri, int i, int i2) throws Throwable {
        InputStream inputStreamOpenInputStream;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        InputStream inputStream = null;
        try {
            InputStream inputStreamOpenInputStream2 = context.getContentResolver().openInputStream(uri);
            try {
                BitmapFactory.decodeStream(inputStreamOpenInputStream2, null, options);
                options.inSampleSize = calculateInSampleSize(options, i, i2);
                options.inJustDecodeBounds = false;
                inputStreamOpenInputStream = context.getContentResolver().openInputStream(uri);
                try {
                    Bitmap bitmapDecodeStream = BitmapFactory.decodeStream(inputStreamOpenInputStream, null, options);
                    try {
                        inputStreamOpenInputStream2.close();
                        inputStreamOpenInputStream.close();
                    } catch (IOException | NullPointerException unused) {
                        Log.e("BitmapUtils", "Failed to close streams");
                    }
                    return bitmapDecodeStream;
                } catch (IOException unused2) {
                    inputStream = inputStreamOpenInputStream2;
                    try {
                        Log.e("BitmapUtils", "Error");
                        Bitmap bitmapDecodeResource = BitmapFactory.decodeResource(context.getResources(), R.drawable.image_broken);
                        try {
                            inputStream.close();
                            inputStreamOpenInputStream.close();
                        } catch (IOException | NullPointerException unused3) {
                            Log.e("BitmapUtils", "Failed to close streams");
                        }
                        return bitmapDecodeResource;
                    } catch (Throwable th) {
                        th = th;
                        try {
                            inputStream.close();
                            inputStreamOpenInputStream.close();
                        } catch (IOException | NullPointerException unused4) {
                            Log.e("BitmapUtils", "Failed to close streams");
                        }
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    inputStream = inputStreamOpenInputStream2;
                    inputStream.close();
                    inputStreamOpenInputStream.close();
                    throw th;
                }
            } catch (IOException unused5) {
                inputStreamOpenInputStream = null;
            } catch (Throwable th3) {
                th = th3;
                inputStreamOpenInputStream = null;
            }
        } catch (IOException unused6) {
            inputStreamOpenInputStream = null;
        } catch (Throwable th4) {
            th = th4;
            inputStreamOpenInputStream = null;
        }
    }

    public static Bitmap decodeSampledBitmapFromResourceMemOpt(InputStream inputStream, int i, int i2) throws IOException {
        byte[] bArr = new byte[0];
        byte[] bArr2 = new byte[1024];
        int i3 = 0;
        while (true) {
            try {
                int i4 = inputStream.read(bArr2);
                if (i4 <= -1) {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                    BitmapFactory.decodeByteArray(bArr, 0, i3, options);
                    options.inSampleSize = calculateInSampleSize(options, i, i2);
                    options.inPurgeable = true;
                    options.inInputShareable = true;
                    options.inJustDecodeBounds = false;
                    options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                    return BitmapFactory.decodeByteArray(bArr, 0, i3, options);
                }
                if (i4 != 0) {
                    int i5 = i3 + i4;
                    if (i5 > bArr.length) {
                        byte[] bArr3 = new byte[i5 * 2];
                        System.arraycopy(bArr, 0, bArr3, 0, i3);
                        bArr = bArr3;
                    }
                    System.arraycopy(bArr2, 0, bArr, i3, i4);
                    i3 = i5;
                }
            } catch (Exception e) {
                Log.d("BitmapUtils", "Explosion processing upgrade!", e);
                return null;
            }
        }
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int i, int i2) throws FileNotFoundException {
        int i3 = options.outHeight;
        int i4 = options.outWidth;
        int i5 = 1;
        if (i3 > i2 || i4 > i) {
            int i6 = i3 / 2;
            int i7 = i4 / 2;
            while (i6 / i5 > i2 && i7 / i5 > i) {
                i5 *= 2;
            }
            while (true) {
                if (i6 / i5 <= i2 * 2 && i7 / i5 <= i * 2) {
                    break;
                }
                i5 *= 2;
            }
        }
        return i5;
    }

    public static Bitmap drawTextToBitmap(Context context, Bitmap bitmap, String str, Integer num, Integer num2, float f, Integer num3) {
        int width;
        int height;
        float f2 = context.getResources().getDisplayMetrics().density;
        Bitmap.Config config = bitmap.getConfig();
        if (config == null) {
            config = Bitmap.Config.RGB_565;
        }
        if (!bitmap.isMutable()) {
            bitmap = bitmap.copy(config, true);
        }
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(1);
        paint.setColor(num3.intValue());
        float width2 = (int) (((f * f2) * bitmap.getWidth()) / 100.0f);
        if (width2 >= 15.0f) {
            width2 = 15.0f;
        }
        paint.setTextSize(width2);
        paint.setShadowLayer(1.0f, 0.0f, 1.0f, -1);
        Rect rect = new Rect();
        paint.getTextBounds(str, 0, str.length(), rect);
        if (num == null) {
            width = (bitmap.getWidth() - rect.width()) / 2;
        } else if (num.intValue() >= 0) {
            width = num.intValue();
        } else {
            width = (bitmap.getWidth() - rect.width()) - num.intValue();
        }
        if (num2 == null) {
            height = (bitmap.getHeight() - rect.height()) / 2;
        } else if (num2.intValue() >= 0) {
            height = num2.intValue();
        } else {
            height = (bitmap.getHeight() - rect.height()) + num2.intValue();
        }
        canvas.drawText(str, width, height, paint);
        return bitmap;
    }

    public static Uri getUri(Context context, int i) {
        return Uri.parse("android.resource://" + context.getPackageName() + "/" + i);
    }

    private static Bitmap scaleImage(Context context, Bitmap bitmap, int i, int i2) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float fDpToPx = dpToPx(context, i) / width;
        float fDpToPx2 = dpToPx(context, i2) / height;
        if (fDpToPx < fDpToPx2) {
            fDpToPx = fDpToPx2;
        }
        Matrix matrix = new Matrix();
        matrix.postScale(fDpToPx, fDpToPx);
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
    }

    public static Bitmap rotateImage(Bitmap bitmap, String str) {
        try {
            int attributeInt = new ExifInterface(str).getAttributeInt("Orientation", 1);
            Matrix matrix = new Matrix();
            if (attributeInt == 6) {
                matrix.postRotate(6.0f);
            } else if (attributeInt == 3) {
                matrix.postRotate(3.0f);
            } else if (attributeInt == 8) {
                matrix.postRotate(8.0f);
            }
            return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        } catch (Exception unused) {
            Log.d("AndroidTouchGallery", "Could not rotate the image");
            return bitmap;
        }
    }

    public static InputStream getBitmapInputStream(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream);
        return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    }

    public static Bitmap createVideoThumbnail(Context context, Bitmap bitmap, int i, int i2) {
        Bitmap bitmapExtractThumbnail = ThumbnailUtils.extractThumbnail(bitmap, i, i2);
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        canvas.drawBitmap(bitmapExtractThumbnail, 0.0f, 0.0f, (Paint) null);
        int iCalculateVideoMarkSize = calculateVideoMarkSize(i, i2);
        canvas.drawBitmap(ThumbnailUtils.extractThumbnail(BitmapFactory.decodeResource(context.getResources(), R.drawable.play_no_bg), iCalculateVideoMarkSize, iCalculateVideoMarkSize), (bitmapExtractThumbnail.getWidth() / 2) - (r4.getWidth() / 2), (bitmapExtractThumbnail.getHeight() / 2) - (r4.getHeight() / 2), (Paint) null);
        return bitmapCreateBitmap;
    }

    private static int calculateVideoMarkSize(int i, int i2) {
        int iMin = Math.min(i, i2) / 9;
        if (iMin < 30) {
            iMin = 30;
        }
        if (iMin > 200) {
            return 200;
        }
        return iMin;
    }

    public static int dpToPx(Context context, int i) {
        return Math.round(i * context.getResources().getDisplayMetrics().density);
    }

    public static int getDominantColor(Bitmap bitmap) {
        return getDominantColor(bitmap, true);
    }

    public static int getDominantColor(Bitmap bitmap, boolean z) {
        if (bitmap == null) {
            return Color.argb(255, 255, 255, 255);
        }
        int[] iArr = new int[36];
        float[] fArr = new float[36];
        float[] fArr2 = new float[36];
        float[] fArr3 = new float[36];
        float[] fArr4 = new float[3];
        int height = bitmap.getHeight();
        int width = bitmap.getWidth();
        int[] iArr2 = new int[width * height];
        bitmap.getPixels(iArr2, 0, width, 0, 0, width, height);
        int i = -1;
        for (int i2 = 0; i2 < height; i2 += 2) {
            for (int i3 = 0; i3 < width; i3 += 2) {
                Color.colorToHSV(iArr2[(i2 * width) + i3], fArr4);
                if (!z || (fArr4[1] > 0.05f && fArr4[2] > 0.35f)) {
                    int iFloor = (int) Math.floor(fArr4[0] / 10.0f);
                    fArr[iFloor] = fArr[iFloor] + fArr4[0];
                    fArr2[iFloor] = fArr2[iFloor] + fArr4[1];
                    fArr3[iFloor] = fArr3[iFloor] + fArr4[2];
                    iArr[iFloor] = iArr[iFloor] + 1;
                    if (i < 0 || iArr[iFloor] > iArr[i]) {
                        i = iFloor;
                    }
                }
            }
        }
        if (i < 0) {
            return Color.argb(255, 255, 255, 255);
        }
        fArr4[0] = fArr[i] / iArr[i];
        fArr4[1] = fArr2[i] / iArr[i];
        fArr4[2] = fArr3[i] / iArr[i];
        return Color.HSVToColor(fArr4);
    }

    public static void changeImageViewDrawableColor(ImageView imageView, int i) {
        imageView.getDrawable().mutate().setColorFilter(i, PorterDuff.Mode.SRC_ATOP);
    }
}
