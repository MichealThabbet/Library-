package it.feio.android.checklistview.models;

import android.content.Context;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import it.feio.android.checklistview.App;
import it.feio.android.checklistview.Settings;
import it.feio.android.checklistview.exceptions.ViewNotSupportedException;
import it.feio.android.checklistview.interfaces.CheckListChangedListener;
import it.feio.android.checklistview.interfaces.Constants;
import it.feio.android.pixlui.links.TextLinkClickListener;
import java.lang.ref.WeakReference;
import java.util.regex.Pattern;

/* loaded from: classes2.dex */
public class ChecklistManager {
    private CheckListChangedListener mCheckListChangedListener;
    private CheckListView mCheckListView;
    private WeakReference<Context> mContext;
    private TextLinkClickListener mTextLinkClickListener;
    private TextWatcher mTextWatcher;
    private EditText originalView;
    private View undoBarContainerView;
    private boolean undoBarEnabled = true;

    public ChecklistManager(Context context) {
        this.mContext = new WeakReference<>(context);
    }

    public ChecklistManager linesSeparator(String str) {
        Settings settings = App.getSettings();
        if (str.length() == 0) {
            str = Constants.LINES_SEPARATOR;
        }
        settings.setLinesSeparator(str);
        return this;
    }

    public ChecklistManager showDeleteIcon(boolean z) {
        App.getSettings().setShowDeleteIcon(z);
        return this;
    }

    public ChecklistManager keepChecked(boolean z) {
        App.getSettings().setKeepChecked(z);
        return this;
    }

    public ChecklistManager showCheckMarks(boolean z) {
        App.getSettings().setShowChecks(z);
        return this;
    }

    public int getMoveCheckedOnBottom() {
        return App.getSettings().getMoveCheckedOnBottom();
    }

    public ChecklistManager moveCheckedOnBottom(int i) {
        App.getSettings().setMoveCheckedOnBottom(i);
        return this;
    }

    public ChecklistManager showHintItem(boolean z) {
        App.getSettings().setShowHintItem(z);
        return this;
    }

    public String getNewEntryHint() {
        return App.getSettings().getNewEntryHint();
    }

    public ChecklistManager newEntryHint(String str) {
        showHintItem(true);
        App.getSettings().setNewEntryHint(str);
        return this;
    }

    public ChecklistManager dragEnabled(boolean z) {
        App.getSettings().setDragEnabled(z);
        return this;
    }

    public boolean getDragEnabled() {
        return App.getSettings().getDragEnabled();
    }

    public ChecklistManager dragVibrationEnabled(boolean z) {
        App.getSettings().setDragVibrationEnabled(z);
        return this;
    }

    public boolean getDragVibrationEnabled() {
        return App.getSettings().getDragVibrationEnabled();
    }

    public ChecklistManager dragVibrationDuration(int i) {
        App.getSettings().setDragVibrationDuration(i);
        return this;
    }

    public int getDragVibrationDuration() {
        return App.getSettings().getDragVibrationDuration();
    }

    public ChecklistManager disableUndoBar() {
        this.undoBarEnabled = false;
        return this;
    }

    public ChecklistManager undoBarContainerView(View view) {
        this.undoBarContainerView = view;
        return this;
    }

    public View convert(View view) throws ViewNotSupportedException {
        if (EditText.class.isAssignableFrom(view.getClass())) {
            return convert((EditText) view);
        }
        if (LinearLayout.class.isAssignableFrom(view.getClass())) {
            return convert((CheckListView) view);
        }
        return null;
    }

    private View convert(EditText editText) {
        this.originalView = editText;
        CheckListView checkListView = new CheckListView(this.mContext);
        this.mCheckListView = checkListView;
        checkListView.setMoveCheckedOnBottom(App.getSettings().getMoveCheckedOnBottom());
        this.mCheckListView.setUndoBarEnabled(this.undoBarEnabled);
        this.mCheckListView.setUndoBarContainerView(this.undoBarContainerView);
        this.mCheckListView.setShowDeleteIcon(App.getSettings().getShowDeleteIcon());
        this.mCheckListView.setNewEntryHint(App.getSettings().getNewEntryHint());
        this.mCheckListView.setId(editText.getId());
        CheckListChangedListener checkListChangedListener = this.mCheckListChangedListener;
        if (checkListChangedListener != null) {
            this.mCheckListView.setCheckListChangedListener(checkListChangedListener);
        }
        TextLinkClickListener textLinkClickListener = this.mTextLinkClickListener;
        if (textLinkClickListener != null) {
            this.mCheckListView.setOnTextLinkClickListener(textLinkClickListener);
        }
        convertToChecklist(editText.getText().toString());
        if (App.getSettings().getShowHintItem()) {
            this.mCheckListView.addHintItem();
        }
        this.mCheckListView.cloneStyles(editText);
        return this.mCheckListView;
    }

