package it.feio.android.pixlui.links;

import android.text.style.ClickableSpan;
import android.view.View;

/* loaded from: classes2.dex */
public class InternalURLSpan extends ClickableSpan {
    private String clickedSpan;
    private TextLinkClickListener mTextLinkClickListener;

    public InternalURLSpan(String str) {
        this.clickedSpan = str;
    }

    public void setTextLinkClickListener(TextLinkClickListener textLinkClickListener) {
        this.mTextLinkClickListener = textLinkClickListener;
    }

    @Override // android.text.style.ClickableSpan
    public void onClick(View view) {
        TextLinkClickListener textLinkClickListener = this.mTextLinkClickListener;
        String str = this.clickedSpan;
        textLinkClickListener.onTextLinkClick(view, str, UrlCompleter.complete(str));
    }
}
