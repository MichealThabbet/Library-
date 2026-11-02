package com.tbruyelle.rxpermissions;

import com.ibm.icu.impl.PatternTokenizer;

/* loaded from: classes2.dex */
public class Permission {
    public final boolean granted;
    public final String name;

    public Permission(String str, boolean z) {
        this.name = str;
        this.granted = z;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Permission permission = (Permission) obj;
        if (this.granted != permission.granted) {
            return false;
        }
        return this.name.equals(permission.name);
    }

    public int hashCode() {
        return (this.name.hashCode() * 31) + (this.granted ? 1 : 0);
    }

    public String toString() {
        return "Permission{name='" + this.name + PatternTokenizer.SINGLE_QUOTE + ", granted=" + this.granted + '}';
    }
}
