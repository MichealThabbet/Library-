package it.gmariotti.changelibs.library.parser;

import android.content.Context;
import android.util.Log;
import android.util.Xml;
import com.facebook.internal.ServerProtocol;
import it.gmariotti.changelibs.library.Constants;
import it.gmariotti.changelibs.library.Util;
import it.gmariotti.changelibs.library.internal.ChangeLog;
import it.gmariotti.changelibs.library.internal.ChangeLogAdapter;
import it.gmariotti.changelibs.library.internal.ChangeLogException;
import it.gmariotti.changelibs.library.internal.ChangeLogRow;
import it.gmariotti.changelibs.library.internal.ChangeLogRowHeader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes2.dex */
public class XmlParser extends BaseParser {
    private static final String ATTRIBUTE_BULLETEDLIST = "bulletedList";
    private static final String ATTRIBUTE_CHANGEDATE = "changeDate";
    private static final String ATTRIBUTE_CHANGETEXTTITLE = "changeTextTitle";
    private static final String ATTRIBUTE_VERSIONCODE = "versionCode";
    private static final String ATTRIBUTE_VERSIONNAME = "versionName";
    private static String TAG = "XmlParser";
    private static final String TAG_CHANGELOG = "changelog";
    private static final String TAG_CHANGELOGBUG = "changelogbug";
    private static final String TAG_CHANGELOGIMPROVEMENT = "changelogimprovement";
    private static final String TAG_CHANGELOGTEXT = "changelogtext";
    private static final String TAG_CHANGELOGVERSION = "changelogversion";
    private static List<String> mChangeLogTags = new ArrayList<String>() { // from class: it.gmariotti.changelibs.library.parser.XmlParser.1
        {
            add(XmlParser.TAG_CHANGELOGBUG);
            add(XmlParser.TAG_CHANGELOGIMPROVEMENT);
            add(XmlParser.TAG_CHANGELOGTEXT);
        }
    };
    protected ChangeLogAdapter mChangeLogAdapter;
    private int mChangeLogFileResourceId;
    private String mChangeLogFileResourceUrl;

    public XmlParser(Context context) {
        super(context);
        this.mChangeLogFileResourceId = Constants.mChangeLogFileResourceId;
        this.mChangeLogFileResourceUrl = null;
    }

    public XmlParser(Context context, int i) {
        super(context);
        this.mChangeLogFileResourceId = Constants.mChangeLogFileResourceId;
        this.mChangeLogFileResourceUrl = null;
        this.mChangeLogFileResourceId = i;
    }

    public XmlParser(Context context, String str) {
        super(context);
        this.mChangeLogFileResourceId = Constants.mChangeLogFileResourceId;
        this.mChangeLogFileResourceUrl = null;
        this.mChangeLogFileResourceUrl = str;
    }

    @Override // it.gmariotti.changelibs.library.parser.BaseParser
    public ChangeLog readChangeLogFile() throws Exception {
        InputStream inputStreamOpenRawResource;
        try {
            if (this.mChangeLogFileResourceUrl != null) {
                inputStreamOpenRawResource = Util.isConnected(this.mContext) ? new URL(this.mChangeLogFileResourceUrl).openStream() : null;
            } else {
                inputStreamOpenRawResource = this.mContext.getResources().openRawResource(this.mChangeLogFileResourceId);
            }
            if (inputStreamOpenRawResource != null) {
                XmlPullParser xmlPullParserNewPullParser = Xml.newPullParser();
                xmlPullParserNewPullParser.setFeature("http://xmlpull.org/v1/doc/features.html#process-namespaces", false);
                xmlPullParserNewPullParser.setInput(inputStreamOpenRawResource, null);
                xmlPullParserNewPullParser.nextTag();
                ChangeLog changeLog = new ChangeLog();
                readChangeLogNode(xmlPullParserNewPullParser, changeLog);
                inputStreamOpenRawResource.close();
                return changeLog;
            }
            Log.d(TAG, "Changelog.xml not found");
            throw new ChangeLogException("Changelog.xml not found");
        } catch (IOException e) {
            Log.d(TAG, "Error i/o with changelog.xml", e);
            throw e;
        } catch (XmlPullParserException e2) {
            Log.d(TAG, "XmlPullParseException while parsing changelog file", e2);
            throw e2;
        }
    }

