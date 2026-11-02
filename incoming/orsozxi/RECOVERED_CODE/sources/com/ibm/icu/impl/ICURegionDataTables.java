package com.ibm.icu.impl;

import com.ibm.icu.impl.LocaleDisplayNamesImpl;
import com.ibm.icu.util.ULocale;

/* loaded from: classes2.dex */
public class ICURegionDataTables extends LocaleDisplayNamesImpl.ICUDataTables {
    @Override // com.ibm.icu.impl.LocaleDisplayNamesImpl.ICUDataTables, com.ibm.icu.impl.LocaleDisplayNamesImpl.DataTables
    public /* bridge */ /* synthetic */ LocaleDisplayNamesImpl.DataTable get(ULocale uLocale, boolean z) {
        return super.get(uLocale, z);
    }

    public ICURegionDataTables() {
        super(ICUData.ICU_REGION_BASE_NAME);
    }
}
