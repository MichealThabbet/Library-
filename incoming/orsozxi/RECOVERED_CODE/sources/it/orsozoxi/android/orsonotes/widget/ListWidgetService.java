package it.orsozoxi.android.orsonotes.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/* loaded from: classes3.dex */
public class ListWidgetService extends RemoteViewsService {
    @Override // android.widget.RemoteViewsService
    public RemoteViewsService.RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListRemoteViewsFactory(getApplication(), intent);
    }
}
