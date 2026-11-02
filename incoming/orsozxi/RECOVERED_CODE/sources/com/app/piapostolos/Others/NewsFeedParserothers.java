package com.app.piapostolos.Others;

import android.net.Uri;
import android.text.Html;
import com.app.piapostolos.Adapters.DBAdapter;
import com.app.piapostolos.Beans.RSSFeed;
import com.huawei.hms.framework.common.ContainerUtils;
import com.ibm.icu.text.DateFormat;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import net.fortuna.ical4j.model.property.RequestStatus;
import org.apache.http.HttpHost;
import org.apache.http.client.methods.HttpGet;
import org.jsoup.Jsoup;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

/* loaded from: classes.dex */
public class NewsFeedParserothers {
    public static final String CATEGORY = "category";
    public static final String CHANNEL = "channel";
    public static final String DESCRIPTION = "description";
    public static final String FEEDBURNERORIGLINK = "content:encoded";
    public static final String GUID = "guid";
    public static final String ITEM = "item";
    public static final String LINK = "link";
    public static final String PUBLISHEDDATE = "pubDate";
    public static final String TITLE = "title";
    private String category;
    private String description;
    private XmlPullParserFactory factory;
    private String feedburner;
    private String guid;
    private String link;
    String new_link = "";
    private XmlPullParser parser;
    private String pubDate;
    private RSSFeed rssFeed;
    private List<RSSFeed> rssFeedList;
    private String tagName;
    private String title;
    private InputStream urlStream;
    private String urlString;

    public NewsFeedParserothers(String str) {
        this.urlString = str;
    }

