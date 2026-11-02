package com.ibm.icu.text;

import com.app.orsozoxi.Views.CustomCopticTextView;
import com.ibm.icu.impl.Assert;
import com.ibm.icu.impl.locale.LanguageTag;
import com.ibm.icu.text.RBBIRuleBuilder;
import com.ibm.icu.util.CodePointTrie;
import com.ibm.icu.util.MutableCodePointTrie;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
class RBBISetBuilder {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int MAX_CHAR_CATEGORIES_FOR_8BITS_TRIE = 255;
    int fDictCategoriesStart;
    CodePointTrie fFrozenTrie;
    int fGroupCount;
    RBBIRuleBuilder fRB;
    RangeDescriptor fRangeList;
    boolean fSawBOF;
    MutableCodePointTrie fTrie;

    static class RangeDescriptor {
        int fEndChar;
        boolean fFirstInGroup;
        boolean fIncludesDict;
        List<RBBINode> fIncludesSets;
        RangeDescriptor fNext;
        int fNum;
        int fStartChar;

        RangeDescriptor() {
            this.fStartChar = 0;
            this.fEndChar = 0;
            this.fNum = 0;
            this.fIncludesDict = false;
            this.fFirstInGroup = false;
            this.fIncludesSets = new ArrayList();
        }

        RangeDescriptor(RangeDescriptor rangeDescriptor) {
            this.fStartChar = 0;
            this.fEndChar = 0;
            this.fNum = 0;
            this.fIncludesDict = false;
            this.fFirstInGroup = false;
            this.fStartChar = rangeDescriptor.fStartChar;
            this.fEndChar = rangeDescriptor.fEndChar;
            this.fNum = rangeDescriptor.fNum;
            this.fIncludesDict = rangeDescriptor.fIncludesDict;
            this.fFirstInGroup = rangeDescriptor.fFirstInGroup;
            this.fIncludesSets = new ArrayList(rangeDescriptor.fIncludesSets);
        }

        void split(int i) {
            Assert.assrt(i > this.fStartChar && i <= this.fEndChar);
            RangeDescriptor rangeDescriptor = new RangeDescriptor(this);
            rangeDescriptor.fStartChar = i;
            this.fEndChar = i - 1;
            rangeDescriptor.fNext = this.fNext;
            this.fNext = rangeDescriptor;
        }

        boolean isDictionaryRange() {
            RBBINode rBBINode;
            for (int i = 0; i < this.fIncludesSets.size(); i++) {
                RBBINode rBBINode2 = this.fIncludesSets.get(i).fParent;
                if (((rBBINode2 == null || (rBBINode = rBBINode2.fParent) == null || rBBINode.fType != 2) ? "" : rBBINode.fText).equals("dictionary")) {
                    return true;
                }
            }
            return false;
        }
    }

    RBBISetBuilder(RBBIRuleBuilder rBBIRuleBuilder) {
        this.fRB = rBBIRuleBuilder;
    }

