package org.apache.poi.hssf.usermodel;

import org.apache.poi.hssf.model.InternalWorkbook;
import org.apache.poi.hssf.record.ExtendedFormatRecord;
import org.apache.poi.hssf.record.FontRecord;
import org.apache.poi.hssf.record.StyleRecord;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;

/* loaded from: classes3.dex */
public final class HSSFCellStyle implements CellStyle {
    private ExtendedFormatRecord _format;
    private short _index;
    private InternalWorkbook _workbook;

    protected HSSFCellStyle(short s, ExtendedFormatRecord extendedFormatRecord, HSSFWorkbook hSSFWorkbook) {
        this(s, extendedFormatRecord, hSSFWorkbook.getWorkbook());
    }

    protected HSSFCellStyle(short s, ExtendedFormatRecord extendedFormatRecord, InternalWorkbook internalWorkbook) {
        this._format = null;
        this._index = (short) 0;
        this._workbook = null;
        this._workbook = internalWorkbook;
        this._index = s;
        this._format = extendedFormatRecord;
    }

    @Override // org.apache.poi.ss.usermodel.CellStyle
    public short getIndex() {
        return this._index;
    }

    public HSSFCellStyle getParentStyle() {
        if (this._format.getParentIndex() == 0) {
            return null;
        }
        return new HSSFCellStyle(this._format.getParentIndex(), this._workbook.getExFormatAt(this._format.getParentIndex()), this._workbook);
    }

    @Override // org.apache.poi.ss.usermodel.CellStyle
    public void setDataFormat(short s) {
        this._format.setFormatIndex(s);
    }

    @Override // org.apache.poi.ss.usermodel.CellStyle
    public short getDataFormat() {
        return this._format.getFormatIndex();
    }

    @Override // org.apache.poi.ss.usermodel.CellStyle
    public String getDataFormatString() {
        return getDataFormatString(this._workbook);
    }

    public String getDataFormatString(Workbook workbook) {
        return getDataFormat() == -1 ? "General" : new HSSFDataFormat(((HSSFWorkbook) workbook).getWorkbook()).getFormat(getDataFormat());
    }

    public String getDataFormatString(InternalWorkbook internalWorkbook) {
        return new HSSFDataFormat(internalWorkbook).getFormat(getDataFormat());
    }

    @Override // org.apache.poi.ss.usermodel.CellStyle
    public void setFont(Font font) {
        setFont((HSSFFont) font);
    }

    public void setFont(HSSFFont hSSFFont) {
        this._format.setIndentNotParentFont(true);
        this._format.setFontIndex(hSSFFont.getIndex());
    }

    @Override // org.apache.poi.ss.usermodel.CellStyle
    public short getFontIndex() {
        return this._format.getFontIndex();
    }

    public HSSFFont getFont(Workbook workbook) {
        return ((HSSFWorkbook) workbook).getFontAt(getFontIndex());
    }

    @Override // org.apache.poi.ss.usermodel.CellStyle
    public void setHidden(boolean z) {
        this._format.setIndentNotParentCellOptions(true);
        this._format.setHidden(z);
    }

    @Override // org.apache.poi.ss.usermodel.CellStyle
    public boolean getHidden() {
        return this._format.isHidden();
    }

    @Override // org.apache.poi.ss.usermodel.CellStyle
    public void setLocked(boolean z) {
        this._format.setIndentNotParentCellOptions(true);
        this._format.setLocked(z);
    }

    @Override // org.apache.poi.ss.usermodel.CellStyle
    public boolean getLocked() {
        return this._format.isLocked();
    }

    @Override // org.apache.poi.ss.usermodel.CellStyle
    public void setAlignment(short s) {
        this._format.setIndentNotParentAlignment(true);
        this._format.setAlignment(s);
    }

    @Override // org.apache.poi.ss.usermodel.CellStyle
    public short getAlignment() {
        return this._format.getAlignment();
    }

    @Override // org.apache.poi.ss.usermodel.CellStyle
    public void setWrapText(boolean z) {
        this._format.setIndentNotParentAlignment(true);
        this._format.setWrapText(z);
    }

    @Override // org.apache.poi.ss.usermodel.CellStyle
    public boolean getWrapText() {
        return this._format.getWrapText();
    }

    @Override // org.apache.poi.ss.usermodel.CellStyle
    public void setVerticalAlignment(short s) {
        this._format.setVerticalAlignment(s);
    }

