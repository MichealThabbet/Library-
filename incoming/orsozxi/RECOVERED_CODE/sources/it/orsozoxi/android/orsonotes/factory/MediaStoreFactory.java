package it.orsozoxi.android.orsonotes.factory;

/* loaded from: classes3.dex */
public class MediaStoreFactory {
    /* JADX WARN: Removed duplicated region for block: B:18:0x0034  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public android.net.Uri createURI(java.lang.String r5) {
        /*
            r4 = this;
            int r0 = r5.hashCode()
            r1 = 93166550(0x58d9bd6, float:1.3316821E-35)
            r2 = 2
            r3 = 1
            if (r0 == r1) goto L2a
            r1 = 100313435(0x5faa95b, float:2.3572098E-35)
            if (r0 == r1) goto L20
            r1 = 112202875(0x6b0147b, float:6.6233935E-35)
            if (r0 == r1) goto L16
            goto L34
        L16:
            java.lang.String r0 = "video"
            boolean r5 = r5.equals(r0)
            if (r5 == 0) goto L34
            r5 = 1
            goto L35
        L20:
            java.lang.String r0 = "image"
            boolean r5 = r5.equals(r0)
            if (r5 == 0) goto L34
            r5 = 0
            goto L35
        L2a:
            java.lang.String r0 = "audio"
            boolean r5 = r5.equals(r0)
            if (r5 == 0) goto L34
            r5 = 2
            goto L35
        L34:
            r5 = -1
        L35:
            if (r5 == 0) goto L43
            if (r5 == r3) goto L40
            if (r5 == r2) goto L3d
            r5 = 0
            return r5
        L3d:
            android.net.Uri r5 = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
            return r5
        L40:
            android.net.Uri r5 = android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI
            return r5
        L43:
            android.net.Uri r5 = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: it.orsozoxi.android.orsonotes.factory.MediaStoreFactory.createURI(java.lang.String):android.net.Uri");
    }
}
