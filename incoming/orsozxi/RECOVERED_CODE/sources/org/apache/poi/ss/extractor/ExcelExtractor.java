package org.apache.poi.ss.extractor;

/* loaded from: classes3.dex */
public interface ExcelExtractor {
    String getText();

    void setFormulasNotResults(boolean z);

    void setIncludeCellComments(boolean z);

    void setIncludeSheetNames(boolean z);
}
