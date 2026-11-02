package com.ibm.icu.impl.number;

import com.ibm.icu.impl.StandardPlural;
import com.ibm.icu.impl.number.Modifier;

/* loaded from: classes2.dex */
public interface ModifierStore {
    Modifier getModifier(Modifier.Signum signum, StandardPlural standardPlural);
}
