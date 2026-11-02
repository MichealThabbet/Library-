package it.piapostolos.android.pinotes.models.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import java.util.List;

/* loaded from: classes3.dex */
public class PlacesAutoCompleteAdapter extends ArrayAdapter<String> implements Filterable {
    private static final int MIN_CHARS = 7;
    private List<String> resultList;

    public PlacesAutoCompleteAdapter(Context context, int i) {
        super(context, i);
    }

    @Override // android.widget.ArrayAdapter, android.widget.Adapter
    public int getCount() {
        return this.resultList.size();
    }

    @Override // android.widget.ArrayAdapter, android.widget.Adapter
    public String getItem(int i) {
        return this.resultList.get(i);
    }

    @Override // android.widget.ArrayAdapter, android.widget.Filterable
    public Filter getFilter() {
        return new Filter() { // from class: it.piapostolos.android.pinotes.models.adapters.PlacesAutoCompleteAdapter.1
            @Override // android.widget.Filter
            protected Filter.FilterResults performFiltering(CharSequence charSequence) {
                Filter.FilterResults filterResults = new Filter.FilterResults();
                if (charSequence != null) {
                    charSequence.length();
                }
                return filterResults;
            }

            @Override // android.widget.Filter
            protected void publishResults(CharSequence charSequence, Filter.FilterResults filterResults) {
                if (filterResults != null && filterResults.count > 0) {
                    PlacesAutoCompleteAdapter.this.notifyDataSetChanged();
                } else {
                    PlacesAutoCompleteAdapter.this.notifyDataSetInvalidated();
                }
            }
        };
    }
}
