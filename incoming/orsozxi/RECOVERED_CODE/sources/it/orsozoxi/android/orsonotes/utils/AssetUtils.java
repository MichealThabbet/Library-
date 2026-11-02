package it.orsozoxi.android.orsonotes.utils;

import android.content.res.AssetManager;
import java.io.IOException;
import java.util.Arrays;

/* loaded from: classes3.dex */
public class AssetUtils {
    private AssetUtils() {
    }

    public static boolean exists(String str, String str2, AssetManager assetManager) throws IOException {
        for (String str3 : assetManager.list(str2)) {
            if (str3.equals(str)) {
                return true;
            }
        }
        return false;
    }

    public static String[] list(String str, AssetManager assetManager) throws IOException {
        String[] list = assetManager.list(str);
        Arrays.sort(list);
        return list;
    }
}
