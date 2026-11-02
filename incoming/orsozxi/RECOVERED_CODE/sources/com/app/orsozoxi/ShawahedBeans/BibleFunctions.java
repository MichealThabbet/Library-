package com.app.orsozoxi.ShawahedBeans;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.SpannableString;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.app.orsozoxi.BibleDataBase.BibleDataBase;
import com.app.orsozoxi.BibleDataBase.UseDatabaseHandler;
import com.app.orsozoxi.MainActivity_Bible;
import com.app.orsozoxi.Views.NoUnderlineClickableSpan;
import com.app.orsozoxi.app;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.lingala.zip4j.util.InternalZipConstants;
import net.sqlcipher.SQLException;
import net.sqlcipher.database.SQLiteDatabase;

/* loaded from: classes.dex */
public class BibleFunctions {
    public static ArrayList<String> getAs7a7AyatAllSequenceDifferent(Activity activity, int i, int i2) throws SQLException {
        int i3 = i - 1;
        int i4 = i2 - 1;
        try {
            SQLiteDatabase.loadLibs(activity);
            new UseDatabaseHandler(activity);
            UseDatabaseHandler useDatabaseHandler = UseDatabaseHandler.getInstance(activity);
            useDatabaseHandler.open(app.getSqKey(activity));
            List<BibleDataBase> chapter = useDatabaseHandler.getChapter(i3, i4);
            useDatabaseHandler.close();
            if (chapter == null) {
                return null;
            }
            ArrayList<String> arrayList = new ArrayList<>();
            for (int i5 = 0; i5 < chapter.size(); i5++) {
                arrayList.add(chapter.get(i5).getAyah().trim());
            }
            return arrayList;
        } catch (android.database.SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:256:0x07e1 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static java.util.ArrayList<java.lang.String> getAs7a7AyatAllSequenceDifferentOld(android.app.Activity r8, int r9, int r10) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 2021
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.orsozoxi.ShawahedBeans.BibleFunctions.getAs7a7AyatAllSequenceDifferentOld(android.app.Activity, int, int):java.util.ArrayList");
    }

    public static void setAyahWithShawahedText(final Activity activity, ArrayList<Shahed> arrayList, View view, String str, final TextView textView) {
        String str2 = str + " ";
        String str3 = "";
        for (int i = 0; i < arrayList.size(); i++) {
            str3 = str3 + "(" + arrayList.get(i).getFillShahed() + ") ";
        }
        String str4 = str2 + str3;
        TextView textView2 = (TextView) view;
        textView2.setText(str4);
        SpannableString spannableString = new SpannableString(str4);
        int length = str2.length();
        Iterator<Shahed> it2 = arrayList.iterator();
        while (it2.hasNext()) {
            final Shahed next = it2.next();
            Log.d("orso", "span=" + spannableString.toString());
            spannableString.setSpan(new NoUnderlineClickableSpan() { // from class: com.app.orsozoxi.ShawahedBeans.BibleFunctions.1
                @Override // com.app.orsozoxi.Views.NoUnderlineClickableSpan, android.text.style.ClickableSpan
                public void onClick(View view2) throws Throwable {
                    ArrayList<String> as7a7AyatAllSequenceDifferentOld = BibleFunctions.getAs7a7AyatAllSequenceDifferentOld(activity, next.getSafrId(), next.getAs7a7Id());
                    if (as7a7AyatAllSequenceDifferentOld == null || as7a7AyatAllSequenceDifferentOld.size() <= 0) {
                        return;
                    }
                    String str5 = "";
                    int i2 = 0;
                    if (next.getStatus() == 1) {
                        if (next.getAyat().size() < 2) {
                            str5 = "هذا الشاهد غير صحيح";
                        } else if (next.getAyat().get(1).intValue() <= as7a7AyatAllSequenceDifferentOld.size()) {
                            for (int iIntValue = next.getAyat().get(0).intValue(); iIntValue <= next.getAyat().get(1).intValue(); iIntValue++) {
                                str5 = str5 + as7a7AyatAllSequenceDifferentOld.get(iIntValue - 1) + "\n";
                            }
                        } else {
                            str5 = "هذا الشاهد غير صحيح";
                        }
                    } else if (next.getStatus() == 3) {
                        while (i2 <= as7a7AyatAllSequenceDifferentOld.size()) {
                            str5 = str5 + as7a7AyatAllSequenceDifferentOld.get(i2) + "\n";
                            i2++;
                        }
                    } else {
                        while (i2 < next.getAyat().size()) {
                            str5 = next.getAyat().get(i2).intValue() - 1 < as7a7AyatAllSequenceDifferentOld.size() ? str5 + as7a7AyatAllSequenceDifferentOld.get(next.getAyat().get(i2).intValue() - 1) + "\n" : str5 + "هذا الشاهد غير صحيح";
                            i2++;
                        }
                    }
                    TextView textView3 = textView;
                    if (textView3 != null) {
                        textView3.setText(str5);
                    }
                    new AlertDialog.Builder(activity).setTitle("الشاهد (" + next.getFillShahed() + ")\nالرابط: " + next.getWord()).setMessage(str5).setPositiveButton("اذهب للشاهد", new DialogInterface.OnClickListener() { // from class: com.app.orsozoxi.ShawahedBeans.BibleFunctions.1.2
                        @Override // android.content.DialogInterface.OnClickListener
                        public void onClick(DialogInterface dialogInterface, int i3) {
                            Intent intent = new Intent(activity, (Class<?>) MainActivity_Bible.class);
                            if (next.getSafrId() >= 47) {
                                intent.putExtra("isOld", false);
                            } else {
                                intent.putExtra("isOld", true);
                            }
                            intent.putExtra("safr", next.getSafrId());
                            intent.putExtra("as7a7", next.getAs7a7Id() - 1);
                            intent.putExtra("ayah", next.getAyat().get(0).intValue() - 1);
                            activity.startActivity(intent);
                        }
                    }).setNegativeButton("خروج", new DialogInterface.OnClickListener() { // from class: com.app.orsozoxi.ShawahedBeans.BibleFunctions.1.1
                        @Override // android.content.DialogInterface.OnClickListener
                        public void onClick(DialogInterface dialogInterface, int i3) {
                        }
                    }).show();
                }
            }, length, next.getFillShahed().length() + length + 2, 0);
            length = length + next.getFillShahed().length() + 3;
        }
        textView2.setText(spannableString);
    }

    public static String loadTextFile(InputStream inputStream) throws IOException {
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

    public static ArrayList<SafrSpecial> fillSpecialSafrs() {
        ArrayList<SafrSpecial> arrayList = new ArrayList<>();
        SafrSpecial safrSpecial = new SafrSpecial();
        safrSpecial.setSafrId(1);
        safrSpecial.setSpecial("تك");
        arrayList.add(safrSpecial);
        SafrSpecial safrSpecial2 = new SafrSpecial();
        safrSpecial2.setSafrId(2);
        safrSpecial2.setSpecial("خر");
        arrayList.add(safrSpecial2);
        SafrSpecial safrSpecial3 = new SafrSpecial();
        safrSpecial3.setSafrId(3);
        safrSpecial3.setSpecial("لا");
        arrayList.add(safrSpecial3);
        SafrSpecial safrSpecial4 = new SafrSpecial();
        safrSpecial4.setSafrId(4);
        safrSpecial4.setSpecial("عد");
        arrayList.add(safrSpecial4);
        SafrSpecial safrSpecial5 = new SafrSpecial();
        safrSpecial5.setSafrId(5);
        safrSpecial5.setSpecial("تث");
        arrayList.add(safrSpecial5);
        SafrSpecial safrSpecial6 = new SafrSpecial();
        safrSpecial6.setSafrId(6);
        safrSpecial6.setSpecial("يش");
        arrayList.add(safrSpecial6);
        SafrSpecial safrSpecial7 = new SafrSpecial();
        safrSpecial7.setSafrId(7);
        safrSpecial7.setSpecial("قض");
        arrayList.add(safrSpecial7);
        SafrSpecial safrSpecial8 = new SafrSpecial();
        safrSpecial8.setSafrId(8);
        safrSpecial8.setSpecial("را");
        arrayList.add(safrSpecial8);
        SafrSpecial safrSpecial9 = new SafrSpecial();
        safrSpecial9.setSafrId(9);
        safrSpecial9.setSpecial("1صم");
        arrayList.add(safrSpecial9);
        SafrSpecial safrSpecial10 = new SafrSpecial();
        safrSpecial10.setSafrId(10);
        safrSpecial10.setSpecial("2صم");
        arrayList.add(safrSpecial10);
        SafrSpecial safrSpecial11 = new SafrSpecial();
        safrSpecial11.setSafrId(11);
        safrSpecial11.setSpecial("1مل");
        arrayList.add(safrSpecial11);
        SafrSpecial safrSpecial12 = new SafrSpecial();
        safrSpecial12.setSafrId(12);
        safrSpecial12.setSpecial("2مل");
        arrayList.add(safrSpecial12);
        SafrSpecial safrSpecial13 = new SafrSpecial();
        safrSpecial13.setSafrId(13);
        safrSpecial13.setSpecial("1أي");
        arrayList.add(safrSpecial13);
        SafrSpecial safrSpecial14 = new SafrSpecial();
        safrSpecial14.setSafrId(14);
        safrSpecial14.setSpecial("2أي");
        arrayList.add(safrSpecial14);
        SafrSpecial safrSpecial15 = new SafrSpecial();
        safrSpecial15.setSafrId(15);
        safrSpecial15.setSpecial("عز");
        arrayList.add(safrSpecial15);
        SafrSpecial safrSpecial16 = new SafrSpecial();
        safrSpecial16.setSafrId(16);
        safrSpecial16.setSpecial("نح");
        arrayList.add(safrSpecial16);
        SafrSpecial safrSpecial17 = new SafrSpecial();
        safrSpecial17.setSafrId(17);
        safrSpecial17.setSpecial("طو");
        arrayList.add(safrSpecial17);
        SafrSpecial safrSpecial18 = new SafrSpecial();
        safrSpecial18.setSafrId(18);
        safrSpecial18.setSpecial("يهو");
        arrayList.add(safrSpecial18);
        SafrSpecial safrSpecial19 = new SafrSpecial();
        safrSpecial19.setSafrId(19);
        safrSpecial19.setSpecial("أس");
        arrayList.add(safrSpecial19);
        SafrSpecial safrSpecial20 = new SafrSpecial();
        safrSpecial20.setSafrId(20);
        safrSpecial20.setSpecial("أي");
        arrayList.add(safrSpecial20);
        SafrSpecial safrSpecial21 = new SafrSpecial();
        safrSpecial21.setSafrId(21);
        safrSpecial21.setSpecial("مز");
        arrayList.add(safrSpecial21);
        SafrSpecial safrSpecial22 = new SafrSpecial();
        safrSpecial22.setSafrId(22);
        safrSpecial22.setSpecial("أم");
        arrayList.add(safrSpecial22);
        SafrSpecial safrSpecial23 = new SafrSpecial();
        safrSpecial23.setSafrId(23);
        safrSpecial23.setSpecial("جا");
        arrayList.add(safrSpecial23);
        SafrSpecial safrSpecial24 = new SafrSpecial();
        safrSpecial24.setSafrId(24);
        safrSpecial24.setSpecial("نش");
        arrayList.add(safrSpecial24);
        SafrSpecial safrSpecial25 = new SafrSpecial();
        safrSpecial25.setSafrId(25);
        safrSpecial25.setSpecial("حك");
        arrayList.add(safrSpecial25);
        SafrSpecial safrSpecial26 = new SafrSpecial();
        safrSpecial26.setSafrId(26);
        safrSpecial26.setSpecial("سي");
        arrayList.add(safrSpecial26);
        SafrSpecial safrSpecial27 = new SafrSpecial();
        safrSpecial27.setSafrId(27);
        safrSpecial27.setSpecial("أش");
        arrayList.add(safrSpecial27);
        SafrSpecial safrSpecial28 = new SafrSpecial();
        safrSpecial28.setSafrId(28);
        safrSpecial28.setSpecial("أر");
        arrayList.add(safrSpecial28);
        SafrSpecial safrSpecial29 = new SafrSpecial();
        safrSpecial29.setSafrId(29);
        safrSpecial29.setSpecial("مرا");
        arrayList.add(safrSpecial29);
        SafrSpecial safrSpecial30 = new SafrSpecial();
        safrSpecial30.setSafrId(30);
        safrSpecial30.setSpecial("با");
        arrayList.add(safrSpecial30);
        SafrSpecial safrSpecial31 = new SafrSpecial();
        safrSpecial31.setSafrId(31);
        safrSpecial31.setSpecial("حز");
        arrayList.add(safrSpecial31);
        SafrSpecial safrSpecial32 = new SafrSpecial();
        safrSpecial32.setSafrId(32);
        safrSpecial32.setSpecial("دا");
        arrayList.add(safrSpecial32);
        SafrSpecial safrSpecial33 = new SafrSpecial();
        safrSpecial33.setSafrId(33);
        safrSpecial33.setSpecial("هو");
        arrayList.add(safrSpecial33);
        SafrSpecial safrSpecial34 = new SafrSpecial();
        safrSpecial34.setSafrId(34);
        safrSpecial34.setSpecial("يؤ");
        safrSpecial34.setSpecial2("يوء");
        arrayList.add(safrSpecial34);
        SafrSpecial safrSpecial35 = new SafrSpecial();
        safrSpecial35.setSafrId(35);
        safrSpecial35.setSpecial("عا");
        arrayList.add(safrSpecial35);
        SafrSpecial safrSpecial36 = new SafrSpecial();
        safrSpecial36.setSafrId(36);
        safrSpecial36.setSpecial("عو");
        arrayList.add(safrSpecial36);
        SafrSpecial safrSpecial37 = new SafrSpecial();
        safrSpecial37.setSafrId(37);
        safrSpecial37.setSpecial("يون");
        arrayList.add(safrSpecial37);
        SafrSpecial safrSpecial38 = new SafrSpecial();
        safrSpecial38.setSafrId(38);
        safrSpecial38.setSpecial("مي");
        arrayList.add(safrSpecial38);
        SafrSpecial safrSpecial39 = new SafrSpecial();
        safrSpecial39.setSafrId(39);
        safrSpecial39.setSpecial("نا");
        arrayList.add(safrSpecial39);
        SafrSpecial safrSpecial40 = new SafrSpecial();
        safrSpecial40.setSafrId(40);
        safrSpecial40.setSpecial("حب");
        arrayList.add(safrSpecial40);
        SafrSpecial safrSpecial41 = new SafrSpecial();
        safrSpecial41.setSafrId(41);
        safrSpecial41.setSpecial("صف");
        arrayList.add(safrSpecial41);
        SafrSpecial safrSpecial42 = new SafrSpecial();
        safrSpecial42.setSafrId(42);
        safrSpecial42.setSpecial("حج");
        arrayList.add(safrSpecial42);
        SafrSpecial safrSpecial43 = new SafrSpecial();
        safrSpecial43.setSafrId(43);
        safrSpecial43.setSpecial("زك");
        arrayList.add(safrSpecial43);
        SafrSpecial safrSpecial44 = new SafrSpecial();
        safrSpecial44.setSafrId(44);
        safrSpecial44.setSpecial("مل");
        arrayList.add(safrSpecial44);
        SafrSpecial safrSpecial45 = new SafrSpecial();
        safrSpecial45.setSafrId(45);
        safrSpecial45.setSpecial("1مك");
        arrayList.add(safrSpecial45);
        SafrSpecial safrSpecial46 = new SafrSpecial();
        safrSpecial46.setSafrId(46);
        safrSpecial46.setSpecial("2مك");
        arrayList.add(safrSpecial46);
        SafrSpecial safrSpecial47 = new SafrSpecial();
        safrSpecial47.setSafrId(47);
        safrSpecial47.setSpecial("مت");
        arrayList.add(safrSpecial47);
        SafrSpecial safrSpecial48 = new SafrSpecial();
        safrSpecial48.setSafrId(48);
        safrSpecial48.setSpecial("مر");
        arrayList.add(safrSpecial48);
        SafrSpecial safrSpecial49 = new SafrSpecial();
        safrSpecial49.setSafrId(49);
        safrSpecial49.setSpecial("لو");
        arrayList.add(safrSpecial49);
        SafrSpecial safrSpecial50 = new SafrSpecial();
        safrSpecial50.setSafrId(50);
        safrSpecial50.setSpecial("يو");
        arrayList.add(safrSpecial50);
        SafrSpecial safrSpecial51 = new SafrSpecial();
        safrSpecial51.setSafrId(51);
        safrSpecial51.setSpecial("أع");
        arrayList.add(safrSpecial51);
        SafrSpecial safrSpecial52 = new SafrSpecial();
        safrSpecial52.setSafrId(52);
        safrSpecial52.setSpecial("رو");
        arrayList.add(safrSpecial52);
        SafrSpecial safrSpecial53 = new SafrSpecial();
        safrSpecial53.setSafrId(53);
        safrSpecial53.setSpecial("1كو");
        arrayList.add(safrSpecial53);
        SafrSpecial safrSpecial54 = new SafrSpecial();
        safrSpecial54.setSafrId(54);
        safrSpecial54.setSpecial("2كو");
        arrayList.add(safrSpecial54);
        SafrSpecial safrSpecial55 = new SafrSpecial();
        safrSpecial55.setSafrId(55);
        safrSpecial55.setSpecial("غل");
        arrayList.add(safrSpecial55);
        SafrSpecial safrSpecial56 = new SafrSpecial();
        safrSpecial56.setSafrId(56);
        safrSpecial56.setSpecial("أف");
        arrayList.add(safrSpecial56);
        SafrSpecial safrSpecial57 = new SafrSpecial();
        safrSpecial57.setSafrId(57);
        safrSpecial57.setSpecial("في");
        arrayList.add(safrSpecial57);
        SafrSpecial safrSpecial58 = new SafrSpecial();
        safrSpecial58.setSafrId(58);
        safrSpecial58.setSpecial("كو");
        arrayList.add(safrSpecial58);
        SafrSpecial safrSpecial59 = new SafrSpecial();
        safrSpecial59.setSafrId(59);
        safrSpecial59.setSpecial("1تس");
        arrayList.add(safrSpecial59);
        SafrSpecial safrSpecial60 = new SafrSpecial();
        safrSpecial60.setSafrId(60);
        safrSpecial60.setSpecial("2تس");
        arrayList.add(safrSpecial60);
        SafrSpecial safrSpecial61 = new SafrSpecial();
        safrSpecial61.setSafrId(61);
        safrSpecial61.setSpecial("1تى");
        arrayList.add(safrSpecial61);
        SafrSpecial safrSpecial62 = new SafrSpecial();
        safrSpecial62.setSafrId(62);
        safrSpecial62.setSpecial("2تي");
        arrayList.add(safrSpecial62);
        SafrSpecial safrSpecial63 = new SafrSpecial();
        safrSpecial63.setSafrId(63);
        safrSpecial63.setSpecial("تي");
        arrayList.add(safrSpecial63);
        SafrSpecial safrSpecial64 = new SafrSpecial();
        safrSpecial64.setSafrId(64);
        safrSpecial64.setSpecial("فل");
        arrayList.add(safrSpecial64);
        SafrSpecial safrSpecial65 = new SafrSpecial();
        safrSpecial65.setSafrId(65);
        safrSpecial65.setSpecial("عب");
        arrayList.add(safrSpecial65);
        SafrSpecial safrSpecial66 = new SafrSpecial();
        safrSpecial66.setSafrId(66);
        safrSpecial66.setSpecial("يع");
        arrayList.add(safrSpecial66);
        SafrSpecial safrSpecial67 = new SafrSpecial();
        safrSpecial67.setSafrId(67);
        safrSpecial67.setSpecial("1بط");
        arrayList.add(safrSpecial67);
        SafrSpecial safrSpecial68 = new SafrSpecial();
        safrSpecial68.setSafrId(68);
        safrSpecial68.setSpecial("2بط");
        arrayList.add(safrSpecial68);
        SafrSpecial safrSpecial69 = new SafrSpecial();
        safrSpecial69.setSafrId(69);
        safrSpecial69.setSpecial("1يو");
        arrayList.add(safrSpecial69);
        SafrSpecial safrSpecial70 = new SafrSpecial();
        safrSpecial70.setSafrId(70);
        safrSpecial70.setSpecial("2يو");
        arrayList.add(safrSpecial70);
        SafrSpecial safrSpecial71 = new SafrSpecial();
        safrSpecial71.setSafrId(71);
        safrSpecial71.setSpecial("3يو");
        arrayList.add(safrSpecial71);
        SafrSpecial safrSpecial72 = new SafrSpecial();
        safrSpecial72.setSafrId(72);
        safrSpecial72.setSpecial("يه");
        arrayList.add(safrSpecial72);
        SafrSpecial safrSpecial73 = new SafrSpecial();
        safrSpecial73.setSafrId(73);
        safrSpecial73.setSpecial("رؤ");
        arrayList.add(safrSpecial73);
        return arrayList;
    }
}
