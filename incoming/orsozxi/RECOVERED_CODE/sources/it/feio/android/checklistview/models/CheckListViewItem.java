package it.feio.android.checklistview.models;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Editable;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.DragEvent;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.material.snackbar.Snackbar;
import it.feio.android.checklistview.App;
import it.feio.android.checklistview.R;
import it.feio.android.checklistview.interfaces.CheckListChangedListener;
import it.feio.android.checklistview.interfaces.CheckListEventListener;
import it.feio.android.checklistview.interfaces.Constants;
import it.feio.android.checklistview.interfaces.EditTextEventListener;
import it.feio.android.checklistview.utils.AlphaManager;
import it.feio.android.checklistview.widgets.EditTextMultiLineNoEnter;
import java.lang.ref.WeakReference;

/* loaded from: classes2.dex */
public class CheckListViewItem extends LinearLayout implements CompoundButton.OnCheckedChangeListener, View.OnClickListener, View.OnFocusChangeListener, TextView.OnEditorActionListener, TextWatcher, EditTextEventListener {
    private CheckBox checkBox;
    private ImageView deleteIcon;
    private boolean deletionUndone;
    private ImageView dragHandler;
    private EditTextMultiLineNoEnter editText;
    private int lengthBeforeTextChanged;
    private CheckListChangedListener mCheckListChangedListener;
    private CheckListEventListener mCheckListEventListener;
    private final WeakReference<Context> mContext;
    private boolean showDeleteIcon;
    private View undoBarContainerView;
    private boolean undoBarEnabled;

    @Override // android.text.TextWatcher
    public void afterTextChanged(Editable editable) {
    }

    public CheckListViewItem(WeakReference<Context> weakReference, boolean z, boolean z2) {
        super(weakReference.get());
        this.undoBarEnabled = true;
        this.mContext = weakReference;
        this.showDeleteIcon = z2;
        inflate(weakReference.get(), R.layout.checklistview_item, this);
        initDragHandler();
        initCheckBox();
        initEditText();
        initDeleteIcon();
        if (z) {
            this.checkBox.setChecked(true);
            onCheckedChanged(this.checkBox, true);
        }
        setTag(Constants.TAG_ITEM);
    }

    private void initDragHandler() {
        if (Build.VERSION.SDK_INT < 11 || !App.getSettings().getDragEnabled()) {
            return;
        }
        this.dragHandler = (ImageView) findViewWithTag(this.mContext.get().getString(R.string.tag_draghandle));
    }

    private void initCheckBox() {
        CheckBox checkBox = (CheckBox) findViewWithTag(this.mContext.get().getString(R.string.tag_checkbox));
        this.checkBox = checkBox;
        checkBox.setOnCheckedChangeListener(this);
    }

    private void initEditText() {
        EditTextMultiLineNoEnter editTextMultiLineNoEnter = (EditTextMultiLineNoEnter) findViewWithTag(this.mContext.get().getString(R.string.tag_edittext));
        this.editText = editTextMultiLineNoEnter;
        editTextMultiLineNoEnter.setOnFocusChangeListener(this);
        this.editText.setOnEditorActionListener(this);
        this.editText.addTextChangedListener(this);
        this.editText.setEditTextEventListener(this);
    }

    void setItemCheckedListener(CheckListEventListener checkListEventListener) {
        this.mCheckListEventListener = checkListEventListener;
    }

    private void initDeleteIcon() {
        if (this.showDeleteIcon && this.deleteIcon == null) {
            this.deleteIcon = (ImageView) findViewWithTag(this.mContext.get().getString(R.string.tag_deleteicon));
            if (Build.VERSION.SDK_INT >= 11) {
                this.deleteIcon.setAlpha(0.7f);
            }
            this.deleteIcon.setOnClickListener(this);
        }
    }

    ImageView getDragHandler() {
        return this.dragHandler;
    }

