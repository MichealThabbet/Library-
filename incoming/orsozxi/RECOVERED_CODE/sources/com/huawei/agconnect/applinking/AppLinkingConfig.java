package com.huawei.agconnect.applinking;

import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public class AppLinkingConfig {
    private static AppLinkingConfig INSTANCE = new Builder().build();
    private long clipboardRetryDelayMillis;
    private int clipboardRetryTimes;
    private boolean isClipboardAlways;
    private boolean isClipboardCheck;
    private boolean isMarketCheck;
    private Map<String, ReferrerProvider> marketsProvider;

    public static class Builder {
        private boolean isClipboardCheck = true;
        private int clipboardRetryTimes = 3;
        private long clipboardRetryDelayMillis = 200;
        private boolean isClipboardAlways = false;
        private boolean isMarketCheck = true;
        private final Map<String, ReferrerProvider> providerMap = new HashMap();

        /* JADX INFO: Access modifiers changed from: private */
        public AppLinkingConfig build() {
            AppLinkingConfig appLinkingConfig = new AppLinkingConfig();
            appLinkingConfig.isClipboardCheck = this.isClipboardCheck;
            appLinkingConfig.clipboardRetryTimes = this.clipboardRetryTimes;
            appLinkingConfig.clipboardRetryDelayMillis = this.clipboardRetryDelayMillis;
            appLinkingConfig.isClipboardAlways = this.isClipboardAlways;
            appLinkingConfig.isMarketCheck = this.isMarketCheck;
            appLinkingConfig.marketsProvider = this.providerMap;
            return appLinkingConfig;
        }

        public Builder addMarketProvider(String str, ReferrerProvider referrerProvider) {
            this.providerMap.put(str, referrerProvider);
            return this;
        }

        public void asDefault() {
            AppLinkingConfig unused = AppLinkingConfig.INSTANCE = build();
        }

        public Builder setClipboardRetryDelayMillis(long j) {
            this.clipboardRetryDelayMillis = j;
            return this;
        }

        public Builder setClipboardRetryTimes(int i) {
            this.clipboardRetryTimes = i;
            return this;
        }

        public Builder setDisableClipboardCheck() {
            this.isClipboardCheck = false;
            return this;
        }

        public Builder setDisableMarketCheck() {
            this.isMarketCheck = false;
            return this;
        }

        public Builder setEnableClipboardAlways() {
            this.isClipboardAlways = true;
            return this;
        }
    }

    public static AppLinkingConfig getInstance() {
        return INSTANCE;
    }

    public long getClipboardRetryDelayMillis() {
        return this.clipboardRetryDelayMillis;
    }

    public int getClipboardRetryTimes() {
        return this.clipboardRetryTimes;
    }

    public Map<String, ReferrerProvider> getMarketsProvider() {
        return this.marketsProvider;
    }

    public boolean isClipboardAlways() {
        return this.isClipboardAlways;
    }

    public boolean isClipboardCheck() {
        return this.isClipboardCheck;
    }

    public boolean isMarketCheck() {
        return this.isMarketCheck;
    }
}