    @Override // org.apache.poi.ss.usermodel.CellStyle
    public short getVerticalAlignment() {
        return this._format.getVerticalAlignment();
    }

    @Override // org.apache.poi.ss.usermodel.CellStyle
    public void setRotation(short s) {
        if (s != 255) {
            if (s < 0 && s >= -90) {
                s = (short) (90 - s);
            } else if (s < -90 || s > 90) {
                throw new IllegalArgumentException("The rotation must be between -90 and 90 degrees, or 0xff");
            }
        }
        this._format.setRotation(s);
    }

    @Override // org.apache.poi.ss.usermodel.CellStyle
    public short getRotation() {
        short rotation = this._format.getRotation();
        return (rotation != 255 && rotation > 90) ? (short) (90 - rotation) : rotation;
    }

    @Override // org.apache.poi.ss.usermodel.CellStyle
    public void setIndention(short s) {
        this._format.setIndent(s);
    }

    @Override // org.apache.poi.ss.usermodel.CellStyle
    public short getIndention() {
        return this._format.getIndent();
    }

    @Override // org.apache.poi.ss.usermodel.CellStyle
    public void setBorderLeft(short s) {
        this._format.setIndentNotParentBorder(true);
        this._format.setBorderLeft(s);
    }

    @Override // org.apache.poi.ss.usermodel.CellStyle
    public short getBorderLeft() {
        return this._format.getBorderLeft();
    }

    @Override // org.apache.poi.ss.usermodel.CellStyle
    public void setBorderRight(short s) {
        this._format.setIndentNotParentBorder(true);
        this._format.setBorderRight(s);
    }

    @Override // org.apache.poi.ss.usermodel.CellStyle
    public short getBorderRight() {
        return this._format.getBorderRight();
    }

    @Override // org.apache.poi.ss.usermodel.CellStyle
    public void setBorderTop(short s) {
        this._format.setIndentNotParentBorder(true);
        this._format.setBorderTop(s);
    }

    @Override // org.apache.poi.ss.usermodel.CellStyle
    public short getBorderTop() {
        return this._format.getBorderTop();
    }

    @Override // org.apache.poi.ss.usermodel.CellStyle
    public void setBorderBottom(short s) {
        this._format.setIndentNotParentBorder(true);
        this._format.setBorderBottom(s);
    }

    @Override // org.apache.poi.ss.usermodel.CellStyle
    public short getBorderBottom() {
        return this._format.getBorderBottom();
    }

    @Override // org.apache.poi.ss.usermodel.CellStyle
    public void setLeftBorderColor(short s) {
        this._format.setLeftBorderPaletteIdx(s);
    }

    @Override // org.apache.poi.ss.usermodel.CellStyle
    public short getLeftBorderColor() {
        return this._format.getLeftBorderPaletteIdx();
    }

    @Override // org.apache.poi.ss.usermodel.CellStyle
    public void setRightBorderColor(short s) {
        this._format.setRightBorderPaletteIdx(s);
    }

    @Override // org.apache.poi.ss.usermodel.CellStyle
    public short getRightBorderColor() {
        return this._format.getRightBorderPaletteIdx();
    }

    @Override // org.apache.poi.ss.usermodel.CellStyle
    public void setTopBorderColor(short s) {
        this._format.setTopBorderPaletteIdx(s);
    }

    @Override // org.apache.poi.ss.usermodel.CellStyle
    public short getTopBorderColor() {
        return this._format.getTopBorderPaletteIdx();
    }

    @Override // org.apache.poi.ss.usermodel.CellStyle
    public void setBottomBorderColor(short s) {
        this._format.setBottomBorderPaletteIdx(s);
    }

    @Override // org.apache.poi.ss.usermodel.CellStyle
    public short getBottomBorderColor() {
        return this._format.getBottomBorderPaletteIdx();
    }

    @Override // org.apache.poi.ss.usermodel.CellStyle
    public void setFillPattern(short s) {
        this._format.setAdtlFillPattern(s);
    }

    @Override // org.apache.poi.ss.usermodel.CellStyle
    public short getFillPattern() {
        return this._format.getAdtlFillPattern();
    }

    private void checkDefaultBackgroundFills() {
        if (this._format.getFillForeground() == 64) {
            if (this._format.getFillBackground() != 65) {
                setFillBackgroundColor((short) 65);
            }
        } else {
            if (this._format.getFillBackground() != 65 || this._format.getFillForeground() == 64) {
                return;
            }
            setFillBackgroundColor((short) 64);
        }
    }

