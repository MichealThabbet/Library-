package it.piapostolos.android.pinotes.models.views;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ListView;
import android.widget.RelativeLayout;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorListener;
import com.getbase.floatingactionbutton.AddFloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import it.piapostolos.android.pinotes.PiNotes;
import it.piapostolos.android.pinotes.R;
import it.piapostolos.android.pinotes.models.listeners.AbsListViewScrollDetector;
import it.piapostolos.android.pinotes.models.listeners.OnFabItemClickedListener;

/* loaded from: classes3.dex */
public class Fab {
    private boolean expandOnLongClick;
    private boolean fabAllowed;
    private boolean fabExpanded;
    private boolean fabHidden;
    private FloatingActionsMenu floatingActionsMenu;
    private final ListView listView;
    private View.OnClickListener onClickListener = new View.OnClickListener() { // from class: it.piapostolos.android.pinotes.models.views.Fab.2
        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            Fab.this.onFabItemClickedListener.onFabItemClick(view.getId());
        }
    };
    OnFabItemClickedListener onFabItemClickedListener;
    private View overlay;

    public Fab(View view, ListView listView, boolean z) {
        this.floatingActionsMenu = (FloatingActionsMenu) view;
        this.listView = listView;
        this.expandOnLongClick = z;
        init();
    }

    private void init() {
        this.fabHidden = true;
        this.fabExpanded = false;
        AddFloatingActionButton addFloatingActionButton = (AddFloatingActionButton) this.floatingActionsMenu.findViewById(R.id.fab_expand_menu_button);
        addFloatingActionButton.setOnClickListener(new View.OnClickListener() { // from class: it.piapostolos.android.pinotes.models.views.-$$Lambda$Fab$dIuFm0PEIC0WxuRN8Tqo3SqhCLI
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$init$0$Fab(view);
            }
        });
        addFloatingActionButton.setOnLongClickListener(new View.OnLongClickListener() { // from class: it.piapostolos.android.pinotes.models.views.-$$Lambda$Fab$beMVyXgB6FsEfmPY7-XWG5JKxkM
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view) {
                return this.f$0.lambda$init$1$Fab(view);
            }
        });
        this.listView.setOnScrollListener(new AbsListViewScrollDetector() { // from class: it.piapostolos.android.pinotes.models.views.Fab.1
            @Override // it.piapostolos.android.pinotes.models.listeners.AbsListViewScrollDetector
            public void onScrollUp() {
                if (Fab.this.floatingActionsMenu != null) {
                    Fab.this.floatingActionsMenu.collapse();
                    Fab.this.hideFab();
                }
            }

            @Override // it.piapostolos.android.pinotes.models.listeners.AbsListViewScrollDetector
            public void onScrollDown() {
                if (Fab.this.floatingActionsMenu != null) {
                    Fab.this.floatingActionsMenu.collapse();
                    Fab.this.showFab();
                }
            }
        });
        this.floatingActionsMenu.findViewById(R.id.fab_checklist).setOnClickListener(this.onClickListener);
        if (this.expandOnLongClick) {
            return;
        }
        View viewFindViewById = this.floatingActionsMenu.findViewById(R.id.fab_note);
        viewFindViewById.setVisibility(0);
        viewFindViewById.setOnClickListener(this.onClickListener);
    }

    public /* synthetic */ void lambda$init$0$Fab(View view) {
        if (!isExpanded() && this.expandOnLongClick) {
            performAction(view);
        } else {
            performToggle();
        }
    }

    public /* synthetic */ boolean lambda$init$1$Fab(View view) {
        if (!this.expandOnLongClick) {
            performAction(view);
            return true;
        }
        performToggle();
        return true;
    }

    public void performToggle() {
        this.fabExpanded = !this.fabExpanded;
        this.floatingActionsMenu.toggle();
    }

    private void performAction(View view) {
        if (this.fabExpanded) {
            this.floatingActionsMenu.toggle();
            this.fabExpanded = false;
        } else {
            this.onFabItemClickedListener.onFabItemClick(view.getId());
        }
    }

    public void showFab() {
        if (this.floatingActionsMenu != null && this.fabAllowed && this.fabHidden) {
            animateFab(0, 0, 0);
            this.fabHidden = false;
        }
    }

    public void hideFab() {
        FloatingActionsMenu floatingActionsMenu = this.floatingActionsMenu;
        if (floatingActionsMenu == null || this.fabHidden) {
            return;
        }
        floatingActionsMenu.collapse();
        animateFab(this.floatingActionsMenu.getHeight() + getMarginBottom(this.floatingActionsMenu), 0, 4);
        this.fabHidden = true;
        this.fabExpanded = false;
    }

    private void animateFab(int i, final int i2, final int i3) {
        ViewCompat.animate(this.floatingActionsMenu).setInterpolator(new AccelerateDecelerateInterpolator()).setDuration(200L).translationY(i).setListener(new ViewPropertyAnimatorListener() { // from class: it.piapostolos.android.pinotes.models.views.Fab.3
            @Override // androidx.core.view.ViewPropertyAnimatorListener
            public void onAnimationCancel(View view) {
            }

            @Override // androidx.core.view.ViewPropertyAnimatorListener
            public void onAnimationStart(View view) {
                Fab.this.floatingActionsMenu.setVisibility(i2);
            }

            @Override // androidx.core.view.ViewPropertyAnimatorListener
            public void onAnimationEnd(View view) {
                Fab.this.floatingActionsMenu.setVisibility(i3);
            }
        });
    }

    public void setAllowed(boolean z) {
        this.fabAllowed = z;
    }

    private int getMarginBottom(View view) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            return ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin;
        }
        return 0;
    }

    public void setOnFabItemClickedListener(OnFabItemClickedListener onFabItemClickedListener) {
        this.onFabItemClickedListener = onFabItemClickedListener;
    }

    public void setOverlay(View view) {
        this.overlay = view;
        view.setOnClickListener(new View.OnClickListener() { // from class: it.piapostolos.android.pinotes.models.views.-$$Lambda$Fab$4BezfkQOVOEc7WXdRJVysQmAT2w
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.lambda$setOverlay$2$Fab(view2);
            }
        });
    }

    public /* synthetic */ void lambda$setOverlay$2$Fab(View view) {
        performToggle();
    }

    public void setOverlay(int i) {
        View view = new View(PiNotes.getAppContext());
        view.setBackgroundResource(i);
        view.setLayoutParams(new RelativeLayout.LayoutParams(-1, -1));
        view.setVisibility(8);
        view.setOnClickListener(new View.OnClickListener() { // from class: it.piapostolos.android.pinotes.models.views.-$$Lambda$Fab$N3zYexlHgHrzIpO5GJru0VOXvR8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.lambda$setOverlay$3$Fab(view2);
            }
        });
        ViewGroup viewGroup = (ViewGroup) this.floatingActionsMenu.getParent();
        viewGroup.addView(view, viewGroup.indexOfChild(this.floatingActionsMenu));
        this.overlay = view;
    }

    public /* synthetic */ void lambda$setOverlay$3$Fab(View view) {
        performToggle();
    }

    public boolean isExpanded() {
        return this.fabExpanded;
    }
}
