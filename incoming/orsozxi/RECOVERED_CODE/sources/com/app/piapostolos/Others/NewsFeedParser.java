package com.app.piapostolos.Others;

import com.app.piapostolos.Adapters.DBAdapter;
import com.app.piapostolos.Beans.RSSFeed;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import net.fortuna.ical4j.model.property.RequestStatus;
import org.apache.http.client.methods.HttpGet;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

/* loaded from: classes.dex */
public class NewsFeedParser {
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

    public NewsFeedParser(String str) {
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
            this.parser.setInput(inputStreamDownloadUrl, null);
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
                    if (this.tagName.equals("description")) {
                        this.description = this.parser.nextText().toString();
                    }
                    if (this.tagName.equals("category")) {
                        this.category = this.parser.nextText().toString();
                    }
                    if (this.tagName.equals("pubDate")) {
                        this.pubDate = this.parser.nextText().toString();
                    }
                    if (this.tagName.equals("guid")) {
                        this.guid = this.parser.nextText().toString();
                    }
                    if (this.tagName.equals("content:encoded")) {
                        this.feedburner = this.parser.nextText().toString();
                        System.out.println(DBAdapter.imagelink + this.feedburner);
                    }
                } else if (eventType == 3) {
                    if (name.equals("channel")) {
                        z = true;
                    } else if (this.tagName.equals("item")) {
                        RSSFeed rSSFeed = new RSSFeed(this.title, this.new_link, this.description, this.category, this.pubDate, this.guid, this.feedburner);
                        this.rssFeed = rSSFeed;
                        this.rssFeedList.add(rSSFeed);
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

    /* JADX WARN: Removed duplicated region for block: B:38:0x011d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public java.lang.String manage(java.lang.String r13) {
        /*
            Method dump skipped, instructions count: 805
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.piapostolos.Others.NewsFeedParser.manage(java.lang.String):java.lang.String");
    }

    public String contenttag(String str) throws XmlPullParserException, IOException {
        System.out.println("Start document+" + str);
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
                    this.guid = xmlPullParserNewPullParser.getAttributeValue(null, "src");
                    xmlPullParserNewPullParser.nextTag();
                } else if (name.equals("iframe")) {
                    xmlPullParserNewPullParser.getAttributeValue(null, "src");
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
}
