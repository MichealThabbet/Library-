package it.orsozoxi.android.orsonotes.models;

/* loaded from: classes3.dex */
public class NavigationItem {
    private int arrayIndex;
    private int icon;
    private int iconSelected;
    private String text;

    public NavigationItem(int i, String str, int i2, int i3) {
        this.arrayIndex = i;
        this.text = str;
        this.icon = i2;
        this.iconSelected = i3;
    }

    public int getArrayIndex() {
        return this.arrayIndex;
    }

    public void setArrayIndex(int i) {
        this.arrayIndex = i;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String str) {
        this.text = str;
    }

    public int getIcon() {
        return this.icon;
    }

    public void setIcon(int i) {
        this.icon = i;
    }

    public int getIconSelected() {
        return this.iconSelected;
    }

    public void setIconSelected(int i) {
        this.iconSelected = i;
    }
}
