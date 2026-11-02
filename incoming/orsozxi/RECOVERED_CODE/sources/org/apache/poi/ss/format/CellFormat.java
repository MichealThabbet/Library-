package org.apache.poi.ss.format;

import java.util.ArrayList;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JLabel;
import org.apache.poi.ss.usermodel.Cell;

/* loaded from: classes3.dex */
public class CellFormat {
    private final String format;
    private final CellFormatPart negNumFmt;
    private final CellFormatPart posNumFmt;
    private final CellFormatPart textFmt;
    private final CellFormatPart zeroNumFmt;
    private static final Pattern ONE_PART = Pattern.compile(CellFormatPart.FORMAT_PAT.pattern() + "(;|$)", 6);
    private static final CellFormatPart DEFAULT_TEXT_FORMAT = new CellFormatPart("@");
    public static final CellFormat GENERAL_FORMAT = new CellFormat("General") { // from class: org.apache.poi.ss.format.CellFormat.1
        @Override // org.apache.poi.ss.format.CellFormat
        public CellFormatResult apply(Object obj) {
            String string;
            if (obj == null) {
                string = "";
            } else if (obj instanceof Number) {
                string = CellNumberFormatter.SIMPLE_NUMBER.format(obj);
            } else {
                string = obj.toString();
            }
            return new CellFormatResult(true, string, null);
        }
    };
    private static final Map<String, CellFormat> formatCache = new WeakHashMap();

    public static CellFormat getInstance(String str) {
        CellFormat cellFormat = formatCache.get(str);
        if (cellFormat == null) {
            if (str.equals("General")) {
                cellFormat = GENERAL_FORMAT;
            } else {
                cellFormat = new CellFormat(str);
            }
            formatCache.put(str, cellFormat);
        }
        return cellFormat;
    }

    private CellFormat(String str) {
        this.format = str;
        Matcher matcher = ONE_PART.matcher(str);
        ArrayList arrayList = new ArrayList();
        while (matcher.find()) {
            try {
                String strGroup = matcher.group();
                arrayList.add(new CellFormatPart(strGroup.endsWith(";") ? strGroup.substring(0, strGroup.length() - 1) : strGroup));
            } catch (RuntimeException e) {
                CellFormatter.logger.log(Level.WARNING, "Invalid format: " + CellFormatter.quote(matcher.group()), (Throwable) e);
                arrayList.add(null);
            }
        }
        int size = arrayList.size();
        if (size == 1) {
            CellFormatPart cellFormatPart = (CellFormatPart) arrayList.get(0);
            this.negNumFmt = cellFormatPart;
            this.zeroNumFmt = cellFormatPart;
            this.posNumFmt = cellFormatPart;
            this.textFmt = DEFAULT_TEXT_FORMAT;
            return;
        }
        if (size == 2) {
            CellFormatPart cellFormatPart2 = (CellFormatPart) arrayList.get(0);
            this.zeroNumFmt = cellFormatPart2;
            this.posNumFmt = cellFormatPart2;
            this.negNumFmt = (CellFormatPart) arrayList.get(1);
            this.textFmt = DEFAULT_TEXT_FORMAT;
            return;
        }
        if (size == 3) {
            this.posNumFmt = (CellFormatPart) arrayList.get(0);
            this.zeroNumFmt = (CellFormatPart) arrayList.get(1);
            this.negNumFmt = (CellFormatPart) arrayList.get(2);
            this.textFmt = DEFAULT_TEXT_FORMAT;
            return;
        }
        this.posNumFmt = (CellFormatPart) arrayList.get(0);
        this.zeroNumFmt = (CellFormatPart) arrayList.get(1);
        this.negNumFmt = (CellFormatPart) arrayList.get(2);
        this.textFmt = (CellFormatPart) arrayList.get(3);
    }

    public CellFormatResult apply(Object obj) {
        if (obj instanceof Number) {
            double dDoubleValue = ((Number) obj).doubleValue();
            if (dDoubleValue > 0.0d) {
                return this.posNumFmt.apply(obj);
            }
            if (dDoubleValue < 0.0d) {
                return this.negNumFmt.apply(Double.valueOf(-dDoubleValue));
            }
            return this.zeroNumFmt.apply(obj);
        }
        return this.textFmt.apply(obj);
    }

    public CellFormatResult apply(Cell cell) {
        int iUltimateType = ultimateType(cell);
        if (iUltimateType == 0) {
            return apply(Double.valueOf(cell.getNumericCellValue()));
        }
        if (iUltimateType == 1) {
            return apply(cell.getStringCellValue());
        }
        if (iUltimateType == 3) {
            return apply("");
        }
        if (iUltimateType == 4) {
            return apply(cell.getStringCellValue());
        }
        return apply("?");
    }

    public CellFormatResult apply(JLabel jLabel, Object obj) {
        CellFormatResult cellFormatResultApply = apply(obj);
        jLabel.setText(cellFormatResultApply.text);
        if (cellFormatResultApply.textColor != null) {
            jLabel.setForeground(cellFormatResultApply.textColor);
        }
        return cellFormatResultApply;
    }

    public CellFormatResult apply(JLabel jLabel, Cell cell) {
        int iUltimateType = ultimateType(cell);
        if (iUltimateType == 0) {
            return apply(jLabel, Double.valueOf(cell.getNumericCellValue()));
        }
        if (iUltimateType == 1) {
            return apply(jLabel, cell.getStringCellValue());
        }
        if (iUltimateType == 3) {
            return apply(jLabel, "");
        }
        if (iUltimateType == 4) {
            return apply(jLabel, cell.getStringCellValue());
        }
        return apply(jLabel, "?");
    }

    public static int ultimateType(Cell cell) {
        int cellType = cell.getCellType();
        return cellType == 2 ? cell.getCachedFormulaResultType() : cellType;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof CellFormat) {
            return this.format.equals(((CellFormat) obj).format);
        }
        return false;
    }

    public int hashCode() {
        return this.format.hashCode();
    }
}
