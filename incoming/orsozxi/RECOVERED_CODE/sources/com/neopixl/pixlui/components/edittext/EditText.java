package com.neopixl.pixlui.components.edittext;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Build;
import android.os.SystemClock;
import android.provider.Settings;
import android.text.ClipboardManager;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
import com.android.export.AllCapsTransformationMethod;
import com.neopixl.pixlui.components.textview.FontFactory;
import com.neopixl.pixlui.intern.CustomPasswordTransformationMethod;
import com.neopixl.pixlui.intern.PixlUIContants;
import it.feio.android.pixlui.links.Hyperlink;
import it.feio.android.pixlui.links.InternalURLSpan;
import it.feio.android.pixlui.links.RegexPatternsConstants;
import it.feio.android.pixlui.links.TextLinkClickListener;
import it.feio.android.pixlui.links.UrlCompleter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes2.dex */
public class EditText extends android.widget.EditText implements View.OnClickListener {
    private static final String EDITTEXT_ATTRIBUTE_AUTO_FOCUS = "autofocus";
    private static final String EDITTEXT_ATTRIBUTE_CANCEL_CLIPBOARD_CONTENT = "clearclipboardcontent";
    private static final String EDITTEXT_ATTRIBUTE_COPY_AND_PASTE = "copyandpaste";
    private static final String EDITTEXT_ATTRIBUTE_FONT_NAME = "typeface";
    private static final String EDITTEXT_KEYBOARD_LG = "com.lge.ime/.LgeImeImpl";
    private static final String EDITTEXT_KEYBOARD_OLD_SAMSUNG = "com.sec.android.inputmethod.axt9/.AxT9IME";
    private static final String EDITTEXT_KEYBOARD_SAMSUNG = "com.sec.android.inputmethod/.SamsungKeypad";
    private static final String EDITTEXT_KEYBOARD_SENSE = "com.htc.android.htcime/.HTCIMEService";
    private static final String EDITTEXT_KEYBOARD_SWIFTKEY = "com.touchtype.swiftkey/com.touchtype.KeyboardService";
    private static final String EDITTEXT_KEYBOARD_SWIFTKEY_TRIAL = "com.touchtype.swiftkey.phone.trial/com.touchtype.KeyboardService";
    private static final String EDITTEXT_KEYBOARD_SWYPE_DRAGON = "com.nuance.swype.trial/com.nuance.swype.input.IME";
    private static final String EDITTEXT_OS_ATTRIBUTE_TEXT_ALL_CAPS = "textAllCaps";
    private SpannableString linkableText;
    private ArrayList<Hyperlink> listOfLinks;
    private EditTextBatchListener listenerBatch;
    private EditTextFocusListener listenerFocus;
    private CustomTextWatcher listenerTextWatcherInternal;
    private boolean mAutoFocus;
    private boolean mCustomPassWordTransformation;
    private InputMethodManager mImm;
    private boolean mOldDeviceKeyboard;
    private boolean mOldDeviceTextAllCaps;
    private WeakReference<TextLinkClickListener> textLinkClickListenerWeakReference;

    @Override // android.widget.TextView, android.view.View
    public InputConnection onCreateInputConnection(EditorInfo editorInfo) {
        return new CustomInputConnection(super.onCreateInputConnection(editorInfo), true, this);
    }

    public EditText(Context context) {
        super(context);
        editTextVersion();
        setOnClickListener(this);
        setLinkTextColor(getTextColors());
    }

    public EditText(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        editTextVersion();
        setCustomFont(context, attributeSet);
        setDisableCopyAndPaste(context, attributeSet);
        setCancelClipboard(context, attributeSet);
        setAutoFocus(context, attributeSet);
        if (isOldDeviceTextAllCaps()) {
            setAllCaps(context, attributeSet);
        }
        setOnClickListener(this);
        setLinkTextColor(getCurrentTextColor());
    }

    public EditText(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        editTextVersion();
        setCustomFont(context, attributeSet);
        setDisableCopyAndPaste(context, attributeSet);
        setCancelClipboard(context, attributeSet);
        setAutoFocus(context, attributeSet);
        if (isOldDeviceTextAllCaps()) {
            setAllCaps(context, attributeSet);
        }
        setOnClickListener(this);
        setLinkTextColor(getCurrentTextColor());
    }