    CheckBox getCheckBox() {
        return this.checkBox;
    }

    void setCheckBox(CheckBox checkBox) {
        for (int i = 0; i < getChildCount(); i++) {
            if (getChildAt(i).equals(this.checkBox)) {
                removeViewAt(i);
                addView(checkBox, i);
            }
        }
        this.checkBox = checkBox;
    }

    public EditTextMultiLineNoEnter getEditText() {
        return this.editText;
    }

    public void setEditText(EditTextMultiLineNoEnter editTextMultiLineNoEnter) {
        this.editText = editTextMultiLineNoEnter;
    }

    public boolean isChecked() {
        return getCheckBox().isChecked();
    }

    public String getText() {
        return getEditText().getText().toString();
    }

    public void setText(String str) {
        getEditText().setText(str);
    }

    public String getHint() {
        return getEditText().getHint() != null ? getEditText().getHint().toString() : "";
    }

    public void setHint(String str) {
        getEditText().setHint(str);
    }

    public void setHint(Spanned spanned) {
        getEditText().setHint(spanned);
    }

    public boolean isFirstItem() {
        return equals(getParentView().getChildAt(0));
    }

    public boolean isLastItem() {
        return equals(getParentView().getChildAt(getParentView().getChildCount() - 1));
    }

    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public int hashCode() {
        return super.hashCode();
    }

    @Override // android.view.View.OnFocusChangeListener
    public void onFocusChange(View view, boolean z) {
        ImageView imageView;
        if (z && (imageView = this.deleteIcon) != null) {
            imageView.setVisibility(0);
            return;
        }
        if (getEditText().getText().length() > 0) {
            CheckBox checkBox = getCheckBox();
            checkBox.setEnabled(true);
            setCheckBox(checkBox);
        }
        ImageView imageView2 = this.deleteIcon;
        if (imageView2 != null) {
            imageView2.setVisibility(4);
        }
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        if (z) {
            EditTextMultiLineNoEnter editTextMultiLineNoEnter = this.editText;
            editTextMultiLineNoEnter.setPaintFlags(editTextMultiLineNoEnter.getPaintFlags() | 16);
            AlphaManager.setAlpha(this.editText, 0.4f);
        } else {
            EditTextMultiLineNoEnter editTextMultiLineNoEnter2 = this.editText;
            editTextMultiLineNoEnter2.setPaintFlags(editTextMultiLineNoEnter2.getPaintFlags() & (-17));
            AlphaManager.setAlpha(this.editText, 1.0f);
        }
        CheckListEventListener checkListEventListener = this.mCheckListEventListener;
        if (checkListEventListener != null) {
            checkListEventListener.onItemChecked(this, z);
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        ViewGroup viewGroup = (ViewGroup) getParent();
        if (viewGroup != null) {
            focusView(130);
            int iIndexOfChild = viewGroup.indexOfChild(this);
            viewGroup.removeView(this);
            if (this.undoBarEnabled) {
                showUndoBar(viewGroup, this, iIndexOfChild);
            }
        }
    }

    private void showUndoBar(final ViewGroup viewGroup, final View view, final int i) {
        View viewFindViewById = this.undoBarContainerView;
        if (viewFindViewById == null) {
            viewFindViewById = viewGroup.getRootView().findViewById(android.R.id.content);
        }
        Snackbar.make(viewFindViewById, R.string.item_deleted, 0).setAction(R.string.undo, new View.OnClickListener() { // from class: it.feio.android.checklistview.models.CheckListViewItem.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                viewGroup.addView(view, i);
                CheckListViewItem.this.deletionUndone = true;
            }
        }).setCallback(new Snackbar.Callback() { // from class: it.feio.android.checklistview.models.CheckListViewItem.1
            @Override // com.google.android.material.snackbar.Snackbar.Callback
            public void onDismissed(Snackbar snackbar, int i2) {
                if (CheckListViewItem.this.deletionUndone) {
                    return;
                }
                CheckListViewItem.this.mCheckListEventListener.onLineDeleted((CheckListViewItem) view);
            }
        }).show();
    }

