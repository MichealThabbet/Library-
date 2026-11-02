package de.keyboardsurfer.android.widget.crouton;

/* loaded from: classes2.dex */
public class Configuration {
    public static final Configuration DEFAULT = new Builder().setDuration(3000).build();
    public static final int DURATION_INFINITE = -1;
    public static final int DURATION_LONG = 5000;
    public static final int DURATION_SHORT = 3000;
    final int durationInMilliseconds;
    final int inAnimationResId;
    final int outAnimationResId;

    private Configuration(Builder builder) {
        this.durationInMilliseconds = builder.durationInMilliseconds;
        this.inAnimationResId = builder.inAnimationResId;
        this.outAnimationResId = builder.outAnimationResId;
    }

    public static class Builder {
        private int durationInMilliseconds = 3000;
        private int inAnimationResId = 0;
        private int outAnimationResId = 0;

        public Builder setDuration(int i) {
            this.durationInMilliseconds = i;
            return this;
        }

        public Builder setInAnimation(int i) {
            this.inAnimationResId = i;
            return this;
        }

        public Builder setOutAnimation(int i) {
            this.outAnimationResId = i;
            return this;
        }

        public Configuration build() {
            return new Configuration(this);
        }
    }

    public String toString() {
        return "Configuration{durationInMilliseconds=" + this.durationInMilliseconds + ", inAnimationResId=" + this.inAnimationResId + ", outAnimationResId=" + this.outAnimationResId + '}';
    }
}
