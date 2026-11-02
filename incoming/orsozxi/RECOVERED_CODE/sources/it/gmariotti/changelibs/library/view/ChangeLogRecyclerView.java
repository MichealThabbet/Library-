package it.gmariotti.changelibs.library.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import it.gmariotti.changelibs.R;
import it.gmariotti.changelibs.library.Constants;
import it.gmariotti.changelibs.library.Util;
import it.gmariotti.changelibs.library.internal.ChangeLog;
import it.gmariotti.changelibs.library.internal.ChangeLogRecyclerViewAdapter;
import it.gmariotti.changelibs.library.parser.XmlParser;

/* loaded from: classes2.dex */
public class ChangeLogRecyclerView extends RecyclerView {
    protected static String TAG = "ChangeLogRecyclerView";
    protected ChangeLogRecyclerViewAdapter mAdapter;
    protected int mChangeLogFileResourceId;
    protected String mChangeLogFileResourceUrl;
    protected int mRowHeaderLayoutId;
    protected int mRowLayoutId;

    public ChangeLogRecyclerView(Context context) {
        this(context, null);
    }

    public ChangeLogRecyclerView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ChangeLogRecyclerView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mRowLayoutId = Constants.mRowLayoutId;
        this.mRowHeaderLayoutId = Constants.mRowHeaderLayoutId;
        this.mChangeLogFileResourceId = Constants.mChangeLogFileResourceId;
        this.mChangeLogFileResourceUrl = null;
        init(attributeSet, i);
    }

    protected void init(AttributeSet attributeSet, int i) {
        initAttrs(attributeSet, i);
        initAdapter();
        initLayoutManager();
    }

    protected void initLayoutManager() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(1);
        setLayoutManager(linearLayoutManager);
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
            ChangeLogRecyclerViewAdapter changeLogRecyclerViewAdapter = new ChangeLogRecyclerViewAdapter(getContext(), new ChangeLog().getRows());
            this.mAdapter = changeLogRecyclerViewAdapter;
            changeLogRecyclerViewAdapter.setRowLayoutId(this.mRowLayoutId);
            this.mAdapter.setRowHeaderLayoutId(this.mRowHeaderLayoutId);
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
        private ChangeLogRecyclerViewAdapter mAdapter;
        private XmlParser mParse;

        public ParseAsyncTask(ChangeLogRecyclerViewAdapter changeLogRecyclerViewAdapter, XmlParser xmlParser) {
            this.mAdapter = changeLogRecyclerViewAdapter;
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
                Log.e(ChangeLogRecyclerView.TAG, ChangeLogRecyclerView.this.getResources().getString(R.string.changelog_internal_error_parsing), e);
                return null;
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(ChangeLog changeLog) {
            if (changeLog != null) {
                this.mAdapter.add(changeLog.getRows());
            }
        }
    }
}
