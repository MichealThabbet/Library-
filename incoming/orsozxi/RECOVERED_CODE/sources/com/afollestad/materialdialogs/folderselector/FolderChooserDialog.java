package com.afollestad.materialdialogs.folderselector;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.commons.R;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/* loaded from: classes.dex */
public class FolderChooserDialog extends DialogFragment implements MaterialDialog.ListCallback {
    private static final String DEFAULT_TAG = "[MD_FOLDER_SELECTOR]";
    private FolderCallback callback;
    private boolean canGoUp = false;
    private File[] parentContents;
    private File parentFolder;

    public interface FolderCallback {
        void onFolderChooserDismissed(FolderChooserDialog folderChooserDialog);

        void onFolderSelection(FolderChooserDialog folderChooserDialog, File file);
    }

    String[] getContentsArray() {
        File[] fileArr = this.parentContents;
        if (fileArr == null) {
            return this.canGoUp ? new String[]{getBuilder().goUpLabel} : new String[0];
        }
        int length = fileArr.length;
        boolean z = this.canGoUp;
        String[] strArr = new String[length + (z ? 1 : 0)];
        if (z) {
            strArr[0] = getBuilder().goUpLabel;
        }
        for (int i = 0; i < this.parentContents.length; i++) {
            strArr[this.canGoUp ? i + 1 : i] = this.parentContents[i].getName();
        }
        return strArr;
    }

    File[] listFiles() {
        File[] fileArrListFiles = this.parentFolder.listFiles();
        ArrayList arrayList = new ArrayList();
        if (fileArrListFiles == null) {
            return null;
        }
        for (File file : fileArrListFiles) {
            if (file.isDirectory()) {
                arrayList.add(file);
            }
        }
        Collections.sort(arrayList, new FolderSorter());
        return (File[]) arrayList.toArray(new File[arrayList.size()]);
    }

