package net.fortuna.ical4j.util;

import java.util.regex.Pattern;

/* loaded from: classes3.dex */
public final class Strings {
    public static final String LINE_SEPARATOR = "\r\n";
    public static final Pattern PARAM_QUOTE_PATTERN = Pattern.compile("[:;,]|[^\\p{ASCII}]");
    private static final Pattern ESCAPE_PUNCTUATION_PATTERN = Pattern.compile("([,;])");
    private static final Pattern UNESCAPE_PUNCTUATION_PATTERN = Pattern.compile("\\\\([,;\"])");
    private static final Pattern ESCAPE_NEWLINE_PATTERN = Pattern.compile("\r?\n");
    private static final Pattern UNESCAPE_NEWLINE_PATTERN = Pattern.compile("(?<!\\\\)\\\\n");
    private static final Pattern ESCAPE_BACKSLASH_PATTERN = Pattern.compile("\\\\");
    private static final Pattern UNESCAPE_BACKSLASH_PATTERN = Pattern.compile("\\\\\\\\");

    private Strings() {
    }

    public static String quote(Object obj) {
        if (obj == null) {
            return "\"\"";
        }
        return "\"" + obj + "\"";
    }

    public static String unquote(String str) {
        return (str != null && str.startsWith("\"") && str.endsWith("\"")) ? str.substring(0, str.length() - 1).substring(1) : str;
    }

    public static String escape(String str) {
        return escapePunctuation(escapeNewline(escapeBackslash(str)));
    }

    public static String unescape(String str) {
        return unescapeBackslash(unescapeNewline(unescapePunctuation(str)));
    }

    private static String escapePunctuation(String str) {
        if (str != null) {
            return ESCAPE_PUNCTUATION_PATTERN.matcher(str).replaceAll("\\\\$1");
        }
        return null;
    }

    private static String unescapePunctuation(String str) {
        if (str != null) {
            return UNESCAPE_PUNCTUATION_PATTERN.matcher(str).replaceAll("$1");
        }
        return null;
    }

    public static String escapeNewline(String str) {
        if (str != null) {
            return ESCAPE_NEWLINE_PATTERN.matcher(str).replaceAll("\\\\n");
        }
        return null;
    }

    private static String unescapeNewline(String str) {
        if (str != null) {
            return UNESCAPE_NEWLINE_PATTERN.matcher(str).replaceAll("\n");
        }
        return null;
    }

    private static String escapeBackslash(String str) {
        if (str != null) {
            return ESCAPE_BACKSLASH_PATTERN.matcher(str).replaceAll("\\\\\\\\");
        }
        return null;
    }

    private static String unescapeBackslash(String str) {
        if (str != null) {
            return UNESCAPE_BACKSLASH_PATTERN.matcher(str).replaceAll("\\\\");
        }
        return null;
    }

    public static String valueOf(Object obj) {
        return obj == null ? "" : obj.toString();
    }
}
