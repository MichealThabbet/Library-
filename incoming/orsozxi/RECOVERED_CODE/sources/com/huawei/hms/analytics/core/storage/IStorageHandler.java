package com.huawei.hms.analytics.core.storage;

import java.util.List;

/* loaded from: classes2.dex */
public interface IStorageHandler {
    long count(String str);

    void deleteAll();

    void deleteByTag(String str);

    void deleteByTagType(String str, String str2);

    void deleteEvents(List<Event> list);

    long insert(Event event);

    void insertEx(List<Event> list);

    List<Event> readBySql(String str);

    List<Event> readEvents(String str);

    List<Event> readEvents(String str, String str2);

    List<Event> readEvents(String str, String str2, int i);
}
