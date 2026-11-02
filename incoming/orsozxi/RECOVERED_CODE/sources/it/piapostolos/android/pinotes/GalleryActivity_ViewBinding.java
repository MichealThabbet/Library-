package it.piapostolos.android.pinotes;

import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import it.feio.android.simplegallery.views.GalleryViewPager;
import it.piapostolos.android.pinotes.models.views.InterceptorFrameLayout;

/* loaded from: classes3.dex */
public class GalleryActivity_ViewBinding implements Unbinder {
    private GalleryActivity target;

    public GalleryActivity_ViewBinding(GalleryActivity galleryActivity) {
        this(galleryActivity, galleryActivity.getWindow().getDecorView());
    }

    public GalleryActivity_ViewBinding(GalleryActivity galleryActivity, View view) {
        this.target = galleryActivity;
        galleryActivity.galleryRootView = (InterceptorFrameLayout) Utils.findRequiredViewAsType(view, R.id.gallery_root, "field 'galleryRootView'", InterceptorFrameLayout.class);
        galleryActivity.mViewPager = (GalleryViewPager) Utils.findRequiredViewAsType(view, R.id.fullscreen_content, "field 'mViewPager'", GalleryViewPager.class);
    }

    @Override // butterknife.Unbinder
    public void unbind() {
        GalleryActivity galleryActivity = this.target;
        if (galleryActivity == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        galleryActivity.galleryRootView = null;
        galleryActivity.mViewPager = null;
    }
}
