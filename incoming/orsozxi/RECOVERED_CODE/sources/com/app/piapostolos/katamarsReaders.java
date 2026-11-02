package com.app.piapostolos;

import android.app.Activity;
import android.util.Log;
import com.app.piapostolos.Beans.Bible;
import com.app.piapostolos.BibleDataBase.BibleDataBase;
import com.app.piapostolos.BibleDataBase.UseDatabaseHandler;
import com.ibm.icu.text.DateFormat;
import com.ibm.icu.util.CopticCalendar;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import net.lingala.zip4j.util.InternalZipConstants;
import net.sqlcipher.SQLException;
import net.sqlcipher.database.SQLiteDatabase;
import org.slf4j.Marker;

/* loaded from: classes.dex */
public class katamarsReaders {
    String ab;
    public ArrayList<String> abraksisData;
    private Activity activity;
    public boolean after5;
    public ArrayList<String> ashyaData;
    String b;
    String ba;
    public ArrayList<String> bakrData;
    String bb;
    String bo;
    public ArrayList<String> bolesData;
    String bookPath = "";
    int bookPathChapters = 0;
    String[] books;
    Dictionary<Integer, String> d;
    public String eidName;
    public ArrayList<String> engelKodasData;
    public ArrayList<String> engelKodaswithoutMzmor;
    String ka;
    public ArrayList<String> kaselikonData;
    List<Bible> legendList;
    ArrayList<String> levels;
    HashMap<String, ArrayList<String>> levelsMap;
    String m;
    String ma;
    String mb;
    String mm;
    public ArrayList<String> mzmorKodas;
    public ArrayList<String> mzmorNightData;
    String n;
    public ArrayList<String> nabwoatData;
    private String realDayName;
    public ArrayList<String> sanksarData;
    public String splashEid;

    private String dayOfWeek(int i) {
        return i == 1 ? "Sun" : i == 2 ? "Mon" : i == 3 ? "Tue" : i == 4 ? "Wed" : i == 5 ? "Thu" : i == 6 ? "Fri" : "Sat";
    }

    public String choosebook(int i) {
        return i == 0 ? "arabbible0" : i == 1 ? "arabbible1" : i == 2 ? "arabbible2" : i == 3 ? "arabbible3" : i == 4 ? "arabbible4" : i == 5 ? "arabbible5" : i == 6 ? "arabbible6" : i == 7 ? "arabbible7" : i == 8 ? "arabbible8" : i == 9 ? "arabbible9" : i == 10 ? "arabbible10" : i == 11 ? "arabbible11" : i == 12 ? "arabbible12" : i == 13 ? "arabbible13" : i == 14 ? "arabbible14" : i == 15 ? "arabbible15" : i == 16 ? "arabbible16" : i == 17 ? "arabbible17" : i == 18 ? "arabbible18" : i == 19 ? "arabbible19" : i == 20 ? "arabbible20" : i == 21 ? "arabbible21" : i == 22 ? "arabbible22" : i == 23 ? "arabbible23" : i == 24 ? "arabbible24" : i == 25 ? "arabbible25" : i == 26 ? "arabbible26" : i == 27 ? "arabbible27" : i == 28 ? "arabbible28" : i == 29 ? "arabbible29" : i == 30 ? "arabbible30" : i == 31 ? "arabbible31" : i == 32 ? "arabbible32" : i == 33 ? "arabbible33" : i == 34 ? "arabbible34" : i == 35 ? "arabbible35" : i == 36 ? "arabbible36" : i == 37 ? "arabbible37" : i == 38 ? "arabbible38" : i == 39 ? "arabbible39" : i == 40 ? "arabbible40" : i == 41 ? "arabbible41" : i == 42 ? "arabbible42" : i == 43 ? "arabbible43" : i == 44 ? "arabbible44" : i == 45 ? "arabbible45" : i == 46 ? "arabbible46" : i == 47 ? "arabbible47" : i == 48 ? "arabbible48" : i == 49 ? "arabbible49" : i == 50 ? "arabbible50" : i == 51 ? "arabbible51" : i == 52 ? "arabbible52" : i == 53 ? "arabbible53" : i == 54 ? "arabbible54" : i == 55 ? "arabbible55" : i == 56 ? "arabbible56" : i == 57 ? "arabbible57" : i == 58 ? "arabbible58" : i == 59 ? "arabbible59" : i == 60 ? "arabbible60" : i == 61 ? "arabbible61" : i == 62 ? "arabbible62" : i == 63 ? "arabbible63" : i == 64 ? "arabbible64" : i == 65 ? "arabbible65" : i == 66 ? "arabbible66" : i == 67 ? "arabbible67" : i == 68 ? "arabbible68" : i == 69 ? "arabbible69" : i == 70 ? "arabbible70" : i == 71 ? "arabbible71" : i == 72 ? "arabbible72" : "";
    }

