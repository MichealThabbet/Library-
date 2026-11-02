package com.afollestad.materialdialogs.folderselector;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.webkit.MimeTypeMap;
import androidx.core.content.ContextCompat;
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
public class FileChooserDialog extends DialogFragment implements MaterialDialog.ListCallback {
    private static final String DEFAULT_TAG = "[MD_FILE_SELECTOR]";
    private FileCallback callback;
    private boolean canGoUp = true;
    private File[] parentContents;
    private File parentFolder;

    public interface FileCallback {
        void onFileChooserDismissed(FileChooserDialog fileChooserDialog);

        void onFileSelection(FileChooserDialog fileChooserDialog, File file);
    }

    CharSequence[] getContentsArray() {
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

    File[] listFiles(String str, String[] strArr) {
        boolean z;
        File[] fileArrListFiles = this.parentFolder.listFiles();
        ArrayList arrayList = new ArrayList();
        if (fileArrListFiles == null) {
            return null;
        }
        MimeTypeMap singleton = MimeTypeMap.getSingleton();
        for (File file : fileArrListFiles) {
            if (file.isDirectory()) {
                arrayList.add(file);
            } else if (strArr != null) {
                int length = strArr.length;
                int i = 0;
                while (true) {
                    if (i >= length) {
                        z = false;
                        break;
                    }
                    if (file.getName().toLowerCase().endsWith(strArr[i].toLowerCase())) {
                        z = true;
                        break;
                    }
                    i++;
                }
                if (z) {
                    arrayList.add(file);
                }
            } else if (str != null && fileIsMimeType(file, str, singleton)) {
                arrayList.add(file);
            }
        }
        Collections.sort(arrayList, new FileSorter());
        return (File[]) arrayList.toArray(new File[arrayList.size()]);
    }

    boolean fileIsMimeType(File file, String str, MimeTypeMap mimeTypeMap) {
        int iLastIndexOf;
        if (str == null || str.equals("*/*")) {
            return true;
        }
        String string = file.toURI().toString();
        int iLastIndexOf2 = string.lastIndexOf(46);
        if (iLastIndexOf2 == -1) {
            return false;
        }
        String strSubstring = string.substring(iLastIndexOf2 + 1);
        if (strSubstring.endsWith("json")) {
            return str.startsWith("application/json");
        }
        String mimeTypeFromExtension = mimeTypeMap.getMimeTypeFromExtension(strSubstring);
        if (mimeTypeFromExtension == null) {
            return false;
        }
        if (mimeTypeFromExtension.equals(str)) {
            return true;
        }
        int iLastIndexOf3 = str.lastIndexOf(47);
        if (iLastIndexOf3 == -1) {
            return false;
        }
        return str.substring(iLastIndexOf3 + 1).equals("*") && (iLastIndexOf = mimeTypeFromExtension.lastIndexOf(47)) != -1 && mimeTypeFromExtension.substring(0, iLastIndexOf).equals(str.substring(0, iLastIndexOf3));
    }

    @Override // androidx.fragment.app.DialogFragment
    public Dialog onCreateDialog(Bundle bundle) {
        if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(getActivity(), "android.permission.READ_EXTERNAL_STORAGE") != 0) {
            return new MaterialDialog.Builder(getActivity()).title(R.string.md_error_label).content(R.string.md_storage_perm_error).positiveText(android.R.string.ok).build();
        }
        if (getArguments() == null || !getArguments().containsKey("builder")) {
            throw new IllegalStateException("You must create a FileChooserDialog using the Builder.");
        }
        if (!getArguments().containsKey("current_path")) {
            getArguments().putString("current_path", getBuilder().initialPath);
        }
        this.parentFolder = new File(getArguments().getString("current_path"));
        checkIfCanGoUp();
        this.parentContents = listFiles(getBuilder().mimeType, getBuilder().extensions);
        return new MaterialDialog.Builder(getActivity()).title(this.parentFolder.getAbsolutePath()).typeface(getBuilder().mediumFont, getBuilder().regularFont).items(getContentsArray()).itemsCallback(this).onNegative(new MaterialDialog.SingleButtonCallback() { // from class: com.afollestad.materialdialogs.folderselector.FileChooserDialog.1
            @Override // com.afollestad.materialdialogs.MaterialDialog.SingleButtonCallback
            public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                materialDialog.dismiss();
            }
        }).autoDismiss(false).negativeText(getBuilder().cancelButton).build();
    }

    @Override // androidx.fragment.app.DialogFragment, android.content.DialogInterface.OnDismissListener
    public void onDismiss(DialogInterface dialogInterface) {
        super.onDismiss(dialogInterface);
        FileCallback fileCallback = this.callback;
        if (fileCallback != null) {
            fileCallback.onFileChooserDismissed(this);
        }
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
        if (this.parentFolder.isFile()) {
            this.callback.onFileSelection(this, this.parentFolder);
            dismiss();
            return;
        }
        this.parentContents = listFiles(getBuilder().mimeType, getBuilder().extensions);
        MaterialDialog materialDialog2 = (MaterialDialog) getDialog();
        materialDialog2.setTitle(this.parentFolder.getAbsolutePath());
        getArguments().putString("current_path", this.parentFolder.getAbsolutePath());
        materialDialog2.setItems(getContentsArray());
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

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() instanceof FileCallback) {
            this.callback = (FileCallback) getActivity();
        } else {
            if (getParentFragment() instanceof FileCallback) {
                this.callback = (FileCallback) getParentFragment();
                return;
            }
            throw new IllegalStateException("FileChooserDialog needs to be shown from an Activity/Fragment implementing FileCallback.");
        }
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

    public void show(FragmentActivity fragmentActivity) {
        show(fragmentActivity.getSupportFragmentManager());
    }

    public String getInitialPath() {
        return getBuilder().initialPath;
    }

    private Builder getBuilder() {
        return (Builder) getArguments().getSerializable("builder");
    }

    public static class Builder implements Serializable {
        final transient Context context;
        String[] extensions;
        String mediumFont;
        String regularFont;
        String tag;
        int cancelButton = android.R.string.cancel;
        String initialPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        String mimeType = null;
        String goUpLabel = "...";

        public Builder(Context context) {
            this.context = context;
        }

        public Builder typeface(String str, String str2) {
            this.mediumFont = str;
            this.regularFont = str2;
            return this;
        }

        public Builder cancelButton(int i) {
            this.cancelButton = i;
            return this;
        }

        public Builder initialPath(String str) {
            if (str == null) {
                str = File.separator;
            }
            this.initialPath = str;
            return this;
        }

        public Builder mimeType(String str) {
            this.mimeType = str;
            return this;
        }

        public Builder extensionsFilter(String... strArr) {
            this.extensions = strArr;
            return this;
        }

        public Builder tag(String str) {
            if (str == null) {
                str = FileChooserDialog.DEFAULT_TAG;
            }
            this.tag = str;
            return this;
        }

        public Builder goUpLabel(String str) {
            this.goUpLabel = str;
            return this;
        }

        public FileChooserDialog build() {
            FileChooserDialog fileChooserDialog = new FileChooserDialog();
            Bundle bundle = new Bundle();
            bundle.putSerializable("builder", this);
            fileChooserDialog.setArguments(bundle);
            return fileChooserDialog;
        }

        public FileChooserDialog show(FragmentManager fragmentManager) {
            FileChooserDialog fileChooserDialogBuild = build();
            fileChooserDialogBuild.show(fragmentManager);
            return fileChooserDialogBuild;
        }

        public FileChooserDialog show(FragmentActivity fragmentActivity) {
            return show(fragmentActivity.getSupportFragmentManager());
        }
    }

    private static class FileSorter implements Comparator<File> {
        private FileSorter() {
        }

        @Override // java.util.Comparator
        public int compare(File file, File file2) {
            if (file.isDirectory() && !file2.isDirectory()) {
                return -1;
            }
            if (file.isDirectory() || !file2.isDirectory()) {
                return file.getName().compareTo(file2.getName());
            }
            return 1;
        }
    }
}
