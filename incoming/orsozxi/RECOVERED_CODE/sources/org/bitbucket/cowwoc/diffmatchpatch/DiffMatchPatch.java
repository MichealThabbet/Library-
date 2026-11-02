package org.bitbucket.cowwoc.diffmatchpatch;

import com.huawei.hms.framework.common.ContainerUtils;
import com.ibm.icu.impl.locale.LanguageTag;
import it.orsozoxi.android.orsonotes.utils.ConstantsBase;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import kotlin.jvm.internal.LongCompanionObject;
import kotlin.text.Typography;
import org.apache.commons.codec.language.Soundex;
import org.apache.http.message.TokenParser;
import org.slf4j.Marker;

/* loaded from: classes3.dex */
public final class DiffMatchPatch {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public float diffTimeout = 1.0f;
    public short diffEditCost = 4;
    public float matchThreshold = 0.5f;
    public int matchDistance = 1000;
    public float patchDeleteThreshold = 0.5f;
    public short patchMargin = 4;
    private final short matchMaxBits = 32;
    private final Pattern BLANKLINEEND = Pattern.compile("\\n\\r?\\n\\Z", 32);
    private final Pattern BLANKLINESTART = Pattern.compile("\\A\\r?\\n\\r?\\n", 32);

    public enum Operation {
        DELETE,
        INSERT,
        EQUAL
    }

    static class LinesToCharsResult {
        final String chars1;
        final String chars2;
        final List<String> lineArray;

        LinesToCharsResult(String str, String str2, List<String> list) {
            this.chars1 = str;
            this.chars2 = str2;
            this.lineArray = list;
        }
    }

    public LinkedList<Diff> diffMain(String str, String str2) {
        return diffMain(str, str2, true);
    }

    public LinkedList<Diff> diffMain(String str, String str2, boolean z) {
        return diffMain(str, str2, z, this.diffTimeout <= 0.0f ? LongCompanionObject.MAX_VALUE : System.currentTimeMillis() + ((long) (this.diffTimeout * 1000.0f)));
    }

    private LinkedList<Diff> diffMain(String str, String str2, boolean z, long j) {
        if (str == null || str2 == null) {
            throw new IllegalArgumentException("Null inputs. (diffMain)");
        }
        if (str.equals(str2)) {
            LinkedList<Diff> linkedList = new LinkedList<>();
            if (str.length() != 0) {
                linkedList.add(new Diff(Operation.EQUAL, str));
            }
            return linkedList;
        }
        int iDiffCommonPrefix = diffCommonPrefix(str, str2);
        String strSubstring = str.substring(0, iDiffCommonPrefix);
        String strSubstring2 = str.substring(iDiffCommonPrefix);
        String strSubstring3 = str2.substring(iDiffCommonPrefix);
        int iDiffCommonSuffix = diffCommonSuffix(strSubstring2, strSubstring3);
        String strSubstring4 = strSubstring2.substring(strSubstring2.length() - iDiffCommonSuffix);
        LinkedList<Diff> linkedListDiffCompute = diffCompute(strSubstring2.substring(0, strSubstring2.length() - iDiffCommonSuffix), strSubstring3.substring(0, strSubstring3.length() - iDiffCommonSuffix), z, j);
        if (strSubstring.length() != 0) {
            linkedListDiffCompute.addFirst(new Diff(Operation.EQUAL, strSubstring));
        }
        if (strSubstring4.length() != 0) {
            linkedListDiffCompute.addLast(new Diff(Operation.EQUAL, strSubstring4));
        }
        diffCleanupMerge(linkedListDiffCompute);
        return linkedListDiffCompute;
    }

    private LinkedList<Diff> diffCompute(String str, String str2, boolean z, long j) {
        LinkedList<Diff> linkedList = new LinkedList<>();
        if (str.length() == 0) {
            linkedList.add(new Diff(Operation.INSERT, str2));
            return linkedList;
        }
        if (str2.length() == 0) {
            linkedList.add(new Diff(Operation.DELETE, str));
            return linkedList;
        }
        String str3 = str.length() > str2.length() ? str : str2;
        String str4 = str.length() > str2.length() ? str2 : str;
        int iIndexOf = str3.indexOf(str4);
        if (iIndexOf != -1) {
            Operation operation = str.length() > str2.length() ? Operation.DELETE : Operation.INSERT;
            linkedList.add(new Diff(operation, str3.substring(0, iIndexOf)));
            linkedList.add(new Diff(Operation.EQUAL, str4));
            linkedList.add(new Diff(operation, str3.substring(iIndexOf + str4.length())));
            return linkedList;
        }
        if (str4.length() == 1) {
            linkedList.add(new Diff(Operation.DELETE, str));
            linkedList.add(new Diff(Operation.INSERT, str2));
            return linkedList;
        }
        String[] strArrDiffHalfMatch = diffHalfMatch(str, str2);
        if (strArrDiffHalfMatch != null) {
            String str5 = strArrDiffHalfMatch[0];
            String str6 = strArrDiffHalfMatch[1];
            String str7 = strArrDiffHalfMatch[2];
            String str8 = strArrDiffHalfMatch[3];
            String str9 = strArrDiffHalfMatch[4];
            LinkedList<Diff> linkedListDiffMain = diffMain(str5, str7, z, j);
            LinkedList<Diff> linkedListDiffMain2 = diffMain(str6, str8, z, j);
            linkedListDiffMain.add(new Diff(Operation.EQUAL, str9));
            linkedListDiffMain.addAll(linkedListDiffMain2);
            return linkedListDiffMain;
        }
        if (z && str.length() > 100 && str2.length() > 100) {
            return diffLineMode(str, str2, j);
        }
        return diffBisect(str, str2, j);
    }

