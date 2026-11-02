package org.apache.poi.ss.format;

import com.ibm.icu.impl.PatternTokenizer;
import java.awt.Color;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JLabel;
import org.apache.http.message.TokenParser;
import org.apache.poi.hssf.util.HSSFColor;

/* loaded from: classes3.dex */
public class CellFormatPart {
    public static final int COLOR_GROUP;
    public static final Pattern COLOR_PAT;
    public static final int CONDITION_OPERATOR_GROUP;
    public static final Pattern CONDITION_PAT;
    public static final int CONDITION_VALUE_GROUP;
    public static final Pattern FORMAT_PAT;
    private static final Map<String, Color> NAMED_COLORS = new TreeMap(String.CASE_INSENSITIVE_ORDER);
    public static final int SPECIFICATION_GROUP;
    public static final Pattern SPECIFICATION_PAT;
    private final Color color;
    private CellFormatCondition condition;
    private final CellFormatter format;

    interface PartHandler {
        String handlePart(Matcher matcher, String str, CellFormatType cellFormatType, StringBuffer stringBuffer);
    }

    static {
        for (HSSFColor hSSFColor : HSSFColor.getIndexHash().values()) {
            String simpleName = hSSFColor.getClass().getSimpleName();
            if (simpleName.equals(simpleName.toUpperCase())) {
                short[] triplet = hSSFColor.getTriplet();
                Color color = new Color(triplet[0], triplet[1], triplet[2]);
                NAMED_COLORS.put(simpleName, color);
                if (simpleName.indexOf(95) > 0) {
                    NAMED_COLORS.put(simpleName.replace('_', TokenParser.SP), color);
                }
                if (simpleName.indexOf("_PERCENT") > 0) {
                    NAMED_COLORS.put(simpleName.replace("_PERCENT", "%").replace('_', TokenParser.SP), color);
                }
            }
        }
        COLOR_PAT = Pattern.compile("\\[(black|blue|cyan|green|magenta|red|white|yellow|color [0-9]+)\\]", 6);
        CONDITION_PAT = Pattern.compile("([<>=]=?|!=|<>)    # The operator\n  \\s*([0-9]+(?:\\.[0-9]*)?)\\s*  # The constant to test against\n", 6);
        SPECIFICATION_PAT = Pattern.compile("\\\\.                 # Quoted single character\n|\"([^\\\\\"]|\\\\.)*\"         # Quoted string of characters (handles escaped quotes like \\\") \n|_.                             # Space as wide as a given character\n|\\*.                           # Repeating fill character\n|@                              # Text: cell text\n|([0?\\#](?:[0?\\#,]*))         # Number: digit + other digits and commas\n|e[-+]                          # Number: Scientific: Exponent\n|m{1,5}                         # Date: month or minute spec\n|d{1,4}                         # Date: day/date spec\n|y{2,4}                         # Date: year spec\n|h{1,2}                         # Date: hour spec\n|s{1,2}                         # Date: second spec\n|am?/pm?                        # Date: am/pm spec\n|\\[h{1,2}\\]                   # Elapsed time: hour spec\n|\\[m{1,2}\\]                   # Elapsed time: minute spec\n|\\[s{1,2}\\]                   # Elapsed time: second spec\n|[^;]                           # A character\n", 6);
        Pattern patternCompile = Pattern.compile("(?:\\[(black|blue|cyan|green|magenta|red|white|yellow|color [0-9]+)\\])?                  # Text color\n(?:\\[([<>=]=?|!=|<>)    # The operator\n  \\s*([0-9]+(?:\\.[0-9]*)?)\\s*  # The constant to test against\n\\])?                # Condition\n((?:\\\\.                 # Quoted single character\n|\"([^\\\\\"]|\\\\.)*\"         # Quoted string of characters (handles escaped quotes like \\\") \n|_.                             # Space as wide as a given character\n|\\*.                           # Repeating fill character\n|@                              # Text: cell text\n|([0?\\#](?:[0?\\#,]*))         # Number: digit + other digits and commas\n|e[-+]                          # Number: Scientific: Exponent\n|m{1,5}                         # Date: month or minute spec\n|d{1,4}                         # Date: day/date spec\n|y{2,4}                         # Date: year spec\n|h{1,2}                         # Date: hour spec\n|s{1,2}                         # Date: second spec\n|am?/pm?                        # Date: am/pm spec\n|\\[h{1,2}\\]                   # Elapsed time: hour spec\n|\\[m{1,2}\\]                   # Elapsed time: minute spec\n|\\[s{1,2}\\]                   # Elapsed time: second spec\n|[^;]                           # A character\n)+)                        # Format spec\n", 6);
        FORMAT_PAT = patternCompile;
        COLOR_GROUP = findGroup(patternCompile, "[Blue]@", "Blue");
        CONDITION_OPERATOR_GROUP = findGroup(FORMAT_PAT, "[>=1]@", ">=");
        CONDITION_VALUE_GROUP = findGroup(FORMAT_PAT, "[>=1]@", "1");
        SPECIFICATION_GROUP = findGroup(FORMAT_PAT, "[Blue][>1]\\a ?", "\\a ?");
    }

