package it.feio.android.checklistview.interfaces;

import android.view.KeyEvent;
import it.feio.android.checklistview.models.CheckListViewItem;

/* loaded from: classes2.dex */
public interface CheckListEventListener {
    void onEditorActionPerformed(CheckListViewItem checkListViewItem, int i, KeyEvent keyEvent);

    void onItemChecked(CheckListViewItem checkListViewItem, boolean z);

    void onLineDeleted(CheckListViewItem checkListViewItem);

    void onNewLineItemEdited(CheckListViewItem checkListViewItem);
}
