package com.ibm.icu.impl;

import com.huawei.hms.framework.common.ContainerUtils;
import com.ibm.icu.text.StringTransform;
import com.ibm.icu.text.SymbolTable;
import com.ibm.icu.text.UnicodeSet;
import com.ibm.icu.util.Freezable;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParsePosition;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;

/* loaded from: classes2.dex */
public class UnicodeRegex implements Cloneable, Freezable<UnicodeRegex>, StringTransform {
    private static final UnicodeRegex STANDARD = new UnicodeRegex();
    private SymbolTable symbolTable;
    private String bnfCommentString = "#";
    private String bnfVariableInfix = ContainerUtils.KEY_VALUE_DELIMITER;
    private String bnfLineSeparator = "\n";
    private Comparator<Object> LongestFirst = new Comparator<Object>() { // from class: com.ibm.icu.impl.UnicodeRegex.1
        @Override // java.util.Comparator
        public int compare(Object obj, Object obj2) {
            String string = obj.toString();
            String string2 = obj2.toString();
            int length = string.length();
            int length2 = string2.length();
            return length != length2 ? length2 - length : string.compareTo(string2);
        }
    };

    @Override // com.ibm.icu.util.Freezable
    public UnicodeRegex freeze() {
        return this;
    }

    @Override // com.ibm.icu.util.Freezable
    public boolean isFrozen() {
        return true;
    }

    public SymbolTable getSymbolTable() {
        return this.symbolTable;
    }

