package com.ibm.icu.impl;

import com.ibm.icu.impl.Trie;
import com.ibm.icu.text.UTF16;
import it.piapostolos.android.pinotes.R2;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import org.apache.http.message.TokenParser;

/* loaded from: classes2.dex */
public class IntTrie extends Trie {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private int[] m_data_;
    private int m_initialValue_;

    @Override // com.ibm.icu.impl.Trie
    public int hashCode() {
        return 42;
    }

    public IntTrie(ByteBuffer byteBuffer, Trie.DataManipulate dataManipulate) throws IOException {
        super(byteBuffer, dataManipulate);
        if (!isIntTrie()) {
            throw new IllegalArgumentException("Data given does not belong to a int trie.");
        }
    }

    public IntTrie(int i, int i2, Trie.DataManipulate dataManipulate) {
        super(new char[R2.dimen.toolbar_elevation], 512, dataManipulate);
        int i3 = i2 != i ? 288 : 256;
        this.m_data_ = new int[i3];
        this.m_dataLength_ = i3;
        this.m_initialValue_ = i;
        for (int i4 = 0; i4 < 256; i4++) {
            this.m_data_[i4] = i;
        }
        if (i2 != i) {
            char c = (char) 64;
            for (int i5 = R2.dimen.item_touch_helper_swipe_escape_velocity; i5 < 1760; i5++) {
                this.m_index_[i5] = c;
            }
            for (int i6 = 256; i6 < 288; i6++) {
                this.m_data_[i6] = i2;
            }
        }
    }

    public final int getCodePointValue(int i) {
        if (i >= 0 && i < 55296) {
            return this.m_data_[(this.m_index_[i >> 5] << 2) + (i & 31)];
        }
        int codePointOffset = getCodePointOffset(i);
        return codePointOffset >= 0 ? this.m_data_[codePointOffset] : this.m_initialValue_;
    }

    public final int getLeadValue(char c) {
        return this.m_data_[getLeadOffset(c)];
    }

    public final int getBMPValue(char c) {
        return this.m_data_[getBMPOffset(c)];
    }

    public final int getSurrogateValue(char c, char c2) {
        if (!UTF16.isLeadSurrogate(c) || !UTF16.isTrailSurrogate(c2)) {
            throw new IllegalArgumentException("Argument characters do not form a supplementary character");
        }
        int surrogateOffset = getSurrogateOffset(c, c2);
        if (surrogateOffset > 0) {
            return this.m_data_[surrogateOffset];
        }
        return this.m_initialValue_;
    }

    public final int getTrailValue(int i, char c) {
        if (this.m_dataManipulate_ == null) {
            throw new NullPointerException("The field DataManipulate in this Trie is null");
        }
        int foldingOffset = this.m_dataManipulate_.getFoldingOffset(i);
        if (foldingOffset > 0) {
            return this.m_data_[getRawOffset(foldingOffset, (char) (c & 1023))];
        }
        return this.m_initialValue_;
    }

    public final int getLatin1LinearValue(char c) {
        return this.m_data_[c + TokenParser.SP];
    }

    @Override // com.ibm.icu.impl.Trie
    public boolean equals(Object obj) {
        if (super.equals(obj) && (obj instanceof IntTrie)) {
            IntTrie intTrie = (IntTrie) obj;
            if (this.m_initialValue_ == intTrie.m_initialValue_ && Arrays.equals(this.m_data_, intTrie.m_data_)) {
                return true;
            }
        }
        return false;
    }

    @Override // com.ibm.icu.impl.Trie
    protected final void unserialize(ByteBuffer byteBuffer) {
        super.unserialize(byteBuffer);
        int[] ints = ICUBinary.getInts(byteBuffer, this.m_dataLength_, 0);
        this.m_data_ = ints;
        this.m_initialValue_ = ints[0];
    }

    @Override // com.ibm.icu.impl.Trie
    protected final int getSurrogateOffset(char c, char c2) {
        if (this.m_dataManipulate_ == null) {
            throw new NullPointerException("The field DataManipulate in this Trie is null");
        }
        int foldingOffset = this.m_dataManipulate_.getFoldingOffset(getLeadValue(c));
        if (foldingOffset > 0) {
            return getRawOffset(foldingOffset, (char) (c2 & 1023));
        }
        return -1;
    }

    @Override // com.ibm.icu.impl.Trie
    protected final int getValue(int i) {
        return this.m_data_[i];
    }

    @Override // com.ibm.icu.impl.Trie
    protected final int getInitialValue() {
        return this.m_initialValue_;
    }

    IntTrie(char[] cArr, int[] iArr, int i, int i2, Trie.DataManipulate dataManipulate) {
        super(cArr, i2, dataManipulate);
        this.m_data_ = iArr;
        this.m_dataLength_ = iArr.length;
        this.m_initialValue_ = i;
    }
}
