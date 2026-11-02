package androidx.mediarouter.app;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.View;
import androidx.core.graphics.ColorUtils;
import androidx.mediarouter.R;

/* loaded from: classes.dex */
final class MediaRouterThemeHelper {
    static final int COLOR_DARK_ON_LIGHT_BACKGROUND = -570425344;
    static final int COLOR_WHITE_ON_DARK_BACKGROUND = -1;
    private static final float MIN_CONTRAST = 3.0f;
    static Drawable sDefaultIcon;
    static Drawable sSpeakerGroupIcon;
    static Drawable sSpeakerIcon;
    static Drawable sTvIcon;

    private MediaRouterThemeHelper() {
    }

    static Drawable getDefaultDrawableIcon(Context context) {
        if (sDefaultIcon == null) {
            sDefaultIcon = getDrawableIcon(context, 0);
        }
        return sDefaultIcon;
    }

    static Drawable getTvDrawableIcon(Context context) {
        if (sTvIcon == null) {
            sTvIcon = getDrawableIcon(context, 1);
        }
        return sTvIcon;
    }

    static Drawable getSpeakerDrawableIcon(Context context) {
        if (sSpeakerIcon == null) {
            sSpeakerIcon = getDrawableIcon(context, 2);
        }
        return sSpeakerIcon;
    }

    static Drawable getSpeakerGropuIcon(Context context) {
        if (sSpeakerGroupIcon == null) {
            sSpeakerGroupIcon = getDrawableIcon(context, 3);
        }
        return sSpeakerGroupIcon;
    }

    private static Drawable getDrawableIcon(Context context, int i) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(new int[]{R.attr.mediaRouteDefaultIconDrawable, R.attr.mediaRouteTvIconDrawable, R.attr.mediaRouteSpeakerIconDrawable, R.attr.mediaRouteSpeakerGroupIconDrawable});
        Drawable drawable = typedArrayObtainStyledAttributes.getDrawable(i);
        typedArrayObtainStyledAttributes.recycle();
        return drawable;
    }

    static Context createThemedButtonContext(Context context) {
        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(context, getRouterThemeId(context));
        int themeResource = getThemeResource(contextThemeWrapper, R.attr.mediaRouteTheme);
        return themeResource != 0 ? new ContextThemeWrapper(contextThemeWrapper, themeResource) : contextThemeWrapper;
    }

    static Context createThemedDialogContext(Context context, int i, boolean z) {
        if (i == 0) {
            i = getThemeResource(context, !z ? androidx.appcompat.R.attr.dialogTheme : androidx.appcompat.R.attr.alertDialogTheme);
        }
        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(context, i);
        return getThemeResource(contextThemeWrapper, R.attr.mediaRouteTheme) != 0 ? new ContextThemeWrapper(contextThemeWrapper, getRouterThemeId(contextThemeWrapper)) : contextThemeWrapper;
    }

    static int createThemedDialogStyle(Context context) {
        int themeResource = getThemeResource(context, R.attr.mediaRouteTheme);
        return themeResource == 0 ? getRouterThemeId(context) : themeResource;
    }

    static int getThemeResource(Context context, int i) {
        TypedValue typedValue = new TypedValue();
        if (context.getTheme().resolveAttribute(i, typedValue, true)) {
            return typedValue.resourceId;
        }
        return 0;
    }

    static float getDisabledAlpha(Context context) {
        TypedValue typedValue = new TypedValue();
        if (context.getTheme().resolveAttribute(android.R.attr.disabledAlpha, typedValue, true)) {
            return typedValue.getFloat();
        }
        return 0.5f;
    }

    static int getControllerColor(Context context, int i) {
        if (ColorUtils.calculateContrast(-1, getThemeColor(context, i, androidx.appcompat.R.attr.colorPrimary)) >= 3.0d) {
            return -1;
        }
        return COLOR_DARK_ON_LIGHT_BACKGROUND;
    }

    static int getButtonTextColor(Context context) throws Resources.NotFoundException {
        int themeColor = getThemeColor(context, 0, androidx.appcompat.R.attr.colorPrimary);
        return ColorUtils.calculateContrast(themeColor, getThemeColor(context, 0, android.R.attr.colorBackground)) < 3.0d ? getThemeColor(context, 0, androidx.appcompat.R.attr.colorAccent) : themeColor;
    }

    static TypedArray getStyledAttributes(Context context) {
        return context.obtainStyledAttributes(new int[]{R.attr.mediaRouteDefaultIconDrawable, R.attr.mediaRouteTvIconDrawable, R.attr.mediaRouteSpeakerIconDrawable, R.attr.mediaRouteSpeakerGroupIconDrawable});
    }

    static void setMediaControlsBackgroundColor(Context context, View view, View view2, boolean z) throws Resources.NotFoundException {
        int themeColor = getThemeColor(context, 0, androidx.appcompat.R.attr.colorPrimary);
        int themeColor2 = getThemeColor(context, 0, androidx.appcompat.R.attr.colorPrimaryDark);
        if (z && getControllerColor(context, 0) == COLOR_DARK_ON_LIGHT_BACKGROUND) {
            themeColor2 = themeColor;
            themeColor = -1;
        }
        view.setBackgroundColor(themeColor);
        view2.setBackgroundColor(themeColor2);
        view.setTag(Integer.valueOf(themeColor));
        view2.setTag(Integer.valueOf(themeColor2));
    }

    static void setVolumeSliderColor(Context context, MediaRouteVolumeSlider mediaRouteVolumeSlider, View view) {
        int controllerColor = getControllerColor(context, 0);
        if (Color.alpha(controllerColor) != 255) {
            controllerColor = ColorUtils.compositeColors(controllerColor, ((Integer) view.getTag()).intValue());
        }
        mediaRouteVolumeSlider.setColor(controllerColor);
    }

    private static boolean isLightTheme(Context context) {
        TypedValue typedValue = new TypedValue();
        return context.getTheme().resolveAttribute(androidx.appcompat.R.attr.isLightTheme, typedValue, true) && typedValue.data != 0;
    }

    private static int getThemeColor(Context context, int i, int i2) throws Resources.NotFoundException {
        if (i != 0) {
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(i, new int[]{i2});
            int color = typedArrayObtainStyledAttributes.getColor(0, 0);
            typedArrayObtainStyledAttributes.recycle();
            if (color != 0) {
                return color;
            }
        }
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(i2, typedValue, true);
        if (typedValue.resourceId != 0) {
            return context.getResources().getColor(typedValue.resourceId);
        }
        return typedValue.data;
    }

    private static int getRouterThemeId(Context context) {
        if (isLightTheme(context)) {
            if (getControllerColor(context, 0) == COLOR_DARK_ON_LIGHT_BACKGROUND) {
                return R.style.Theme_MediaRouter_Light;
            }
            return R.style.Theme_MediaRouter_Light_DarkControlPanel;
        }
        if (getControllerColor(context, 0) == COLOR_DARK_ON_LIGHT_BACKGROUND) {
            return R.style.Theme_MediaRouter_LightControlPanel;
        }
        return R.style.Theme_MediaRouter;
    }
}
