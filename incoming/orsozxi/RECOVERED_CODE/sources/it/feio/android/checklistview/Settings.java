package it.feio.android.checklistview;

import it.feio.android.checklistview.interfaces.Constants;

/* loaded from: classes2.dex */
public class Settings {
    public static final int CHECKED_HOLD = 0;
    public static final int CHECKED_ON_BOTTOM = 1;
    public static final int CHECKED_ON_TOP_OF_CHECKED = 2;
    private String linesSeparator = Constants.LINES_SEPARATOR;
    private boolean showDeleteIcon = true;
    private boolean keepChecked = true;
    private boolean showChecks = false;
    private boolean showHintItem = false;
    private String newEntryHint = "";
    private int moveCheckedOnBottom = 0;
    private boolean dragEnabled = true;
    private boolean dragVibrationEnabled = false;
    private int dragVibrationDuration = 25;

    public String getLinesSeparator() {
        return this.linesSeparator;
    }

    public void setLinesSeparator(String str) {
        this.linesSeparator = str;
    }

    public boolean getShowDeleteIcon() {
        return this.showDeleteIcon;
    }

    public void setShowDeleteIcon(boolean z) {
        this.showDeleteIcon = z;
    }

    public boolean getKeepChecked() {
        return this.keepChecked;
    }

    public void setKeepChecked(boolean z) {
        this.keepChecked = z;
    }

    public boolean getShowChecks() {
        return this.showChecks;
    }

    public void setShowChecks(boolean z) {
        this.showChecks = z;
    }

    public boolean getShowHintItem() {
        return this.showHintItem;
    }

    public void setShowHintItem(boolean z) {
        this.showHintItem = z;
    }

    public String getNewEntryHint() {
        return this.newEntryHint;
    }

    public void setNewEntryHint(String str) {
        this.newEntryHint = str;
    }

    public int getMoveCheckedOnBottom() {
        return this.moveCheckedOnBottom;
    }

    public void setMoveCheckedOnBottom(int i) {
        this.moveCheckedOnBottom = i;
    }

    public boolean getDragEnabled() {
        return this.dragEnabled;
    }

    public void setDragEnabled(boolean z) {
        this.dragEnabled = z;
    }

    public boolean getDragVibrationEnabled() {
        return this.dragVibrationEnabled;
    }

    public void setDragVibrationEnabled(boolean z) {
        this.dragVibrationEnabled = z;
    }

    public int getDragVibrationDuration() {
        return this.dragVibrationDuration;
    }

    public void setDragVibrationDuration(int i) {
        this.dragVibrationDuration = i;
    }
}
