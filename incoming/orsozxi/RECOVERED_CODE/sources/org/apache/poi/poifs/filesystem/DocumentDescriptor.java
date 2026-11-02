package org.apache.poi.poifs.filesystem;

/* loaded from: classes3.dex */
public class DocumentDescriptor {
    private int hashcode = 0;
    private String name;
    private POIFSDocumentPath path;

    public DocumentDescriptor(POIFSDocumentPath pOIFSDocumentPath, String str) {
        if (pOIFSDocumentPath == null) {
            throw new NullPointerException("path must not be null");
        }
        if (str == null) {
            throw new NullPointerException("name must not be null");
        }
        if (str.length() == 0) {
            throw new IllegalArgumentException("name cannot be empty");
        }
        this.path = pOIFSDocumentPath;
        this.name = str;
    }

    public boolean equals(Object obj) {
        if (obj != null && obj.getClass() == getClass()) {
            if (this == obj) {
                return true;
            }
            DocumentDescriptor documentDescriptor = (DocumentDescriptor) obj;
            if (this.path.equals(documentDescriptor.path) && this.name.equals(documentDescriptor.name)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        if (this.hashcode == 0) {
            this.hashcode = this.path.hashCode() ^ this.name.hashCode();
        }
        return this.hashcode;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer((this.path.length() + 1) * 40);
        for (int i = 0; i < this.path.length(); i++) {
            stringBuffer.append(this.path.getComponent(i));
            stringBuffer.append("/");
        }
        stringBuffer.append(this.name);
        return stringBuffer.toString();
    }
}
