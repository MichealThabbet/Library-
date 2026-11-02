package it.piapostolos.android.pinotes.utils;

import android.content.Context;
import android.content.res.Resources;
import it.piapostolos.android.pinotes.PiNotes;
import it.piapostolos.android.pinotes.R;
import it.piapostolos.android.pinotes.models.Category;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/* loaded from: classes3.dex */
public class Navigation {
    public static final int ARCHIVE = 1;
    public static final int CATEGORY = 5;
    public static final int NOTES = 0;
    public static final int REMINDERS = 2;
    public static final int TRASH = 3;
    public static final int UNCATEGORIZED = 4;

    private Navigation() {
    }

    public static int getNavigation() throws Resources.NotFoundException {
        String[] stringArray = PiNotes.getAppContext().getResources().getStringArray(R.array.navigation_list_codes);
        String navigationText = getNavigationText();
        if (stringArray[0].equals(navigationText)) {
            return 0;
        }
        if (stringArray[1].equals(navigationText)) {
            return 1;
        }
        if (stringArray[2].equals(navigationText)) {
            return 2;
        }
        if (stringArray[3].equals(navigationText)) {
            return 3;
        }
        return stringArray[4].equals(navigationText) ? 4 : 5;
    }

    public static String getNavigationText() throws Resources.NotFoundException {
        Context appContext = PiNotes.getAppContext();
        return appContext.getSharedPreferences(Constants.PREFS_NAME, 4).getString("navigation", appContext.getResources().getStringArray(R.array.navigation_list_codes)[0]);
    }

    public static Long getCategory() {
        if (getNavigation() == 5) {
            return Long.valueOf(PiNotes.getAppContext().getSharedPreferences(Constants.PREFS_NAME, 4).getString("navigation", ""));
        }
        return null;
    }

    public static boolean checkNavigation(int i) {
        return checkNavigation(new Integer[]{Integer.valueOf(i)});
    }

    public static boolean checkNavigation(Integer[] numArr) throws Resources.NotFoundException {
        int navigation = getNavigation();
        Iterator it2 = new ArrayList(Arrays.asList(numArr)).iterator();
        while (it2.hasNext()) {
            if (navigation == ((Integer) it2.next()).intValue()) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkNavigationCategory(Category category) {
        Context appContext = PiNotes.getAppContext();
        return category != null && appContext.getSharedPreferences(Constants.PREFS_NAME, 4).getString("navigation", appContext.getResources().getStringArray(R.array.navigation_list_codes)[0]).equals(String.valueOf(category.getId()));
    }
}