    protected void readChangeLogNode(XmlPullParser xmlPullParser, ChangeLog changeLog) throws Exception {
        if (xmlPullParser == null || changeLog == null) {
            return;
        }
        xmlPullParser.require(2, null, TAG_CHANGELOG);
        String attributeValue = xmlPullParser.getAttributeValue(null, ATTRIBUTE_BULLETEDLIST);
        if (attributeValue == null || attributeValue.equals(ServerProtocol.DIALOG_RETURN_SCOPES_TRUE)) {
            changeLog.setBulletedList(true);
            this.bulletedList = true;
        } else {
            changeLog.setBulletedList(false);
            this.bulletedList = false;
        }
        while (xmlPullParser.next() != 3) {
            if (xmlPullParser.getEventType() == 2 && xmlPullParser.getName().equals(TAG_CHANGELOGVERSION)) {
                readChangeLogVersionNode(xmlPullParser, changeLog);
            }
        }
    }

    protected void readChangeLogVersionNode(XmlPullParser xmlPullParser, ChangeLog changeLog) throws Exception {
        if (xmlPullParser == null) {
            return;
        }
        xmlPullParser.require(2, null, TAG_CHANGELOGVERSION);
        String attributeValue = xmlPullParser.getAttributeValue(null, ATTRIBUTE_VERSIONNAME);
        String attributeValue2 = xmlPullParser.getAttributeValue(null, ATTRIBUTE_VERSIONCODE);
        int i = 0;
        if (attributeValue2 != null) {
            try {
                i = Integer.parseInt(attributeValue2);
            } catch (NumberFormatException unused) {
                Log.w(TAG, "Error while parsing versionCode.It must be a numeric value. Check you file.");
            }
        }
        String attributeValue3 = xmlPullParser.getAttributeValue(null, ATTRIBUTE_CHANGEDATE);
        if (attributeValue == null) {
            throw new ChangeLogException("VersionName required in changeLogVersion node");
        }
        ChangeLogRowHeader changeLogRowHeader = new ChangeLogRowHeader();
        changeLogRowHeader.setVersionName(attributeValue);
        changeLogRowHeader.setChangeDate(attributeValue3);
        changeLog.addRow(changeLogRowHeader);
        while (xmlPullParser.next() != 3) {
            if (xmlPullParser.getEventType() == 2) {
                if (mChangeLogTags.contains(xmlPullParser.getName())) {
                    readChangeLogRowNode(xmlPullParser, changeLog, attributeValue, i);
                }
            }
        }
    }

    private void readChangeLogRowNode(XmlPullParser xmlPullParser, ChangeLog changeLog, String str, int i) throws Exception {
        if (xmlPullParser == null) {
            return;
        }
        String name = xmlPullParser.getName();
        ChangeLogRow changeLogRow = new ChangeLogRow();
        changeLogRow.setVersionName(str);
        changeLogRow.setVersionCode(i);
        String attributeValue = xmlPullParser.getAttributeValue(null, ATTRIBUTE_CHANGETEXTTITLE);
        if (attributeValue != null) {
            changeLogRow.setChangeTextTitle(attributeValue);
        }
        String attributeValue2 = xmlPullParser.getAttributeValue(null, ATTRIBUTE_BULLETEDLIST);
        if (attributeValue2 != null) {
            if (attributeValue2.equals(ServerProtocol.DIALOG_RETURN_SCOPES_TRUE)) {
                changeLogRow.setBulletedList(true);
            } else {
                changeLogRow.setBulletedList(false);
            }
        } else {
            changeLogRow.setBulletedList(this.bulletedList);
        }
        if (xmlPullParser.next() == 4) {
            String text = xmlPullParser.getText();
            if (text == null) {
                throw new ChangeLogException("ChangeLogText required in changeLogText node");
            }
            changeLogRow.parseChangeText(text);
            changeLogRow.setType(name.equalsIgnoreCase(TAG_CHANGELOGBUG) ? 1 : name.equalsIgnoreCase(TAG_CHANGELOGIMPROVEMENT) ? 2 : 0);
            xmlPullParser.nextTag();
        }
        changeLog.addRow(changeLogRow);
    }

    public void setChangeLogAdapter(ChangeLogAdapter changeLogAdapter) {
        this.mChangeLogAdapter = changeLogAdapter;
    }
}
