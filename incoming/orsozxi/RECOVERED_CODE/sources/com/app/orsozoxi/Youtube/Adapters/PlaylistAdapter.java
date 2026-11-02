package com.app.orsozoxi.Youtube.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.app.orsozoxi.Orsozoxi;
import com.app.orsozoxi.R;
import com.app.orsozoxi.Youtube.Beans.PlaylistItemDetails;
import com.app.orsozoxi.Youtube.FullscreenDemoActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class PlaylistAdapter extends BaseAdapter {
    private Activity context;
    private ArrayList<PlaylistItemDetails> items;
    private DisplayImageOptions options;

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return i;
    }

    public PlaylistAdapter(Activity activity, ArrayList<PlaylistItemDetails> arrayList) {
        this.context = activity;
        this.items = arrayList;
        try {
            this.options = new DisplayImageOptions.Builder().cacheInMemory(true).showImageOnFail(R.mipmap.ic_launcher).showImageForEmptyUri(R.mipmap.ic_launcher).imageScaleType(ImageScaleType.EXACTLY).bitmapConfig(Bitmap.Config.RGB_565).build();
        } catch (IllegalStateException unused) {
        }
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.items.size();
    }

    @Override // android.widget.Adapter
    public Object getItem(int i) {
        return this.items.get(i);
    }

    @Override // android.widget.Adapter
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = ((LayoutInflater) this.context.getSystemService("layout_inflater")).inflate(R.layout.playlist_items_row, (ViewGroup) null);
        }
        final PlaylistItemDetails playlistItemDetails = this.items.get(i);
        ((TextView) view.findViewById(R.id.tv_title)).setText(playlistItemDetails.getSnippet().getChannelTitle());
        ImageView imageView = (ImageView) view.findViewById(R.id.iv_menu);
        if (playlistItemDetails.getSnippet().getThumbnails().getHigh().getUrl().toString().equals(null)) {
            Log.d("test", "medium");
            ImageLoader.getInstance().displayImage(playlistItemDetails.getSnippet().getThumbnails().getMedium().getUrl(), imageView, this.options);
        } else {
            ImageLoader.getInstance().displayImage(playlistItemDetails.getSnippet().getThumbnails().getHigh().getUrl(), imageView, this.options);
        }
        view.setOnClickListener(new View.OnClickListener() { // from class: com.app.orsozoxi.Youtube.Adapters.PlaylistAdapter.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                Orsozoxi.setSelectedPlaylistItem((PlaylistItemDetails) PlaylistAdapter.this.items.get(i));
                Intent intent = new Intent(PlaylistAdapter.this.context, (Class<?>) FullscreenDemoActivity.class);
                intent.putExtra("playlistId", playlistItemDetails.getId());
                intent.putExtra("title", playlistItemDetails.getSnippet().getChannelTitle());
                PlaylistAdapter.this.context.startActivity(intent);
            }
        });
        return view;
    }
}
