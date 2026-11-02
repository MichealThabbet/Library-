package com.app.piapostolos.ApsalmodyBeans;

import java.util.ArrayList;

/* loaded from: classes.dex */
public class PsalmodyEntity {
    private String arabicTitle;
    private ArrayList<psalomdyItem> content;
    private String copticTitle;
    private boolean isArabic;
    private boolean isArabicCoptic;
    private boolean isCoptic;
    private String title;

    public ArrayList<psalomdyItem> getContent() {
        return this.content;
    }

    public void setContent(ArrayList<psalomdyItem> arrayList) {
        this.content = arrayList;
    }

    public boolean isArabic() {
        return this.isArabic;
    }

    public void setArabic(boolean z) {
        this.isArabic = z;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public String getArabicTitle() {
        return this.arabicTitle;
    }

    public void setArabicTitle(String str) {
        this.arabicTitle = str;
    }

    public String getCopticTitle() {
        return this.copticTitle;
    }

    public void setCopticTitle(String str) {
        this.copticTitle = str;
    }

    public boolean isArabicCoptic() {
        return this.isArabicCoptic;
    }

    public void setArabicCoptic(boolean z) {
        this.isArabicCoptic = z;
    }

    public boolean isCoptic() {
        return this.isCoptic;
    }

    public void setCoptic(boolean z) {
        this.isCoptic = z;
    }
}
