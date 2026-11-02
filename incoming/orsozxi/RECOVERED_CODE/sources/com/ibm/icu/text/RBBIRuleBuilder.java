package com.ibm.icu.text;

import com.ibm.icu.impl.Assert;
import com.ibm.icu.impl.ICUBinary;
import com.ibm.icu.impl.ICUDebug;
import com.ibm.icu.impl.RBBIDataWrapper;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: classes2.dex */
class RBBIRuleBuilder {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static final int U_BRK_ASSIGN_ERROR = 66054;
    static final int U_BRK_ERROR_LIMIT = 66064;
    static final int U_BRK_ERROR_START = 66048;
    static final int U_BRK_HEX_DIGITS_EXPECTED = 66050;
    static final int U_BRK_INIT_ERROR = 66059;
    static final int U_BRK_INTERNAL_ERROR = 66049;
    static final int U_BRK_MALFORMED_RULE_TAG = 66062;
    static final int U_BRK_MALFORMED_SET = 66063;
    static final int U_BRK_MISMATCHED_PAREN = 66056;
    static final int U_BRK_NEW_LINE_IN_QUOTED_STRING = 66057;
    static final int U_BRK_RULE_EMPTY_SET = 66060;
    static final int U_BRK_RULE_SYNTAX = 66052;
    static final int U_BRK_SEMICOLON_EXPECTED = 66051;
    static final int U_BRK_UNCLOSED_SET = 66053;
    static final int U_BRK_UNDEFINED_VARIABLE = 66058;
    static final int U_BRK_UNRECOGNIZED_OPTION = 66061;
    static final int U_BRK_VARIABLE_REDFINITION = 66055;
    static final int U_ILLEGAL_CHAR_FOUND = 12;
    static final int fForwardTree = 0;
    static final int fReverseTree = 1;
    static final int fSafeFwdTree = 2;
    static final int fSafeRevTree = 3;
    boolean fChainRules;
    String fDebugEnv;
    RBBITableBuilder fForwardTable;
    boolean fLBCMNoChain;
    boolean fLookAheadHardBreak;
    List<Integer> fRuleStatusVals;
    String fRules;
    RBBIRuleScanner fScanner;
    RBBISetBuilder fSetBuilder;
    StringBuilder fStrippedRules;
    List<RBBINode> fUSetNodes;
    RBBINode[] fTreeRoots = new RBBINode[4];
    int fDefaultTree = 0;
    Map<Set<Integer>, Integer> fStatusSets = new HashMap();

    static final int align8(int i) {
        return (i + 7) & (-8);
    }

    RBBIRuleBuilder(String str) {
        this.fDebugEnv = ICUDebug.enabled("rbbi") ? ICUDebug.value("rbbi") : null;
        this.fRules = str;
        this.fStrippedRules = new StringBuilder(str);
        this.fUSetNodes = new ArrayList();
        this.fRuleStatusVals = new ArrayList();
        this.fScanner = new RBBIRuleScanner(this);
        this.fSetBuilder = new RBBISetBuilder(this);
    }

    void flattenData(OutputStream outputStream) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        String strStripRules = RBBIRuleScanner.stripRules(this.fStrippedRules.toString());
        int iAlign8 = align8(this.fForwardTable.getTableSize());
        int iAlign82 = align8(this.fForwardTable.getSafeTableSize());
        int iAlign83 = align8(this.fSetBuilder.getTrieSize());
        int iAlign84 = align8(this.fRuleStatusVals.size() * 4);
        byte[] bytes = strStripRules.getBytes(StandardCharsets.UTF_8);
        int iAlign85 = iAlign8 + 80 + iAlign82 + iAlign84 + iAlign83 + align8(bytes.length + 1);
        ICUBinary.writeHeader(RBBIDataWrapper.DATA_FORMAT, RBBIDataWrapper.FORMAT_VERSION, 0, dataOutputStream);
        int[] iArr = new int[20];
        iArr[0] = 45472;
        iArr[1] = 100663296;
        iArr[2] = iAlign85;
        iArr[3] = this.fSetBuilder.getNumCharCategories();
        iArr[4] = 80;
        iArr[5] = iAlign8;
        iArr[6] = iArr[4] + iAlign8;
        iArr[7] = iAlign82;
        iArr[8] = iArr[6] + iArr[7];
        iArr[9] = this.fSetBuilder.getTrieSize();
        iArr[12] = iArr[8] + iAlign83;
        iArr[13] = iAlign84;
        iArr[10] = iArr[12] + iAlign84;
        iArr[11] = bytes.length;
        int i = 0;
        for (int i2 = 0; i2 < 20; i2++) {
            dataOutputStream.writeInt(iArr[i2]);
            i += 4;
        }
        int iPut = i + this.fForwardTable.exportTable().put(dataOutputStream);
        RBBIDataWrapper.RBBIStateTable rBBIStateTableExportSafeTable = this.fForwardTable.exportSafeTable();
        Assert.assrt(iPut == iArr[6]);
        int iPut2 = iPut + rBBIStateTableExportSafeTable.put(dataOutputStream);
        Assert.assrt(iPut2 == iArr[8]);
        this.fSetBuilder.serializeTrie(outputStream);
        int i3 = iPut2 + iArr[9];
        while (i3 % 8 != 0) {
            dataOutputStream.write(0);
            i3++;
        }
        Assert.assrt(i3 == iArr[12]);
        Iterator<Integer> it2 = this.fRuleStatusVals.iterator();
        while (it2.hasNext()) {
            dataOutputStream.writeInt(it2.next().intValue());
            i3 += 4;
        }
        while (i3 % 8 != 0) {
            dataOutputStream.write(0);
            i3++;
        }
        Assert.assrt(i3 == iArr[10]);
        dataOutputStream.write(bytes, 0, bytes.length);
        dataOutputStream.write(0);
        for (int length = i3 + bytes.length + 1; length % 8 != 0; length++) {
            dataOutputStream.write(0);
        }
    }

    static void compileRules(String str, OutputStream outputStream) throws IOException {
        new RBBIRuleBuilder(str).build(outputStream);
    }

    void build(OutputStream outputStream) throws IOException {
        this.fScanner.parse();
        this.fSetBuilder.buildRanges();
        RBBITableBuilder rBBITableBuilder = new RBBITableBuilder(this, 0);
        this.fForwardTable = rBBITableBuilder;
        rBBITableBuilder.buildForwardTable();
        optimizeTables();
        this.fForwardTable.buildSafeReverseTable();
        String str = this.fDebugEnv;
        if (str != null && str.indexOf("states") >= 0) {
            this.fForwardTable.printStates();
            this.fForwardTable.printRuleStatusTable();
            this.fForwardTable.printReverseTable();
        }
        this.fSetBuilder.buildTrie();
        flattenData(outputStream);
    }

    static class IntPair {
        int first;
        int second;

        IntPair() {
            this.first = 0;
            this.second = 0;
        }

        IntPair(int i, int i2) {
            this.first = 0;
            this.second = 0;
            this.first = i;
            this.second = i2;
        }
    }

    void optimizeTables() {
        boolean z;
        do {
            z = false;
            IntPair intPair = new IntPair(3, 0);
            while (this.fForwardTable.findDuplCharClassFrom(intPair)) {
                this.fSetBuilder.mergeCategories(intPair);
                this.fForwardTable.removeColumn(intPair.second);
                z = true;
            }
            while (this.fForwardTable.removeDuplicateStates() > 0) {
                z = true;
            }
        } while (z);
    }
}