    private void editTextVersion() {
        if (Build.VERSION.SDK_INT < 14) {
            setOldDeviceKeyboard(true);
            setOldDeviceTextAllCaps(true);
        } else {
            setOldDeviceKeyboard(true);
            setOldDeviceTextAllCaps(false);
        }
        setListenerTextWatcherInternal(new CustomTextWatcher(this));
        addTextChangedListener(getListenerTextWatcherInternal());
        this.mImm = (InputMethodManager) getContext().getSystemService("input_method");
        this.mAutoFocus = false;
        this.mCustomPassWordTransformation = false;
    }

    private void setCustomFont(Context context, AttributeSet attributeSet) {
        String attributeValue = attributeSet.getAttributeValue(PixlUIContants.SCHEMA_URL, EDITTEXT_ATTRIBUTE_FONT_NAME);
        if (attributeValue != null) {
            setCustomFont(context, attributeValue);
        }
    }

    private void setDisableCopyAndPaste(Context context, AttributeSet attributeSet) {
        if (attributeSet.getAttributeBooleanValue(PixlUIContants.SCHEMA_URL, EDITTEXT_ATTRIBUTE_COPY_AND_PASTE, true) || isInEditMode()) {
            return;
        }
        disableCopyAndPaste();
    }

    private void setCancelClipboard(Context context, AttributeSet attributeSet) {
        if (!attributeSet.getAttributeBooleanValue(PixlUIContants.SCHEMA_URL, EDITTEXT_ATTRIBUTE_CANCEL_CLIPBOARD_CONTENT, false) || isInEditMode()) {
            return;
        }
        cancelClipBoardContent();
    }

    private void setAllCaps(Context context, AttributeSet attributeSet) {
        if (isInEditMode()) {
            return;
        }
        int attributeCount = attributeSet.getAttributeCount();
        boolean attributeBooleanValue = false;
        int i = 0;
        while (true) {
            if (i >= attributeCount) {
                break;
            }
            if (attributeSet.getAttributeName(i).equals(EDITTEXT_OS_ATTRIBUTE_TEXT_ALL_CAPS)) {
                attributeBooleanValue = attributeSet.getAttributeBooleanValue(i, false);
                break;
            }
            i++;
        }
        if (!attributeBooleanValue || isInEditMode()) {
            return;
        }
        setAllCaps(attributeBooleanValue);
    }

    private void setAutoFocus(Context context, AttributeSet attributeSet) {
        if (isInEditMode()) {
            return;
        }
        int attributeCount = attributeSet.getAttributeCount();
        boolean attributeBooleanValue = false;
        int i = 0;
        while (true) {
            if (i >= attributeCount) {
                break;
            }
            if (attributeSet.getAttributeName(i).equals(EDITTEXT_ATTRIBUTE_AUTO_FOCUS)) {
                attributeBooleanValue = attributeSet.getAttributeBooleanValue(i, false);
                break;
            }
            i++;
        }
        if (!attributeBooleanValue || isInEditMode()) {
            return;
        }
        setAutoFocus(attributeBooleanValue);
    }

    @Override // android.widget.TextView
    public void setAllCaps(boolean z) {
        if (!isOldDeviceTextAllCaps()) {
            super.setAllCaps(z);
        } else if (z) {
            setTransformationMethod(new AllCapsTransformationMethod(getContext()));
        } else {
            setTransformationMethod(null);
        }
    }

    public boolean setCustomFont(Context context, String str) {
        Typeface font = FontFactory.getInstance(context).getFont(str);
        if (font == null) {
            return false;
        }
        setTypeface(font);
        return true;
    }

