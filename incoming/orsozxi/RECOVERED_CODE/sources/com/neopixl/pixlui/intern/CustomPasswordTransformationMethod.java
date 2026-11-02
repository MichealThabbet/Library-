package com.neopixl.pixlui.intern;

import android.text.method.PasswordTransformationMethod;
import android.view.View;

/* loaded from: classes2.dex */
public class CustomPasswordTransformationMethod extends PasswordTransformationMethod {
    @Override // android.text.method.PasswordTransformationMethod, android.text.method.TransformationMethod
    public CharSequence getTransformation(CharSequence charSequence, View view) {
        return new CustomPasswordCharSequence(charSequence);
    }

    private class CustomPasswordCharSequence implements CharSequence {
        private CharSequence mSource;

        @Override // java.lang.CharSequence
        public char charAt(int i) {
            return '*';
        }

        public CustomPasswordCharSequence(CharSequence charSequence) {
            setSource(charSequence);
        }

        @Override // java.lang.CharSequence
        public int length() {
            return getSource().length();
        }

        @Override // java.lang.CharSequence
        public CharSequence subSequence(int i, int i2) {
            return getSource().subSequence(i, i2);
        }

        private CharSequence getSource() {
            return this.mSource;
        }

        private void setSource(CharSequence charSequence) {
            this.mSource = charSequence;
        }
    }
}
