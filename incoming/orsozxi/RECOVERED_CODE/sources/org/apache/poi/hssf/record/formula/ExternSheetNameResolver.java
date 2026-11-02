package org.apache.poi.hssf.record.formula;

import org.apache.poi.ss.formula.EvaluationWorkbook;
import org.apache.poi.ss.formula.FormulaRenderingWorkbook;

/* loaded from: classes3.dex */
final class ExternSheetNameResolver {
    private ExternSheetNameResolver() {
    }

    public static String prependSheetName(FormulaRenderingWorkbook formulaRenderingWorkbook, int i, String str) {
        StringBuffer stringBuffer;
        EvaluationWorkbook.ExternalSheet externalSheet = formulaRenderingWorkbook.getExternalSheet(i);
        if (externalSheet != null) {
            String workbookName = externalSheet.getWorkbookName();
            String sheetName = externalSheet.getSheetName();
            stringBuffer = new StringBuffer(workbookName.length() + sheetName.length() + str.length() + 4);
            SheetNameFormatter.appendFormat(stringBuffer, workbookName, sheetName);
        } else {
            String sheetNameByExternSheet = formulaRenderingWorkbook.getSheetNameByExternSheet(i);
            stringBuffer = new StringBuffer(sheetNameByExternSheet.length() + str.length() + 4);
            if (sheetNameByExternSheet.length() < 1) {
                stringBuffer.append("#REF");
            } else {
                SheetNameFormatter.appendFormat(stringBuffer, sheetNameByExternSheet);
            }
        }
        stringBuffer.append('!');
        stringBuffer.append(str);
        return stringBuffer.toString();
    }
}
