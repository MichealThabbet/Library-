package org.acra.config;

import java.io.Serializable;
import org.acra.ReportField;
import org.acra.attachment.AttachmentUriProvider;
import org.acra.collections.ImmutableList;
import org.acra.collections.ImmutableSet;
import org.acra.data.StringFormat;
import org.acra.file.Directory;
import org.acra.plugins.PluginLoader;
import org.acra.sender.ReportSenderFactory;

/* loaded from: classes3.dex */
public final class CoreConfiguration implements Serializable, Configuration {
    private final ImmutableList<String> additionalDropBoxTags;
    private final ImmutableList<String> additionalSharedPreferences;
    private final boolean alsoReportToAndroidFramework;
    private final String applicationLogFile;
    private final Directory applicationLogFileDir;
    private final int applicationLogFileLines;
    private final Class<? extends AttachmentUriProvider> attachmentUriProvider;
    private final ImmutableList<String> attachmentUris;
    private final Class buildConfigClass;

    @Deprecated
    private final boolean deleteOldUnsentReportsOnApplicationStart;
    private final boolean deleteUnapprovedReportsOnApplicationStart;
    private final int dropboxCollectionMinutes;
    private final boolean enabled;
    private final ImmutableList<String> excludeMatchingSettingsKeys;
    private final ImmutableList<String> excludeMatchingSharedPreferencesKeys;
    private final boolean includeDropBoxSystemTags;
    private final ImmutableList<String> logcatArguments;
    private final boolean logcatFilterByPid;
    private final boolean logcatReadNonBlocking;
    private final boolean parallel;
    private final ImmutableList<Configuration> pluginConfigurations;
    private final PluginLoader pluginLoader;
    private final ImmutableSet<ReportField> reportContent;
    private final StringFormat reportFormat;
    private final String reportSendFailureToast;
    private final String reportSendSuccessToast;

    @Deprecated
    private final ImmutableList<Class<? extends ReportSenderFactory>> reportSenderFactoryClasses;
    private final Class<? extends RetryPolicy> retryPolicyClass;
    private final boolean sendReportsInDevMode;
    private final String sharedPreferencesName;
    private final boolean stopServicesOnCrash;

    public CoreConfiguration(CoreConfigurationBuilder coreConfigurationBuilder) {
        this.enabled = coreConfigurationBuilder.enabled();
        this.sharedPreferencesName = coreConfigurationBuilder.sharedPreferencesName();
        this.includeDropBoxSystemTags = coreConfigurationBuilder.includeDropBoxSystemTags();
        this.additionalDropBoxTags = new ImmutableList<>(coreConfigurationBuilder.additionalDropBoxTags());
        this.dropboxCollectionMinutes = coreConfigurationBuilder.dropboxCollectionMinutes();
        this.logcatArguments = new ImmutableList<>(coreConfigurationBuilder.logcatArguments());
        this.reportContent = new ImmutableSet<>(coreConfigurationBuilder.reportContent());
        this.deleteUnapprovedReportsOnApplicationStart = coreConfigurationBuilder.deleteUnapprovedReportsOnApplicationStart();
        this.deleteOldUnsentReportsOnApplicationStart = coreConfigurationBuilder.deleteOldUnsentReportsOnApplicationStart();
        this.alsoReportToAndroidFramework = coreConfigurationBuilder.alsoReportToAndroidFramework();
        this.additionalSharedPreferences = new ImmutableList<>(coreConfigurationBuilder.additionalSharedPreferences());
        this.logcatFilterByPid = coreConfigurationBuilder.logcatFilterByPid();
        this.logcatReadNonBlocking = coreConfigurationBuilder.logcatReadNonBlocking();
        this.sendReportsInDevMode = coreConfigurationBuilder.sendReportsInDevMode();
        this.excludeMatchingSharedPreferencesKeys = new ImmutableList<>(coreConfigurationBuilder.excludeMatchingSharedPreferencesKeys());
        this.excludeMatchingSettingsKeys = new ImmutableList<>(coreConfigurationBuilder.excludeMatchingSettingsKeys());
        this.buildConfigClass = coreConfigurationBuilder.buildConfigClass();
        this.reportSenderFactoryClasses = new ImmutableList<>(coreConfigurationBuilder.reportSenderFactoryClasses());
        this.applicationLogFile = coreConfigurationBuilder.applicationLogFile();
        this.applicationLogFileLines = coreConfigurationBuilder.applicationLogFileLines();
        this.applicationLogFileDir = coreConfigurationBuilder.applicationLogFileDir();
        this.retryPolicyClass = coreConfigurationBuilder.retryPolicyClass();
        this.stopServicesOnCrash = coreConfigurationBuilder.stopServicesOnCrash();
        this.attachmentUris = new ImmutableList<>(coreConfigurationBuilder.attachmentUris());
        this.attachmentUriProvider = coreConfigurationBuilder.attachmentUriProvider();
        this.reportSendSuccessToast = coreConfigurationBuilder.reportSendSuccessToast();
        this.reportSendFailureToast = coreConfigurationBuilder.reportSendFailureToast();
        this.reportFormat = coreConfigurationBuilder.reportFormat();
        this.parallel = coreConfigurationBuilder.parallel();
        this.pluginLoader = coreConfigurationBuilder.pluginLoader();
        this.pluginConfigurations = new ImmutableList<>(coreConfigurationBuilder.pluginConfigurations());
    }