    private LinkedList<Diff> diffLineMode(String str, String str2, long j) {
        LinesToCharsResult linesToCharsResultDiffLinesToChars = diffLinesToChars(str, str2);
        String str3 = linesToCharsResultDiffLinesToChars.chars1;
        String str4 = linesToCharsResultDiffLinesToChars.chars2;
        List<String> list = linesToCharsResultDiffLinesToChars.lineArray;
        LinkedList<Diff> linkedListDiffMain = diffMain(str3, str4, false, j);
        diffCharsToLines(linkedListDiffMain, list);
        diffCleanupSemantic(linkedListDiffMain);
        linkedListDiffMain.add(new Diff(Operation.EQUAL, ""));
        ListIterator<Diff> listIterator = linkedListDiffMain.listIterator();
        Diff next = listIterator.next();
        String str5 = "";
        String str6 = str5;
        int i = 0;
        int i2 = 0;
        while (next != null) {
            int i3 = AnonymousClass1.$SwitchMap$org$bitbucket$cowwoc$diffmatchpatch$DiffMatchPatch$Operation[next.operation.ordinal()];
            if (i3 == 1) {
                i++;
                str6 = str6 + next.text;
            } else if (i3 == 2) {
                i2++;
                str5 = str5 + next.text;
            } else if (i3 == 3) {
                if (i2 >= 1 && i >= 1) {
                    listIterator.previous();
                    for (int i4 = 0; i4 < i2 + i; i4++) {
                        listIterator.previous();
                        listIterator.remove();
                    }
                    Iterator<Diff> it2 = diffMain(str5, str6, false, j).iterator();
                    while (it2.hasNext()) {
                        listIterator.add(it2.next());
                    }
                }
                str5 = "";
                str6 = str5;
                i = 0;
                i2 = 0;
            }
            next = listIterator.hasNext() ? listIterator.next() : null;
        }
        linkedListDiffMain.removeLast();
        return linkedListDiffMain;
    }

