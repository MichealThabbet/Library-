package it.feio.android.omninotes.commons.models;

/* loaded from: classes2.dex */
public class BaseTag {
    private int count;
    private String text;

    public BaseTag() {
    }

    public BaseTag(String str, int i) {
        this.text = str;
        this.count = i;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int i) {
        this.count = i;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String str) {
        this.text = str;
    }
}
