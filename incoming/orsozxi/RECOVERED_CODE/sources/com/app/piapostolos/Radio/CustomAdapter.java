package com.app.piapostolos.Radio;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.app.piapostolos.R;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class CustomAdapter extends ArrayAdapter<MediaItem> {
    Context context;
    ViewHolder holder;
    LayoutInflater inflator;
    ArrayList<MediaItem> listOfSongs;

    public CustomAdapter(Context context, int i, ArrayList<MediaItem> arrayList) {
        super(context, i, arrayList);
        this.listOfSongs = arrayList;
        this.context = context;
        this.inflator = LayoutInflater.from(context);
    }

    private class ViewHolder {
        TextView textViewArtist;
        TextView textViewDuration;
        TextView textViewSongName;

        private ViewHolder() {
        }
    }

    @Override // android.widget.ArrayAdapter, android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = this.inflator.inflate(R.layout.custom_list, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder();
            this.holder = viewHolder;
            viewHolder.textViewSongName = (TextView) view.findViewById(R.id.textViewSongName);
            this.holder.textViewArtist = (TextView) view.findViewById(R.id.textViewArtist);
            this.holder.textViewDuration = (TextView) view.findViewById(R.id.textViewDuration);
            view.setTag(this.holder);
        } else {
            this.holder = (ViewHolder) view.getTag();
        }
        MediaItem mediaItem = this.listOfSongs.get(i);
        this.holder.textViewSongName.setText(mediaItem.toString());
        this.holder.textViewArtist.setText(mediaItem.getAlbum() + " - " + mediaItem.getArtist());
        this.holder.textViewDuration.setText(UtilFunctions.getDuration(mediaItem.getDuration()));
        return view;
    }
}
