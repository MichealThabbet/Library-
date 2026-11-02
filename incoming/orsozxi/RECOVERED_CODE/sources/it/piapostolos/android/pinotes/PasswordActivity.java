package it.piapostolos.android.pinotes;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import de.greenrobot.event.EventBus;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.LifecycleCallback;
import it.piapostolos.android.pinotes.async.bus.PasswordRemovedEvent;
import it.piapostolos.android.pinotes.db.DbHelper;
import it.piapostolos.android.pinotes.models.Note;
import it.piapostolos.android.pinotes.models.ONStyle;
import it.piapostolos.android.pinotes.models.PasswordValidator;
import it.piapostolos.android.pinotes.utils.ConstantsBase;
import it.piapostolos.android.pinotes.utils.PasswordHelper;
import it.piapostolos.android.pinotes.utils.Security;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/* loaded from: classes3.dex */
public class PasswordActivity extends BaseActivity {
    private EditText answer;
    private EditText answerCheck;
    private ViewGroup croutonHandle;
    private PasswordActivity mActivity;
    private EditText password;
    private EditText passwordCheck;
    private EditText question;

    @Override // it.piapostolos.android.pinotes.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        setContentView(R.layout.activity_password);
        getWindow().setLayout((int) (displayMetrics.widthPixels * 0.8d), (int) (displayMetrics.heightPixels * 0.8d));
        this.mActivity = this;
        setActionBarTitle(getString(R.string.title_activity_password));
        initViews();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this, 1);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    private void initViews() {
        this.croutonHandle = (ViewGroup) findViewById(R.id.crouton_handle);
        this.password = (EditText) findViewById(R.id.password);
        this.passwordCheck = (EditText) findViewById(R.id.password_check);
        this.question = (EditText) findViewById(R.id.question);
        this.answer = (EditText) findViewById(R.id.answer);
        this.answerCheck = (EditText) findViewById(R.id.answer_check);
        findViewById(R.id.password_remove).setOnClickListener(new View.OnClickListener() { // from class: it.piapostolos.android.pinotes.-$$Lambda$PasswordActivity$Z-MG8taHLWrtsqRyEtMbjiq_-lE
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$initViews$1$PasswordActivity(view);
            }
        });
        findViewById(R.id.password_confirm).setOnClickListener(new View.OnClickListener() { // from class: it.piapostolos.android.pinotes.-$$Lambda$PasswordActivity$PcQjkCEo3IsxFtXmpm1yTNmhsEs
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$initViews$3$PasswordActivity(view);
            }
        });
        findViewById(R.id.password_forgotten).setOnClickListener(new View.OnClickListener() { // from class: it.piapostolos.android.pinotes.-$$Lambda$PasswordActivity$gHXyQW9OoIjWdHY84CHoSD4Of7w
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$initViews$4$PasswordActivity(view);
            }
        });
    }

    public /* synthetic */ void lambda$initViews$1$PasswordActivity(View view) {
        if (this.prefs.getString(ConstantsBase.PREF_PASSWORD, null) != null) {
            PasswordHelper.requestPassword(this.mActivity, new PasswordValidator() { // from class: it.piapostolos.android.pinotes.-$$Lambda$PasswordActivity$ylzGPg4FyRMqnhhZSxrSqsacr-g
                @Override // it.piapostolos.android.pinotes.models.PasswordValidator
                public final void onPasswordValidated(PasswordValidator.Result result) {
                    this.f$0.lambda$initViews$0$PasswordActivity(result);
                }
            });
        } else {
            PasswordHelper.removePassword();
        }
    }

    public /* synthetic */ void lambda$initViews$0$PasswordActivity(PasswordValidator.Result result) {
        if (result.equals(PasswordValidator.Result.SUCCEED)) {
            updatePassword(null, null, null);
        }
    }

    public /* synthetic */ void lambda$initViews$3$PasswordActivity(View view) {
        if (checkData()) {
            final String string = this.password.getText().toString();
            final String string2 = this.question.getText().toString();
            final String string3 = this.answer.getText().toString();
            if (this.prefs.getString(ConstantsBase.PREF_PASSWORD, null) != null) {
                PasswordHelper.requestPassword(this.mActivity, new PasswordValidator() { // from class: it.piapostolos.android.pinotes.-$$Lambda$PasswordActivity$lYpK023TzJTjKrZwuwRhEBZ1mMQ
                    @Override // it.piapostolos.android.pinotes.models.PasswordValidator
                    public final void onPasswordValidated(PasswordValidator.Result result) {
                        this.f$0.lambda$initViews$2$PasswordActivity(string, string2, string3, result);
                    }
                });
            } else {
                updatePassword(string, string2, string3);
            }
        }
    }

    public /* synthetic */ void lambda$initViews$2$PasswordActivity(String str, String str2, String str3, PasswordValidator.Result result) {
        if (result.equals(PasswordValidator.Result.SUCCEED)) {
            updatePassword(str, str2, str3);
        }
    }

    public /* synthetic */ void lambda$initViews$4$PasswordActivity(View view) {
        if (this.prefs.getString(ConstantsBase.PREF_PASSWORD, "").length() == 0) {
            Crouton.makeText(this.mActivity, R.string.password_not_set, ONStyle.WARN, this.croutonHandle).show();
        } else {
            PasswordHelper.resetPassword(this);
        }
    }

    public void onEvent(PasswordRemovedEvent passwordRemovedEvent) {
        this.passwordCheck.setText("");
        this.password.setText("");
        this.question.setText("");
        this.answer.setText("");
        this.answerCheck.setText("");
        Crouton croutonMakeText = Crouton.makeText(this.mActivity, R.string.password_successfully_removed, ONStyle.ALERT, this.croutonHandle);
        croutonMakeText.setLifecycleCallback(new LifecycleCallback() { // from class: it.piapostolos.android.pinotes.PasswordActivity.1
            @Override // de.keyboardsurfer.android.widget.crouton.LifecycleCallback
            public void onDisplayed() {
            }

            @Override // de.keyboardsurfer.android.widget.crouton.LifecycleCallback
            public void onRemoved() {
                PasswordActivity.this.onBackPressed();
            }
        });
        croutonMakeText.show();
    }

    private void updatePassword(final String str, final String str2, final String str3) {
        if (str == null) {
            if (this.prefs.getString(ConstantsBase.PREF_PASSWORD, "").length() == 0) {
                Crouton.makeText(this.mActivity, R.string.password_not_set, ONStyle.WARN, this.croutonHandle).show();
                return;
            } else {
                new MaterialDialog.Builder(this.mActivity).content(R.string.agree_unlocking_all_notes).positiveText(R.string.ok).onPositive(new MaterialDialog.SingleButtonCallback() { // from class: it.piapostolos.android.pinotes.-$$Lambda$PasswordActivity$-39VflGDWv4Eb2WVgqy9wnkue4k
                    @Override // com.afollestad.materialdialogs.MaterialDialog.SingleButtonCallback
                    public final void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                        PasswordHelper.removePassword();
                    }
                }).build().show();
                return;
            }
        }
        if (str.length() == 0) {
            Crouton.makeText(this.mActivity, R.string.empty_password, ONStyle.WARN, this.croutonHandle).show();
        } else {
            Observable.from(DbHelper.getInstance().getNotesWithLock(true)).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).doOnSubscribe(new Action0() { // from class: it.piapostolos.android.pinotes.-$$Lambda$PasswordActivity$74dOh2A792Qg7lvoC_Kk_Fbfv_o
                @Override // rx.functions.Action0
                public final void call() {
                    this.f$0.lambda$updatePassword$6$PasswordActivity(str, str2, str3);
                }
            }).doOnNext(new Action1() { // from class: it.piapostolos.android.pinotes.-$$Lambda$PasswordActivity$zdaiCgppETp9lF56Aj23eUotp_E
                @Override // rx.functions.Action1
                public final void call(Object obj) {
                    DbHelper.getInstance().updateNote((Note) obj, false);
                }
            }).doOnCompleted(new Action0() { // from class: it.piapostolos.android.pinotes.-$$Lambda$PasswordActivity$75fHWG31sl49b776kJjOSakJONo
                @Override // rx.functions.Action0
                public final void call() {
                    this.f$0.lambda$updatePassword$8$PasswordActivity();
                }
            }).subscribe();
        }
    }

    public /* synthetic */ void lambda$updatePassword$6$PasswordActivity(String str, String str2, String str3) {
        this.prefs.edit().putString(ConstantsBase.PREF_PASSWORD, Security.md5(str)).putString(ConstantsBase.PREF_PASSWORD_QUESTION, str2).putString(ConstantsBase.PREF_PASSWORD_ANSWER, Security.md5(str3)).commit();
    }

    public /* synthetic */ void lambda$updatePassword$8$PasswordActivity() {
        Crouton croutonMakeText = Crouton.makeText(this.mActivity, R.string.password_successfully_changed, ONStyle.CONFIRM, this.croutonHandle);
        croutonMakeText.setLifecycleCallback(new LifecycleCallback() { // from class: it.piapostolos.android.pinotes.PasswordActivity.2
            @Override // de.keyboardsurfer.android.widget.crouton.LifecycleCallback
            public void onDisplayed() {
            }

            @Override // de.keyboardsurfer.android.widget.crouton.LifecycleCallback
            public void onRemoved() {
                PasswordActivity.this.onBackPressed();
            }
        });
        croutonMakeText.show();
    }

    private boolean checkData() {
        if (this.password.getText().length() == this.passwordCheck.getText().length() && this.passwordCheck.getText().length() == 0) {
            return true;
        }
        boolean z = this.password.getText().toString().length() > 0;
        boolean z2 = this.passwordCheck.getText().toString().length() > 0 && this.password.getText().toString().equals(this.passwordCheck.getText().toString());
        boolean z3 = this.question.getText().toString().length() > 0;
        boolean z4 = this.answer.getText().toString().length() > 0;
        boolean z5 = this.answerCheck.getText().toString().length() > 0 && this.answer.getText().toString().equals(this.answerCheck.getText().toString());
        if (z && z2 && z3 && z4 && z5) {
            return true;
        }
        if (!z) {
            this.password.setError(getString(R.string.settings_password_not_matching));
        }
        if (!z2) {
            this.passwordCheck.setError(getString(R.string.settings_password_not_matching));
        }
        if (!z3) {
            this.question.setError(getString(R.string.settings_password_question));
        }
        if (!z4) {
            this.answer.setError(getString(R.string.settings_answer_not_matching));
        }
        if (!z5) {
            this.answerCheck.setError(getString(R.string.settings_answer_not_matching));
        }
        return false;
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        setResult(-1);
        finish();
    }
}
