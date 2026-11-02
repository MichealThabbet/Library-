package com.ibm.icu.text;

import com.ibm.icu.impl.CharacterIteration;
import com.ibm.icu.lang.UCharacter;
import com.ibm.icu.text.DictionaryBreakEngine;
import java.text.CharacterIterator;

/* loaded from: classes2.dex */
final class UnhandledBreakEngine implements LanguageBreakEngine {
    volatile UnicodeSet fHandled = new UnicodeSet();

    @Override // com.ibm.icu.text.LanguageBreakEngine
    public boolean handles(int i) {
        return this.fHandled.contains(i);
    }

    @Override // com.ibm.icu.text.LanguageBreakEngine
    public int findBreaks(CharacterIterator characterIterator, int i, int i2, DictionaryBreakEngine.DequeI dequeI) {
        UnicodeSet unicodeSet = this.fHandled;
        int iCurrent32 = CharacterIteration.current32(characterIterator);
        while (characterIterator.getIndex() < i2 && unicodeSet.contains(iCurrent32)) {
            CharacterIteration.next32(characterIterator);
            iCurrent32 = CharacterIteration.current32(characterIterator);
        }
        return 0;
    }

    public void handleChar(int i) {
        UnicodeSet unicodeSet = this.fHandled;
        if (unicodeSet.contains(i)) {
            return;
        }
        int intPropertyValue = UCharacter.getIntPropertyValue(i, 4106);
        UnicodeSet unicodeSet2 = new UnicodeSet();
        unicodeSet2.applyIntPropertyValue(4106, intPropertyValue);
        unicodeSet2.addAll(unicodeSet);
        this.fHandled = unicodeSet2;
    }
}
