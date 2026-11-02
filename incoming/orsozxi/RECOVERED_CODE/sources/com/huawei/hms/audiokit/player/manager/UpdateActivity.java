package com.huawei.hms.audiokit.player.manager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import com.huawei.hms.api.errorcode.HwAudioErrorCode;
import com.huawei.hms.audiokit.R;
import com.huawei.hms.audiokit.player.callback.HwAudioConfigCallBack;
import com.huawei.hms.support.api.entity.core.CommonCode;
import com.huawei.hms.ui.SafeIntent;
import com.huawei.hms.update.kpms.KpmsConstant;
import java.util.Map;

/* loaded from: classes2.dex */
public class UpdateActivity extends Activity {
    private static final Map<Integer, Integer> a = new i();
    private int b = HwAudioErrorCode.ERROR_MODULE_UPGRADE_UNKNOWN;

    @Override // android.app.Activity
    protected void onActivityResult(int i, int i2, Intent intent) throws IllegalArgumentException {
        super.onActivityResult(i, i2, intent);
        Log.i("UpdateActivity", "onActivityResult requestCode:" + i + " resultCode:" + i2);
        if (i == 1 && i2 == -1) {
            Log.i("UpdateActivity", "onActivityResult if");
            HwAudioManagerFactory.createHwAudioManager(HwAudioManagerFactory.b(), HwAudioManagerFactory.a());
        } else {
            Log.i("UpdateActivity", "onActivityResult else");
            if (intent != null) {
                int intExtra = new SafeIntent(intent).getIntExtra(KpmsConstant.KIT_UPDATE_RESULT, 207);
                if (a.containsKey(Integer.valueOf(intExtra))) {
                    this.b = a.get(Integer.valueOf(intExtra)).intValue();
                }
                Log.i("UpdateActivity", "kitUpdateResult:" + intExtra + " upgradeCode:" + this.b);
            }
            HwAudioConfigCallBack hwAudioConfigCallBackA = HwAudioManagerFactory.a();
            if (hwAudioConfigCallBackA != null) {
                hwAudioConfigCallBackA.onError(this.b);
            }
        }
        finish();
    }

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_update);
        Intent intent = getIntent();
        if (intent != null) {
            Log.d("UpdateActivity", "onCreate intent:" + intent);
            try {
                Parcelable parcelableB = com.huawei.hms.audiokit.d.b(new SafeIntent(intent).getExtras(), CommonCode.Resolution.HAS_RESOLUTION_FROM_APK);
                if (parcelableB instanceof Intent) {
                    startActivityForResult((Intent) parcelableB, 1);
                    return;
                }
                return;
            } catch (Exception e) {
                Log.w("UpdateActivity", "start update components activity error:" + e.getMessage());
            }
        }
        finish();
    }
}
