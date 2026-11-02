package it.piapostolos.android.pinotes.helpers.count;

import it.piapostolos.android.pinotes.helpers.count.WordCounter;
import it.piapostolos.android.pinotes.models.Note;
import rx.Observable;
import rx.functions.Func1;

/* loaded from: classes3.dex */
public class DefaultWordCounter implements WordCounter {
    @Override // it.piapostolos.android.pinotes.helpers.count.WordCounter
    public /* synthetic */ String sanitizeTextForWordsAndCharsCount(Note note, String str) {
        return WordCounter.CC.$default$sanitizeTextForWordsAndCharsCount(this, note, str);
    }

    @Override // it.piapostolos.android.pinotes.helpers.count.WordCounter
    public int countWords(Note note) {
        String[] strArr = {note.getTitle(), note.getContent()};
        int i = 0;
        for (int i2 = 0; i2 < 2; i2++) {
            String strSanitizeTextForWordsAndCharsCount = sanitizeTextForWordsAndCharsCount(note, strArr[i2]);
            int length = strSanitizeTextForWordsAndCharsCount.length() - 1;
            boolean z = false;
            for (int i3 = 0; i3 < strSanitizeTextForWordsAndCharsCount.length(); i3++) {
                if (Character.isLetter(strSanitizeTextForWordsAndCharsCount.charAt(i3)) && i3 != length) {
                    z = true;
                } else if (!Character.isLetter(strSanitizeTextForWordsAndCharsCount.charAt(i3)) && z) {
                    i++;
                    z = false;
                } else if (Character.isLetter(strSanitizeTextForWordsAndCharsCount.charAt(i3)) && i3 == length) {
                    i++;
                }
            }
        }
        return i;
    }

    @Override // it.piapostolos.android.pinotes.helpers.count.WordCounter
    public int countChars(Note note) {
        return Observable.from(sanitizeTextForWordsAndCharsCount(note, note.getTitle() + "\n" + note.getContent()).split("")).filter(new Func1() { // from class: it.piapostolos.android.pinotes.helpers.count.-$$Lambda$DefaultWordCounter$N0F2o-ltitPxwH7Olgo4utht_9s
            @Override // rx.functions.Func1
            public final Object call(Object obj) {
                String str = (String) obj;
                return Boolean.valueOf(!str.matches("\\s"));
            }
        }).count().toBlocking().single().intValue();
    }
}
