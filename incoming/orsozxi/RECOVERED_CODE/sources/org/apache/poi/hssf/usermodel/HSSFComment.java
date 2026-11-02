package org.apache.poi.hssf.usermodel;

import org.apache.poi.hssf.record.NoteRecord;
import org.apache.poi.hssf.record.TextObjectRecord;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.RichTextString;

/* loaded from: classes3.dex */
public class HSSFComment extends HSSFTextbox implements Comment {
    private String _author;
    private int _col;
    private NoteRecord _note;
    private int _row;
    private TextObjectRecord _txo;
    private boolean _visible;

    @Override // org.apache.poi.ss.usermodel.Comment
    public /* bridge */ /* synthetic */ RichTextString getString() {
        return super.getString();
    }

    public HSSFComment(HSSFShape hSSFShape, HSSFAnchor hSSFAnchor) {
        super(hSSFShape, hSSFAnchor);
        setShapeType(25);
        this._fillColor = 134217808;
        this._visible = false;
        this._author = "";
    }

    protected HSSFComment(NoteRecord noteRecord, TextObjectRecord textObjectRecord) {
        this((HSSFShape) null, (HSSFAnchor) null);
        this._txo = textObjectRecord;
        this._note = noteRecord;
    }

    @Override // org.apache.poi.ss.usermodel.Comment
    public void setVisible(boolean z) {
        NoteRecord noteRecord = this._note;
        if (noteRecord != null) {
            noteRecord.setFlags(z ? (short) 2 : (short) 0);
        }
        this._visible = z;
    }

    @Override // org.apache.poi.ss.usermodel.Comment
    public boolean isVisible() {
        return this._visible;
    }

    @Override // org.apache.poi.ss.usermodel.Comment
    public int getRow() {
        return this._row;
    }

    @Override // org.apache.poi.ss.usermodel.Comment
    public void setRow(int i) {
        NoteRecord noteRecord = this._note;
        if (noteRecord != null) {
            noteRecord.setRow(i);
        }
        this._row = i;
    }

    @Override // org.apache.poi.ss.usermodel.Comment
    public int getColumn() {
        return this._col;
    }

    @Override // org.apache.poi.ss.usermodel.Comment
    public void setColumn(int i) {
        NoteRecord noteRecord = this._note;
        if (noteRecord != null) {
            noteRecord.setColumn(i);
        }
        this._col = i;
    }

    @Deprecated
    public void setColumn(short s) {
        setColumn((int) s);
    }

    @Override // org.apache.poi.ss.usermodel.Comment
    public String getAuthor() {
        return this._author;
    }

    @Override // org.apache.poi.ss.usermodel.Comment
    public void setAuthor(String str) {
        NoteRecord noteRecord = this._note;
        if (noteRecord != null) {
            noteRecord.setAuthor(str);
        }
        this._author = str;
    }

    @Override // org.apache.poi.hssf.usermodel.HSSFTextbox, org.apache.poi.ss.usermodel.Comment
    public void setString(RichTextString richTextString) {
        HSSFRichTextString hSSFRichTextString = (HSSFRichTextString) richTextString;
        if (hSSFRichTextString.numFormattingRuns() == 0) {
            hSSFRichTextString.applyFont((short) 0);
        }
        TextObjectRecord textObjectRecord = this._txo;
        if (textObjectRecord != null) {
            textObjectRecord.setStr(hSSFRichTextString);
        }
        super.setString(richTextString);
    }

    protected NoteRecord getNoteRecord() {
        return this._note;
    }

    protected TextObjectRecord getTextObjectRecord() {
        return this._txo;
    }
}
