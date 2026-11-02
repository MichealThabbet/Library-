package com.ibm.icu.impl.coll;

import androidx.core.internal.view.SupportMenu;
import org.apache.http.message.TokenParser;

/* loaded from: classes2.dex */
public final class CollationCompare {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    public static int compareUpToQuaternary(CollationIterator collationIterator, CollationIterator collationIterator2, CollationSettings collationSettings) {
        long jReorder;
        long jReorder2;
        int i;
        int ce;
        int i2;
        int i3;
        long jReorder3;
        int i4;
        int ce2;
        int i5;
        int i6;
        int ce3;
        int i7;
        int i8;
        int i9;
        char c;
        long j;
        int i10;
        int i11;
        int i12;
        long j2;
        long ce4;
        int ce5;
        int i13;
        int ce6;
        long j3;
        long j4;
        int i14 = collationSettings.options;
        long j5 = (i14 & 12) == 0 ? 0L : collationSettings.variableTop + 1;
        boolean z = false;
        while (true) {
            long jNextCE = collationIterator.nextCE();
            char c2 = TokenParser.SP;
            long j6 = jNextCE >>> 32;
            if (j6 >= j5 || j6 <= Collation.MERGE_SEPARATOR_PRIMARY) {
                jReorder = j6;
            } else {
                do {
                    collationIterator.setCurrentCE(jNextCE & (-4294967296L));
                    while (true) {
                        jNextCE = collationIterator.nextCE();
                        j4 = jNextCE >>> 32;
                        if (j4 != 0) {
                            break;
                        }
                        collationIterator.setCurrentCE(0L);
                    }
                    if (j4 >= j5) {
                        break;
                    }
                } while (j4 > Collation.MERGE_SEPARATOR_PRIMARY);
                jReorder = j4;
                z = true;
            }
            if (jReorder != 0) {
                do {
                    long jNextCE2 = collationIterator2.nextCE();
                    long j7 = jNextCE2 >>> 32;
                    if (j7 >= j5 || j7 <= Collation.MERGE_SEPARATOR_PRIMARY) {
                        jReorder2 = j7;
                    } else {
                        do {
                            collationIterator2.setCurrentCE(jNextCE2 & (-4294967296L));
                            while (true) {
                                jNextCE2 = collationIterator2.nextCE();
                                j3 = jNextCE2 >>> 32;
                                if (j3 != 0) {
                                    break;
                                }
                                collationIterator2.setCurrentCE(0L);
                            }
                            if (j3 >= j5) {
                                break;
                            }
                        } while (j3 > Collation.MERGE_SEPARATOR_PRIMARY);
                        jReorder2 = j3;
                        z = true;
                    }
                } while (jReorder2 == 0);
                if (jReorder != jReorder2) {
                    if (collationSettings.hasReordering()) {
                        jReorder = collationSettings.reorder(jReorder);
                        jReorder2 = collationSettings.reorder(jReorder2);
                    }
                    return jReorder < jReorder2 ? -1 : 1;
                }
                if (jReorder == 1) {
                    if (CollationSettings.getStrength(i14) >= 1) {
                        if ((i14 & 2048) == 0) {
                            int i15 = 0;
                            int i16 = 0;
                            while (true) {
                                int i17 = i15 + 1;
                                int ce7 = ((int) collationIterator.getCE(i15)) >>> 16;
                                if (ce7 != 0) {
                                    while (true) {
                                        i13 = i16 + 1;
                                        ce6 = ((int) collationIterator2.getCE(i16)) >>> 16;
                                        if (ce6 != 0) {
                                            break;
                                        }
                                        i16 = i13;
                                    }
                                    if (ce7 != ce6) {
                                        return ce7 < ce6 ? -1 : 1;
                                    }
                                    if (ce7 == 256) {
                                        break;
                                    }
                                    i15 = i17;
                                    i16 = i13;
                                } else {
                                    i15 = i17;
                                }
                            }
                        } else {
                            int i18 = 0;
                            int i19 = 0;
                            while (true) {
                                int i20 = i18;
                                while (true) {
                                    long ce8 = collationIterator.getCE(i20) >>> c2;
                                    if (ce8 <= Collation.MERGE_SEPARATOR_PRIMARY) {
                                        j2 = 0;
                                        if (ce8 != 0) {
                                            break;
                                        }
                                    }
                                    i20++;
                                    c2 = TokenParser.SP;
                                }
                                int i21 = i19;
                                while (true) {
                                    ce4 = collationIterator2.getCE(i21) >>> c2;
                                    if (ce4 <= Collation.MERGE_SEPARATOR_PRIMARY && ce4 != j2) {
                                        break;
                                    }
                                    i21++;
                                    j2 = 0;
                                    c2 = TokenParser.SP;
                                }
                                int i22 = i20;
                                int i23 = i21;
                                do {
                                    ce5 = 0;
                                    while (ce5 == 0 && i22 > i18) {
                                        i22--;
                                        ce5 = ((int) collationIterator.getCE(i22)) >>> 16;
                                    }
                                    int ce9 = 0;
                                    while (ce9 == 0 && i23 > i19) {
                                        i23--;
                                        ce9 = ((int) collationIterator2.getCE(i23)) >>> 16;
                                    }
                                    if (ce5 != ce9) {
                                        return ce5 < ce9 ? -1 : 1;
                                    }
                                } while (ce5 != 0);
                                if (ce4 == 1) {
                                    break;
                                }
                                i18 = i20 + 1;
                                i19 = i21 + 1;
                                c2 = TokenParser.SP;
                            }
                        }
                    }
                    if ((i14 & 1024) != 0) {
                        int strength = CollationSettings.getStrength(i14);
                        int i24 = 0;
                        int i25 = 0;
                        while (true) {
                            if (strength == 0) {
                                while (true) {
                                    i7 = i24 + 1;
                                    long ce10 = collationIterator.getCE(i24);
                                    i9 = (int) ce10;
                                    c = TokenParser.SP;
                                    j = 0;
                                    if ((ce10 >>> 32) != 0 && i9 != 0) {
                                        break;
                                    }
                                    i24 = i7;
                                }
                                i5 = i9 & Collation.CASE_MASK;
                                while (true) {
                                    i10 = i25 + 1;
                                    i11 = i9;
                                    long ce11 = collationIterator2.getCE(i25);
                                    i12 = (int) ce11;
                                    if ((ce11 >>> c) != j && i12 != 0) {
                                        break;
                                    }
                                    i25 = i10;
                                    i9 = i11;
                                    c = TokenParser.SP;
                                    j = 0;
                                }
                                i8 = i12 & Collation.CASE_MASK;
                                i25 = i10;
                                ce2 = i11;
                            } else {
                                while (true) {
                                    i4 = i24 + 1;
                                    ce2 = (int) collationIterator.getCE(i24);
                                    if ((ce2 & SupportMenu.CATEGORY_MASK) != 0) {
                                        break;
                                    }
                                    i24 = i4;
                                }
                                i5 = ce2 & Collation.CASE_MASK;
                                while (true) {
                                    i6 = i25 + 1;
                                    ce3 = (int) collationIterator2.getCE(i25);
                                    if ((ce3 & SupportMenu.CATEGORY_MASK) != 0) {
                                        break;
                                    }
                                    i25 = i6;
                                }
                                i7 = i4;
                                i8 = ce3 & Collation.CASE_MASK;
                                i25 = i6;
                            }
                            if (i5 != i8) {
                                return (i14 & 256) == 0 ? i5 < i8 ? -1 : 1 : i5 < i8 ? 1 : -1;
                            }
                            if ((ce2 >>> 16) == 256) {
                                break;
                            }
                            i24 = i7;
                        }
                    }
                    if (CollationSettings.getStrength(i14) <= 1) {
                        return 0;
                    }
                    int tertiaryMask = CollationSettings.getTertiaryMask(i14);
                    int i26 = 0;
                    int i27 = 0;
                    int i28 = 0;
                    while (true) {
                        int i29 = i27 + 1;
                        int ce12 = (int) collationIterator.getCE(i27);
                        i28 |= ce12;
                        int i30 = ce12 & tertiaryMask;
                        if (i30 != 0) {
                            while (true) {
                                i = i26 + 1;
                                ce = (int) collationIterator2.getCE(i26);
                                i28 |= ce;
                                i2 = ce & tertiaryMask;
                                if (i2 != 0) {
                                    break;
                                }
                                i26 = i;
                            }
                            if (i30 != i2) {
                                if (CollationSettings.sortsTertiaryUpperCaseFirst(i14)) {
                                    if (i30 > 256) {
                                        i30 = (ce12 & SupportMenu.CATEGORY_MASK) != 0 ? i30 ^ Collation.CASE_MASK : i30 + 16384;
                                    }
                                    if (i2 > 256) {
                                        i2 = (ce & SupportMenu.CATEGORY_MASK) != 0 ? i2 ^ Collation.CASE_MASK : i2 + 16384;
                                    }
                                }
                                return i30 < i2 ? -1 : 1;
                            }
                            if (i30 != 256) {
                                i27 = i29;
                                i26 = i;
                            } else {
                                if (CollationSettings.getStrength(i14) <= 2) {
                                    return 0;
                                }
                                if (!z && (i28 & 192) == 0) {
                                    return 0;
                                }
                                int i31 = 0;
                                int i32 = 0;
                                while (true) {
                                    int i33 = i32 + 1;
                                    long ce13 = collationIterator.getCE(i32);
                                    long j8 = ce13 & 65535;
                                    long jReorder4 = j8 <= 256 ? ce13 >>> 32 : j8 | 4294967103L;
                                    if (jReorder4 != 0) {
                                        while (true) {
                                            i3 = i31 + 1;
                                            long ce14 = collationIterator2.getCE(i31);
                                            long j9 = ce14 & 65535;
                                            jReorder3 = j9 <= 256 ? ce14 >>> 32 : j9 | 4294967103L;
                                            if (jReorder3 != 0) {
                                                break;
                                            }
                                            i31 = i3;
                                        }
                                        if (jReorder4 != jReorder3) {
                                            if (collationSettings.hasReordering()) {
                                                jReorder4 = collationSettings.reorder(jReorder4);
                                                jReorder3 = collationSettings.reorder(jReorder3);
                                            }
                                            return jReorder4 < jReorder3 ? -1 : 1;
                                        }
                                        if (jReorder4 == 1) {
                                            return 0;
                                        }
                                        i31 = i3;
                                    }
                                    i32 = i33;
                                }
                            }
                        } else {
                            i27 = i29;
                        }
                    }
                }
            }
        }
    }
}
