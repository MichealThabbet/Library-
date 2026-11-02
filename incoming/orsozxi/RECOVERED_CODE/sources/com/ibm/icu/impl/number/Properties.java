package com.ibm.icu.impl.number;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/* loaded from: classes2.dex */
public class Properties implements Serializable {
    private static final long serialVersionUID = 4095518955889349243L;
    private transient DecimalFormatProperties instance;

    public DecimalFormatProperties getInstance() {
        return this.instance;
    }

    private void readObject(ObjectInputStream objectInputStream) throws IllegalAccessException, ClassNotFoundException, IOException, IllegalArgumentException {
        if (this.instance == null) {
            this.instance = new DecimalFormatProperties();
        }
        this.instance.readObjectImpl(objectInputStream);
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IllegalAccessException, IOException, IllegalArgumentException {
        if (this.instance == null) {
            this.instance = new DecimalFormatProperties();
        }
        this.instance.writeObjectImpl(objectOutputStream);
    }
}
