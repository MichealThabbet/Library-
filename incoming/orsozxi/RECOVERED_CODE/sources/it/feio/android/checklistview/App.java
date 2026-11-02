package it.feio.android.checklistview;

import android.app.Application;

/* loaded from: classes2.dex */
public class App extends Application {
    private static Settings settings;

    public static Settings getSettings() {
        if (settings == null) {
            settings = new Settings();
        }
        return settings;
    }
}
