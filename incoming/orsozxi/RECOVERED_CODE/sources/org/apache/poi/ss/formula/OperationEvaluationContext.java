package org.apache.poi.ss.formula;

import org.apache.poi.hssf.record.formula.Area3DPtg;
import org.apache.poi.hssf.record.formula.NameXPtg;
import org.apache.poi.hssf.record.formula.Ptg;
import org.apache.poi.hssf.record.formula.Ref3DPtg;
import org.apache.poi.hssf.record.formula.eval.ErrorEval;
import org.apache.poi.hssf.record.formula.eval.NameXEval;
import org.apache.poi.hssf.record.formula.eval.ValueEval;
import org.apache.poi.hssf.record.formula.functions.FreeRefFunction;
import org.apache.poi.ss.SpreadsheetVersion;
import org.apache.poi.ss.formula.CollaboratingWorkbooksEnvironment;
import org.apache.poi.ss.formula.EvaluationWorkbook;
import org.apache.poi.ss.util.CellReference;

/* loaded from: classes3.dex */
public final class OperationEvaluationContext {
    public static final FreeRefFunction UDF = UserDefinedFunction.instance;
    private final WorkbookEvaluator _bookEvaluator;
    private final int _columnIndex;
    private final int _rowIndex;
    private final int _sheetIndex;
    private final EvaluationTracker _tracker;
    private final EvaluationWorkbook _workbook;

    public OperationEvaluationContext(WorkbookEvaluator workbookEvaluator, EvaluationWorkbook evaluationWorkbook, int i, int i2, int i3, EvaluationTracker evaluationTracker) {
        this._bookEvaluator = workbookEvaluator;
        this._workbook = evaluationWorkbook;
        this._sheetIndex = i;
        this._rowIndex = i2;
        this._columnIndex = i3;
        this._tracker = evaluationTracker;
    }

    public EvaluationWorkbook getWorkbook() {
        return this._workbook;
    }

    public int getRowIndex() {
        return this._rowIndex;
    }

    public int getColumnIndex() {
        return this._columnIndex;
    }

    SheetRefEvaluator createExternSheetRefEvaluator(ExternSheetReferenceToken externSheetReferenceToken) {
        return createExternSheetRefEvaluator(externSheetReferenceToken.getExternSheetIndex());
    }