    public static InputStream downloadUrl(String str) throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
        httpURLConnection.setRequestMethod(HttpGet.METHOD_NAME);
        httpURLConnection.setDoInput(true);
        httpURLConnection.connect();
        return httpURLConnection.getInputStream();
    }

    public List<RSSFeed> parse() throws XmlPullParserException, IOException {
        try {
            XmlPullParserFactory xmlPullParserFactoryNewInstance = XmlPullParserFactory.newInstance();
            this.factory = xmlPullParserFactoryNewInstance;
            this.parser = xmlPullParserFactoryNewInstance.newPullParser();
            InputStream inputStreamDownloadUrl = downloadUrl(this.urlString);
            this.urlStream = inputStreamDownloadUrl;
            this.parser.setInput(inputStreamDownloadUrl, "UTF-8");
            int eventType = this.parser.getEventType();
            boolean z = false;
            this.rssFeed = new RSSFeed();
            this.rssFeedList = new ArrayList();
            while (eventType != 1 && !z) {
                String name = this.parser.getName();
                this.tagName = name;
                if (eventType == 2) {
                    if (name.equals("item")) {
                        this.rssFeed = new RSSFeed();
                    }
                    if (this.tagName.equals("title")) {
                        this.title = this.parser.nextText().toString();
                    }
                    this.tagName.equals("link");
                    this.tagName.equals("description");
                    if (this.tagName.equals("category")) {
                        this.category += this.parser.nextText() + " - ";
                    }
                    if (this.tagName.equals("pubDate")) {
                        this.pubDate = this.parser.nextText().toString();
                    }
                    if (this.tagName.equals("guid")) {
                        this.guid = this.parser.nextText().toString();
                    }
                    if (this.tagName.equals("content:encoded")) {
                        String string = this.parser.nextText().toString();
                        this.feedburner = string;
                        this.feedburner = contenttag(string.replaceAll("<br>", ""));
                    }
                } else if (eventType == 3) {
                    if (name.equals("channel")) {
                        z = true;
                    } else if (this.tagName.equals("item")) {
                        this.rssFeed = new RSSFeed(this.title, this.new_link, this.description, this.category, this.pubDate, this.guid, this.feedburner);
                        System.out.println("category:-" + this.category);
                        this.rssFeedList.add(this.rssFeed);
                        this.new_link = "";
                    }
                }
                eventType = this.parser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.rssFeedList;
    }

    public String ss(String str) {
        String[] strArrSplit = str.split("/");
        String str2 = "";
        for (int i = 0; i < strArrSplit.length - 1; i++) {
            str2 = str2 + strArrSplit[i] + "/";
        }
        String str3 = str2 + Uri.encode(strArrSplit[strArrSplit.length - 1]);
        System.out.println(DBAdapter.imagelink + str3);
        return str3;
    }

    public String imgparse(String str) throws UnsupportedEncodingException {
        System.out.println("urllllll" + str);
        if (str.equals(null)) {
            return null;
        }
        String[] strArrSplit = str.split("/");
        String str2 = "";
        for (int i = 0; i < strArrSplit.length - 1; i++) {
            str2 = str2 + strArrSplit[i] + "/";
            System.out.println(i + "web2222" + strArrSplit[i]);
        }
        System.out.println("web222" + str2);
        System.out.println("web2222" + strArrSplit[strArrSplit.length - 1]);
        String strEncode = URLEncoder.encode(strArrSplit[strArrSplit.length - 1]);
        String str3 = str2 + strEncode;
        System.out.println("besho3y" + strEncode);
        System.out.println("web4422" + str3);
        return str3;
    }

    public static String html2text(String str) {
        return Jsoup.parse(str).text();
    }

    public String contentTag(String str) throws XmlPullParserException, IOException {
        String strReplace = str.replace("&nbsp;", " ");
        if (!strReplace.contains("img")) {
            return Html.fromHtml(strReplace).toString();
        }
        XmlPullParserFactory xmlPullParserFactoryNewInstance = XmlPullParserFactory.newInstance();
        xmlPullParserFactoryNewInstance.setNamespaceAware(true);
        XmlPullParser xmlPullParserNewPullParser = xmlPullParserFactoryNewInstance.newPullParser();
        xmlPullParserNewPullParser.setInput(new StringReader(strReplace));
        xmlPullParserNewPullParser.next();
        String name = xmlPullParserNewPullParser.getName();
        xmlPullParserNewPullParser.getAttributeValue(null, "src");
        if (!name.equals("img")) {
            return "";
        }
        String attributeValue = xmlPullParserNewPullParser.getAttributeValue(null, "src");
        System.out.println("img link " + attributeValue);
        this.guid = attributeValue;
        this.guid = ss(attributeValue);
        return html2text(strReplace).toString();
    }

    public String contenttag(String str) throws XmlPullParserException, IOException {
        String strReplace = str.replace("&nbsp;", " ");
        XmlPullParserFactory xmlPullParserFactoryNewInstance = XmlPullParserFactory.newInstance();
        xmlPullParserFactoryNewInstance.setNamespaceAware(true);
        XmlPullParser xmlPullParserNewPullParser = xmlPullParserFactoryNewInstance.newPullParser();
        xmlPullParserNewPullParser.setInput(new StringReader(strReplace));
        String str2 = "";
        String str3 = "";
        for (int eventType = xmlPullParserNewPullParser.getEventType(); eventType != 1; eventType = xmlPullParserNewPullParser.next()) {
            System.out.println("1");
            if (eventType == 0) {
                System.out.println("Start document");
            } else if (eventType == 1) {
                System.out.println("End document");
            } else if (eventType == 2) {
                String str4 = str3 + xmlPullParserNewPullParser.getName();
                String name = xmlPullParserNewPullParser.getName();
                xmlPullParserNewPullParser.getAttributeValue(null, "src");
                if (name.equals("img")) {
                    String attributeValue = xmlPullParserNewPullParser.getAttributeValue(null, "src");
                    this.guid = attributeValue;
                    this.guid = ss(attributeValue);
                    xmlPullParserNewPullParser.nextTag();
                } else if (name.equals("iframe")) {
                    String attributeValue2 = xmlPullParserNewPullParser.getAttributeValue(null, "src");
                    if (attributeValue2.contains("youtube")) {
                        String[] strArrSplit = attributeValue2.split("/");
                        String str5 = "http://www.youtube.com/watch?v=" + strArrSplit[strArrSplit.length - 1];
                        this.new_link = str5;
                        this.guid = getYoutubeThumbnailUrl(str5);
                        System.out.println("youtube link " + attributeValue2);
                        System.out.println("youtube link " + this.guid);
                    }
                    xmlPullParserNewPullParser.nextTag();
                }
                System.out.println("Start tag " + xmlPullParserNewPullParser.getName());
                str3 = str4;
            } else if (eventType == 0) {
                System.out.println(RequestStatus.SUCCESS);
            } else if (eventType == 3) {
                System.out.println("End tag " + xmlPullParserNewPullParser.getName());
            } else if (eventType == 4) {
                System.out.println("Text " + xmlPullParserNewPullParser.getText());
                System.out.println(RequestStatus.CLIENT_ERROR);
                str2 = str2 + xmlPullParserNewPullParser.getText();
            }
        }
        return str2;
    }

    public static String getYoutubeThumbnailUrl(String str) throws UnsupportedEncodingException {
        if (str != null && str.trim().length() > 0 && str.startsWith(HttpHost.DEFAULT_SCHEME_NAME) && str.contains("youtube")) {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            try {
                String strDecode = URLDecoder.decode(str, "UTF-8");
                if (strDecode.indexOf(63) > 0) {
                    String[] strArrSplit = strDecode.split("\\?");
                    String str2 = strArrSplit[strArrSplit.length - 1];
                    if (str2.indexOf(38) > 0) {
                        for (String str3 : str2.split(ContainerUtils.FIELD_DELIMITER)) {
                            String[] strArrSplit2 = str3.split(ContainerUtils.KEY_VALUE_DELIMITER);
                            linkedHashMap.put(URLDecoder.decode(strArrSplit2[0], "UTF-8"), URLDecoder.decode(strArrSplit2[1], "UTF-8"));
                        }
                    } else {
                        String[] strArrSplit3 = str2.split(ContainerUtils.KEY_VALUE_DELIMITER);
                        linkedHashMap.put(URLDecoder.decode(strArrSplit3[0], "UTF-8"), URLDecoder.decode(strArrSplit3[1], "UTF-8"));
                    }
                }
                int size = linkedHashMap.size();
                if (size != 0 && linkedHashMap.containsKey(DateFormat.ABBR_GENERIC_TZ)) {
                    return "http://img.youtube.com/vi/" + ((String) linkedHashMap.get(DateFormat.ABBR_GENERIC_TZ)) + "/default.jpg";
                }
                if (size > 0) {
                    strDecode = strDecode.substring(0, strDecode.indexOf("?", 0));
                }
                int iIndexOf = strDecode.indexOf("/v/", 0) + 3;
                int iIndexOf2 = strDecode.indexOf("?", 0);
                if (iIndexOf2 == -1) {
                    iIndexOf2 = strDecode.length();
                }
                return "http://img.youtube.com/vi/" + strDecode.substring(iIndexOf, iIndexOf2) + "/default.jpg";
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "http://img.youtube.com/vi/noimagefound/default.jpg";
    }
}
