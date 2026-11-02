package org.acra.config;

import android.content.Context;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import java.util.List;
import java.util.Set;
import org.acra.ReportField;
import org.acra.annotation.AcraCore;
import org.acra.attachment.AttachmentUriProvider;
import org.acra.attachment.DefaultAttachmentProvider;
import org.acra.data.StringFormat;
import org.acra.file.Directory;
import org.acra.plugins.PluginLoader;
import org.acra.sender.ReportSenderFactory;

/* loaded from: classes3.dex */
public final class CoreConfigurationBuilder implements ConfigurationBuilder {
    private String[] additionalDropBoxTags;
    private String[] additionalSharedPreferences;
    private boolean alsoReportToAndroidFramework;
    private String applicationLogFile;
    private Directory applicationLogFileDir;
    private int applicationLogFileLines;
    private Class<? extends AttachmentUriProvider> attachmentUriProvider;
    private String[] attachmentUris;
    private Class buildConfigClass;
    private final Context context;
    private final BaseCoreConfigurationBuilder delegate;

    @Deprecated
    private boolean deleteOldUnsentReportsOnApplicationStart;
    private boolean deleteUnapprovedReportsOnApplicationStart;
    private int dropboxCollectionMinutes;
    private boolean enabled;
    private String[] excludeMatchingSettingsKeys;
    private String[] excludeMatchingSharedPreferencesKeys;
    private boolean includeDropBoxSystemTags;
    private String[] logcatArguments;
    private boolean logcatFilterByPid;
    private boolean logcatReadNonBlocking;
    private boolean parallel;
    private ReportField[] reportContent;
    private StringFormat reportFormat;
    private String reportSendFailureToast;
    private String reportSendSuccessToast;

    @Deprecated
    private Class<? extends ReportSenderFactory>[] reportSenderFactoryClasses;
    private Class<? extends RetryPolicy> retryPolicyClass;
    private boolean sendReportsInDevMode;
    private String sharedPreferencesName;
    private boolean stopServicesOnCrash;

    public CoreConfigurationBuilder(Context context) {
        AcraCore acraCore = (AcraCore) context.getClass().getAnnotation(AcraCore.class);
        this.context = context;
        this.enabled = acraCore != null;
        this.delegate = new BaseCoreConfigurationBuilder(context);
        if (this.enabled) {
            this.sharedPreferencesName = acraCore.sharedPreferencesName();
            this.includeDropBoxSystemTags = acraCore.includeDropBoxSystemTags();
            this.additionalDropBoxTags = acraCore.additionalDropBoxTags();
            this.dropboxCollectionMinutes = acraCore.dropboxCollectionMinutes();
            this.logcatArguments = acraCore.logcatArguments();
            this.reportContent = acraCore.reportContent();
            this.deleteUnapprovedReportsOnApplicationStart = acraCore.deleteUnapprovedReportsOnApplicationStart();
            this.deleteOldUnsentReportsOnApplicationStart = acraCore.deleteOldUnsentReportsOnApplicationStart();
            this.alsoReportToAndroidFramework = acraCore.alsoReportToAndroidFramework();
            this.additionalSharedPreferences = acraCore.additionalSharedPreferences();
            this.logcatFilterByPid = acraCore.logcatFilterByPid();
            this.logcatReadNonBlocking = acraCore.logcatReadNonBlocking();
            this.sendReportsInDevMode = acraCore.sendReportsInDevMode();
            this.excludeMatchingSharedPreferencesKeys = acraCore.excludeMatchingSharedPreferencesKeys();
            this.excludeMatchingSettingsKeys = acraCore.excludeMatchingSettingsKeys();
            this.buildConfigClass = acraCore.buildConfigClass();
            this.reportSenderFactoryClasses = acraCore.reportSenderFactoryClasses();
            this.applicationLogFile = acraCore.applicationLogFile();
            this.applicationLogFileLines = acraCore.applicationLogFileLines();
            this.applicationLogFileDir = acraCore.applicationLogFileDir();
            this.retryPolicyClass = acraCore.retryPolicyClass();
            this.stopServicesOnCrash = acraCore.stopServicesOnCrash();
            this.attachmentUris = acraCore.attachmentUris();
            this.attachmentUriProvider = acraCore.attachmentUriProvider();
            if (acraCore.resReportSendSuccessToast() != 0) {
                this.reportSendSuccessToast = this.context.getString(acraCore.resReportSendSuccessToast());
            }
            if (acraCore.resReportSendFailureToast() != 0) {
                this.reportSendFailureToast = this.context.getString(acraCore.resReportSendFailureToast());
            }
            this.reportFormat = acraCore.reportFormat();
            this.parallel = acraCore.parallel();
            return;
        }
        this.sharedPreferencesName = "";
        this.includeDropBoxSystemTags = false;
        this.additionalDropBoxTags = new String[0];
        this.dropboxCollectionMinutes = 5;
        this.logcatArguments = new String[]{"-t", "100", "-v", CrashHianalyticsData.TIME};
        this.reportContent = new ReportField[0];
        this.deleteUnapprovedReportsOnApplicationStart = true;
        this.deleteOldUnsentReportsOnApplicationStart = true;
        this.alsoReportToAndroidFramework = false;
        this.additionalSharedPreferences = new String[0];
        this.logcatFilterByPid = true;
        this.logcatReadNonBlocking = false;
        this.sendReportsInDevMode = true;
        this.excludeMatchingSharedPreferencesKeys = new String[0];
        this.excludeMatchingSettingsKeys = new String[0];
        this.buildConfigClass = Object.class;
        this.reportSenderFactoryClasses = new Class[0];
        this.applicationLogFile = "";
        this.applicationLogFileLines = 100;
        this.applicationLogFileDir = Directory.FILES_LEGACY;
        this.retryPolicyClass = DefaultRetryPolicy.class;
        this.stopServicesOnCrash = false;
        this.attachmentUris = new String[0];
        this.attachmentUriProvider = DefaultAttachmentProvider.class;
        this.reportFormat = StringFormat.JSON;
        this.parallel = true;
    }

