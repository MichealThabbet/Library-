package com.app.orsozoxi;

import android.app.Activity;
import android.content.res.AssetManager;
import android.database.SQLException;
import android.util.Log;
import com.app.orsozoxi.Beans.Bible;
import com.app.orsozoxi.BibleDataBase.BibleDataBase;
import com.app.orsozoxi.BibleDataBase.UseDatabaseHandler;
import com.app.orsozoxi.Others.Util;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.share.internal.ShareConstants;
import com.ibm.icu.util.CopticCalendar;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.sqlcipher.database.SQLiteDatabase;

/* loaded from: /workspace/work/library-orsozxi/incoming/orsozxi/classes.dex */
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
    String bookPath;
    int bookPathChapters;
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

    private String dayOfWeek(int r2) {
        if (r2 != 1) goto L7;
        return "Sun";
    L7:
        if (r2 != 2) goto L11;
        return "Mon";
    L11:
        if (r2 != 3) goto L15;
        return "Tue";
    L15:
        if (r2 != 4) goto L19;
        return "Wed";
    L19:
        if (r2 != 5) goto L23;
        return "Thu";
    L23:
        if (r2 != 6) goto L26;
        return "Fri";
    L26:
        return "Sat";
    }

    public String choosebook(int r2) {
        if (r2 != 0) goto L6;
        return "arabbible0";
    L6:
        if (r2 != 1) goto L10;
        return "arabbible1";
    L10:
        if (r2 != 2) goto L14;
        return "arabbible2";
    L14:
        if (r2 != 3) goto L18;
        return "arabbible3";
    L18:
        if (r2 != 4) goto L22;
        return "arabbible4";
    L22:
        if (r2 != 5) goto L26;
        return "arabbible5";
    L26:
        if (r2 != 6) goto L30;
        return "arabbible6";
    L30:
        if (r2 != 7) goto L34;
        return "arabbible7";
    L34:
        if (r2 != 8) goto L38;
        return "arabbible8";
    L38:
        if (r2 != 9) goto L42;
        return "arabbible9";
    L42:
        if (r2 != 10) goto L46;
        return "arabbible10";
    L46:
        if (r2 != 11) goto L50;
        return "arabbible11";
    L50:
        if (r2 != 12) goto L54;
        return "arabbible12";
    L54:
        if (r2 != 13) goto L58;
        return "arabbible13";
    L58:
        if (r2 != 14) goto L62;
        return "arabbible14";
    L62:
        if (r2 != 15) goto L66;
        return "arabbible15";
    L66:
        if (r2 != 16) goto L70;
        return "arabbible16";
    L70:
        if (r2 != 17) goto L74;
        return "arabbible17";
    L74:
        if (r2 != 18) goto L78;
        return "arabbible18";
    L78:
        if (r2 != 19) goto L82;
        return "arabbible19";
    L82:
        if (r2 != 20) goto L86;
        return "arabbible20";
    L86:
        if (r2 != 21) goto L90;
        return "arabbible21";
    L90:
        if (r2 != 22) goto L94;
        return "arabbible22";
    L94:
        if (r2 != 23) goto L98;
        return "arabbible23";
    L98:
        if (r2 != 24) goto L102;
        return "arabbible24";
    L102:
        if (r2 != 25) goto L106;
        return "arabbible25";
    L106:
        if (r2 != 26) goto L110;
        return "arabbible26";
    L110:
        if (r2 != 27) goto L114;
        return "arabbible27";
    L114:
        if (r2 != 28) goto L118;
        return "arabbible28";
    L118:
        if (r2 != 29) goto L122;
        return "arabbible29";
    L122:
        if (r2 != 30) goto L126;
        return "arabbible30";
    L126:
        if (r2 != 31) goto L130;
        return "arabbible31";
    L130:
        if (r2 != 32) goto L134;
        return "arabbible32";
    L134:
        if (r2 != 33) goto L138;
        return "arabbible33";
    L138:
        if (r2 != 34) goto L142;
        return "arabbible34";
    L142:
        if (r2 != 35) goto L146;
        return "arabbible35";
    L146:
        if (r2 != 36) goto L150;
        return "arabbible36";
    L150:
        if (r2 != 37) goto L154;
        return "arabbible37";
    L154:
        if (r2 != 38) goto L158;
        return "arabbible38";
    L158:
        if (r2 != 39) goto L162;
        return "arabbible39";
    L162:
        if (r2 != 40) goto L166;
        return "arabbible40";
    L166:
        if (r2 != 41) goto L170;
        return "arabbible41";
    L170:
        if (r2 != 42) goto L174;
        return "arabbible42";
    L174:
        if (r2 != 43) goto L178;
        return "arabbible43";
    L178:
        if (r2 != 44) goto L182;
        return "arabbible44";
    L182:
        if (r2 != 45) goto L186;
        return "arabbible45";
    L186:
        if (r2 != 46) goto L190;
        return "arabbible46";
    L190:
        if (r2 != 47) goto L194;
        return "arabbible47";
    L194:
        if (r2 != 48) goto L198;
        return "arabbible48";
    L198:
        if (r2 != 49) goto L202;
        return "arabbible49";
    L202:
        if (r2 != 50) goto L206;
        return "arabbible50";
    L206:
        if (r2 != 51) goto L210;
        return "arabbible51";
    L210:
        if (r2 != 52) goto L214;
        return "arabbible52";
    L214:
        if (r2 != 53) goto L218;
        return "arabbible53";
    L218:
        if (r2 != 54) goto L222;
        return "arabbible54";
    L222:
        if (r2 != 55) goto L226;
        return "arabbible55";
    L226:
        if (r2 != 56) goto L230;
        return "arabbible56";
    L230:
        if (r2 != 57) goto L234;
        return "arabbible57";
    L234:
        if (r2 != 58) goto L238;
        return "arabbible58";
    L238:
        if (r2 != 59) goto L242;
        return "arabbible59";
    L242:
        if (r2 != 60) goto L246;
        return "arabbible60";
    L246:
        if (r2 != 61) goto L250;
        return "arabbible61";
    L250:
        if (r2 != 62) goto L254;
        return "arabbible62";
    L254:
        if (r2 != 63) goto L258;
        return "arabbible63";
    L258:
        if (r2 != 64) goto L262;
        return "arabbible64";
    L262:
        if (r2 != 65) goto L266;
        return "arabbible65";
    L266:
        if (r2 != 66) goto L270;
        return "arabbible66";
    L270:
        if (r2 != 67) goto L274;
        return "arabbible67";
    L274:
        if (r2 != 68) goto L278;
        return "arabbible68";
    L278:
        if (r2 != 69) goto L282;
        return "arabbible69";
    L282:
        if (r2 != 70) goto L286;
        return "arabbible70";
    L286:
        if (r2 != 71) goto L290;
        return "arabbible71";
    L290:
        if (r2 != 72) goto L293;
        return "arabbible72";
    L293:
        return "";
    }

    public katamarsReaders(Activity r17, boolean r18) {
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
        int r1 = 0;
        this.after5 = false;
        this.realDayName = "";
        this.bookPath = "";
        this.bookPathChapters = 0;
        this.ma = r17.getString(R.string.t_mzmor_ashya);
        this.ba = r17.getString(R.string.t_engel_ashya);
        this.mb = r17.getString(R.string.t_mzmor_baker);
        this.bb = r17.getString(R.string.t_engel_baker);
        this.bo = r17.getString(R.string.t_boules);
        this.ka = r17.getString(R.string.t_kathlekon);
        this.ab = r17.getString(R.string.t_apraksis);
        this.m = r17.getString(R.string.t_mzmor_engel);
        this.mm = r17.getString(R.string.t_mzmor_night);
        this.b = r17.getString(R.string.t_the_bible);
        this.n = r17.getString(R.string.t_predictions);
        this.legendList = new ArrayList();
        this.activity = r17;
        this.ashyaData = new ArrayList();
        this.bakrData = new ArrayList();
        this.nabwoatData = new ArrayList();
        this.bolesData = new ArrayList();
        this.kaselikonData = new ArrayList();
        this.abraksisData = new ArrayList();
        this.sanksarData = new ArrayList();
        this.engelKodasData = new ArrayList();
        this.mzmorNightData = new ArrayList();
        this.engelKodaswithoutMzmor = new ArrayList();
        this.mzmorKodas = new ArrayList();
        this.levelsMap = new HashMap();
        this.levels = new ArrayList();
        InputStream r0 = r17.getAssets().open("biblenames.xml");     // Catch: IOException -> L9
        byte[] r2 = new byte[r0.available()];     // Catch: IOException -> L9
        r0.read(r2);     // Catch: IOException -> L9
        String[] r02 = new String(r2).split("\n");     // Catch: IOException -> L9
        this.books = new String[73];     // Catch: IOException -> L9
        this.d = new Hashtable();     // Catch: IOException -> L9
    L4:
        if (r1 >= r02.length) goto L11;
        if (r1 >= 73) goto L8;
        this.d.put(Integer.valueOf(r1), r02[r1]);     // Catch: IOException -> L9
        this.books[r1] = r02[r1];     // Catch: IOException -> L9
    L8:
        r1 = r1 + 1;
    L11:
        Calendar r03 = Calendar.getInstance();
        r03.set(11, r03.get(11));
        CopticCalendar r12 = new CopticCalendar();
        calculateEvents(r12.get(5), r12.get(2), r12.get(1), r03.get(5), r03.get(2), r03.get(1), dayOfWeek(r03.get(7)));
        this.realDayName = dayOfWeek(r03.get(7));
        if (r18 == true) goto L14;
        return;
    L14:
        if (r03.get(11) < 17) goto L23;
        this.after5 = true;
        this.eidName = "";
        this.splashEid = "";
        this.legendList = new ArrayList();
        this.ashyaData = new ArrayList();
        this.bakrData = new ArrayList();
        this.nabwoatData = new ArrayList();
        this.bolesData = new ArrayList();
        this.kaselikonData = new ArrayList();
        this.abraksisData = new ArrayList();
        this.sanksarData = new ArrayList();
        this.engelKodasData = new ArrayList();
        this.mzmorNightData = new ArrayList();
        this.engelKodaswithoutMzmor = new ArrayList();
        this.mzmorKodas = new ArrayList();
        this.levelsMap = new HashMap();
        this.levels = new ArrayList();
        Calendar r13 = Calendar.getInstance();
        r13.set(11, r03.get(11));
        CopticCalendar r04 = new CopticCalendar();
        r13.set(6, r13.get(6) + 1);
        r04.set(6, r04.get(6) + 1);
        calculateEvents(r04.get(5), r04.get(2), r04.get(1), r13.get(5), r13.get(2), r13.get(1), dayOfWeek(r13.get(7)));
        return;
    L23:
        return;
    L9:
        e = move-exception;
        e.printStackTrace();
        goto L11
    }

    private void getDynamicData() {
        int r1 = 0;
        boolean r2 = false;
        boolean r3 = false;
        boolean r4 = false;
        boolean r5 = false;
        boolean r6 = false;
        boolean r7 = false;
        boolean r8 = false;
        boolean r9 = false;
        boolean r10 = false;
    L4:
        if (r1 >= this.legendList.size()) goto L57;
        if (this.legendList.get(r1).getNick().contains(this.n) == false) goto L15;
        r2 = true;
        r3 = false;
    L8:
        r4 = false;
    L9:
        r5 = false;
    L10:
        r6 = false;
    L11:
        r7 = false;
    L12:
        r8 = false;
    L13:
        r10 = false;
    L38:
        if (r2 == false) goto L40;
        this.nabwoatData.add(this.legendList.get(r1).getNick());
    L56:
        r1 = r1 + 1;
        goto L4
    L40:
        if (r3 == false) goto L42;
        this.ashyaData.add(this.legendList.get(r1).getNick());
        goto L56
    L42:
        if (r4 == false) goto L44;
        this.bakrData.add(this.legendList.get(r1).getNick());
        goto L56
    L44:
        if (r5 == false) goto L46;
        this.bolesData.add(this.legendList.get(r1).getNick());
        goto L56
    L46:
        if (r6 == false) goto L48;
        this.abraksisData.add(this.legendList.get(r1).getNick());
        goto L56
    L48:
        if (r7 == false) goto L50;
        this.kaselikonData.add(this.legendList.get(r1).getNick());
        goto L56
    L50:
        if (r8 == false) goto L52;
        this.engelKodaswithoutMzmor.add(this.legendList.get(r1).getNick());
        goto L56
    L52:
        if (r9 == false) goto L54;
        this.mzmorKodas.add(this.legendList.get(r1).getNick());
        goto L56
    L54:
        if (r10 == false) goto L56;
        this.mzmorNightData.add(this.legendList.get(r1).getNick());
        goto L56
    L15:
        if (this.legendList.get(r1).getNick().contains(this.m) == false) goto L18;
        r2 = false;
        r3 = false;
        r4 = false;
        r5 = false;
        r6 = false;
        r7 = false;
        r8 = false;
        r9 = true;
        goto L13
    L18:
        if (this.legendList.get(r1).getNick().contains(this.b) == false) goto L21;
        r2 = false;
        r3 = false;
        r4 = false;
        r5 = false;
        r6 = false;
        r7 = false;
        r8 = true;
        r9 = false;
        goto L13
    L21:
        if (this.legendList.get(r1).getNick().contains(this.ma) == false) goto L24;
        r2 = false;
        r3 = true;
        goto L8
    L24:
        if (this.legendList.get(r1).getNick().contains(this.mb) == false) goto L27;
        r2 = false;
        r3 = false;
        r4 = true;
        goto L9
    L27:
        if (this.legendList.get(r1).getNick().contains(this.bo) == false) goto L30;
        r2 = false;
        r3 = false;
        r4 = false;
        r5 = true;
        goto L10
    L30:
        if (this.legendList.get(r1).getNick().contains(this.ka) == false) goto L33;
        r2 = false;
        r3 = false;
        r4 = false;
        r5 = false;
        r6 = false;
        r7 = true;
        goto L12
    L33:
        if (this.legendList.get(r1).getNick().contains(this.ab) == false) goto L36;
        r2 = false;
        r3 = false;
        r4 = false;
        r5 = false;
        r6 = true;
        goto L11
    L36:
        if (this.legendList.get(r1).getNick().contains(this.mm) == false) goto L38;
        r2 = false;
        r3 = false;
        r4 = false;
        r5 = false;
        r6 = false;
        r7 = false;
        r8 = false;
        r10 = true;
        goto L38
    }

    private void getDataStatic() {
        int r0 = 0;
        int r1 = 0;
        boolean r2 = false;
        boolean r3 = false;
        boolean r4 = false;
        boolean r5 = false;
        boolean r6 = false;
        boolean r7 = false;
    L4:
        if (r1 >= this.legendList.size()) goto L50;
        if (this.legendList.get(r1).getNick().contains("القداس") == false) goto L14;
        r2 = false;
    L8:
        r3 = false;
    L9:
        r4 = false;
    L10:
        r5 = false;
    L11:
        r6 = false;
    L12:
        r7 = false;
    L37:
        if (r2 == false) goto L39;
        this.ashyaData.add(this.legendList.get(r1).getNick());
    L49:
        r1 = r1 + 1;
        goto L4
    L39:
        if (r3 == false) goto L41;
        this.bakrData.add(this.legendList.get(r1).getNick());
        goto L49
    L41:
        if (r4 == false) goto L43;
        this.bolesData.add(this.legendList.get(r1).getNick());
        goto L49
    L43:
        if (r5 == false) goto L45;
        this.abraksisData.add(this.legendList.get(r1).getNick());
        goto L49
    L45:
        if (r6 == false) goto L47;
        this.kaselikonData.add(this.legendList.get(r1).getNick());
        goto L49
    L47:
        if (r7 == false) goto L49;
        this.engelKodasData.add(this.legendList.get(r1).getNick());
        goto L49
    L14:
        if (this.legendList.get(r1).getNick().contains("المزمور") == false) goto L23;
        int r8 = r1 - 1;
        if (r8 <= 0) goto L23;
        if (this.legendList.get(r8).getNick().contains("عشية") == true) goto L23;
        if (this.legendList.get(r8).getNick().contains("باكر") == true) goto L23;
        this.engelKodasData.add(this.legendList.get(r1).getNick());
        r2 = false;
        r3 = false;
        r4 = false;
        r5 = false;
        r6 = false;
        r7 = true;
    L23:
        if (this.legendList.get(r1).getNick().contains("عشية") == false) goto L26;
        r2 = true;
        goto L8
    L26:
        if (this.legendList.get(r1).getNick().contains("باكر") == false) goto L29;
        r2 = false;
        r3 = true;
        goto L9
    L29:
        if (this.legendList.get(r1).getNick().contains("البولس") == false) goto L32;
        r2 = false;
        r3 = false;
        r4 = true;
        goto L10
    L32:
        if (this.legendList.get(r1).getNick().contains("الكاثوليكون") == false) goto L35;
        r2 = false;
        r3 = false;
        r4 = false;
        r5 = false;
        r6 = true;
        goto L12
    L35:
        if (this.legendList.get(r1).getNick().contains("الإبركسيس") == false) goto L37;
        r2 = false;
        r3 = false;
        r4 = false;
        r5 = true;
        goto L11
    L50:
        boolean r12 = false;
    L52:
        if (r0 >= this.engelKodasData.size()) goto L60;
        if (this.engelKodasData.get(r0).contains("الإنجيل") == false) goto L56;
        r12 = true;
    L56:
        if (r12 == false) goto L58;
        this.engelKodaswithoutMzmor.add(this.engelKodasData.get(r0));
    L59:
        r0 = r0 + 1;
        goto L52
    L58:
        this.mzmorKodas.add(this.engelKodasData.get(r0));
        goto L59
    }

    public Calendar getOrthodoxEaster(int r7) {
        Calendar r0 = Calendar.getInstance();
        int r3 = (((r7 % 19) * 19) + 15) % 30;
        int r1 = (((((((r7 % 4) * 2) + ((r7 % 7) * 4)) + (r3 * 6)) + 6) % 7) + r3) + 13;
        if (r1 <= 39) goto L6;
        r0.set(r7, 4, r1 - 39);
    L9:
        return r0;
    L6:
        if (r1 <= 9) goto L8;
        r0.set(r7, 3, r1 - 9);
        goto L9
    L8:
        r0.set(r7, 2, r1 + 22);
        goto L9
    }

    public String calculateEvents(int r18, int r19, int r20, int r21, int r22, int r23, String r24) {
        if (r24.equals("Sat") == false) goto L6;
        this.eidName = "sanwy_sat";
    L17:
        int r14 = 1;
        if (r19 != 11) goto L41;
        if (r18 < 1) goto L41;
        if (r18 > 16) goto L41;
        this.eidName = "soom_stmary";
        if (r18 != 13) goto L27;
        if (r19 != 11) goto L27;
        this.eidName = "eid_tagali";
        loadingAyam(13, 11);
        return "";
    L27:
        if (r24.equals("Sun") == false) goto L37;
        if (r18 >= 8) goto L31;
    L34:
        loadingA7ad(r14, r19);
        this.eidName = "soom_stmary_sun";
        return "";
    L31:
        if (r18 >= 15) goto L33;
        r14 = 2;
        goto L34
    L33:
        r14 = 3;
        goto L34
    L37:
        if (r24.equals("Sat") == false) goto L39;
        this.eidName = "soom_stmary_sat";
    L39:
        loadingAyam(r18, r19);
        return "";
    L41:
        Calendar r11 = Calendar.getInstance();
        r11.set(1, r23);
        r11.set(2, r22);
        r11.set(5, r21);
        Log.d("cal", "cal " + r11.getTime().toString());
        Log.d("cal", "cal gmt " + r11.getTime().toGMTString());
        Calendar r8 = getOrthodoxEaster(r23);
        int r13 = r8.get(5);
        int r10 = r8.get(2);
        if (r23 != r8.get(1)) goto L47;
        if (r22 != r10) goto L47;
        if (r21 != r13) goto L47;
        this.eidName = "kyama";
        this.splashEid = "kyama";
        loading7maseen(1, 1);
        return "read eid kyama";
    L47:
        Calendar r3 = getOrthodoxEaster(r23);
        r3.set(6, r3.get(6) - 69);
        Log.d("cal", "" + r3.getTime().toString());
        Calendar r82 = getOrthodoxEaster(r23);
        r82.set(6, r82.get(6) + (-66));
        if (isCalendarsEqual(r11, r3) == 0) goto L52;
        if (r11.compareTo(r3) == 1) goto L52;
    L54:
        if (r11.compareTo(r82) == 0) goto L1201;
        Calendar r32 = getOrthodoxEaster(r23);
        r32.set(6, r32.get(6) - 57);
        Calendar r83 = getOrthodoxEaster(r23);
        r83.set(6, r83.get(6) - 9);
        if ((Integer.valueOf(String.valueOf(r20).substring(2, 4)).intValue() % 4) != 0) goto L64;
        if (r19 != 3) goto L61;
        if (r18 <= 28) goto L61;
    L67:
        boolean r102 = true;
    L70:
        if (r11.compareTo(r32) == (-1)) goto L72;
    L74:
        r102 = false;
        boolean r12 = false;
    L75:
        if (r18 != 29) goto L82;
        if (r19 != 6) goto L82;
        if (r24.equals("Sun") == false) goto L80;
        this.eidName = "eid_beshara_sun";
        goto L82
    L80:
        this.eidName = "eid_beshara";
    L82:
        if (this.eidName.equals("sanwy_sun") == false) goto L88;
        if (r102 == false) goto L86;
        if (r12 == false) goto L86;
        this.eidName = "sanwy_sun_1";
    L86:
        this.eidName = "sanwy_sun_2";
    L88:
        if (r19 == 4) goto L103;
        if (r19 == 5) goto L103;
        if (r18 == 29) goto L93;
    L98:
        if (r18 != 29) goto L103;
        if (r24.equals("Sun") == true) goto L103;
        this.eidName = "29";
        goto L103
    L93:
        if (r24.equals("Sun") == false) goto L98;
        if (r102 == false) goto L97;
        if (r12 == false) goto L97;
        this.eidName = "29_sun_1";
    L97:
        this.eidName = "29_sun_2";
    L103:
        if (isCalendarsEqual(r11, r32) == 0) goto L107;
        int r132 = 1;
        if (r11.compareTo(r32) == 1) goto L109;
    L342:
        Calendar r33 = getOrthodoxEaster(r23);
        r33.set(6, r33.get(6) + 1);
        Calendar r84 = getOrthodoxEaster(r23);
        r84.set(6, r84.get(6) + 49);
        if (isCalendarsEqual(r11, r33) == 0) goto L347;
        if (r11.compareTo(r33) == 1) goto L347;
    L349:
        if (isCalendarsEqual(r11, r84) != 0) goto L352;
    L1177:
        int r5 = (r11.get(6) - r33.get(6)) + 2;
        loading7maseen(r5, 1);
        this.splashEid = "kyama";
        if (r5 != 40) goto L1181;
        this.eidName = "soud";
    L1199:
        return "read from katmars el kamaseen we are in day=" + r5;
    L1181:
        if (r5 != 50) goto L1184;
        this.eidName = "helol";
        goto L1199
    L1184:
        if (r5 == 51) goto L1199;
        if (r5 >= 40) goto L1194;
        if (r18 == 24) goto L1190;
    L1192:
        this.eidName = "before_soud";
        goto L1199
    L1190:
        if (r19 != 8) goto L1192;
        this.eidName = "masr_before_soud";
        goto L1199
    L1194:
        if (r18 != 24) goto L1197;
        if (r19 != 8) goto L1197;
        this.eidName = "masr_before_helol";
    L1197:
        this.eidName = "before_helol";
        goto L1199
    L352:
        if (r18 == 24) goto L354;
    L364:
        Calendar r4 = getOrthodoxEaster(r23);
        r4.set(6, (r4.get(6) + 49) + 50);
        if (isCalendarsEqual(r11, r33) == 0) goto L369;
        if (r11.compareTo(r33) == 1) goto L369;
    L371:
        if (isCalendarsEqual(r11, r4) != 0) goto L392;
    L373:
        if (r19 < 10) goto L385;
        if (r18 >= 5) goto L378;
        if (r19 == 10) goto L385;
    L378:
        if (r19 != 10) goto L392;
        if (r18 != 5) goto L392;
        if (r24.equals("Sun") == false) goto L383;
        this.eidName = "eid_rosol_sun";
        goto L392
    L383:
        this.eidName = "eid_rosol_days";
    L385:
        if (r24.equals("Sat") == false) goto L388;
        this.eidName = "rosol_sat";
    L388:
        if (r24.equals("Sun") == false) goto L390;
        this.eidName = "rosol_sun";
        goto L392
    L390:
        this.eidName = "rosol_days";
    L392:
        if ((Integer.valueOf(String.valueOf(r20).substring(2, 4)).intValue() % 4) == 0) goto L394;
    L443:
        int r112 = 3;
        int r85 = 28;
    L444:
        if (r19 != r112) goto L458;
        if (r18 != 28) goto L458;
        if (r24.equals("Fri") == false) goto L450;
    L455:
        loadingAyam(28, 3);
        this.eidName = "barmoon_melad_lastday";
        this.splashEid = "melad";
        return "read barmon melad 28 kiahk";
    L450:
        if (r24.equals("Thu") == true) goto L455;
        if (r24.equals("Wed") == true) goto L455;
        if (r24.equals("Tue") == true) goto L455;
    L458:
        if (r19 != 3) goto L466;
        if (r18 != 27) goto L466;
        if (r24.equals("Fri") == false) goto L466;
        loadingAyam(28, 3);
        this.eidName = "barmoon_melad";
        return "read barmon melad 28 kiahk";
    L466:
        if (r19 != 3) goto L472;
        if (r18 != 28) goto L472;
        if (r24.equals("Sat") == false) goto L472;
        loadingAyam(28, 3);
        this.eidName = "barmoon_melad_lastday";
        this.splashEid = "melad";
        return "read barmon melad 28 kiahk";
    L472:
        if (r19 == 3) goto L474;
    L479:
        if (r19 == 3) goto L481;
    L486:
        if (r19 != 3) goto L494;
        if (r18 != 27) goto L494;
        if (r24.equals("Sat") == false) goto L494;
        loadingAyam(28, 3);
        this.eidName = "barmoon_melad";
        return "read barmon melad 28 kiahk";
    L494:
        if (r19 != 3) goto L501;
        if (r18 != 28) goto L501;
        if (r24.equals("Sun") == false) goto L501;
        loadingAyam(28, 3);
        this.splashEid = "melad";
        this.eidName = "barmoon_melad_lastday_sun";
        return "read barmon melad 28 kiahk";
    L501:
        if (r19 == 2) goto L505;
        if (r19 == 3) goto L505;
        boolean r34 = false;
        boolean r52 = false;
        int r86 = 4;
    L695:
        if (r19 == r86) goto L697;
    L702:
        if (r19 != r86) goto L710;
        if (r18 != 9) goto L710;
        if (r24.equals("Fri") == false) goto L710;
        loadingAyam(10, r86);
        this.eidName = "barmoon_gotas";
        return "read barmon eid 7'otas 10 toba";
    L710:
        if (r19 != r86) goto L716;
        if (r18 != 10) goto L716;
        if (r24.equals("Sat") == false) goto L716;
        loadingAyam(10, r86);
        this.eidName = "barmoon_gotas_lastday";
        return "read barmon eid 7'otas 10 toba";
    L716:
        if (r19 == r86) goto L718;
    L723:
        if (r19 != r86) goto L731;
        if (r18 != 9) goto L731;
        if (r24.equals("Sat") == false) goto L731;
        loadingAyam(10, r86);
        this.eidName = "barmoon_gotas";
        return "read barmon eid 7'otas 10 toba";
    L731:
        if (r19 != r86) goto L737;
        if (r18 != 10) goto L737;
        if (r24.equals("Sun") == false) goto L737;
        this.splashEid = "gotas";
        loadingAyam(10, r86);
        this.eidName = "barmoon_gotas_lastday_sun";
        return "read barmon eid 7'otas 10 toba";
    L737:
        if (r19 == 0) goto L739;
    L767:
        if (r19 != 0) goto L779;
        if (r18 <= 16) goto L779;
        if (r18 >= 20) goto L779;
        if (r18 != 17) goto L775;
        this.eidName = "elSaleb";
    L776:
        loadingAyam(r18, r19);
        return "";
    L775:
        this.eidName = "elSaleb_after";
    L779:
        if (r24.equals("Sun") == false) goto L1073;
        if (r18 != 6) goto L786;
        if (r19 != 4) goto L786;
        loadingAyam(6, 4);
        this.eidName = "eid_7etan";
        return "read 7'tan";
    L786:
        if (r18 != 11) goto L791;
        if (r19 != 4) goto L791;
        loadingAyam(11, 4);
        this.eidName = "eid_gotas";
        this.splashEid = "gotas";
        return "read 3id gohats";
    L791:
        if (r18 != 13) goto L795;
        if (r19 != 4) goto L795;
        this.eidName = "ors_kana_elgalel";
        loadingAyam(13, 4);
        return "";
    L795:
        if (r18 != 13) goto L801;
        if (r19 != 11) goto L801;
        this.eidName = "eid_tagali";
        loadingAyam(13, 11);
        return "";
    L801:
        if (r18 != 8) goto L807;
        if (r19 != 5) goto L807;
        this.eidName = "jesus_hakal";
        loadingAyam(8, 5);
        return "read 3id";
    L807:
        if (r18 != 10) goto L813;
        if (r19 != 6) goto L813;
        loadingAyam(10, 6);
        this.eidName = "elSaleb";
        return "read 3id ";
    L813:
        if (r18 != 29) goto L826;
        if (r19 == 0) goto L826;
        if (r19 == 2) goto L826;
        if (r19 == 3) goto L826;
        if (r19 == 4) goto L826;
        loadingAyam(29, 6);
        if (r19 != 6) goto L837;
        this.eidName = "eid_beshara_sun";
        return "read 3id ";
    L837:
        if (r18 >= 8) goto L840;
        int r42 = 1;
    L850:
        if (r19 != 0) goto L857;
        if (r18 != 1) goto L857;
        if (r24.equals("Sun") == false) goto L857;
        loadingAyam(1, 0);
        return "";
    L857:
        if (r19 == 0) goto L859;
    L864:
        if (r19 == 0) goto L866;
    L871:
        if (r19 != 0) goto L879;
        if (r18 != 22) goto L879;
        if (r24.equals("Sun") == false) goto L879;
        loadingA7ad(3, 0);
        return "";
    L879:
        if (r19 != 0) goto L885;
        if (r18 != 29) goto L885;
        if (r24.equals("Sun") == false) goto L885;
        loadingA7ad(4, 0);
        return "";
    L885:
        if (r18 != 29) goto L893;
        if (r19 != 6) goto L893;
        if (r24.equals("Sun") == false) goto L891;
        this.eidName = "eid_beshara_sun";
        goto L893
    L891:
        this.eidName = "eid_beshara";
    L893:
        if (this.eidName.equals("sanwy_sun") == false) goto L899;
        if (r102 == false) goto L897;
        if (r12 == false) goto L897;
        this.eidName = "sanwy_sun_1";
    L897:
        this.eidName = "sanwy_sun_2";
    L899:
        if (r19 != 4) goto L901;
    L915:
        if (r52 == true) goto L917;
        if (r34 == false) goto L1036;
        if (r18 != 29) goto L982;
        int r35 = 2;
        if (r19 != 2) goto L984;
        loadingA7ad(1, 3);
        return "";
    L984:
        if (r18 != 6) goto L989;
        if (r19 != 3) goto L989;
        loadingA7ad(r35, 3);
        return "";
    L989:
        if (r18 != 13) goto L994;
        if (r19 != 3) goto L994;
        loadingA7ad(3, 3);
        return "";
    L994:
        if (r18 != 20) goto L999;
        if (r19 != 3) goto L999;
        loadingA7ad(4, 3);
        return "";
    L999:
        if (r19 != 0) goto L1006;
        if (r18 != 1) goto L1006;
        if (r24.equals("Sun") == false) goto L1006;
        loadingAyam(1, 0);
        return "";
    L1006:
        if (r19 == 0) goto L1008;
    L1013:
        if (r19 == 0) goto L1015;
    L1020:
        if (r19 == 0) goto L1022;
    L1027:
        if (r19 == 0) goto L1029;
    L1034:
        readKatA7adOthers(r18, r19, r42);
    L1071:
        return "";
    L1029:
        if (r18 != 29) goto L1034;
        if (r24.equals("Sun") == false) goto L1034;
        this.eidName = "sanwy_sun_2";
        loadingA7ad(4, 0);
        return "";
    L1022:
        if (r18 != 22) goto L1027;
        if (r24.equals("Sun") == false) goto L1027;
        loadingA7ad(3, 0);
        return "";
    L1015:
        if (r18 != 15) goto L1020;
        if (r24.equals("Sun") == false) goto L1020;
        loadingA7ad(2, 0);
        return "";
    L1008:
        if (r18 != 8) goto L1013;
        if (r24.equals("Sun") == false) goto L1013;
        loadingA7ad(1, 0);
        return "";
    L982:
        r35 = 2;
        goto L984
    L1036:
        if (r19 != 0) goto L1042;
        if (r18 != 1) goto L1042;
        if (r24.equals("Sun") == false) goto L1042;
        loadingAyam(1, 0);
        return "";
    L1042:
        if (r19 == 0) goto L1044;
    L1049:
        if (r19 == 0) goto L1051;
    L1056:
        if (r19 == 0) goto L1058;
    L1063:
        if (r19 == 0) goto L1065;
    L1070:
        readKatA7adOthers(r18, r19, r42);
        goto L1071
    L1065:
        if (r18 != 29) goto L1070;
        if (r24.equals("Sun") == false) goto L1070;
        this.eidName = "sanwy_sun_2";
        loadingA7ad(4, 0);
        return "";
    L1058:
        if (r18 != 22) goto L1063;
        if (r24.equals("Sun") == false) goto L1063;
        loadingA7ad(3, 0);
        return "";
    L1051:
        if (r18 != 15) goto L1056;
        if (r24.equals("Sun") == false) goto L1056;
        loadingA7ad(2, 0);
        return "";
    L1044:
        if (r18 != 8) goto L1049;
        if (r24.equals("Sun") == false) goto L1049;
        loadingA7ad(1, 0);
        return "";
    L917:
        if (r18 != 30) goto L922;
        int r36 = 2;
        if (r19 != 2) goto L924;
        loadingA7ad(1, 3);
        return "";
    L924:
        if (r18 != 7) goto L929;
        if (r19 != 3) goto L929;
        loadingA7ad(r36, 3);
        return "";
    L929:
        if (r18 != 14) goto L934;
        if (r19 != 3) goto L934;
        loadingA7ad(3, 3);
        return "";
    L934:
        if (r18 != 21) goto L939;
        if (r19 != 3) goto L939;
        loadingA7ad(4, 3);
        return "";
    L939:
        if (r19 != 0) goto L946;
        if (r18 != 1) goto L946;
        if (r24.equals("Sun") == false) goto L946;
        loadingAyam(1, 0);
        return "";
    L946:
        if (r19 == 0) goto L948;
    L953:
        if (r19 == 0) goto L955;
    L960:
        if (r19 == 0) goto L962;
    L967:
        if (r19 == 0) goto L969;
    L974:
        readKatA7adOthers(r18, r19, r42);
        goto L1071
    L969:
        if (r18 != 29) goto L974;
        if (r24.equals("Sun") == false) goto L974;
        this.eidName = "sanwy_sun_2";
        loadingA7ad(4, 0);
        return "";
    L962:
        if (r18 != 22) goto L967;
        if (r24.equals("Sun") == false) goto L967;
        loadingA7ad(3, 0);
        return "";
    L955:
        if (r18 != 15) goto L960;
        if (r24.equals("Sun") == false) goto L960;
        loadingA7ad(2, 0);
        return "";
    L948:
        if (r18 != 8) goto L953;
        if (r24.equals("Sun") == false) goto L953;
        loadingA7ad(1, 0);
        return "";
    L922:
        r36 = 2;
        goto L924
    L901:
        if (r19 == 5) goto L915;
        int r7 = 29;
        if (r18 == 29) goto L905;
    L911:
        if (r18 != r7) goto L915;
        if (r24.equals("Sun") == true) goto L915;
        this.eidName = "29";
        goto L915
    L905:
        if (r24.equals("Sun") == false) goto L910;
        if (r102 == false) goto L909;
        if (r12 == false) goto L909;
        this.eidName = "29_sun_1";
    L909:
        this.eidName = "29_sun_2";
        goto L915
    L910:
        r7 = 29;
        goto L911
    L866:
        if (r18 != 15) goto L871;
        if (r24.equals("Sun") == false) goto L871;
        loadingA7ad(2, 0);
        return "";
    L859:
        if (r18 != 8) goto L864;
        if (r24.equals("Sun") == false) goto L864;
        loadingA7ad(1, 0);
        return "";
    L840:
        if (r18 >= 15) goto L843;
        r42 = 2;
        goto L850
    L843:
        if (r18 >= 22) goto L846;
        r42 = 3;
        goto L850
    L846:
        if (r18 >= 29) goto L848;
        r42 = 4;
        goto L850
    L848:
        r42 = 5;
    L826:
        if (r18 != 24) goto L832;
        if (r19 != 8) goto L832;
        this.eidName = "jesus_inside_egypt_sun";
        loadingAyam(24, 8);
        return "read 3id ";
    L832:
        if (r18 != 17) goto L837;
        if (r19 != 0) goto L837;
        loadingAyam(17, 0);
        this.eidName = "elSaleb";
        return "read 3id ";
    L1073:
        if (r18 != 29) goto L1078;
        int r37 = 3;
        if (r19 != 3) goto L1080;
        this.splashEid = "melad";
        this.eidName = "eid_melad";
        loadingAyam(r18, r19);
        return "read from katamars ayam";
    L1080:
        if (r18 != 28) goto L1085;
        if (r19 != r37) goto L1085;
        this.splashEid = "melad";
    L1167:
        if (r18 == 29) goto L1169;
    L1175:
        loadingAyam(r18, r19);
        return "read from katamars ayam";
    L1169:
        if (r24.equals("Sun") == true) goto L1175;
        if (r19 == 4) goto L1175;
        if (r19 == 5) goto L1175;
        this.eidName = "29";
    L1085:
        if (r18 != 30) goto L1089;
        if (r19 != r37) goto L1089;
        this.eidName = "eid_melad_after";
    L1089:
        if (r18 >= 6) goto L1092;
        if (r19 != 4) goto L1092;
        this.eidName = "eid_melad_after";
    L1092:
        if (r18 != 6) goto L1096;
        if (r19 != 4) goto L1096;
        this.eidName = "eid_7etan";
    L1096:
        if (r18 != 11) goto L1100;
        if (r19 != 4) goto L1100;
        this.eidName = "eid_gotas";
        this.splashEid = "gotas";
    L1100:
        if (r18 != 12) goto L1104;
        if (r19 != 4) goto L1104;
        this.eidName = "eid_gotas_second_day";
    L1104:
        if (r18 != 10) goto L1108;
        if (r19 != 4) goto L1108;
        this.eidName = "barmoon_gotas_lastday";
        this.splashEid = "gotas";
    L1108:
        if (r18 != 13) goto L1112;
        if (r19 != 4) goto L1112;
        this.eidName = "ors_kana_elgalel";
    L1112:
        if (r18 != 21) goto L1116;
        if (r19 == 4) goto L1167;
    L1116:
        if (r18 != 8) goto L1121;
        if (r19 != 5) goto L1121;
        this.eidName = "jesus_hakal";
    L1121:
        if (r18 != 10) goto L1125;
        int r38 = 6;
        if (r19 != 6) goto L1127;
        this.eidName = "elSaleb";
    L1127:
        if (r18 != 29) goto L1134;
        if (r19 != r38) goto L1134;
        if (r24.equals("Sun") == false) goto L1132;
        this.eidName = "eid_beshara_sun";
        goto L1167
    L1132:
        this.eidName = "eid_beshara";
    L1134:
        if (r18 != 24) goto L1139;
        if (r19 == 8) goto L1167;
    L1139:
        if (r18 != 1) goto L1143;
        int r39 = 11;
        if (r19 == 11) goto L1167;
    L1145:
        if (r18 != 13) goto L1149;
        if (r19 != r39) goto L1149;
        this.eidName = "eid_tagali";
    L1149:
        if (r18 != 16) goto L1153;
        if (r19 == r39) goto L1167;
    L1153:
        if (r18 != 1) goto L1157;
        if (r19 == 0) goto L1167;
    L1157:
        if (r18 != 17) goto L1161;
        if (r19 != 0) goto L1161;
        this.eidName = "elSaleb";
    L1161:
        if (r18 != 16) goto L1164;
        if (r19 == 2) goto L1167;
    L1164:
        if (r18 <= 16) goto L1167;
        if (r19 == 2) goto L1167;
        goto L1167
    L1143:
        r39 = 11;
        goto L1145
    L1125:
        r38 = 6;
        goto L1127
    L1078:
        r37 = 3;
        goto L1080
    L739:
        if (r18 > 16) goto L767;
        if (r24.equals("Sun") == false) goto L762;
        if (r18 != 1) goto L745;
        this.eidName = "nayrooz_sun";
    L747:
        if (r18 >= 8) goto L750;
        int r142 = 1;
    L759:
        loadingA7ad(r142, r19);
        return "";
    L750:
        if (r18 >= 15) goto L753;
        r142 = 2;
        goto L759
    L753:
        if (r18 >= 22) goto L756;
        r142 = 3;
        goto L759
    L756:
        if (r18 >= 29) goto L758;
        r142 = 4;
        goto L759
    L758:
        r142 = 5;
        goto L759
    L745:
        this.eidName = "nayrooz_sun_after";
        goto L747
    L762:
        if (r18 != 1) goto L764;
        this.eidName = "nayrooz";
    L765:
        loadingAyam(r18, r19);
        return "";
    L764:
        this.eidName = "nayrooz_after";
        goto L765
    L718:
        if (r18 != 8) goto L723;
        if (r24.equals("Fri") == false) goto L723;
        loadingAyam(10, r86);
        this.eidName = "barmoon_gotas";
        return "read barmon eid 7'otas 10 toba";
    L697:
        if (r18 != 10) goto L702;
        if (r24.equals("Fri") == false) goto L702;
        loadingAyam(10, r86);
        this.eidName = "barmoon_gotas_lastday";
        this.splashEid = "gotas";
        return "read barmon eid 7'otas 10 tobak";
    L505:
        CopticCalendar r113 = new CopticCalendar();
        r113.set(5, 1);
        r113.set(2, 3);
        r113.set(1, r20);
        if (dayOfWeek(r113.get(7)) != "Sun") goto L526;
        if (r19 != 3) goto L526;
        if (r18 < 1) goto L512;
        if (r18 >= 8) goto L512;
        namekiahEidName(1, r24);
    L673:
        boolean r310 = false;
    L553:
        boolean r53 = false;
    L674:
        if (r19 == 2) goto L676;
    L694:
        r86 = 4;
        boolean r16 = r53;
        r52 = r310;
        r34 = r16;
        goto L695
    L676:
        if (r18 < 16) goto L694;
        if (r310 == true) goto L679;
    L681:
        if (r53 == false) goto L688;
        if (r18 == 30) goto L694;
        if (r18 == 29) goto L694;
    L688:
        if (r24.equals("Sat") == false) goto L691;
        this.eidName = "hator_soom_melad_sat";
        goto L694
    L691:
        if (r24.equals("Sun") == false) goto L693;
        this.eidName = "hator_soom_melad_sun";
        goto L694
    L693:
        this.eidName = "hator_soom_melad_others";
        goto L694
    L679:
        if (r18 != 30) goto L681;
    L512:
        if (r18 < 8) goto L516;
        int r311 = 15;
        if (r18 >= 15) goto L517;
        namekiahEidName(2, r24);
    L517:
        if (r18 < r311) goto L522;
        if (r18 >= 22) goto L522;
        namekiahEidName(3, r24);
    L522:
        if (r18 < 22) goto L673;
        if (r18 >= r85) goto L673;
        namekiahEidName(4, r24);
        goto L673
    L516:
        r311 = 15;
    L526:
        if (dayOfWeek(r113.get(7)) != "Mon") goto L556;
        if (r19 == 2) goto L530;
    L532:
        int r312 = 1;
    L534:
        if (r19 == 3) goto L536;
    L552:
        r310 = true;
        goto L553
    L536:
        if (r18 < r312) goto L539;
        if (r18 >= 7) goto L539;
        namekiahEidName(r312, r24);
    L539:
        if (r18 < 7) goto L544;
        if (r18 >= 14) goto L544;
        namekiahEidName(2, r24);
    L544:
        if (r18 < 14) goto L549;
        if (r18 >= 21) goto L549;
        namekiahEidName(3, r24);
    L549:
        if (r18 < 21) goto L552;
        if (r18 >= r85) goto L552;
        namekiahEidName(4, r24);
        goto L552
    L530:
        if (r18 != 30) goto L532;
        r312 = 1;
        namekiahEidName(1, r24);
        goto L534
    L556:
        if (dayOfWeek(r113.get(7)) != "Tue") goto L586;
        if (r19 == 2) goto L560;
        int r313 = 1;
    L567:
        if (r19 == 3) goto L569;
    L584:
        r310 = false;
        r53 = true;
        goto L674
    L569:
        if (r18 < r313) goto L573;
        if (r18 >= 6) goto L573;
        namekiahEidName(r313, r24);
    L573:
        if (r18 < 6) goto L576;
        if (r18 >= 13) goto L576;
        namekiahEidName(2, r24);
    L576:
        if (r18 < 13) goto L581;
        if (r18 >= 20) goto L581;
        namekiahEidName(3, r24);
    L581:
        if (r18 < 20) goto L584;
        if (r18 >= r85) goto L584;
        namekiahEidName(4, r24);
        goto L584
    L560:
        if (r18 != 30) goto L562;
        r313 = 1;
        namekiahEidName(1, r24);
        goto L567
    L562:
        r313 = 1;
        if (r18 != 29) goto L567;
        namekiahEidName(1, r24);
        goto L567
    L586:
        if (dayOfWeek(r113.get(7)) != "Wed") goto L609;
        if (r19 != 3) goto L609;
        if (r18 < 1) goto L595;
        if (r18 >= 12) goto L595;
        namekiahEidName(1, r24);
    L595:
        if (r18 < 12) goto L600;
        if (r18 >= 19) goto L600;
        namekiahEidName(2, r24);
    L600:
        if (r18 < 19) goto L605;
        if (r18 >= 26) goto L605;
        namekiahEidName(3, r24);
    L605:
        if (r18 < 26) goto L673;
        if (r18 >= r85) goto L673;
        namekiahEidName(4, r24);
    L609:
        if (dayOfWeek(r113.get(7)) != "Thu") goto L630;
        if (r19 != 3) goto L630;
        if (r18 < 1) goto L616;
        if (r18 >= 11) goto L616;
        namekiahEidName(1, r24);
    L616:
        if (r18 < 11) goto L621;
        if (r18 >= 18) goto L621;
        namekiahEidName(2, r24);
    L621:
        if (r18 < 18) goto L626;
        if (r18 >= 25) goto L626;
        namekiahEidName(3, r24);
    L626:
        if (r18 < 25) goto L673;
        if (r18 >= r85) goto L673;
        namekiahEidName(4, r24);
    L630:
        if (dayOfWeek(r113.get(7)) != "Fri") goto L651;
        if (r19 != 3) goto L651;
        if (r18 < 1) goto L637;
        if (r18 >= 10) goto L637;
        namekiahEidName(1, r24);
    L637:
        if (r18 < 10) goto L642;
        if (r18 >= 17) goto L642;
        namekiahEidName(2, r24);
    L642:
        if (r18 < 17) goto L646;
        int r314 = 24;
        if (r18 >= 24) goto L647;
        namekiahEidName(3, r24);
    L647:
        if (r18 < r314) goto L673;
        if (r18 >= r85) goto L673;
        namekiahEidName(4, r24);
        goto L673
    L646:
        r314 = 24;
    L651:
        if (dayOfWeek(r113.get(7)) != "Sat") goto L673;
        if (r19 != 3) goto L673;
        if (r18 < 1) goto L660;
        if (r18 >= 9) goto L660;
        namekiahEidName(1, r24);
    L660:
        if (r18 < 9) goto L664;
        int r315 = 16;
        if (r18 >= 16) goto L665;
        namekiahEidName(2, r24);
    L665:
        if (r18 < r315) goto L670;
        if (r18 >= 23) goto L670;
        namekiahEidName(3, r24);
    L670:
        if (r18 < 23) goto L673;
        if (r18 >= r85) goto L673;
        namekiahEidName(4, r24);
        goto L673
    L664:
        r315 = 16;
        goto L665
    L481:
        if (r18 != 26) goto L486;
        if (r24.equals("Fri") == false) goto L486;
        loadingAyam(28, 3);
        this.eidName = "barmoon_melad";
        return "read barmon melad 28 kiahk";
    L474:
        if (r18 != 29) goto L479;
        if (r24.equals("Sun") == false) goto L479;
        loadingAyam(29, 3);
        this.eidName = "eid_melad";
        this.splashEid = "melad";
        return "read melad 29 kiahk";
    L394:
        if (r19 != 3) goto L443;
        r85 = 27;
        if (r18 != 27) goto L400;
        loadingAyam(28, 3);
        this.eidName = "barmoon_melad_lastday";
        this.splashEid = "melad";
        return "read barmoon 28 kiahk instead of 27kiahk";
    L400:
        if (r18 != 28) goto L402;
        int r114 = 3;
        int r1 = 29;
    L441:
        loadingAyam(r1, r114);
        this.eidName = "eid_melad";
        this.splashEid = "melad";
        return "read 29 kiahk";
    L402:
        if (r18 != 29) goto L405;
        r1 = 29;
        r114 = 3;
        goto L441
    L405:
        if (r18 != 27) goto L411;
        if (r24.equals("Fri") == false) goto L411;
        loadingAyam(28, 3);
        this.eidName = "barmoon_melad_lastday";
        this.splashEid = "melad";
        return "read barmon melad 28 kiahk";
    L411:
        if (r18 != 26) goto L417;
        if (r24.equals("Fri") == false) goto L417;
        loadingAyam(28, 3);
        this.eidName = "barmoon_melad";
        return "read barmon melad 28 kiahk";
    L417:
        if (r18 != 27) goto L423;
        if (r24.equals("Sat") == false) goto L423;
        loadingAyam(28, 3);
        this.eidName = "barmoon_melad_lastday";
        this.splashEid = "melad";
        return "read barmon melad 28 kiahk";
    L423:
        if (r18 == 25) goto L425;
    L428:
        r112 = 3;
        if (r18 != 26) goto L435;
        if (r24.equals("Sat") == false) goto L435;
        loadingAyam(28, 3);
        this.eidName = "barmoon_melad";
        return "read barmon melad 28 kiahk";
    L435:
        if (r18 != 27) goto L444;
        if (r24.equals("Sun") == false) goto L444;
        loadingAyam(28, 3);
        this.splashEid = "melad";
        this.eidName = "barmoon_melad_lastday_sun";
        return "read barmon melad 28 kiahk";
    L425:
        if (r24.equals("Fri") == false) goto L428;
        loadingAyam(28, 3);
        this.eidName = "barmoon_melad";
        return "read barmon melad 28 kiahk";
    L369:
        if (r11.compareTo(r4) == (-1)) goto L373;
    L354:
        if (r19 != 8) goto L364;
        if (this.eidName.equals("soud") == true) goto L364;
        if (this.eidName.equals("helol") == true) goto L364;
        loadingAyam(r18, r19);
        if (r24.equals("Sun") == false) goto L362;
        this.eidName = "jesus_inside_egypt_sun";
    L363:
        return "";
    L362:
        this.eidName = "jesus_inside_egypt";
    L347:
        if (r11.compareTo(r84) == (-1)) goto L1177;
    L109:
        if (r11.compareTo(getOrthodoxEaster(r23)) != (-1)) goto L342;
        int r54 = (r11.get(6) - r32.get(6)) + r132;
        if (r54 >= 49) goto L120;
        if (r18 != 29) goto L120;
        if (r19 != 6) goto L120;
        if (r11.compareTo(r83) != (-1)) goto L120;
        loadingAyam(29, r19);
        this.eidName = "eid_beshara";
        return "read eid el beshara from katamars el ayam";
    L120:
        if (r54 >= 49) goto L128;
        if (r18 != 10) goto L128;
        if (r19 != 6) goto L128;
        loadingAyam(10, 6);
        this.eidName = "elSaleb";
        return "read eid el saleb";
    L128:
        if (r54 != 1) goto L131;
        loadingSoom(r54, 0);
    L341:
        return "read soom kyama we are in day=" + r54;
    L131:
        if (r54 != 2) goto L140;
        if (r19 == 5) goto L135;
    L138:
        loadingSoom(r54, 0);
        this.eidName = "Kabir_Sun_refaa";
        goto L341
    L135:
        if (r18 != 8) goto L138;
        loadingAyam(8, 5);
        this.eidName = "jesus_hakal";
        return "read d7'ol jesus el hykal from ayam katamars";
    L140:
        if (r54 != 3) goto L143;
        loadingSoom(r54, 0);
        this.eidName = "gomaa_ketam";
        goto L341
    L143:
        if (r54 != 9) goto L146;
        loadingSoom(r54, 0);
        this.eidName = "soom_kber_week1_sat_sun";
        goto L341
    L146:
        if (r54 != 16) goto L149;
        loadingSoom(r54, 0);
        this.eidName = "soom_kber_week2_sat_sun";
        goto L341
    L149:
        if (r54 != 23) goto L152;
        loadingSoom(r54, 0);
        this.eidName = "soom_kber_week3_sat_sun";
        goto L341
    L152:
        if (r54 != 30) goto L155;
        loadingSoom(r54, 0);
        this.eidName = "soom_kber_week4_sat_sun";
        goto L341
    L155:
        if (r54 != 37) goto L158;
        loadingSoom(r54, 0);
        this.eidName = "soom_kber_week5_sat_sun";
        goto L341
    L158:
        if (r54 != 44) goto L161;
        loadingSoom(r54, 0);
        this.eidName = "soom_kber_week6_sat_sun";
        goto L341
    L161:
        if (r54 != 49) goto L164;
        loadingSoom(r54, 0);
        this.eidName = "gomaa_ketam";
        goto L341
    L164:
        if (r54 != 50) goto L167;
        this.eidName = "Sabt_azzr";
        loadingSoom(r54, 0);
        goto L341
    L167:
        if (r54 != 51) goto L170;
        this.splashEid = "a7d_shaaneen";
        loadingSoom(r54, 0);
        this.eidName = "a7d_shaaneen";
        goto L341
    L170:
        if (r54 != 52) goto L173;
        this.splashEid = "alam";
        loadingSoom(r54, 0);
        goto L341
    L173:
        if (r54 != 53) goto L176;
        this.splashEid = "alam";
        loadingSoom(r54, 0);
        goto L341
    L176:
        if (r54 != 54) goto L179;
        this.splashEid = "alam";
        loadingSoom(r54, 0);
        goto L341
    L179:
        if (r54 != 55) goto L182;
        this.splashEid = "alam";
        loadingSoom(r54, 0);
        this.eidName = "7ames_el_ahd";
        goto L341
    L182:
        if (r54 != 56) goto L185;
        this.splashEid = "alam";
        loadingSoom(r54, 0);
        this.eidName = "gomaa_azema";
        goto L341
    L185:
        if (r54 != 57) goto L187;
        this.splashEid = "kyama";
        loadingSoom(r54, 0);
        String r15 = "sabt_farah_" + this.realDayName;
        this.eidName = r15;
        Log.d(Util.TAG, r15);
        goto L341
    L187:
        loadingSoom(r54, 0);
        if (r54 == 3) goto L272;
        if (r54 == 10) goto L272;
        if (r54 == 17) goto L272;
        if (r54 == 24) goto L272;
        if (r54 == 31) goto L272;
        if (r54 == 38) goto L272;
        if (r54 == 45) goto L272;
        if (r54 == 4) goto L272;
        if (r54 == 11) goto L272;
        if (r54 == 18) goto L272;
        if (r54 == 25) goto L272;
        if (r54 == 32) goto L272;
        if (r54 == 39) goto L272;
        if (r54 == 46) goto L272;
        if (r54 == 5) goto L272;
        if (r54 == 12) goto L272;
        if (r54 == 19) goto L272;
        if (r54 == 26) goto L272;
        if (r54 == 33) goto L272;
        if (r54 == 40) goto L272;
        if (r54 == 47) goto L272;
        if (r54 == 6) goto L272;
        if (r54 == 13) goto L272;
        if (r54 == 20) goto L272;
        if (r54 == 27) goto L272;
        if (r54 == 34) goto L272;
        if (r54 == 41) goto L272;
        if (r54 == 48) goto L272;
        if (r54 == 7) goto L272;
        if (r54 == 14) goto L272;
        if (r54 == 21) goto L272;
        if (r54 == 28) goto L272;
        if (r54 == 35) goto L272;
        if (r54 == 42) goto L272;
        if (r54 == 8) goto L272;
        if (r54 == 15) goto L272;
        if (r54 == 22) goto L272;
        if (r54 == 29) goto L272;
        if (r54 != 36) goto L272;
    L272:
        if (r54 < 3) goto L283;
        if (r54 > 8) goto L283;
        if (r24.equals("Sat") == false) goto L278;
    L281:
        this.eidName = "soom_kber_week1_sat_sun";
        goto L341
    L278:
        if (r24.equals("Sun") == true) goto L281;
        this.eidName = "soom_kber_week1";
    L283:
        if (r54 < 10) goto L294;
        if (r54 > 15) goto L294;
        if (r24.equals("Sat") == false) goto L289;
    L292:
        this.eidName = "soom_kber_week2_sat_sun";
        goto L341
    L289:
        if (r24.equals("Sun") == true) goto L292;
        this.eidName = "soom_kber_week2";
    L294:
        if (r54 < 17) goto L305;
        if (r54 > 22) goto L305;
        if (r24.equals("Sat") == false) goto L300;
    L303:
        this.eidName = "soom_kber_week3_sat_sun";
        goto L341
    L300:
        if (r24.equals("Sun") == true) goto L303;
        this.eidName = "soom_kber_week3";
    L305:
        if (r54 < 24) goto L316;
        if (r54 > 29) goto L316;
        if (r24.equals("Sat") == false) goto L311;
    L314:
        this.eidName = "soom_kber_week4_sat_sun";
        goto L341
    L311:
        if (r24.equals("Sun") == true) goto L314;
        this.eidName = "soom_kber_week4";
    L316:
        if (r54 < 31) goto L327;
        if (r54 > 36) goto L327;
        if (r24.equals("Sat") == false) goto L322;
    L325:
        this.eidName = "soom_kber_week5_sat_sun";
        goto L341
    L322:
        if (r24.equals("Sun") == true) goto L325;
        this.eidName = "soom_kber_week5";
    L327:
        if (r54 < 38) goto L338;
        if (r54 > 43) goto L338;
        if (r24.equals("Sat") == false) goto L333;
    L336:
        this.eidName = "soom_kber_week6_sat_sun";
        goto L341
    L333:
        if (r24.equals("Sun") == true) goto L336;
        this.eidName = "soom_kber_week6";
    L338:
        if (r54 < 45) goto L341;
        this.eidName = "soom_kber_week6";
        goto L341
    L107:
        r132 = 1;
        goto L109
    L72:
        if (r11.compareTo(getOrthodoxEaster(r23)) != (-1)) goto L74;
        r12 = true;
    L61:
        if (r19 > 3) goto L67;
    L68:
        r102 = false;
        goto L70
    L64:
        if (r19 != 3) goto L66;
        if (r18 > 29) goto L67;
    L66:
        if (r19 <= 3) goto L68;
    L1201:
        if (r19 == 5) goto L1203;
    L1206:
        int r2 = (r11.get(6) - r3.get(6)) + 1;
        loadingyunan(r2, 2);
        if (r24.equals("Mon") == false) goto L1210;
        this.eidName = "soom_Younah_Mon";
    L1219:
        return "read yunan we are in day=" + r2;
    L1210:
        if (r24.equals("Tue") == false) goto L1213;
        this.eidName = "soom_Younah_Tue";
        goto L1219
    L1213:
        if (r24.equals("Wed") == false) goto L1216;
        this.eidName = "soom_Younah_Wed";
        goto L1219
    L1216:
        if (r24.equals("Thu") == false) goto L1219;
        this.eidName = "soom_Younah_Thu";
        goto L1219
    L1203:
        if (r18 != 8) goto L1206;
        loadingAyam(8, 5);
        this.eidName = "jesus_hakal";
        return "read d7'ol jesus el hykal from ayam katamars";
    L52:
        if (r11.compareTo(r82) == (-1)) goto L1201;
    L6:
        if (r24.equals("Sun") == false) goto L9;
        this.eidName = "sanwy_sun";
        goto L17
    L9:
        if (r24.equals("Mon") == false) goto L11;
    L16:
        this.eidName = "sanwy_days2_fetar";
        goto L17
    L11:
        if (r24.equals("Tue") == true) goto L16;
        if (r24.equals("Thu") == true) goto L16;
        this.eidName = "sanwy_others";
        goto L17
    }

    private void readKatA7adOthers(int r3, int r4, int r5) {
        if (r3 != 13) goto L8;
        if (r4 != 11) goto L8;
        loadingAyam(13, 11);
        return;
    L8:
        if (r3 != 1) goto L12;
        if (r4 != 0) goto L12;
        loadingAyam(1, 0);
        return;
    L12:
        if (r5 != 5) goto L14;
        loadingA7ad(5, r4);
        return;
    L14:
        loadingA7ad(r5, r4);
    }

    private int isCalendarsEqual(Calendar r3, Calendar r4) {
        int r32 = Integer.valueOf(((r3.getTimeInMillis() - r4.getTimeInMillis()) / 86400000) + "").intValue();
        if (r32 != 0) goto L6;
        return 0;
    L6:
        if (r32 <= 0) goto L9;
        return 1;
    L9:
        return -1;
    }

    private void loadingAyam(int r7, int r8) {
        int r82 = r8 + 1;
        String r1 = new String(app.decodeFile2(this.activity, "orsozoxi", "katamars/KatamarsAyam/" + r82 + "/" + r7 + ".orso"));     // Catch: Exception -> L8 IOException -> L10
        r1.replaceAll("\\+", "");     // Catch: Exception -> L8 IOException -> L10
        String[] r72 = r1.split("\n");     // Catch: Exception -> L8 IOException -> L10
        int r83 = 0;
    L4:
        if (r83 >= r72.length) goto L12;
        this.legendList.add(new Bible("", r72[r83].replace("+", "")));     // Catch: Exception -> L8 IOException -> L10
        r83 = r83 + 1;
    L12:
        getDataStatic();
        return;
    L10:
        e = move-exception;
        e.printStackTrace();
    L8:
        e = move-exception;
        e.printStackTrace();
        goto L12
    }

    private void loadingA7ad(int r7, int r8) {
        int r82 = r8 + 1;
        String r1 = new String(app.decodeFile2(this.activity, "orsozoxi", "katamars/KatamarsA7ad/" + r82 + "/" + r7 + ".orso"));     // Catch: Exception -> L8 IOException -> L10
        r1.replaceAll("\\+", "");     // Catch: Exception -> L8 IOException -> L10
        String[] r72 = r1.split("\n");     // Catch: Exception -> L8 IOException -> L10
        int r83 = 0;
    L4:
        if (r83 >= r72.length) goto L12;
        this.legendList.add(new Bible("", r72[r83].replace("+", "")));     // Catch: Exception -> L8 IOException -> L10
        r83 = r83 + 1;
    L12:
        getDataStatic();
        return;
    L10:
        e = move-exception;
        e.printStackTrace();
    L8:
        e = move-exception;
        e.printStackTrace();
        goto L12
    }

    public void loadingyunan(int r43, int r44) {
        String r2 = "frommmm:-";
        String r3 = "dis:";
        String[] r4 = new String(app.decodeFile2(this.activity, "orsozoxi", "katamars/yunan.orso")).split("\n");     // Catch: Exception -> L266 IOException -> L268
        int r5 = 0;
        String r9 = "";
        String r10 = r9;
        String r11 = r10;
        String r12 = r11;
        String r13 = r12;
        int r7 = 0;
        int r8 = 0;
    L5:
        if (r7 >= r4.length) goto L270;
        if (r4[r7].equals("") == false) goto L11;
        String r16 = r2;
        String r21 = r3;
        String r22 = r12;
        String r19 = r13;
    L33:
        int r37 = r7;
        String r38 = r9;
        String r92 = r16;
        r13 = r19;
        String r33 = r21;
        r12 = r22;
    L265:
        r7 = r37 + 1;
        r2 = r92;
        r3 = r33;
        r9 = r38;
        r5 = 0;
        goto L5
    L11:
        r16 = r2;
        if (r4[r7].charAt(r5) == '+') goto L20;
        if (r4[r7].contains("+") == true) goto L20;
        if (r4[r7].contains(" ") == false) goto L18;
        r4[r7].replace(" ", "");     // Catch: Exception -> L266 IOException -> L268
    L18:
        r8 = Integer.valueOf(r4[r7]).intValue();     // Catch: Exception -> L266 IOException -> L268
        r33 = r3;
        r37 = r7;
        r38 = r9;
        r92 = r16;
    L20:
        String r18 = " ";
        r19 = r13;
        String r132 = "+++";
        r21 = r3;
        String r32 = "\\+";
        r22 = r12;
        if (r4[r7].charAt(0) != '+') goto L23;
    L43:
        if (r7 <= 0) goto L49;
        int r122 = r7 - 1;
        boolean r14 = r4[r122].contains("==");     // Catch: Exception -> L266 IOException -> L268
        String r17 = "type:";
        if (r14 != true) goto L47;
        boolean r24 = r14;
        this.legendList.add(new Bible("", r4[r122].replace("==", "")));     // Catch: Exception -> L266 IOException -> L268
    L48:
        int r23 = r43;
    L50:
        if (r23 != r8) goto L256;
        if (r24 == true) goto L256;
        int r123 = Integer.valueOf(r9).intValue() - 1;     // Catch: Exception -> L266 IOException -> L268
        int r15 = Integer.valueOf(r10).intValue() - 1;     // Catch: Exception -> L266 IOException -> L268
        String r52 = r123 + "+" + r15;     // Catch: Exception -> L266 IOException -> L268
        if (r11.equals("") == true) goto L57;
        String r142 = r22;
        if (r142.equals("") == true) goto L59;
        int r222 = Integer.valueOf(r11).intValue() - 1;
        int r25 = Integer.valueOf(r142).intValue() - 1;
    L64:
        new ArrayList();     // Catch: Exception -> L266 IOException -> L268
        int r242 = r8;
        loadinmapp(choosebook(r123), r123, r15);     // Catch: Exception -> L266 IOException -> L268
        Iterator<Map.Entry<String, ArrayList<String>>> r82 = this.levelsMap.entrySet().iterator();     // Catch: Exception -> L266 IOException -> L268
        String r26 = "";
        int r252 = r222;
        int r152 = 1;
        int r223 = 0;
    L66:
        if (r82.hasNext() == false) goto L255;
        Map.Entry<String, ArrayList<String>> r27 = r82.next();     // Catch: Exception -> L266 IOException -> L268
        Iterator<Map.Entry<String, ArrayList<String>>> r29 = r82;
        String r83 = r27.getKey();     // Catch: Exception -> L266 IOException -> L268
        int r28 = r152;
        PrintStream r153 = System.out;     // Catch: Exception -> L266 IOException -> L268
        int r30 = r25;
        StringBuilder r210 = new StringBuilder();     // Catch: Exception -> L266 IOException -> L268
        int r31 = r123;
        String r124 = r21;
        r210.append(r124);     // Catch: Exception -> L266 IOException -> L268
        r210.append(r52);     // Catch: Exception -> L266 IOException -> L268
        r210.append(r124);     // Catch: Exception -> L266 IOException -> L268
        r210.append(r83);     // Catch: Exception -> L266 IOException -> L268
        r153.println(r210.toString());     // Catch: Exception -> L266 IOException -> L268
        if (r83.equals(AppEventsConstants.EVENT_PARAM_VALUE_NO) == false) goto L253;
        ArrayList<String> r211 = r27.getValue();     // Catch: Exception -> L266 IOException -> L268
        int r84 = Integer.valueOf(r10).intValue();     // Catch: Exception -> L266 IOException -> L268
        int r154 = r7 + 1;     // Catch: Exception -> L266 IOException -> L268
        String r272 = r52;
        String r322 = r10;
        String r332 = r124;
        if (r11.equals("") == false) goto L73;
    L126:
        String[] r212 = r4;
        int r372 = r7;
        String r382 = r9;
        String r42 = r132;
        String r93 = r18;
        String r125 = r11;
        String r112 = r17;
        String r102 = r32;
        String r34 = r19;
        if (r125.equals("") == false) goto L129;
    L175:
        String r192 = r112;
        String r172 = r142;
        String r143 = r34;
        r4 = r212;
        r4[r154].split(r102);     // Catch: Exception -> L266 IOException -> L268
        int r35 = r372 - 3;
        if (r35 < 0) goto L184;
        String r352 = r125;
        if (r4[r35].contains(r42) == false) goto L180;
        int r213 = r35;
        String r182 = ShareConstants.WEB_DIALOG_PARAM_DATA;
        this.legendList.add(new Bible("", typeofslah(r143)));     // Catch: Exception -> L266 IOException -> L268
    L186:
        if (r372 < (r4.length - 2)) goto L188;
    L220:
        String r36 = r42;
        String r53 = r102;
        int r373 = r372;
    L221:
        String r72 = r143;
    L223:
        Iterator<String> r214 = r211.iterator();     // Catch: Exception -> L266 IOException -> L268
        int r103 = r223;
        int r113 = r252;
        String r126 = r26;
        int r85 = 0;
    L225:
        if (r214.hasNext() == false) goto L252;
        String r133 = r214.next();     // Catch: Exception -> L266 IOException -> L268
        String r144 = r352;
        if (r144.equals("") == true) goto L241;
        Iterator<String> r183 = r214;
        String r215 = r172;
        if (r215.equals("") == true) goto L240;
        if (r85 < r113) goto L238;
        String r362 = r53;
        PrintStream r54 = System.out;     // Catch: Exception -> L266 IOException -> L268
        String r216 = r72;
        StringBuilder r73 = new StringBuilder();     // Catch: Exception -> L266 IOException -> L268
        String r173 = r93;
        String r94 = r16;
        r73.append(r94);     // Catch: Exception -> L266 IOException -> L268
        r73.append(r113);     // Catch: Exception -> L266 IOException -> L268
        r54.println(r73.toString());     // Catch: Exception -> L266 IOException -> L268
        System.out.println(r94 + r113);     // Catch: Exception -> L266 IOException -> L268
        String r55 = r126 + r133 + "<br>";     // Catch: Exception -> L266 IOException -> L268
        int r74 = r30;
        if (r113 > r74) goto L236;
        r113 = r113 + 1;     // Catch: Exception -> L266 IOException -> L268
        String r162 = r55;
        this.legendList.add(new Bible(String.valueOf(r113), r133));     // Catch: Exception -> L266 IOException -> L268
    L237:
        r126 = r162;
    L239:
        r85 = r85 + 1;     // Catch: Exception -> L266 IOException -> L268
        String r163 = r215;
    L251:
        r30 = r74;
        r352 = r144;
        r214 = r183;
        r72 = r216;
        r53 = r362;
        String r41 = r163;
        r16 = r94;
        r93 = r173;
        r172 = r41;
        goto L225
    L236:
        r162 = r55;
        goto L237
    L238:
        r362 = r53;
        r216 = r72;
        r173 = r93;
        r94 = r16;
        r74 = r30;
        goto L239
    L240:
        r362 = r53;
        r216 = r72;
        r173 = r93;
        r94 = r16;
        r74 = r30;
    L243:
        if (r144.equals("") == false) goto L245;
    L250:
        r163 = r215;
        r126 = r126 + r133 + "<br>";     // Catch: Exception -> L266 IOException -> L268
        this.legendList.add(new Bible(String.valueOf(r28), r133));     // Catch: Exception -> L266 IOException -> L268
        r28 = r28 + 1;     // Catch: Exception -> L266 IOException -> L268
        goto L251
    L245:
        if (r215.equals("") == false) goto L250;
        if (r113 != r103) goto L248;
        r163 = r215;
        this.legendList.add(new Bible(String.valueOf(r113 + 1), r133));     // Catch: Exception -> L266 IOException -> L268
    L249:
        r103 = r103 + 1;     // Catch: Exception -> L266 IOException -> L268
        goto L251
    L248:
        r163 = r215;
        goto L249
    L241:
        r183 = r214;
        r362 = r53;
        r216 = r72;
        r215 = r172;
        r74 = r30;
        r173 = r93;
        r94 = r16;
        goto L243
    L252:
        String r363 = r53;
        String r217 = r72;
        int r75 = r30;
        String r145 = r352;
        String r412 = r172;
        String r174 = r93;
        String r95 = r16;
        String r164 = r412;
        r223 = r103;
        r252 = r113;
        r26 = r126;
    L254:
        r152 = r28;
        r132 = r36;
        r25 = r75;
        r11 = r145;
        r142 = r164;
        r18 = r174;
        r17 = r192;
        r19 = r217;
        r52 = r272;
        r82 = r29;
        r123 = r31;
        r10 = r322;
        r21 = r332;
        r32 = r363;
        r7 = r373;
        r16 = r95;
        r9 = r382;
        goto L66
    L188:
        if (r4[r372].contains(r42) == true) goto L220;
        String[] r39 = r4[r154].split(r102);     // Catch: Exception -> L266 IOException -> L268
        if (r39[0].equals(r143) == false) goto L211;
        String r155 = r39[1];     // Catch: Exception -> L266 IOException -> L268
        String r127 = r39[2];     // Catch: Exception -> L266 IOException -> L268
        r373 = r372;
        System.out.println(r192 + r143);     // Catch: Exception -> L266 IOException -> L268
        if (r39.length != 4) goto L194;
        String r114 = r39[3];     // Catch: Exception -> L266 IOException -> L268
    L195:
        r192 = r192;
        if (r39.length != 5) goto L198;
        r114 = r39[3];     // Catch: Exception -> L266 IOException -> L268
        String r310 = r39[4];     // Catch: Exception -> L266 IOException -> L268
    L200:
        if (r114.equals("") == false) goto L202;
    L204:
        String r40 = r42;
        String r392 = r143;
        if (r114.equals("") == false) goto L207;
    L209:
        this.legendList.add(new Bible("", this.books[Integer.valueOf(r31).intValue()] + r93 + r84 + " , " + r127));     // Catch: Exception -> L266 IOException -> L268
    L210:
        r53 = r102;
        r72 = r392;
        r36 = r40;
        goto L223
    L207:
        if (r310.equals("") == false) goto L209;
        this.legendList.add(new Bible("", this.books[Integer.valueOf(r31).intValue()] + r93 + r84 + " , " + r127 + " : " + r114));     // Catch: Exception -> L266 IOException -> L268
        goto L210
    L202:
        if (r310.equals("") == true) goto L204;
        List<Bible> r104 = this.legendList;     // Catch: Exception -> L266 IOException -> L268
        r392 = r143;
        StringBuilder r146 = new StringBuilder();     // Catch: Exception -> L266 IOException -> L268
        r40 = r42;
        r146.append(this.books[Integer.valueOf(r31).intValue()]);     // Catch: Exception -> L266 IOException -> L268
        r146.append(r93);     // Catch: Exception -> L266 IOException -> L268
        r146.append(r84);     // Catch: Exception -> L266 IOException -> L268
        r146.append(" , ");     // Catch: Exception -> L266 IOException -> L268
        r146.append(r127);     // Catch: Exception -> L266 IOException -> L268
        r146.append(" : ");     // Catch: Exception -> L266 IOException -> L268
        r146.append(r310);     // Catch: Exception -> L266 IOException -> L268
        r146.append(" - ");     // Catch: Exception -> L266 IOException -> L268
        r146.append(r114);     // Catch: Exception -> L266 IOException -> L268
        r104.add(new Bible("", r146.toString()));     // Catch: Exception -> L266 IOException -> L268
        goto L210
    L198:
        r310 = "";
        goto L200
    L194:
        r114 = "";
        goto L195
    L211:
        r373 = r372;
        if (r213 < 0) goto L219;
        r36 = r42;
        if (r4[r213].contains(r36) == true) goto L218;
        System.out.println(r182 + r4[r213]);     // Catch: Exception -> L266 IOException -> L268
        r53 = r102;
        r72 = r143;
        if (r4[r213].split(r53)[0].equals(r72) == true) goto L223;
        this.legendList.add(new Bible("", this.books[Integer.valueOf(r31).intValue()] + r93 + r84));     // Catch: Exception -> L266 IOException -> L268
        goto L223
    L218:
        r53 = r102;
        r72 = r143;
        this.legendList.add(new Bible("", this.books[Integer.valueOf(r31).intValue()] + r93 + r84));     // Catch: Exception -> L266 IOException -> L268
        goto L223
    L219:
        r53 = r102;
        r72 = r143;
        r36 = r42;
        this.legendList.add(new Bible("", typeofslah(r72)));     // Catch: Exception -> L266 IOException -> L268
        goto L223
    L180:
        if (r4[r35].split(r102)[0].equals(r143) == true) goto L182;
        List<Bible> r128 = this.legendList;     // Catch: Exception -> L266 IOException -> L268
        r182 = ShareConstants.WEB_DIALOG_PARAM_DATA;
        r213 = r35;
        r128.add(new Bible("", typeofslah(r143)));     // Catch: Exception -> L266 IOException -> L268
        goto L186
    L182:
        r213 = r35;
        r182 = ShareConstants.WEB_DIALOG_PARAM_DATA;
        goto L186
    L184:
        r213 = r35;
        r182 = ShareConstants.WEB_DIALOG_PARAM_DATA;
        r352 = r125;
        goto L186
    L129:
        if (r142.equals("") == false) goto L175;
        r212[r154].split(r102);     // Catch: Exception -> L266 IOException -> L268
        int r76 = r372 - 3;
        if (r76 < 0) goto L139;
        r172 = r142;
        if (r212[r76].contains(r42) == false) goto L135;
        String r353 = r42;
        String r184 = ShareConstants.WEB_DIALOG_PARAM_DATA;
        this.legendList.add(new Bible("", typeofslah(r34)));     // Catch: Exception -> L266 IOException -> L268
    L140:
        r4 = r212;
        if (r372 >= (r4.length - 2)) goto L173;
        String[] r56 = r4[r154].split(r102);     // Catch: Exception -> L266 IOException -> L268
        r373 = r372;
        if (r56[0].equals(r34) == false) goto L164;
        String r147 = r56[1];     // Catch: Exception -> L266 IOException -> L268
        String r148 = r56[2];     // Catch: Exception -> L266 IOException -> L268
        System.out.println(r112 + r34);     // Catch: Exception -> L266 IOException -> L268
        if (r56.length != 4) goto L147;
        String r77 = r56[3];     // Catch: Exception -> L266 IOException -> L268
    L148:
        String r185 = r77;
        if (r56.length != 5) goto L151;
        String r78 = r56[3];     // Catch: Exception -> L266 IOException -> L268
        String r57 = r56[4];     // Catch: Exception -> L266 IOException -> L268
    L153:
        if (r78.equals("") == false) goto L155;
    L157:
        String r218 = r34;
        String r364 = r102;
        r192 = r112;
        if (r78.equals("") == false) goto L160;
    L162:
        this.legendList.add(new Bible("", this.books[Integer.valueOf(r31).intValue()] + r93 + r84 + " : " + r125 + ", " + r148));     // Catch: Exception -> L266 IOException -> L268
    L163:
        r143 = r218;
        String r58 = r353;
        r102 = r364;
    L174:
        r36 = r58;
        r53 = r102;
        r352 = r125;
        goto L221
    L160:
        if (r57.equals("") == false) goto L162;
        this.legendList.add(new Bible("", this.books[Integer.valueOf(r31).intValue()] + r93 + r84 + " : " + r125 + " , " + r148 + " : " + r78));     // Catch: Exception -> L266 IOException -> L268
        goto L163
    L155:
        if (r57.equals("") == true) goto L157;
        List<Bible> r156 = this.legendList;     // Catch: Exception -> L266 IOException -> L268
        r192 = r112;
        r218 = r34;
        StringBuilder r311 = new StringBuilder();     // Catch: Exception -> L266 IOException -> L268
        r364 = r102;
        r311.append(this.books[Integer.valueOf(r31).intValue()]);     // Catch: Exception -> L266 IOException -> L268
        r311.append(r93);     // Catch: Exception -> L266 IOException -> L268
        r311.append(r84);     // Catch: Exception -> L266 IOException -> L268
        r311.append(" : ");     // Catch: Exception -> L266 IOException -> L268
        r311.append(r125);     // Catch: Exception -> L266 IOException -> L268
        r311.append(" , ");     // Catch: Exception -> L266 IOException -> L268
        r311.append(r148);     // Catch: Exception -> L266 IOException -> L268
        r311.append(" : ");     // Catch: Exception -> L266 IOException -> L268
        r311.append(r57);     // Catch: Exception -> L266 IOException -> L268
        r311.append(" -");     // Catch: Exception -> L266 IOException -> L268
        r311.append(r78);     // Catch: Exception -> L266 IOException -> L268
        r156.add(new Bible("", r311.toString()));     // Catch: Exception -> L266 IOException -> L268
        goto L163
    L151:
        r57 = "";
        r78 = r185;
        goto L153
    L147:
        r77 = "";
        goto L148
    L164:
        r192 = r112;
        if (r76 < 0) goto L172;
        r58 = r353;
        if (r4[r76].contains(r58) == true) goto L171;
        System.out.println(r184 + r4[r76]);     // Catch: Exception -> L266 IOException -> L268
        r102 = r102;
        r143 = r34;
        if (r4[r76].split(r102)[0].equals(r143) == true) goto L174;
        this.legendList.add(new Bible("", this.books[Integer.valueOf(r31).intValue()] + r93 + r84 + " : " + r125));     // Catch: Exception -> L266 IOException -> L268
        goto L174
    L171:
        r143 = r34;
        r102 = r102;
        this.legendList.add(new Bible("", this.books[Integer.valueOf(r31).intValue()] + r93 + r84 + " : " + r125));     // Catch: Exception -> L266 IOException -> L268
        goto L174
    L172:
        r143 = r34;
        r58 = r353;
        r102 = r102;
        this.legendList.add(new Bible("", typeofslah(r143)));     // Catch: Exception -> L266 IOException -> L268
        goto L174
    L173:
        r192 = r112;
        r373 = r372;
        r58 = r353;
        r143 = r34;
        goto L174
    L135:
        if (r212[r76].split(r102)[0].equals(r34) == true) goto L137;
        List<Bible> r149 = this.legendList;     // Catch: Exception -> L266 IOException -> L268
        r184 = ShareConstants.WEB_DIALOG_PARAM_DATA;
        r353 = r42;
        r149.add(new Bible("", typeofslah(r34)));     // Catch: Exception -> L266 IOException -> L268
        goto L140
    L137:
        r353 = r42;
        r184 = ShareConstants.WEB_DIALOG_PARAM_DATA;
        goto L140
    L139:
        r353 = r42;
        r184 = ShareConstants.WEB_DIALOG_PARAM_DATA;
        r172 = r142;
        this.legendList.add(new Bible("", typeofslah(r34)));     // Catch: Exception -> L266 IOException -> L268
        goto L140
    L73:
        if (r142.equals("") == true) goto L126;
        if (r7 >= (r4.length - 1)) goto L124;
        String r354 = r11;
        System.out.println(ShareConstants.WEB_DIALOG_PARAM_DATA + r4.length + "data2:" + r154 + "xml:" + r4[r154]);     // Catch: Exception -> L266 IOException -> L268
        String[] r105 = r4[r154].split(r32);     // Catch: Exception -> L266 IOException -> L268
        int r115 = r7 + (-3);     // Catch: Exception -> L266 IOException -> L268
        if (r115 >= 0) goto L79;
        r373 = r7;
        String r129 = r19;
        String r193 = r32;
    L87:
        if (r105[0].equals(r129) == true) goto L89;
    L110:
        String[] r219 = r4;
        r382 = r9;
        r93 = r18;
        String r186 = r129;
        String r1210 = r354;
        String r355 = r132;
        if (r115 < 0) goto L122;
        String r45 = r355;
        if (r219[r115].contains(r45) == true) goto L121;
        System.out.println(ShareConstants.WEB_DIALOG_PARAM_DATA + r219[r115]);     // Catch: Exception -> L266 IOException -> L268
        String r106 = r193;
        String[] r312 = r219[r115].split(r106);     // Catch: Exception -> L266 IOException -> L268
        String r59 = r186;
        if (r312[0].equals(r59) == true) goto L118;
        this.legendList.add(new Bible("", this.books[Integer.valueOf(r31).intValue()] + r93 + r84 + " : " + r142 + " - " + r1210));     // Catch: Exception -> L266 IOException -> L268
    L123:
        r36 = r45;
        r72 = r59;
        r53 = r106;
        r352 = r1210;
        r192 = r17;
        r4 = r219;
    L125:
        r172 = r142;
        goto L223
    L118:
        if (r312[1].equals(r382) == true) goto L120;
        List<Bible> r313 = this.legendList;     // Catch: Exception -> L266 IOException -> L268
        StringBuilder r157 = new StringBuilder();     // Catch: Exception -> L266 IOException -> L268
        r382 = r382;
        r157.append(this.books[Integer.valueOf(r31).intValue()]);     // Catch: Exception -> L266 IOException -> L268
        r157.append(r93);     // Catch: Exception -> L266 IOException -> L268
        r157.append(r84);     // Catch: Exception -> L266 IOException -> L268
        r157.append(" : ");     // Catch: Exception -> L266 IOException -> L268
        r157.append(r142);     // Catch: Exception -> L266 IOException -> L268
        r157.append(" - ");     // Catch: Exception -> L266 IOException -> L268
        r157.append(r1210);     // Catch: Exception -> L266 IOException -> L268
        r313.add(new Bible("", r157.toString()));     // Catch: Exception -> L266 IOException -> L268
        goto L123
    L120:
        r382 = r382;
        goto L123
    L121:
        r59 = r186;
        r106 = r193;
        this.legendList.add(new Bible("", this.books[Integer.valueOf(r31).intValue()] + r93 + r84 + " : " + r142 + " - " + r1210));     // Catch: Exception -> L266 IOException -> L268
        goto L123
    L122:
        r59 = r186;
        r106 = r193;
        r45 = r355;
        this.legendList.add(new Bible("", typeofslah(r59)));     // Catch: Exception -> L266 IOException -> L268
        this.legendList.add(new Bible("", this.books[Integer.valueOf(r31).intValue()] + r93 + r84 + " : " + r142 + " - " + r1210));     // Catch: Exception -> L266 IOException -> L268
        goto L123
    L89:
        if (r105[1].equals(r9) == false) goto L110;
        String r510 = r105[1];     // Catch: Exception -> L266 IOException -> L268
        String r511 = r105[2];     // Catch: Exception -> L266 IOException -> L268
        PrintStream r314 = System.out;     // Catch: Exception -> L266 IOException -> L268
        StringBuilder r79 = new StringBuilder();     // Catch: Exception -> L266 IOException -> L268
        String r158 = r17;
        r79.append(r158);     // Catch: Exception -> L266 IOException -> L268
        r79.append(r129);     // Catch: Exception -> L266 IOException -> L268
        r314.println(r79.toString());     // Catch: Exception -> L266 IOException -> L268
        if (r105.length != 4) goto L93;
        String r315 = r105[3];     // Catch: Exception -> L266 IOException -> L268
    L95:
        if (r105.length != 5) goto L97;
        r315 = r105[3];     // Catch: Exception -> L266 IOException -> L268
        String r107 = r105[4];     // Catch: Exception -> L266 IOException -> L268
    L99:
        if (r315.equals("") == false) goto L101;
    L103:
        r382 = r9;
        r17 = r158;
        r93 = r18;
        String r187 = r129;
        r1210 = r354;
        String r356 = r132;
        if (r315.equals("") == false) goto L106;
    L108:
        r219 = r4;
        this.legendList.add(new Bible("", this.books[Integer.valueOf(r31).intValue()] + r93 + r84 + " : " + r142 + " - " + r1210 + " , " + r511));     // Catch: Exception -> L266 IOException -> L268
    L109:
        r59 = r187;
        r106 = r193;
        r45 = r356;
        goto L123
    L106:
        if (r107.equals("") == false) goto L108;
        List<Bible> r108 = this.legendList;     // Catch: Exception -> L266 IOException -> L268
        StringBuilder r159 = new StringBuilder();     // Catch: Exception -> L266 IOException -> L268
        r219 = r4;
        r159.append(this.books[Integer.valueOf(r31).intValue()]);     // Catch: Exception -> L266 IOException -> L268
        r159.append(r93);     // Catch: Exception -> L266 IOException -> L268
        r159.append(r84);     // Catch: Exception -> L266 IOException -> L268
        r159.append(" : ");     // Catch: Exception -> L266 IOException -> L268
        r159.append(r142);     // Catch: Exception -> L266 IOException -> L268
        r159.append(" - ");     // Catch: Exception -> L266 IOException -> L268
        r159.append(r1210);     // Catch: Exception -> L266 IOException -> L268
        r159.append(" , ");     // Catch: Exception -> L266 IOException -> L268
        r159.append(r511);     // Catch: Exception -> L266 IOException -> L268
        r159.append(" : ");     // Catch: Exception -> L266 IOException -> L268
        r159.append(r315);     // Catch: Exception -> L266 IOException -> L268
        r108.add(new Bible("", r159.toString()));     // Catch: Exception -> L266 IOException -> L268
        goto L109
    L101:
        if (r107.equals("") == true) goto L103;
        List<Bible> r710 = this.legendList;     // Catch: Exception -> L266 IOException -> L268
        r17 = r158;
        StringBuilder r1510 = new StringBuilder();     // Catch: Exception -> L266 IOException -> L268
        r382 = r9;
        r1510.append(this.books[Integer.valueOf(r31).intValue()]);     // Catch: Exception -> L266 IOException -> L268
        r93 = r18;
        r1510.append(r93);     // Catch: Exception -> L266 IOException -> L268
        r1510.append(r84);     // Catch: Exception -> L266 IOException -> L268
        r1510.append(" : ");     // Catch: Exception -> L266 IOException -> L268
        r1510.append(r142);     // Catch: Exception -> L266 IOException -> L268
        r1510.append(" - ");     // Catch: Exception -> L266 IOException -> L268
        r187 = r129;
        r1210 = r354;
        r1510.append(r1210);     // Catch: Exception -> L266 IOException -> L268
        r356 = r132;
        r1510.append(" , ");     // Catch: Exception -> L266 IOException -> L268
        r1510.append(r511);     // Catch: Exception -> L266 IOException -> L268
        r1510.append(" : ");     // Catch: Exception -> L266 IOException -> L268
        r1510.append(r107);     // Catch: Exception -> L266 IOException -> L268
        r1510.append(" - ");     // Catch: Exception -> L266 IOException -> L268
        r1510.append(r315);     // Catch: Exception -> L266 IOException -> L268
        r710.add(new Bible("", r1510.toString()));     // Catch: Exception -> L266 IOException -> L268
        r219 = r4;
        goto L109
    L97:
        r107 = "";
        goto L99
    L93:
        r315 = "";
        goto L95
    L79:
        if (r4[r115].contains(r132) == true) goto L84;
        PrintStream r1211 = System.out;     // Catch: Exception -> L266 IOException -> L268
        StringBuilder r1511 = new StringBuilder();     // Catch: Exception -> L266 IOException -> L268
        r1511.append(ShareConstants.WEB_DIALOG_PARAM_DATA);     // Catch: Exception -> L266 IOException -> L268
        r373 = r7;
        r1511.append(r4[r115]);     // Catch: Exception -> L266 IOException -> L268
        r1211.println(r1511.toString());     // Catch: Exception -> L266 IOException -> L268
        String[] r711 = r4[r115].split(r32);     // Catch: Exception -> L266 IOException -> L268
        r129 = r19;
        if (r711[0].equals(r129) == true) goto L83;
        r193 = r32;
        this.legendList.add(new Bible("", typeofslah(r129)));     // Catch: Exception -> L266 IOException -> L268
        goto L87
    L83:
        r193 = r32;
        r711[1].equals(r9);     // Catch: Exception -> L266 IOException -> L268
        goto L87
    L84:
        r373 = r7;
        r129 = r19;
        r193 = r32;
        this.legendList.add(new Bible("", typeofslah(r129)));     // Catch: Exception -> L266 IOException -> L268
        goto L87
    L124:
        r382 = r9;
        r93 = r18;
        r53 = r32;
        r373 = r7;
        r352 = r11;
        r36 = r132;
        r72 = r19;
        r192 = r17;
        goto L125
    L253:
        r363 = r32;
        r272 = r52;
        r373 = r7;
        r382 = r9;
        r322 = r10;
        r332 = r124;
        r36 = r132;
        r95 = r16;
        r217 = r19;
        r75 = r30;
        r164 = r142;
        r192 = r17;
        r174 = r18;
        r145 = r11;
        goto L254
    L255:
        r37 = r7;
        r38 = r9;
        String r323 = r10;
        String r316 = r132;
        r92 = r16;
        r33 = r21;
        String r165 = r142;
        String r2110 = r19;
        String r1410 = r11;
    L258:
        if (r4[r37].equals(r316) == false) goto L260;
    L263:
        r10 = "";
        r11 = r10;
        r12 = r11;
        r38 = r12;
        r8 = 0;
    L264:
        r13 = r2110;
        goto L265
    L260:
        if (r4[r37].contains(r316) == true) goto L263;
        r11 = r1410;
        r12 = r165;
        r8 = r242;
        r10 = r323;
    L59:
        if (r11.equals("") == false) goto L61;
    L63:
        r25 = 0;
        r222 = 0;
        goto L64
    L61:
        if (r142.equals("") == false) goto L63;
        r222 = Integer.valueOf(r11).intValue() - 1;
        r25 = 0;
        goto L64
    L57:
        r142 = r22;
    L256:
        r37 = r7;
        r242 = r8;
        r38 = r9;
        r323 = r10;
        r1410 = r11;
        r316 = "+++";
        r92 = r16;
        r33 = r21;
        r165 = r22;
        r2110 = r19;
        goto L258
    L47:
        r24 = r14;
        goto L48
    L49:
        r17 = "type:";
        r23 = r43;
        r24 = false;
        goto L50
    L23:
        if (r4[r7].equals("+") == true) goto L43;
        if (r4[r7].equals("+++") == true) goto L43;
        if (r4[r7].contains("+++") == true) goto L43;
        if (r4[r7].length() < 2) goto L43;
        if (r4[r7].contains("==") == true) goto L33;
        String[] r317 = r4[r7].split("\\+");     // Catch: Exception -> L266 IOException -> L268
        r13 = r317[0];     // Catch: Exception -> L266 IOException -> L268
        System.out.println("day:" + r4[r7]);     // Catch: Exception -> L266 IOException -> L268
        String r96 = r317[1];     // Catch: Exception -> L266 IOException -> L268
        r10 = r317[2];     // Catch: Exception -> L266 IOException -> L268
        System.out.println("type:" + r13);     // Catch: Exception -> L266 IOException -> L268
        if (r317.length != 4) goto L37;
        r11 = r317[3];     // Catch: Exception -> L266 IOException -> L268
    L39:
        if (r317.length != 5) goto L41;
        r11 = r317[3];     // Catch: Exception -> L266 IOException -> L268
        r12 = r317[4];     // Catch: Exception -> L266 IOException -> L268
    L42:
        r37 = r7;
        r38 = r96;
        r92 = r16;
        r33 = r21;
        goto L265
    L41:
        r12 = "";
        goto L42
    L37:
        r11 = "";
    L270:
        getDynamicData();
        return;
    L268:
        e = move-exception;
        e.printStackTrace();
    L266:
        e = move-exception;
        e.printStackTrace();
        goto L270
    }

    public void loadingSoom(int r45, int r46) {
        int r2 = r45;
        String r3 = "frommmm:-";
        String r4 = "dis:";
        String r5 = "orso2";
        String[] r6 = new String(app.decodeFile2(this.activity, "orsozoxi", "katamars/soomkyber.orso")).split("\n");     // Catch: Exception -> L294 IOException -> L296
        String r11 = "";
        String r12 = r11;
        String r13 = r12;
        String r14 = r13;
        String r15 = r14;
        int r9 = 0;
        int r10 = 0;
    L5:
        if (r9 >= r6.length) goto L298;
        String r16 = r3;
        if (r6[r9].equals("") == false) goto L11;
        String r19 = r4;
        String r25 = r12;
        String r24 = r13;
        String r22 = r14;
        String r18 = r15;
    L35:
        String r20 = r5;
        String[] r23 = r6;
        r15 = r18;
    L36:
        String r28 = r19;
        r14 = r22;
        r13 = r24;
        r12 = r25;
        int r32 = 1;
        int r242 = r10;
        String r102 = r16;
    L293:
        r9 = r9 + r32;
        r6 = r23;
        r3 = r102;
        r5 = r20;
        r10 = r242;
        r4 = r28;
        r2 = r45;
        goto L5
    L11:
        r18 = r15;
        r19 = r4;
        if (r6[r9].charAt(0) == '+') goto L22;
        if (r6[r9].contains("+") == true) goto L22;
        if (r6[r9].contains(" ") == false) goto L18;
        r6[r9].replace(" ", "");     // Catch: Exception -> L294 IOException -> L296
    L18:
        r20 = r5;
        r23 = r6;
        r242 = Integer.valueOf(r6[r9]).intValue();     // Catch: Exception -> L294 IOException -> L296
        r102 = r16;
        r15 = r18;
    L19:
        r28 = r19;
    L20:
        r32 = 1;
    L22:
        String r21 = " ";
        String r42 = "type:";
        r22 = r14;
        String r142 = "+++";
        r24 = r13;
        String r132 = "\\+";
        r25 = r12;
        if (r6[r9].charAt(0) != '+') goto L25;
    L71:
        if (r9 <= 0) goto L76;
        int r7 = r9 - 1;
        boolean r122 = r6[r7].contains("==");     // Catch: Exception -> L294 IOException -> L296
        r20 = r5;
        if (r122 != true) goto L75;
        boolean r26 = r122;
        this.legendList.add(new Bible("", r6[r7].replace("==", "")));     // Catch: Exception -> L294 IOException -> L296
    L77:
        if (r2 != r10) goto L284;
        if (r26 == true) goto L284;
        int r33 = Integer.valueOf(r11).intValue() - 1;     // Catch: Exception -> L294 IOException -> L296
        int r72 = Integer.valueOf(r25).intValue() - 1;     // Catch: Exception -> L294 IOException -> L296
        String r52 = r33 + "+" + r72;     // Catch: Exception -> L294 IOException -> L296
        String r123 = r24;
        if (r123.equals("") == true) goto L84;
        String r152 = r22;
        if (r152.equals("") == true) goto L86;
        int r222 = Integer.valueOf(r123).intValue() - 1;
        int r27 = Integer.valueOf(r152).intValue() - 1;
    L91:
        new ArrayList();     // Catch: Exception -> L294 IOException -> L296
        r242 = r10;
        loadinmapp(choosebook(r33), r33, r72);     // Catch: Exception -> L294 IOException -> L296
        Iterator<Map.Entry<String, ArrayList<String>>> r73 = this.levelsMap.entrySet().iterator();     // Catch: Exception -> L294 IOException -> L296
        String r272 = "";
        int r262 = r222;
        int r103 = 0;
        int r223 = 1;
    L93:
        if (r73.hasNext() == false) goto L283;
        Map.Entry<String, ArrayList<String>> r282 = r73.next();     // Catch: Exception -> L294 IOException -> L296
        Iterator<Map.Entry<String, ArrayList<String>>> r30 = r73;
        String r74 = r282.getKey();     // Catch: Exception -> L294 IOException -> L296
        int r29 = r103;
        PrintStream r104 = System.out;     // Catch: Exception -> L294 IOException -> L296
        int r31 = r27;
        StringBuilder r210 = new StringBuilder();     // Catch: Exception -> L294 IOException -> L296
        int r322 = r33;
        String r34 = r19;
        r210.append(r34);     // Catch: Exception -> L294 IOException -> L296
        r210.append(r52);     // Catch: Exception -> L294 IOException -> L296
        r210.append(r34);     // Catch: Exception -> L294 IOException -> L296
        r210.append(r74);     // Catch: Exception -> L294 IOException -> L296
        r104.println(r210.toString());     // Catch: Exception -> L294 IOException -> L296
        if (r74.equals(AppEventsConstants.EVENT_PARAM_VALUE_NO) == false) goto L281;
        ArrayList<String> r211 = r282.getValue();     // Catch: Exception -> L294 IOException -> L296
        int r75 = Integer.valueOf(r25).intValue();     // Catch: Exception -> L294 IOException -> L296
        int r105 = r9 + 1;     // Catch: Exception -> L294 IOException -> L296
        String r283 = r34;
        String r332 = r52;
        if (r123.equals("") == false) goto L100;
    L154:
        String r39 = r42;
        int r38 = r9;
        String r92 = r132;
        String r133 = r152;
        String r153 = r18;
        String r43 = r21;
        String r422 = r11;
        String[] r35 = r6;
        String r62 = r142;
        if (r123.equals("") == false) goto L157;
    L203:
        String r212 = r422;
        String r37 = r133;
        String[] r213 = r35;
        r213[r105].split(r92);     // Catch: Exception -> L294 IOException -> L296
        int r36 = r38 - 3;
        if (r36 < 0) goto L212;
        String r192 = r123;
        if (r213[r36].contains(r62) == false) goto L208;
        int r352 = r36;
        String r182 = ShareConstants.WEB_DIALOG_PARAM_DATA;
        this.legendList.add(new Bible("", typeofslah(r153)));     // Catch: Exception -> L294 IOException -> L296
    L215:
        if (r38 < (r213.length - 2)) goto L217;
    L249:
        String r53 = r62;
        String r63 = r92;
        int r382 = r38;
    L250:
        String r106 = r153;
    L251:
        Iterator<String> r310 = r211.iterator();     // Catch: Exception -> L294 IOException -> L296
        int r124 = r262;
        String r134 = r272;
        int r112 = r29;
        int r76 = 0;
    L253:
        if (r310.hasNext() == false) goto L280;
        String r143 = r310.next();     // Catch: Exception -> L294 IOException -> L296
        String r154 = r192;
        if (r154.equals("") == true) goto L269;
        Iterator<String> r183 = r310;
        String r311 = r37;
        if (r311.equals("") == true) goto L268;
        if (r76 < r124) goto L266;
        String r193 = r43;
        PrintStream r44 = System.out;     // Catch: Exception -> L294 IOException -> L296
        String r362 = r63;
        StringBuilder r64 = new StringBuilder();     // Catch: Exception -> L294 IOException -> L296
        String r40 = r106;
        String r107 = r16;
        r64.append(r107);     // Catch: Exception -> L294 IOException -> L296
        r64.append(r124);     // Catch: Exception -> L294 IOException -> L296
        r44.println(r64.toString());     // Catch: Exception -> L294 IOException -> L296
        System.out.println(r107 + r124);     // Catch: Exception -> L294 IOException -> L296
        String r47 = r134 + r143 + "<br>";     // Catch: Exception -> L294 IOException -> L296
        int r65 = r31;
        if (r124 > r65) goto L264;
        r124 = r124 + 1;     // Catch: Exception -> L294 IOException -> L296
        String r162 = r47;
        this.legendList.add(new Bible(String.valueOf(r124), r143));     // Catch: Exception -> L294 IOException -> L296
    L265:
        r134 = r162;
    L267:
        r76 = r76 + 1;     // Catch: Exception -> L294 IOException -> L296
        r37 = r311;
    L279:
        r31 = r65;
        r16 = r107;
        r310 = r183;
        r43 = r193;
        r63 = r362;
        r106 = r40;
        r192 = r154;
        goto L253
    L264:
        r162 = r47;
        goto L265
    L266:
        r193 = r43;
        r362 = r63;
        r40 = r106;
        r107 = r16;
        r65 = r31;
        goto L267
    L268:
        r193 = r43;
        r362 = r63;
        r40 = r106;
        r107 = r16;
        r65 = r31;
    L271:
        if (r154.equals("") == false) goto L273;
    L278:
        r37 = r311;
        r134 = r134 + r143 + "<br>";     // Catch: Exception -> L294 IOException -> L296
        this.legendList.add(new Bible(String.valueOf(r223), r143));     // Catch: Exception -> L294 IOException -> L296
        r223 = r223 + 1;     // Catch: Exception -> L294 IOException -> L296
        goto L279
    L273:
        if (r311.equals("") == false) goto L278;
        if (r124 != r112) goto L276;
        r37 = r311;
        this.legendList.add(new Bible(String.valueOf(r124 + 1), r143));     // Catch: Exception -> L294 IOException -> L296
    L277:
        r112 = r112 + 1;     // Catch: Exception -> L294 IOException -> L296
        goto L279
    L276:
        r37 = r311;
        goto L277
    L269:
        r183 = r310;
        r193 = r43;
        r362 = r63;
        r40 = r106;
        r107 = r16;
        r65 = r31;
        r311 = r37;
        goto L271
    L280:
        String r363 = r63;
        String r402 = r106;
        String r108 = r16;
        String r155 = r192;
        int r66 = r31;
        String r194 = r43;
        r29 = r112;
        r262 = r124;
        r272 = r134;
    L282:
        r142 = r53;
        r16 = r108;
        r123 = r155;
        r11 = r212;
        r103 = r29;
        r73 = r30;
        r33 = r322;
        r52 = r332;
        r132 = r363;
        r152 = r37;
        r9 = r382;
        r42 = r39;
        r18 = r402;
        r21 = r194;
        r19 = r283;
        int r423 = r66;
        r6 = r213;
        r27 = r423;
        goto L93
    L217:
        if (r213[r38].contains(r62) == true) goto L249;
        String[] r312 = r213[r105].split(r92);     // Catch: Exception -> L294 IOException -> L296
        if (r312[0].equals(r153) == false) goto L240;
        String r135 = r312[1];     // Catch: Exception -> L294 IOException -> L296
        String r136 = r312[2];     // Catch: Exception -> L294 IOException -> L296
        r382 = r38;
        System.out.println(r39 + r153);     // Catch: Exception -> L294 IOException -> L296
        if (r312.length != 4) goto L223;
        String r109 = r312[3];     // Catch: Exception -> L294 IOException -> L296
    L224:
        r39 = r39;
        if (r312.length != 5) goto L227;
        r109 = r312[3];     // Catch: Exception -> L294 IOException -> L296
        String r313 = r312[4];     // Catch: Exception -> L294 IOException -> L296
    L229:
        if (r109.equals("") == false) goto L231;
    L233:
        String r41 = r62;
        String r403 = r153;
        if (r109.equals("") == false) goto L236;
    L238:
        this.legendList.add(new Bible("", this.books[Integer.valueOf(r322).intValue()] + r43 + r75 + " , " + r136));     // Catch: Exception -> L294 IOException -> L296
    L239:
        r63 = r92;
        r106 = r403;
        r53 = r41;
        goto L251
    L236:
        if (r313.equals("") == false) goto L238;
        this.legendList.add(new Bible("", this.books[Integer.valueOf(r322).intValue()] + r43 + r75 + " , " + r136 + " : " + r109));     // Catch: Exception -> L294 IOException -> L296
        goto L239
    L231:
        if (r313.equals("") == true) goto L233;
        List<Bible> r93 = this.legendList;     // Catch: Exception -> L294 IOException -> L296
        r403 = r153;
        StringBuilder r156 = new StringBuilder();     // Catch: Exception -> L294 IOException -> L296
        r41 = r62;
        r156.append(this.books[Integer.valueOf(r322).intValue()]);     // Catch: Exception -> L294 IOException -> L296
        r156.append(r43);     // Catch: Exception -> L294 IOException -> L296
        r156.append(r75);     // Catch: Exception -> L294 IOException -> L296
        r156.append(" , ");     // Catch: Exception -> L294 IOException -> L296
        r156.append(r136);     // Catch: Exception -> L294 IOException -> L296
        r156.append(" : ");     // Catch: Exception -> L294 IOException -> L296
        r156.append(r313);     // Catch: Exception -> L294 IOException -> L296
        r156.append(" - ");     // Catch: Exception -> L294 IOException -> L296
        r156.append(r109);     // Catch: Exception -> L294 IOException -> L296
        r93.add(new Bible("", r156.toString()));     // Catch: Exception -> L294 IOException -> L296
        goto L239
    L227:
        r313 = "";
        goto L229
    L223:
        r109 = "";
        goto L224
    L240:
        r382 = r38;
        if (r352 < 0) goto L248;
        r53 = r62;
        if (r213[r352].contains(r53) == true) goto L247;
        System.out.println(r182 + r213[r352]);     // Catch: Exception -> L294 IOException -> L296
        r63 = r92;
        r106 = r153;
        if (r213[r352].split(r63)[0].equals(r106) == true) goto L251;
        this.legendList.add(new Bible("", this.books[Integer.valueOf(r322).intValue()] + r43 + r75));     // Catch: Exception -> L294 IOException -> L296
        goto L251
    L247:
        r63 = r92;
        r106 = r153;
        this.legendList.add(new Bible("", this.books[Integer.valueOf(r322).intValue()] + r43 + r75));     // Catch: Exception -> L294 IOException -> L296
        goto L251
    L248:
        r63 = r92;
        r106 = r153;
        r53 = r62;
        this.legendList.add(new Bible("", typeofslah(r106)));     // Catch: Exception -> L294 IOException -> L296
        goto L251
    L208:
        if (r213[r36].split(r92)[0].equals(r153) == true) goto L210;
        List<Bible> r125 = this.legendList;     // Catch: Exception -> L294 IOException -> L296
        r182 = ShareConstants.WEB_DIALOG_PARAM_DATA;
        r352 = r36;
        r125.add(new Bible("", typeofslah(r153)));     // Catch: Exception -> L294 IOException -> L296
        goto L215
    L210:
        r352 = r36;
    L213:
        r182 = ShareConstants.WEB_DIALOG_PARAM_DATA;
        goto L215
    L212:
        r352 = r36;
        r192 = r123;
        goto L213
    L157:
        if (r133.equals("") == false) goto L203;
        r35[r105].split(r92);     // Catch: Exception -> L294 IOException -> L296
        int r113 = r38 - 3;
        if (r113 < 0) goto L167;
        r212 = r422;
        if (r35[r113].contains(r62) == false) goto L163;
        String r184 = ShareConstants.WEB_DIALOG_PARAM_DATA;
        r37 = r133;
        this.legendList.add(new Bible("", typeofslah(r153)));     // Catch: Exception -> L294 IOException -> L296
    L168:
        r213 = r35;
        if (r38 >= (r213.length - 2)) goto L201;
        String[] r314 = r213[r105].split(r92);     // Catch: Exception -> L294 IOException -> L296
        r382 = r38;
        if (r314[0].equals(r153) == false) goto L193;
        String r114 = r314[1];     // Catch: Exception -> L294 IOException -> L296
        String r115 = r314[2];     // Catch: Exception -> L294 IOException -> L296
        System.out.println(r39 + r153);     // Catch: Exception -> L294 IOException -> L296
        if (r314.length != 4) goto L175;
        String r1010 = r314[3];     // Catch: Exception -> L294 IOException -> L296
    L176:
        String r185 = r1010;
        if (r314.length != 5) goto L179;
        String r1011 = r314[3];     // Catch: Exception -> L294 IOException -> L296
        String r315 = r314[4];     // Catch: Exception -> L294 IOException -> L296
    L181:
        if (r1011.equals("") == false) goto L183;
    L185:
        String r353 = r62;
        r39 = r39;
        String r364 = r153;
        if (r1011.equals("") == false) goto L188;
    L190:
        this.legendList.add(new Bible("", this.books[Integer.valueOf(r322).intValue()] + r43 + r75 + " : " + r123 + ", " + r115));     // Catch: Exception -> L294 IOException -> L296
    L191:
        r92 = r92;
        r62 = r353;
        r153 = r364;
    L202:
        r53 = r62;
        r63 = r92;
        r192 = r123;
        goto L250
    L188:
        if (r315.equals("") == false) goto L190;
        this.legendList.add(new Bible("", this.books[Integer.valueOf(r322).intValue()] + r43 + r75 + " : " + r123 + " , " + r115 + " : " + r1011));     // Catch: Exception -> L294 IOException -> L296
        goto L191
    L183:
        if (r315.equals("") == true) goto L185;
        List<Bible> r137 = this.legendList;     // Catch: Exception -> L294 IOException -> L296
        r39 = r39;
        r364 = r153;
        StringBuilder r157 = new StringBuilder();     // Catch: Exception -> L294 IOException -> L296
        r353 = r62;
        r157.append(this.books[Integer.valueOf(r322).intValue()]);     // Catch: Exception -> L294 IOException -> L296
        r157.append(r43);     // Catch: Exception -> L294 IOException -> L296
        r157.append(r75);     // Catch: Exception -> L294 IOException -> L296
        r157.append(" : ");     // Catch: Exception -> L294 IOException -> L296
        r157.append(r123);     // Catch: Exception -> L294 IOException -> L296
        r157.append(" , ");     // Catch: Exception -> L294 IOException -> L296
        r157.append(r115);     // Catch: Exception -> L294 IOException -> L296
        r157.append(" : ");     // Catch: Exception -> L294 IOException -> L296
        r157.append(r315);     // Catch: Exception -> L294 IOException -> L296
        r157.append(" -");     // Catch: Exception -> L294 IOException -> L296
        r157.append(r1011);     // Catch: Exception -> L294 IOException -> L296
        r137.add(new Bible("", r157.toString()));     // Catch: Exception -> L294 IOException -> L296
        goto L191
    L179:
        r315 = "";
        r1011 = r185;
        goto L181
    L175:
        r1010 = "";
        goto L176
    L193:
        if (r113 < 0) goto L200;
        r62 = r62;
        if (r213[r113].contains(r62) == true) goto L199;
        System.out.println(r184 + r213[r113]);     // Catch: Exception -> L294 IOException -> L296
        r92 = r92;
        r153 = r153;
        if (r213[r113].split(r92)[0].equals(r153) == true) goto L202;
        this.legendList.add(new Bible("", this.books[Integer.valueOf(r322).intValue()] + r43 + r75 + " : " + r123));     // Catch: Exception -> L294 IOException -> L296
        goto L202
    L199:
        r92 = r92;
        r153 = r153;
        this.legendList.add(new Bible("", this.books[Integer.valueOf(r322).intValue()] + r43 + r75 + " : " + r123));     // Catch: Exception -> L294 IOException -> L296
        goto L202
    L200:
        r92 = r92;
        r62 = r62;
        r153 = r153;
        this.legendList.add(new Bible("", typeofslah(r153)));     // Catch: Exception -> L294 IOException -> L296
        goto L202
    L201:
        r382 = r38;
        goto L202
    L163:
        if (r35[r113].split(r92)[0].equals(r153) == true) goto L165;
        List<Bible> r214 = this.legendList;     // Catch: Exception -> L294 IOException -> L296
        r37 = r133;
        r184 = ShareConstants.WEB_DIALOG_PARAM_DATA;
        r214.add(new Bible("", typeofslah(r153)));     // Catch: Exception -> L294 IOException -> L296
        goto L168
    L165:
        r184 = ShareConstants.WEB_DIALOG_PARAM_DATA;
        r37 = r133;
        goto L168
    L167:
        r212 = r422;
        r184 = ShareConstants.WEB_DIALOG_PARAM_DATA;
        r37 = r133;
        this.legendList.add(new Bible("", typeofslah(r153)));     // Catch: Exception -> L294 IOException -> L296
        goto L168
    L100:
        if (r152.equals("") == true) goto L154;
        r192 = r123;
        if (r9 >= (r6.length - 1)) goto L151;
        PrintStream r126 = System.out;     // Catch: Exception -> L294 IOException -> L296
        StringBuilder r215 = new StringBuilder();     // Catch: Exception -> L294 IOException -> L296
        r215.append(ShareConstants.WEB_DIALOG_PARAM_DATA);     // Catch: Exception -> L294 IOException -> L296
        String r372 = r152;
        r215.append(r6.length);     // Catch: Exception -> L294 IOException -> L296
        r215.append("data2:");     // Catch: Exception -> L294 IOException -> L296
        r215.append(r105);     // Catch: Exception -> L294 IOException -> L296
        r215.append("xml:");     // Catch: Exception -> L294 IOException -> L296
        r215.append(r6[r105]);     // Catch: Exception -> L294 IOException -> L296
        r126.println(r215.toString());     // Catch: Exception -> L294 IOException -> L296
        String[] r216 = r6[r105].split(r132);     // Catch: Exception -> L294 IOException -> L296
        int r1012 = r9 - 3;
        if (r1012 >= 0) goto L106;
        r382 = r9;
        String r127 = r18;
        String r186 = r132;
    L114:
        if (r216[0].equals(r127) == true) goto L116;
    L137:
        r39 = r42;
        String[] r354 = r6;
        r43 = r21;
        String r138 = r372;
        r212 = r11;
        String r365 = r127;
        String r128 = r192;
        String r195 = r142;
        if (r1012 < 0) goto L149;
        String r67 = r195;
        if (r354[r1012].contains(r67) == true) goto L148;
        System.out.println(ShareConstants.WEB_DIALOG_PARAM_DATA + r354[r1012]);     // Catch: Exception -> L294 IOException -> L296
        String r94 = r186;
        String[] r217 = r354[r1012].split(r94);     // Catch: Exception -> L294 IOException -> L296
        String r158 = r365;
        if (r217[0].equals(r158) == true) goto L145;
        this.legendList.add(new Bible("", this.books[Integer.valueOf(r322).intValue()] + r43 + r75 + " : " + r138 + " - " + r128));     // Catch: Exception -> L294 IOException -> L296
    L150:
        r53 = r67;
        r63 = r94;
        r192 = r128;
        r37 = r138;
        r106 = r158;
    L152:
        r213 = r354;
        goto L251
    L145:
        if (r217[1].equals(r212) == true) goto L147;
        List<Bible> r218 = this.legendList;     // Catch: Exception -> L294 IOException -> L296
        StringBuilder r144 = new StringBuilder();     // Catch: Exception -> L294 IOException -> L296
        r212 = r212;
        r144.append(this.books[Integer.valueOf(r322).intValue()]);     // Catch: Exception -> L294 IOException -> L296
        r144.append(r43);     // Catch: Exception -> L294 IOException -> L296
        r144.append(r75);     // Catch: Exception -> L294 IOException -> L296
        r144.append(" : ");     // Catch: Exception -> L294 IOException -> L296
        r144.append(r138);     // Catch: Exception -> L294 IOException -> L296
        r144.append(" - ");     // Catch: Exception -> L294 IOException -> L296
        r144.append(r128);     // Catch: Exception -> L294 IOException -> L296
        r218.add(new Bible("", r144.toString()));     // Catch: Exception -> L294 IOException -> L296
        goto L150
    L147:
        r212 = r212;
        goto L150
    L148:
        r94 = r186;
        r158 = r365;
        this.legendList.add(new Bible("", this.books[Integer.valueOf(r322).intValue()] + r43 + r75 + " : " + r138 + " - " + r128));     // Catch: Exception -> L294 IOException -> L296
        goto L150
    L149:
        r94 = r186;
        r67 = r195;
        r158 = r365;
        this.legendList.add(new Bible("", typeofslah(r158)));     // Catch: Exception -> L294 IOException -> L296
        this.legendList.add(new Bible("", this.books[Integer.valueOf(r322).intValue()] + r43 + r75 + " : " + r138 + " - " + r128));     // Catch: Exception -> L294 IOException -> L296
        goto L150
    L116:
        if (r216[1].equals(r11) == false) goto L137;
        String r316 = r216[1];     // Catch: Exception -> L294 IOException -> L296
        String r95 = r216[2];     // Catch: Exception -> L294 IOException -> L296
        System.out.println(r42 + r127);     // Catch: Exception -> L294 IOException -> L296
        if (r216.length != 4) goto L120;
        String r317 = r216[3];     // Catch: Exception -> L294 IOException -> L296
    L122:
        if (r216.length != 5) goto L124;
        r317 = r216[3];     // Catch: Exception -> L294 IOException -> L296
        String r219 = r216[4];     // Catch: Exception -> L294 IOException -> L296
    L126:
        if (r317.equals("") == false) goto L128;
    L130:
        r39 = r42;
        r43 = r21;
        r138 = r372;
        r212 = r11;
        String r366 = r127;
        r128 = r192;
        String r196 = r142;
        if (r317.equals("") == false) goto L133;
    L135:
        r354 = r6;
        this.legendList.add(new Bible("", this.books[Integer.valueOf(r322).intValue()] + r43 + r75 + " : " + r138 + " - " + r128 + " , " + r95));     // Catch: Exception -> L294 IOException -> L296
    L136:
        r94 = r186;
        r67 = r196;
        r158 = r366;
        goto L150
    L133:
        if (r219.equals("") == false) goto L135;
        List<Bible> r220 = this.legendList;     // Catch: Exception -> L294 IOException -> L296
        StringBuilder r159 = new StringBuilder();     // Catch: Exception -> L294 IOException -> L296
        r354 = r6;
        r159.append(this.books[Integer.valueOf(r322).intValue()]);     // Catch: Exception -> L294 IOException -> L296
        r159.append(r43);     // Catch: Exception -> L294 IOException -> L296
        r159.append(r75);     // Catch: Exception -> L294 IOException -> L296
        r159.append(" : ");     // Catch: Exception -> L294 IOException -> L296
        r159.append(r138);     // Catch: Exception -> L294 IOException -> L296
        r159.append(" - ");     // Catch: Exception -> L294 IOException -> L296
        r159.append(r128);     // Catch: Exception -> L294 IOException -> L296
        r159.append(" , ");     // Catch: Exception -> L294 IOException -> L296
        r159.append(r95);     // Catch: Exception -> L294 IOException -> L296
        r159.append(" : ");     // Catch: Exception -> L294 IOException -> L296
        r159.append(r317);     // Catch: Exception -> L294 IOException -> L296
        r220.add(new Bible("", r159.toString()));     // Catch: Exception -> L294 IOException -> L296
        goto L136
    L128:
        if (r219.equals("") == true) goto L130;
        List<Bible> r1013 = this.legendList;     // Catch: Exception -> L294 IOException -> L296
        StringBuilder r1510 = new StringBuilder();     // Catch: Exception -> L294 IOException -> L296
        r39 = r42;
        r1510.append(this.books[Integer.valueOf(r322).intValue()]);     // Catch: Exception -> L294 IOException -> L296
        r43 = r21;
        r1510.append(r43);     // Catch: Exception -> L294 IOException -> L296
        r1510.append(r75);     // Catch: Exception -> L294 IOException -> L296
        r1510.append(" : ");     // Catch: Exception -> L294 IOException -> L296
        r1510.append(r372);     // Catch: Exception -> L294 IOException -> L296
        r212 = r11;
        r1510.append(" - ");     // Catch: Exception -> L294 IOException -> L296
        r366 = r127;
        r128 = r192;
        r1510.append(r128);     // Catch: Exception -> L294 IOException -> L296
        r196 = r142;
        r1510.append(" , ");     // Catch: Exception -> L294 IOException -> L296
        r1510.append(r95);     // Catch: Exception -> L294 IOException -> L296
        r1510.append(" : ");     // Catch: Exception -> L294 IOException -> L296
        r1510.append(r219);     // Catch: Exception -> L294 IOException -> L296
        r1510.append(" - ");     // Catch: Exception -> L294 IOException -> L296
        r1510.append(r317);     // Catch: Exception -> L294 IOException -> L296
        r1013.add(new Bible("", r1510.toString()));     // Catch: Exception -> L294 IOException -> L296
        r354 = r6;
        r138 = r372;
        goto L136
    L124:
        r219 = "";
        goto L126
    L120:
        r317 = "";
        goto L122
    L106:
        if (r6[r1012].contains(r142) == true) goto L111;
        PrintStream r129 = System.out;     // Catch: Exception -> L294 IOException -> L296
        StringBuilder r1511 = new StringBuilder();     // Catch: Exception -> L294 IOException -> L296
        r1511.append(ShareConstants.WEB_DIALOG_PARAM_DATA);     // Catch: Exception -> L294 IOException -> L296
        r382 = r9;
        r1511.append(r6[r1012]);     // Catch: Exception -> L294 IOException -> L296
        r129.println(r1511.toString());     // Catch: Exception -> L294 IOException -> L296
        String[] r96 = r6[r1012].split(r132);     // Catch: Exception -> L294 IOException -> L296
        r127 = r18;
        if (r96[0].equals(r127) == true) goto L110;
        r186 = r132;
        this.legendList.add(new Bible("", typeofslah(r127)));     // Catch: Exception -> L294 IOException -> L296
        goto L114
    L110:
        r186 = r132;
        r96[1].equals(r11);     // Catch: Exception -> L294 IOException -> L296
        goto L114
    L111:
        r382 = r9;
        r127 = r18;
        r186 = r132;
        this.legendList.add(new Bible("", typeofslah(r127)));     // Catch: Exception -> L294 IOException -> L296
        goto L114
    L151:
        r39 = r42;
        r354 = r6;
        r382 = r9;
        r43 = r21;
        r212 = r11;
        r63 = r132;
        r53 = r142;
        r37 = r152;
        r106 = r18;
        goto L152
    L281:
        r283 = r34;
        r39 = r42;
        r332 = r52;
        r213 = r6;
        r382 = r9;
        r363 = r132;
        r53 = r142;
        r37 = r152;
        r108 = r16;
        r402 = r18;
        r194 = r21;
        r66 = r31;
        r212 = r11;
        r155 = r123;
        goto L282
    L283:
        r23 = r6;
        int r383 = r9;
        String r2110 = r11;
        String r54 = r142;
        String r373 = r152;
        r102 = r16;
        String r404 = r18;
        r28 = r19;
        String r1512 = r123;
    L286:
        if (r23[r383].equals(r54) == false) goto L288;
    L291:
        r11 = "";
        r12 = r11;
        r13 = r12;
        r14 = r13;
        r242 = 0;
    L292:
        r9 = r383;
        r15 = r404;
        goto L20
    L288:
        if (r23[r383].contains(r54) == true) goto L291;
        r13 = r1512;
        r11 = r2110;
        r12 = r25;
        r14 = r373;
    L86:
        if (r123.equals("") == false) goto L88;
    L90:
        r27 = 0;
        r222 = 0;
        goto L91
    L88:
        if (r152.equals("") == false) goto L90;
        r222 = Integer.valueOf(r123).intValue() - 1;
        r27 = 0;
        goto L91
    L84:
        r152 = r22;
    L284:
        r23 = r6;
        r383 = r9;
        r2110 = r11;
        r54 = "+++";
        r404 = r18;
        r28 = r19;
        r373 = r22;
        r1512 = r24;
        r242 = r10;
        r102 = r16;
        goto L286
    L75:
        r26 = r122;
        goto L77
    L76:
        r20 = r5;
        r26 = false;
        goto L77
    L25:
        if (r6[r9].equals("+") == true) goto L71;
        if (r6[r9].equals("+++") == true) goto L71;
        if (r6[r9].contains("+++") == true) goto L71;
        if (r6[r9].length() < 2) goto L71;
        if (r6[r9].contains("==") == true) goto L35;
        String[] r318 = r6[r9].split("\\+");     // Catch: Exception -> L294 IOException -> L296
        r15 = r318[0];     // Catch: Exception -> L294 IOException -> L296
        if (r2 == r10) goto L40;
    L58:
        r15 = r318[0];     // Catch: Exception -> L294 IOException -> L296
        System.out.println("day:" + r6[r9]);     // Catch: Exception -> L294 IOException -> L296
        r11 = r318[1];     // Catch: Exception -> L294 IOException -> L296
        if (r318.length <= 2) goto L61;
        r12 = r318[2];     // Catch: Exception -> L294 IOException -> L296
    L62:
        System.out.println("type:" + r15);     // Catch: Exception -> L294 IOException -> L296
        if (r318.length != 4) goto L65;
        r13 = r318[3];     // Catch: Exception -> L294 IOException -> L296
    L67:
        if (r318.length != 5) goto L69;
        r13 = r318[3];     // Catch: Exception -> L294 IOException -> L296
        r14 = r318[4];     // Catch: Exception -> L294 IOException -> L296
        r20 = r5;
        r23 = r6;
    L70:
        r242 = r10;
        r102 = r16;
        goto L19
    L69:
        r20 = r5;
        r23 = r6;
        r14 = "";
        goto L70
    L65:
        r13 = "";
        goto L67
    L61:
        r12 = "";
        goto L62
    L40:
        if (r15.equals("Mm") == false) goto L42;
    L57:
        Log.d(r5, "inside" + r15);     // Catch: Exception -> L294 IOException -> L296
        Log.d(r5, "soomday" + r2 + " day=" + r10);     // Catch: Exception -> L294 IOException -> L296
        loadmazmor(r6[r9], 0);     // Catch: Exception -> L294 IOException -> L296
        r9 = r9 + 1;     // Catch: Exception -> L294 IOException -> L296
        r20 = r5;
        r23 = r6;
        goto L36
    L42:
        if (r15.equals("MM") == true) goto L57;
        if (r15.equals("mm") == true) goto L57;
        if (r15.equals("m") == true) goto L57;
        if (r15.equals("M") == true) goto L57;
        if (r15.contains("Mb") == true) goto L57;
        if (r15.contains("mb") == true) goto L57;
        if (r15.contains("Ma") == true) goto L57;
        if (r15.contains("ma") == false) goto L58;
    L298:
        getDynamicData();
        return;
    L296:
        e = move-exception;
        e.printStackTrace();
    L294:
        e = move-exception;
        e.printStackTrace();
        goto L298
    }

    public void loading7maseen(int r45, int r46) {
        int r2 = r45;
        String r3 = "frommmm:-";
        String r4 = "dis:";
        String r6 = "orso2";
        Log.d("orso2", "7maseenday=" + r2);
        String[] r5 = new String(app.decodeFile2(this.activity, "orsozoxi", "katamars/7maseen.orso")).split("\n");     // Catch: Exception -> L294 IOException -> L296
        String r11 = "";
        String r12 = r11;
        String r13 = r12;
        String r14 = r13;
        String r15 = r14;
        int r9 = 0;
        int r10 = 0;
    L5:
        if (r9 >= r5.length) goto L298;
        String r16 = r3;
        if (r5[r9].equals("") == false) goto L11;
        String r19 = r4;
        String r25 = r12;
        String r24 = r13;
        String r22 = r14;
        String r18 = r15;
    L35:
        String[] r23 = r5;
        String r20 = r6;
        r15 = r18;
    L36:
        String r28 = r19;
        r14 = r22;
        r13 = r24;
        r12 = r25;
        int r32 = 1;
        int r242 = r10;
        String r102 = r16;
    L293:
        r9 = r9 + r32;
        r5 = r23;
        r3 = r102;
        r6 = r20;
        r10 = r242;
        r4 = r28;
        r2 = r45;
        goto L5
    L11:
        r18 = r15;
        r19 = r4;
        if (r5[r9].charAt(0) == '+') goto L22;
        if (r5[r9].contains("+") == true) goto L22;
        if (r5[r9].contains(" ") == false) goto L18;
        r5[r9].replace(" ", "");     // Catch: Exception -> L294 IOException -> L296
    L18:
        r23 = r5;
        r20 = r6;
        r242 = Integer.valueOf(r5[r9]).intValue();     // Catch: Exception -> L294 IOException -> L296
        r102 = r16;
        r15 = r18;
    L19:
        r28 = r19;
    L20:
        r32 = 1;
    L22:
        String r21 = " ";
        String r42 = "type:";
        r22 = r14;
        String r142 = "+++";
        r24 = r13;
        String r132 = "\\+";
        r25 = r12;
        if (r5[r9].charAt(0) != '+') goto L25;
    L71:
        if (r9 <= 0) goto L76;
        int r7 = r9 - 1;
        boolean r122 = r5[r7].contains("==");     // Catch: Exception -> L294 IOException -> L296
        r20 = r6;
        if (r122 != true) goto L75;
        boolean r26 = r122;
        this.legendList.add(new Bible("", r5[r7].replace("==", "")));     // Catch: Exception -> L294 IOException -> L296
    L77:
        if (r2 != r10) goto L284;
        if (r26 == true) goto L284;
        int r33 = Integer.valueOf(r11).intValue() - 1;     // Catch: Exception -> L294 IOException -> L296
        int r72 = Integer.valueOf(r25).intValue() - 1;     // Catch: Exception -> L294 IOException -> L296
        String r62 = r33 + "+" + r72;     // Catch: Exception -> L294 IOException -> L296
        String r123 = r24;
        if (r123.equals("") == true) goto L84;
        String r152 = r22;
        if (r152.equals("") == true) goto L86;
        int r222 = Integer.valueOf(r123).intValue() - 1;
        int r27 = Integer.valueOf(r152).intValue() - 1;
    L91:
        new ArrayList();     // Catch: Exception -> L294 IOException -> L296
        r242 = r10;
        loadinmapp(choosebook(r33), r33, r72);     // Catch: Exception -> L294 IOException -> L296
        Iterator<Map.Entry<String, ArrayList<String>>> r73 = this.levelsMap.entrySet().iterator();     // Catch: Exception -> L294 IOException -> L296
        String r272 = "";
        int r262 = r222;
        int r103 = 0;
        int r223 = 1;
    L93:
        if (r73.hasNext() == false) goto L283;
        Map.Entry<String, ArrayList<String>> r282 = r73.next();     // Catch: Exception -> L294 IOException -> L296
        Iterator<Map.Entry<String, ArrayList<String>>> r30 = r73;
        String r74 = r282.getKey();     // Catch: Exception -> L294 IOException -> L296
        int r29 = r103;
        PrintStream r104 = System.out;     // Catch: Exception -> L294 IOException -> L296
        int r31 = r27;
        StringBuilder r210 = new StringBuilder();     // Catch: Exception -> L294 IOException -> L296
        int r322 = r33;
        String r34 = r19;
        r210.append(r34);     // Catch: Exception -> L294 IOException -> L296
        r210.append(r62);     // Catch: Exception -> L294 IOException -> L296
        r210.append(r34);     // Catch: Exception -> L294 IOException -> L296
        r210.append(r74);     // Catch: Exception -> L294 IOException -> L296
        r104.println(r210.toString());     // Catch: Exception -> L294 IOException -> L296
        if (r74.equals(AppEventsConstants.EVENT_PARAM_VALUE_NO) == false) goto L281;
        ArrayList<String> r211 = r282.getValue();     // Catch: Exception -> L294 IOException -> L296
        int r75 = Integer.valueOf(r25).intValue();     // Catch: Exception -> L294 IOException -> L296
        int r105 = r9 + 1;     // Catch: Exception -> L294 IOException -> L296
        String r283 = r34;
        String r332 = r62;
        if (r123.equals("") == false) goto L100;
    L154:
        String r39 = r42;
        int r38 = r9;
        String r92 = r132;
        String r133 = r152;
        String r153 = r18;
        String r43 = r21;
        String r422 = r11;
        String[] r35 = r5;
        String r52 = r142;
        if (r123.equals("") == false) goto L157;
    L203:
        String r212 = r422;
        String r37 = r133;
        String[] r213 = r35;
        r213[r105].split(r92);     // Catch: Exception -> L294 IOException -> L296
        int r36 = r38 - 3;
        if (r36 < 0) goto L212;
        String r192 = r123;
        if (r213[r36].contains(r52) == false) goto L208;
        int r352 = r36;
        String r182 = ShareConstants.WEB_DIALOG_PARAM_DATA;
        this.legendList.add(new Bible("", typeofslah(r153)));     // Catch: Exception -> L294 IOException -> L296
    L215:
        if (r38 < (r213.length - 2)) goto L217;
    L249:
        String r63 = r92;
        int r382 = r38;
    L250:
        String r106 = r153;
    L251:
        Iterator<String> r310 = r211.iterator();     // Catch: Exception -> L294 IOException -> L296
        int r124 = r262;
        String r134 = r272;
        int r112 = r29;
        int r76 = 0;
    L253:
        if (r310.hasNext() == false) goto L280;
        String r143 = r310.next();     // Catch: Exception -> L294 IOException -> L296
        String r154 = r192;
        if (r154.equals("") == true) goto L269;
        Iterator<String> r183 = r310;
        String r311 = r37;
        if (r311.equals("") == true) goto L268;
        if (r76 < r124) goto L266;
        String r193 = r43;
        PrintStream r44 = System.out;     // Catch: Exception -> L294 IOException -> L296
        String r362 = r63;
        StringBuilder r64 = new StringBuilder();     // Catch: Exception -> L294 IOException -> L296
        String r40 = r106;
        String r107 = r16;
        r64.append(r107);     // Catch: Exception -> L294 IOException -> L296
        r64.append(r124);     // Catch: Exception -> L294 IOException -> L296
        r44.println(r64.toString());     // Catch: Exception -> L294 IOException -> L296
        System.out.println(r107 + r124);     // Catch: Exception -> L294 IOException -> L296
        String r47 = r134 + r143 + "<br>";     // Catch: Exception -> L294 IOException -> L296
        int r65 = r31;
        if (r124 > r65) goto L264;
        r124 = r124 + 1;     // Catch: Exception -> L294 IOException -> L296
        String r162 = r47;
        this.legendList.add(new Bible(String.valueOf(r124), r143));     // Catch: Exception -> L294 IOException -> L296
    L265:
        r134 = r162;
    L267:
        r76 = r76 + 1;     // Catch: Exception -> L294 IOException -> L296
        r37 = r311;
    L279:
        r31 = r65;
        r16 = r107;
        r310 = r183;
        r43 = r193;
        r63 = r362;
        r106 = r40;
        r192 = r154;
        goto L253
    L264:
        r162 = r47;
        goto L265
    L266:
        r193 = r43;
        r362 = r63;
        r40 = r106;
        r107 = r16;
        r65 = r31;
        goto L267
    L268:
        r193 = r43;
        r362 = r63;
        r40 = r106;
        r107 = r16;
        r65 = r31;
    L271:
        if (r154.equals("") == false) goto L273;
    L278:
        r37 = r311;
        r134 = r134 + r143 + "<br>";     // Catch: Exception -> L294 IOException -> L296
        this.legendList.add(new Bible(String.valueOf(r223), r143));     // Catch: Exception -> L294 IOException -> L296
        r223 = r223 + 1;     // Catch: Exception -> L294 IOException -> L296
        goto L279
    L273:
        if (r311.equals("") == false) goto L278;
        if (r124 != r112) goto L276;
        r37 = r311;
        this.legendList.add(new Bible(String.valueOf(r124 + 1), r143));     // Catch: Exception -> L294 IOException -> L296
    L277:
        r112 = r112 + 1;     // Catch: Exception -> L294 IOException -> L296
        goto L279
    L276:
        r37 = r311;
        goto L277
    L269:
        r183 = r310;
        r193 = r43;
        r362 = r63;
        r40 = r106;
        r107 = r16;
        r65 = r31;
        r311 = r37;
        goto L271
    L280:
        String r363 = r63;
        String r402 = r106;
        String r108 = r16;
        String r155 = r192;
        int r66 = r31;
        String r194 = r43;
        r29 = r112;
        r262 = r124;
        r272 = r134;
    L282:
        r142 = r52;
        r16 = r108;
        r123 = r155;
        r11 = r212;
        r103 = r29;
        r73 = r30;
        r33 = r322;
        r132 = r363;
        r152 = r37;
        r9 = r382;
        r42 = r39;
        r18 = r402;
        r5 = r213;
        r27 = r66;
        r21 = r194;
        r19 = r283;
        r62 = r332;
        goto L93
    L217:
        if (r213[r38].contains(r52) == true) goto L249;
        String[] r312 = r213[r105].split(r92);     // Catch: Exception -> L294 IOException -> L296
        if (r312[0].equals(r153) == false) goto L240;
        String r135 = r312[1];     // Catch: Exception -> L294 IOException -> L296
        String r136 = r312[2];     // Catch: Exception -> L294 IOException -> L296
        r382 = r38;
        System.out.println(r39 + r153);     // Catch: Exception -> L294 IOException -> L296
        if (r312.length != 4) goto L223;
        String r109 = r312[3];     // Catch: Exception -> L294 IOException -> L296
    L224:
        r39 = r39;
        if (r312.length != 5) goto L227;
        r109 = r312[3];     // Catch: Exception -> L294 IOException -> L296
        String r313 = r312[4];     // Catch: Exception -> L294 IOException -> L296
    L229:
        if (r109.equals("") == false) goto L231;
    L233:
        String r41 = r52;
        String r403 = r153;
        if (r109.equals("") == false) goto L236;
    L238:
        this.legendList.add(new Bible("", this.books[Integer.valueOf(r322).intValue()] + r43 + r75 + " , " + r136));     // Catch: Exception -> L294 IOException -> L296
    L239:
        r63 = r92;
        r106 = r403;
        r52 = r41;
        goto L251
    L236:
        if (r313.equals("") == false) goto L238;
        this.legendList.add(new Bible("", this.books[Integer.valueOf(r322).intValue()] + r43 + r75 + " , " + r136 + " : " + r109));     // Catch: Exception -> L294 IOException -> L296
        goto L239
    L231:
        if (r313.equals("") == true) goto L233;
        List<Bible> r93 = this.legendList;     // Catch: Exception -> L294 IOException -> L296
        r403 = r153;
        StringBuilder r156 = new StringBuilder();     // Catch: Exception -> L294 IOException -> L296
        r41 = r52;
        r156.append(this.books[Integer.valueOf(r322).intValue()]);     // Catch: Exception -> L294 IOException -> L296
        r156.append(r43);     // Catch: Exception -> L294 IOException -> L296
        r156.append(r75);     // Catch: Exception -> L294 IOException -> L296
        r156.append(" , ");     // Catch: Exception -> L294 IOException -> L296
        r156.append(r136);     // Catch: Exception -> L294 IOException -> L296
        r156.append(" : ");     // Catch: Exception -> L294 IOException -> L296
        r156.append(r313);     // Catch: Exception -> L294 IOException -> L296
        r156.append(" - ");     // Catch: Exception -> L294 IOException -> L296
        r156.append(r109);     // Catch: Exception -> L294 IOException -> L296
        r93.add(new Bible("", r156.toString()));     // Catch: Exception -> L294 IOException -> L296
        goto L239
    L227:
        r313 = "";
        goto L229
    L223:
        r109 = "";
        goto L224
    L240:
        r382 = r38;
        if (r352 < 0) goto L248;
        r52 = r52;
        if (r213[r352].contains(r52) == true) goto L247;
        System.out.println(r182 + r213[r352]);     // Catch: Exception -> L294 IOException -> L296
        r63 = r92;
        r106 = r153;
        if (r213[r352].split(r63)[0].equals(r106) == true) goto L251;
        this.legendList.add(new Bible("", this.books[Integer.valueOf(r322).intValue()] + r43 + r75));     // Catch: Exception -> L294 IOException -> L296
        goto L251
    L247:
        r63 = r92;
        r106 = r153;
        this.legendList.add(new Bible("", this.books[Integer.valueOf(r322).intValue()] + r43 + r75));     // Catch: Exception -> L294 IOException -> L296
        goto L251
    L248:
        r63 = r92;
        r106 = r153;
        r52 = r52;
        this.legendList.add(new Bible("", typeofslah(r106)));     // Catch: Exception -> L294 IOException -> L296
        goto L251
    L208:
        if (r213[r36].split(r92)[0].equals(r153) == true) goto L210;
        List<Bible> r125 = this.legendList;     // Catch: Exception -> L294 IOException -> L296
        r182 = ShareConstants.WEB_DIALOG_PARAM_DATA;
        r352 = r36;
        r125.add(new Bible("", typeofslah(r153)));     // Catch: Exception -> L294 IOException -> L296
        goto L215
    L210:
        r352 = r36;
    L213:
        r182 = ShareConstants.WEB_DIALOG_PARAM_DATA;
        goto L215
    L212:
        r352 = r36;
        r192 = r123;
        goto L213
    L157:
        if (r133.equals("") == false) goto L203;
        r35[r105].split(r92);     // Catch: Exception -> L294 IOException -> L296
        int r113 = r38 - 3;
        if (r113 < 0) goto L167;
        r212 = r422;
        if (r35[r113].contains(r52) == false) goto L163;
        String r184 = ShareConstants.WEB_DIALOG_PARAM_DATA;
        r37 = r133;
        this.legendList.add(new Bible("", typeofslah(r153)));     // Catch: Exception -> L294 IOException -> L296
    L168:
        r213 = r35;
        if (r38 >= (r213.length - 2)) goto L201;
        String[] r314 = r213[r105].split(r92);     // Catch: Exception -> L294 IOException -> L296
        r382 = r38;
        if (r314[0].equals(r153) == false) goto L193;
        String r114 = r314[1];     // Catch: Exception -> L294 IOException -> L296
        String r115 = r314[2];     // Catch: Exception -> L294 IOException -> L296
        System.out.println(r39 + r153);     // Catch: Exception -> L294 IOException -> L296
        if (r314.length != 4) goto L175;
        String r1010 = r314[3];     // Catch: Exception -> L294 IOException -> L296
    L176:
        String r185 = r1010;
        if (r314.length != 5) goto L179;
        String r1011 = r314[3];     // Catch: Exception -> L294 IOException -> L296
        String r315 = r314[4];     // Catch: Exception -> L294 IOException -> L296
    L181:
        if (r1011.equals("") == false) goto L183;
    L185:
        String r353 = r52;
        r39 = r39;
        String r364 = r153;
        if (r1011.equals("") == false) goto L188;
    L190:
        this.legendList.add(new Bible("", this.books[Integer.valueOf(r322).intValue()] + r43 + r75 + " : " + r123 + ", " + r115));     // Catch: Exception -> L294 IOException -> L296
    L191:
        r92 = r92;
        r52 = r353;
        r153 = r364;
    L202:
        r63 = r92;
        r192 = r123;
        goto L250
    L188:
        if (r315.equals("") == false) goto L190;
        this.legendList.add(new Bible("", this.books[Integer.valueOf(r322).intValue()] + r43 + r75 + " : " + r123 + " , " + r115 + " : " + r1011));     // Catch: Exception -> L294 IOException -> L296
        goto L191
    L183:
        if (r315.equals("") == true) goto L185;
        List<Bible> r137 = this.legendList;     // Catch: Exception -> L294 IOException -> L296
        r39 = r39;
        r364 = r153;
        StringBuilder r157 = new StringBuilder();     // Catch: Exception -> L294 IOException -> L296
        r353 = r52;
        r157.append(this.books[Integer.valueOf(r322).intValue()]);     // Catch: Exception -> L294 IOException -> L296
        r157.append(r43);     // Catch: Exception -> L294 IOException -> L296
        r157.append(r75);     // Catch: Exception -> L294 IOException -> L296
        r157.append(" : ");     // Catch: Exception -> L294 IOException -> L296
        r157.append(r123);     // Catch: Exception -> L294 IOException -> L296
        r157.append(" , ");     // Catch: Exception -> L294 IOException -> L296
        r157.append(r115);     // Catch: Exception -> L294 IOException -> L296
        r157.append(" : ");     // Catch: Exception -> L294 IOException -> L296
        r157.append(r315);     // Catch: Exception -> L294 IOException -> L296
        r157.append(" -");     // Catch: Exception -> L294 IOException -> L296
        r157.append(r1011);     // Catch: Exception -> L294 IOException -> L296
        r137.add(new Bible("", r157.toString()));     // Catch: Exception -> L294 IOException -> L296
        goto L191
    L179:
        r315 = "";
        r1011 = r185;
        goto L181
    L175:
        r1010 = "";
        goto L176
    L193:
        if (r113 < 0) goto L200;
        r52 = r52;
        if (r213[r113].contains(r52) == true) goto L199;
        System.out.println(r184 + r213[r113]);     // Catch: Exception -> L294 IOException -> L296
        r92 = r92;
        r153 = r153;
        if (r213[r113].split(r92)[0].equals(r153) == true) goto L202;
        this.legendList.add(new Bible("", this.books[Integer.valueOf(r322).intValue()] + r43 + r75 + " : " + r123));     // Catch: Exception -> L294 IOException -> L296
        goto L202
    L199:
        r92 = r92;
        r153 = r153;
        this.legendList.add(new Bible("", this.books[Integer.valueOf(r322).intValue()] + r43 + r75 + " : " + r123));     // Catch: Exception -> L294 IOException -> L296
        goto L202
    L200:
        r92 = r92;
        r52 = r52;
        r153 = r153;
        this.legendList.add(new Bible("", typeofslah(r153)));     // Catch: Exception -> L294 IOException -> L296
        goto L202
    L201:
        r382 = r38;
        goto L202
    L163:
        if (r35[r113].split(r92)[0].equals(r153) == true) goto L165;
        List<Bible> r214 = this.legendList;     // Catch: Exception -> L294 IOException -> L296
        r37 = r133;
        r184 = ShareConstants.WEB_DIALOG_PARAM_DATA;
        r214.add(new Bible("", typeofslah(r153)));     // Catch: Exception -> L294 IOException -> L296
        goto L168
    L165:
        r184 = ShareConstants.WEB_DIALOG_PARAM_DATA;
        r37 = r133;
        goto L168
    L167:
        r212 = r422;
        r184 = ShareConstants.WEB_DIALOG_PARAM_DATA;
        r37 = r133;
        this.legendList.add(new Bible("", typeofslah(r153)));     // Catch: Exception -> L294 IOException -> L296
        goto L168
    L100:
        if (r152.equals("") == true) goto L154;
        r192 = r123;
        if (r9 >= (r5.length - 1)) goto L151;
        PrintStream r126 = System.out;     // Catch: Exception -> L294 IOException -> L296
        StringBuilder r215 = new StringBuilder();     // Catch: Exception -> L294 IOException -> L296
        r215.append(ShareConstants.WEB_DIALOG_PARAM_DATA);     // Catch: Exception -> L294 IOException -> L296
        String r372 = r152;
        r215.append(r5.length);     // Catch: Exception -> L294 IOException -> L296
        r215.append("data2:");     // Catch: Exception -> L294 IOException -> L296
        r215.append(r105);     // Catch: Exception -> L294 IOException -> L296
        r215.append("xml:");     // Catch: Exception -> L294 IOException -> L296
        r215.append(r5[r105]);     // Catch: Exception -> L294 IOException -> L296
        r126.println(r215.toString());     // Catch: Exception -> L294 IOException -> L296
        String[] r216 = r5[r105].split(r132);     // Catch: Exception -> L294 IOException -> L296
        int r1012 = r9 - 3;
        if (r1012 >= 0) goto L106;
        r382 = r9;
        String r127 = r18;
        String r186 = r132;
    L114:
        if (r216[0].equals(r127) == true) goto L116;
    L137:
        r39 = r42;
        String[] r354 = r5;
        r43 = r21;
        String r138 = r372;
        r212 = r11;
        String r365 = r127;
        String r128 = r192;
        String r195 = r142;
        if (r1012 < 0) goto L149;
        r52 = r195;
        if (r354[r1012].contains(r52) == true) goto L148;
        System.out.println(ShareConstants.WEB_DIALOG_PARAM_DATA + r354[r1012]);     // Catch: Exception -> L294 IOException -> L296
        String r94 = r186;
        String[] r217 = r354[r1012].split(r94);     // Catch: Exception -> L294 IOException -> L296
        String r158 = r365;
        if (r217[0].equals(r158) == true) goto L145;
        this.legendList.add(new Bible("", this.books[Integer.valueOf(r322).intValue()] + r43 + r75 + " : " + r138 + " - " + r128));     // Catch: Exception -> L294 IOException -> L296
    L150:
        r63 = r94;
        r192 = r128;
        r37 = r138;
        r106 = r158;
    L152:
        r213 = r354;
        goto L251
    L145:
        if (r217[1].equals(r212) == true) goto L147;
        List<Bible> r218 = this.legendList;     // Catch: Exception -> L294 IOException -> L296
        StringBuilder r144 = new StringBuilder();     // Catch: Exception -> L294 IOException -> L296
        r212 = r212;
        r144.append(this.books[Integer.valueOf(r322).intValue()]);     // Catch: Exception -> L294 IOException -> L296
        r144.append(r43);     // Catch: Exception -> L294 IOException -> L296
        r144.append(r75);     // Catch: Exception -> L294 IOException -> L296
        r144.append(" : ");     // Catch: Exception -> L294 IOException -> L296
        r144.append(r138);     // Catch: Exception -> L294 IOException -> L296
        r144.append(" - ");     // Catch: Exception -> L294 IOException -> L296
        r144.append(r128);     // Catch: Exception -> L294 IOException -> L296
        r218.add(new Bible("", r144.toString()));     // Catch: Exception -> L294 IOException -> L296
        goto L150
    L147:
        r212 = r212;
        goto L150
    L148:
        r94 = r186;
        r158 = r365;
        this.legendList.add(new Bible("", this.books[Integer.valueOf(r322).intValue()] + r43 + r75 + " : " + r138 + " - " + r128));     // Catch: Exception -> L294 IOException -> L296
        goto L150
    L149:
        r94 = r186;
        r52 = r195;
        r158 = r365;
        this.legendList.add(new Bible("", typeofslah(r158)));     // Catch: Exception -> L294 IOException -> L296
        this.legendList.add(new Bible("", this.books[Integer.valueOf(r322).intValue()] + r43 + r75 + " : " + r138 + " - " + r128));     // Catch: Exception -> L294 IOException -> L296
        goto L150
    L116:
        if (r216[1].equals(r11) == false) goto L137;
        String r316 = r216[1];     // Catch: Exception -> L294 IOException -> L296
        String r95 = r216[2];     // Catch: Exception -> L294 IOException -> L296
        System.out.println(r42 + r127);     // Catch: Exception -> L294 IOException -> L296
        if (r216.length != 4) goto L120;
        String r317 = r216[3];     // Catch: Exception -> L294 IOException -> L296
    L122:
        if (r216.length != 5) goto L124;
        r317 = r216[3];     // Catch: Exception -> L294 IOException -> L296
        String r219 = r216[4];     // Catch: Exception -> L294 IOException -> L296
    L126:
        if (r317.equals("") == false) goto L128;
    L130:
        r39 = r42;
        r43 = r21;
        r138 = r372;
        r212 = r11;
        String r366 = r127;
        r128 = r192;
        String r196 = r142;
        if (r317.equals("") == false) goto L133;
    L135:
        r354 = r5;
        this.legendList.add(new Bible("", this.books[Integer.valueOf(r322).intValue()] + r43 + r75 + " : " + r138 + " - " + r128 + " , " + r95));     // Catch: Exception -> L294 IOException -> L296
    L136:
        r94 = r186;
        r52 = r196;
        r158 = r366;
        goto L150
    L133:
        if (r219.equals("") == false) goto L135;
        List<Bible> r220 = this.legendList;     // Catch: Exception -> L294 IOException -> L296
        StringBuilder r159 = new StringBuilder();     // Catch: Exception -> L294 IOException -> L296
        r354 = r5;
        r159.append(this.books[Integer.valueOf(r322).intValue()]);     // Catch: Exception -> L294 IOException -> L296
        r159.append(r43);     // Catch: Exception -> L294 IOException -> L296
        r159.append(r75);     // Catch: Exception -> L294 IOException -> L296
        r159.append(" : ");     // Catch: Exception -> L294 IOException -> L296
        r159.append(r138);     // Catch: Exception -> L294 IOException -> L296
        r159.append(" - ");     // Catch: Exception -> L294 IOException -> L296
        r159.append(r128);     // Catch: Exception -> L294 IOException -> L296
        r159.append(" , ");     // Catch: Exception -> L294 IOException -> L296
        r159.append(r95);     // Catch: Exception -> L294 IOException -> L296
        r159.append(" : ");     // Catch: Exception -> L294 IOException -> L296
        r159.append(r317);     // Catch: Exception -> L294 IOException -> L296
        r220.add(new Bible("", r159.toString()));     // Catch: Exception -> L294 IOException -> L296
        goto L136
    L128:
        if (r219.equals("") == true) goto L130;
        List<Bible> r1013 = this.legendList;     // Catch: Exception -> L294 IOException -> L296
        StringBuilder r1510 = new StringBuilder();     // Catch: Exception -> L294 IOException -> L296
        r39 = r42;
        r1510.append(this.books[Integer.valueOf(r322).intValue()]);     // Catch: Exception -> L294 IOException -> L296
        r43 = r21;
        r1510.append(r43);     // Catch: Exception -> L294 IOException -> L296
        r1510.append(r75);     // Catch: Exception -> L294 IOException -> L296
        r1510.append(" : ");     // Catch: Exception -> L294 IOException -> L296
        r1510.append(r372);     // Catch: Exception -> L294 IOException -> L296
        r212 = r11;
        r1510.append(" - ");     // Catch: Exception -> L294 IOException -> L296
        r366 = r127;
        r128 = r192;
        r1510.append(r128);     // Catch: Exception -> L294 IOException -> L296
        r196 = r142;
        r1510.append(" , ");     // Catch: Exception -> L294 IOException -> L296
        r1510.append(r95);     // Catch: Exception -> L294 IOException -> L296
        r1510.append(" : ");     // Catch: Exception -> L294 IOException -> L296
        r1510.append(r219);     // Catch: Exception -> L294 IOException -> L296
        r1510.append(" - ");     // Catch: Exception -> L294 IOException -> L296
        r1510.append(r317);     // Catch: Exception -> L294 IOException -> L296
        r1013.add(new Bible("", r1510.toString()));     // Catch: Exception -> L294 IOException -> L296
        r354 = r5;
        r138 = r372;
        goto L136
    L124:
        r219 = "";
        goto L126
    L120:
        r317 = "";
        goto L122
    L106:
        if (r5[r1012].contains(r142) == true) goto L111;
        PrintStream r129 = System.out;     // Catch: Exception -> L294 IOException -> L296
        StringBuilder r1511 = new StringBuilder();     // Catch: Exception -> L294 IOException -> L296
        r1511.append(ShareConstants.WEB_DIALOG_PARAM_DATA);     // Catch: Exception -> L294 IOException -> L296
        r382 = r9;
        r1511.append(r5[r1012]);     // Catch: Exception -> L294 IOException -> L296
        r129.println(r1511.toString());     // Catch: Exception -> L294 IOException -> L296
        String[] r96 = r5[r1012].split(r132);     // Catch: Exception -> L294 IOException -> L296
        r127 = r18;
        if (r96[0].equals(r127) == true) goto L110;
        r186 = r132;
        this.legendList.add(new Bible("", typeofslah(r127)));     // Catch: Exception -> L294 IOException -> L296
        goto L114
    L110:
        r186 = r132;
        r96[1].equals(r11);     // Catch: Exception -> L294 IOException -> L296
        goto L114
    L111:
        r382 = r9;
        r127 = r18;
        r186 = r132;
        this.legendList.add(new Bible("", typeofslah(r127)));     // Catch: Exception -> L294 IOException -> L296
        goto L114
    L151:
        r39 = r42;
        r354 = r5;
        r382 = r9;
        r43 = r21;
        r212 = r11;
        r63 = r132;
        r52 = r142;
        r37 = r152;
        r106 = r18;
        goto L152
    L281:
        r283 = r34;
        r39 = r42;
        r213 = r5;
        r332 = r62;
        r382 = r9;
        r363 = r132;
        r52 = r142;
        r37 = r152;
        r108 = r16;
        r402 = r18;
        r194 = r21;
        r66 = r31;
        r212 = r11;
        r155 = r123;
        goto L282
    L283:
        r23 = r5;
        int r383 = r9;
        String r2110 = r11;
        String r53 = r142;
        String r373 = r152;
        r102 = r16;
        String r404 = r18;
        r28 = r19;
        String r1512 = r123;
    L286:
        if (r23[r383].equals(r53) == false) goto L288;
    L291:
        r11 = "";
        r12 = r11;
        r13 = r12;
        r14 = r13;
        r242 = 0;
    L292:
        r9 = r383;
        r15 = r404;
        goto L20
    L288:
        if (r23[r383].contains(r53) == true) goto L291;
        r13 = r1512;
        r11 = r2110;
        r12 = r25;
        r14 = r373;
    L86:
        if (r123.equals("") == false) goto L88;
    L90:
        r27 = 0;
        r222 = 0;
        goto L91
    L88:
        if (r152.equals("") == false) goto L90;
        r222 = Integer.valueOf(r123).intValue() - 1;
        r27 = 0;
        goto L91
    L84:
        r152 = r22;
    L284:
        r23 = r5;
        r383 = r9;
        r2110 = r11;
        r53 = "+++";
        r404 = r18;
        r28 = r19;
        r373 = r22;
        r1512 = r24;
        r242 = r10;
        r102 = r16;
        goto L286
    L75:
        r26 = r122;
        goto L77
    L76:
        r20 = r6;
        r26 = false;
        goto L77
    L25:
        if (r5[r9].equals("+") == true) goto L71;
        if (r5[r9].equals("+++") == true) goto L71;
        if (r5[r9].contains("+++") == true) goto L71;
        if (r5[r9].length() < 2) goto L71;
        if (r5[r9].contains("==") == true) goto L35;
        String[] r318 = r5[r9].split("\\+");     // Catch: Exception -> L294 IOException -> L296
        r15 = r318[0];     // Catch: Exception -> L294 IOException -> L296
        if (r2 == r10) goto L40;
    L58:
        r15 = r318[0];     // Catch: Exception -> L294 IOException -> L296
        System.out.println("day:" + r5[r9]);     // Catch: Exception -> L294 IOException -> L296
        r11 = r318[1];     // Catch: Exception -> L294 IOException -> L296
        if (r318.length <= 2) goto L61;
        r12 = r318[2];     // Catch: Exception -> L294 IOException -> L296
    L62:
        System.out.println("type:" + r15);     // Catch: Exception -> L294 IOException -> L296
        if (r318.length != 4) goto L65;
        r13 = r318[3];     // Catch: Exception -> L294 IOException -> L296
    L67:
        if (r318.length != 5) goto L69;
        r13 = r318[3];     // Catch: Exception -> L294 IOException -> L296
        r14 = r318[4];     // Catch: Exception -> L294 IOException -> L296
        r23 = r5;
        r20 = r6;
    L70:
        r242 = r10;
        r102 = r16;
        goto L19
    L69:
        r23 = r5;
        r20 = r6;
        r14 = "";
        goto L70
    L65:
        r13 = "";
        goto L67
    L61:
        r12 = "";
        goto L62
    L40:
        if (r15.equals("Mm") == false) goto L42;
    L57:
        Log.d(r6, "inside" + r15);     // Catch: Exception -> L294 IOException -> L296
        Log.d(r6, "soomday" + r2 + " day=" + r10);     // Catch: Exception -> L294 IOException -> L296
        loadmazmor(r5[r9], 1);     // Catch: Exception -> L294 IOException -> L296
        r9 = r9 + 1;     // Catch: Exception -> L294 IOException -> L296
        r23 = r5;
        r20 = r6;
        goto L36
    L42:
        if (r15.equals("MM") == true) goto L57;
        if (r15.equals("mm") == true) goto L57;
        if (r15.equals("m") == true) goto L57;
        if (r15.equals("M") == true) goto L57;
        if (r15.contains("Mb") == true) goto L57;
        if (r15.contains("mb") == true) goto L57;
        if (r15.contains("Ma") == true) goto L57;
        if (r15.contains("ma") == false) goto L58;
    L298:
        getDynamicData();
        return;
    L296:
        e = move-exception;
        e.printStackTrace();
    L294:
        e = move-exception;
        e.printStackTrace();
        goto L298
    }

    private void loadmazmor(String r11, int r12) {
        String r2 = r11.split("\\+")[0];
        if (r12 != 0) goto L5;
        String r122 = new String(app.decodeFile2(this.activity, "orsozoxi", "katamars/mzamerSoom.orso"));     // Catch: Exception -> L30 IOException -> L32
    L6:
        String[] r123 = r122.split("\n");     // Catch: Exception -> L30 IOException -> L32
        r11.split("\\+");     // Catch: Exception -> L30 IOException -> L32
        int r1 = 0;
        boolean r4 = false;
    L7:
        if (r1 >= r123.length) goto L44;
        if (r123[r1].toLowerCase().equals(r11.toLowerCase()) == false) goto L14;
        Log.d("orso2", "line" + r11);     // Catch: Exception -> L30 IOException -> L32
        Log.d("orso2", "xml" + r123[r1]);     // Catch: Exception -> L30 IOException -> L32
        r4 = true;
    L29:
        r1 = r1 + 1;
        goto L7
    L14:
        if (r4 == false) goto L21;
        if (r123[r1].contains("@") == false) goto L21;
        if (r123[r1].contains("@@") == true) goto L21;
        this.legendList.add(new Bible("", typeofslah(r2)));     // Catch: Exception -> L30 IOException -> L32
        this.legendList.add(new Bible("", r123[r1].replace("@", "")));     // Catch: Exception -> L30 IOException -> L32
    L21:
        if (r123[r1].contains("@@") == false) goto L23;
    L28:
        r4 = false;
        goto L29
    L23:
        if (r123[r1].equals("@") == true) goto L28;
        if (r4 == false) goto L29;
        this.legendList.add(new Bible("", r123[r1]));     // Catch: Exception -> L30 IOException -> L32
        goto L29
    L44:
        return;
    L5:
        r122 = new String(app.decodeFile2(this.activity, "orsozoxi", "katamars/mzamer7maseen.orso"));     // Catch: Exception -> L30 IOException -> L32
    L32:
        e = move-exception;
        e.printStackTrace();
        return;
    L30:
        e = move-exception;
        e.printStackTrace();
    }

    public void loadinmapp(String r4, int r5, int r6) {
        System.out.println("beshooooooooo" + r4);
        this.levelsMap.clear();
        this.levels.clear();
        this.levelsMap = new HashMap();
        this.levels = new ArrayList();
        String[] r42 = updatelistOld(r5, r6);
        int r52 = 0;
    L4:
        if (r52 >= r42.length) goto L6;
        this.levels.add(r42[r52]);
        r52 = r52 + 1;
        goto L4
    L6:
        this.levelsMap.put(AppEventsConstants.EVENT_PARAM_VALUE_NO, new ArrayList(this.levels));
        this.levels.clear();
    }

    public String[] updatelist(int r3, int r4) {
        SQLiteDatabase.loadLibs(this.activity);     // Catch: SQLException -> L10
        new UseDatabaseHandler(this.activity);     // Catch: SQLException -> L10
        UseDatabaseHandler r0 = UseDatabaseHandler.getInstance(this.activity);     // Catch: SQLException -> L10
        r0.open(app.getSqKey(this.activity));     // Catch: SQLException -> L10
        List<BibleDataBase> r32 = r0.getChapter(r3, r4);     // Catch: SQLException -> L10
        r0.close();     // Catch: SQLException -> L10
        if (r32 == null) goto L17;
        String[] r42 = new String[r32.size()];     // Catch: SQLException -> L10
        int r02 = 0;
    L5:
        if (r02 >= r32.size()) goto L9;
        r42[r02] = r32.get(r02).getAyah();     // Catch: SQLException -> L10
        r02 = r02 + 1;
        goto L5
    L9:
        return r42;
    L17:
        return null;
    L10:
        e = move-exception;
        e.printStackTrace();
        return null;
    }

    public String[] updatelistOld(int r7, int r8) {
        AssetManager r0 = this.activity.getAssets();
        int r82 = r8 + 1;
        InputStream r2 = null;
        String r3 = "";
        if (r7 != 0) goto L7;
        r3 = "mybible/old/01-Takwen/" + r82 + ".txt";     // Catch: Throwable -> L231 IOException -> L233
    L142:
        if (r7 != 46) goto L145;
        r3 = "mybible/new/47-Matta/" + r82 + ".txt";     // Catch: Throwable -> L231 IOException -> L233
    L222:
        InputStream r72 = r0.open(r3);     // Catch: Throwable -> L231 IOException -> L233
        String[] r83 = loadTextFile(r72).split("\n");     // Catch: Throwable -> L227 IOException -> L229
        if (r72 != null) goto L249;
    L226:
        return r83;
    L249:
        r72.close();     // Catch: IOException -> L244
        goto L226
    L229:
        e = move-exception;
        InputStream r84 = r72;
        e = e;
    L253:
        e.printStackTrace();     // Catch: Throwable -> L239
        if (r84 != null) goto L247;
    L238:
        return null;
    L247:
        r84.close();     // Catch: IOException -> L246
    L239:
        th = th;
        r2 = r84;
    L241:
        if (r2 != null) goto L251;
    L243:
        throw th;
    L251:
        r2.close();     // Catch: IOException -> L245
    L227:
        th = move-exception;
        r2 = r72;
        th = th;
        goto L241
    L145:
        if (r7 != 47) goto L148;
        r3 = "mybible/new/48-Morkos/" + r82 + ".txt";     // Catch: Throwable -> L231 IOException -> L233
        goto L222
    L148:
        if (r7 != 48) goto L151;
        r3 = "mybible/new/49-Loka/" + r82 + ".txt";     // Catch: Throwable -> L231 IOException -> L233
        goto L222
    L151:
        if (r7 != 49) goto L154;
        r3 = "mybible/new/50-Yo7ana/" + r82 + ".txt";     // Catch: Throwable -> L231 IOException -> L233
        goto L222
    L154:
        if (r7 != 50) goto L157;
        r3 = "mybible/new/51-A3mal/" + r82 + ".txt";     // Catch: Throwable -> L231 IOException -> L233
        goto L222
    L157:
        if (r7 != 51) goto L160;
        r3 = "mybible/new/52-Romia/" + r82 + ".txt";     // Catch: Throwable -> L231 IOException -> L233
        goto L222
    L160:
        if (r7 != 52) goto L163;
        r3 = "mybible/new/53-Koronsos1/" + r82 + ".txt";     // Catch: Throwable -> L231 IOException -> L233
        goto L222
    L163:
        if (r7 != 53) goto L166;
        r3 = "mybible/new/54-Koronsos2/" + r82 + ".txt";     // Catch: Throwable -> L231 IOException -> L233
        goto L222
    L166:
        if (r7 != 54) goto L169;
        r3 = "mybible/new/55-Ghalatia/" + r82 + ".txt";     // Catch: Throwable -> L231 IOException -> L233
        goto L222
    L169:
        if (r7 != 55) goto L172;
        r3 = "mybible/new/56-Afasos/" + r82 + ".txt";     // Catch: Throwable -> L231 IOException -> L233
        goto L222
    L172:
        if (r7 != 56) goto L175;
        r3 = "mybible/new/57-Philipy/" + r82 + ".txt";     // Catch: Throwable -> L231 IOException -> L233
        goto L222
    L175:
        if (r7 != 57) goto L178;
        r3 = "mybible/new/58-Kolosy/" + r82 + ".txt";     // Catch: Throwable -> L231 IOException -> L233
        goto L222
    L178:
        if (r7 != 58) goto L181;
        r3 = "mybible/new/59-Tsaloneky1/" + r82 + ".txt";     // Catch: Throwable -> L231 IOException -> L233
        goto L222
    L181:
        if (r7 != 59) goto L184;
        r3 = "mybible/new/60-Tsaloneky2/" + r82 + ".txt";     // Catch: Throwable -> L231 IOException -> L233
        goto L222
    L184:
        if (r7 != 60) goto L187;
        r3 = "mybible/new/61-Timosawes1/" + r82 + ".txt";     // Catch: Throwable -> L231 IOException -> L233
        goto L222
    L187:
        if (r7 != 61) goto L190;
        r3 = "mybible/new/62-Timosawes2/" + r82 + ".txt";     // Catch: Throwable -> L231 IOException -> L233
        goto L222
    L190:
        if (r7 != 62) goto L193;
        r3 = "mybible/new/63-Titues/" + r82 + ".txt";     // Catch: Throwable -> L231 IOException -> L233
        goto L222
    L193:
        if (r7 != 63) goto L196;
        r3 = "mybible/new/64-Philimon/" + r82 + ".txt";     // Catch: Throwable -> L231 IOException -> L233
        goto L222
    L196:
        if (r7 != 64) goto L199;
        r3 = "mybible/new/65-Abranyen/" + r82 + ".txt";     // Catch: Throwable -> L231 IOException -> L233
        goto L222
    L199:
        if (r7 != 65) goto L202;
        r3 = "mybible/new/66-Yaquob/" + r82 + ".txt";     // Catch: Throwable -> L231 IOException -> L233
        goto L222
    L202:
        if (r7 != 66) goto L205;
        r3 = "mybible/new/67-Botros1/" + r82 + ".txt";     // Catch: Throwable -> L231 IOException -> L233
        goto L222
    L205:
        if (r7 != 67) goto L208;
        r3 = "mybible/new/68-Botros2/" + r82 + ".txt";     // Catch: Throwable -> L231 IOException -> L233
        goto L222
    L208:
        if (r7 != 68) goto L211;
        r3 = "mybible/new/69-You7ana1/" + r82 + ".txt";     // Catch: Throwable -> L231 IOException -> L233
        goto L222
    L211:
        if (r7 != 69) goto L214;
        r3 = "mybible/new/70-You7ana2/" + r82 + ".txt";     // Catch: Throwable -> L231 IOException -> L233
        goto L222
    L214:
        if (r7 != 70) goto L217;
        r3 = "mybible/new/71-You7ana3/" + r82 + ".txt";     // Catch: Throwable -> L231 IOException -> L233
        goto L222
    L217:
        if (r7 != 71) goto L220;
        r3 = "mybible/new/72-Yahouza/" + r82 + ".txt";     // Catch: Throwable -> L231 IOException -> L233
        goto L222
    L220:
        if (r7 != 72) goto L222;
        r3 = "mybible/new/73-RoyaYou7ana/" + r82 + ".txt";     // Catch: Throwable -> L231 IOException -> L233
        goto L222
    L7:
        if (r7 != 1) goto L10;
        r3 = "mybible/old/02-Khrog/" + r82 + ".txt";     // Catch: Throwable -> L231 IOException -> L233
        goto L142
    L10:
        if (r7 != 2) goto L13;
        r3 = "mybible/old/03-Laween/" + r82 + ".txt";     // Catch: Throwable -> L231 IOException -> L233
        goto L142
    L13:
        if (r7 != 3) goto L16;
        r3 = "mybible/old/04-Addad/" + r82 + ".txt";     // Catch: Throwable -> L231 IOException -> L233
        goto L142
    L16:
        if (r7 != 4) goto L19;
        r3 = "mybible/old/05-Tasnya/" + r82 + ".txt";     // Catch: Throwable -> L231 IOException -> L233
        goto L142
    L19:
        if (r7 != 5) goto L22;
        r3 = "mybible/old/06-Yasho3/" + r82 + ".txt";     // Catch: Throwable -> L231 IOException -> L233
        goto L142
    L22:
        if (r7 != 6) goto L25;
        r3 = "mybible/old/07-Kodah/" + r82 + ".txt";     // Catch: Throwable -> L231 IOException -> L233
        goto L142
    L25:
        if (r7 != 7) goto L28;
        r3 = "mybible/old/08-Ra3os/" + r82 + ".txt";     // Catch: Throwable -> L231 IOException -> L233
        goto L142
    L28:
        if (r7 != 8) goto L31;
        r3 = "mybible/old/09-Samuel1/" + r82 + ".txt";     // Catch: Throwable -> L231 IOException -> L233
        goto L142
    L31:
        if (r7 != 9) goto L34;
        r3 = "mybible/old/10-Samuel2/" + r82 + ".txt";     // Catch: Throwable -> L231 IOException -> L233
        goto L142
    L34:
        if (r7 != 10) goto L37;
        r3 = "mybible/old/11-Mlokk1/" + r82 + ".txt";     // Catch: Throwable -> L231 IOException -> L233
        goto L142
    L37:
        if (r7 != 11) goto L40;
        r3 = "mybible/old/12-Mlokk2/" + r82 + ".txt";     // Catch: Throwable -> L231 IOException -> L233
        goto L142
    L40:
        if (r7 != 12) goto L43;
        r3 = "mybible/old/13-AkhbarAyam1/" + r82 + ".txt";     // Catch: Throwable -> L231 IOException -> L233
        goto L142
    L43:
        if (r7 != 13) goto L46;
        r3 = "mybible/old/14-AkhbarAyam2/" + r82 + ".txt";     // Catch: Throwable -> L231 IOException -> L233
        goto L142
    L46:
        if (r7 != 14) goto L49;
        r3 = "mybible/old/15-Ezra/" + r82 + ".txt";     // Catch: Throwable -> L231 IOException -> L233
        goto L142
    L49:
        if (r7 != 15) goto L52;
        r3 = "mybible/old/16-Na7amya/" + r82 + ".txt";     // Catch: Throwable -> L231 IOException -> L233
        goto L142
    L52:
        if (r7 != 16) goto L55;
        r3 = "mybible/old/17-Tobya/" + r82 + ".txt";     // Catch: Throwable -> L231 IOException -> L233
        goto L142
    L55:
        if (r7 != 17) goto L58;
        r3 = "mybible/old/18-Yahodet/" + r82 + ".txt";     // Catch: Throwable -> L231 IOException -> L233
        goto L142
    L58:
        if (r7 != 18) goto L61;
        r3 = "mybible/old/19-Aster/" + r82 + ".txt";     // Catch: Throwable -> L231 IOException -> L233
        goto L142
    L61:
        if (r7 != 19) goto L64;
        r3 = "mybible/old/20-Ayob/" + r82 + ".txt";     // Catch: Throwable -> L231 IOException -> L233
        goto L142
    L64:
        if (r7 != 20) goto L67;
        r3 = "mybible/old/21-Psalms/" + r82 + ".txt";     // Catch: Throwable -> L231 IOException -> L233
        goto L142
    L67:
        if (r7 != 21) goto L70;
        r3 = "mybible/old/22-Amsal/" + r82 + ".txt";     // Catch: Throwable -> L231 IOException -> L233
        goto L142
    L70:
        if (r7 != 22) goto L73;
        r3 = "mybible/old/23-Gam3a/" + r82 + ".txt";     // Catch: Throwable -> L231 IOException -> L233
        goto L142
    L73:
        if (r7 != 23) goto L76;
        r3 = "mybible/old/24-NashedElAnshad/" + r82 + ".txt";     // Catch: Throwable -> L231 IOException -> L233
        goto L142
    L76:
        if (r7 != 24) goto L79;
        r3 = "mybible/old/25-7ekma/" + r82 + ".txt";     // Catch: Throwable -> L231 IOException -> L233
        goto L142
    L79:
        if (r7 != 25) goto L82;
        r3 = "mybible/old/26-Yasho3EbnSira5/" + r82 + ".txt";     // Catch: Throwable -> L231 IOException -> L233
        goto L142
    L82:
        if (r7 != 26) goto L85;
        r3 = "mybible/old/27-Ash3ya/" + r82 + ".txt";     // Catch: Throwable -> L231 IOException -> L233
        goto L142
    L85:
        if (r7 != 27) goto L88;
        r3 = "mybible/old/28-Armya/" + r82 + ".txt";     // Catch: Throwable -> L231 IOException -> L233
        goto L142
    L88:
        if (r7 != 28) goto L91;
        r3 = "mybible/old/29-MarasyArmya/" + r82 + ".txt";     // Catch: Throwable -> L231 IOException -> L233
        goto L142
    L91:
        if (r7 != 29) goto L94;
        r3 = "mybible/old/30-Baroo5/" + r82 + ".txt";     // Catch: Throwable -> L231 IOException -> L233
        goto L142
    L94:
        if (r7 != 30) goto L97;
        r3 = "mybible/old/31-Ezkial/" + r82 + ".txt";     // Catch: Throwable -> L231 IOException -> L233
        goto L142
    L97:
        if (r7 != 31) goto L100;
        r3 = "mybible/old/32-Daniel/" + r82 + ".txt";     // Catch: Throwable -> L231 IOException -> L233
        goto L142
    L100:
        if (r7 != 32) goto L103;
        r3 = "mybible/old/33-Hosa3/" + r82 + ".txt";     // Catch: Throwable -> L231 IOException -> L233
        goto L142
    L103:
        if (r7 != 33) goto L106;
        r3 = "mybible/old/34-Uoyel/" + r82 + ".txt";     // Catch: Throwable -> L231 IOException -> L233
        goto L142
    L106:
        if (r7 != 34) goto L109;
        r3 = "mybible/old/35-Amous/" + r82 + ".txt";     // Catch: Throwable -> L231 IOException -> L233
        goto L142
    L109:
        if (r7 != 35) goto L112;
        r3 = "mybible/old/36-Obadiah/" + r82 + ".txt";     // Catch: Throwable -> L231 IOException -> L233
        goto L142
    L112:
        if (r7 != 36) goto L115;
        r3 = "mybible/old/37-Younan/" + r82 + ".txt";     // Catch: Throwable -> L231 IOException -> L233
        goto L142
    L115:
        if (r7 != 37) goto L118;
        r3 = "mybible/old/38-Mikha/" + r82 + ".txt";     // Catch: Throwable -> L231 IOException -> L233
        goto L142
    L118:
        if (r7 != 38) goto L121;
        r3 = "mybible/old/39-Na7oum/" + r82 + ".txt";     // Catch: Throwable -> L231 IOException -> L233
        goto L142
    L121:
        if (r7 != 39) goto L124;
        r3 = "mybible/old/40-Habakouk/" + r82 + ".txt";     // Catch: Throwable -> L231 IOException -> L233
        goto L142
    L124:
        if (r7 != 40) goto L127;
        r3 = "mybible/old/41-Saphnia/" + r82 + ".txt";     // Catch: Throwable -> L231 IOException -> L233
        goto L142
    L127:
        if (r7 != 41) goto L130;
        r3 = "mybible/old/42-Heggi/" + r82 + ".txt";     // Catch: Throwable -> L231 IOException -> L233
        goto L142
    L130:
        if (r7 != 42) goto L133;
        r3 = "mybible/old/43-Zakaria/" + r82 + ".txt";     // Catch: Throwable -> L231 IOException -> L233
        goto L142
    L133:
        if (r7 != 43) goto L136;
        r3 = "mybible/old/44-Malakhy/" + r82 + ".txt";     // Catch: Throwable -> L231 IOException -> L233
        goto L142
    L136:
        if (r7 != 44) goto L139;
        r3 = "mybible/old/45-Makabyeen1/" + r82 + ".txt";     // Catch: Throwable -> L231 IOException -> L233
        goto L142
    L139:
        if (r7 != 45) goto L142;
        r3 = "mybible/old/46-Makabyeen2/" + r82 + ".txt";     // Catch: Throwable -> L231 IOException -> L233
    L233:
        e = e;
        r84 = null;
    L231:
        th = th;
        goto L241
    }

    public String loadTextFile(InputStream r5) throws IOException {
        ByteArrayOutputStream r0 = new ByteArrayOutputStream();
        byte[] r1 = new byte[4096];
    L3:
        int r2 = r5.read(r1);
        if (r2 <= 0) goto L7;
        r0.write(r1, 0, r2);
        goto L3
    L7:
        return new String(r0.toByteArray(), "UTF8");
    }

    public String typeofslah(String r4) {
        if (r4.equals("ma") == true) goto L87;
        if (r4.equals("Ma") == true) goto L87;
        if (r4.equals("ba") == true) goto L85;
        if (r4.equals("Ba") == true) goto L85;
        if (r4.equals("mb") == true) goto L83;
        if (r4.equals("Mb") == true) goto L83;
        if (r4.equals("bb") == true) goto L81;
        if (r4.equals("Bb") == true) goto L81;
        if (r4.equals("bo") == true) goto L79;
        if (r4.equals("Bo") == true) goto L79;
        if (r4.equals("ka") == true) goto L77;
        if (r4.equals("Ka") == true) goto L77;
        if (r4.equals("ba") == true) goto L75;
        if (r4.equals("Ba") == true) goto L75;
        if (r4.equals("ab") == true) goto L73;
        if (r4.equals("Ab") == true) goto L73;
        if (r4.equals("m") == true) goto L71;
        if (r4.equals("M") == true) goto L71;
        if (r4.equals("Mm") == true) goto L69;
        if (r4.equals("mm") == true) goto L69;
        if (r4.equals("b") == true) goto L67;
        if (r4.equals("B") == true) goto L67;
        if (r4.equals("n") == true) goto L65;
        if (r4.equals("N") == true) goto L65;
        return "";
    L65:
        return this.n;
    L67:
        return this.b;
    L69:
        return this.mm;
    L71:
        return this.m;
    L73:
        return this.ab;
    L75:
        return this.ba;
    L77:
        return this.ka;
    L79:
        return this.bo;
    L81:
        return this.bb;
    L83:
        return this.mb;
    L85:
        return this.ba;
    L87:
        return this.ma;
    }

    private void namekiahEidName(int r2, String r3) {
        if (r3.equals("Sat") == false) goto L6;
        this.eidName = "kiak_sat_" + r2;
        return;
    L6:
        if (r3.equals("Sun") == false) goto L8;
        this.eidName = "kiak_sun_" + r2;
        return;
    L8:
        this.eidName = "kiak_ayam_" + r2;
    }
}
