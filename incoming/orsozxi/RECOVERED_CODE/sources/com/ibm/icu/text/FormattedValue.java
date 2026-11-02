package com.ibm.icu.text;

import java.text.AttributedCharacterIterator;

/* loaded from: classes2.dex */
public interface FormattedValue extends CharSequence {
    <A extends Appendable> A appendTo(A a);

    boolean nextPosition(ConstrainedFieldPosition constrainedFieldPosition);

    AttributedCharacterIterator toCharacterIterator();

    @Override // java.lang.CharSequence
    String toString();
}
