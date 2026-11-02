package com.app.orsozoxi.Beans;

import java.util.ArrayList;

/* loaded from: classes.dex */
public class Items {
    private String content;
    private ArrayList<item_details> item_details;
    private String title;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String str) {
        this.content = str;
    }

    public ArrayList<item_details> getItem_details() {
        return this.item_details;
    }

    public void setItem_details(ArrayList<item_details> arrayList) {
        this.item_details = arrayList;
    }
}
