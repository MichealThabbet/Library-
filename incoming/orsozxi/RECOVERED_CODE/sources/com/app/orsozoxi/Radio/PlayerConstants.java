package com.app.orsozoxi.Radio;

import android.os.Handler;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class PlayerConstants {
    public static Handler PLAY_PAUSE_HANDLER = null;
    public static Handler PROGRESSBAR_HANDLER = null;
    public static Handler SONG_CHANGE_HANDLER = null;
    public static final String radioUrl = "http://144.208.70.156:8000/live";
    public static final String radioUrlGetInfo = "http://144.208.70.156:8000";
    public static ArrayList<MediaItem> SONGS_LIST = new ArrayList<>();
    public static int SONG_NUMBER = 0;
    public static boolean SONG_PAUSED = true;
    public static boolean SONG_CHANGED = false;
}