    @Override // org.acra.config.Configuration
    public boolean enabled() {
        return this.enabled;
    }

    public String sharedPreferencesName() {
        return this.sharedPreferencesName;
    }

    public boolean includeDropBoxSystemTags() {
        return this.includeDropBoxSystemTags;
    }

    public ImmutableList<String> additionalDropBoxTags() {
        return this.additionalDropBoxTags;
    }

    public int dropboxCollectionMinutes() {
        return this.dropboxCollectionMinutes;
    }

    public ImmutableList<String> logcatArguments() {
        return this.logcatArguments;
    }

    public ImmutableSet<ReportField> reportContent() {
        return this.reportContent;
    }

    public boolean deleteUnapprovedReportsOnApplicationStart() {
        return this.deleteUnapprovedReportsOnApplicationStart;
    }

    @Deprecated
    public boolean deleteOldUnsentReportsOnApplicationStart() {
        return this.deleteOldUnsentReportsOnApplicationStart;
    }

    public boolean alsoReportToAndroidFramework() {
        return this.alsoReportToAndroidFramework;
    }

    public ImmutableList<String> additionalSharedPreferences() {
        return this.additionalSharedPreferences;
    }

    public boolean logcatFilterByPid() {
        return this.logcatFilterByPid;
    }

    public boolean logcatReadNonBlocking() {
        return this.logcatReadNonBlocking;
    }

    public boolean sendReportsInDevMode() {
        return this.sendReportsInDevMode;
    }

    public ImmutableList<String> excludeMatchingSharedPreferencesKeys() {
        return this.excludeMatchingSharedPreferencesKeys;
    }

    public ImmutableList<String> excludeMatchingSettingsKeys() {
        return this.excludeMatchingSettingsKeys;
    }

    public Class buildConfigClass() {
        return this.buildConfigClass;
    }

    @Deprecated
    public ImmutableList<Class<? extends ReportSenderFactory>> reportSenderFactoryClasses() {
        return this.reportSenderFactoryClasses;
    }

    public String applicationLogFile() {
        return this.applicationLogFile;
    }

    public int applicationLogFileLines() {
        return this.applicationLogFileLines;
    }

    public Directory applicationLogFileDir() {
        return this.applicationLogFileDir;
    }

    public Class<? extends RetryPolicy> retryPolicyClass() {
        return this.retryPolicyClass;
    }

    public boolean stopServicesOnCrash() {
        return this.stopServicesOnCrash;
    }

    public ImmutableList<String> attachmentUris() {
        return this.attachmentUris;
    }

    public Class<? extends AttachmentUriProvider> attachmentUriProvider() {
        return this.attachmentUriProvider;
    }

    public String reportSendSuccessToast() {
        return this.reportSendSuccessToast;
    }

    public String reportSendFailureToast() {
        return this.reportSendFailureToast;
    }

    public StringFormat reportFormat() {
        return this.reportFormat;
    }

    public boolean parallel() {
        return this.parallel;
    }

    public PluginLoader pluginLoader() {
        return this.pluginLoader;
    }

    public ImmutableList<Configuration> pluginConfigurations() {
        return this.pluginConfigurations;
    }
}
