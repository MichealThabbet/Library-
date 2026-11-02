package org.apache.poi.hssf.record.aggregates;

import it.piapostolos.android.pinotes.R2;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.hssf.model.RecordStream;
import org.apache.poi.hssf.record.CFHeaderRecord;
import org.apache.poi.hssf.record.CFRuleRecord;
import org.apache.poi.hssf.record.Record;
import org.apache.poi.hssf.record.aggregates.RecordAggregate;
import org.apache.poi.hssf.record.formula.AreaErrPtg;
import org.apache.poi.hssf.record.formula.AreaPtg;
import org.apache.poi.hssf.record.formula.FormulaShifter;
import org.apache.poi.hssf.record.formula.Ptg;
import org.apache.poi.ss.util.CellRangeAddress;

/* loaded from: classes3.dex */
public final class CFRecordsAggregate extends RecordAggregate {
    private static final int MAX_CONDTIONAL_FORMAT_RULES = 3;
    private final CFHeaderRecord header;
    private final List rules;

    private CFRecordsAggregate(CFHeaderRecord cFHeaderRecord, CFRuleRecord[] cFRuleRecordArr) {
        if (cFHeaderRecord == null) {
            throw new IllegalArgumentException("header must not be null");
        }
        if (cFRuleRecordArr == null) {
            throw new IllegalArgumentException("rules must not be null");
        }
        if (cFRuleRecordArr.length > 3) {
            throw new IllegalArgumentException("No more than 3 rules may be specified");
        }
        if (cFRuleRecordArr.length != cFHeaderRecord.getNumberOfConditionalFormats()) {
            throw new RuntimeException("Mismatch number of rules");
        }
        this.header = cFHeaderRecord;
        this.rules = new ArrayList(3);
        for (CFRuleRecord cFRuleRecord : cFRuleRecordArr) {
            this.rules.add(cFRuleRecord);
        }
    }

    public CFRecordsAggregate(CellRangeAddress[] cellRangeAddressArr, CFRuleRecord[] cFRuleRecordArr) {
        this(new CFHeaderRecord(cellRangeAddressArr, cFRuleRecordArr.length), cFRuleRecordArr);
    }

    public static CFRecordsAggregate createCFAggregate(RecordStream recordStream) {
        Record next = recordStream.getNext();
        if (next.getSid() != 432) {
            throw new IllegalStateException("next record sid was " + ((int) next.getSid()) + " instead of " + R2.attr.expandedHintEnabled + " as expected");
        }
        CFHeaderRecord cFHeaderRecord = (CFHeaderRecord) next;
        int numberOfConditionalFormats = cFHeaderRecord.getNumberOfConditionalFormats();
        CFRuleRecord[] cFRuleRecordArr = new CFRuleRecord[numberOfConditionalFormats];
        for (int i = 0; i < numberOfConditionalFormats; i++) {
            cFRuleRecordArr[i] = (CFRuleRecord) recordStream.getNext();
        }
        return new CFRecordsAggregate(cFHeaderRecord, cFRuleRecordArr);
    }

    public CFRecordsAggregate cloneCFAggregate() {
        int size = this.rules.size();
        CFRuleRecord[] cFRuleRecordArr = new CFRuleRecord[size];
        for (int i = 0; i < size; i++) {
            cFRuleRecordArr[i] = (CFRuleRecord) getRule(i).clone();
        }
        return new CFRecordsAggregate((CFHeaderRecord) this.header.clone(), cFRuleRecordArr);
    }

    public CFHeaderRecord getHeader() {
        return this.header;
    }

    private void checkRuleIndex(int i) {
        if (i < 0 || i >= this.rules.size()) {
            throw new IllegalArgumentException("Bad rule record index (" + i + ") nRules=" + this.rules.size());
        }
    }

    public CFRuleRecord getRule(int i) {
        checkRuleIndex(i);
        return (CFRuleRecord) this.rules.get(i);
    }

    public void setRule(int i, CFRuleRecord cFRuleRecord) {
        if (cFRuleRecord == null) {
            throw new IllegalArgumentException("r must not be null");
        }
        checkRuleIndex(i);
        this.rules.set(i, cFRuleRecord);
    }

    public void addRule(CFRuleRecord cFRuleRecord) {
        if (cFRuleRecord == null) {
            throw new IllegalArgumentException("r must not be null");
        }
        if (this.rules.size() >= 3) {
            throw new IllegalStateException("Cannot have more than 3 conditional format rules");
        }
        this.rules.add(cFRuleRecord);
        this.header.setNumberOfConditionalFormats(this.rules.size());
    }

