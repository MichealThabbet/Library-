package com.ibm.icu.text;

import com.ibm.icu.impl.Assert;
import com.ibm.icu.impl.RBBIDataWrapper;
import com.ibm.icu.lang.UCharacter;
import com.ibm.icu.text.RBBIRuleBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/* loaded from: classes2.dex */
class RBBITableBuilder {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int MAX_STATE_FOR_8BITS_TABLE = 255;
    int[] fLookAheadRuleMap;
    private RBBIRuleBuilder fRB;
    private int fRootIx;
    private List<short[]> fSafeTable;
    int fLASlotsInUse = 1;
    private List<RBBIStateDescriptor> fDStates = new ArrayList();

    static class RBBIStateDescriptor {
        int fAccepting;
        int[] fDtran;
        int fLookAhead;
        boolean fMarked;
        int fTagsIdx;
        SortedSet<Integer> fTagVals = new TreeSet();
        Set<RBBINode> fPositions = new HashSet();

        RBBIStateDescriptor(int i) {
            this.fDtran = new int[i + 1];
        }
    }

    RBBITableBuilder(RBBIRuleBuilder rBBIRuleBuilder, int i) {
        this.fRootIx = i;
        this.fRB = rBBIRuleBuilder;
    }

    void buildForwardTable() {
        if (this.fRB.fTreeRoots[this.fRootIx] == null) {
            return;
        }
        this.fRB.fTreeRoots[this.fRootIx] = this.fRB.fTreeRoots[this.fRootIx].flattenVariables();
        if (this.fRB.fDebugEnv != null && this.fRB.fDebugEnv.indexOf("ftree") >= 0) {
            System.out.println("Parse tree after flattening variable references.");
            this.fRB.fTreeRoots[this.fRootIx].printTree(true);
        }
        if (this.fRB.fSetBuilder.sawBOF()) {
            RBBINode rBBINode = new RBBINode(8);
            RBBINode rBBINode2 = new RBBINode(3);
            rBBINode.fLeftChild = rBBINode2;
            rBBINode.fRightChild = this.fRB.fTreeRoots[this.fRootIx];
            rBBINode2.fParent = rBBINode;
            rBBINode2.fVal = 2;
            this.fRB.fTreeRoots[this.fRootIx] = rBBINode;
        }
        RBBINode rBBINode3 = new RBBINode(8);
        rBBINode3.fLeftChild = this.fRB.fTreeRoots[this.fRootIx];
        this.fRB.fTreeRoots[this.fRootIx].fParent = rBBINode3;
        RBBINode rBBINode4 = new RBBINode(6);
        rBBINode3.fRightChild = rBBINode4;
        rBBINode3.fRightChild.fParent = rBBINode3;
        this.fRB.fTreeRoots[this.fRootIx] = rBBINode3;
        this.fRB.fTreeRoots[this.fRootIx].flattenSets();
        if (this.fRB.fDebugEnv != null && this.fRB.fDebugEnv.indexOf("stree") >= 0) {
            System.out.println("Parse tree after flattening Unicode Set references.");
            this.fRB.fTreeRoots[this.fRootIx].printTree(true);
        }
        calcNullable(this.fRB.fTreeRoots[this.fRootIx]);
        calcFirstPos(this.fRB.fTreeRoots[this.fRootIx]);
        calcLastPos(this.fRB.fTreeRoots[this.fRootIx]);
        calcFollowPos(this.fRB.fTreeRoots[this.fRootIx]);
        if (this.fRB.fDebugEnv != null && this.fRB.fDebugEnv.indexOf("pos") >= 0) {
            System.out.print("\n");
            printPosSets(this.fRB.fTreeRoots[this.fRootIx]);
        }
        if (this.fRB.fChainRules) {
            calcChainedFollowPos(this.fRB.fTreeRoots[this.fRootIx], rBBINode4);
        }
        if (this.fRB.fSetBuilder.sawBOF()) {
            bofFixup();
        }
        buildStateTable();
        mapLookAheadRules();
        flagAcceptingStates();
        flagLookAheadStates();
        flagTaggedStates();
        mergeRuleStatusVals();
    }

    void calcNullable(RBBINode rBBINode) {
        if (rBBINode == null) {
            return;
        }
        if (rBBINode.fType == 0 || rBBINode.fType == 6) {
            rBBINode.fNullable = false;
            return;
        }
        if (rBBINode.fType == 4 || rBBINode.fType == 5) {
            rBBINode.fNullable = true;
            return;
        }
        calcNullable(rBBINode.fLeftChild);
        calcNullable(rBBINode.fRightChild);
        if (rBBINode.fType == 9) {
            rBBINode.fNullable = rBBINode.fLeftChild.fNullable || rBBINode.fRightChild.fNullable;
            return;
        }
        if (rBBINode.fType == 8) {
            if (rBBINode.fLeftChild.fNullable && rBBINode.fRightChild.fNullable) {
                z = true;
            }
            rBBINode.fNullable = z;
            return;
        }
        if (rBBINode.fType == 10 || rBBINode.fType == 12) {
            rBBINode.fNullable = true;
        } else {
            rBBINode.fNullable = false;
        }
    }

