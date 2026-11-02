package com.afollestad.materialdialogs;

import android.os.Build;
import androidx.core.view.GravityCompat;

/* loaded from: classes.dex */
public enum GravityEnum {
    START,
    CENTER,
    END;

    private static final boolean HAS_RTL;

    static {
        HAS_RTL = Build.VERSION.SDK_INT >= 17;
    }

    /* renamed from: com.afollestad.materialdialogs.GravityEnum$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$afollestad$materialdialogs$GravityEnum;

        static {
            int[] iArr = new int[GravityEnum.values().length];
            $SwitchMap$com$afollestad$materialdialogs$GravityEnum = iArr;
            try {
                iArr[GravityEnum.START.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$afollestad$materialdialogs$GravityEnum[GravityEnum.CENTER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$afollestad$materialdialogs$GravityEnum[GravityEnum.END.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public int getGravityInt() {
        int i = AnonymousClass1.$SwitchMap$com$afollestad$materialdialogs$GravityEnum[ordinal()];
        if (i == 1) {
            return HAS_RTL ? 8388611 : 3;
        }
        if (i == 2) {
            return 1;
        }
        if (i == 3) {
            if (HAS_RTL) {
                return GravityCompat.END;
            }
            return 5;
        }
        throw new IllegalStateException("Invalid gravity constant");
    }

    public int getTextAlignment() {
        int i = AnonymousClass1.$SwitchMap$com$afollestad$materialdialogs$GravityEnum[ordinal()];
        if (i != 2) {
            return i != 3 ? 5 : 6;
        }
        return 4;
    }
}
