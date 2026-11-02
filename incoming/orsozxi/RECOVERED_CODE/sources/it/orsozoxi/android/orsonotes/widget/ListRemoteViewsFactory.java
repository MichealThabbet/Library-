package it.orsozoxi.android.orsonotes.widget;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spanned;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import it.orsozoxi.android.orsonotes.OrsoNotes;
import it.orsozoxi.android.orsonotes.R;
import it.orsozoxi.android.orsonotes.db.DbHelper;
import it.orsozoxi.android.orsonotes.helpers.LogDelegate;
import it.orsozoxi.android.orsonotes.models.Note;
import it.orsozoxi.android.orsonotes.utils.BitmapHelper;
import it.orsozoxi.android.orsonotes.utils.Constants;
import it.orsozoxi.android.orsonotes.utils.ConstantsBase;
import it.orsozoxi.android.orsonotes.utils.Navigation;
import it.orsozoxi.android.orsonotes.utils.TextHelper;
import java.util.List;

/* loaded from: classes3.dex */
public class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private static final String SET_BACKGROUND_COLOR = "setBackgroundColor";
    private static boolean showThumbnails = true;
    private static boolean showTimestamps = true;
    private OrsoNotes app;
    private int appWidgetId;
    private int navigation;
    private List<Note> notes;
    private final int WIDTH = 80;
    private final int HEIGHT = 80;

    @Override // android.widget.RemoteViewsService.RemoteViewsFactory
    public long getItemId(int i) {
        return i;
    }

    @Override // android.widget.RemoteViewsService.RemoteViewsFactory
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override // android.widget.RemoteViewsService.RemoteViewsFactory
    public int getViewTypeCount() {
        return 1;
    }

    @Override // android.widget.RemoteViewsService.RemoteViewsFactory
    public boolean hasStableIds() {
        return false;
    }

    public ListRemoteViewsFactory(Application application, Intent intent) {
        this.app = (OrsoNotes) application;
        this.appWidgetId = intent.getIntExtra("appWidgetId", 0);
    }

    static void updateConfiguration(Context context, int i, String str, boolean z, boolean z2) {
        LogDelegate.d("Widget configuration updated");
        context.getSharedPreferences(Constants.PREFS_NAME, 4).edit().putString(ConstantsBase.PREF_WIDGET_PREFIX + i, str).apply();
        showThumbnails = z;
        showTimestamps = z2;
    }

    @Override // android.widget.RemoteViewsService.RemoteViewsFactory
    public void onCreate() {
        LogDelegate.d("Created widget " + this.appWidgetId);
        this.notes = DbHelper.getInstance().getNotes(this.app.getSharedPreferences(Constants.PREFS_NAME, 4).getString(ConstantsBase.PREF_WIDGET_PREFIX + this.appWidgetId, ""), true);
    }

    @Override // android.widget.RemoteViewsService.RemoteViewsFactory
    public void onDataSetChanged() {
        LogDelegate.d("onDataSetChanged widget " + this.appWidgetId);
        this.navigation = Navigation.getNavigation();
        this.notes = DbHelper.getInstance().getNotes(this.app.getSharedPreferences(Constants.PREFS_NAME, 4).getString(ConstantsBase.PREF_WIDGET_PREFIX + this.appWidgetId, ""), true);
    }

    @Override // android.widget.RemoteViewsService.RemoteViewsFactory
    public void onDestroy() {
        this.app.getSharedPreferences(Constants.PREFS_NAME, 4).edit().remove(ConstantsBase.PREF_WIDGET_PREFIX + this.appWidgetId).apply();
    }

    @Override // android.widget.RemoteViewsService.RemoteViewsFactory
    public int getCount() {
        return this.notes.size();
    }

    @Override // android.widget.RemoteViewsService.RemoteViewsFactory
    public RemoteViews getViewAt(int i) {
        RemoteViews remoteViews = new RemoteViews(this.app.getPackageName(), R.layout.note_layout_widget);
        Note note = this.notes.get(i);
        Spanned[] titleAndContent = TextHelper.parseTitleAndContent(this.app, note);
        remoteViews.setTextViewText(R.id.note_title, titleAndContent[0]);
        remoteViews.setTextViewText(R.id.note_content, titleAndContent[1]);
        color(note, remoteViews);
        if (!note.isLocked().booleanValue() && showThumbnails && !note.getAttachmentsList().isEmpty()) {
            remoteViews.setBitmap(R.id.attachmentThumbnail, "setImageBitmap", BitmapHelper.getBitmapFromAttachment(this.app, note.getAttachmentsList().get(0), 80, 80));
            remoteViews.setInt(R.id.attachmentThumbnail, "setVisibility", 0);
        } else {
            remoteViews.setInt(R.id.attachmentThumbnail, "setVisibility", 8);
        }
        if (showTimestamps) {
            remoteViews.setTextViewText(R.id.note_date, TextHelper.getDateText(this.app, note, this.navigation));
        } else {
            remoteViews.setTextViewText(R.id.note_date, "");
        }
        Bundle bundle = new Bundle();
        bundle.putParcelable(ConstantsBase.INTENT_NOTE, note);
        Intent intent = new Intent();
        intent.putExtras(bundle);
        remoteViews.setOnClickFillInIntent(R.id.root, intent);
        return remoteViews;
    }

    private void color(Note note, RemoteViews remoteViews) {
        String string = this.app.getSharedPreferences(Constants.PREFS_NAME, 4).getString("settings_colors_widget", ConstantsBase.PREF_COLORS_APP_DEFAULT);
        if (string.equals("disabled")) {
            return;
        }
        remoteViews.setInt(R.id.tag_marker, SET_BACKGROUND_COLOR, Color.parseColor("#00000000"));
        if (note.getCategory() != null && note.getCategory().getColor() != null) {
            if (string.equals("list")) {
                remoteViews.setInt(R.id.card_layout, SET_BACKGROUND_COLOR, Integer.parseInt(note.getCategory().getColor()));
                return;
            } else {
                remoteViews.setInt(R.id.tag_marker, SET_BACKGROUND_COLOR, Integer.parseInt(note.getCategory().getColor()));
                return;
            }
        }
        remoteViews.setInt(R.id.tag_marker, SET_BACKGROUND_COLOR, 0);
    }
}
