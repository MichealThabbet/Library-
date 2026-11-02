package com.ibm.icu.impl;

import com.ibm.icu.text.SymbolTable;
import com.ibm.icu.text.UTF16;
import java.text.ParsePosition;

/* loaded from: classes2.dex */
public class RuleCharacterIterator {
    public static final int DONE = -1;
    public static final int PARSE_ESCAPES = 2;
    public static final int PARSE_VARIABLES = 1;
    public static final int SKIP_WHITESPACE = 4;
    private char[] buf;
    private int bufPos;
    private boolean isEscaped;
    private ParsePosition pos;
    private SymbolTable sym;
    private String text;

    public RuleCharacterIterator(String str, SymbolTable symbolTable, ParsePosition parsePosition) {
        if (str == null || parsePosition.getIndex() > str.length()) {
            throw new IllegalArgumentException();
        }
        this.text = str;
        this.sym = symbolTable;
        this.pos = parsePosition;
        this.buf = null;
    }

    public boolean atEnd() {
        return this.buf == null && this.pos.getIndex() == this.text.length();
    }

    public int next(int i) {
        int i_current;
        SymbolTable symbolTable;
        this.isEscaped = false;
        while (true) {
            i_current = _current();
            _advance(UTF16.getCharCount(i_current));
            if (i_current == 36 && this.buf == null && (i & 1) != 0 && (symbolTable = this.sym) != null) {
                String str = this.text;
                String reference = symbolTable.parseReference(str, this.pos, str.length());
                if (reference == null) {
                    return i_current;
                }
                this.bufPos = 0;
                char[] cArrLookup = this.sym.lookup(reference);
                this.buf = cArrLookup;
                if (cArrLookup == null) {
                    throw new IllegalArgumentException("Undefined variable: " + reference);
                }
                if (cArrLookup.length == 0) {
                    this.buf = null;
                }
            } else if ((i & 4) == 0 || !PatternProps.isWhiteSpace(i_current)) {
                break;
            }
        }
        if (i_current != 92 || (i & 2) == 0) {
            return i_current;
        }
        int[] iArr = {0};
        int iUnescapeAt = Utility.unescapeAt(lookahead(), iArr);
        jumpahead(iArr[0]);
        this.isEscaped = true;
        if (iUnescapeAt >= 0) {
            return iUnescapeAt;
        }
        throw new IllegalArgumentException("Invalid escape");
    }

    public boolean isEscaped() {
        return this.isEscaped;
    }

    public boolean inVariable() {
        return this.buf != null;
    }

    public Object getPos(Object obj) {
        if (obj == null) {
            return new Object[]{this.buf, new int[]{this.pos.getIndex(), this.bufPos}};
        }
        Object[] objArr = (Object[]) obj;
        objArr[0] = this.buf;
        int[] iArr = (int[]) objArr[1];
        iArr[0] = this.pos.getIndex();
        iArr[1] = this.bufPos;
        return obj;
    }

    public void setPos(Object obj) {
        Object[] objArr = (Object[]) obj;
        this.buf = (char[]) objArr[0];
        int[] iArr = (int[]) objArr[1];
        this.pos.setIndex(iArr[0]);
        this.bufPos = iArr[1];
    }

    public void skipIgnored(int i) {
        if ((i & 4) == 0) {
            return;
        }
        while (true) {
            int i_current = _current();
            if (!PatternProps.isWhiteSpace(i_current)) {
                return;
            } else {
                _advance(UTF16.getCharCount(i_current));
            }
        }
    }

    public String lookahead() {
        if (this.buf != null) {
            char[] cArr = this.buf;
            int i = this.bufPos;
            return new String(cArr, i, cArr.length - i);
        }
        return this.text.substring(this.pos.getIndex());
    }

    public void jumpahead(int i) {
        if (i < 0) {
            throw new IllegalArgumentException();
        }
        char[] cArr = this.buf;
        if (cArr != null) {
            int i2 = this.bufPos + i;
            this.bufPos = i2;
            if (i2 > cArr.length) {
                throw new IllegalArgumentException();
            }
            if (i2 == cArr.length) {
                this.buf = null;
                return;
            }
            return;
        }
        int index = this.pos.getIndex() + i;
        this.pos.setIndex(index);
        if (index > this.text.length()) {
            throw new IllegalArgumentException();
        }
    }

    public String toString() {
        int index = this.pos.getIndex();
        return this.text.substring(0, index) + '|' + this.text.substring(index);
    }

    private int _current() {
        char[] cArr = this.buf;
        if (cArr != null) {
            return UTF16.charAt(cArr, 0, cArr.length, this.bufPos);
        }
        int index = this.pos.getIndex();
        if (index < this.text.length()) {
            return UTF16.charAt(this.text, index);
        }
        return -1;
    }

    private void _advance(int i) {
        char[] cArr = this.buf;
        if (cArr != null) {
            int i2 = this.bufPos + i;
            this.bufPos = i2;
            if (i2 == cArr.length) {
                this.buf = null;
                return;
            }
            return;
        }
        ParsePosition parsePosition = this.pos;
        parsePosition.setIndex(parsePosition.getIndex() + i);
        if (this.pos.getIndex() > this.text.length()) {
            this.pos.setIndex(this.text.length());
        }
    }
}
