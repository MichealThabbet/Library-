package com.ibm.icu.text;

import com.huawei.hms.push.constant.RemoteMessageConst;
import com.ibm.icu.impl.Assert;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.http.message.TokenParser;

/* loaded from: classes2.dex */
class RBBINode {
    static final int endMark = 6;
    static int gLastSerial = 0;
    static final int leafChar = 3;
    static final int lookAhead = 4;
    static final int nodeTypeLimit = 16;
    static final String[] nodeTypeNames = {"setRef", "uset", "varRef", "leafChar", "lookAhead", RemoteMessageConst.Notification.TAG, "endMark", "opStart", "opCat", "opOr", "opStar", "opPlus", "opQuestion", "opBreak", "opReverse", "opLParen"};
    static final int opBreak = 13;
    static final int opCat = 8;
    static final int opLParen = 15;
    static final int opOr = 9;
    static final int opPlus = 11;
    static final int opQuestion = 12;
    static final int opReverse = 14;
    static final int opStar = 10;
    static final int opStart = 7;
    static final int precLParen = 2;
    static final int precOpCat = 4;
    static final int precOpOr = 3;
    static final int precStart = 1;
    static final int precZero = 0;
    static final int setRef = 0;
    static final int tag = 5;
    static final int uset = 1;
    static final int varRef = 2;
    boolean fChainIn;
    int fFirstPos;
    Set<RBBINode> fFirstPosSet;
    Set<RBBINode> fFollowPos;
    UnicodeSet fInputSet;
    int fLastPos;
    Set<RBBINode> fLastPosSet;
    RBBINode fLeftChild;
    boolean fLookAheadEnd;
    boolean fNullable;
    RBBINode fParent;
    int fPrecedence;
    RBBINode fRightChild;
    boolean fRuleRoot;
    int fSerialNum;
    String fText;
    int fType;
    int fVal;

    RBBINode(int i) {
        this.fPrecedence = 0;
        Assert.assrt(i < 16);
        int i2 = gLastSerial + 1;
        gLastSerial = i2;
        this.fSerialNum = i2;
        this.fType = i;
        this.fFirstPosSet = new HashSet();
        this.fLastPosSet = new HashSet();
        this.fFollowPos = new HashSet();
        if (i == 8) {
            this.fPrecedence = 4;
            return;
        }
        if (i == 9) {
            this.fPrecedence = 3;
            return;
        }
        if (i == 7) {
            this.fPrecedence = 1;
        } else if (i == 15) {
            this.fPrecedence = 2;
        } else {
            this.fPrecedence = 0;
        }
    }

    RBBINode(RBBINode rBBINode) {
        this.fPrecedence = 0;
        int i = gLastSerial + 1;
        gLastSerial = i;
        this.fSerialNum = i;
        this.fType = rBBINode.fType;
        this.fInputSet = rBBINode.fInputSet;
        this.fPrecedence = rBBINode.fPrecedence;
        this.fText = rBBINode.fText;
        this.fFirstPos = rBBINode.fFirstPos;
        this.fLastPos = rBBINode.fLastPos;
        this.fNullable = rBBINode.fNullable;
        this.fVal = rBBINode.fVal;
        this.fRuleRoot = false;
        this.fChainIn = rBBINode.fChainIn;
        this.fFirstPosSet = new HashSet(rBBINode.fFirstPosSet);
        this.fLastPosSet = new HashSet(rBBINode.fLastPosSet);
        this.fFollowPos = new HashSet(rBBINode.fFollowPos);
    }

    RBBINode cloneTree() {
        int i = this.fType;
        if (i == 2) {
            return this.fLeftChild.cloneTree();
        }
        if (i == 1) {
            return this;
        }
        RBBINode rBBINode = new RBBINode(this);
        RBBINode rBBINode2 = this.fLeftChild;
        if (rBBINode2 != null) {
            RBBINode rBBINodeCloneTree = rBBINode2.cloneTree();
            rBBINode.fLeftChild = rBBINodeCloneTree;
            rBBINodeCloneTree.fParent = rBBINode;
        }
        RBBINode rBBINode3 = this.fRightChild;
        if (rBBINode3 == null) {
            return rBBINode;
        }
        RBBINode rBBINodeCloneTree2 = rBBINode3.cloneTree();
        rBBINode.fRightChild = rBBINodeCloneTree2;
        rBBINodeCloneTree2.fParent = rBBINode;
        return rBBINode;
    }

