package com.google.android.youtube.player;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import androidx.core.view.ViewCompat;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.internal.aa;
import com.google.android.youtube.player.internal.ab;
import com.google.android.youtube.player.internal.n;
import com.google.android.youtube.player.internal.s;
import com.google.android.youtube.player.internal.t;
import com.google.android.youtube.player.internal.w;
import com.google.android.youtube.player.internal.y;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/* loaded from: classes2.dex */
public final class YouTubePlayerView extends ViewGroup implements YouTubePlayer.Provider {
    private final a a;
    private final Set<View> b;
    private final b c;
    private com.google.android.youtube.player.internal.b d;
    private s e;
    private View f;
    private n g;
    private YouTubePlayer.Provider h;
    private Bundle i;
    private YouTubePlayer.OnInitializedListener j;
    private boolean k;
    private boolean l;

    private final class a implements ViewTreeObserver.OnGlobalFocusChangeListener {
        private a() {
        }

        /* synthetic */ a(YouTubePlayerView youTubePlayerView, byte b) {
            this();
        }

        @Override // android.view.ViewTreeObserver.OnGlobalFocusChangeListener
        public final void onGlobalFocusChanged(View view, View view2) {
            if (YouTubePlayerView.this.e == null || !YouTubePlayerView.this.b.contains(view2) || YouTubePlayerView.this.b.contains(view)) {
                return;
            }
            YouTubePlayerView.this.e.g();
        }
    }

    interface b {
        void a(YouTubePlayerView youTubePlayerView);

        void a(YouTubePlayerView youTubePlayerView, String str, YouTubePlayer.OnInitializedListener onInitializedListener);
    }

    public YouTubePlayerView(Context context) {
        this(context, null);
    }

