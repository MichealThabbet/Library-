package it.orsozoxi.android.orsonotes.helpers.count;

import it.orsozoxi.android.orsonotes.helpers.count.WordCounter;
import it.orsozoxi.android.orsonotes.models.Note;
import rx.Observable;
import rx.functions.Func1;

/* loaded from: classes3.dex */
public class IdeogramsWordCounter implements WordCounter {
    @Override // it.orsozoxi.android.orsonotes.helpers.count.WordCounter
    public /* synthetic */ String sanitizeTextForWordsAndCharsCount(Note note, String str) {
        return WordCounter.CC.$default$sanitizeTextForWordsAndCharsCount(this, note, str);
    }

    @Override // it.orsozoxi.android.orsonotes.helpers.count.WordCounter
    public int countWords(Note note) {
        return countChars(note);
    }

    @Override // it.orsozoxi.android.orsonotes.helpers.count.WordCounter
    public int countChars(Note note) {
        return Observable.from(sanitizeTextForWordsAndCharsCount(note, note.getTitle() + "\n" + note.getContent()).split("")).filter(new Func1() { // from class: it.orsozoxi.android.orsonotes.helpers.count.-$$Lambda$IdeogramsWordCounter$trITPwtvpKYtKqVZdAwGjSDOrMY
            @Override // rx.functions.Func1
            public final Object call(Object obj) {
                String str = (String) obj;
                return Boolean.valueOf(!str.matches("\\s"));
            }
        }).count().toBlocking().single().intValue();
    }
}