    RBBINode flattenVariables() {
        if (this.fType == 2) {
            RBBINode rBBINodeCloneTree = this.fLeftChild.cloneTree();
            rBBINodeCloneTree.fRuleRoot = this.fRuleRoot;
            rBBINodeCloneTree.fChainIn = this.fChainIn;
            return rBBINodeCloneTree;
        }
        RBBINode rBBINode = this.fLeftChild;
        if (rBBINode != null) {
            RBBINode rBBINodeFlattenVariables = rBBINode.flattenVariables();
            this.fLeftChild = rBBINodeFlattenVariables;
            rBBINodeFlattenVariables.fParent = this;
        }
        RBBINode rBBINode2 = this.fRightChild;
        if (rBBINode2 != null) {
            RBBINode rBBINodeFlattenVariables2 = rBBINode2.flattenVariables();
            this.fRightChild = rBBINodeFlattenVariables2;
            rBBINodeFlattenVariables2.fParent = this;
        }
        return this;
    }

    void flattenSets() {
        Assert.assrt(this.fType != 0);
        RBBINode rBBINode = this.fLeftChild;
        if (rBBINode != null) {
            if (rBBINode.fType == 0) {
                RBBINode rBBINodeCloneTree = rBBINode.fLeftChild.fLeftChild.cloneTree();
                this.fLeftChild = rBBINodeCloneTree;
                rBBINodeCloneTree.fParent = this;
            } else {
                rBBINode.flattenSets();
            }
        }
        RBBINode rBBINode2 = this.fRightChild;
        if (rBBINode2 != null) {
            if (rBBINode2.fType == 0) {
                RBBINode rBBINodeCloneTree2 = rBBINode2.fLeftChild.fLeftChild.cloneTree();
                this.fRightChild = rBBINodeCloneTree2;
                rBBINodeCloneTree2.fParent = this;
                return;
            }
            rBBINode2.flattenSets();
        }
    }

    void findNodes(List<RBBINode> list, int i) {
        if (this.fType == i) {
            list.add(this);
        }
        RBBINode rBBINode = this.fLeftChild;
        if (rBBINode != null) {
            rBBINode.findNodes(list, i);
        }
        RBBINode rBBINode2 = this.fRightChild;
        if (rBBINode2 != null) {
            rBBINode2.findNodes(list, i);
        }
    }

    static void printNode(RBBINode rBBINode) {
        if (rBBINode == null) {
            System.out.print(" -- null --\n");
        } else {
            printInt(rBBINode.fSerialNum, 10);
            printString(nodeTypeNames[rBBINode.fType], 11);
            RBBINode rBBINode2 = rBBINode.fParent;
            printInt(rBBINode2 == null ? 0 : rBBINode2.fSerialNum, 11);
            RBBINode rBBINode3 = rBBINode.fLeftChild;
            printInt(rBBINode3 == null ? 0 : rBBINode3.fSerialNum, 11);
            RBBINode rBBINode4 = rBBINode.fRightChild;
            printInt(rBBINode4 != null ? rBBINode4.fSerialNum : 0, 12);
            printInt(rBBINode.fFirstPos, 12);
            printInt(rBBINode.fVal, 7);
            if (rBBINode.fType == 2) {
                System.out.print(" " + rBBINode.fText);
            }
        }
        System.out.println("");
    }

    static void printString(String str, int i) {
        for (int i2 = i; i2 < 0; i2++) {
            System.out.print(TokenParser.SP);
        }
        for (int length = str.length(); length < i; length++) {
            System.out.print(TokenParser.SP);
        }
        System.out.print(str);
    }

    static void printInt(int i, int i2) {
        String string = Integer.toString(i);
        printString(string, Math.max(i2, string.length() + 1));
    }

    static void printHex(int i, int i2) {
        String string = Integer.toString(i, 16);
        printString("00000".substring(0, Math.max(0, 5 - string.length())) + string, i2);
    }

    void printTree(boolean z) {
        if (z) {
            System.out.println("-------------------------------------------------------------------");
            System.out.println("    Serial       type     Parent  LeftChild  RightChild    position  value");
        }
        printNode(this);
        if (this.fType != 2) {
            RBBINode rBBINode = this.fLeftChild;
            if (rBBINode != null) {
                rBBINode.printTree(false);
            }
            RBBINode rBBINode2 = this.fRightChild;
            if (rBBINode2 != null) {
                rBBINode2.printTree(false);
            }
        }
    }
}
