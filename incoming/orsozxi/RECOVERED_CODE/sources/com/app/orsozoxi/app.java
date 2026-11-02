package com.app.orsozoxi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.text.ClipboardManager;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.widget.Toast;
import com.app.orsozoxi.KholgayBeans.CopticDate;
import com.app.orsozoxi.KholgayBeans.KholagyConvert;
import java.io.InputStream;
import java.util.Locale;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.http.protocol.HTTP;

/* loaded from: classes.dex */
public class app {
    public static final int BIBLE_ASKS = 12;
    public static final int BIBLE_INTRO = 11;
    public static final int BOOKMARK = 8;
    public static final int COPYE = 0;
    public static final int COPY_ALL = 14;
    public static final int ENGLISH = 9;
    public static final int FACEBOOK_INVITE = 10;
    public static final int FACEBOOK_SCREENSHOT = 5;
    public static final int FACEBOOK_SHARE = 4;
    public static final int FRAGMENT_SCHEDULE = 1;
    public static final int FRAGMENT_SUGGESTION = 0;
    public static final int FRAGMENT_TIMELINE = 2;
    private static final String HEX = "0123456789ABCDEF";
    public static final int HIGHLGHT = 2;
    private static final int IV_SIZE = 16;
    private static final int KEY_SIZE = 32;
    public static final int LISTEN = 6;
    public static final String PREFS_NAME = "RadioOrsozoxi";
    public static final int SEARCH = 7;
    public static final int SHARE = 3;
    public static final int SHARE_BIBLE = 1;
    public static final int SHARE_KATAMARAS = 2;
    public static final int SHARE_PUBLIC = 0;
    public static final int SHOW_HIGHLIGHT = 13;
    public static final int TAFSER = 1;
    public static Activity activity;
    private static CopticDate copticDate;
    public static KholagyConvert kholagyConvert;
    private int currentSelectedTab;

    public static Activity getActivity() {
        return activity;
    }

    public static KholagyConvert getKholagyConvert() {
        return kholagyConvert;
    }

    public static void setKholagyConvert(KholagyConvert kholagyConvert2) {
        kholagyConvert = kholagyConvert2;
    }

    public static void setActivity(Activity activity2) {
        activity = activity2;
    }

    public int getCurrentSelectedTab() {
        return this.currentSelectedTab;
    }

    public void setCurrentSelectedTab(int i) {
        this.currentSelectedTab = i;
    }

    public static CopticDate getCopticDate() {
        return copticDate;
    }

    public static void setCopticDate(CopticDate copticDate2) {
        copticDate = copticDate2;
    }

    public static String decodeFile2(Context context, String str, String str2) throws Exception {
        InputStream inputStreamOpen = context.getAssets().open(str2);
        byte[] bArr = new byte[inputStreamOpen.available()];
        inputStreamOpen.read(bArr);
        inputStreamOpen.close();
        SecretKey secretKeyDeriveKeySecurely = deriveKeySecurely(context.getResources().getString(R.string.none) + context.getResources().getString(R.string.cfive) + context.getResources().getString(R.string.ntwo) + context.getResources().getString(R.string.csix) + context.getResources().getString(R.string.nthree) + context.getResources().getString(R.string.cone) + context.getResources().getString(R.string.nfour) + context.getResources().getString(R.string.cfour) + context.getResources().getString(R.string.nfive) + context.getResources().getString(R.string.cone) + context.getResources().getString(R.string.nsix), 32);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7PADDING");
        cipher.init(2, secretKeyDeriveKeySecurely, new IvParameterSpec(retrieveIv()));
        return new String(cipher.doFinal(bArr), "UTF-8");
    }

    public static String decodeText(Context context, byte[] bArr, String str) throws Exception {
        byte[] bArr2 = toByte(new String(Base64.decode(str, 0)));
        SecretKey secretKeyDeriveKeySecurely = deriveKeySecurely(context.getResources().getString(R.string.none) + context.getResources().getString(R.string.cfive) + context.getResources().getString(R.string.ntwo) + context.getResources().getString(R.string.csix) + context.getResources().getString(R.string.nthree) + context.getResources().getString(R.string.cone) + context.getResources().getString(R.string.nfour) + context.getResources().getString(R.string.cfour) + context.getResources().getString(R.string.nfive) + context.getResources().getString(R.string.cone) + context.getResources().getString(R.string.nsix), 32);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7PADDING");
        cipher.init(2, secretKeyDeriveKeySecurely, new IvParameterSpec(retrieveIv()));
        return new String(cipher.doFinal(bArr2), "UTF-8");
    }

    private static byte[] retrieveIv() {
        byte[] bArr = new byte[16];
        System.arraycopy("orso".getBytes(), 0, bArr, 12, 4);
        return bArr;
    }

    private static SecretKey deriveKeySecurely(String str, int i) {
        try {
            return new SecretKeySpec(SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1").generateSecret(new PBEKeySpec(str.toCharArray(), retrieveIv(), 100, i * 8)).getEncoded(), "AES");
        } catch (Exception e) {
            throw new RuntimeException("Deal with exceptions properly!", e);
        }
    }

    public static byte[] toByte(String str) {
        int length = str.length() / 2;
        byte[] bArr = new byte[length];
        for (int i = 0; i < length; i++) {
            int i2 = i * 2;
            bArr[i] = Integer.valueOf(str.substring(i2, i2 + 2), 16).byteValue();
        }
        return bArr;
    }

    public static String toHex(byte[] bArr) {
        if (bArr == null) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer(bArr.length * 2);
        for (byte b : bArr) {
            appendHex(stringBuffer, b);
        }
        return stringBuffer.toString();
    }

    private static void appendHex(StringBuffer stringBuffer, byte b) {
        stringBuffer.append("0123456789ABCDEF".charAt((b >> 4) & 15));
        stringBuffer.append("0123456789ABCDEF".charAt(b & 15));
    }

    public static void copyText(Activity activity2, String str) {
        ((ClipboardManager) activity2.getSystemService("clipboard")).setText(str);
        Toast.makeText(activity2, R.string.copy_done, 1).show();
    }

    public static void shareText(Activity activity2, String str) {
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType(HTTP.PLAIN_TEXT_TYPE);
        intent.putExtra("android.intent.extra.TEXT", str + "\n" + activity2.getString(R.string.message_from_orsozoxi) + "\nhttps://www.orsozoxi.net/");
        activity2.startActivity(Intent.createChooser(intent, activity2.getString(R.string.fb_share)));
    }

    public static boolean isArabicLocale() {
        return Locale.getDefault().getLanguage().equals("ar");
    }

    public static void updateLanguage(Context context, String str) {
        if ("".equals(str)) {
            return;
        }
        Locale locale = new Locale(str);
        Locale.setDefault(locale);
        Resources resources = context.getResources();
        Configuration configuration = new Configuration();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        configuration.locale = locale;
        context.getResources().updateConfiguration(configuration, displayMetrics);
    }

    public static String getSqKey(Activity activity2) {
        return "" + activity2.getString(R.string.mm) + activity2.getString(R.string.cone) + activity2.getString(R.string.none) + activity2.getString(R.string.bb) + activity2.getString(R.string.cfour) + activity2.getString(R.string.ntwo) + activity2.getString(R.string.mm) + activity2.getString(R.string.cone) + activity2.getString(R.string.nthree) + activity2.getString(R.string.csix) + activity2.getString(R.string.nfour) + activity2.getString(R.string.cfive) + activity2.getString(R.string.nfive);
    }
}
