package it.piapostolos.android.pinotes.utils;

import android.R;
import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.mediarouter.media.SystemMediaRouteProvider;
import it.piapostolos.android.pinotes.helpers.LogDelegate;

/* loaded from: classes3.dex */
public class Display {
    private Display() {
    }

    public static View getRootView(Activity activity) {
        return activity.getWindow().getDecorView().findViewById(R.id.content);
    }

    public static Point getUsableSize(Context context) {
        android.view.Display defaultDisplay;
        Point point = new Point();
        try {
            WindowManager windowManager = (WindowManager) context.getSystemService("window");
            if (windowManager != null && (defaultDisplay = windowManager.getDefaultDisplay()) != null) {
                defaultDisplay.getSize(point);
            }
        } catch (Exception e) {
            LogDelegate.e("Error checking display sizes", e);
        }
        return point;
    }

    public static Point getVisibleSize(Activity activity) {
        Point point = new Point();
        Rect rect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        point.x = rect.right - rect.left;
        point.y = rect.bottom - rect.top;
        return point;
    }

    public static Point getFullSize(View view) {
        Point point = new Point();
        point.x = view.getRootView().getWidth();
        point.y = view.getRootView().getHeight();
        return point;
    }

    public static int getStatusBarHeight(Context context) {
        int identifier = context.getResources().getIdentifier("status_bar_height", "dimen", SystemMediaRouteProvider.PACKAGE_NAME);
        if (identifier > 0) {
            return context.getResources().getDimensionPixelSize(identifier);
        }
        return 0;
    }

    public static int getNavigationBarHeightStandard(Context context) {
        int identifier = context.getResources().getIdentifier("navigation_bar_height", "dimen", SystemMediaRouteProvider.PACKAGE_NAME);
        if (identifier > 0) {
            return context.getResources().getDimensionPixelSize(identifier);
        }
        return 0;
    }

    public static int getNavigationBarHeight(View view) {
        return getFullSize(view).y - getUsableSize(view.getContext()).y;
    }

    public static int getActionbarHeight(Object obj) {
        if (AppCompatActivity.class.isAssignableFrom(obj.getClass())) {
            return ((AppCompatActivity) obj).getSupportActionBar().getHeight();
        }
        if (Activity.class.isAssignableFrom(obj.getClass())) {
            return ((Activity) obj).getActionBar().getHeight();
        }
        return 0;
    }

    public static Point getScreenDimensions(Context context) {
        android.view.Display defaultDisplay = ((WindowManager) context.getSystemService("window")).getDefaultDisplay();
        Point point = new Point();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        defaultDisplay.getRealMetrics(displayMetrics);
        point.x = displayMetrics.widthPixels;
        point.y = displayMetrics.heightPixels;
        return point;
    }

    public static int getNavigationBarHeightKitkat(Context context) {
        return getScreenDimensions(context).y - getUsableSize(context).y;
    }

    public static boolean orientationLandscape(Context context) {
        return context.getResources().getConfiguration().orientation == 2;
    }

    public static int getSoftButtonsBarHeight(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int i = displayMetrics.heightPixels;
        activity.getWindowManager().getDefaultDisplay().getRealMetrics(displayMetrics);
        int i2 = displayMetrics.heightPixels;
        if (i2 > i) {
            return i2 - i;
        }
        return 0;
    }
}