    @Override // android.widget.TextView.OnEditorActionListener
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        this.mCheckListEventListener.onEditorActionPerformed(this, i, keyEvent);
        return true;
    }

    @Override // android.text.TextWatcher
    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        this.lengthBeforeTextChanged = charSequence.length();
    }

    @Override // android.text.TextWatcher
    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        if (this.lengthBeforeTextChanged == 0) {
            if (isHintItem()) {
                this.mCheckListEventListener.onNewLineItemEdited(this);
            }
            this.showDeleteIcon = true;
            initDeleteIcon();
            setHint("");
        }
        CheckListChangedListener checkListChangedListener = this.mCheckListChangedListener;
        if (checkListChangedListener != null) {
            checkListChangedListener.onCheckListChanged();
        }
    }

    private void focusView(int i) {
        View viewFocusSearch = focusSearch(i);
        if (viewFocusSearch == null || !viewFocusSearch.getClass().isAssignableFrom(EditTextMultiLineNoEnter.class)) {
            return;
        }
        try {
            EditTextMultiLineNoEnter editTextMultiLineNoEnter = (EditTextMultiLineNoEnter) viewFocusSearch;
            editTextMultiLineNoEnter.requestFocus();
            editTextMultiLineNoEnter.setSelection(editTextMultiLineNoEnter.getText().length());
        } catch (ClassCastException e) {
            Log.e(Constants.TAG, "Cast exception on focus", e);
        }
    }

    public void cloneStyles(EditText editText) {
        if (editText != null) {
            Drawable background = editText.getBackground();
            if (Build.VERSION.SDK_INT < 16) {
                getEditText().setBackgroundDrawable(background);
            } else {
                getEditText().setBackground(background);
            }
            getEditText().setTypeface(editText.getTypeface());
            getEditText().setTextSize(0, editText.getTextSize());
            getEditText().setTextColor(editText.getTextColors());
            getEditText().setLinkTextColor(editText.getLinkTextColors());
        }
    }

    void setCheckListChangedListener(CheckListChangedListener checkListChangedListener) {
        this.mCheckListChangedListener = checkListChangedListener;
    }

    void setUndoBarEnabled(boolean z) {
        this.undoBarEnabled = z;
    }

    void setUndoBarContainerView(View view) {
        this.undoBarContainerView = view;
    }

    public boolean isHintItem() {
        return !getCheckBox().isEnabled();
    }

    @Override // it.feio.android.checklistview.interfaces.EditTextEventListener
    public void onDeletePressed() {
        if (isHintItem() || getText().length() != 0 || this.deleteIcon == null) {
            return;
        }
        focusView(33);
        ((ViewGroup) getParent()).removeView(this);
        this.mCheckListEventListener.onLineDeleted(this);
    }

    @Override // android.view.View
    public void setOnDragListener(final View.OnDragListener onDragListener) {
        super.setOnDragListener(onDragListener);
        getEditText().setOnDragListener(new View.OnDragListener() { // from class: it.feio.android.checklistview.models.CheckListViewItem.3
            @Override // android.view.View.OnDragListener
            public boolean onDrag(View view, DragEvent dragEvent) {
                return CheckListViewItem.this.manageDragEvents(view, dragEvent, onDragListener);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean manageDragEvents(View view, DragEvent dragEvent, View.OnDragListener onDragListener) {
        int action = dragEvent.getAction();
        if (action == 1) {
            return onDragListener.onDrag(view, dragEvent);
        }
        if (action == 2) {
            return false;
        }
        if (action != 3) {
            return true;
        }
        return onDragListener.onDrag(view, dragEvent);
    }

    public CheckListView getParentView() {
        return (CheckListView) getParent();
    }
}