    private void convertToChecklist(String str) {
        if (str.length() > 0) {
            for (String str2 : str.split(Pattern.quote(App.getSettings().getLinesSeparator()))) {
                convertLineToChecklist(str2);
            }
        }
    }

    private void convertLineToChecklist(String str) {
        if (str.length() == 0) {
            return;
        }
        this.mCheckListView.addItem(str.replace(Constants.CHECKED_SYM, "").replace(Constants.UNCHECKED_SYM, ""), str.indexOf(Constants.CHECKED_SYM) == 0);
    }

    private View convert(CheckListView checkListView) {
        StringBuilder sb = new StringBuilder();
        removeChecked(checkListView, sb);
        this.originalView.setText(sb.toString());
        TextWatcher textWatcher = this.mTextWatcher;
        if (textWatcher != null) {
            this.originalView.addTextChangedListener(textWatcher);
        }
        this.mCheckListView = null;
        return this.originalView;
    }

    private void restoreTypography(CheckListView checkListView, EditText editText) {
        if (checkListView.getEditText() != null) {
            editText.setTypeface(checkListView.getEditText().getTypeface());
            editText.setTextSize(0, checkListView.getEditText().getTextSize());
            editText.setTextColor(checkListView.getEditText().getTextColors());
            editText.setLinkTextColor(checkListView.getEditText().getLinkTextColors());
        }
    }

    private void removeChecked(CheckListView checkListView, StringBuilder sb) {
        boolean zIsChecked;
        int i = 0;
        while (i < checkListView.getChildCount()) {
            CheckListViewItem childAt = checkListView.getChildAt(i);
            if (!childAt.isHintItem() && (!(zIsChecked = childAt.isChecked()) || (zIsChecked && App.getSettings().getKeepChecked()))) {
                sb.append(i > 0 ? App.getSettings().getLinesSeparator() : "");
                sb.append(App.getSettings().getShowChecks() ? zIsChecked ? Constants.CHECKED_SYM : Constants.UNCHECKED_SYM : "");
                sb.append(childAt.getText());
            }
            i++;
        }
    }

    public void replaceViews(View view, View view2) {
        if (view == null || view2 == null) {
            return;
        }
        ViewGroup viewGroup = (ViewGroup) view.getParent();
        int iIndexOfChild = viewGroup.indexOfChild(view);
        viewGroup.removeView(view);
        viewGroup.addView(view2, iIndexOfChild);
    }

    public void setCheckListChangedListener(CheckListChangedListener checkListChangedListener) {
        this.mCheckListChangedListener = checkListChangedListener;
    }

    public void addTextChangedListener(TextWatcher textWatcher) {
        this.mTextWatcher = textWatcher;
    }

    public String getText() {
        boolean zIsChecked;
        if (this.mCheckListView == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.mCheckListView.getChildCount(); i++) {
            CheckListViewItem childAt = this.mCheckListView.getChildAt(i);
            if (!childAt.isHintItem() && (!(zIsChecked = childAt.isChecked()) || (zIsChecked && App.getSettings().getKeepChecked()))) {
                sb.append(App.getSettings().getLinesSeparator());
                sb.append(App.getSettings().getShowChecks() ? zIsChecked ? Constants.CHECKED_SYM : Constants.UNCHECKED_SYM : "");
                sb.append(childAt.getText());
            }
        }
        return sb.length() > App.getSettings().getLinesSeparator().length() ? sb.substring(App.getSettings().getLinesSeparator().length()) : "";
    }

    public int getCheckedCount() {
        if (this.mCheckListView == null) {
            return 0;
        }
        int i = 0;
        for (int i2 = 0; i2 < this.mCheckListView.getChildCount(); i2++) {
            CheckListViewItem childAt = this.mCheckListView.getChildAt(i2);
            if (!childAt.isHintItem() && childAt.isChecked()) {
                i++;
            }
        }
        return i;
    }

    public int getCount() {
        if (this.mCheckListView == null) {
            return 0;
        }
        int i = 0;
        for (int i2 = 0; i2 < this.mCheckListView.getChildCount(); i2++) {
            if (!this.mCheckListView.getChildAt(i2).isHintItem()) {
                i++;
            }
        }
        return i;
    }

    public CheckListViewItem getFocusedItemView() {
        CheckListView checkListView = this.mCheckListView;
        if (checkListView == null || !checkListView.hasFocus()) {
            return null;
        }
        for (int i = 0; i < this.mCheckListView.getChildCount(); i++) {
            if (this.mCheckListView.getChildAt(i).hasFocus()) {
                return this.mCheckListView.getChildAt(i);
            }
        }
        return null;
    }

    public void setOnTextLinkClickListener(TextLinkClickListener textLinkClickListener) {
        this.mTextLinkClickListener = textLinkClickListener;
    }
}
