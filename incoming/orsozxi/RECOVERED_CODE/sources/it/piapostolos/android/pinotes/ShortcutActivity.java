package it.piapostolos.android.pinotes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import it.piapostolos.android.pinotes.utils.ConstantsBase;

/* loaded from: classes3.dex */
public class ShortcutActivity extends Activity {
    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Intent intent = new Intent(this, (Class<?>) NotesMainActivity.class);
        intent.setAction(ConstantsBase.ACTION_SHORTCUT_WIDGET);
        Parcelable parcelableFromContext = Intent.ShortcutIconResource.fromContext(this, R.drawable.shortcut_icon);
        Intent intent2 = new Intent();
        intent2.putExtra("android.intent.extra.shortcut.INTENT", intent);
        intent2.putExtra("android.intent.extra.shortcut.NAME", getString(R.string.add_note));
        intent2.putExtra("android.intent.extra.shortcut.ICON_RESOURCE", parcelableFromContext);
        setResult(-1, intent2);
        finish();
    }
}
