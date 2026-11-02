package it.feio.android.checklistview.models;

import android.animation.LayoutTransition;
import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.util.Log;
import android.view.DragEvent;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import it.feio.android.checklistview.App;
import it.feio.android.checklistview.dragging.ChecklistViewItemOnDragListener;
import it.feio.android.checklistview.dragging.ChecklistViewOnTouchListener;
import it.feio.android.checklistview.interfaces.CheckListChangedListener;
import it.feio.android.checklistview.interfaces.CheckListEventListener;
import it.feio.android.checklistview.interfaces.Constants;
import it.feio.android.checklistview.widgets.EditTextMultiLineNoEnter;
import it.feio.android.pixlui.links.TextLinkClickListener;
import java.lang.ref.WeakReference;

/* loaded from: classes2.dex */
public class CheckListView extends LinearLayout implements Constants, CheckListEventListener {
    private CheckListChangedListener mCheckListChangedListener;
    private ChecklistViewItemOnDragListener mChecklistViewItemOnDragListener;
    private WeakReference<Context> mContext;
    private TextLinkClickListener mTextLinkClickListener;
    private int moveCheckedOnBottom;
    private String newEntryHint;
    private boolean showDeleteIcon;
    private boolean showHintItem;
    private View undoBarContainerView;
    private boolean undoBarEnabled;

    public CheckListView(WeakReference<Context> weakReference) {
        super(weakReference.get());
        this.showDeleteIcon = true;
        this.showHintItem = false;
        this.newEntryHint = "";
        this.moveCheckedOnBottom = 0;
        this.mContext = weakReference;
        setTag(Constants.TAG_LIST);
        setOrientation(1);
        setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
        setLayoutTransition(new LayoutTransition());
        if (Build.VERSION.SDK_INT > 10) {
            ChecklistViewItemOnDragListener checklistViewItemOnDragListener = new ChecklistViewItemOnDragListener();
            this.mChecklistViewItemOnDragListener = checklistViewItemOnDragListener;
            setOnDragListener(checklistViewItemOnDragListener);
        }
    }

    void setMoveCheckedOnBottom(int i) {
        this.moveCheckedOnBottom = i;
    }

    void setShowDeleteIcon(boolean z) {
        this.showDeleteIcon = z;
    }

    void setShowHintItem(boolean z) {
        this.showHintItem = z;
    }

    void setNewEntryHint(String str) {
        setShowHintItem(true);
        this.newEntryHint = str;
    }

    void setUndoBarEnabled(boolean z) {
        this.undoBarEnabled = z;
    }

    void setUndoBarContainerView(View view) {
        this.undoBarContainerView = view;
    }

