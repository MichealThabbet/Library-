package com.google.zxing.datamatrix.encoder;

import it.orsozoxi.android.orsonotes.R2;

/* loaded from: classes2.dex */
final class DataMatrixSymbolInfo144 extends SymbolInfo {
    @Override // com.google.zxing.datamatrix.encoder.SymbolInfo
    public int getDataLengthForInterleavedBlock(int i) {
        return i <= 8 ? 156 : 155;
    }

    @Override // com.google.zxing.datamatrix.encoder.SymbolInfo
    public int getInterleavedBlockCount() {
        return 10;
    }

    DataMatrixSymbolInfo144() {
        super(false, R2.dimen.abc_panel_menu_list_width, R2.attr.layout_constraintLeft_toLeftOf, 22, 22, 36, -1, 62);
    }
}
