package com.sun.activation.viewers;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.TextArea;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.activation.CommandObject;
import javax.activation.DataHandler;

/* loaded from: classes2.dex */
public class TextViewer extends Panel implements CommandObject {
    private TextArea text_area;
    private File text_file = null;
    private String text_buffer = null;
    private DataHandler _dh = null;
    private boolean DEBUG = false;

    public TextViewer() {
        this.text_area = null;
        setLayout(new GridLayout(1, 1));
        TextArea textArea = new TextArea("", 24, 80, 1);
        this.text_area = textArea;
        textArea.setEditable(false);
        add(this.text_area);
    }

    @Override // javax.activation.CommandObject
    public void setCommandContext(String str, DataHandler dataHandler) throws IOException {
        this._dh = dataHandler;
        setInputStream(dataHandler.getInputStream());
    }

    public void setInputStream(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = new byte[1024];
        while (true) {
            int i = inputStream.read(bArr);
            if (i > 0) {
                byteArrayOutputStream.write(bArr, 0, i);
            } else {
                inputStream.close();
                String string = byteArrayOutputStream.toString();
                this.text_buffer = string;
                this.text_area.setText(string);
                return;
            }
        }
    }

    public void addNotify() {
        super.addNotify();
        invalidate();
    }

    public Dimension getPreferredSize() {
        return this.text_area.getMinimumSize(24, 80);
    }
}