    void calcFirstPos(RBBINode rBBINode) {
        if (rBBINode == null) {
            return;
        }
        if (rBBINode.fType == 3 || rBBINode.fType == 6 || rBBINode.fType == 4 || rBBINode.fType == 5) {
            rBBINode.fFirstPosSet.add(rBBINode);
            return;
        }
        calcFirstPos(rBBINode.fLeftChild);
        calcFirstPos(rBBINode.fRightChild);
        if (rBBINode.fType == 9) {
            rBBINode.fFirstPosSet.addAll(rBBINode.fLeftChild.fFirstPosSet);
            rBBINode.fFirstPosSet.addAll(rBBINode.fRightChild.fFirstPosSet);
            return;
        }
        if (rBBINode.fType == 8) {
            rBBINode.fFirstPosSet.addAll(rBBINode.fLeftChild.fFirstPosSet);
            if (rBBINode.fLeftChild.fNullable) {
                rBBINode.fFirstPosSet.addAll(rBBINode.fRightChild.fFirstPosSet);
                return;
            }
            return;
        }
        if (rBBINode.fType == 10 || rBBINode.fType == 12 || rBBINode.fType == 11) {
            rBBINode.fFirstPosSet.addAll(rBBINode.fLeftChild.fFirstPosSet);
        }
    }

    void calcLastPos(RBBINode rBBINode) {
        if (rBBINode == null) {
            return;
        }
        if (rBBINode.fType == 3 || rBBINode.fType == 6 || rBBINode.fType == 4 || rBBINode.fType == 5) {
            rBBINode.fLastPosSet.add(rBBINode);
            return;
        }
        calcLastPos(rBBINode.fLeftChild);
        calcLastPos(rBBINode.fRightChild);
        if (rBBINode.fType == 9) {
            rBBINode.fLastPosSet.addAll(rBBINode.fLeftChild.fLastPosSet);
            rBBINode.fLastPosSet.addAll(rBBINode.fRightChild.fLastPosSet);
            return;
        }
        if (rBBINode.fType == 8) {
            rBBINode.fLastPosSet.addAll(rBBINode.fRightChild.fLastPosSet);
            if (rBBINode.fRightChild.fNullable) {
                rBBINode.fLastPosSet.addAll(rBBINode.fLeftChild.fLastPosSet);
                return;
            }
            return;
        }
        if (rBBINode.fType == 10 || rBBINode.fType == 12 || rBBINode.fType == 11) {
            rBBINode.fLastPosSet.addAll(rBBINode.fLeftChild.fLastPosSet);
        }
    }

    void calcFollowPos(RBBINode rBBINode) {
        if (rBBINode == null || rBBINode.fType == 3 || rBBINode.fType == 6) {
            return;
        }
        calcFollowPos(rBBINode.fLeftChild);
        calcFollowPos(rBBINode.fRightChild);
        if (rBBINode.fType == 8) {
            Iterator<RBBINode> it2 = rBBINode.fLeftChild.fLastPosSet.iterator();
            while (it2.hasNext()) {
                it2.next().fFollowPos.addAll(rBBINode.fRightChild.fFirstPosSet);
            }
        }
        if (rBBINode.fType == 10 || rBBINode.fType == 11) {
            Iterator<RBBINode> it3 = rBBINode.fLastPosSet.iterator();
            while (it3.hasNext()) {
                it3.next().fFollowPos.addAll(rBBINode.fFirstPosSet);
            }
        }
    }

    void addRuleRootNodes(List<RBBINode> list, RBBINode rBBINode) {
        if (rBBINode == null) {
            return;
        }
        if (rBBINode.fRuleRoot) {
            list.add(rBBINode);
        } else {
            addRuleRootNodes(list, rBBINode.fLeftChild);
            addRuleRootNodes(list, rBBINode.fRightChild);
        }
    }

    void calcChainedFollowPos(RBBINode rBBINode, RBBINode rBBINode2) {
        int firstChar;
        ArrayList arrayList = new ArrayList();
        rBBINode.findNodes(arrayList, 3);
        ArrayList arrayList2 = new ArrayList();
        addRuleRootNodes(arrayList2, rBBINode);
        HashSet<RBBINode> hashSet = new HashSet();
        for (RBBINode rBBINode3 : arrayList2) {
            if (rBBINode3.fChainIn) {
                hashSet.addAll(rBBINode3.fFirstPosSet);
            }
        }
        for (RBBINode rBBINode4 : arrayList) {
            if (rBBINode4.fFollowPos.contains(rBBINode2) && (!this.fRB.fLBCMNoChain || (firstChar = this.fRB.fSetBuilder.getFirstChar(rBBINode4.fVal)) == -1 || UCharacter.getIntPropertyValue(firstChar, 4104) != 9)) {
                for (RBBINode rBBINode5 : hashSet) {
                    if (rBBINode5.fType == 3 && rBBINode4.fVal == rBBINode5.fVal) {
                        rBBINode4.fFollowPos.addAll(rBBINode5.fFollowPos);
                    }
                }
            }
        }
    }

