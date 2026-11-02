package it.orsozoxi.android.orsonotes.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.Looper;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import it.feio.android.simplegallery.util.BitmapUtils;
import it.orsozoxi.android.orsonotes.OrsoNotes;
import it.orsozoxi.android.orsonotes.R;
import it.orsozoxi.android.orsonotes.helpers.AttachmentsHelper;
import it.orsozoxi.android.orsonotes.models.Attachment;
import java.util.concurrent.ExecutionException;
import org.apache.commons.io.FilenameUtils;

/* loaded from: classes3.dex */
public class BitmapHelper {
    private static final String ANDROID_RESOURCE = "android.resource://";

    private BitmapHelper() {
    }

    public static Bitmap getBitmapFromAttachment(Context context, Attachment attachment, int i, int i2) {
        attachment.getUri().getPath();
        if (AttachmentsHelper.typeOf(attachment, ConstantsBase.MIME_TYPE_VIDEO, ConstantsBase.MIME_TYPE_IMAGE, ConstantsBase.MIME_TYPE_SKETCH)) {
            return getImageBitmap(context, attachment, i, i2);
        }
        if (ConstantsBase.MIME_TYPE_AUDIO.equals(attachment.getMime_type())) {
            return ThumbnailUtils.extractThumbnail(BitmapUtils.decodeSampledBitmapFromResourceMemOpt(context.getResources().openRawResource(R.raw.play), i, i2), i, i2);
        }
        if (!ConstantsBase.MIME_TYPE_FILES.equals(attachment.getMime_type())) {
            return null;
        }
        if (ConstantsBase.MIME_TYPE_CONTACT_EXT.equals(FilenameUtils.getExtension(attachment.getName()))) {
            return ThumbnailUtils.extractThumbnail(BitmapUtils.decodeSampledBitmapFromResourceMemOpt(context.getResources().openRawResource(R.raw.vcard), i, i2), i, i2);
        }
        return ThumbnailUtils.extractThumbnail(BitmapUtils.decodeSampledBitmapFromResourceMemOpt(context.getResources().openRawResource(R.raw.files), i, i2), i, i2);
    }

    private static Bitmap getImageBitmap(Context context, Attachment attachment, int i, int i2) {
        try {
            if (Looper.getMainLooper() == Looper.myLooper()) {
                return BitmapUtils.getThumbnail(context, attachment.getUri(), i, i2);
            }
            return Glide.with(OrsoNotes.getAppContext()).asBitmap().apply(new RequestOptions().centerCrop().error(R.drawable.attachment_broken)).load(attachment.getUri()).submit(i, i2).get();
        } catch (InterruptedException | NullPointerException | ExecutionException unused) {
            return null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x0057  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static android.net.Uri getThumbnailUri(android.content.Context r10, it.orsozoxi.android.orsonotes.models.Attachment r11) {
        /*
            android.net.Uri r11 = r11.getUri()
            java.lang.String r0 = r11.toString()
            java.lang.String r0 = it.orsozoxi.android.orsonotes.utils.StorageHelper.getMimeType(r0)
            boolean r1 = android.text.TextUtils.isEmpty(r0)
            java.lang.String r2 = "android.resource://"
            java.lang.String r3 = "/"
            if (r1 != 0) goto La9
            java.lang.String[] r1 = r0.split(r3)
            r4 = 0
            r1 = r1[r4]
            java.lang.String[] r0 = r0.split(r3)
            r5 = 1
            r0 = r0[r5]
            r6 = -1
            int r7 = r1.hashCode()
            r8 = 93166550(0x58d9bd6, float:1.3316821E-35)
            r9 = 2
            if (r7 == r8) goto L4d
            r8 = 100313435(0x5faa95b, float:2.3572098E-35)
            if (r7 == r8) goto L44
            r4 = 112202875(0x6b0147b, float:6.6233935E-35)
            if (r7 == r4) goto L3a
            goto L57
        L3a:
            java.lang.String r4 = "video"
            boolean r1 = r1.equals(r4)
            if (r1 == 0) goto L57
            r4 = 1
            goto L58
        L44:
            java.lang.String r7 = "image"
            boolean r1 = r1.equals(r7)
            if (r1 == 0) goto L57
            goto L58
        L4d:
            java.lang.String r4 = "audio"
            boolean r1 = r1.equals(r4)
            if (r1 == 0) goto L57
            r4 = 2
            goto L58
        L57:
            r4 = -1
        L58:
            if (r4 == 0) goto Lc8
            if (r4 == r5) goto Lc8
            if (r4 == r9) goto L89
            java.lang.String r11 = "x-vcard"
            boolean r11 = r11.equals(r0)
            if (r11 == 0) goto L69
            int r11 = it.orsozoxi.android.orsonotes.R.raw.vcard
            goto L6b
        L69:
            int r11 = it.orsozoxi.android.orsonotes.R.raw.files
        L6b:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r0.append(r2)
            java.lang.String r10 = r10.getPackageName()
            r0.append(r10)
            r0.append(r3)
            r0.append(r11)
            java.lang.String r10 = r0.toString()
            android.net.Uri r11 = android.net.Uri.parse(r10)
            goto Lc8
        L89:
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            r11.append(r2)
            java.lang.String r10 = r10.getPackageName()
            r11.append(r10)
            r11.append(r3)
            int r10 = it.orsozoxi.android.orsonotes.R.raw.play
            r11.append(r10)
            java.lang.String r10 = r11.toString()
            android.net.Uri r11 = android.net.Uri.parse(r10)
            goto Lc8
        La9:
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            r11.append(r2)
            java.lang.String r10 = r10.getPackageName()
            r11.append(r10)
            r11.append(r3)
            int r10 = it.orsozoxi.android.orsonotes.R.raw.files
            r11.append(r10)
            java.lang.String r10 = r11.toString()
            android.net.Uri r11 = android.net.Uri.parse(r10)
        Lc8:
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: it.orsozoxi.android.orsonotes.utils.BitmapHelper.getThumbnailUri(android.content.Context, it.orsozoxi.android.orsonotes.models.Attachment):android.net.Uri");
    }
}
