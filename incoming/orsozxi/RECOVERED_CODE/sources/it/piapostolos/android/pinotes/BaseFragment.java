package it.piapostolos.android.pinotes;

import android.os.SystemClock;
import androidx.fragment.app.Fragment;

/* loaded from: classes3.dex */
public class BaseFragment extends Fragment {
    private static final long OPTIONS_ITEM_CLICK_DELAY_TIME = 1000;
    private long mLastClickTime;

    @Override // androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
    }

    protected boolean isOptionsItemFastClick() {
        if (SystemClock.elapsedRealtime() - this.mLastClickTime < 1000) {
            return true;
        }
        this.mLastClickTime = SystemClock.elapsedRealtime();
        return false;
    }
}
