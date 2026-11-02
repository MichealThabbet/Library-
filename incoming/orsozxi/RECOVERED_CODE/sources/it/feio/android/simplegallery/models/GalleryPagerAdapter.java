package it.feio.android.simplegallery.models;

import android.net.Uri;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import it.feio.android.simplegallery.GalleryPagerFragment;
import java.util.List;

/* loaded from: classes2.dex */
public class GalleryPagerAdapter extends FragmentStatePagerAdapter {
    private List<Uri> resources;

    public GalleryPagerAdapter(FragmentActivity fragmentActivity, List<Uri> list) {
        super(fragmentActivity.getSupportFragmentManager());
        this.resources = list;
    }

    @Override // androidx.fragment.app.FragmentStatePagerAdapter
    public Fragment getItem(int i) {
        return GalleryPagerFragment.create(i, this.resources.get(i));
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public int getCount() {
        return this.resources.size();
    }
}
