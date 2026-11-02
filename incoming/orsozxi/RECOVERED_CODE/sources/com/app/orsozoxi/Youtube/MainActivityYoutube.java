package com.app.orsozoxi.Youtube;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.GridLayoutManager;
import com.app.orsozoxi.Adapters.ExpandableListAdapter;
import com.app.orsozoxi.BaseActivity;
import com.app.orsozoxi.Others.Util;
import com.app.orsozoxi.R;
import com.app.orsozoxi.Slider;
import com.app.orsozoxi.Youtube.Adapters.PlaylistsUltimateRecyclerViewAdapter;
import com.app.orsozoxi.Youtube.Beans.GetPlaylistsResponse;
import com.app.orsozoxi.Youtube.Beans.PageInfo;
import com.app.orsozoxi.Youtube.Beans.PlaylistItem;
import com.app.orsozoxi.Youtube.Beans.PlaylistSnippetItem;
import com.app.orsozoxi.Youtube.Beans.ThumbItem;
import com.app.orsozoxi.Youtube.Beans.Thumbnails;
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
public class MainActivityYoutube extends BaseActivity {
    private UltimateRecyclerView mTonesUltimateRecyclerView;
    private TextView noConent_tv;
    private PlaylistsUltimateRecyclerViewAdapter playlistsAdapter;
    private ArrayList<PlaylistItem> playlistsList;
    private ImageView reloadImageView;
    private int total;
    private String url;
    private int start = 0;
    private int max = 50;
    private String token = null;
    private boolean isHistoryH120 = false;

    static /* synthetic */ int access$312(MainActivityYoutube mainActivityYoutube, int i) {
        int i2 = mainActivityYoutube.start + i;
        mainActivityYoutube.start = i2;
        return i2;
    }

