package it.feio.android.omninotes.commons.models;

import java.util.Calendar;

/* loaded from: classes2.dex */
public class BaseCategory {
    private String color;
    private int count;
    private String description;
    private Long id;
    private String name;

    public BaseCategory() {
        this.id = Long.valueOf(Calendar.getInstance().getTimeInMillis());
    }

    public BaseCategory(BaseCategory baseCategory) {
        this(baseCategory.getId(), baseCategory.getName(), baseCategory.getDescription(), baseCategory.getColor());
    }

    public BaseCategory(Long l, String str, String str2, String str3) {
        this(l, str, str2, str3, 0);
    }

    public BaseCategory(Long l, String str, String str2, String str3, int i) {
        this.id = l;
        this.name = str;
        this.description = str2;
        this.color = str3;
        this.count = i;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long l) {
        this.id = l;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String str) {
        this.description = str;
    }

    public String getColor() {
        return this.color;
    }

    public void setColor(String str) {
        this.color = str;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int i) {
        this.count = i;
    }

    public boolean equals(Object obj) {
        if (obj == null || !obj.getClass().equals(getClass())) {
            return false;
        }
        try {
            BaseCategory baseCategory = (BaseCategory) obj;
            if (getColor() != baseCategory.getColor() && !getColor().equals(baseCategory.getColor())) {
                return false;
            }
            if (getDescription() != baseCategory.getDescription() && !getDescription().equals(baseCategory.getDescription())) {
                return false;
            }
            if (getName() != baseCategory.getName() && !getName().equals(baseCategory.getName())) {
                return false;
            }
            if (getId() != baseCategory.getId()) {
                if (!getId().equals(baseCategory.getId())) {
                    return false;
                }
            }
            return true;
        } catch (ClassCastException unused) {
            return false;
        }
    }
}
