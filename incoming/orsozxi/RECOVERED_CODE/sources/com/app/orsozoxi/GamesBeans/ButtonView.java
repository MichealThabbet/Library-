package com.app.orsozoxi.GamesBeans;

import android.R;
import android.os.Build;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import com.app.orsozoxi.PuzzleActivity;

/* loaded from: classes.dex */
public class ButtonView extends FrameLayout {
    private View contentView;

    public ButtonView(final PuzzleActivity puzzleActivity, View view) {
        super(puzzleActivity);
        this.contentView = view;
        view.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        addView(view);
        RelativeLayout relativeLayout = new RelativeLayout(puzzleActivity);
        relativeLayout.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        addView(relativeLayout);
        if (hasPermanentMenuKey()) {
            return;
        }
        final ImageButton imageButton = new ImageButton(getContext());
        imageButton.setImageResource(R.drawable.ic_menu_more);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.addRule(11);
        layoutParams.addRule(12);
        imageButton.setLayoutParams(layoutParams);
        imageButton.setOnClickListener(new View.OnClickListener() { // from class: com.app.orsozoxi.GamesBeans.ButtonView.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                puzzleActivity.openContextMenu(imageButton);
            }
        });
        puzzleActivity.registerForContextMenu(imageButton);
        relativeLayout.addView(imageButton);
    }

    private boolean hasPermanentMenuKey() {
        if (Build.VERSION.SDK_INT < 11) {
            return true;
        }
        if (Build.VERSION.SDK_INT < 14) {
            return false;
        }
        return ViewConfiguration.get(getContext()).hasPermanentMenuKey();
    }

    public View getContentView() {
        return this.contentView;
    }
}
