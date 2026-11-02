package it.feio.android.springpadimporter.models;

import it.feio.android.springpadimporter.utils.Utils;
import java.util.Date;

/* loaded from: classes2.dex */
public class SpringpadComments {
    private String comment;
    private String commenter;
    private String date;

    public String getCommenter() {
        return this.commenter;
    }

    public Date getDate() {
        return Utils.getDate(this.date);
    }

    public String getComment() {
        return this.comment;
    }
}
