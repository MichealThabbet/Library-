package org.apache.poi.poifs.dev;

import com.app.orsozoxi.Views.CustomCopticTextView;
import com.huawei.hms.support.hianalytics.HiAnalyticsConstant;
import com.ibm.icu.impl.locale.LanguageTag;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

/* loaded from: classes3.dex */
public class POIFSViewer {
    public static void main(String[] strArr) {
        if (strArr.length < 0) {
            System.err.println("Must specify at least one file to view");
            System.exit(1);
        }
        boolean z = strArr.length > 1;
        for (String str : strArr) {
            viewFile(str, z);
        }
    }

    private static void viewFile(String str, boolean z) {
        if (z) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(".");
            for (int i = 0; i < str.length(); i++) {
                stringBuffer.append(LanguageTag.SEP);
            }
            stringBuffer.append(".");
            System.out.println(stringBuffer);
            System.out.println(HiAnalyticsConstant.REPORT_VAL_SEPARATOR + str + HiAnalyticsConstant.REPORT_VAL_SEPARATOR);
            System.out.println(stringBuffer);
        }
        try {
            Iterator it2 = POIFSViewEngine.inspectViewable(new POIFSFileSystem(new FileInputStream(str)), true, 0, CustomCopticTextView.TWO_CHINESE_BLANK).iterator();
            while (it2.hasNext()) {
                System.out.print(it2.next());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