    void buildRanges() {
        if (this.fRB.fDebugEnv != null && this.fRB.fDebugEnv.indexOf("usets") >= 0) {
            printSets();
        }
        RangeDescriptor rangeDescriptor = new RangeDescriptor();
        this.fRangeList = rangeDescriptor;
        int i = 0;
        rangeDescriptor.fStartChar = 0;
        this.fRangeList.fEndChar = 1114111;
        for (RBBINode rBBINode : this.fRB.fUSetNodes) {
            UnicodeSet unicodeSet = rBBINode.fInputSet;
            int rangeCount = unicodeSet.getRangeCount();
            RangeDescriptor rangeDescriptor2 = this.fRangeList;
            int i2 = 0;
            while (i2 < rangeCount) {
                int rangeStart = unicodeSet.getRangeStart(i2);
                int rangeEnd = unicodeSet.getRangeEnd(i2);
                while (rangeDescriptor2.fEndChar < rangeStart) {
                    rangeDescriptor2 = rangeDescriptor2.fNext;
                }
                if (rangeDescriptor2.fStartChar < rangeStart) {
                    rangeDescriptor2.split(rangeStart);
                } else {
                    if (rangeDescriptor2.fEndChar > rangeEnd) {
                        rangeDescriptor2.split(rangeEnd + 1);
                    }
                    if (rangeDescriptor2.fIncludesSets.indexOf(rBBINode) == -1) {
                        rangeDescriptor2.fIncludesSets.add(rBBINode);
                    }
                    if (rangeEnd == rangeDescriptor2.fEndChar) {
                        i2++;
                    }
                    rangeDescriptor2 = rangeDescriptor2.fNext;
                }
            }
        }
        if (this.fRB.fDebugEnv != null && this.fRB.fDebugEnv.indexOf("range") >= 0) {
            printRanges();
        }
        for (RangeDescriptor rangeDescriptor3 = this.fRangeList; rangeDescriptor3 != null; rangeDescriptor3 = rangeDescriptor3.fNext) {
            RangeDescriptor rangeDescriptor4 = this.fRangeList;
            while (true) {
                if (rangeDescriptor4 == rangeDescriptor3) {
                    break;
                }
                if (!rangeDescriptor3.fIncludesSets.equals(rangeDescriptor4.fIncludesSets)) {
                    rangeDescriptor4 = rangeDescriptor4.fNext;
                } else {
                    rangeDescriptor3.fNum = rangeDescriptor4.fNum;
                    rangeDescriptor3.fIncludesDict = rangeDescriptor4.fIncludesDict;
                    break;
                }
            }
            if (rangeDescriptor3.fNum == 0) {
                rangeDescriptor3.fFirstInGroup = true;
                if (rangeDescriptor3.isDictionaryRange()) {
                    i++;
                    rangeDescriptor3.fNum = i;
                    rangeDescriptor3.fIncludesDict = true;
                } else {
                    int i3 = this.fGroupCount + 1;
                    this.fGroupCount = i3;
                    rangeDescriptor3.fNum = i3 + 2;
                    addValToSets(rangeDescriptor3.fIncludesSets, this.fGroupCount + 2);
                }
            }
        }
        this.fDictCategoriesStart = this.fGroupCount + 3;
        for (RangeDescriptor rangeDescriptor5 = this.fRangeList; rangeDescriptor5 != null; rangeDescriptor5 = rangeDescriptor5.fNext) {
            if (rangeDescriptor5.fIncludesDict) {
                rangeDescriptor5.fNum += this.fDictCategoriesStart - 1;
                if (rangeDescriptor5.fFirstInGroup) {
                    addValToSets(rangeDescriptor5.fIncludesSets, rangeDescriptor5.fNum);
                }
            }
        }
        this.fGroupCount += i;
        for (RBBINode rBBINode2 : this.fRB.fUSetNodes) {
            UnicodeSet unicodeSet2 = rBBINode2.fInputSet;
            if (unicodeSet2.contains("eof")) {
                addValToSet(rBBINode2, 1);
            }
            if (unicodeSet2.contains("bof")) {
                addValToSet(rBBINode2, 2);
                this.fSawBOF = true;
            }
        }
        if (this.fRB.fDebugEnv != null && this.fRB.fDebugEnv.indexOf("rgroup") >= 0) {
            printRangeGroups();
        }
        if (this.fRB.fDebugEnv == null || this.fRB.fDebugEnv.indexOf("esets") < 0) {
            return;
        }
        printSets();
    }

    void buildTrie() {
        this.fTrie = new MutableCodePointTrie(0, 0);
        for (RangeDescriptor rangeDescriptor = this.fRangeList; rangeDescriptor != null; rangeDescriptor = rangeDescriptor.fNext) {
            this.fTrie.setRange(rangeDescriptor.fStartChar, rangeDescriptor.fEndChar, rangeDescriptor.fNum);
        }
    }

    void mergeCategories(RBBIRuleBuilder.IntPair intPair) {
        for (RangeDescriptor rangeDescriptor = this.fRangeList; rangeDescriptor != null; rangeDescriptor = rangeDescriptor.fNext) {
            int i = rangeDescriptor.fNum;
            if (i == intPair.second) {
                rangeDescriptor.fNum = intPair.first;
            } else if (i > intPair.second) {
                rangeDescriptor.fNum--;
            }
        }
        this.fGroupCount--;
        int i2 = intPair.second;
        int i3 = this.fDictCategoriesStart;
        if (i2 <= i3) {
            this.fDictCategoriesStart = i3 - 1;
        }
    }

    void freezeTrieIfNotYet() {
        if (this.fFrozenTrie == null) {
            this.fFrozenTrie = this.fTrie.buildImmutable(CodePointTrie.Type.FAST, getNumCharCategories() <= 255 ? CodePointTrie.ValueWidth.BITS_8 : CodePointTrie.ValueWidth.BITS_16);
            this.fTrie = null;
        }
    }

    int getTrieSize() {
        freezeTrieIfNotYet();
        return this.fFrozenTrie.toBinary(new ByteArrayOutputStream());
    }

    void serializeTrie(OutputStream outputStream) throws IOException {
        freezeTrieIfNotYet();
        this.fFrozenTrie.toBinary(outputStream);
    }

    void addValToSets(List<RBBINode> list, int i) {
        Iterator<RBBINode> it2 = list.iterator();
        while (it2.hasNext()) {
            addValToSet(it2.next(), i);
        }
    }