    @Override // androidx.fragment.app.DialogFragment
    public Dialog onCreateDialog(Bundle bundle) {
        if (Build.VERSION.SDK_INT >= 23 && ActivityCompat.checkSelfPermission(getActivity(), "android.permission.READ_EXTERNAL_STORAGE") != 0) {
            return new MaterialDialog.Builder(getActivity()).title(R.string.md_error_label).content(R.string.md_storage_perm_error).positiveText(android.R.string.ok).build();
        }
        if (getArguments() == null || !getArguments().containsKey("builder")) {
            throw new IllegalStateException("You must create a FolderChooserDialog using the Builder.");
        }
        if (!getArguments().containsKey("current_path")) {
            getArguments().putString("current_path", getBuilder().initialPath);
        }
        this.parentFolder = new File(getArguments().getString("current_path"));
        checkIfCanGoUp();
        this.parentContents = listFiles();
        MaterialDialog.Builder builderNegativeText = new MaterialDialog.Builder(getActivity()).typeface(getBuilder().mediumFont, getBuilder().regularFont).title(this.parentFolder.getAbsolutePath()).items(getContentsArray()).itemsCallback(this).onPositive(new MaterialDialog.SingleButtonCallback() { // from class: com.afollestad.materialdialogs.folderselector.FolderChooserDialog.2
            @Override // com.afollestad.materialdialogs.MaterialDialog.SingleButtonCallback
            public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                materialDialog.dismiss();
                FolderCallback folderCallback = FolderChooserDialog.this.callback;
                FolderChooserDialog folderChooserDialog = FolderChooserDialog.this;
                folderCallback.onFolderSelection(folderChooserDialog, folderChooserDialog.parentFolder);
            }
        }).onNegative(new MaterialDialog.SingleButtonCallback() { // from class: com.afollestad.materialdialogs.folderselector.FolderChooserDialog.1
            @Override // com.afollestad.materialdialogs.MaterialDialog.SingleButtonCallback
            public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                materialDialog.dismiss();
            }
        }).autoDismiss(false).positiveText(getBuilder().chooseButton).negativeText(getBuilder().cancelButton);
        if (getBuilder().allowNewFolder) {
            builderNegativeText.neutralText(getBuilder().newFolderButton);
            builderNegativeText.onNeutral(new MaterialDialog.SingleButtonCallback() { // from class: com.afollestad.materialdialogs.folderselector.FolderChooserDialog.3
                @Override // com.afollestad.materialdialogs.MaterialDialog.SingleButtonCallback
                public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                    FolderChooserDialog.this.createNewFolder();
                }
            });
        }
        if ("/".equals(getBuilder().initialPath)) {
            this.canGoUp = false;
        }
        return builderNegativeText.build();
    }

    @Override // androidx.fragment.app.DialogFragment, android.content.DialogInterface.OnDismissListener
    public void onDismiss(DialogInterface dialogInterface) {
        super.onDismiss(dialogInterface);
        FolderCallback folderCallback = this.callback;
        if (folderCallback != null) {
            folderCallback.onFolderChooserDismissed(this);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void createNewFolder() {
        new MaterialDialog.Builder(getActivity()).title(getBuilder().newFolderButton).input(0, 0, false, new MaterialDialog.InputCallback() { // from class: com.afollestad.materialdialogs.folderselector.FolderChooserDialog.4
            @Override // com.afollestad.materialdialogs.MaterialDialog.InputCallback
            public void onInput(MaterialDialog materialDialog, CharSequence charSequence) {
                File file = new File(FolderChooserDialog.this.parentFolder, charSequence.toString());
                if (file.mkdir()) {
                    FolderChooserDialog.this.reload();
                    return;
                }
                Toast.makeText(FolderChooserDialog.this.getActivity(), "Unable to create folder " + file.getAbsolutePath() + ", make sure you have the WRITE_EXTERNAL_STORAGE permission or root permissions.", 1).show();
            }
        }).show();
    }

    @Override // com.afollestad.materialdialogs.MaterialDialog.ListCallback
    public void onSelection(MaterialDialog materialDialog, View view, int i, CharSequence charSequence) {
        if (this.canGoUp && i == 0) {
            File parentFile = this.parentFolder.getParentFile();
            this.parentFolder = parentFile;
            if (parentFile.getAbsolutePath().equals("/storage/emulated")) {
                this.parentFolder = this.parentFolder.getParentFile();
            }
            this.canGoUp = this.parentFolder.getParent() != null;
        } else {
            File[] fileArr = this.parentContents;
            if (this.canGoUp) {
                i--;
            }
            File file = fileArr[i];
            this.parentFolder = file;
            this.canGoUp = true;
            if (file.getAbsolutePath().equals("/storage/emulated")) {
                this.parentFolder = Environment.getExternalStorageDirectory();
            }
        }
        reload();
    }

    private void checkIfCanGoUp() {
        try {
            boolean z = true;
            if (this.parentFolder.getPath().split("/").length <= 1) {
                z = false;
            }
            this.canGoUp = z;
        } catch (IndexOutOfBoundsException unused) {
            this.canGoUp = false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reload() {
        this.parentContents = listFiles();
        MaterialDialog materialDialog = (MaterialDialog) getDialog();
        materialDialog.setTitle(this.parentFolder.getAbsolutePath());
        getArguments().putString("current_path", this.parentFolder.getAbsolutePath());
        materialDialog.setItems(getContentsArray());
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() instanceof FolderCallback) {
            this.callback = (FolderCallback) getActivity();
        } else {
            if (getParentFragment() instanceof FolderCallback) {
                this.callback = (FolderCallback) getParentFragment();
                return;
            }
            throw new IllegalStateException("FolderChooserDialog needs to be shown from an Activity/Fragment implementing FolderCallback.");
        }
    }

    public void show(FragmentActivity fragmentActivity) {
        show(fragmentActivity.getSupportFragmentManager());
    }

    public void show(FragmentManager fragmentManager) {
        String str = getBuilder().tag;
        Fragment fragmentFindFragmentByTag = fragmentManager.findFragmentByTag(str);
        if (fragmentFindFragmentByTag != null) {
            ((DialogFragment) fragmentFindFragmentByTag).dismiss();
            fragmentManager.beginTransaction().remove(fragmentFindFragmentByTag).commit();
        }
        show(fragmentManager, str);
    }

    private Builder getBuilder() {
        return (Builder) getArguments().getSerializable("builder");
    }

    public static class Builder implements Serializable {
        boolean allowNewFolder;
        final transient Context context;
        String mediumFont;
        int newFolderButton;
        String regularFont;
        String tag;
        int chooseButton = R.string.md_choose_label;
        int cancelButton = android.R.string.cancel;
        String goUpLabel = "...";
        String initialPath = Environment.getExternalStorageDirectory().getAbsolutePath();

        public Builder(Context context) {
            this.context = context;
        }

        public Builder typeface(String str, String str2) {
            this.mediumFont = str;
            this.regularFont = str2;
            return this;
        }

        public Builder chooseButton(int i) {
            this.chooseButton = i;
            return this;
        }

        public Builder cancelButton(int i) {
            this.cancelButton = i;
            return this;
        }

        public Builder goUpLabel(String str) {
            this.goUpLabel = str;
            return this;
        }

        public Builder allowNewFolder(boolean z, int i) {
            this.allowNewFolder = z;
            if (i == 0) {
                i = R.string.new_folder;
            }
            this.newFolderButton = i;
            return this;
        }

        public Builder initialPath(String str) {
            if (str == null) {
                str = File.separator;
            }
            this.initialPath = str;
            return this;
        }

        public Builder tag(String str) {
            if (str == null) {
                str = FolderChooserDialog.DEFAULT_TAG;
            }
            this.tag = str;
            return this;
        }

        public FolderChooserDialog build() {
            FolderChooserDialog folderChooserDialog = new FolderChooserDialog();
            Bundle bundle = new Bundle();
            bundle.putSerializable("builder", this);
            folderChooserDialog.setArguments(bundle);
            return folderChooserDialog;
        }

        public FolderChooserDialog show(FragmentManager fragmentManager) {
            FolderChooserDialog folderChooserDialogBuild = build();
            folderChooserDialogBuild.show(fragmentManager);
            return folderChooserDialogBuild;
        }

        public FolderChooserDialog show(FragmentActivity fragmentActivity) {
            return show(fragmentActivity.getSupportFragmentManager());
        }
    }

    private static class FolderSorter implements Comparator<File> {
        private FolderSorter() {
        }

        @Override // java.util.Comparator
        public int compare(File file, File file2) {
            return file.getName().compareTo(file2.getName());
        }
    }
}