    @Override // org.apache.poi.ss.usermodel.CellStyle
    public void setFillBackgroundColor(short s) {
        this._format.setFillBackground(s);
        checkDefaultBackgroundFills();
    }

    @Override // org.apache.poi.ss.usermodel.CellStyle
    public short getFillBackgroundColor() {
        short fillBackground = this._format.getFillBackground();
        if (fillBackground == 65) {
            return (short) 64;
        }
        return fillBackground;
    }

    @Override // org.apache.poi.ss.usermodel.CellStyle
    public HSSFColor getFillBackgroundColorColor() {
        return new HSSFPalette(this._workbook.getCustomPalette()).getColor(getFillBackgroundColor());
    }

    @Override // org.apache.poi.ss.usermodel.CellStyle
    public void setFillForegroundColor(short s) {
        this._format.setFillForeground(s);
        checkDefaultBackgroundFills();
    }

    @Override // org.apache.poi.ss.usermodel.CellStyle
    public short getFillForegroundColor() {
        return this._format.getFillForeground();
    }

    @Override // org.apache.poi.ss.usermodel.CellStyle
    public HSSFColor getFillForegroundColorColor() {
        return new HSSFPalette(this._workbook.getCustomPalette()).getColor(getFillForegroundColor());
    }

    public String getUserStyleName() {
        StyleRecord styleRecord = this._workbook.getStyleRecord(this._index);
        if (styleRecord == null || styleRecord.isBuiltin()) {
            return null;
        }
        return styleRecord.getName();
    }

    public void setUserStyleName(String str) {
        StyleRecord styleRecord = this._workbook.getStyleRecord(this._index);
        if (styleRecord == null) {
            styleRecord = this._workbook.createStyleRecord(this._index);
        }
        if (styleRecord.isBuiltin() && this._index <= 20) {
            throw new IllegalArgumentException("Unable to set user specified style names for built in styles!");
        }
        styleRecord.setName(str);
    }

    public void verifyBelongsToWorkbook(HSSFWorkbook hSSFWorkbook) {
        if (hSSFWorkbook.getWorkbook() != this._workbook) {
            throw new IllegalArgumentException("This Style does not belong to the supplied Workbook. Are you trying to assign a style from one workbook to the cell of a differnt workbook?");
        }
    }

    @Override // org.apache.poi.ss.usermodel.CellStyle
    public void cloneStyleFrom(CellStyle cellStyle) {
        if (cellStyle instanceof HSSFCellStyle) {
            cloneStyleFrom((HSSFCellStyle) cellStyle);
            return;
        }
        throw new IllegalArgumentException("Can only clone from one HSSFCellStyle to another, not between HSSFCellStyle and XSSFCellStyle");
    }

    public void cloneStyleFrom(HSSFCellStyle hSSFCellStyle) {
        this._format.cloneStyleFrom(hSSFCellStyle._format);
        InternalWorkbook internalWorkbook = this._workbook;
        if (internalWorkbook != hSSFCellStyle._workbook) {
            setDataFormat((short) internalWorkbook.createFormat(hSSFCellStyle.getDataFormatString()));
            FontRecord fontRecordCreateNewFont = this._workbook.createNewFont();
            fontRecordCreateNewFont.cloneStyleFrom(hSSFCellStyle._workbook.getFontRecordAt(hSSFCellStyle.getFontIndex()));
            setFont(new HSSFFont((short) this._workbook.getFontIndex(fontRecordCreateNewFont), fontRecordCreateNewFont));
        }
    }

    public int hashCode() {
        ExtendedFormatRecord extendedFormatRecord = this._format;
        return (((extendedFormatRecord == null ? 0 : extendedFormatRecord.hashCode()) + 31) * 31) + this._index;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof HSSFCellStyle)) {
            return false;
        }
        HSSFCellStyle hSSFCellStyle = (HSSFCellStyle) obj;
        ExtendedFormatRecord extendedFormatRecord = this._format;
        if (extendedFormatRecord == null) {
            if (hSSFCellStyle._format != null) {
                return false;
            }
        } else if (!extendedFormatRecord.equals(hSSFCellStyle._format)) {
            return false;
        }
        return this._index == hSSFCellStyle._index;
    }
}
