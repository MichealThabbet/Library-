package com.app.piapostolos.Beans;

import java.util.ArrayList;

/* loaded from: classes.dex */
public class KolagyItems {
    private ArrayList<kolagy_item_details> item_details;
    private String title;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public ArrayList<kolagy_item_details> getItem_details() {
        return this.item_details;
    }

    public void setItem_details(ArrayList<kolagy_item_details> arrayList) {
        this.item_details = arrayList;
    }
}
