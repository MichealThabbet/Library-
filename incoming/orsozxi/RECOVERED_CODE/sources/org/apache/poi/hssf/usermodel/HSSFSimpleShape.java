package org.apache.poi.hssf.usermodel;

/* loaded from: classes3.dex */
public class HSSFSimpleShape extends HSSFShape {
    public static final short OBJECT_TYPE_COMBO_BOX = 20;
    public static final short OBJECT_TYPE_COMMENT = 25;
    public static final short OBJECT_TYPE_LINE = 1;
    public static final short OBJECT_TYPE_OVAL = 3;
    public static final short OBJECT_TYPE_PICTURE = 8;
    public static final short OBJECT_TYPE_RECTANGLE = 2;
    int shapeType;

    HSSFSimpleShape(HSSFShape hSSFShape, HSSFAnchor hSSFAnchor) {
        super(hSSFShape, hSSFAnchor);
        this.shapeType = 1;
    }

    public int getShapeType() {
        return this.shapeType;
    }

    public void setShapeType(int i) {
        this.shapeType = i;
    }
}
