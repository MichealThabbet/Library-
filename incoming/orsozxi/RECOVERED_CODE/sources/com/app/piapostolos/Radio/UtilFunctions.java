package com.app.piapostolos.Radio;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;
import com.app.piapostolos.R;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes.dex */
public class UtilFunctions {
    static String LOG_CLASS = "UtilFunctions";

    public static boolean isServiceRunning(String str, Context context) {
        Iterator<ActivityManager.RunningServiceInfo> it2 = ((ActivityManager) context.getSystemService("activity")).getRunningServices(Integer.MAX_VALUE).iterator();
        while (it2.hasNext()) {
            if (str.equals(it2.next().service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public static ArrayList<MediaItem> listOfSongs(Context context, int i) {
        ArrayList<MediaItem> arrayList = new ArrayList<>();
        MediaItem mediaItem = new MediaItem();
        mediaItem.setTitle("نستمع الأن إلى");
        mediaItem.setAlbum("راديو أرثوذكسى");
        mediaItem.setArtist("");
        mediaItem.setDuration(0L);
        mediaItem.setPath(PlayerConstants.radioUrl);
        mediaItem.setAlbumId(0L);
        mediaItem.setComposer("");
        arrayList.add(mediaItem);
        Log.d("SIZE", "SIZE: " + arrayList.size());
        return arrayList;
    }

    public static Bitmap getDefaultAlbumArt(Context context) {
        try {
            return BitmapFactory.decodeResource(context.getResources(), R.drawable.radioooo, new BitmapFactory.Options());
        } catch (Error | Exception unused) {
            return null;
        }
    }

    public static String getDuration(long j) {
        StringBuilder sb;
        StringBuilder sb2;
        long j2 = (j / 1000) % 60;
        long j3 = (j / 60000) % 60;
        long j4 = j / 3600000;
        if (j2 < 10) {
            sb = new StringBuilder();
            sb.append("0");
        } else {
            sb = new StringBuilder();
            sb.append("");
        }
        sb.append(j2);
        String string = sb.toString();
        if (j3 < 10) {
            sb2 = new StringBuilder();
            sb2.append("0");
        } else {
            sb2 = new StringBuilder();
            sb2.append("");
        }
        sb2.append(j3);
        String string2 = sb2.toString();
        String str = "" + j4;
        if (j4 > 0) {
            return str + ":" + string2 + ":" + string;
        }
        return string2 + ":" + string;
    }

    public static boolean currentVersionSupportBigNotification() {
        return Build.VERSION.SDK_INT >= 16;
    }

    public static boolean currentVersionSupportLockScreenControls() {
        return Build.VERSION.SDK_INT >= 14;
    }
}
