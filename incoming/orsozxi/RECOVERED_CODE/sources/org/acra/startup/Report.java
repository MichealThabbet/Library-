package org.acra.startup;

import java.io.File;

/* loaded from: classes3.dex */
public class Report {
    private final boolean approved;
    private final File file;
    private boolean delete = false;
    private boolean approve = false;

    public Report(File file, boolean z) {
        this.file = file;
        this.approved = z;
    }

    public File getFile() {
        return this.file;
    }

    public boolean isApproved() {
        return this.approved;
    }

    public void delete() {
        this.delete = true;
    }

    public void approve() {
        this.approve = true;
    }

    boolean isDelete() {
        return this.delete;
    }

    boolean isApprove() {
        return this.approve;
    }
}
