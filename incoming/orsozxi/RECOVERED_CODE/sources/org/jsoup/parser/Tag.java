package org.jsoup.parser;

import androidx.core.app.NotificationCompat;
import com.facebook.internal.AnalyticsEvents;
import com.facebook.share.internal.ShareConstants;
import com.google.firebase.messaging.Constants;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import com.huawei.hms.push.constant.RemoteMessageConst;
import com.ibm.icu.text.DateFormat;
import com.sun.mail.imap.IMAPStore;
import java.util.HashMap;
import java.util.Map;
import org.apache.poi.ss.util.CellUtil;
import org.jsoup.helper.Validate;
import org.jsoup.internal.Normalizer;
import wseemann.media.FFmpegMediaMetadataRetriever;

/* loaded from: classes3.dex */
public class Tag implements Cloneable {
    private static final String[] blockTags;
    private static final String[] emptyTags;
    private static final String[] formListedTags;
    private static final String[] formSubmitTags;
    private static final String[] formatAsInlineTags;
    private static final String[] inlineTags;
    private static final String[] preserveWhitespaceTags;
    private static final Map<String, Tag> tags = new HashMap();
    private String normalName;
    private String tagName;
    private boolean isBlock = true;
    private boolean formatAsBlock = true;
    private boolean empty = false;
    private boolean selfClosing = false;
    private boolean preserveWhitespace = false;
    private boolean formList = false;
    private boolean formSubmit = false;

    static {
        String[] strArr = {"html", "head", "body", "frameset", "script", "noscript", AnalyticsEvents.PARAMETER_LIKE_VIEW_STYLE, "meta", "link", "title", "frame", "noframes", "section", "nav", "aside", "hgroup", "header", "footer", "p", "h1", "h2", "h3", "h4", "h5", "h6", "ul", "ol", "pre", "div", "blockquote", "hr", "address", "figure", "figcaption", "form", "fieldset", "ins", "del", "dl", "dt", "dd", "li", "table", ShareConstants.FEED_CAPTION_PARAM, "thead", "tfoot", "tbody", "colgroup", "col", "tr", "th", "td", AnalyticsEvents.PARAMETER_SHARE_DIALOG_CONTENT_VIDEO, "audio", "canvas", "details", "menu", "plaintext", "template", "article", "main", "svg", "math", "center"};
        blockTags = strArr;
        inlineTags = new String[]{"object", "base", CellUtil.FONT, "tt", "i", "b", "u", "big", "small", "em", "strong", "dfn", "code", "samp", "kbd", "var", "cite", "abbr", CrashHianalyticsData.TIME, "acronym", "mark", "ruby", "rt", "rp", "a", "img", "br", "wbr", "map", "q", "sub", "sup", "bdo", "iframe", "embed", "span", "input", "select", "textarea", Constants.ScionAnalytics.PARAM_LABEL, "button", "optgroup", "option", "legend", "datalist", "keygen", "output", NotificationCompat.CATEGORY_PROGRESS, "meter", "area", RemoteMessageConst.MessageBody.PARAM, "source", FFmpegMediaMetadataRetriever.METADATA_KEY_TRACK, "summary", IMAPStore.ID_COMMAND, "device", "area", "basefont", "bgsound", "menuitem", RemoteMessageConst.MessageBody.PARAM, "source", FFmpegMediaMetadataRetriever.METADATA_KEY_TRACK, "data", "bdi", DateFormat.SECOND};
        emptyTags = new String[]{"meta", "link", "base", "frame", "img", "br", "wbr", "embed", "hr", "input", "keygen", "col", IMAPStore.ID_COMMAND, "device", "area", "basefont", "bgsound", "menuitem", RemoteMessageConst.MessageBody.PARAM, "source", FFmpegMediaMetadataRetriever.METADATA_KEY_TRACK};
        formatAsInlineTags = new String[]{"title", "a", "p", "h1", "h2", "h3", "h4", "h5", "h6", "pre", "address", "li", "th", "td", "script", AnalyticsEvents.PARAMETER_LIKE_VIEW_STYLE, "ins", "del", DateFormat.SECOND};
        preserveWhitespaceTags = new String[]{"pre", "plaintext", "title", "textarea"};
        formListedTags = new String[]{"button", "fieldset", "input", "keygen", "object", "output", "select", "textarea"};
        formSubmitTags = new String[]{"input", "keygen", "object", "select", "textarea"};
        for (String str : strArr) {
            register(new Tag(str));
        }
        for (String str2 : inlineTags) {
            Tag tag = new Tag(str2);
            tag.isBlock = false;
            tag.formatAsBlock = false;
            register(tag);
        }
        for (String str3 : emptyTags) {
            Tag tag2 = tags.get(str3);
            Validate.notNull(tag2);
            tag2.empty = true;
        }
        for (String str4 : formatAsInlineTags) {
            Tag tag3 = tags.get(str4);
            Validate.notNull(tag3);
            tag3.formatAsBlock = false;
        }
        for (String str5 : preserveWhitespaceTags) {
            Tag tag4 = tags.get(str5);
            Validate.notNull(tag4);
            tag4.preserveWhitespace = true;
        }
        for (String str6 : formListedTags) {
            Tag tag5 = tags.get(str6);
            Validate.notNull(tag5);
            tag5.formList = true;
        }
        for (String str7 : formSubmitTags) {
            Tag tag6 = tags.get(str7);
            Validate.notNull(tag6);
            tag6.formSubmit = true;
        }
    }