    public int getNumberOfRules() {
        return this.rules.size();
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[CF]\n");
        CFHeaderRecord cFHeaderRecord = this.header;
        if (cFHeaderRecord != null) {
            stringBuffer.append(cFHeaderRecord.toString());
        }
        for (int i = 0; i < this.rules.size(); i++) {
            stringBuffer.append(((CFRuleRecord) this.rules.get(i)).toString());
        }
        stringBuffer.append("[/CF]\n");
        return stringBuffer.toString();
    }

    @Override // org.apache.poi.hssf.record.aggregates.RecordAggregate
    public void visitContainedRecords(RecordAggregate.RecordVisitor recordVisitor) {
        recordVisitor.visitRecord(this.header);
        for (int i = 0; i < this.rules.size(); i++) {
            recordVisitor.visitRecord((CFRuleRecord) this.rules.get(i));
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x001a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean updateFormulasAfterCellShift(org.apache.poi.hssf.record.formula.FormulaShifter r9, int r10) {
        /*
            r8 = this;
            org.apache.poi.hssf.record.CFHeaderRecord r0 = r8.header
            org.apache.poi.ss.util.CellRangeAddress[] r0 = r0.getCellRanges()
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            r2 = 0
            r3 = 0
            r4 = 0
        Le:
            int r5 = r0.length
            r6 = 1
            if (r3 >= r5) goto L25
            r5 = r0[r3]
            org.apache.poi.ss.util.CellRangeAddress r7 = shiftRange(r9, r5, r10)
            if (r7 != 0) goto L1c
        L1a:
            r4 = 1
            goto L22
        L1c:
            r1.add(r7)
            if (r7 == r5) goto L22
            goto L1a
        L22:
            int r3 = r3 + 1
            goto Le
        L25:
            if (r4 == 0) goto L38
            int r0 = r1.size()
            if (r0 != 0) goto L2e
            return r2
        L2e:
            org.apache.poi.ss.util.CellRangeAddress[] r0 = new org.apache.poi.ss.util.CellRangeAddress[r0]
            r1.toArray(r0)
            org.apache.poi.hssf.record.CFHeaderRecord r1 = r8.header
            r1.setCellRanges(r0)
        L38:
            java.util.List r0 = r8.rules
            int r0 = r0.size()
            if (r2 >= r0) goto L69
            java.util.List r0 = r8.rules
            java.lang.Object r0 = r0.get(r2)
            org.apache.poi.hssf.record.CFRuleRecord r0 = (org.apache.poi.hssf.record.CFRuleRecord) r0
            org.apache.poi.hssf.record.formula.Ptg[] r1 = r0.getParsedExpression1()
            if (r1 == 0) goto L57
            boolean r3 = r9.adjustFormula(r1, r10)
            if (r3 == 0) goto L57
            r0.setParsedExpression1(r1)
        L57:
            org.apache.poi.hssf.record.formula.Ptg[] r1 = r0.getParsedExpression2()
            if (r1 == 0) goto L66
            boolean r3 = r9.adjustFormula(r1, r10)
            if (r3 == 0) goto L66
            r0.setParsedExpression2(r1)
        L66:
            int r2 = r2 + 1
            goto L38
        L69:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.poi.hssf.record.aggregates.CFRecordsAggregate.updateFormulasAfterCellShift(org.apache.poi.hssf.record.formula.FormulaShifter, int):boolean");
    }

    private static CellRangeAddress shiftRange(FormulaShifter formulaShifter, CellRangeAddress cellRangeAddress, int i) {
        Ptg[] ptgArr = {new AreaPtg(cellRangeAddress.getFirstRow(), cellRangeAddress.getLastRow(), cellRangeAddress.getFirstColumn(), cellRangeAddress.getLastColumn(), false, false, false, false)};
        if (!formulaShifter.adjustFormula(ptgArr, i)) {
            return cellRangeAddress;
        }
        Ptg ptg = ptgArr[0];
        if (ptg instanceof AreaPtg) {
            AreaPtg areaPtg = (AreaPtg) ptg;
            return new CellRangeAddress(areaPtg.getFirstRow(), areaPtg.getLastRow(), areaPtg.getFirstColumn(), areaPtg.getLastColumn());
        }
        if (ptg instanceof AreaErrPtg) {
            return null;
        }
        throw new IllegalStateException("Unexpected shifted ptg class (" + ptg.getClass().getName() + ")");
    }
}
