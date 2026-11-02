package androidx.mediarouter.app;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.os.SystemClock;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatDialog;
import androidx.core.util.ObjectsCompat;
import androidx.mediarouter.R;
import androidx.mediarouter.app.MediaRouteChooserDialog;
import androidx.mediarouter.media.MediaRouteSelector;
import androidx.mediarouter.media.MediaRouter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public class MediaRouteCastDialog extends AppCompatDialog {
    static final int CONNECTION_TIMEOUT_MILLIS = (int) TimeUnit.SECONDS.toMillis(30);
    static final int MSG_UPDATE_ROUTES = 1;
    static final String TAG = "MediaRouteCastDialog";
    private static final long UPDATE_ROUTES_DELAY_MS = 300;
    private RecyclerAdapter mAdapter;
    int mArtIconBackgroundColor;
    Bitmap mArtIconBitmap;
    boolean mArtIconIsLoaded;
    Bitmap mArtIconLoadedBitmap;
    Uri mArtIconUri;
    private ImageView mArtView;
    private boolean mAttachedToWindow;
    private final MediaRouterCallback mCallback;
    private ImageButton mCloseButton;
    Context mContext;
    MediaControllerCallback mControllerCallback;
    private boolean mCreated;
    MediaDescriptionCompat mDescription;
    FetchArtTask mFetchArtTask;
    private final Handler mHandler;
    private long mLastUpdateTime;
    MediaControllerCompat mMediaController;
    private RelativeLayout mMetadataLayout;
    private RecyclerView mRecyclerView;
    final MediaRouter.RouteInfo mRoute;
    final MediaRouter mRouter;
    final List<MediaRouter.RouteInfo> mRoutes;
    private MediaRouteSelector mSelector;
    private Button mStopCastingButton;
    private TextView mSubtitleView;
    private String mTitlePlaceholder;
    private TextView mTitleView;
    VolumeChangeListener mVolumeChangeListener;
    int mVolumeSliderColor;

    public MediaRouteCastDialog(Context context) {
        this(context, 0);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public MediaRouteCastDialog(Context context, int i) {
        Context contextCreateThemedDialogContext = MediaRouterThemeHelper.createThemedDialogContext(context, i, false);
        super(contextCreateThemedDialogContext, MediaRouterThemeHelper.createThemedDialogStyle(contextCreateThemedDialogContext));
        this.mSelector = MediaRouteSelector.EMPTY;
        this.mRoutes = new ArrayList();
        this.mHandler = new Handler() { // from class: androidx.mediarouter.app.MediaRouteCastDialog.1
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                if (message.what != 1) {
                    return;
                }
                MediaRouteCastDialog.this.updateRoutes((List) message.obj);
            }
        };
        Context context2 = getContext();
        this.mContext = context2;
        this.mRouter = MediaRouter.getInstance(context2);
        this.mCallback = new MediaRouterCallback();
        this.mRoute = this.mRouter.getSelectedRoute();
        this.mControllerCallback = new MediaControllerCallback();
        setMediaSession(this.mRouter.getMediaSessionToken());
    }

    private void setMediaSession(MediaSessionCompat.Token token) {
        MediaControllerCompat mediaControllerCompat = this.mMediaController;
        if (mediaControllerCompat != null) {
            mediaControllerCompat.unregisterCallback(this.mControllerCallback);
            this.mMediaController = null;
        }
        if (token != null && this.mAttachedToWindow) {
            try {
                this.mMediaController = new MediaControllerCompat(this.mContext, token);
            } catch (RemoteException e) {
                Log.e(TAG, "Error creating media controller in setMediaSession.", e);
            }
            MediaControllerCompat mediaControllerCompat2 = this.mMediaController;
            if (mediaControllerCompat2 != null) {
                mediaControllerCompat2.registerCallback(this.mControllerCallback);
            }
            MediaControllerCompat mediaControllerCompat3 = this.mMediaController;
            MediaMetadataCompat metadata = mediaControllerCompat3 == null ? null : mediaControllerCompat3.getMetadata();
            this.mDescription = metadata != null ? metadata.getDescription() : null;
            updateArtIconIfNeeded();
            update();
        }
    }

    public MediaSessionCompat.Token getMediaSession() {
        MediaControllerCompat mediaControllerCompat = this.mMediaController;
        if (mediaControllerCompat == null) {
            return null;
        }
        return mediaControllerCompat.getSessionToken();
    }

    public MediaRouteSelector getRouteSelector() {
        return this.mSelector;
    }

    public void setRouteSelector(MediaRouteSelector mediaRouteSelector) {
        if (mediaRouteSelector == null) {
            throw new IllegalArgumentException("selector must not be null");
        }
        if (this.mSelector.equals(mediaRouteSelector)) {
            return;
        }
        this.mSelector = mediaRouteSelector;
        if (this.mAttachedToWindow) {
            this.mRouter.removeCallback(this.mCallback);
            this.mRouter.addCallback(mediaRouteSelector, this.mCallback, 1);
        }
        refreshRoutes();
    }

    public void onFilterRoutes(List<MediaRouter.RouteInfo> list) {
        for (int size = list.size() - 1; size >= 0; size--) {
            if (!onFilterRoute(list.get(size))) {
                list.remove(size);
            }
        }
    }

    public boolean onFilterRoute(MediaRouter.RouteInfo routeInfo) {
        return !routeInfo.isDefaultOrBluetooth() && routeInfo.isEnabled() && routeInfo.matchesSelector(this.mSelector);
    }

    @Override // androidx.appcompat.app.AppCompatDialog, android.app.Dialog
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.mr_cast_dialog);
        ImageButton imageButton = (ImageButton) findViewById(R.id.mr_cast_close_button);
        this.mCloseButton = imageButton;
        imageButton.setOnClickListener(new View.OnClickListener() { // from class: androidx.mediarouter.app.MediaRouteCastDialog.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MediaRouteCastDialog.this.dismiss();
            }
        });
        Button button = (Button) findViewById(R.id.mr_cast_stop_button);
        this.mStopCastingButton = button;
        button.setOnClickListener(new View.OnClickListener() { // from class: androidx.mediarouter.app.MediaRouteCastDialog.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (MediaRouteCastDialog.this.mRoute.isSelected()) {
                    MediaRouteCastDialog.this.mRouter.unselect(2);
                }
                MediaRouteCastDialog.this.dismiss();
            }
        });
        this.mAdapter = new RecyclerAdapter();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.mr_cast_list);
        this.mRecyclerView = recyclerView;
        recyclerView.setAdapter(this.mAdapter);
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(this.mContext));
        this.mVolumeChangeListener = new VolumeChangeListener();
        this.mVolumeSliderColor = MediaRouterThemeHelper.getControllerColor(this.mContext, 0);
        this.mMetadataLayout = (RelativeLayout) findViewById(R.id.mr_cast_meta);
        this.mArtView = (ImageView) findViewById(R.id.mr_cast_meta_art);
        this.mTitleView = (TextView) findViewById(R.id.mr_cast_meta_title);
        this.mSubtitleView = (TextView) findViewById(R.id.mr_cast_meta_subtitle);
        this.mTitlePlaceholder = this.mContext.getResources().getString(R.string.mr_cast_dialog_title_view_placeholder);
        this.mCreated = true;
        updateLayout();
    }

    void updateLayout() {
        getWindow().setLayout(-1, -1);
        this.mArtIconBitmap = null;
        this.mArtIconUri = null;
        updateArtIconIfNeeded();
        update();
    }

    @Override // android.app.Dialog, android.view.Window.Callback
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mAttachedToWindow = true;
        this.mRouter.addCallback(this.mSelector, this.mCallback, 1);
        refreshRoutes();
        setMediaSession(this.mRouter.getMediaSessionToken());
    }

    @Override // android.app.Dialog, android.view.Window.Callback
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mAttachedToWindow = false;
        this.mRouter.removeCallback(this.mCallback);
        this.mHandler.removeMessages(1);
        setMediaSession(null);
    }

    void update() {
        if (!this.mRoute.isSelected() || this.mRoute.isDefaultOrBluetooth()) {
            dismiss();
            return;
        }
        if (this.mCreated) {
            if (this.mArtIconIsLoaded) {
                if (isBitmapRecycled(this.mArtIconLoadedBitmap)) {
                    this.mArtView.setVisibility(8);
                    Log.w(TAG, "Can't set artwork image with recycled bitmap: " + this.mArtIconLoadedBitmap);
                } else {
                    this.mArtView.setVisibility(0);
                    this.mArtView.setImageBitmap(this.mArtIconLoadedBitmap);
                    this.mArtView.setBackgroundColor(this.mArtIconBackgroundColor);
                    this.mMetadataLayout.setBackgroundDrawable(new BitmapDrawable(this.mArtIconLoadedBitmap));
                }
                clearLoadedBitmap();
            } else {
                this.mArtView.setVisibility(8);
            }
            updateMetadataLayout();
        }
    }

    static boolean isBitmapRecycled(Bitmap bitmap) {
        return bitmap != null && bitmap.isRecycled();
    }

    int getDesiredArtHeight(int i, int i2) {
        return this.mArtView.getHeight();
    }

    void updateArtIconIfNeeded() {
        if (isIconChanged()) {
            FetchArtTask fetchArtTask = this.mFetchArtTask;
            if (fetchArtTask != null) {
                fetchArtTask.cancel(true);
            }
            FetchArtTask fetchArtTask2 = new FetchArtTask();
            this.mFetchArtTask = fetchArtTask2;
            fetchArtTask2.execute(new Void[0]);
        }
    }

    void clearLoadedBitmap() {
        this.mArtIconIsLoaded = false;
        this.mArtIconLoadedBitmap = null;
        this.mArtIconBackgroundColor = 0;
    }

    private boolean isIconChanged() {
        MediaDescriptionCompat mediaDescriptionCompat = this.mDescription;
        Bitmap iconBitmap = mediaDescriptionCompat == null ? null : mediaDescriptionCompat.getIconBitmap();
        MediaDescriptionCompat mediaDescriptionCompat2 = this.mDescription;
        Uri iconUri = mediaDescriptionCompat2 != null ? mediaDescriptionCompat2.getIconUri() : null;
        FetchArtTask fetchArtTask = this.mFetchArtTask;
        Bitmap iconBitmap2 = fetchArtTask == null ? this.mArtIconBitmap : fetchArtTask.getIconBitmap();
        FetchArtTask fetchArtTask2 = this.mFetchArtTask;
        Uri iconUri2 = fetchArtTask2 == null ? this.mArtIconUri : fetchArtTask2.getIconUri();
        if (iconBitmap2 != iconBitmap) {
            return true;
        }
        return iconBitmap2 == null && ObjectsCompat.equals(iconUri2, iconUri);
    }

    private void updateMetadataLayout() {
        MediaDescriptionCompat mediaDescriptionCompat = this.mDescription;
        CharSequence title = mediaDescriptionCompat == null ? null : mediaDescriptionCompat.getTitle();
        boolean z = !TextUtils.isEmpty(title);
        MediaDescriptionCompat mediaDescriptionCompat2 = this.mDescription;
        CharSequence subtitle = mediaDescriptionCompat2 != null ? mediaDescriptionCompat2.getSubtitle() : null;
        boolean z2 = !TextUtils.isEmpty(subtitle);
        if (z) {
            this.mTitleView.setText(title);
        } else {
            this.mTitleView.setText(this.mTitlePlaceholder);
        }
        if (z2) {
            this.mSubtitleView.setText(subtitle);
            this.mSubtitleView.setVisibility(0);
        } else {
            this.mSubtitleView.setVisibility(8);
        }
    }

    private class VolumeChangeListener implements SeekBar.OnSeekBarChangeListener {
        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public void onStopTrackingTouch(SeekBar seekBar) {
        }

        VolumeChangeListener() {
        }
    }

    public void refreshRoutes() {
        if (this.mAttachedToWindow) {
            ArrayList arrayList = new ArrayList(this.mRouter.getRoutes());
            onFilterRoutes(arrayList);
            Collections.sort(arrayList, MediaRouteChooserDialog.RouteComparator.sInstance);
            if (SystemClock.uptimeMillis() - this.mLastUpdateTime >= UPDATE_ROUTES_DELAY_MS) {
                updateRoutes(arrayList);
                return;
            }
            this.mHandler.removeMessages(1);
            Handler handler = this.mHandler;
            handler.sendMessageAtTime(handler.obtainMessage(1, arrayList), this.mLastUpdateTime + UPDATE_ROUTES_DELAY_MS);
        }
    }

    void updateRoutes(List<MediaRouter.RouteInfo> list) {
        this.mLastUpdateTime = SystemClock.uptimeMillis();
        this.mRoutes.clear();
        this.mRoutes.addAll(list);
        this.mAdapter.setItems();
    }

    private final class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private static final int ITEM_TYPE_GROUP = 4;
        private static final int ITEM_TYPE_GROUP_VOLUME = 1;
        private static final int ITEM_TYPE_HEADER = 2;
        private static final int ITEM_TYPE_ROUTE = 3;
        private static final String TAG = "RecyclerAdapter";
        private final Drawable mDefaultIcon;
        private final LayoutInflater mInflater;
        private final Drawable mSpeakerGroupIcon;
        private final Drawable mSpeakerIcon;
        private final Drawable mTvIcon;
        private final ArrayList<Item> mItems = new ArrayList<>();
        private final ArrayList<MediaRouter.RouteInfo> mAvailableRoutes = new ArrayList<>();
        private final ArrayList<MediaRouter.RouteInfo> mAvailableGroups = new ArrayList<>();

        RecyclerAdapter() {
            this.mInflater = LayoutInflater.from(MediaRouteCastDialog.this.mContext);
            this.mDefaultIcon = MediaRouterThemeHelper.getDefaultDrawableIcon(MediaRouteCastDialog.this.mContext);
            this.mTvIcon = MediaRouterThemeHelper.getTvDrawableIcon(MediaRouteCastDialog.this.mContext);
            this.mSpeakerIcon = MediaRouterThemeHelper.getSpeakerDrawableIcon(MediaRouteCastDialog.this.mContext);
            this.mSpeakerGroupIcon = MediaRouterThemeHelper.getSpeakerGropuIcon(MediaRouteCastDialog.this.mContext);
            setItems();
        }

        boolean isSelectedRoute(MediaRouter.RouteInfo routeInfo) {
            if (routeInfo.isSelected()) {
                return true;
            }
            if (!(MediaRouteCastDialog.this.mRoute instanceof MediaRouter.RouteGroup)) {
                return false;
            }
            Iterator<MediaRouter.RouteInfo> it2 = ((MediaRouter.RouteGroup) MediaRouteCastDialog.this.mRoute).getRoutes().iterator();
            while (it2.hasNext()) {
                if (it2.next().getId().equals(routeInfo.getId())) {
                    return true;
                }
            }
            return false;
        }

        void setItems() {
            this.mItems.clear();
            if (MediaRouteCastDialog.this.mRoute instanceof MediaRouter.RouteGroup) {
                this.mItems.add(new Item(MediaRouteCastDialog.this.mRoute, 1));
                Iterator<MediaRouter.RouteInfo> it2 = ((MediaRouter.RouteGroup) MediaRouteCastDialog.this.mRoute).getRoutes().iterator();
                while (it2.hasNext()) {
                    this.mItems.add(new Item(it2.next(), 3));
                }
            } else {
                this.mItems.add(new Item(MediaRouteCastDialog.this.mRoute, 3));
            }
            this.mAvailableRoutes.clear();
            this.mAvailableGroups.clear();
            for (MediaRouter.RouteInfo routeInfo : MediaRouteCastDialog.this.mRoutes) {
                if (!isSelectedRoute(routeInfo)) {
                    if (routeInfo instanceof MediaRouter.RouteGroup) {
                        this.mAvailableGroups.add(routeInfo);
                    } else {
                        this.mAvailableRoutes.add(routeInfo);
                    }
                }
            }
            if (this.mAvailableRoutes.size() > 0) {
                this.mItems.add(new Item(MediaRouteCastDialog.this.mContext.getString(R.string.mr_dialog_device_header), 2));
                Iterator<MediaRouter.RouteInfo> it3 = this.mAvailableRoutes.iterator();
                while (it3.hasNext()) {
                    this.mItems.add(new Item(it3.next(), 3));
                }
            }
            if (this.mAvailableGroups.size() > 0) {
                this.mItems.add(new Item(MediaRouteCastDialog.this.mContext.getString(R.string.mr_dialog_route_header), 2));
                Iterator<MediaRouter.RouteInfo> it4 = this.mAvailableGroups.iterator();
                while (it4.hasNext()) {
                    this.mItems.add(new Item(it4.next(), 4));
                }
            }
            notifyDataSetChanged();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            if (i == 1) {
                return new GroupVolumeViewHolder(this.mInflater.inflate(R.layout.mr_cast_group_volume_item, viewGroup, false));
            }
            if (i == 2) {
                return new HeaderViewHolder(this.mInflater.inflate(R.layout.mr_dialog_header_item, viewGroup, false));
            }
            if (i == 3) {
                return new RouteViewHolder(this.mInflater.inflate(R.layout.mr_cast_route_item, viewGroup, false));
            }
            if (i == 4) {
                return new GroupViewHolder(this.mInflater.inflate(R.layout.mr_cast_group_item, viewGroup, false));
            }
            Log.w(TAG, "Cannot create ViewHolder because of wrong view type");
            return null;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
            int itemViewType = getItemViewType(i);
            Item item = getItem(i);
            if (itemViewType == 1) {
                ((GroupVolumeViewHolder) viewHolder).bindGroupVolumeView(item);
                return;
            }
            if (itemViewType == 2) {
                ((HeaderViewHolder) viewHolder).bindHeaderViewHolder(item);
                return;
            }
            if (itemViewType == 3) {
                ((RouteViewHolder) viewHolder).bindRouteViewHolder(item);
            } else if (itemViewType == 4) {
                ((GroupViewHolder) viewHolder).bindGroupViewHolder(item);
            } else {
                Log.w(TAG, "Cannot bind item to ViewHolder because of wrong view type");
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            return this.mItems.size();
        }

        Drawable getIconDrawable(MediaRouter.RouteInfo routeInfo) {
            Uri iconUri = routeInfo.getIconUri();
            if (iconUri != null) {
                try {
                    Drawable drawableCreateFromStream = Drawable.createFromStream(MediaRouteCastDialog.this.mContext.getContentResolver().openInputStream(iconUri), null);
                    if (drawableCreateFromStream != null) {
                        return drawableCreateFromStream;
                    }
                } catch (IOException e) {
                    Log.w(TAG, "Failed to load " + iconUri, e);
                }
            }
            return getDefaultIconDrawable(routeInfo);
        }

        private Drawable getDefaultIconDrawable(MediaRouter.RouteInfo routeInfo) {
            int deviceType = routeInfo.getDeviceType();
            if (deviceType == 1) {
                return this.mTvIcon;
            }
            if (deviceType == 2) {
                return this.mSpeakerIcon;
            }
            if (routeInfo instanceof MediaRouter.RouteGroup) {
                return this.mSpeakerGroupIcon;
            }
            return this.mDefaultIcon;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemViewType(int i) {
            return this.mItems.get(i).getType();
        }

        public Item getItem(int i) {
            return this.mItems.get(i);
        }

        private class Item {
            private final Object mData;
            private final int mType;

            Item(Object obj, int i) {
                this.mData = obj;
                this.mType = i;
            }

            public Object getData() {
                return this.mData;
            }

            public int getType() {
                return this.mType;
            }
        }

        private class GroupVolumeViewHolder extends RecyclerView.ViewHolder {
            MediaRouteVolumeSlider mGroupVolumeSlider;
            TextView mTextView;

            GroupVolumeViewHolder(View view) {
                super(view);
                this.mTextView = (TextView) view.findViewById(R.id.mr_group_volume_route_name);
                this.mGroupVolumeSlider = (MediaRouteVolumeSlider) view.findViewById(R.id.mr_group_volume_slider);
            }

            public void bindGroupVolumeView(Item item) {
                MediaRouter.RouteInfo routeInfo = (MediaRouter.RouteInfo) item.getData();
                this.mTextView.setText(routeInfo.getName().toUpperCase());
                this.mGroupVolumeSlider.setColor(MediaRouteCastDialog.this.mVolumeSliderColor);
                this.mGroupVolumeSlider.setTag(routeInfo);
                this.mGroupVolumeSlider.setProgress(MediaRouteCastDialog.this.mRoute.getVolume());
                this.mGroupVolumeSlider.setOnSeekBarChangeListener(MediaRouteCastDialog.this.mVolumeChangeListener);
            }
        }

        private class HeaderViewHolder extends RecyclerView.ViewHolder {
            TextView mTextView;

            HeaderViewHolder(View view) {
                super(view);
                this.mTextView = (TextView) view.findViewById(R.id.mr_dialog_header_name);
            }

            public void bindHeaderViewHolder(Item item) {
                this.mTextView.setText(item.getData().toString().toUpperCase());
            }
        }

        private class RouteViewHolder extends RecyclerView.ViewHolder {
            CheckBox mCheckBox;
            ImageView mImageView;
            TextView mTextView;
            MediaRouteVolumeSlider mVolumeSlider;

            RouteViewHolder(View view) {
                super(view);
                this.mImageView = (ImageView) view.findViewById(R.id.mr_cast_route_icon);
                this.mTextView = (TextView) view.findViewById(R.id.mr_cast_route_name);
                this.mCheckBox = (CheckBox) view.findViewById(R.id.mr_cast_checkbox);
                this.mVolumeSlider = (MediaRouteVolumeSlider) view.findViewById(R.id.mr_cast_volume_slider);
            }

            public void bindRouteViewHolder(Item item) {
                MediaRouter.RouteInfo routeInfo = (MediaRouter.RouteInfo) item.getData();
                this.mImageView.setImageDrawable(RecyclerAdapter.this.getIconDrawable(routeInfo));
                this.mTextView.setText(routeInfo.getName());
                this.mCheckBox.setChecked(RecyclerAdapter.this.isSelectedRoute(routeInfo));
                this.mVolumeSlider.setColor(MediaRouteCastDialog.this.mVolumeSliderColor);
                this.mVolumeSlider.setTag(routeInfo);
                this.mVolumeSlider.setProgress(routeInfo.getVolume());
                this.mVolumeSlider.setOnSeekBarChangeListener(MediaRouteCastDialog.this.mVolumeChangeListener);
            }
        }

        private class GroupViewHolder extends RecyclerView.ViewHolder {
            ImageView mImageView;
            TextView mTextView;

            GroupViewHolder(View view) {
                super(view);
                this.mImageView = (ImageView) view.findViewById(R.id.mr_cast_group_icon);
                this.mTextView = (TextView) view.findViewById(R.id.mr_cast_group_name);
            }

            public void bindGroupViewHolder(Item item) {
                MediaRouter.RouteInfo routeInfo = (MediaRouter.RouteInfo) item.getData();
                this.mImageView.setImageDrawable(RecyclerAdapter.this.getIconDrawable(routeInfo));
                this.mTextView.setText(routeInfo.getName());
            }
        }
    }

    private final class MediaRouterCallback extends MediaRouter.Callback {
        MediaRouterCallback() {
        }

        @Override // androidx.mediarouter.media.MediaRouter.Callback
        public void onRouteAdded(MediaRouter mediaRouter, MediaRouter.RouteInfo routeInfo) {
            MediaRouteCastDialog.this.refreshRoutes();
        }

        @Override // androidx.mediarouter.media.MediaRouter.Callback
        public void onRouteRemoved(MediaRouter mediaRouter, MediaRouter.RouteInfo routeInfo) {
            MediaRouteCastDialog.this.refreshRoutes();
        }

        @Override // androidx.mediarouter.media.MediaRouter.Callback
        public void onRouteSelected(MediaRouter mediaRouter, MediaRouter.RouteInfo routeInfo) {
            MediaRouteCastDialog.this.update();
        }

        @Override // androidx.mediarouter.media.MediaRouter.Callback
        public void onRouteUnselected(MediaRouter mediaRouter, MediaRouter.RouteInfo routeInfo) {
            MediaRouteCastDialog.this.update();
        }

        @Override // androidx.mediarouter.media.MediaRouter.Callback
        public void onRouteChanged(MediaRouter mediaRouter, MediaRouter.RouteInfo routeInfo) {
            MediaRouteCastDialog.this.refreshRoutes();
            MediaRouteCastDialog.this.update();
        }
    }

    private final class MediaControllerCallback extends MediaControllerCompat.Callback {
        MediaControllerCallback() {
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.Callback
        public void onSessionDestroyed() {
            if (MediaRouteCastDialog.this.mMediaController != null) {
                MediaRouteCastDialog.this.mMediaController.unregisterCallback(MediaRouteCastDialog.this.mControllerCallback);
                MediaRouteCastDialog.this.mMediaController = null;
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.Callback
        public void onMetadataChanged(MediaMetadataCompat mediaMetadataCompat) {
            MediaRouteCastDialog.this.mDescription = mediaMetadataCompat == null ? null : mediaMetadataCompat.getDescription();
            MediaRouteCastDialog.this.updateArtIconIfNeeded();
            MediaRouteCastDialog.this.update();
        }
    }

    private class FetchArtTask extends AsyncTask<Void, Void, Bitmap> {
        private int mBackgroundColor;
        private final Bitmap mIconBitmap;
        private final Uri mIconUri;

        FetchArtTask() {
            Bitmap iconBitmap = MediaRouteCastDialog.this.mDescription == null ? null : MediaRouteCastDialog.this.mDescription.getIconBitmap();
            if (MediaRouteCastDialog.isBitmapRecycled(iconBitmap)) {
                Log.w(MediaRouteCastDialog.TAG, "Can't fetch the given art bitmap because it's already recycled.");
                iconBitmap = null;
            }
            this.mIconBitmap = iconBitmap;
            this.mIconUri = MediaRouteCastDialog.this.mDescription != null ? MediaRouteCastDialog.this.mDescription.getIconUri() : null;
        }

        public Bitmap getIconBitmap() {
            return this.mIconBitmap;
        }

        public Uri getIconUri() {
            return this.mIconUri;
        }

        @Override // android.os.AsyncTask
        protected void onPreExecute() {
            MediaRouteCastDialog.this.clearLoadedBitmap();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:56:0x00d2  */
        /* JADX WARN: Removed duplicated region for block: B:58:0x00e7  */
        /* JADX WARN: Type inference failed for: r4v0 */
        /* JADX WARN: Type inference failed for: r4v1 */
        /* JADX WARN: Type inference failed for: r4v2, types: [java.io.InputStream] */
        @Override // android.os.AsyncTask
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public android.graphics.Bitmap doInBackground(java.lang.Void... r10) throws java.lang.Throwable {
            /*
                Method dump skipped, instructions count: 284
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.mediarouter.app.MediaRouteCastDialog.FetchArtTask.doInBackground(java.lang.Void[]):android.graphics.Bitmap");
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(Bitmap bitmap) {
            MediaRouteCastDialog.this.mFetchArtTask = null;
            if (ObjectsCompat.equals(MediaRouteCastDialog.this.mArtIconBitmap, this.mIconBitmap) && ObjectsCompat.equals(MediaRouteCastDialog.this.mArtIconUri, this.mIconUri)) {
                return;
            }
            MediaRouteCastDialog.this.mArtIconBitmap = this.mIconBitmap;
            MediaRouteCastDialog.this.mArtIconLoadedBitmap = bitmap;
            MediaRouteCastDialog.this.mArtIconUri = this.mIconUri;
            MediaRouteCastDialog.this.mArtIconBackgroundColor = this.mBackgroundColor;
            MediaRouteCastDialog.this.mArtIconIsLoaded = true;
            MediaRouteCastDialog.this.update();
        }

        private InputStream openInputStreamByScheme(Uri uri) throws IOException {
            InputStream inputStreamOpenInputStream;
            String lowerCase = uri.getScheme().toLowerCase();
            if ("android.resource".equals(lowerCase) || "content".equals(lowerCase) || "file".equals(lowerCase)) {
                inputStreamOpenInputStream = MediaRouteCastDialog.this.mContext.getContentResolver().openInputStream(uri);
            } else {
                URLConnection uRLConnectionOpenConnection = new URL(uri.toString()).openConnection();
                uRLConnectionOpenConnection.setConnectTimeout(MediaRouteCastDialog.CONNECTION_TIMEOUT_MILLIS);
                uRLConnectionOpenConnection.setReadTimeout(MediaRouteCastDialog.CONNECTION_TIMEOUT_MILLIS);
                inputStreamOpenInputStream = uRLConnectionOpenConnection.getInputStream();
            }
            if (inputStreamOpenInputStream == null) {
                return null;
            }
            return new BufferedInputStream(inputStreamOpenInputStream);
        }
    }
}
