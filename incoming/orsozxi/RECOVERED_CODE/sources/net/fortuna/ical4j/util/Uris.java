package net.fortuna.ical4j.util;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.regex.Pattern;

/* loaded from: classes3.dex */
public final class Uris {
    public static final String INVALID_SCHEME = "net.fortunal.ical4j.invalid";
    private static final Pattern CID_PATTERN = Pattern.compile("(?i)^cid:.*");
    private static final Pattern NOTES_CID_REPLACEMENT_PATTERN = Pattern.compile("[<>]");

    public static String decode(String str) {
        return str;
    }

    private Uris() {
    }

    public static String encode(String str) {
        return (CompatibilityHints.isHintEnabled(CompatibilityHints.KEY_NOTES_COMPATIBILITY) && CID_PATTERN.matcher(str).matches()) ? NOTES_CID_REPLACEMENT_PATTERN.matcher(str).replaceAll("") : str;
    }

    public static URI create(String str) throws URISyntaxException {
        try {
            return new URI(encode(str));
        } catch (URISyntaxException e) {
            if (CompatibilityHints.isHintEnabled(CompatibilityHints.KEY_RELAXED_PARSING)) {
                String strEncode = encode(str);
                int iIndexOf = strEncode.indexOf(58);
                if (iIndexOf != -1 && iIndexOf < strEncode.length() - 1) {
                    try {
                        return new URI(strEncode.substring(0, iIndexOf), strEncode.substring(iIndexOf + 1), null);
                    } catch (URISyntaxException unused) {
                        return new URI(INVALID_SCHEME, str, null);
                    }
                }
                try {
                    return new URI(INVALID_SCHEME, str, null);
                } catch (URISyntaxException unused2) {
                    throw new IllegalArgumentException("Could not build URI from " + str);
                }
            }
            throw e;
        }
    }
}
