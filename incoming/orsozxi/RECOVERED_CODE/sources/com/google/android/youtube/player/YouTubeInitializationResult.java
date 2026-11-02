package com.google.android.youtube.player;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import com.google.android.youtube.player.internal.ab;
import com.google.android.youtube.player.internal.y;

/* loaded from: classes2.dex */
public enum YouTubeInitializationResult {
    SUCCESS,
    INTERNAL_ERROR,
    UNKNOWN_ERROR,
    SERVICE_MISSING,
    SERVICE_VERSION_UPDATE_REQUIRED,
    SERVICE_DISABLED,
    SERVICE_INVALID,
    ERROR_CONNECTING_TO_SERVICE,
    CLIENT_LIBRARY_UPDATE_REQUIRED,
    NETWORK_ERROR,
    DEVELOPER_KEY_INVALID,
    INVALID_APPLICATION_SIGNATURE;

    /* renamed from: com.google.android.youtube.player.YouTubeInitializationResult$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[YouTubeInitializationResult.values().length];
            a = iArr;
            try {
                iArr[YouTubeInitializationResult.SERVICE_MISSING.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                a[YouTubeInitializationResult.SERVICE_DISABLED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                a[YouTubeInitializationResult.SERVICE_VERSION_UPDATE_REQUIRED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    private static final class a implements DialogInterface.OnClickListener {
        private final Activity a;
        private final Intent b;
        private final int c;

        public a(Activity activity, Intent intent, int i) {
            this.a = (Activity) ab.a(activity);
            this.b = (Intent) ab.a(intent);
            this.c = ((Integer) ab.a(Integer.valueOf(i))).intValue();
        }

        @Override // android.content.DialogInterface.OnClickListener
        public final void onClick(DialogInterface dialogInterface, int i) {
            try {
                this.a.startActivityForResult(this.b, this.c);
                dialogInterface.dismiss();
            } catch (ActivityNotFoundException e) {
                y.a("Can't perform resolution for YouTubeInitalizationError", e);
            }
        }
    }

    public final Dialog getErrorDialog(Activity activity, int i) {
        return getErrorDialog(activity, i, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0026  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final android.app.Dialog getErrorDialog(android.app.Activity r6, int r7, android.content.DialogInterface.OnCancelListener r8) {
        /*
            r5 = this;
            android.app.AlertDialog$Builder r0 = new android.app.AlertDialog$Builder
            r0.<init>(r6)
            if (r8 == 0) goto La
            r0.setOnCancelListener(r8)
        La:
            int[] r8 = com.google.android.youtube.player.YouTubeInitializationResult.AnonymousClass1.a
            int r1 = r5.ordinal()
            r8 = r8[r1]
            r1 = 3
            r2 = 2
            r3 = 1
            if (r8 == r3) goto L26
            if (r8 == r2) goto L1d
            if (r8 == r1) goto L26
            r8 = 0
            goto L2e
        L1d:
            java.lang.String r8 = com.google.android.youtube.player.internal.z.a(r6)
            android.content.Intent r8 = com.google.android.youtube.player.internal.z.a(r8)
            goto L2e
        L26:
            java.lang.String r8 = com.google.android.youtube.player.internal.z.a(r6)
            android.content.Intent r8 = com.google.android.youtube.player.internal.z.b(r8)
        L2e:
            com.google.android.youtube.player.YouTubeInitializationResult$a r4 = new com.google.android.youtube.player.YouTubeInitializationResult$a
            r4.<init>(r6, r8, r7)
            com.google.android.youtube.player.internal.m r7 = new com.google.android.youtube.player.internal.m
            r7.<init>(r6)
            int[] r6 = com.google.android.youtube.player.YouTubeInitializationResult.AnonymousClass1.a
            int r8 = r5.ordinal()
            r6 = r6[r8]
            if (r6 == r3) goto L8d
            if (r6 == r2) goto L7e
            if (r6 == r1) goto L67
            java.lang.IllegalArgumentException r6 = new java.lang.IllegalArgumentException
            java.lang.String r7 = "Unexpected errorReason: "
            java.lang.String r8 = r5.name()
            java.lang.String r8 = java.lang.String.valueOf(r8)
            int r0 = r8.length()
            if (r0 == 0) goto L5d
            java.lang.String r7 = r7.concat(r8)
            goto L63
        L5d:
            java.lang.String r8 = new java.lang.String
            r8.<init>(r7)
            r7 = r8
        L63:
            r6.<init>(r7)
            throw r6
        L67:
            java.lang.String r6 = r7.h
            android.app.AlertDialog$Builder r6 = r0.setTitle(r6)
            java.lang.String r8 = r7.i
            android.app.AlertDialog$Builder r6 = r6.setMessage(r8)
            java.lang.String r7 = r7.j
        L75:
            android.app.AlertDialog$Builder r6 = r6.setPositiveButton(r7, r4)
            android.app.AlertDialog r6 = r6.create()
            return r6
        L7e:
            java.lang.String r6 = r7.e
            android.app.AlertDialog$Builder r6 = r0.setTitle(r6)
            java.lang.String r8 = r7.f
            android.app.AlertDialog$Builder r6 = r6.setMessage(r8)
            java.lang.String r7 = r7.g
            goto L75
        L8d:
            java.lang.String r6 = r7.b
            android.app.AlertDialog$Builder r6 = r0.setTitle(r6)
            java.lang.String r8 = r7.c
            android.app.AlertDialog$Builder r6 = r6.setMessage(r8)
            java.lang.String r7 = r7.d
            goto L75
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.youtube.player.YouTubeInitializationResult.getErrorDialog(android.app.Activity, int, android.content.DialogInterface$OnCancelListener):android.app.Dialog");
    }

    public final boolean isUserRecoverableError() {
        int i = AnonymousClass1.a[ordinal()];
        return i == 1 || i == 2 || i == 3;
    }
}