    public UnicodeRegex setSymbolTable(SymbolTable symbolTable) {
        this.symbolTable = symbolTable;
        return this;
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x003a  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x003c  */
    @Override // com.ibm.icu.text.Transform
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public java.lang.String transform(java.lang.String r13) {
        /*
            r12 = this;
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            com.ibm.icu.text.UnicodeSet r7 = new com.ibm.icu.text.UnicodeSet
            r7.<init>()
            java.text.ParsePosition r8 = new java.text.ParsePosition
            r9 = 0
            r8.<init>(r9)
            r2 = 0
            r10 = 0
        L12:
            int r0 = r13.length()
            if (r2 >= r0) goto L6b
            char r0 = r13.charAt(r2)
            r1 = 3
            r3 = 92
            r4 = 2
            r11 = 1
            if (r10 == 0) goto L3e
            if (r10 == r11) goto L36
            if (r10 == r4) goto L32
            if (r10 == r1) goto L2a
            goto L66
        L2a:
            r1 = 69
            if (r0 != r1) goto L2f
            goto L3c
        L2f:
            if (r0 == r3) goto L66
            goto L3a
        L32:
            if (r0 != r3) goto L66
            r10 = 3
            goto L66
        L36:
            r1 = 81
            if (r0 != r1) goto L3c
        L3a:
            r10 = 2
            goto L66
        L3c:
            r10 = 0
            goto L66
        L3e:
            if (r0 != r3) goto L52
            boolean r1 = com.ibm.icu.text.UnicodeSet.resemblesPattern(r13, r2)
            if (r1 == 0) goto L50
            r0 = r12
            r1 = r13
            r3 = r6
            r4 = r7
            r5 = r8
            int r2 = r0.processSet(r1, r2, r3, r4, r5)
            goto L69
        L50:
            r10 = 1
            goto L66
        L52:
            r1 = 91
            if (r0 != r1) goto L66
            boolean r1 = com.ibm.icu.text.UnicodeSet.resemblesPattern(r13, r2)
            if (r1 == 0) goto L66
            r0 = r12
            r1 = r13
            r3 = r6
            r4 = r7
            r5 = r8
            int r2 = r0.processSet(r1, r2, r3, r4, r5)
            goto L69
        L66:
            r6.append(r0)
        L69:
            int r2 = r2 + r11
            goto L12
        L6b:
            java.lang.String r13 = r6.toString()
            return r13
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.impl.UnicodeRegex.transform(java.lang.String):java.lang.String");
    }

    public static String fix(String str) {
        return STANDARD.transform(str);
    }

    public static Pattern compile(String str) {
        return Pattern.compile(STANDARD.transform(str));
    }

    public static Pattern compile(String str, int i) {
        return Pattern.compile(STANDARD.transform(str), i);
    }

    public String compileBnf(String str) {
        return compileBnf(Arrays.asList(str.split("\\r\\n?|\\n")));
    }

    public String compileBnf(List<String> list) {
        Map<String, String> variables = getVariables(list);
        LinkedHashSet linkedHashSet = new LinkedHashSet(variables.keySet());
        for (int i = 0; i < 2; i++) {
            for (Map.Entry<String, String> entry : variables.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                for (Map.Entry<String, String> entry2 : variables.entrySet()) {
                    String key2 = entry2.getKey();
                    String value2 = entry2.getValue();
                    if (!key.equals(key2)) {
                        String strReplace = value2.replace(key, value);
                        if (!strReplace.equals(value2)) {
                            linkedHashSet.remove(key);
                            variables.put(key2, strReplace);
                        }
                    }
                }
            }
        }
        if (linkedHashSet.size() != 1) {
            throw new IllegalArgumentException("Not a single root: " + linkedHashSet);
        }
        return variables.get(linkedHashSet.iterator().next());
    }

    public String getBnfCommentString() {
        return this.bnfCommentString;
    }

    public void setBnfCommentString(String str) {
        this.bnfCommentString = str;
    }

    public String getBnfVariableInfix() {
        return this.bnfVariableInfix;
    }

    public void setBnfVariableInfix(String str) {
        this.bnfVariableInfix = str;
    }

    public String getBnfLineSeparator() {
        return this.bnfLineSeparator;
    }

    public void setBnfLineSeparator(String str) {
        this.bnfLineSeparator = str;
    }

    public static List<String> appendLines(List<String> list, String str, String str2) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(str);
        try {
            return appendLines(list, fileInputStream, str2);
        } finally {
            fileInputStream.close();
        }
    }

    public static List<String> appendLines(List<String> list, InputStream inputStream, String str) throws IOException {
        if (str == null) {
            str = "UTF-8";
        }
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, str));
        while (true) {
            String line = bufferedReader.readLine();
            if (line == null) {
                return list;
            }
            list.add(line);
        }
    }

    @Override // com.ibm.icu.util.Freezable
    public UnicodeRegex cloneAsThawed() {
        try {
            return (UnicodeRegex) clone();
        } catch (CloneNotSupportedException unused) {
            throw new IllegalArgumentException();
        }
    }

    private int processSet(String str, int i, StringBuilder sb, UnicodeSet unicodeSet, ParsePosition parsePosition) {
        try {
            parsePosition.setIndex(i);
            UnicodeSet unicodeSetApplyPattern = unicodeSet.clear().applyPattern(str, parsePosition, this.symbolTable, 0);
            unicodeSetApplyPattern.complement().complement();
            sb.append(unicodeSetApplyPattern.toPattern(false));
            return parsePosition.getIndex() - 1;
        } catch (Exception e) {
            throw ((IllegalArgumentException) new IllegalArgumentException("Error in " + str).initCause(e));
        }
    }

    private Map<String, String> getVariables(List<String> list) {
        int iIndexOf;
        TreeMap treeMap = new TreeMap(this.LongestFirst);
        StringBuffer stringBuffer = new StringBuffer();
        String strTrim = null;
        int i = 0;
        for (String strSubstring : list) {
            i++;
            if (strSubstring.length() != 0) {
                if (strSubstring.charAt(0) == 65279) {
                    strSubstring = strSubstring.substring(1);
                }
                String str = this.bnfCommentString;
                if (str != null && (iIndexOf = strSubstring.indexOf(str)) >= 0) {
                    strSubstring = strSubstring.substring(0, iIndexOf);
                }
                String strTrim2 = strSubstring.trim();
                if (strTrim2.length() != 0 && strSubstring.trim().length() != 0) {
                    boolean zEndsWith = strTrim2.endsWith(";");
                    String strSubstring2 = zEndsWith ? strSubstring.substring(0, strSubstring.lastIndexOf(59)) : strSubstring;
                    int iIndexOf2 = strSubstring2.indexOf(this.bnfVariableInfix);
                    if (iIndexOf2 >= 0) {
                        if (strTrim != null) {
                            throw new IllegalArgumentException("Missing ';' before " + i + ") " + strSubstring);
                        }
                        strTrim = strSubstring2.substring(0, iIndexOf2).trim();
                        if (treeMap.containsKey(strTrim)) {
                            throw new IllegalArgumentException("Duplicate variable definition in " + strSubstring);
                        }
                        stringBuffer.append(strSubstring2.substring(iIndexOf2 + 1).trim());
                    } else {
                        if (strTrim == null) {
                            throw new IllegalArgumentException("Missing '=' at " + i + ") " + strSubstring);
                        }
                        stringBuffer.append(this.bnfLineSeparator);
                        stringBuffer.append(strSubstring2);
                    }
                    if (zEndsWith) {
                        treeMap.put(strTrim, stringBuffer.toString());
                        stringBuffer.setLength(0);
                        strTrim = null;
                    }
                }
            }
        }
        if (strTrim == null) {
            return treeMap;
        }
        throw new IllegalArgumentException("Missing ';' at end");
    }
}
