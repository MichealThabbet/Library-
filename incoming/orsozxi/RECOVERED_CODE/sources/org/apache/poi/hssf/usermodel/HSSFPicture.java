package org.apache.poi.hssf.usermodel;

import java.awt.Dimension;
import java.io.ByteArrayInputStream;
import org.apache.poi.ddf.EscherBSERecord;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.util.ImageUtils;

/* loaded from: classes3.dex */
public final class HSSFPicture extends HSSFSimpleShape implements Picture {
    public static final int PICTURE_TYPE_DIB = 7;
    public static final int PICTURE_TYPE_EMF = 2;
    public static final int PICTURE_TYPE_JPEG = 5;
    public static final int PICTURE_TYPE_PICT = 4;
    public static final int PICTURE_TYPE_PNG = 6;
    public static final int PICTURE_TYPE_WMF = 3;
    private static final float PX_DEFAULT = 32.0f;
    private static final float PX_MODIFIED = 36.56f;
    private static final int PX_ROW = 15;
    HSSFPatriarch _patriarch;
    private int _pictureIndex;

    public HSSFPicture(HSSFShape hSSFShape, HSSFAnchor hSSFAnchor) {
        super(hSSFShape, hSSFAnchor);
        setShapeType(8);
    }

    public int getPictureIndex() {
        return this._pictureIndex;
    }

    public void setPictureIndex(int i) {
        this._pictureIndex = i;
    }

    @Override // org.apache.poi.ss.usermodel.Picture
    public void resize(double d) {
        HSSFClientAnchor hSSFClientAnchor = (HSSFClientAnchor) getAnchor();
        hSSFClientAnchor.setAnchorType(2);
        HSSFClientAnchor preferredSize = getPreferredSize(d);
        int row1 = hSSFClientAnchor.getRow1() + (preferredSize.getRow2() - preferredSize.getRow1());
        hSSFClientAnchor.setCol2((short) (hSSFClientAnchor.getCol1() + (preferredSize.getCol2() - preferredSize.getCol1())));
        hSSFClientAnchor.setDx1(0);
        hSSFClientAnchor.setDx2(preferredSize.getDx2());
        hSSFClientAnchor.setRow2(row1);
        hSSFClientAnchor.setDy1(0);
        hSSFClientAnchor.setDy2(preferredSize.getDy2());
    }

    @Override // org.apache.poi.ss.usermodel.Picture
    public void resize() {
        resize(1.0d);
    }

    @Override // org.apache.poi.ss.usermodel.Picture
    public HSSFClientAnchor getPreferredSize() {
        return getPreferredSize(1.0d);
    }

    public HSSFClientAnchor getPreferredSize(double d) {
        double d2;
        int i;
        double d3;
        HSSFClientAnchor hSSFClientAnchor = (HSSFClientAnchor) getAnchor();
        Dimension imageDimension = getImageDimension();
        double width = imageDimension.getWidth() * d;
        double height = imageDimension.getHeight() * d;
        float columnWidthInPixels = (getColumnWidthInPixels(hSSFClientAnchor.col1) * (1.0f - (hSSFClientAnchor.dx1 / 1024.0f))) + 0.0f;
        short s = (short) (hSSFClientAnchor.col1 + 1);
        while (true) {
            d2 = columnWidthInPixels;
            if (d2 >= width) {
                break;
            }
            columnWidthInPixels += getColumnWidthInPixels(s);
            s = (short) (s + 1);
        }
        int i2 = 0;
        if (d2 > width) {
            s = (short) (s - 1);
            double columnWidthInPixels2 = getColumnWidthInPixels(s);
            i = (int) (((columnWidthInPixels2 - (d2 - width)) / columnWidthInPixels2) * 1024.0d);
        } else {
            i = 0;
        }
        hSSFClientAnchor.col2 = s;
        hSSFClientAnchor.dx2 = i;
        float rowHeightInPixels = ((1.0f - (hSSFClientAnchor.dy1 / 256.0f)) * getRowHeightInPixels(hSSFClientAnchor.row1)) + 0.0f;
        int i3 = hSSFClientAnchor.row1 + 1;
        while (true) {
            d3 = rowHeightInPixels;
            if (d3 >= height) {
                break;
            }
            rowHeightInPixels += getRowHeightInPixels(i3);
            i3++;
        }
        if (d3 > height) {
            i3--;
            double rowHeightInPixels2 = getRowHeightInPixels(i3);
            i2 = (int) (((rowHeightInPixels2 - (d3 - height)) / rowHeightInPixels2) * 256.0d);
        }
        hSSFClientAnchor.row2 = i3;
        hSSFClientAnchor.dy2 = i2;
        return hSSFClientAnchor;
    }

    private float getColumnWidthInPixels(int i) {
        return this._patriarch._sheet.getColumnWidth(i) / getPixelWidth(i);
    }

    private float getRowHeightInPixels(int i) {
        short defaultRowHeight;
        HSSFRow row = this._patriarch._sheet.getRow(i);
        if (row != null) {
            defaultRowHeight = row.getHeight();
        } else {
            defaultRowHeight = this._patriarch._sheet.getDefaultRowHeight();
        }
        return defaultRowHeight / 15.0f;
    }

    private float getPixelWidth(int i) {
        return this._patriarch._sheet.getColumnWidth(i) == this._patriarch._sheet.getDefaultColumnWidth() * 256 ? PX_DEFAULT : PX_MODIFIED;
    }

    public Dimension getImageDimension() {
        EscherBSERecord bSERecord = this._patriarch._sheet._book.getBSERecord(this._pictureIndex);
        byte[] picturedata = bSERecord.getBlipRecord().getPicturedata();
        return ImageUtils.getImageDimension(new ByteArrayInputStream(picturedata), bSERecord.getBlipTypeWin32());
    }
}
