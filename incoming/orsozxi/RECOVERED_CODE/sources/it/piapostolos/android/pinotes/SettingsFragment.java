package it.piapostolos.android.pinotes;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.folderselector.FolderChooserDialog;
import it.piapostolos.android.pinotes.async.DataBackupIntentService;
import it.piapostolos.android.pinotes.helpers.BackupHelper;
import it.piapostolos.android.pinotes.helpers.LanguageHelper;
import it.piapostolos.android.pinotes.helpers.LogDelegate;
import it.piapostolos.android.pinotes.helpers.PermissionsHelper;
import it.piapostolos.android.pinotes.helpers.SpringImportHelper;
import it.piapostolos.android.pinotes.models.ONStyle;
import it.piapostolos.android.pinotes.models.PasswordValidator;
import it.piapostolos.android.pinotes.models.listeners.OnPermissionRequestedListener;
import it.piapostolos.android.pinotes.utils.Constants;
import it.piapostolos.android.pinotes.utils.ConstantsBase;
import it.piapostolos.android.pinotes.utils.FileHelper;
import it.piapostolos.android.pinotes.utils.IntentChecker;
import it.piapostolos.android.pinotes.utils.PasswordHelper;
import it.piapostolos.android.pinotes.utils.ResourcesUtils;
import it.piapostolos.android.pinotes.utils.StorageHelper;
import it.piapostolos.android.pinotes.utils.SystemHelper;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/* loaded from: classes3.dex */
public class SettingsFragment extends PreferenceFragmentCompat {
    private static final int RINGTONE_REQUEST_CODE = 100;
    private static final int SPRINGPAD_IMPORT = 0;
    public static final String XML_NAME = "xmlName";
    private SharedPreferences prefs;

    static /* synthetic */ boolean lambda$onResume$7(Preference preference, Object obj) {
        return true;
    }

