package org.apache.poi;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.hpsf.DocumentSummaryInformation;
import org.apache.poi.hpsf.HPSFException;
import org.apache.poi.hpsf.MutablePropertySet;
import org.apache.poi.hpsf.PropertySet;
import org.apache.poi.hpsf.PropertySetFactory;
import org.apache.poi.hpsf.SummaryInformation;
import org.apache.poi.hpsf.WritingNotSupportedException;
import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.apache.poi.poifs.filesystem.DirectoryNode;
import org.apache.poi.poifs.filesystem.DocumentEntry;
import org.apache.poi.poifs.filesystem.DocumentInputStream;
import org.apache.poi.poifs.filesystem.Entry;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.util.POILogFactory;
import org.apache.poi.util.POILogger;

/* loaded from: classes3.dex */
public abstract class POIDocument {
    private static final POILogger logger = POILogFactory.getLogger(POIDocument.class);
    protected DirectoryNode directory;
    private DocumentSummaryInformation dsInf;
    protected POIFSFileSystem filesystem;
    private boolean initialized;
    private SummaryInformation sInf;

    public abstract void write(OutputStream outputStream) throws IOException;

    protected POIDocument(DirectoryNode directoryNode, POIFSFileSystem pOIFSFileSystem) {
        this.initialized = false;
        this.filesystem = pOIFSFileSystem;
        this.directory = directoryNode;
    }

    protected POIDocument(POIFSFileSystem pOIFSFileSystem) {
        this(pOIFSFileSystem.getRoot(), pOIFSFileSystem);
    }

    public DocumentSummaryInformation getDocumentSummaryInformation() {
        if (!this.initialized) {
            readProperties();
        }
        return this.dsInf;
    }

    public SummaryInformation getSummaryInformation() {
        if (!this.initialized) {
            readProperties();
        }
        return this.sInf;
    }

    public void createInformationProperties() {
        if (!this.initialized) {
            readProperties();
        }
        if (this.sInf == null) {
            this.sInf = PropertySetFactory.newSummaryInformation();
        }
        if (this.dsInf == null) {
            this.dsInf = PropertySetFactory.newDocumentSummaryInformation();
        }
    }

    protected void readProperties() {
        PropertySet propertySet = getPropertySet(DocumentSummaryInformation.DEFAULT_STREAM_NAME);
        if (propertySet != null && (propertySet instanceof DocumentSummaryInformation)) {
            this.dsInf = (DocumentSummaryInformation) propertySet;
        } else if (propertySet != null) {
            logger.log(POILogger.WARN, "DocumentSummaryInformation property set came back with wrong class - ", propertySet.getClass());
        }
        PropertySet propertySet2 = getPropertySet(SummaryInformation.DEFAULT_STREAM_NAME);
        if (propertySet2 instanceof SummaryInformation) {
            this.sInf = (SummaryInformation) propertySet2;
        } else if (propertySet2 != null) {
            logger.log(POILogger.WARN, "SummaryInformation property set came back with wrong class - ", propertySet2.getClass());
        }
        this.initialized = true;
    }

    protected PropertySet getPropertySet(String str) {
        DirectoryNode directoryNode = this.directory;
        if (directoryNode == null) {
            return null;
        }
        try {
            try {
                return PropertySetFactory.create(directoryNode.createDocumentInputStream(str));
            } catch (IOException e) {
                logger.log(POILogger.WARN, "Error creating property set with name " + str + "\n" + e);
                return null;
            } catch (HPSFException e2) {
                logger.log(POILogger.WARN, "Error creating property set with name " + str + "\n" + e2);
                return null;
            }
        } catch (IOException e3) {
            logger.log(POILogger.WARN, "Error getting property set with name " + str + "\n" + e3);
            return null;
        }
    }

    protected void writeProperties(POIFSFileSystem pOIFSFileSystem) throws IOException {
        writeProperties(pOIFSFileSystem, null);
    }

    protected void writeProperties(POIFSFileSystem pOIFSFileSystem, List list) throws IOException {
        SummaryInformation summaryInformation = getSummaryInformation();
        if (summaryInformation != null) {
            writePropertySet(SummaryInformation.DEFAULT_STREAM_NAME, summaryInformation, pOIFSFileSystem);
            if (list != null) {
                list.add(SummaryInformation.DEFAULT_STREAM_NAME);
            }
        }
        DocumentSummaryInformation documentSummaryInformation = getDocumentSummaryInformation();
        if (documentSummaryInformation != null) {
            writePropertySet(DocumentSummaryInformation.DEFAULT_STREAM_NAME, documentSummaryInformation, pOIFSFileSystem);
            if (list != null) {
                list.add(DocumentSummaryInformation.DEFAULT_STREAM_NAME);
            }
        }
    }

    protected void writePropertySet(String str, PropertySet propertySet, POIFSFileSystem pOIFSFileSystem) throws IOException {
        try {
            MutablePropertySet mutablePropertySet = new MutablePropertySet(propertySet);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            mutablePropertySet.write(byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            pOIFSFileSystem.createDocument(new ByteArrayInputStream(byteArray), str);
            logger.log(POILogger.INFO, "Wrote property set " + str + " of size " + byteArray.length);
        } catch (WritingNotSupportedException unused) {
            System.err.println("Couldn't write property set with name " + str + " as not supported by HPSF yet");
        }
    }

    protected void copyNodes(POIFSFileSystem pOIFSFileSystem, POIFSFileSystem pOIFSFileSystem2, List list) throws IOException {
        DirectoryNode root = pOIFSFileSystem.getRoot();
        DirectoryNode root2 = pOIFSFileSystem2.getRoot();
        Iterator<Entry> entries = root.getEntries();
        while (entries.hasNext()) {
            Entry next = entries.next();
            if (!isInList(next.getName(), list)) {
                copyNodeRecursively(next, root2);
            }
        }
    }

    private boolean isInList(String str, List list) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals(str)) {
                return true;
            }
        }
        return false;
    }

    private void copyNodeRecursively(Entry entry, DirectoryEntry directoryEntry) throws IOException {
        if (entry.isDirectoryEntry()) {
            DirectoryEntry directoryEntryCreateDirectory = directoryEntry.createDirectory(entry.getName());
            Iterator<Entry> entries = ((DirectoryEntry) entry).getEntries();
            while (entries.hasNext()) {
                copyNodeRecursively(entries.next(), directoryEntryCreateDirectory);
            }
            return;
        }
        DocumentEntry documentEntry = (DocumentEntry) entry;
        DocumentInputStream documentInputStream = new DocumentInputStream(documentEntry);
        directoryEntry.createDocument(documentEntry.getName(), documentInputStream);
        documentInputStream.close();
    }
}
