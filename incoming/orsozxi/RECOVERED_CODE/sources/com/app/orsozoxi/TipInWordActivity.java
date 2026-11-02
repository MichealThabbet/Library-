package com.app.orsozoxi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.view.InputDeviceCompat;
import androidx.core.view.ViewCompat;
import com.app.orsozoxi.Adapters.ExpandableListAdapter;
import com.app.orsozoxi.Beans.CasheFeeds;
import com.app.orsozoxi.Beans.RSSFeed;
import com.app.orsozoxi.Beans.WhiteBackgroundActivition;
import com.app.orsozoxi.Others.AlertDialogManager;
import com.app.orsozoxi.Others.ConnectionDetector;
import com.app.orsozoxi.Others.NewsFeedParserothers;
import com.app.orsozoxi.Others.SavePrefs;
import com.app.orsozoxi.Others.checksim;
import com.app.orsozoxi.Views.UrlImageView;
import com.navdrawer.SimpleSideDrawer;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.squareup.picasso.Picasso;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/* loaded from: classes.dex */
public class TipInWordActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    private static final String TOPSTORIES = "https://www.orsozoxi.net/archives/category/media/audio_library/fr_marcos_zaky/info-in-word/feed";
    AlertDialogManager alert = new AlertDialogManager();
    ConnectionDetector cd;
    private GestureDetector gestureDetector;
    private NewsFeedParserothers mNewsFeeder;
    private RssAdapter mRssAdap;
    public List<RSSFeed> mRssFeedList;
    private ListView mRssListView;
    DisplayImageOptions options;

    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        return true;
    }

    @Override // com.app.orsozoxi.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_akwal);
        ((TextView) findViewById(R.id.tv_header_title)).setText(getString(R.string.tip_in_word));
        getWindow().addFlags(128);
        new checksim();
        this.options = new DisplayImageOptions.Builder().displayer(new RoundedBitmapDisplayer(5)).cacheInMemory().imageScaleType(ImageScaleType.EXACTLY).cacheOnDisc().showImageForEmptyUri(R.drawable.thumb).showImageOnFail(R.drawable.thumb).showStubImage(R.drawable.thumb).bitmapConfig(Bitmap.Config.RGB_565).build();
        WhiteBackgroundActivition whiteBackgroundActivition = (WhiteBackgroundActivition) new SavePrefs((Activity) this, (Class<?>) WhiteBackgroundActivition.class).load();
        if (whiteBackgroundActivition == null || !whiteBackgroundActivition.isWhiteBackground()) {
            getWindow().setBackgroundDrawableResource(R.drawable.background);
        }
        Button button = (Button) findViewById(R.id.btn_slide);
        final SimpleSideDrawer simpleSideDrawer = new SimpleSideDrawer(this);
        simpleSideDrawer.setLeftBehindContentView(R.layout.left_menu_ex);
        button.setVisibility(0);
        Slider.prepareListData(this);
        ((ExpandableListView) findViewById(R.id.lvExp)).setAdapter(new ExpandableListAdapter(this, this, Slider.listDataHeader, Slider.listDataChild));
        button.setOnClickListener(new View.OnClickListener() { // from class: com.app.orsozoxi.TipInWordActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                simpleSideDrawer.toggleLeftDrawer();
            }
        });
        try {
            this.mRssListView = (ListView) findViewById(R.id.rss_list_view);
            this.mRssFeedList = new ArrayList();
            CasheFeeds casheFeeds = (CasheFeeds) new SavePrefs((Activity) this, (Class<?>) CasheFeeds.class).load();
            if (casheFeeds != null && casheFeeds.getTipInWordFeeds() != null && casheFeeds.getTipInWordFeeds().size() > 0) {
                this.mRssAdap = new RssAdapter(this, R.layout.rss_list_item, casheFeeds.getTipInWordFeeds());
                this.mRssFeedList.addAll(casheFeeds.getTipInWordFeeds());
                this.mRssListView.setAdapter((ListAdapter) this.mRssAdap);
            }
            if (!checkinternet()) {
                Toast.makeText(getApplicationContext(), getString(R.string.no_internet), 1).show();
            } else {
                getData(TOPSTORIES);
            }
            this.mRssListView.setOnItemClickListener(this);
            this.mRssListView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.app.orsozoxi.TipInWordActivity.2
                @Override // android.widget.AdapterView.OnItemClickListener
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                    Intent intent = new Intent();
                    intent.setClass(TipInWordActivity.this, TipInWordDetailsActivity.class);
                    intent.putExtra("title", TipInWordActivity.this.mRssFeedList.get(i).getTitle());
                    intent.putExtra("pubDate", TipInWordActivity.this.mRssFeedList.get(i).getPubDate());
                    intent.putExtra("feedburner", TipInWordActivity.this.mRssFeedList.get(i).getFeedburnerOrigLink());
                    intent.putExtra("guid", TipInWordActivity.this.mRssFeedList.get(i).getGuid());
                    intent.putExtra("linkk", TipInWordActivity.this.mRssFeedList.get(i).getLink());
                    intent.putExtra("category", TipInWordActivity.this.mRssFeedList.get(i).getCategory());
                    intent.putExtra("url", TipInWordActivity.TOPSTORIES);
                    TipInWordActivity.this.startActivity(intent);
                }
            });
        } catch (Exception unused) {
            Log.e("Error", "Loading exception");
        }
    }

    private class RssAdapter extends ArrayAdapter<RSSFeed> {
        ArrayList<Integer> list;
        private List<RSSFeed> rssFeedLst;

        public Drawable LoadImageFromWebOperations(String str) {
            try {
                return Drawable.createFromStream((InputStream) new URL(str).getContent(), "src name");
            } catch (Exception unused) {
                return null;
            }
        }

        public RssAdapter(Context context, int i, List<RSSFeed> list) {
            super(context, i, list);
            this.list = new ArrayList<>();
            this.rssFeedLst = list;
        }

        @Override // android.widget.ArrayAdapter, android.widget.Adapter
        public View getView(int i, View view, ViewGroup viewGroup) {
            String str;
            if (view == null) {
                view = View.inflate(TipInWordActivity.this, R.layout.list_row_tips, null);
            }
            RSSFeed rSSFeed = this.rssFeedLst.get(i);
            RssHolder rssHolder = new RssHolder();
            rssHolder.description = (TextView) view.findViewById(R.id.desc10);
            rssHolder.title = (TextView) view.findViewById(R.id.site_title);
            rssHolder.pubDate = (TextView) view.findViewById(R.id.datee);
            rssHolder.src = (ImageView) view.findViewById(R.id.img12);
            new checksim();
            WhiteBackgroundActivition whiteBackgroundActivition = (WhiteBackgroundActivition) new SavePrefs((Activity) TipInWordActivity.this, (Class<?>) WhiteBackgroundActivition.class).load();
            if (whiteBackgroundActivition == null || !whiteBackgroundActivition.isWhiteBackground()) {
                rssHolder.description.setTextColor(-1);
                rssHolder.title.setTextColor(Color.parseColor("#FF6600"));
                rssHolder.pubDate.setTextColor(InputDeviceCompat.SOURCE_ANY);
            } else {
                rssHolder.description.setTextColor(ViewCompat.MEASURED_STATE_MASK);
                rssHolder.title.setTextColor(ViewCompat.MEASURED_STATE_MASK);
                rssHolder.pubDate.setTextColor(-16776961);
            }
            rssHolder.title.setText(rSSFeed.getTitle());
            rSSFeed.getPubDate().substring(0, 16);
            String[] strArrSplit = rSSFeed.getFeedburnerOrigLink().split(" ");
            if (strArrSplit.length > 10) {
                String str2 = "";
                for (int i2 = 0; i2 < 10; i2++) {
                    str2 = str2 + strArrSplit[i2] + " ";
                }
                str = str2 + "...المزيد";
            } else {
                str = "";
            }
            if (str.equals("")) {
                rssHolder.description.setVisibility(8);
            } else {
                rssHolder.description.setVisibility(0);
                rssHolder.description.setText(str);
            }
            Picasso.with(TipInWordActivity.this).load(rSSFeed.getGuid()).fit().placeholder(R.drawable.thumb_m).into(rssHolder.src);
            return view;
        }
    }

    static class RssHolder {
        TextView category;
        TextView description;
        TextView feedburner;
        TextView guid;
        TextView pubDate;
        ImageView src;
        UrlImageView thumb;
        TextView title;

        RssHolder() {
        }
    }

    private void getData(final String str) {
        findViewById(R.id.progress_smooth).setVisibility(0);
        Toast.makeText(getApplicationContext(), R.string.feeds_updating, 1).show();
        Observable.fromCallable(new Callable() { // from class: com.app.orsozoxi.-$$Lambda$TipInWordActivity$HbDMJKpY09il49b3s0lfUC5NQ2s
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return this.f$0.lambda$getData$0$TipInWordActivity(str);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.app.orsozoxi.-$$Lambda$TipInWordActivity$rhx7owCz7exOrxjfb1RKA0jVlHU
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.lambda$getData$1$TipInWordActivity((List) obj);
            }
        });
    }

    public /* synthetic */ List lambda$getData$0$TipInWordActivity(String str) throws Exception {
        NewsFeedParserothers newsFeedParserothers = new NewsFeedParserothers(str);
        this.mNewsFeeder = newsFeedParserothers;
        return newsFeedParserothers.parse();
    }

    public /* synthetic */ void lambda$getData$1$TipInWordActivity(final List list) throws Exception {
        findViewById(R.id.progress_smooth).setVisibility(8);
        runOnUiThread(new Runnable() { // from class: com.app.orsozoxi.TipInWordActivity.3
            @Override // java.lang.Runnable
            public void run() {
                List list2;
                List list3 = list;
                if (list3 != null && list3.size() > 0 && TipInWordActivity.this.mRssAdap != null) {
                    TipInWordActivity.this.mRssAdap.clear();
                }
                TipInWordActivity tipInWordActivity = TipInWordActivity.this;
                TipInWordActivity tipInWordActivity2 = TipInWordActivity.this;
                tipInWordActivity.mRssAdap = tipInWordActivity2.new RssAdapter(tipInWordActivity2, R.layout.rss_list_item, list);
                List list4 = list;
                if (list4 != null && list4.size() > 0) {
                    TipInWordActivity.this.mRssFeedList.clear();
                    TipInWordActivity.this.mRssFeedList.addAll(list);
                }
                if (TipInWordActivity.this.mRssAdap == null || (list2 = list) == null || list2.size() <= 0) {
                    return;
                }
                TipInWordActivity.this.mRssListView.setAdapter((ListAdapter) TipInWordActivity.this.mRssAdap);
                CasheFeeds casheFeeds = (CasheFeeds) new SavePrefs((Activity) TipInWordActivity.this, (Class<?>) CasheFeeds.class).load();
                if (casheFeeds == null) {
                    casheFeeds = new CasheFeeds();
                }
                casheFeeds.setTipInWordFeeds(list);
                new SavePrefs((Activity) TipInWordActivity.this, (Class<?>) CasheFeeds.class).save(casheFeeds);
            }
        });
    }

    public boolean checkinternet() {
        ConnectionDetector connectionDetector = new ConnectionDetector(getApplicationContext());
        this.cd = connectionDetector;
        return connectionDetector.isConnectingToInternet();
    }
}
