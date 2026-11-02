package com.app.piapostolos.Fragments;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import com.app.piapostolos.Beans.Feeds;
import com.app.piapostolos.Beans.RSSFeed;
import com.app.piapostolos.Beans.RadioQuality;
import com.app.piapostolos.NewRadioActivity;
import com.app.piapostolos.Others.SavePrefs;
import com.app.piapostolos.Others.Util;
import com.app.piapostolos.R;
import com.app.piapostolos.Radio.Controls;
import com.app.piapostolos.Radio.CustomAdapter;
import com.app.piapostolos.Radio.MediaItem;
import com.app.piapostolos.Radio.PlayerConstants;
import com.app.piapostolos.Radio.SongService;
import com.app.piapostolos.Radio.UtilFunctions;
import com.app.piapostolos.app;
import com.facebook.internal.AnalyticsEvents;
import com.github.clans.fab.FloatingActionMenu;
import com.ibm.icu.impl.locale.LanguageTag;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.exceptions.OnErrorNotImplementedException;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import org.apache.http.client.methods.HttpGet;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

/* loaded from: classes.dex */
public class RadioHomeFragment extends Fragment implements View.OnClickListener {
    public static final String CATEGORY = "category";
    public static final String CHANNEL = "channel";
    public static final String DESCRIPTION = "description";
    public static final String FEEDBURNERORIGLINK = "content:encoded";
    public static final String GUID = "guid";
    public static final String ITEM = "item";
    public static final String LINK = "link";
    public static final String PUBLISHEDDATE = "pubDate";
    public static final String TITLE = "title";
    static Button btnNext;
    static Button btnPause;
    static Button btnPlay;
    static Button btnPrevious;
    static ImageView imageViewAlbumArt;
    static LinearLayout linearLayoutPlayingSong;
    static TextView playingSong;
    private ProgressBar animationListener;
    Button btnPlayer;
    Button btnStop;
    private String category;
    private String description;
    private XmlPullParserFactory factory;
    private String feedburner;
    private String guid;
    private String link;
    private FloatingActionMenu mHomeFloatingActionMenu;
    RelativeLayout mediaLayout;
    private XmlPullParser parser;
    ProgressBar progressBar;
    private String pubDate;
    private View rootView;
    private RSSFeed rssFeed;
    private ArrayList<RSSFeed> rssFeedList;
    private String tagName;
    TextView textBufferDuration;
    private String title;
    private InputStream urlStream;
    CustomAdapter customAdapter = null;
    private ArrayList<String> mImages = new ArrayList<>();
    private int quality = 24;
    String new_link = "";

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.rootView = layoutInflater.inflate(R.layout.fragment_home, viewGroup, false);
        initView();
        return this.rootView;
    }

    private void initView() {
        initFloating();
        init();
        playingSong.setText(R.string.listen_now);
        WebView webView = (WebView) this.rootView.findViewById(R.id.wv_radio);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.loadUrl("https://services.invalid/unconfigured");
        webView.setBackgroundColor(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void alert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View viewInflate = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_alert, (ViewGroup) null);
        String string = getActivity().getSharedPreferences(app.PREFS_NAME, 0).getString("skipMessage", "NOT checked");
        final CheckBox checkBox = (CheckBox) viewInflate.findViewById(R.id.skip);
        builder.setView(viewInflate);
        builder.setPositiveButton(R.string.okay, new DialogInterface.OnClickListener() { // from class: com.app.piapostolos.Fragments.RadioHomeFragment.1
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                String str = checkBox.isChecked() ? "checked" : "NOT checked";
                SharedPreferences.Editor editorEdit = RadioHomeFragment.this.getActivity().getSharedPreferences(app.PREFS_NAME, 0).edit();
                editorEdit.putString("skipMessage", str);
                editorEdit.commit();
                RadioHomeFragment.this.startRadio();
            }
        });
        if (!string.equals("checked")) {
            builder.show();
        } else {
            startRadio();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startRadio() {
        boolean zIsServiceRunning;
        PlayerConstants.SONG_PAUSED = false;
        PlayerConstants.SONG_NUMBER = 0;
        if (Build.VERSION.SDK_INT >= 26) {
            zIsServiceRunning = UtilFunctions.isServiceRunning(SongService.class.getName(), getActivity());
        } else {
            zIsServiceRunning = UtilFunctions.isServiceRunning(SongService.class.getName(), getActivity());
        }
        if (!zIsServiceRunning) {
            if (Build.VERSION.SDK_INT >= 26) {
                Intent intent = new Intent(getActivity(), (Class<?>) SongService.class);
                intent.putExtra("quality", this.quality);
                ContextCompat.startForegroundService(getActivity(), intent);
            } else {
                Intent intent2 = new Intent(getActivity(), (Class<?>) SongService.class);
                intent2.putExtra("quality", this.quality);
                getActivity().startService(intent2);
            }
        } else {
            try {
                PlayerConstants.SONG_CHANGE_HANDLER.sendMessage(PlayerConstants.SONG_CHANGE_HANDLER.obtainMessage());
            } catch (Exception unused) {
            }
        }
        updateUI();
        changeButton();
        Controls.playControl(getActivity());
        getRadioDetails();
    }

    private void init() {
        getViews();
        setListeners();
        playingSong.setSelected(true);
        this.progressBar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_IN);
        if (((RadioQuality) new SavePrefs((Activity) getActivity(), (Class<?>) RadioQuality.class).load()) == null) {
            this.quality = 64;
            return;
        }
        this.quality = 64;
        PlayerConstants.SONGS_LIST = UtilFunctions.listOfSongs(getActivity(), this.quality);
        setListItems();
    }

    @Override // androidx.fragment.app.Fragment
    public void setUserVisibleHint(boolean z) {
        boolean zIsServiceRunning;
        super.setUserVisibleHint(z);
        if (Build.VERSION.SDK_INT >= 26) {
            zIsServiceRunning = UtilFunctions.isServiceRunning(SongService.class.getName(), getActivity());
        } else {
            zIsServiceRunning = UtilFunctions.isServiceRunning(SongService.class.getName(), getActivity());
        }
        if (z && zIsServiceRunning) {
            getRadioDetails();
        }
        if (z) {
            return;
        }
        parseData();
    }

    private void setListItems() {
        this.customAdapter = new CustomAdapter(getActivity(), R.layout.custom_list, PlayerConstants.SONGS_LIST);
    }

    private void getViews() {
        playingSong = (TextView) this.rootView.findViewById(R.id.textNowPlaying);
        this.btnPlayer = (Button) this.rootView.findViewById(R.id.btnMusicPlayer);
        this.mediaLayout = (RelativeLayout) this.rootView.findViewById(R.id.linearLayoutMusicList);
        btnPause = (Button) this.rootView.findViewById(R.id.btnPause);
        btnPlay = (Button) this.rootView.findViewById(R.id.btnPlay);
        linearLayoutPlayingSong = (LinearLayout) this.rootView.findViewById(R.id.linearLayoutPlayingSong);
        this.progressBar = (ProgressBar) this.rootView.findViewById(R.id.progressBar);
        this.btnStop = (Button) this.rootView.findViewById(R.id.btnStop);
        this.textBufferDuration = (TextView) this.rootView.findViewById(R.id.textBufferDuration);
        imageViewAlbumArt = (ImageView) this.rootView.findViewById(R.id.imageViewAlbumArt);
        btnNext = (Button) this.rootView.findViewById(R.id.btnNext);
        btnPrevious = (Button) this.rootView.findViewById(R.id.btnPrevious);
    }

    private void setListeners() {
        Log.d("TAG", "TAG Tapped INOUT(OUT)");
        this.btnPlayer.setOnClickListener(new View.OnClickListener() { // from class: com.app.piapostolos.Fragments.RadioHomeFragment.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
            }
        });
        btnPlay.setOnClickListener(new View.OnClickListener() { // from class: com.app.piapostolos.Fragments.RadioHomeFragment.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Log.d("TAG", "TAG Tapped INOUT(IN)");
                if (Util.hasConnection(RadioHomeFragment.this.getActivity())) {
                    RadioHomeFragment.this.alert();
                } else {
                    Util.showToast(RadioHomeFragment.this.getActivity(), R.string.no_connection);
                }
            }
        });
        btnPause.setOnClickListener(new View.OnClickListener() { // from class: com.app.piapostolos.Fragments.RadioHomeFragment.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ((NewRadioActivity) RadioHomeFragment.this.getActivity()).dismissProgress2();
                PlayerConstants.SONG_PAUSED = true;
                RadioHomeFragment.this.updateUI();
                RadioHomeFragment.changeButton();
                Controls.pauseControl(RadioHomeFragment.this.getActivity());
                RadioHomeFragment.this.getActivity().startService(new Intent(RadioHomeFragment.this.getContext(), (Class<?>) SongService.class).setAction("STOP_ACTION"));
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() { // from class: com.app.piapostolos.Fragments.RadioHomeFragment.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Controls.nextControl(RadioHomeFragment.this.getActivity());
            }
        });
        btnPrevious.setOnClickListener(new View.OnClickListener() { // from class: com.app.piapostolos.Fragments.RadioHomeFragment.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Controls.previousControl(RadioHomeFragment.this.getActivity());
            }
        });
        this.btnStop.setOnClickListener(new View.OnClickListener() { // from class: com.app.piapostolos.Fragments.RadioHomeFragment.7
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= 26) {
                    RadioHomeFragment.this.getActivity().stopService(new Intent(RadioHomeFragment.this.getActivity(), (Class<?>) SongService.class));
                } else {
                    RadioHomeFragment.this.getActivity().stopService(new Intent(RadioHomeFragment.this.getActivity(), (Class<?>) SongService.class));
                }
            }
        });
        imageViewAlbumArt.setOnClickListener(new View.OnClickListener() { // from class: com.app.piapostolos.Fragments.RadioHomeFragment.8
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
            }
        });
    }

    public void stopPlayer() {
        Button button = btnPause;
        if (button != null) {
            button.performClick();
            return;
        }
        PlayerConstants.SONG_PAUSED = true;
        updateUI();
        changeButton();
        Controls.pauseControl(getActivity());
        if (Build.VERSION.SDK_INT >= 26) {
            getActivity().stopService(new Intent(getActivity(), (Class<?>) SongService.class));
        } else {
            getActivity().stopService(new Intent(getActivity(), (Class<?>) SongService.class));
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        boolean zIsServiceRunning;
        super.onResume();
        try {
            if (Build.VERSION.SDK_INT >= 26) {
                zIsServiceRunning = UtilFunctions.isServiceRunning(SongService.class.getName(), getActivity());
            } else {
                zIsServiceRunning = UtilFunctions.isServiceRunning(SongService.class.getName(), getActivity());
            }
            if (zIsServiceRunning) {
                updateUI();
            }
            changeButton();
            PlayerConstants.PROGRESSBAR_HANDLER = new Handler() { // from class: com.app.piapostolos.Fragments.RadioHomeFragment.9
                @Override // android.os.Handler
                public void handleMessage(Message message) {
                    Integer[] numArr = (Integer[]) message.obj;
                    RadioHomeFragment.this.textBufferDuration.setText(UtilFunctions.getDuration(numArr[0].intValue()));
                    RadioHomeFragment.this.progressBar.setProgress(numArr[2].intValue());
                }
            };
        } catch (Exception unused) {
        }
    }

    public void updateUI() {
        try {
            MediaItem mediaItem = PlayerConstants.SONGS_LIST.get(PlayerConstants.SONG_NUMBER);
            playingSong.setText(mediaItem.getTitle() + " " + mediaItem.getArtist() + " - " + mediaItem.getAlbum());
            imageViewAlbumArt.setBackgroundDrawable(new BitmapDrawable(UtilFunctions.getDefaultAlbumArt(getActivity())));
            linearLayoutPlayingSong.setVisibility(0);
        } catch (Exception unused) {
        }
    }

    public static void changeButton() {
        if (PlayerConstants.SONG_PAUSED) {
            btnPause.setVisibility(8);
            btnPlay.setVisibility(0);
        } else {
            btnPause.setVisibility(0);
            btnPlay.setVisibility(8);
        }
    }

    public void changeUI() {
        updateUI();
        changeButton();
    }

    private String getRadioDetails() {
        try {
            Observable.fromCallable(new Callable() { // from class: com.app.piapostolos.Fragments.-$$Lambda$RadioHomeFragment$x5BQz5vH43FjPEQ8S5tDh5hLzao
                @Override // java.util.concurrent.Callable
                public final Object call() {
                    return RadioHomeFragment.lambda$getRadioDetails$0();
                }
            }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.app.piapostolos.Fragments.-$$Lambda$RadioHomeFragment$VPbsSyyNQEhaB3OiGCo8bD6dISg
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    this.f$0.lambda$getRadioDetails$2$RadioHomeFragment((String) obj);
                }
            });
            return "";
        } catch (OnErrorNotImplementedException unused) {
            return "";
        }
    }

    static /* synthetic */ String lambda$getRadioDetails$0() throws Exception {
        Document document = Jsoup.parse(new URL(PlayerConstants.radioUrlGetInfo).openStream(), "UTF-8", PlayerConstants.radioUrlGetInfo);
        for (int i = 0; i < document.getAllElements().size(); i++) {
            System.out.println("Song Title: " + document.getElementsByIndexEquals(i).text());
            if (document.getElementsByIndexEquals(i).text().toString().startsWith("Currently playing:")) {
                return document.getElementsByIndexEquals(i).text().toString().replace("Currently playing:", "").replace(AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN, "").replaceAll(LanguageTag.SEP, "");
            }
        }
        return null;
    }

    public /* synthetic */ void lambda$getRadioDetails$2$RadioHomeFragment(String str) throws Exception {
        System.out.println("Song Current: " + str);
        PlayerConstants.SONGS_LIST.get(PlayerConstants.SONG_NUMBER).setAlbum(str);
        MediaItem mediaItem = PlayerConstants.SONGS_LIST.get(PlayerConstants.SONG_NUMBER);
        playingSong.setText(mediaItem.getTitle() + " " + mediaItem.getArtist() + " - " + mediaItem.getAlbum());
        Observable.timer(45L, TimeUnit.SECONDS).map(new Function() { // from class: com.app.piapostolos.Fragments.-$$Lambda$RadioHomeFragment$_g_KjwVeIuoH3XptaS1c5hdsKlM
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                return this.f$0.lambda$getRadioDetails$1$RadioHomeFragment((Long) obj);
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe();
    }

    public /* synthetic */ String lambda$getRadioDetails$1$RadioHomeFragment(Long l) throws Exception {
        return getRadioDetails();
    }

    public static InputStream downloadUrl(String str) throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
        httpURLConnection.setRequestMethod(HttpGet.METHOD_NAME);
        httpURLConnection.setDoInput(true);
        httpURLConnection.connect();
        return httpURLConnection.getInputStream();
    }

    private void initFeeds() {
        Feeds feeds = (Feeds) new SavePrefs((Activity) getActivity(), (Class<?>) Feeds.class).load();
        if (feeds != null && feeds.getFeeds() != null && feeds.getFeeds().size() > 0) {
            for (int i = 0; i < feeds.getFeeds().size(); i++) {
                this.mImages.add(feeds.getFeeds().get(i).getFeedburnerOrigLink());
            }
        }
        if (Util.hasConnection(getActivity())) {
            parseData();
        }
    }

    private void parseData() {
        Observable.fromCallable(new Callable() { // from class: com.app.piapostolos.Fragments.-$$Lambda$RadioHomeFragment$y4gB_MxgnMothHEi_1ilpWdTPnA
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return this.f$0.lambda$parseData$3$RadioHomeFragment();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.app.piapostolos.Fragments.-$$Lambda$RadioHomeFragment$ClEajlKq6G-VGQO-Cdbj-ptL_gM
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.lambda$parseData$4$RadioHomeFragment((ArrayList) obj);
            }
        });
    }

    public /* synthetic */ void lambda$parseData$4$RadioHomeFragment(ArrayList arrayList) throws Exception {
        if (arrayList == null || getActivity() == null) {
            return;
        }
        SavePrefs savePrefs = new SavePrefs((Activity) getActivity(), (Class<?>) Feeds.class);
        Feeds feeds = new Feeds();
        feeds.setFeeds(arrayList);
        savePrefs.save(feeds);
        this.mImages.clear();
        for (int i = 0; i < arrayList.size(); i++) {
            this.mImages.add(((RSSFeed) arrayList.get(i)).getFeedburnerOrigLink());
        }
    }

    /* renamed from: parse, reason: merged with bridge method [inline-methods] */
    public ArrayList<RSSFeed> lambda$parseData$3$RadioHomeFragment() throws XmlPullParserException, IOException {
        try {
            XmlPullParserFactory xmlPullParserFactoryNewInstance = XmlPullParserFactory.newInstance();
            this.factory = xmlPullParserFactoryNewInstance;
            this.parser = xmlPullParserFactoryNewInstance.newPullParser();
            InputStream inputStreamDownloadUrl = downloadUrl("http://gergesyaromany.net/category/radiohashtag3/feed");
            this.urlStream = inputStreamDownloadUrl;
            this.parser.setInput(inputStreamDownloadUrl, null);
            int eventType = this.parser.getEventType();
            boolean z = false;
            this.rssFeed = new RSSFeed();
            this.rssFeedList = new ArrayList<>();
            while (eventType != 1 && !z) {
                String name = this.parser.getName();
                this.tagName = name;
                if (eventType == 2) {
                    if (name.equals("item")) {
                        this.rssFeed = new RSSFeed();
                    }
                    if (this.tagName.equals("title")) {
                        this.title = this.parser.nextText().toString();
                    }
                    this.tagName.equals("link");
                    this.tagName.equals("description");
                    if (this.tagName.equals("category")) {
                        this.category = this.parser.nextText().toString();
                    }
                    if (this.tagName.equals("pubDate")) {
                        this.pubDate = this.parser.nextText().toString();
                    }
                    if (this.tagName.equals("guid")) {
                        this.guid = this.parser.nextText().toString();
                    }
                    if (this.tagName.equals("content:encoded")) {
                        String string = this.parser.nextText().toString();
                        this.feedburner = string;
                        if (!string.equals(null) && !this.feedburner.equals("") && this.feedburner.contains("src=")) {
                            int iIndexOf = this.feedburner.indexOf("src=\"") + 5;
                            String strSubstring = this.feedburner.substring(iIndexOf, this.feedburner.indexOf("\"", iIndexOf));
                            this.feedburner = strSubstring;
                            if (!strSubstring.contains("http://gergesyaromany.net")) {
                                this.feedburner = "http://gergesyaromany.net" + this.feedburner;
                            }
                        } else {
                            this.feedburner = "";
                        }
                    }
                } else if (eventType == 3) {
                    if (name.equals("channel")) {
                        z = true;
                    } else if (this.tagName.equals("item")) {
                        RSSFeed rSSFeed = new RSSFeed(this.title, this.new_link, this.description, this.category, this.pubDate, this.guid, this.feedburner);
                        this.rssFeed = rSSFeed;
                        this.rssFeedList.add(rSSFeed);
                        this.new_link = "";
                    }
                }
                eventType = this.parser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.rssFeedList;
    }

    private void initFloating() {
        FloatingActionMenu floatingActionMenu = (FloatingActionMenu) this.rootView.findViewById(R.id.menu_options);
        this.mHomeFloatingActionMenu = floatingActionMenu;
        floatingActionMenu.setVisibility(8);
        this.mHomeFloatingActionMenu.setIconAnimated(false);
        this.mHomeFloatingActionMenu.setClosedOnTouchOutside(true);
        this.mHomeFloatingActionMenu.setOnMenuButtonClickListener(new View.OnClickListener() { // from class: com.app.piapostolos.Fragments.RadioHomeFragment.10
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                RadioHomeFragment.this.mHomeFloatingActionMenu.toggle(true);
            }
        });
        this.rootView.findViewById(R.id.fb_quality).setOnClickListener(this);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (view.getId() != R.id.fb_quality) {
            return;
        }
        this.mHomeFloatingActionMenu.toggle(false);
    }
}
