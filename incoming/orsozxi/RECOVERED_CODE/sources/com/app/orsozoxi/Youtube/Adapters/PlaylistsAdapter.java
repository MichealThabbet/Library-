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
import com.app.orsozoxi.R;
import com.app.orsozoxi.Youtube.Beans.PlaylistItem;
import com.app.orsozoxi.Youtube.PlayListItemsActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class PlaylistsAdapter extends BaseAdapter {
    private Activity context;
    private DisplayImageOptions options;
    private ArrayList<PlaylistItem> playlistItem;

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return i;
    }

    public PlaylistsAdapter(Activity activity, ArrayList<PlaylistItem> arrayList) {
        this.context = activity;
        this.playlistItem = arrayList;
        try {
            this.options = new DisplayImageOptions.Builder().cacheInMemory(true).showImageOnFail(R.mipmap.ic_launcher).showImageForEmptyUri(R.mipmap.ic_launcher).imageScaleType(ImageScaleType.EXACTLY).bitmapConfig(Bitmap.Config.RGB_565).build();
        } catch (IllegalStateException unused) {
        }
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.playlistItem.size();
    }

    @Override // android.widget.Adapter
    public Object getItem(int i) {
        return this.playlistItem.get(i);
    }

    @Override // android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = ((LayoutInflater) this.context.getSystemService("layout_inflater")).inflate(R.layout.playlists_row, (ViewGroup) null);
        }
        final PlaylistItem playlistItem = this.playlistItem.get(i);
        ((TextView) view.findViewById(R.id.tv_title)).setText(playlistItem.getSnippet().getChannelTitle());
        ImageView imageView = (ImageView) view.findViewById(R.id.iv_menu);
        if (playlistItem.getSnippet().getThumbnails().getHigh().getUrl().toString().equals(null)) {
            Log.d("test", "medium");
            ImageLoader.getInstance().displayImage(playlistItem.getSnippet().getThumbnails().getMedium().getUrl().toString(), imageView, this.options);
        } else {
            ImageLoader.getInstance().displayImage(playlistItem.getSnippet().getThumbnails().getHigh().getUrl().toString(), imageView, this.options);
        }
        view.setOnClickListener(new View.OnClickListener() { // from class: com.app.orsozoxi.Youtube.Adapters.PlaylistsAdapter.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                Intent intent = new Intent(PlaylistsAdapter.this.context, (Class<?>) PlayListItemsActivity.class);
                intent.putExtra("playlistId", playlistItem.getId());
                intent.putExtra("title", playlistItem.getSnippet().getChannelTitle());
                PlaylistsAdapter.this.context.startActivity(intent);
            }
        });
        return view;
    }
}