    public CellFormatPart(String str) {
        Matcher matcher = FORMAT_PAT.matcher(str);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Unrecognized format: " + CellFormatter.quote(str));
        }
        this.color = getColor(matcher);
        this.condition = getCondition(matcher);
        this.format = getFormatter(matcher);
    }

    public boolean applies(Object obj) {
        CellFormatCondition cellFormatCondition = this.condition;
        if (cellFormatCondition != null && (obj instanceof Number)) {
            return cellFormatCondition.pass(((Number) obj).doubleValue());
        }
        if (obj != null) {
            return true;
        }
        throw new NullPointerException("valueObject");
    }

    private static int findGroup(Pattern pattern, String str, String str2) {
        Matcher matcher = pattern.matcher(str);
        if (!matcher.find()) {
            throw new IllegalArgumentException("Pattern \"" + pattern.pattern() + "\" doesn't match \"" + str + "\"");
        }
        for (int i = 1; i <= matcher.groupCount(); i++) {
            String strGroup = matcher.group(i);
            if (strGroup != null && strGroup.equals(str2)) {
                return i;
            }
        }
        throw new IllegalArgumentException("\"" + str2 + "\" not found in \"" + pattern.pattern() + "\"");
    }

    private static Color getColor(Matcher matcher) {
        String strGroup = matcher.group(COLOR_GROUP);
        if (strGroup == null || strGroup.length() == 0) {
            return null;
        }
        Color color = NAMED_COLORS.get(strGroup);
        if (color == null) {
            CellFormatter.logger.warning("Unknown color: " + CellFormatter.quote(strGroup));
        }
        return color;
    }

    private CellFormatCondition getCondition(Matcher matcher) {
        String strGroup = matcher.group(CONDITION_OPERATOR_GROUP);
        if (strGroup == null || strGroup.length() == 0) {
            return null;
        }
        return CellFormatCondition.getInstance(matcher.group(CONDITION_OPERATOR_GROUP), matcher.group(CONDITION_VALUE_GROUP));
    }

    private CellFormatter getFormatter(Matcher matcher) {
        String strGroup = matcher.group(SPECIFICATION_GROUP);
        return formatType(strGroup).formatter(strGroup);
    }

    private CellFormatType formatType(String str) {
        String strTrim = str.trim();
        if (strTrim.equals("") || strTrim.equalsIgnoreCase("General")) {
            return CellFormatType.GENERAL;
        }
        Matcher matcher = SPECIFICATION_PAT.matcher(strTrim);
        boolean z = false;
        boolean z2 = false;
        while (matcher.find()) {
            String strGroup = matcher.group(0);
            if (strGroup.length() > 0) {
                switch (strGroup.charAt(0)) {
                    case '#':
                    case '?':
                        return CellFormatType.NUMBER;
                    case '0':
                        z2 = true;
                        break;
                    case '@':
                        return CellFormatType.TEXT;
                    case 'D':
                    case 'Y':
                    case 'd':
                    case 'y':
                        return CellFormatType.DATE;
                    case 'H':
                    case 'M':
                    case 'S':
                    case 'h':
                    case 'm':
                    case 's':
                        z = true;
                        break;
                    case '[':
                        return CellFormatType.ELAPSED;
                }
            }
        }
        if (z) {
            return CellFormatType.DATE;
        }
        if (z2) {
            return CellFormatType.NUMBER;
        }
        return CellFormatType.TEXT;
    }

    static String quoteSpecial(String str, CellFormatType cellFormatType) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char cCharAt = str.charAt(i);
            if (cCharAt == '\'' && cellFormatType.isSpecial(PatternTokenizer.SINGLE_QUOTE)) {
                sb.append((char) 0);
            } else {
                boolean zIsSpecial = cellFormatType.isSpecial(cCharAt);
                if (zIsSpecial) {
                    sb.append("'");
                }
                sb.append(cCharAt);
                if (zIsSpecial) {
                    sb.append("'");
                }
            }
        }
        return sb.toString();
    }

    public CellFormatResult apply(Object obj) {
        String strSimpleFormat;
        Color color;
        boolean zApplies = applies(obj);
        if (zApplies) {
            strSimpleFormat = this.format.format(obj);
            color = this.color;
        } else {
            strSimpleFormat = this.format.simpleFormat(obj);
            color = null;
        }
        return new CellFormatResult(zApplies, strSimpleFormat, color);
    }

    public CellFormatResult apply(JLabel jLabel, Object obj) {
        CellFormatResult cellFormatResultApply = apply(obj);
        jLabel.setText(cellFormatResultApply.text);
        if (cellFormatResultApply.textColor != null) {
            jLabel.setForeground(cellFormatResultApply.textColor);
        }
        return cellFormatResultApply;
    }

    public static StringBuffer parseFormat(String str, CellFormatType cellFormatType, PartHandler partHandler) {
        int iIndexOf;
        Matcher matcher = SPECIFICATION_PAT.matcher(str);
        StringBuffer stringBuffer = new StringBuffer();
        while (true) {
            iIndexOf = 0;
            if (!matcher.find()) {
                break;
            }
            String strGroup = group(matcher, 0);
            if (strGroup.length() > 0) {
                String strHandlePart = partHandler.handlePart(matcher, strGroup, cellFormatType, stringBuffer);
                if (strHandlePart == null) {
                    char cCharAt = strGroup.charAt(0);
                    if (cCharAt == '\"') {
                        strGroup = quoteSpecial(strGroup.substring(1, strGroup.length() - 1), cellFormatType);
                    } else if (cCharAt == '*') {
                        strGroup = expandChar(strGroup);
                    } else if (cCharAt == '\\') {
                        strGroup = quoteSpecial(strGroup.substring(1), cellFormatType);
                    } else if (cCharAt == '_') {
                        strGroup = " ";
                    }
                } else {
                    strGroup = strHandlePart;
                }
                matcher.appendReplacement(stringBuffer, Matcher.quoteReplacement(strGroup));
            }
        }
        matcher.appendTail(stringBuffer);
        if (cellFormatType.isSpecial(PatternTokenizer.SINGLE_QUOTE)) {
            int iIndexOf2 = 0;
            while (true) {
                iIndexOf2 = stringBuffer.indexOf("''", iIndexOf2);
                if (iIndexOf2 < 0) {
                    break;
                }
                stringBuffer.delete(iIndexOf2, iIndexOf2 + 2);
            }
            while (true) {
                iIndexOf = stringBuffer.indexOf("\u0000", iIndexOf);
                if (iIndexOf < 0) {
                    break;
                }
                stringBuffer.replace(iIndexOf, iIndexOf + 1, "''");
            }
        }
        return stringBuffer;
    }

    static String expandChar(String str) {
        char cCharAt = str.charAt(1);
        return "" + cCharAt + cCharAt + cCharAt;
    }

    public static String group(Matcher matcher, int i) {
        String strGroup = matcher.group(i);
        return strGroup == null ? "" : strGroup;
    }
}
