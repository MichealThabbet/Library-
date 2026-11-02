package com.huawei.agconnect.common.api;

import android.text.TextUtils;
import com.huawei.agconnect.AGConnectInstance;
import com.huawei.agconnect.datastore.annotation.DefaultCrypto;
import com.huawei.agconnect.datastore.core.SharedPrefUtil;
import com.huawei.agconnect.exception.AGCException;
import it.orsozoxi.android.orsonotes.utils.ConstantsBase;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;

/* loaded from: classes2.dex */
public class RequestThrottle {
    private static final String TAG = "AGC_THROTTLE";
    private final Map<String, Throttle> throttleMap = new HashMap();
    private static final Object lock = new Object();
    private static final RequestThrottle INSTANCE = new RequestThrottle();

    public static class Throttle {
        private static final Object LOCK = new Object();
        private static final int MAX_REQUEST_COUNT_IN_INTERVAL = 20;
        private static final String SP_FILE_NAME = "com.huawei.agc.throttle";
        private static final long THROTTLE_INTERVAL_TIME = 3600000;
        private final ArrayDeque<Long> countDeque = new ArrayDeque<>();
        private Integer failCount;
        private Long failTime;
        private boolean isDeveloperMode;
        private final String name;

        Throttle(String str) {
            this.name = str;
            loadCache();
        }

        private long getFirst() {
            try {
                return this.countDeque.getFirst().longValue();
            } catch (NoSuchElementException unused) {
                return 0L;
            }
        }

        private void loadCache() {
            for (String str : ((String) SharedPrefUtil.getInstance().get(SP_FILE_NAME, this.name + "-countDeque", String.class, "", DefaultCrypto.class)).split(ConstantsBase.PROPERTIES_PARAMS_SEPARATOR)) {
                try {
                    if (str.length() > 0) {
                        this.countDeque.addLast(Long.valueOf(str));
                    }
                } catch (NumberFormatException e) {
                    Logger.e(RequestThrottle.TAG, "count deque", e);
                }
            }
            this.failCount = (Integer) SharedPrefUtil.getInstance().get(SP_FILE_NAME, this.name + "-failCount", Integer.class, 0, DefaultCrypto.class);
            this.failTime = (Long) SharedPrefUtil.getInstance().get(SP_FILE_NAME, this.name + "-failTime", Long.class, 0L, DefaultCrypto.class);
        }

        private void saveCache() {
            StringBuilder sb = new StringBuilder();
            ArrayList arrayList = new ArrayList(this.countDeque);
            for (int i = 0; i < arrayList.size(); i++) {
                sb.append(arrayList.get(i));
                if (i != arrayList.size() - 1) {
                    sb.append(ConstantsBase.PROPERTIES_PARAMS_SEPARATOR);
                }
            }
            SharedPrefUtil.getInstance().put(SP_FILE_NAME, this.name + "-countDeque", String.class, sb.toString(), DefaultCrypto.class);
            SharedPrefUtil.getInstance().put(SP_FILE_NAME, this.name + "-failCount", Integer.class, this.failCount, DefaultCrypto.class);
            SharedPrefUtil.getInstance().put(SP_FILE_NAME, this.name + "-failTime", Long.class, this.failTime, DefaultCrypto.class);
        }

        public void addForFail() {
            synchronized (LOCK) {
                this.failCount = Integer.valueOf(this.failCount.intValue() + 1);
                this.failTime = Long.valueOf(System.currentTimeMillis());
                saveCache();
            }
        }

        public void addForStart() {
            synchronized (LOCK) {
                if (this.countDeque.size() == 20) {
                    this.countDeque.pollFirst();
                }
                this.countDeque.addLast(Long.valueOf(System.currentTimeMillis()));
            }
        }

        public void addForSuccess() {
            synchronized (LOCK) {
                this.failCount = 0;
                this.failTime = 0L;
                saveCache();
            }
        }

        public boolean checkFail(Exception exc) {
            if (!(exc instanceof AGCException)) {
                return false;
            }
            AGCException aGCException = (AGCException) exc;
            return aGCException.getCode() == 429 || aGCException.getCode() == 500 || aGCException.getCode() == 502 || aGCException.getCode() == 503 || aGCException.getCode() == 504;
        }

        public long getEndTime() {
            synchronized (LOCK) {
                if (!this.isDeveloperMode && this.countDeque.size() >= 20 && System.currentTimeMillis() - getFirst() < 3600000) {
                    return (getFirst() + 3600000) - System.currentTimeMillis();
                }
                if (this.failCount.intValue() > 1 && this.failTime.longValue() > 0) {
                    long jPow = ((long) Math.pow(2.0d, Math.min(this.failCount.intValue() - 1, 8))) * 60 * 1000;
                    if (System.currentTimeMillis() < this.failTime.longValue() + jPow) {
                        return (this.failTime.longValue() + jPow) - System.currentTimeMillis();
                    }
                }
                return 0L;
            }
        }

        public void setDeveloperMode(boolean z) {
            this.isDeveloperMode = z;
        }
    }

    private RequestThrottle() {
    }

    public static RequestThrottle getInstance() {
        return INSTANCE;
    }

    public Throttle get(AGConnectInstance aGConnectInstance, String str) {
        synchronized (lock) {
            if (TextUtils.isEmpty(str)) {
                throw new RuntimeException("throttle name can not be empty");
            }
            if (aGConnectInstance == null) {
                aGConnectInstance = AGConnectInstance.getInstance();
            }
            String str2 = String.format(Locale.ENGLISH, "%s_%s", str, aGConnectInstance.getOptions().getIdentifier());
            if (this.throttleMap.containsKey(str2)) {
                return this.throttleMap.get(str2);
            }
            Throttle throttle = new Throttle(str2);
            this.throttleMap.put(str2, throttle);
            return throttle;
        }
    }

    public Throttle get(String str) {
        return get(null, str);
    }
}
