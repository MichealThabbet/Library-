package com.sun.mail.handlers;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.activation.ActivationDataFlavor;
import javax.activation.DataSource;

/* loaded from: classes2.dex */
public class image_gif extends handler_base {
    private static ActivationDataFlavor[] myDF = {new ActivationDataFlavor(Image.class, "image/gif", "GIF Image")};

    @Override // com.sun.mail.handlers.handler_base
    protected ActivationDataFlavor[] getDataFlavors() {
        return myDF;
    }

    @Override // javax.activation.DataContentHandler
    public Object getContent(DataSource dataSource) throws IOException {
        InputStream inputStream = dataSource.getInputStream();
        byte[] bArr = new byte[1024];
        int i = 0;
        while (true) {
            int i2 = inputStream.read(bArr, i, bArr.length - i);
            if (i2 != -1) {
                i += i2;
                if (i >= bArr.length) {
                    int length = bArr.length;
                    byte[] bArr2 = new byte[length < 262144 ? length + length : length + 262144];
                    System.arraycopy(bArr, 0, bArr2, 0, i);
                    bArr = bArr2;
                }
            } else {
                return Toolkit.getDefaultToolkit().createImage(bArr, 0, i);
            }
        }
    }

    @Override // javax.activation.DataContentHandler
    public void writeTo(Object obj, String str, OutputStream outputStream) throws IOException {
        if (!(obj instanceof Image)) {
            throw new IOException("\"" + getDataFlavors()[0].getMimeType() + "\" DataContentHandler requires Image object, was given object of type " + obj.getClass().toString());
        }
        throw new IOException(getDataFlavors()[0].getMimeType() + " encoding not supported");
    }
}