    private Tag(String str) {
        this.tagName = str;
        this.normalName = Normalizer.lowerCase(str);
    }

    public String getName() {
        return this.tagName;
    }

    public String normalName() {
        return this.normalName;
    }

    public static Tag valueOf(String str, ParseSettings parseSettings) {
        Validate.notNull(str);
        Tag tag = tags.get(str);
        if (tag != null) {
            return tag;
        }
        String strNormalizeTag = parseSettings.normalizeTag(str);
        Validate.notEmpty(strNormalizeTag);
        String strLowerCase = Normalizer.lowerCase(strNormalizeTag);
        Tag tag2 = tags.get(strLowerCase);
        if (tag2 == null) {
            Tag tag3 = new Tag(strNormalizeTag);
            tag3.isBlock = false;
            return tag3;
        }
        if (!parseSettings.preserveTagCase() || strNormalizeTag.equals(strLowerCase)) {
            return tag2;
        }
        Tag tagClone = tag2.clone();
        tagClone.tagName = strNormalizeTag;
        return tagClone;
    }

    public static Tag valueOf(String str) {
        return valueOf(str, ParseSettings.preserveCase);
    }

    public boolean isBlock() {
        return this.isBlock;
    }

    public boolean formatAsBlock() {
        return this.formatAsBlock;
    }

    public boolean isInline() {
        return !this.isBlock;
    }

    public boolean isEmpty() {
        return this.empty;
    }

    public boolean isSelfClosing() {
        return this.empty || this.selfClosing;
    }

    public boolean isKnownTag() {
        return tags.containsKey(this.tagName);
    }

    public static boolean isKnownTag(String str) {
        return tags.containsKey(str);
    }

    public boolean preserveWhitespace() {
        return this.preserveWhitespace;
    }

    public boolean isFormListed() {
        return this.formList;
    }

    public boolean isFormSubmittable() {
        return this.formSubmit;
    }

    Tag setSelfClosing() {
        this.selfClosing = true;
        return this;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Tag)) {
            return false;
        }
        Tag tag = (Tag) obj;
        return this.tagName.equals(tag.tagName) && this.empty == tag.empty && this.formatAsBlock == tag.formatAsBlock && this.isBlock == tag.isBlock && this.preserveWhitespace == tag.preserveWhitespace && this.selfClosing == tag.selfClosing && this.formList == tag.formList && this.formSubmit == tag.formSubmit;
    }

    public int hashCode() {
        return (((((((((((((this.tagName.hashCode() * 31) + (this.isBlock ? 1 : 0)) * 31) + (this.formatAsBlock ? 1 : 0)) * 31) + (this.empty ? 1 : 0)) * 31) + (this.selfClosing ? 1 : 0)) * 31) + (this.preserveWhitespace ? 1 : 0)) * 31) + (this.formList ? 1 : 0)) * 31) + (this.formSubmit ? 1 : 0);
    }

    public String toString() {
        return this.tagName;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public Tag clone() {
        try {
            return (Tag) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void register(Tag tag) {
        tags.put(tag.tagName, tag);
    }
}
