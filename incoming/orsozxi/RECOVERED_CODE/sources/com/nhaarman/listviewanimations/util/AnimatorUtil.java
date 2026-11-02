package com.nhaarman.listviewanimations.util;

import com.nineoldandroids.animation.Animator;

/* loaded from: classes2.dex */
public class AnimatorUtil {
    private AnimatorUtil() {
    }

    public static Animator[] concatAnimators(Animator[] animatorArr, Animator[] animatorArr2, Animator animator) {
        int length = animatorArr.length + animatorArr2.length + 1;
        Animator[] animatorArr3 = new Animator[length];
        int i = 0;
        while (i < animatorArr.length) {
            animatorArr3[i] = animatorArr[i];
            i++;
        }
        for (Animator animator2 : animatorArr2) {
            animatorArr3[i] = animator2;
            i++;
        }
        animatorArr3[length - 1] = animator;
        return animatorArr3;
    }
}
