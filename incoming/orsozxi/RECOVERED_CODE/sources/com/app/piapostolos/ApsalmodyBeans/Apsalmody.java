package com.app.piapostolos.ApsalmodyBeans;

import android.content.Context;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes.dex */
public class Apsalmody {
    public static String[] allNames;
    public static String[] allNamesAll;
    public static int[] allNumbers;
    public static int[] allNumbersAll;
    public static int[] sunday = {212, 213, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 127, 128, 129, 130, 131, 132, 133, 134, 135, 136, 137, 138, 139, 140, 141, 142, 143, 144, 145, 146, 147, 148, 149, 150, 151, 152, 153, 154, 155, 156, 157, 158, 159, 160, 161, 162, 163, 164, 165, 166, 167, 168, 169, 170, 171, 172, 173, 174, 175, 176, 177, 178, 179, 180, 181, 182, 183, 184, 185, 186, 187, 188, 189, 190, 191, 192, 193, 194, 195, 196, 197, 198, 199, 200, 201, 202, 203, 204, 205, 206, 207, 209, 210};
    public static int[] monday = {212, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 172, 173, 174, 175, 176, 177, 178, 179, 180, 181, 182, 183, 184, 185, 186, 187, 188, 189, 190, 191, 192, 193, 194, 195, 196, 197, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 127, 128, 129, 130, 131, 132, 133, 15, 16, 17, 18, 19, 139, 20, 21, 22, 23, 24, 202, 203, 204, 205, 206, 207, 209, 210};
    public static int[] tuesday = {212, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 172, 173, 174, 175, 176, 177, 178, 179, 180, 181, 182, 183, 184, 185, 186, 187, 188, 189, 190, 191, 192, 193, 194, 195, 196, 197, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 127, 128, 129, 130, 131, 132, 133, 25, 26, 27, 28, 29, 139, 30, 31, 32, 33, 202, 203, 204, 205, 206, 207, 209, 210};
    public static int[] wednesday = {212, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 172, 173, 174, 175, 176, 177, 178, 179, 180, 181, 182, 183, 184, 185, 186, 187, 188, 189, 190, 191, 192, 193, 194, 195, 196, 197, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 127, 128, 129, 130, 131, 132, 133, 46, 47, 48, 49, 50, 51, 211, 52, 53, 54, 55, 56, 57, 124, 125, 126, 208, 209, 210};
    public static int[] thursday = {212, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 172, 173, 174, 175, 176, 177, 178, 179, 180, 181, 182, 183, 184, 185, 186, 187, 188, 189, 190, 191, 192, 193, 194, 195, 196, 197, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 127, 128, 129, 130, 131, 132, 133, 58, 59, 60, 61, 211, 62, 63, 64, 65, 66, 67, 68, 69, 124, 125, 126, 208, 209, 210};
    public static int[] friday = {212, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 172, 173, 174, 175, 176, 177, 178, 179, 180, 181, 182, 183, 184, 185, 186, 187, 188, 189, 190, 191, 192, 193, 194, 195, 196, 197, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 127, 128, 129, 130, 131, 132, 133, 96, 97, 98, 99, 100, 101, 211, 102, 103, 104, 105, 106, 107, 124, 125, 126, 208, 209, 210};
    public static int[] saturday = {212, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 172, 173, 174, 175, 176, 177, 178, 179, 180, 181, 182, 183, 184, 185, 186, 187, 188, 189, 190, 191, 192, 193, 194, 195, 196, 197, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 127, 128, 129, 130, 131, 132, 133, 108, 109, 110, 111, 112, 211, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126, 208, 209, 210};

    public static void readsheetAll(Context context) throws IOException {
        try {
            InputStream inputStreamOpen = context.getAssets().open("absalmody/all.xml");
            byte[] bArr = new byte[inputStreamOpen.available()];
            inputStreamOpen.read(bArr);
            String[] strArrSplit = new String(bArr).split("\n");
            allNames = new String[strArrSplit.length];
            allNumbers = new int[strArrSplit.length];
            for (int i = 0; i < strArrSplit.length; i++) {
                String[] strArrSplit2 = strArrSplit[i].split("\\+");
                allNames[i] = strArrSplit2[1];
                allNumbers[i] = Integer.valueOf(strArrSplit2[0]).intValue();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readsheetAllForAllpsalmody(Context context) throws IOException {
        try {
            InputStream inputStreamOpen = context.getAssets().open("absalmody/all.xml");
            byte[] bArr = new byte[inputStreamOpen.available()];
            inputStreamOpen.read(bArr);
            String[] strArrSplit = new String(bArr).split("\n");
            allNamesAll = new String[strArrSplit.length];
            allNumbersAll = new int[strArrSplit.length];
            for (int i = 0; i < strArrSplit.length; i++) {
                if (i == 0) {
                    String[] strArrSplit2 = strArrSplit[strArrSplit.length - 2].split("\\+");
                    allNamesAll[i] = strArrSplit2[1];
                    allNumbersAll[i] = Integer.valueOf(strArrSplit2[0]).intValue();
                } else if (i == 1) {
                    String[] strArrSplit3 = strArrSplit[strArrSplit.length - 1].split("\\+");
                    allNamesAll[i] = strArrSplit3[1];
                    allNumbersAll[i] = Integer.valueOf(strArrSplit3[0]).intValue();
                } else {
                    String[] strArrSplit4 = strArrSplit[i - 2].split("\\+");
                    allNamesAll[i] = strArrSplit4[1];
                    allNumbersAll[i] = Integer.valueOf(strArrSplit4[0]).intValue();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
