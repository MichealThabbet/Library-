package it.feio.android.pixlui.links;

import androidx.core.net.MailTo;
import java.util.Locale;
import java.util.regex.Matcher;
import org.apache.http.HttpHost;

/* loaded from: classes2.dex */
public class UrlCompleter {
    public static final String HASHTAG_SCHEME = "hashtag:";

    public static String complete(String str) {
        if (RegexPatternsConstants.EMAIL.matcher(str).matches()) {
            return MailTo.MAILTO_SCHEME + str;
        }
        if (RegexPatternsConstants.HASH_TAG.matcher(str).find()) {
            return HASHTAG_SCHEME + parseHashtag(str);
        }
        if (RegexPatternsConstants.HYPER_LINK.matcher(str).matches() && !str.toLowerCase(Locale.getDefault()).startsWith(HttpHost.DEFAULT_SCHEME_NAME)) {
            return "http://" + str;
        }
        if (!RegexPatternsConstants.PHONE.matcher(str).matches()) {
            return str;
        }
        return "tel:" + str;
    }

    public static String parseHashtag(String str) {
        Matcher matcher = RegexPatternsConstants.HASH_TAG.matcher(str);
        return matcher.find() ? matcher.group() : "";
    }
}