    void bofFixup() {
        RBBINode rBBINode = this.fRB.fTreeRoots[this.fRootIx].fLeftChild.fLeftChild;
        Assert.assrt(rBBINode.fType == 3);
        Assert.assrt(rBBINode.fVal == 2);
        for (RBBINode rBBINode2 : this.fRB.fTreeRoots[this.fRootIx].fLeftChild.fRightChild.fFirstPosSet) {
            if (rBBINode2.fType == 3 && rBBINode2.fVal == rBBINode.fVal) {
                rBBINode.fFollowPos.addAll(rBBINode2.fFollowPos);
            }
        }
    }

    void buildStateTable() {
        RBBIStateDescriptor rBBIStateDescriptor;
        int numCharCategories = this.fRB.fSetBuilder.getNumCharCategories() - 1;
        this.fDStates.add(new RBBIStateDescriptor(numCharCategories));
        RBBIStateDescriptor rBBIStateDescriptor2 = new RBBIStateDescriptor(numCharCategories);
        rBBIStateDescriptor2.fPositions.addAll(this.fRB.fTreeRoots[this.fRootIx].fFirstPosSet);
        this.fDStates.add(rBBIStateDescriptor2);
        while (true) {
            int i = 1;
            while (true) {
                if (i >= this.fDStates.size()) {
                    rBBIStateDescriptor = null;
                    break;
                }
                rBBIStateDescriptor = this.fDStates.get(i);
                if (!rBBIStateDescriptor.fMarked) {
                    break;
                } else {
                    i++;
                }
            }
            if (rBBIStateDescriptor == null) {
                return;
            }
            rBBIStateDescriptor.fMarked = true;
            for (int i2 = 1; i2 <= numCharCategories; i2++) {
                Set<RBBINode> hashSet = null;
                for (RBBINode rBBINode : rBBIStateDescriptor.fPositions) {
                    if (rBBINode.fType == 3 && rBBINode.fVal == i2) {
                        if (hashSet == null) {
                            hashSet = new HashSet<>();
                        }
                        hashSet.addAll(rBBINode.fFollowPos);
                    }
                }
                if (hashSet != null) {
                    boolean z = false;
                    Assert.assrt(hashSet.size() > 0);
                    int size = 0;
                    while (true) {
                        if (size >= this.fDStates.size()) {
                            size = 0;
                            break;
                        }
                        RBBIStateDescriptor rBBIStateDescriptor3 = this.fDStates.get(size);
                        if (hashSet.equals(rBBIStateDescriptor3.fPositions)) {
                            hashSet = rBBIStateDescriptor3.fPositions;
                            z = true;
                            break;
                        }
                        size++;
                    }
                    if (!z) {
                        RBBIStateDescriptor rBBIStateDescriptor4 = new RBBIStateDescriptor(numCharCategories);
                        rBBIStateDescriptor4.fPositions = hashSet;
                        this.fDStates.add(rBBIStateDescriptor4);
                        size = this.fDStates.size() - 1;
                    }
                    rBBIStateDescriptor.fDtran[i2] = size;
                }
            }
        }
    }

    void mapLookAheadRules() {
        this.fLookAheadRuleMap = new int[this.fRB.fScanner.numRules() + 1];
        for (RBBIStateDescriptor rBBIStateDescriptor : this.fDStates) {
            boolean z = false;
            int i = 0;
            for (RBBINode rBBINode : rBBIStateDescriptor.fPositions) {
                if (rBBINode.fType == 4) {
                    int i2 = this.fLookAheadRuleMap[rBBINode.fVal];
                    if (i2 != 0 && i == 0) {
                        i = i2;
                    }
                    z = true;
                }
            }
            if (z) {
                if (i == 0) {
                    i = this.fLASlotsInUse + 1;
                    this.fLASlotsInUse = i;
                }
                for (RBBINode rBBINode2 : rBBIStateDescriptor.fPositions) {
                    if (rBBINode2.fType == 4) {
                        int i3 = rBBINode2.fVal;
                        int[] iArr = this.fLookAheadRuleMap;
                        int i4 = iArr[i3];
                        iArr[i3] = i;
                    }
                }
            }
        }
    }

