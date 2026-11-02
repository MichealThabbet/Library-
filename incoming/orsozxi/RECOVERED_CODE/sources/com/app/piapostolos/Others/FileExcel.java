package com.app.piapostolos.Others;

import java.util.ArrayList;

/* loaded from: classes.dex */
public class FileExcel {
    private ArrayList<String> cell;
    private ArrayList<Integer> cellPos;
    private int row;

    public ArrayList<Integer> getCellPos() {
        return this.cellPos;
    }

    public void setCellPos(ArrayList<Integer> arrayList) {
        this.cellPos = arrayList;
    }

    public int getRow() {
        return this.row;
    }

    public void setRow(int i) {
        this.row = i;
    }

    public ArrayList<String> getCell() {
        return this.cell;
    }

    public void setCell(ArrayList<String> arrayList) {
        this.cell = arrayList;
    }
}
