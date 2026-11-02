package com.app.orsozoxi.Views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.app.orsozoxi.R;

/* loaded from: classes.dex */
public class CircleImageView extends ImageView {
    private float angle;
    private String name;
    private int position;

    public float getAngle() {
        return this.angle;
    }

    public void setAngle(float f) {
        this.angle = f;
    }

    public int getPosition() {
        return this.position;
    }

    public void setPosition(int i) {
        this.position = i;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public CircleImageView(Context context) {
        this(context, null);
    }

    public CircleImageView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public CircleImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.angle = 0.0f;
        this.position = 0;
        if (attributeSet != null) {
            this.name = getContext().obtainStyledAttributes(attributeSet, R.styleable.CircleImageView).getString(5);
        }
    }
}
