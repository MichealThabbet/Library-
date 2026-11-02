package com.app.piapostolos;

import android.app.Activity;
import com.app.piapostolos.Beans.Bible;
import com.ibm.icu.util.CopticCalendar;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes.dex */
public class PsalmodySanawyReaders {
    private Activity activity;
    public boolean after5;
    String[] books;
    Dictionary<Integer, String> d;
    public String eidName;
    List<Bible> legendList;
    ArrayList<String> levels;
    HashMap<String, ArrayList<String>> levelsMap;
    private String realDayName;

    private String dayOfWeek(int i) {
        return i == 1 ? "Sun" : i == 2 ? "Mon" : i == 3 ? "Tue" : i == 4 ? "Wed" : i == 5 ? "Thu" : i == 6 ? "Fri" : "Sat";
    }

    private void loadingA7ad(int i, int i2) {
    }

    private void loadingAyam(int i, int i2) {
    }

    private void namekiahEidName(int i, String str) {
    }

    public PsalmodySanawyReaders(Activity activity, boolean z) {
        this.eidName = "";
        this.after5 = false;
        this.realDayName = "";
        this.legendList = new ArrayList();
        this.activity = activity;
        this.levelsMap = new HashMap<>();
        this.levels = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.set(11, calendar.get(11));
        CopticCalendar copticCalendar = new CopticCalendar();
        calculateEvents(copticCalendar.get(5), copticCalendar.get(2), copticCalendar.get(1), calendar.get(5), calendar.get(2), calendar.get(1), dayOfWeek(calendar.get(7)));
        this.realDayName = dayOfWeek(calendar.get(7));
        if (!z || calendar.get(11) < 17) {
            return;
        }
        this.after5 = true;
        this.eidName = "";
        this.legendList = new ArrayList();
        this.levelsMap = new HashMap<>();
        this.levels = new ArrayList<>();
        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(11, calendar.get(11));
        CopticCalendar copticCalendar2 = new CopticCalendar();
        calendar2.set(6, calendar2.get(6) + 1);
        copticCalendar2.set(6, copticCalendar2.get(6) + 1);
        calculateEvents(copticCalendar2.get(5), copticCalendar2.get(2), copticCalendar2.get(1), calendar2.get(5), calendar2.get(2), calendar2.get(1), dayOfWeek(calendar2.get(7)));
    }

    public Calendar getOrthodoxEaster(int i) {
        Calendar calendar = Calendar.getInstance();
        int i2 = (((i % 19) * 19) + 15) % 30;
        int i3 = ((((((i % 4) * 2) + ((i % 7) * 4)) + (i2 * 6)) + 6) % 7) + i2 + 13;
        if (i3 > 39) {
            calendar.set(i, 4, i3 - 39);
        } else if (i3 > 9) {
            calendar.set(i, 3, i3 - 9);
        } else {
            calendar.set(i, 2, i3 + 22);
        }
        return calendar;
    }

    /* JADX WARN: Removed duplicated region for block: B:130:0x0293  */
    /* JADX WARN: Removed duplicated region for block: B:131:0x0298  */
    /* JADX WARN: Removed duplicated region for block: B:161:0x02f9  */
    /* JADX WARN: Removed duplicated region for block: B:186:0x0369  */
    /* JADX WARN: Removed duplicated region for block: B:193:0x037c  */
    /* JADX WARN: Removed duplicated region for block: B:242:0x042a  */
    /* JADX WARN: Removed duplicated region for block: B:265:0x0468  */
    /* JADX WARN: Removed duplicated region for block: B:296:0x04bf  */
    /* JADX WARN: Removed duplicated region for block: B:297:0x04c2  */
    /* JADX WARN: Removed duplicated region for block: B:317:0x050e  */
    /* JADX WARN: Removed duplicated region for block: B:319:0x051a  */
    /* JADX WARN: Removed duplicated region for block: B:344:0x0558  */
    /* JADX WARN: Removed duplicated region for block: B:466:0x06b4  */
    /* JADX WARN: Removed duplicated region for block: B:474:0x06cc  */
    /* JADX WARN: Removed duplicated region for block: B:482:0x06e4  */
    /* JADX WARN: Removed duplicated region for block: B:490:0x06fe  */
    /* JADX WARN: Removed duplicated region for block: B:498:0x0716  */
    /* JADX WARN: Removed duplicated region for block: B:525:0x076b  */
    /* JADX WARN: Removed duplicated region for block: B:601:0x0865  */
    /* JADX WARN: Removed duplicated region for block: B:659:0x08e9  */
    /* JADX WARN: Removed duplicated region for block: B:753:0x09c7  */
    /* JADX WARN: Removed duplicated region for block: B:755:0x09cb  */
    /* JADX WARN: Removed duplicated region for block: B:759:0x09dc  */
    /* JADX WARN: Removed duplicated region for block: B:764:0x09e7  */
    /* JADX WARN: Removed duplicated region for block: B:768:0x09f6  */
    /* JADX WARN: Removed duplicated region for block: B:772:0x0a05  */
    /* JADX WARN: Removed duplicated region for block: B:775:0x0a12  */
    /* JADX WARN: Removed duplicated region for block: B:779:0x0a21  */
    /* JADX WARN: Removed duplicated region for block: B:783:0x0a30  */
    /* JADX WARN: Removed duplicated region for block: B:787:0x0a3f  */
    /* JADX WARN: Removed duplicated region for block: B:791:0x0a4e  */
    /* JADX WARN: Removed duplicated region for block: B:795:0x0a55  */
    /* JADX WARN: Removed duplicated region for block: B:800:0x0a66  */
    /* JADX WARN: Removed duplicated region for block: B:802:0x0a6a  */
    /* JADX WARN: Removed duplicated region for block: B:805:0x0a77  */
    /* JADX WARN: Removed duplicated region for block: B:810:0x0a88  */
    /* JADX WARN: Removed duplicated region for block: B:815:0x0a92  */
    /* JADX WARN: Removed duplicated region for block: B:817:0x0a95  */
    /* JADX WARN: Removed duplicated region for block: B:820:0x0a9b  */
    /* JADX WARN: Removed duplicated region for block: B:825:0x0aad  */
    /* JADX WARN: Removed duplicated region for block: B:835:0x0ac0 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:837:0x0acc  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public java.lang.String calculateEvents(int r18, int r19, int r20, int r21, int r22, int r23, java.lang.String r24) {
        /*
            Method dump skipped, instructions count: 2851
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.piapostolos.PsalmodySanawyReaders.calculateEvents(int, int, int, int, int, int, java.lang.String):java.lang.String");
    }

    private int isCalendarsEqual(Calendar calendar, Calendar calendar2) {
        int iIntValue = Integer.valueOf(((calendar.getTimeInMillis() - calendar2.getTimeInMillis()) / 86400000) + "").intValue();
        if (iIntValue == 0) {
            return 0;
        }
        return iIntValue > 0 ? 1 : -1;
    }

    private String eitAppendDayName(String str, String str2) {
        if (str2.equals("Sat")) {
            return str + "sat";
        }
        if (str2.equals("Sun")) {
            return str + "sun";
        }
        if (str2.equals("Mon")) {
            return str + "mon";
        }
        if (str2.equals("Tue")) {
            return str + "tue";
        }
        if (str2.equals("Wed")) {
            return str + "wed";
        }
        if (str2.equals("Thu")) {
            return str + "thu";
        }
        if (!str2.equals("Fri")) {
            return str;
        }
        return str + "fri";
    }
}