    @Override // com.app.orsozoxi.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main_youtube_app);
        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());
        }
        this.reloadImageView = (ImageView) findViewById(R.id.iv_reload);
        TextView textView = (TextView) findViewById(R.id.tv_header_title);
        if (getIntent() != null && getIntent().hasExtra("isHistory")) {
            this.isHistoryH120 = getIntent().getBooleanExtra("isHistory", false);
        }
        if (this.isHistoryH120) {
            textView.setText("برنامج H120");
        } else {
            textView.setText("قناة اليوتيوب");
        }
        initView();
        Button button = (Button) findViewById(R.id.btn_slide);
        final SimpleSideDrawer simpleSideDrawer = new SimpleSideDrawer(this);
        simpleSideDrawer.setLeftBehindContentView(R.layout.left_menu_ex);
        button.setVisibility(0);
        Slider.prepareListData(this);
        ((ExpandableListView) findViewById(R.id.lvExp)).setAdapter(new ExpandableListAdapter(this, this, Slider.listDataHeader, Slider.listDataChild));
        button.setOnClickListener(new View.OnClickListener() { // from class: com.app.orsozoxi.Youtube.MainActivityYoutube.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                simpleSideDrawer.toggleLeftDrawer();
            }
        });
        findViewById(R.id.iv_reload).setOnClickListener(new View.OnClickListener() { // from class: com.app.orsozoxi.Youtube.MainActivityYoutube.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                RotateAnimation rotateAnimation = new RotateAnimation(0.0f, 360.0f, 1, 0.5f, 1, 0.5f);
                rotateAnimation.setDuration(500L);
                MainActivityYoutube.this.reloadImageView.startAnimation(rotateAnimation);
                MainActivityYoutube.this.getComedyVideos();
            }
        });
    }

    private void initView() {
        this.mTonesUltimateRecyclerView = (UltimateRecyclerView) findViewById(R.id.v_recycler);
        this.playlistsList = new ArrayList<>();
        this.noConent_tv = (TextView) findViewById(R.id.nocontent);
        this.total = 0;
        getComedyVideos();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getComedyVideos() {
        if (!Util.hasConnection(this)) {
            Util.showToast(this, R.string.no_connection);
            this.reloadImageView.setVisibility(0);
            return;
        }
        findViewById(R.id.iv_reload).setVisibility(8);
        findViewById(R.id.progress).setVisibility(0);
        if (this.isHistoryH120) {
            this.url = "https://www.googleapis.com/youtube/v3/playlists?part=snippet&channelId=UCAga3fiKOaUy0xVAl2f856Q&key=AIzaSyDwG5e0laBuPvmeVjjL6L84JaSUWVtjeIA&maxResults=" + this.max;
        } else {
            this.url = "https://www.googleapis.com/youtube/v3/playlists?part=snippet&channelId=UCU6j31zEB-Bo-1vDWlQOF2g&key=AIzaSyDwG5e0laBuPvmeVjjL6L84JaSUWVtjeIA&maxResults=" + this.max;
        }
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
            MainActivityYoutube.this.findViewById(R.id.progress).setVisibility(8);
        }

        private InputStream getStatusServiceRequest() {
            Log.d("val", "URL=" + MainActivityYoutube.this.url);
            HttpGet httpGet = new HttpGet(MainActivityYoutube.this.url);
            BasicHttpParams basicHttpParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(basicHttpParams, 30000);
            HttpConnectionParams.setSoTimeout(basicHttpParams, 30000);
            try {
                return new DefaultHttpClient(basicHttpParams).execute((HttpUriRequest) httpGet).getEntity().getContent();
            } catch (ClientProtocolException e) {
                MainActivityYoutube.this.reloadImageView.setVisibility(0);
                e.printStackTrace();
                return null;
            } catch (IOException e2) {
                MainActivityYoutube.this.reloadImageView.setVisibility(0);
                e2.printStackTrace();
                return null;
            }
        }

        private void getStatusResponse(InputStream inputStream) throws JSONException, IOException {
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
                GetPlaylistsResponse getPlaylistsResponse = new GetPlaylistsResponse();
                PageInfo pageInfo = new PageInfo();
                JSONObject jSONObject2 = jSONObject.getJSONObject("pageInfo");
                pageInfo.setResultsPerPage(jSONObject2.getInt("resultsPerPage"));
                pageInfo.setTotalResults(jSONObject2.getInt("totalResults"));
                getPlaylistsResponse.setPageInfo(pageInfo);
                getPlaylistsResponse.setEtag(jSONObject.getString("etag"));
                getPlaylistsResponse.setKind(jSONObject.getString("kind"));
                if (jSONObject.has("nextPageToken")) {
                    getPlaylistsResponse.setNextPageToken(jSONObject.getString("nextPageToken"));
                }
                JSONArray jSONArray = jSONObject.getJSONArray(FirebaseAnalytics.Param.ITEMS);
                PlaylistItem[] playlistItemArr = new PlaylistItem[jSONArray.length()];
                MainActivityYoutube.access$312(MainActivityYoutube.this, jSONArray.length());
                int i = 0;
                while (i < jSONArray.length()) {
                    PlaylistItem playlistItem = new PlaylistItem();
                    JSONObject jSONObject3 = jSONArray.getJSONObject(i);
                    playlistItem.setEtag(jSONObject3.getString("etag"));
                    playlistItem.setKind(jSONObject3.getString("kind"));
                    playlistItem.setId(jSONObject3.getString("id"));
                    JSONObject jSONObject4 = jSONObject3.getJSONObject("snippet");
                    PlaylistSnippetItem playlistSnippetItem = new PlaylistSnippetItem();
                    playlistSnippetItem.setChannelId(jSONObject4.getString(RemoteMessageConst.Notification.CHANNEL_ID));
                    playlistSnippetItem.setChannelTitle(jSONObject4.getString("title"));
                    playlistSnippetItem.setDescription(jSONObject4.getString("description"));
                    JSONObject jSONObject5 = jSONObject4.getJSONObject("thumbnails");
                    JSONObject jSONObject6 = jSONObject5.getJSONObject("default");
                    JSONObject jSONObject7 = jSONObject5.getJSONObject("medium");
                    Thumbnails thumbnails = new Thumbnails();
                    JSONArray jSONArray2 = jSONArray;
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
                    playlistSnippetItem.setThumbnails(thumbnails);
                    playlistItem.setSnippet(playlistSnippetItem);
                    playlistItemArr[i] = playlistItem;
                    i++;
                    jSONArray = jSONArray2;
                }
                getPlaylistsResponse.setItems(playlistItemArr);
                if (getPlaylistsResponse.getItems() != null && getPlaylistsResponse.getItems().length > 0) {
                    MainActivityYoutube.this.total = getPlaylistsResponse.getPageInfo().getTotalResults();
                    if (MainActivityYoutube.this.start == 0) {
                        MainActivityYoutube.this.playlistsList.clear();
                    }
                    for (int i2 = 0; i2 < getPlaylistsResponse.getItems().length; i2++) {
                        MainActivityYoutube.this.playlistsList.add(getPlaylistsResponse.getItems()[i2]);
                    }
                    if (getPlaylistsResponse.getNextPageToken() == null) {
                        MainActivityYoutube.this.token = null;
                    } else {
                        MainActivityYoutube.this.token = getPlaylistsResponse.getNextPageToken();
                    }
                    MainActivityYoutube.this.setAdapter();
                    MainActivityYoutube.this.playlistsAdapter.notifyDataSetChanged();
                    MainActivityYoutube.this.reloadImageView.setVisibility(8);
                } else if (getPlaylistsResponse.getItems().length == 0) {
                    MainActivityYoutube.this.noConent_tv.setVisibility(0);
                    MainActivityYoutube.this.reloadImageView.setVisibility(0);
                } else {
                    Util.showToast(MainActivityYoutube.this, R.string.fail_response);
                    MainActivityYoutube.this.reloadImageView.setVisibility(0);
                }
                Log.d("value", jSONObject.getString("etag"));
            } catch (JSONException e2) {
                MainActivityYoutube.this.reloadImageView.setVisibility(0);
                e2.printStackTrace();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setAdapter() {
        PlaylistsUltimateRecyclerViewAdapter playlistsUltimateRecyclerViewAdapter = new PlaylistsUltimateRecyclerViewAdapter(this, this.playlistsList);
        this.playlistsAdapter = playlistsUltimateRecyclerViewAdapter;
        this.mTonesUltimateRecyclerView.setAdapter((UltimateViewAdapter) playlistsUltimateRecyclerViewAdapter);
        this.mTonesUltimateRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        this.mTonesUltimateRecyclerView.setHasFixedSize(true);
        this.mTonesUltimateRecyclerView.setOnLoadMoreListener(new UltimateRecyclerView.OnLoadMoreListener() { // from class: com.app.orsozoxi.Youtube.MainActivityYoutube.3
            @Override // com.marshalchen.ultimaterecyclerview.UltimateRecyclerView.OnLoadMoreListener
            public void loadMore(int i, int i2) {
                if (MainActivityYoutube.this.start < MainActivityYoutube.this.total) {
                    MainActivityYoutube.this.getComedyVideos();
                } else {
                    MainActivityYoutube.this.mTonesUltimateRecyclerView.disableLoadmore();
                }
            }
        });
    }
}
