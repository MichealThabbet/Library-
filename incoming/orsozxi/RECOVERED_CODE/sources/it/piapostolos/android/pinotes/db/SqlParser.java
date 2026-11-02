package it.piapostolos.android.pinotes.db;

import android.content.res.AssetManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class SqlParser {
    public static List<String> parseSqlFile(String str, AssetManager assetManager) throws IOException {
        InputStream inputStreamOpen = assetManager.open(str);
        try {
            return parseSqlFile(inputStreamOpen);
        } finally {
            inputStreamOpen.close();
        }
    }

    public static List<String> parseSqlFile(InputStream inputStream) throws IOException {
        return splitSqlScript(removeComments(inputStream), ';');
    }

    private static String removeComments(InputStream inputStream) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        try {
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            while (true) {
                String str = null;
                while (true) {
                    try {
                        String line = bufferedReader.readLine();
                        if (line != null) {
                            String strTrim = line.trim();
                            if (str == null) {
                                if (strTrim.startsWith("/*")) {
                                    if (!strTrim.endsWith("}")) {
                                        str = "/*";
                                    }
                                } else if (strTrim.startsWith("{")) {
                                    if (!strTrim.endsWith("}")) {
                                        str = "{";
                                    }
                                } else if (!strTrim.startsWith("--") && !strTrim.equals("")) {
                                    sb.append(" ");
                                    sb.append(strTrim);
                                }
                            } else if (str.equals("/*")) {
                                if (strTrim.endsWith("*/")) {
                                    break;
                                }
                            } else if (!str.equals("{") || !strTrim.endsWith("}")) {
                            }
                        } else {
                            inputStreamReader.close();
                            return sb.toString();
                        }
                    } finally {
                        bufferedReader.close();
                    }
                }
            }
        } catch (Throwable th) {
            inputStreamReader.close();
            throw th;
        }
    }

    private static List<String> splitSqlScript(String str, char c) {
        ArrayList arrayList = new ArrayList();
        StringBuilder sb = new StringBuilder();
        char[] charArray = str.toCharArray();
        boolean z = false;
        for (int i = 0; i < str.length(); i++) {
            if (charArray[i] == '\'') {
                z = !z;
            }
            if (charArray[i] == c && !z) {
                if (sb.length() > 0) {
                    arrayList.add(sb.toString().trim());
                    sb = new StringBuilder();
                }
            } else {
                sb.append(charArray[i]);
            }
        }
        if (sb.length() > 0) {
            arrayList.add(sb.toString().trim());
        }
        return arrayList;
    }
}
