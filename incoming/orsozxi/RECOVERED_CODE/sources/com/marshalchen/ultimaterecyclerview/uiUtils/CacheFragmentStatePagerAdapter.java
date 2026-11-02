package com.marshalchen.ultimaterecyclerview.uiUtils;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.SparseArray;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

/* loaded from: classes2.dex */
public abstract class CacheFragmentStatePagerAdapter extends FragmentStatePagerAdapter {
    private static final String STATE_PAGES = "pages";
    private static final String STATE_PAGE_INDEX_PREFIX = "pageIndex:";
    private static final String STATE_PAGE_KEY_PREFIX = "page:";
    private static final String STATE_SUPER_STATE = "superState";
    private FragmentManager mFm;
    private SparseArray<Fragment> mPages;

    protected abstract Fragment createItem(int i);

    public CacheFragmentStatePagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
        this.mPages = new SparseArray<>();
        this.mFm = fragmentManager;
    }

    @Override // androidx.fragment.app.FragmentStatePagerAdapter, androidx.viewpager.widget.PagerAdapter
    public Parcelable saveState() {
        Parcelable parcelableSaveState = super.saveState();
        Bundle bundle = new Bundle();
        bundle.putParcelable(STATE_SUPER_STATE, parcelableSaveState);
        bundle.putInt(STATE_PAGES, this.mPages.size());
        if (this.mPages.size() > 0) {
            for (int i = 0; i < this.mPages.size(); i++) {
                int iKeyAt = this.mPages.keyAt(i);
                bundle.putInt(createCacheIndex(i), iKeyAt);
                this.mFm.putFragment(bundle, createCacheKey(iKeyAt), this.mPages.get(iKeyAt));
            }
        }
        return bundle;
    }

    @Override // androidx.fragment.app.FragmentStatePagerAdapter, androidx.viewpager.widget.PagerAdapter
    public void restoreState(Parcelable parcelable, ClassLoader classLoader) throws NumberFormatException {
        Bundle bundle = (Bundle) parcelable;
        int i = bundle.getInt(STATE_PAGES);
        if (i > 0) {
            for (int i2 = 0; i2 < i; i2++) {
                int i3 = bundle.getInt(createCacheIndex(i2));
                this.mPages.put(i3, this.mFm.getFragment(bundle, createCacheKey(i3)));
            }
        }
        super.restoreState(bundle.getParcelable(STATE_SUPER_STATE), classLoader);
    }

    @Override // androidx.fragment.app.FragmentStatePagerAdapter
    public Fragment getItem(int i) {
        Fragment fragmentCreateItem = createItem(i);
        this.mPages.put(i, fragmentCreateItem);
        return fragmentCreateItem;
    }

    @Override // androidx.fragment.app.FragmentStatePagerAdapter, androidx.viewpager.widget.PagerAdapter
    public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
        if (this.mPages.indexOfKey(i) >= 0) {
            this.mPages.remove(i);
        }
        super.destroyItem(viewGroup, i, obj);
    }

    public Fragment getItemAt(int i) {
        return this.mPages.get(i);
    }

    protected String createCacheIndex(int i) {
        return STATE_PAGE_INDEX_PREFIX + i;
    }

    protected String createCacheKey(int i) {
        return STATE_PAGE_KEY_PREFIX + i;
    }
}
