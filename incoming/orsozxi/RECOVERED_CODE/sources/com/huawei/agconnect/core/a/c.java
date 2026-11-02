package com.huawei.agconnect.core.a;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.os.Bundle;
import android.util.Log;
import com.huawei.agconnect.core.Service;
import com.huawei.agconnect.core.ServiceDiscovery;
import com.huawei.agconnect.core.ServiceRegistrar;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public class c {
    private final Context a;

    private static class a implements Serializable, Comparator<Map.Entry<String, Integer>> {
        private a() {
        }

        @Override // java.util.Comparator
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public int compare(Map.Entry<String, Integer> entry, Map.Entry<String, Integer> entry2) {
            return entry.getValue().intValue() - entry2.getValue().intValue();
        }
    }

    c(Context context) {
        this.a = context;
    }

    private <T extends ServiceRegistrar> T a(String str) throws ClassNotFoundException {
        StringBuilder sb;
        String localizedMessage;
        String string;
        try {
            Class<?> cls = Class.forName(str);
            if (ServiceRegistrar.class.isAssignableFrom(cls)) {
                return (T) Class.forName(str).newInstance();
            }
            Log.e("ServiceRegistrarParser", cls + " must extends from ServiceRegistrar.");
            return null;
        } catch (ClassNotFoundException e) {
            string = "Can not found service class, " + e.getMessage();
            Log.e("ServiceRegistrarParser", string);
            return null;
        } catch (IllegalAccessException e2) {
            sb = new StringBuilder();
            sb.append("instantiate service class exception ");
            localizedMessage = e2.getLocalizedMessage();
            sb.append(localizedMessage);
            string = sb.toString();
            Log.e("ServiceRegistrarParser", string);
            return null;
        } catch (InstantiationException e3) {
            sb = new StringBuilder();
            sb.append("instantiate service class exception ");
            localizedMessage = e3.getLocalizedMessage();
            sb.append(localizedMessage);
            string = sb.toString();
            Log.e("ServiceRegistrarParser", string);
            return null;
        }
    }

    private List<String> b() throws PackageManager.NameNotFoundException {
        StringBuilder sb;
        ArrayList arrayList = new ArrayList();
        Bundle bundleC = c();
        if (bundleC == null) {
            return arrayList;
        }
        HashMap map = new HashMap(10);
        for (String message : bundleC.keySet()) {
            if ("com.huawei.agconnect.core.ServiceRegistrar".equals(bundleC.getString(message))) {
                String[] strArrSplit = message.split(":");
                if (strArrSplit.length == 2) {
                    try {
                        map.put(strArrSplit[0], Integer.valueOf(strArrSplit[1]));
                    } catch (NumberFormatException e) {
                        sb = new StringBuilder();
                        sb.append("registrar configuration format error:");
                        message = e.getMessage();
                    }
                } else if (strArrSplit.length == 1) {
                    map.put(strArrSplit[0], 1000);
                } else {
                    sb = new StringBuilder();
                    sb.append("registrar configuration error, ");
                    sb.append(message);
                    Log.e("ServiceRegistrarParser", sb.toString());
                }
            }
        }
        ArrayList arrayList2 = new ArrayList(map.entrySet());
        Collections.sort(arrayList2, new a());
        Iterator it2 = arrayList2.iterator();
        while (it2.hasNext()) {
            arrayList.add(((Map.Entry) it2.next()).getKey());
        }
        return arrayList;
    }

    private Bundle c() throws PackageManager.NameNotFoundException {
        ServiceInfo serviceInfo;
        PackageManager packageManager = this.a.getPackageManager();
        if (packageManager == null) {
            return null;
        }
        try {
            serviceInfo = packageManager.getServiceInfo(new ComponentName(this.a, (Class<?>) ServiceDiscovery.class), 128);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("ServiceRegistrarParser", "get ServiceDiscovery exception." + e.getLocalizedMessage());
        }
        if (serviceInfo != null) {
            return serviceInfo.metaData;
        }
        Log.e("ServiceRegistrarParser", "Can not found ServiceDiscovery service.");
        return null;
    }

    public List<Service> a() throws PackageManager.NameNotFoundException, ClassNotFoundException {
        Log.i("ServiceRegistrarParser", "getServices");
        List<String> listB = b();
        ArrayList arrayList = new ArrayList();
        Iterator<String> it2 = listB.iterator();
        while (it2.hasNext()) {
            ServiceRegistrar serviceRegistrarA = a(it2.next());
            if (serviceRegistrarA != null) {
                serviceRegistrarA.initialize(this.a);
                List<Service> services = serviceRegistrarA.getServices(this.a);
                if (services != null) {
                    arrayList.addAll(services);
                }
            }
        }
        Log.i("ServiceRegistrarParser", "services:" + Integer.valueOf(arrayList.size()));
        return arrayList;
    }
}
