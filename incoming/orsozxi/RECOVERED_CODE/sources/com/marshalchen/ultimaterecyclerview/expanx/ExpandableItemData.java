package com.marshalchen.ultimaterecyclerview.expanx;

import com.marshalchen.ultimaterecyclerview.expanx.ExpandableItemData;
import java.util.List;

/* loaded from: classes2.dex */
public class ExpandableItemData<T extends ExpandableItemData> implements Comparable<T> {
    private List<T> children;
    private boolean expand;
    private String path;
    private String text;
    private int treeDepth;
    private int type;
    private String uuid;

    public boolean isExpand() {
        return this.expand;
    }

    public void setExpand(boolean z) {
        this.expand = z;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int i) {
        this.type = i;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String str) {
        this.text = str;
    }

    public List<T> getChildren() {
        return this.children;
    }

    public void setChildren(List<T> list) {
        this.children = list;
    }

    public ExpandableItemData(int i, String str, String str2, String str3, int i2, List<T> list) {
        this.treeDepth = 0;
        this.type = i;
        this.text = str;
        this.uuid = str3;
        this.path = str2;
        this.treeDepth = i2;
        this.children = list;
    }

    public ExpandableItemData() {
        this.treeDepth = 0;
    }

    public String getUuid() {
        return this.uuid;
    }

    public void setUuid(String str) {
        this.uuid = str;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String str) {
        this.path = str;
    }

    public int getTreeDepth() {
        return this.treeDepth;
    }

    public void setTreeDepth(int i) {
        this.treeDepth = i;
    }

    @Override // java.lang.Comparable
    public int compareTo(ExpandableItemData expandableItemData) {
        return getText().compareTo(expandableItemData.getText());
    }
}
