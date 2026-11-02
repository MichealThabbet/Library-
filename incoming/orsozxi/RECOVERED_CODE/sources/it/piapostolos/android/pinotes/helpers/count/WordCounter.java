package it.piapostolos.android.pinotes.helpers.count;

import com.huawei.hms.support.hianalytics.HiAnalyticsConstant;
import it.feio.android.checklistview.interfaces.Constants;
import it.piapostolos.android.pinotes.models.Note;
import java.util.regex.Pattern;

/* loaded from: classes3.dex */
public interface WordCounter {
    int countChars(Note note);

    int countWords(Note note);

    String sanitizeTextForWordsAndCharsCount(Note note, String str);

    /* renamed from: it.piapostolos.android.pinotes.helpers.count.WordCounter$-CC, reason: invalid class name */
    public final /* synthetic */ class CC {
        public static String $default$sanitizeTextForWordsAndCharsCount(WordCounter _this, Note note, String str) {
            if (!note.isChecklist().booleanValue()) {
                return str;
            }
            return str.replaceAll("(" + Pattern.quote(Constants.CHECKED_SYM) + HiAnalyticsConstant.REPORT_VAL_SEPARATOR + Pattern.quote(Constants.UNCHECKED_SYM) + ")", "");
        }
    }
}
