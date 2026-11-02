package it.orsozoxi.android.orsonotes.utils;

import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Property;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import it.orsozoxi.android.orsonotes.R;

/* loaded from: classes3.dex */
public class AnimationsHelper {
    private AnimationsHelper() {
    }

    public static void zoomListItem(Context context, View view, ImageView imageView, View view2, AnimatorListenerAdapter animatorListenerAdapter) {
        float fWidth;
        long integer = context.getResources().getInteger(R.integer.zooming_view_anim_time);
        Rect rect = new Rect();
        Rect rect2 = new Rect();
        Point point = new Point();
        view.getGlobalVisibleRect(rect);
        view2.getGlobalVisibleRect(rect2, point);
        rect.offset(-point.x, -point.y);
        rect2.offset(-point.x, -point.y);
        if (rect2.width() / rect2.height() > rect.width() / rect.height()) {
            fWidth = rect.height() / rect2.height();
            float fWidth2 = ((rect2.width() * fWidth) - rect.width()) / 2.0f;
            rect.left = (int) (rect.left - fWidth2);
            rect.right = (int) (rect.right + fWidth2);
        } else {
            fWidth = rect.width() / rect2.width();
            float fHeight = ((rect2.height() * fWidth) - rect.height()) / 2.0f;
            rect.top = (int) (rect.top - fHeight);
            rect.bottom = (int) (rect.bottom + fHeight);
        }
        view.setAlpha(0.0f);
        imageView.setVisibility(0);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(ObjectAnimator.ofFloat(imageView, (Property<ImageView, Float>) View.X, rect.left, rect2.left)).with(ObjectAnimator.ofFloat(imageView, (Property<ImageView, Float>) View.Y, rect.top, rect2.top)).with(ObjectAnimator.ofFloat(imageView, (Property<ImageView, Float>) View.SCALE_X, fWidth, 1.0f)).with(ObjectAnimator.ofFloat(imageView, (Property<ImageView, Float>) View.SCALE_Y, fWidth, 1.0f));
        animatorSet.setDuration(integer);
        animatorSet.setInterpolator(new DecelerateInterpolator());
        animatorSet.addListener(animatorListenerAdapter);
        animatorSet.start();
    }

    public static void expandOrCollapse(final View view, boolean z) {
        TranslateAnimation translateAnimation;
        if (z) {
            translateAnimation = new TranslateAnimation(0.0f, 0.0f, -view.getHeight(), 0.0f);
            view.setVisibility(0);
        } else {
            translateAnimation = new TranslateAnimation(0.0f, 0.0f, 0.0f, -view.getHeight());
            translateAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: it.orsozoxi.android.orsonotes.utils.AnimationsHelper.1
                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationRepeat(Animation animation) {
                }

                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationStart(Animation animation) {
                }

                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationEnd(Animation animation) {
                    view.setVisibility(8);
                }
            });
        }
        translateAnimation.setDuration(300L);
        translateAnimation.setInterpolator(new AccelerateInterpolator(0.5f));
        view.startAnimation(translateAnimation);
    }
}
