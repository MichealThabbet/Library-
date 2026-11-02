package org.apache.poi.hssf.usermodel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.poi.hssf.model.HSSFFormulaParser;
import org.apache.poi.hssf.record.formula.NumberPtg;
import org.apache.poi.hssf.record.formula.Ptg;
import org.apache.poi.hssf.record.formula.StringPtg;
import org.apache.poi.ss.usermodel.DataValidationConstraint;

/* loaded from: classes3.dex */
public class DVConstraint implements DataValidationConstraint {
    private static final DataValidationConstraint.ValidationType VT = null;
    private String[] _explicitListValues;
    private String _formula1;
    private String _formula2;
    private int _operator;
    private final int _validationType;
    private Double _value1;
    private Double _value2;

    public static final class FormulaPair {
        private final Ptg[] _formula1;
        private final Ptg[] _formula2;

        public FormulaPair(Ptg[] ptgArr, Ptg[] ptgArr2) {
            this._formula1 = ptgArr;
            this._formula2 = ptgArr2;
        }

        public Ptg[] getFormula1() {
            return this._formula1;
        }

        public Ptg[] getFormula2() {
            return this._formula2;
        }
    }

    private DVConstraint(int i, int i2, String str, String str2, Double d, Double d2, String[] strArr) {
        this._validationType = i;
        this._operator = i2;
        this._formula1 = str;
        this._formula2 = str2;
        this._value1 = d;
        this._value2 = d2;
        this._explicitListValues = strArr;
    }

    private DVConstraint(String str, String[] strArr) {
        this(3, 0, str, null, null, null, strArr);
    }

    public static DVConstraint createNumericConstraint(int i, int i2, String str, String str2) {
        if (i != 0) {
            if (i != 1 && i != 2 && i != 6) {
                throw new IllegalArgumentException("Validation Type (" + i + ") not supported with this method");
            }
            if (str == null) {
                throw new IllegalArgumentException("expr1 must be supplied");
            }
            DataValidationConstraint.OperatorType.validateSecondArg(i2, str2);
        } else if (str != null || str2 != null) {
            throw new IllegalArgumentException("expr1 and expr2 must be null for validation type 'any'");
        }
        String formulaFromTextExpression = getFormulaFromTextExpression(str);
        Double dConvertNumber = formulaFromTextExpression == null ? convertNumber(str) : null;
        String formulaFromTextExpression2 = getFormulaFromTextExpression(str2);
        return new DVConstraint(i, i2, formulaFromTextExpression, formulaFromTextExpression2, dConvertNumber, formulaFromTextExpression2 == null ? convertNumber(str2) : null, null);
    }

    public static DVConstraint createFormulaListConstraint(String str) {
        return new DVConstraint(str, null);
    }

    public static DVConstraint createExplicitListConstraint(String[] strArr) {
        return new DVConstraint(null, strArr);
    }

    public static DVConstraint createTimeConstraint(int i, String str, String str2) {
        if (str == null) {
            throw new IllegalArgumentException("expr1 must be supplied");
        }
        DataValidationConstraint.OperatorType.validateSecondArg(i, str);
        String formulaFromTextExpression = getFormulaFromTextExpression(str);
        Double dConvertTime = formulaFromTextExpression == null ? convertTime(str) : null;
        String formulaFromTextExpression2 = getFormulaFromTextExpression(str2);
        return new DVConstraint(5, i, formulaFromTextExpression, formulaFromTextExpression2, dConvertTime, formulaFromTextExpression2 == null ? convertTime(str2) : null, null);
    }

    public static DVConstraint createDateConstraint(int i, String str, String str2, String str3) {
        if (str == null) {
            throw new IllegalArgumentException("expr1 must be supplied");
        }
        DataValidationConstraint.OperatorType.validateSecondArg(i, str2);
        SimpleDateFormat simpleDateFormat = str3 == null ? null : new SimpleDateFormat(str3);
        String formulaFromTextExpression = getFormulaFromTextExpression(str);
        Double dConvertDate = formulaFromTextExpression == null ? convertDate(str, simpleDateFormat) : null;
        String formulaFromTextExpression2 = getFormulaFromTextExpression(str2);
        return new DVConstraint(4, i, formulaFromTextExpression, formulaFromTextExpression2, dConvertDate, formulaFromTextExpression2 == null ? convertDate(str2, simpleDateFormat) : null, null);
    }

    private static String getFormulaFromTextExpression(String str) {
        if (str == null) {
            return null;
        }
        if (str.length() < 1) {
            throw new IllegalArgumentException("Empty string is not a valid formula/value expression");
        }
        if (str.charAt(0) == '=') {
            return str.substring(1);
        }
        return null;
    }

    private static Double convertNumber(String str) {
        if (str == null) {
            return null;
        }
        try {
            return new Double(str);
        } catch (NumberFormatException unused) {
            throw new RuntimeException("The supplied text '" + str + "' could not be parsed as a number");
        }
    }

    private static Double convertTime(String str) {
        if (str == null) {
            return null;
        }
        return new Double(HSSFDateUtil.convertTime(str));
    }

