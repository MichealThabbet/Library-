package com.app.orsozoxi.Others;

import android.content.Context;
import com.app.orsozoxi.Beans.FontPrefs;

/* loaded from: classes.dex */
public class checksim {
    public String checkfontsize(Context context) {
        FontPrefs fontPrefs = (FontPrefs) new SavePrefs(context, (Class<?>) FontPrefs.class).load();
        if (fontPrefs != null) {
            return fontPrefs.getFontSize() + "";
        }
        FontPrefs fontPrefs2 = new FontPrefs();
        fontPrefs2.setFontSize(20);
        new SavePrefs(context, (Class<?>) FontPrefs.class).save(fontPrefs2);
        return "20";
    }

    public static boolean isNumeric(String str) throws NumberFormatException {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException unused) {
            return false;
        }
    }
}
