package it.orsozoxi.android.orsonotes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.folderselector.FolderChooserDialog;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import it.orsozoxi.android.orsonotes.R2;
import it.orsozoxi.android.orsonotes.async.DataBackupIntentService;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class SettingsActivity extends AppCompatActivity implements PreferenceFragmentCompat.OnPreferenceStartFragmentCallback, FolderChooserDialog.FolderCallback {
    private List<Fragment> backStack = new ArrayList();

    @BindView(R2.id.crouton_handle)
    ViewGroup croutonViewContainer;

    @BindView(R2.id.toolbar)
    Toolbar toolbar;

    @Override // com.afollestad.materialdialogs.folderselector.FolderChooserDialog.FolderCallback
    public void onFolderChooserDismissed(FolderChooserDialog folderChooserDialog) {
    }

    @Override // android.view.Window.Callback
    public void onPointerCaptureChanged(boolean z) {
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        initUI();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new SettingsFragment()).commit();
    }

    void initUI() {
        setSupportActionBar(this.toolbar);
        this.toolbar.setNavigationOnClickListener(new View.OnClickListener() { // from class: it.orsozoxi.android.orsonotes.-$$Lambda$SettingsActivity$t-RVUXwZ0h7lKlRhIW7lH9KxAhE
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$initUI$0$SettingsActivity(view);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    public /* synthetic */ void lambda$initUI$0$SettingsActivity(View view) {
        onBackPressed();
    }

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.animator.fade_in, R.animator.fade_out, R.animator.fade_in, R.animator.fade_out).replace(R.id.content_frame, fragment).commit();
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        if (!this.backStack.isEmpty()) {
            replaceFragment(this.backStack.remove(r0.size() - 1));
        } else {
            super.onBackPressed();
        }
    }

    public void showMessage(int i, Style style) {
        showMessage(getString(i), style);
    }

    public void showMessage(String str, Style style) {
        Crouton.makeText(this, str, style, this.croutonViewContainer).show();
    }

    @Override // com.afollestad.materialdialogs.folderselector.FolderChooserDialog.FolderCallback
    public void onFolderSelection(FolderChooserDialog folderChooserDialog, final File file) {
        new MaterialDialog.Builder(this).title(R.string.data_import_message_warning).content(file.getName()).positiveText(R.string.confirm).onPositive(new MaterialDialog.SingleButtonCallback() { // from class: it.orsozoxi.android.orsonotes.-$$Lambda$SettingsActivity$-3BxQX5GGsZuCDmIsy_uE1dSYhQ
            @Override // com.afollestad.materialdialogs.MaterialDialog.SingleButtonCallback
            public final void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                this.f$0.lambda$onFolderSelection$1$SettingsActivity(file, materialDialog, dialogAction);
            }
        }).build().show();
    }

    public /* synthetic */ void lambda$onFolderSelection$1$SettingsActivity(File file, MaterialDialog materialDialog, DialogAction dialogAction) {
        Intent intent = new Intent(getApplicationContext(), (Class<?>) DataBackupIntentService.class);
        intent.setAction(DataBackupIntentService.ACTION_DATA_IMPORT_LEGACY);
        intent.putExtra(DataBackupIntentService.INTENT_BACKUP_NAME, file.getAbsolutePath());
        startService(intent);
    }

    @Override // androidx.preference.PreferenceFragmentCompat.OnPreferenceStartFragmentCallback
    public boolean onPreferenceStartFragment(PreferenceFragmentCompat preferenceFragmentCompat, Preference preference) {
        Bundle bundle = new Bundle();
        bundle.putString(SettingsFragment.XML_NAME, preference.getKey());
        Fragment fragmentInstantiate = getSupportFragmentManager().getFragmentFactory().instantiate(getClassLoader(), preference.getFragment());
        fragmentInstantiate.setArguments(bundle);
        fragmentInstantiate.setTargetFragment(preferenceFragmentCompat, 0);
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragmentInstantiate).addToBackStack(null).commit();
        return true;
    }
}
