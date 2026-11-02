package net.fortuna.ical4j.model;

import it.orsozoxi.android.orsonotes.utils.ConstantsBase;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.function.Function;
import java.util.stream.Collectors;
import net.fortuna.ical4j.util.Numbers;

/* loaded from: classes3.dex */
public class NumberList extends ArrayList<Integer> implements Serializable {
    private static final long serialVersionUID = -1667481795613729889L;
    private final boolean allowsNegativeValues;
    private final int maxValue;
    private final int minValue;

    public NumberList() {
        this(Integer.MIN_VALUE, Integer.MAX_VALUE, true);
    }

    public NumberList(int i, int i2, boolean z) {
        this.minValue = i;
        this.maxValue = i2;
        this.allowsNegativeValues = z;
    }

    public NumberList(String str) {
        this(str, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
    }

    public NumberList(String str, int i, int i2, boolean z) {
        this(i, i2, z);
        StringTokenizer stringTokenizer = new StringTokenizer(str, ConstantsBase.PROPERTIES_PARAMS_SEPARATOR);
        while (stringTokenizer.hasMoreTokens()) {
            add(Integer.valueOf(Numbers.parseInt(stringTokenizer.nextToken())));
        }
    }

    @Override // java.util.ArrayList, java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean add(Integer num) {
        int iIntValue = num.intValue();
        if (((iIntValue >> 31) | ((-iIntValue) >>> 31)) < 0) {
            if (!this.allowsNegativeValues) {
                throw new IllegalArgumentException("Negative value not allowed: " + num);
            }
            iIntValue = Math.abs(iIntValue);
        }
        if (iIntValue < this.minValue || iIntValue > this.maxValue) {
            throw new IllegalArgumentException("Value not in range [" + this.minValue + ".." + this.maxValue + "]: " + num);
        }
        return super.add((NumberList) num);
    }

    @Override // java.util.AbstractCollection
    public final String toString() {
        return (String) stream().map(new Function() { // from class: net.fortuna.ical4j.model.-$$Lambda$y_Ozl3EVfH5ahWhf28tOyZD4fgQ
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((Integer) obj).toString();
            }
        }).collect(Collectors.joining(ConstantsBase.PROPERTIES_PARAMS_SEPARATOR));
    }
}