    static /* synthetic */ boolean lambda$onResume$8(Preference preference, Object obj) {
        return true;
    }

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        int xmlId = R.xml.settings;
        if (getArguments() != null && getArguments().containsKey(XML_NAME)) {
            xmlId = ResourcesUtils.getXmlId(PiNotes.getAppContext(), ResourcesUtils.ResourceIdentifiers.XML, String.valueOf(getArguments().get(XML_NAME)));
        }
        addPreferencesFromResource(xmlId);
    }

    @Override // androidx.preference.PreferenceFragmentCompat
    public void onCreatePreferences(Bundle bundle, String str) {
        this.prefs = getContext().getSharedPreferences(Constants.PREFS_NAME, 4);
        setTitle();
    }

    private void setTitle() {
        int identifier;
        String string = getString(R.string.settings);
        if (getArguments() != null && getArguments().containsKey(XML_NAME)) {
            String string2 = getArguments().getString(XML_NAME);
            if (!TextUtils.isEmpty(string2) && (identifier = getActivity().getResources().getIdentifier(string2.replace("settings_", "settings_screen_"), "string", getActivity().getPackageName())) != 0) {
                string = getString(identifier);
            }
        }
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle(string);
        }
    }

    @Override // androidx.fragment.app.Fragment
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            getActivity().onBackPressed();
        } else {
            LogDelegate.e("Wrong element choosen: " + menuItem.getItemId());
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        Preference preferenceFindPreference = findPreference("settings_export_data");
        if (preferenceFindPreference != null) {
            preferenceFindPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() { // from class: it.piapostolos.android.pinotes.-$$Lambda$SettingsFragment$fTuwVFRm_NsFnH1eoS1CI58MMaM
                @Override // androidx.preference.Preference.OnPreferenceClickListener
                public final boolean onPreferenceClick(Preference preference) {
                    return this.f$0.lambda$onResume$1$SettingsFragment(preference);
                }
            });
        }
        Preference preferenceFindPreference2 = findPreference("settings_import_data");
        if (preferenceFindPreference2 != null) {
            preferenceFindPreference2.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() { // from class: it.piapostolos.android.pinotes.-$$Lambda$SettingsFragment$8U-T4bgUrwtOND_dAXgRkb3JCCM
                @Override // androidx.preference.Preference.OnPreferenceClickListener
                public final boolean onPreferenceClick(Preference preference) {
                    return this.f$0.lambda$onResume$2$SettingsFragment(preference);
                }
            });
        }
        Preference preferenceFindPreference3 = findPreference("settings_import_data_legacy");
        if (preferenceFindPreference3 != null) {
            preferenceFindPreference3.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() { // from class: it.piapostolos.android.pinotes.-$$Lambda$SettingsFragment$XaGhI3yB3U-p89qv3ayeE3etEi8
                @Override // androidx.preference.Preference.OnPreferenceClickListener
                public final boolean onPreferenceClick(Preference preference) {
                    return this.f$0.lambda$onResume$4$SettingsFragment(preference);
                }
            });
        }
        Preference preferenceFindPreference4 = findPreference("settings_import_from_springpad");
        if (preferenceFindPreference4 != null) {
            preferenceFindPreference4.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() { // from class: it.piapostolos.android.pinotes.-$$Lambda$SettingsFragment$Ws5PekrBYigR7lvn_s-E1Q3CKUk
                @Override // androidx.preference.Preference.OnPreferenceClickListener
                public final boolean onPreferenceClick(Preference preference) {
                    return this.f$0.lambda$onResume$5$SettingsFragment(preference);
                }
            });
        }
        final SwitchPreference switchPreference = (SwitchPreference) findPreference("settings_swipe_to_trash");
        if (switchPreference != null) {
            if (this.prefs.getBoolean("settings_swipe_to_trash", false)) {
                switchPreference.setChecked(true);
                switchPreference.setSummary(getResources().getString(R.string.settings_swipe_to_trash_summary_2));
            } else {
                switchPreference.setChecked(false);
                switchPreference.setSummary(getResources().getString(R.string.settings_swipe_to_trash_summary_1));
            }
            switchPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() { // from class: it.piapostolos.android.pinotes.-$$Lambda$SettingsFragment$q8RtbTMmRyuVNbYM7I_wKDAgdQQ
                @Override // androidx.preference.Preference.OnPreferenceChangeListener
                public final boolean onPreferenceChange(Preference preference, Object obj) {
                    return this.f$0.lambda$onResume$6$SettingsFragment(switchPreference, preference, obj);
                }
            });
        }
        SwitchPreference switchPreference2 = (SwitchPreference) findPreference(ConstantsBase.PREF_SHOW_UNCATEGORIZED);
        if (switchPreference2 != null) {
            switchPreference2.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() { // from class: it.piapostolos.android.pinotes.-$$Lambda$SettingsFragment$B0oDwca_SyxSczcPOuq1QN-A2GQ
                @Override // androidx.preference.Preference.OnPreferenceChangeListener
                public final boolean onPreferenceChange(Preference preference, Object obj) {
                    return SettingsFragment.lambda$onResume$7(preference, obj);
                }
            });
        }
        SwitchPreference switchPreference3 = (SwitchPreference) findPreference(ConstantsBase.PREF_AUTO_LOCATION);
        if (switchPreference3 != null) {
            switchPreference3.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() { // from class: it.piapostolos.android.pinotes.-$$Lambda$SettingsFragment$4TrVVu2d4q1KZLn3RgvWnKj3EfU
                @Override // androidx.preference.Preference.OnPreferenceChangeListener
                public final boolean onPreferenceChange(Preference preference, Object obj) {
                    return SettingsFragment.lambda$onResume$8(preference, obj);
                }
            });
        }
        final EditTextPreference editTextPreference = (EditTextPreference) findPreference("settings_max_video_size");
        if (editTextPreference != null) {
            editTextPreference.setSummary(getString(R.string.settings_max_video_size_summary) + ": " + this.prefs.getString("settings_max_video_size", getString(R.string.not_set)));
            editTextPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() { // from class: it.piapostolos.android.pinotes.-$$Lambda$SettingsFragment$OkJwfR3NHt7zMe9fAOlfLFGpERk
                @Override // androidx.preference.Preference.OnPreferenceChangeListener
                public final boolean onPreferenceChange(Preference preference, Object obj) {
                    return this.f$0.lambda$onResume$9$SettingsFragment(editTextPreference, preference, obj);
                }
            });
        }
        Preference preferenceFindPreference5 = findPreference("settings_password");
        if (preferenceFindPreference5 != null) {
            preferenceFindPreference5.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() { // from class: it.piapostolos.android.pinotes.-$$Lambda$SettingsFragment$jGZASIhsK4JUL9TOIMyB409V0aQ
                @Override // androidx.preference.Preference.OnPreferenceClickListener
                public final boolean onPreferenceClick(Preference preference) {
                    return this.f$0.lambda$onResume$10$SettingsFragment(preference);
                }
            });
        }
        final SwitchPreference switchPreference4 = (SwitchPreference) findPreference("settings_password_access");
        if (switchPreference4 != null) {
            if (this.prefs.getString(ConstantsBase.PREF_PASSWORD, null) == null) {
                switchPreference4.setEnabled(false);
                switchPreference4.setChecked(false);
            } else {
                switchPreference4.setEnabled(true);
            }
            switchPreference4.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() { // from class: it.piapostolos.android.pinotes.-$$Lambda$SettingsFragment$YrwbV-OJMuKk1-L3YL2iXNYoF00
                @Override // androidx.preference.Preference.OnPreferenceChangeListener
                public final boolean onPreferenceChange(Preference preference, Object obj) {
                    return this.f$0.lambda$onResume$12$SettingsFragment(switchPreference4, preference, obj);
                }
            });
        }
        ListPreference listPreference = (ListPreference) findPreference(ConstantsBase.PREF_LANG);
        if (listPreference != null) {
            String displayName = getResources().getConfiguration().locale.getDisplayName();
            listPreference.setSummary(displayName.substring(0, 1).toUpperCase(getResources().getConfiguration().locale) + displayName.substring(1));
            listPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() { // from class: it.piapostolos.android.pinotes.-$$Lambda$SettingsFragment$i8i3KBMqFkHe2ZHjkKxRzoEZX1s
                @Override // androidx.preference.Preference.OnPreferenceChangeListener
                public final boolean onPreferenceChange(Preference preference, Object obj) {
                    return this.f$0.lambda$onResume$13$SettingsFragment(preference, obj);
                }
            });
        }
        final ListPreference listPreference2 = (ListPreference) findPreference("settings_colors_app");
        if (listPreference2 != null) {
            listPreference2.setSummary(getResources().getStringArray(R.array.colors_app)[listPreference2.findIndexOfValue(this.prefs.getString("settings_colors_app", ConstantsBase.PREF_COLORS_APP_DEFAULT))]);
            listPreference2.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() { // from class: it.piapostolos.android.pinotes.-$$Lambda$SettingsFragment$rucnNhv4DsRGWHjCsVHYg2Z0KCo
                @Override // androidx.preference.Preference.OnPreferenceChangeListener
                public final boolean onPreferenceChange(Preference preference, Object obj) {
                    return this.f$0.lambda$onResume$14$SettingsFragment(listPreference2, preference, obj);
                }
            });
        }
        final ListPreference listPreference3 = (ListPreference) findPreference("settings_checked_items_behavior");
        if (listPreference3 != null) {
            listPreference3.setSummary(getResources().getStringArray(R.array.checked_items_behavior)[listPreference3.findIndexOfValue(this.prefs.getString("settings_checked_items_behavior", "0"))]);
            listPreference3.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() { // from class: it.piapostolos.android.pinotes.-$$Lambda$SettingsFragment$ARchqSlh-PcxzTaFbMTuPg_9uR0
                @Override // androidx.preference.Preference.OnPreferenceChangeListener
                public final boolean onPreferenceChange(Preference preference, Object obj) {
                    return this.f$0.lambda$onResume$15$SettingsFragment(listPreference3, preference, obj);
                }
            });
        }
        final ListPreference listPreference4 = (ListPreference) findPreference("settings_colors_widget");
        if (listPreference4 != null) {
            listPreference4.setSummary(getResources().getStringArray(R.array.colors_widget)[listPreference4.findIndexOfValue(this.prefs.getString("settings_colors_widget", ConstantsBase.PREF_COLORS_APP_DEFAULT))]);
            listPreference4.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() { // from class: it.piapostolos.android.pinotes.-$$Lambda$SettingsFragment$AMR1Fie4-qvp9uOsmd7wRtQa38E
                @Override // androidx.preference.Preference.OnPreferenceChangeListener
                public final boolean onPreferenceChange(Preference preference, Object obj) {
                    return this.f$0.lambda$onResume$16$SettingsFragment(listPreference4, preference, obj);
                }
            });
        }
        Preference preferenceFindPreference6 = findPreference("settings_notification_ringtone");
        if (preferenceFindPreference6 != null) {
            preferenceFindPreference6.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() { // from class: it.piapostolos.android.pinotes.-$$Lambda$SettingsFragment$vREMyadhPyjrWMyW2q1Ip4fWRL8
                @Override // androidx.preference.Preference.OnPreferenceClickListener
                public final boolean onPreferenceClick(Preference preference) {
                    return this.f$0.lambda$onResume$17$SettingsFragment(preference);
                }
            });
        }
        final EditTextPreference editTextPreference2 = (EditTextPreference) findPreference("settings_notification_snooze_delay");
        if (editTextPreference2 != null) {
            SharedPreferences sharedPreferences = this.prefs;
            String str = ConstantsBase.PREF_SNOOZE_DEFAULT;
            String string = sharedPreferences.getString("settings_notification_snooze_delay", ConstantsBase.PREF_SNOOZE_DEFAULT);
            if (!TextUtils.isEmpty(string)) {
                str = string;
            }
            editTextPreference2.setSummary(str + " " + getString(R.string.minutes));
            editTextPreference2.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() { // from class: it.piapostolos.android.pinotes.-$$Lambda$SettingsFragment$egkzcmicUAQuPEmtPgWBS1gJuVw
                @Override // androidx.preference.Preference.OnPreferenceChangeListener
                public final boolean onPreferenceChange(Preference preference, Object obj) {
                    return this.f$0.lambda$onResume$18$SettingsFragment(editTextPreference2, preference, obj);
                }
            });
        }
        Preference preferenceFindPreference7 = findPreference("settings_notification_service_listener");
        if (preferenceFindPreference7 != null) {
            getPreferenceScreen().removePreference(preferenceFindPreference7);
        }
        Preference preferenceFindPreference8 = findPreference("settings_changelog");
        if (preferenceFindPreference8 != null) {
            preferenceFindPreference8.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() { // from class: it.piapostolos.android.pinotes.-$$Lambda$SettingsFragment$4e0sNL-isy4lh2Smo0iZYG03huE
                @Override // androidx.preference.Preference.OnPreferenceClickListener
                public final boolean onPreferenceClick(Preference preference) {
                    return this.f$0.lambda$onResume$19$SettingsFragment(preference);
                }
            });
        }
        Preference preferenceFindPreference9 = findPreference("reset_all_data");
        if (preferenceFindPreference9 != null) {
            preferenceFindPreference9.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() { // from class: it.piapostolos.android.pinotes.-$$Lambda$SettingsFragment$XqPMdM589aUBzc8VKOY6EH_pbIc
                @Override // androidx.preference.Preference.OnPreferenceClickListener
                public final boolean onPreferenceClick(Preference preference) {
                    return this.f$0.lambda$onResume$21$SettingsFragment(preference);
                }
            });
        }
        final SwitchPreference switchPreference5 = (SwitchPreference) findPreference(ConstantsBase.PREF_ENABLE_FILE_LOGGING);
        if (switchPreference5 != null) {
            switchPreference5.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() { // from class: it.piapostolos.android.pinotes.-$$Lambda$SettingsFragment$pRKP2m4mMmt9iTn8Dhr2XsTW-wk
                @Override // androidx.preference.Preference.OnPreferenceChangeListener
                public final boolean onPreferenceChange(Preference preference, Object obj) {
                    return this.f$0.lambda$onResume$23$SettingsFragment(switchPreference5, preference, obj);
                }
            });
        }
        Preference preferenceFindPreference10 = findPreference("settings_tour_show_again");
        if (preferenceFindPreference10 != null) {
            preferenceFindPreference10.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() { // from class: it.piapostolos.android.pinotes.-$$Lambda$SettingsFragment$uImTtWkBCuDdQ32MoF3SrzhQOS8
                @Override // androidx.preference.Preference.OnPreferenceClickListener
                public final boolean onPreferenceClick(Preference preference) {
                    return this.f$0.lambda$onResume$25$SettingsFragment(preference);
                }
            });
        }
    }

    public /* synthetic */ boolean lambda$onResume$1$SettingsFragment(Preference preference) {
        final View viewInflate = getActivity().getLayoutInflater().inflate(R.layout.dialog_backup_layout, (ViewGroup) null);
        PermissionsHelper.requestPermission(getActivity(), "android.permission.WRITE_EXTERNAL_STORAGE", R.string.permission_external_storage, getActivity().findViewById(R.id.crouton_handle), new OnPermissionRequestedListener() { // from class: it.piapostolos.android.pinotes.-$$Lambda$SettingsFragment$pEIC_LZef2QB1MWV_aBfgIcIFJs
            @Override // it.piapostolos.android.pinotes.models.listeners.OnPermissionRequestedListener
            public final void onPermissionGranted() {
                this.f$0.lambda$onResume$0$SettingsFragment(viewInflate);
            }
        });
        return false;
    }

    public /* synthetic */ boolean lambda$onResume$2$SettingsFragment(Preference preference) {
        PermissionsHelper.requestPermission(getActivity(), "android.permission.WRITE_EXTERNAL_STORAGE", R.string.permission_external_storage, getActivity().findViewById(R.id.crouton_handle), new OnPermissionRequestedListener() { // from class: it.piapostolos.android.pinotes.-$$Lambda$SettingsFragment$qTWrWLtDBio-gB7yZcvQAv6vNeY
            @Override // it.piapostolos.android.pinotes.models.listeners.OnPermissionRequestedListener
            public final void onPermissionGranted() {
                this.f$0.importNotes();
            }
        });
        return false;
    }

    public /* synthetic */ boolean lambda$onResume$4$SettingsFragment(Preference preference) {
        PermissionsHelper.requestPermission(getActivity(), "android.permission.READ_EXTERNAL_STORAGE", R.string.permission_external_storage, getActivity().findViewById(R.id.crouton_handle), new OnPermissionRequestedListener() { // from class: it.piapostolos.android.pinotes.-$$Lambda$SettingsFragment$PyHEEyDoauFI65GRTXyjyLJrdDk
            @Override // it.piapostolos.android.pinotes.models.listeners.OnPermissionRequestedListener
            public final void onPermissionGranted() {
                this.f$0.lambda$onResume$3$SettingsFragment();
            }
        });
        return false;
    }

    public /* synthetic */ void lambda$onResume$3$SettingsFragment() {
        new FolderChooserDialog.Builder(getActivity()).chooseButton(R.string.md_choose_label).show(getActivity());
    }

    public /* synthetic */ boolean lambda$onResume$5$SettingsFragment(Preference preference) {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.addCategory("android.intent.category.OPENABLE");
        intent.setType("application/zip");
        if (!IntentChecker.isAvailable(getActivity(), intent, null)) {
            Toast.makeText(getActivity(), R.string.feature_not_available_on_this_device, 0).show();
            return false;
        }
        startActivityForResult(intent, 0);
        return false;
    }

    public /* synthetic */ boolean lambda$onResume$6$SettingsFragment(SwitchPreference switchPreference, Preference preference, Object obj) {
        if (((Boolean) obj).booleanValue()) {
            switchPreference.setSummary(getResources().getString(R.string.settings_swipe_to_trash_summary_2));
            return true;
        }
        switchPreference.setSummary(getResources().getString(R.string.settings_swipe_to_trash_summary_1));
        return true;
    }

    public /* synthetic */ boolean lambda$onResume$9$SettingsFragment(EditTextPreference editTextPreference, Preference preference, Object obj) {
        editTextPreference.setSummary(getString(R.string.settings_max_video_size_summary) + ": " + obj);
        this.prefs.edit().putString("settings_max_video_size", obj.toString()).apply();
        return false;
    }

    public /* synthetic */ boolean lambda$onResume$10$SettingsFragment(Preference preference) {
        startActivity(new Intent(getActivity(), (Class<?>) PasswordActivity.class));
        return false;
    }

    public /* synthetic */ boolean lambda$onResume$12$SettingsFragment(final SwitchPreference switchPreference, Preference preference, final Object obj) {
        PasswordHelper.requestPassword(getActivity(), new PasswordValidator() { // from class: it.piapostolos.android.pinotes.-$$Lambda$SettingsFragment$EwOLUL8yEjsm8sFsO01f-x1JHJs
            @Override // it.piapostolos.android.pinotes.models.PasswordValidator
            public final void onPasswordValidated(PasswordValidator.Result result) {
                SettingsFragment.lambda$onResume$11(switchPreference, obj, result);
            }
        });
        return true;
    }

    static /* synthetic */ void lambda$onResume$11(SwitchPreference switchPreference, Object obj, PasswordValidator.Result result) {
        if (result.equals(PasswordValidator.Result.SUCCEED)) {
            switchPreference.setChecked(((Boolean) obj).booleanValue());
        }
    }

    public /* synthetic */ boolean lambda$onResume$13$SettingsFragment(Preference preference, Object obj) {
        LanguageHelper.updateLanguage(getActivity(), obj.toString());
        SystemHelper.restartApp(getActivity().getApplicationContext(), NotesMainActivity.class);
        return false;
    }

    public /* synthetic */ boolean lambda$onResume$14$SettingsFragment(ListPreference listPreference, Preference preference, Object obj) {
        int iFindIndexOfValue = listPreference.findIndexOfValue(obj.toString());
        listPreference.setSummary(getResources().getStringArray(R.array.colors_app)[iFindIndexOfValue]);
        this.prefs.edit().putString("settings_colors_app", obj.toString()).apply();
        listPreference.setValueIndex(iFindIndexOfValue);
        return false;
    }

    public /* synthetic */ boolean lambda$onResume$15$SettingsFragment(ListPreference listPreference, Preference preference, Object obj) {
        int iFindIndexOfValue = listPreference.findIndexOfValue(obj.toString());
        listPreference.setSummary(getResources().getStringArray(R.array.checked_items_behavior)[iFindIndexOfValue]);
        this.prefs.edit().putString("settings_checked_items_behavior", obj.toString()).apply();
        listPreference.setValueIndex(iFindIndexOfValue);
        return false;
    }

    public /* synthetic */ boolean lambda$onResume$16$SettingsFragment(ListPreference listPreference, Preference preference, Object obj) {
        int iFindIndexOfValue = listPreference.findIndexOfValue(obj.toString());
        listPreference.setSummary(getResources().getStringArray(R.array.colors_widget)[iFindIndexOfValue]);
        this.prefs.edit().putString("settings_colors_widget", obj.toString()).apply();
        listPreference.setValueIndex(iFindIndexOfValue);
        return false;
    }

    public /* synthetic */ boolean lambda$onResume$17$SettingsFragment(Preference preference) {
        Intent intent = new Intent("android.intent.action.RINGTONE_PICKER");
        intent.putExtra("android.intent.extra.ringtone.TYPE", 2);
        intent.putExtra("android.intent.extra.ringtone.SHOW_DEFAULT", true);
        intent.putExtra("android.intent.extra.ringtone.SHOW_SILENT", true);
        intent.putExtra("android.intent.extra.ringtone.DEFAULT_URI", Settings.System.DEFAULT_NOTIFICATION_URI);
        String string = this.prefs.getString("settings_notification_ringtone", null);
        if (string != null) {
            if (string.length() == 0) {
                intent.putExtra("android.intent.extra.ringtone.EXISTING_URI", (Uri) null);
            } else {
                intent.putExtra("android.intent.extra.ringtone.EXISTING_URI", Uri.parse(string));
            }
        } else {
            intent.putExtra("android.intent.extra.ringtone.EXISTING_URI", Settings.System.DEFAULT_NOTIFICATION_URI);
        }
        startActivityForResult(intent, 100);
        return false;
    }

    public /* synthetic */ boolean lambda$onResume$18$SettingsFragment(EditTextPreference editTextPreference, Preference preference, Object obj) {
        String strValueOf = TextUtils.isEmpty(String.valueOf(obj)) ? ConstantsBase.PREF_SNOOZE_DEFAULT : String.valueOf(obj);
        editTextPreference.setSummary(strValueOf + " " + getString(R.string.minutes));
        this.prefs.edit().putString("settings_notification_snooze_delay", strValueOf).apply();
        return false;
    }

    public /* synthetic */ boolean lambda$onResume$19$SettingsFragment(Preference preference) {
        new MaterialDialog.Builder(getContext()).customView(R.layout.activity_changelog, false).positiveText(R.string.ok).build().show();
        return false;
    }

    public /* synthetic */ boolean lambda$onResume$21$SettingsFragment(Preference preference) {
        new MaterialDialog.Builder(getContext()).content(R.string.reset_all_data_confirmation).positiveText(R.string.confirm).onPositive(new MaterialDialog.SingleButtonCallback() { // from class: it.piapostolos.android.pinotes.-$$Lambda$SettingsFragment$tegq4c26hQDNJ70mQC9rQpfVyTs
            @Override // com.afollestad.materialdialogs.MaterialDialog.SingleButtonCallback
            public final void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                this.f$0.lambda$onResume$20$SettingsFragment(materialDialog, dialogAction);
            }
        }).build().show();
        return false;
    }

    public /* synthetic */ void lambda$onResume$20$SettingsFragment(MaterialDialog materialDialog, DialogAction dialogAction) {
        this.prefs.edit().clear().apply();
        StorageHelper.delete(getActivity(), getActivity().getDatabasePath(ConstantsBase.DATABASE_NAME).getAbsolutePath());
        StorageHelper.delete(getActivity(), StorageHelper.getAttachmentDir().getAbsolutePath());
        StorageHelper.delete(getActivity(), StorageHelper.getCacheDir(getActivity()).getAbsolutePath());
        SystemHelper.restartApp(getActivity().getApplicationContext(), NotesMainActivity.class);
    }

    public /* synthetic */ boolean lambda$onResume$23$SettingsFragment(final SwitchPreference switchPreference, Preference preference, Object obj) {
        if (((Boolean) obj).booleanValue()) {
            PermissionsHelper.requestPermission(getActivity(), "android.permission.WRITE_EXTERNAL_STORAGE", R.string.permission_external_storage, getActivity().findViewById(R.id.crouton_handle), new OnPermissionRequestedListener() { // from class: it.piapostolos.android.pinotes.-$$Lambda$SettingsFragment$RV1jYy0oC-3p_JJ-9vKPFaGDVUk
                @Override // it.piapostolos.android.pinotes.models.listeners.OnPermissionRequestedListener
                public final void onPermissionGranted() {
                    switchPreference.setChecked(true);
                }
            });
        } else {
            switchPreference.setChecked(false);
        }
        return false;
    }

    public /* synthetic */ boolean lambda$onResume$25$SettingsFragment(Preference preference) {
        new MaterialDialog.Builder(getActivity()).content(getString(R.string.settings_tour_show_again_summary) + "?").positiveText(R.string.confirm).onPositive(new MaterialDialog.SingleButtonCallback() { // from class: it.piapostolos.android.pinotes.-$$Lambda$SettingsFragment$2rmxrrDLsSpXUfFlQ3mmndRpMcY
            @Override // com.afollestad.materialdialogs.MaterialDialog.SingleButtonCallback
            public final void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                this.f$0.lambda$onResume$24$SettingsFragment(materialDialog, dialogAction);
            }
        }).build().show();
        return false;
    }

    public /* synthetic */ void lambda$onResume$24$SettingsFragment(MaterialDialog materialDialog, DialogAction dialogAction) {
        this.prefs.edit().putBoolean(ConstantsBase.PREF_TOUR_COMPLETE, false).apply();
        SystemHelper.restartApp(getActivity().getApplicationContext(), NotesMainActivity.class);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void importNotes() {
        List listAsList = Arrays.asList(StorageHelper.getExternalStoragePublicDir(getActivity()).list());
        Collections.reverse(listAsList);
        if (listAsList.isEmpty()) {
            ((SettingsActivity) getActivity()).showMessage(R.string.no_backups_available, ONStyle.WARN);
            return;
        }
        CharSequence[] charSequenceArr = new CharSequence[listAsList.size()];
        for (int i = 0; i < listAsList.size(); i++) {
            charSequenceArr[i] = (CharSequence) listAsList.get(i);
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.data_import_message);
        builder.setItems(charSequenceArr, new AnonymousClass1(listAsList));
        builder.show();
    }

    /* renamed from: it.piapostolos.android.pinotes.SettingsFragment$1, reason: invalid class name */
    class AnonymousClass1 implements DialogInterface.OnClickListener {
        final /* synthetic */ List val$backups;

        AnonymousClass1(List list) {
            this.val$backups = list;
        }

        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(final DialogInterface dialogInterface, final int i) throws IOException {
            String str;
            String str2;
            File backupDir = StorageHelper.getBackupDir(SettingsFragment.this.getContext(), (String) this.val$backups.get(i));
            long size = StorageHelper.getSize(backupDir) / 1024;
            if (size > 1024) {
                str = (size / 1024) + "Mb";
            } else {
                str = size + "Kb";
            }
            boolean zExists = new File(backupDir, StorageHelper.getSharedPreferencesFile(SettingsFragment.this.getActivity()).getName()).exists();
            StringBuilder sb = new StringBuilder();
            sb.append((String) this.val$backups.get(i));
            sb.append(" (");
            sb.append(str);
            if (zExists) {
                str2 = " " + SettingsFragment.this.getString(R.string.settings_included);
            } else {
                str2 = "";
            }
            sb.append(str2);
            sb.append(")");
            MaterialDialog.Builder builderPositiveText = new MaterialDialog.Builder(SettingsFragment.this.getActivity()).title(R.string.confirm_restoring_backup).content(sb.toString()).positiveText(R.string.confirm);
            final List list = this.val$backups;
            builderPositiveText.onPositive(new MaterialDialog.SingleButtonCallback() { // from class: it.piapostolos.android.pinotes.-$$Lambda$SettingsFragment$1$IjfyxWFheO-7ysj-IDwPL0o7ueY
                @Override // com.afollestad.materialdialogs.MaterialDialog.SingleButtonCallback
                public final void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                    this.f$0.lambda$onClick$0$SettingsFragment$1(dialogInterface, list, i, materialDialog, dialogAction);
                }
            }).build().show();
        }

        public /* synthetic */ void lambda$onClick$0$SettingsFragment$1(DialogInterface dialogInterface, List list, int i, MaterialDialog materialDialog, DialogAction dialogAction) {
            dialogInterface.dismiss();
            Intent intent = new Intent(SettingsFragment.this.getActivity(), (Class<?>) DataBackupIntentService.class);
            intent.setAction(DataBackupIntentService.ACTION_DATA_IMPORT);
            intent.putExtra(DataBackupIntentService.INTENT_BACKUP_NAME, (String) list.get(i));
            SettingsFragment.this.getActivity().startService(intent);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: export, reason: merged with bridge method [inline-methods] */
    public void lambda$onResume$0$SettingsFragment(View view) {
        final List listAsList = Arrays.asList(StorageHelper.getExternalStoragePublicDir(getActivity()).list());
        String str = new SimpleDateFormat(ConstantsBase.DATE_FORMAT_EXPORT).format(Calendar.getInstance().getTime());
        final EditText editText = (EditText) view.findViewById(R.id.export_file_name);
        final TextView textView = (TextView) view.findViewById(R.id.backup_existing);
        editText.setHint(str);
        editText.addTextChangedListener(new TextWatcher() { // from class: it.piapostolos.android.pinotes.SettingsFragment.2
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                if (listAsList.contains(editable.toString())) {
                    textView.setText(R.string.backup_existing);
                } else {
                    textView.setText("");
                }
            }
        });
        new MaterialDialog.Builder(getActivity()).title(R.string.data_export_message).customView(view, false).positiveText(R.string.confirm).onPositive(new MaterialDialog.SingleButtonCallback() { // from class: it.piapostolos.android.pinotes.-$$Lambda$SettingsFragment$Rx6JGHfZH6Zxct2UZMAmVIxiV3Q
            @Override // com.afollestad.materialdialogs.MaterialDialog.SingleButtonCallback
            public final void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                EditText editText2 = editText;
                BackupHelper.startBackupService(TextUtils.isEmpty(editText2.getText().toString()) ? editText2.getHint().toString() : editText2.getText().toString());
            }
        }).build().show();
    }

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityResult(int i, int i2, Intent intent) {
        if (i2 == -1) {
            if (i == 0) {
                String path = FileHelper.getPath(getActivity(), intent.getData());
                Intent intent2 = new Intent(getActivity(), (Class<?>) DataBackupIntentService.class);
                intent2.setAction(SpringImportHelper.ACTION_DATA_IMPORT_SPRINGPAD);
                intent2.putExtra(SpringImportHelper.EXTRA_SPRINGPAD_BACKUP, path);
                getActivity().startService(intent2);
                return;
            }
            if (i == 100) {
                Uri uri = (Uri) intent.getParcelableExtra("android.intent.extra.ringtone.PICKED_URI");
                this.prefs.edit().putString("settings_notification_ringtone", uri == null ? null : uri.toString()).apply();
            } else {
                LogDelegate.e("Wrong element choosen: " + i);
            }
        }
    }
}
