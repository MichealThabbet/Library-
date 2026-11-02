package com.app.piapostolos.Youtube;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.GridLayoutManager;
import com.app.piapostolos.Adapters.ExpandableListAdapter;
import com.app.piapostolos.BaseActivity;
import com.app.piapostolos.Others.Util;
import com.app.piapostolos.R;
import com.app.piapostolos.Slider;
import com.app.piapostolos.Youtube.Adapters.PlaylistUltimateRecyclerViewAdapter;
import com.app.piapostolos.Youtube.Beans.GetPlaylistItemsResponse;
import com.app.piapostolos.Youtube.Beans.PageInfo;
import com.app.piapostolos.Youtube.Beans.PlaylistItemDetails;
import com.app.piapostolos.Youtube.Beans.PlaylistSnippetItemDetails;
import com.app.piapostolos.Youtube.Beans.Resource;
import com.app.piapostolos.Youtube.Beans.ThumbItem;
import com.app.piapostolos.Youtube.Beans.Thumbnails;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.huawei.hms.push.constant.RemoteMessageConst;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;
import com.navdrawer.SimpleSideDrawer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class PlayListItemsActivity extends BaseActivity {
    private UltimateRecyclerView mTonesUltimateRecyclerView;
    private TextView noConent_tv;
    private TextView noContent_tv;
    private PlaylistUltimateRecyclerViewAdapter playlistItemsAdapter;
    private ArrayList<PlaylistItemDetails> playlistItemsList;
    private ImageView reloadImageView;
    private int total;
    private String url;
    private int start = 0;
    private int max = 50;
    private String playlistId = "";
    private String token = null;

    static /* synthetic */ int access$312(PlayListItemsActivity playListItemsActivity, int i) {
        int i2 = playListItemsActivity.start + i;
        playListItemsActivity.start = i2;
        return i2;
    }

    @Override // com.app.piapostolos.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_playlist_items);
        initView();
        TextView textView = (TextView) findViewById(R.id.tv_header_title);
        Button button = (Button) findViewById(R.id.btn_slide);
        final SimpleSideDrawer simpleSideDrawer = new SimpleSideDrawer(this);
        simpleSideDrawer.setLeftBehindContentView(R.layout.left_menu_ex);
        button.setVisibility(0);
        Slider.prepareListData(this);
        ((ExpandableListView) findViewById(R.id.lvExp)).setAdapter(new ExpandableListAdapter(this, this, Slider.listDataHeader, Slider.listDataChild));
        button.setOnClickListener(new View.OnClickListener() { // from class: com.app.piapostolos.Youtube.PlayListItemsActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                simpleSideDrawer.toggleLeftDrawer();
            }
        });
        textView.setText(getIntent().getStringExtra("title"));
        this.playlistId = getIntent().getStringExtra("playlistId");
        this.reloadImageView = (ImageView) findViewById(R.id.iv_reload);
        Log.d("val", "playlistId=" + this.playlistId);
        findViewById(R.id.iv_reload).setOnClickListener(new View.OnClickListener() { // from class: com.app.piapostolos.Youtube.PlayListItemsActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                RotateAnimation rotateAnimation = new RotateAnimation(0.0f, 360.0f, 1, 0.5f, 1, 0.5f);
                rotateAnimation.setDuration(500L);
                PlayListItemsActivity.this.reloadImageView.startAnimation(rotateAnimation);
                PlayListItemsActivity.this.getBBImages();
            }
        });
    }

    private void initView() {
        this.mTonesUltimateRecyclerView = (UltimateRecyclerView) findViewById(R.id.v_recycler);
        this.playlistItemsList = new ArrayList<>();
        this.noContent_tv = (TextView) findViewById(R.id.nocontent);
        this.total = 0;
        getBBImages();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getBBImages() {
        if (!Util.hasConnection(this)) {
            Util.showToast(this, R.string.no_connection);
            this.reloadImageView.setVisibility(0);
            return;
        }
        findViewById(R.id.iv_reload).setVisibility(8);
        findViewById(R.id.progress).setVisibility(0);
        this.url = "http://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId=" + getIntent().getStringExtra("playlistId") + "&key=AIzaSyDwG5e0laBuPvmeVjjL6L84JaSUWVtjeIA&maxResults=" + this.max;
        this.url = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId=" + getIntent().getStringExtra("playlistId") + "&key=AIzaSyDwG5e0laBuPvmeVjjL6L84JaSUWVtjeIA&maxResults=" + this.max;
        if (this.token != null) {
            this.url += "&pageToken=" + this.token;
        }
        new GetRecordStateAsyncTask().execute(new String[0]);
    }

    class GetRecordStateAsyncTask extends AsyncTask<String, Void, InputStream> {
        @Override // android.os.AsyncTask
        protected void onPreExecute() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onProgressUpdate(Void... voidArr) {
        }

        public GetRecordStateAsyncTask() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public InputStream doInBackground(String... strArr) {
            return getStatusServiceRequest();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(InputStream inputStream) throws JSONException, IOException {
            getStatusResponse(inputStream);
            PlayListItemsActivity.this.findViewById(R.id.progress).setVisibility(8);
        }

        private InputStream getStatusServiceRequest() {
            Log.d("val", "URL=" + PlayListItemsActivity.this.url);
            HttpGet httpGet = new HttpGet(PlayListItemsActivity.this.url);
            BasicHttpParams basicHttpParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(basicHttpParams, 30000);
            HttpConnectionParams.setSoTimeout(basicHttpParams, 30000);
            try {
                return new DefaultHttpClient(basicHttpParams).execute((HttpUriRequest) httpGet).getEntity().getContent();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
                PlayListItemsActivity.this.reloadImageView.setVisibility(0);
                return null;
            } catch (IOException e2) {
                e2.printStackTrace();
                PlayListItemsActivity.this.reloadImageView.setVisibility(0);
                return null;
            }
        }

        private void getStatusResponse(InputStream inputStream) throws JSONException, IOException {
            JSONArray jSONArray;
            String str;
            String str2;
            String str3 = "kind";
            String str4 = "title";
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb = new StringBuilder();
            while (true) {
                try {
                    String line = bufferedReader.readLine();
                    if (line == null) {
                        break;
                    } else {
                        sb.append(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Log.d("value", "val=" + sb.toString());
            try {
                JSONObject jSONObject = new JSONObject(sb.toString());
                GetPlaylistItemsResponse getPlaylistItemsResponse = new GetPlaylistItemsResponse();
                PageInfo pageInfo = new PageInfo();
                JSONObject jSONObject2 = jSONObject.getJSONObject("pageInfo");
                pageInfo.setResultsPerPage(jSONObject2.getInt("resultsPerPage"));
                pageInfo.setTotalResults(jSONObject2.getInt("totalResults"));
                getPlaylistItemsResponse.setPageInfo(pageInfo);
                getPlaylistItemsResponse.setEtag(jSONObject.getString("etag"));
                getPlaylistItemsResponse.setKind(jSONObject.getString("kind"));
                if (jSONObject.has("nextPageToken")) {
                    getPlaylistItemsResponse.setNextPageToken(jSONObject.getString("nextPageToken"));
                }
                JSONArray jSONArray2 = jSONObject.getJSONArray(FirebaseAnalytics.Param.ITEMS);
                ArrayList<PlaylistItemDetails> arrayList = new ArrayList<>();
                PlayListItemsActivity.access$312(PlayListItemsActivity.this, jSONArray2.length());
                int i = 0;
                while (i < jSONArray2.length()) {
                    PlaylistItemDetails playlistItemDetails = new PlaylistItemDetails();
                    JSONObject jSONObject3 = jSONArray2.getJSONObject(i);
                    playlistItemDetails.setEtag(jSONObject3.getString("etag"));
                    playlistItemDetails.setKind(jSONObject3.getString(str3));
                    playlistItemDetails.setId(jSONObject3.getString("id"));
                    JSONObject jSONObject4 = jSONObject3.getJSONObject("snippet");
                    if (TextUtils.isEmpty(jSONObject4.getString(str4)) || jSONObject4.getString(str4).equals("Private video")) {
                        jSONArray = jSONArray2;
                        str = str3;
                        str2 = str4;
                    } else {
                        PlaylistSnippetItemDetails playlistSnippetItemDetails = new PlaylistSnippetItemDetails();
                        playlistSnippetItemDetails.setChannelId(jSONObject4.getString(RemoteMessageConst.Notification.CHANNEL_ID));
                        playlistSnippetItemDetails.setTitle(jSONObject4.getString(str4));
                        playlistSnippetItemDetails.setChannelTitle(jSONObject4.getString(str4));
                        playlistSnippetItemDetails.setDescription(jSONObject4.getString("description"));
                        JSONObject jSONObject5 = jSONObject4.getJSONObject("thumbnails");
                        JSONObject jSONObject6 = jSONObject5.getJSONObject("default");
                        jSONArray = jSONArray2;
                        JSONObject jSONObject7 = jSONObject5.getJSONObject("medium");
                        str = str3;
                        Thumbnails thumbnails = new Thumbnails();
                        str2 = str4;
                        ThumbItem thumbItem = new ThumbItem();
                        thumbItem.setUrl(jSONObject6.getString("url"));
                        thumbnails.setDefaultItem(thumbItem);
                        ThumbItem thumbItem2 = new ThumbItem();
                        thumbItem2.setUrl(jSONObject7.getString("url"));
                        thumbnails.setMedium(thumbItem2);
                        JSONObject jSONObject8 = jSONObject5.getJSONObject("high");
                        ThumbItem thumbItem3 = new ThumbItem();
                        thumbItem3.setUrl(jSONObject8.getString("url"));
                        thumbnails.setHigh(thumbItem3);
                        playlistSnippetItemDetails.setThumbnails(thumbnails);
                        Resource resource = new Resource();
                        resource.setVideoId(jSONObject4.getJSONObject("resourceId").getString("videoId"));
                        playlistSnippetItemDetails.setResourceId(resource);
                        playlistItemDetails.setSnippet(playlistSnippetItemDetails);
                        arrayList.add(playlistItemDetails);
                    }
                    i++;
                    jSONArray2 = jSONArray;
                    str3 = str;
                    str4 = str2;
                }
                getPlaylistItemsResponse.setItems(arrayList);
                if (getPlaylistItemsResponse.getItems() != null && getPlaylistItemsResponse.getItems().size() > 0) {
                    PlayListItemsActivity.this.total = getPlaylistItemsResponse.getPageInfo().getTotalResults();
                    if (PlayListItemsActivity.this.start == 0) {
                        PlayListItemsActivity.this.playlistItemsList.clear();
                    }
                    for (int i2 = 0; i2 < getPlaylistItemsResponse.getItems().size(); i2++) {
                        PlayListItemsActivity.this.playlistItemsList.add(getPlaylistItemsResponse.getItems().get(i2));
                    }
                    if (getPlaylistItemsResponse.getNextPageToken() == null) {
                        PlayListItemsActivity.this.token = null;
                    } else {
                        PlayListItemsActivity.this.token = getPlaylistItemsResponse.getNextPageToken();
                    }
                    PlayListItemsActivity.this.setAdapter();
                    PlayListItemsActivity.this.playlistItemsAdapter.notifyDataSetChanged();
                    PlayListItemsActivity.this.reloadImageView.setVisibility(8);
                } else if (getPlaylistItemsResponse.getItems().size() == 0) {
                    PlayListItemsActivity.this.reloadImageView.setVisibility(0);
                } else {
                    Util.showToast(PlayListItemsActivity.this, R.string.fail_response);
                    PlayListItemsActivity.this.reloadImageView.setVisibility(0);
                }
                Log.d("value", jSONObject.getString("etag"));
            } catch (JSONException e2) {
                PlayListItemsActivity.this.reloadImageView.setVisibility(0);
                e2.printStackTrace();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setAdapter() {
        PlaylistUltimateRecyclerViewAdapter playlistUltimateRecyclerViewAdapter = new PlaylistUltimateRecyclerViewAdapter(this, this.playlistItemsList);
        this.playlistItemsAdapter = playlistUltimateRecyclerViewAdapter;
        this.mTonesUltimateRecyclerView.setAdapter((UltimateViewAdapter) playlistUltimateRecyclerViewAdapter);
        this.mTonesUltimateRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        this.mTonesUltimateRecyclerView.setHasFixedSize(true);
        this.mTonesUltimateRecyclerView.setOnLoadMoreListener(new UltimateRecyclerView.OnLoadMoreListener() { // from class: com.app.piapostolos.Youtube.PlayListItemsActivity.3
            @Override // com.marshalchen.ultimaterecyclerview.UltimateRecyclerView.OnLoadMoreListener
            public void loadMore(int i, int i2) {
                if (PlayListItemsActivity.this.start < PlayListItemsActivity.this.total) {
                    PlayListItemsActivity.this.getBBImages();
                } else {
                    PlayListItemsActivity.this.mTonesUltimateRecyclerView.disableLoadmore();
                }
            }
        });
    }
}
