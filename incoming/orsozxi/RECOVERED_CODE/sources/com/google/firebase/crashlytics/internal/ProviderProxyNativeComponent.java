package com.google.firebase.crashlytics.internal;

import com.google.firebase.inject.Provider;
import java.io.File;

/* loaded from: classes2.dex */
public final class ProviderProxyNativeComponent implements CrashlyticsNativeComponent {
    private static final NativeSessionFileProvider MISSING_NATIVE_SESSION_FILE_PROVIDER = new MissingNativeSessionFileProvider();
    private final Provider<CrashlyticsNativeComponent> provider;

    public ProviderProxyNativeComponent(Provider<CrashlyticsNativeComponent> provider) {
        this.provider = provider;
    }

    @Override // com.google.firebase.crashlytics.internal.CrashlyticsNativeComponent
    public boolean hasCrashDataForSession(String str) {
        CrashlyticsNativeComponent crashlyticsNativeComponent = this.provider.get();
        if (crashlyticsNativeComponent != null) {
            return crashlyticsNativeComponent.hasCrashDataForSession(str);
        }
        return false;
    }

    @Override // com.google.firebase.crashlytics.internal.CrashlyticsNativeComponent
    public boolean openSession(String str) {
        CrashlyticsNativeComponent crashlyticsNativeComponent = this.provider.get();
        if (crashlyticsNativeComponent != null) {
            return crashlyticsNativeComponent.openSession(str);
        }
        return true;
    }

    @Override // com.google.firebase.crashlytics.internal.CrashlyticsNativeComponent
    public boolean finalizeSession(String str) {
        CrashlyticsNativeComponent crashlyticsNativeComponent = this.provider.get();
        if (crashlyticsNativeComponent != null) {
            return crashlyticsNativeComponent.finalizeSession(str);
        }
        return true;
    }

    @Override // com.google.firebase.crashlytics.internal.CrashlyticsNativeComponent
    public NativeSessionFileProvider getSessionFileProvider(String str) {
        CrashlyticsNativeComponent crashlyticsNativeComponent = this.provider.get();
        if (crashlyticsNativeComponent != null) {
            return crashlyticsNativeComponent.getSessionFileProvider(str);
        }
        return MISSING_NATIVE_SESSION_FILE_PROVIDER;
    }

    @Override // com.google.firebase.crashlytics.internal.CrashlyticsNativeComponent
    public void writeBeginSession(String str, String str2, long j) {
        CrashlyticsNativeComponent crashlyticsNativeComponent = this.provider.get();
        if (crashlyticsNativeComponent != null) {
            crashlyticsNativeComponent.writeBeginSession(str, str2, j);
        }
    }

    @Override // com.google.firebase.crashlytics.internal.CrashlyticsNativeComponent
    public void writeSessionApp(String str, String str2, String str3, String str4, String str5, int i, String str6) {
        CrashlyticsNativeComponent crashlyticsNativeComponent = this.provider.get();
        if (crashlyticsNativeComponent != null) {
            crashlyticsNativeComponent.writeSessionApp(str, str2, str3, str4, str5, i, str6);
        }
    }

    @Override // com.google.firebase.crashlytics.internal.CrashlyticsNativeComponent
    public void writeSessionOs(String str, String str2, String str3, boolean z) {
        CrashlyticsNativeComponent crashlyticsNativeComponent = this.provider.get();
        if (crashlyticsNativeComponent != null) {
            crashlyticsNativeComponent.writeSessionOs(str, str2, str3, z);
        }
    }

    @Override // com.google.firebase.crashlytics.internal.CrashlyticsNativeComponent
    public void writeSessionDevice(String str, int i, String str2, int i2, long j, long j2, boolean z, int i3, String str3, String str4) {
        CrashlyticsNativeComponent crashlyticsNativeComponent = this.provider.get();
        if (crashlyticsNativeComponent != null) {
            crashlyticsNativeComponent.writeSessionDevice(str, i, str2, i2, j, j2, z, i3, str3, str4);
        }
    }

    private static final class MissingNativeSessionFileProvider implements NativeSessionFileProvider {
        @Override // com.google.firebase.crashlytics.internal.NativeSessionFileProvider
        public File getAppFile() {
            return null;
        }

        @Override // com.google.firebase.crashlytics.internal.NativeSessionFileProvider
        public File getBinaryImagesFile() {
            return null;
        }

        @Override // com.google.firebase.crashlytics.internal.NativeSessionFileProvider
        public File getDeviceFile() {
            return null;
        }

        @Override // com.google.firebase.crashlytics.internal.NativeSessionFileProvider
        public File getMetadataFile() {
            return null;
        }

        @Override // com.google.firebase.crashlytics.internal.NativeSessionFileProvider
        public File getMinidumpFile() {
            return null;
        }

        @Override // com.google.firebase.crashlytics.internal.NativeSessionFileProvider
        public File getOsFile() {
            return null;
        }

        @Override // com.google.firebase.crashlytics.internal.NativeSessionFileProvider
        public File getSessionFile() {
            return null;
        }

        private MissingNativeSessionFileProvider() {
        }
    }
}
