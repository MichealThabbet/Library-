package org.apache.poi.poifs.dev;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes3.dex */
public class POIFSViewEngine {
    private static final String _EOL = System.getProperty("line.separator");

    public static List inspectViewable(Object obj, boolean z, int i, String str) {
        ArrayList arrayList = new ArrayList();
        if (obj instanceof POIFSViewable) {
            POIFSViewable pOIFSViewable = (POIFSViewable) obj;
            arrayList.add(indent(i, str, pOIFSViewable.getShortDescription()));
            if (z) {
                if (pOIFSViewable.preferArray()) {
                    for (Object obj2 : pOIFSViewable.getViewableArray()) {
                        arrayList.addAll(inspectViewable(obj2, z, i + 1, str));
                    }
                } else {
                    Iterator viewableIterator = pOIFSViewable.getViewableIterator();
                    while (viewableIterator.hasNext()) {
                        arrayList.addAll(inspectViewable(viewableIterator.next(), z, i + 1, str));
                    }
                }
            }
        } else {
            arrayList.add(indent(i, str, obj.toString()));
        }
        return arrayList;
    }

    private static String indent(int i, String str, String str2) throws IOException {
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        for (int i2 = 0; i2 < i; i2++) {
            stringBuffer2.append(str);
        }
        LineNumberReader lineNumberReader = new LineNumberReader(new StringReader(str2));
        try {
            for (String line = lineNumberReader.readLine(); line != null; line = lineNumberReader.readLine()) {
                stringBuffer.append(stringBuffer2);
                stringBuffer.append(line);
                stringBuffer.append(_EOL);
            }
        } catch (IOException e) {
            stringBuffer.append(stringBuffer2);
            stringBuffer.append(e.getMessage());
            stringBuffer.append(_EOL);
        }
        return stringBuffer.toString();
    }
}
