package javax.mail;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Vector;

/* loaded from: classes3.dex */
public abstract class Multipart {
    protected Part parent;
    protected Vector<BodyPart> parts = new Vector<>();
    protected String contentType = "multipart/mixed";

    public abstract void writeTo(OutputStream outputStream) throws MessagingException, IOException;

    protected Multipart() {
    }

    protected synchronized void setMultipartDataSource(MultipartDataSource multipartDataSource) throws MessagingException {
        this.contentType = multipartDataSource.getContentType();
        int count = multipartDataSource.getCount();
        for (int i = 0; i < count; i++) {
            addBodyPart(multipartDataSource.getBodyPart(i));
        }
    }

    public synchronized String getContentType() {
        return this.contentType;
    }

    public synchronized int getCount() throws MessagingException {
        if (this.parts == null) {
            return 0;
        }
        return this.parts.size();
    }

    public synchronized BodyPart getBodyPart(int i) throws MessagingException {
        if (this.parts == null) {
            throw new IndexOutOfBoundsException("No such BodyPart");
        }
        return this.parts.elementAt(i);
    }

    public synchronized boolean removeBodyPart(BodyPart bodyPart) throws MessagingException {
        boolean zRemoveElement;
        if (this.parts == null) {
            throw new MessagingException("No such body part");
        }
        zRemoveElement = this.parts.removeElement(bodyPart);
        bodyPart.setParent(null);
        return zRemoveElement;
    }

    public synchronized void removeBodyPart(int i) throws MessagingException {
        if (this.parts == null) {
            throw new IndexOutOfBoundsException("No such BodyPart");
        }
        BodyPart bodyPartElementAt = this.parts.elementAt(i);
        this.parts.removeElementAt(i);
        bodyPartElementAt.setParent(null);
    }

    public synchronized void addBodyPart(BodyPart bodyPart) throws MessagingException {
        if (this.parts == null) {
            this.parts = new Vector<>();
        }
        this.parts.addElement(bodyPart);
        bodyPart.setParent(this);
    }

    public synchronized void addBodyPart(BodyPart bodyPart, int i) throws MessagingException {
        if (this.parts == null) {
            this.parts = new Vector<>();
        }
        this.parts.insertElementAt(bodyPart, i);
        bodyPart.setParent(this);
    }

    public synchronized Part getParent() {
        return this.parent;
    }

    public synchronized void setParent(Part part) {
        this.parent = part;
    }
}