    public YouTubePlayerView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public YouTubePlayerView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, ((YouTubeBaseActivity) context).a());
        if (!(context instanceof YouTubeBaseActivity)) {
            throw new IllegalStateException("A YouTubePlayerView can only be created with an Activity  which extends YouTubeBaseActivity as its context.");
        }
    }

    YouTubePlayerView(Context context, AttributeSet attributeSet, int i, b bVar) {
        super((Context) ab.a(context, "context cannot be null"), attributeSet, i);
        this.c = (b) ab.a(bVar, "listener cannot be null");
        if (getBackground() == null) {
            setBackgroundColor(ViewCompat.MEASURED_STATE_MASK);
        }
        setClipToPadding(false);
        n nVar = new n(context);
        this.g = nVar;
        requestTransparentRegion(nVar);
        addView(this.g);
        this.b = new HashSet();
        this.a = new a(this, (byte) 0);
    }

    private void a(View view) {
        if (!(view == this.g || (this.e != null && view == this.f))) {
            throw new UnsupportedOperationException("No views can be added on top of the player");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(YouTubeInitializationResult youTubeInitializationResult) {
        this.e = null;
        this.g.c();
        YouTubePlayer.OnInitializedListener onInitializedListener = this.j;
        if (onInitializedListener != null) {
            onInitializedListener.onInitializationFailure(this.h, youTubeInitializationResult);
            this.j = null;
        }
    }

    static /* synthetic */ void a(YouTubePlayerView youTubePlayerView, Activity activity) {
        try {
            s sVar = new s(youTubePlayerView.d, aa.a().a(activity, youTubePlayerView.d, youTubePlayerView.k));
            youTubePlayerView.e = sVar;
            View viewA = sVar.a();
            youTubePlayerView.f = viewA;
            youTubePlayerView.addView(viewA);
            youTubePlayerView.removeView(youTubePlayerView.g);
            youTubePlayerView.c.a(youTubePlayerView);
            if (youTubePlayerView.j != null) {
                boolean zA = false;
                Bundle bundle = youTubePlayerView.i;
                if (bundle != null) {
                    zA = youTubePlayerView.e.a(bundle);
                    youTubePlayerView.i = null;
                }
                youTubePlayerView.j.onInitializationSuccess(youTubePlayerView.h, youTubePlayerView.e, zA);
                youTubePlayerView.j = null;
            }
        } catch (w.a e) {
            y.a("Error creating YouTubePlayerView", e);
            youTubePlayerView.a(YouTubeInitializationResult.INTERNAL_ERROR);
        }
    }

    static /* synthetic */ com.google.android.youtube.player.internal.b b(YouTubePlayerView youTubePlayerView) {
        youTubePlayerView.d = null;
        return null;
    }

    static /* synthetic */ View g(YouTubePlayerView youTubePlayerView) {
        youTubePlayerView.f = null;
        return null;
    }

    static /* synthetic */ s h(YouTubePlayerView youTubePlayerView) {
        youTubePlayerView.e = null;
        return null;
    }

    final void a() {
        s sVar = this.e;
        if (sVar != null) {
            sVar.b();
        }
    }

    final void a(final Activity activity, YouTubePlayer.Provider provider, String str, YouTubePlayer.OnInitializedListener onInitializedListener, Bundle bundle) {
        if (this.e == null && this.j == null) {
            ab.a(activity, "activity cannot be null");
            this.h = (YouTubePlayer.Provider) ab.a(provider, "provider cannot be null");
            this.j = (YouTubePlayer.OnInitializedListener) ab.a(onInitializedListener, "listener cannot be null");
            this.i = bundle;
            this.g.b();
            com.google.android.youtube.player.internal.b bVarA = aa.a().a(getContext(), str, new t.a() { // from class: com.google.android.youtube.player.YouTubePlayerView.1
                @Override // com.google.android.youtube.player.internal.t.a
                public final void a() {
                    if (YouTubePlayerView.this.d != null) {
                        YouTubePlayerView.a(YouTubePlayerView.this, activity);
                    }
                    YouTubePlayerView.b(YouTubePlayerView.this);
                }

                @Override // com.google.android.youtube.player.internal.t.a
                public final void b() {
                    if (!YouTubePlayerView.this.l && YouTubePlayerView.this.e != null) {
                        YouTubePlayerView.this.e.f();
                    }
                    YouTubePlayerView.this.g.a();
                    YouTubePlayerView youTubePlayerView = YouTubePlayerView.this;
                    if (youTubePlayerView.indexOfChild(youTubePlayerView.g) < 0) {
                        YouTubePlayerView youTubePlayerView2 = YouTubePlayerView.this;
                        youTubePlayerView2.addView(youTubePlayerView2.g);
                        YouTubePlayerView youTubePlayerView3 = YouTubePlayerView.this;
                        youTubePlayerView3.removeView(youTubePlayerView3.f);
                    }
                    YouTubePlayerView.g(YouTubePlayerView.this);
                    YouTubePlayerView.h(YouTubePlayerView.this);
                    YouTubePlayerView.b(YouTubePlayerView.this);
                }
            }, new t.b() { // from class: com.google.android.youtube.player.YouTubePlayerView.2
                @Override // com.google.android.youtube.player.internal.t.b
                public final void a(YouTubeInitializationResult youTubeInitializationResult) {
                    YouTubePlayerView.this.a(youTubeInitializationResult);
                    YouTubePlayerView.b(YouTubePlayerView.this);
                }
            });
            this.d = bVarA;
            bVarA.e();
        }
    }

    final void a(boolean z) {
        if (!z || Build.VERSION.SDK_INT >= 14) {
            this.k = z;
        } else {
            y.a("Could not enable TextureView because API level is lower than 14", new Object[0]);
            this.k = false;
        }
    }

    @Override // android.view.View
    public final void addFocusables(ArrayList<View> arrayList, int i) {
        ArrayList<View> arrayList2 = new ArrayList<>();
        super.addFocusables(arrayList2, i);
        arrayList.addAll(arrayList2);
        this.b.clear();
        this.b.addAll(arrayList2);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void addFocusables(ArrayList<View> arrayList, int i, int i2) {
        ArrayList<View> arrayList2 = new ArrayList<>();
        super.addFocusables(arrayList2, i, i2);
        arrayList.addAll(arrayList2);
        this.b.clear();
        this.b.addAll(arrayList2);
    }

    @Override // android.view.ViewGroup
    public final void addView(View view) {
        a(view);
        super.addView(view);
    }

    @Override // android.view.ViewGroup
    public final void addView(View view, int i) {
        a(view);
        super.addView(view, i);
    }

    @Override // android.view.ViewGroup
    public final void addView(View view, int i, int i2) {
        a(view);
        super.addView(view, i, i2);
    }

    @Override // android.view.ViewGroup
    public final void addView(View view, int i, ViewGroup.LayoutParams layoutParams) {
        a(view);
        super.addView(view, i, layoutParams);
    }

    @Override // android.view.ViewGroup, android.view.ViewManager
    public final void addView(View view, ViewGroup.LayoutParams layoutParams) {
        a(view);
        super.addView(view, layoutParams);
    }

    final void b() {
        s sVar = this.e;
        if (sVar != null) {
            sVar.c();
        }
    }

    final void b(boolean z) {
        s sVar = this.e;
        if (sVar != null) {
            sVar.b(z);
            c(z);
        }
    }

    final void c() {
        s sVar = this.e;
        if (sVar != null) {
            sVar.d();
        }
    }

    final void c(boolean z) {
        this.l = true;
        s sVar = this.e;
        if (sVar != null) {
            sVar.a(z);
        }
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final void clearChildFocus(View view) {
        if (hasFocusable()) {
            requestFocus();
        } else {
            super.clearChildFocus(view);
        }
    }

    final void d() {
        s sVar = this.e;
        if (sVar != null) {
            sVar.e();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (this.e != null) {
            if (keyEvent.getAction() == 0) {
                return this.e.a(keyEvent.getKeyCode(), keyEvent) || super.dispatchKeyEvent(keyEvent);
            }
            if (keyEvent.getAction() == 1) {
                return this.e.b(keyEvent.getKeyCode(), keyEvent) || super.dispatchKeyEvent(keyEvent);
            }
        }
        return super.dispatchKeyEvent(keyEvent);
    }

    final Bundle e() {
        s sVar = this.e;
        return sVar == null ? this.i : sVar.h();
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final void focusableViewAvailable(View view) {
        super.focusableViewAvailable(view);
        this.b.add(view);
    }

    @Override // com.google.android.youtube.player.YouTubePlayer.Provider
    public final void initialize(String str, YouTubePlayer.OnInitializedListener onInitializedListener) {
        ab.a(str, (Object) "Developer key cannot be null or empty");
        this.c.a(this, str, onInitializedListener);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected final void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnGlobalFocusChangeListener(this.a);
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        s sVar = this.e;
        if (sVar != null) {
            sVar.a(configuration);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getViewTreeObserver().removeOnGlobalFocusChangeListener(this.a);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        if (getChildCount() > 0) {
            getChildAt(0).layout(0, 0, i3 - i, i4 - i2);
        }
    }

    @Override // android.view.View
    protected final void onMeasure(int i, int i2) {
        if (getChildCount() <= 0) {
            setMeasuredDimension(0, 0);
            return;
        }
        View childAt = getChildAt(0);
        childAt.measure(i, i2);
        setMeasuredDimension(childAt.getMeasuredWidth(), childAt.getMeasuredHeight());
    }

    @Override // android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        super.onTouchEvent(motionEvent);
        return true;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final void requestChildFocus(View view, View view2) {
        super.requestChildFocus(view, view2);
        this.b.add(view2);
    }

    @Override // android.view.ViewGroup
    public final void setClipToPadding(boolean z) {
    }

    @Override // android.view.View
    public final void setPadding(int i, int i2, int i3, int i4) {
    }
}
