package it.feio.android.simplegallery;

import android.app.ActionBar;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import it.feio.android.simplegallery.views.TouchImageView;

/* loaded from: classes2.dex */
public class GalleryPagerFragment extends Fragment {
    public static final String ARG_PAGE = "page";
    public static final String ARG_PATH = "path";
    private Uri mImagePath;
    private int mPageNumber;

    public static GalleryPagerFragment create(int i, Uri uri) {
        GalleryPagerFragment galleryPagerFragment = new GalleryPagerFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_PAGE, i);
        bundle.putSerializable("path", uri.toString());
        galleryPagerFragment.setArguments(bundle);
        return galleryPagerFragment;
    }

    @Override // androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mPageNumber = getArguments().getInt(ARG_PAGE);
        this.mImagePath = Uri.parse(getArguments().getString("path"));
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        TouchImageView touchImageView = new TouchImageView(getActivity());
        touchImageView.setLayoutParams(new ActionBar.LayoutParams(-1, -1));
        Glide.with(this).load(this.mImagePath).transition(DrawableTransitionOptions.withCrossFade()).apply(new RequestOptions().fitCenter().placeholder(new ColorDrawable(ViewCompat.MEASURED_STATE_MASK)).error(R.drawable.image_broken)).into(touchImageView);
        return touchImageView;
    }

    public int getPageNumber() {
        return this.mPageNumber;
    }
}
