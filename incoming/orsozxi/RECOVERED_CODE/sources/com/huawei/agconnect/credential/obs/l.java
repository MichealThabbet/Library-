package com.huawei.agconnect.credential.obs;

import android.os.Handler;
import android.os.HandlerThread;
import android.text.TextUtils;
import com.huawei.agconnect.AGConnectInstance;
import com.huawei.agconnect.common.api.BackendService;
import com.huawei.agconnect.common.api.Logger;
import com.huawei.hmf.tasks.OnCompleteListener;
import com.huawei.hmf.tasks.Task;
import com.huawei.hmf.tasks.TaskExecutors;
import com.huawei.hms.push.constant.RemoteMessageConst;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes2.dex */
public class l {
    private static final l a = new l();
    private Map<String, List<r>> b = new HashMap();
    private Map<String, r> c = new HashMap();
    private AtomicBoolean d = new AtomicBoolean(false);
    private String e;
    private HandlerThread f;
    private Handler g;

    private l() {
        HandlerThread handlerThread = new HandlerThread("subThread");
        this.f = handlerThread;
        handlerThread.start();
        this.g = new Handler(this.f.getLooper());
    }

    public static final l a() {
        return a;
    }

    private String a(AGConnectInstance aGConnectInstance, List<ad> list) {
        for (ad adVar : list) {
            if (adVar.a().equalsIgnoreCase(aGConnectInstance.getOptions().getRoutePolicy().getRouteName())) {
                return adVar.b();
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(int i, String str) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(13, i);
        long time = calendar.getTime().getTime();
        r rVar = this.c.get(str);
        if (rVar != null) {
            rVar.validTime = time;
        }
        Logger.i("AGCHost", "updateTTL:" + time);
        r.a(str, time);
    }

    private void a(final AGConnectInstance aGConnectInstance, long j) {
        if (this.d.get() || !a(j)) {
            return;
        }
        this.d.set(true);
        this.g.postDelayed(new Runnable() { // from class: com.huawei.agconnect.credential.obs.l.1
            @Override // java.lang.Runnable
            public void run() {
                Logger.i("AGCHost", "getCachedHostAsync#start");
                af afVar = new af(aGConnectInstance);
                afVar.a(l.this.e);
                BackendService.sendRequest(afVar, 1, ag.class, new BackendService.Options.Builder().app(aGConnectInstance).clientToken(false).build()).addOnCompleteListener(TaskExecutors.immediate(), new OnCompleteListener<ag>() { // from class: com.huawei.agconnect.credential.obs.l.1.1
                    @Override // com.huawei.hmf.tasks.OnCompleteListener
                    public void onComplete(Task<ag> task) throws NumberFormatException {
                        int iNextInt = new Random().nextInt(172801) + RemoteMessageConst.DEFAULT_TTL;
                        Logger.i("AGCHost", "default ttl=" + iNextInt);
                        if (task.isSuccessful()) {
                            ag result = task.getResult();
                            l.this.a(aGConnectInstance, result);
                            try {
                                iNextInt = Integer.parseInt(result.c());
                            } catch (NumberFormatException unused) {
                                Logger.i("AGCHost", "parse ttl failed, use default instead");
                            }
                        }
                        l.this.a(iNextInt, aGConnectInstance.getIdentifier());
                    }
                });
            }
        }, 5000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(AGConnectInstance aGConnectInstance, ag agVar) {
        String strA = a(aGConnectInstance, agVar.a());
        String strA2 = a(aGConnectInstance, agVar.b());
        Logger.d("AGCHost", "cached main host:" + strA);
        Logger.d("AGCHost", "cached backup host:" + strA2);
        String identifier = aGConnectInstance.getIdentifier();
        if (TextUtils.isEmpty(strA) || TextUtils.isEmpty(strA2)) {
            this.c.remove(identifier);
            r.c(identifier);
        } else {
            r rVar = new r(strA, strA2);
            this.c.put(identifier, rVar);
            rVar.a(identifier);
        }
    }

    private boolean a(long j) {
        return Calendar.getInstance().getTime().after(new Date(j));
    }

    private r b(String str) {
        return r.b(str);
    }

    List<r> a(AGConnectInstance aGConnectInstance) {
        long j;
        List<r> listSingletonList;
        List<r> list;
        long j2;
        String identifier = aGConnectInstance.getIdentifier();
        if (this.b.containsKey(identifier)) {
            list = this.b.get(identifier);
            j2 = 0;
        } else {
            r rVar = new r(aGConnectInstance.getOptions().getString("agcgw/url"), aGConnectInstance.getOptions().getString("agcgw/backurl"));
            r rVar2 = this.c.get(identifier);
            if (rVar2 != null) {
                j = rVar2.validTime;
                listSingletonList = Arrays.asList(rVar2, rVar);
            } else {
                r rVarB = b(identifier);
                j = rVarB.validTime;
                if (rVarB == null || !rVarB.e()) {
                    listSingletonList = Collections.singletonList(rVar);
                } else {
                    this.c.put(identifier, rVarB);
                    listSingletonList = Arrays.asList(rVarB, rVar);
                }
            }
            this.b.put(identifier, listSingletonList);
            list = listSingletonList;
            j2 = j;
        }
        a(aGConnectInstance, j2);
        return list;
    }

    public void a(String str) {
        if ("agconnect-credential".equals(str)) {
            return;
        }
        this.e = str;
    }
}