    SheetRefEvaluator createExternSheetRefEvaluator(int i) {
        WorkbookEvaluator workbookEvaluator;
        int iConvertFromExternSheetIndex;
        EvaluationWorkbook.ExternalSheet externalSheet = this._workbook.getExternalSheet(i);
        if (externalSheet == null) {
            iConvertFromExternSheetIndex = this._workbook.convertFromExternSheetIndex(i);
            workbookEvaluator = this._bookEvaluator;
        } else {
            String workbookName = externalSheet.getWorkbookName();
            try {
                WorkbookEvaluator otherWorkbookEvaluator = this._bookEvaluator.getOtherWorkbookEvaluator(workbookName);
                int sheetIndex = otherWorkbookEvaluator.getSheetIndex(externalSheet.getSheetName());
                if (sheetIndex < 0) {
                    throw new RuntimeException("Invalid sheet name '" + externalSheet.getSheetName() + "' in bool '" + workbookName + "'.");
                }
                workbookEvaluator = otherWorkbookEvaluator;
                iConvertFromExternSheetIndex = sheetIndex;
            } catch (CollaboratingWorkbooksEnvironment.WorkbookNotFoundException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return new SheetRefEvaluator(workbookEvaluator, this._tracker, iConvertFromExternSheetIndex);
    }

    private SheetRefEvaluator createExternSheetRefEvaluator(String str, String str2) {
        WorkbookEvaluator otherWorkbookEvaluator;
        if (str == null) {
            otherWorkbookEvaluator = this._bookEvaluator;
        } else {
            if (str2 == null) {
                throw new IllegalArgumentException("sheetName must not be null if workbookName is provided");
            }
            try {
                otherWorkbookEvaluator = this._bookEvaluator.getOtherWorkbookEvaluator(str);
            } catch (CollaboratingWorkbooksEnvironment.WorkbookNotFoundException unused) {
                return null;
            }
        }
        int sheetIndex = str2 == null ? this._sheetIndex : otherWorkbookEvaluator.getSheetIndex(str2);
        if (sheetIndex < 0) {
            return null;
        }
        return new SheetRefEvaluator(otherWorkbookEvaluator, this._tracker, sheetIndex);
    }

    public SheetRefEvaluator getRefEvaluatorForCurrentSheet() {
        return new SheetRefEvaluator(this._bookEvaluator, this._tracker, this._sheetIndex);
    }

    public ValueEval getDynamicReference(String str, String str2, String str3, String str4, boolean z) {
        int lastRowIndex;
        int colRef;
        int colRef2;
        int rowRef;
        if (!z) {
            throw new RuntimeException("R1C1 style not supported yet");
        }
        SheetRefEvaluator sheetRefEvaluatorCreateExternSheetRefEvaluator = createExternSheetRefEvaluator(str, str2);
        if (sheetRefEvaluatorCreateExternSheetRefEvaluator == null) {
            return ErrorEval.REF_INVALID;
        }
        SpreadsheetVersion spreadsheetVersion = ((FormulaParsingWorkbook) this._workbook).getSpreadsheetVersion();
        CellReference.NameType nameTypeClassifyCellReference = classifyCellReference(str3, spreadsheetVersion);
        int i = AnonymousClass1.$SwitchMap$org$apache$poi$ss$util$CellReference$NameType[nameTypeClassifyCellReference.ordinal()];
        if (i == 1) {
            return ErrorEval.REF_INVALID;
        }
        if (i == 2) {
            EvaluationName name = ((FormulaParsingWorkbook) this._workbook).getName(str3, this._sheetIndex);
            if (!name.isRange()) {
                throw new RuntimeException("Specified name '" + str3 + "' is not a range as expected.");
            }
            return this._bookEvaluator.evaluateNameFormula(name.getNameDefinition(), this);
        }
        if (str4 == null) {
            int i2 = AnonymousClass1.$SwitchMap$org$apache$poi$ss$util$CellReference$NameType[nameTypeClassifyCellReference.ordinal()];
            if (i2 == 3 || i2 == 4) {
                return ErrorEval.REF_INVALID;
            }
            if (i2 == 5) {
                CellReference cellReference = new CellReference(str3);
                return new LazyRefEval(cellReference.getRow(), cellReference.getCol(), sheetRefEvaluatorCreateExternSheetRefEvaluator);
            }
            throw new IllegalStateException("Unexpected reference classification of '" + str3 + "'.");
        }
        CellReference.NameType nameTypeClassifyCellReference2 = classifyCellReference(str3, spreadsheetVersion);
        int i3 = AnonymousClass1.$SwitchMap$org$apache$poi$ss$util$CellReference$NameType[nameTypeClassifyCellReference2.ordinal()];
        if (i3 == 1) {
            return ErrorEval.REF_INVALID;
        }
        if (i3 == 2) {
            throw new RuntimeException("Cannot evaluate '" + str3 + "'. Indirect evaluation of defined names not supported yet");
        }
        if (nameTypeClassifyCellReference2 != nameTypeClassifyCellReference) {
            return ErrorEval.REF_INVALID;
        }
        int i4 = AnonymousClass1.$SwitchMap$org$apache$poi$ss$util$CellReference$NameType[nameTypeClassifyCellReference.ordinal()];
        if (i4 == 3) {
            lastRowIndex = spreadsheetVersion.getLastRowIndex();
            colRef = parseColRef(str3);
            colRef2 = parseColRef(str4);
            rowRef = 0;
        } else if (i4 == 4) {
            colRef2 = spreadsheetVersion.getLastColumnIndex();
            rowRef = parseRowRef(str3);
            lastRowIndex = parseRowRef(str4);
            colRef = 0;
        } else if (i4 == 5) {
            CellReference cellReference2 = new CellReference(str3);
            int row = cellReference2.getRow();
            short col = cellReference2.getCol();
            CellReference cellReference3 = new CellReference(str4);
            int row2 = cellReference3.getRow();
            colRef = col;
            rowRef = row;
            colRef2 = cellReference3.getCol();
            lastRowIndex = row2;
        } else {
            throw new IllegalStateException("Unexpected reference classification of '" + str3 + "'.");
        }
        return new LazyAreaEval(rowRef, colRef, lastRowIndex, colRef2, sheetRefEvaluatorCreateExternSheetRefEvaluator);
    }

    /* renamed from: org.apache.poi.ss.formula.OperationEvaluationContext$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$apache$poi$ss$util$CellReference$NameType;

        static {
            int[] iArr = new int[CellReference.NameType.values().length];
            $SwitchMap$org$apache$poi$ss$util$CellReference$NameType = iArr;
            try {
                iArr[CellReference.NameType.BAD_CELL_OR_NAMED_RANGE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$apache$poi$ss$util$CellReference$NameType[CellReference.NameType.NAMED_RANGE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$apache$poi$ss$util$CellReference$NameType[CellReference.NameType.COLUMN.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$org$apache$poi$ss$util$CellReference$NameType[CellReference.NameType.ROW.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$org$apache$poi$ss$util$CellReference$NameType[CellReference.NameType.CELL.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    private static int parseRowRef(String str) {
        return CellReference.convertColStringToIndex(str);
    }

    private static int parseColRef(String str) {
        return Integer.parseInt(str) - 1;
    }

    private static CellReference.NameType classifyCellReference(String str, SpreadsheetVersion spreadsheetVersion) {
        if (str.length() < 1) {
            return CellReference.NameType.BAD_CELL_OR_NAMED_RANGE;
        }
        return CellReference.classifyCellReference(str, spreadsheetVersion);
    }

    public FreeRefFunction findUserDefinedFunction(String str) {
        return this._bookEvaluator.findUserDefinedFunction(str);
    }

    public ValueEval getRefEval(int i, int i2) {
        return new LazyRefEval(i, i2, getRefEvaluatorForCurrentSheet());
    }

    public ValueEval getRef3DEval(int i, int i2, int i3) {
        return new LazyRefEval(i, i2, createExternSheetRefEvaluator(i3));
    }

    public ValueEval getAreaEval(int i, int i2, int i3, int i4) {
        return new LazyAreaEval(i, i2, i3, i4, getRefEvaluatorForCurrentSheet());
    }

    public ValueEval getArea3DEval(int i, int i2, int i3, int i4, int i5) {
        return new LazyAreaEval(i, i2, i3, i4, createExternSheetRefEvaluator(i5));
    }

    public ValueEval getNameXEval(NameXPtg nameXPtg) {
        EvaluationWorkbook.ExternalSheet externalSheet = this._workbook.getExternalSheet(nameXPtg.getSheetRefIndex());
        if (externalSheet == null) {
            return new NameXEval(nameXPtg);
        }
        String workbookName = externalSheet.getWorkbookName();
        EvaluationWorkbook.ExternalName externalName = this._workbook.getExternalName(nameXPtg.getSheetRefIndex(), nameXPtg.getNameIndex());
        try {
            WorkbookEvaluator otherWorkbookEvaluator = this._bookEvaluator.getOtherWorkbookEvaluator(workbookName);
            EvaluationName name = otherWorkbookEvaluator.getName(externalName.getName(), externalName.getIx() - 1);
            if (name != null && name.hasFormula()) {
                if (name.getNameDefinition().length > 1) {
                    throw new RuntimeException("Complex name formulas not supported yet");
                }
                Ptg ptg = name.getNameDefinition()[0];
                if (ptg instanceof Ref3DPtg) {
                    Ref3DPtg ref3DPtg = (Ref3DPtg) ptg;
                    return new LazyRefEval(ref3DPtg.getRow(), ref3DPtg.getColumn(), createExternSheetRefEvaluator(workbookName, otherWorkbookEvaluator.getSheetName(otherWorkbookEvaluator.getSheetIndexByExternIndex(ref3DPtg.getExternSheetIndex()))));
                }
                if (ptg instanceof Area3DPtg) {
                    Area3DPtg area3DPtg = (Area3DPtg) ptg;
                    return new LazyAreaEval(area3DPtg.getFirstRow(), area3DPtg.getFirstColumn(), area3DPtg.getLastRow(), area3DPtg.getLastColumn(), createExternSheetRefEvaluator(workbookName, otherWorkbookEvaluator.getSheetName(otherWorkbookEvaluator.getSheetIndexByExternIndex(area3DPtg.getExternSheetIndex()))));
                }
            }
            return ErrorEval.REF_INVALID;
        } catch (CollaboratingWorkbooksEnvironment.WorkbookNotFoundException unused) {
            return ErrorEval.REF_INVALID;
        }
    }
}