    /* renamed from: org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$bitbucket$cowwoc$diffmatchpatch$DiffMatchPatch$Operation;

        static {
            int[] iArr = new int[Operation.values().length];
            $SwitchMap$org$bitbucket$cowwoc$diffmatchpatch$DiffMatchPatch$Operation = iArr;
            try {
                iArr[Operation.INSERT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$bitbucket$cowwoc$diffmatchpatch$DiffMatchPatch$Operation[Operation.DELETE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$bitbucket$cowwoc$diffmatchpatch$DiffMatchPatch$Operation[Operation.EQUAL.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Not found exit edge by exit block: B:29:0x0079
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.checkLoopExits(LoopRegionMaker.java:225)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.makeLoopRegion(LoopRegionMaker.java:195)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.process(LoopRegionMaker.java:62)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:89)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:95)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.process(LoopRegionMaker.java:124)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:89)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.process(LoopRegionMaker.java:124)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:89)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeMthRegion(RegionMaker.java:48)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:25)
        */
    java.util.LinkedList<org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch.Diff> diffBisect(java.lang.String r25, java.lang.String r26, long r27) {
        /*
            Method dump skipped, instructions count: 418
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch.diffBisect(java.lang.String, java.lang.String, long):java.util.LinkedList");
    }

    private LinkedList<Diff> diffBisectSplit(String str, String str2, int i, int i2, long j) {
        String strSubstring = str.substring(0, i);
        String strSubstring2 = str2.substring(0, i2);
        String strSubstring3 = str.substring(i);
        String strSubstring4 = str2.substring(i2);
        LinkedList<Diff> linkedListDiffMain = diffMain(strSubstring, strSubstring2, false, j);
        linkedListDiffMain.addAll(diffMain(strSubstring3, strSubstring4, false, j));
        return linkedListDiffMain;
    }

    LinesToCharsResult diffLinesToChars(String str, String str2) {
        ArrayList arrayList = new ArrayList();
        HashMap map = new HashMap();
        arrayList.add("");
        return new LinesToCharsResult(diffLinesToCharsMunge(str, arrayList, map), diffLinesToCharsMunge(str2, arrayList, map), arrayList);
    }

    private String diffLinesToCharsMunge(String str, List<String> list, Map<String, Integer> map) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        int iIndexOf = -1;
        while (iIndexOf < str.length() - 1) {
            iIndexOf = str.indexOf(10, i);
            if (iIndexOf == -1) {
                iIndexOf = str.length() - 1;
            }
            int i2 = iIndexOf + 1;
            String strSubstring = str.substring(i, i2);
            if (map.containsKey(strSubstring)) {
                sb.append(String.valueOf((char) map.get(strSubstring).intValue()));
            } else {
                list.add(strSubstring);
                map.put(strSubstring, Integer.valueOf(list.size() - 1));
                sb.append(String.valueOf((char) (list.size() - 1)));
            }
            i = i2;
        }
        return sb.toString();
    }

    void diffCharsToLines(LinkedList<Diff> linkedList, List<String> list) {
        Iterator<Diff> it2 = linkedList.iterator();
        while (it2.hasNext()) {
            Diff next = it2.next();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < next.text.length(); i++) {
                sb.append(list.get(next.text.charAt(i)));
            }
            next.text = sb.toString();
        }
    }

    public int diffCommonPrefix(String str, String str2) {
        int iMin = Math.min(str.length(), str2.length());
        for (int i = 0; i < iMin; i++) {
            if (str.charAt(i) != str2.charAt(i)) {
                return i;
            }
        }
        return iMin;
    }

    public int diffCommonSuffix(String str, String str2) {
        int length = str.length();
        int length2 = str2.length();
        int iMin = Math.min(length, length2);
        for (int i = 1; i <= iMin; i++) {
            if (str.charAt(length - i) != str2.charAt(length2 - i)) {
                return i - 1;
            }
        }
        return iMin;
    }

    int diffCommonOverlap(String str, String str2) {
        int length = str.length();
        int length2 = str2.length();
        if (length == 0 || length2 == 0) {
            return 0;
        }
        if (length > length2) {
            str = str.substring(length - length2);
        } else if (length < length2) {
            str2 = str2.substring(0, length);
        }
        int iMin = Math.min(length, length2);
        if (str.equals(str2)) {
            return iMin;
        }
        int i = 1;
        int i2 = 0;
        while (true) {
            int iIndexOf = str2.indexOf(str.substring(iMin - i));
            if (iIndexOf == -1) {
                return i2;
            }
            i += iIndexOf;
            if (iIndexOf == 0 || str.substring(iMin - i).equals(str2.substring(0, i))) {
                i2 = i;
                i++;
            }
        }
    }

    String[] diffHalfMatch(String str, String str2) {
        if (this.diffTimeout <= 0.0f) {
            return null;
        }
        String str3 = str.length() > str2.length() ? str : str2;
        String str4 = str.length() > str2.length() ? str2 : str;
        if (str3.length() < 4 || str4.length() * 2 < str3.length()) {
            return null;
        }
        String[] strArrDiffHalfMatchI = diffHalfMatchI(str3, str4, (str3.length() + 3) / 4);
        String[] strArrDiffHalfMatchI2 = diffHalfMatchI(str3, str4, (str3.length() + 1) / 2);
        if (strArrDiffHalfMatchI == null && strArrDiffHalfMatchI2 == null) {
            return null;
        }
        if (strArrDiffHalfMatchI2 != null && (strArrDiffHalfMatchI == null || strArrDiffHalfMatchI[4].length() <= strArrDiffHalfMatchI2[4].length())) {
            strArrDiffHalfMatchI = strArrDiffHalfMatchI2;
        }
        return str.length() > str2.length() ? strArrDiffHalfMatchI : new String[]{strArrDiffHalfMatchI[2], strArrDiffHalfMatchI[3], strArrDiffHalfMatchI[0], strArrDiffHalfMatchI[1], strArrDiffHalfMatchI[4]};
    }

    private String[] diffHalfMatchI(String str, String str2, int i) {
        String strSubstring = str.substring(i, (str.length() / 4) + i);
        int i2 = -1;
        String str3 = "";
        String str4 = "";
        String strSubstring2 = str4;
        String strSubstring3 = strSubstring2;
        String strSubstring4 = strSubstring3;
        int iIndexOf = -1;
        while (true) {
            iIndexOf = str2.indexOf(strSubstring, iIndexOf + 1);
            if (iIndexOf == i2) {
                break;
            }
            int iDiffCommonPrefix = diffCommonPrefix(str.substring(i), str2.substring(iIndexOf));
            int iDiffCommonSuffix = diffCommonSuffix(str.substring(0, i), str2.substring(0, iIndexOf));
            if (str3.length() < iDiffCommonSuffix + iDiffCommonPrefix) {
                StringBuilder sb = new StringBuilder();
                int i3 = iIndexOf - iDiffCommonSuffix;
                sb.append(str2.substring(i3, iIndexOf));
                int i4 = iIndexOf + iDiffCommonPrefix;
                sb.append(str2.substring(iIndexOf, i4));
                String string = sb.toString();
                String strSubstring5 = str.substring(0, i - iDiffCommonSuffix);
                strSubstring2 = str.substring(i + iDiffCommonPrefix);
                strSubstring3 = str2.substring(0, i3);
                strSubstring4 = str2.substring(i4);
                str3 = string;
                str4 = strSubstring5;
            }
            i2 = -1;
        }
        if (str3.length() * 2 >= str.length()) {
            return new String[]{str4, strSubstring2, strSubstring3, strSubstring4, str3};
        }
        return null;
    }

    /* JADX WARN: Code restructure failed: missing block: B:74:0x00e9, code lost:
    
        r1 = null;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void diffCleanupSemantic(java.util.LinkedList<org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch.Diff> r15) {
        /*
            Method dump skipped, instructions count: 413
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch.diffCleanupSemantic(java.util.LinkedList):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:47:0x002f, code lost:
    
        r3 = null;
     */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0119  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void diffCleanupSemanticLossless(java.util.LinkedList<org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch.Diff> r15) {
        /*
            Method dump skipped, instructions count: 298
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch.diffCleanupSemanticLossless(java.util.LinkedList):void");
    }

    private int diffCleanupSemanticScore(String str, String str2) {
        if (str.length() == 0 || str2.length() == 0) {
            return 6;
        }
        char cCharAt = str.charAt(str.length() - 1);
        char cCharAt2 = str2.charAt(0);
        boolean z = !Character.isLetterOrDigit(cCharAt);
        boolean z2 = !Character.isLetterOrDigit(cCharAt2);
        boolean z3 = z && Character.isWhitespace(cCharAt);
        boolean z4 = z2 && Character.isWhitespace(cCharAt2);
        boolean z5 = z3 && Character.getType(cCharAt) == 15;
        boolean z6 = z4 && Character.getType(cCharAt2) == 15;
        boolean z7 = z5 && this.BLANKLINEEND.matcher(str).find();
        boolean z8 = z6 && this.BLANKLINESTART.matcher(str2).find();
        if (z7 || z8) {
            return 5;
        }
        if (z5 || z6) {
            return 4;
        }
        if (z && !z3 && z4) {
            return 3;
        }
        if (z3 || z4) {
            return 2;
        }
        return (z || z2) ? 1 : 0;
    }

    public void diffCleanupEfficiency(LinkedList<Diff> linkedList) {
        int i;
        if (linkedList.isEmpty()) {
            return;
        }
        Stack stack = new Stack();
        ListIterator<Diff> listIterator = linkedList.listIterator();
        Diff next = listIterator.next();
        Diff diff = next;
        String str = null;
        boolean z = false;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        while (next != null) {
            if (next.operation == Operation.EQUAL) {
                if (next.text.length() < this.diffEditCost && (i2 != 0 || i3 != 0)) {
                    stack.push(next);
                    str = next.text;
                    next = diff;
                } else {
                    stack.clear();
                    str = null;
                    i2 = i4;
                    i3 = i5;
                }
                diff = next;
                i4 = i2;
                i5 = i3;
                i2 = 0;
                i3 = 0;
            } else {
                if (next.operation == Operation.DELETE) {
                    i3 = 1;
                } else {
                    i2 = 1;
                }
                if (str != null && ((i4 != 0 && i5 != 0 && i2 != 0 && i3 != 0) || (str.length() < this.diffEditCost / 2 && i4 + i5 + i2 + i3 == 3))) {
                    while (next != stack.lastElement()) {
                        next = listIterator.previous();
                    }
                    listIterator.next();
                    listIterator.set(new Diff(Operation.DELETE, str));
                    Diff diff2 = new Diff(Operation.INSERT, str);
                    listIterator.add(diff2);
                    stack.pop();
                    if (i4 != 0 && i5 != 0) {
                        stack.clear();
                        diff = diff2;
                        i = 1;
                    } else {
                        if (!stack.empty()) {
                            stack.pop();
                        }
                        while ((stack.empty() ? diff : (Diff) stack.lastElement()) != listIterator.previous()) {
                        }
                        i = 0;
                    }
                    i2 = i;
                    i3 = i2;
                    str = null;
                    z = true;
                }
            }
            next = listIterator.hasNext() ? listIterator.next() : null;
        }
        if (z) {
            diffCleanupMerge(linkedList);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:100:0x016b A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:101:0x0163 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:91:0x027a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void diffCleanupMerge(java.util.LinkedList<org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch.Diff> r15) {
        /*
            Method dump skipped, instructions count: 659
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch.diffCleanupMerge(java.util.LinkedList):void");
    }

    public int diffXIndex(LinkedList<Diff> linkedList, int i) {
        Diff next;
        Iterator<Diff> it2 = linkedList.iterator();
        int length = 0;
        int length2 = 0;
        int i2 = 0;
        int i3 = 0;
        while (true) {
            if (!it2.hasNext()) {
                next = null;
                break;
            }
            next = it2.next();
            if (next.operation != Operation.INSERT) {
                length += next.text.length();
            }
            if (next.operation != Operation.DELETE) {
                length2 += next.text.length();
            }
            if (length > i) {
                break;
            }
            i2 = length;
            i3 = length2;
        }
        return (next == null || next.operation != Operation.DELETE) ? i3 + (i - i2) : i3;
    }

    public String diffPrettyHtml(LinkedList<Diff> linkedList) {
        StringBuilder sb = new StringBuilder();
        Iterator<Diff> it2 = linkedList.iterator();
        while (it2.hasNext()) {
            Diff next = it2.next();
            String strReplace = next.text.replace(ContainerUtils.FIELD_DELIMITER, "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\n", "&para;<br>");
            int i = AnonymousClass1.$SwitchMap$org$bitbucket$cowwoc$diffmatchpatch$DiffMatchPatch$Operation[next.operation.ordinal()];
            if (i == 1) {
                sb.append("<ins style=\"background:#e6ffe6;\">");
                sb.append(strReplace);
                sb.append("</ins>");
            } else if (i == 2) {
                sb.append("<del style=\"background:#ffe6e6;\">");
                sb.append(strReplace);
                sb.append("</del>");
            } else if (i == 3) {
                sb.append("<span>");
                sb.append(strReplace);
                sb.append("</span>");
            }
        }
        return sb.toString();
    }

    public String diffText1(LinkedList<Diff> linkedList) {
        StringBuilder sb = new StringBuilder();
        Iterator<Diff> it2 = linkedList.iterator();
        while (it2.hasNext()) {
            Diff next = it2.next();
            if (next.operation != Operation.INSERT) {
                sb.append(next.text);
            }
        }
        return sb.toString();
    }

    public String diffText2(LinkedList<Diff> linkedList) {
        StringBuilder sb = new StringBuilder();
        Iterator<Diff> it2 = linkedList.iterator();
        while (it2.hasNext()) {
            Diff next = it2.next();
            if (next.operation != Operation.DELETE) {
                sb.append(next.text);
            }
        }
        return sb.toString();
    }

    public int diffLevenshtein(LinkedList<Diff> linkedList) {
        Iterator<Diff> it2 = linkedList.iterator();
        int iMax = 0;
        while (true) {
            int length = 0;
            int length2 = 0;
            while (it2.hasNext()) {
                Diff next = it2.next();
                int i = AnonymousClass1.$SwitchMap$org$bitbucket$cowwoc$diffmatchpatch$DiffMatchPatch$Operation[next.operation.ordinal()];
                if (i == 1) {
                    length += next.text.length();
                } else if (i == 2) {
                    length2 += next.text.length();
                } else if (i != 3) {
                }
            }
            return iMax + Math.max(length, length2);
            iMax += Math.max(length, length2);
        }
    }

    public String diffToDelta(LinkedList<Diff> linkedList) {
        StringBuilder sb = new StringBuilder();
        Iterator<Diff> it2 = linkedList.iterator();
        while (it2.hasNext()) {
            Diff next = it2.next();
            int i = AnonymousClass1.$SwitchMap$org$bitbucket$cowwoc$diffmatchpatch$DiffMatchPatch$Operation[next.operation.ordinal()];
            if (i == 1) {
                try {
                    sb.append(Marker.ANY_NON_NULL_MARKER);
                    sb.append(URLEncoder.encode(next.text, "UTF-8").replace('+', TokenParser.SP));
                    sb.append("\t");
                } catch (UnsupportedEncodingException e) {
                    throw new Error("This system does not support UTF-8.", e);
                }
            } else if (i == 2) {
                sb.append(LanguageTag.SEP);
                sb.append(next.text.length());
                sb.append("\t");
            } else if (i == 3) {
                sb.append(ContainerUtils.KEY_VALUE_DELIMITER);
                sb.append(next.text.length());
                sb.append("\t");
            }
        }
        String string = sb.toString();
        return string.length() != 0 ? unescapeForEncodeUriCompatability(string.substring(0, string.length() - 1)) : string;
    }

    public LinkedList<Diff> diffFromDelta(String str, String str2) throws IllegalArgumentException, UnsupportedEncodingException {
        LinkedList<Diff> linkedList = new LinkedList<>();
        int i = 0;
        for (String str3 : str2.split("\t")) {
            if (str3.length() != 0) {
                String strSubstring = str3.substring(1);
                char cCharAt = str3.charAt(0);
                if (cCharAt == '+') {
                    String strReplace = strSubstring.replace(Marker.ANY_NON_NULL_MARKER, "%2B");
                    try {
                        linkedList.add(new Diff(Operation.INSERT, URLDecoder.decode(strReplace, "UTF-8")));
                    } catch (UnsupportedEncodingException e) {
                        throw new Error("This system does not support UTF-8.", e);
                    } catch (IllegalArgumentException e2) {
                        throw new IllegalArgumentException("Illegal escape in diffFromDelta: " + strReplace, e2);
                    }
                } else if (cCharAt == '-' || cCharAt == '=') {
                    try {
                        int i2 = Integer.parseInt(strSubstring);
                        if (i2 < 0) {
                            throw new IllegalArgumentException("Negative number in diffFromDelta: " + strSubstring);
                        }
                        int i3 = i2 + i;
                        try {
                            String strSubstring2 = str.substring(i, i3);
                            if (str3.charAt(0) == '=') {
                                linkedList.add(new Diff(Operation.EQUAL, strSubstring2));
                            } else {
                                linkedList.add(new Diff(Operation.DELETE, strSubstring2));
                            }
                            i = i3;
                        } catch (StringIndexOutOfBoundsException e3) {
                            throw new IllegalArgumentException("Delta length (" + i3 + ") larger than source text length (" + str.length() + ").", e3);
                        }
                    } catch (NumberFormatException e4) {
                        throw new IllegalArgumentException("Invalid number in diffFromDelta: " + strSubstring, e4);
                    }
                } else {
                    throw new IllegalArgumentException("Invalid diff operation in diffFromDelta: " + str3.charAt(0));
                }
            }
        }
        if (i == str.length()) {
            return linkedList;
        }
        throw new IllegalArgumentException("Delta length (" + i + ") smaller than source text length (" + str.length() + ").");
    }

    public int matchMain(String str, String str2, int i) {
        if (str == null || str2 == null) {
            throw new IllegalArgumentException("Null inputs. (matchMain)");
        }
        int iMax = Math.max(0, Math.min(i, str.length()));
        if (str.equals(str2)) {
            return 0;
        }
        if (str.length() == 0) {
            return -1;
        }
        return (str2.length() + iMax > str.length() || !str.substring(iMax, str2.length() + iMax).equals(str2)) ? matchBitap(str, str2, iMax) : iMax;
    }

    /* JADX WARN: Removed duplicated region for block: B:36:0x0104  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    int matchBitap(java.lang.String r22, java.lang.String r23, int r24) {
        /*
            Method dump skipped, instructions count: 285
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch.matchBitap(java.lang.String, java.lang.String, int):int");
    }

    private double matchBitapScore(int i, int i2, int i3, String str) {
        float length = i / str.length();
        int iAbs = Math.abs(i3 - i2);
        if (this.matchDistance != 0) {
            return length + (iAbs / r3);
        }
        if (iAbs == 0) {
            return length;
        }
        return 1.0d;
    }

    Map<Character, Integer> matchAlphabet(String str) {
        HashMap map = new HashMap();
        char[] charArray = str.toCharArray();
        for (char c : charArray) {
            map.put(Character.valueOf(c), 0);
        }
        int i = 0;
        for (char c2 : charArray) {
            map.put(Character.valueOf(c2), Integer.valueOf(((Integer) map.get(Character.valueOf(c2))).intValue() | (1 << ((str.length() - i) - 1))));
            i++;
        }
        return map;
    }

    void patchAddContext(Patch patch, String str) {
        if (str.length() == 0) {
            return;
        }
        String strSubstring = str.substring(patch.start2, patch.start2 + patch.length1);
        int i = 0;
        while (str.indexOf(strSubstring) != str.lastIndexOf(strSubstring)) {
            int length = strSubstring.length();
            short s = this.patchMargin;
            if (length >= (32 - s) - s) {
                break;
            }
            i += s;
            strSubstring = str.substring(Math.max(0, patch.start2 - i), Math.min(str.length(), patch.start2 + patch.length1 + i));
        }
        int i2 = i + this.patchMargin;
        String strSubstring2 = str.substring(Math.max(0, patch.start2 - i2), patch.start2);
        if (strSubstring2.length() != 0) {
            patch.diffs.addFirst(new Diff(Operation.EQUAL, strSubstring2));
        }
        String strSubstring3 = str.substring(patch.start2 + patch.length1, Math.min(str.length(), patch.start2 + patch.length1 + i2));
        if (strSubstring3.length() != 0) {
            patch.diffs.addLast(new Diff(Operation.EQUAL, strSubstring3));
        }
        patch.start1 -= strSubstring2.length();
        patch.start2 -= strSubstring2.length();
        patch.length1 += strSubstring2.length() + strSubstring3.length();
        patch.length2 += strSubstring2.length() + strSubstring3.length();
    }

    public LinkedList<Patch> patchMake(String str, String str2) {
        if (str == null || str2 == null) {
            throw new IllegalArgumentException("Null inputs. (patchMake)");
        }
        LinkedList<Diff> linkedListDiffMain = diffMain(str, str2, true);
        if (linkedListDiffMain.size() > 2) {
            diffCleanupSemantic(linkedListDiffMain);
            diffCleanupEfficiency(linkedListDiffMain);
        }
        return patchMake(str, linkedListDiffMain);
    }

    public LinkedList<Patch> patchMake(LinkedList<Diff> linkedList) {
        if (linkedList == null) {
            throw new IllegalArgumentException("Null inputs. (patchMake)");
        }
        return patchMake(diffText1(linkedList), linkedList);
    }

    @Deprecated
    public LinkedList<Patch> patchMake(String str, String str2, LinkedList<Diff> linkedList) {
        return patchMake(str, linkedList);
    }

    public LinkedList<Patch> patchMake(String str, LinkedList<Diff> linkedList) {
        if (str == null || linkedList == null) {
            throw new IllegalArgumentException("Null inputs. (patchMake)");
        }
        LinkedList<Patch> linkedList2 = new LinkedList<>();
        if (linkedList.isEmpty()) {
            return linkedList2;
        }
        Patch patch = new Patch();
        Iterator<Diff> it2 = linkedList.iterator();
        Patch patch2 = patch;
        int length = 0;
        int length2 = 0;
        String str2 = str;
        while (it2.hasNext()) {
            Diff next = it2.next();
            if (patch2.diffs.isEmpty() && next.operation != Operation.EQUAL) {
                patch2.start1 = length;
                patch2.start2 = length2;
            }
            int i = AnonymousClass1.$SwitchMap$org$bitbucket$cowwoc$diffmatchpatch$DiffMatchPatch$Operation[next.operation.ordinal()];
            if (i == 1) {
                patch2.diffs.add(next);
                patch2.length2 += next.text.length();
                str2 = str2.substring(0, length2) + next.text + str2.substring(length2);
            } else if (i == 2) {
                patch2.length1 += next.text.length();
                patch2.diffs.add(next);
                str2 = str2.substring(0, length2) + str2.substring(next.text.length() + length2);
            } else if (i == 3) {
                if (next.text.length() <= this.patchMargin * 2 && !patch2.diffs.isEmpty() && next != linkedList.getLast()) {
                    patch2.diffs.add(next);
                    patch2.length1 += next.text.length();
                    patch2.length2 += next.text.length();
                }
                if (next.text.length() >= this.patchMargin * 2 && !patch2.diffs.isEmpty()) {
                    patchAddContext(patch2, str);
                    linkedList2.add(patch2);
                    patch2 = new Patch();
                    str = str2;
                    length = length2;
                }
            }
            if (next.operation != Operation.INSERT) {
                length += next.text.length();
            }
            if (next.operation != Operation.DELETE) {
                length2 += next.text.length();
            }
        }
        if (!patch2.diffs.isEmpty()) {
            patchAddContext(patch2, str);
            linkedList2.add(patch2);
        }
        return linkedList2;
    }

    public LinkedList<Patch> patchDeepCopy(LinkedList<Patch> linkedList) {
        LinkedList<Patch> linkedList2 = new LinkedList<>();
        Iterator<Patch> it2 = linkedList.iterator();
        while (it2.hasNext()) {
            Patch next = it2.next();
            Patch patch = new Patch();
            Iterator<Diff> it3 = next.diffs.iterator();
            while (it3.hasNext()) {
                Diff next2 = it3.next();
                patch.diffs.add(new Diff(next2.operation, next2.text));
            }
            patch.start1 = next.start1;
            patch.start2 = next.start2;
            patch.length1 = next.length1;
            patch.length2 = next.length2;
            linkedList2.add(patch);
        }
        return linkedList2;
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x0096  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x00a0  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public java.lang.Object[] patchApply(java.util.LinkedList<org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch.Patch> r18, java.lang.String r19) {
        /*
            Method dump skipped, instructions count: 437
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch.patchApply(java.util.LinkedList, java.lang.String):java.lang.Object[]");
    }

    public String patchAddPadding(LinkedList<Patch> linkedList) {
        short s = this.patchMargin;
        String str = "";
        for (short s2 = 1; s2 <= s; s2 = (short) (s2 + 1)) {
            str = str + String.valueOf((char) s2);
        }
        Iterator<Patch> it2 = linkedList.iterator();
        while (it2.hasNext()) {
            Patch next = it2.next();
            next.start1 += s;
            next.start2 += s;
        }
        Patch first = linkedList.getFirst();
        LinkedList<Diff> linkedList2 = first.diffs;
        if (linkedList2.isEmpty() || linkedList2.getFirst().operation != Operation.EQUAL) {
            linkedList2.addFirst(new Diff(Operation.EQUAL, str));
            first.start1 -= s;
            first.start2 -= s;
            first.length1 += s;
            first.length2 += s;
        } else if (s > linkedList2.getFirst().text.length()) {
            Diff first2 = linkedList2.getFirst();
            int length = s - first2.text.length();
            first2.text = str.substring(first2.text.length()) + first2.text;
            first.start1 = first.start1 - length;
            first.start2 = first.start2 - length;
            first.length1 = first.length1 + length;
            first.length2 += length;
        }
        Patch last = linkedList.getLast();
        LinkedList<Diff> linkedList3 = last.diffs;
        if (linkedList3.isEmpty() || linkedList3.getLast().operation != Operation.EQUAL) {
            linkedList3.addLast(new Diff(Operation.EQUAL, str));
            last.length1 += s;
            last.length2 += s;
        } else if (s > linkedList3.getLast().text.length()) {
            Diff last2 = linkedList3.getLast();
            int length2 = s - last2.text.length();
            last2.text += str.substring(0, length2);
            last.length1 += length2;
            last.length2 += length2;
        }
        return str;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0015  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:5:0x0012 -> B:6:0x0013). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void patchSplitMax(java.util.LinkedList<org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch.Patch> r15) {
        /*
            Method dump skipped, instructions count: 516
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch.patchSplitMax(java.util.LinkedList):void");
    }

    public String patchToText(List<Patch> list) {
        StringBuilder sb = new StringBuilder();
        Iterator<Patch> it2 = list.iterator();
        while (it2.hasNext()) {
            sb.append(it2.next());
        }
        return sb.toString();
    }

    public List<Patch> patchFromText(String str) throws IllegalArgumentException, UnsupportedEncodingException {
        char cCharAt;
        String strReplace;
        LinkedList linkedList = new LinkedList();
        if (str.length() == 0) {
            return linkedList;
        }
        LinkedList linkedList2 = new LinkedList(Arrays.asList(str.split("\n")));
        Pattern patternCompile = Pattern.compile("^@@ -(\\d+),?(\\d*) \\+(\\d+),?(\\d*) @@$");
        while (!linkedList2.isEmpty()) {
            Matcher matcher = patternCompile.matcher((CharSequence) linkedList2.getFirst());
            if (!matcher.matches()) {
                throw new IllegalArgumentException("Invalid patch string: " + ((String) linkedList2.getFirst()));
            }
            Patch patch = new Patch();
            linkedList.add(patch);
            patch.start1 = Integer.parseInt(matcher.group(1));
            if (matcher.group(2).length() == 0) {
                patch.start1--;
                patch.length1 = 1;
            } else if (matcher.group(2).equals("0")) {
                patch.length1 = 0;
            } else {
                patch.start1--;
                patch.length1 = Integer.parseInt(matcher.group(2));
            }
            patch.start2 = Integer.parseInt(matcher.group(3));
            if (matcher.group(4).length() == 0) {
                patch.start2--;
                patch.length2 = 1;
            } else if (matcher.group(4).equals("0")) {
                patch.length2 = 0;
            } else {
                patch.start2--;
                patch.length2 = Integer.parseInt(matcher.group(4));
            }
            linkedList2.removeFirst();
            while (true) {
                if (!linkedList2.isEmpty()) {
                    try {
                        cCharAt = ((String) linkedList2.getFirst()).charAt(0);
                        strReplace = ((String) linkedList2.getFirst()).substring(1).replace(Marker.ANY_NON_NULL_MARKER, "%2B");
                    } catch (IndexOutOfBoundsException unused) {
                        linkedList2.removeFirst();
                    }
                    try {
                        String strDecode = URLDecoder.decode(strReplace, "UTF-8");
                        if (cCharAt == '-') {
                            patch.diffs.add(new Diff(Operation.DELETE, strDecode));
                        } else if (cCharAt == '+') {
                            patch.diffs.add(new Diff(Operation.INSERT, strDecode));
                        } else if (cCharAt == ' ') {
                            patch.diffs.add(new Diff(Operation.EQUAL, strDecode));
                        } else if (cCharAt != '@') {
                            throw new IllegalArgumentException("Invalid patch mode '" + cCharAt + "' in: " + strDecode);
                        }
                        linkedList2.removeFirst();
                    } catch (UnsupportedEncodingException e) {
                        throw new Error("This system does not support UTF-8.", e);
                    } catch (IllegalArgumentException e2) {
                        throw new IllegalArgumentException("Illegal escape in patchFromText: " + strReplace, e2);
                    }
                }
            }
        }
        return linkedList;
    }

    public static class Diff {
        public Operation operation;
        public String text;

        public Diff(Operation operation, String str) {
            this.operation = operation;
            this.text = str;
        }

        public String toString() {
            return "Diff(" + this.operation + ",\"" + this.text.replace('\n', Typography.paragraph) + "\")";
        }

        public int hashCode() {
            Operation operation = this.operation;
            int iHashCode = operation == null ? 0 : operation.hashCode();
            String str = this.text;
            return iHashCode + ((str != null ? str.hashCode() : 0) * 31);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            Diff diff = (Diff) obj;
            if (this.operation != diff.operation) {
                return false;
            }
            String str = this.text;
            if (str == null) {
                if (diff.text != null) {
                    return false;
                }
            } else if (!str.equals(diff.text)) {
                return false;
            }
            return true;
        }
    }

    public static class Patch {
        public LinkedList<Diff> diffs = new LinkedList<>();
        public int length1;
        public int length2;
        public int start1;
        public int start2;

        public String toString() {
            String string;
            String string2;
            int i = this.length1;
            if (i == 0) {
                string = this.start1 + ",0";
            } else if (i == 1) {
                string = Integer.toString(this.start1 + 1);
            } else {
                string = (this.start1 + 1) + ConstantsBase.PROPERTIES_PARAMS_SEPARATOR + this.length1;
            }
            int i2 = this.length2;
            if (i2 == 0) {
                string2 = this.start2 + ",0";
            } else if (i2 == 1) {
                string2 = Integer.toString(this.start2 + 1);
            } else {
                string2 = (this.start2 + 1) + ConstantsBase.PROPERTIES_PARAMS_SEPARATOR + this.length2;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("@@ -");
            sb.append(string);
            sb.append(" +");
            sb.append(string2);
            sb.append(" @@\n");
            Iterator<Diff> it2 = this.diffs.iterator();
            while (it2.hasNext()) {
                Diff next = it2.next();
                int i3 = AnonymousClass1.$SwitchMap$org$bitbucket$cowwoc$diffmatchpatch$DiffMatchPatch$Operation[next.operation.ordinal()];
                if (i3 == 1) {
                    sb.append('+');
                } else if (i3 == 2) {
                    sb.append(Soundex.SILENT_MARKER);
                } else if (i3 == 3) {
                    sb.append(TokenParser.SP);
                }
                try {
                    sb.append(URLEncoder.encode(next.text, "UTF-8").replace('+', TokenParser.SP));
                    sb.append("\n");
                } catch (UnsupportedEncodingException e) {
                    throw new Error("This system does not support UTF-8.", e);
                }
            }
            return DiffMatchPatch.unescapeForEncodeUriCompatability(sb.toString());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String unescapeForEncodeUriCompatability(String str) {
        return str.replace("%21", "!").replace("%7E", "~").replace("%27", "'").replace("%28", "(").replace("%29", ")").replace("%3B", ";").replace("%2F", "/").replace("%3F", "?").replace("%3A", ":").replace("%40", "@").replace("%26", ContainerUtils.FIELD_DELIMITER).replace("%3D", ContainerUtils.KEY_VALUE_DELIMITER).replace("%2B", Marker.ANY_NON_NULL_MARKER).replace("%24", "$").replace("%2C", ConstantsBase.PROPERTIES_PARAMS_SEPARATOR).replace("%23", "#");
    }
}
