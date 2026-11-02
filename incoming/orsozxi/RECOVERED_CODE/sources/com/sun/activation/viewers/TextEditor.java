package com.sun.activation.viewers;

import java.awt.Button;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import javax.activation.CommandObject;
import javax.activation.DataHandler;

/* loaded from: classes2.dex */
public class TextEditor extends Panel implements CommandObject, ActionListener {
    private Panel button_panel;
    private GridBagLayout panel_gb;
    private Button save_button;
    private TextArea text_area;
    private File text_file = null;
    private String text_buffer = null;
    private InputStream data_ins = null;
    private FileInputStream fis = null;
    private DataHandler _dh = null;
    private boolean DEBUG = false;

    public TextEditor() {
        this.text_area = null;
        this.panel_gb = null;
        this.button_panel = null;
        this.save_button = null;
        GridBagLayout gridBagLayout = new GridBagLayout();
        this.panel_gb = gridBagLayout;
        setLayout(gridBagLayout);
        Panel panel = new Panel();
        this.button_panel = panel;
        panel.setLayout(new FlowLayout());
        Button button = new Button("SAVE");
        this.save_button = button;
        this.button_panel.add(button);
        addGridComponent(this, this.button_panel, this.panel_gb, 0, 0, 1, 1, 1, 0);
        TextArea textArea = new TextArea("This is text", 24, 80, 1);
        this.text_area = textArea;
        textArea.setEditable(true);
        addGridComponent(this, this.text_area, this.panel_gb, 0, 1, 1, 2, 1, 1);
        this.save_button.addActionListener(this);
    }

    private void addGridComponent(Container container, Component component, GridBagLayout gridBagLayout, int i, int i2, int i3, int i4, int i5, int i6) {
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = i;
        gridBagConstraints.gridy = i2;
        gridBagConstraints.gridwidth = i3;
        gridBagConstraints.gridheight = i4;
        gridBagConstraints.fill = 1;
        gridBagConstraints.weighty = i6;
        gridBagConstraints.weightx = i5;
        gridBagConstraints.anchor = 10;
        gridBagLayout.setConstraints(component, gridBagConstraints);
        container.add(component);
    }

    @Override // javax.activation.CommandObject
    public void setCommandContext(String str, DataHandler dataHandler) throws IOException {
        this._dh = dataHandler;
        setInputStream(dataHandler.getInputStream());
    }

    public void setInputStream(InputStream inputStream) throws IOException {
        byte[] bArr = new byte[1024];
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
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

    private void performSaveOperation() throws IOException {
        OutputStream outputStream;
        try {
            outputStream = this._dh.getOutputStream();
        } catch (Exception unused) {
            outputStream = null;
        }
        String text = this.text_area.getText();
        if (outputStream == null) {
            System.out.println("Invalid outputstream in TextEditor!");
            System.out.println("not saving!");
        }
        try {
            outputStream.write(text.getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            PrintStream printStream = System.out;
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("TextEditor Save Operation failed with: ");
            stringBuffer.append(e);
            printStream.println(stringBuffer.toString());
        }
    }

    public void addNotify() {
        super.addNotify();
        invalidate();
    }

    public Dimension getPreferredSize() {
        return this.text_area.getMinimumSize(24, 80);
    }

    public void actionPerformed(ActionEvent actionEvent) throws IOException {
        if (actionEvent.getSource() == this.save_button) {
            performSaveOperation();
        }
    }
}
