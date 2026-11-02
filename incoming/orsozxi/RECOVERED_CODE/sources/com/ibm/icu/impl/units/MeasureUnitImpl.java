package com.ibm.icu.impl.units;

import androidx.core.view.InputDeviceCompat;
import com.ibm.icu.impl.locale.LanguageTag;
import com.ibm.icu.util.BytesTrie;
import com.ibm.icu.util.CharsTrie;
import com.ibm.icu.util.CharsTrieBuilder;
import com.ibm.icu.util.ICUCloneNotSupportedException;
import com.ibm.icu.util.MeasureUnit;
import com.ibm.icu.util.StringTrieBuilder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

/* loaded from: classes2.dex */
public class MeasureUnitImpl {
    private MeasureUnit.Complexity complexity;
    private String identifier;
    private final ArrayList<SingleUnitImpl> singleUnits;

    public MeasureUnitImpl() {
        this.identifier = null;
        this.complexity = MeasureUnit.Complexity.SINGLE;
        this.singleUnits = new ArrayList<>();
    }

    public MeasureUnitImpl(SingleUnitImpl singleUnitImpl) {
        this();
        appendSingleUnit(singleUnitImpl);
    }

    public static MeasureUnitImpl forIdentifier(String str) {
        return UnitsParser.parseForIdentifier(str);
    }

    public static MeasureUnitImpl forCurrencyCode(String str) {
        MeasureUnitImpl measureUnitImpl = new MeasureUnitImpl();
        measureUnitImpl.identifier = str;
        return measureUnitImpl;
    }

    public MeasureUnitImpl copy() {
        MeasureUnitImpl measureUnitImpl = new MeasureUnitImpl();
        measureUnitImpl.complexity = this.complexity;
        measureUnitImpl.identifier = this.identifier;
        Iterator<SingleUnitImpl> it2 = this.singleUnits.iterator();
        while (it2.hasNext()) {
            measureUnitImpl.singleUnits.add(it2.next().copy());
        }
        return measureUnitImpl;
    }

    public ArrayList<SingleUnitImpl> getSingleUnits() {
        return this.singleUnits;
    }

    public void takeReciprocal() {
        this.identifier = null;
        Iterator<SingleUnitImpl> it2 = this.singleUnits.iterator();
        while (it2.hasNext()) {
            SingleUnitImpl next = it2.next();
            next.setDimensionality(next.getDimensionality() * (-1));
        }
    }

    public ArrayList<MeasureUnitImplWithIndex> extractIndividualUnitsWithIndices() {
        ArrayList<MeasureUnitImplWithIndex> arrayList = new ArrayList<>();
        int i = 0;
        if (getComplexity() == MeasureUnit.Complexity.MIXED) {
            Iterator<SingleUnitImpl> it2 = getSingleUnits().iterator();
            while (it2.hasNext()) {
                arrayList.add(new MeasureUnitImplWithIndex(i, new MeasureUnitImpl(it2.next())));
                i++;
            }
            return arrayList;
        }
        arrayList.add(new MeasureUnitImplWithIndex(0, copy()));
        return arrayList;
    }

    public void applyDimensionality(int i) {
        Iterator<SingleUnitImpl> it2 = this.singleUnits.iterator();
        while (it2.hasNext()) {
            SingleUnitImpl next = it2.next();
            next.setDimensionality(next.getDimensionality() * i);
        }
    }

    public boolean appendSingleUnit(SingleUnitImpl singleUnitImpl) {
        SingleUnitImpl singleUnitImpl2 = null;
        this.identifier = null;
        if (singleUnitImpl == null) {
            return false;
        }
        Iterator<SingleUnitImpl> it2 = this.singleUnits.iterator();
        while (true) {
            if (!it2.hasNext()) {
                break;
            }
            SingleUnitImpl next = it2.next();
            if (next.isCompatibleWith(singleUnitImpl)) {
                singleUnitImpl2 = next;
                break;
            }
        }
        if (singleUnitImpl2 != null) {
            singleUnitImpl2.setDimensionality(singleUnitImpl2.getDimensionality() + singleUnitImpl.getDimensionality());
            return false;
        }
        this.singleUnits.add(singleUnitImpl.copy());
        if (this.singleUnits.size() > 1 && this.complexity == MeasureUnit.Complexity.SINGLE) {
            setComplexity(MeasureUnit.Complexity.COMPOUND);
        }
        return true;
    }

