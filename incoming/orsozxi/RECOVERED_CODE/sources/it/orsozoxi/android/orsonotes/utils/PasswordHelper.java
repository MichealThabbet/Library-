package it.orsozoxi.android.orsonotes.utils;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import de.greenrobot.event.EventBus;
import it.orsozoxi.android.orsonotes.OrsoNotes;
import it.orsozoxi.android.orsonotes.R;
import it.orsozoxi.android.orsonotes.async.bus.PasswordRemovedEvent;
import it.orsozoxi.android.orsonotes.db.DbHelper;
import it.orsozoxi.android.orsonotes.models.Note;
import it.orsozoxi.android.orsonotes.models.PasswordValidator;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/* loaded from: classes3.dex */
public class PasswordHelper {
    public static void requestPassword(final Activity activity, final PasswordValidator passwordValidator) {
        View viewInflate = activity.getLayoutInflater().inflate(R.layout.password_request_dialog_layout, (ViewGroup) null);
        final EditText editText = (EditText) viewInflate.findViewById(R.id.password_request);
        final MaterialDialog materialDialogBuild = new MaterialDialog.Builder(activity).autoDismiss(false).title(R.string.insert_security_password).customView(viewInflate, false).positiveText(R.string.ok).positiveColorRes(R.color.colorPrimary).onPositive(new MaterialDialog.SingleButtonCallback() { // from class: it.orsozoxi.android.orsonotes.utils.-$$Lambda$PasswordHelper$5ghjeoAk5mXZ-AtYBaoHV8o-bXk
            @Override // com.afollestad.materialdialogs.MaterialDialog.SingleButtonCallback
            public final void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                PasswordHelper.lambda$requestPassword$0(activity, editText, passwordValidator, materialDialog, dialogAction);
            }
        }).neutralText(activity.getResources().getString(R.string.password_forgot)).onNeutral(new MaterialDialog.SingleButtonCallback() { // from class: it.orsozoxi.android.orsonotes.utils.-$$Lambda$PasswordHelper$gMLYbJNydNcLOyhGCg4MeJDXVxg
            @Override // com.afollestad.materialdialogs.MaterialDialog.SingleButtonCallback
            public final void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                PasswordHelper.lambda$requestPassword$1(activity, passwordValidator, materialDialog, dialogAction);
            }
        }).build();
        materialDialogBuild.setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: it.orsozoxi.android.orsonotes.utils.-$$Lambda$PasswordHelper$-F8JKZU5StzzjQGFNNhB_FS91B4
            @Override // android.content.DialogInterface.OnCancelListener
            public final void onCancel(DialogInterface dialogInterface) {
                PasswordHelper.lambda$requestPassword$2(editText, passwordValidator, dialogInterface);
            }
        });
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: it.orsozoxi.android.orsonotes.utils.-$$Lambda$PasswordHelper$m8L428MpW0CQqQrKUt3Atu5_eJs
            @Override // android.widget.TextView.OnEditorActionListener
            public final boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                return PasswordHelper.lambda$requestPassword$3(materialDialogBuild, textView, i, keyEvent);
            }
        });
        materialDialogBuild.show();
        new Handler().postDelayed(new Runnable() { // from class: it.orsozoxi.android.orsonotes.utils.-$$Lambda$PasswordHelper$-9iZ-PJRdiUWwAPKnH46I3l8P0Y
            @Override // java.lang.Runnable
            public final void run() {
                KeyboardUtils.showKeyboard(editText);
            }
        }, 100L);
    }

    static /* synthetic */ void lambda$requestPassword$0(Activity activity, EditText editText, PasswordValidator passwordValidator, MaterialDialog materialDialog, DialogAction dialogAction) {
        boolean zEquals;
        String string = activity.getSharedPreferences(Constants.PREFS_NAME, 4).getString(ConstantsBase.PREF_PASSWORD, "");
        if (string.equals("")) {
            editText.getText().toString();
            zEquals = true;
            removePassword();
        } else {
            zEquals = Security.md5(editText.getText().toString()).equals(string);
        }
        if (zEquals) {
            KeyboardUtils.hideKeyboard(editText);
            materialDialog.dismiss();
            passwordValidator.onPasswordValidated(PasswordValidator.Result.SUCCEED);
            return;
        }
        editText.setError(activity.getString(R.string.wrong_password));
    }

    static /* synthetic */ void lambda$requestPassword$1(Activity activity, PasswordValidator passwordValidator, MaterialDialog materialDialog, DialogAction dialogAction) {
        resetPassword(activity);
        passwordValidator.onPasswordValidated(PasswordValidator.Result.RESTORE);
        materialDialog.dismiss();
    }

    static /* synthetic */ void lambda$requestPassword$2(EditText editText, PasswordValidator passwordValidator, DialogInterface dialogInterface) {
        KeyboardUtils.hideKeyboard(editText);
        dialogInterface.dismiss();
        passwordValidator.onPasswordValidated(PasswordValidator.Result.FAIL);
    }

    static /* synthetic */ boolean lambda$requestPassword$3(MaterialDialog materialDialog, TextView textView, int i, KeyEvent keyEvent) {
        if (i != 6) {
            return false;
        }
        materialDialog.getActionButton(DialogAction.POSITIVE).callOnClick();
        return true;
    }

    public static void resetPassword(final Activity activity) {
        View viewInflate = activity.getLayoutInflater().inflate(R.layout.password_reset_dialog_layout, (ViewGroup) null);
        final EditText editText = (EditText) viewInflate.findViewById(R.id.reset_password_answer);
        final MaterialDialog materialDialogBuild = new MaterialDialog.Builder(activity).title(OrsoNotes.getSharedPreferences().getString(ConstantsBase.PREF_PASSWORD_QUESTION, "")).customView(viewInflate, false).autoDismiss(false).contentColorRes(R.color.text_color).positiveText(R.string.ok).onPositive(new MaterialDialog.SingleButtonCallback() { // from class: it.orsozoxi.android.orsonotes.utils.-$$Lambda$PasswordHelper$0QRayoa7cNRZwCb769YUtv7XV-g
            @Override // com.afollestad.materialdialogs.MaterialDialog.SingleButtonCallback
            public final void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                PasswordHelper.lambda$resetPassword$5(editText, activity, materialDialog, dialogAction);
            }
        }).build();
        materialDialogBuild.show();
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: it.orsozoxi.android.orsonotes.utils.-$$Lambda$PasswordHelper$zWPUpclR3ewTFK7H_oDTlggnJlA
            @Override // android.widget.TextView.OnEditorActionListener
            public final boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                return PasswordHelper.lambda$resetPassword$6(materialDialogBuild, textView, i, keyEvent);
            }
        });
        new Handler().postDelayed(new Runnable() { // from class: it.orsozoxi.android.orsonotes.utils.-$$Lambda$PasswordHelper$D9kFrRV-7SVC2dNDy-9IjBUUJYM
            @Override // java.lang.Runnable
            public final void run() {
                KeyboardUtils.showKeyboard(editText);
            }
        }, 100L);
    }

    static /* synthetic */ void lambda$resetPassword$5(EditText editText, Activity activity, MaterialDialog materialDialog, DialogAction dialogAction) {
        if (Security.md5(editText.getText().toString()).equals(OrsoNotes.getSharedPreferences().getString(ConstantsBase.PREF_PASSWORD_ANSWER, ""))) {
            materialDialog.dismiss();
            removePassword();
        } else {
            editText.setError(activity.getString(R.string.wrong_answer));
        }
    }

    static /* synthetic */ boolean lambda$resetPassword$6(MaterialDialog materialDialog, TextView textView, int i, KeyEvent keyEvent) {
        if (i != 6) {
            return false;
        }
        materialDialog.getActionButton(DialogAction.POSITIVE).callOnClick();
        return true;
    }

    public static void removePassword() {
        Observable.from(DbHelper.getInstance().getNotesWithLock(true)).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).doOnNext(new Action1() { // from class: it.orsozoxi.android.orsonotes.utils.-$$Lambda$PasswordHelper$MlGt2XCUNIRaA01xlJ3t0UJ1qOw
            @Override // rx.functions.Action1
            public final void call(Object obj) {
                PasswordHelper.lambda$removePassword$8((Note) obj);
            }
        }).doOnCompleted(new Action0() { // from class: it.orsozoxi.android.orsonotes.utils.-$$Lambda$PasswordHelper$5Blh3rPLKOl2_iHKXmHqJvNgrF8
            @Override // rx.functions.Action0
            public final void call() {
                PasswordHelper.lambda$removePassword$9();
            }
        }).subscribe();
    }

    static /* synthetic */ void lambda$removePassword$8(Note note) {
        note.setLocked((Boolean) false);
        DbHelper.getInstance().updateNote(note, false);
    }

    static /* synthetic */ void lambda$removePassword$9() {
        OrsoNotes.getSharedPreferences().edit().remove(ConstantsBase.PREF_PASSWORD).remove(ConstantsBase.PREF_PASSWORD_QUESTION).remove(ConstantsBase.PREF_PASSWORD_ANSWER).remove("settings_password_access").apply();
        EventBus.getDefault().post(new PasswordRemovedEvent());
    }
}
