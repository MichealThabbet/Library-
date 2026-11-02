package com.ibm.icu.impl.number;

import com.ibm.icu.impl.StandardPlural;
import com.ibm.icu.impl.number.Modifier;

/* loaded from: classes2.dex */
public class AdoptingModifierStore implements ModifierStore {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    boolean frozen;
    final Modifier[] mods;
    private final Modifier negZero;
    private final Modifier negative;
    private final Modifier posZero;
    private final Modifier positive;

    public AdoptingModifierStore(Modifier modifier, Modifier modifier2, Modifier modifier3, Modifier modifier4) {
        this.positive = modifier;
        this.posZero = modifier2;
        this.negZero = modifier3;
        this.negative = modifier4;
        this.mods = null;
        this.frozen = true;
    }

    public AdoptingModifierStore() {
        this.positive = null;
        this.posZero = null;
        this.negZero = null;
        this.negative = null;
        this.mods = new Modifier[StandardPlural.COUNT * 4];
        this.frozen = false;
    }

    public void setModifier(Modifier.Signum signum, StandardPlural standardPlural, Modifier modifier) {
        this.mods[getModIndex(signum, standardPlural)] = modifier;
    }

    public void freeze() {
        this.frozen = true;
    }

    /* renamed from: com.ibm.icu.impl.number.AdoptingModifierStore$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$ibm$icu$impl$number$Modifier$Signum;

        static {
            int[] iArr = new int[Modifier.Signum.values().length];
            $SwitchMap$com$ibm$icu$impl$number$Modifier$Signum = iArr;
            try {
                iArr[Modifier.Signum.POS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$ibm$icu$impl$number$Modifier$Signum[Modifier.Signum.POS_ZERO.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$ibm$icu$impl$number$Modifier$Signum[Modifier.Signum.NEG_ZERO.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$ibm$icu$impl$number$Modifier$Signum[Modifier.Signum.NEG.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public Modifier getModifierWithoutPlural(Modifier.Signum signum) {
        int i = AnonymousClass1.$SwitchMap$com$ibm$icu$impl$number$Modifier$Signum[signum.ordinal()];
        if (i == 1) {
            return this.positive;
        }
        if (i == 2) {
            return this.posZero;
        }
        if (i == 3) {
            return this.negZero;
        }
        if (i == 4) {
            return this.negative;
        }
        throw new AssertionError("Unreachable");
    }

    @Override // com.ibm.icu.impl.number.ModifierStore
    public Modifier getModifier(Modifier.Signum signum, StandardPlural standardPlural) {
        return this.mods[getModIndex(signum, standardPlural)];
    }

    private static int getModIndex(Modifier.Signum signum, StandardPlural standardPlural) {
        return (standardPlural.ordinal() * Modifier.Signum.COUNT) + signum.ordinal();
    }
}
