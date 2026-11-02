package com.app.orsozoxi.Others;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;

/* loaded from: classes.dex */
public class SavePrefs<T> {
    private final String DATA = "DATA";
    private String USER_PREFS_FILE_NAME;
    private Class<?> cls;
    private SharedPreferences prefs;

    public SavePrefs(Activity activity, Class<?> cls) {
        this.cls = cls;
        String name = cls.getName();
        this.USER_PREFS_FILE_NAME = name;
        this.prefs = activity.getSharedPreferences(name, 0);
    }

    public SavePrefs(Context context, Class<?> cls) {
        this.cls = cls;
        String name = cls.getName();
        this.USER_PREFS_FILE_NAME = name;
        this.prefs = context.getSharedPreferences(name, 0);
    }

    public void save(T t) {
        SharedPreferences.Editor editorEdit = this.prefs.edit();
        editorEdit.putString("DATA", new Gson().toJson(t));
        editorEdit.commit();
    }

    public T load() {
        return (T) new Gson().fromJson(this.prefs.getString("DATA", ""), (Class) this.cls);
    }

    public void clear() {
        this.prefs.edit().clear().commit();
    }
}
