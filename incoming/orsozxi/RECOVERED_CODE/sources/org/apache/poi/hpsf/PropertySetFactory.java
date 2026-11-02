package org.apache.poi.hpsf;

import java.io.IOException;
import java.io.InputStream;
import java.rmi.UnexpectedException;
import org.apache.poi.hpsf.wellknown.SectionIDMap;

/* loaded from: classes3.dex */
public class PropertySetFactory {
    /* JADX INFO: Thrown type has an unknown type hierarchy: java.rmi.UnexpectedException */
    public static PropertySet create(InputStream inputStream) throws MarkUnsupportedException, NoPropertySetStreamException, IOException, UnexpectedException {
        PropertySet propertySet = new PropertySet(inputStream);
        try {
            if (propertySet.isSummaryInformation()) {
                return new SummaryInformation(propertySet);
            }
            return propertySet.isDocumentSummaryInformation() ? new DocumentSummaryInformation(propertySet) : propertySet;
        } catch (UnexpectedPropertySetTypeException e) {
            throw new UnexpectedException(e.toString());
        }
    }

    public static SummaryInformation newSummaryInformation() {
        MutablePropertySet mutablePropertySet = new MutablePropertySet();
        ((MutableSection) mutablePropertySet.getFirstSection()).setFormatID(SectionIDMap.SUMMARY_INFORMATION_ID);
        try {
            return new SummaryInformation(mutablePropertySet);
        } catch (UnexpectedPropertySetTypeException e) {
            throw new HPSFRuntimeException(e);
        }
    }

    public static DocumentSummaryInformation newDocumentSummaryInformation() {
        MutablePropertySet mutablePropertySet = new MutablePropertySet();
        ((MutableSection) mutablePropertySet.getFirstSection()).setFormatID(SectionIDMap.DOCUMENT_SUMMARY_INFORMATION_ID[0]);
        try {
            return new DocumentSummaryInformation(mutablePropertySet);
        } catch (UnexpectedPropertySetTypeException e) {
            throw new HPSFRuntimeException(e);
        }
    }
}
