package com.app.piapostolos.Views;

import android.widget.Button;

/* loaded from: classes.dex */
public class MenuDateView {
    private Button menuButton;
    private UKHorizontalScrollView menuView;

    public MenuDateView(UKHorizontalScrollView uKHorizontalScrollView, Button button) {
        this.menuButton = button;
        this.menuView = uKHorizontalScrollView;
    }

    public void setMenuButton(Button button) {
        this.menuButton = button;
    }

    public void setMenuView(UKHorizontalScrollView uKHorizontalScrollView) {
        this.menuView = uKHorizontalScrollView;
    }

    public Button getMenuButton() {
        return this.menuButton;
    }

    public UKHorizontalScrollView getMenuView() {
        return this.menuView;
    }
}
