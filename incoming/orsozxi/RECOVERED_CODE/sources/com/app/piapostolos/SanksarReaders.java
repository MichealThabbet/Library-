package com.app.piapostolos;

import android.app.Activity;
import android.util.Log;
import com.app.piapostolos.Beans.Bible;
import com.ibm.icu.util.CopticCalendar;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes.dex */
public class SanksarReaders {
    private Activity activity;
    private boolean isEaster;
    private boolean isKamasen;
    private boolean isSood;
    private ArrayList<Bible> legendList;
    private String sanksarDayTitle;
    public ArrayList<String> sayer = new ArrayList<>();
    public ArrayList<String> sayerTitle = new ArrayList<>();
    CopticCalendar copticCalendar = new CopticCalendar();

    protected void loadData() {
        this.isEaster = false;
        this.isKamasen = false;
        this.isSood = false;
    }

    public SanksarReaders(Activity activity, boolean z) {
        this.isEaster = false;
        this.isSood = false;
        this.isKamasen = false;
        this.activity = activity;
        Calendar calendar = Calendar.getInstance();
        if (z && calendar.get(11) >= 17) {
            Log.d("orso", "now" + calendar.getTime());
        }
        Calendar orthodoxEaster = getOrthodoxEaster(calendar.get(1));
        if (z && calendar.get(11) >= 17) {
            CopticCalendar copticCalendar = this.copticCalendar;
            copticCalendar.set(6, copticCalendar.get(6) + 1);
            orthodoxEaster.set(6, orthodoxEaster.get(6) + 1);
        }
        if (orthodoxEaster.get(1) == calendar.get(1) && orthodoxEaster.get(5) == calendar.get(5) && orthodoxEaster.get(2) == calendar.get(2)) {
            this.isEaster = true;
            this.sanksarDayTitle = copticToString(0);
        }
        orthodoxEaster.set(6, orthodoxEaster.get(6) + 39);
        if (orthodoxEaster.get(1) == calendar.get(1) && orthodoxEaster.get(5) == calendar.get(5) && orthodoxEaster.get(2) == calendar.get(2)) {
            this.isSood = true;
            this.sanksarDayTitle = copticToString(1);
        }
        Calendar orthodoxEaster2 = getOrthodoxEaster(calendar.get(1));
        orthodoxEaster2.set(6, orthodoxEaster2.get(6) + 49);
        if (orthodoxEaster2.get(1) == calendar.get(1) && orthodoxEaster2.get(5) == calendar.get(5) && orthodoxEaster2.get(2) == calendar.get(2)) {
            this.isKamasen = true;
            this.sanksarDayTitle = copticToString(2);
        }
        if (!this.isEaster && !this.isKamasen && !this.isSood) {
            this.sanksarDayTitle = copticToString(-1);
        }
        loadData();
    }

    public String copticToString(int i) {
        String strValueOf = String.valueOf(this.copticCalendar.get(5));
        String strValueOf2 = String.valueOf(this.copticCalendar.get(1));
        int i2 = this.copticCalendar.get(2) + 1;
        if (i == -1 && Integer.valueOf(strValueOf2.substring(2, 4)).intValue() % 4 == 0 && i2 == 4) {
            if (this.copticCalendar.get(5) == 27) {
                ReadData(28, i2, this.copticCalendar.get(1), i);
            } else if (this.copticCalendar.get(5) == 29 || this.copticCalendar.get(5) == 28) {
                ReadData(29, i2, this.copticCalendar.get(1), i);
            } else {
                ReadData(this.copticCalendar.get(5), i2, this.copticCalendar.get(1), i);
            }
        } else {
            ReadData(this.copticCalendar.get(5), i2, this.copticCalendar.get(1), i);
        }
        Log.d("sanksar", strValueOf + " " + copticMonth(i2) + " " + strValueOf2);
        return strValueOf2 + " " + copticMonth(i2) + " " + strValueOf;
    }