    public CoreConfigurationBuilder setEnabled(boolean z) {
        this.enabled = z;
        return this;
    }

    boolean enabled() {
        return this.enabled;
    }

    public CoreConfigurationBuilder setSharedPreferencesName(String str) {
        this.sharedPreferencesName = str;
        return this;
    }

    String sharedPreferencesName() {
        return this.sharedPreferencesName;
    }

    public CoreConfigurationBuilder setIncludeDropBoxSystemTags(boolean z) {
        this.includeDropBoxSystemTags = z;
        return this;
    }

    boolean includeDropBoxSystemTags() {
        return this.includeDropBoxSystemTags;
    }

    public CoreConfigurationBuilder setAdditionalDropBoxTags(String... strArr) {
        this.additionalDropBoxTags = strArr;
        return this;
    }

    String[] additionalDropBoxTags() {
        return this.additionalDropBoxTags;
    }

    public CoreConfigurationBuilder setDropboxCollectionMinutes(int i) {
        this.dropboxCollectionMinutes = i;
        return this;
    }

    int dropboxCollectionMinutes() {
        return this.dropboxCollectionMinutes;
    }

    public CoreConfigurationBuilder setLogcatArguments(String... strArr) {
        this.logcatArguments = strArr;
        return this;
    }

    String[] logcatArguments() {
        return this.logcatArguments;
    }

    public CoreConfigurationBuilder setReportContent(ReportField... reportFieldArr) {
        this.reportContent = reportFieldArr;
        return this;
    }

    Set<ReportField> reportContent() {
        return this.delegate.transformReportContent(this.reportContent);
    }

    public CoreConfigurationBuilder setDeleteUnapprovedReportsOnApplicationStart(boolean z) {
        this.deleteUnapprovedReportsOnApplicationStart = z;
        return this;
    }

    boolean deleteUnapprovedReportsOnApplicationStart() {
        return this.deleteUnapprovedReportsOnApplicationStart;
    }

    @Deprecated
    public CoreConfigurationBuilder setDeleteOldUnsentReportsOnApplicationStart(boolean z) {
        this.deleteOldUnsentReportsOnApplicationStart = z;
        return this;
    }

    @Deprecated
    boolean deleteOldUnsentReportsOnApplicationStart() {
        return this.deleteOldUnsentReportsOnApplicationStart;
    }

    public CoreConfigurationBuilder setAlsoReportToAndroidFramework(boolean z) {
        this.alsoReportToAndroidFramework = z;
        return this;
    }

    boolean alsoReportToAndroidFramework() {
        return this.alsoReportToAndroidFramework;
    }

    public CoreConfigurationBuilder setAdditionalSharedPreferences(String... strArr) {
        this.additionalSharedPreferences = strArr;
        return this;
    }

    String[] additionalSharedPreferences() {
        return this.additionalSharedPreferences;
    }

    public CoreConfigurationBuilder setLogcatFilterByPid(boolean z) {
        this.logcatFilterByPid = z;
        return this;
    }

    boolean logcatFilterByPid() {
        return this.logcatFilterByPid;
    }

    public CoreConfigurationBuilder setLogcatReadNonBlocking(boolean z) {
        this.logcatReadNonBlocking = z;
        return this;
    }

    boolean logcatReadNonBlocking() {
        return this.logcatReadNonBlocking;
    }

    public CoreConfigurationBuilder setSendReportsInDevMode(boolean z) {
        this.sendReportsInDevMode = z;
        return this;
    }

    boolean sendReportsInDevMode() {
        return this.sendReportsInDevMode;
    }

    public CoreConfigurationBuilder setExcludeMatchingSharedPreferencesKeys(String... strArr) {
        this.excludeMatchingSharedPreferencesKeys = strArr;
        return this;
    }