    void flagAcceptingStates() {
        ArrayList arrayList = new ArrayList();
        this.fRB.fTreeRoots[this.fRootIx].findNodes(arrayList, 6);
        for (int i = 0; i < arrayList.size(); i++) {
            RBBINode rBBINode = (RBBINode) arrayList.get(i);
            for (int i2 = 0; i2 < this.fDStates.size(); i2++) {
                RBBIStateDescriptor rBBIStateDescriptor = this.fDStates.get(i2);
                if (rBBIStateDescriptor.fPositions.contains(rBBINode)) {
                    if (rBBIStateDescriptor.fAccepting == 0) {
                        rBBIStateDescriptor.fAccepting = this.fLookAheadRuleMap[rBBINode.fVal];
                        if (rBBIStateDescriptor.fAccepting == 0) {
                            rBBIStateDescriptor.fAccepting = 1;
                        }
                    }
                    if (rBBIStateDescriptor.fAccepting == 1 && rBBINode.fVal != 0) {
                        rBBIStateDescriptor.fAccepting = this.fLookAheadRuleMap[rBBINode.fVal];
                    }
                }
            }
        }
    }

    void flagLookAheadStates() {
        ArrayList arrayList = new ArrayList();
        this.fRB.fTreeRoots[this.fRootIx].findNodes(arrayList, 4);
        for (int i = 0; i < arrayList.size(); i++) {
            RBBINode rBBINode = (RBBINode) arrayList.get(i);
            for (int i2 = 0; i2 < this.fDStates.size(); i2++) {
                RBBIStateDescriptor rBBIStateDescriptor = this.fDStates.get(i2);
                if (rBBIStateDescriptor.fPositions.contains(rBBINode)) {
                    rBBIStateDescriptor.fLookAhead = this.fLookAheadRuleMap[rBBINode.fVal];
                }
            }
        }
    }

    void flagTaggedStates() {
        ArrayList arrayList = new ArrayList();
        this.fRB.fTreeRoots[this.fRootIx].findNodes(arrayList, 5);
        for (int i = 0; i < arrayList.size(); i++) {
            RBBINode rBBINode = (RBBINode) arrayList.get(i);
            for (int i2 = 0; i2 < this.fDStates.size(); i2++) {
                RBBIStateDescriptor rBBIStateDescriptor = this.fDStates.get(i2);
                if (rBBIStateDescriptor.fPositions.contains(rBBINode)) {
                    rBBIStateDescriptor.fTagVals.add(Integer.valueOf(rBBINode.fVal));
                }
            }
        }
    }

    void mergeRuleStatusVals() {
        if (this.fRB.fRuleStatusVals.size() == 0) {
            this.fRB.fRuleStatusVals.add(1);
            this.fRB.fRuleStatusVals.add(0);
            this.fRB.fStatusSets.put(new TreeSet(), 0);
            TreeSet treeSet = new TreeSet();
            treeSet.add(0);
            this.fRB.fStatusSets.put(treeSet, 0);
        }
        for (int i = 0; i < this.fDStates.size(); i++) {
            RBBIStateDescriptor rBBIStateDescriptor = this.fDStates.get(i);
            SortedSet<Integer> sortedSet = rBBIStateDescriptor.fTagVals;
            Integer numValueOf = this.fRB.fStatusSets.get(sortedSet);
            if (numValueOf == null) {
                numValueOf = Integer.valueOf(this.fRB.fRuleStatusVals.size());
                this.fRB.fStatusSets.put(sortedSet, numValueOf);
                this.fRB.fRuleStatusVals.add(Integer.valueOf(sortedSet.size()));
                this.fRB.fRuleStatusVals.addAll(sortedSet);
            }
            rBBIStateDescriptor.fTagsIdx = numValueOf.intValue();
        }
    }

