package com.huawei.agconnect.common.api;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Build;
import com.google.firebase.messaging.Constants;
import com.huawei.agconnect.AGConnectInstance;
import com.huawei.agconnect.AGConnectOptions;
import com.huawei.agconnect.common.appinfo.SafeAppInfo;
import com.huawei.agconnect.https.annotation.Header;
import com.huawei.agconnect.version.LibraryInfos;

/* loaded from: classes2.dex */
public abstract class BaseRequest {

    @Header("access_token")
    private String accessToken;

    @Header("appVersion")
    private String appVersion;

    @Header("Authorization")
    private String authorization;

    @Header("aaId")
    private String headerAaId;

    @Header("appId")
    private String headerAppId;

    @Header("client_id")
    private String headerClientId;

    @Header("productId")
    private String headerProductId;

    @Header(Constants.FirelogAnalytics.PARAM_PACKAGE_NAME)
    private String packageName;

    @Header(Constants.FirelogAnalytics.PARAM_SDK_PLATFORM)
    private String sdkPlatform;

    @Header("sdkPlatformVersion")
    private String sdkPlatformVersion;

    @Header("sdkServiceName")
    private String sdkServiceName;

    @Header("sdkType")
    private String sdkType;

    @Header("sdkVersion")
    private String sdkVersion;

    public BaseRequest() {
        initBase(AGConnectInstance.getInstance());
    }

    public BaseRequest(AGConnectInstance aGConnectInstance) {
        initBase(aGConnectInstance);
    }

    public String getAccessToken() {
        return this.accessToken;
    }

    public String getAppVersion() {
        return this.appVersion;
    }

    public String getAuthorization() {
        return this.authorization;
    }

    public String getHeaderAaId() {
        return this.headerAaId;
    }

    public String getHeaderAppId() {
        return this.headerAppId;
    }

    public String getHeaderClientId() {
        return this.headerClientId;
    }

    public String getHeaderProductId() {
        return this.headerProductId;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public String getSdkPlatform() {
        return this.sdkPlatform;
    }

    public String getSdkPlatformVersion() {
        return this.sdkPlatformVersion;
    }

    public String getSdkServiceName() {
        return this.sdkServiceName;
    }

    public String getSdkVersion() {
        return this.sdkVersion;
    }

    public void initBase(AGConnectInstance aGConnectInstance) {
        Context context = aGConnectInstance.getContext();
        this.sdkType = LibraryInfos.getInstance().getLibraryType();
        this.sdkPlatform = "Android";
        this.sdkPlatformVersion = Build.VERSION.RELEASE;
        this.packageName = context.getPackageName();
        PackageInfo packageInfoSafeGetPackageInfo = SafeAppInfo.safeGetPackageInfo(context.getPackageManager(), this.packageName, 16384);
        if (packageInfoSafeGetPackageInfo != null) {
            this.appVersion = packageInfoSafeGetPackageInfo.versionName;
        }
        AGConnectOptions options = aGConnectInstance.getOptions();
        this.packageName = aGConnectInstance.getOptions().getPackageName();
        this.headerProductId = options.getString("client/product_id");
        this.headerAppId = options.getString("client/app_id");
        this.headerClientId = options.getString("client/client_id");
    }

    public void setAccessToken(String str) {
        this.accessToken = str;
    }

    public void setAppVersion(String str) {
        this.appVersion = str;
    }

    public void setAuthorization(String str) {
        this.authorization = str;
    }

    public void setHeaderAaId(String str) {
        this.headerAaId = str;
    }

    public void setHeaderAppId(String str) {
        this.headerAppId = str;
    }

    public void setHeaderClientId(String str) {
        this.headerClientId = str;
    }

    public void setHeaderProductId(String str) {
        this.headerProductId = str;
    }

    public void setPackageName(String str) {
        this.packageName = str;
    }

    public void setSdkPlatform(String str) {
        this.sdkPlatform = str;
    }

    public void setSdkPlatformVersion(String str) {
        this.sdkPlatformVersion = str;
    }

    public void setSdkServiceName(String str) {
        this.sdkServiceName = str;
    }

    public void setSdkVersion(String str) {
        this.sdkVersion = str;
    }
}
