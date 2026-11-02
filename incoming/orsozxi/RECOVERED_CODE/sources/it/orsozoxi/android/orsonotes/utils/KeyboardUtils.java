package it.orsozoxi.android.orsonotes.utils;

import android.view.View;
import android.view.inputmethod.InputMethodManager;
import it.orsozoxi.android.orsonotes.NotesMainActivity;

/* loaded from: classes3.dex */
public class KeyboardUtils {
    private KeyboardUtils() {
    }

    public static void showKeyboard(View view) {
        if (view == null) {
            return;
        }
        view.requestFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getSystemService("input_method");
        inputMethodManager.showSoftInput(view, 1);
        ((InputMethodManager) view.getContext().getSystemService("input_method")).showSoftInput(view, 0);
        if (isKeyboardShowed(view)) {
            return;
        }
        inputMethodManager.toggleSoftInput(2, 0);
    }

    public static boolean isKeyboardShowed(View view) {
        if (view == null) {
            return false;
        }
        return ((InputMethodManager) view.getContext().getSystemService("input_method")).isActive(view);
    }

    public static void hideKeyboard(View view) {
        if (view == null) {
            return;
        }
        InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getSystemService("input_method");
        if (inputMethodManager.isActive()) {
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void hideKeyboard(NotesMainActivity notesMainActivity) {
        notesMainActivity.getWindow().setSoftInputMode(3);
    }
}
