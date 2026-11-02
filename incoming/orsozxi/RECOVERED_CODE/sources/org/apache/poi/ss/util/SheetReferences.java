package org.apache.poi.ss.util;

import java.util.HashMap;
import java.util.Map;

/* loaded from: classes3.dex */
public class SheetReferences {
    Map map = new HashMap(5);

    public void addSheetReference(String str, int i) {
        this.map.put(Integer.valueOf(i), str);
    }

    public String getSheetName(int i) {
        return (String) this.map.get(Integer.valueOf(i));
    }
}