    public katamarsReaders(Activity activity, boolean z) throws Throwable {
        this.ma = "مزمور عشية";
        this.ba = "إنجيل عشية";
        this.mb = "مزمور باكر";
        this.bb = "إنجيل باكر";
        this.bo = "البولس";
        this.ka = "الكاثوليكون";
        this.ab = "الإبركسيس";
        this.m = "مزمور الإنجيل";
        this.mm = "مزمور المساء";
        this.b = "الإنجيل";
        this.n = "النبؤات";
        this.eidName = "";
        this.splashEid = "";
        this.after5 = false;
        this.realDayName = "";
        this.ma = activity.getString(R.string.t_mzmor_ashya);
        this.ba = activity.getString(R.string.t_engel_ashya);
        this.mb = activity.getString(R.string.t_mzmor_baker);
        this.bb = activity.getString(R.string.t_engel_baker);
        this.bo = activity.getString(R.string.t_boules);
        this.ka = activity.getString(R.string.t_kathlekon);
        this.ab = activity.getString(R.string.t_apraksis);
        this.m = activity.getString(R.string.t_mzmor_engel);
        this.mm = activity.getString(R.string.t_mzmor_night);
        this.b = activity.getString(R.string.t_the_bible);
        this.n = activity.getString(R.string.t_predictions);
        this.legendList = new ArrayList();
        this.activity = activity;
        this.ashyaData = new ArrayList<>();
        this.bakrData = new ArrayList<>();
        this.nabwoatData = new ArrayList<>();
        this.bolesData = new ArrayList<>();
        this.kaselikonData = new ArrayList<>();
        this.abraksisData = new ArrayList<>();
        this.sanksarData = new ArrayList<>();
        this.engelKodasData = new ArrayList<>();
        this.mzmorNightData = new ArrayList<>();
        this.engelKodaswithoutMzmor = new ArrayList<>();
        this.mzmorKodas = new ArrayList<>();
        this.levelsMap = new HashMap<>();
        this.levels = new ArrayList<>();
        try {
            InputStream inputStreamOpen = activity.getAssets().open("biblenames.xml");
            byte[] bArr = new byte[inputStreamOpen.available()];
            inputStreamOpen.read(bArr);
            String[] strArrSplit = new String(bArr).split("\n");
            this.books = new String[73];
            this.d = new Hashtable();
            for (int i = 0; i < strArrSplit.length; i++) {
                if (i < 73) {
                    this.d.put(Integer.valueOf(i), strArrSplit[i]);
                    this.books[i] = strArrSplit[i];
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        this.splashEid = "";
        this.legendList = new ArrayList();
        this.ashyaData = new ArrayList<>();
        this.bakrData = new ArrayList<>();
        this.nabwoatData = new ArrayList<>();
        this.bolesData = new ArrayList<>();
        this.kaselikonData = new ArrayList<>();
        this.abraksisData = new ArrayList<>();
        this.sanksarData = new ArrayList<>();
        this.engelKodasData = new ArrayList<>();
        this.mzmorNightData = new ArrayList<>();
        this.engelKodaswithoutMzmor = new ArrayList<>();
        this.mzmorKodas = new ArrayList<>();
        this.levelsMap = new HashMap<>();
        this.levels = new ArrayList<>();
        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(11, calendar.get(11));
        CopticCalendar copticCalendar2 = new CopticCalendar();
        calendar2.set(6, calendar2.get(6) + 1);
        copticCalendar2.set(6, copticCalendar2.get(6) + 1);
        calculateEvents(copticCalendar2.get(5), copticCalendar2.get(2), copticCalendar2.get(1), calendar2.get(5), calendar2.get(2), calendar2.get(1), dayOfWeek(calendar2.get(7)));
    }

    /* JADX WARN: Removed duplicated region for block: B:39:0x010a  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x011d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void getDynamicData() {
        /*
            Method dump skipped, instructions count: 451
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.piapostolos.katamarsReaders.getDynamicData():void");
    }

    /* JADX WARN: Removed duplicated region for block: B:38:0x0107  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0119  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void getDataStatic() {
        /*
            Method dump skipped, instructions count: 444
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.piapostolos.katamarsReaders.getDataStatic():void");
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

    /* JADX WARN: Removed duplicated region for block: B:1179:0x0e76  */
    /* JADX WARN: Removed duplicated region for block: B:1180:0x0e7b  */
    /* JADX WARN: Removed duplicated region for block: B:675:0x0946  */
    /* JADX WARN: Removed duplicated region for block: B:689:0x0962  */
    /* JADX WARN: Removed duplicated region for block: B:690:0x0967  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public java.lang.String calculateEvents(int r18, int r19, int r20, int r21, int r22, int r23, java.lang.String r24) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 3879
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.piapostolos.katamarsReaders.calculateEvents(int, int, int, int, int, int, java.lang.String):java.lang.String");
    }

    private void readKatA7adOthers(int i, int i2, int i3) {
        if (i == 13 && i2 == 11) {
            loadingAyam(13, 11);
            return;
        }
        if (i == 1 && i2 == 0) {
            loadingAyam(1, 0);
        } else if (i3 == 5) {
            loadingA7ad(5, i2);
        } else {
            loadingA7ad(i3, i2);
        }
    }

    private int isCalendarsEqual(Calendar calendar, Calendar calendar2) {
        int iIntValue = Integer.valueOf(((calendar.getTimeInMillis() - calendar2.getTimeInMillis()) / 86400000) + "").intValue();
        if (iIntValue == 0) {
            return 0;
        }
        return iIntValue > 0 ? 1 : -1;
    }

    private void loadingAyam(int i, int i2) {
        try {
            String str = new String(app.decodeFile2(this.activity, "piapostolos", "katamars/KatamarsAyam/" + (i2 + 1) + "/" + i + ".txt"));
            str.replaceAll("\\+", "");
            for (String str2 : str.split("\n")) {
                this.legendList.add(new Bible("", str2.replace(Marker.ANY_NON_NULL_MARKER, "")));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        getDataStatic();
    }

    private void loadingA7ad(int i, int i2) {
        try {
            String str = new String(app.decodeFile2(this.activity, "piapostolos", "katamars/KatamarsA7ad/" + (i2 + 1) + "/" + i + ".txt"));
            str.replaceAll("\\+", "");
            for (String str2 : str.split("\n")) {
                this.legendList.add(new Bible("", str2.replace(Marker.ANY_NON_NULL_MARKER, "")));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        getDataStatic();
    }

    /* JADX WARN: Removed duplicated region for block: B:67:0x01e3 A[Catch: Exception -> 0x0bdf, IOException -> 0x0be5, TryCatch #2 {IOException -> 0x0be5, Exception -> 0x0bdf, blocks: (B:3:0x0006, B:5:0x0025, B:7:0x0028, B:10:0x003a, B:13:0x004a, B:15:0x0052, B:17:0x005a, B:18:0x005f, B:19:0x0073, B:22:0x0090, B:24:0x0098, B:26:0x00a0, B:28:0x00a8, B:31:0x00b3, B:34:0x00c9, B:36:0x0108, B:38:0x010d, B:40:0x0111, B:44:0x0124, B:46:0x0131, B:52:0x0153, B:54:0x017e, B:56:0x0186, B:64:0x01be, B:65:0x01dd, B:67:0x01e3, B:69:0x021d, B:72:0x0243, B:74:0x0249, B:76:0x0252, B:78:0x0288, B:80:0x0290, B:82:0x02b9, B:86:0x02ee, B:88:0x02f7, B:90:0x0300, B:92:0x031f, B:94:0x0323, B:96:0x0327, B:98:0x032e, B:100:0x0334, B:102:0x033a, B:223:0x0a2d, B:224:0x0a38, B:226:0x0a3e, B:230:0x0a52, B:233:0x0a5a, B:235:0x0aa0, B:239:0x0ac2, B:242:0x0ae0, B:244:0x0ae6, B:247:0x0aee, B:249:0x0b03, B:250:0x0b06, B:103:0x0394, B:105:0x03aa, B:107:0x03b0, B:108:0x03f3, B:112:0x0447, B:114:0x0451, B:116:0x047a, B:117:0x04b0, B:119:0x04bb, B:121:0x04f7, B:122:0x0530, B:83:0x02ca, B:84:0x02d3, B:126:0x0598, B:128:0x05af, B:130:0x05b5, B:132:0x05be, B:134:0x05c8, B:136:0x05d8, B:140:0x0617, B:142:0x0620, B:144:0x0631, B:146:0x064f, B:148:0x0653, B:150:0x0659, B:152:0x0662, B:154:0x0668, B:156:0x066e, B:157:0x06b8, B:159:0x06c4, B:161:0x06ca, B:162:0x0705, B:166:0x074b, B:168:0x0755, B:170:0x0780, B:171:0x07af, B:172:0x07e2, B:138:0x05f0, B:139:0x0603, B:175:0x0804, B:177:0x0816, B:179:0x0820, B:181:0x0830, B:185:0x0861, B:187:0x0868, B:189:0x0870, B:191:0x087f, B:193:0x08a3, B:195:0x08a7, B:197:0x08ad, B:199:0x08b5, B:201:0x08bb, B:203:0x08c1, B:204:0x0900, B:206:0x090a, B:208:0x0910, B:209:0x0945, B:213:0x0985, B:215:0x098f, B:217:0x09ba, B:218:0x09e3, B:219:0x0a11, B:183:0x0848, B:257:0x0bb4, B:259:0x0bbc, B:58:0x01a1, B:60:0x01a7, B:62:0x01ad), top: B:272:0x0006 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void loadingyunan(int r43, int r44) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 3054
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.piapostolos.katamarsReaders.loadingyunan(int, int):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:254:0x0ae7 A[Catch: Exception -> 0x0c87, IOException -> 0x0c8d, TRY_LEAVE, TryCatch #2 {IOException -> 0x0c8d, Exception -> 0x0c87, blocks: (B:3:0x000a, B:5:0x0028, B:7:0x002b, B:10:0x0041, B:13:0x0054, B:15:0x005c, B:17:0x0064, B:18:0x0069, B:21:0x0081, B:24:0x009e, B:26:0x00a6, B:28:0x00ae, B:30:0x00b6, B:33:0x00c1, B:37:0x00dd, B:39:0x00e8, B:41:0x00f0, B:43:0x00f8, B:45:0x0100, B:47:0x0108, B:49:0x0110, B:51:0x0118, B:53:0x0120, B:55:0x0128, B:57:0x0130, B:58:0x016d, B:60:0x018f, B:62:0x0194, B:64:0x01ac, B:66:0x01b1, B:68:0x01b5, B:72:0x01ca, B:74:0x01d7, B:79:0x01f5, B:81:0x0222, B:83:0x022a, B:91:0x0262, B:92:0x0281, B:94:0x0287, B:96:0x02c1, B:99:0x02e7, B:101:0x02ed, B:103:0x02f6, B:105:0x032c, B:107:0x0334, B:109:0x035d, B:113:0x0392, B:115:0x039b, B:117:0x03a4, B:119:0x03c1, B:121:0x03c5, B:123:0x03c9, B:125:0x03d0, B:127:0x03d6, B:129:0x03dc, B:251:0x0ad6, B:252:0x0ae1, B:254:0x0ae7, B:258:0x0afb, B:261:0x0b03, B:263:0x0b49, B:267:0x0b6b, B:270:0x0b89, B:272:0x0b8f, B:275:0x0b97, B:277:0x0bac, B:278:0x0baf, B:130:0x0439, B:132:0x0451, B:134:0x0457, B:135:0x049a, B:139:0x04f2, B:141:0x04fc, B:143:0x0525, B:144:0x055b, B:146:0x0566, B:148:0x05a2, B:149:0x05db, B:110:0x036e, B:111:0x0377, B:154:0x0640, B:156:0x065c, B:158:0x0662, B:160:0x066b, B:162:0x0675, B:164:0x0685, B:168:0x06c4, B:170:0x06cd, B:172:0x06de, B:174:0x0700, B:176:0x0704, B:178:0x070a, B:180:0x0713, B:182:0x0719, B:184:0x071f, B:185:0x0769, B:187:0x0775, B:189:0x077b, B:190:0x07b6, B:194:0x07fc, B:196:0x0806, B:198:0x0831, B:199:0x0860, B:200:0x0893, B:166:0x069d, B:167:0x06b0, B:203:0x08b0, B:205:0x08c0, B:207:0x08ca, B:209:0x08da, B:214:0x0909, B:216:0x0910, B:218:0x0918, B:220:0x0927, B:222:0x094b, B:224:0x094f, B:226:0x0955, B:228:0x095d, B:230:0x0963, B:232:0x0969, B:233:0x09a8, B:235:0x09b2, B:237:0x09b8, B:238:0x09ed, B:242:0x0a2d, B:244:0x0a37, B:246:0x0a62, B:247:0x0a8b, B:248:0x0ab9, B:211:0x08f0, B:285:0x0c55, B:287:0x0c5d, B:85:0x0245, B:87:0x024b, B:89:0x0251), top: B:300:0x000a }] */
    /* JADX WARN: Removed duplicated region for block: B:94:0x0287 A[Catch: Exception -> 0x0c87, IOException -> 0x0c8d, TryCatch #2 {IOException -> 0x0c8d, Exception -> 0x0c87, blocks: (B:3:0x000a, B:5:0x0028, B:7:0x002b, B:10:0x0041, B:13:0x0054, B:15:0x005c, B:17:0x0064, B:18:0x0069, B:21:0x0081, B:24:0x009e, B:26:0x00a6, B:28:0x00ae, B:30:0x00b6, B:33:0x00c1, B:37:0x00dd, B:39:0x00e8, B:41:0x00f0, B:43:0x00f8, B:45:0x0100, B:47:0x0108, B:49:0x0110, B:51:0x0118, B:53:0x0120, B:55:0x0128, B:57:0x0130, B:58:0x016d, B:60:0x018f, B:62:0x0194, B:64:0x01ac, B:66:0x01b1, B:68:0x01b5, B:72:0x01ca, B:74:0x01d7, B:79:0x01f5, B:81:0x0222, B:83:0x022a, B:91:0x0262, B:92:0x0281, B:94:0x0287, B:96:0x02c1, B:99:0x02e7, B:101:0x02ed, B:103:0x02f6, B:105:0x032c, B:107:0x0334, B:109:0x035d, B:113:0x0392, B:115:0x039b, B:117:0x03a4, B:119:0x03c1, B:121:0x03c5, B:123:0x03c9, B:125:0x03d0, B:127:0x03d6, B:129:0x03dc, B:251:0x0ad6, B:252:0x0ae1, B:254:0x0ae7, B:258:0x0afb, B:261:0x0b03, B:263:0x0b49, B:267:0x0b6b, B:270:0x0b89, B:272:0x0b8f, B:275:0x0b97, B:277:0x0bac, B:278:0x0baf, B:130:0x0439, B:132:0x0451, B:134:0x0457, B:135:0x049a, B:139:0x04f2, B:141:0x04fc, B:143:0x0525, B:144:0x055b, B:146:0x0566, B:148:0x05a2, B:149:0x05db, B:110:0x036e, B:111:0x0377, B:154:0x0640, B:156:0x065c, B:158:0x0662, B:160:0x066b, B:162:0x0675, B:164:0x0685, B:168:0x06c4, B:170:0x06cd, B:172:0x06de, B:174:0x0700, B:176:0x0704, B:178:0x070a, B:180:0x0713, B:182:0x0719, B:184:0x071f, B:185:0x0769, B:187:0x0775, B:189:0x077b, B:190:0x07b6, B:194:0x07fc, B:196:0x0806, B:198:0x0831, B:199:0x0860, B:200:0x0893, B:166:0x069d, B:167:0x06b0, B:203:0x08b0, B:205:0x08c0, B:207:0x08ca, B:209:0x08da, B:214:0x0909, B:216:0x0910, B:218:0x0918, B:220:0x0927, B:222:0x094b, B:224:0x094f, B:226:0x0955, B:228:0x095d, B:230:0x0963, B:232:0x0969, B:233:0x09a8, B:235:0x09b2, B:237:0x09b8, B:238:0x09ed, B:242:0x0a2d, B:244:0x0a37, B:246:0x0a62, B:247:0x0a8b, B:248:0x0ab9, B:211:0x08f0, B:285:0x0c55, B:287:0x0c5d, B:85:0x0245, B:87:0x024b, B:89:0x0251), top: B:300:0x000a }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void loadingSoom(int r45, int r46) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 3222
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.piapostolos.katamarsReaders.loadingSoom(int, int):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:254:0x0af8 A[Catch: Exception -> 0x0c95, IOException -> 0x0c9b, TRY_LEAVE, TryCatch #2 {IOException -> 0x0c9b, Exception -> 0x0c95, blocks: (B:3:0x001e, B:5:0x003c, B:7:0x003f, B:10:0x0055, B:13:0x0068, B:15:0x0070, B:17:0x0078, B:18:0x007d, B:21:0x0095, B:24:0x00b2, B:26:0x00ba, B:28:0x00c2, B:30:0x00ca, B:33:0x00d5, B:37:0x00f1, B:39:0x00fc, B:41:0x0104, B:43:0x010c, B:45:0x0114, B:47:0x011c, B:49:0x0124, B:51:0x012c, B:53:0x0134, B:55:0x013c, B:57:0x0144, B:58:0x0181, B:60:0x01a3, B:62:0x01a8, B:64:0x01c0, B:66:0x01c5, B:68:0x01c9, B:72:0x01de, B:74:0x01eb, B:79:0x0209, B:81:0x0236, B:83:0x023e, B:91:0x0276, B:92:0x0295, B:94:0x029b, B:96:0x02d5, B:99:0x02fb, B:101:0x0301, B:103:0x030a, B:105:0x0340, B:107:0x0348, B:109:0x0371, B:113:0x03a6, B:115:0x03af, B:117:0x03b8, B:119:0x03d5, B:121:0x03d9, B:123:0x03dd, B:125:0x03e4, B:127:0x03ea, B:129:0x03f0, B:251:0x0ae7, B:252:0x0af2, B:254:0x0af8, B:258:0x0b0c, B:261:0x0b14, B:263:0x0b5a, B:267:0x0b7c, B:270:0x0b9a, B:272:0x0ba0, B:275:0x0ba8, B:277:0x0bbd, B:278:0x0bc0, B:130:0x044d, B:132:0x0465, B:134:0x046b, B:135:0x04ae, B:139:0x0506, B:141:0x0510, B:143:0x0539, B:144:0x056f, B:146:0x057a, B:148:0x05b6, B:149:0x05ef, B:110:0x0382, B:111:0x038b, B:154:0x0653, B:156:0x066f, B:158:0x0675, B:160:0x067e, B:162:0x0688, B:164:0x0698, B:168:0x06d7, B:170:0x06e0, B:172:0x06f1, B:174:0x0713, B:176:0x0717, B:178:0x071d, B:180:0x0726, B:182:0x072c, B:184:0x0732, B:185:0x077c, B:187:0x0788, B:189:0x078e, B:190:0x07c9, B:194:0x080f, B:196:0x0819, B:198:0x0844, B:199:0x0873, B:200:0x08a6, B:166:0x06b0, B:167:0x06c3, B:203:0x08c2, B:205:0x08d2, B:207:0x08dc, B:209:0x08ec, B:214:0x091b, B:216:0x0922, B:218:0x092a, B:220:0x0939, B:222:0x095d, B:224:0x0961, B:226:0x0967, B:228:0x096f, B:230:0x0975, B:232:0x097b, B:233:0x09ba, B:235:0x09c4, B:237:0x09ca, B:238:0x09ff, B:242:0x0a3f, B:244:0x0a49, B:246:0x0a74, B:247:0x0a9d, B:248:0x0acb, B:211:0x0902, B:285:0x0c63, B:287:0x0c6b, B:85:0x0259, B:87:0x025f, B:89:0x0265), top: B:300:0x001e }] */
    /* JADX WARN: Removed duplicated region for block: B:94:0x029b A[Catch: Exception -> 0x0c95, IOException -> 0x0c9b, TryCatch #2 {IOException -> 0x0c9b, Exception -> 0x0c95, blocks: (B:3:0x001e, B:5:0x003c, B:7:0x003f, B:10:0x0055, B:13:0x0068, B:15:0x0070, B:17:0x0078, B:18:0x007d, B:21:0x0095, B:24:0x00b2, B:26:0x00ba, B:28:0x00c2, B:30:0x00ca, B:33:0x00d5, B:37:0x00f1, B:39:0x00fc, B:41:0x0104, B:43:0x010c, B:45:0x0114, B:47:0x011c, B:49:0x0124, B:51:0x012c, B:53:0x0134, B:55:0x013c, B:57:0x0144, B:58:0x0181, B:60:0x01a3, B:62:0x01a8, B:64:0x01c0, B:66:0x01c5, B:68:0x01c9, B:72:0x01de, B:74:0x01eb, B:79:0x0209, B:81:0x0236, B:83:0x023e, B:91:0x0276, B:92:0x0295, B:94:0x029b, B:96:0x02d5, B:99:0x02fb, B:101:0x0301, B:103:0x030a, B:105:0x0340, B:107:0x0348, B:109:0x0371, B:113:0x03a6, B:115:0x03af, B:117:0x03b8, B:119:0x03d5, B:121:0x03d9, B:123:0x03dd, B:125:0x03e4, B:127:0x03ea, B:129:0x03f0, B:251:0x0ae7, B:252:0x0af2, B:254:0x0af8, B:258:0x0b0c, B:261:0x0b14, B:263:0x0b5a, B:267:0x0b7c, B:270:0x0b9a, B:272:0x0ba0, B:275:0x0ba8, B:277:0x0bbd, B:278:0x0bc0, B:130:0x044d, B:132:0x0465, B:134:0x046b, B:135:0x04ae, B:139:0x0506, B:141:0x0510, B:143:0x0539, B:144:0x056f, B:146:0x057a, B:148:0x05b6, B:149:0x05ef, B:110:0x0382, B:111:0x038b, B:154:0x0653, B:156:0x066f, B:158:0x0675, B:160:0x067e, B:162:0x0688, B:164:0x0698, B:168:0x06d7, B:170:0x06e0, B:172:0x06f1, B:174:0x0713, B:176:0x0717, B:178:0x071d, B:180:0x0726, B:182:0x072c, B:184:0x0732, B:185:0x077c, B:187:0x0788, B:189:0x078e, B:190:0x07c9, B:194:0x080f, B:196:0x0819, B:198:0x0844, B:199:0x0873, B:200:0x08a6, B:166:0x06b0, B:167:0x06c3, B:203:0x08c2, B:205:0x08d2, B:207:0x08dc, B:209:0x08ec, B:214:0x091b, B:216:0x0922, B:218:0x092a, B:220:0x0939, B:222:0x095d, B:224:0x0961, B:226:0x0967, B:228:0x096f, B:230:0x0975, B:232:0x097b, B:233:0x09ba, B:235:0x09c4, B:237:0x09ca, B:238:0x09ff, B:242:0x0a3f, B:244:0x0a49, B:246:0x0a74, B:247:0x0a9d, B:248:0x0acb, B:211:0x0902, B:285:0x0c63, B:287:0x0c6b, B:85:0x0259, B:87:0x025f, B:89:0x0265), top: B:300:0x001e }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void loading7maseen(int r45, int r46) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 3236
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.piapostolos.katamarsReaders.loading7maseen(int, int):void");
    }

    private void loadmazmor(String str, int i) {
        String str2;
        String str3 = str.split("\\+")[0];
        try {
            if (i == 0) {
                str2 = new String(app.decodeFile2(this.activity, "piapostolos", "katamars/mzamerSoom.txt"));
            } else {
                str2 = new String(app.decodeFile2(this.activity, "piapostolos", "katamars/mzamer7maseen.txt"));
            }
            String[] strArrSplit = str2.split("\n");
            str.split("\\+");
            boolean z = false;
            for (int i2 = 0; i2 < strArrSplit.length; i2++) {
                if (strArrSplit[i2].toLowerCase().equals(str.toLowerCase())) {
                    Log.d("orso2", "line" + str);
                    Log.d("orso2", "xml" + strArrSplit[i2]);
                    z = true;
                } else if (z && strArrSplit[i2].contains("@") && !strArrSplit[i2].contains("@@")) {
                    this.legendList.add(new Bible("", typeofslah(str3)));
                    this.legendList.add(new Bible("", strArrSplit[i2].replace("@", "")));
                } else if (strArrSplit[i2].contains("@@") || strArrSplit[i2].equals("@")) {
                    z = false;
                } else if (z) {
                    this.legendList.add(new Bible("", strArrSplit[i2]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void loadinmapp(String str, int i, int i2) throws Throwable {
        System.out.println("beshooooooooo" + str);
        this.levelsMap.clear();
        this.levels.clear();
        this.levelsMap = new HashMap<>();
        this.levels = new ArrayList<>();
        String[] strArrUpdatelistOld = updatelistOld(i, i2);
        for (String str2 : strArrUpdatelistOld) {
            this.levels.add(str2);
        }
        this.levelsMap.put("0", new ArrayList<>(this.levels));
        this.levels.clear();
    }

    public String[] updatelist(int i, int i2) throws SQLException {
        try {
            SQLiteDatabase.loadLibs(this.activity);
            new UseDatabaseHandler(this.activity);
            UseDatabaseHandler useDatabaseHandler = UseDatabaseHandler.getInstance(this.activity);
            useDatabaseHandler.open(app.getSqKey(this.activity));
            List<BibleDataBase> chapter = useDatabaseHandler.getChapter(i, i2);
            useDatabaseHandler.close();
            if (chapter == null) {
                return null;
            }
            String[] strArr = new String[chapter.size()];
            for (int i3 = 0; i3 < chapter.size(); i3++) {
                strArr[i3] = chapter.get(i3).getAyah();
            }
            return strArr;
        } catch (android.database.SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:251:0x078f A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public java.lang.String[] updatelistOld(int r7, int r8) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 1939
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.piapostolos.katamarsReaders.updatelistOld(int, int):java.lang.String[]");
    }

    public String loadTextFile(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = new byte[4096];
        while (true) {
            int i = inputStream.read(bArr);
            if (i > 0) {
                byteArrayOutputStream.write(bArr, 0, i);
            } else {
                return new String(byteArrayOutputStream.toByteArray(), InternalZipConstants.CHARSET_UTF8);
            }
        }
    }

    public String typeofslah(String str) {
        if (str.equals("ma") || str.equals("Ma")) {
            return this.ma;
        }
        if (str.equals("ba") || str.equals("Ba")) {
            return this.ba;
        }
        if (str.equals("mb") || str.equals("Mb")) {
            return this.mb;
        }
        if (str.equals("bb") || str.equals("Bb")) {
            return this.bb;
        }
        if (str.equals("bo") || str.equals("Bo")) {
            return this.bo;
        }
        if (str.equals("ka") || str.equals("Ka")) {
            return this.ka;
        }
        if (str.equals("ba") || str.equals("Ba")) {
            return this.ba;
        }
        if (str.equals("ab") || str.equals("Ab")) {
            return this.ab;
        }
        if (str.equals(DateFormat.MINUTE) || str.equals(DateFormat.NUM_MONTH)) {
            return this.m;
        }
        if (str.equals("Mm") || str.equals("mm")) {
            return this.mm;
        }
        if (str.equals("b") || str.equals("B")) {
            return this.b;
        }
        return (str.equals("n") || str.equals("N")) ? this.n : "";
    }

    private void namekiahEidName(int i, String str) {
        if (str.equals("Sat")) {
            this.eidName = "kiak_sat_" + i;
            return;
        }
        if (str.equals("Sun")) {
            this.eidName = "kiak_sun_" + i;
            return;
        }
        this.eidName = "kiak_ayam_" + i;
    }
}
