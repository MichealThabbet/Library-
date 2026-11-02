package com.robohorse.gpversionchecker.domain;

import java.io.Serializable;

/* loaded from: classes2.dex */
public class Version implements Serializable {
    private static final String SEPARATOR = System.getProperty("line.separator");
    private static final long serialVersionUID = 2635;
    private String changes;
    private String description;
    private boolean needToUpdate;
    private String newVersionCode;
    private String url;

    public String getNewVersionCode() {
        String str = this.newVersionCode;
        return str == null ? "" : str;
    }

    @Deprecated
    public String getChanges() {
        String str = this.changes;
        return str == null ? "" : str;
    }

    public boolean isNeedToUpdate() {
        return this.needToUpdate;
    }

    public String getUrl() {
        String str = this.url;
        return str == null ? "" : str;
    }

    public String getDescription() {
        String str = this.description;
        return str == null ? "" : str;
    }

    public static class Builder {
        private Version instance;

        public Builder(Version version) {
            this.instance = version;
        }

        public Builder() {
            this.instance = new Version();
        }

        public Builder setNewVersionCode(String str) {
            this.instance.newVersionCode = str;
            return this;
        }

        public Builder setChanges(String str) {
            this.instance.changes = str;
            return this;
        }

        public Builder setNeedToUpdate(boolean z) {
            this.instance.needToUpdate = z;
            return this;
        }

        public Builder setUrl(String str) {
            this.instance.url = str;
            return this;
        }

        public Builder setDescription(String str) {
            this.instance.description = str;
            return this;
        }

        public Version build() {
            return this.instance;
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(SEPARATOR);
        sb.append("Google play app version: " + getNewVersionCode() + SEPARATOR);
        sb.append("url: " + getUrl() + SEPARATOR);
        sb.append("description: " + getDescription() + SEPARATOR);
        sb.append("with changes: " + getChanges() + SEPARATOR);
        StringBuilder sb2 = new StringBuilder();
        sb2.append("update required: ");
        sb2.append(isNeedToUpdate());
        sb.append(sb2.toString());
        return sb.toString();
    }
}
