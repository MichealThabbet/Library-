package it.piapostolos.android.pinotes.helpers;

import it.feio.android.checklistview.interfaces.Constants;
import it.piapostolos.android.pinotes.PiNotes;
import it.piapostolos.android.pinotes.helpers.count.CountFactory;
import it.piapostolos.android.pinotes.models.Attachment;
import it.piapostolos.android.pinotes.models.Note;
import it.piapostolos.android.pinotes.models.StatsSingleNote;
import it.piapostolos.android.pinotes.utils.ConstantsBase;
import it.piapostolos.android.pinotes.utils.StorageHelper;
import it.piapostolos.android.pinotes.utils.TagsHelper;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes3.dex */
public class NotesHelper {
    public static boolean haveSameId(Note note, Note note2) {
        return (note2 == null || note2.get_id() == null || !note2.get_id().equals(note.get_id())) ? false : true;
    }

    public static StringBuilder appendContent(Note note, StringBuilder sb, boolean z) {
        if (sb.length() > 0 && (!StringUtils.isEmpty(note.getTitle()) || !StringUtils.isEmpty(note.getContent()))) {
            sb.append(System.getProperty("line.separator"));
            sb.append(System.getProperty("line.separator"));
            sb.append(ConstantsBase.MERGED_NOTES_SEPARATOR);
            sb.append(System.getProperty("line.separator"));
            sb.append(System.getProperty("line.separator"));
        }
        if (z && !StringUtils.isEmpty(note.getTitle())) {
            sb.append(note.getTitle());
        }
        if (!StringUtils.isEmpty(note.getTitle()) && !StringUtils.isEmpty(note.getContent())) {
            sb.append(System.getProperty("line.separator"));
            sb.append(System.getProperty("line.separator"));
        }
        if (!StringUtils.isEmpty(note.getContent())) {
            sb.append(note.getContent());
        }
        return sb;
    }

    public static void addAttachments(boolean z, Note note, ArrayList<Attachment> arrayList) {
        if (z) {
            Iterator<Attachment> it2 = note.getAttachmentsList().iterator();
            while (it2.hasNext()) {
                arrayList.add(StorageHelper.createAttachmentFromUri(PiNotes.getAppContext(), it2.next().getUri()));
            }
            return;
        }
        arrayList.addAll(note.getAttachmentsList());
    }

    public static Note mergeNotes(List<Note> list, boolean z) {
        ArrayList<Attachment> arrayList = new ArrayList<>();
        Note note = new Note();
        note.setTitle(list.get(0).getTitle());
        note.setArchived(list.get(0).isArchived());
        note.setCategory(list.get(0).getCategory());
        StringBuilder sb = new StringBuilder();
        String str = null;
        String recurrenceRule = null;
        Double d = null;
        Double d2 = null;
        boolean z2 = false;
        boolean z3 = false;
        for (Note note2 : list) {
            appendContent(note2, sb, z3);
            z2 = z2 || note2.isLocked().booleanValue();
            String alarm = note2.getAlarm();
            if (!StringUtils.isEmpty(alarm) && str == null) {
                recurrenceRule = note2.getRecurrenceRule();
                str = alarm;
            }
            d = (Double) ObjectUtils.defaultIfNull(d, note2.getLatitude());
            d2 = (Double) ObjectUtils.defaultIfNull(d2, note2.getLongitude());
            addAttachments(z, note2, arrayList);
            z3 = true;
        }
        note.setContent(sb.toString());
        note.setLocked(Boolean.valueOf(z2));
        note.setAlarm(str);
        note.setRecurrenceRule(recurrenceRule);
        note.setLatitude(d);
        note.setLongitude(d2);
        note.setAttachmentsList(arrayList);
        return note;
    }

    public static StatsSingleNote getNoteInfos(Note note) {
        StatsSingleNote statsSingleNote = new StatsSingleNote();
        if (note.isChecklist().booleanValue()) {
            statsSingleNote.setChecklistCompletedItemsNumber(StringUtils.countMatches(note.getContent(), Constants.CHECKED_SYM));
            statsSingleNote.setChecklistItemsNumber(statsSingleNote.getChecklistCompletedItemsNumber() + StringUtils.countMatches(note.getContent(), Constants.UNCHECKED_SYM));
        }
        statsSingleNote.setTags(TagsHelper.retrieveTags(note).size());
        int words = getWords(note);
        int chars = getChars(note);
        statsSingleNote.setWords(words);
        statsSingleNote.setChars(chars);
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        for (Attachment attachment : note.getAttachmentsList()) {
            if (ConstantsBase.MIME_TYPE_IMAGE.equals(attachment.getMime_type())) {
                i2++;
            } else if (ConstantsBase.MIME_TYPE_VIDEO.equals(attachment.getMime_type())) {
                i3++;
            } else if (ConstantsBase.MIME_TYPE_AUDIO.equals(attachment.getMime_type())) {
                i4++;
            } else if (ConstantsBase.MIME_TYPE_SKETCH.equals(attachment.getMime_type())) {
                i5++;
            } else if (ConstantsBase.MIME_TYPE_FILES.equals(attachment.getMime_type())) {
                i6++;
            }
            i++;
        }
        statsSingleNote.setAttachments(i);
        statsSingleNote.setImages(i2);
        statsSingleNote.setVideos(i3);
        statsSingleNote.setAudioRecordings(i4);
        statsSingleNote.setSketches(i5);
        statsSingleNote.setFiles(i6);
        if (note.getCategory() != null) {
            statsSingleNote.setCategoryName(note.getCategory().getName());
        }
        return statsSingleNote;
    }

    public static int getWords(Note note) {
        return CountFactory.getWordCounter().countWords(note);
    }

    public static int getChars(Note note) {
        return CountFactory.getWordCounter().countChars(note);
    }
}
