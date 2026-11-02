package it.piapostolos.android.pinotes;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import it.feio.android.simplegallery.models.GalleryPagerAdapter;
import it.feio.android.simplegallery.views.GalleryViewPager;
import it.piapostolos.android.pinotes.R2;
import it.piapostolos.android.pinotes.helpers.LogDelegate;
import it.piapostolos.android.pinotes.models.Attachment;
import it.piapostolos.android.pinotes.models.listeners.OnViewTouchedListener;
import it.piapostolos.android.pinotes.models.views.InterceptorFrameLayout;
import it.piapostolos.android.pinotes.utils.ConstantsBase;
import it.piapostolos.android.pinotes.utils.FileProviderHelper;
import it.piapostolos.android.pinotes.utils.StorageHelper;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes3.dex */
public class GalleryActivity extends AppCompatActivity {
    private static final boolean AUTO_HIDE = false;
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;
    private static final boolean TOGGLE_ON_CLICK = true;

    @BindView(R2.id.gallery_root)
    InterceptorFrameLayout galleryRootView;
    private List<Attachment> images;

    @BindView(R2.id.fullscreen_content)
    GalleryViewPager mViewPager;
    OnViewTouchedListener screenTouches = new OnViewTouchedListener() { // from class: it.piapostolos.android.pinotes.GalleryActivity.1
        private final int MOVING_THRESHOLD = 30;
        private boolean status_pressed = false;
        float x;
        float y;

        @Override // it.piapostolos.android.pinotes.models.listeners.OnViewTouchedListener
        public void onViewTouchOccurred(MotionEvent motionEvent) {
            if ((motionEvent.getAction() & 255) == 0) {
                this.x = motionEvent.getX();
                this.y = motionEvent.getY();
                this.status_pressed = true;
            }
            if ((motionEvent.getAction() & 255) == 2) {
                float fAbs = Math.abs(this.x - motionEvent.getX());
                float fAbs2 = Math.abs(this.y - motionEvent.getY());
                double dSqrt = Math.sqrt((fAbs * fAbs) + (fAbs2 * fAbs2));
                LogDelegate.d("Moved of " + dSqrt);
                if (dSqrt >= 30.0d) {
                    this.status_pressed = false;
                }
            }
            if ((motionEvent.getAction() & 255) == 1 && this.status_pressed) {
                click();
                this.status_pressed = false;
            }
        }

        private void click() {
            if (((Attachment) GalleryActivity.this.images.get(GalleryActivity.this.mViewPager.getCurrentItem())).getMime_type().equals(ConstantsBase.MIME_TYPE_VIDEO)) {
                GalleryActivity.this.viewMedia();
            }
        }
    };

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_gallery);
        ButterKnife.bind(this);
        initViews();
        initData();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStart() {
        super.onStart();
    }

    @Override // android.app.Activity
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_gallery, menu);
        return true;
    }

    private void initViews() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        this.galleryRootView.setOnViewTouchedListener(this.screenTouches);
        this.mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: it.piapostolos.android.pinotes.GalleryActivity.2
            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int i) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrolled(int i, float f, int i2) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageSelected(int i) {
                GalleryActivity.this.getSupportActionBar().setSubtitle("(" + (i + 1) + "/" + GalleryActivity.this.images.size() + ")");
            }
        });
    }

    private void initData() {
        String stringExtra = getIntent().getStringExtra(ConstantsBase.GALLERY_TITLE);
        this.images = getIntent().getParcelableArrayListExtra(ConstantsBase.GALLERY_IMAGES);
        int intExtra = getIntent().getIntExtra(ConstantsBase.GALLERY_CLICKED_IMAGE, 0);
        ArrayList arrayList = new ArrayList();
        Iterator<Attachment> it2 = this.images.iterator();
        while (it2.hasNext()) {
            arrayList.add(it2.next().getUri());
        }
        GalleryPagerAdapter galleryPagerAdapter = new GalleryPagerAdapter(this, arrayList);
        this.mViewPager.setOffscreenPageLimit(3);
        this.mViewPager.setAdapter(galleryPagerAdapter);
        this.mViewPager.setCurrentItem(intExtra);
        getSupportActionBar().setTitle(stringExtra);
        getSupportActionBar().setSubtitle("(" + (intExtra + 1) + "/" + this.images.size() + ")");
        if (this.images.get(intExtra).getMime_type().equals(ConstantsBase.MIME_TYPE_VIDEO)) {
            viewMedia();
        }
    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId == 16908332) {
            onBackPressed();
        } else if (itemId == R.id.menu_gallery_share) {
            shareMedia();
        } else if (itemId == R.id.menu_gallery) {
            viewMedia();
        } else {
            LogDelegate.e("Wrong element choosen: " + menuItem.getItemId());
        }
        return super.onOptionsItemSelected(menuItem);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void viewMedia() {
        Attachment attachment = this.images.get(this.mViewPager.getCurrentItem());
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setFlags(1);
        intent.setDataAndType(FileProviderHelper.getShareableUri(attachment), StorageHelper.getMimeType(this, attachment.getUri()));
        startActivity(intent);
    }

    private void shareMedia() {
        Attachment attachment = this.images.get(this.mViewPager.getCurrentItem());
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType(StorageHelper.getMimeType(this, attachment.getUri()));
        intent.putExtra("android.intent.extra.STREAM", FileProviderHelper.getShareableUri(attachment));
        startActivity(intent);
    }
}
