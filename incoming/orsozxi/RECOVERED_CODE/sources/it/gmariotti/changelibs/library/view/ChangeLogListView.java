package it.gmariotti.changelibs.library.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import it.gmariotti.changelibs.R;
import it.gmariotti.changelibs.library.Constants;
import it.gmariotti.changelibs.library.Util;
import it.gmariotti.changelibs.library.internal.ChangeLog;
import it.gmariotti.changelibs.library.internal.ChangeLogAdapter;
import it.gmariotti.changelibs.library.internal.ChangeLogRow;
import it.gmariotti.changelibs.library.parser.XmlParser;
import java.util.Iterator;

/* loaded from: classes2.dex */
public class ChangeLogListView extends ListView implements AdapterView.OnItemClickListener {
    protected static String TAG = "ChangeLogListView";
    protected ChangeLogAdapter mAdapter;
    protected int mChangeLogFileResourceId;
    protected String mChangeLogFileResourceUrl;
    protected int mRowHeaderLayoutId;
    protected int mRowLayoutId;

    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
    }

    public ChangeLogListView(Context context) {
        this(context, null);
    }

    public ChangeLogListView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ChangeLogListView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mRowLayoutId = Constants.mRowLayoutId;
        this.mRowHeaderLayoutId = Constants.mRowHeaderLayoutId;
        this.mChangeLogFileResourceId = Constants.mChangeLogFileResourceId;
        this.mChangeLogFileResourceUrl = null;
        if (Build.VERSION.SDK_INT >= 21) {
            setNestedScrollingEnabled(true);
        }
        init(attributeSet, i);
    }

    protected void init(AttributeSet attributeSet, int i) {
        initAttrs(attributeSet, i);
        initAdapter();
        setDividerHeight(0);
    }

    protected void initAttrs(AttributeSet attributeSet, int i) {
        TypedArray typedArrayObtainStyledAttributes = getContext().getTheme().obtainStyledAttributes(attributeSet, R.styleable.ChangeLogListView, i, i);
        try {
            this.mRowLayoutId = typedArrayObtainStyledAttributes.getResourceId(R.styleable.ChangeLogListView_rowLayoutId, this.mRowLayoutId);
            this.mRowHeaderLayoutId = typedArrayObtainStyledAttributes.getResourceId(R.styleable.ChangeLogListView_rowHeaderLayoutId, this.mRowHeaderLayoutId);
            this.mChangeLogFileResourceId = typedArrayObtainStyledAttributes.getResourceId(R.styleable.ChangeLogListView_changeLogFileResourceId, this.mChangeLogFileResourceId);
            this.mChangeLogFileResourceUrl = typedArrayObtainStyledAttributes.getString(R.styleable.ChangeLogListView_changeLogFileResourceUrl);
        } finally {
            typedArrayObtainStyledAttributes.recycle();
        }
    }

    protected void initAdapter() {
        XmlParser xmlParser;
        try {
            if (this.mChangeLogFileResourceUrl != null) {
                xmlParser = new XmlParser(getContext(), this.mChangeLogFileResourceUrl);
            } else {
                xmlParser = new XmlParser(getContext(), this.mChangeLogFileResourceId);
            }
            ChangeLogAdapter changeLogAdapter = new ChangeLogAdapter(getContext(), new ChangeLog().getRows());
            this.mAdapter = changeLogAdapter;
            changeLogAdapter.setmRowLayoutId(this.mRowLayoutId);
            this.mAdapter.setmRowHeaderLayoutId(this.mRowHeaderLayoutId);
            if (this.mChangeLogFileResourceUrl == null || (this.mChangeLogFileResourceUrl != null && Util.isConnected(getContext()))) {
                new ParseAsyncTask(this.mAdapter, xmlParser).execute(new Void[0]);
            } else {
                Toast.makeText(getContext(), R.string.changelog_internal_error_internet_connection, 1).show();
            }
            setAdapter(this.mAdapter);
        } catch (Exception e) {
            Log.e(TAG, getResources().getString(R.string.changelog_internal_error_parsing), e);
        }
    }

    protected class ParseAsyncTask extends AsyncTask<Void, Void, ChangeLog> {
        private ChangeLogAdapter mAdapter;
        private XmlParser mParse;

        public ParseAsyncTask(ChangeLogAdapter changeLogAdapter, XmlParser xmlParser) {
            this.mAdapter = changeLogAdapter;
            this.mParse = xmlParser;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public ChangeLog doInBackground(Void... voidArr) {
            try {
                if (this.mParse != null) {
                    return this.mParse.readChangeLogFile();
                }
                return null;
            } catch (Exception e) {
                Log.e(ChangeLogListView.TAG, ChangeLogListView.this.getResources().getString(R.string.changelog_internal_error_parsing), e);
                return null;
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(ChangeLog changeLog) {
            if (changeLog != null) {
                if (Build.VERSION.SDK_INT >= 11) {
                    this.mAdapter.addAll(changeLog.getRows());
                } else if (changeLog.getRows() != null) {
                    Iterator<ChangeLogRow> it2 = changeLog.getRows().iterator();
                    while (it2.hasNext()) {
                        this.mAdapter.add(it2.next());
                    }
                }
                this.mAdapter.notifyDataSetChanged();
            }
        }
    }

    public void setAdapter(ChangeLogAdapter changeLogAdapter) {
        super.setAdapter((ListAdapter) changeLogAdapter);
    }
}
