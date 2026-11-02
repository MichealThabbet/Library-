package org.apache.poi.hssf.usermodel;

import org.apache.poi.hssf.model.HSSFFormulaParser;
import org.apache.poi.hssf.record.CFRuleRecord;
import org.apache.poi.hssf.record.cf.BorderFormatting;
import org.apache.poi.hssf.record.cf.FontFormatting;
import org.apache.poi.hssf.record.cf.PatternFormatting;
import org.apache.poi.hssf.record.formula.Ptg;

/* loaded from: classes3.dex */
public final class HSSFConditionalFormattingRule {
    private static final byte CELL_COMPARISON = 1;
    private final CFRuleRecord cfRuleRecord;
    private final HSSFWorkbook workbook;

    HSSFConditionalFormattingRule(HSSFWorkbook hSSFWorkbook, CFRuleRecord cFRuleRecord) {
        if (hSSFWorkbook == null) {
            throw new IllegalArgumentException("pWorkbook must not be null");
        }
        if (cFRuleRecord == null) {
            throw new IllegalArgumentException("pRuleRecord must not be null");
        }
        this.workbook = hSSFWorkbook;
        this.cfRuleRecord = cFRuleRecord;
    }

    CFRuleRecord getCfRuleRecord() {
        return this.cfRuleRecord;
    }

    private HSSFFontFormatting getFontFormatting(boolean z) {
        FontFormatting fontFormatting = this.cfRuleRecord.getFontFormatting();
        if (fontFormatting != null) {
            this.cfRuleRecord.setFontFormatting(fontFormatting);
            return new HSSFFontFormatting(this.cfRuleRecord);
        }
        if (!z) {
            return null;
        }
        this.cfRuleRecord.setFontFormatting(new FontFormatting());
        return new HSSFFontFormatting(this.cfRuleRecord);
    }

    public HSSFFontFormatting getFontFormatting() {
        return getFontFormatting(false);
    }

    public HSSFFontFormatting createFontFormatting() {
        return getFontFormatting(true);
    }

    private HSSFBorderFormatting getBorderFormatting(boolean z) {
        BorderFormatting borderFormatting = this.cfRuleRecord.getBorderFormatting();
        if (borderFormatting != null) {
            this.cfRuleRecord.setBorderFormatting(borderFormatting);
            return new HSSFBorderFormatting(this.cfRuleRecord);
        }
        if (!z) {
            return null;
        }
        this.cfRuleRecord.setBorderFormatting(new BorderFormatting());
        return new HSSFBorderFormatting(this.cfRuleRecord);
    }

    public HSSFBorderFormatting getBorderFormatting() {
        return getBorderFormatting(false);
    }

    public HSSFBorderFormatting createBorderFormatting() {
        return getBorderFormatting(true);
    }

    private HSSFPatternFormatting getPatternFormatting(boolean z) {
        PatternFormatting patternFormatting = this.cfRuleRecord.getPatternFormatting();
        if (patternFormatting != null) {
            this.cfRuleRecord.setPatternFormatting(patternFormatting);
            return new HSSFPatternFormatting(this.cfRuleRecord);
        }
        if (!z) {
            return null;
        }
        this.cfRuleRecord.setPatternFormatting(new PatternFormatting());
        return new HSSFPatternFormatting(this.cfRuleRecord);
    }

    public HSSFPatternFormatting getPatternFormatting() {
        return getPatternFormatting(false);
    }

    public HSSFPatternFormatting createPatternFormatting() {
        return getPatternFormatting(true);
    }

    public byte getConditionType() {
        return this.cfRuleRecord.getConditionType();
    }

    public byte getComparisonOperation() {
        return this.cfRuleRecord.getComparisonOperation();
    }

    public String getFormula1() {
        return toFormulaString(this.cfRuleRecord.getParsedExpression1());
    }

    public String getFormula2() {
        if (this.cfRuleRecord.getConditionType() != 1) {
            return null;
        }
        byte comparisonOperation = this.cfRuleRecord.getComparisonOperation();
        if (comparisonOperation == 1 || comparisonOperation == 2) {
            return toFormulaString(this.cfRuleRecord.getParsedExpression2());
        }
        return null;
    }

    private String toFormulaString(Ptg[] ptgArr) {
        if (ptgArr == null) {
            return null;
        }
        return HSSFFormulaParser.toFormulaString(this.workbook, ptgArr);
    }
}
