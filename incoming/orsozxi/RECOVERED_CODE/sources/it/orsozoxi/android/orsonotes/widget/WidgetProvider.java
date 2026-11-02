package it.orsozoxi.android.orsonotes.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.SparseArray;
import android.widget.RemoteViews;
import it.orsozoxi.android.orsonotes.NotesMainActivity;
import it.orsozoxi.android.orsonotes.R;
import it.orsozoxi.android.orsonotes.helpers.LogDelegate;
import it.orsozoxi.android.orsonotes.utils.ConstantsBase;
import net.sqlcipher.database.SQLiteDatabase;

/* loaded from: classes3.dex */
public abstract class WidgetProvider extends AppWidgetProvider {
    public static final String EXTRA_ITEM = "it.orsozoxi.android.orsonotes.widget.EXTRA_FIELD";
    public static final String EXTRA_WORD = "it.orsozoxi.android.orsonotes.widget.WORD";
    public static final String TOAST_ACTION = "it.orsozoxi.android.orsonotes.widget.NOTE";

    protected abstract RemoteViews getRemoteViews(Context context, int i, boolean z, boolean z2, SparseArray<PendingIntent> sparseArray);

    @Override // android.appwidget.AppWidgetProvider
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] iArr) {
        for (int i : appWidgetManager.getAppWidgetIds(new ComponentName(context, getClass()))) {
            LogDelegate.d("WidgetProvider onUpdate() widget " + i);
            setLayout(context, appWidgetManager, i);
        }
        super.onUpdate(context, appWidgetManager, iArr);
    }

    @Override // android.appwidget.AppWidgetProvider
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int i, Bundle bundle) {
        LogDelegate.d("Widget size changed");
        setLayout(context, appWidgetManager, i);
    }

    private void setLayout(Context context, AppWidgetManager appWidgetManager, int i) {
        PendingIntent activity;
        PendingIntent activity2;
        Intent intent = new Intent(context, (Class<?>) NotesMainActivity.class);
        intent.setAction(ConstantsBase.ACTION_WIDGET);
        intent.putExtra(ConstantsBase.INTENT_WIDGET, i);
        if (Build.VERSION.SDK_INT >= 23) {
            activity = PendingIntent.getActivity(context, i, intent, 335544320);
        } else {
            activity = PendingIntent.getActivity(context, i, intent, SQLiteDatabase.CREATE_IF_NECESSARY);
        }
        Intent intent2 = new Intent(context, (Class<?>) NotesMainActivity.class);
        intent2.setAction(ConstantsBase.ACTION_WIDGET_SHOW_LIST);
        intent2.putExtra(ConstantsBase.INTENT_WIDGET, i);
        if (Build.VERSION.SDK_INT >= 23) {
            activity2 = PendingIntent.getActivity(context, i, intent2, 335544320);
        } else {
            activity2 = PendingIntent.getActivity(context, i, intent2, SQLiteDatabase.CREATE_IF_NECESSARY);
        }
        Bundle appWidgetOptions = appWidgetManager.getAppWidgetOptions(i);
        boolean z = appWidgetOptions.getInt("appWidgetMinWidth") < 110;
        boolean z2 = appWidgetOptions.getInt("appWidgetMinHeight") < 110;
        SparseArray<PendingIntent> sparseArray = new SparseArray<>();
        sparseArray.put(R.id.list, activity2);
        sparseArray.put(R.id.add, activity);
        appWidgetManager.updateAppWidget(i, getRemoteViews(context, i, z, z2, sparseArray));
    }
}
