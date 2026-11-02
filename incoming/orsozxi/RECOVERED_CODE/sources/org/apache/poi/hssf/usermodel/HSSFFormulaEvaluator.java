package org.apache.poi.hssf.usermodel;

import java.util.Iterator;
import org.apache.poi.hssf.record.formula.eval.BoolEval;
import org.apache.poi.hssf.record.formula.eval.ErrorEval;
import org.apache.poi.hssf.record.formula.eval.NumberEval;
import org.apache.poi.hssf.record.formula.eval.StringEval;
import org.apache.poi.hssf.record.formula.eval.ValueEval;
import org.apache.poi.hssf.record.formula.udf.UDFFinder;
import org.apache.poi.ss.formula.CollaboratingWorkbooksEnvironment;
import org.apache.poi.ss.formula.IStabilityClassifier;
import org.apache.poi.ss.formula.WorkbookEvaluator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;

/* loaded from: classes3.dex */
public class HSSFFormulaEvaluator implements FormulaEvaluator {
    private WorkbookEvaluator _bookEvaluator;

    public void setCurrentRow(HSSFRow hSSFRow) {
    }

    public HSSFFormulaEvaluator(HSSFSheet hSSFSheet, HSSFWorkbook hSSFWorkbook) {
        this(hSSFWorkbook);
    }

    public HSSFFormulaEvaluator(HSSFWorkbook hSSFWorkbook) {
        this(hSSFWorkbook, (IStabilityClassifier) null);
    }

    public HSSFFormulaEvaluator(HSSFWorkbook hSSFWorkbook, IStabilityClassifier iStabilityClassifier) {
        this(hSSFWorkbook, iStabilityClassifier, null);
    }

    private HSSFFormulaEvaluator(HSSFWorkbook hSSFWorkbook, IStabilityClassifier iStabilityClassifier, UDFFinder uDFFinder) {
        this._bookEvaluator = new WorkbookEvaluator(HSSFEvaluationWorkbook.create(hSSFWorkbook), iStabilityClassifier, uDFFinder);
    }

    public static HSSFFormulaEvaluator create(HSSFWorkbook hSSFWorkbook, IStabilityClassifier iStabilityClassifier, UDFFinder uDFFinder) {
        return new HSSFFormulaEvaluator(hSSFWorkbook, iStabilityClassifier, uDFFinder);
    }

    public static void setupEnvironment(String[] strArr, HSSFFormulaEvaluator[] hSSFFormulaEvaluatorArr) {
        int length = hSSFFormulaEvaluatorArr.length;
        WorkbookEvaluator[] workbookEvaluatorArr = new WorkbookEvaluator[length];
        for (int i = 0; i < length; i++) {
            workbookEvaluatorArr[i] = hSSFFormulaEvaluatorArr[i]._bookEvaluator;
        }
        CollaboratingWorkbooksEnvironment.setup(strArr, workbookEvaluatorArr);
    }

    @Override // org.apache.poi.ss.usermodel.FormulaEvaluator
    public void clearAllCachedResultValues() {
        this._bookEvaluator.clearAllCachedResultValues();
    }

    public void notifyUpdateCell(HSSFCell hSSFCell) {
        this._bookEvaluator.notifyUpdateCell(new HSSFEvaluationCell(hSSFCell));
    }

    @Override // org.apache.poi.ss.usermodel.FormulaEvaluator
    public void notifyUpdateCell(Cell cell) {
        this._bookEvaluator.notifyUpdateCell(new HSSFEvaluationCell((HSSFCell) cell));
    }

    public void notifyDeleteCell(HSSFCell hSSFCell) {
        this._bookEvaluator.notifyDeleteCell(new HSSFEvaluationCell(hSSFCell));
    }

    @Override // org.apache.poi.ss.usermodel.FormulaEvaluator
    public void notifyDeleteCell(Cell cell) {
        this._bookEvaluator.notifyDeleteCell(new HSSFEvaluationCell((HSSFCell) cell));
    }

    @Override // org.apache.poi.ss.usermodel.FormulaEvaluator
    public void notifySetFormula(Cell cell) {
        this._bookEvaluator.notifyUpdateCell(new HSSFEvaluationCell((HSSFCell) cell));
    }

