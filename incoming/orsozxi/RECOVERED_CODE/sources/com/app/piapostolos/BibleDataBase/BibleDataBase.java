package com.app.piapostolos.BibleDataBase;

/* loaded from: classes.dex */
public class BibleDataBase {
    private String ayah;
    private int ayahIndex;
    private String ayahNormalized;
    private int bookIndex;
    private String bookName;
    private int chapterIndex;
    private String chapterName;

    public int getAyahIndex() {
        return this.ayahIndex;
    }

    public void setAyahIndex(int i) {
        this.ayahIndex = i;
    }

    public int getBookIndex() {
        return this.bookIndex;
    }

    public void setBookIndex(int i) {
        this.bookIndex = i;
    }

    public int getChapterIndex() {
        return this.chapterIndex;
    }

    public void setChapterIndex(int i) {
        this.chapterIndex = i;
    }

    public String getAyahNormalized() {
        return this.ayahNormalized;
    }

    public void setAyahNormalized(String str) {
        this.ayahNormalized = str;
    }

    public String getBookName() {
        return this.bookName;
    }

    public void setBookName(String str) {
        this.bookName = str;
    }

    public String getChapterName() {
        return this.chapterName;
    }

    public void setChapterName(String str) {
        this.chapterName = str;
    }

    public String getAyah() {
        return this.ayah;
    }

    public void setAyah(String str) {
        this.ayah = str;
    }
}
