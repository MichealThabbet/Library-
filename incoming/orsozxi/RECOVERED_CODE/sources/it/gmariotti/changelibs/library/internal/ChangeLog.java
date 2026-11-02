package it.gmariotti.changelibs.library.internal;

import java.util.Iterator;
import java.util.LinkedList;

/* loaded from: classes2.dex */
public class ChangeLog {
    private boolean bulletedList;
    private LinkedList<ChangeLogRow> rows = new LinkedList<>();

    public void addRow(ChangeLogRow changeLogRow) {
        if (changeLogRow != null) {
            if (this.rows == null) {
                this.rows = new LinkedList<>();
            }
            this.rows.add(changeLogRow);
        }
    }

    public void clearAllRows() {
        this.rows = new LinkedList<>();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("bulletedList=" + this.bulletedList);
        sb.append("\n");
        LinkedList<ChangeLogRow> linkedList = this.rows;
        if (linkedList != null) {
            Iterator<ChangeLogRow> it2 = linkedList.iterator();
            while (it2.hasNext()) {
                ChangeLogRow next = it2.next();
                sb.append("row=[");
                sb.append(next.toString());
                sb.append("]\n");
            }
        } else {
            sb.append("rows:none");
        }
        return sb.toString();
    }

    public boolean isBulletedList() {
        return this.bulletedList;
    }

    public void setBulletedList(boolean z) {
        this.bulletedList = z;
    }

    public LinkedList<ChangeLogRow> getRows() {
        return this.rows;
    }

    public void setRows(LinkedList<ChangeLogRow> linkedList) {
        this.rows = linkedList;
    }
}
