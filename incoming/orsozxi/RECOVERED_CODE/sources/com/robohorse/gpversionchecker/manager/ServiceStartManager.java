package com.robohorse.gpversionchecker.manager;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import com.robohorse.gpversionchecker.VersionCheckerService;
import com.robohorse.gpversionchecker.domain.CheckingStrategy;
import com.robohorse.gpversionchecker.domain.Version;
import com.robohorse.gpversionchecker.domain.VersionCheckedException;
import com.robohorse.gpversionchecker.provider.SharedDataProvider;
import com.robohorse.gpversionchecker.utils.DateFormatUtils;
import java.util.Date;

/* loaded from: classes2.dex */
public class ServiceStartManager {
    private final DateFormatUtils formatUtils;
    private final SharedDataProvider sharedDataProvider;

    public ServiceStartManager(SharedDataProvider sharedDataProvider, DateFormatUtils dateFormatUtils) {
        this.sharedDataProvider = sharedDataProvider;
        this.formatUtils = dateFormatUtils;
    }

    /* renamed from: com.robohorse.gpversionchecker.manager.ServiceStartManager$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$robohorse$gpversionchecker$domain$CheckingStrategy;

        static {
            int[] iArr = new int[CheckingStrategy.values().length];
            $SwitchMap$com$robohorse$gpversionchecker$domain$CheckingStrategy = iArr;
            try {
                iArr[CheckingStrategy.ALWAYS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$robohorse$gpversionchecker$domain$CheckingStrategy[CheckingStrategy.ONE_PER_VERSION.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$robohorse$gpversionchecker$domain$CheckingStrategy[CheckingStrategy.ONE_PER_VERSION_PER_DAY.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$robohorse$gpversionchecker$domain$CheckingStrategy[CheckingStrategy.ONE_PER_DAY.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public void checkAndStartService(Activity activity, CheckingStrategy checkingStrategy) {
        int i = AnonymousClass1.$SwitchMap$com$robohorse$gpversionchecker$domain$CheckingStrategy[checkingStrategy.ordinal()];
        if (i == 1 || i == 2) {
            startService(activity);
            return;
        }
        if (i == 3 || i == 4) {
            if (this.formatUtils.formatTodayDate().after(new Date(this.sharedDataProvider.provideLastCheckTime()))) {
                startService(activity);
            }
        }
    }

    public void onResulted(CheckingStrategy checkingStrategy, Version version) throws VersionCheckedException {
        this.sharedDataProvider.saveCurrentDate();
        if (version != null) {
            if ((checkingStrategy == CheckingStrategy.ONE_PER_VERSION || checkingStrategy == CheckingStrategy.ONE_PER_VERSION_PER_DAY) && TextUtils.equals(this.sharedDataProvider.provideLastVersionCode(), version.getNewVersionCode())) {
                throw new VersionCheckedException();
            }
            this.sharedDataProvider.saveCurrentVersion(version);
        }
    }

    private void startService(Activity activity) {
        activity.startService(new Intent(activity, (Class<?>) VersionCheckerService.class));
    }
}