    public void disableCopyAndPaste() {
        if (Build.VERSION.SDK_INT < 11) {
            setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() { // from class: com.neopixl.pixlui.components.edittext.EditText.1
                @Override // android.view.View.OnCreateContextMenuListener
                public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
                    contextMenu.clear();
                }
            });
        } else {
            setCustomSelectionActionModeCallback(new ActionMode.Callback() { // from class: com.neopixl.pixlui.components.edittext.EditText.2
                @Override // android.view.ActionMode.Callback
                public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                    return false;
                }

                @Override // android.view.ActionMode.Callback
                public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                    return false;
                }

                @Override // android.view.ActionMode.Callback
                public void onDestroyActionMode(ActionMode actionMode) {
                }

                @Override // android.view.ActionMode.Callback
                public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                    return false;
                }
            });
        }
    }

    public void cancelClipBoardContent() {
        if (Build.VERSION.SDK_INT < 11) {
            ClipboardManager clipboardManager = (ClipboardManager) getContext().getSystemService("clipboard");
            if (clipboardManager != null) {
                clipboardManager.setText("");
                return;
            }
            return;
        }
        android.content.ClipboardManager clipboardManager2 = (android.content.ClipboardManager) getContext().getSystemService("clipboard");
        if (clipboardManager2 == null || clipboardManager2.getPrimaryClip() == null || clipboardManager2.getPrimaryClip().getItemCount() <= 0) {
            return;
        }
        clipboardManager2.setPrimaryClip(ClipData.newPlainText("", ""));
    }

    @Override // android.widget.TextView, android.view.View
    protected void onFocusChanged(boolean z, int i, Rect rect) {
        EditTextFocusListener focusListener = getFocusListener();
        if (focusListener != null) {
            if (z) {
                focusListener.requestFocus(this);
            } else {
                focusListener.loseFocus(this);
            }
        }
        super.onFocusChanged(z, i, rect);
        if (this.mAutoFocus) {
            if (z) {
                this.mImm.showSoftInput(this, 3);
            } else {
                this.mImm.hideSoftInputFromWindow(getWindowToken(), 0);
            }
            this.mImm.toggleSoftInput(0, 0);
        }
        if (!z || this.textLinkClickListenerWeakReference == null) {
            return;
        }
        onClick(this);
    }

    public void showKeyboard() {
        requestFocus();
        Context context = getContext();
        if (Activity.class.isInstance(context)) {
            ((Activity) context).getWindow().setSoftInputMode(5);
        }
        MotionEvent motionEventObtain = MotionEvent.obtain(0L, SystemClock.uptimeMillis(), 1, getMeasuredWidth(), 0.0f, 0);
        onTouchEvent(motionEventObtain);
        motionEventObtain.recycle();
    }

    public void hideKeyboard() {
        hideKeyboard(true);
    }

    public void hideKeyboard(boolean z) {
        if (z) {
            clearFocus();
        }
        Context context = getContext();
        if (Activity.class.isInstance(context)) {
            ((Activity) context).getWindow().setSoftInputMode(3);
        }
    }

    private String getKeyboardName() {
        return Settings.Secure.getString(getContext().getContentResolver(), "default_input_method");
    }

    public boolean isASenseKeyboard() {
        return getKeyboardName().equals(EDITTEXT_KEYBOARD_SENSE);
    }

    public boolean isASwypeDragonKeyboard() {
        return getKeyboardName().equals(EDITTEXT_KEYBOARD_SWYPE_DRAGON);
    }

    public boolean isASwiftKeyTrialKeyboard() {
        return getKeyboardName().equals(EDITTEXT_KEYBOARD_SWIFTKEY_TRIAL);
    }

    public boolean isASwiftKeyKeyboard() {
        return getKeyboardName().equals(EDITTEXT_KEYBOARD_SWIFTKEY);
    }

    public boolean isAOldSamsungKeyboard() {
        return getKeyboardName().equals(EDITTEXT_KEYBOARD_OLD_SAMSUNG);
    }

    public boolean isASamsungKeyboard() {
        return getKeyboardName().equals(EDITTEXT_KEYBOARD_SAMSUNG);
    }

    public boolean isALGKeyboard() {
        return getKeyboardName().equals(EDITTEXT_KEYBOARD_LG);
    }

    public boolean isUsingCustomInputMethod() {
        List<InputMethodInfo> enabledInputMethodList = ((InputMethodManager) getContext().getSystemService("input_method")).getEnabledInputMethodList();
        int size = enabledInputMethodList.size();
        for (int i = 0; i < size; i++) {
            InputMethodInfo inputMethodInfo = enabledInputMethodList.get(i);
            if (inputMethodInfo.getId().equals(Settings.Secure.getString(getContext().getContentResolver(), "default_input_method")) && (inputMethodInfo.getServiceInfo().applicationInfo.flags & 1) == 0) {
                return true;
            }
        }
        return false;
    }

    private class CustomTextWatcher implements TextWatcher {
        private EditText mEdittext;
        private String previousText;

        @Override // android.text.TextWatcher
        public void afterTextChanged(Editable editable) {
        }

        @Override // android.text.TextWatcher
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        public CustomTextWatcher(EditText editText) {
            setEdittext(editText);
        }

        @Override // android.text.TextWatcher
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            EditTextBatchListener batchListener = getEdittext().getBatchListener();
            if (batchListener != null && i3 == 1) {
                batchListener.addNewChar(getEdittext());
            }
            EditText.this.updateLinksSpan(i, i3 - i2);
        }

        public EditText getEdittext() {
            return this.mEdittext;
        }

        public void setEdittext(EditText editText) {
            this.mEdittext = editText;
        }
    }

    private class CustomInputConnection extends InputConnectionWrapper {
        private EditText mEdittext;
        private KeyEvent mKeyEvent;
        private int mLastLength;

        public CustomInputConnection(InputConnection inputConnection, boolean z, EditText editText) {
            super(inputConnection, z);
            setEdittext(editText);
        }

        @Override // android.view.inputmethod.InputConnectionWrapper, android.view.inputmethod.InputConnection
        public boolean beginBatchEdit() {
            this.mLastLength = EditText.this.length();
            this.mKeyEvent = null;
            return super.beginBatchEdit();
        }

        @Override // android.view.inputmethod.InputConnectionWrapper, android.view.inputmethod.InputConnection
        public boolean commitText(CharSequence charSequence, int i) {
            try {
                return super.commitText(charSequence, i);
            } catch (IndexOutOfBoundsException unused) {
                return true;
            }
        }

        @Override // android.view.inputmethod.InputConnectionWrapper, android.view.inputmethod.InputConnection
        public boolean sendKeyEvent(KeyEvent keyEvent) {
            this.mKeyEvent = keyEvent;
            EditTextBatchListener batchListener = getEdittext().getBatchListener();
            if (keyEvent.getKeyCode() == 67) {
                String string = getEdittext().getText().toString();
                if (batchListener != null && keyEvent.getAction() == 0) {
                    if (string.length() == 0) {
                        batchListener.deleteKeyboardButton(getEdittext(), true);
                    } else {
                        batchListener.deleteKeyboardButton(getEdittext(), false);
                    }
                }
            }
            return super.sendKeyEvent(keyEvent);
        }

        @Override // android.view.inputmethod.InputConnectionWrapper, android.view.inputmethod.InputConnection
        public boolean endBatchEdit() {
            int i;
            int length = EditText.this.length();
            EditTextBatchListener batchListener = getEdittext().getBatchListener();
            if (batchListener != null && length <= (i = this.mLastLength)) {
                if (i - length == 1) {
                    if (this.mKeyEvent == null) {
                        batchListener.deleteKeyboardButton(getEdittext(), false);
                    }
                } else if (i == 0 && length == 0 && this.mKeyEvent == null) {
                    batchListener.deleteKeyboardButton(getEdittext(), true);
                }
            }
            return super.endBatchEdit();
        }

        public EditText getEdittext() {
            return this.mEdittext;
        }

        public void setEdittext(EditText editText) {
            this.mEdittext = editText;
        }
    }

    public boolean isOldDeviceTextAllCaps() {
        return this.mOldDeviceTextAllCaps;
    }

    public void setOldDeviceTextAllCaps(boolean z) {
        this.mOldDeviceTextAllCaps = z;
    }

    public boolean isOldDeviceKeyboard() {
        return this.mOldDeviceKeyboard;
    }

    public void setOldDeviceKeyboard(boolean z) {
        this.mOldDeviceKeyboard = z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public EditTextBatchListener getBatchListener() {
        return this.listenerBatch;
    }

    public void setBatchListener(EditTextBatchListener editTextBatchListener) {
        this.listenerBatch = editTextBatchListener;
    }

    private EditTextFocusListener getFocusListener() {
        return this.listenerFocus;
    }

    public void setFocusListener(EditTextFocusListener editTextFocusListener) {
        this.listenerFocus = editTextFocusListener;
    }

    public CustomTextWatcher getListenerTextWatcherInternal() {
        return this.listenerTextWatcherInternal;
    }

    public void setListenerTextWatcherInternal(CustomTextWatcher customTextWatcher) {
        this.listenerTextWatcherInternal = customTextWatcher;
    }

    public boolean isAutoFocus() {
        return this.mAutoFocus;
    }

    public void setAutoFocus(boolean z) {
        this.mAutoFocus = z;
    }

    public boolean isCustomPassWordTransformation() {
        return this.mCustomPassWordTransformation;
    }

    public void setCustomPassWordTransformation(boolean z) {
        this.mCustomPassWordTransformation = z;
        if (z) {
            setTransformationMethod(new CustomPasswordTransformationMethod());
        }
    }

    public void gatherLinksForText() {
        String string = getText().toString();
        this.linkableText = new SpannableString(string);
        this.listOfLinks = new ArrayList<>();
        for (Pattern pattern : RegexPatternsConstants.patterns) {
            gatherLinks2(this.listOfLinks, string.split("\\s"), pattern);
        }
        for (int i = 0; i < this.listOfLinks.size(); i++) {
            Hyperlink hyperlink = this.listOfLinks.get(i);
            this.linkableText.setSpan(hyperlink.span, hyperlink.start, hyperlink.end, 33);
        }
        setText(this.linkableText);
    }

    private final void gatherLinks(ArrayList<Hyperlink> arrayList, Spannable spannable, Pattern pattern) {
        Matcher matcher = pattern.matcher(spannable);
        while (matcher.find()) {
            int iStart = matcher.start();
            int iEnd = matcher.end();
            Hyperlink hyperlink = new Hyperlink();
            hyperlink.textSpan = spannable.subSequence(iStart, iEnd);
            hyperlink.span = new InternalURLSpan(hyperlink.textSpan.toString());
            hyperlink.start = iStart;
            hyperlink.end = iEnd;
            arrayList.add(hyperlink);
        }
    }

    private final void gatherLinks2(ArrayList<Hyperlink> arrayList, String[] strArr, Pattern pattern) {
        int i = 0;
        for (String str : strArr) {
            Matcher matcher = pattern.matcher(str);
            int length = str.length() + i;
            if (matcher.matches()) {
                Hyperlink hyperlink = new Hyperlink();
                hyperlink.textSpan = str;
                hyperlink.span = new InternalURLSpan(hyperlink.textSpan.toString());
                hyperlink.start = i;
                hyperlink.end = length;
                arrayList.add(hyperlink);
            }
            i = length + 1;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateLinksSpan(int i, int i2) {
        ArrayList<Hyperlink> arrayList = this.listOfLinks;
        if (arrayList != null) {
            Iterator<Hyperlink> it2 = arrayList.iterator();
            while (it2.hasNext()) {
                Hyperlink next = it2.next();
                if (next.start > i) {
                    next.start += i2;
                    next.end += i2;
                }
            }
        }
    }

    public void setOnTextLinkClickListener(TextLinkClickListener textLinkClickListener) {
        this.textLinkClickListenerWeakReference = new WeakReference<>(textLinkClickListener);
    }

    public TextLinkClickListener getOnTextLinkClickListener() {
        WeakReference<TextLinkClickListener> weakReference = this.textLinkClickListenerWeakReference;
        if (weakReference != null) {
            return weakReference.get();
        }
        return null;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (this.textLinkClickListenerWeakReference != null) {
            int selectionStart = getSelectionStart();
            Log.v("PixlUI", "onClick() on position " + selectionStart);
            Iterator<Hyperlink> it2 = this.listOfLinks.iterator();
            while (it2.hasNext()) {
                Hyperlink next = it2.next();
                if (selectionStart > next.start && selectionStart < next.end) {
                    this.textLinkClickListenerWeakReference.get().onTextLinkClick(view, next.textSpan.toString(), UrlCompleter.complete(next.textSpan.toString()));
                }
            }
        }
    }
}