    private static Double convertDate(String str, SimpleDateFormat simpleDateFormat) {
        Date yYYYMMDDDate;
        if (str == null) {
            return null;
        }
        if (simpleDateFormat == null) {
            yYYYMMDDDate = HSSFDateUtil.parseYYYYMMDDDate(str);
        } else {
            try {
                yYYYMMDDDate = simpleDateFormat.parse(str);
            } catch (ParseException e) {
                throw new RuntimeException("Failed to parse date '" + str + "' using specified format '" + simpleDateFormat + "'", e);
            }
        }
        return new Double(HSSFDateUtil.getExcelDate(yYYYMMDDDate));
    }

    public static DVConstraint createCustomFormulaConstraint(String str) {
        if (str == null) {
            throw new IllegalArgumentException("formula must be supplied");
        }
        return new DVConstraint(7, 0, str, null, null, null, null);
    }

    @Override // org.apache.poi.ss.usermodel.DataValidationConstraint
    public int getValidationType() {
        return this._validationType;
    }

    public boolean isListValidationType() {
        return this._validationType == 3;
    }

    public boolean isExplicitList() {
        return this._validationType == 3 && this._explicitListValues != null;
    }

    @Override // org.apache.poi.ss.usermodel.DataValidationConstraint
    public int getOperator() {
        return this._operator;
    }

    @Override // org.apache.poi.ss.usermodel.DataValidationConstraint
    public void setOperator(int i) {
        this._operator = i;
    }

    @Override // org.apache.poi.ss.usermodel.DataValidationConstraint
    public String[] getExplicitListValues() {
        return this._explicitListValues;
    }

    @Override // org.apache.poi.ss.usermodel.DataValidationConstraint
    public void setExplicitListValues(String[] strArr) {
        if (this._validationType != 3) {
            throw new RuntimeException("Cannot setExplicitListValues on non-list constraint");
        }
        this._formula1 = null;
        this._explicitListValues = strArr;
    }

    @Override // org.apache.poi.ss.usermodel.DataValidationConstraint
    public String getFormula1() {
        return this._formula1;
    }

    @Override // org.apache.poi.ss.usermodel.DataValidationConstraint
    public void setFormula1(String str) {
        this._value1 = null;
        this._explicitListValues = null;
        this._formula1 = str;
    }

    @Override // org.apache.poi.ss.usermodel.DataValidationConstraint
    public String getFormula2() {
        return this._formula2;
    }

    @Override // org.apache.poi.ss.usermodel.DataValidationConstraint
    public void setFormula2(String str) {
        this._value2 = null;
        this._formula2 = str;
    }

    public Double getValue1() {
        return this._value1;
    }

    public void setValue1(double d) {
        this._formula1 = null;
        this._value1 = new Double(d);
    }

    public Double getValue2() {
        return this._value2;
    }

    public void setValue2(double d) {
        this._formula2 = null;
        this._value2 = new Double(d);
    }

    FormulaPair createFormulas(HSSFSheet hSSFSheet) {
        Ptg[] ptgArrConvertDoubleFormula;
        Ptg[] ptgArrCreateListFormula;
        if (isListValidationType()) {
            ptgArrCreateListFormula = createListFormula(hSSFSheet);
            ptgArrConvertDoubleFormula = Ptg.EMPTY_PTG_ARRAY;
        } else {
            Ptg[] ptgArrConvertDoubleFormula2 = convertDoubleFormula(this._formula1, this._value1, hSSFSheet);
            ptgArrConvertDoubleFormula = convertDoubleFormula(this._formula2, this._value2, hSSFSheet);
            ptgArrCreateListFormula = ptgArrConvertDoubleFormula2;
        }
        return new FormulaPair(ptgArrCreateListFormula, ptgArrConvertDoubleFormula);
    }

    private Ptg[] createListFormula(HSSFSheet hSSFSheet) {
        String[] strArr = this._explicitListValues;
        if (strArr == null) {
            HSSFWorkbook workbook = hSSFSheet.getWorkbook();
            return HSSFFormulaParser.parse(this._formula1, workbook, 5, workbook.getSheetIndex(hSSFSheet));
        }
        StringBuffer stringBuffer = new StringBuffer(strArr.length * 16);
        for (int i = 0; i < this._explicitListValues.length; i++) {
            if (i > 0) {
                stringBuffer.append((char) 0);
            }
            stringBuffer.append(this._explicitListValues[i]);
        }
        return new Ptg[]{new StringPtg(stringBuffer.toString())};
    }

    private static Ptg[] convertDoubleFormula(String str, Double d, HSSFSheet hSSFSheet) {
        if (str == null) {
            return d == null ? Ptg.EMPTY_PTG_ARRAY : new Ptg[]{new NumberPtg(d.doubleValue())};
        }
        if (d != null) {
            throw new IllegalStateException("Both formula and value cannot be present");
        }
        HSSFWorkbook workbook = hSSFSheet.getWorkbook();
        return HSSFFormulaParser.parse(str, workbook, 0, workbook.getSheetIndex(hSSFSheet));
    }
}
