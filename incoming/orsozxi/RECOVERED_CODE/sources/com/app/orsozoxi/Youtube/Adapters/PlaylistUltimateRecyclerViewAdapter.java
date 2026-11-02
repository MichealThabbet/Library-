package com.app.orsozoxi.Youtube.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.app.orsozoxi.Orsozoxi;
import com.app.orsozoxi.R;
import com.app.orsozoxi.Youtube.Beans.PlaylistItemDetails;
import com.app.orsozoxi.Youtube.FullscreenDemoActivity;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class PlaylistUltimateRecyclerViewAdapter extends UltimateViewAdapter<SimpleViewHolder> {
    Activity activity;
    ArrayList<PlaylistItemDetails> mItems;
    private DisplayImageOptions options;

    @Override // com.marshalchen.ultimaterecyclerview.UltimateViewAdapter
    public long generateHeaderId(int i) {
        return 0L;
    }

    @Override // com.marshalchen.ultimaterecyclerview.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
    }

    @Override // com.marshalchen.ultimaterecyclerview.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup viewGroup) {
        return null;
    }

    public PlaylistUltimateRecyclerViewAdapter(Activity activity, ArrayList<PlaylistItemDetails> arrayList) {
        this.mItems = new ArrayList<>();
        this.activity = activity;
        this.mItems = arrayList;
        try {
            this.options = new DisplayImageOptions.Builder().cacheInMemory(true).showImageOnFail(R.mipmap.ic_launcher).showImageForEmptyUri(R.mipmap.ic_launcher).imageScaleType(ImageScaleType.EXACTLY).bitmapConfig(Bitmap.Config.RGB_565).build();
        } catch (IllegalStateException unused) {
        }
    }

    @Override // com.marshalchen.ultimaterecyclerview.UltimateViewAdapter
    public SimpleViewHolder getViewHolder(View view) {
        return new SimpleViewHolder(view);
    }

    @Override // com.marshalchen.ultimaterecyclerview.UltimateViewAdapter
    public SimpleViewHolder onCreateViewHolder(ViewGroup viewGroup) {
        return new SimpleViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.playlist_items_row, viewGroup, false)) { // from class: com.app.orsozoxi.Youtube.Adapters.PlaylistUltimateRecyclerViewAdapter.1
        };
    }

    @Override // com.marshalchen.ultimaterecyclerview.UltimateViewAdapter
    public int getAdapterItemCount() {
        return this.mItems.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(SimpleViewHolder simpleViewHolder, final int i) {
        if (i < this.mItems.size()) {
            simpleViewHolder.mNameTextView.setText(this.mItems.get(i).getSnippet().getChannelTitle());
            if (this.mItems.get(i).getSnippet().getThumbnails().getHigh().getUrl().toString().equals(null)) {
                Log.d("test", "medium");
                Picasso.with(this.activity).load(this.mItems.get(i).getSnippet().getThumbnails().getMedium().getUrl().toString()).fit().into(simpleViewHolder.mImageView);
            } else {
                Picasso.with(this.activity).load(this.mItems.get(i).getSnippet().getThumbnails().getHigh().getUrl().toString()).fit().into(simpleViewHolder.mImageView);
            }
            simpleViewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() { // from class: com.app.orsozoxi.Youtube.Adapters.PlaylistUltimateRecyclerViewAdapter.2
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    Orsozoxi.setSelectedPlaylistItem(PlaylistUltimateRecyclerViewAdapter.this.mItems.get(i));
                    Intent intent = new Intent(PlaylistUltimateRecyclerViewAdapter.this.activity, (Class<?>) FullscreenDemoActivity.class);
                    intent.putExtra("playlistId", PlaylistUltimateRecyclerViewAdapter.this.mItems.get(i).getId());
                    intent.putExtra("title", PlaylistUltimateRecyclerViewAdapter.this.mItems.get(i).getSnippet().getChannelTitle());
                    PlaylistUltimateRecyclerViewAdapter.this.activity.startActivity(intent);
                }
            });
        }
    }

    public class SimpleViewHolder extends UltimateRecyclerviewViewHolder {
        public final ImageView mImageView;
        public final TextView mNameTextView;
        public final RelativeLayout relativeLayout;

        public SimpleViewHolder(View view) {
            super(view);
            this.mNameTextView = (TextView) view.findViewById(R.id.tv_title);
            this.relativeLayout = (RelativeLayout) view.findViewById(R.id.v_item);
            this.mImageView = (ImageView) view.findViewById(R.id.iv_menu);
        }
    }
}
