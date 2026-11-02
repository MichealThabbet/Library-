package it.piapostolos.android.pinotes.helpers.count;

import it.piapostolos.android.pinotes.helpers.count.WordCounter;
import it.piapostolos.android.pinotes.models.Note;
import rx.Observable;
import rx.functions.Func1;

/* loaded from: classes3.dex */
public class IdeogramsWordCounter implements WordCounter {
    @Override // it.piapostolos.android.pinotes.helpers.count.WordCounter
    public /* synthetic */ String sanitizeTextForWordsAndCharsCount(Note note, String str) {
        return WordCounter.CC.$default$sanitizeTextForWordsAndCharsCount(this, note, str);
    }

    @Override // it.piapostolos.android.pinotes.helpers.count.WordCounter
    public int countWords(Note note) {
        return countChars(note);
    }

    @Override // it.piapostolos.android.pinotes.helpers.count.WordCounter
    public int countChars(Note note) {
        return Observable.from(sanitizeTextForWordsAndCharsCount(note, note.getTitle() + "\n" + note.getContent()).split("")).filter(new Func1() { // from class: it.piapostolos.android.pinotes.helpers.count.-$$Lambda$IdeogramsWordCounter$trITPwtvpKYtKqVZdAwGjSDOrMY
            @Override // rx.functions.Func1
            public final Object call(Object obj) {
                String str = (String) obj;
                return Boolean.valueOf(!str.matches("\\s"));
            }
        }).count().toBlocking().single().intValue();
    }
}