    void addValToSet(RBBINode rBBINode, int i) {
        RBBINode rBBINode2 = new RBBINode(3);
        rBBINode2.fVal = i;
        if (rBBINode.fLeftChild == null) {
            rBBINode.fLeftChild = rBBINode2;
            rBBINode2.fParent = rBBINode;
            return;
        }
        RBBINode rBBINode3 = new RBBINode(9);
        rBBINode3.fLeftChild = rBBINode.fLeftChild;
        rBBINode3.fRightChild = rBBINode2;
        rBBINode3.fLeftChild.fParent = rBBINode3;
        rBBINode3.fRightChild.fParent = rBBINode3;
        rBBINode.fLeftChild = rBBINode3;
        rBBINode3.fParent = rBBINode;
    }

    int getNumCharCategories() {
        return this.fGroupCount + 3;
    }

    int getDictCategoriesStart() {
        return this.fDictCategoriesStart;
    }

    boolean sawBOF() {
        return this.fSawBOF;
    }

    int getFirstChar(int i) {
        for (RangeDescriptor rangeDescriptor = this.fRangeList; rangeDescriptor != null; rangeDescriptor = rangeDescriptor.fNext) {
            if (rangeDescriptor.fNum == i) {
                return rangeDescriptor.fStartChar;
            }
        }
        return -1;
    }

    void printRanges() {
        RBBINode rBBINode;
        System.out.print("\n\n Nonoverlapping Ranges ...\n");
        for (RangeDescriptor rangeDescriptor = this.fRangeList; rangeDescriptor != null; rangeDescriptor = rangeDescriptor.fNext) {
            System.out.printf("%04x-%04x ", Integer.valueOf(rangeDescriptor.fStartChar), Integer.valueOf(rangeDescriptor.fEndChar));
            for (int i = 0; i < rangeDescriptor.fIncludesSets.size(); i++) {
                RBBINode rBBINode2 = rangeDescriptor.fIncludesSets.get(i).fParent;
                System.out.print((rBBINode2 == null || (rBBINode = rBBINode2.fParent) == null || rBBINode.fType != 2) ? "anon" : rBBINode.fText);
                System.out.print(CustomCopticTextView.TWO_CHINESE_BLANK);
            }
            System.out.println("");
        }
    }

    void printRangeGroups() {
        RBBINode rBBINode;
        System.out.print("\nRanges grouped by Unicode Set Membership...\n");
        for (RangeDescriptor rangeDescriptor = this.fRangeList; rangeDescriptor != null; rangeDescriptor = rangeDescriptor.fNext) {
            if (rangeDescriptor.fFirstInGroup) {
                int i = rangeDescriptor.fNum;
                if (i < 10) {
                    System.out.print(" ");
                }
                System.out.print(i + " ");
                if (i >= this.fDictCategoriesStart) {
                    System.out.print(" <DICT> ");
                }
                for (int i2 = 0; i2 < rangeDescriptor.fIncludesSets.size(); i2++) {
                    RBBINode rBBINode2 = rangeDescriptor.fIncludesSets.get(i2).fParent;
                    System.out.print((rBBINode2 == null || (rBBINode = rBBINode2.fParent) == null || rBBINode.fType != 2) ? "anon" : rBBINode.fText);
                    System.out.print(" ");
                }
                int i3 = 0;
                for (RangeDescriptor rangeDescriptor2 = rangeDescriptor; rangeDescriptor2 != null; rangeDescriptor2 = rangeDescriptor2.fNext) {
                    if (rangeDescriptor2.fNum == rangeDescriptor.fNum) {
                        int i4 = i3 + 1;
                        if (i3 % 5 == 0) {
                            System.out.print("\n    ");
                        }
                        RBBINode.printHex(rangeDescriptor2.fStartChar, -1);
                        System.out.print(LanguageTag.SEP);
                        RBBINode.printHex(rangeDescriptor2.fEndChar, 0);
                        i3 = i4;
                    }
                }
                System.out.print("\n");
            }
        }
        System.out.print("\n");
    }

    void printSets() {
        RBBINode rBBINode;
        System.out.print("\n\nUnicode Sets List\n------------------\n");
        for (int i = 0; i < this.fRB.fUSetNodes.size(); i++) {
            RBBINode rBBINode2 = this.fRB.fUSetNodes.get(i);
            RBBINode.printInt(2, i);
            RBBINode rBBINode3 = rBBINode2.fParent;
            String str = (rBBINode3 == null || (rBBINode = rBBINode3.fParent) == null || rBBINode.fType != 2) ? "anonymous" : rBBINode.fText;
            System.out.print(CustomCopticTextView.TWO_CHINESE_BLANK + str);
            System.out.print("   ");
            System.out.print(rBBINode2.fText);
            System.out.print("\n");
            if (rBBINode2.fLeftChild != null) {
                rBBINode2.fLeftChild.printTree(true);
            }
        }
        System.out.print("\n");
    }
}