    public String copticMonth(int i) {
        if (i == 1) {
            return this.activity.getString(R.string.c_1);
        }
        if (i == 2) {
            return this.activity.getString(R.string.c_2);
        }
        if (i == 3) {
            return this.activity.getString(R.string.c_3);
        }
        if (i == 4) {
            return this.activity.getString(R.string.c_4);
        }
        if (i == 5) {
            return this.activity.getString(R.string.c_5);
        }
        if (i == 6) {
            return this.activity.getString(R.string.c_6);
        }
        if (i == 7) {
            return this.activity.getString(R.string.c_7);
        }
        if (i == 8) {
            return this.activity.getString(R.string.c_8);
        }
        if (i == 9) {
            return this.activity.getString(R.string.c_9);
        }
        if (i == 10) {
            return this.activity.getString(R.string.c_10);
        }
        if (i == 11) {
            return this.activity.getString(R.string.c_11);
        }
        if (i == 12) {
            return this.activity.getString(R.string.c_12);
        }
        return i == 13 ? this.activity.getString(R.string.c_13) : "";
    }

    public int getmonthInt(String str) {
        if (str.equals("Jan")) {
            return 0;
        }
        if (str.equals("Feb")) {
            return 1;
        }
        if (str.equals("Mar")) {
            return 2;
        }
        if (str.equals("Apr")) {
            return 3;
        }
        if (str.equals("May")) {
            return 4;
        }
        if (str.equals("Jun")) {
            return 5;
        }
        if (str.equals("Jul")) {
            return 6;
        }
        if (str.equals("Aug")) {
            return 7;
        }
        if (str.equals("Sep")) {
            return 8;
        }
        if (str.equals("Oct")) {
            return 9;
        }
        if (str.equals("Nov")) {
            return 10;
        }
        return str.equals("Dec") ? 11 : 0;
    }

    public void ReadData(int i, int i2, int i3, int i4) {
        try {
            this.sayer.clear();
            this.sayerTitle.clear();
            String str = null;
            if (i4 == -1) {
                str = new String(app.decodeFile2(this.activity, "piapostolos", "sanksar2/" + i2 + "/" + (i2 < 10 ? "0" + i2 + " (" + i + ")" : i2 + " (" + i + ")") + ".txt"));
            } else if (i4 == 0) {
                str = new String(app.decodeFile2(this.activity, "piapostolos", "sanksar2/special/1.txt"));
            } else if (i4 == 1) {
                str = new String(app.decodeFile2(this.activity, "piapostolos", "sanksar2/special/40.txt"));
            } else if (i4 == 2) {
                str = new String(app.decodeFile2(this.activity, "piapostolos", "sanksar2/special/50.txt"));
            }
            String[] strArrSplit = str.split("\n");
            String str2 = "";
            boolean z = false;
            int i5 = 0;
            for (int i6 = 0; i6 < strArrSplit.length; i6++) {
                System.out.println("" + strArrSplit[i6] + "=size" + strArrSplit[i6].length());
                if (i6 == 0) {
                    str2 = str2 + strArrSplit[i6] + "\n";
                } else if (strArrSplit[i6] == null || strArrSplit[i6].equals("") || strArrSplit[i6].length() < 3 || strArrSplit[i6].equals(StringUtils.CR)) {
                    if (i5 != 0) {
                        this.sayer.add(str2);
                        str2 = "";
                    }
                    i5++;
                    z = true;
                } else if (!z) {
                    this.sayerTitle.add(strArrSplit[i6]);
                } else if (i6 == strArrSplit.length - 1) {
                    this.sayer.add(str2 + strArrSplit[i6] + "\n");
                    str2 = "";
                } else if (i5 > 0) {
                    str2 = str2 + strArrSplit[i6] + "\n";
                }
            }
            String[] strArr = new String[this.sayerTitle.size()];
            for (int i7 = 0; i7 < this.sayerTitle.size(); i7++) {
                strArr[i7] = this.sayerTitle.get(i7);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
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
}
