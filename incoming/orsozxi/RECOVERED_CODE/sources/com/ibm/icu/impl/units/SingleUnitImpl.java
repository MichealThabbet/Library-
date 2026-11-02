package com.ibm.icu.impl.units;

import com.ibm.icu.util.MeasureUnit;
import org.apache.commons.codec.language.Soundex;

/* loaded from: classes2.dex */
public class SingleUnitImpl {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private int index = -1;
    private String simpleUnitID = "";
    private int dimensionality = 1;
    private MeasureUnit.MeasurePrefix unitPrefix = MeasureUnit.MeasurePrefix.ONE;

    public SingleUnitImpl copy() {
        SingleUnitImpl singleUnitImpl = new SingleUnitImpl();
        singleUnitImpl.index = this.index;
        singleUnitImpl.dimensionality = this.dimensionality;
        singleUnitImpl.simpleUnitID = this.simpleUnitID;
        singleUnitImpl.unitPrefix = this.unitPrefix;
        return singleUnitImpl;
    }

    public MeasureUnit build() {
        return new MeasureUnitImpl(this).build();
    }

    public String getNeutralIdentifier() {
        StringBuilder sb = new StringBuilder();
        int iAbs = Math.abs(getDimensionality());
        if (iAbs != 1) {
            if (iAbs == 2) {
                sb.append("square-");
            } else if (iAbs == 3) {
                sb.append("cubic-");
            } else if (iAbs <= 15) {
                sb.append("pow");
                sb.append(iAbs);
                sb.append(Soundex.SILENT_MARKER);
            } else {
                throw new IllegalArgumentException("Unit Identifier Syntax Error");
            }
        }
        sb.append(getPrefix().getIdentifier());
        sb.append(getSimpleUnitID());
        return sb.toString();
    }

    int compareTo(SingleUnitImpl singleUnitImpl) {
        int categoryIndexOfSimpleUnit;
        int categoryIndexOfSimpleUnit2;
        if (this.dimensionality < 0 && singleUnitImpl.dimensionality > 0) {
            return 1;
        }
        if ((this.dimensionality > 0 && singleUnitImpl.dimensionality < 0) || (categoryIndexOfSimpleUnit = UnitsData.getCategoryIndexOfSimpleUnit(this.index)) < (categoryIndexOfSimpleUnit2 = UnitsData.getCategoryIndexOfSimpleUnit(singleUnitImpl.index))) {
            return -1;
        }
        if (categoryIndexOfSimpleUnit > categoryIndexOfSimpleUnit2) {
            return 1;
        }
        int i = this.index;
        int i2 = singleUnitImpl.index;
        if (i < i2) {
            return -1;
        }
        if (i > i2 || getPrefix().getBase() < singleUnitImpl.getPrefix().getBase()) {
            return 1;
        }
        if (getPrefix().getBase() <= singleUnitImpl.getPrefix().getBase() && getPrefix().getPower() >= singleUnitImpl.getPrefix().getPower()) {
            return getPrefix().getPower() > singleUnitImpl.getPrefix().getPower() ? 1 : 0;
        }
        return -1;
    }

    boolean isCompatibleWith(SingleUnitImpl singleUnitImpl) {
        return compareTo(singleUnitImpl) == 0;
    }

    public String getSimpleUnitID() {
        return this.simpleUnitID;
    }

    public void setSimpleUnit(int i, String[] strArr) {
        this.index = i;
        this.simpleUnitID = strArr[i];
    }

    public int getDimensionality() {
        return this.dimensionality;
    }

    public void setDimensionality(int i) {
        this.dimensionality = i;
    }

    public MeasureUnit.MeasurePrefix getPrefix() {
        return this.unitPrefix;
    }

    public void setPrefix(MeasureUnit.MeasurePrefix measurePrefix) {
        this.unitPrefix = measurePrefix;
    }

    public int getIndex() {
        return this.index;
    }
}
