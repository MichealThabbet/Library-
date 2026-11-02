package it.orsozoxi.android.orsonotes.widget;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.SparseArray;
import android.widget.RemoteViews;
import it.orsozoxi.android.orsonotes.NotesMainActivity;
import it.orsozoxi.android.orsonotes.R;
import it.orsozoxi.android.orsonotes.utils.ConstantsBase;

/* loaded from: classes3.dex */
public class ListWidgetProvider extends WidgetProvider {
    @Override // it.orsozoxi.android.orsonotes.widget.WidgetProvider
    protected RemoteViews getRemoteViews(Context context, int i, boolean z, boolean z2, SparseArray<PendingIntent> sparseArray) {
        if (z) {
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout_small);
            remoteViews.setOnClickPendingIntent(R.id.list, sparseArray.get(R.id.list));
            return remoteViews;
        }
        if (z2) {
            RemoteViews remoteViews2 = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
            remoteViews2.setOnClickPendingIntent(R.id.add, sparseArray.get(R.id.add));
            remoteViews2.setOnClickPendingIntent(R.id.list, sparseArray.get(R.id.list));
            return remoteViews2;
        }
        RemoteViews remoteViews3 = new RemoteViews(context.getPackageName(), R.layout.widget_layout_list);
        remoteViews3.setOnClickPendingIntent(R.id.add, sparseArray.get(R.id.add));
        remoteViews3.setOnClickPendingIntent(R.id.list, sparseArray.get(R.id.list));
        Intent intent = new Intent(context, (Class<?>) ListWidgetService.class);
        intent.putExtra("appWidgetId", i);
        intent.setData(Uri.parse(intent.toUri(1)));
        remoteViews3.setRemoteAdapter(R.id.widget_list, intent);
        Intent intent2 = new Intent(context, (Class<?>) NotesMainActivity.class);
        intent2.setAction(ConstantsBase.ACTION_WIDGET);
        remoteViews3.setPendingIntentTemplate(R.id.widget_list, PendingIntent.getActivity(context, 0, intent2, 134217728));
        return remoteViews3;
    }
}
