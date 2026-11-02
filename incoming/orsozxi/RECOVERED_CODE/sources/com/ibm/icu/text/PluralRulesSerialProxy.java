package com.ibm.icu.text;

import java.io.ObjectStreamException;
import java.io.Serializable;

/* loaded from: classes2.dex */
class PluralRulesSerialProxy implements Serializable {
    private static final long serialVersionUID = 42;
    private final String data;

    PluralRulesSerialProxy(String str) {
        this.data = str;
    }

    private Object readResolve() throws ObjectStreamException {
        return PluralRules.createRules(this.data);
    }
}
