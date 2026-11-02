package com.tbruyelle.rxpermissions;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import com.facebook.internal.NativeProtocol;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.sqlcipher.database.SQLiteDatabase;
import rx.Observable;
import rx.functions.Func1;
import rx.subjects.PublishSubject;

/* loaded from: classes2.dex */
public class RxPermissions {
    public static final String TAG = "RxPermissions";
    private static RxPermissions sSingleton;
    private Context mCtx;
    private boolean mLogging;
    private Map<String, PublishSubject<Permission>> mSubjects = new HashMap();

    public static RxPermissions getInstance(Context context) {
        if (sSingleton == null) {
            sSingleton = new RxPermissions(context.getApplicationContext());
        }
        return sSingleton;
    }

    RxPermissions(Context context) {
        this.mCtx = context;
    }

    public void setLogging(boolean z) {
        this.mLogging = z;
    }

    private void log(String str) {
        if (this.mLogging) {
            Log.d(TAG, str);
        }
    }

    public Observable<Permission> requestEach(String... strArr) {
        return requestEach(null, strArr);
    }

    public Observable<Permission> requestEach(Observable<?> observable, final String... strArr) {
        if (strArr == null || strArr.length == 0) {
            throw new IllegalArgumentException("RxPermissions.request/requestEach requires at least one input permission");
        }
        return oneOf(observable, pending(strArr)).flatMap(new Func1<Object, Observable<Permission>>() { // from class: com.tbruyelle.rxpermissions.RxPermissions.1
            @Override // rx.functions.Func1
            public Observable<Permission> call(Object obj) {
                return RxPermissions.this.request_(strArr);
            }
        });
    }

    public Observable<Boolean> request(String... strArr) {
        return request(null, strArr);
    }

    public Observable<Boolean> request(Observable<?> observable, String... strArr) {
        return requestEach(observable, strArr).buffer(strArr.length).flatMap(new Func1<List<Permission>, Observable<Boolean>>() { // from class: com.tbruyelle.rxpermissions.RxPermissions.2
            @Override // rx.functions.Func1
            public Observable<Boolean> call(List<Permission> list) {
                if (list.isEmpty()) {
                    return Observable.empty();
                }
                Iterator<Permission> it2 = list.iterator();
                while (it2.hasNext()) {
                    if (!it2.next().granted) {
                        return Observable.just(false);
                    }
                }
                return Observable.just(true);
            }
        });
    }

    private Observable<?> pending(String... strArr) {
        for (String str : strArr) {
            PublishSubject<Permission> publishSubject = this.mSubjects.get(str);
            if (publishSubject == null || !publishSubject.hasCompleted()) {
                return Observable.empty();
            }
        }
        return Observable.just(null);
    }

    private Observable<Object> oneOf(Observable<?> observable, Observable<?> observable2) {
        if (observable == null) {
            return Observable.just(null);
        }
        return Observable.merge(observable, observable2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Observable<Permission> request_(String... strArr) {
        if (this.mLogging) {
            log("Requesting permissions " + TextUtils.join(", ", strArr));
        }
        if (isGranted(strArr)) {
            if (this.mLogging) {
                log("GRANTED " + TextUtils.join(", ", strArr));
            }
            return Observable.from(strArr).map(new Func1<String, Permission>() { // from class: com.tbruyelle.rxpermissions.RxPermissions.3
                @Override // rx.functions.Func1
                public Permission call(String str) {
                    return new Permission(str, true);
                }
            });
        }
        ArrayList arrayList = new ArrayList(strArr.length);
        ArrayList arrayList2 = new ArrayList();
        for (String str : strArr) {
            PublishSubject<Permission> publishSubjectCreate = this.mSubjects.get(str);
            if (publishSubjectCreate == null || publishSubjectCreate.hasCompleted()) {
                if (publishSubjectCreate == null) {
                    arrayList2.add(str);
                }
                publishSubjectCreate = PublishSubject.create();
                this.mSubjects.put(str, publishSubjectCreate);
            }
            arrayList.add(publishSubjectCreate);
        }
        if (!arrayList2.isEmpty()) {
            startShadowActivity((String[]) arrayList2.toArray(new String[0]));
        }
        return Observable.concat(Observable.from(arrayList));
    }

    public Observable<Boolean> shouldShowRequestPermissionRationale(Activity activity, String... strArr) {
        if (isMarshmallow()) {
            return Observable.just(Boolean.valueOf(shouldShowRequestPermissionRationale_(activity, strArr)));
        }
        return Observable.just(false);
    }

    private boolean shouldShowRequestPermissionRationale_(Activity activity, String... strArr) {
        for (String str : strArr) {
            if (!activity.shouldShowRequestPermissionRationale(str)) {
                return false;
            }
        }
        return true;
    }

    void startShadowActivity(String[] strArr) {
        log("startShadowActivity " + TextUtils.join(", ", strArr));
        Intent intent = new Intent(this.mCtx, (Class<?>) ShadowActivity.class);
        intent.putExtra(NativeProtocol.RESULT_ARGS_PERMISSIONS, strArr);
        intent.addFlags(SQLiteDatabase.CREATE_IF_NECESSARY);
        this.mCtx.startActivity(intent);
    }

    public boolean isGranted(String... strArr) {
        return !isMarshmallow() || hasPermission_(strArr);
    }

    boolean isMarshmallow() {
        return Build.VERSION.SDK_INT >= 23;
    }

    private boolean hasPermission_(String... strArr) {
        for (String str : strArr) {
            if (this.mCtx.checkSelfPermission(str) != 0) {
                return false;
            }
        }
        return true;
    }

    void onDestroy() {
        log("onDestroy");
        Iterator<PublishSubject<Permission>> it2 = this.mSubjects.values().iterator();
        while (it2.hasNext()) {
            it2.next().onCompleted();
        }
    }

    void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        int length = strArr.length;
        for (int i2 = 0; i2 < length; i2++) {
            log("onRequestPermissionsResult  " + strArr[i2]);
            PublishSubject<Permission> publishSubject = this.mSubjects.get(strArr[i2]);
            if (publishSubject == null) {
                throw new IllegalStateException("RxPermissions.onRequestPermissionsResult invoked but didn't find the corresponding permission request.");
            }
            this.mSubjects.remove(strArr[i2]);
            publishSubject.onNext(new Permission(strArr[i2], iArr[i2] == 0));
            publishSubject.onCompleted();
        }
    }
}
