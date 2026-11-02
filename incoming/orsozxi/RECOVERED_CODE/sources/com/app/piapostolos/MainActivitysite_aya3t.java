package com.app.piapostolos;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.view.InputDeviceCompat;
import androidx.core.view.ViewCompat;
import com.app.piapostolos.Adapters.ExpandableListAdapter;
import com.app.piapostolos.Beans.CasheFeeds;
import com.app.piapostolos.Beans.RSSFeed;
import com.app.piapostolos.Beans.WhiteBackgroundActivition;
import com.app.piapostolos.Others.AlertDialogManager;
import com.app.piapostolos.Others.ConnectionDetector;
import com.app.piapostolos.Others.NewsFeedParserothers;
import com.app.piapostolos.Others.SavePrefs;
import com.app.piapostolos.Others.checksim;
import com.app.piapostolos.Views.MenuDateView;
import com.navdrawer.SimpleSideDrawer;
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
public class MainActivitysite_aya3t extends BaseActivity implements AdapterView.OnItemClickListener {
    private static String TOPSTORIES = "";
    AlertDialogManager alert = new AlertDialogManager();
    ConnectionDetector cd;
    private View content;
    private GestureDetector gestureDetector;
    private NewsFeedParserothers mNewsFeeder;
    private RssAdapter mRssAdap;
    public List<RSSFeed> mRssFeedList;
    private ListView mRssListView;
    private MenuDateView menuDateView;

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
        ((TextView) findViewById(R.id.tv_header_title)).setText(R.string.t_true_ayah);
        getWindow().addFlags(128);
        new checksim();
        WhiteBackgroundActivition whiteBackgroundActivition = (WhiteBackgroundActivition) new SavePrefs((Activity) this, (Class<?>) WhiteBackgroundActivition.class).load();
        if (whiteBackgroundActivition == null || !whiteBackgroundActivition.isWhiteBackground()) {
            getWindow().setBackgroundDrawableResource(R.drawable.background);
        }
        TOPSTORIES = "https://services.invalid/unconfigured";
        Button button = (Button) findViewById(R.id.btn_slide);
        final SimpleSideDrawer simpleSideDrawer = new SimpleSideDrawer(this);
        simpleSideDrawer.setLeftBehindContentView(R.layout.left_menu_ex);
        button.setVisibility(0);
        Slider.prepareListData(this);
        ((ExpandableListView) findViewById(R.id.lvExp)).setAdapter(new ExpandableListAdapter(this, this, Slider.listDataHeader, Slider.listDataChild));
        button.setOnClickListener(new View.OnClickListener() { // from class: com.app.piapostolos.MainActivitysite_aya3t.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                simpleSideDrawer.toggleLeftDrawer();
            }
        });
        try {
            this.mRssListView = (ListView) findViewById(R.id.rss_list_view);
            this.mRssFeedList = new ArrayList();
            CasheFeeds casheFeeds = (CasheFeeds) new SavePrefs((Activity) this, (Class<?>) CasheFeeds.class).load();
            if (casheFeeds != null && casheFeeds.getTrueAyahFeeds() != null && casheFeeds.getTrueAyahFeeds().size() > 0) {
                this.mRssAdap = new RssAdapter(this, R.layout.rss_list_item, casheFeeds.getTrueAyahFeeds());
                this.mRssFeedList.addAll(casheFeeds.getTrueAyahFeeds());
                this.mRssListView.setAdapter((ListAdapter) this.mRssAdap);
            }
            if (!checkinternet()) {
                Toast.makeText(getApplicationContext(), R.string.no_internet, 1).show();
            } else {
                doRssFeed(TOPSTORIES);
            }
            this.mRssListView.setOnItemClickListener(this);
            this.mRssListView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.app.piapostolos.MainActivitysite_aya3t.2
                @Override // android.widget.AdapterView.OnItemClickListener
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                    Intent intent = new Intent();
                    intent.setClass(MainActivitysite_aya3t.this, AyatDetailsActivity.class);
                    intent.putExtra("title", MainActivitysite_aya3t.this.mRssFeedList.get(i).getTitle());
                    intent.putExtra("pubDate", MainActivitysite_aya3t.this.mRssFeedList.get(i).getPubDate());
                    intent.putExtra("feedburner", MainActivitysite_aya3t.this.mRssFeedList.get(i).getFeedburnerOrigLink());
                    intent.putExtra("linkk", MainActivitysite_aya3t.this.mRssFeedList.get(i).getLink());
                    intent.putExtra("url", MainActivitysite_aya3t.TOPSTORIES);
                    MainActivitysite_aya3t.this.startActivity(intent);
                }
            });
        } catch (Exception unused) {
            Log.e("Error", "Loading exception");
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStart() {
        super.onStart();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStop() {
        super.onStop();
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
                view = View.inflate(MainActivitysite_aya3t.this, R.layout.list_row_ayat, null);
            }
            RSSFeed rSSFeed = this.rssFeedLst.get(i);
            RssHolder rssHolder = new RssHolder();
            rssHolder.description = (TextView) view.findViewById(R.id.desc10);
            rssHolder.title = (TextView) view.findViewById(R.id.site_title);
            rssHolder.pubDate = (TextView) view.findViewById(R.id.datee);
            rssHolder.link = (TextView) view.findViewById(R.id.yout);
            new checksim();
            rssHolder.title.setText(rSSFeed.getTitle());
            String strSubstring = rSSFeed.getPubDate().substring(0, 16);
            String[] strArrSplit = rSSFeed.getFeedburnerOrigLink().split(" ");
            String str = "";
            if (strArrSplit.length > 10) {
                for (int i2 = 0; i2 < 10; i2++) {
                    str = str + strArrSplit[i2] + " ";
                }
                str = str + "...المزيد";
            }
            WhiteBackgroundActivition whiteBackgroundActivition = (WhiteBackgroundActivition) new SavePrefs((Activity) MainActivitysite_aya3t.this, (Class<?>) WhiteBackgroundActivition.class).load();
            if (whiteBackgroundActivition == null || !whiteBackgroundActivition.isWhiteBackground()) {
                rssHolder.description.setTextColor(-1);
                rssHolder.title.setTextColor(Color.parseColor("#FF6600"));
                rssHolder.link.setTextColor(InputDeviceCompat.SOURCE_ANY);
                rssHolder.pubDate.setTextColor(InputDeviceCompat.SOURCE_ANY);
            } else {
                rssHolder.description.setTextColor(ViewCompat.MEASURED_STATE_MASK);
                rssHolder.title.setTextColor(ViewCompat.MEASURED_STATE_MASK);
                rssHolder.link.setTextColor(-16776961);
                rssHolder.pubDate.setTextColor(-16776961);
            }
            rssHolder.description.setText(str);
            rssHolder.pubDate.setText(strSubstring);
            rssHolder.link.setText(rSSFeed.getLink());
            return view;
        }
    }

    static class RssHolder {
        TextView description;
        TextView link;
        TextView pubDate;
        TextView title;

        RssHolder() {
        }
    }

    private void doRssFeed(final String str) {
        findViewById(R.id.progress_smooth).setVisibility(0);
        Toast.makeText(getApplicationContext(), R.string.true_ayah_update, 1).show();
        Observable.fromCallable(new Callable() { // from class: com.app.piapostolos.-$$Lambda$MainActivitysite_aya3t$EfZg6fcKuPGprxFM7wnAv9ddzE0
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return this.f$0.lambda$doRssFeed$0$MainActivitysite_aya3t(str);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.app.piapostolos.-$$Lambda$MainActivitysite_aya3t$1C1bnJfg8-lRttiA3sYkN9ZoztI
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.lambda$doRssFeed$1$MainActivitysite_aya3t((List) obj);
            }
        });
    }

    public /* synthetic */ List lambda$doRssFeed$0$MainActivitysite_aya3t(String str) throws Exception {
        NewsFeedParserothers newsFeedParserothers = new NewsFeedParserothers(str);
        this.mNewsFeeder = newsFeedParserothers;
        return newsFeedParserothers.parse();
    }

    public /* synthetic */ void lambda$doRssFeed$1$MainActivitysite_aya3t(final List list) throws Exception {
        findViewById(R.id.progress_smooth).setVisibility(8);
        runOnUiThread(new Runnable() { // from class: com.app.piapostolos.MainActivitysite_aya3t.3
            @Override // java.lang.Runnable
            public void run() {
                List list2;
                List list3 = list;
                if (list3 != null && list3.size() > 0 && MainActivitysite_aya3t.this.mRssAdap != null) {
                    MainActivitysite_aya3t.this.mRssAdap.clear();
                }
                MainActivitysite_aya3t mainActivitysite_aya3t = MainActivitysite_aya3t.this;
                MainActivitysite_aya3t mainActivitysite_aya3t2 = MainActivitysite_aya3t.this;
                mainActivitysite_aya3t.mRssAdap = mainActivitysite_aya3t2.new RssAdapter(mainActivitysite_aya3t2, R.layout.rss_list_item, list);
                List list4 = list;
                if (list4 != null && list4.size() > 0) {
                    MainActivitysite_aya3t.this.mRssFeedList.clear();
                    MainActivitysite_aya3t.this.mRssFeedList.addAll(list);
                }
                if (MainActivitysite_aya3t.this.mRssAdap == null || (list2 = list) == null || list2.size() <= 0) {
                    return;
                }
                MainActivitysite_aya3t.this.mRssListView.setAdapter((ListAdapter) MainActivitysite_aya3t.this.mRssAdap);
                CasheFeeds casheFeeds = (CasheFeeds) new SavePrefs((Activity) MainActivitysite_aya3t.this, (Class<?>) CasheFeeds.class).load();
                if (casheFeeds == null) {
                    casheFeeds = new CasheFeeds();
                }
                casheFeeds.setTrueAyahFeeds(list);
                new SavePrefs((Activity) MainActivitysite_aya3t.this, (Class<?>) CasheFeeds.class).save(casheFeeds);
            }
        });
    }

    public boolean checkinternet() {
        ConnectionDetector connectionDetector = new ConnectionDetector(getApplicationContext());
        this.cd = connectionDetector;
        return connectionDetector.isConnectingToInternet();
    }
}
