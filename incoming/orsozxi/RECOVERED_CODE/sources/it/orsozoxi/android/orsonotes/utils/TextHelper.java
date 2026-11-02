package it.orsozoxi.android.orsonotes.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.Html;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextUtils;
import it.orsozoxi.android.orsonotes.R;
import it.orsozoxi.android.orsonotes.helpers.date.DateHelper;
import it.orsozoxi.android.orsonotes.models.Note;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes3.dex */
public class TextHelper {
    public static Spanned[] parseTitleAndContent(Context context, Note note) {
        Spanned spannedString;
        String title = note.getTitle();
        String strLimit = limit(note.getContent().trim(), 300, false, true);
        if (note.isLocked().booleanValue() && !context.getSharedPreferences(Constants.PREFS_NAME, 4).getBoolean("settings_password_access", false)) {
            if (!note.getTitle().equals(title) && title.length() > 3) {
                title = limit(title, 4, false, false);
            }
            strLimit = "";
        }
        if (note.isChecklist().booleanValue() && !TextUtils.isEmpty(strLimit)) {
            spannedString = Html.fromHtml(strLimit.replace(it.feio.android.checklistview.interfaces.Constants.CHECKED_SYM, it.feio.android.checklistview.interfaces.Constants.CHECKED_ENTITY).replace(it.feio.android.checklistview.interfaces.Constants.UNCHECKED_SYM, it.feio.android.checklistview.interfaces.Constants.UNCHECKED_ENTITY).replace(System.getProperty("line.separator"), "<br/>"));
        } else {
            spannedString = new SpannedString(strLimit);
        }
        return new Spanned[]{new SpannedString(title), spannedString};
    }

    private static String limit(String str, int i, boolean z, boolean z2) {
        StringBuilder sb = new StringBuilder(str);
        int iIndexOf = sb.indexOf(System.getProperty("line.separator"));
        if (z && iIndexOf < i) {
            i = iIndexOf;
        } else if (i >= sb.length()) {
            i = -1;
        }
        if (i != -1) {
            sb.setLength(i);
            if (z2) {
                sb.append("...");
            }
        }
        return sb.toString();
    }

    public static String capitalize(String str) {
        return str.substring(0, 1).toUpperCase(Locale.getDefault()) + str.substring(1).toLowerCase(Locale.getDefault());
    }

    public static String checkIntentCategory(String str) {
        Matcher matcher = Pattern.compile("category_id\\s*=\\s*([\\d]+)").matcher(str);
        if (!matcher.find() || matcher.group(1) == null) {
            return null;
        }
        return matcher.group(1).trim();
    }

    public static String getDateText(Context context, Note note, int i) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.PREFS_NAME, 4);
        String string = 2 == i ? "alarm" : sharedPreferences.getString(ConstantsBase.PREF_SORTING_COLUMN, "");
        char c = 65535;
        int iHashCode = string.hashCode();
        if (iHashCode != 92895825) {
            if (iHashCode == 1820421855 && string.equals("creation")) {
                c = 0;
            }
        } else if (string.equals("alarm")) {
            c = 1;
        }
        if (c == 0) {
            return context.getString(R.string.creation) + " " + DateHelper.getFormattedDate(note.getCreation(), sharedPreferences.getBoolean(ConstantsBase.PREF_PRETTIFIED_DATES, true));
        }
        if (c == 1) {
            if (note.getAlarm() == null) {
                return context.getString(R.string.no_reminder_set);
            }
            return context.getString(R.string.alarm_set_on) + " " + DateHelper.getDateTimeShort(context, Long.valueOf(Long.parseLong(note.getAlarm())));
        }
        return context.getString(R.string.last_update) + " " + DateHelper.getFormattedDate(note.getLastModification(), sharedPreferences.getBoolean(ConstantsBase.PREF_PRETTIFIED_DATES, true));
    }

    public static String getAlternativeTitle(Context context, Note note, Spanned spanned) {
        if (spanned.length() > 0) {
            return spanned.toString();
        }
        return context.getString(R.string.note) + " " + context.getString(R.string.creation) + " " + DateHelper.getDateTimeShort(context, note.getCreation());
    }
}
