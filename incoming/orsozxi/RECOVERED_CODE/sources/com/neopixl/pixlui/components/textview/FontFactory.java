package com.neopixl.pixlui.components.textview;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import java.util.HashMap;

/* loaded from: classes2.dex */
public class FontFactory {
    private static FontFactory instance;
    private Context context;
    private HashMap<String, Typeface> fontMap = new HashMap<>();

    private FontFactory(Context context) {
        this.context = context;
    }

    public static FontFactory getInstance(Context context) {
        FontFactory fontFactory = instance;
        if (fontFactory != null) {
            return fontFactory;
        }
        FontFactory fontFactory2 = new FontFactory(context);
        instance = fontFactory2;
        return fontFactory2;
    }

    public Typeface getFont(String str) {
        Typeface typeface = this.fontMap.get(str);
        if (typeface != null) {
            return typeface;
        }
        try {
            Typeface typefaceCreateFromAsset = Typeface.createFromAsset(this.context.getResources().getAssets(), "fonts/" + str);
            this.fontMap.put(str, typefaceCreateFromAsset);
            return typefaceCreateFromAsset;
        } catch (Exception e) {
            Log.e("FontFactory", "Could not get typeface: " + e.getMessage() + " with name: " + str);
            return null;
        }
    }
}
