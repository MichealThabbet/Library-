package it.orsozoxi.android.orsonotes.models;

import de.keyboardsurfer.android.widget.crouton.Configuration;
import de.keyboardsurfer.android.widget.crouton.Style;
import it.orsozoxi.android.orsonotes.R;

/* loaded from: classes3.dex */
public class ONStyle {
    public static final int DURATION_INFINITE = -1;
    public static final int DURATION_LONG = 2300;
    public static final int DURATION_MEDIUM = 1650;
    public static final int DURATION_SHORT = 950;
    public static final int ALERT_COLOR = R.color.alert;
    public static final int WARN_COLOR = R.color.warning;
    public static final int CONFIRM_COLOR = R.color.confirm;
    public static final int INFO_COLOR = R.color.info;
    public static final Configuration CONFIGURATION = new Configuration.Builder().setDuration(950).setInAnimation(R.animator.fade_in_support).setOutAnimation(R.animator.fade_out_support).build();
    public static final Style ALERT = new Style.Builder().setBackgroundColor(ALERT_COLOR).setHeight(-1).setGravity(17).setTextAppearance(R.style.crouton_text).setConfiguration(CONFIGURATION).build();
    public static final Style WARN = new Style.Builder().setBackgroundColor(WARN_COLOR).setHeight(-1).setGravity(17).setTextAppearance(R.style.crouton_text).setConfiguration(CONFIGURATION).build();
    public static final Style CONFIRM = new Style.Builder().setBackgroundColor(CONFIRM_COLOR).setHeight(-1).setGravity(17).setTextAppearance(R.style.crouton_text).setConfiguration(CONFIGURATION).build();
    public static final Style INFO = new Style.Builder().setBackgroundColor(INFO_COLOR).setHeight(-1).setGravity(17).setTextAppearance(R.style.crouton_text).setConfiguration(CONFIGURATION).build();

    private ONStyle() {
    }
}
