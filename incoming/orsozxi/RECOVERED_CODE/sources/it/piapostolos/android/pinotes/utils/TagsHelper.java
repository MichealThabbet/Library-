package it.piapostolos.android.pinotes.utils;

import androidx.core.util.Pair;
import it.feio.android.pixlui.links.UrlCompleter;
import it.piapostolos.android.pinotes.db.DbHelper;
import it.piapostolos.android.pinotes.models.Note;
import it.piapostolos.android.pinotes.models.Tag;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import rx.Observable;
import rx.functions.Func1;
import rx.functions.Func2;

/* loaded from: classes3.dex */
public class TagsHelper {
    public static List<Tag> getAllTags() {
        return DbHelper.getInstance().getTags();
    }

    public static HashMap<String, Integer> retrieveTags(Note note) {
        HashMap<String, Integer> map = new HashMap<>();
        for (String str : (note.getTitle() + " " + note.getContent()).replaceAll("\n", " ").trim().split(" ")) {
            String hashtag = UrlCompleter.parseHashtag(str);
            if (StringUtils.isNotEmpty(hashtag)) {
                map.put(hashtag, Integer.valueOf((map.get(hashtag) == null ? 0 : map.get(hashtag).intValue()) + 1));
            }
        }
        return map;
    }

    public static Pair<String, List<Tag>> addTagToNote(List<Tag> list, Integer[] numArr, Note note) {
        StringBuilder sb = new StringBuilder();
        ArrayList arrayList = new ArrayList();
        HashMap<String, Integer> mapRetrieveTags = retrieveTags(note);
        List listAsList = Arrays.asList(numArr);
        for (int i = 0; i < list.size(); i++) {
            if (mapContainsTag(mapRetrieveTags, list.get(i))) {
                if (!listAsList.contains(Integer.valueOf(i))) {
                    arrayList.add(list.get(i));
                }
            } else if (listAsList.contains(Integer.valueOf(i))) {
                if (sb.length() > 0) {
                    sb.append(" ");
                }
                sb.append(list.get(i));
            }
        }
        return Pair.create(sb.toString(), arrayList);
    }

    private static boolean mapContainsTag(HashMap<String, Integer> map, Tag tag) {
        Iterator<String> it2 = map.keySet().iterator();
        while (it2.hasNext()) {
            if (it2.next().equals(tag.getText())) {
                return true;
            }
        }
        return false;
    }

    public static Pair<String, String> removeTag(String str, String str2, List<Tag> list) {
        for (final Tag tag : list) {
            if (StringUtils.isNotEmpty(str)) {
                str = (String) Observable.from(str.replaceAll(ConstantsBase.TAG_SPECIAL_CHARS_TO_REMOVE, " ").split("\\s")).map(new Func1() { // from class: it.piapostolos.android.pinotes.utils.-$$Lambda$6rauPI2WqeSTthaJGOytxTiesGI
                    @Override // rx.functions.Func1
                    public final Object call(Object obj) {
                        return ((String) obj).trim();
                    }
                }).filter(new Func1() { // from class: it.piapostolos.android.pinotes.utils.-$$Lambda$TagsHelper$cSq6mhBvQrEpjgJSXUyUQIw-ADA
                    @Override // rx.functions.Func1
                    public final Object call(Object obj) {
                        Tag tag2 = tag;
                        String str3 = (String) obj;
                        return Boolean.valueOf(!str3.matches(tag2.getText()));
                    }
                }).reduce(new Func2() { // from class: it.piapostolos.android.pinotes.utils.-$$Lambda$TagsHelper$NRZCiu5NqZfLQQmAifEM15dx6sM
                    @Override // rx.functions.Func2
                    public final Object call(Object obj, Object obj2) {
                        return TagsHelper.lambda$removeTag$1((String) obj, (String) obj2);
                    }
                }).toBlocking().singleOrDefault("");
            }
            if (StringUtils.isNotEmpty(str2)) {
                str2 = (String) Observable.from(str2.replaceAll(ConstantsBase.TAG_SPECIAL_CHARS_TO_REMOVE, " ").split("\\s")).map(new Func1() { // from class: it.piapostolos.android.pinotes.utils.-$$Lambda$6rauPI2WqeSTthaJGOytxTiesGI
                    @Override // rx.functions.Func1
                    public final Object call(Object obj) {
                        return ((String) obj).trim();
                    }
                }).filter(new Func1() { // from class: it.piapostolos.android.pinotes.utils.-$$Lambda$TagsHelper$ij1gErmIF1iivGzUcvZKU8pHX6s
                    @Override // rx.functions.Func1
                    public final Object call(Object obj) {
                        Tag tag2 = tag;
                        String str3 = (String) obj;
                        return Boolean.valueOf(!str3.matches(tag2.getText()));
                    }
                }).reduce(new Func2() { // from class: it.piapostolos.android.pinotes.utils.-$$Lambda$TagsHelper$t-8ekVYcdNtpp4szBEsVYeJmo28
                    @Override // rx.functions.Func2
                    public final Object call(Object obj, Object obj2) {
                        return TagsHelper.lambda$removeTag$3((String) obj, (String) obj2);
                    }
                }).toBlocking().singleOrDefault("");
            }
        }
        return new Pair<>(str, str2);
    }

    static /* synthetic */ String lambda$removeTag$1(String str, String str2) {
        return str + " " + str2;
    }

    static /* synthetic */ String lambda$removeTag$3(String str, String str2) {
        return str + " " + str2;
    }

    public static String[] getTagsArray(List<Tag> list) {
        String[] strArr = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            strArr[i] = list.get(i).getText().substring(1) + " (" + list.get(i).getCount() + ")";
        }
        return strArr;
    }

    public static Integer[] getPreselectedTagsArray(Note note, List<Tag> list) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(note);
        return getPreselectedTagsArray(arrayList, list);
    }

    public static Integer[] getPreselectedTagsArray(List<Note> list, List<Tag> list2) {
        if (list.size() != 1) {
            return new Integer[0];
        }
        ArrayList arrayList = new ArrayList();
        for (String str : retrieveTags(list.get(0)).keySet()) {
            Iterator<Tag> it2 = list2.iterator();
            while (true) {
                if (it2.hasNext()) {
                    Tag next = it2.next();
                    if (next.getText().equals(str)) {
                        arrayList.add(Integer.valueOf(list2.indexOf(next)));
                        break;
                    }
                }
            }
        }
        return (Integer[]) arrayList.toArray(new Integer[arrayList.size()]);
    }
}
