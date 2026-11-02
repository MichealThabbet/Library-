package org.apache.poi.hpsf.extractor;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.apache.poi.POIDocument;
import org.apache.poi.POITextExtractor;
import org.apache.poi.hpsf.CustomProperties;
import org.apache.poi.hpsf.DocumentSummaryInformation;
import org.apache.poi.hpsf.NoSingleSectionException;
import org.apache.poi.hpsf.Property;
import org.apache.poi.hpsf.SpecialPropertySet;
import org.apache.poi.hpsf.wellknown.PropertyIDMap;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.util.LittleEndian;

/* loaded from: classes3.dex */
public class HPSFPropertiesExtractor extends POITextExtractor {
    public HPSFPropertiesExtractor(POITextExtractor pOITextExtractor) {
        super(pOITextExtractor);
    }

    public HPSFPropertiesExtractor(POIDocument pOIDocument) {
        super(pOIDocument);
    }

    public HPSFPropertiesExtractor(POIFSFileSystem pOIFSFileSystem) {
        super(new PropertiesOnlyDocument(pOIFSFileSystem));
    }

    public String getDocumentSummaryInformationText() {
        DocumentSummaryInformation documentSummaryInformation = this.document.getDocumentSummaryInformation();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(getPropertiesText(documentSummaryInformation));
        CustomProperties customProperties = documentSummaryInformation == null ? null : documentSummaryInformation.getCustomProperties();
        if (customProperties != null) {
            for (String str : customProperties.nameSet()) {
                stringBuffer.append(str + " = " + getPropertyValueText(customProperties.get(str)) + "\n");
            }
        }
        return stringBuffer.toString();
    }

    public String getSummaryInformationText() {
        return getPropertiesText(this.document.getSummaryInformation());
    }

    private static String getPropertiesText(SpecialPropertySet specialPropertySet) throws NoSingleSectionException {
        if (specialPropertySet == null) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer();
        PropertyIDMap propertySetIDMap = specialPropertySet.getPropertySetIDMap();
        Property[] properties = specialPropertySet.getProperties();
        for (int i = 0; i < properties.length; i++) {
            String string = Long.toString(properties[i].getID());
            Object obj = propertySetIDMap.get(properties[i].getID());
            if (obj != null) {
                string = obj.toString();
            }
            stringBuffer.append(string + " = " + getPropertyValueText(properties[i].getValue()) + "\n");
        }
        return stringBuffer.toString();
    }

    private static String getPropertyValueText(Object obj) {
        if (obj == null) {
            return "(not set)";
        }
        if (obj instanceof byte[]) {
            byte[] bArr = (byte[]) obj;
            if (bArr.length == 0) {
                return "";
            }
            if (bArr.length == 1) {
                return Byte.toString(bArr[0]);
            }
            if (bArr.length == 2) {
                return Integer.toString(LittleEndian.getUShort(bArr));
            }
            if (bArr.length == 4) {
                return Long.toString(LittleEndian.getUInt(bArr));
            }
            return new String(bArr);
        }
        return obj.toString();
    }

    @Override // org.apache.poi.POITextExtractor
    public String getText() {
        return getSummaryInformationText() + getDocumentSummaryInformationText();
    }

    @Override // org.apache.poi.POITextExtractor
    public POITextExtractor getMetadataTextExtractor() {
        throw new IllegalStateException("You already have the Metadata Text Extractor, not recursing!");
    }

    private static final class PropertiesOnlyDocument extends POIDocument {
        public PropertiesOnlyDocument(POIFSFileSystem pOIFSFileSystem) {
            super(pOIFSFileSystem);
        }

        @Override // org.apache.poi.POIDocument
        public void write(OutputStream outputStream) {
            throw new IllegalStateException("Unable to write, only for properties!");
        }
    }

    public static void main(String[] strArr) throws IOException {
        for (String str : strArr) {
            System.out.println(new HPSFPropertiesExtractor(new POIFSFileSystem(new FileInputStream(str))).getText());
        }
    }
}
