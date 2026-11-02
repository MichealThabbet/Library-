package org.jsoup.nodes;

import com.facebook.internal.NativeProtocol;
import com.facebook.share.internal.ShareConstants;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.http.client.methods.HttpPost;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.helper.HttpConnection;
import org.jsoup.helper.Validate;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;

/* loaded from: classes3.dex */
public class FormElement extends Element {
    private final Elements elements;

    public FormElement(Tag tag, String str, Attributes attributes) {
        super(tag, str, attributes);
        this.elements = new Elements();
    }

    public Elements elements() {
        return this.elements;
    }

    public FormElement addElement(Element element) {
        this.elements.add(element);
        return this;
    }

    @Override // org.jsoup.nodes.Node
    protected void removeChild(Node node) {
        super.removeChild(node);
        this.elements.remove(node);
    }

    public Connection submit() {
        String strAbsUrl = hasAttr(NativeProtocol.WEB_DIALOG_ACTION) ? absUrl(NativeProtocol.WEB_DIALOG_ACTION) : baseUri();
        Validate.notEmpty(strAbsUrl, "Could not determine a form action URL for submit. Ensure you set a base URI when parsing.");
        return Jsoup.connect(strAbsUrl).data(formData()).method(attr(FirebaseAnalytics.Param.METHOD).toUpperCase().equals(HttpPost.METHOD_NAME) ? Connection.Method.POST : Connection.Method.GET);
    }

    public List<Connection.KeyVal> formData() {
        Element elementFirst;
        ArrayList arrayList = new ArrayList();
        Iterator<Element> it2 = this.elements.iterator();
        while (it2.hasNext()) {
            Element next = it2.next();
            if (next.tag().isFormSubmittable() && !next.hasAttr("disabled")) {
                String strAttr = next.attr("name");
                if (strAttr.length() != 0) {
                    String strAttr2 = next.attr(ShareConstants.MEDIA_TYPE);
                    if (!strAttr2.equalsIgnoreCase("button")) {
                        if ("select".equals(next.normalName())) {
                            boolean z = false;
                            Iterator<Element> it3 = next.select("option[selected]").iterator();
                            while (it3.hasNext()) {
                                arrayList.add(HttpConnection.KeyVal.create(strAttr, it3.next().val()));
                                z = true;
                            }
                            if (!z && (elementFirst = next.select("option").first()) != null) {
                                arrayList.add(HttpConnection.KeyVal.create(strAttr, elementFirst.val()));
                            }
                        } else if ("checkbox".equalsIgnoreCase(strAttr2) || "radio".equalsIgnoreCase(strAttr2)) {
                            if (next.hasAttr("checked")) {
                                arrayList.add(HttpConnection.KeyVal.create(strAttr, next.val().length() > 0 ? next.val() : "on"));
                            }
                        } else {
                            arrayList.add(HttpConnection.KeyVal.create(strAttr, next.val()));
                        }
                    }
                }
            }
        }
        return arrayList;
    }

    @Override // org.jsoup.nodes.Element, org.jsoup.nodes.Node
    /* renamed from: clone */
    public FormElement mo1364clone() {
        return (FormElement) super.mo1364clone();
    }
}
