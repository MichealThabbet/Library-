package it.orsozoxi.android.orsonotes.utils;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.os.Build;
import it.orsozoxi.android.orsonotes.NotesMainActivity;
import it.orsozoxi.android.orsonotes.OrsoNotes;
import it.orsozoxi.android.orsonotes.R;
import it.orsozoxi.android.orsonotes.helpers.date.DateHelper;
import it.orsozoxi.android.orsonotes.models.Note;

/* loaded from: classes3.dex */
public class ShortcutHelper {
    private ShortcutHelper() {
    }

    public static void addShortcut(Context context, Note note) {
        String title = note.getTitle().length() > 0 ? note.getTitle() : DateHelper.getFormattedDate(note.getCreation(), OrsoNotes.getSharedPreferences().getBoolean(ConstantsBase.PREF_PRETTIFIED_DATES, true));
        if (Build.VERSION.SDK_INT < 26) {
            Intent intent = new Intent(context, (Class<?>) NotesMainActivity.class);
            intent.putExtra("note_id", note.get_id());
            intent.setAction(ConstantsBase.ACTION_SHORTCUT);
            Intent intent2 = new Intent();
            intent2.putExtra("android.intent.extra.shortcut.INTENT", intent);
            intent2.putExtra("android.intent.extra.shortcut.NAME", title);
            intent2.putExtra("android.intent.extra.shortcut.ICON_RESOURCE", Intent.ShortcutIconResource.fromContext(context, R.drawable.ic_logo));
            intent2.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
            context.sendBroadcast(intent2);
            return;
        }
        ShortcutManager shortcutManager = (ShortcutManager) context.getSystemService(ShortcutManager.class);
        if (shortcutManager.isRequestPinShortcutSupported()) {
            Intent intent3 = new Intent(context.getApplicationContext(), context.getClass());
            intent3.setAction("android.intent.action.MAIN");
            ShortcutInfo shortcutInfoBuild = new ShortcutInfo.Builder(context, "pinned-shortcut").setIcon(Icon.createWithResource(context, R.drawable.ic_logo)).setIntent(intent3).setShortLabel(title).build();
            shortcutManager.requestPinShortcut(shortcutInfoBuild, PendingIntent.getBroadcast(context, 0, shortcutManager.createShortcutResultIntent(shortcutInfoBuild), 0).getIntentSender());
        }
    }

    public static void removeshortCut(Context context, Note note) {
        Intent intent = new Intent(context, (Class<?>) NotesMainActivity.class);
        intent.putExtra("note_id", note.get_id());
        intent.setAction(ConstantsBase.ACTION_SHORTCUT);
        Intent intent2 = new Intent();
        intent2.putExtra("android.intent.extra.shortcut.INTENT", intent);
        intent2.putExtra("android.intent.extra.shortcut.NAME", note.getTitle().length() > 0 ? note.getTitle() : DateHelper.getFormattedDate(note.getCreation(), OrsoNotes.getSharedPreferences().getBoolean(ConstantsBase.PREF_PRETTIFIED_DATES, true)));
        intent2.setAction("com.android.launcher.action.UNINSTALL_SHORTCUT");
        context.sendBroadcast(intent2);
    }
}
