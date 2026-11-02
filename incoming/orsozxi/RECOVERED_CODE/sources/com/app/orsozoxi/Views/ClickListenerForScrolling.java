package com.app.orsozoxi.Views;

import android.view.View;

/* loaded from: classes.dex */
public class ClickListenerForScrolling implements View.OnClickListener {
    private View menu;
    private boolean menuOut = false;
    private UKHorizontalScrollView scrollView;

    public ClickListenerForScrolling(UKHorizontalScrollView uKHorizontalScrollView, View view) {
        this.scrollView = uKHorizontalScrollView;
        this.menu = view;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        int measuredWidth = this.menu.getMeasuredWidth();
        this.menu.setVisibility(0);
        UKHorizontalScrollView uKHorizontalScrollView = this.scrollView;
        if (!this.menuOut) {
            measuredWidth = 0;
        }
        uKHorizontalScrollView.smoothScrollTo(measuredWidth, 0);
        boolean z = !this.menuOut;
        this.menuOut = z;
        this.scrollView.setMenuOpen(z);
    }
}