    @Override // org.apache.poi.ss.usermodel.FormulaEvaluator
    public CellValue evaluate(Cell cell) {
        if (cell == null) {
            return null;
        }
        int cellType = cell.getCellType();
        if (cellType == 0) {
            return new CellValue(cell.getNumericCellValue());
        }
        if (cellType == 1) {
            return new CellValue(cell.getRichStringCellValue().getString());
        }
        if (cellType == 2) {
            return evaluateFormulaCellValue(cell);
        }
        if (cellType == 3) {
            return null;
        }
        if (cellType == 4) {
            return CellValue.valueOf(cell.getBooleanCellValue());
        }
        if (cellType == 5) {
            return CellValue.getError(cell.getErrorCellValue());
        }
        throw new IllegalStateException("Bad cell type (" + cell.getCellType() + ")");
    }

    @Override // org.apache.poi.ss.usermodel.FormulaEvaluator
    public int evaluateFormulaCell(Cell cell) {
        if (cell == null || cell.getCellType() != 2) {
            return -1;
        }
        CellValue cellValueEvaluateFormulaCellValue = evaluateFormulaCellValue(cell);
        setCellValue(cell, cellValueEvaluateFormulaCellValue);
        return cellValueEvaluateFormulaCellValue.getCellType();
    }

    @Override // org.apache.poi.ss.usermodel.FormulaEvaluator
    public HSSFCell evaluateInCell(Cell cell) {
        if (cell == null) {
            return null;
        }
        HSSFCell hSSFCell = (HSSFCell) cell;
        if (cell.getCellType() == 2) {
            CellValue cellValueEvaluateFormulaCellValue = evaluateFormulaCellValue(cell);
            setCellValue(cell, cellValueEvaluateFormulaCellValue);
            setCellType(cell, cellValueEvaluateFormulaCellValue);
        }
        return hSSFCell;
    }

    private static void setCellType(Cell cell, CellValue cellValue) {
        int cellType = cellValue.getCellType();
        if (cellType == 0 || cellType == 1 || cellType == 4 || cellType == 5) {
            cell.setCellType(cellType);
            return;
        }
        throw new IllegalStateException("Unexpected cell value type (" + cellType + ")");
    }

    private static void setCellValue(Cell cell, CellValue cellValue) {
        int cellType = cellValue.getCellType();
        if (cellType == 0) {
            cell.setCellValue(cellValue.getNumberValue());
            return;
        }
        if (cellType == 1) {
            cell.setCellValue(new HSSFRichTextString(cellValue.getStringValue()));
            return;
        }
        if (cellType == 4) {
            cell.setCellValue(cellValue.getBooleanValue());
            return;
        }
        if (cellType == 5) {
            cell.setCellErrorValue(cellValue.getErrorValue());
            return;
        }
        throw new IllegalStateException("Unexpected cell value type (" + cellType + ")");
    }

    public static void evaluateAllFormulaCells(HSSFWorkbook hSSFWorkbook) {
        HSSFFormulaEvaluator hSSFFormulaEvaluator = new HSSFFormulaEvaluator(hSSFWorkbook);
        for (int i = 0; i < hSSFWorkbook.getNumberOfSheets(); i++) {
            Iterator<Row> itRowIterator = hSSFWorkbook.getSheetAt(i).rowIterator();
            while (itRowIterator.hasNext()) {
                Iterator<Cell> itCellIterator = itRowIterator.next().cellIterator();
                while (itCellIterator.hasNext()) {
                    Cell next = itCellIterator.next();
                    if (next.getCellType() == 2) {
                        hSSFFormulaEvaluator.evaluateFormulaCell(next);
                    }
                }
            }
        }
    }

    private CellValue evaluateFormulaCellValue(Cell cell) {
        ValueEval valueEvalEvaluate = this._bookEvaluator.evaluate(new HSSFEvaluationCell((HSSFCell) cell));
        if (valueEvalEvaluate instanceof NumberEval) {
            return new CellValue(((NumberEval) valueEvalEvaluate).getNumberValue());
        }
        if (valueEvalEvaluate instanceof BoolEval) {
            return CellValue.valueOf(((BoolEval) valueEvalEvaluate).getBooleanValue());
        }
        if (valueEvalEvaluate instanceof StringEval) {
            return new CellValue(((StringEval) valueEvalEvaluate).getStringValue());
        }
        if (valueEvalEvaluate instanceof ErrorEval) {
            return CellValue.getError(((ErrorEval) valueEvalEvaluate).getErrorCode());
        }
        throw new RuntimeException("Unexpected eval class (" + valueEvalEvaluate.getClass().getName() + ")");
    }
}
