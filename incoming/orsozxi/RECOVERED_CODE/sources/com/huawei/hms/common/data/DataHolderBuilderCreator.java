package com.huawei.hms.common.data;

import android.content.ContentValues;
import com.huawei.hms.common.data.DataHolder;
import java.util.HashMap;

/* loaded from: classes2.dex */
final class DataHolderBuilderCreator extends DataHolder.Builder {
    DataHolderBuilderCreator(String[] strArr, String str) {
        super(strArr, (String) null, (DataHolderBuilderCreator) null);
    }

    @Override // com.huawei.hms.common.data.DataHolder.Builder
    public final DataHolder.Builder setDataForContentValuesHashMap(HashMap<String, Object> map) {
        throw new UnsupportedOperationException("DataHolderBuilderCreator unsupported setDataForContentValuesHashMap");
    }

    @Override // com.huawei.hms.common.data.DataHolder.Builder
    public final DataHolder.Builder withRow(ContentValues contentValues) {
        throw new UnsupportedOperationException("DataHolderBuilderCreator unsupported withRow");
    }
}
