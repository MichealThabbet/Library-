package com.marshalchen.ultimaterecyclerview.expanx.Util;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.marshalchen.ultimaterecyclerview.R;
import com.marshalchen.ultimaterecyclerview.expanx.ExpandableItemData;

/* loaded from: classes2.dex */
public abstract class easyTemplateChild<T extends ExpandableItemData, B extends TextView, H extends RelativeLayout> extends child<T> {
    private boolean capitalized;
    private boolean countenabled;
    private int itemMargin;
    private int offsetMargin;
    public H relativeLayout;
    public B text;

    protected void request_api(String[] strArr, String str) {
    }

    public easyTemplateChild(View view, int i, int i2) {
        this(view);
        this.itemMargin = i;
        this.offsetMargin = i2;
    }

    public easyTemplateChild(View view) {
        super(view);
        this.capitalized = false;
        this.countenabled = true;
        this.text = (B) view.findViewById(R.id.exp_section_title);
        this.relativeLayout = (H) view.findViewById(R.id.exp_section_ripple_wrapper_click);
        this.itemMargin = view.getContext().getResources().getDimensionPixelSize(R.dimen.item_margin);
        this.offsetMargin = view.getContext().getResources().getDimensionPixelSize(R.dimen.expand_size);
    }

    protected void forceTitleCapitalized(boolean z) {
        this.capitalized = z;
    }

    @Override // com.marshalchen.ultimaterecyclerview.expanx.Util.ChildVH
    public void bindView(final T t, int i) {
        if (this.capitalized) {
            this.text.setText(t.getText().toUpperCase());
        } else {
            this.text.setText(t.getText());
        }
        B b = this.text;
        b.setLayoutParams(getParamsLayoutOffset((TextView) b, (B) t));
        this.relativeLayout.setOnClickListener(new View.OnClickListener() { // from class: com.marshalchen.ultimaterecyclerview.expanx.Util.easyTemplateChild.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                easyTemplateChild.this.onChildItemClick(t.getText(), t.getPath());
            }
        });
    }

    @Override // com.marshalchen.ultimaterecyclerview.expanx.Util.ChildVH
    public void onChildItemClick(String str, String str2) {
        String[] strArrSplit = str2.split("/");
        if (strArrSplit.length > 1) {
            request_api(strArrSplit, str);
        }
    }
}
