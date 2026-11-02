package me.zhanghai.android.materialprogressbar;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Path;
import me.zhanghai.android.materialprogressbar.Interpolators;
import me.zhanghai.android.materialprogressbar.internal.ObjectAnimatorCompat;
import org.apache.poi.ss.util.CellUtil;

/* loaded from: classes3.dex */
class Animators {
    private static final Path PATH_INDETERMINATE_HORIZONTAL_RECT1_SCALE_X;
    private static final Path PATH_INDETERMINATE_HORIZONTAL_RECT1_TRANSLATE_X;
    private static final Path PATH_INDETERMINATE_HORIZONTAL_RECT2_SCALE_X;
    private static final Path PATH_INDETERMINATE_HORIZONTAL_RECT2_TRANSLATE_X;

    private Animators() {
    }

    static {
        Path path = new Path();
        PATH_INDETERMINATE_HORIZONTAL_RECT1_TRANSLATE_X = path;
        path.moveTo(-522.6f, 0.0f);
        PATH_INDETERMINATE_HORIZONTAL_RECT1_TRANSLATE_X.rCubicTo(48.89972f, 0.0f, 166.02657f, 0.0f, 301.2173f, 0.0f);
        PATH_INDETERMINATE_HORIZONTAL_RECT1_TRANSLATE_X.rCubicTo(197.58128f, 0.0f, 420.9827f, 0.0f, 420.9827f, 0.0f);
        Path path2 = new Path();
        PATH_INDETERMINATE_HORIZONTAL_RECT1_SCALE_X = path2;
        path2.moveTo(0.0f, 0.1f);
        PATH_INDETERMINATE_HORIZONTAL_RECT1_SCALE_X.lineTo(1.0f, 0.8268492f);
        PATH_INDETERMINATE_HORIZONTAL_RECT1_SCALE_X.lineTo(2.0f, 0.1f);
        Path path3 = new Path();
        PATH_INDETERMINATE_HORIZONTAL_RECT2_TRANSLATE_X = path3;
        path3.moveTo(-197.6f, 0.0f);
        PATH_INDETERMINATE_HORIZONTAL_RECT2_TRANSLATE_X.rCubicTo(14.28182f, 0.0f, 85.07782f, 0.0f, 135.54689f, 0.0f);
        PATH_INDETERMINATE_HORIZONTAL_RECT2_TRANSLATE_X.rCubicTo(54.26191f, 0.0f, 90.42461f, 0.0f, 168.24332f, 0.0f);
        PATH_INDETERMINATE_HORIZONTAL_RECT2_TRANSLATE_X.rCubicTo(144.72154f, 0.0f, 316.40982f, 0.0f, 316.40982f, 0.0f);
        Path path4 = new Path();
        PATH_INDETERMINATE_HORIZONTAL_RECT2_SCALE_X = path4;
        path4.moveTo(0.0f, 0.1f);
        PATH_INDETERMINATE_HORIZONTAL_RECT2_SCALE_X.lineTo(1.0f, 0.5713795f);
        PATH_INDETERMINATE_HORIZONTAL_RECT2_SCALE_X.lineTo(2.0f, 0.90995026f);
        PATH_INDETERMINATE_HORIZONTAL_RECT2_SCALE_X.lineTo(3.0f, 0.1f);
    }

    public static Animator createIndeterminateHorizontalRect1(Object obj) {
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimatorCompat.ofFloat(obj, "translateX", (String) null, PATH_INDETERMINATE_HORIZONTAL_RECT1_TRANSLATE_X);
        objectAnimatorOfFloat.setDuration(2000L);
        objectAnimatorOfFloat.setInterpolator(Interpolators.INDETERMINATE_HORIZONTAL_RECT1_TRANSLATE_X.INSTANCE);
        objectAnimatorOfFloat.setRepeatCount(-1);
        ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimatorCompat.ofFloat(obj, (String) null, "scaleX", PATH_INDETERMINATE_HORIZONTAL_RECT1_SCALE_X);
        objectAnimatorOfFloat2.setDuration(2000L);
        objectAnimatorOfFloat2.setInterpolator(Interpolators.INDETERMINATE_HORIZONTAL_RECT1_SCALE_X.INSTANCE);
        objectAnimatorOfFloat2.setRepeatCount(-1);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(objectAnimatorOfFloat, objectAnimatorOfFloat2);
        return animatorSet;
    }

    public static Animator createIndeterminateHorizontalRect2(Object obj) {
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimatorCompat.ofFloat(obj, "translateX", (String) null, PATH_INDETERMINATE_HORIZONTAL_RECT2_TRANSLATE_X);
        objectAnimatorOfFloat.setDuration(2000L);
        objectAnimatorOfFloat.setInterpolator(Interpolators.INDETERMINATE_HORIZONTAL_RECT2_TRANSLATE_X.INSTANCE);
        objectAnimatorOfFloat.setRepeatCount(-1);
        ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimatorCompat.ofFloat(obj, (String) null, "scaleX", PATH_INDETERMINATE_HORIZONTAL_RECT2_SCALE_X);
        objectAnimatorOfFloat2.setDuration(2000L);
        objectAnimatorOfFloat2.setInterpolator(Interpolators.INDETERMINATE_HORIZONTAL_RECT2_SCALE_X.INSTANCE);
        objectAnimatorOfFloat2.setRepeatCount(-1);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(objectAnimatorOfFloat, objectAnimatorOfFloat2);
        return animatorSet;
    }

    public static Animator createIndeterminate(Object obj) {
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(obj, "trimPathStart", 0.0f, 0.75f);
        objectAnimatorOfFloat.setDuration(1333L);
        objectAnimatorOfFloat.setInterpolator(Interpolators.TRIM_PATH_START.INSTANCE);
        objectAnimatorOfFloat.setRepeatCount(-1);
        ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(obj, "trimPathEnd", 0.0f, 0.75f);
        objectAnimatorOfFloat2.setDuration(1333L);
        objectAnimatorOfFloat2.setInterpolator(Interpolators.TRIM_PATH_END.INSTANCE);
        objectAnimatorOfFloat2.setRepeatCount(-1);
        ObjectAnimator objectAnimatorOfFloat3 = ObjectAnimator.ofFloat(obj, "trimPathOffset", 0.0f, 0.25f);
        objectAnimatorOfFloat3.setDuration(1333L);
        objectAnimatorOfFloat3.setInterpolator(Interpolators.LINEAR.INSTANCE);
        objectAnimatorOfFloat3.setRepeatCount(-1);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(objectAnimatorOfFloat, objectAnimatorOfFloat2, objectAnimatorOfFloat3);
        return animatorSet;
    }

    public static Animator createIndeterminateRotation(Object obj) {
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(obj, CellUtil.ROTATION, 0.0f, 720.0f);
        objectAnimatorOfFloat.setDuration(6665L);
        objectAnimatorOfFloat.setInterpolator(Interpolators.LINEAR.INSTANCE);
        objectAnimatorOfFloat.setRepeatCount(-1);
        return objectAnimatorOfFloat;
    }
}
