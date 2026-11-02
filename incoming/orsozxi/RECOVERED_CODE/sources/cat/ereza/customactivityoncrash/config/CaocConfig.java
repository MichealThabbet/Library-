package cat.ereza.customactivityoncrash.config;

import android.app.Activity;
import cat.ereza.customactivityoncrash.CustomActivityOnCrash;
import java.io.Serializable;
import java.lang.reflect.Modifier;

/* loaded from: classes.dex */
public class CaocConfig implements Serializable {
    public static final int BACKGROUND_MODE_CRASH = 2;
    public static final int BACKGROUND_MODE_SHOW_CUSTOM = 1;
    public static final int BACKGROUND_MODE_SILENT = 0;
    private int backgroundMode = 1;
    private boolean enabled = true;
    private boolean showErrorDetails = true;
    private boolean showRestartButton = true;
    private boolean logErrorOnRestart = true;
    private boolean trackActivities = false;
    private int minTimeBetweenCrashesMs = 3000;
    private Integer errorDrawable = null;
    private Class<? extends Activity> errorActivityClass = null;
    private Class<? extends Activity> restartActivityClass = null;
    private CustomActivityOnCrash.EventListener eventListener = null;

    public int getBackgroundMode() {
        return this.backgroundMode;
    }

    public void setBackgroundMode(int i) {
        this.backgroundMode = i;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean z) {
        this.enabled = z;
    }

    public boolean isShowErrorDetails() {
        return this.showErrorDetails;
    }

    public void setShowErrorDetails(boolean z) {
        this.showErrorDetails = z;
    }

    public boolean isShowRestartButton() {
        return this.showRestartButton;
    }

    public void setShowRestartButton(boolean z) {
        this.showRestartButton = z;
    }

    public boolean isLogErrorOnRestart() {
        return this.logErrorOnRestart;
    }

    public void setLogErrorOnRestart(boolean z) {
        this.logErrorOnRestart = z;
    }

    public boolean isTrackActivities() {
        return this.trackActivities;
    }

    public void setTrackActivities(boolean z) {
        this.trackActivities = z;
    }

    public int getMinTimeBetweenCrashesMs() {
        return this.minTimeBetweenCrashesMs;
    }

    public void setMinTimeBetweenCrashesMs(int i) {
        this.minTimeBetweenCrashesMs = i;
    }

    public Integer getErrorDrawable() {
        return this.errorDrawable;
    }

    public void setErrorDrawable(Integer num) {
        this.errorDrawable = num;
    }

    public Class<? extends Activity> getErrorActivityClass() {
        return this.errorActivityClass;
    }

    public void setErrorActivityClass(Class<? extends Activity> cls) {
        this.errorActivityClass = cls;
    }

    public Class<? extends Activity> getRestartActivityClass() {
        return this.restartActivityClass;
    }

    public void setRestartActivityClass(Class<? extends Activity> cls) {
        this.restartActivityClass = cls;
    }

    public CustomActivityOnCrash.EventListener getEventListener() {
        return this.eventListener;
    }

    public void setEventListener(CustomActivityOnCrash.EventListener eventListener) {
        this.eventListener = eventListener;
    }

    public static class Builder {
        private CaocConfig config;

        public static Builder create() {
            Builder builder = new Builder();
            CaocConfig config = CustomActivityOnCrash.getConfig();
            CaocConfig caocConfig = new CaocConfig();
            caocConfig.backgroundMode = config.backgroundMode;
            caocConfig.enabled = config.enabled;
            caocConfig.showErrorDetails = config.showErrorDetails;
            caocConfig.showRestartButton = config.showRestartButton;
            caocConfig.logErrorOnRestart = config.logErrorOnRestart;
            caocConfig.trackActivities = config.trackActivities;
            caocConfig.minTimeBetweenCrashesMs = config.minTimeBetweenCrashesMs;
            caocConfig.errorDrawable = config.errorDrawable;
            caocConfig.errorActivityClass = config.errorActivityClass;
            caocConfig.restartActivityClass = config.restartActivityClass;
            caocConfig.eventListener = config.eventListener;
            builder.config = caocConfig;
            return builder;
        }

        public Builder backgroundMode(int i) {
            this.config.backgroundMode = i;
            return this;
        }

        public Builder enabled(boolean z) {
            this.config.enabled = z;
            return this;
        }

        public Builder showErrorDetails(boolean z) {
            this.config.showErrorDetails = z;
            return this;
        }

        public Builder showRestartButton(boolean z) {
            this.config.showRestartButton = z;
            return this;
        }

        public Builder logErrorOnRestart(boolean z) {
            this.config.logErrorOnRestart = z;
            return this;
        }

        public Builder trackActivities(boolean z) {
            this.config.trackActivities = z;
            return this;
        }

        public Builder minTimeBetweenCrashesMs(int i) {
            this.config.minTimeBetweenCrashesMs = i;
            return this;
        }

        public Builder errorDrawable(Integer num) {
            this.config.errorDrawable = num;
            return this;
        }

        public Builder errorActivity(Class<? extends Activity> cls) {
            this.config.errorActivityClass = cls;
            return this;
        }

        public Builder restartActivity(Class<? extends Activity> cls) {
            this.config.restartActivityClass = cls;
            return this;
        }

        public Builder eventListener(CustomActivityOnCrash.EventListener eventListener) {
            if (eventListener == null || eventListener.getClass().getEnclosingClass() == null || Modifier.isStatic(eventListener.getClass().getModifiers())) {
                this.config.eventListener = eventListener;
                return this;
            }
            throw new IllegalArgumentException("The event listener cannot be an inner or anonymous class, because it will need to be serialized. Change it to a class of its own, or make it a static inner class.");
        }

        public CaocConfig get() {
            return this.config;
        }

        public void apply() {
            CustomActivityOnCrash.setConfig(this.config);
        }
    }
}
