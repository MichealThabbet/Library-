package it.orsozoxi.android.orsonotes.widget;

import android.app.PendingIntent;
import android.content.Context;
import android.util.SparseArray;
import android.widget.RemoteViews;
import it.orsozoxi.android.orsonotes.R;

/* loaded from: classes3.dex */
public class SimpleWidgetProvider extends WidgetProvider {
    @Override // it.orsozoxi.android.orsonotes.widget.WidgetProvider
    protected RemoteViews getRemoteViews(Context context, int i, boolean z, boolean z2, SparseArray<PendingIntent> sparseArray) {
        if (z) {
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout_small);
            remoteViews.setOnClickPendingIntent(R.id.list, sparseArray.get(R.id.list));
            return remoteViews;
        }
        RemoteViews remoteViews2 = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        remoteViews2.setOnClickPendingIntent(R.id.add, sparseArray.get(R.id.add));
        remoteViews2.setOnClickPendingIntent(R.id.list, sparseArray.get(R.id.list));
        return remoteViews2;
    }
}
