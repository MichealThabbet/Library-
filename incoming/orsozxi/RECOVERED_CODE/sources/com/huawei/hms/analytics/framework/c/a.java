package com.huawei.hms.analytics.framework.c;

import android.text.TextUtils;
import com.huawei.hms.analytics.core.crypto.RandomUtil;
import com.huawei.hms.analytics.core.crypto.RsaCipher;
import com.huawei.hms.analytics.core.log.HiLog;
import com.huawei.hms.analytics.core.storage.Event;
import com.huawei.hms.analytics.core.storage.IStorageHandler;
import com.huawei.hms.analytics.framework.config.DeviceAttributeCollector;
import com.huawei.hms.analytics.framework.config.ICallback;
import com.huawei.hms.analytics.framework.config.ICollectorConfig;
import com.huawei.hms.analytics.framework.policy.IStoragePolicy;
import java.io.UnsupportedEncodingException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONException;

/* loaded from: classes2.dex */
public abstract class a {
    protected List<Event> a;
    protected ICallback b;
    protected IStorageHandler c;
    protected com.huawei.hms.analytics.framework.c.a.a d;
    private ICollectorConfig e;
    private IStoragePolicy f;

    public a(List<Event> list, com.huawei.hms.analytics.framework.c.a.a aVar, ICallback iCallback) {
        this.a = list;
        this.b = iCallback;
        this.d = aVar;
        ICollectorConfig iCollectorConfigA = com.huawei.hms.analytics.framework.b.b.a().a(aVar.a);
        this.e = iCollectorConfigA;
        iCollectorConfigA.setRegion(aVar.c);
        this.f = com.huawei.hms.analytics.framework.a.a.b(aVar.a);
        this.c = com.huawei.hms.analytics.framework.a.a.a(aVar.a);
    }

    private com.huawei.hms.analytics.framework.c.a.c a(String str) throws JSONException, InvalidKeySpecException {
        com.huawei.hms.analytics.framework.b.e eVarA = com.huawei.hms.analytics.framework.b.e.a();
        String str2 = this.d.c;
        com.huawei.hms.analytics.framework.b.d dVar = eVarA.a.get(str2);
        if (dVar == null) {
            dVar = new com.huawei.hms.analytics.framework.b.d();
            com.huawei.hms.analytics.framework.b.e.a(dVar, str2);
            eVarA.a.put(str2, dVar);
        } else {
            if (!dVar.d.equals(com.huawei.hms.analytics.framework.b.e.a(str2))) {
                com.huawei.hms.analytics.framework.b.e.a(dVar, str2);
            }
            if (System.currentTimeMillis() - dVar.c > 43200000) {
                String strGenerateSecureRandomStr = RandomUtil.getInstance().generateSecureRandomStr(16);
                String strEncrypt = RsaCipher.encrypt(dVar.a(), strGenerateSecureRandomStr);
                dVar.a = strGenerateSecureRandomStr;
                dVar.b = strEncrypt;
                dVar.c = System.currentTimeMillis();
            }
        }
        if (TextUtils.isEmpty(dVar.b)) {
            return null;
        }
        this.e.syncOaid();
        String appId = this.e.getAppId();
        DeviceAttributeCollector deviceAttribute = this.e.getDeviceAttribute(this.d.b);
        String str3 = dVar.b;
        com.huawei.hms.analytics.framework.c.a.b bVar = new com.huawei.hms.analytics.framework.c.a.b();
        bVar.b = appId;
        bVar.a = str3;
        bVar.e = str;
        bVar.c = this.d.a;
        StringBuffer stringBuffer = new StringBuffer("hmshi");
        stringBuffer.append(this.d.b);
        stringBuffer.append("qrt");
        bVar.f = stringBuffer.toString();
        StringBuilder sb = new StringBuilder();
        sb.append(System.currentTimeMillis());
        bVar.d = sb.toString();
        return new com.huawei.hms.analytics.framework.c.a.c(deviceAttribute, this.e.getEvtCustomHeader(this.d.b, bVar.a()), this.e.getRomAttribute(this.d.b), dVar.a, this.d.a);
    }

    private void a(byte[] bArr, String str, List<Event> list) {
        this.d.d = str;
        i iVar = new i(bArr, this.d, list);
        iVar.a = this.b;
        iVar.run();
    }

    private static byte[] a(List<Event> list, com.huawei.hms.analytics.framework.c.a.c cVar, boolean z) {
        String str;
        try {
            return com.huawei.hms.analytics.framework.g.a.a(cVar.a(list, z).toString().getBytes("UTF-8"));
        } catch (UnsupportedEncodingException unused) {
            str = "sendData(): getBytes - Unsupported coding format!!";
            HiLog.w("ReportAssignment", str);
            return new byte[0];
        } catch (JSONException unused2) {
            str = "json exception";
            HiLog.w("ReportAssignment", str);
            return new byte[0];
        }
    }

    public static long b(long j) {
        long j2 = j / 300;
        return j % 300 > 0 ? j2 + 1 : j2;
    }

    public final Event a() {
        return this.e.getSpecialEvent(this.d.b);
    }

    public final List<Event> a(int i) {
        List<Event> list = this.a;
        List<Event> listSubList = list.subList(i, Math.min(list.size(), i + 300));
        a(listSubList, false);
        return listSubList;
    }

    public final void a(long j) {
        ICallback iCallback = this.b;
        if (iCallback != null) {
            iCallback.onResult(-1, j, -1, this.a);
        }
    }

    public final void a(List<Event> list) throws JSONException, InvalidKeySpecException {
        String strGenerateSecureRandomStr = RandomUtil.getInstance().generateSecureRandomStr(16);
        com.huawei.hms.analytics.framework.c.a.c cVarA = a(strGenerateSecureRandomStr);
        if (cVarA == null) {
            throw new IllegalArgumentException("uploadEvtModel is null");
        }
        byte[] bArrA = a(list, cVarA, this.d.e);
        if (bArrA.length != 0) {
            a(bArrA, strGenerateSecureRandomStr, list);
        } else {
            HiLog.w("ReportAssignment", "request body is empty");
            throw new IllegalArgumentException("body length is empty");
        }
    }

    public final void a(List<Event> list, boolean z) {
        ArrayList arrayList = new ArrayList();
        Iterator<Event> it2 = list.iterator();
        while (it2.hasNext()) {
            Event next = it2.next();
            if (this.f.decide(IStoragePolicy.PolicyType.STORAGECYCLY, Long.parseLong(next.getEvttime()))) {
                arrayList.add(next);
                it2.remove();
            }
        }
        if (arrayList.size() <= 0 || !z || this.c == null) {
            return;
        }
        HiLog.d("ReportAssignment", "delete events: " + arrayList.size());
        this.c.deleteEvents(arrayList);
    }

    public final void a(boolean z, String str) {
        this.e.serviceListener(z, str);
    }

    public final boolean b() {
        return this.f.decide(IStoragePolicy.PolicyType.PARAMS, this.d.c);
    }

    public final void c() throws JSONException, InvalidKeySpecException {
        a(this.a);
    }
}