    public MeasureUnit build() {
        return MeasureUnit.fromMeasureUnitImpl(this);
    }

    public SingleUnitImpl getSingleUnitImpl() {
        if (this.singleUnits.size() == 0) {
            return new SingleUnitImpl();
        }
        if (this.singleUnits.size() == 1) {
            return this.singleUnits.get(0).copy();
        }
        throw new UnsupportedOperationException();
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public MeasureUnit.Complexity getComplexity() {
        return this.complexity;
    }

    public void setComplexity(MeasureUnit.Complexity complexity) {
        this.complexity = complexity;
    }

    public void serialize() {
        if (getSingleUnits().size() == 0) {
            return;
        }
        if (this.complexity == MeasureUnit.Complexity.COMPOUND) {
            Collections.sort(getSingleUnits(), new SingleUnitComparator());
        }
        StringBuilder sb = new StringBuilder();
        Iterator<SingleUnitImpl> it2 = getSingleUnits().iterator();
        boolean z = true;
        boolean z2 = false;
        while (it2.hasNext()) {
            SingleUnitImpl next = it2.next();
            if (z && next.getDimensionality() < 0) {
                z = false;
                z2 = true;
            } else if (next.getDimensionality() < 0) {
                z2 = false;
            }
            if (getComplexity() == MeasureUnit.Complexity.MIXED) {
                if (sb.length() != 0) {
                    sb.append("-and-");
                }
            } else if (z2) {
                if (sb.length() == 0) {
                    sb.append("per-");
                } else {
                    sb.append("-per-");
                }
            } else if (sb.length() != 0) {
                sb.append(LanguageTag.SEP);
            }
            sb.append(next.getNeutralIdentifier());
        }
        this.identifier = sb.toString();
    }

    public String toString() {
        return "MeasureUnitImpl [" + build().getIdentifier() + "]";
    }

    public enum CompoundPart {
        PER(0),
        TIMES(1),
        AND(2);

        private final int index;

        CompoundPart(int i) {
            this.index = i;
        }

        public static CompoundPart getCompoundPartFromTrieIndex(int i) {
            int i2 = i - 128;
            if (i2 == 0) {
                return PER;
            }
            if (i2 == 1) {
                return TIMES;
            }
            if (i2 == 2) {
                return AND;
            }
            throw new AssertionError("CompoundPart index must be 0, 1 or 2");
        }

        public int getTrieIndex() {
            return this.index + 128;
        }

        public int getValue() {
            return this.index;
        }
    }

    public enum PowerPart {
        P2(2),
        P3(3),
        P4(4),
        P5(5),
        P6(6),
        P7(7),
        P8(8),
        P9(9),
        P10(10),
        P11(11),
        P12(12),
        P13(13),
        P14(14),
        P15(15);

        private final int power;

        public static int getPowerFromTrieIndex(int i) {
            return i + InputDeviceCompat.SOURCE_ANY;
        }

        PowerPart(int i) {
            this.power = i;
        }

        public int getTrieIndex() {
            return this.power + 256;
        }

        public int getValue() {
            return this.power;
        }
    }

    public enum InitialCompoundPart {
        INITIAL_COMPOUND_PART_PER(0);

        private final int index;

        InitialCompoundPart(int i) {
            this.index = i;
        }

        public static InitialCompoundPart getInitialCompoundPartFromTrieIndex(int i) {
            if (i - 192 == 0) {
                return INITIAL_COMPOUND_PART_PER;
            }
            throw new IllegalArgumentException("Incorrect trieIndex");
        }

        public int getTrieIndex() {
            return this.index + 192;
        }

        public int getValue() {
            return this.index;
        }
    }

    public static class MeasureUnitImplWithIndex {
        int index;
        MeasureUnitImpl unitImpl;

        MeasureUnitImplWithIndex(int i, MeasureUnitImpl measureUnitImpl) {
            this.index = i;
            this.unitImpl = measureUnitImpl;
        }
    }

    public static class UnitsParser {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private static MeasureUnit.MeasurePrefix[] measurePrefixValues = MeasureUnit.MeasurePrefix.values();
        private static volatile CharsTrie savedTrie;
        private final String fSource;
        private final CharsTrie trie;
        private int fIndex = 0;
        private boolean fAfterPer = false;
        private boolean fSawAnd = false;

        static {
            savedTrie = null;
            CharsTrieBuilder charsTrieBuilder = new CharsTrieBuilder();
            for (MeasureUnit.MeasurePrefix measurePrefix : measurePrefixValues) {
                charsTrieBuilder.add(measurePrefix.getIdentifier(), getTrieIndexForPrefix(measurePrefix));
            }
            charsTrieBuilder.add("-per-", CompoundPart.PER.getTrieIndex());
            charsTrieBuilder.add(LanguageTag.SEP, CompoundPart.TIMES.getTrieIndex());
            charsTrieBuilder.add("-and-", CompoundPart.AND.getTrieIndex());
            charsTrieBuilder.add("per-", InitialCompoundPart.INITIAL_COMPOUND_PART_PER.getTrieIndex());
            charsTrieBuilder.add("square-", PowerPart.P2.getTrieIndex());
            charsTrieBuilder.add("cubic-", PowerPart.P3.getTrieIndex());
            charsTrieBuilder.add("pow2-", PowerPart.P2.getTrieIndex());
            charsTrieBuilder.add("pow3-", PowerPart.P3.getTrieIndex());
            charsTrieBuilder.add("pow4-", PowerPart.P4.getTrieIndex());
            charsTrieBuilder.add("pow5-", PowerPart.P5.getTrieIndex());
            charsTrieBuilder.add("pow6-", PowerPart.P6.getTrieIndex());
            charsTrieBuilder.add("pow7-", PowerPart.P7.getTrieIndex());
            charsTrieBuilder.add("pow8-", PowerPart.P8.getTrieIndex());
            charsTrieBuilder.add("pow9-", PowerPart.P9.getTrieIndex());
            charsTrieBuilder.add("pow10-", PowerPart.P10.getTrieIndex());
            charsTrieBuilder.add("pow11-", PowerPart.P11.getTrieIndex());
            charsTrieBuilder.add("pow12-", PowerPart.P12.getTrieIndex());
            charsTrieBuilder.add("pow13-", PowerPart.P13.getTrieIndex());
            charsTrieBuilder.add("pow14-", PowerPart.P14.getTrieIndex());
            charsTrieBuilder.add("pow15-", PowerPart.P15.getTrieIndex());
            String[] simpleUnits = UnitsData.getSimpleUnits();
            for (int i = 0; i < simpleUnits.length; i++) {
                charsTrieBuilder.add(simpleUnits[i], i + 512);
            }
            savedTrie = charsTrieBuilder.build(StringTrieBuilder.Option.FAST);
        }

        private UnitsParser(String str) {
            this.fSource = str;
            try {
                this.trie = savedTrie.m26clone();
            } catch (CloneNotSupportedException unused) {
                throw new ICUCloneNotSupportedException();
            }
        }

        public static MeasureUnitImpl parseForIdentifier(String str) {
            if (str == null || str.isEmpty()) {
                return null;
            }
            return new UnitsParser(str).parse();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static MeasureUnit.MeasurePrefix getPrefixFromTrieIndex(int i) {
            return measurePrefixValues[i - 64];
        }

        private static int getTrieIndexForPrefix(MeasureUnit.MeasurePrefix measurePrefix) {
            return measurePrefix.ordinal() + 64;
        }

        private MeasureUnitImpl parse() {
            MeasureUnitImpl measureUnitImpl = new MeasureUnitImpl();
            if (this.fSource.isEmpty()) {
                return null;
            }
            while (hasNext()) {
                this.fSawAnd = false;
                boolean zAppendSingleUnit = measureUnitImpl.appendSingleUnit(nextSingleUnit());
                if (!this.fSawAnd || zAppendSingleUnit) {
                    if (measureUnitImpl.singleUnits.size() >= 2) {
                        MeasureUnit.Complexity complexity = this.fSawAnd ? MeasureUnit.Complexity.MIXED : MeasureUnit.Complexity.COMPOUND;
                        if (measureUnitImpl.getSingleUnits().size() == 2) {
                            measureUnitImpl.setComplexity(complexity);
                        } else if (measureUnitImpl.getComplexity() != complexity) {
                            throw new IllegalArgumentException("Can't have mixed compound units");
                        }
                    }
                } else {
                    throw new IllegalArgumentException("Two similar units are not allowed in a mixed unit.");
                }
            }
            return measureUnitImpl;
        }

        private SingleUnitImpl nextSingleUnit() {
            SingleUnitImpl singleUnitImpl = new SingleUnitImpl();
            char c = 0;
            boolean z = this.fIndex == 0;
            Token tokenNextToken = nextToken();
            if (z) {
                if (tokenNextToken.getType() == Token.Type.TYPE_INITIAL_COMPOUND_PART) {
                    this.fAfterPer = true;
                    singleUnitImpl.setDimensionality(-1);
                    tokenNextToken = nextToken();
                }
            } else {
                if (tokenNextToken.getType() != Token.Type.TYPE_COMPOUND_PART) {
                    throw new IllegalArgumentException("token type must be TYPE_COMPOUND_PART");
                }
                int i = AnonymousClass1.$SwitchMap$com$ibm$icu$impl$units$MeasureUnitImpl$CompoundPart[CompoundPart.getCompoundPartFromTrieIndex(tokenNextToken.getMatch()).ordinal()];
                if (i != 1) {
                    if (i != 2) {
                        if (i == 3) {
                            if (this.fAfterPer) {
                                throw new IllegalArgumentException("Can't start with \"-and-\", and mixed compound units");
                            }
                            this.fSawAnd = true;
                        }
                    } else if (this.fAfterPer) {
                        singleUnitImpl.setDimensionality(-1);
                    }
                } else {
                    if (this.fSawAnd) {
                        throw new IllegalArgumentException("Mixed compound units not yet supported");
                    }
                    this.fAfterPer = true;
                    singleUnitImpl.setDimensionality(-1);
                }
                tokenNextToken = nextToken();
            }
            while (true) {
                int i2 = AnonymousClass1.$SwitchMap$com$ibm$icu$impl$units$MeasureUnitImpl$UnitsParser$Token$Type[tokenNextToken.getType().ordinal()];
                if (i2 != 1) {
                    if (i2 != 2) {
                        if (i2 == 3) {
                            singleUnitImpl.setSimpleUnit(tokenNextToken.getSimpleUnitIndex(), UnitsData.getSimpleUnits());
                            return singleUnitImpl;
                        }
                        throw new IllegalArgumentException();
                    }
                    if (c > 1) {
                        throw new IllegalArgumentException();
                    }
                    singleUnitImpl.setPrefix(tokenNextToken.getPrefix());
                    c = 2;
                } else {
                    if (c > 0) {
                        throw new IllegalArgumentException();
                    }
                    singleUnitImpl.setDimensionality(singleUnitImpl.getDimensionality() * tokenNextToken.getPower());
                    c = 1;
                }
                if (!hasNext()) {
                    throw new IllegalArgumentException("We ran out of tokens before finding a complete single unit.");
                }
                tokenNextToken = nextToken();
            }
        }

        private boolean hasNext() {
            return this.fIndex < this.fSource.length();
        }

        private Token nextToken() {
            this.trie.reset();
            int value = -1;
            int i = -1;
            while (this.fIndex < this.fSource.length()) {
                CharsTrie charsTrie = this.trie;
                String str = this.fSource;
                int i2 = this.fIndex;
                this.fIndex = i2 + 1;
                BytesTrie.Result next = charsTrie.next(str.charAt(i2));
                if (next == BytesTrie.Result.NO_MATCH) {
                    break;
                }
                if (next != BytesTrie.Result.NO_VALUE) {
                    value = this.trie.getValue();
                    i = this.fIndex;
                    if (next == BytesTrie.Result.FINAL_VALUE) {
                        break;
                    }
                    if (next != BytesTrie.Result.INTERMEDIATE_VALUE) {
                        throw new IllegalArgumentException("result must has an intermediate value");
                    }
                }
            }
            if (value < 0) {
                throw new IllegalArgumentException("Encountered unknown token starting at index " + i);
            }
            this.fIndex = i;
            return new Token(value);
        }

        static class Token {
            static final /* synthetic */ boolean $assertionsDisabled = false;
            private final int fMatch;
            private final Type type;

            enum Type {
                TYPE_UNDEFINED,
                TYPE_PREFIX,
                TYPE_COMPOUND_PART,
                TYPE_INITIAL_COMPOUND_PART,
                TYPE_POWER_PART,
                TYPE_SIMPLE_UNIT
            }

            public Token(int i) {
                this.fMatch = i;
                this.type = calculateType(i);
            }

            public Type getType() {
                return this.type;
            }

            public MeasureUnit.MeasurePrefix getPrefix() {
                return UnitsParser.getPrefixFromTrieIndex(this.fMatch);
            }

            public int getMatch() {
                return this.fMatch;
            }

            public InitialCompoundPart getInitialCompoundPart() {
                return InitialCompoundPart.getInitialCompoundPartFromTrieIndex(this.fMatch);
            }

            public int getPower() {
                return PowerPart.getPowerFromTrieIndex(this.fMatch);
            }

            public int getSimpleUnitIndex() {
                return this.fMatch - 512;
            }

            private Type calculateType(int i) {
                if (i <= 0) {
                    throw new AssertionError("fMatch must have a positive value");
                }
                if (i < 128) {
                    return Type.TYPE_PREFIX;
                }
                if (i < 192) {
                    return Type.TYPE_COMPOUND_PART;
                }
                if (i < 256) {
                    return Type.TYPE_INITIAL_COMPOUND_PART;
                }
                if (i < 512) {
                    return Type.TYPE_POWER_PART;
                }
                return Type.TYPE_SIMPLE_UNIT;
            }
        }
    }

    /* renamed from: com.ibm.icu.impl.units.MeasureUnitImpl$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$ibm$icu$impl$units$MeasureUnitImpl$CompoundPart;
        static final /* synthetic */ int[] $SwitchMap$com$ibm$icu$impl$units$MeasureUnitImpl$UnitsParser$Token$Type;

        static {
            int[] iArr = new int[UnitsParser.Token.Type.values().length];
            $SwitchMap$com$ibm$icu$impl$units$MeasureUnitImpl$UnitsParser$Token$Type = iArr;
            try {
                iArr[UnitsParser.Token.Type.TYPE_POWER_PART.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$ibm$icu$impl$units$MeasureUnitImpl$UnitsParser$Token$Type[UnitsParser.Token.Type.TYPE_PREFIX.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$ibm$icu$impl$units$MeasureUnitImpl$UnitsParser$Token$Type[UnitsParser.Token.Type.TYPE_SIMPLE_UNIT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            int[] iArr2 = new int[CompoundPart.values().length];
            $SwitchMap$com$ibm$icu$impl$units$MeasureUnitImpl$CompoundPart = iArr2;
            try {
                iArr2[CompoundPart.PER.ordinal()] = 1;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$ibm$icu$impl$units$MeasureUnitImpl$CompoundPart[CompoundPart.TIMES.ordinal()] = 2;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$ibm$icu$impl$units$MeasureUnitImpl$CompoundPart[CompoundPart.AND.ordinal()] = 3;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    static class MeasureUnitImplComparator implements Comparator<MeasureUnitImpl> {
        private final ConversionRates conversionRates;

        public MeasureUnitImplComparator(ConversionRates conversionRates) {
            this.conversionRates = conversionRates;
        }

        @Override // java.util.Comparator
        public int compare(MeasureUnitImpl measureUnitImpl, MeasureUnitImpl measureUnitImpl2) {
            return this.conversionRates.getFactorToBase(measureUnitImpl).getConversionRate().compareTo(this.conversionRates.getFactorToBase(measureUnitImpl2).getConversionRate());
        }
    }

    static class MeasureUnitImplWithIndexComparator implements Comparator<MeasureUnitImplWithIndex> {
        private MeasureUnitImplComparator measureUnitImplComparator;

        public MeasureUnitImplWithIndexComparator(ConversionRates conversionRates) {
            this.measureUnitImplComparator = new MeasureUnitImplComparator(conversionRates);
        }

        @Override // java.util.Comparator
        public int compare(MeasureUnitImplWithIndex measureUnitImplWithIndex, MeasureUnitImplWithIndex measureUnitImplWithIndex2) {
            return this.measureUnitImplComparator.compare(measureUnitImplWithIndex.unitImpl, measureUnitImplWithIndex2.unitImpl);
        }
    }

    static class SingleUnitComparator implements Comparator<SingleUnitImpl> {
        SingleUnitComparator() {
        }

        @Override // java.util.Comparator
        public int compare(SingleUnitImpl singleUnitImpl, SingleUnitImpl singleUnitImpl2) {
            return singleUnitImpl.compareTo(singleUnitImpl2);
        }
    }
}
