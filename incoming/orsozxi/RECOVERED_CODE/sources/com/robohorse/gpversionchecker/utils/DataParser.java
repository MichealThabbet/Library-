package com.robohorse.gpversionchecker.utils;

import android.text.Html;
import android.text.TextUtils;
import com.robohorse.gpversionchecker.domain.Version;
import java.util.Iterator;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/* loaded from: classes2.dex */
public class DataParser {
    private static final String DIV_CHANGES = "div[class=DWPxHb]";
    private static final String DIV_DESCRIPTION = "div[itemprop=description]";
    private static final String DIV_VERSION = "Current Version";

    public Version parse(Document document, String str) {
        String strText = "";
        if (document != null) {
            Iterator<Element> it2 = document.getElementsContainingOwnText(DIV_VERSION).iterator();
            while (it2.hasNext()) {
                Element next = it2.next();
                if (next.siblingElements() != null) {
                    Iterator<Element> it3 = next.siblingElements().iterator();
                    while (it3.hasNext()) {
                        strText = it3.next().text();
                    }
                }
            }
        }
        String strValueOf = String.valueOf(Html.fromHtml(document.select(DIV_DESCRIPTION).html()));
        Elements elementsSelect = document.select(DIV_CHANGES);
        String str2 = null;
        if (elementsSelect != null) {
            Elements elementsSelect2 = elementsSelect.select(DIV_CHANGES);
            if (!elementsSelect2.isEmpty()) {
                String strValueOf2 = String.valueOf(Html.fromHtml(elementsSelect2.last().html()));
                if (!TextUtils.equals(strValueOf2, strValueOf)) {
                    str2 = strValueOf2;
                }
            }
        }
        return new Version.Builder().setNewVersionCode(strText).setChanges(str2).setUrl(str).setDescription(strValueOf).build();
    }

    public static String replaceNonDigits(String str) {
        return str.replaceAll("[^\\d.]", "").replace(".", "");
    }
}
