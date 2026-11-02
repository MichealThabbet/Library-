package it.feio.android.simplegallery.util;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import androidx.mediarouter.media.SystemMediaRouteProvider;

/* loaded from: classes2.dex */
public class Display {
    public static Point getUsableSize(Context context) {
        android.view.Display defaultDisplay;
        Point point = new Point();
        try {
            WindowManager windowManager = (WindowManager) context.getSystemService("window");
            if (windowManager != null && (defaultDisplay = windowManager.getDefaultDisplay()) != null) {
                if (Build.VERSION.SDK_INT < 13) {
                    point.set(defaultDisplay.getWidth(), defaultDisplay.getHeight());
                } else {
                    defaultDisplay.getSize(point);
                }
            }
        } catch (Exception e) {
            Log.e("checkDisplaySize", "Error checking display sizes", e);
        }
        return point;
    }

    public static Point getVisibleSize(View view) {
        Point point = new Point();
        Rect rect = new Rect();
        view.getWindowVisibleDisplayFrame(rect);
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
}