    void cloneStyles(EditText editText) {
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).cloneStyles(editText);
        }
    }

    @Override // android.view.ViewGroup
    public CheckListViewItem getChildAt(int i) {
        return (CheckListViewItem) super.getChildAt(i);
    }

    EditText getEditText() {
        CheckListViewItem childAt = getChildAt(0);
        if (childAt != null) {
            return childAt.getEditText();
        }
        return null;
    }

    @Override // it.feio.android.checklistview.interfaces.CheckListEventListener
    public void onItemChecked(CheckListViewItem checkListViewItem, boolean z) {
        if (z) {
            doOnCheck(checkListViewItem);
        } else {
            doOnUncheck(checkListViewItem);
        }
    }

    private void doOnCheck(CheckListViewItem checkListViewItem) {
        if (this.moveCheckedOnBottom != 0) {
            for (int i = 0; i < getChildCount(); i++) {
                if (moveOnChecked(checkListViewItem, i)) {
                    return;
                }
            }
        }
        CheckListChangedListener checkListChangedListener = this.mCheckListChangedListener;
        if (checkListChangedListener != null) {
            checkListChangedListener.onCheckListChanged();
        }
    }

    private boolean moveOnChecked(CheckListViewItem checkListViewItem, int i) {
        if (!checkListViewItem.equals(getChildAt(i))) {
            return false;
        }
        int childCount = getChildCount() - 1;
        if (i == childCount) {
            Log.v(Constants.TAG, "Not moving item it's the last one");
            return true;
        }
        Log.v(Constants.TAG, "Moving item at position " + i);
        int i2 = this.moveCheckedOnBottom;
        if (i2 == 1) {
            removeView(checkListViewItem);
            addView(checkListViewItem, childCount);
            return true;
        }
        if (i2 != 2) {
            return false;
        }
        while (childCount > i) {
            if (!getChildAt(childCount).isChecked()) {
                removeView(checkListViewItem);
                addView(checkListViewItem, childCount);
                return true;
            }
            childCount--;
        }
        return false;
    }

    private void doOnUncheck(CheckListViewItem checkListViewItem) {
        if (this.moveCheckedOnBottom != 0) {
            int i = 0;
            int i2 = 0;
            while (true) {
                if (i >= getChildCount()) {
                    i = i2;
                    break;
                }
                CheckListViewItem childAt = getChildAt(i);
                if (childAt.isChecked() || childAt.isHintItem()) {
                    break;
                }
                i2 = i;
                i++;
            }
            removeView(checkListViewItem);
            addView(checkListViewItem, i);
        }
    }

    @Override // it.feio.android.checklistview.interfaces.CheckListEventListener
    public void onNewLineItemEdited(CheckListViewItem checkListViewItem) {
        checkListViewItem.getCheckBox().setEnabled(true);
        enableDragAndDrop(checkListViewItem);
        addHintItem();
    }

    @Override // it.feio.android.checklistview.interfaces.CheckListEventListener
    public void onEditorActionPerformed(CheckListViewItem checkListViewItem, int i, KeyEvent keyEvent) {
        String strSubstring;
        if (i == 5 || keyEvent.getKeyCode() == 66) {
            EditTextMultiLineNoEnter editText = checkListViewItem.getEditText();
            int length = checkListViewItem.getText().length();
            int selectionStart = editText.getSelectionStart();
            int selectionEnd = editText.getSelectionEnd();
            boolean z = selectionEnd != selectionStart;
            boolean z2 = !z && selectionStart > 0 && selectionStart < length;
            int iIndexOfChild = indexOfChild(checkListViewItem);
            boolean z3 = iIndexOfChild == getChildCount() - 1;
            if (checkListViewItem.isHintItem() || z3) {
                ((InputMethodManager) this.mContext.get().getSystemService("input_method")).hideSoftInputFromWindow(checkListViewItem.getWindowToken(), 2);
                return;
            }
            int i2 = iIndexOfChild + 1;
            CheckListViewItem childAt = getChildAt(i2);
            if (newItemIsNeeded(length, selectionStart, z, z2, childAt)) {
                childAt.requestFocus();
                childAt.getEditText().setSelection(0);
                return;
            }
            String string = editText.getText().toString();
            if (z) {
                strSubstring = string.substring(0, selectionStart) + string.substring(selectionEnd, string.length());
            } else {
                strSubstring = string.substring(0, selectionStart);
            }
            String strSubstring2 = z ? string.substring(selectionStart, selectionEnd) : string.substring(selectionEnd, string.length());
            editText.setText(strSubstring);
            addItem(strSubstring2, checkListViewItem.isChecked(), Integer.valueOf(i2));
            getChildAt(i2).requestFocus();
        }
    }

    private boolean newItemIsNeeded(int i, int i2, boolean z, boolean z2, CheckListViewItem checkListViewItem) {
        boolean z3 = z || z2;
        return i == 0 || (checkListViewItem.getText().length() == 0 && !z3) || (!z3 && i2 == 0);
    }

    public void addItem(String str) {
        addItem(str, false);
    }

    public void addItem(String str, boolean z) {
        addItem(str, z, null);
    }

    public void addItem(String str, boolean z, Integer num) {
        CheckListViewItem checkListViewItem = new CheckListViewItem(this.mContext, z, this.showDeleteIcon);
        checkListViewItem.cloneStyles(getEditText());
        checkListViewItem.setText(str);
        checkListViewItem.getEditText().setImeOptions(5);
        checkListViewItem.setItemCheckedListener(this);
        checkListViewItem.setUndoBarEnabled(this.undoBarEnabled);
        checkListViewItem.setUndoBarContainerView(this.undoBarContainerView);
        if (this.mTextLinkClickListener != null) {
            checkListViewItem.getEditText().gatherLinksForText();
            checkListViewItem.getEditText().setOnTextLinkClickListener(this.mTextLinkClickListener);
        }
        CheckListChangedListener checkListChangedListener = this.mCheckListChangedListener;
        if (checkListChangedListener != null) {
            checkListViewItem.setCheckListChangedListener(checkListChangedListener);
        }
        if (num != null) {
            addView(checkListViewItem, num.intValue());
        } else {
            addView(checkListViewItem);
        }
        enableDragAndDrop(checkListViewItem);
    }

    private void enableDragAndDrop(CheckListViewItem checkListViewItem) {
        if (Build.VERSION.SDK_INT <= 10 || !App.getSettings().getDragEnabled()) {
            return;
        }
        checkListViewItem.getDragHandler().setOnTouchListener(new ChecklistViewOnTouchListener());
        checkListViewItem.setOnDragListener(this.mChecklistViewItemOnDragListener);
    }

    public void addHintItem() {
        int i = 0;
        CheckListViewItem checkListViewItem = new CheckListViewItem(this.mContext, false, false);
        checkListViewItem.cloneStyles(getEditText());
        checkListViewItem.setHint(Html.fromHtml("<i>" + this.newEntryHint + "</i>"));
        checkListViewItem.getEditText().setImeOptions(5);
        CheckBox checkBox = checkListViewItem.getCheckBox();
        checkBox.setEnabled(false);
        checkListViewItem.setCheckBox(checkBox);
        checkListViewItem.setItemCheckedListener(this);
        CheckListChangedListener checkListChangedListener = this.mCheckListChangedListener;
        if (checkListChangedListener != null) {
            checkListViewItem.setCheckListChangedListener(checkListChangedListener);
        }
        checkListViewItem.setUndoBarContainerView(this.undoBarContainerView);
        int childCount = getChildCount();
        if (this.moveCheckedOnBottom != 0) {
            while (true) {
                if (i >= getChildCount()) {
                    break;
                }
                if (getChildAt(i).isChecked()) {
                    childCount = i;
                    break;
                }
                i++;
            }
        }
        if (Build.VERSION.SDK_INT >= 11) {
            checkListViewItem.setOnDragListener(new View.OnDragListener() { // from class: it.feio.android.checklistview.models.CheckListView.1
                @Override // android.view.View.OnDragListener
                public boolean onDrag(View view, DragEvent dragEvent) {
                    if (dragEvent.getAction() != 3) {
                        return true;
                    }
                    CheckListView.this.mChecklistViewItemOnDragListener.onDrag(view, dragEvent);
                    return true;
                }
            });
        }
        addView(checkListViewItem, childCount);
    }

    private void focusView(View view, int i) {
        EditTextMultiLineNoEnter editTextMultiLineNoEnter = (EditTextMultiLineNoEnter) view.focusSearch(i);
        if (editTextMultiLineNoEnter != null) {
            editTextMultiLineNoEnter.requestFocus();
            editTextMultiLineNoEnter.setSelection(editTextMultiLineNoEnter.getText().length());
        }
    }

    public void setCheckListChangedListener(CheckListChangedListener checkListChangedListener) {
        this.mCheckListChangedListener = checkListChangedListener;
    }

    @Override // it.feio.android.checklistview.interfaces.CheckListEventListener
    public void onLineDeleted(CheckListViewItem checkListViewItem) {
        CheckListChangedListener checkListChangedListener = this.mCheckListChangedListener;
        if (checkListChangedListener != null) {
            checkListChangedListener.onCheckListChanged();
        }
    }

    public void setOnTextLinkClickListener(TextLinkClickListener textLinkClickListener) {
        this.mTextLinkClickListener = textLinkClickListener;
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchDragEvent(DragEvent dragEvent) {
        boolean zDispatchDragEvent = super.dispatchDragEvent(dragEvent);
        if (zDispatchDragEvent && (dragEvent.getAction() == 1 || dragEvent.getAction() == 4)) {
            onDragEvent(dragEvent);
        }
        return zDispatchDragEvent;
    }
}
