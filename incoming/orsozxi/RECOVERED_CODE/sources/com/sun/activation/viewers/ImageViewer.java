package com.sun.activation.viewers;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Panel;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import javax.activation.CommandObject;
import javax.activation.DataHandler;

/* loaded from: classes2.dex */
public class ImageViewer extends Panel implements CommandObject {
    private ImageViewerCanvas canvas;
    private Image image = null;
    private DataHandler _dh = null;
    private boolean DEBUG = false;

    public ImageViewer() {
        this.canvas = null;
        ImageViewerCanvas imageViewerCanvas = new ImageViewerCanvas();
        this.canvas = imageViewerCanvas;
        add(imageViewerCanvas);
    }

    @Override // javax.activation.CommandObject
    public void setCommandContext(String str, DataHandler dataHandler) throws IOException {
        this._dh = dataHandler;
        setInputStream(dataHandler.getInputStream());
    }

    private void setInputStream(InputStream inputStream) throws IOException {
        MediaTracker mediaTracker = new MediaTracker(this);
        byte[] bArr = new byte[1024];
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        while (true) {
            int i = inputStream.read(bArr);
            if (i <= 0) {
                break;
            } else {
                byteArrayOutputStream.write(bArr, 0, i);
            }
        }
        inputStream.close();
        Image imageCreateImage = getToolkit().createImage(byteArrayOutputStream.toByteArray());
        this.image = imageCreateImage;
        mediaTracker.addImage(imageCreateImage, 0);
        try {
            mediaTracker.waitForID(0);
            mediaTracker.waitForAll();
            if (mediaTracker.statusID(0, true) != 8) {
                PrintStream printStream = System.out;
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Error occured in image loading = ");
                stringBuffer.append(mediaTracker.getErrorsID(0));
                printStream.println(stringBuffer.toString());
            }
            this.canvas.setImage(this.image);
            if (this.DEBUG) {
                System.out.println("calling invalidate");
            }
        } catch (InterruptedException unused) {
            throw new IOException("Error reading image data");
        }
    }

    public void addNotify() {
        super.addNotify();
        invalidate();
        validate();
        doLayout();
    }

    public Dimension getPreferredSize() {
        return this.canvas.getPreferredSize();
    }
}
