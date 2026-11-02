package it.feio.android.checklistview.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;
import androidx.constraintlayout.solver.widgets.analyzer.BasicMeasure;
import com.neopixl.pixlui.components.edittext.EditText;
import it.feio.android.checklistview.interfaces.EditTextEventListener;

/* loaded from: classes2.dex */
public class EditTextMultiLineNoEnter extends EditText {
    private EditTextEventListener mEditTextEventListener;

    public EditTextMultiLineNoEnter(Context context) {
        super(context.getApplicationContext());
    }

    public EditTextMultiLineNoEnter(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public EditTextMultiLineNoEnter(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // com.neopixl.pixlui.components.edittext.EditText, android.widget.TextView, android.view.View
    public InputConnection onCreateInputConnection(EditorInfo editorInfo) {
        InputConnection inputConnectionOnCreateInputConnection = super.onCreateInputConnection(editorInfo);
        if ((editorInfo.imeOptions & BasicMeasure.EXACTLY) != 0) {
            editorInfo.imeOptions &= -1073741825;
        }
        return new DelCatcherInputConnection(inputConnectionOnCreateInputConnection, true);
    }

    public void setEditTextEventListener(EditTextEventListener editTextEventListener) {
        this.mEditTextEventListener = editTextEventListener;
    }

    private class DelCatcherInputConnection extends InputConnectionWrapper {
        public DelCatcherInputConnection(InputConnection inputConnection, boolean z) {
            super(inputConnection, z);
        }

        @Override // android.view.inputmethod.InputConnectionWrapper, android.view.inputmethod.InputConnection
        public boolean sendKeyEvent(KeyEvent keyEvent) {
            if (keyEvent.getAction() == 0 && keyEvent.getKeyCode() == 67) {
                EditTextMultiLineNoEnter.this.mEditTextEventListener.onDeletePressed();
            }
            return super.sendKeyEvent(keyEvent);
        }

        @Override // android.view.inputmethod.InputConnectionWrapper, android.view.inputmethod.InputConnection
        public boolean deleteSurroundingText(int i, int i2) {
            EditTextMultiLineNoEnter.this.mEditTextEventListener.onDeletePressed();
            return super.deleteSurroundingText(i, i2);
        }
    }
}
