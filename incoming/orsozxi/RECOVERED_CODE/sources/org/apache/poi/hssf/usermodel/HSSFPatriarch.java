package org.apache.poi.hssf.usermodel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.ddf.EscherComplexProperty;
import org.apache.poi.ddf.EscherOptRecord;
import org.apache.poi.ddf.EscherProperty;
import org.apache.poi.hssf.record.EscherAggregate;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.util.StringUtil;

/* loaded from: classes3.dex */
public final class HSSFPatriarch implements HSSFShapeContainer, Drawing {
    private EscherAggregate _boundAggregate;
    final HSSFSheet _sheet;
    private final List<HSSFShape> _shapes = new ArrayList();
    private int _x1 = 0;
    private int _y1 = 0;
    private int _x2 = 1023;
    private int _y2 = 255;

    HSSFPatriarch(HSSFSheet hSSFSheet, EscherAggregate escherAggregate) {
        this._sheet = hSSFSheet;
        this._boundAggregate = escherAggregate;
    }

    public HSSFShapeGroup createGroup(HSSFClientAnchor hSSFClientAnchor) {
        HSSFShapeGroup hSSFShapeGroup = new HSSFShapeGroup(null, hSSFClientAnchor);
        hSSFShapeGroup.anchor = hSSFClientAnchor;
        this._shapes.add(hSSFShapeGroup);
        return hSSFShapeGroup;
    }

    public HSSFSimpleShape createSimpleShape(HSSFClientAnchor hSSFClientAnchor) {
        HSSFSimpleShape hSSFSimpleShape = new HSSFSimpleShape(null, hSSFClientAnchor);
        hSSFSimpleShape.anchor = hSSFClientAnchor;
        this._shapes.add(hSSFSimpleShape);
        return hSSFSimpleShape;
    }

    public HSSFPicture createPicture(HSSFClientAnchor hSSFClientAnchor, int i) {
        HSSFPicture hSSFPicture = new HSSFPicture(null, hSSFClientAnchor);
        hSSFPicture.setPictureIndex(i);
        hSSFPicture.anchor = hSSFClientAnchor;
        hSSFPicture._patriarch = this;
        this._shapes.add(hSSFPicture);
        return hSSFPicture;
    }

    @Override // org.apache.poi.ss.usermodel.Drawing
    public HSSFPicture createPicture(ClientAnchor clientAnchor, int i) {
        return createPicture((HSSFClientAnchor) clientAnchor, i);
    }

    public HSSFPolygon createPolygon(HSSFClientAnchor hSSFClientAnchor) {
        HSSFPolygon hSSFPolygon = new HSSFPolygon(null, hSSFClientAnchor);
        hSSFPolygon.anchor = hSSFClientAnchor;
        this._shapes.add(hSSFPolygon);
        return hSSFPolygon;
    }

    public HSSFTextbox createTextbox(HSSFClientAnchor hSSFClientAnchor) {
        HSSFTextbox hSSFTextbox = new HSSFTextbox(null, hSSFClientAnchor);
        hSSFTextbox.anchor = hSSFClientAnchor;
        this._shapes.add(hSSFTextbox);
        return hSSFTextbox;
    }

    public HSSFComment createComment(HSSFAnchor hSSFAnchor) {
        HSSFComment hSSFComment = new HSSFComment((HSSFShape) null, hSSFAnchor);
        hSSFComment.anchor = hSSFAnchor;
        this._shapes.add(hSSFComment);
        return hSSFComment;
    }

    HSSFSimpleShape createComboBox(HSSFAnchor hSSFAnchor) {
        HSSFSimpleShape hSSFSimpleShape = new HSSFSimpleShape(null, hSSFAnchor);
        hSSFSimpleShape.setShapeType(20);
        hSSFSimpleShape.anchor = hSSFAnchor;
        this._shapes.add(hSSFSimpleShape);
        return hSSFSimpleShape;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.apache.poi.ss.usermodel.Drawing
    public HSSFComment createCellComment(ClientAnchor clientAnchor) {
        return createComment((HSSFAnchor) clientAnchor);
    }

    @Override // org.apache.poi.hssf.usermodel.HSSFShapeContainer
    public List<HSSFShape> getChildren() {
        return this._shapes;
    }

    public int countOfAllChildren() {
        int size = this._shapes.size();
        Iterator<HSSFShape> it2 = this._shapes.iterator();
        while (it2.hasNext()) {
            size += it2.next().countOfAllChildren();
        }
        return size;
    }

    public void setCoordinates(int i, int i2, int i3, int i4) {
        this._x1 = i;
        this._y1 = i2;
        this._x2 = i3;
        this._y2 = i4;
    }

    public boolean containsChart() {
        EscherOptRecord escherOptRecord = (EscherOptRecord) this._boundAggregate.findFirstWithId(EscherOptRecord.RECORD_ID);
        if (escherOptRecord == null) {
            return false;
        }
        for (EscherProperty escherProperty : escherOptRecord.getEscherProperties()) {
            if (escherProperty.getPropertyNumber() == 896 && escherProperty.isComplex() && StringUtil.getFromUnicodeLE(((EscherComplexProperty) escherProperty).getComplexData()).equals("Chart 1\u0000")) {
                return true;
            }
        }
        return false;
    }

    public int getX1() {
        return this._x1;
    }

    public int getY1() {
        return this._y1;
    }

    public int getX2() {
        return this._x2;
    }

    public int getY2() {
        return this._y2;
    }

    protected EscherAggregate _getBoundAggregate() {
        return this._boundAggregate;
    }
}
