package com.huawei.secure.android.common.xml;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/* loaded from: classes2.dex */
public class DocumentBuilderFactorySecurity {
    public static DocumentBuilderFactory getInstance() throws ParserConfigurationException, NullPointerException {
        DocumentBuilderFactory documentBuilderFactoryNewInstance = DocumentBuilderFactory.newInstance();
        documentBuilderFactoryNewInstance.setFeature("http://xml.org/sax/features/namespaces", true);
        documentBuilderFactoryNewInstance.setFeature("http://xml.org/sax/features/validation", false);
        documentBuilderFactoryNewInstance.setExpandEntityReferences(false);
        return documentBuilderFactoryNewInstance;
    }
}
