package com.app.piapostolos;

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
import com.app.piapostolos.Adapters.ExpandableListAdapter;
import com.app.piapostolos.Beans.CasheFeeds;
import com.app.piapostolos.Beans.RSSFeed;
import com.app.piapostolos.Beans.WhiteBackgroundActivition;
import com.app.piapostolos.Others.AlertDialogManager;
import com.app.piapostolos.Others.ConnectionDetector;
import com.app.piapostolos.Others.NewsFeedParserothers;
import com.app.piapostolos.Others.SavePrefs;
import com.app.piapostolos.Others.checksim;
import com.navdrawer.SimpleSideDrawer;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
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
public class MainActivityCards extends BaseActivity implements AdapterView.OnItemClickListener {
    private static String TOPSTORIES = "";
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

    @Override // com.app.piapostolos.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_akwal);
        this.options = new DisplayImageOptions.Builder().displayer(new RoundedBitmapDisplayer(5)).cacheInMemory().imageScaleType(ImageScaleType.EXACTLY).cacheOnDisc().showImageForEmptyUri(R.drawable.thumb).showImageOnFail(R.drawable.thumb).showStubImage(R.drawable.thumb).bitmapConfig(Bitmap.Config.RGB_565).build();
        TextView textView = (TextView) findViewById(R.id.tv_header_title);
        TOPSTORIES = "https://www.orsozoxi.net/archives/category/cards/feed";
        textView.setText(R.string.eids_cards);
        getWindow().addFlags(128);
        new checksim();
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
        button.setOnClickListener(new View.OnClickListener() { // from class: com.app.piapostolos.MainActivityCards.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                simpleSideDrawer.toggleLeftDrawer();
            }
        });
        try {
            this.mRssListView = (ListView) findViewById(R.id.rss_list_view);
            this.mRssFeedList = new ArrayList();
            CasheFeeds casheFeeds = (CasheFeeds) new SavePrefs((Activity) this, (Class<?>) CasheFeeds.class).load();
            if (casheFeeds != null && casheFeeds.getCardsFeeds() != null && casheFeeds.getCardsFeeds().size() > 0) {
                this.mRssAdap = new RssAdapter(this, R.layout.rss_list_item, casheFeeds.getCardsFeeds());
                this.mRssFeedList.addAll(casheFeeds.getCardsFeeds());
                this.mRssListView.setAdapter((ListAdapter) this.mRssAdap);
            }
            if (!checkinternet()) {
                Toast.makeText(getApplicationContext(), R.string.no_internet, 1).show();
            } else {
                doRssFeedTask(TOPSTORIES);
            }
            this.mRssListView.setOnItemClickListener(this);
            this.mRssListView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.app.piapostolos.MainActivityCards.2
                @Override // android.widget.AdapterView.OnItemClickListener
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                    Intent intent = new Intent();
                    intent.setClass(MainActivityCards.this, CardsDetailsActivity.class);
                    intent.putExtra("title", MainActivityCards.this.mRssFeedList.get(i).getTitle());
                    intent.putExtra("pubDate", MainActivityCards.this.mRssFeedList.get(i).getPubDate());
                    intent.putExtra("feedburner", MainActivityCards.this.mRssFeedList.get(i).getFeedburnerOrigLink());
                    intent.putExtra("guid", MainActivityCards.this.mRssFeedList.get(i).getGuid());
                    intent.putExtra("linkk", MainActivityCards.this.mRssFeedList.get(i).getLink());
                    intent.putExtra("url", MainActivityCards.TOPSTORIES);
                    MainActivityCards.this.startActivity(intent);
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
            if (view == null) {
                view = View.inflate(MainActivityCards.this, R.layout.list_rowcards, null);
            }
            RSSFeed rSSFeed = this.rssFeedLst.get(i);
            RssHolder rssHolder = new RssHolder();
            rssHolder.title = (TextView) view.findViewById(R.id.site_title);
            new checksim();
            WhiteBackgroundActivition whiteBackgroundActivition = (WhiteBackgroundActivition) new SavePrefs((Activity) MainActivityCards.this, (Class<?>) WhiteBackgroundActivition.class).load();
            if (whiteBackgroundActivition == null || !whiteBackgroundActivition.isWhiteBackground()) {
                rssHolder.title.setTextColor(Color.parseColor("#FF6600"));
            } else {
                rssHolder.title.setTextColor(Color.parseColor("#FF6600"));
            }
            rssHolder.src = (ImageView) view.findViewById(R.id.img12);
            rssHolder.title.setText(rSSFeed.getTitle());
            rSSFeed.getPubDate().substring(0, 16);
            String[] strArrSplit = rSSFeed.getFeedburnerOrigLink().split(" ");
            if (strArrSplit.length > 10) {
                String str = "";
                for (int i2 = 0; i2 < 10; i2++) {
                    str = str + strArrSplit[i2] + " ";
                }
            }
            ImageLoader.getInstance().displayImage(rSSFeed.getGuid(), rssHolder.src, MainActivityCards.this.options);
            return view;
        }
    }

    static class RssHolder {
        ImageView src;
        TextView title;

        RssHolder() {
        }
    }

    private void doRssFeedTask(final String str) {
        findViewById(R.id.progress_smooth).setVisibility(0);
        Toast.makeText(getApplicationContext(), "جارى تحديث الكروت....", 1).show();
        Observable.fromCallable(new Callable() { // from class: com.app.piapostolos.-$$Lambda$MainActivityCards$SqPRlt6k7JJJ8KiXYpQVYzv5dI0
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return this.f$0.lambda$doRssFeedTask$0$MainActivityCards(str);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.app.piapostolos.-$$Lambda$MainActivityCards$-XRdQ-plXi3it1HGWBhRui2ET8E
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.lambda$doRssFeedTask$1$MainActivityCards((List) obj);
            }
        });
    }

    public /* synthetic */ List lambda$doRssFeedTask$0$MainActivityCards(String str) throws Exception {
        NewsFeedParserothers newsFeedParserothers = new NewsFeedParserothers(str);
        this.mNewsFeeder = newsFeedParserothers;
        return newsFeedParserothers.parse();
    }

    public /* synthetic */ void lambda$doRssFeedTask$1$MainActivityCards(final List list) throws Exception {
        findViewById(R.id.progress_smooth).setVisibility(8);
        runOnUiThread(new Runnable() { // from class: com.app.piapostolos.MainActivityCards.3
            @Override // java.lang.Runnable
            public void run() {
                List list2;
                List list3 = list;
                if (list3 != null && list3.size() > 0 && MainActivityCards.this.mRssAdap != null) {
                    MainActivityCards.this.mRssAdap.clear();
                }
                MainActivityCards mainActivityCards = MainActivityCards.this;
                MainActivityCards mainActivityCards2 = MainActivityCards.this;
                mainActivityCards.mRssAdap = mainActivityCards2.new RssAdapter(mainActivityCards2, R.layout.rss_list_item, list);
                List list4 = list;
                if (list4 != null && list4.size() > 0) {
                    MainActivityCards.this.mRssFeedList.clear();
                    MainActivityCards.this.mRssFeedList.addAll(list);
                }
                if (MainActivityCards.this.mRssAdap == null || (list2 = list) == null || list2.size() <= 0) {
                    return;
                }
                MainActivityCards.this.mRssListView.setAdapter((ListAdapter) MainActivityCards.this.mRssAdap);
                CasheFeeds casheFeeds = (CasheFeeds) new SavePrefs((Activity) MainActivityCards.this, (Class<?>) CasheFeeds.class).load();
                if (casheFeeds == null) {
                    casheFeeds = new CasheFeeds();
                }
                casheFeeds.setCardsFeeds(list);
                new SavePrefs((Activity) MainActivityCards.this, (Class<?>) CasheFeeds.class).save(casheFeeds);
            }
        });
    }

    public boolean checkinternet() {
        ConnectionDetector connectionDetector = new ConnectionDetector(getApplicationContext());
        this.cd = connectionDetector;
        return connectionDetector.isConnectingToInternet();
    }
}
