package it.gmariotti.changelibs.library.internal;

import it.piapostolos.android.pinotes.utils.ConstantsBase;

/* loaded from: classes2.dex */
public class ChangeLogRowHeader extends ChangeLogRow {
    public ChangeLogRowHeader() {
        setHeader(true);
        setBulletedList(false);
        setChangeTextTitle(null);
    }

    @Override // it.gmariotti.changelibs.library.internal.ChangeLogRow
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("header=" + this.header);
        sb.append(ConstantsBase.PROPERTIES_PARAMS_SEPARATOR);
        sb.append("versionName=" + this.versionName);
        sb.append(ConstantsBase.PROPERTIES_PARAMS_SEPARATOR);
        sb.append("changeDate=" + this.changeDate);
        return sb.toString();
    }
}