    void printPosSets(RBBINode rBBINode) {
        if (rBBINode == null) {
            return;
        }
        RBBINode.printNode(rBBINode);
        System.out.print("         Nullable:  " + rBBINode.fNullable);
        System.out.print("         firstpos:  ");
        printSet(rBBINode.fFirstPosSet);
        System.out.print("         lastpos:   ");
        printSet(rBBINode.fLastPosSet);
        System.out.print("         followpos: ");
        printSet(rBBINode.fFollowPos);
        printPosSets(rBBINode.fLeftChild);
        printPosSets(rBBINode.fRightChild);
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x0060, code lost:
    
        r11.first++;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    boolean findDuplCharClassFrom(com.ibm.icu.text.RBBIRuleBuilder.IntPair r11) {
        /*
            r10 = this;
            java.util.List<com.ibm.icu.text.RBBITableBuilder$RBBIStateDescriptor> r0 = r10.fDStates
            int r0 = r0.size()
            com.ibm.icu.text.RBBIRuleBuilder r1 = r10.fRB
            com.ibm.icu.text.RBBISetBuilder r1 = r1.fSetBuilder
            int r1 = r1.getNumCharCategories()
            r2 = 0
            r3 = 0
            r4 = 0
        L11:
            int r5 = r11.first
            int r6 = r1 + (-1)
            if (r5 >= r6) goto L66
            int r5 = r11.first
            com.ibm.icu.text.RBBIRuleBuilder r6 = r10.fRB
            com.ibm.icu.text.RBBISetBuilder r6 = r6.fSetBuilder
            int r6 = r6.getDictCategoriesStart()
            if (r5 >= r6) goto L2c
            com.ibm.icu.text.RBBIRuleBuilder r5 = r10.fRB
            com.ibm.icu.text.RBBISetBuilder r5 = r5.fSetBuilder
            int r5 = r5.getDictCategoriesStart()
            goto L2d
        L2c:
            r5 = r1
        L2d:
            int r6 = r11.first
            r7 = 1
        L30:
            int r6 = r6 + r7
            r11.second = r6
            int r6 = r11.second
            if (r6 >= r5) goto L60
            r6 = 0
        L38:
            if (r6 >= r0) goto L5a
            java.util.List<com.ibm.icu.text.RBBITableBuilder$RBBIStateDescriptor> r3 = r10.fDStates
            java.lang.Object r3 = r3.get(r6)
            com.ibm.icu.text.RBBITableBuilder$RBBIStateDescriptor r3 = (com.ibm.icu.text.RBBITableBuilder.RBBIStateDescriptor) r3
            int[] r4 = r3.fDtran
            int r8 = r11.first
            r4 = r4[r8]
            int[] r3 = r3.fDtran
            int r8 = r11.second
            r3 = r3[r8]
            if (r4 == r3) goto L54
            r9 = r4
            r4 = r3
            r3 = r9
            goto L5a
        L54:
            int r6 = r6 + 1
            r9 = r4
            r4 = r3
            r3 = r9
            goto L38
        L5a:
            if (r3 != r4) goto L5d
            return r7
        L5d:
            int r6 = r11.second
            goto L30
        L60:
            int r5 = r11.first
            int r5 = r5 + r7
            r11.first = r5
            goto L11
        L66:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.text.RBBITableBuilder.findDuplCharClassFrom(com.ibm.icu.text.RBBIRuleBuilder$IntPair):boolean");
    }

    void removeColumn(int i) {
        int size = this.fDStates.size();
        for (int i2 = 0; i2 < size; i2++) {
            RBBIStateDescriptor rBBIStateDescriptor = this.fDStates.get(i2);
            int[] iArrCopyOf = Arrays.copyOf(rBBIStateDescriptor.fDtran, rBBIStateDescriptor.fDtran.length - 1);
            System.arraycopy(rBBIStateDescriptor.fDtran, i + 1, iArrCopyOf, i, iArrCopyOf.length - i);
            rBBIStateDescriptor.fDtran = iArrCopyOf;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:33:0x006f, code lost:
    
        r11.first++;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    boolean findDuplicateState(com.ibm.icu.text.RBBIRuleBuilder.IntPair r11) {
        /*
            r10 = this;
            java.util.List<com.ibm.icu.text.RBBITableBuilder$RBBIStateDescriptor> r0 = r10.fDStates
            int r0 = r0.size()
            com.ibm.icu.text.RBBIRuleBuilder r1 = r10.fRB
            com.ibm.icu.text.RBBISetBuilder r1 = r1.fSetBuilder
            int r1 = r1.getNumCharCategories()
        Le:
            int r2 = r11.first
            int r3 = r0 + (-1)
            r4 = 0
            if (r2 >= r3) goto L75
            java.util.List<com.ibm.icu.text.RBBITableBuilder$RBBIStateDescriptor> r2 = r10.fDStates
            int r3 = r11.first
            java.lang.Object r2 = r2.get(r3)
            com.ibm.icu.text.RBBITableBuilder$RBBIStateDescriptor r2 = (com.ibm.icu.text.RBBITableBuilder.RBBIStateDescriptor) r2
            int r3 = r11.first
            r5 = 1
        L22:
            int r3 = r3 + r5
            r11.second = r3
            int r3 = r11.second
            if (r3 >= r0) goto L6f
            java.util.List<com.ibm.icu.text.RBBITableBuilder$RBBIStateDescriptor> r3 = r10.fDStates
            int r6 = r11.second
            java.lang.Object r3 = r3.get(r6)
            com.ibm.icu.text.RBBITableBuilder$RBBIStateDescriptor r3 = (com.ibm.icu.text.RBBITableBuilder.RBBIStateDescriptor) r3
            int r6 = r2.fAccepting
            int r7 = r3.fAccepting
            if (r6 != r7) goto L6c
            int r6 = r2.fLookAhead
            int r7 = r3.fLookAhead
            if (r6 != r7) goto L6c
            int r6 = r2.fTagsIdx
            int r7 = r3.fTagsIdx
            if (r6 == r7) goto L46
            goto L6c
        L46:
            r6 = 0
        L47:
            if (r6 >= r1) goto L68
            int[] r7 = r2.fDtran
            r7 = r7[r6]
            int[] r8 = r3.fDtran
            r8 = r8[r6]
            if (r7 == r8) goto L65
            int r9 = r11.first
            if (r7 == r9) goto L5b
            int r9 = r11.second
            if (r7 != r9) goto L63
        L5b:
            int r7 = r11.first
            if (r8 == r7) goto L65
            int r7 = r11.second
            if (r8 == r7) goto L65
        L63:
            r3 = 0
            goto L69
        L65:
            int r6 = r6 + 1
            goto L47
        L68:
            r3 = 1
        L69:
            if (r3 == 0) goto L6c
            return r5
        L6c:
            int r3 = r11.second
            goto L22
        L6f:
            int r2 = r11.first
            int r2 = r2 + r5
            r11.first = r2
            goto Le
        L75:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.text.RBBITableBuilder.findDuplicateState(com.ibm.icu.text.RBBIRuleBuilder$IntPair):boolean");
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x0051, code lost:
    
        r11.first++;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    boolean findDuplicateSafeState(com.ibm.icu.text.RBBIRuleBuilder.IntPair r11) {
        /*
            r10 = this;
            java.util.List<short[]> r0 = r10.fSafeTable
            int r0 = r0.size()
        L6:
            int r1 = r11.first
            int r2 = r0 + (-1)
            r3 = 0
            if (r1 >= r2) goto L57
            java.util.List<short[]> r1 = r10.fSafeTable
            int r2 = r11.first
            java.lang.Object r1 = r1.get(r2)
            short[] r1 = (short[]) r1
            int r2 = r11.first
            r4 = 1
        L1a:
            int r2 = r2 + r4
            r11.second = r2
            int r2 = r11.second
            if (r2 >= r0) goto L51
            java.util.List<short[]> r2 = r10.fSafeTable
            int r5 = r11.second
            java.lang.Object r2 = r2.get(r5)
            short[] r2 = (short[]) r2
            int r5 = r1.length
            r6 = 0
        L2d:
            if (r6 >= r5) goto L4a
            short r7 = r1[r6]
            short r8 = r2[r6]
            if (r7 == r8) goto L47
            int r9 = r11.first
            if (r7 == r9) goto L3d
            int r9 = r11.second
            if (r7 != r9) goto L45
        L3d:
            int r7 = r11.first
            if (r8 == r7) goto L47
            int r7 = r11.second
            if (r8 == r7) goto L47
        L45:
            r2 = 0
            goto L4b
        L47:
            int r6 = r6 + 1
            goto L2d
        L4a:
            r2 = 1
        L4b:
            if (r2 == 0) goto L4e
            return r4
        L4e:
            int r2 = r11.second
            goto L1a
        L51:
            int r1 = r11.first
            int r1 = r1 + r4
            r11.first = r1
            goto L6
        L57:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.text.RBBITableBuilder.findDuplicateSafeState(com.ibm.icu.text.RBBIRuleBuilder$IntPair):boolean");
    }

    void removeState(RBBIRuleBuilder.IntPair intPair) {
        int i = intPair.first;
        int i2 = intPair.second;
        this.fDStates.remove(i2);
        int size = this.fDStates.size();
        int numCharCategories = this.fRB.fSetBuilder.getNumCharCategories();
        for (int i3 = 0; i3 < size; i3++) {
            RBBIStateDescriptor rBBIStateDescriptor = this.fDStates.get(i3);
            for (int i4 = 0; i4 < numCharCategories; i4++) {
                int i5 = rBBIStateDescriptor.fDtran[i4];
                if (i5 == i2) {
                    i5 = i;
                } else if (i5 > i2) {
                    i5--;
                }
                rBBIStateDescriptor.fDtran[i4] = i5;
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    void removeSafeState(RBBIRuleBuilder.IntPair intPair) {
        int i = intPair.first;
        int i2 = intPair.second;
        this.fSafeTable.remove(i2);
        int size = this.fSafeTable.size();
        for (int i3 = 0; i3 < size; i3++) {
            short[] sArr = this.fSafeTable.get(i3);
            for (int i4 = 0; i4 < sArr.length; i4++) {
                short s = sArr[i4];
                if (s == i2) {
                    s = i;
                } else if (s > i2) {
                    s--;
                }
                sArr[i4] = (short) s;
            }
        }
    }

    int removeDuplicateStates() {
        int i = 0;
        RBBIRuleBuilder.IntPair intPair = new RBBIRuleBuilder.IntPair(3, 0);
        while (findDuplicateState(intPair)) {
            removeState(intPair);
            i++;
        }
        return i;
    }

    int getTableSize() {
        if (this.fRB.fTreeRoots[this.fRootIx] == null) {
            return 0;
        }
        int i = RBBIDataWrapper.RBBIStateTable.fHeaderSize;
        int size = this.fDStates.size();
        return (i + (size * (size <= 255 ? 1 : 2) * (this.fRB.fSetBuilder.getNumCharCategories() + 3)) + 7) & (-8);
    }

    RBBIDataWrapper.RBBIStateTable exportTable() {
        RBBIDataWrapper.RBBIStateTable rBBIStateTable = new RBBIDataWrapper.RBBIStateTable();
        if (this.fRB.fTreeRoots[this.fRootIx] == null) {
            return rBBIStateTable;
        }
        Assert.assrt(this.fRB.fSetBuilder.getNumCharCategories() < 32767 && this.fDStates.size() < 32767);
        rBBIStateTable.fNumStates = this.fDStates.size();
        rBBIStateTable.fDictCategoriesStart = this.fRB.fSetBuilder.getDictCategoriesStart();
        int i = this.fLASlotsInUse;
        rBBIStateTable.fLookAheadResultsSize = i == 1 ? 0 : i + 1;
        boolean z = rBBIStateTable.fNumStates <= 255;
        int numCharCategories = this.fRB.fSetBuilder.getNumCharCategories() + 3;
        if (z) {
            rBBIStateTable.fTable = new char[getTableSize() - RBBIDataWrapper.RBBIStateTable.fHeaderSize];
            rBBIStateTable.fRowLen = numCharCategories;
        } else {
            rBBIStateTable.fTable = new char[(getTableSize() - RBBIDataWrapper.RBBIStateTable.fHeaderSize) / 2];
            rBBIStateTable.fRowLen = numCharCategories * 2;
        }
        if (this.fRB.fLookAheadHardBreak) {
            rBBIStateTable.fFlags |= 1;
        }
        if (this.fRB.fSetBuilder.sawBOF()) {
            rBBIStateTable.fFlags |= 2;
        }
        if (z) {
            rBBIStateTable.fFlags |= 4;
        }
        int numCharCategories2 = this.fRB.fSetBuilder.getNumCharCategories();
        for (int i2 = 0; i2 < rBBIStateTable.fNumStates; i2++) {
            RBBIStateDescriptor rBBIStateDescriptor = this.fDStates.get(i2);
            int i3 = i2 * numCharCategories;
            if (z) {
                Assert.assrt(rBBIStateDescriptor.fAccepting >= 0 && rBBIStateDescriptor.fAccepting <= 255);
                Assert.assrt(rBBIStateDescriptor.fLookAhead >= 0 && rBBIStateDescriptor.fLookAhead <= 255);
            } else {
                Assert.assrt(rBBIStateDescriptor.fAccepting >= 0 && rBBIStateDescriptor.fAccepting <= 65535);
                Assert.assrt(rBBIStateDescriptor.fLookAhead >= 0 && rBBIStateDescriptor.fLookAhead <= 65535);
            }
            rBBIStateTable.fTable[i3 + 0] = (char) rBBIStateDescriptor.fAccepting;
            rBBIStateTable.fTable[i3 + 1] = (char) rBBIStateDescriptor.fLookAhead;
            rBBIStateTable.fTable[i3 + 2] = (char) rBBIStateDescriptor.fTagsIdx;
            for (int i4 = 0; i4 < numCharCategories2; i4++) {
                if (z) {
                    Assert.assrt(rBBIStateDescriptor.fDtran[i4] >= 0 && rBBIStateDescriptor.fDtran[i4] <= 255);
                }
                rBBIStateTable.fTable[i3 + 3 + i4] = (char) rBBIStateDescriptor.fDtran[i4];
            }
        }
        return rBBIStateTable;
    }

    void buildSafeReverseTable() {
        int i;
        StringBuilder sb = new StringBuilder();
        int numCharCategories = this.fRB.fSetBuilder.getNumCharCategories();
        int size = this.fDStates.size();
        for (int i2 = 0; i2 < numCharCategories; i2++) {
            for (int i3 = 0; i3 < numCharCategories; i3++) {
                int i4 = -1;
                int i5 = 0;
                for (int i6 = 1; i6 < size; i6++) {
                    i5 = this.fDStates.get(this.fDStates.get(i6).fDtran[i2]).fDtran[i3];
                    if (i4 >= 0) {
                        if (i4 != i5) {
                            break;
                        }
                    } else {
                        i4 = i5;
                    }
                }
                if (i4 == i5) {
                    sb.append((char) i2);
                    sb.append((char) i3);
                }
            }
        }
        this.fSafeTable = new ArrayList();
        int i7 = 0;
        while (true) {
            i = numCharCategories + 2;
            if (i7 >= i) {
                break;
            }
            this.fSafeTable.add(new short[numCharCategories]);
            i7++;
        }
        short[] sArr = this.fSafeTable.get(1);
        for (int i8 = 0; i8 < numCharCategories; i8++) {
            sArr[i8] = (short) (i8 + 2);
        }
        for (int i9 = 2; i9 < i; i9++) {
            System.arraycopy(sArr, 0, this.fSafeTable.get(i9), 0, sArr.length);
        }
        for (int i10 = 0; i10 < sb.length(); i10 += 2) {
            this.fSafeTable.get(sb.charAt(i10 + 1) + 2)[sb.charAt(i10)] = 0;
        }
        RBBIRuleBuilder.IntPair intPair = new RBBIRuleBuilder.IntPair(1, 0);
        while (findDuplicateSafeState(intPair)) {
            removeSafeState(intPair);
        }
    }

    int getSafeTableSize() {
        if (this.fSafeTable == null) {
            return 0;
        }
        int i = RBBIDataWrapper.RBBIStateTable.fHeaderSize;
        int size = this.fSafeTable.size();
        return (i + (size * (size <= 255 ? 1 : 2) * (this.fSafeTable.get(0).length + 3)) + 7) & (-8);
    }

    RBBIDataWrapper.RBBIStateTable exportSafeTable() {
        RBBIDataWrapper.RBBIStateTable rBBIStateTable = new RBBIDataWrapper.RBBIStateTable();
        rBBIStateTable.fNumStates = this.fSafeTable.size();
        boolean z = rBBIStateTable.fNumStates <= 255;
        int length = this.fSafeTable.get(0).length;
        int i = length + 3;
        int safeTableSize = getSafeTableSize() - RBBIDataWrapper.RBBIStateTable.fHeaderSize;
        if (z) {
            rBBIStateTable.fFlags |= 4;
            rBBIStateTable.fTable = new char[safeTableSize];
            rBBIStateTable.fRowLen = i;
        } else {
            rBBIStateTable.fTable = new char[safeTableSize / 2];
            rBBIStateTable.fRowLen = i * 2;
        }
        for (int i2 = 0; i2 < rBBIStateTable.fNumStates; i2++) {
            short[] sArr = this.fSafeTable.get(i2);
            int i3 = i2 * i;
            for (int i4 = 0; i4 < length; i4++) {
                if (z) {
                    Assert.assrt(sArr[i4] <= 255);
                }
                rBBIStateTable.fTable[i3 + 3 + i4] = (char) sArr[i4];
            }
        }
        return rBBIStateTable;
    }

    void printSet(Collection<RBBINode> collection) {
        Iterator<RBBINode> it2 = collection.iterator();
        while (it2.hasNext()) {
            RBBINode.printInt(it2.next().fSerialNum, 8);
        }
        System.out.println();
    }

    void printStates() {
        System.out.print("state |           i n p u t     s y m b o l s \n");
        System.out.print("      | Acc  LA    Tag");
        for (int i = 0; i < this.fRB.fSetBuilder.getNumCharCategories(); i++) {
            RBBINode.printInt(i, 4);
        }
        System.out.print("\n");
        System.out.print("      |---------------");
        for (int i2 = 0; i2 < this.fRB.fSetBuilder.getNumCharCategories(); i2++) {
            System.out.print("----");
        }
        System.out.print("\n");
        for (int i3 = 0; i3 < this.fDStates.size(); i3++) {
            RBBIStateDescriptor rBBIStateDescriptor = this.fDStates.get(i3);
            RBBINode.printInt(i3, 5);
            System.out.print(" | ");
            RBBINode.printInt(rBBIStateDescriptor.fAccepting, 3);
            RBBINode.printInt(rBBIStateDescriptor.fLookAhead, 4);
            RBBINode.printInt(rBBIStateDescriptor.fTagsIdx, 6);
            System.out.print(" ");
            for (int i4 = 0; i4 < this.fRB.fSetBuilder.getNumCharCategories(); i4++) {
                RBBINode.printInt(rBBIStateDescriptor.fDtran[i4], 4);
            }
            System.out.print("\n");
        }
        System.out.print("\n\n");
    }

    void printReverseTable() {
        System.out.printf("    Safe Reverse Table \n", new Object[0]);
        List<short[]> list = this.fSafeTable;
        if (list == null) {
            System.out.printf("   --- nullptr ---\n", new Object[0]);
            return;
        }
        int length = list.get(0).length;
        System.out.printf("state |           i n p u t     s y m b o l s \n", new Object[0]);
        System.out.printf("      | Acc  LA    Tag", new Object[0]);
        for (int i = 0; i < length; i++) {
            System.out.printf(" %2d", Integer.valueOf(i));
        }
        System.out.printf("\n", new Object[0]);
        System.out.printf("      |---------------", new Object[0]);
        for (int i2 = 0; i2 < length; i2++) {
            System.out.printf("---", new Object[0]);
        }
        System.out.printf("\n", new Object[0]);
        for (int i3 = 0; i3 < this.fSafeTable.size(); i3++) {
            short[] sArr = this.fSafeTable.get(i3);
            System.out.printf("  %3d | ", Integer.valueOf(i3));
            System.out.printf("%3d %3d %5d ", 0, 0, 0);
            for (int i4 = 0; i4 < length; i4++) {
                System.out.printf(" %2d", Short.valueOf(sArr[i4]));
            }
            System.out.printf("\n", new Object[0]);
        }
        System.out.printf("\n\n", new Object[0]);
    }

    void printRuleStatusTable() {
        List<Integer> list = this.fRB.fRuleStatusVals;
        System.out.print("index |  tags \n");
        System.out.print("-------------------\n");
        int i = 0;
        while (i < list.size()) {
            int iIntValue = list.get(i).intValue() + i + 1;
            RBBINode.printInt(i, 7);
            while (true) {
                i++;
                if (i < iIntValue) {
                    RBBINode.printInt(list.get(i).intValue(), 7);
                }
            }
            System.out.print("\n");
            i = iIntValue;
        }
        System.out.print("\n\n");
    }
}
