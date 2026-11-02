package com.app.orsozoxi.Alarm.Preferences;

/* loaded from: classes.dex */
public class AlarmPreference {
    private Key key;
    private String[] options;
    private String summary;
    private String title;
    private Type type;
    private Object value;

    public enum Key {
        ALARM_NAME,
        ALARM_ACTIVE,
        ALARM_TIME,
        ALARM_REPEAT,
        ALARM_TONE,
        ALARM_VIBRATE,
        ALARM_DIFFICULTY
    }

    public enum Type {
        BOOLEAN,
        INTEGER,
        STRING,
        LIST,
        MULTIPLE_LIST,
        TIME
    }

    public AlarmPreference(Key key, Object obj, Type type) {
        this(key, null, null, null, obj, type);
    }

    public AlarmPreference(Key key, String str, String str2, String[] strArr, Object obj, Type type) {
        setTitle(str);
        setSummary(str2);
        setOptions(strArr);
        setKey(key);
        setValue(obj);
        setType(type);
    }

    public Key getKey() {
        return this.key;
    }

    public void setKey(Key key) {
        this.key = key;
    }

    public Object getValue() {
        return this.value;
    }

    public void setValue(Object obj) {
        this.value = obj;
    }

    public Type getType() {
        return this.type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String[] getOptions() {
        return this.options;
    }

    public void setOptions(String[] strArr) {
        this.options = strArr;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public String getSummary() {
        return this.summary;
    }

    public void setSummary(String str) {
        this.summary = str;
    }
}