    String[] excludeMatchingSharedPreferencesKeys() {
        return this.excludeMatchingSharedPreferencesKeys;
    }

    public CoreConfigurationBuilder setExcludeMatchingSettingsKeys(String... strArr) {
        this.excludeMatchingSettingsKeys = strArr;
        return this;
    }

    String[] excludeMatchingSettingsKeys() {
        return this.excludeMatchingSettingsKeys;
    }

    public CoreConfigurationBuilder setBuildConfigClass(Class cls) {
        this.buildConfigClass = cls;
        return this;
    }

    Class buildConfigClass() {
        return this.buildConfigClass;
    }

    @Deprecated
    public CoreConfigurationBuilder setReportSenderFactoryClasses(Class<? extends ReportSenderFactory>... clsArr) {
        this.reportSenderFactoryClasses = clsArr;
        return this;
    }

    @Deprecated
    Class<? extends ReportSenderFactory>[] reportSenderFactoryClasses() {
        return this.reportSenderFactoryClasses;
    }

    public CoreConfigurationBuilder setApplicationLogFile(String str) {
        this.applicationLogFile = str;
        return this;
    }

    String applicationLogFile() {
        return this.applicationLogFile;
    }

    public CoreConfigurationBuilder setApplicationLogFileLines(int i) {
        this.applicationLogFileLines = i;
        return this;
    }

    int applicationLogFileLines() {
        return this.applicationLogFileLines;
    }

    public CoreConfigurationBuilder setApplicationLogFileDir(Directory directory) {
        this.applicationLogFileDir = directory;
        return this;
    }

    Directory applicationLogFileDir() {
        return this.applicationLogFileDir;
    }

    public CoreConfigurationBuilder setRetryPolicyClass(Class<? extends RetryPolicy> cls) {
        this.retryPolicyClass = cls;
        return this;
    }

    Class<? extends RetryPolicy> retryPolicyClass() {
        return this.retryPolicyClass;
    }

    public CoreConfigurationBuilder setStopServicesOnCrash(boolean z) {
        this.stopServicesOnCrash = z;
        return this;
    }

    boolean stopServicesOnCrash() {
        return this.stopServicesOnCrash;
    }

    public CoreConfigurationBuilder setAttachmentUris(String... strArr) {
        this.attachmentUris = strArr;
        return this;
    }

    String[] attachmentUris() {
        return this.attachmentUris;
    }

    public CoreConfigurationBuilder setAttachmentUriProvider(Class<? extends AttachmentUriProvider> cls) {
        this.attachmentUriProvider = cls;
        return this;
    }

    Class<? extends AttachmentUriProvider> attachmentUriProvider() {
        return this.attachmentUriProvider;
    }

    public CoreConfigurationBuilder setReportSendSuccessToast(String str) {
        this.reportSendSuccessToast = str;
        return this;
    }

    public CoreConfigurationBuilder setResReportSendSuccessToast(int i) {
        this.reportSendSuccessToast = this.context.getString(i);
        return this;
    }

    String reportSendSuccessToast() {
        return this.reportSendSuccessToast;
    }

    public CoreConfigurationBuilder setReportSendFailureToast(String str) {
        this.reportSendFailureToast = str;
        return this;
    }

    public CoreConfigurationBuilder setResReportSendFailureToast(int i) {
        this.reportSendFailureToast = this.context.getString(i);
        return this;
    }

    String reportSendFailureToast() {
        return this.reportSendFailureToast;
    }

    public CoreConfigurationBuilder setReportFormat(StringFormat stringFormat) {
        this.reportFormat = stringFormat;
        return this;
    }

    StringFormat reportFormat() {
        return this.reportFormat;
    }

    public CoreConfigurationBuilder setParallel(boolean z) {
        this.parallel = z;
        return this;
    }

    boolean parallel() {
        return this.parallel;
    }

    public CoreConfigurationBuilder setPluginLoader(PluginLoader pluginLoader) {
        this.delegate.setPluginLoader(pluginLoader);
        return this;
    }

    PluginLoader pluginLoader() {
        return this.delegate.pluginLoader();
    }

    public CoreConfigurationBuilder setReportField(ReportField reportField, boolean z) {
        this.delegate.setReportField(reportField, z);
        return this;
    }

    List<Configuration> pluginConfigurations() {
        return this.delegate.pluginConfigurations();
    }

    public <R extends ConfigurationBuilder> R getPluginConfigurationBuilder(Class<R> cls) {
        return (R) this.delegate.getPluginConfigurationBuilder(cls);
    }

    @Override // org.acra.config.ConfigurationBuilder
    public CoreConfiguration build() throws ACRAConfigurationException, NoSuchMethodException, SecurityException {
        if (this.enabled) {
            ClassValidator.check(this.reportSenderFactoryClasses);
            ClassValidator.check(this.retryPolicyClass);
            ClassValidator.check(this.attachmentUriProvider);
        }
        this.delegate.preBuild();
        return new CoreConfiguration(this);
    }
}
