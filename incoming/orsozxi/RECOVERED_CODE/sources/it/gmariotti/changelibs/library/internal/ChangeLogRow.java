package it.gmariotti.changelibs.library.internal;

import android.content.Context;
import it.gmariotti.changelibs.R;
import it.piapostolos.android.pinotes.utils.ConstantsBase;

/* loaded from: classes2.dex */
public class ChangeLogRow {
    public static final int BUGFIX = 1;
    public static final int DEFAULT = 0;
    public static final int IMPROVEMENT = 2;
    private boolean bulletedList;
    protected String changeDate;
    private String changeText;
    private String changeTextTitle;
    protected boolean header;
    private int type;
    protected int versionCode;
    protected String versionName;

    public void parseChangeText(String str) {
        if (str != null) {
            str = str.replaceAll("\\[", "<").replaceAll("\\]", ">");
        }
        setChangeText(str);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("header=" + this.header);
        sb.append(ConstantsBase.PROPERTIES_PARAMS_SEPARATOR);
        sb.append("versionName=" + this.versionName);
        sb.append(ConstantsBase.PROPERTIES_PARAMS_SEPARATOR);
        sb.append("versionCode=" + this.versionCode);
        sb.append(ConstantsBase.PROPERTIES_PARAMS_SEPARATOR);
        sb.append("bulletedList=" + this.bulletedList);
        sb.append(ConstantsBase.PROPERTIES_PARAMS_SEPARATOR);
        sb.append("changeText=" + this.changeText);
        return sb.toString();
    }

    public boolean isHeader() {
        return this.header;
    }

    public void setHeader(boolean z) {
        this.header = z;
    }

    public boolean isBulletedList() {
        return this.bulletedList;
    }

    public void setBulletedList(boolean z) {
        this.bulletedList = z;
    }

    public String getChangeText() {
        return this.changeText;
    }

    public String getChangeText(Context context) {
        String strReplaceAll;
        if (context == null) {
            return getChangeText();
        }
        int i = this.type;
        if (i == 1) {
            strReplaceAll = context.getResources().getString(R.string.changelog_row_prefix_bug).replaceAll("\\[", "<").replaceAll("\\]", ">");
        } else {
            strReplaceAll = i != 2 ? "" : context.getResources().getString(R.string.changelog_row_prefix_improvement).replaceAll("\\[", "<").replaceAll("\\]", ">");
        }
        return strReplaceAll + " " + this.changeText;
    }

    public void setChangeText(String str) {
        this.changeText = str;
    }

    public String getVersionName() {
        return this.versionName;
    }

    public void setVersionName(String str) {
        this.versionName = str;
    }

    public int getVersionCode() {
        return this.versionCode;
    }

    public void setVersionCode(int i) {
        this.versionCode = i;
    }

    public String getChangeTextTitle() {
        return this.changeTextTitle;
    }

    public void setChangeTextTitle(String str) {
        this.changeTextTitle = str;
    }

    public String getChangeDate() {
        return this.changeDate;
    }

    public void setChangeDate(String str) {
        this.changeDate = str;
    }

    public void setType(int i) {
        this.type = i;
    }
}
