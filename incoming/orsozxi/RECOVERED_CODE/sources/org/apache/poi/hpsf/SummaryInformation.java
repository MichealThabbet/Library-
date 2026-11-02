package org.apache.poi.hpsf;

import java.util.Date;
import org.apache.poi.hpsf.wellknown.PropertyIDMap;

/* loaded from: classes3.dex */
public final class SummaryInformation extends SpecialPropertySet {
    public static final String DEFAULT_STREAM_NAME = "\u0005SummaryInformation";

    @Override // org.apache.poi.hpsf.SpecialPropertySet
    public PropertyIDMap getPropertySetIDMap() {
        return PropertyIDMap.getSummaryInformationProperties();
    }

    public SummaryInformation(PropertySet propertySet) throws UnexpectedPropertySetTypeException {
        super(propertySet);
        if (isSummaryInformation()) {
            return;
        }
        throw new UnexpectedPropertySetTypeException("Not a " + getClass().getName());
    }

    public String getTitle() {
        return (String) getProperty(2);
    }

    public void setTitle(String str) {
        ((MutableSection) getFirstSection()).setProperty(2, str);
    }

    public void removeTitle() {
        ((MutableSection) getFirstSection()).removeProperty(2L);
    }

    public String getSubject() {
        return (String) getProperty(3);
    }

    public void setSubject(String str) {
        ((MutableSection) getFirstSection()).setProperty(3, str);
    }

    public void removeSubject() {
        ((MutableSection) getFirstSection()).removeProperty(3L);
    }

    public String getAuthor() {
        return (String) getProperty(4);
    }

    public void setAuthor(String str) {
        ((MutableSection) getFirstSection()).setProperty(4, str);
    }

    public void removeAuthor() {
        ((MutableSection) getFirstSection()).removeProperty(4L);
    }

    public String getKeywords() {
        return (String) getProperty(5);
    }

    public void setKeywords(String str) {
        ((MutableSection) getFirstSection()).setProperty(5, str);
    }

    public void removeKeywords() {
        ((MutableSection) getFirstSection()).removeProperty(5L);
    }

    public String getComments() {
        return (String) getProperty(6);
    }

    public void setComments(String str) {
        ((MutableSection) getFirstSection()).setProperty(6, str);
    }

    public void removeComments() {
        ((MutableSection) getFirstSection()).removeProperty(6L);
    }

    public String getTemplate() {
        return (String) getProperty(7);
    }

    public void setTemplate(String str) {
        ((MutableSection) getFirstSection()).setProperty(7, str);
    }

    public void removeTemplate() {
        ((MutableSection) getFirstSection()).removeProperty(7L);
    }

    public String getLastAuthor() {
        return (String) getProperty(8);
    }

    public void setLastAuthor(String str) {
        ((MutableSection) getFirstSection()).setProperty(8, str);
    }

    public void removeLastAuthor() {
        ((MutableSection) getFirstSection()).removeProperty(8L);
    }

    public String getRevNumber() {
        return (String) getProperty(9);
    }

    public void setRevNumber(String str) {
        ((MutableSection) getFirstSection()).setProperty(9, str);
    }

    public void removeRevNumber() {
        ((MutableSection) getFirstSection()).removeProperty(9L);
    }

    public long getEditTime() {
        Date date = (Date) getProperty(10);
        if (date == null) {
            return 0L;
        }
        return Util.dateToFileTime(date);
    }

    public void setEditTime(long j) {
        ((MutableSection) getFirstSection()).setProperty(10, 64L, Util.filetimeToDate(j));
    }

    public void removeEditTime() {
        ((MutableSection) getFirstSection()).removeProperty(10L);
    }

    public Date getLastPrinted() {
        return (Date) getProperty(11);
    }

    public void setLastPrinted(Date date) {
        ((MutableSection) getFirstSection()).setProperty(11, 64L, date);
    }

    public void removeLastPrinted() {
        ((MutableSection) getFirstSection()).removeProperty(11L);
    }

    public Date getCreateDateTime() {
        return (Date) getProperty(12);
    }

    public void setCreateDateTime(Date date) {
        ((MutableSection) getFirstSection()).setProperty(12, 64L, date);
    }

    public void removeCreateDateTime() {
        ((MutableSection) getFirstSection()).removeProperty(12L);
    }

    public Date getLastSaveDateTime() {
        return (Date) getProperty(13);
    }

    public void setLastSaveDateTime(Date date) {
        ((MutableSection) getFirstSection()).setProperty(13, 64L, date);
    }

    public void removeLastSaveDateTime() {
        ((MutableSection) getFirstSection()).removeProperty(13L);
    }

    public int getPageCount() {
        return getPropertyIntValue(14);
    }

    public void setPageCount(int i) {
        ((MutableSection) getFirstSection()).setProperty(14, i);
    }

    public void removePageCount() {
        ((MutableSection) getFirstSection()).removeProperty(14L);
    }

    public int getWordCount() {
        return getPropertyIntValue(15);
    }

    public void setWordCount(int i) {
        ((MutableSection) getFirstSection()).setProperty(15, i);
    }

    public void removeWordCount() {
        ((MutableSection) getFirstSection()).removeProperty(15L);
    }

    public int getCharCount() {
        return getPropertyIntValue(16);
    }

    public void setCharCount(int i) {
        ((MutableSection) getFirstSection()).setProperty(16, i);
    }

    public void removeCharCount() {
        ((MutableSection) getFirstSection()).removeProperty(16L);
    }

    public byte[] getThumbnail() {
        return (byte[]) getProperty(17);
    }

    public void setThumbnail(byte[] bArr) {
        ((MutableSection) getFirstSection()).setProperty(17, 30L, bArr);
    }

    public void removeThumbnail() {
        ((MutableSection) getFirstSection()).removeProperty(17L);
    }

    public String getApplicationName() {
        return (String) getProperty(18);
    }

    public void setApplicationName(String str) {
        ((MutableSection) getFirstSection()).setProperty(18, str);
    }

    public void removeApplicationName() {
        ((MutableSection) getFirstSection()).removeProperty(18L);
    }

    public int getSecurity() {
        return getPropertyIntValue(19);
    }

    public void setSecurity(int i) {
        ((MutableSection) getFirstSection()).setProperty(19, i);
    }

    public void removeSecurity() {
        ((MutableSection) getFirstSection()).removeProperty(19L);
    }
}
