package it.orsozoxi.android.orsonotes.models;

import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.core.view.ViewPropertyAnimatorListenerAdapter;
import it.feio.android.checklistview.utils.AlphaManager;
import it.orsozoxi.android.orsonotes.R;
import java.util.Locale;

/* loaded from: classes3.dex */
public class UndoBarController {
    private boolean isVisible;
    private ViewPropertyAnimatorCompat mBarAnimator;
    private View mBarView;
    private Button mButtonView;
    private TextView mMessageView;
    private UndoListener mUndoListener;
    private CharSequence mUndoMessage;
    private Parcelable mUndoToken;

    public interface UndoListener {
        void onUndo(Parcelable parcelable);
    }

    public UndoBarController(View view, UndoListener undoListener) {
        this.mBarView = view;
        this.mBarAnimator = ViewCompat.animate(view);
        this.mUndoListener = undoListener;
        this.mMessageView = (TextView) this.mBarView.findViewById(R.id.undobar_message);
        Button button = (Button) this.mBarView.findViewById(R.id.undobar_button);
        this.mButtonView = button;
        button.setText(button.getText().toString().toUpperCase(Locale.getDefault()));
        this.mButtonView.setOnClickListener(new View.OnClickListener() { // from class: it.orsozoxi.android.orsonotes.models.UndoBarController.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                UndoBarController.this.hideUndoBar(false);
                UndoBarController.this.mUndoListener.onUndo(UndoBarController.this.mUndoToken);
            }
        });
        hideUndoBar(false);
    }

    public void showUndoBar(boolean z, CharSequence charSequence, Parcelable parcelable) {
        this.mUndoToken = parcelable;
        this.mUndoMessage = charSequence;
        this.mMessageView.setText(charSequence);
        this.mBarView.setVisibility(0);
        if (z) {
            AlphaManager.setAlpha(this.mBarView, 1.0f);
        } else {
            this.mBarAnimator.cancel();
            this.mBarAnimator.alpha(1.0f).setDuration(this.mBarView.getResources().getInteger(android.R.integer.config_shortAnimTime)).setListener(null);
        }
        this.isVisible = true;
    }

    public void hideUndoBar(boolean z) {
        if (z) {
            this.mBarView.setVisibility(8);
            AlphaManager.setAlpha(this.mBarView, 0.0f);
            this.mUndoMessage = null;
            this.mUndoToken = null;
        } else {
            this.mBarAnimator.cancel();
            this.mBarAnimator.alpha(0.0f).setDuration(this.mBarView.getResources().getInteger(android.R.integer.config_shortAnimTime)).setListener(new ViewPropertyAnimatorListenerAdapter() { // from class: it.orsozoxi.android.orsonotes.models.UndoBarController.2
                @Override // androidx.core.view.ViewPropertyAnimatorListenerAdapter, androidx.core.view.ViewPropertyAnimatorListener
                public void onAnimationEnd(View view) {
                    super.onAnimationEnd(view);
                    UndoBarController.this.mBarView.setVisibility(8);
                    UndoBarController.this.mUndoMessage = null;
                    UndoBarController.this.mUndoToken = null;
                }
            });
        }
        this.isVisible = false;
    }

    public void onSaveInstanceState(Bundle bundle) {
        bundle.putCharSequence("undo_message", this.mUndoMessage);
        bundle.putParcelable("undo_token", this.mUndoToken);
    }

    public void onRestoreInstanceState(Bundle bundle) {
        if (bundle != null) {
            this.mUndoMessage = bundle.getCharSequence("undo_message");
            Parcelable parcelable = bundle.getParcelable("undo_token");
            this.mUndoToken = parcelable;
            if (parcelable == null && TextUtils.isEmpty(this.mUndoMessage)) {
                return;
            }
            showUndoBar(true, this.mUndoMessage, this.mUndoToken);
        }
    }

    public boolean isVisible() {
        return this.isVisible;
    }
}
